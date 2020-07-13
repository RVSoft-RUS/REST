package ru.rvs.springbootcrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rvs.springbootcrud.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getRolesByName(String name);
}
