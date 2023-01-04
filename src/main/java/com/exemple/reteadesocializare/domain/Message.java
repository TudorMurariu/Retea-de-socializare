package com.exemple.reteadesocializare.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Message extends Entity<UUID> {
    private String text;
    private User sender;
    private User reciver;
    private LocalDateTime date;

    public Message(String mesage, User sender, User reciver) {
        this.text = mesage;
        this.sender = sender;
        this.reciver = reciver;
        this.date = LocalDateTime.now();
        this.setId(UUID.randomUUID());
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReciver() {
        return reciver;
    }

    public void setReciver(User reciver) {
        this.reciver = reciver;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
