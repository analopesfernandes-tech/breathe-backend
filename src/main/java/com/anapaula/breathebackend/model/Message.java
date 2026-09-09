package com.anapaula.breathebackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private String author;

    private String source;

    private String category;

    public Message() {
    }

    public Message(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }
    public String getAuthor() {
    return author;
}

public String getSource() {
    return source;
}

public String getCategory() {
    return category;
}

    public void setText(String text) {
        this.text = text;
    }

    public void setAuthor(String author) {
    this.author = author;
}

public void setSource(String source) {
    this.source = source;
}

public void setCategory(String category) {
    this.category = category;
}

}
