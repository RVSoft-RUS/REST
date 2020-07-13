package ru.rvs.springbootcrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.rvs.springbootcrud.model.Role;
import ru.rvs.springbootcrud.model.User;
import ru.rvs.springbootcrud.service.RoleService;
import ru.rvs.springbootcrud.service.UserService;
import ru.rvs.springbootcrud.service.UserServiceImp;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
//    private String lastLogin;
    @Qualifier("userServiceImp")
    @Autowired
    private UserServiceImp userService;
//    @Qualifier("roleServiceImp")
//    private final RoleService roleService;
//
//    @Autowired
//    public AdminController(UserService userService, RoleService roleService) {
//        this.userService = userService;
//        this.roleService = roleService;
//    }

    @GetMapping("/users")
    public ModelAndView getAllUsers(Authentication auth) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("authUser", (User) auth.getPrincipal());
        mv.addObject("userEmail",((User) auth.getPrincipal()).getLogin());
        mv.addObject("rolesAuth",((User) auth.getPrincipal()).getRoles()
                .stream().map(Objects::toString).collect(Collectors.joining(", ")));
        mv.setViewName("admin/users");
        return mv;
    }
}
