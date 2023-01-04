package com.exemple.reteadesocializare.repository.file;

import com.exemple.reteadesocializare.domain.FriendRequest;
import com.exemple.reteadesocializare.domain.FriendShip;
import com.exemple.reteadesocializare.domain.User;
import com.exemple.reteadesocializare.domain.validators.Validator;
import com.exemple.reteadesocializare.repository.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class FriendshipFile extends AbstractFileRepository<UUID, FriendShip>{

    Repository<UUID, User> userRepository;
    public FriendshipFile(String fileName, Validator<FriendShip> validator, Repository<UUID, User> userRepository) {
        super(validator);
        this.userRepository = userRepository;
        this.fileName = fileName;
        super.loadData();
    }

    @Override
    public FriendShip extractEntity(List<String> attributes) {

        // we get the first User
        User u1 = (User) userRepository.findOne(UUID.fromString(attributes.get(1)));

        // we get the second User
        User u2 = (User) userRepository.findOne(UUID.fromString(attributes.get(2)));

        FriendShip entity = new FriendShip(u1, u2);
        entity.setId(UUID.fromString(attributes.get(0)));
        entity.setFriendsFrom(LocalDateTime.parse(attributes.get((3))));

        if(FriendRequest.valueOf(attributes.get((4))).equals(FriendRequest.ACCEPTED))
        { // we add them to each-other's friendliest
            u1.addFriend(u2);
            u2.addFriend(u1);
        }
        entity.setAcceptance(FriendRequest.valueOf(attributes.get((4))));

        return entity;
    }

    @Override
    protected String createEntityAsString(FriendShip entity) {
        return entity.getId() + ";" + entity.getUser1().getId() + ";" + entity.getUser2().getId() + ";" + entity.getFriendsFrom() + ";" + entity.getAcceptance();
    }
}
