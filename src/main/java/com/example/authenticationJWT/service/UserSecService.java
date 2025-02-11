package com.example.authenticationJWT.service;

import com.example.authenticationJWT.model.dto.UserSecDto;
import com.example.authenticationJWT.model.UserSec;

import java.util.List;
import java.util.Optional;

public interface UserSecService {

    List<UserSec> findAll();
    Optional<UserSec> findById(Long id);
    Optional<UserSec> findUserSecByUsername(String name);
    UserSec save(UserSecDto userSecDto);
    void deleteById(Long id);
    UserSec update(UserSecDto userSecDto);
}
