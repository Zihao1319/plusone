package com.plueone.server.models.Chat;

import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "messages")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long messageId;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "sender_id")
    private String senderId;

    @Column(name = "time_stamp")
    private String timeStamp;

    @Column(name = "content")
    private String content;

    public MessageEntity() {
    }

    public MessageEntity(Long messageId, Long chatId, String senderId, String timeStamp, String content) {
        this.messageId = messageId;
        this.chatId = chatId;
        this.senderId = senderId;
        this.timeStamp = timeStamp;
        this.content = content;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static JsonArray toJsonArr(List<MessageEntity> messages){

        JsonArrayBuilder jArr = Json.createArrayBuilder();
      
        for (MessageEntity m : messages){

            JsonObject json = Json.createObjectBuilder()
                            .add("messageId", m.getMessageId())
                            .add("chatId", m.getChatId())
                            .add("senderId", m.getSenderId())
                            .add("timeStamp", m.getTimeStamp())
                            .build();
            jArr.add(json);
        }
        return jArr.build();
    }
}
