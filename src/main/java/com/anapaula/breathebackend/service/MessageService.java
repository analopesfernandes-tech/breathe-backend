package com.anapaula.breathebackend.service;

import com.anapaula.breathebackend.model.Message;
import com.anapaula.breathebackend.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> saveMessages(List<Message> messages) {
    return messageRepository.saveAll(messages);
}

    public Message getRandomMessage() {
        return messageRepository.findRandomMessage();
    }

    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }

    public Message updateMessage(Long id, Message message) {
        Message existingMessage = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        existingMessage.setText(message.getText());
        existingMessage.setAuthor(message.getAuthor());
        existingMessage.setSource(message.getSource());
        existingMessage.setCategory(message.getCategory());

        return messageRepository.save(existingMessage);
    }
}