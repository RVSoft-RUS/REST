package ru.rvs.springbootcrud.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.rvs.springbootcrud.dto.RoleDTO;
import ru.rvs.springbootcrud.dto.UserDTO;

import java.util.List;
import java.util.Optional;

@Component
public class ExchangeClientServerImpl implements ExchangeClientServer {
    private final RestTemplate restTemplate;
    private final String ADRESS_URL = "http://localhost:8081/api/";

    public ExchangeClientServerImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<UserDTO> findAll() {
        ResponseEntity<List<UserDTO>> responseEntity = restTemplate.exchange(
                ADRESS_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserDTO>>() {
                });
        return responseEntity.getBody();
    }

    @Override
    public UserDTO getUserById(Long id) {
        UserDTO userDto = restTemplate.getForObject(ADRESS_URL + id.toString(), UserDTO.class);
        return userDto;
    }

    @Override
    public void addUser(UserDTO userDto) {
        restTemplate.postForObject(ADRESS_URL, userDto, UserDTO.class);
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            restTemplate.delete(ADRESS_URL + id.toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean editUser(UserDTO userDto) {
        try {
            restTemplate.put(ADRESS_URL, userDto);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public UserDTO getUserByLogin(String s) {
        Optional<UserDTO> userDto = Optional.ofNullable(
                restTemplate.getForObject(ADRESS_URL + "getAuth/" + s, UserDTO.class));
        if (!userDto.isPresent()) {
            throw new RuntimeException("Ошибка в getUserById ExchangeClientServer");
        }
        return userDto.get();
    }

    @Override
    public RoleDTO getRoleByName(String s) {
        try {
            RoleDTO roleDto = restTemplate.getForObject(ADRESS_URL + "getrole/" + s, RoleDTO.class);
            return roleDto;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка в getRoleByName ExchangeClientServer");
        }
    }
}
