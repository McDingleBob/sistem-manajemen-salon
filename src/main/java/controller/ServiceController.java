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
import model.Service;

/**
 * Controller untuk mengelola Service (Pelayanan)
 */
public class ServiceController {

    private ObservableList<Service> serviceList;
    private TableView<Service> serviceTable;
    private int nextId = 1;

    public ServiceController() {
        // Initialize dengan data dummy
        serviceList = FXCollections.observableArrayList();
        loadDummyData();
    }

    /**
     * Load data dummy untuk testing
     */
    private void loadDummyData() {
        serviceList.add(new Service(nextId++, "Potong Rambut Pria", 50000, 30, "Potong rambut standar untuk pria"));
        serviceList.add(new Service(nextId++, "Potong Rambut Wanita", 75000, 45, "Potong rambut standar untuk wanita"));
        serviceList.add(new Service(nextId++, "Pewarnaan Rambut", 200000, 120, "Pewarnaan rambut dengan bahan berkualitas"));
        serviceList.add(new Service(nextId++, "Smoothing", 350000, 180, "Smoothing rambut untuk tampilan lebih rapi"));
        serviceList.add(new Service(nextId++, "Manicure", 50000, 30, "Perawatan kuku tangan"));
        serviceList.add(new Service(nextId++, "Pedicure", 60000, 45, "Perawatan kuku kaki"));
        serviceList.add(new Service(nextId++, "Facial Treatment", 100000, 60, "Perawatan wajah lengkap"));
    }

