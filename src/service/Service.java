package service;

import domain.Entity;
import repository.Repository;

public class Service<ID, E extends Entity<ID>> {
    
    Repository repo;

    public Service(Repository repo) {
        this.repo = repo;
    }

    public boolean addUser(Entity<ID> user) {
        return false;
    }

    public boolean deleteUser(ID id) {
        return false;
    }

    public boolean createFriendship(Entity<ID> user1, Entity<ID> user2) {
        return false;
    }

    public boolean deleteFriendship(Entity<ID> user1, Entity<ID> user2) {
        return false;
    }
}
