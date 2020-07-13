package ru.rvs.springbootcrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.rvs.springbootcrud.dto.RoleDTO;
import ru.rvs.springbootcrud.dto.UserDTO;
import ru.rvs.springbootcrud.model.Role;
import ru.rvs.springbootcrud.model.User;
import ru.rvs.springbootcrud.service.RoleService;
import ru.rvs.springbootcrud.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {
    @Qualifier("userServiceImp")
    private final UserService userService;
    @Qualifier("roleServiceImp")
    private final RoleService roleService;

    @Autowired
    public RestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> read() {
        List<UserDTO> userDTOList = userService.getAllUsers();

        return userDTOList.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> read(@PathVariable("id") Long id) {
        UserDTO userDTO = userService.getUserById(id);

        return userDTO == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/getAuth/{s}")
    public ResponseEntity<?> authenticate(@PathVariable("s") String s) {
        UserDTO userDto = userService.loadUserByUsername(s);
        System.out.println(userDto);
        return userDto != null ?
                new ResponseEntity<>(userDto, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/getrole/{s}")
    public ResponseEntity<?> getRoleByName(@PathVariable("s") String s) {
        RoleDTO roleDto = roleService.getRoleDtoByName(s);

        return roleDto != null ?
                new ResponseEntity<>(roleDto, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserDTO userDTO) {
        if (isCorrect(userDTO)) {
            userService.addUser(userDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody UserDTO userDTO) {
        if (!isCorrect(userDTO)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return userService.updateUser(userDTO)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return userService.deleteUser(id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    private Set<Role> makeRoles(String role){
        Set<Role> userRoles = new HashSet<>();
        if (role.equals("user")) {
            userRoles.add(roleService.getRoleByName("USER"));
        }
        if (role.equals("admin")) {
            userRoles.add(roleService.getRoleByName("ADMIN"));
        }
        if (role.equals("adminAndUser")) {
            userRoles.add(roleService.getRoleByName("ADMIN"));
            userRoles.add(roleService.getRoleByName("USER"));
        }
        return userRoles;
    }

    private boolean isCorrect(UserDTO userDto) {
        if(userDto.getName().equals("")
                || userDto.getSurName().equals("")
                || userDto.getLogin().equals("")
                || userDto.getPassword().equals("")
                || userDto.getRoles().length == 0) {
            return false;
        }
        return true;
    }
}
