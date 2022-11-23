package repository.database;

import domain.User;
import domain.validators.FriendshipValidator;
import domain.validators.UserValidator;
import domain.validators.Validator;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class UserDataBase extends AbstractDataBaseRepository<UUID, User> {

    public UserDataBase(String url, String username, String password, UserValidator validator) {
        super(url, username, password, validator, "\"User\"");
    }

    @Override
    protected void loadData() {
        findAll().forEach( u -> super.save(u) );
    }

    @Override
    public User findOne(UUID id) {
        User user = null;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from " + tableName + " where id = " + id.toString() + ";");
             ResultSet resultSet = statement.executeQuery())
        {
            resultSet.next();

            UUID id_ = UUID.fromString(resultSet.getString("id"));
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String email = resultSet.getString("email");

            user = new User(firstName, lastName, email);
            user.setId(id_);

            return user;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }


    @Override
    public Iterable<User> findAll() {
        Set<User> users = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from " + tableName + ";");
             ResultSet resultSet = statement.executeQuery())
        {
            System.out.println("A");
            while (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");

                User utilizator = new User(firstName, lastName, email);
                utilizator.setId(id);
                users.add(utilizator);
            }
            return users;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public User save(User entity) {
        User u = super.save(entity);
        if (u == null)
        {
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                Statement statement = connection.createStatement();
                String sql = "INSERT INTO " + tableName + " (id, firstName, lastName, email) "
                        + "VALUES ('" + entity.getId().toString() +"', '" + entity.getFirstName() + "', '"
                        + entity.getLastName() + "', '" + entity.getEmail() + "');";
                statement.executeUpdate(sql);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return u;
    }

    @Override
    public User delete(UUID id) {
        User user = super.delete(id);
        if(user != null)
        {

        }

        return  user;
    }

    @Override
    public User update(User entity) {
        return super.update(entity);
    }
}
