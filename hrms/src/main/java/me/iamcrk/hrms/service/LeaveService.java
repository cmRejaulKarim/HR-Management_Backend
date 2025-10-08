package me.iamcrk.hrms.service;

import me.iamcrk.hrms.dto.LeaveDTO;
import me.iamcrk.hrms.entity.DepartmentHead;
import me.iamcrk.hrms.entity.Employee;
import me.iamcrk.hrms.entity.Leave;
import me.iamcrk.hrms.entity.Status;
import me.iamcrk.hrms.repository.IDepartmentHeadRepo;
import me.iamcrk.hrms.repository.IEmployeeRepo;
import me.iamcrk.hrms.repository.ILeaveRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveService {

    @Autowired
    private ILeaveRepo leaveRepo;
    @Autowired
    private IEmployeeRepo employeeRepo;
    @Autowired
    private IDepartmentHeadRepo departmentHeadRepo;


    // -------------------------------
    // Add Leave
    // -------------------------------
    public Leave addLeave(Leave leave, String email) {
        Employee employee = employeeRepo.findByUserEmail(email);

        leave.setEmployee(employee);
        leave.setTotalLeaveDays(calculateTotalLeaveDays(leave.getStartDate(), leave.getEndDate()));
        leave.setRequestedDate(LocalDate.now());
        leave.setStatus(Status.PENDING);

        return leaveRepo.save(leave);
    }

    private int calculateTotalLeaveDays(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) throw new IllegalArgumentException("Start and end date required");
        return (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }

    // -------------------------------
    // Get leaves by employee
    // -------------------------------
    public List<Leave> getByEmployeeId(Long empId) {
        return leaveRepo.findByEmployeeIdOrderByRequestedDateDesc(empId);
    }

    // -------------------------------
    // Get leaves by department head
    // -------------------------------
    public List<Leave> getLeavesForDeptHead(Long deptHeadUserId) {
        DepartmentHead dh = departmentHeadRepo.findByUserIdAndActiveTrue(deptHeadUserId)
                .orElseThrow(() -> new RuntimeException("Active DepartmentHead not found"));

        // Get all leaves in the department
        List<Leave> leaves = leaveRepo.findAllLeavesByDepartment(dh.getDepartment().getId());

        // Filter out the DeptHead's own leaves
        leaves.removeIf(l -> l.getEmployee().getUser().getId().equals(deptHeadUserId));

        return leaves;
    }

    public List<Leave> getAllDeptHeadLeaves() {
        return leaveRepo.findAllDeptHeadLeaves(); // unchanged
    }

    // -------------------------------
    // Get all leaves
    // -------------------------------
    public List<Leave> getAllLeaves() {
        return leaveRepo.findAllByOrderByRequestedDateDesc();
    }

    // -------------------------------
    // Get leaves by status
    // -------------------------------
    public List<Leave> getLeavesByStatus(Status status) {
        return leaveRepo.findByStatusOrderByRequestedDateDesc(status);
    }

    // -------------------------------
    // Approve / Reject leave
    // -------------------------------
    public Leave approveLeave(Long id) {
        Leave leave = leaveRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        if (leave.getStatus() == Status.PENDING) {
            leave.setStatus(Status.APPROVED);
            leave.setApprovalDate(LocalDate.now());
            return leaveRepo.save(leave);
        }
        return leave;
    }

    public Leave rejectLeave(Long id) {
        Leave leave = leaveRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        if (leave.getStatus() == Status.PENDING) {
            leave.setStatus(Status.REJECTED);
            leave.setApprovalDate(LocalDate.now());
            return leaveRepo.save(leave);
        }
        return leave;
    }

    // -------------------------------
    // Monthly approved leave days
    // -------------------------------
    public int getMonthlyApprovedLeaveDays(Long empId, int month, int year) {
        Integer days = leaveRepo.getMonthlyApprovedLeaveDays(empId, month, year);
        return days == null ? 0 : days;
    }

    // -------------------------------
    // Yearly approved leave days
    // -------------------------------
    public int getYearlyApprovedLeaveDays(Long empId, int year) {
        Integer days = leaveRepo.getYearlyApprovedLeaveDays(empId, year);
        return days == null ? 0 : days;
    }

    // -------------------------------
    // Get approved leaves for month (for salary)
    // -------------------------------
    public List<Leave> getApprovedLeaveForMonth(Long empId, int month, int year) {
        return leaveRepo.findApprovedLeaveForMonth(empId, month, year);
    }

//    //for salary
//    public List<Leave> getMonthlyApprovedLeaveDetails(Long empId, int month, int year) {
//        return leaveRepo.findApprovedLeaveForMonth(empId, month, year);
//    }
//    public int getMonthlyApprovedLeaveDays(Long empId, int month, int year) {
//        List<Leave> leaves = leaveRepo.findApprovedLeaveForMonth(empId, month, year);
//        return leaves.stream()
//                .mapToInt(Leave::getTotalLeaveDays)
//                .sum();
//    }
//
//    //for salary
//
//    // Get All Leaves By Employee ID
////    public List<LeaveDTO> getByEmployeeId(Long empId) {
////        List<Leave> leaves = leaveRepo.findByEmployeeId(empId);
////
////        return leaves.stream()
////                .map(LeaveDTO::new)
////                .collect(Collectors.toList());
////    }
//    public List<LeaveDTO> getByEmployeeId(Long empId) {
//        List<Leave> leaves = leaveRepo.findByEmployeeIdOrderByRequestedDateDesc(empId);
//        return leaves.stream()
//                .map(LeaveDTO::new)
//                .collect(Collectors.toList());
//    }
//
//    // Get All Leaves
////    public List<LeaveDTO> getAll() {
////        List<Leave> leaves = leaveRepo.findAll();
////        return leaves.stream()
////                .map(LeaveDTO::new)
////                .collect(Collectors.toList());
////    }
//    public List<LeaveDTO> getAll() {
//        List<Leave> leaves = leaveRepo.findAllByOrderByRequestedDateDesc();
//        return leaves.stream()
//                .map(LeaveDTO::new)
//                .collect(Collectors.toList());
//    }
//
//    //add leaves
//    public Leave addLeave(Leave leave, String email) {
//        Employee employee = employeeRepo.findByUserEmail(email);
//
//        leave.setEmployee(employee);
//
//        int days = totalLeaveDays(leave.getStartDate(), leave.getEndDate());
//        leave.setTotalLeaveDays(days);
//        leave.setRequestedDate(LocalDate.now());
//        leave.setStatus(Status.PENDING);
//
//        return leaveRepo.save(leave);
//    }
//
//    //calculate leaves
//    private int totalLeaveDays(LocalDate startDate, LocalDate endDate) {
//        if (startDate == null || endDate == null) {
//            throw new IllegalArgumentException("Start date and end date cannot be null");
//        }
//        return (int) (java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1);
//    }
////    public List<LeaveDTO> getLeaveByEmployeeId(Long empId) {
////
////    }
//
//    // Get All Leaves by Status
////    public List<Leave> getLeavesByStatus(Status status) {
////        return leaveRepo.findByStatus(status);
////    }
//    public List<Leave> getAllDeptHeadLeaves() {
//        return leaveRepo.findAllDeptHeadLeaves();
//    }
//
//    public List<Leave> getLeavesByStatus(Status status) {
//        return leaveRepo.findByStatusOrderByRequestedDateDesc(status);
//    }
//
//    // Approve Leave
//    public Leave approveLeave(Long id) {
//        Leave leave = leaveRepo.findById(id)
//                .orElseThrow(() -> new RuntimeException("Leave not found with ID: " + id));
//
//        if (leave.getStatus().equals(Status.PENDING)) {
//            leave.setStatus(Status.APPROVED);
//            leave.setApprovalDate(LocalDate.now());
//            return leaveRepo.save(leave);
//        } else {
//            return leave;
//        }
//    }
//
//    // Reject Leave
//    public Leave rejectLeave(Long id) {
//        Leave leave = leaveRepo.findById(id)
//                .orElseThrow(() -> new RuntimeException("Leave not found with ID: " + id));
//
//        if (leave.getStatus().equals(Status.PENDING)) {
//            leave.setStatus(Status.REJECTED);
//            leave.setApprovalDate(LocalDate.now());
//            return leaveRepo.save(leave);
//        } else {
//            return leave;
//        }
//    }
//
//    public int getMonthlyApprovedLeave(Long empId, int month, int year) {
//        List<Leave> approvedLeaves = leaveRepo.findByEmployeeIdAndStatus(empId, Status.APPROVED);
//
//        LocalDate monthStart = LocalDate.of(year, month, 1);
//        LocalDate monthEnd = monthStart.withDayOfMonth(monthStart.lengthOfMonth());
//
//        return approvedLeaves.stream()
//                .mapToInt(leave -> {
//                    LocalDate start = leave.getStartDate();
//                    LocalDate end = leave.getEndDate();
//
//                    if (start.isBefore(monthStart)) {
//                        start = monthStart;
//                    }
//                    if (end.isAfter(monthEnd) && !monthEnd.isBefore(monthStart)) {
//                        return (int) ChronoUnit.DAYS.between(start, end);
//                    }
//                    return 0;
//                }).sum();
//    }
//
//    public int getYearlyApprovedLeave(Long empId, int year) {
//        List<Leave> approvedLeaves = leaveRepo.findByEmployeeIdAndStatus(empId, Status.APPROVED);
//
//        LocalDate yearStart = LocalDate.of(year, 1, 1);
//        LocalDate yearEnd = LocalDate.of(year, 12, 31);
//
//        return approvedLeaves.stream()
//                .mapToInt(leave -> {
//                    LocalDate start = leave.getStartDate();
//                    LocalDate end = leave.getEndDate();
//
//                    if (start.isBefore(yearStart)) {
//                        start = yearStart;
//                    }
//                    if (end.isAfter(yearEnd)) {
//                        end = yearEnd;
//                    }
//                    if (!start.isAfter(yearEnd) && !end.isBefore(yearStart)) {
//                        return (int) ChronoUnit.DAYS.between(start, end) + 1;
//                    }
//                    return 0;
//                })
//                .sum();
//    }
}
