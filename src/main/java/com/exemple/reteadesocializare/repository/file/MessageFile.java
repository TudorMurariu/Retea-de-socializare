package com.exemple.reteadesocializare.repository.file;

import com.exemple.reteadesocializare.domain.Message;
import com.exemple.reteadesocializare.domain.User;
import com.exemple.reteadesocializare.domain.validators.Validator;
import com.exemple.reteadesocializare.repository.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class MessageFile extends AbstractFileRepository<UUID, Message>{

    Repository<UUID, User> userRepository;
    public MessageFile(String fileName, Validator<Message> validator, Repository<UUID, User> userRepository) {
        super(validator);
        this.userRepository = userRepository;
        this.fileName = fileName;
        super.loadData();
    }

    @Override
    public Message extractEntity(List<String> attributes) {
        // we get the sender
        User sender = (User) userRepository.findOne(UUID.fromString(attributes.get(1)));

        // we get the reciver
        User reciver = (User) userRepository.findOne(UUID.fromString(attributes.get(2)));

        Message entity = new Message(attributes.get(3) ,sender, reciver);
        entity.setId(UUID.fromString(attributes.get(0)));
        entity.setDate(LocalDateTime.parse(attributes.get((4))));

        return entity;
    }

    @Override
    protected String createEntityAsString(Message entity) {
        return entity.getId() + ";" + entity.getSender().getId() + ";" + entity.getReciver().getId() + ";" + entity.getText() + ";" + entity.getDate();
    }
}
