package repository.file;

import domain.FriendShip;
import domain.User;
import domain.validators.Validator;

import java.util.List;
import java.util.UUID;

public class FriendshipFile extends AbstractFileRepository<UUID, FriendShip>{

    public FriendshipFile(String fileName, Validator<FriendShip> validator) {
        super(fileName, validator);
    }

    @Override
    public FriendShip extractEntity(List<String> attributes) {
        //TODO: implement method

       //FriendShip entity = new FriendShip();

        return null;
    }

    @Override
    protected String createEntityAsString(FriendShip entity) {
        return entity.getId() + ";" + entity.getUser1().getId() + ";" + entity.getUser2().getId() + ";" + entity.getFriendsFrom();
    }
}
