package me.iamcrk.hrms.service;

import me.iamcrk.hrms.dto.DepartmentDTO;
import me.iamcrk.hrms.dto.DesignationDTO;
import me.iamcrk.hrms.entity.Department;
import me.iamcrk.hrms.entity.Designation;
import me.iamcrk.hrms.repository.IDesignationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DesignationService {

    @Autowired
    private IDesignationRepo designationRepo;


    public List<Designation> getAllDesignations() {
        return designationRepo.findAll();
    }

    public List<DesignationDTO> getAllDesignationDTOS() {
        return designationRepo.findAll().stream().map(des -> {
            DesignationDTO dto = new DesignationDTO();
            dto.setId(des.getId());
            dto.setName(des.getName());

            Department dep = des.getDepartment();
            if (dep != null) {
                DepartmentDTO departmentDTO = new DepartmentDTO();
                departmentDTO.setId(dep.getId());
                departmentDTO.setName(dep.getName());
                dto.setDepartment(departmentDTO);
            }

            return dto;
        }).toList();
    }

    public Optional<Designation> findById(Long id) {
        return designationRepo.findById(id);
    }

    public Designation saveDesignation(Designation designation) {
        return designationRepo.save(designation);
    }

    public void deleteById(Long id) {
        designationRepo.deleteById(id);
    }


    // DivisionService
    public List<DesignationDTO> getByDepartmentId(Long deptId) {
        return designationRepo.findByDepartmentId(deptId)
                .stream()
                .map(d -> new DesignationDTO(d.getId(), d.getName(), null))
                .toList();
    }
}
