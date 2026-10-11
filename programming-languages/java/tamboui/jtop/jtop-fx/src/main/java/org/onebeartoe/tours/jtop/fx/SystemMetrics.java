package org.onebeartoe.tours.jtop.fx;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

/**
 * Reads system metrics using OSHI (cross-platform).
 * Thread-safe: uses a ReentrantReadWriteLock for safe concurrent access.
 */
public final class SystemMetrics {

    public static final int HISTORY_SIZE = 60;

    private final SystemInfo systemInfo = new SystemInfo();
    private final GlobalMemory memory = systemInfo.getHardware().getMemory();
    private final CentralProcessor processor = systemInfo.getHardware().getProcessor();
    private final OperatingSystem operatingSystem = systemInfo.getOperatingSystem();

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    // CPU tracking
    private final int numCpus;
    private final double[] coreUsage;
    private final List<Deque<Long>> coreHistory;
    private long[][] lastCpuTicks;

    // Memory tracking
    private final Deque<Long> memoryHistory = new ArrayDeque<>(HISTORY_SIZE);
    private long memTotal;
    private long memUsed;
    private long memAvailable;
    private long swapTotal;
    private long swapFree;

    // System info
    private double uptime;
    private double loadAvg1;
    private double loadAvg5;
    private double loadAvg15;

    // Process list
    private List<ProcessInfo> processes = new ArrayList<>();
    private final Map<Integer, OSProcess> lastProcessesByPid = new HashMap<>();

    /**
     * Information about a running process.
     */
    public record ProcessInfo(
        int pid,
        String name,
        char state,
        double cpuPercent,
        long memoryKb,
        String user
    ) {
        public String stateString() {
            return String.valueOf(state);
        }

        public String formattedMemory() {
            if (memoryKb < 1024) {
                return memoryKb + " KB";
            }
            if (memoryKb < 1024 * 1024) {
                return String.format("%.1f MB", memoryKb / 1024.0);
            }
            return String.format("%.2f GB", memoryKb / (1024.0 * 1024));
        }

        public String formattedCpu() {
            return String.format("%.1f%%", cpuPercent);
        }
    }

    /**
     * Sort modes for the process list.
     */
    public enum SortMode {
        CPU, MEMORY, PID
    }

    public SystemMetrics() {
        numCpus = processor.getLogicalProcessorCount();
        coreUsage = new double[numCpus];
        coreHistory = new ArrayList<>(numCpus);

        for (var i = 0; i < numCpus; i++) {
            var history = new ArrayDeque<Long>(HISTORY_SIZE);
            for (var j = 0; j < HISTORY_SIZE; j++) {
                history.add(0L);
            }
            coreHistory.add(history);
        }

        for (var i = 0; i < HISTORY_SIZE; i++) {
            memoryHistory.add(0L);
        }
    }

