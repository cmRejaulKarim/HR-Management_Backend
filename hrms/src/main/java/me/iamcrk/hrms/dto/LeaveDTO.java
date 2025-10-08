package me.iamcrk.hrms.dto;

import me.iamcrk.hrms.entity.Employee;
import me.iamcrk.hrms.entity.Leave;
import me.iamcrk.hrms.entity.Status;

import java.time.LocalDate;

public class LeaveDTO {
    private Long id;

    private Employee employee;

    private LocalDate startDate;
    private LocalDate endDate;
    private int totalLeaveDays;

    private String reason;
    private LocalDate requestedDate;
    private Status status; // PENDING, APPROVED, REJECTED

    private LocalDate approvalDate;

    public LeaveDTO() {
    }



    public LeaveDTO(Leave leave) {
        this.id = leave.getId();
        this.employee = leave.getEmployee();
        this.startDate = leave.getStartDate();
        this.endDate = leave.getEndDate();
        this.totalLeaveDays = leave.getTotalLeaveDays();
        this.reason = leave.getReason();
        this.requestedDate = leave.getRequestedDate();
        this.status = leave.getStatus();
        this.approvalDate = leave.getApprovalDate();
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getTotalLeaveDays() {
        return totalLeaveDays;
    }

    public void setTotalLeaveDays(int totalLeaveDays) {
        this.totalLeaveDays = totalLeaveDays;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDate getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(LocalDate requestedDate) {
        this.requestedDate = requestedDate;
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
