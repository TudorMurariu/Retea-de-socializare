package com.exemple.reteadesocializare.service;

import com.exemple.reteadesocializare.domain.Message;
import com.exemple.reteadesocializare.repository.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.UUID;

public class MessageService0 implements MessageService {
    private final Repository messageRepo;

    public MessageService0(Repository messageRepo) {
        this.messageRepo = messageRepo;
    }

    @Override
    public boolean addMessage(Message message) {
        Message m = null;
        try {
            m = (Message) messageRepo.save(message);
        }
        catch (Exception e) {
            System.err.println(e);
            return false;
        }

        if(m != null) {
            System.err.println("Exista deja un user cu acest ID!");
            return false;
        }

        return true;
    }

    @Override
    public Message deleteMessage(UUID id) {
        Message m = null;

        try {
            m = (Message) messageRepo.delete(id);
        }
        catch (Exception e) {
            System.err.println(e);
        }

        return m;
    }

    @Override
    public Iterable<Message> getAllMessages() {
        ArrayList<Message> l =  (ArrayList) messageRepo.findAll();

        l.sort(new Comparator<Message>() {
            @Override
            public int compare(Message m1, Message m2) {
                return m1.getDate().compareTo(m2.getDate());
            }
        });

        return l;
    }
}
