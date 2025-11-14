# Sistem Manajemen Salon

Aplikasi Java JavaFX untuk Sistem Manajemen Salon - Tugas Kelompok

## 📋 Deskripsi Proyek

Sistem Manajemen Salon adalah aplikasi desktop berbasis JavaFX yang dirancang untuk mengelola operasional salon secara efisien. Aplikasi ini dibangun dengan Java dan menggunakan JavaFX untuk antarmuka grafis yang modern dan responsif.

## 🎯 Fitur Utama

- Manajemen Pelayanan (Service)
- Manajemen Karyawan (Employee)
- Manajemen Jadwal Appointment
- Manajemen Pembayaran & Transaksi
- Laporan Penjualan & Pendapatan
- Manajemen Member/Pelanggan

## 🛠️ Teknologi yang Digunakan

- **Java 11+** - Bahasa pemrograman utama
- **JavaFX 25.0.1** - GUI Framework
- **MySQL** - Database (opsional)
- **Maven** - Build tool (opsional)

## 📁 Struktur Proyek

```
sistem-manajemen-salon/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── main/
│   │   │   │   └── Main.java          # Entry point aplikasi
│   │   │   ├── model/
│   │   │   │   ├── Service.java       # Model data Service
│   │   │   │   ├── Employee.java      # Model data Employee
│   │   │   │   ├── Customer.java      # Model data Customer
│   │   │   │   └── Transaction.java   # Model data Transaction
│   │   │   ├── controller/
│   │   │   │   └── (Controller classes)
│   │   │   ├── view/
│   │   │   │   └── (FXML files)
│   │   │   └── util/
│   │   │       └── (Utility classes)
│   │   └── resources/
│   │       ├── fxml/
│   │       │   └── (FXML UI files)
│   │       └── css/
│   │           └── (CSS Stylesheets)
│   └── test/
│       └── java/
├── lib/
│   └── (External libraries/JAR files)
├── .gitignore
├── README.md
└── build.xml (atau pom.xml untuk Maven)
```

## 🚀 Cara Menjalankan Aplikasi

### Dengan IDE (IntelliJ IDEA / Eclipse / NetBeans)

1. Clone repository:
   ```bash
   git clone https://github.com/McDingleBob/sistem-manajemen-salon.git
   ```

2. Import project ke IDE Anda

3. Konfigurasi JavaFX SDK (jika belum)
   - Download JavaFX SDK dari [javafx.io](https://gluonhq.com/products/javafx/)
   - Setup JavaFX SDK path di IDE settings

4. Run `Main.java` dari package `main`

### Dengan Command Line

```bash
# Compile
javac -d bin --module-path /path/to/javafx/lib --add-modules javafx.controls src/main/java/main/*.java

# Run
java --module-path /path/to/javafx/lib --add-modules javafx.controls -cp bin main.Main
```

## 👥 Anggota Kelompok

Daftar anggota kelompok yang berkontribusi pada proyek ini:

- [Nama Anggota 1] - Role
- [Nama Anggota 2] - Role
- [Nama Anggota 3] - Role

*Silakan update daftar anggota di README ini*

## 📝 Panduan Kolaborasi Git

### Clone Repository
```bash
git clone https://github.com/McDingleBob/sistem-manajemen-salon.git
cd sistem-manajemen-salon
```

### Membuat Branch untuk Fitur Baru
```bash
git checkout -b fitur/nama-fitur
```

### Commit Perubahan
```bash
git add .
git commit -m "Deskripsi perubahan yang meaningful"
```

### Push ke Remote
```bash
git push origin fitur/nama-fitur
```

### Pull Request
1. Buat Pull Request di GitHub
2. Tanyakan review dari anggota lain
3. Merge setelah diapprove

### Update Repository Lokal
```bash
git pull origin main
```

## 📚 Konvensi Coding

- Gunakan CamelCase untuk nama class: `MainWindow`, `ServiceModel`
- Gunakan camelCase untuk nama method dan variable: `getServiceName()`, `serviceList`
- Gunakan UPPER_CASE untuk constant: `MAX_LENGTH`, `DEFAULT_SIZE`
- Tambahkan JavaDoc untuk public methods
- Maksimal 100 karakter per baris kode

## 🤝 Kontribusi

1. Fork repository ini
2. Buat branch fitur (`git checkout -b fitur/AmazingFeature`)
3. Commit perubahan Anda (`git commit -m 'Add some AmazingFeature'`)
4. Push ke branch (`git push origin fitur/AmazingFeature`)
5. Buat Pull Request

## 📄 Lisensi

Proyek ini adalah bagian dari tugas kelompok pembelajaran dan bebas digunakan untuk tujuan pendidikan.

## 📞 Kontak

Untuk pertanyaan atau bantuan, silakan hubungi pembuat proyek atau open issue di GitHub.

---

**Last Updated:** November 2025
**Repository URL:** https://github.com/McDingleBob/sistem-manajemen-salon
