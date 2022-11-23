package repository.database;

import domain.FriendShip;
import domain.User;
import domain.validators.FriendshipValidator;
import domain.validators.UserValidator;

import java.util.List;
import java.util.UUID;

public class FriendShipDataBase extends AbstractDataBaseRepository<UUID, FriendShip> {

    public FriendShipDataBase(String url, String username, String password, FriendshipValidator validator) {
        super(url, username, password, validator, "\"Friendship\"");
    }

    @Override
    protected void loadData() {
        //findAll().forEach( f -> super.save(f) );
    }

    @Override
    public FriendShip findOne(UUID id) {
        return null;
    }


    @Override
    public Iterable<FriendShip> findAll() {
        return null;
    }

    @Override
    public FriendShip update(FriendShip entity) {
        return super.update(entity);
    }

    @Override
    public FriendShip save(FriendShip entity) {
        FriendShip e = super.save(entity);
        if (e == null)
        {

        }
        return e;

    }

    @Override
    public FriendShip delete(UUID id) {
        return super.delete(id);
    }
}
