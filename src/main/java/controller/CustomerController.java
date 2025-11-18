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

import java.time.LocalDate;

/**
 * Controller untuk mengelola Customer (Pelanggan)
 */
public class CustomerController {

    private ObservableList<Customer> customerList;
    private TableView<Customer> customerTable;
    private int nextId = 1;

    public CustomerController() {
        customerList = FXCollections.observableArrayList();
        loadDummyData();
    }

    private void loadDummyData() {
        customerList.add(new Customer(nextId++, "Ahmad Rizki", "Laki-laki", "081234567890", "ahmad@email.com", "Jl. Merdeka No. 123", LocalDate.of(2024, 1, 10), true, 150));
        customerList.add(new Customer(nextId++, "Siti Fatimah", "Perempuan", "081234567891", "siti@email.com", "Jl. Sudirman No. 45", LocalDate.of(2024, 2, 15), true, 200));
        customerList.add(new Customer(nextId++, "Budi Hartono", "Laki-laki", "081234567892", "budi@email.com", "Jl. Gatot Subroto No. 78", LocalDate.of(2024, 3, 20), false, 0));
        customerList.add(new Customer(nextId++, "Dewi Anjani", "Perempuan", "081234567893", "dewi@email.com", "Jl. Ahmad Yani No. 12", LocalDate.of(2024, 4, 5), true, 300));
        customerList.add(new Customer(nextId++, "Rudi Setiawan", "Laki-laki", "081234567894", "rudi@email.com", "Jl. Diponegoro No. 56", LocalDate.of(2024, 5, 18), false, 0));
    }

