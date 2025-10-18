package me.iamcrk.hrms.restcontroller;

import me.iamcrk.hrms.entity.Leave;
import me.iamcrk.hrms.entity.Status;
import me.iamcrk.hrms.entity.User;
import me.iamcrk.hrms.repository.IUserRepo;
import me.iamcrk.hrms.service.EmployeeService;
import me.iamcrk.hrms.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave/")
public class LeaveRestController {

    @Autowired
    private LeaveService leaveService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private IUserRepo userRepo;

    // -------------------------------
    // Add leave
    // -------------------------------
    @PostMapping("add")
    public ResponseEntity<Leave> addLeave(@RequestBody Leave leave, Authentication authentication) {
        String email = authentication.getName();
        Leave savedLeave = leaveService.addLeave(leave, email);
        return ResponseEntity.ok(savedLeave);
    }

    // -------------------------------
    // Get all leaves
    // -------------------------------
    @GetMapping("all")
    public ResponseEntity<List<Leave>> getAllLeaves() {
        return ResponseEntity.ok(leaveService.getAllLeaves());
    }

    // -------------------------------
    // Get leaves by employee (logged-in)
    // -------------------------------
    @GetMapping("byEmp")
    public ResponseEntity<List<Leave>> getLeavesByEmployee(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Long empId = employeeService.getProfileByUserId(user.getId()).getId();
        return ResponseEntity.ok(leaveService.getByEmployeeId(empId));
    }

    // -------------------------------
// Get leaves by department head (DeptHead view)
// -------------------------------
    @GetMapping("byDept")
    public ResponseEntity<List<Leave>> getLeavesByDept(Authentication authentication) {
        String email = authentication.getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch leaves of employees in DeptHead's department, excluding DeptHead's own leaves
        List<Leave> leaves = leaveService.getLeavesForDeptHead(user.getId());

        return ResponseEntity.ok(leaves);
    }

    // -------------------------------
// Get leaves of all DeptHeads (Admin view)
// -------------------------------
    @GetMapping("ofDeptHeads")
    public ResponseEntity<List<Leave>> getDeptHeadLeaves() {
        List<Leave> leaves = leaveService.getAllDeptHeadLeaves();
        return ResponseEntity.ok(leaves);
    }


    // -------------------------------
    // Get leaves by status
    // -------------------------------
    @GetMapping("status/{status}")
    public ResponseEntity<List<Leave>> getLeavesByStatus(@PathVariable Status status) {
        return ResponseEntity.ok(leaveService.getLeavesByStatus(status));
    }

    // -------------------------------
    // Approve / Reject leave
    // -------------------------------
    @PutMapping("{id}/approve")
    public ResponseEntity<Leave> approveLeave(@PathVariable Long id) {
        return ResponseEntity.ok(leaveService.approveLeave(id));
    }

    @PutMapping("{id}/reject")
    public ResponseEntity<Leave> rejectLeave(@PathVariable Long id) {
        return ResponseEntity.ok(leaveService.rejectLeave(id));
    }

    // -------------------------------
    // Get monthly / yearly approved leave days
    // -------------------------------
    @GetMapping("approved/monthly")
    public ResponseEntity<Integer> getMonthlyApprovedLeaveDays(
            @RequestParam Long empId,
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(leaveService.getMonthlyApprovedLeaveDays(empId, month, year));
    }

