package com.plueone.server.models.Chat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "chats")
public class ChatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "user_one_id")
    private String userOneId;

    @Column(name = "user_two_id")
    private String userTwoId;

    public ChatEntity() {
    }

    public ChatEntity(String userOneId, String userTwoId) {
        this.userOneId = userOneId;
        this.userTwoId = userTwoId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getUserOneId() {
        return userOneId;
    }

    public void setUserOneId(String userOneId) {
        this.userOneId = userOneId;
    }

    public String getUserTwoId() {
        return userTwoId;
    }

    public void setUserTwoId(String userTwoId) {
        this.userTwoId = userTwoId;
    }

}
