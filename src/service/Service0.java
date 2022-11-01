package service;

import domain.Entity;
import domain.FriendShip;
import domain.User;
import domain.validators.ValidationException;
import repository.Repository;

public class Service0<ID> implements Service<ID>{

    private Repository userRepo;
    private Repository friendshipRepo;

    public Service0(Repository userRepo, Repository friendshipRepo) {
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
    @Override
    public boolean addUser(Entity user) {
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
    @Override
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
    @Override
    public boolean createFriendship(ID id1, ID id2) {

        Entity<ID> f = null;
        User u1, u2;
        try{
            if(id1 == null || id2 == null)
                throw new IllegalArgumentException("ids must not be null!");

            u1 = (User) userRepo.findOne(id1);
            u2 = (User) userRepo.findOne(id2);
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

        u1.addFriend(u2);
        u2.addFriend(u1);
        return true;
    }

    /**
     * The function deletes a friendship between two users
     */
    @Override
    public boolean deleteFriendship(ID id1, ID id2) {
        return false;
    }

    @Override
    public Iterable<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public Iterable<FriendShip> getAllFriendships() {
        return friendshipRepo.findAll();
    }

    @Override
    public void Add_Predefined_Values() {

    }

}
