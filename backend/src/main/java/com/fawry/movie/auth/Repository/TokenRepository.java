package com.fawry.movie.auth.Repository;


import com.fawry.movie.auth.Entity.AppUser;
import com.fawry.movie.auth.Entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);

    @Query("SELECT t FROM Token t where t.user=:user")
    List<Token> findAllUserTokens(@Param("user") AppUser user);
}
