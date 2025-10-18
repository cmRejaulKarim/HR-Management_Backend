package me.iamcrk.hrms.dto;

import me.iamcrk.hrms.entity.AdvanceSalary;
import me.iamcrk.hrms.entity.Bonus;
import me.iamcrk.hrms.entity.Employee;

import java.time.YearMonth;

public class SalaryDTO {

    private Long id;
    private Employee employee;
    private YearMonth month;
    private Double basicSalary;    // from employee table
    private Double allowance;      // from employee table
    private Bonus bonus;          // from employee table
    private Double overtimeSalary; // auto calculate
    private Double totalSalary;
    private AdvanceSalary advanceDeduction;     // leave penalty or other cuts
    private Double leavePenalty;
    private Double netPay;

    public SalaryDTO() {
    }

    public SalaryDTO(Long id, Employee employee, YearMonth month, Double basicSalary, Double allowance, Bonus bonus, Double overtimeSalary, Double totalSalary, AdvanceSalary advanceDeduction, Double leavePenalty, Double netPay) {
        this.id = id;
        this.employee = employee;
        this.month = month;
        this.basicSalary = basicSalary;
        this.allowance = allowance;
        this.bonus = bonus;
        this.overtimeSalary = overtimeSalary;
        this.totalSalary = totalSalary;
        this.advanceDeduction = advanceDeduction;
        this.leavePenalty = leavePenalty;
        this.netPay = netPay;
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

    public YearMonth getMonth() {
        return month;
    }

    public void setMonth(YearMonth month) {
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

    public Bonus getBonus() {
        return bonus;
    }

    public void setBonus(Bonus bonus) {
        this.bonus = bonus;
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
}
