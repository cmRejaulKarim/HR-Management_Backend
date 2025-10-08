package me.iamcrk.hrms.service;

import me.iamcrk.hrms.entity.*;
import me.iamcrk.hrms.repository.IAdvanceSalaryRepo;
import me.iamcrk.hrms.repository.IEmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class AdvanceSalaryService {

    @Autowired
    private IAdvanceSalaryRepo advanceSalaryRepo;
    @Autowired
    private IEmployeeRepo employeeRepo;

    public AdvanceSalary addAdvanceRequest(Long empId, Double amount, String reason) {
        Employee employee = employeeRepo.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // 1. Check if employee already requested this month
        LocalDate now = LocalDate.now();
        LocalDate firstDayOfMonth = now.withDayOfMonth(1);
        LocalDate lastDayOfMonth = now.withDayOfMonth(now.lengthOfMonth());

        boolean alreadyRequested = advanceSalaryRepo.existsByEmployeeIdAndRequestDateBetween(
                empId, firstDayOfMonth, lastDayOfMonth
        );

        if (alreadyRequested) {
            throw new RuntimeException("You have already requested an advance for this month.");
        }

        // 2. Check if amount exceeds salary
        // may be it will be better if amount > basicSalary
        if (amount > employee.getBasicSalary()) {
            throw new RuntimeException("Requested amount exceeds your monthly salary.");
        }

        // 3. Create and save advance request
        AdvanceSalary advance = new AdvanceSalary();
        advance.setEmployee(employee);
        advance.setAmount(amount);
        advance.setReason(reason);
        advance.setRequestDate(LocalDate.now());
        advance.setStatus(Status.PENDING);

        return advanceSalaryRepo.save(advance);
    }




//    public AdvanceSalary addAdvanceRequest(Long empId, Double amount, String reason) {
//        Employee employee = employeeRepo.findById(empId)
//                .orElseThrow(() -> new RuntimeException("Employee not found"));
//
//        AdvanceSalary advance = new AdvanceSalary();
//        advance.setEmployee(employee);
//        advance.setAmount(amount);
//        advance.setReason(reason);
//        advance.setRequestDate(LocalDate.now()); // request date is today
//        advance.setStatus(Status.PENDING); // default status
//
//        return advanceSalaryRepo.save(advance);
//    }

    public List<AdvanceSalary> getAllAdvanceSalary() {
        List<AdvanceSalary> allAdvanceSalary = advanceSalaryRepo.findAllByOrderByIdDesc();
        return allAdvanceSalary;
    }

    public AdvanceSalary approveAdvance(Long id) {
        AdvanceSalary advance = advanceSalaryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found with ID: " + id));

        if (advance.getStatus() == Status.PENDING) {
            advance.setStatus(Status.APPROVED);
            advance.setApprovalDate(LocalDate.now());
            return advanceSalaryRepo.save(advance);
        }
        return advance;
    }

    public AdvanceSalary rejectAdvance(Long id) {
        AdvanceSalary advance = advanceSalaryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found with ID: " + id));

        if (advance.getStatus() == Status.PENDING) {
            advance.setStatus(Status.REJECTED);
            advance.setApprovalDate(LocalDate.now());
            return advanceSalaryRepo.save(advance);
        }
        return advance;
    }
    /**
     * View all advance salary requests for an employee in descending order
     */
    public List<AdvanceSalary> viewAdvanceRequests(Long empId) {
        Employee employee = employeeRepo.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return advanceSalaryRepo.findAllByEmployeeIdOrderByRequestDateDesc(empId);
    }

    public AdvanceSalary getApprovedAdvanceForDate(Long empId, LocalDate date) {
        return advanceSalaryRepo.findFirstByEmployeeIdAndStatusAndRequestDate(
                empId, Status.APPROVED, date
        );
    }

    public List<AdvanceSalary> getAllAdvancesInPeriod(Long empId, LocalDate startDate, LocalDate endDate) {
        return advanceSalaryRepo.findAllByEmployeeIdAndRequestDateBetweenOrderByRequestDateDesc(empId, startDate, endDate);
    }

}
