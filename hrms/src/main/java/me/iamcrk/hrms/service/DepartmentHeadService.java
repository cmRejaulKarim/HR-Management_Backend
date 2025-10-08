package me.iamcrk.hrms.service;

import me.iamcrk.hrms.entity.*;
import me.iamcrk.hrms.repository.IDepartmentHeadRepo;
import me.iamcrk.hrms.repository.IEmployeeRepo;
import me.iamcrk.hrms.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class DepartmentHeadService {

    @Autowired
    private IDepartmentHeadRepo deptHeadRepo;
    @Autowired
    private IEmployeeRepo employeeRepo;
    @Autowired
    private IUserRepo userRepo;


    public String registerAsDepartmentHead(String email) {
        // 1. Find Employee by email
        Employee employee = employeeRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Employee not found with email: " + email));

        Department department = employee.getDepartment();
        User user = employee.getUser();

        // 2. Check if user has ever been a Department Head
        if (deptHeadRepo.findByUserId(user.getId()).isPresent()) {
            throw new RuntimeException("You are already registered (current or previous) as a Department Head!");
        }

        // 3. Check if department already has an active head
        if (deptHeadRepo.findByDepartmentAndActiveTrue(department).isPresent()) {
            throw new RuntimeException("Your department already has an active Department Head!");
        }

        // 4. Create new Department Head
        DepartmentHead deptHead = new DepartmentHead();
        deptHead.setUser(user);
        deptHead.setDepartment(department);
        deptHead.setAssignedDate(LocalDate.now());
        deptHead.setActive(true);
        deptHeadRepo.save(deptHead);

        // 5. Update User role
        user.setRole(Role.DEPARTMENT_HEAD);
        userRepo.save(user);

        return "Successfully registered as Department Head!";
    }


    public List<DepartmentHead> getAllDepartmentHeads() {
        return deptHeadRepo.findAll();
    }

    public List<DepartmentHead> getAllActiveDepartmentHeads() {
        return deptHeadRepo.findByActiveTrue();
    }

    public void endDepartmentHeadRole(Long deptHeadId) {
        DepartmentHead deptHead = deptHeadRepo.findById(deptHeadId)
                .orElseThrow(() -> new RuntimeException("Department Head not found"));

        deptHead.setActive(false);
        deptHead.setEndDate(LocalDate.now());

        deptHeadRepo.save(deptHead);
    }
}
