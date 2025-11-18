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
import model.Appointment;
import model.Customer;
import model.Service;
import model.Employee;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Controller untuk mengelola Appointment (Jadwal)
 */
public class AppointmentController {

    private ObservableList<Appointment> appointmentList;
    private TableView<Appointment> appointmentTable;
    private int nextId = 1;

    private ObservableList<Customer> customerList;
    private ObservableList<Service> serviceList;
    private ObservableList<Employee> employeeList;

    public AppointmentController(ObservableList<Customer> customerList, ObservableList<Service> serviceList, ObservableList<Employee> employeeList) {
        this.customerList = customerList;
        this.serviceList = serviceList;
        this.employeeList = employeeList;
        appointmentList = FXCollections.observableArrayList();
        loadDummyData();
    }

    private void loadDummyData() {
        if (!customerList.isEmpty() && !serviceList.isEmpty() && !employeeList.isEmpty()) {
            appointmentList.add(new Appointment(nextId++, customerList.get(0), serviceList.get(0), employeeList.get(0), LocalDate.now(), LocalTime.of(10, 0), "Confirmed", ""));
            appointmentList.add(new Appointment(nextId++, customerList.get(1), serviceList.get(1), employeeList.get(1), LocalDate.now(), LocalTime.of(11, 30), "Pending", ""));
            appointmentList.add(new Appointment(nextId++, customerList.get(2), serviceList.get(2), employeeList.get(0), LocalDate.now().plusDays(1), LocalTime.of(14, 0), "Confirmed", ""));
        }
    }

