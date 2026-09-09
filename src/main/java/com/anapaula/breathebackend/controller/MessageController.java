package com.anapaula.breathebackend.controller;

import com.anapaula.breathebackend.model.Message;
import com.anapaula.breathebackend.service.MessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public List<Message> getMessages() {
        return messageService.getAllMessages();
    }

    @PostMapping
    public Message createMessage(@RequestBody Message message) {
        return messageService.saveMessage(message);
    }

    @PostMapping("/import")
    public List<Message> importMessages(
            @RequestBody List<Message> messages) {
        return messageService.saveMessages(messages);
    }

    @PutMapping("/{id}")
    public Message updateMessage(
            @PathVariable Long id,
            @RequestBody Message message) {
        return messageService.updateMessage(id, message);
    }

    @GetMapping("/random")
    public Message getRandomMessage() {
        return messageService.getRandomMessage();
    }

    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
    }

}