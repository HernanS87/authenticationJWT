package com.example.authenticationJWT.repository;

import com.example.authenticationJWT.model.UserSec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSecRepository extends JpaRepository<UserSec, Long> {

    //Crea la sentencia en base al nombre en inglés del métodoo
    //Tmb se puede hacer mediante Query pero en este caso no es necesario
    Optional<UserSec> findUserSecByUsername( String username);
}

