package com.catsix.systemprojectcatsix.mapper;

import com.catsix.systemprojectcatsix.model.Client;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClientMapper {
    String getCurrentDateTime() throws Exception;
    List<Client> login(@Param("client_ID") String client_ID, @Param("client_password") String client_password);
    Client showClientInfo(@Param("client_ID") String client_ID);
    Client showMyInfo(@Param("client_ID") String client_ID);
    void createClient(@Param("client_ID") String client_ID, @Param("client_password") String client_password, @Param("client_name") String client_name, @Param("client_nickname") String client_nickname, @Param("profile_picture") String profile_picture);
    void deleteClient(@Param("client_ID") String client_ID);
    void updateClient(@Param("client_ID") String client_ID, @Param("client_password") String client_password, @Param("client_name") String client_name, @Param("client_nickname") String client_nickname, @Param("profile_picture") String profile_picture);
}