    public VBox createAppointmentListView() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(20));

        Label titleLabel = new Label("Daftar Appointment");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        HBox filterBox = new HBox(10);
        filterBox.setAlignment(Pos.CENTER_LEFT);

        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        datePicker.setPromptText("Filter tanggal");

        ComboBox<String> statusCombo = new ComboBox<>();
        statusCombo.getItems().addAll("Semua", "Pending", "Confirmed", "Completed", "Cancelled");
        statusCombo.setValue("Semua");

        Button filterButton = new Button("Filter");
        Button refreshButton = new Button("Refresh");

        filterButton.setOnAction(e -> {
            ObservableList<Appointment> filtered = FXCollections.observableArrayList();
            LocalDate selectedDate = datePicker.getValue();
            String selectedStatus = statusCombo.getValue();

            for (Appointment apt : appointmentList) {
                boolean matchDate = selectedDate == null || apt.getTanggal().equals(selectedDate);
                boolean matchStatus = "Semua".equals(selectedStatus) || apt.getStatus().equals(selectedStatus);

                if (matchDate && matchStatus) {
                    filtered.add(apt);
                }
            }
            appointmentTable.setItems(filtered);
        });

        refreshButton.setOnAction(e -> {
            datePicker.setValue(LocalDate.now());
            statusCombo.setValue("Semua");
            appointmentTable.setItems(appointmentList);
        });

        filterBox.getChildren().addAll(new Label("Tanggal:"), datePicker, new Label("Status:"), statusCombo, filterButton, refreshButton);

        appointmentTable = new TableView<>();
        appointmentTable.setItems(appointmentList);

        TableColumn<Appointment, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(50);

        TableColumn<Appointment, String> customerCol = new TableColumn<>("Pelanggan");
        customerCol.setCellValueFactory(cellData -> {
            Customer customer = cellData.getValue().getCustomer();
            return new javafx.beans.property.SimpleStringProperty(customer != null ? customer.getNama() : "-");
        });
        customerCol.setPrefWidth(150);

        TableColumn<Appointment, String> serviceCol = new TableColumn<>("Pelayanan");
        serviceCol.setCellValueFactory(cellData -> {
            Service service = cellData.getValue().getService();
            return new javafx.beans.property.SimpleStringProperty(service != null ? service.getNama() : "-");
        });
        serviceCol.setPrefWidth(150);

        TableColumn<Appointment, String> employeeCol = new TableColumn<>("Karyawan");
        employeeCol.setCellValueFactory(cellData -> {
            Employee employee = cellData.getValue().getEmployee();
            return new javafx.beans.property.SimpleStringProperty(employee != null ? employee.getNama() : "-");
        });
        employeeCol.setPrefWidth(120);

        TableColumn<Appointment, LocalDate> tanggalCol = new TableColumn<>("Tanggal");
        tanggalCol.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        tanggalCol.setPrefWidth(100);

        TableColumn<Appointment, LocalTime> waktuCol = new TableColumn<>("Waktu");
        waktuCol.setCellValueFactory(new PropertyValueFactory<>("waktu"));
        waktuCol.setPrefWidth(80);

        TableColumn<Appointment, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setPrefWidth(100);

        TableColumn<Appointment, Void> actionCol = new TableColumn<>("Aksi");
        actionCol.setPrefWidth(200);
        actionCol.setCellFactory(column -> new TableCell<Appointment, Void>() {
            private final Button confirmBtn = new Button("Confirm");
            private final Button completeBtn = new Button("Complete");
            private final Button cancelBtn = new Button("Cancel");
            private final HBox actionBox = new HBox(5, confirmBtn, completeBtn, cancelBtn);

            {
                confirmBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 10px;");
                completeBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 10px;");
                cancelBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 10px;");

                confirmBtn.setOnAction(e -> {
                    Appointment apt = getTableView().getItems().get(getIndex());
                    apt.setStatus("Confirmed");
                    getTableView().refresh();
                    showSuccessDialog("Appointment dikonfirmasi!");
                });

                completeBtn.setOnAction(e -> {
                    Appointment apt = getTableView().getItems().get(getIndex());
                    apt.setStatus("Completed");
                    getTableView().refresh();
                    showSuccessDialog("Appointment selesai!");
                });

                cancelBtn.setOnAction(e -> {
                    Appointment apt = getTableView().getItems().get(getIndex());
                    handleCancel(apt);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : actionBox);
            }
        });

        appointmentTable.getColumns().addAll(idCol, customerCol, serviceCol, employeeCol, tanggalCol, waktuCol, statusCol, actionCol);

        Label statsLabel = new Label(String.format("Total Appointment: %d | Hari Ini: %d",
            appointmentList.size(),
            appointmentList.stream().filter(a -> a.getTanggal().equals(LocalDate.now())).count()));
        statsLabel.setFont(Font.font("System", FontWeight.SEMI_BOLD, 14));

        container.getChildren().addAll(titleLabel, filterBox, appointmentTable, statsLabel);
        VBox.setVgrow(appointmentTable, Priority.ALWAYS);

        return container;
    }

    public VBox createAppointmentAddForm(Runnable onSaveCallback) {
        VBox container = new VBox(15);
        container.setPadding(new Insets(20));
        container.setMaxWidth(600);

        Label titleLabel = new Label("Buat Appointment Baru");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(15);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 5;");

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

        ComboBox<Service> serviceCombo = new ComboBox<>(serviceList);
        serviceCombo.setPromptText("Pilih pelayanan");
        serviceCombo.setPrefWidth(400);
        serviceCombo.setButtonCell(new ListCell<Service>() {
            @Override
            protected void updateItem(Service item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNama());
            }
        });
        serviceCombo.setCellFactory(param -> new ListCell<Service>() {
            @Override
            protected void updateItem(Service item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNama());
            }
        });

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

        DatePicker tanggalPicker = new DatePicker();
        tanggalPicker.setValue(LocalDate.now());
        tanggalPicker.setPrefWidth(400);

        ComboBox<String> waktuCombo = new ComboBox<>();
        for (int hour = 9; hour <= 20; hour++) {
            waktuCombo.getItems().add(String.format("%02d:00", hour));
            waktuCombo.getItems().add(String.format("%02d:30", hour));
        }
        waktuCombo.setPromptText("Pilih waktu");
        waktuCombo.setPrefWidth(400);

        TextArea catatanArea = new TextArea();
        catatanArea.setPromptText("Catatan (opsional)");
        catatanArea.setPrefRowCount(3);
        catatanArea.setWrapText(true);

        form.add(new Label("Pelanggan:"), 0, 0);
        form.add(customerCombo, 1, 0);
        form.add(new Label("Pelayanan:"), 0, 1);
        form.add(serviceCombo, 1, 1);
        form.add(new Label("Karyawan:"), 0, 2);
        form.add(employeeCombo, 1, 2);
        form.add(new Label("Tanggal:"), 0, 3);
        form.add(tanggalPicker, 1, 3);
        form.add(new Label("Waktu:"), 0, 4);
        form.add(waktuCombo, 1, 4);
        form.add(new Label("Catatan:"), 0, 5);
        form.add(catatanArea, 1, 5);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        Button saveButton = new Button("Simpan");
        Button cancelButton = new Button("Batal");

        saveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;");
        cancelButton.setStyle("-fx-background-color: #757575; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;");

        saveButton.setOnAction(e -> {
            if (validateForm(customerCombo, serviceCombo, employeeCombo, waktuCombo)) {
                String[] timeParts = waktuCombo.getValue().split(":");
                LocalTime time = LocalTime.of(Integer.parseInt(timeParts[0]), Integer.parseInt(timeParts[1]));

                Appointment newAppointment = new Appointment(
                    nextId++,
                    customerCombo.getValue(),
                    serviceCombo.getValue(),
                    employeeCombo.getValue(),
                    tanggalPicker.getValue(),
                    time,
                    "Pending",
                    catatanArea.getText()
                );
                appointmentList.add(newAppointment);
                showSuccessDialog("Appointment berhasil dibuat!");

                customerCombo.setValue(null);
                serviceCombo.setValue(null);
                employeeCombo.setValue(null);
                tanggalPicker.setValue(LocalDate.now());
                waktuCombo.setValue(null);
                catatanArea.clear();

                if (onSaveCallback != null) {
                    onSaveCallback.run();
                }
            }
        });

        cancelButton.setOnAction(e -> {
            if (onSaveCallback != null) {
                onSaveCallback.run();
            }
        });

        buttonBox.getChildren().addAll(saveButton, cancelButton);
        container.getChildren().addAll(titleLabel, form, buttonBox);
        return container;
    }

    private boolean validateForm(ComboBox<Customer> customerCombo, ComboBox<Service> serviceCombo, ComboBox<Employee> employeeCombo, ComboBox<String> waktuCombo) {
        if (customerCombo.getValue() == null) {
            showErrorDialog("Pelanggan harus dipilih!");
            return false;
        }
        if (serviceCombo.getValue() == null) {
            showErrorDialog("Pelayanan harus dipilih!");
            return false;
        }
        if (employeeCombo.getValue() == null) {
            showErrorDialog("Karyawan harus dipilih!");
            return false;
        }
        if (waktuCombo.getValue() == null) {
            showErrorDialog("Waktu harus dipilih!");
            return false;
        }
        return true;
    }

    private void handleCancel(Appointment appointment) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Cancel");
        alert.setHeaderText("Cancel Appointment");
        alert.setContentText("Apakah Anda yakin ingin membatalkan appointment ini?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                appointment.setStatus("Cancelled");
                appointmentTable.refresh();
                showSuccessDialog("Appointment dibatalkan!");
            }
        });
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

    public ObservableList<Appointment> getAppointmentList() {
        return appointmentList;
    }
}
