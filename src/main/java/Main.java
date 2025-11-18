package main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Main application class untuk Sistem Manajemen Salon
 *
 * Aplikasi ini menggunakan JavaFX untuk membuat aplikasi desktop
 * manajemen salon dengan fitur lengkap.
 *
 * @author Tim Sistem Manajemen Salon
 * @version 1.0
 */
public class Main extends Application {

    private BorderPane mainLayout;
    private Label statusLabel;

    @Override
    public void start(Stage primaryStage) {
        try {
            // Create main layout
            mainLayout = new BorderPane();

            // Create menu bar
            MenuBar menuBar = createMenuBar();
            mainLayout.setTop(menuBar);

            // Create dashboard (center content)
            VBox dashboard = createDashboard();
            mainLayout.setCenter(dashboard);

            // Create status bar
            HBox statusBar = createStatusBar();
            mainLayout.setBottom(statusBar);

            // Create scene
            Scene scene = new Scene(mainLayout, 1024, 768);

            // Setup stage
            primaryStage.setTitle("Sistem Manajemen Salon - Dashboard");
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(600);
            primaryStage.centerOnScreen();
            primaryStage.show();

            // Update status
            updateStatus("Aplikasi berhasil dimulai");

        } catch (Exception e) {
            e.printStackTrace();
            showErrorDialog("Error", "Gagal memulai aplikasi: " + e.getMessage());
        }
    }

    /**
     * Membuat MenuBar dengan semua menu aplikasi
     */
    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        // Menu File
        Menu menuFile = new Menu("File");
        MenuItem menuDashboard = new MenuItem("Dashboard");
        MenuItem menuExit = new MenuItem("Keluar");

        menuDashboard.setOnAction(e -> showDashboard());
        menuExit.setOnAction(e -> handleExit());

        menuFile.getItems().addAll(menuDashboard, new SeparatorMenuItem(), menuExit);

        // Menu Pelayanan
        Menu menuService = new Menu("Pelayanan");
        MenuItem menuServiceList = new MenuItem("Daftar Pelayanan");
        MenuItem menuServiceAdd = new MenuItem("Tambah Pelayanan");

        menuServiceList.setOnAction(e -> showServiceList());
        menuServiceAdd.setOnAction(e -> showServiceAdd());

        menuService.getItems().addAll(menuServiceList, menuServiceAdd);

        // Menu Karyawan
        Menu menuEmployee = new Menu("Karyawan");
        MenuItem menuEmployeeList = new MenuItem("Daftar Karyawan");
        MenuItem menuEmployeeAdd = new MenuItem("Tambah Karyawan");

        menuEmployeeList.setOnAction(e -> showEmployeeList());
        menuEmployeeAdd.setOnAction(e -> showEmployeeAdd());

        menuEmployee.getItems().addAll(menuEmployeeList, menuEmployeeAdd);

        // Menu Pelanggan
        Menu menuCustomer = new Menu("Pelanggan");
        MenuItem menuCustomerList = new MenuItem("Daftar Pelanggan");
        MenuItem menuCustomerAdd = new MenuItem("Tambah Pelanggan");

        menuCustomerList.setOnAction(e -> showCustomerList());
        menuCustomerAdd.setOnAction(e -> showCustomerAdd());

        menuCustomer.getItems().addAll(menuCustomerList, menuCustomerAdd);

        // Menu Appointment
        Menu menuAppointment = new Menu("Appointment");
        MenuItem menuAppointmentList = new MenuItem("Daftar Appointment");
        MenuItem menuAppointmentAdd = new MenuItem("Buat Appointment Baru");
        MenuItem menuAppointmentCalendar = new MenuItem("Kalender Appointment");

        menuAppointmentList.setOnAction(e -> showAppointmentList());
        menuAppointmentAdd.setOnAction(e -> showAppointmentAdd());
        menuAppointmentCalendar.setOnAction(e -> showAppointmentCalendar());

        menuAppointment.getItems().addAll(menuAppointmentList, menuAppointmentAdd,
                                          new SeparatorMenuItem(), menuAppointmentCalendar);

        // Menu Transaksi
        Menu menuTransaction = new Menu("Transaksi");
        MenuItem menuTransactionNew = new MenuItem("Transaksi Baru");
        MenuItem menuTransactionHistory = new MenuItem("Riwayat Transaksi");

        menuTransactionNew.setOnAction(e -> showTransactionNew());
        menuTransactionHistory.setOnAction(e -> showTransactionHistory());

        menuTransaction.getItems().addAll(menuTransactionNew, menuTransactionHistory);

        // Menu Laporan
        Menu menuReport = new Menu("Laporan");
        MenuItem menuReportSales = new MenuItem("Laporan Penjualan");
        MenuItem menuReportRevenue = new MenuItem("Laporan Pendapatan");
        MenuItem menuReportEmployee = new MenuItem("Laporan Kinerja Karyawan");

