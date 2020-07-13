package ru.rvs.springbootcrud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.rvs.springbootcrud.dto.UserDTO;
import ru.rvs.springbootcrud.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Qualifier("userServiceImp")
    private final RoleService roleService;
    private final ExchangeClientServer exchangeClientServer;

    @Autowired
    public UserDetailsServiceImpl(RoleService roleService, ExchangeClientServer exchangeClientServer) {
        this.roleService = roleService;
        this.exchangeClientServer = exchangeClientServer;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserDTO userDTO = exchangeClientServer.getUserByLogin(login);
        User user = new User(userDTO);
        user.setRoles(Arrays.stream(userDTO.getRoles()).map(roleService::getRoleByName).collect(Collectors.toSet()));

        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("This user not found");
        }
    }
}
