package service;

import domain.Entity;
import domain.FriendShip;
import domain.User;
import domain.validators.ValidationException;

public interface Service<ID> {

    /**
     *
     * @param user - the entity that has to be added
     * @return true if the entity was added
     *         false otherwise
     * @throws IllegalArgumentException
     *                  if entity is null
     * @throws ValidationException
     *                  if the entity validation did not work.
     */
    boolean addUser(Entity<ID> user);


    /**
     *
     * @param id - the id of the entity that we have to remove
     * @return the entity that was removed if there was any
     *         null otherwise
     * @throws IllegalArgumentException
     *                  if id is null
     */
    Entity<ID> deleteUser(ID id);


    /**
     *
     * @param id1 and
     * @param id2 - the ids of the user we have to create a friendship between
     *
     * @return true if the entity was added
     *         false otherwise
     * @throws IllegalArgumentException
     *                  if any of the ids are null
     * @throws ValidationException
     *                  if the ids are the same (you cannot add yourself as a friend)
     */
    boolean createFriendship(ID id1, ID id2);


    //TODO : deocumentation for deleteFriendship
    boolean deleteFriendship(ID id1, ID id2);


    /**
     * @return an Iterable of all the users
     */
    Iterable<User> getAllUsers();


    /**
     * @return an Iterable of all the friendships
     */
    Iterable<FriendShip> getAllFriendships();


    /**
     * Adds predefined users and friendships
     */
    void Add_Predefined_Values();
}
