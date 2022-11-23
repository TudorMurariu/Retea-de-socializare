package service;

import domain.Entity;
import domain.FriendShip;
import domain.User;
import domain.validators.ValidationException;
import repository.Repository;

import java.util.*;
public class Service0 implements Service<UUID>{

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
    public boolean addUser(User user) {
        Entity<UUID> u = null;
        try{
            if(user.getEmail() == null)
                throw new IllegalArgumentException("The Email mustn't be null!");
            else if(getUserByEmail(user.getEmail()) != null)
                throw new IllegalArgumentException("There is another user with this email account!");
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
     * The function removes an user from the userRepo by a given email
     * if there are anny exception we will show an error message
     * returns the user if we found one
     * and null otherwise
     *
     * we also have to delete all the friendships that include this user in the friendshipRepo.
     */
    @Override
    public Entity<UUID> deleteUser(String email) {
        User u = null;
        try{
            u = getUserByEmail(email);
            if(u == null) {
                System.err.println("Nu exista nici un user cu acest id!");
                return null;
            }

            for(User friend : u.getFriends())
            {
                for(FriendShip f : (Iterable<FriendShip>) friendshipRepo.findAll())
                    if((f.getUser1().equals(u) && f.getUser2().equals(friend)) || (f.getUser1().equals(friend) && f.getUser2().equals(u)))
                    {
                        friendshipRepo.delete(f.getId());
                        break;
                    }
                friend.removeFriend(u);
            }


            u = (User) userRepo.delete(u.getId());
        }
        catch (Exception e) {
            System.err.println(e);
            return null;
        }

        if(u == null) {
            System.err.println("Nu exista nici un user cu acest id!");
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
    public boolean createFriendship(String email1, String email2) {

        Entity<UUID> f = null;
        User u1, u2;
        try{
            if(email1 == null || email2 == null)
                throw new IllegalArgumentException("Emails must not be null!");

            u1 = getUserByEmail(email1);
            u2 = getUserByEmail(email2);
            if(u1 == null || u2 == null || u1.equals(u2))
                throw new ValidationException("There are no two users with these two emails!");

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
     *  @param email1 and
     *  @param email2 - the emails of the user we have to create a friendship between
     *
     *  @return the friendship if it exists
     *          null otherwise
     *  @throws IllegalArgumentException
     *                if any of the emails are null
     */
    @Override
    public Entity<UUID> deleteFriendship(String email1, String email2) {
        Entity<UUID> f = null;
        User u1, u2;
        try{
            if(email1 == null || email2 == null)
                throw new IllegalArgumentException("Emails must not be null!");
            u1 = getUserByEmail(email1);
            u2 = getUserByEmail(email2);

            if(u1 == null || u2 == null || u1.equals(u2))
                throw new ValidationException("There are no two users with these two emails!!");

            Iterable<FriendShip> l = friendshipRepo.findAll();
            for(FriendShip el : l)
                if(
                        (el.getUser1().getId().equals(u1.getId()) && el.getUser2().getId().equals(u2.getId()))
                        || (el.getUser1().getId().equals(u2.getId()) && el.getUser2().getId().equals(u1.getId()))
                )
                {
                    f = friendshipRepo.delete(el.getId());
                    break;
                }
        }
        catch (Exception e) {
            System.err.println(e);
            return null;
        }

        if(f == null) {
            System.err.println("These users are not friends!");
            return null;
        }

        u1.removeFriend(u2);
        u2.removeFriend(u1);
        return f;
    }

    /**
     * @return an Iterable of all the users
     */
    @Override
    public Iterable<User> getAllUsers() {
        return userRepo.findAll();
    }

    /**
     * @return an Iterable of all the friendships
     */
    @Override
    public Iterable<FriendShip> getAllFriendships() {
        return friendshipRepo.findAll();
    }

    /**
     * Adds predefined users and friendships
     * @param i : int - represents what iteration of predefined values we add to the repos
     */
    @Override
    public void add_Predefined_Values(int i) {
        switch (i)
        {
            case 1:
                // Doua cuminitati, cu drumul maxim fiind 2
                {
                    User u1 = new User("Marian", "Popescu", "marian.popescu@yahoo.com");
                    User u2 = new User("Gabi", "Paslaru", "gabriel.paslaru@yahoo.com");
                    User u3 = new User("Stefan", "Nastasa", "stefan.nastasa@yahoo.com");
                    User u4 = new User("Stefan", "Atumulesei", "stefan.atumulesei@gmail.com");
                    User u5 = new User("Ana", "Maria", "ana.maria@gmail.com");
                    User u6 = new User("Nicu", "Margine", "nicu.hanganu@yahoo.com");
                    User u7 = new User("Nicu", "Pop", "nicu.pop@yahoo.com");

                    this.addUser(u1);
                    this.addUser(u2);
                    this.addUser(u3);
                    this.addUser(u4);
                    this.addUser(u5);
                    this.addUser(u6);
                    this.addUser(u7);

                    this.createFriendship(u1.getEmail(), u2.getEmail());
                    this.createFriendship(u1.getEmail(), u7.getEmail());

                    this.createFriendship(u6.getEmail(), u5.getEmail());
                    this.createFriendship(u5.getEmail(), u4.getEmail());
                    this.createFriendship(u4.getEmail(), u3.getEmail());
                }
                break;

            case 2:
                // Doua cuminitati, cu drumul maxim fiind 5
                {
                    User u1 = new User("Marian", "Popescu", "marian.popescu@yahoo.com");
                    User u2 = new User("Gabi", "Paslaruu", "gabriel.paslaru@yahoo.com");
                    User u3 = new User("Stefan", "Nastasa", "stefan.nastasa@yahoo.com");
                    User u4 = new User("Stefan", "Atumulese", "stefan.atumulesei@gmail.com");
                    User u5 = new User("Ana", "Maria", "ana.maria@gmail.com");
                    User u6 = new User("Nicu", "Margine", "nicu.hanganu@yahoo.com");
                    User u7 = new User("Nicu", "Pop", "nicu.pop@yahoo.com");
                    User u8 = new User("Matei", "Hanganu", "matei.hanga@yahoo.com");

                    this.addUser(u1);
                    this.addUser(u2);
                    this.addUser(u3);
                    this.addUser(u4);
                    this.addUser(u5);
                    this.addUser(u6);
                    this.addUser(u7);
                    this.addUser(u8);

                    this.createFriendship(u1.getEmail(), u2.getEmail());

                    this.createFriendship(u3.getEmail(), u4.getEmail());
                    this.createFriendship(u4.getEmail(), u5.getEmail());
                    this.createFriendship(u5.getEmail(), u6.getEmail());
                    this.createFriendship(u6.getEmail(), u7.getEmail());
                    this.createFriendship(u7.getEmail(), u8.getEmail());
                }
                break;

            case 3:
                {
                    // O comunitate, drum maxim de 3
                    User u1 = new User("Marian", "Popescu", "marian.popescu@yahoo.com");
                    User u2 = new User("Gabi", "Paslaruu", "gabriel.paslaru@yahoo.com");
                    User u3 = new User("Stefan", "Nastasa", "stefan.nastasa@yahoo.com");
                    User u4 = new User("Stefan", "Atumulese", "stefan.atumulesei@gmail.com");
                    this.addUser(u1);
                    this.addUser(u2);
                    this.addUser(u3);
                    this.addUser(u4);
                    this.createFriendship(u1.getEmail(), u2.getEmail());
                    this.createFriendship(u2.getEmail(), u3.getEmail());
                    this.createFriendship(u3.getEmail(), u4.getEmail());
                }
                break;

            case 4:
                {
                    // 4 comunitati , drum max de 0
                    User u1 = new User("Marian", "Popescu", "marian.popescu@yahoo.com");
                    User u2 = new User("Gabi", "Paslaruu", "gabriel.paslaru@yahoo.com");
                    User u3 = new User("Stefan", "Nastasa", "stefan.nastasa@yahoo.com");
                    User u4 = new User("Stefan", "Atumulese", "stefan.atumulesei@gmail.com");
                    this.addUser(u1);
                    this.addUser(u2);
                    this.addUser(u3);
                    this.addUser(u4);
                }
                break;

            default:
                System.out.println("There is not iteration with this number!");
        }
    }

    /**
     * @return an int that represents the number of communities
     *
     * we use a DFS for each node to count the number of conex elements
     *
     */
    @Override
    public int numberOfCommunities() {
        Iterable<User> it = userRepo.findAll();
        Set<User> set = new HashSet<>();
        int count = 0;

        for(User u : it)
            if(!set.contains(u))
            {
                ++count;
                DFS(u, set);
                //System.out.println(DFS(u, set));
            }

        return count;
    }

    /**
     * a simple DFS algorithm
     *
     * @return a list of all the users in the DF Search
     */
    private List<User> DFS(User u, Set<User> set) {
        List<User> list = new ArrayList<>();
        list.add(u);
        set.add(u);

        for(User f : u.getFriends())
            if(!set.contains(f))
            {
                List<User> l = DFS(f, set);
                for(User x : l)
                    list.add(x);
            }

        return list;
    }

    /**
     * @returns a list of all the communities
     */
    @Override
    public List<List<User>> getAllCommunities() {
        Iterable<User> it = userRepo.findAll();
        Set<User> set = new HashSet<>();

        List<List<User>> l = new ArrayList<>();

        for(User u : it)
            if(!set.contains(u))
                l.add(DFS(u, set));

        return l;
    }
    /**
     * @param email a string that represents the user's email we have to find
     *
     * @return the user that has that specific email
     *         or null if there is no user with that email
     */
    public User getUserByEmail(String email) {
        Iterable<User> it = userRepo.findAll();
        for(User u : it)
            if(u.getEmail().equals(email))
                return u;
        return null;
    }

    /**
     * Returns the most sociable community
     * the most sociable community is the community of users with the longest path
     *
     * @return an Iterable of all the most sociable communities users
     */
    public List<Iterable<User>> mostSociableCommunity() {
        List<Iterable<User>> list = new ArrayList<>();
        Iterable<User> it = userRepo.findAll();
        Set<User> set = new HashSet<>();

        int max = -1;
        for(User u : it)
            if(!set.contains(u))
            {
                List<User> aux = DFS(u, set);
                int l = longestPath(aux);
                if(l > max)
                {
                    list = new ArrayList<>();
                    list.add(aux);
                    max = l;
                }
                else if(l == max)
                    list.add(aux);
            }

        return list;
    }

    /**
     * We look for the longest path in the community
     *
     * @param nodes - a list of all the users in a community
     *
     * @return the longest path
     */
    private int longestPath(List<User> nodes) {

        int max = 0;

        for(User u : nodes)
        {
            int l = longestPathFromSource(u);
            if(max < l)
                max = l;
        }

        return max;
    }

    // this function is used to initialise the set
    private int longestPathFromSource(User source) {
        Set<User> set = new HashSet<>();
        return lee(source, set);
    }

    /**
     * lee's algorithm on a graph
     *
     * @param source - the source node (or user)
     * @param set - we need a map/set or frequency vector to keep track of the nodes we went through previously
     *
     * @return the longest path from the node source
     */
    private int lee(User source, Set<User> set) {

        int max = -1;
        for(User f : source.getFriends())
            if(!set.contains(f))
            {
                set.add(f);
                int l = lee(f, set);
                if(l > max)
                    max = l;
                set.remove(f);
            }

        return max + 1;
    }
}