    /**
     * Membuat view untuk daftar service
     */
    public VBox createServiceListView() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(20));

        // Header
        Label titleLabel = new Label("Daftar Pelayanan");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        // Search bar
        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        TextField searchField = new TextField();
        searchField.setPromptText("Cari pelayanan...");
        searchField.setPrefWidth(300);
        Button searchButton = new Button("Cari");
        Button refreshButton = new Button("Refresh");

        searchButton.setOnAction(e -> {
            String keyword = searchField.getText().toLowerCase();
            if (keyword.isEmpty()) {
                serviceTable.setItems(serviceList);
            } else {
                ObservableList<Service> filtered = FXCollections.observableArrayList();
                for (Service s : serviceList) {
                    if (s.getNama().toLowerCase().contains(keyword) ||
                        s.getDeskripsi().toLowerCase().contains(keyword)) {
                        filtered.add(s);
                    }
                }
                serviceTable.setItems(filtered);
            }
        });

        refreshButton.setOnAction(e -> {
            searchField.clear();
            serviceTable.setItems(serviceList);
        });

        searchBox.getChildren().addAll(new Label("Pencarian:"), searchField, searchButton, refreshButton);

        // Table
        serviceTable = new TableView<>();
        serviceTable.setItems(serviceList);

        TableColumn<Service, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(50);

        TableColumn<Service, String> namaCol = new TableColumn<>("Nama Pelayanan");
        namaCol.setCellValueFactory(new PropertyValueFactory<>("nama"));
        namaCol.setPrefWidth(200);

        TableColumn<Service, Double> hargaCol = new TableColumn<>("Harga");
        hargaCol.setCellValueFactory(new PropertyValueFactory<>("harga"));
        hargaCol.setPrefWidth(100);
        hargaCol.setCellFactory(column -> new TableCell<Service, Double>() {
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

        TableColumn<Service, Integer> durasiCol = new TableColumn<>("Durasi (menit)");
        durasiCol.setCellValueFactory(new PropertyValueFactory<>("durasi"));
        durasiCol.setPrefWidth(120);

        TableColumn<Service, String> deskripsiCol = new TableColumn<>("Deskripsi");
        deskripsiCol.setCellValueFactory(new PropertyValueFactory<>("deskripsi"));
        deskripsiCol.setPrefWidth(250);

        TableColumn<Service, Void> actionCol = new TableColumn<>("Aksi");
        actionCol.setPrefWidth(150);
        actionCol.setCellFactory(column -> new TableCell<Service, Void>() {
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Hapus");
            private final HBox actionBox = new HBox(5, editBtn, deleteBtn);

            {
                editBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                deleteBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

                editBtn.setOnAction(e -> {
                    Service service = getTableView().getItems().get(getIndex());
                    handleEdit(service);
                });

                deleteBtn.setOnAction(e -> {
                    Service service = getTableView().getItems().get(getIndex());
                    handleDelete(service);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(actionBox);
                }
            }
        });

        serviceTable.getColumns().addAll(idCol, namaCol, hargaCol, durasiCol, deskripsiCol, actionCol);

        // Statistics
        Label statsLabel = new Label(String.format("Total Pelayanan: %d", serviceList.size()));
        statsLabel.setFont(Font.font("System", FontWeight.SEMI_BOLD, 14));

        container.getChildren().addAll(titleLabel, searchBox, serviceTable, statsLabel);
        VBox.setVgrow(serviceTable, Priority.ALWAYS);

        return container;
    }

    /**
     * Membuat form untuk tambah service baru
     */
    public VBox createServiceAddForm(Runnable onSaveCallback) {
        VBox container = new VBox(15);
        container.setPadding(new Insets(20));
        container.setMaxWidth(600);

        // Header
        Label titleLabel = new Label("Tambah Pelayanan Baru");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        // Form
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(15);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 5;");

        // Nama
        Label namaLabel = new Label("Nama Pelayanan:");
        TextField namaField = new TextField();
        namaField.setPromptText("Masukkan nama pelayanan");
        namaField.setPrefWidth(400);

        // Harga
        Label hargaLabel = new Label("Harga (Rp):");
        TextField hargaField = new TextField();
        hargaField.setPromptText("Masukkan harga");

        // Durasi
        Label durasiLabel = new Label("Durasi (menit):");
        TextField durasiField = new TextField();
        durasiField.setPromptText("Masukkan durasi");

        // Deskripsi
        Label deskripsiLabel = new Label("Deskripsi:");
        TextArea deskripsiArea = new TextArea();
        deskripsiArea.setPromptText("Masukkan deskripsi pelayanan");
        deskripsiArea.setPrefRowCount(4);
        deskripsiArea.setWrapText(true);

        form.add(namaLabel, 0, 0);
        form.add(namaField, 1, 0);
        form.add(hargaLabel, 0, 1);
        form.add(hargaField, 1, 1);
        form.add(durasiLabel, 0, 2);
        form.add(durasiField, 1, 2);
        form.add(deskripsiLabel, 0, 3);
        form.add(deskripsiArea, 1, 3);

        // Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        Button saveButton = new Button("Simpan");
        Button cancelButton = new Button("Batal");

        saveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;");
        cancelButton.setStyle("-fx-background-color: #757575; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;");

        saveButton.setOnAction(e -> {
            if (validateForm(namaField, hargaField, durasiField)) {
                Service newService = new Service(
                    nextId++,
                    namaField.getText(),
                    Double.parseDouble(hargaField.getText()),
                    Integer.parseInt(durasiField.getText()),
                    deskripsiArea.getText()
                );
                serviceList.add(newService);

                showSuccessDialog("Pelayanan berhasil ditambahkan!");

                // Clear form
                namaField.clear();
                hargaField.clear();
                durasiField.clear();
                deskripsiArea.clear();

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

    /**
     * Validasi form
     */
    private boolean validateForm(TextField namaField, TextField hargaField, TextField durasiField) {
        if (namaField.getText().trim().isEmpty()) {
            showErrorDialog("Nama pelayanan tidak boleh kosong!");
            return false;
        }

        try {
            double harga = Double.parseDouble(hargaField.getText());
            if (harga <= 0) {
                showErrorDialog("Harga harus lebih dari 0!");
                return false;
            }
        } catch (NumberFormatException e) {
            showErrorDialog("Harga harus berupa angka!");
            return false;
        }

        try {
            int durasi = Integer.parseInt(durasiField.getText());
            if (durasi <= 0) {
                showErrorDialog("Durasi harus lebih dari 0!");
                return false;
            }
        } catch (NumberFormatException e) {
            showErrorDialog("Durasi harus berupa angka!");
            return false;
        }

        return true;
    }

    /**
     * Handle edit service
     */
    private void handleEdit(Service service) {
        // Create edit dialog
        Dialog<Service> dialog = new Dialog<>();
        dialog.setTitle("Edit Pelayanan");
        dialog.setHeaderText("Edit data pelayanan: " + service.getNama());

        ButtonType saveButtonType = new ButtonType("Simpan", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField namaField = new TextField(service.getNama());
        TextField hargaField = new TextField(String.valueOf(service.getHarga()));
        TextField durasiField = new TextField(String.valueOf(service.getDurasi()));
        TextArea deskripsiArea = new TextArea(service.getDeskripsi());
        deskripsiArea.setPrefRowCount(3);

        grid.add(new Label("Nama:"), 0, 0);
        grid.add(namaField, 1, 0);
        grid.add(new Label("Harga:"), 0, 1);
        grid.add(hargaField, 1, 1);
        grid.add(new Label("Durasi:"), 0, 2);
        grid.add(durasiField, 1, 2);
        grid.add(new Label("Deskripsi:"), 0, 3);
        grid.add(deskripsiArea, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                service.setNama(namaField.getText());
                service.setHarga(Double.parseDouble(hargaField.getText()));
                service.setDurasi(Integer.parseInt(durasiField.getText()));
                service.setDeskripsi(deskripsiArea.getText());
                return service;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(result -> {
            serviceTable.refresh();
            showSuccessDialog("Pelayanan berhasil diupdate!");
        });
    }

    /**
     * Handle delete service
     */
    private void handleDelete(Service service) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Hapus");
        alert.setHeaderText("Hapus Pelayanan");
        alert.setContentText("Apakah Anda yakin ingin menghapus pelayanan: " + service.getNama() + "?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                serviceList.remove(service);
                showSuccessDialog("Pelayanan berhasil dihapus!");
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

    public ObservableList<Service> getServiceList() {
        return serviceList;
    }
}
