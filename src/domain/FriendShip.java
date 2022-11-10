package domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class FriendShip extends Entity<UUID> {
    private User user1;
    private User user2;

    private LocalDateTime friendsFrom;

    public FriendShip(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
        this.setId(UUID.randomUUID());
        friendsFrom = LocalDateTime.now();
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public LocalDateTime getFriendsFrom() {
        return friendsFrom;
    }

    @Override
    public String toString() {
        return "FriendShip {" +
                "user1=" + user1 +
                ", user2=" + user2 +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FriendShip))
            return false;
        FriendShip that = (FriendShip) o;
        return id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser1(), getUser2());
    }
}