    @GetMapping("approved/yearly")
    public ResponseEntity<Integer> getYearlyApprovedLeaveDays(
            @RequestParam Long empId,
            @RequestParam int year) {
        return ResponseEntity.ok(leaveService.getYearlyApprovedLeaveDays(empId, year));
    }

//    @PostMapping("add")
//    public ResponseEntity<Leave> addLeaves(@RequestBody Leave leave, Authentication authentication) {
//        String email = authentication.getName();  // Logged-in user's email
//        Leave leaves = leaveService.addLeave(leave, email);
//        return ResponseEntity.ok(leaves);
//    }
//
//    @GetMapping("all")
//    public ResponseEntity<List<LeaveDTO>> getAllLeaves() {
//        List<LeaveDTO> savedLeaves = leaveService.getAll();
//        return ResponseEntity.ok(savedLeaves);
//    }
//
//    @GetMapping("byEmp")
//    public ResponseEntity<List<LeaveDTO>> getLeavesByEmployee(Authentication authentications) {
//        String email = authentications.getName();
//
//        User users = userRepo.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
//        Employee employees = employeeService.getProfileByUserId(users.getId());
//
//        List<LeaveDTO> leaves = leaveService.getByEmployeeId(employees.getId());
//
//        return ResponseEntity.ok(leaves);
//    }
//
//    @GetMapping("byEmp")
//    public ResponseEntity<List<Leave>> getLeavesByEmployee(Authentication authentication) {
//        String email = authentication.getName();
//
//        User user = userRepo.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
//        Employee employee = employeeService.getProfileByUserId(user.getId());
//
//        List<Leave> leaves = leaveService.getByEmployeeId(employee.getId());
//
//        return ResponseEntity.ok(leaves);
//    }
//
//
//    @GetMapping("/deptHead")
//    public ResponseEntity<List<Leave>> getDepartmentHeadLeaves() {
//        return ResponseEntity.ok(leaveService.getAllDeptHeadLeaves());
//    }
//
//    @GetMapping("byDept")
//    public ResponseEntity<List<LeaveDTO>> getLeavesByDept(Authentication authentications) {
//        String email = authentications.getName();
//
//        User users = userRepo.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
//
//        // 1. Get all employees of Dept Head's department
//        List<Employee> employees = employeeService.getAllEmpByDepartmentId(users.getId());
//
//
//        // 2. Collect leaves of all employees
//        List<LeaveDTO> leaves = employees.stream()
//                .flatMap(emp -> leaveService.getByEmployeeId(emp.getId()).stream())
//                .toList();
//
//        return ResponseEntity.ok(leaves);
//    }
//
//        @GetMapping("byDept")
//    public ResponseEntity<List<Leave>> getLeavesByDept(Authentication authentication) {
//        String email = authentication.getName();
//
//        User user = userRepo.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
//
//        // 1. Get all employees of Dept Head's department
//        List<Employee> employees = employeeService.getAllEmpByDepartmentId(user.getId());
//
//
//        // 2. Collect leaves of all employees
//        List<Leave> leaves = employees.stream()
//                .flatMap(emp -> leaveService.getByEmployeeId(emp.getId()).stream())
//                .toList();
//
//        return ResponseEntity.ok(leaves);
//    }
//
//
//    @GetMapping("status/{status}")
//    public List<Leave> getByStatus(@PathVariable Status status) {
//        return leaveService.getLeavesByStatus(status);
//    }
//
//    @PutMapping("{id}/approve")
//    public Leave approve(@PathVariable Long id) {
//        return leaveService.approveLeave(id);
//    }
//
//    @PutMapping("{id}/reject")
//    public Leave reject(@PathVariable Long id) {
//        return leaveService.rejectLeave(id);
//    }
//
//    @GetMapping("approved/monthly")
//    public ResponseEntity<Integer> getMonthlyApprovedLeaveDays(
//            @RequestParam Long empId,
//            @RequestParam int month,
//            @RequestParam int year) {
//        int totalLeaveDays = leaveService.getMonthlyApprovedLeaveDays(empId, month, year);
//        return ResponseEntity.ok(totalLeaveDays);
//    }
//
//    @GetMapping("approved/yearly")
//    public ResponseEntity<Integer> getYearlyApprovedLeaveDays(
//            @RequestParam Long empId,
//            @RequestParam int year) {
//        int totalLeaveDays = leaveService.calculateYearlyApprovedLeaveDays(empId, year);
//        return ResponseEntity.ok(totalLeaveDays);
//    }


}
