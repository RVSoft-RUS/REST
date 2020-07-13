package ru.rvs.springbootcrud.dto;

import ru.rvs.springbootcrud.model.Role;
import ru.rvs.springbootcrud.model.User;

import java.util.Arrays;
import java.util.Objects;

public class UserDTO {
    private Long id;
    private String name;
    private String surName;
    private String password;
    private String login;
    private int age;
    private String[] roles;

    public UserDTO(Long id, String name, String surName, String password, String login, int age, String[] roles) {
        this.id = id;
        this.name = name;
        this.surName = surName;
        this.password = password;
        this.login = login;
        this.age = age;
        this.roles = roles;
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.surName = user.getSurname();
        this.password = user.getPassword();
        this.login = user.getLogin();
        this.age = user.getAge();
        Object[] objectArr = user.getRoles().stream().map(Role::getName).toArray();
        this.roles = Arrays.copyOf(objectArr, objectArr.length, String[].class);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDTO userDTO = (UserDTO) o;

        if (age != userDTO.age) return false;
        if (!Objects.equals(id, userDTO.id)) return false;
        if (!Objects.equals(name, userDTO.name)) return false;
        if (!Objects.equals(surName, userDTO.surName)) return false;
        if (!Objects.equals(password, userDTO.password)) return false;
        if (!Objects.equals(login, userDTO.login)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(roles, userDTO.roles);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surName != null ? surName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + Arrays.hashCode(roles);
        return result;
    }
}
