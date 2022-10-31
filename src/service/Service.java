package service;

import domain.Entity;
import domain.FriendShip;
import domain.User;
import domain.validators.ValidationException;
import repository.Repository;

import java.util.List;
import java.util.UUID;

public class Service<ID, E extends Entity<ID>> {

    private Repository userRepo;
    private Repository friendshipRepo;

    public Service(Repository userRepo, Repository friendshipRepo) {
        this.userRepo = userRepo;
        this.friendshipRepo = friendshipRepo;
    }

    /**
     * The function adds an user to the userRepo
     * if there already exists an user with that id, we will show an error message,
     * if there are anny exceptions we will show an error message,
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

    /**
     * The function removes an user from the userRepo by a given ID
     * if there are anny exception we will show an error message
     * returns the user if we found one
     * and null otherwise
     */
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

    /**
     * The function creates a friendship between two users
     * if there are anny exception we will show an error message
     * returns true if the friendship is added
     * returns false if the friendship isn't added
     */
    public boolean createFriendship(ID id1, ID id2) {

        Entity<ID> f = null;
        try{
            User u1 = (User) userRepo.findOne(id1);
            User u2 = (User) userRepo.findOne(id2);
            if(u1 == null || u2 == null)
                throw new ValidationException("There are no users with these two ids!");

            f = friendshipRepo.save(new FriendShip(u1, u2));
        }
        catch (Exception e) {
            System.err.println(e);
            return false;
        }

        if(f != null) {
            System.err.println("These two users are already friends!");
            return false;
        }

        return true;
    }

    /**
     * The function deletes a friendship between two users
     */
    public boolean deleteFriendship(ID id1, ID id2) {
        return false;
    }

    public Iterable<User> getAllUsers() {
        return userRepo.findAll();
    }
}
