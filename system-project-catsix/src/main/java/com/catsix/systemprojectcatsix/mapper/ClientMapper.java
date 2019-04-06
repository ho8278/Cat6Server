package com.catsix.systemprojectcatsix.mapper;

import com.catsix.systemprojectcatsix.model.Client;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ClientMapper {
    String getCurrentDateTime() throws Exception;
    Client login(@Param("client_ID") String client_ID, @Param("client_password") String client_password);
}
