package ru.rvs.springbootcrud.service;

import ru.rvs.springbootcrud.dto.RoleDTO;
import ru.rvs.springbootcrud.dto.UserDTO;

import java.util.List;

public interface ExchangeClientServer {
    List<UserDTO> findAll();
    UserDTO getUserById(Long id);
    void addUser(UserDTO userDto);
    boolean deleteById(Long id);
    boolean editUser(UserDTO userDto);
    UserDTO getUserByLogin(String s);
    RoleDTO getRoleByName(String s);
}
