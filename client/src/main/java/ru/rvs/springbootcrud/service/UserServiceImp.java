package ru.rvs.springbootcrud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rvs.springbootcrud.dto.UserDTO;
import ru.rvs.springbootcrud.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {
   private ExchangeClientServer exchangeClientServer;
   private RoleService roleService;
   private BCryptPasswordEncoder bCryptPasswordEncoder;

   @Autowired
   public void setExchangeClientServer(ExchangeClientServer exchangeClientServer) {
      this.exchangeClientServer = exchangeClientServer;
   }

   @Autowired
   public void setRoleService(RoleService roleService) {
      this.roleService = roleService;
   }

   @Autowired
   public void setEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
      this.bCryptPasswordEncoder = bCryptPasswordEncoder;
   }

   public UserServiceImp() {
   }

   @Override
   public boolean addUser(UserDTO userDTO) {
      userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
      exchangeClientServer.addUser(userDTO);
      return true;
   }

   @Override
   public List<UserDTO> getAllUsers() {
       return exchangeClientServer.findAll();
   }

   @Override
   public boolean deleteUser(long id) {
      return exchangeClientServer.deleteById(id);
   }

   @Override
   public UserDTO getUserById(long id) {
      return exchangeClientServer.getUserById(id);
   }

   @Override
   public boolean updateUser(UserDTO userDTO) {
      userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
      exchangeClientServer.addUser(userDTO);
      return true;
   }

   @Override
   public User getUserByLogin(String login) {
      UserDTO userDTO = exchangeClientServer.getUserByLogin(login);
      User user = new User(userDTO);
      user.setRoles(Arrays.stream(userDTO.getRoles()).map(roleService::getRoleByName).collect(Collectors.toSet()));
      return user;
   }
//
//   @Transactional(readOnly = true)
//   @Override
//   public boolean isExistLogin(String login) {
//      User user = userDao.getUsersByLogin(login);
//      return (user != null);
//   }

   @Override
   public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
      User user = getUserByLogin(s);
      if (user == null) {
         throw new UsernameNotFoundException("User not found");
      }
      return user;
   }
}
