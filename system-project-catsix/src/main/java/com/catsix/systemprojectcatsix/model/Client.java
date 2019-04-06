package com.catsix.systemprojectcatsix.model;

public class Client {
    private String client_ID;
    private String client_password;
    private String client_name;
    private String client_nickname;
    private String profile_picture;

    public String getClient_ID() {
        return client_ID;
    }
    public void setClient_ID(String client_ID) {
        this.client_ID = client_ID;
    }

    public String getClient_password() {
        return client_password;
    }
    public void setClient_password(String client_password) {
        this.client_password = client_password;
    }

    public String getClient_name() {
        return client_name;
    }
    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getClient_nickname() {
        return client_nickname;
    }
    public void setClient_nickname(String client_nickname) {
        this.client_nickname = client_nickname;
    }

    public String getProfile_picture() {
        return profile_picture;
    }
    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }
}
