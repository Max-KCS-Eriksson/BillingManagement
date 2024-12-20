package com.maxeriksson.BillingManagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

/** Bill */
@Entity
@Table(name = "bills")
public class Bill {

    @EmbeddedId private BillId id;

    @MapsId("customerId")
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "customerDateOfBirth", referencedColumnName = "dateOfBirth"),
        @JoinColumn(name = "customerIdLastFour", referencedColumnName = "idLastFour"),
    })
    private Customer customer;

    // TODO:
    @ManyToOne()
    @JoinColumn(name = "service")
    private Service service;

    @Column(name = "hours")
    private int hours;

    @Column(name = "isPaid")
    private boolean isPaid;

    public Bill() {} // Required by JPA

    public Bill(BillId id, Service service, int hours) {
        this(id, service, hours, false);
    }

    public Bill(BillId id, Service service, int hours, boolean isPaid) {
        this.id = id;
        this.service = service;
        setHours(hours);
        this.isPaid = isPaid;
    }

    public BillId getId() {
        return id;
    }

    public void setId(BillId id) {
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

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    @Override
    public String toString() {
        return "Bill [customer="
                + id.getCustomerId()
                + ", booking="
                + id.getBookedTime()
                + ", service="
                + service
                + ", hours="
                + hours
                + ", isPaid="
                + isPaid
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Bill other = (Bill) obj;
        if (id == null) {
            if (other.id != null) return false;
        } else if (!id.equals(other.id)) return false;
        return true;
    }
}
