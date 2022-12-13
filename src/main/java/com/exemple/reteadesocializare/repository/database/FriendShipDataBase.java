package com.exemple.reteadesocializare.repository.database;

import com.exemple.reteadesocializare.domain.FriendShip;
import com.exemple.reteadesocializare.domain.User;
import com.exemple.reteadesocializare.domain.validators.FriendshipValidator;
import com.exemple.reteadesocializare.repository.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FriendShipDataBase extends AbstractDataBaseRepository<UUID, FriendShip> {

    Repository userRepo;
    public FriendShipDataBase(String url, String username, String password, FriendshipValidator validator, Repository userRepo) {
        super(validator);
        this.url = url;
        this.username = username;
        this.password = password;
        this.tableName = "\"FriendShip\"";
        this.userRepo = userRepo;
        loadData();
    }

    @Override
    protected void loadData() {
        findAll_DB().forEach( f -> super.save(f) );
    }

    @Override
    public FriendShip findOne(UUID id) {
        FriendShip friendShip = null;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from " + tableName + " where id = " + id.toString() + ";");
             ResultSet resultSet = statement.executeQuery())
        {
            resultSet.next();

            UUID id_ = UUID.fromString(resultSet.getString("id"));
            UUID id1 = UUID.fromString(resultSet.getString("id1"));
            UUID id2 = UUID.fromString(resultSet.getString("id2"));
            LocalDateTime from = LocalDateTime.parse(resultSet.getString("friendsFrom"));

            User u1 = (User)userRepo.findOne(id1);
            User u2 = (User)userRepo.findOne(id2);
            friendShip = new FriendShip(u1, u2, from);
            friendShip.setId(id_);

            return friendShip;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return friendShip;
    }


    @Override
    protected Iterable<FriendShip> findAll_DB() {
        Set<FriendShip> friendShips = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from " + tableName + ";");
             ResultSet resultSet = statement.executeQuery())
        {
            while (resultSet.next()) {
                UUID id_ = UUID.fromString(resultSet.getString("id"));
                UUID id1 = UUID.fromString(resultSet.getString("id1"));
                UUID id2 = UUID.fromString(resultSet.getString("id2"));
                LocalDateTime from = LocalDateTime.parse(resultSet.getString("friendsFrom"));

                User u1 = (User)userRepo.findOne(id1);
                User u2 = (User)userRepo.findOne(id2);

                u1.addFriend(u2);
                u2.addFriend(u1);

                FriendShip friendShip = new FriendShip(u1, u2, from);
                friendShip.setId(id_);

                friendShips.add(friendShip);
            }
            return friendShips;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return friendShips;
    }

    @Override
    public FriendShip save(FriendShip entity) {
        FriendShip f = super.save(entity);
        if (f == null)
        {
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement statement = connection.prepareStatement("INSERT INTO " + tableName + " "
                        + "VALUES ('" + entity.getId().toString() +"', '" + entity.getFriendsFrom().toString() + "', '"
                        + entity.getUser1().getId().toString() + "', '" + entity.getUser2().getId().toString()  + "');");
                 ResultSet resultSet = statement.executeQuery())
            {

            }
            catch (SQLException e) {
                System.out.println(e);
            }
        }
        return f;
    }

    @Override
    public FriendShip update(FriendShip entity) {
        FriendShip friendShip = super.update(entity);
        if(! friendShip.equals(entity))
        {
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                Statement statement = connection.createStatement();
                String sql = "UPDATE " + tableName + " set id1 = '" + entity.getUser1().getId().toString() + "' " + " where id = '" + entity.toString() + "';";
                statement.executeUpdate(sql);
                sql = "UPDATE " + tableName + " set id2 = '" + entity.getUser2().getId().toString() + "' " + " where id = '" + entity.toString() + "';";
                statement.executeUpdate(sql);
                sql = "UPDATE " + tableName + " set friendsFrom = '" + entity.getFriendsFrom().toString() + "' " + " where id = '" + entity.toString() + "';";
                statement.executeUpdate(sql);
            }
            catch (SQLException e) {
                System.out.println(e);
            }
        }

        return friendShip;
    }
}
