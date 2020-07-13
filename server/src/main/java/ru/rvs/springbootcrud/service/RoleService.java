package ru.rvs.springbootcrud.service;

import ru.rvs.springbootcrud.dto.RoleDTO;
import ru.rvs.springbootcrud.model.Role;

public interface RoleService {
    Role getRoleByName(String name);
    RoleDTO getRoleDtoByName(String name);
}
