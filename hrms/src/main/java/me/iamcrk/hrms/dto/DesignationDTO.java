package me.iamcrk.hrms.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import me.iamcrk.hrms.entity.Department;

public class DesignationDTO {

    private Long id;
    private String name;
    private DepartmentDTO department;

    public DesignationDTO() {
    }

    public DesignationDTO(Long id, String name, DepartmentDTO department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }
}
