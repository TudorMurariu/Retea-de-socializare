package com.exemple.reteadesocializare.repository.database;

import com.exemple.reteadesocializare.domain.Entity;
import com.exemple.reteadesocializare.domain.validators.Validator;
import com.exemple.reteadesocializare.repository.memory.InMemoryRepository;

import java.sql.*;

public abstract class AbstractDataBaseRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID,E> {
    protected String url;
    protected String username;
    protected String password;
    protected String tableName;

    public AbstractDataBaseRepository(Validator<E> validator) {
        super(validator);
    }

    public AbstractDataBaseRepository(String url, String username, String password, Validator<E> validator, String tableName) {
        super(validator);
        this.url = url;
        this.username = username;
        this.password = password;
        this.tableName = tableName;
        loadData();
    }

    protected abstract void loadData();

    protected abstract Iterable<E> findAll_DB();

    @Override
    public E delete(ID id) {
        E entity = super.delete(id);
        if(entity != null)
        {
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement statement =
                         connection.prepareStatement("DELETE from " + tableName +
                                 " where id = '" + id.toString() + "';");
                 ResultSet resultSet = statement.executeQuery();)
            {

            }
            catch (SQLException e) {
                System.out.println(e);
            }
        }
        return  entity;
    }
}