        menuReportSales.setOnAction(e -> showReportSales());
        menuReportRevenue.setOnAction(e -> showReportRevenue());
        menuReportEmployee.setOnAction(e -> showReportEmployee());

        menuReport.getItems().addAll(menuReportSales, menuReportRevenue,
                                     new SeparatorMenuItem(), menuReportEmployee);

        // Menu Bantuan
        Menu menuHelp = new Menu("Bantuan");
        MenuItem menuAbout = new MenuItem("Tentang Aplikasi");
        MenuItem menuManual = new MenuItem("Panduan Penggunaan");

        menuAbout.setOnAction(e -> showAbout());
        menuManual.setOnAction(e -> showManual());

        menuHelp.getItems().addAll(menuManual, new SeparatorMenuItem(), menuAbout);

        // Add all menus to menu bar
        menuBar.getMenus().addAll(menuFile, menuService, menuEmployee, menuCustomer,
                                  menuAppointment, menuTransaction, menuReport, menuHelp);

        return menuBar;
    }

    /**
     * Membuat Dashboard utama
     */
    private VBox createDashboard() {
        VBox dashboard = new VBox(20);
        dashboard.setPadding(new Insets(30));
        dashboard.setAlignment(Pos.TOP_CENTER);

        // Title
        Label titleLabel = new Label("Sistem Manajemen Salon");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 32));

        // Welcome message
        Label welcomeLabel = new Label("Selamat datang di Aplikasi Manajemen Salon");
        welcomeLabel.setFont(Font.font("System", 16));

        // Quick access buttons
        Label quickAccessLabel = new Label("Akses Cepat");
        quickAccessLabel.setFont(Font.font("System", FontWeight.BOLD, 18));

        GridPane buttonGrid = new GridPane();
        buttonGrid.setHgap(15);
        buttonGrid.setVgap(15);
        buttonGrid.setAlignment(Pos.CENTER);
        buttonGrid.setPadding(new Insets(20));

        // Quick access buttons
        Button btnNewTransaction = createQuickButton("Transaksi Baru", 150, 80);
        Button btnNewAppointment = createQuickButton("Buat Appointment", 150, 80);
        Button btnServiceList = createQuickButton("Daftar Pelayanan", 150, 80);
        Button btnCustomerList = createQuickButton("Daftar Pelanggan", 150, 80);
        Button btnEmployeeList = createQuickButton("Daftar Karyawan", 150, 80);
        Button btnReports = createQuickButton("Laporan", 150, 80);

        // Set button actions
        btnNewTransaction.setOnAction(e -> showTransactionNew());
        btnNewAppointment.setOnAction(e -> showAppointmentAdd());
        btnServiceList.setOnAction(e -> showServiceList());
        btnCustomerList.setOnAction(e -> showCustomerList());
        btnEmployeeList.setOnAction(e -> showEmployeeList());
        btnReports.setOnAction(e -> showReportSales());

        // Add buttons to grid
        buttonGrid.add(btnNewTransaction, 0, 0);
        buttonGrid.add(btnNewAppointment, 1, 0);
        buttonGrid.add(btnServiceList, 2, 0);
        buttonGrid.add(btnCustomerList, 0, 1);
        buttonGrid.add(btnEmployeeList, 1, 1);
        buttonGrid.add(btnReports, 2, 1);

        // Statistics area (placeholder)
        Label statsLabel = new Label("Statistik Hari Ini");
        statsLabel.setFont(Font.font("System", FontWeight.BOLD, 18));

        GridPane statsGrid = new GridPane();
        statsGrid.setHgap(30);
        statsGrid.setVgap(10);
        statsGrid.setAlignment(Pos.CENTER);
        statsGrid.setPadding(new Insets(20));

        // Stats placeholders
        statsGrid.add(createStatBox("Total Transaksi", "0"), 0, 0);
        statsGrid.add(createStatBox("Total Pendapatan", "Rp 0"), 1, 0);
        statsGrid.add(createStatBox("Appointment Hari Ini", "0"), 2, 0);

        // Add all to dashboard
        dashboard.getChildren().addAll(
            titleLabel,
            welcomeLabel,
            new Separator(),
            quickAccessLabel,
            buttonGrid,
            new Separator(),
            statsLabel,
            statsGrid
        );

        return dashboard;
    }

    /**
     * Membuat button untuk quick access
     */
    private Button createQuickButton(String text, double width, double height) {
        Button button = new Button(text);
        button.setPrefSize(width, height);
        button.setFont(Font.font("System", FontWeight.SEMI_BOLD, 12));
        return button;
    }

    /**
     * Membuat box untuk menampilkan statistik
     */
    private VBox createStatBox(String label, String value) {
        VBox box = new VBox(5);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(15));
        box.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1; -fx-background-color: #f9f9f9;");
        box.setPrefWidth(200);

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        Label labelText = new Label(label);
        labelText.setFont(Font.font("System", 12));

        box.getChildren().addAll(valueLabel, labelText);
        return box;
    }

    /**
     * Membuat status bar di bagian bawah aplikasi
     */
    private HBox createStatusBar() {
        HBox statusBar = new HBox();
        statusBar.setPadding(new Insets(5, 10, 5, 10));
        statusBar.setStyle("-fx-background-color: #e0e0e0;");

        statusLabel = new Label("Siap");
        statusLabel.setFont(Font.font("System", 11));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label versionLabel = new Label("v1.0");
        versionLabel.setFont(Font.font("System", 11));

        statusBar.getChildren().addAll(statusLabel, spacer, versionLabel);
        return statusBar;
    }

    /**
     * Update status bar text
     */
    private void updateStatus(String message) {
        if (statusLabel != null) {
            statusLabel.setText(message);
        }
    }

    // ===== Menu Action Handlers =====

    private void showDashboard() {
        mainLayout.setCenter(createDashboard());
        updateStatus("Dashboard ditampilkan");
    }

    private void showServiceList() {
        showPlaceholder("Daftar Pelayanan", "Modul Daftar Pelayanan akan ditampilkan di sini");
    }

    private void showServiceAdd() {
        showPlaceholder("Tambah Pelayanan", "Form Tambah Pelayanan akan ditampilkan di sini");
    }

    private void showEmployeeList() {
        showPlaceholder("Daftar Karyawan", "Modul Daftar Karyawan akan ditampilkan di sini");
    }

    private void showEmployeeAdd() {
        showPlaceholder("Tambah Karyawan", "Form Tambah Karyawan akan ditampilkan di sini");
    }

    private void showCustomerList() {
        showPlaceholder("Daftar Pelanggan", "Modul Daftar Pelanggan akan ditampilkan di sini");
    }

    private void showCustomerAdd() {
        showPlaceholder("Tambah Pelanggan", "Form Tambah Pelanggan akan ditampilkan di sini");
    }

    private void showAppointmentList() {
        showPlaceholder("Daftar Appointment", "Modul Daftar Appointment akan ditampilkan di sini");
    }

    private void showAppointmentAdd() {
        showPlaceholder("Buat Appointment", "Form Buat Appointment akan ditampilkan di sini");
    }

    private void showAppointmentCalendar() {
        showPlaceholder("Kalender Appointment", "Kalender Appointment akan ditampilkan di sini");
    }

    private void showTransactionNew() {
        showPlaceholder("Transaksi Baru", "Form Transaksi Baru akan ditampilkan di sini");
    }

    private void showTransactionHistory() {
        showPlaceholder("Riwayat Transaksi", "Riwayat Transaksi akan ditampilkan di sini");
    }

    private void showReportSales() {
        showPlaceholder("Laporan Penjualan", "Laporan Penjualan akan ditampilkan di sini");
    }

    private void showReportRevenue() {
        showPlaceholder("Laporan Pendapatan", "Laporan Pendapatan akan ditampilkan di sini");
    }

    private void showReportEmployee() {
        showPlaceholder("Laporan Kinerja Karyawan", "Laporan Kinerja Karyawan akan ditampilkan di sini");
    }

    private void showManual() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Panduan Penggunaan");
        alert.setHeaderText("Panduan Penggunaan Aplikasi");
        alert.setContentText("Panduan lengkap dapat dilihat di file SETUP_GUIDE.md di repository.\n\n" +
                            "Untuk informasi lebih lanjut, silakan hubungi tim developer.");
        alert.showAndWait();
    }

    private void showAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tentang Aplikasi");
        alert.setHeaderText("Sistem Manajemen Salon v1.0");
        alert.setContentText("Aplikasi desktop untuk manajemen salon.\n\n" +
                            "Teknologi: Java + JavaFX\n" +
                            "Dikembangkan oleh: Tim Sistem Manajemen Salon\n" +
                            "Tahun: 2025\n\n" +
                            "Repository: github.com/McDingleBob/sistem-manajemen-salon");
        alert.showAndWait();
    }

    /**
     * Menampilkan placeholder untuk modul yang belum diimplementasi
     */
    private void showPlaceholder(String title, String message) {
        VBox placeholder = new VBox(20);
        placeholder.setAlignment(Pos.CENTER);
        placeholder.setPadding(new Insets(50));

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        Label messageLabel = new Label(message);
        messageLabel.setFont(Font.font("System", 14));

        Button backButton = new Button("Kembali ke Dashboard");
        backButton.setOnAction(e -> showDashboard());

        placeholder.getChildren().addAll(titleLabel, messageLabel, backButton);
        mainLayout.setCenter(placeholder);
        updateStatus(title);
    }

    /**
     * Handle exit aplikasi dengan konfirmasi
     */
    private void handleExit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Keluar");
        alert.setHeaderText("Keluar dari Aplikasi");
        alert.setContentText("Apakah Anda yakin ingin keluar?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.exit(0);
            }
        });
    }

    /**
     * Menampilkan dialog error
     */
    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Main method - entry point aplikasi
     */
    public static void main(String[] args) {
        launch(args);
    }
}
