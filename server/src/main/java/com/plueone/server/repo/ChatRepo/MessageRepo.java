package com.plueone.server.repo.ChatRepo;

import java.util.List;

import com.plueone.server.models.Chat.MessageEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepo extends JpaRepository<MessageEntity, String> {

    List<MessageEntity> findAllByChatId(long chatId);

    List<MessageEntity> findAllByChatIdAndSenderId(long chatId, String senderId);
}
