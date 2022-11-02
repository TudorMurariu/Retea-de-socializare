package domain;

import java.util.*;

public class User extends Entity<UUID> {
    private String firstName;
    private String lastName;

    private String email;
    private Map<UUID, User> friends;

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        friends = new HashMap<>();
        // generates a random unique ID
        this.setId(UUID.randomUUID());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Iterable<User> getFriends() {
        return friends.values();
    }

    @Override
    public String toString() {
        return "Utilizator {" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email=" + email +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User that = (User) o;
        return id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getEmail());
    }

    // adds a new friend
    public void addFriend(User u) {
        friends.put(u.id, u);
    }

    // deletes a friend
    public boolean removeFriend(User u) {
        return friends.remove(u.id) != null;
    }

}