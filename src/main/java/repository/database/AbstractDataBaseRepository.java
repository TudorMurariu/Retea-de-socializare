package repository.database;

import domain.Entity;
import domain.User;
import domain.validators.UserValidator;
import domain.validators.Validator;
import repository.memory.InMemoryRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

public abstract class AbstractDataBaseRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID,E> {
    protected String url;
    protected String username;
    protected String password;
    protected String tableName;

    public AbstractDataBaseRepository(String url, String username, String password, Validator<E> validator, String tableName) {
        super(validator);
        this.url = url;
        this.username = username;
        this.password = password;
        this.tableName = tableName;
        loadData();
    }

    protected abstract void loadData();

    @Override
    public E delete(ID id) {
        E entity = super.delete(id);
        if(entity != null)
        {
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                Statement statement = connection.createStatement();
                String sql = "DELETE from " + tableName + " where id = '" + id.toString() + "';";
                statement.executeUpdate(sql);
            }
            catch (SQLException e) {

            }
        }
        return  entity;
    }
}
