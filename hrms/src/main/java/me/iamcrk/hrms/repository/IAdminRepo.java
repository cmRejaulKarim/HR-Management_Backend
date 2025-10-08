package me.iamcrk.hrms.repository;

import me.iamcrk.hrms.entity.Accountant;
import me.iamcrk.hrms.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAdminRepo extends JpaRepository<Admin, Long> {

    Optional<Admin> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    // Get all active admins
    List<Admin> findByActiveTrue();
}
