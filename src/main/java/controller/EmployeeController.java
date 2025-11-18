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
import model.Employee;

import java.time.LocalDate;

/**
 * Controller untuk mengelola Employee (Karyawan)
 */
public class EmployeeController {

    private ObservableList<Employee> employeeList;
    private TableView<Employee> employeeTable;
    private int nextId = 1;

    public EmployeeController() {
        employeeList = FXCollections.observableArrayList();
        loadDummyData();
    }

    private void loadDummyData() {
        employeeList.add(new Employee(nextId++, "Siti Nurhaliza", "Hair Stylist", "081234567890", "siti@salon.com", LocalDate.of(2023, 1, 15), 5000000, "Aktif"));
        employeeList.add(new Employee(nextId++, "Budi Santoso", "Barber", "081234567891", "budi@salon.com", LocalDate.of(2023, 3, 20), 4500000, "Aktif"));
        employeeList.add(new Employee(nextId++, "Dewi Lestari", "Beautician", "081234567892", "dewi@salon.com", LocalDate.of(2023, 5, 10), 4800000, "Aktif"));
        employeeList.add(new Employee(nextId++, "Agus Pratama", "Manager", "081234567893", "agus@salon.com", LocalDate.of(2022, 6, 1), 7000000, "Aktif"));
        employeeList.add(new Employee(nextId++, "Rina Susanti", "Receptionist", "081234567894", "rina@salon.com", LocalDate.of(2023, 8, 5), 4000000, "Cuti"));
    }

