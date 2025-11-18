package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Customer;
import model.Employee;
import model.Service;
import model.Transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller untuk mengelola Transaction (Transaksi)
 */
public class TransactionController {

    private ObservableList<Transaction> transactionList;
    private TableView<Transaction> transactionTable;
    private int nextId = 1;

    private ObservableList<Customer> customerList;
    private ObservableList<Service> serviceList;
    private ObservableList<Employee> employeeList;

    public TransactionController(ObservableList<Customer> customerList, ObservableList<Service> serviceList, ObservableList<Employee> employeeList) {
        this.customerList = customerList;
        this.serviceList = serviceList;
        this.employeeList = employeeList;
        transactionList = FXCollections.observableArrayList();
        loadDummyData();
    }

    private void loadDummyData() {
        if (!customerList.isEmpty() && !serviceList.isEmpty() && !employeeList.isEmpty()) {
            List<Service> services1 = new ArrayList<>();
            services1.add(serviceList.get(0));
            services1.add(serviceList.get(4));

            List<Service> services2 = new ArrayList<>();
            services2.add(serviceList.get(1));

            transactionList.add(new Transaction(nextId++, customerList.get(0), services1, employeeList.get(0), LocalDateTime.now().minusDays(1), 100000, 0, 10000, 110000, "Cash", "Paid"));
            transactionList.add(new Transaction(nextId++, customerList.get(1), services2, employeeList.get(1), LocalDateTime.now(), 75000, 5000, 7000, 77000, "Debit", "Paid"));
        }
    }

