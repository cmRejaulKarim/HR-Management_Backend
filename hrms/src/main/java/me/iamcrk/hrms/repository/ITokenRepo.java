package me.iamcrk.hrms.repository;

import me.iamcrk.hrms.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITokenRepo extends JpaRepository<Token,Long> {

    Optional<Token> findByToken(String token);

    @Query("""
    SELECT t FROM Token t 
    WHERE t.user.id = :userId AND t.logout = false
""")
    List<Token> findAllTokenByUser(@Param("userId") Long userId);
}
