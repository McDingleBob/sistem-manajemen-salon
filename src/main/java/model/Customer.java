package model;

import java.time.LocalDate;

/**
 * Model class untuk Customer (Pelanggan) Salon
 */
public class Customer {
    private int id;
    private String nama;
    private String jenisKelamin;
    private String nomorTelepon;
    private String email;
    private String alamat;
    private LocalDate tanggalDaftar;
    private boolean isMember;
    private int poin;

    public Customer() {}

    public Customer(int id, String nama, String jenisKelamin, String nomorTelepon, String email, String alamat, LocalDate tanggalDaftar, boolean isMember, int poin) {
        this.id = id;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.alamat = alamat;
        this.tanggalDaftar = tanggalDaftar;
        this.isMember = isMember;
        this.poin = poin;
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

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
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

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public LocalDate getTanggalDaftar() {
        return tanggalDaftar;
    }

    public void setTanggalDaftar(LocalDate tanggalDaftar) {
        this.tanggalDaftar = tanggalDaftar;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean member) {
        isMember = member;
    }

    public int getPoin() {
        return poin;
    }

    public void setPoin(int poin) {
        this.poin = poin;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", nama='" + nama + '\'' +
                ", nomorTelepon='" + nomorTelepon + '\'' +
                ", isMember=" + isMember +
                '}';
    }
}
