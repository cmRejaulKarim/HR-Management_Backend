package me.iamcrk.hrms.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class AdvanceSalary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    private Double amount;
    private String reason;
    private LocalDate requestDate;

    @Enumerated(EnumType.STRING)
    private Status status; // PENDING, APPROVED, REJECTED
    private LocalDate approvalDate;

    public AdvanceSalary() {
    }

    public AdvanceSalary(Long id, Employee employee, Double amount, String reason, LocalDate requestDate, Status status, LocalDate approvalDate) {
        this.id = id;
        this.employee = employee;
        this.amount = amount;
        this.reason = reason;
        this.requestDate = requestDate;
        this.status = status;
        this.approvalDate = approvalDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }
}
