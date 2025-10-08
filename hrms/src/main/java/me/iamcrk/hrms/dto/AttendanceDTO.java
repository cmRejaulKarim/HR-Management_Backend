package me.iamcrk.hrms.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import me.iamcrk.hrms.entity.Attendance;
import me.iamcrk.hrms.entity.Employee;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceDTO {
    private Long id;
    private Long empId;
    private LocalDate date;
    private LocalTime checkIn;
    private LocalTime checkOut;
    private Double totalWorkingTime; // auto calculate
    private Long overtimeHours;     // auto if > 1 hr
    private Integer lateCount;
    private boolean absent;


    public AttendanceDTO(Attendance attendance) {
        id = attendance.getId();
        empId = attendance.getEmployee().getId();
        date = attendance.getDate();
        checkIn = attendance.getCheckIn();
        checkOut = attendance.getCheckOut();
        totalWorkingTime = attendance.getTotalWorkingTime();
        overtimeHours = attendance.getOvertimeHours();
        lateCount = attendance.getLateCount();
        absent = attendance.isAbsent();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
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
