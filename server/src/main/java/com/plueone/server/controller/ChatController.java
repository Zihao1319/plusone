package com.plueone.server.controller;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.plueone.server.models.Chat.ChatEntity;
import com.plueone.server.models.Chat.MessageEntity;
import com.plueone.server.repo.ChatRepo.ChatRepo;
import com.plueone.server.repo.ChatRepo.MessageRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@Controller
@RequestMapping(path = "/api")
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ChatRepo chatRepo;

    @Autowired
    private MessageRepo msgRepo;

    @MessageMapping("/chat/{chatId}")
    public void sendMessage(@DestinationVariable Long chatId, MessageEntity message) {
        System.out.println("handling send message: " + message + " chatId: " + chatId);

        message.setChatId(chatId);
        message.setTimeStamp(generateTimeStamp());
        message = msgRepo.save(message);
        simpMessagingTemplate.convertAndSend("/topic/messages/" + chatId, message);
    }

    private String generateTimeStamp() {
        Instant i = Instant.now();
        String date = i.toString();

        int endRange = date.indexOf('T');
        date = date.substring(0, endRange);
        date = date.replace('-', '/');

        String time = Integer.toString(i.atZone(ZoneOffset.UTC).getHour() + 1);
        time += ":";

        int minutes = i.atZone(ZoneOffset.UTC).getMinute();
        if (minutes > 9) {
            time += Integer.toString(minutes);
        } else {
            time += "0" + Integer.toString(minutes);
        }

        String timeStamp = date + "-" + time;
        return timeStamp;
    }

    @GetMapping("/getchatid")
    private ResponseEntity<String> getChatIdFromUserIds(@RequestParam("id1") String userId1,
            @RequestParam("id2") String userId2) {

        ChatEntity ce = getChatId(userId1, userId2);

        if (ce == null) {

            JsonObject json = Json.createObjectBuilder()
                    .add("message", "chat id not found")
                    .build();

            return ResponseEntity
                    .status(404)
                    .body(json.toString());
        } else {

            JsonObject json = Json.createObjectBuilder()
                    .add("chatId", ce.getChatId())
                    .build();

            return ResponseEntity
                    .status(200)
                    .body(json.toString());
        }

    }

    @GetMapping("/create/newchatid")
    private ResponseEntity<String> createChatId(@RequestParam("id1") String userId1,
            @RequestParam("id2") String userId2) {

        ChatEntity ce = getChatId(userId1, userId2);

        if (ce != null) {
            JsonObject json = Json.createObjectBuilder()
                    .add("chatId", ce.getChatId())
                    .build();

            return ResponseEntity
                    .status(200)
                    .body(json.toString());

        } else {
            ChatEntity newChat = new ChatEntity(userId1, userId2);
            Long newId = chatRepo.save(newChat).getChatId();

            JsonObject json = Json.createObjectBuilder()
                    .add("chatId", newId)
                    .build();

            return ResponseEntity
                    .status(200)
                    .body(json.toString());

        }
    }

    @GetMapping("/getmessages")
    public ResponseEntity<List<MessageEntity>> getMessages(@RequestParam("chatId") Long chatId) {

        List<MessageEntity> messages = msgRepo.findAllByChatId(chatId);

        if (messages.isEmpty()) {
            messages = new ArrayList<MessageEntity>();

            return ResponseEntity
                    .ok()
                    .body(messages);

        } else {
            return ResponseEntity
                    .ok()
                    .body(messages);
        }

        // JsonObject json = Json.createObjectBuilder()
        // .add("chats", MessageEntity.toJsonArr(messages))
        // .build();

        // return ResponseEntity
        // .status(200)
        // .body(json);

        // ChatEntity ce = chatRepo.findByChatId(chatId);
        // System.out.printf(">>>>>>>>%d\n", ce.getChatId());

        // if (ce != null) {

        // List<MessageEntity> messages = msgRepo.findAllByChatId(ce.getChatId());

        // JsonObject json = Json.createObjectBuilder()
        // .add("chats", MessageEntity.toJsonArr(messages))
        // .build();

        // return ResponseEntity
        // .status(200)
        // .body(json.toString());

        // } else {

        // // JsonObject json = Json.createObjectBuilder()
        // // .add("chats", "")
        // // .build();

        // return ResponseEntity
        // .status(404)
        // .body("not found");
        // }
    }

    @GetMapping("/getchats")
    public ResponseEntity<List<ChatEntity>> getChats(@RequestParam("id") String userId) {

        Optional<List<ChatEntity>> list1 = chatRepo.findByUserOneId(userId);
        Optional<List<ChatEntity>> list2 = chatRepo.findByUserTwoId(userId);

        List<ChatEntity> combinedList = new ArrayList<>();

        if (list1.isPresent()) {
            combinedList.addAll(list1.get());
        }

        if (list2.isPresent()) {
            combinedList.addAll(list2.get());
        }

        return ResponseEntity
                .ok()
                .body(combinedList);

    }

    public ChatEntity getChatId(String userId1, String userId2) {

        ChatEntity ce = chatRepo.findByUserOneIdAndUserTwoId(userId1, userId2);

        if (ce != null) {
            return ce;

        } else {
            return chatRepo.findByUserOneIdAndUserTwoId(userId2, userId1);
        }

    }

}
