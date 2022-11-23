package repository.database;

import domain.Entity;
import domain.User;
import domain.validators.UserValidator;
import domain.validators.Validator;
import repository.memory.InMemoryRepository;

import java.util.List;

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
}
