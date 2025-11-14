package model;

/**
 * Model class untuk Service (Pelayanan) Salon
 */
public class Service {
    private int id;
    private String nama;
    private double harga;
    private int durasi; // dalam menit
    private String deskripsi;

    public Service() {}

    public Service(int id, String nama, double harga, int durasi) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.durasi = durasi;
    }

    public Service(int id, String nama, double harga, int durasi, String deskripsi) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.durasi = durasi;
        this.deskripsi = deskripsi;
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

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public int getDurasi() {
        return durasi;
    }

    public void setDurasi(int durasi) {
        this.durasi = durasi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", nama='" + nama + '\'' +
                ", harga=" + harga +
                ", durasi=" + durasi +
                '}';
    }
}
