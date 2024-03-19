package com.example.appbootstrap.service;

import com.example.appbootstrap.model.Role;
import com.example.appbootstrap.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    public Optional<Role> findById(Long id) {return roleRepository.findById(id);}

    public String getRolesNames(List<Role> roles) {
        return roles.stream().map(role->role.getName().replace("ROLE_","")).collect(Collectors.joining(" "));
    }

}