    public VBox createCustomerListView() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(20));

        Label titleLabel = new Label("Daftar Pelanggan");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        TextField searchField = new TextField();
        searchField.setPromptText("Cari pelanggan...");
        searchField.setPrefWidth(300);
        Button searchButton = new Button("Cari");
        Button refreshButton = new Button("Refresh");

        searchButton.setOnAction(e -> {
            String keyword = searchField.getText().toLowerCase();
            if (keyword.isEmpty()) {
                customerTable.setItems(customerList);
            } else {
                ObservableList<Customer> filtered = FXCollections.observableArrayList();
                for (Customer c : customerList) {
                    if (c.getNama().toLowerCase().contains(keyword) ||
                        c.getNomorTelepon().contains(keyword) ||
                        c.getEmail().toLowerCase().contains(keyword)) {
                        filtered.add(c);
                    }
                }
                customerTable.setItems(filtered);
            }
        });

        refreshButton.setOnAction(e -> {
            searchField.clear();
            customerTable.setItems(customerList);
        });

        searchBox.getChildren().addAll(new Label("Pencarian:"), searchField, searchButton, refreshButton);

        customerTable = new TableView<>();
        customerTable.setItems(customerList);

        TableColumn<Customer, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(50);

        TableColumn<Customer, String> namaCol = new TableColumn<>("Nama");
        namaCol.setCellValueFactory(new PropertyValueFactory<>("nama"));
        namaCol.setPrefWidth(150);

        TableColumn<Customer, String> genderCol = new TableColumn<>("Jenis Kelamin");
        genderCol.setCellValueFactory(new PropertyValueFactory<>("jenisKelamin"));
        genderCol.setPrefWidth(100);

        TableColumn<Customer, String> teleponCol = new TableColumn<>("No. Telepon");
        teleponCol.setCellValueFactory(new PropertyValueFactory<>("nomorTelepon"));
        teleponCol.setPrefWidth(120);

        TableColumn<Customer, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setPrefWidth(150);

        TableColumn<Customer, LocalDate> tanggalCol = new TableColumn<>("Tanggal Daftar");
        tanggalCol.setCellValueFactory(new PropertyValueFactory<>("tanggalDaftar"));
        tanggalCol.setPrefWidth(120);

        TableColumn<Customer, Boolean> memberCol = new TableColumn<>("Member");
        memberCol.setCellValueFactory(new PropertyValueFactory<>("member"));
        memberCol.setPrefWidth(80);
        memberCol.setCellFactory(column -> new TableCell<Customer, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Ya" : "Tidak");
                }
            }
        });

        TableColumn<Customer, Integer> poinCol = new TableColumn<>("Poin");
        poinCol.setCellValueFactory(new PropertyValueFactory<>("poin"));
        poinCol.setPrefWidth(80);

        TableColumn<Customer, Void> actionCol = new TableColumn<>("Aksi");
        actionCol.setPrefWidth(150);
        actionCol.setCellFactory(column -> new TableCell<Customer, Void>() {
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

        customerTable.getColumns().addAll(idCol, namaCol, genderCol, teleponCol, emailCol, tanggalCol, memberCol, poinCol, actionCol);

        Label statsLabel = new Label(String.format("Total Pelanggan: %d | Member: %d",
            customerList.size(),
            customerList.stream().filter(Customer::isMember).count()));
        statsLabel.setFont(Font.font("System", FontWeight.SEMI_BOLD, 14));

        container.getChildren().addAll(titleLabel, searchBox, customerTable, statsLabel);
        VBox.setVgrow(customerTable, Priority.ALWAYS);

        return container;
    }

    public VBox createCustomerAddForm(Runnable onSaveCallback) {
        VBox container = new VBox(15);
        container.setPadding(new Insets(20));
        container.setMaxWidth(600);

        Label titleLabel = new Label("Tambah Pelanggan Baru");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(15);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 5;");

        TextField namaField = new TextField();
        namaField.setPromptText("Nama lengkap");

        ComboBox<String> genderCombo = new ComboBox<>();
        genderCombo.getItems().addAll("Laki-laki", "Perempuan");
        genderCombo.setPromptText("Pilih jenis kelamin");
        genderCombo.setPrefWidth(400);

        TextField teleponField = new TextField();
        teleponField.setPromptText("08xxxxxxxxxx");

        TextField emailField = new TextField();
        emailField.setPromptText("email@example.com");

        TextArea alamatArea = new TextArea();
        alamatArea.setPromptText("Alamat lengkap");
        alamatArea.setPrefRowCount(3);
        alamatArea.setWrapText(true);

        DatePicker tanggalPicker = new DatePicker();
        tanggalPicker.setValue(LocalDate.now());
        tanggalPicker.setPrefWidth(400);

        CheckBox memberCheck = new CheckBox("Daftar sebagai Member");

        TextField poinField = new TextField("0");
        poinField.setPromptText("Poin awal");

        form.add(new Label("Nama:"), 0, 0);
        form.add(namaField, 1, 0);
        form.add(new Label("Jenis Kelamin:"), 0, 1);
        form.add(genderCombo, 1, 1);
        form.add(new Label("No. Telepon:"), 0, 2);
        form.add(teleponField, 1, 2);
        form.add(new Label("Email:"), 0, 3);
        form.add(emailField, 1, 3);
        form.add(new Label("Alamat:"), 0, 4);
        form.add(alamatArea, 1, 4);
        form.add(new Label("Tanggal Daftar:"), 0, 5);
        form.add(tanggalPicker, 1, 5);
        form.add(new Label("Member:"), 0, 6);
        form.add(memberCheck, 1, 6);
        form.add(new Label("Poin:"), 0, 7);
        form.add(poinField, 1, 7);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        Button saveButton = new Button("Simpan");
        Button cancelButton = new Button("Batal");

        saveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;");
        cancelButton.setStyle("-fx-background-color: #757575; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;");

        saveButton.setOnAction(e -> {
            if (validateForm(namaField, genderCombo, teleponField, emailField)) {
                Customer newCustomer = new Customer(
                    nextId++,
                    namaField.getText(),
                    genderCombo.getValue(),
                    teleponField.getText(),
                    emailField.getText(),
                    alamatArea.getText(),
                    tanggalPicker.getValue(),
                    memberCheck.isSelected(),
                    Integer.parseInt(poinField.getText())
                );
                customerList.add(newCustomer);
                showSuccessDialog("Pelanggan berhasil ditambahkan!");

                namaField.clear();
                genderCombo.setValue(null);
                teleponField.clear();
                emailField.clear();
                alamatArea.clear();
                tanggalPicker.setValue(LocalDate.now());
                memberCheck.setSelected(false);
                poinField.setText("0");

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

    private boolean validateForm(TextField namaField, ComboBox<String> genderCombo, TextField teleponField, TextField emailField) {
        if (namaField.getText().trim().isEmpty()) {
            showErrorDialog("Nama tidak boleh kosong!");
            return false;
        }
        if (genderCombo.getValue() == null) {
            showErrorDialog("Jenis kelamin harus dipilih!");
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
        return true;
    }

    private void handleEdit(Customer customer) {
        Dialog<Customer> dialog = new Dialog<>();
        dialog.setTitle("Edit Pelanggan");
        dialog.setHeaderText("Edit data pelanggan: " + customer.getNama());

        ButtonType saveButtonType = new ButtonType("Simpan", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField namaField = new TextField(customer.getNama());
        ComboBox<String> genderCombo = new ComboBox<>();
        genderCombo.getItems().addAll("Laki-laki", "Perempuan");
        genderCombo.setValue(customer.getJenisKelamin());
        TextField teleponField = new TextField(customer.getNomorTelepon());
        TextField emailField = new TextField(customer.getEmail());
        TextArea alamatArea = new TextArea(customer.getAlamat());
        alamatArea.setPrefRowCount(3);
        CheckBox memberCheck = new CheckBox();
        memberCheck.setSelected(customer.isMember());
        TextField poinField = new TextField(String.valueOf(customer.getPoin()));

        grid.add(new Label("Nama:"), 0, 0);
        grid.add(namaField, 1, 0);
        grid.add(new Label("Jenis Kelamin:"), 0, 1);
        grid.add(genderCombo, 1, 1);
        grid.add(new Label("Telepon:"), 0, 2);
        grid.add(teleponField, 1, 2);
        grid.add(new Label("Email:"), 0, 3);
        grid.add(emailField, 1, 3);
        grid.add(new Label("Alamat:"), 0, 4);
        grid.add(alamatArea, 1, 4);
        grid.add(new Label("Member:"), 0, 5);
        grid.add(memberCheck, 1, 5);
        grid.add(new Label("Poin:"), 0, 6);
        grid.add(poinField, 1, 6);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                customer.setNama(namaField.getText());
                customer.setJenisKelamin(genderCombo.getValue());
                customer.setNomorTelepon(teleponField.getText());
                customer.setEmail(emailField.getText());
                customer.setAlamat(alamatArea.getText());
                customer.setMember(memberCheck.isSelected());
                customer.setPoin(Integer.parseInt(poinField.getText()));
                return customer;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(result -> {
            customerTable.refresh();
            showSuccessDialog("Pelanggan berhasil diupdate!");
        });
    }

    private void handleDelete(Customer customer) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Hapus");
        alert.setHeaderText("Hapus Pelanggan");
        alert.setContentText("Apakah Anda yakin ingin menghapus pelanggan: " + customer.getNama() + "?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                customerList.remove(customer);
                showSuccessDialog("Pelanggan berhasil dihapus!");
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

    public ObservableList<Customer> getCustomerList() {
        return customerList;
    }
}
