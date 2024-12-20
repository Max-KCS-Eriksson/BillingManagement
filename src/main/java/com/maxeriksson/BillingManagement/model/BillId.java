package com.maxeriksson.BillingManagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.LocalDateTime;

/** BillId */
@Embeddable
public class BillId implements Serializable {

    @Column(name = "customerId")
    private SocialSecurityNumber customerId;

    @Column(name = "bookedTime")
    private LocalDateTime bookedTime;

    public BillId() {} // Required by JPA

    public BillId(SocialSecurityNumber customerId, LocalDateTime bookedTime) {
        this.customerId = customerId;
        this.bookedTime = bookedTime;
    }

    public SocialSecurityNumber getCustomerId() {
        return customerId;
    }

    public void setCustomerId(SocialSecurityNumber customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getBookedTime() {
        return bookedTime;
    }

    public void setBookedTime(LocalDateTime bookedTime) {
        this.bookedTime = bookedTime;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
        result = prime * result + ((bookedTime == null) ? 0 : bookedTime.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        BillId other = (BillId) obj;
        if (customerId == null) {
            if (other.customerId != null) return false;
        } else if (!customerId.equals(other.customerId)) return false;
        if (bookedTime == null) {
            if (other.bookedTime != null) return false;
        } else if (!bookedTime.equals(other.bookedTime)) return false;
        return true;
    }
}