    public VBox createTransactionView() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(20));

        Label titleLabel = new Label("Transaksi Baru");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(15);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 5;");

        // Customer selection
        ComboBox<Customer> customerCombo = new ComboBox<>(customerList);
        customerCombo.setPromptText("Pilih pelanggan");
        customerCombo.setPrefWidth(400);
        customerCombo.setButtonCell(new ListCell<Customer>() {
            @Override
            protected void updateItem(Customer item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNama());
            }
        });
        customerCombo.setCellFactory(param -> new ListCell<Customer>() {
            @Override
            protected void updateItem(Customer item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNama());
            }
        });

        // Employee selection
        ComboBox<Employee> employeeCombo = new ComboBox<>(employeeList);
        employeeCombo.setPromptText("Pilih karyawan");
        employeeCombo.setPrefWidth(400);
        employeeCombo.setButtonCell(new ListCell<Employee>() {
            @Override
            protected void updateItem(Employee item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNama());
            }
        });
        employeeCombo.setCellFactory(param -> new ListCell<Employee>() {
            @Override
            protected void updateItem(Employee item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNama());
            }
        });

        // Service selection with ListView
        Label serviceLabel = new Label("Pilih Pelayanan:");
        ListView<Service> serviceListView = new ListView<>(serviceList);
        serviceListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        serviceListView.setPrefHeight(150);
        serviceListView.setCellFactory(param -> new ListCell<Service>() {
            @Override
            protected void updateItem(Service item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%s - Rp %,.0f", item.getNama(), item.getHarga()));
                }
            }
        });

        // Payment details
        TextField diskonField = new TextField("0");
        diskonField.setPromptText("Diskon (Rp)");

        TextField pajakField = new TextField("10");
        pajakField.setPromptText("Pajak (%)");

        ComboBox<String> metodePembayaranCombo = new ComboBox<>();
        metodePembayaranCombo.getItems().addAll("Cash", "Debit", "Credit", "E-Wallet");
        metodePembayaranCombo.setValue("Cash");
        metodePembayaranCombo.setPrefWidth(400);

        // Summary labels
        Label subtotalLabel = new Label("Subtotal: Rp 0");
        subtotalLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        Label totalLabel = new Label("Total: Rp 0");
        totalLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

        // Calculate total when selection changes
        Runnable calculateTotal = () -> {
            double subtotal = 0;
            for (Service s : serviceListView.getSelectionModel().getSelectedItems()) {
                subtotal += s.getHarga();
            }

            double diskon = 0;
            try {
                diskon = Double.parseDouble(diskonField.getText());
            } catch (NumberFormatException e) {
                diskon = 0;
            }

            double pajakPercent = 10;
            try {
                pajakPercent = Double.parseDouble(pajakField.getText());
            } catch (NumberFormatException e) {
                pajakPercent = 10;
            }

            double afterDiskon = subtotal - diskon;
            double pajak = afterDiskon * (pajakPercent / 100);
            double total = afterDiskon + pajak;

            subtotalLabel.setText(String.format("Subtotal: Rp %,.0f", subtotal));
            totalLabel.setText(String.format("Total: Rp %,.0f", total));
        };

        serviceListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> calculateTotal.run());
        diskonField.textProperty().addListener((obs, oldVal, newVal) -> calculateTotal.run());
        pajakField.textProperty().addListener((obs, oldVal, newVal) -> calculateTotal.run());

        form.add(new Label("Pelanggan:"), 0, 0);
        form.add(customerCombo, 1, 0);
        form.add(new Label("Karyawan:"), 0, 1);
        form.add(employeeCombo, 1, 1);
        form.add(serviceLabel, 0, 2);
        form.add(serviceListView, 1, 2);
        form.add(new Label("Diskon (Rp):"), 0, 3);
        form.add(diskonField, 1, 3);
        form.add(new Label("Pajak (%):"), 0, 4);
        form.add(pajakField, 1, 4);
        form.add(new Label("Metode Pembayaran:"), 0, 5);
        form.add(metodePembayaranCombo, 1, 5);

        VBox summaryBox = new VBox(10);
        summaryBox.setPadding(new Insets(20));
        summaryBox.setStyle("-fx-background-color: #e3f2fd; -fx-border-color: #2196F3; -fx-border-width: 2; -fx-border-radius: 5;");
        summaryBox.getChildren().addAll(subtotalLabel, totalLabel);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        Button processButton = new Button("Proses Transaksi");
        Button clearButton = new Button("Clear");

        processButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;");
        clearButton.setStyle("-fx-background-color: #757575; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;");

        processButton.setOnAction(e -> {
            if (customerCombo.getValue() == null) {
                showErrorDialog("Pelanggan harus dipilih!");
                return;
            }
            if (employeeCombo.getValue() == null) {
                showErrorDialog("Karyawan harus dipilih!");
                return;
            }
            if (serviceListView.getSelectionModel().getSelectedItems().isEmpty()) {
                showErrorDialog("Minimal pilih satu pelayanan!");
                return;
            }

            List<Service> selectedServices = new ArrayList<>(serviceListView.getSelectionModel().getSelectedItems());
            double subtotal = selectedServices.stream().mapToDouble(Service::getHarga).sum();
            double diskon = Double.parseDouble(diskonField.getText());
            double pajakPercent = Double.parseDouble(pajakField.getText());
            double afterDiskon = subtotal - diskon;
            double pajak = afterDiskon * (pajakPercent / 100);
            double total = afterDiskon + pajak;

            Transaction newTransaction = new Transaction(
                nextId++,
                customerCombo.getValue(),
                selectedServices,
                employeeCombo.getValue(),
                LocalDateTime.now(),
                subtotal,
                diskon,
                pajak,
                total,
                metodePembayaranCombo.getValue(),
                "Paid"
            );

            transactionList.add(newTransaction);
            showSuccessDialog("Transaksi berhasil diproses!\n\nTotal: Rp " + String.format("%,.0f", total));

            // Clear form
            customerCombo.setValue(null);
            employeeCombo.setValue(null);
            serviceListView.getSelectionModel().clearSelection();
            diskonField.setText("0");
            pajakField.setText("10");
        });

        clearButton.setOnAction(e -> {
            customerCombo.setValue(null);
            employeeCombo.setValue(null);
            serviceListView.getSelectionModel().clearSelection();
            diskonField.setText("0");
            pajakField.setText("10");
        });

        buttonBox.getChildren().addAll(processButton, clearButton);

        container.getChildren().addAll(titleLabel, form, summaryBox, buttonBox);
        return container;
    }

    public VBox createTransactionHistoryView() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(20));

        Label titleLabel = new Label("Riwayat Transaksi");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        HBox filterBox = new HBox(10);
        filterBox.setAlignment(Pos.CENTER_LEFT);

        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setPromptText("Dari tanggal");

        DatePicker endDatePicker = new DatePicker();
        endDatePicker.setPromptText("Sampai tanggal");

        Button filterButton = new Button("Filter");
        Button refreshButton = new Button("Refresh");

        filterButton.setOnAction(e -> {
            // Simple filter implementation
            showSuccessDialog("Filter diterapkan!");
        });

        refreshButton.setOnAction(e -> {
            startDatePicker.setValue(null);
            endDatePicker.setValue(null);
            transactionTable.setItems(transactionList);
        });

        filterBox.getChildren().addAll(new Label("Periode:"), startDatePicker, new Label("-"), endDatePicker, filterButton, refreshButton);

        transactionTable = new TableView<>();
        transactionTable.setItems(transactionList);

        TableColumn<Transaction, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(50);

        TableColumn<Transaction, String> customerCol = new TableColumn<>("Pelanggan");
        customerCol.setCellValueFactory(cellData -> {
            Customer customer = cellData.getValue().getCustomer();
            return new javafx.beans.property.SimpleStringProperty(customer != null ? customer.getNama() : "-");
        });
        customerCol.setPrefWidth(150);

        TableColumn<Transaction, LocalDateTime> tanggalCol = new TableColumn<>("Tanggal & Waktu");
        tanggalCol.setCellValueFactory(new PropertyValueFactory<>("tanggalWaktu"));
        tanggalCol.setPrefWidth(150);

        TableColumn<Transaction, Double> subtotalCol = new TableColumn<>("Subtotal");
        subtotalCol.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        subtotalCol.setPrefWidth(100);
        subtotalCol.setCellFactory(column -> new TableCell<Transaction, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : String.format("Rp %,.0f", item));
            }
        });

        TableColumn<Transaction, Double> diskonCol = new TableColumn<>("Diskon");
        diskonCol.setCellValueFactory(new PropertyValueFactory<>("diskon"));
        diskonCol.setPrefWidth(100);
        diskonCol.setCellFactory(column -> new TableCell<Transaction, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : String.format("Rp %,.0f", item));
            }
        });

        TableColumn<Transaction, Double> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
        totalCol.setPrefWidth(120);
        totalCol.setCellFactory(column -> new TableCell<Transaction, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : String.format("Rp %,.0f", item));
            }
        });

        TableColumn<Transaction, String> metodePembayaranCol = new TableColumn<>("Metode Bayar");
        metodePembayaranCol.setCellValueFactory(new PropertyValueFactory<>("metodePembayaran"));
        metodePembayaranCol.setPrefWidth(100);

        TableColumn<Transaction, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setPrefWidth(80);

        transactionTable.getColumns().addAll(idCol, customerCol, tanggalCol, subtotalCol, diskonCol, totalCol, metodePembayaranCol, statusCol);

        double totalRevenue = transactionList.stream()
            .filter(t -> "Paid".equals(t.getStatus()))
            .mapToDouble(Transaction::getTotal)
            .sum();

        Label statsLabel = new Label(String.format("Total Transaksi: %d | Total Pendapatan: Rp %,.0f",
            transactionList.size(), totalRevenue));
        statsLabel.setFont(Font.font("System", FontWeight.SEMI_BOLD, 14));

        container.getChildren().addAll(titleLabel, filterBox, transactionTable, statsLabel);
        VBox.setVgrow(transactionTable, Priority.ALWAYS);

        return container;
    }

    private void showSuccessDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sukses");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Validasi Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public ObservableList<Transaction> getTransactionList() {
        return transactionList;
    }
}
