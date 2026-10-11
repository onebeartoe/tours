package org.onebeartoe.tours.jtop.fx;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Modern JavaFX Controller for JTop.
 */
public class JTopController {

    public enum CpuViewMode {
        BARS,
        SPARKLINES,
        CHART
    }

    private final SystemMetrics metrics = new SystemMetrics();
    private final AtomicReference<SystemMetrics.SortMode> sortMode =
        new AtomicReference<>(SystemMetrics.SortMode.CPU);
    private final AtomicReference<CpuViewMode> cpuViewMode =
        new AtomicReference<>(CpuViewMode.BARS);

    private ScheduledExecutorService scheduler;

    // FXML Header & Controls
    @FXML private Button btnSort;
    @FXML private Button btnCpuView;
    @FXML private Button btnQuit;

    // FXML CPU Section
    @FXML private Label lblCpuAvg;
    @FXML private StackPane cpuViewStack;
    @FXML private ScrollPane cpuBarsScrollPane;
    @FXML private VBox cpuBarsContainer;
    @FXML private ScrollPane cpuSparklinesScrollPane;
    @FXML private VBox cpuSparklinesContainer;
    @FXML private VBox cpuChartContainer;
    @FXML private LineChart<Number, Number> cpuHistoryChart;

    // FXML Memory Section
    @FXML private Label lblMemRatio;
    @FXML private ProgressBar memProgressBar;
    @FXML private Label lblMemDetails;
    @FXML private Label lblSwapDetails;
    @FXML private LineChart<Number, Number> memHistoryChart;

    // FXML System Section
    @FXML private Label lblUptime;
    @FXML private Label lblLoadAvg;
    @FXML private Label lblCores;
    @FXML private Label lblTotalMem;
    @FXML private Label lblSwapSummary;

    // FXML Process Table Section
    @FXML private Label lblProcessCount;
    @FXML private Label lblCurrentSort;
    @FXML private TableView<SystemMetrics.ProcessInfo> processTable;
    @FXML private TableColumn<SystemMetrics.ProcessInfo, Integer> colPid;
    @FXML private TableColumn<SystemMetrics.ProcessInfo, String> colUser;
    @FXML private TableColumn<SystemMetrics.ProcessInfo, String> colState;
    @FXML private TableColumn<SystemMetrics.ProcessInfo, String> colMem;
    @FXML private TableColumn<SystemMetrics.ProcessInfo, String> colCpu;
    @FXML private TableColumn<SystemMetrics.ProcessInfo, String> colCommand;

    // FXML Status Bar
    @FXML private Label lblStatus;

    // Persistent UI elements for CPU Core bars/sparklines
    private ProgressBar[] coreProgressBars;
    private Label[] coreValueLabels;
    private Canvas[] coreSparklineCanvases;
    private Label[] coreSparklineValueLabels;

    // Chart Series
    private final XYChart.Series<Number, Number> cpuSeries = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> memSeries = new XYChart.Series<>();