    public VBox createEmployeeListView() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(20));

        Label titleLabel = new Label("Daftar Karyawan");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        TextField searchField = new TextField();
        searchField.setPromptText("Cari karyawan...");
        searchField.setPrefWidth(300);
        Button searchButton = new Button("Cari");
        Button refreshButton = new Button("Refresh");

        searchButton.setOnAction(e -> {
            String keyword = searchField.getText().toLowerCase();
            if (keyword.isEmpty()) {
                employeeTable.setItems(employeeList);
            } else {
                ObservableList<Employee> filtered = FXCollections.observableArrayList();
                for (Employee emp : employeeList) {
                    if (emp.getNama().toLowerCase().contains(keyword) ||
                        emp.getPosisi().toLowerCase().contains(keyword)) {
                        filtered.add(emp);
                    }
                }
                employeeTable.setItems(filtered);
            }
        });

        refreshButton.setOnAction(e -> {
            searchField.clear();
            employeeTable.setItems(employeeList);
        });

        searchBox.getChildren().addAll(new Label("Pencarian:"), searchField, searchButton, refreshButton);

        employeeTable = new TableView<>();
        employeeTable.setItems(employeeList);

        TableColumn<Employee, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(50);

        TableColumn<Employee, String> namaCol = new TableColumn<>("Nama");
        namaCol.setCellValueFactory(new PropertyValueFactory<>("nama"));
        namaCol.setPrefWidth(150);

        TableColumn<Employee, String> posisiCol = new TableColumn<>("Posisi");
        posisiCol.setCellValueFactory(new PropertyValueFactory<>("posisi"));
        posisiCol.setPrefWidth(120);

        TableColumn<Employee, String> teleponCol = new TableColumn<>("No. Telepon");
        teleponCol.setCellValueFactory(new PropertyValueFactory<>("nomorTelepon"));
        teleponCol.setPrefWidth(120);

        TableColumn<Employee, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setPrefWidth(150);

        TableColumn<Employee, LocalDate> tanggalCol = new TableColumn<>("Tanggal Masuk");
        tanggalCol.setCellValueFactory(new PropertyValueFactory<>("tanggalMasuk"));
        tanggalCol.setPrefWidth(120);

        TableColumn<Employee, Double> gajiCol = new TableColumn<>("Gaji");
        gajiCol.setCellValueFactory(new PropertyValueFactory<>("gaji"));
        gajiCol.setPrefWidth(120);
        gajiCol.setCellFactory(column -> new TableCell<Employee, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("Rp %,.0f", item));
                }
            }
        });

        TableColumn<Employee, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setPrefWidth(80);

        TableColumn<Employee, Void> actionCol = new TableColumn<>("Aksi");
        actionCol.setPrefWidth(150);
        actionCol.setCellFactory(column -> new TableCell<Employee, Void>() {
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Hapus");
            private final HBox actionBox = new HBox(5, editBtn, deleteBtn);

            {
                editBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                deleteBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

                editBtn.setOnAction(e -> handleEdit(getTableView().getItems().get(getIndex())));
                deleteBtn.setOnAction(e -> handleDelete(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : actionBox);
            }
        });

        employeeTable.getColumns().addAll(idCol, namaCol, posisiCol, teleponCol, emailCol, tanggalCol, gajiCol, statusCol, actionCol);

        Label statsLabel = new Label(String.format("Total Karyawan: %d | Aktif: %d",
            employeeList.size(),
            employeeList.stream().filter(e -> "Aktif".equals(e.getStatus())).count()));
        statsLabel.setFont(Font.font("System", FontWeight.SEMI_BOLD, 14));

        container.getChildren().addAll(titleLabel, searchBox, employeeTable, statsLabel);
        VBox.setVgrow(employeeTable, Priority.ALWAYS);

        return container;
    }

    public VBox createEmployeeAddForm(Runnable onSaveCallback) {
        VBox container = new VBox(15);
        container.setPadding(new Insets(20));
        container.setMaxWidth(600);

        Label titleLabel = new Label("Tambah Karyawan Baru");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(15);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 5;");

        TextField namaField = new TextField();
        namaField.setPromptText("Nama lengkap");

        ComboBox<String> posisiCombo = new ComboBox<>();
        posisiCombo.getItems().addAll("Hair Stylist", "Barber", "Beautician", "Manager", "Receptionist", "Cleaning Service");
        posisiCombo.setPromptText("Pilih posisi");
        posisiCombo.setPrefWidth(400);

        TextField teleponField = new TextField();
        teleponField.setPromptText("08xxxxxxxxxx");

        TextField emailField = new TextField();
        emailField.setPromptText("email@example.com");

        DatePicker tanggalPicker = new DatePicker();
        tanggalPicker.setValue(LocalDate.now());
        tanggalPicker.setPrefWidth(400);

        TextField gajiField = new TextField();
        gajiField.setPromptText("Masukkan gaji");

        ComboBox<String> statusCombo = new ComboBox<>();
        statusCombo.getItems().addAll("Aktif", "Cuti", "Resign");
        statusCombo.setValue("Aktif");
        statusCombo.setPrefWidth(400);

        form.add(new Label("Nama:"), 0, 0);
        form.add(namaField, 1, 0);
        form.add(new Label("Posisi:"), 0, 1);
        form.add(posisiCombo, 1, 1);
        form.add(new Label("No. Telepon:"), 0, 2);
        form.add(teleponField, 1, 2);
        form.add(new Label("Email:"), 0, 3);
        form.add(emailField, 1, 3);
        form.add(new Label("Tanggal Masuk:"), 0, 4);
        form.add(tanggalPicker, 1, 4);
        form.add(new Label("Gaji:"), 0, 5);
        form.add(gajiField, 1, 5);
        form.add(new Label("Status:"), 0, 6);
        form.add(statusCombo, 1, 6);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        Button saveButton = new Button("Simpan");
        Button cancelButton = new Button("Batal");

        saveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;");
        cancelButton.setStyle("-fx-background-color: #757575; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;");

        saveButton.setOnAction(e -> {
            if (validateForm(namaField, posisiCombo, teleponField, emailField, gajiField)) {
                Employee newEmployee = new Employee(
                    nextId++,
                    namaField.getText(),
                    posisiCombo.getValue(),
                    teleponField.getText(),
                    emailField.getText(),
                    tanggalPicker.getValue(),
                    Double.parseDouble(gajiField.getText()),
                    statusCombo.getValue()
                );
                employeeList.add(newEmployee);
                showSuccessDialog("Karyawan berhasil ditambahkan!");

                namaField.clear();
                posisiCombo.setValue(null);
                teleponField.clear();
                emailField.clear();
                tanggalPicker.setValue(LocalDate.now());
                gajiField.clear();
                statusCombo.setValue("Aktif");

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

    private boolean validateForm(TextField namaField, ComboBox<String> posisiCombo, TextField teleponField, TextField emailField, TextField gajiField) {
        if (namaField.getText().trim().isEmpty()) {
            showErrorDialog("Nama tidak boleh kosong!");
            return false;
        }
        if (posisiCombo.getValue() == null) {
            showErrorDialog("Posisi harus dipilih!");
            return false;
        }
        if (teleponField.getText().trim().isEmpty()) {
            showErrorDialog("No. Telepon tidak boleh kosong!");
            return false;
        }
        if (emailField.getText().trim().isEmpty()) {
            showErrorDialog("Email tidak boleh kosong!");
            return false;
        }
        try {
            double gaji = Double.parseDouble(gajiField.getText());
            if (gaji <= 0) {
                showErrorDialog("Gaji harus lebih dari 0!");
                return false;
            }
        } catch (NumberFormatException e) {
            showErrorDialog("Gaji harus berupa angka!");
            return false;
        }
        return true;
    }

    private void handleEdit(Employee employee) {
        Dialog<Employee> dialog = new Dialog<>();
        dialog.setTitle("Edit Karyawan");
        dialog.setHeaderText("Edit data karyawan: " + employee.getNama());

        ButtonType saveButtonType = new ButtonType("Simpan", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField namaField = new TextField(employee.getNama());
        ComboBox<String> posisiCombo = new ComboBox<>();
        posisiCombo.getItems().addAll("Hair Stylist", "Barber", "Beautician", "Manager", "Receptionist", "Cleaning Service");
        posisiCombo.setValue(employee.getPosisi());
        TextField teleponField = new TextField(employee.getNomorTelepon());
        TextField emailField = new TextField(employee.getEmail());
        TextField gajiField = new TextField(String.valueOf(employee.getGaji()));
        ComboBox<String> statusCombo = new ComboBox<>();
        statusCombo.getItems().addAll("Aktif", "Cuti", "Resign");
        statusCombo.setValue(employee.getStatus());

        grid.add(new Label("Nama:"), 0, 0);
        grid.add(namaField, 1, 0);
        grid.add(new Label("Posisi:"), 0, 1);
        grid.add(posisiCombo, 1, 1);
        grid.add(new Label("Telepon:"), 0, 2);
        grid.add(teleponField, 1, 2);
        grid.add(new Label("Email:"), 0, 3);
        grid.add(emailField, 1, 3);
        grid.add(new Label("Gaji:"), 0, 4);
        grid.add(gajiField, 1, 4);
        grid.add(new Label("Status:"), 0, 5);
        grid.add(statusCombo, 1, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                employee.setNama(namaField.getText());
                employee.setPosisi(posisiCombo.getValue());
                employee.setNomorTelepon(teleponField.getText());
                employee.setEmail(emailField.getText());
                employee.setGaji(Double.parseDouble(gajiField.getText()));
                employee.setStatus(statusCombo.getValue());
                return employee;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(result -> {
            employeeTable.refresh();
            showSuccessDialog("Karyawan berhasil diupdate!");
        });
    }

    private void handleDelete(Employee employee) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Hapus");
        alert.setHeaderText("Hapus Karyawan");
        alert.setContentText("Apakah Anda yakin ingin menghapus karyawan: " + employee.getNama() + "?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                employeeList.remove(employee);
                showSuccessDialog("Karyawan berhasil dihapus!");
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

    public ObservableList<Employee> getEmployeeList() {
        return employeeList;
    }
}
