package com.catsix.systemprojectcatsix.service;

import com.catsix.systemprojectcatsix.mapper.ClientMapper;
import com.catsix.systemprojectcatsix.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    ClientMapper clientMapper;

    public String getCurrentDateTime() throws  Exception {
        return clientMapper.getCurrentDateTime();
    }

    public Client login(String client_ID, String client_password) {
        return clientMapper.login(client_ID, client_password);
    }

    public Client showClientInfo(String client_ID) {
        return clientMapper.showClientInfo(client_ID);
    }

    public void createClient(String client_ID, String client_password, String client_name, String client_nickname, String profile_picture) {
        clientMapper.createClient(client_ID, client_password, client_name, client_nickname, profile_picture);
    }

    public void deleteClient(String client_ID) {
        clientMapper.deleteClient(client_ID);
    }
}
