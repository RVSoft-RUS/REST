package ru.rvs.springbootcrud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rvs.springbootcrud.dto.RoleDTO;
import ru.rvs.springbootcrud.model.Role;

@Service
public class RoleServiceImp implements RoleService {
    private ExchangeClientServer exchangeClientServer;

    @Autowired
    public void setExchangeClientServer(ExchangeClientServer exchangeClientServer) {
        this.exchangeClientServer = exchangeClientServer;
    }

    @Override
    public Role getRoleByName(String name) {
        RoleDTO roleDTO = exchangeClientServer.getRoleByName(name);
        return new Role(roleDTO);
    }
}
