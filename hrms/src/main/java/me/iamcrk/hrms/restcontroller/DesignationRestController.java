package me.iamcrk.hrms.restcontroller;

import me.iamcrk.hrms.dto.DesignationDTO;
import me.iamcrk.hrms.entity.Designation;
import me.iamcrk.hrms.service.DesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/designation")
public class DesignationRestController {
    @Autowired
    private DesignationService designationService;


    @GetMapping("")
    public List<DesignationDTO> getAllDesignation() {
        return designationService.getAllDesignationDTOS();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Designation> getDesignationById(@PathVariable Long id) {
        Optional<Designation> Designation = designationService.findById(id);
        return Designation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("")
    public ResponseEntity<Designation> createDesignation(@RequestBody Designation designation) {
        Designation savedDesignation = designationService.saveDesignation(designation);
        return ResponseEntity.ok(savedDesignation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Designation> updateDesignation(@PathVariable Long id, @RequestBody Designation designation) {
        Optional<Designation> existing = designationService.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Designation toUpdate = existing.get();
        toUpdate.setName(designation.getName());
        toUpdate.setDepartment(designation.getDepartment());

        Designation updated = designationService.saveDesignation(toUpdate);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDesignation(@PathVariable Long id) {
        Optional<Designation> existing = designationService.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        designationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/by-department/{departmentId}")
    public List<DesignationDTO> getByDepartment(@PathVariable Long departmentId) {
        return designationService.getByDepartmentId(departmentId);
    }

}
