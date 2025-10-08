package me.iamcrk.hrms.repository;

import me.iamcrk.hrms.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IEmployeeRepo extends JpaRepository<Employee,Long> {

    Optional<Employee> findByUserId(Long userId);

    Employee findByUserEmail(String userEmail);

    List<Employee> findByDepartmentId( long departmentId);

    Optional<Employee> findByEmail(String email);

    @Query("SELECT e FROM Employee e " +
            "WHERE e.department.id = :departmentId " +
            "AND e.user.role IN ('EMPLOYEE', 'DEPARTMENT_HEAD')")
    List<Employee> findEmployeesByDepartmentWithAllowedRoles(@Param("departmentId") Long departmentId);

    @Query("SELECT e FROM Employee e " +
            "WHERE e.department.id = :departmentId " +
            "AND e.user.role = 'EMPLOYEE'")
    List<Employee> findEmployeesByDepartmentWithRoleEmp(@Param("departmentId") Long departmentId);
}
