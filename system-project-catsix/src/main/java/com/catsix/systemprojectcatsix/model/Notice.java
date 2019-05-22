package com.catsix.systemprojectcatsix.model;

public class Notice {
    private String notice_ID;
    private String notice_contents;
    private String notice_chat_room_ID;

    public String getNotice_ID() { return notice_ID; }

    public String getNotice_contents() { return notice_contents; }

    public String getNotice_chat_room_ID() { return notice_chat_room_ID; }

    public void setNotice_ID(String notice_ID) { this.notice_ID = notice_ID; }

    public void setNotice_contents(String notice_contents) { this.notice_contents = notice_contents; }

    public void setNotice_chat_room_ID(String notice_chat_room_ID) { this.notice_chat_room_ID = notice_chat_room_ID; }

}
