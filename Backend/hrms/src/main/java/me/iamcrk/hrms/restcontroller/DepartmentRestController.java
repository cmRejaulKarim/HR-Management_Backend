package me.iamcrk.hrms.restcontroller;

import me.iamcrk.hrms.dto.DepartmentDTO;
import me.iamcrk.hrms.entity.Department;
import me.iamcrk.hrms.repository.IDepartmentRepo;
import me.iamcrk.hrms.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/department")
public class DepartmentRestController {

    @Autowired
    private DepartmentService departmentService;


    @Autowired
    private IDepartmentRepo departmentRepo;


    // Get all Departments as DTOs
    @GetMapping("")
    public List<DepartmentDTO> getAllDepartments() {
        return departmentService.getAllDepartmentDTOs();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        return departmentRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    // Create new Department
    @PostMapping("")
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department savedDepartment = departmentService.saveDepartment(department);
        return ResponseEntity.ok(savedDepartment);
    }

    // Update existing Department
    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        Optional<Department> existingDepartmentOpt = departmentService.getAllDepartments()
                .stream()
                .filter(c -> c.getId() == id)
                .findFirst();

        if (existingDepartmentOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Department existingDepartment = existingDepartmentOpt.get();
        existingDepartment.setName(department.getName());
        // You can update divisions if needed, but usually divisions are managed separately

        Department updatedDepartment = departmentService.saveDepartment(existingDepartment);
        return ResponseEntity.ok(updatedDepartment);
    }

    // Delete Department by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        Optional<Department> existingDepartmentOpt = departmentService.getAllDepartments()
                .stream()
                .filter(c -> c.getId() == id)
                .findFirst();

        if (existingDepartmentOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        departmentService.saveDepartment(existingDepartmentOpt.get()); // you can remove this line if unnecessary
        departmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
