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
    public Client login(@Param("client_ID") String client_ID, @Param("client_password") String client_password) {
        return clientService.login(client_ID, client_password);
    }
}
