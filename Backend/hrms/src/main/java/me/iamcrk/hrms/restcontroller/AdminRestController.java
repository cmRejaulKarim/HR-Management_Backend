package me.iamcrk.hrms.restcontroller;

import me.iamcrk.hrms.entity.Admin;
import me.iamcrk.hrms.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    @Autowired
    private AdminService adminService;

    // Register current Department Head as Admin
    @PostMapping("/register")
    public ResponseEntity<String> registerAsAdmin(Authentication authentication) {
        String email = authentication.getName();
        String response = adminService.registerAsAdmin(email);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/active")
    public ResponseEntity<List<Admin>> getAllActiveAdmins() {
        return ResponseEntity.ok(adminService.getAllActiveAdmins());
    }

    // End Admin role
    @PutMapping("/end/{id}")
    public ResponseEntity<String> endRole(@PathVariable Long id) {
        adminService.endAdminRole(id);
        return ResponseEntity.ok("Admin role ended successfully.");
    }
}

