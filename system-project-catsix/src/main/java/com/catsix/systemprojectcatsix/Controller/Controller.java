package com.catsix.systemprojectcatsix.Controller;

import com.catsix.systemprojectcatsix.model.Client;
import com.catsix.systemprojectcatsix.service.ClientService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {
    @Autowired
    private ClientService clientService;

    @RequestMapping("/")
    public String home() {
        return "HOME";
    }

    @RequestMapping("/now")
    @ResponseBody
    public String now() throws Exception {
        return clientService.getCurrentDateTime();
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public String login(@Param("client_ID") String client_ID, @Param("client_password") String client_password) {
        if(clientService.login(client_ID, client_password) != null) {
            return "success";
        }
        else {
            return "fail";
        }
    }
    @RequestMapping(value = "/showClientInfo", method = RequestMethod.GET)
    @ResponseBody
    public Client showClientInfo(@Param("client_ID") String client_ID) {
        return clientService.showClientInfo(client_ID);
    }

    @RequestMapping(value = "/createClient", method = RequestMethod.POST)
    @ResponseBody
    void createClient(@Param("client_ID") String client_ID, @Param("client_password") String client_password, @Param("client_name") String client_name, @Param("client_nickname") String client_nickname, @Param("profile_picture") String profile_picture) {
        clientService.createClient(client_ID, client_password, client_name, client_nickname, profile_picture);
    }
}
