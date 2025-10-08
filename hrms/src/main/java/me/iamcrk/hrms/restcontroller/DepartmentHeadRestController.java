package me.iamcrk.hrms.restcontroller;

import me.iamcrk.hrms.entity.DepartmentHead;
import me.iamcrk.hrms.service.DepartmentHeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deptHead")
public class DepartmentHeadRestController {

    @Autowired
    private DepartmentHeadService deptHeadService;

    @PostMapping("/register")
    public ResponseEntity<String> registerAsDeptHead(Authentication authentication) {
        String email = authentication.getName();
        String response = deptHeadService.registerAsDepartmentHead(email);
        return ResponseEntity.ok(response);
    }

    // Get all department heads
    @GetMapping
    public ResponseEntity<List<DepartmentHead>> getAllDepartmentHeads() {
        return ResponseEntity.ok(deptHeadService.getAllDepartmentHeads());
    }

    // Get all active department heads only
    @GetMapping("/active")
    public ResponseEntity<List<DepartmentHead>> getAllActiveDepartmentHeads() {
        return ResponseEntity.ok(deptHeadService.getAllActiveDepartmentHeads());
    }

    // End Dept Head Role
    @PutMapping("/end/{id}")
    public ResponseEntity<String> endRole(@PathVariable Long id) {
        deptHeadService.endDepartmentHeadRole(id);
        return ResponseEntity.ok("Department Head role ended successfully.");
    }
}
