package service;

import domain.Entity;
import repository.Repository;

public class Service<ID, E extends Entity<ID>> {

    private Repository userRepo;

    public Service(Repository userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * Functia adauga un user in repo
     * daca exista deja un user cu id ul respectiv se va afisa un mesaj de eroare,
     * daca apare o exceptie se va afisa mesaj de eroare.
     * returns true if the entity is added
     * returns false if the entity isn't added
     */
    public boolean addUser(Entity<ID> user) {
        Entity<ID> u = null;
        try{
            u = userRepo.save(user);
        }
        catch (Exception e) {
            System.err.println(e);
            return false;
        }

        if(u != null) {
            System.err.println("Exista deja un user cu acest ID!");
            return false;
        }

        return true;
    }

    public Entity<ID> deleteUser(ID id) {
        Entity<ID> u = null;
        try{
            u = userRepo.delete(id);
        }
        catch (Exception e) {
            System.err.println(e);
            return null;
        }

        if(u == null) {
            System.err.println("Nu exista nici o entitate cu acest id!");
            return null;
        }

        return u;
    }

    public boolean createFriendship(ID id1, ID id2) {
        return false;
    }

    public boolean deleteFriendship(ID id1, ID id2) {
        return false;
    }
}
