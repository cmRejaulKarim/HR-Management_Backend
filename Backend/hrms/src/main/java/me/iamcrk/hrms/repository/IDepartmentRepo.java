package me.iamcrk.hrms.repository;

import me.iamcrk.hrms.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDepartmentRepo extends JpaRepository<Department,Long> {
}