    // Process Table Data
    private final ObservableList<SystemMetrics.ProcessInfo> processList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        initCpuControls();
        initCharts();
        initTable();
        startMetricsPolling();
    }

    private void initCpuControls() {
        int numCpus = metrics.numCpus();
        coreProgressBars = new ProgressBar[numCpus];
        coreValueLabels = new Label[numCpus];
        coreSparklineCanvases = new Canvas[numCpus];
        coreSparklineValueLabels = new Label[numCpus];

        for (int i = 0; i < numCpus; i++) {
            // Bars row
            var barRow = new HBox(8);
            barRow.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

            var lblCore = new Label(String.format("CPU %-2d", i));
            lblCore.getStyleClass().add("cpu-label");

            var bar = new ProgressBar(0.0);
            bar.setMaxWidth(Double.MAX_VALUE);
            bar.getStyleClass().addAll("cpu-bar", "cpu-bar-low");
            HBox.setHgrow(bar, Priority.ALWAYS);
            coreProgressBars[i] = bar;

            var lblVal = new Label("0%");
            lblVal.getStyleClass().add("cpu-val-label");
            coreValueLabels[i] = lblVal;

            barRow.getChildren().addAll(lblCore, bar, lblVal);
            cpuBarsContainer.getChildren().add(barRow);

            // Sparklines row
            var sparkRow = new HBox(8);
            sparkRow.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

            var lblSparkCore = new Label(String.format("CPU %-2d", i));
            lblSparkCore.getStyleClass().add("cpu-label");

            var canvas = new Canvas(150, 18);
            HBox.setHgrow(canvas, Priority.ALWAYS);
            coreSparklineCanvases[i] = canvas;

            var lblSparkVal = new Label("0%");
            lblSparkVal.getStyleClass().add("cpu-val-label");
            coreSparklineValueLabels[i] = lblSparkVal;

            sparkRow.getChildren().addAll(lblSparkCore, canvas, lblSparkVal);
            cpuSparklinesContainer.getChildren().add(sparkRow);
        }
    }

    private void initCharts() {
        cpuSeries.setName("Average CPU");
        cpuHistoryChart.getData().add(cpuSeries);

        memSeries.setName("Memory Usage");
        memHistoryChart.getData().add(memSeries);
    }

    private void initTable() {
        colPid.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().pid()).asObject());
        colUser.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().user()));
        colState.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().stateString()));
        colMem.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().formattedMemory()));
        colCpu.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().formattedCpu()));
        colCommand.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().name()));

        // Colored process state cell
        colState.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    String color = switch (item) {
                        case "R" -> "#9ece6a"; // Running: Green
                        case "S" -> "#7aa2f7"; // Sleeping: Blue
                        case "D" -> "#f7768e"; // Waiting: Red
                        case "Z" -> "#bb9af7"; // Zombie: Magenta
                        case "T" -> "#e0af68"; // Stopped: Yellow
                        default -> "#565f89";
                    };
                    setStyle("-fx-text-fill: " + color + "; -fx-font-weight: bold;");
                }
            }
        });

        processTable.setItems(processList);
    }

    private void startMetricsPolling() {
        scheduler = Executors.newSingleThreadScheduledExecutor(runnable -> {
            var thread = new Thread(runnable, "jtop-metrics-worker");
            thread.setDaemon(true);
            return thread;
        });

        scheduler.scheduleWithFixedDelay(() -> {
            try {
                metrics.update(sortMode.get());
                Platform.runLater(this::renderMetrics);
            } catch (Exception e) {
                // Ignore transient query issues during poll
            }
        }, 0, 500, TimeUnit.MILLISECONDS);
    }

    private void renderMetrics() {
        metrics.read(() -> {
            updateCpuSection();
            updateMemorySection();
            updateSystemSection();
            updateProcessSection();
            lblStatus.setText(String.format("Updated • %d Cores • %d Processes",
                metrics.numCpus(), metrics.processes().size()));
            return null;
        });
    }

    private void updateCpuSection() {
        double avgCpu = metrics.averageCpuUsage();
        lblCpuAvg.setText(String.format("Avg: %.1f%%", avgCpu));

        int numCpus = metrics.numCpus();
        for (int i = 0; i < numCpus; i++) {
            double usage = metrics.coreUsage(i);
            var bar = coreProgressBars[i];
            bar.setProgress(usage / 100.0);
            coreValueLabels[i].setText(String.format("%3.0f%%", usage));

            bar.getStyleClass().removeAll("cpu-bar-low", "cpu-bar-med", "cpu-bar-high");
            if (usage < 50) {
                bar.getStyleClass().add("cpu-bar-low");
            } else if (usage < 80) {
                bar.getStyleClass().add("cpu-bar-med");
            } else {
                bar.getStyleClass().add("cpu-bar-high");
            }

            // Sparkline
            var canvas = coreSparklineCanvases[i];
            var history = metrics.coreHistory(i);
            drawSparkline(canvas, history, usage);
            coreSparklineValueLabels[i].setText(String.format("%3.0f%%", usage));
        }

        // Chart history update
        var allHistories = metrics.allCoreHistories();
        if (!allHistories.isEmpty() && !allHistories.get(0).isEmpty()) {
            int historyLen = allHistories.get(0).size();
            var points = new ArrayList<XYChart.Data<Number, Number>>(historyLen);
            for (int t = 0; t < historyLen; t++) {
                double sum = 0;
                for (var h : allHistories) {
                    if (t < h.size()) {
                        sum += h.get(t);
                    }
                }
                double avg = numCpus > 0 ? sum / numCpus : 0;
                points.add(new XYChart.Data<>(t, avg));
            }
            cpuSeries.getData().setAll(points);
        }
    }

    private void drawSparkline(Canvas canvas, List<Long> history, double usage) {
        var gc = canvas.getGraphicsContext2D();
        double w = canvas.getWidth();
        double h = canvas.getHeight();
        gc.clearRect(0, 0, w, h);

        if (history.isEmpty()) return;

        gc.setFill(Color.web("#24283b"));
        gc.fillRoundRect(0, 0, w, h, 4, 4);

        Color barColor = usage < 50 ? Color.web("#9ece6a") :
                        (usage < 80 ? Color.web("#e0af68") : Color.web("#f7768e"));
        gc.setFill(barColor);

        int size = history.size();
        double step = (w - 4) / (double) size;

        for (int i = 0; i < size; i++) {
            long val = history.get(i);
            double barH = (val / 100.0) * (h - 4);
            barH = Math.max(1, Math.min(h - 4, barH));
            double x = 2 + i * step;
            double y = h - 2 - barH;
            gc.fillRect(x, y, Math.max(1, step - 0.5), barH);
        }
    }

    private void updateMemorySection() {
        double ratio = metrics.memoryRatio();
        double percent = ratio * 100.0;
        lblMemRatio.setText(String.format("%.0f%%", percent));
        memProgressBar.setProgress(ratio);

        lblMemDetails.setText(String.format("%s / %s",
            formatKb(metrics.memUsed()), formatKb(metrics.memTotal())));
        lblSwapDetails.setText(String.format("Swap: %s / %s",
            formatKb(metrics.swapUsed()), formatKb(metrics.swapTotal())));

        var memHistory = metrics.memoryHistory();
        var points = new ArrayList<XYChart.Data<Number, Number>>(memHistory.size());
        for (int t = 0; t < memHistory.size(); t++) {
            points.add(new XYChart.Data<>(t, memHistory.get(t)));
        }
        memSeries.getData().setAll(points);
    }

    private void updateSystemSection() {
        lblUptime.setText(formatUptime((long) metrics.uptime()));
        lblLoadAvg.setText(String.format("%.2f, %.2f, %.2f",
            metrics.loadAvg1(), metrics.loadAvg5(), metrics.loadAvg15()));
        lblCores.setText(String.valueOf(metrics.numCpus()));
        lblTotalMem.setText(formatKb(metrics.memTotal()));
        lblSwapSummary.setText(String.format("%s / %s",
            formatKb(metrics.swapUsed()), formatKb(metrics.swapTotal())));
    }

    private void updateProcessSection() {
        var processes = metrics.processes();
        lblProcessCount.setText(String.format("Processes (%d)", processes.size()));
        lblCurrentSort.setText("Sorted by: " + sortMode.get());

        // Update items while preserving scroll/selection where possible
        int selectedIndex = processTable.getSelectionModel().getSelectedIndex();
        processList.setAll(processes);
        if (selectedIndex >= 0 && selectedIndex < processList.size()) {
            processTable.getSelectionModel().select(selectedIndex);
        }
    }

    @FXML
    public void handleToggleSort() {
        sortMode.updateAndGet(current -> switch (current) {
            case CPU -> SystemMetrics.SortMode.MEMORY;
            case MEMORY -> SystemMetrics.SortMode.PID;
            case PID -> SystemMetrics.SortMode.CPU;
        });
        btnSort.setText("[s] Sort: " + sortMode.get());
        lblCurrentSort.setText("Sorted by: " + sortMode.get());
    }

    @FXML
    public void handleToggleCpuView() {
        cpuViewMode.updateAndGet(current -> switch (current) {
            case BARS -> CpuViewMode.SPARKLINES;
            case SPARKLINES -> CpuViewMode.CHART;
            case CHART -> CpuViewMode.BARS;
        });

        var current = cpuViewMode.get();
        btnCpuView.setText("[c] CPU View: " + current);

        cpuBarsScrollPane.setVisible(current == CpuViewMode.BARS);
        cpuSparklinesScrollPane.setVisible(current == CpuViewMode.SPARKLINES);
        cpuChartContainer.setVisible(current == CpuViewMode.CHART);
    }

    @FXML
    public void handleQuit() {
        shutdown();
        Platform.exit();
    }

    public void handleKeyPressed(KeyEvent event) {
        String text = event.getText();
        if ("s".equalsIgnoreCase(text)) {
            handleToggleSort();
        } else if ("c".equalsIgnoreCase(text)) {
            handleToggleCpuView();
        } else if ("q".equalsIgnoreCase(text)) {
            handleQuit();
        }
    }

    public void shutdown() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow();
        }
    }

    private static String formatKb(long kb) {
        if (kb < 1024) {
            return kb + " KB";
        }
        if (kb < 1024 * 1024) {
            return String.format("%.1f MB", kb / 1024.0);
        }
        return String.format("%.1f GB", kb / (1024.0 * 1024));
    }

    private static String formatUptime(long seconds) {
        var d = Duration.ofSeconds(seconds);
        var days = d.toDays();
        long hours = d.toHoursPart();
        long minutes = d.toMinutesPart();
        if (days > 0) {
            return String.format("%dd %02d:%02d", days, hours, minutes);
        }
        return String.format("%02d:%02d:%02d", hours, minutes, d.toSecondsPart());
    }
}
