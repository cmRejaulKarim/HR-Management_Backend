package me.iamcrk.hrms.repository;

import me.iamcrk.hrms.entity.Department;
import me.iamcrk.hrms.entity.DepartmentHead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IDepartmentHeadRepo extends JpaRepository<DepartmentHead, Long> {

    // Find active DeptHead by department
    Optional<DepartmentHead> findByDepartmentAndActiveTrue(Department department);

    // Find DeptHead by User (to prevent duplicate registration)
    Optional<DepartmentHead> findByUserId(Long userId);

    List<DepartmentHead> findByActiveTrue();

    // Find active DepartmentHead by userId
    Optional<DepartmentHead> findByUserIdAndActiveTrue(Long userId);
}
