package com.waflo.cooltimediaplattform.backend.service;

import com.waflo.cooltimediaplattform.backend.model.Role;
import com.waflo.cooltimediaplattform.backend.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository repo;

    public RoleService(RoleRepository repo) {
        this.repo = repo;
    }

    public Optional<Role> findByRoleName(String roleName) {
        return repo.findAll().stream().filter(r -> r.getRoleName().equalsIgnoreCase(roleName)).findFirst();
    }

    public Role save(Role role) {
        return repo.save(role);
    }
}
