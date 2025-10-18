package me.iamcrk.hrms.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    private LocalDate date;
    private LocalTime checkIn;
    private LocalTime checkOut;

    private Double totalWorkingTime; // auto calculate
    private Long overtimeHours;     // auto if > 1 hr
    private Integer lateCount;        // late tracking
    private boolean absent;

    public Attendance() {
    }

    public Attendance(Long id, Employee employee, LocalDate date, LocalTime checkIn, LocalTime checkOut, Double totalWorkingTime, Long overtimeHours, Integer lateCount, boolean absent) {
        this.id = id;
        this.employee = employee;
        this.date = date;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalWorkingTime = totalWorkingTime;
        this.overtimeHours = overtimeHours;
        this.lateCount = lateCount;
        this.absent = absent;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalTime checkOut) {
        this.checkOut = checkOut;
    }

    public Double getTotalWorkingTime() {
        return totalWorkingTime;
    }

    public void setTotalWorkingTime(Double totalWorkingTime) {
        this.totalWorkingTime = totalWorkingTime;
    }

    public Long getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(Long overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    public Integer getLateCount() {
        return lateCount;
    }

    public void setLateCount(Integer lateCount) {
        this.lateCount = lateCount;
    }

    public boolean isAbsent() {
        return absent;
    }

    public void setAbsent(boolean absent) {
        this.absent = absent;
    }
}
