# 🗣️ Panduan Setup Sistem Manajemen Salon

> Panduan lengkap untuk setup repository lokal dan memulai coding bersama

---

## ⏱️ Persiapan Awal (First Time Setup)

### 1. Install Required Software

Sebelum memulai, pastikan Anda sudah menginstall:

#### a. **Java Development Kit (JDK) 11+**
- Download dari: https://www.oracle.com/java/technologies/downloads/
- Atau gunakan OpenJDK: https://openjdk.java.net/
- Verifikasi instalasi:
  ```bash
  java -version
  javac -version
  ```

#### b. **Git**
- Download dari: https://git-scm.com/downloads
- Verifikasi instalasi:
  ```bash
  git --version
  ```

#### c. **JavaFX SDK 25.0.1+**
- Download dari: https://gluonhq.com/products/javafx/
- Extract ke folder yang mudah diakses (contoh: `C:\javafx-sdk` atau `~/javafx-sdk`)
- **Catat path ini untuk langkah selanjutnya**

#### d. **IDE (Pilih salah satu)**
- **IntelliJ IDEA** (Recommended)
  - Download: https://www.jetbrains.com/idea/download/
  - Community Edition: FREE
  
- **Eclipse IDE**
  - Download: https://www.eclipse.org/downloads/
  
- **NetBeans**
  - Download: https://netbeans.apache.org/download/

---

## 📋 Setup Repository

### 1. Clone Repository

Buka Command Prompt/Terminal dan jalankan:

```bash
# Navigate ke folder tempat Anda ingin menyimpan project
cd Users/YourUsername/Documents

# Clone repository
git clone https://github.com/McDingleBob/sistem-manajemen-salon.git

# Masuk ke folder project
cd sistem-manajemen-salon
```

### 2. Verifikasi Clone

```bash
# Lihat status repository
git status

# Lihat remote repository
git remote -v

# Lihat commit history
git log --oneline -5
```

---

## 🙋🏽 Setup IDE (IntelliJ IDEA)

### 1. Buka Project

1. Buka **IntelliJ IDEA**
2. Pilih **File** > **Open**
3. Navigate ke folder `sistem-manajemen-salon` yang sudah di-clone
4. Klik **Open**
5. Pilih **Open as Project**

### 2. Configure JavaFX SDK

1. Buka **File** > **Project Structure** (atau tekan `Ctrl+Alt+Shift+S`)
2. Pilih **Libraries** di sebelah kiri
3. Klik **+** (New Project Library) > **Java**
4. Browse dan pilih folder JavaFX SDK yang sudah di-download
5. Klik **Apply** > **OK**

### 3. Setup Module Dependencies

1. Buka **File** > **Project Structure** lagi
2. Pilih **Modules** > **Dependencies**
3. Tambahkan JavaFX library yang sudah Anda daftarkan di step 2
4. Klik **Apply** > **OK**

### 4. Configure Run Configuration

1. Buka **Run** > **Edit Configurations**
2. Klik **+** untuk menambah configuration baru
3. Pilih **Application**
4. Set konfigurasi:
   ```
   Name: Main
   Main class: main.Main
   VM options: --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls
   ```
5. Replace `/path/to/javafx-sdk` dengan path JavaFX SDK Anda
6. Klik **Apply** > **OK**

### 5. Run Project

1. Buka file `src/main/java/main/Main.java`
2. Klik tombol **Run** (Green play button) atau tekan `Shift+F10`
3. Aplikasi JavaFX akan terbuka!

---

## 📚 Git Workflow Dasar

### Membuat Feature Branch

```bash
# Pastikan Anda di branch main dan sudah pull latest
git checkout main
git pull origin main

# Buat branch baru untuk fitur
git checkout -b feature/nama-fitur
```

### Commit Perubahan

```bash
# Lihat status file yang berubah
git status

# Staging file untuk commit
git add .

# Commit dengan pesan yang descriptive
git commit -m "[FEATURE] Deskripsi fitur yang dibuat"

# Contoh:
git commit -m "[FEATURE] Add Service model class with getters and setters"
```

### Push ke Remote

```bash
# Push branch ke GitHub
git push origin feature/nama-fitur
```

### Membuat Pull Request

1. Buka GitHub repository di browser
2. Anda akan melihat banner untuk membuat Pull Request
3. Klik **Compare & pull request**
4. Isi:
   - **Title**: Deskripsi singkat
   - **Description**: Penjelasan detail perubahan
5. Klik **Create Pull Request**

### Merge Pull Request

1. Anggota lain akan review code Anda
2. Jika OK, mereka akan klik **Approve** dan **Merge pull request**
3. Setelah merge, update local branch Anda:
   ```bash
   git checkout main
   git pull origin main
   ```

---

## 🗐️ Update Repository

### Sebelum Mulai Coding

Selalu pull latest changes:

```bash
git pull origin main
```

### Conflict Resolution

Jika terjadi conflict:

```bash
# Lihat file yang conflict
git status

# Edit file yang conflict
# Hapus conflict markers (<<<<<<<, =======, >>>>>>>)
# Pilih version mana yang ingin digunakan

# Setelah fix, commit:
git add .
git commit -m "[MERGE] Resolve conflicts from main branch"
```

---

## 🔍 Troubleshooting

### Error: JavaFX not found

**Solusi:**
- Pastikan JavaFX SDK sudah di-download dan di-extract
- Verifikasi path ke JavaFX di IDE settings
- Restart IDE setelah mengubah settings

### Error: Cannot run program "javac"

**Solusi:**
- Pastikan JDK (bukan hanya JRE) sudah ter-install
- Set JAVA_HOME environment variable
- Restart terminal/IDE

### Git: Permission denied (publickey)

**Solusi:**
- Setup SSH key: https://docs.github.com/en/authentication/connecting-to-github-with-ssh
- Atau gunakan HTTPS URL dengan GitHub token

### Bagaimana cara undo last commit?

```bash
# Undo commit tetapi keep changes
git reset --soft HEAD~1

# Undo commit dan discard changes
git reset --hard HEAD~1
```

---

## 퉰a Tips & Best Practices

✅ **DO:**
- Pull latest changes sebelum mulai coding
- Commit frequently dengan pesan yang meaningful
- Test code sebelum push
- Push ke feature branch, bukan langsung ke main
- Communicate dengan team tentang fitur yang dikerjakan

❌ **DON'T:**
- Langsung push ke main branch
- Commit dengan pesan generic seperti "fix", "update", "work"
- Forgot to pull latest changes
- Push half-finished code
- Work pada file yang sedang dikerjakan orang lain tanpa koordinasi

---

## 📏 Referensi

- [Git Official Documentation](https://git-scm.com/doc)
- [GitHub Hello World](https://guides.github.com/activities/hello-world/)
- [JavaFX Getting Started](https://openjfx.io/openjfx-docs/21/getting-started/)
- [IntelliJ IDEA Help](https://www.jetbrains.com/help/idea/)

---

## 🙋🏽 Butuh Bantuan?

Jika ada masalah:

1. **Cek Channel Discussion** di GitHub untuk FAQ
2. **Buka Issue** jika menemukan bug
3. **Chat dengan team** di Telegram/WhatsApp group
4. **Lihat dokumentasi** di README.md dan TEAM.md

---

**Happy Coding! 🚀**

*Last Updated: November 2025*
