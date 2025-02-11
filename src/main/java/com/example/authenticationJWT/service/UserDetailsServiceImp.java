package com.example.authenticationJWT.service;

import com.example.authenticationJWT.mapper.UserSecMapper;
import com.example.authenticationJWT.repository.UserSecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserSecRepository userSecRepository;

    @Autowired
    private UserSecMapper userSecMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var userSec = userSecRepository.findUserSecByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("El usuario " + username + "no fue encontrado"));

        //con GrantedAuthority Spring Security maneja permisos
        var authorityList = new ArrayList<SimpleGrantedAuthority>();

        //tomamos roles y los convertimos en SimpleGrantedAuthority para poder agregarlos a la authorityList
        userSec.getRoles()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName()))));


        //ahora tenemos que agregar los permisos
        userSec.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        //retornamos el usuario en formato Spring Security con los datos de nuestro userSec
        return userSecMapper.userSecToUser(userSec, authorityList);
    }

}
