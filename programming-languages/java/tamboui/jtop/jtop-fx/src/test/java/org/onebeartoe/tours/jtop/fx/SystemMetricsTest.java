package org.onebeartoe.tours.jtop.fx;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class SystemMetricsTest {

    @Test
    public void testMetricsUpdateAndRead() {
        SystemMetrics metrics = new SystemMetrics();
        metrics.update(SystemMetrics.SortMode.CPU);

        metrics.read(() -> 
        {
            assertTrue(metrics.numCpus() > 0, "Number of CPUs should be greater than 0");
            assertTrue(metrics.averageCpuUsage() >= 0.0, "CPU usage should be >= 0");
            assertTrue(metrics.memTotal() > 0, "Memory total should be > 0");
            assertNotNull(metrics.processes(), "Processes list should not be null");
            assertFalse(metrics.processes().isEmpty(), "Processes list should not be empty");
            
            return null;
        });
    }
}
