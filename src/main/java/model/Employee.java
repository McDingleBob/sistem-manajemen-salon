package model;

import java.time.LocalDate;

/**
 * Model class untuk Employee (Karyawan) Salon
 */
public class Employee {
    private int id;
    private String nama;
    private String posisi;
    private String nomorTelepon;
    private String email;
    private LocalDate tanggalMasuk;
    private double gaji;
    private String status; // Aktif, Cuti, Resign

    public Employee() {}

    public Employee(int id, String nama, String posisi, String nomorTelepon, String email, LocalDate tanggalMasuk, double gaji, String status) {
        this.id = id;
        this.nama = nama;
        this.posisi = posisi;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.tanggalMasuk = tanggalMasuk;
        this.gaji = gaji;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPosisi() {
        return posisi;
    }

    public void setPosisi(String posisi) {
        this.posisi = posisi;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getTanggalMasuk() {
        return tanggalMasuk;
    }

    public void setTanggalMasuk(LocalDate tanggalMasuk) {
        this.tanggalMasuk = tanggalMasuk;
    }

    public double getGaji() {
        return gaji;
    }

    public void setGaji(double gaji) {
        this.gaji = gaji;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", nama='" + nama + '\'' +
                ", posisi='" + posisi + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
