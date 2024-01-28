package com.example.whatsapp.Model;

public class MessageModel {
    String uId, message, messageId;
    Long timeStamp;

    public MessageModel() {}

    public MessageModel(String uId, String message, Long timeStamp) {
        this.uId = uId;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public MessageModel(String uId, String message) {
        this.uId = uId;
        this.message = message;
    }

    //Setters
    public void setuId(String uId) {
        this.uId = uId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }


    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    //Getters
    public String getMessageId() {
        return messageId;
    }
    public String getuId() {
        return uId;
    }

    public String getMessage() {
        return message;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }
}
