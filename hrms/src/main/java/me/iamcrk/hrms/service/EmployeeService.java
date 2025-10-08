package me.iamcrk.hrms.service;

import me.iamcrk.hrms.dto.EmployeeDTO;
import me.iamcrk.hrms.entity.Employee;
import me.iamcrk.hrms.repository.IEmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//log
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//log

@Service
public class EmployeeService {
    @Autowired
    private IEmployeeRepo employeeRepo;

    public List<Employee> getAll() {
        return employeeRepo.findAll();
    }

    public Optional<Employee> getById(Long id) {
        return employeeRepo.findById(id);
    }
//    public Employee getByEmpId(Long empId) {
//        return employeeRepo.findById(empId);
//    }

    public Employee save(Employee employee) {
        return employeeRepo.save(employee);
    }

    public void deleteById(Long id) {
        employeeRepo.deleteById(id);
    }

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);


    public Employee getProfileByUserId(long userId) {
        return employeeRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Employee Not Found."));
    }

    public EmployeeDTO getProfileByUserIdFromDto(long userId) {
        Employee employee = employeeRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Employee Not Found."));

//        log.info("=========================================");
//        log.info("Fetching employees by userId: {}", userId);
//        log.info("Fetching employees: {}", employee.getEmail());
//        log.info("=========================================");

        // Convert entity to DTO
        return new EmployeeDTO(employee);
    }


    public List<Employee> getAllByDepartmentId(long userId) {
        // fetch employee by userId instead of employeeId
        Employee emp = employeeRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Employee not found for userId: " + userId));

        if (emp.getDepartment() == null) {
            throw new RuntimeException("Employee " + emp.getName() + " has no department assigned.");
        }

        Long departmentId = emp.getDepartment().getId();
//        log.info("=========================================");
//        log.info("Fetching List<Employee> getAllByDepartmentId by userId: {}", userId);
//        log.info("Fetching List<Employee> getAllByDepartmentId by empid: {}", emp.getId());
//        log.info("Fetching List<Employee> getAllByDepartmentId by Email: {}",emp.getEmail());
//        log.info("Fetching List<Employee> getAllByDepartmentId by departmentId: {}", departmentId);
//        log.info("Fetching List<Employee> getAllByDepartmentId by departmentId: {}", emp.getDepartment());
//        log.info("==========================================");
        return employeeRepo.findEmployeesByDepartmentWithAllowedRoles(departmentId);
    }


    public List<Employee> getAllEmpByDepartmentId(long userId) {
        // fetch employee by userId instead of employeeId
        Employee emp = employeeRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Employee not found for userId: " + userId));

        if (emp.getDepartment() == null) {
            throw new RuntimeException("Employee " + emp.getName() + " has no department assigned.");
        }

        Long departmentId = emp.getDepartment().getId();

        return employeeRepo.findEmployeesByDepartmentWithRoleEmp(departmentId);
    }

//    public List<Employee> getAllByDepartmentId(long userId) {
//        Employee emp = employeeRepo.findById(userId)
//                .orElseThrow(() -> new RuntimeException("Employee not found"));
//
//        if (emp.getDepartment() == null) {
//            throw new RuntimeException("This employee has no department assigned");
//        }
//
//        long departmentId = emp.getDepartment().getId();
//
//        return employeeRepo.findByDepartmentId(departmentId);
//    }

    public Employee updateSalary(Long empId, EmployeeDTO dto) {
        Employee emp = employeeRepo.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // update only salary-related fields
        emp.setBasicSalary(dto.getBasicSalary());
        emp.setAllowance(dto.getAllowance());

        return employeeRepo.save(emp);
    }

}
