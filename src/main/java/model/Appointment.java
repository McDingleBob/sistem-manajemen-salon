package model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Model class untuk Appointment (Jadwal) Salon
 */
public class Appointment {
    private int id;
    private Customer customer;
    private Service service;
    private Employee employee;
    private LocalDate tanggal;
    private LocalTime waktu;
    private String status; // Pending, Confirmed, Completed, Cancelled
    private String catatan;

    public Appointment() {}

    public Appointment(int id, Customer customer, Service service, Employee employee, LocalDate tanggal, LocalTime waktu, String status, String catatan) {
        this.id = id;
        this.customer = customer;
        this.service = service;
        this.employee = employee;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.status = status;
        this.catatan = catatan;
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

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public LocalTime getWaktu() {
        return waktu;
    }

    public void setWaktu(LocalTime waktu) {
        this.waktu = waktu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", customer=" + (customer != null ? customer.getNama() : "null") +
                ", service=" + (service != null ? service.getNama() : "null") +
                ", tanggal=" + tanggal +
                ", waktu=" + waktu +
                ", status='" + status + '\'' +
                '}';
    }
}
