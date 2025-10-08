package me.iamcrk.hrms.repository;

import me.iamcrk.hrms.entity.Designation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDesignationRepo extends JpaRepository<Designation, Long> {


    List<Designation> findByDepartmentId(Long id);
}
