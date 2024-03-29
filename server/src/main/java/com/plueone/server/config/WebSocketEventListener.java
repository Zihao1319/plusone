// package com.plueone.server.config;

// import com.plueone.server.models.ChatMessage;
// import com.plueone.server.models.MessageType;

// import org.springframework.context.event.EventListener;
// import org.springframework.messaging.simp.SimpMessageSendingOperations;
// import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
// import org.springframework.stereotype.Component;
// import org.springframework.web.socket.messaging.SessionDisconnectEvent;

// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;

// @Component
// @RequiredArgsConstructor
// @Slf4j
// public class WebSocketEventListener {

// private final SimpMessageSendingOperations messageTemplate;

// @EventListener
// public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
// StompHeaderAccessor headerAccessor =
// StompHeaderAccessor.wrap(event.getMessage());
// String username = (String)
// headerAccessor.getSessionAttributes().get("username");
// if (username != null) {
// System.out.printf(">>>>>>>>>User disconnects: %s\n", username);
// var chatMessage = ChatMessage.builder()
// .messageType(MessageType.LEAVE)
// .sender(username)
// .build();

// messageTemplate.convertAndSend("/topic/public", chatMessage);

// }
// }

// }
