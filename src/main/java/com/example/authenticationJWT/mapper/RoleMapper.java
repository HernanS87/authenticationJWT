package com.example.authenticationJWT.mapper;

import com.example.authenticationJWT.dto.RoleDto;
import com.example.authenticationJWT.model.Permission;
import com.example.authenticationJWT.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Set;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    @Mapping(target = "permissions", expression = "java(permissions)")
    Role roleDtoToEntity(RoleDto dto, Set<Permission> permissions);

}

