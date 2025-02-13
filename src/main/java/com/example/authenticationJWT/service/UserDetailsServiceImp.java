package com.example.authenticationJWT.service;

import com.example.authenticationJWT.model.dto.AuthLoginRequestDto;
import com.example.authenticationJWT.model.dto.AuthLoginResponseDto;
import com.example.authenticationJWT.service.mapper.UserSecMapper;
import com.example.authenticationJWT.repository.UserSecRepository;
import com.example.authenticationJWT.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserSecRepository userSecRepository;

    @Autowired
    private UserSecMapper userSecMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    public AuthLoginResponseDto loginUser (AuthLoginRequestDto authLoginRequest){

        //recuperamos nombre de usuario y contraseña
        var username = authLoginRequest.username();
        var password = authLoginRequest.password();

        var authentication = this.authenticate(username, password);
        //si todoo sale bien
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var accessToken = jwtUtils.createToken(authentication);

        return new AuthLoginResponseDto(username, "login ok", accessToken, true);

    }

    public Authentication authenticate (String username, String password) {
        //con esto debo buscar el usuario
        var userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Ivalid username or password");
        }
        // si no es igual
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }

}
