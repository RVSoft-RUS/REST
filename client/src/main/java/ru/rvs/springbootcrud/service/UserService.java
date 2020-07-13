package ru.rvs.springbootcrud.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.rvs.springbootcrud.dto.UserDTO;
import ru.rvs.springbootcrud.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    boolean addUser(UserDTO userDTO);

    List<UserDTO> getAllUsers();

    boolean deleteUser(long id);

    UserDTO getUserById(long id);

    boolean updateUser(UserDTO userDTO);

    User getUserByLogin(String login);
//
//    boolean isExistLogin(String login);
}
