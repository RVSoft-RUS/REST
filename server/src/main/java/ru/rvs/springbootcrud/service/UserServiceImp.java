package ru.rvs.springbootcrud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rvs.springbootcrud.dto.UserDTO;
import ru.rvs.springbootcrud.model.User;
import ru.rvs.springbootcrud.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {
   private UserRepository userDao;
   private RoleService roleService;
   private BCryptPasswordEncoder bCryptPasswordEncoder;

   @Autowired
   public void setUserDao(UserRepository userDao) {
      this.userDao = userDao;
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

   @Transactional
   @Override
   public boolean addUser(UserDTO userDto) {
      User user = new User(userDto);
//      user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
      user.setRoles(Arrays.stream(userDto.getRoles()).map(roleService::getRoleByName).collect(Collectors.toSet()));
      userDao.save(user);
      return true;
   }

   @Transactional(readOnly = true)
   @Override
   public List<UserDTO> getAllUsers() {
      List<User> userList = userDao.findAll();
      List<UserDTO> userDtoList = userList
              .stream()
              .map(UserDTO::new)
              .collect(Collectors.toList());
      return userDtoList;
   }

   @Transactional(readOnly = true)
   public List<User> getAll_Users() {
      List<User> userList = userDao.findAll();

      return userList;
   }

   @Transactional
   @Override
   public boolean deleteUser(long id) {
      userDao.deleteById(id);
      return true;
   }

   @Transactional(readOnly = true)
   @Override
   public UserDTO getUserById(long id) {
      User user = userDao.getOne(id);
      UserDTO userDTO = new UserDTO(user);
      return userDTO;
   }

   @Transactional
   @Override
   public boolean updateUser(UserDTO userDTO) {
      User user = new User(userDTO);
      user.setRoles(Arrays.stream(userDTO.getRoles()).map(roleService::getRoleByName).collect(Collectors.toSet()));
//      user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
      userDao.save(user);
      return true;
   }

   @Transactional(readOnly = true)
   @Override
   public User getUserByLogin(String login) {
      User user = userDao.getUsersByLogin(login);
      return user;
   }

   @Transactional(readOnly = true)
   @Override
   public boolean isExistLogin(String login) {
      User user = userDao.getUsersByLogin(login);
      return (user != null);
   }

   public UserDTO loadUserByUsername(String s) {
      User user = getUserByLogin(s);
      if (user == null) {
         throw new UsernameNotFoundException("User not found");
      }
      return new UserDTO(user);
   }

}
