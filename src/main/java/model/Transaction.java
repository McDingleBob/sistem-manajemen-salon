package model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Model class untuk Transaction (Transaksi) Salon
 */
public class Transaction {
    private int id;
    private Customer customer;
    private List<Service> services;
    private Employee employee;
    private LocalDateTime tanggalWaktu;
    private double subtotal;
    private double diskon;
    private double pajak;
    private double total;
    private String metodePembayaran; // Cash, Debit, Credit, E-Wallet
    private String status; // Pending, Paid, Cancelled

    public Transaction() {}

    public Transaction(int id, Customer customer, List<Service> services, Employee employee, LocalDateTime tanggalWaktu, double subtotal, double diskon, double pajak, double total, String metodePembayaran, String status) {
        this.id = id;
        this.customer = customer;
        this.services = services;
        this.employee = employee;
        this.tanggalWaktu = tanggalWaktu;
        this.subtotal = subtotal;
        this.diskon = diskon;
        this.pajak = pajak;
        this.total = total;
        this.metodePembayaran = metodePembayaran;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDateTime getTanggalWaktu() {
        return tanggalWaktu;
    }

    public void setTanggalWaktu(LocalDateTime tanggalWaktu) {
        this.tanggalWaktu = tanggalWaktu;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getDiskon() {
        return diskon;
    }

    public void setDiskon(double diskon) {
        this.diskon = diskon;
    }

    public double getPajak() {
        return pajak;
    }

    public void setPajak(double pajak) {
        this.pajak = pajak;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", customer=" + (customer != null ? customer.getNama() : "null") +
                ", total=" + total +
                ", metodePembayaran='" + metodePembayaran + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
