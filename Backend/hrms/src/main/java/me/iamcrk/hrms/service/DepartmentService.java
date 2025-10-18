package me.iamcrk.hrms.service;

import me.iamcrk.hrms.dto.DepartmentDTO;
import me.iamcrk.hrms.entity.Department;
import me.iamcrk.hrms.repository.IDepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private IDepartmentRepo departmentRepo;

    public List<Department> getAllDepartments() {
        return departmentRepo.findAll();
    }

    public List<DepartmentDTO> getAllDepartmentDTOs() {
        return getAllDepartments().stream().map(c -> {
            DepartmentDTO dto = new DepartmentDTO();
            dto.setId(c.getId());
            dto.setName(c.getName());

            List<Long> departmentIds = c.getDesignations().stream()
                    .map(d -> d.getId())
                    .toList();


            return dto;
        }).toList();
    }


    public Department saveDepartment(Department department) {
        return departmentRepo.save(department);
    }

    public void deleteById(Long id) {
        departmentRepo.deleteById(id);
    }
}
