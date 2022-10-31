package repository.file;;

import domain.User;
import domain.validators.Validator;

import java.util.List;
import java.util.UUID;

public class UserFile extends AbstractFileRepository<UUID, User> {

    public UserFile(String fileName, Validator<User> validator) {
        super(fileName, validator);
    }

    @Override
    public User extractEntity(List<String> attributes) {
        //TODO: implement method
        User user = new User(attributes.get(1), attributes.get(2), attributes.get(3));
        //user.setId(Long.parseLong(attributes.get(0)));

        return user;
    }

    @Override
    protected String createEntityAsString(User entity) {
        return entity.getId() + ";" + entity.getFirstName() + ";" + entity.getLastName();
    }
}
