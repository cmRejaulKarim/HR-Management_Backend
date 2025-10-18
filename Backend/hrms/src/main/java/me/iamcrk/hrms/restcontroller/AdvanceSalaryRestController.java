package me.iamcrk.hrms.restcontroller;

import me.iamcrk.hrms.entity.AdvanceSalary;
import me.iamcrk.hrms.entity.Employee;
import me.iamcrk.hrms.repository.IEmployeeRepo;
import me.iamcrk.hrms.service.AdvanceSalaryService;
import me.iamcrk.hrms.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
//log

@RestController
@RequestMapping("/api/advanceSalary")
public class AdvanceSalaryRestController {


    @Autowired
    private AdvanceSalaryService advanceSalaryService;
    @Autowired
    private IEmployeeRepo employeeRepo;

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    // Add new advance request
    @PostMapping("/request")
    public AdvanceSalary addAdvanceRequest(
            @RequestParam Double amount,
            @RequestParam String reason,
            Authentication authentication
    ) {
        // Get logged-in user's email
        String email = authentication.getName();

        // Fetch employee by email
        Employee employee = employeeRepo.findByUserEmail(email);

        // Call service method
        return advanceSalaryService.addAdvanceRequest(employee.getId(), amount, reason);
    }

    // View all advance requests for an employee in descending order
    @GetMapping("/ByEmp")
    public ResponseEntity<List<AdvanceSalary>> viewAdvanceRequestsByEmp(Authentication authentication) {
        String email = authentication.getName();
        Employee employee = employeeRepo.findByUserEmail(email);
        List<AdvanceSalary> advanceSalaries = advanceSalaryService.viewAdvanceRequests(employee.getId());
        return ResponseEntity.ok(advanceSalaries);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AdvanceSalary>> viewAllAdvanceRequests() {
        List<AdvanceSalary> allAdvanceSalary = advanceSalaryService.getAllAdvanceSalary();

        log.info("=========================================");
//        log.info("Fetching employees by userId: {}", userId);
        log.info("Fetching allAdvanceSalary in api: {}", allAdvanceSalary);
        log.info("=========================================");

        return ResponseEntity.ok(allAdvanceSalary);
    }

    // View all advance requests for an employee in descending order
    @GetMapping("/employee/{empId}")
    public ResponseEntity<List<AdvanceSalary>> viewAdvanceRequests(@PathVariable Long empId) {
        List<AdvanceSalary> allAdvanceSalary = advanceSalaryService.viewAdvanceRequests(empId);
        return ResponseEntity.ok(allAdvanceSalary);
    }

    @PutMapping("{id}/approve")
    public AdvanceSalary approve(@PathVariable Long id) {

        return advanceSalaryService.approveAdvance(id);
    }

    @PutMapping("{id}/reject")
    public AdvanceSalary reject(@PathVariable Long id) {

        return advanceSalaryService.rejectAdvance(id);
    }

    // Optional: Get approved advance for specific date
    @GetMapping("/employee/{empId}/approved")
    public AdvanceSalary getApprovedAdvance(
            @PathVariable Long empId,
            @RequestParam String date // format: yyyy-MM-dd
    ) {
        LocalDate localDate = LocalDate.parse(date);
        return advanceSalaryService.getApprovedAdvanceForDate(empId, localDate);
    }

    // Optional: Get all advances in a period
    @GetMapping("/employee/{empId}/period")
    public List<AdvanceSalary> getAdvancesInPeriod(
            @PathVariable Long empId,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return advanceSalaryService.getAllAdvancesInPeriod(empId, start, end);
    }
}
