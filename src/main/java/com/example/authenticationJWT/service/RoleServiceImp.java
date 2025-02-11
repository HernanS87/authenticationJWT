package com.example.authenticationJWT.service;

import com.example.authenticationJWT.dto.RoleDto;
import com.example.authenticationJWT.mapper.RoleMapper;
import com.example.authenticationJWT.model.Permission;
import com.example.authenticationJWT.model.Role;
import com.example.authenticationJWT.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImp implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleMapper roleMapper;



    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Optional<Role> findRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }

    @Override
    public Role save(RoleDto roleDto) {

        var permissions = new HashSet<Permission>();
        Permission readPermission;

        for (String per : roleDto.getPermissions()) {
            readPermission = permissionService.findPermissionByName(per).orElse(null);
            if (readPermission != null) {
                //si encuentro, guardo en la lista
                permissions.add(readPermission);
            }
        }

        if (permissions.isEmpty()) return null; //TODO delvolver un optional vacío y cambiar el controller para que devuelva un 400

        return roleRepository.save(roleMapper.roleDtoToEntity(roleDto, permissions));
    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role update(RoleDto roleDto) {
        return save(roleDto);
    }
}
