package repository.database;

import domain.User;
import domain.validators.UserValidator;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UserDataBase extends AbstractDataBaseRepository<UUID, User> {

    public UserDataBase(String url, String username, String password, UserValidator validator) {
        super(url, username, password, validator, "\"User\"");
    }

    @Override
    protected void loadData() {
        findAll_DB().forEach(super::save);
    }

    @Override
    public User findOne(UUID id) {
        User user = null;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from " + tableName + " where id = '" + id.toString() + "';");
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
    protected Iterable<User> findAll_DB() {
        Set<User> users = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from " + tableName + ";");
             ResultSet resultSet = statement.executeQuery())
        {
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
                String sql = "INSERT INTO " + tableName + " "
                        + "VALUES ('" + entity.getId().toString() +"', '" + entity.getFirstName() + "', '"
                        + entity.getLastName() + "', '" + entity.getEmail() + "');";
                statement.executeQuery(sql);
            }
            catch (SQLException e) {
                System.out.println(e);
            }
        }
        return u;
    }

    @Override
    public User update(User entity) {
        User user = super.update(entity);
        if(! user.equals(entity))
        {
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Statement statement = connection.createStatement();
            String sql = "UPDATE " + tableName + " set firstName = '" + entity.getFirstName() + "' " + " where id = '" + entity.toString() + "';";
            statement.executeUpdate(sql);
            sql = "UPDATE " + tableName + " set lastName = '" + entity.getLastName() + "' " + " where id = '" + entity.toString() + "';";
            statement.executeUpdate(sql);
            sql = "UPDATE " + tableName + " set email = '" + entity.getEmail() + "' " + " where id = '" + entity.toString() + "';";
            statement.executeUpdate(sql);
            }
            catch (SQLException e) {
                System.out.println(e);
            }
        }

        return user;
    }
}
