package me.iamcrk.hrms.repository;

import me.iamcrk.hrms.entity.Accountant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAccountantRepo extends JpaRepository<Accountant, Long> {

    Optional<Accountant> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    // Get all active accountants
    List<Accountant> findByActiveTrue();
}
