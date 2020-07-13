package ru.rvs.springbootcrud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rvs.springbootcrud.dto.RoleDTO;
import ru.rvs.springbootcrud.model.Role;
import ru.rvs.springbootcrud.repository.RoleRepository;

@Service
public class RoleServiceImp implements RoleService {
    private RoleRepository roleDao;

    @Autowired
    public void setRoleDao(RoleRepository roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    @Transactional
    public Role getRoleByName(String name) {
        return roleDao.getRolesByName(name);
    }

    @Override
    public RoleDTO getRoleDtoByName(String name) {
        Role role = roleDao.getRolesByName(name);
        return new RoleDTO(role);
    }
}
