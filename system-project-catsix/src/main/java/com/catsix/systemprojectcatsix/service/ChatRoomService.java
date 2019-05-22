package com.catsix.systemprojectcatsix.service;

import com.catsix.systemprojectcatsix.mapper.ChatRoomMapper;
import com.catsix.systemprojectcatsix.model.ChatRoom;
import com.catsix.systemprojectcatsix.model.Client;
import com.catsix.systemprojectcatsix.model.File;
import com.catsix.systemprojectcatsix.model.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRoomService {
    @Autowired
    ChatRoomMapper chatRoomMapper;

    public List<ChatRoom> viewChatRooms(String client_ID, String team_ID) { return chatRoomMapper.viewChatRooms(client_ID, team_ID); }
    public void createChatRoom(String chat_room_ID, String chat_room_name, String team_ID) { chatRoomMapper.createChatRoom(chat_room_ID, chat_room_name, team_ID); }
    public void inviteChatRoom(String client_ID, String chat_room_ID) { chatRoomMapper.inviteChatRoom(client_ID, chat_room_ID); }
    public void notice(String notice_ID, String notice_contents, String chat_room_ID){ chatRoomMapper.notice(notice_ID, notice_contents ,chat_room_ID);}
    public List<Notice> viewNotice(String chat_room_ID) { return chatRoomMapper.viewNotice(chat_room_ID); }
    public void deleteNotice(String chat_room_ID) { chatRoomMapper.deleteNotice(chat_room_ID); }
    public List<Client> viewParticipateClients(String chat_room_ID) { return chatRoomMapper.viewParticipateClients(chat_room_ID); }
}
