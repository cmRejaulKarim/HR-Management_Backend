package me.iamcrk.hrms.service;

import me.iamcrk.hrms.entity.Admin;
import me.iamcrk.hrms.entity.DepartmentHead;
import me.iamcrk.hrms.entity.Role;
import me.iamcrk.hrms.entity.User;
import me.iamcrk.hrms.repository.IAdminRepo;
import me.iamcrk.hrms.repository.IDepartmentHeadRepo;
import me.iamcrk.hrms.repository.IEmployeeRepo;
import me.iamcrk.hrms.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private IAdminRepo adminRepo;
    @Autowired
    private IDepartmentHeadRepo deptHeadRepo;
    @Autowired
    private IUserRepo userRepo;
    @Autowired
    private IEmployeeRepo employeeRepo;

    public List<Admin> getAllActiveAdmins() {
        return adminRepo.findByActiveTrue();
    }

    public String registerAsAdmin(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Must be a Department Head
        DepartmentHead deptHead = deptHeadRepo.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("You are not registered as a Department Head"));

        // Check if user has ever been an Admin
        if (adminRepo.findByUserId(user.getId()).isPresent()) {
            throw new RuntimeException("You are already registered (current or previous) as an Admin!");
        }

        deptHead.setEndDate(LocalDate.now());
        deptHead.setActive(false);
        deptHeadRepo.save(deptHead);

        // Create new Admin
        Admin admin = new Admin();
        admin.setUser(user);
        admin.setAssignedDate(LocalDate.now());
        admin.setActive(true);
        adminRepo.save(admin);

        // Update user role
        user.setRole(Role.ADMIN);
        userRepo.save(user);

        return "Successfully registered as Admin!";
    }


    // End Admin role
    public void endAdminRole(Long adminId) {
        Admin admin = adminRepo.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        admin.setActive(false);
        admin.setEndDate(LocalDate.now());

        adminRepo.save(admin);
    }

//    // Register Department Head as Admin (using email of logged-in user)
//    public Admin registerAsAdmin(String email) {
//        // Find user by email
//        User user = userRepo.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        // Find Department Head by User ID
//        DepartmentHead deptHead = deptHeadRepo.findByEmployeeUserId(user.getId())
//                .orElseThrow(() -> new RuntimeException("You are not registered as Department Head"));
//
//        // Prevent duplicate admin registration
//        if (adminRepo.existsByDepartmentHeadId(deptHead.getId())) {
//            throw new RuntimeException("This Department Head is already an Admin!");
//        }
//
//        Admin admin = new Admin();
//        admin.setDepartmentHead(deptHead);
//        admin.setAssignedDate(LocalDate.now());
//        admin.setActive(true);
//
//        //  Update User role
////        User user = employee.getUser();
//        user.setRole(Role.ACCOUNTANT);
//        userRepo.save(user);
//
//        return adminRepo.save(admin);
//    }
}
