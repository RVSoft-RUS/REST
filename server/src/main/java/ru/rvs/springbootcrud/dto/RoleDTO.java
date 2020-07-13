package ru.rvs.springbootcrud.dto;

import ru.rvs.springbootcrud.model.Role;

public class RoleDTO {
    private Long role_id;
    private String name;

    public RoleDTO() {
    }

    public RoleDTO(Role role) {
        this.role_id = role.getId();
        this.name = role.getName();
    }

    public RoleDTO(Long role_id, String name) {
        this.role_id = role_id;
        this.name = name;
    }

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
