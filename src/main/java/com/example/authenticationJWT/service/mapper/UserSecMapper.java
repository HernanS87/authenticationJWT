package com.example.authenticationJWT.service.mapper;

import com.example.authenticationJWT.model.dto.UserSecDto;
import com.example.authenticationJWT.model.Role;
import com.example.authenticationJWT.model.UserSec;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserSecMapper {



    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "username", source = "dto.username")
    @Mapping(target = "password", source = "dto.password", qualifiedByName = "encrypt")
    @Mapping(target = "enabled", source = "dto.enabled")
    @Mapping(target = "accountNotExpired", source = "dto.accountNotExpired")
    @Mapping(target = "credentialNotExpired", source = "dto.credentialNotExpired")
    @Mapping(target = "accountNotLocked", source = "dto.accountNotLocked")
    @Mapping(target = "roles", expression = "java(roles)")
    UserSec userSecDtoToEntity(UserSecDto dto, Set<Role> roles);


    @Named("encrypt")
    default String encryptPassword(String password){
        return new BCryptPasswordEncoder().encode(password);
    }
    /*
     Uso la anotación "@Named" junto con "qualifiedByName" porque MapStruct puede llamar y utilizar los métodos "default" automáticamente si sus parámetros coinciden con los tipos que MapStruct está intentando mapear.
     En este caso no solo lo llamaba para la variable password sino tambien para username
    */

    default User userSecToUser(UserSec userSec, List<SimpleGrantedAuthority> authorities) {
        return new User(
                userSec.getUsername(),
                userSec.getPassword(),
                userSec.isEnabled(),
                userSec.isAccountNotExpired(),
                userSec.isCredentialNotExpired(),
                userSec.isAccountNotLocked(),
                authorities);
    }


}
