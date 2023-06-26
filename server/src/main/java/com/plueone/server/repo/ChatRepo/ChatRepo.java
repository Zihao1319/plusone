package com.plueone.server.repo.ChatRepo;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.plueone.server.models.Chat.ChatEntity;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ChatRepo extends JpaRepository<ChatEntity, Long> {

    boolean existsByUserOneId(String userId);

    boolean existsByUserTwoId(String userId);

    ChatEntity findByUserOneIdAndUserTwoId(String userId1, String userId2);

    ChatEntity findByChatId(Long chatId);

    Optional<List<ChatEntity>> findByUserOneId(String userId);

    Optional<List<ChatEntity>> findByUserTwoId(String userId);

}