    /**
     * Updates all metrics.
     * This method should be called from a background thread.
     */
    public void update(SortMode sortMode) {
        lock.writeLock().lock();
        try {
            updateCpuUsage();
            updateMemoryInfo();
            updateSystemInfo();
            updateProcessList(sortMode);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Executes a read operation under the read lock.
     */
    public <T> T read(Supplier<T> reader) {
        lock.readLock().lock();
        try {
            return reader.get();
        } finally {
            lock.readLock().unlock();
        }
    }

    // ==================== Accessors (must be called within read()) ====================

    public int numCpus() {
        return numCpus;
    }

    public double coreUsage(int core) {
        return core >= 0 && core < numCpus ? coreUsage[core] : 0;
    }

    public double averageCpuUsage() {
        double avg = 0;
        for (var i = 0; i < numCpus; i++) {
            avg += coreUsage[i];
        }
        return numCpus > 0 ? avg / numCpus : 0;
    }

    public List<Long> coreHistory(int core) {
        return core >= 0 && core < numCpus ? List.copyOf(coreHistory.get(core)) : List.of();
    }

    public List<List<Long>> allCoreHistories() {
        var result = new ArrayList<List<Long>>(numCpus);
        for (var history : coreHistory) {
            result.add(List.copyOf(history));
        }
        return result;
    }

    public long memTotal() {
        return memTotal;
    }

    public long memUsed() {
        return memUsed;
    }

    public long memAvailable() {
        return memAvailable;
    }

    public long swapTotal() {
        return swapTotal;
    }

    public long swapFree() {
        return swapFree;
    }

    public long swapUsed() {
        return swapTotal - swapFree;
    }

    public double memoryRatio() {
        return memTotal > 0 ? (double) memUsed / memTotal : 0;
    }

    public List<Long> memoryHistory() {
        return List.copyOf(memoryHistory);
    }

    public double uptime() {
        return uptime;
    }

    public double loadAvg1() {
        return loadAvg1;
    }

    public double loadAvg5() {
        return loadAvg5;
    }

    public double loadAvg15() {
        return loadAvg15;
    }

    public List<ProcessInfo> processes() {
        return processes;
    }

    // ==================== Private Update Methods ====================

    private void updateCpuUsage() {
        if (lastCpuTicks == null) {
            lastCpuTicks = processor.getProcessorCpuLoadTicks();
            return;
        }

        var load = processor.getProcessorCpuLoadBetweenTicks(lastCpuTicks);
        lastCpuTicks = processor.getProcessorCpuLoadTicks();

        for (var coreId = 0; coreId < Math.min(numCpus, load.length); coreId++) {
            var usage = 100.0 * load[coreId];
            if (Double.isNaN(usage) || Double.isInfinite(usage)) {
                usage = 0;
            }
            coreUsage[coreId] = Math.max(0, Math.min(100, usage));

            var history = coreHistory.get(coreId);
            if (history.size() >= HISTORY_SIZE) {
                history.removeFirst();
            }
            history.addLast((long) coreUsage[coreId]);
        }
    }

    private void updateMemoryInfo() {
        memTotal = memory.getTotal() / 1024;
        memAvailable = memory.getAvailable() / 1024;
        memUsed = Math.max(0, memTotal - memAvailable);

        var vm = memory.getVirtualMemory();
        swapTotal = vm.getSwapTotal() / 1024;
        var swapUsedVal = vm.getSwapUsed() / 1024;
        swapFree = Math.max(0, swapTotal - swapUsedVal);

        var memPercent = memTotal > 0 ? (memUsed * 100) / memTotal : 0;
        if (memoryHistory.size() >= HISTORY_SIZE) {
            memoryHistory.removeFirst();
        }
        memoryHistory.addLast(memPercent);
    }

    private void updateSystemInfo() {
        uptime = operatingSystem.getSystemUptime();

        var la = processor.getSystemLoadAverage(3);
        if (la != null && la.length >= 3) {
            loadAvg1 = la[0] < 0 ? 0 : la[0];
            loadAvg5 = la[1] < 0 ? 0 : la[1];
            loadAvg15 = la[2] < 0 ? 0 : la[2];
        } else {
            loadAvg1 = 0;
            loadAvg5 = 0;
            loadAvg15 = 0;
        }
    }

    private void updateProcessList(SortMode sortMode) {
        var newProcesses = new ArrayList<ProcessInfo>();

        var osProcesses = operatingSystem.getProcesses();
        var newByPid = new HashMap<Integer, OSProcess>(osProcesses.size());

        for (var p : osProcesses) {
            var pid = p.getProcessID();
            var prev = lastProcessesByPid.get(pid);

            var cpuPercent = 0.0;
            try {
                if (prev != null) {
                    cpuPercent = 100.0 * p.getProcessCpuLoadBetweenTicks(prev);
                }
            } catch (Exception ignored) {
                // Some platforms/permissions may fail per-process CPU query.
            }
            if (Double.isNaN(cpuPercent) || Double.isInfinite(cpuPercent)) {
                cpuPercent = 0;
            }

            var memoryKb = p.getResidentMemory() / 1024;
            var user = p.getUser();
            if (user == null || user.isBlank()) {
                user = "?";
            }

            var name = p.getCommandLine();
            if (name == null || name.isBlank()) {
                name = p.getName();
            }
            if (name == null || name.isBlank()) {
                name = "?";
            }

            newProcesses.add(new ProcessInfo(
                pid,
                name,
                mapState(p.getState()),
                Math.max(0, cpuPercent),
                Math.max(0, memoryKb),
                user
            ));

            newByPid.put(pid, p);
        }

        var comparator = switch (sortMode) {
            case CPU -> Comparator.comparingDouble(ProcessInfo::cpuPercent).reversed();
            case MEMORY -> Comparator.comparingLong(ProcessInfo::memoryKb).reversed();
            case PID -> Comparator.comparingInt(ProcessInfo::pid);
        };
        newProcesses.sort(comparator);

        this.processes = newProcesses;
        lastProcessesByPid.clear();
        lastProcessesByPid.putAll(newByPid);
    }

    private static char mapState(OSProcess.State state) {
        if (state == null) {
            return '?';
        }
        return switch (state) {
            case RUNNING -> 'R';
            case SLEEPING -> 'S';
            case WAITING -> 'D';
            case STOPPED -> 'T';
            case ZOMBIE -> 'Z';
            default -> '?';
        };
    }
}
