package controller;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Employee;
import model.Service;
import model.Transaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller untuk mengelola Reports (Laporan)
 */
public class ReportController {

    private ObservableList<Transaction> transactionList;
    private ObservableList<Service> serviceList;
    private ObservableList<Employee> employeeList;

    public ReportController(ObservableList<Transaction> transactionList, ObservableList<Service> serviceList, ObservableList<Employee> employeeList) {
        this.transactionList = transactionList;
        this.serviceList = serviceList;
        this.employeeList = employeeList;
    }

    /**
     * Create sales report view
     */
    public VBox createSalesReportView() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(20));

        Label titleLabel = new Label("Laporan Penjualan");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        // Summary cards
        GridPane summaryGrid = new GridPane();
        summaryGrid.setHgap(20);
        summaryGrid.setVgap(20);
        summaryGrid.setAlignment(Pos.CENTER);

        int totalTransactions = transactionList.size();
        double totalRevenue = transactionList.stream()
            .filter(t -> "Paid".equals(t.getStatus()))
            .mapToDouble(Transaction::getTotal)
            .sum();
        double averageTransaction = totalTransactions > 0 ? totalRevenue / totalTransactions : 0;

        summaryGrid.add(createSummaryCard("Total Transaksi", String.valueOf(totalTransactions), "#2196F3"), 0, 0);
        summaryGrid.add(createSummaryCard("Total Pendapatan", String.format("Rp %,.0f", totalRevenue), "#4CAF50"), 1, 0);
        summaryGrid.add(createSummaryCard("Rata-rata Transaksi", String.format("Rp %,.0f", averageTransaction), "#FF9800"), 2, 0);

        // Sales chart
        BarChart<String, Number> salesChart = createSalesChart();
        salesChart.setPrefHeight(300);

        // Popular services
        VBox popularServicesBox = createPopularServicesBox();

        container.getChildren().addAll(titleLabel, summaryGrid, new Label("Grafik Penjualan 7 Hari Terakhir"), salesChart, popularServicesBox);

        return container;
    }

    /**
     * Create revenue report view
     */
    public VBox createRevenueReportView() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(20));

        Label titleLabel = new Label("Laporan Pendapatan");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        // Revenue summary
        GridPane summaryGrid = new GridPane();
        summaryGrid.setHgap(20);
        summaryGrid.setVgap(20);
        summaryGrid.setAlignment(Pos.CENTER);

        LocalDate today = LocalDate.now();
        double todayRevenue = transactionList.stream()
            .filter(t -> "Paid".equals(t.getStatus()) && t.getTanggalWaktu().toLocalDate().equals(today))
            .mapToDouble(Transaction::getTotal)
            .sum();

        double weekRevenue = transactionList.stream()
            .filter(t -> "Paid".equals(t.getStatus()) &&
                   t.getTanggalWaktu().toLocalDate().isAfter(today.minusDays(7)))
            .mapToDouble(Transaction::getTotal)
            .sum();

        double monthRevenue = transactionList.stream()
            .filter(t -> "Paid".equals(t.getStatus()) &&
                   t.getTanggalWaktu().getMonth() == today.getMonth())
            .mapToDouble(Transaction::getTotal)
            .sum();

        summaryGrid.add(createSummaryCard("Hari Ini", String.format("Rp %,.0f", todayRevenue), "#4CAF50"), 0, 0);
        summaryGrid.add(createSummaryCard("7 Hari Terakhir", String.format("Rp %,.0f", weekRevenue), "#2196F3"), 1, 0);
        summaryGrid.add(createSummaryCard("Bulan Ini", String.format("Rp %,.0f", monthRevenue), "#9C27B0"), 2, 0);

        // Revenue pie chart
        PieChart revenueChart = createRevenueByPaymentMethodChart();
        revenueChart.setPrefHeight(350);

        container.getChildren().addAll(titleLabel, summaryGrid, new Label("Pendapatan Berdasarkan Metode Pembayaran"), revenueChart);

        return container;
    }

    /**
     * Create employee performance report view
     */
    public VBox createEmployeeReportView() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(20));

        Label titleLabel = new Label("Laporan Kinerja Karyawan");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        // Employee performance table
        GridPane employeeGrid = new GridPane();
        employeeGrid.setHgap(10);
        employeeGrid.setVgap(10);
        employeeGrid.setPadding(new Insets(20));
        employeeGrid.setStyle("-fx-background-color: #f5f5f5; -fx-border-radius: 5;");

        employeeGrid.add(new Label("Nama"), 0, 0);
        employeeGrid.add(new Label("Posisi"), 1, 0);
        employeeGrid.add(new Label("Total Transaksi"), 2, 0);
        employeeGrid.add(new Label("Total Pendapatan"), 3, 0);

        // Calculate employee performance
        Map<Employee, Integer> transactionCount = new HashMap<>();
        Map<Employee, Double> revenueCount = new HashMap<>();

        for (Transaction t : transactionList) {
            if ("Paid".equals(t.getStatus()) && t.getEmployee() != null) {
                Employee emp = t.getEmployee();
                transactionCount.put(emp, transactionCount.getOrDefault(emp, 0) + 1);
                revenueCount.put(emp, revenueCount.getOrDefault(emp, 0.0) + t.getTotal());
            }
        }

        int row = 1;
        for (Employee emp : employeeList) {
            int count = transactionCount.getOrDefault(emp, 0);
            double revenue = revenueCount.getOrDefault(emp, 0.0);

            Label namaLabel = new Label(emp.getNama());
            Label posisiLabel = new Label(emp.getPosisi());
            Label countLabel = new Label(String.valueOf(count));
            Label revenueLabel = new Label(String.format("Rp %,.0f", revenue));

            employeeGrid.add(namaLabel, 0, row);
            employeeGrid.add(posisiLabel, 1, row);
            employeeGrid.add(countLabel, 2, row);
            employeeGrid.add(revenueLabel, 3, row);

            row++;
        }

        // Top performer
        Employee topPerformer = null;
        double maxRevenue = 0;
        for (Map.Entry<Employee, Double> entry : revenueCount.entrySet()) {
            if (entry.getValue() > maxRevenue) {
                maxRevenue = entry.getValue();
                topPerformer = entry.getKey();
            }
        }

        VBox topPerformerBox = new VBox(10);
        topPerformerBox.setPadding(new Insets(20));
        topPerformerBox.setStyle("-fx-background-color: #fff3cd; -fx-border-color: #ffc107; -fx-border-width: 2; -fx-border-radius: 5;");
        topPerformerBox.setAlignment(Pos.CENTER);

        Label topLabel = new Label("⭐ Top Performer ⭐");
        topLabel.setFont(Font.font("System", FontWeight.BOLD, 18));

        if (topPerformer != null) {
            Label performerNameLabel = new Label(topPerformer.getNama());
            performerNameLabel.setFont(Font.font("System", FontWeight.BOLD, 20));

            Label performerRevenueLabel = new Label(String.format("Total Pendapatan: Rp %,.0f", maxRevenue));
            performerRevenueLabel.setFont(Font.font("System", 14));

            topPerformerBox.getChildren().addAll(topLabel, performerNameLabel, performerRevenueLabel);
        } else {
            topPerformerBox.getChildren().addAll(topLabel, new Label("Belum ada data"));
        }

        container.getChildren().addAll(titleLabel, topPerformerBox, new Label("Detail Kinerja Karyawan"), employeeGrid);

        return container;
    }

    /**
     * Create summary card
     */
    private VBox createSummaryCard(String title, String value, String color) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-border-color: " + color + "; -fx-border-width: 2; -fx-border-radius: 5; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");
        card.setAlignment(Pos.CENTER);
        card.setPrefWidth(200);
        card.setPrefHeight(120);

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", 12));
        titleLabel.setStyle("-fx-text-fill: #666666;");

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("System", FontWeight.BOLD, 20));
        valueLabel.setStyle("-fx-text-fill: " + color + ";");

        card.getChildren().addAll(titleLabel, valueLabel);
        return card;
    }

    /**
     * Create sales bar chart
     */
    private BarChart<String, Number> createSalesChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Tanggal");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Pendapatan (Rp)");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Penjualan 7 Hari Terakhir");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Pendapatan");

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM");

        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            double dailyRevenue = transactionList.stream()
                .filter(t -> "Paid".equals(t.getStatus()) &&
                       t.getTanggalWaktu().toLocalDate().equals(date))
                .mapToDouble(Transaction::getTotal)
                .sum();

            series.getData().add(new XYChart.Data<>(date.format(formatter), dailyRevenue));
        }

        barChart.getData().add(series);
        barChart.setLegendVisible(false);

        return barChart;
    }

    /**
     * Create revenue pie chart by payment method
     */
    private PieChart createRevenueByPaymentMethodChart() {
        PieChart pieChart = new PieChart();
        pieChart.setTitle("Pendapatan per Metode Pembayaran");

        Map<String, Double> paymentMethodRevenue = new HashMap<>();

        for (Transaction t : transactionList) {
            if ("Paid".equals(t.getStatus())) {
                String method = t.getMetodePembayaran();
                paymentMethodRevenue.put(method, paymentMethodRevenue.getOrDefault(method, 0.0) + t.getTotal());
            }
        }

        for (Map.Entry<String, Double> entry : paymentMethodRevenue.entrySet()) {
            PieChart.Data data = new PieChart.Data(
                entry.getKey() + " (Rp " + String.format("%,.0f", entry.getValue()) + ")",
                entry.getValue()
            );
            pieChart.getData().add(data);
        }

        return pieChart;
    }

    /**
     * Create popular services box
     */
    private VBox createPopularServicesBox() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background-color: #f5f5f5; -fx-border-radius: 5;");

        Label titleLabel = new Label("Pelayanan Terpopuler");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

        // Count service usage
        Map<Service, Integer> serviceCount = new HashMap<>();
        for (Transaction t : transactionList) {
            if (t.getServices() != null) {
                for (Service s : t.getServices()) {
                    serviceCount.put(s, serviceCount.getOrDefault(s, 0) + 1);
                }
            }
        }

        // Find top 5 services
        serviceCount.entrySet().stream()
            .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
            .limit(5)
            .forEach(entry -> {
                Label serviceLabel = new Label(String.format("• %s - %d kali",
                    entry.getKey().getNama(), entry.getValue()));
                serviceLabel.setFont(Font.font("System", 14));
                box.getChildren().add(serviceLabel);
            });

        if (serviceCount.isEmpty()) {
            Label noDataLabel = new Label("Belum ada data");
            box.getChildren().add(noDataLabel);
        }

        box.getChildren().add(0, titleLabel);
        return box;
    }
}
