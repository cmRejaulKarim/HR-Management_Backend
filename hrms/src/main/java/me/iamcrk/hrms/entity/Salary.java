package me.iamcrk.hrms.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.YearMonth;

@Entity
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    private LocalDate month;

    private Double basicSalary;    // from employee table
    private Double allowance;        // from employee table
    private Double overtimeSalary; // auto calculate
    private Double totalSalary;

    @OneToOne
    @JoinColumn(name = "advanceSalary_id")
    private AdvanceSalary advanceDeduction;


    private int totalMonthlyLeave;// leave penalty or other cuts
    private Double leavePenalty;
    private Double netPay;
    private LocalDate submitDate;

    public Salary() {
    }

    public Salary(Long id, Employee employee, LocalDate month, Double basicSalary, Double allowance, Double overtimeSalary, Double totalSalary, AdvanceSalary advanceDeduction, int totalMonthlyLeave, Double leavePenalty, Double netPay, LocalDate submitDate) {
        this.id = id;
        this.employee = employee;
        this.month = month;
        this.basicSalary = basicSalary;
        this.allowance = allowance;
        this.overtimeSalary = overtimeSalary;
        this.totalSalary = totalSalary;
        this.advanceDeduction = advanceDeduction;
        this.totalMonthlyLeave = totalMonthlyLeave;
        this.leavePenalty = leavePenalty;
        this.netPay = netPay;
        this.submitDate = submitDate;
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

    public LocalDate getMonth() {
        return month;
    }

    public void setMonth(LocalDate month) {
        this.month = month;
    }

    public Double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public Double getAllowance() {
        return allowance;
    }

    public void setAllowance(Double allowance) {
        this.allowance = allowance;
    }

    public Double getOvertimeSalary() {
        return overtimeSalary;
    }

    public void setOvertimeSalary(Double overtimeSalary) {
        this.overtimeSalary = overtimeSalary;
    }

    public Double getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(Double totalSalary) {
        this.totalSalary = totalSalary;
    }

    public AdvanceSalary getAdvanceDeduction() {
        return advanceDeduction;
    }

    public void setAdvanceDeduction(AdvanceSalary advanceDeduction) {
        this.advanceDeduction = advanceDeduction;
    }

    public int getTotalMonthlyLeave() {
        return totalMonthlyLeave;
    }

    public void setTotalMonthlyLeave(int totalMonthlyLeave) {
        this.totalMonthlyLeave = totalMonthlyLeave;
    }

    public Double getLeavePenalty() {
        return leavePenalty;
    }

    public void setLeavePenalty(Double leavePenalty) {
        this.leavePenalty = leavePenalty;
    }

    public Double getNetPay() {
        return netPay;
    }

    public void setNetPay(Double netPay) {
        this.netPay = netPay;
    }

    public LocalDate getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(LocalDate submitDate) {
        this.submitDate = submitDate;
    }
}
