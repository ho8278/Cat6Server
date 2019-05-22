package com.catsix.systemprojectcatsix.model;

public class ServerResponse<T> {
    private int result;
    private T data;
    private String cookie;

    public void setData(T data) {
        this.data = data;
        if(data == null) {
            result = 100;
        }
        else {
            result = 200;
        }
    }
    public T getData() {
        return data;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
