package com.catsix.systemprojectcatsix.service;

import com.catsix.systemprojectcatsix.mapper.ChatRoomMapper;
import com.catsix.systemprojectcatsix.model.ChatRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRoomService {
    @Autowired
    ChatRoomMapper chatRoomMapper;

    public List<ChatRoom> viewChatRooms(String client_ID, String team_ID) { return chatRoomMapper.viewChatRooms(client_ID, team_ID); }
    public void createChatRoom(String chat_room_ID, String chat_room_name) { chatRoomMapper.createChatRoom(chat_room_ID, chat_room_name); }
    public void inviteChatRoom(String client_ID, String team_ID, String chat_room_ID) { chatRoomMapper.inviteChatRoom(client_ID, team_ID, chat_room_ID); }
}
