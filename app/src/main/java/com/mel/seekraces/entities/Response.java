package com.mel.seekraces.entities;

import java.io.Serializable;

/**
 * Created by void on 15/01/2017.
 */

public class Response implements Serializable{
    private String message;
    private String content;
    private boolean isOk;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    @Override
    public String toString() {
        return "Response{" +
                "message='" + message + '\'' +
                ", content='" + content + '\'' +
                ", isOk=" + isOk +
                '}';
    }
}
