package me.iamcrk.hrms.repository;

import me.iamcrk.hrms.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISalaryAdvanceRequestRepo extends JpaRepository<Attendance,Long> {
}
