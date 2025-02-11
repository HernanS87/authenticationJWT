package com.example.authenticationJWT.service;

import com.example.authenticationJWT.model.dto.UserSecDto;
import com.example.authenticationJWT.service.mapper.UserSecMapper;
import com.example.authenticationJWT.model.Role;
import com.example.authenticationJWT.model.UserSec;
import com.example.authenticationJWT.repository.UserSecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserSecServiceImp implements UserSecService{

    @Autowired
    private UserSecRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserSecMapper userSecMapper;


    @Override
    public List<UserSec> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserSec> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<UserSec> findUserSecByUsername(String name) {
        return userRepository.findUserSecByUsername(name);
    }

    @Override
    public UserSec save(UserSecDto userDto) {

        var roles = new HashSet<Role>();
        Role readRole;

        for (String rol : userDto.getRoles()) {
            readRole = roleService.findRoleByName(rol).orElse(null);
            if (readRole != null) {
                //si encuentro, guardo en la lista
                roles.add(readRole);
            }
        }

        if (roles.isEmpty()) return null; //TODO delvolver un optional vacío y cambiar el controller para que devuelva un 400

        return userRepository.save(userSecMapper.userSecDtoToEntity(userDto, roles));
    }


    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserSec update(UserSecDto userDto) {
        return save(userDto);
    }
}
