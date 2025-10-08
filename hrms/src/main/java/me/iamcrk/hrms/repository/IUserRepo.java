package me.iamcrk.hrms.repository;

import me.iamcrk.hrms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepo extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
}
