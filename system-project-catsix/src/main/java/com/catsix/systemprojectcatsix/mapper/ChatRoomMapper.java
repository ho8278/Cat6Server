package com.catsix.systemprojectcatsix.mapper;

import com.catsix.systemprojectcatsix.model.ChatRoom;
import com.catsix.systemprojectcatsix.model.Client;
import com.catsix.systemprojectcatsix.model.File;
import com.catsix.systemprojectcatsix.model.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatRoomMapper {
    List<ChatRoom> viewChatRooms(@Param("client_ID") String client_ID, @Param("team_ID") String team_ID);
    void createChatRoom(@Param("chat_room_ID") String chat_room_ID, @Param("chat_room_name") String chat_room_name, @Param("team_ID") String team_ID);
    void inviteChatRoom(@Param("client_ID") String client_ID, @Param("chat_room_ID") String chat_room_ID);
    void notice(@Param("notice_ID") String notice_ID, @Param("notice_contents") String notice_contents, @Param("chat_room_ID") String chat_room_ID);
    List<Notice> viewNotice(@Param("chat_room_ID") String chat_room_ID);
    void deleteNotice(@Param("chat_room_ID") String chat_room_ID);
    List<Client> viewParticipateClients(@Param("chat_room_ID") String chat_room_ID);
}
