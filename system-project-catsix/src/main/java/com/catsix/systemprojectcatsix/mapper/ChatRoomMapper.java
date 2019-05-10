package com.catsix.systemprojectcatsix.mapper;

import com.catsix.systemprojectcatsix.model.ChatRoom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatRoomMapper {
    List<ChatRoom> viewChatRooms(@Param("client_ID") String client_ID, @Param("team_ID") String team_ID);
    void createChatRoom(@Param("chat_room_ID") String chat_room_ID, @Param("chat_room_name") String chat_room_name);
    void inviteChatRoom(@Param("client_ID") String client_ID, @Param("team_ID") String team_ID, @Param("chat_room_ID") String chat_room_ID);
}
