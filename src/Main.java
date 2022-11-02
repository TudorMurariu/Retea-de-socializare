import domain.FriendShip;
import domain.User;
import domain.validators.FriendshipValidator;
import domain.validators.UserValidator;
import repository.Repository;
import repository.memory.InMemoryRepository;
import service.Service;
import service.Service0;
import ui.ConsoleUI;
import ui.UI;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        Repository<UUID, User> userRepo = new InMemoryRepository<>(new UserValidator());

        Repository<UUID, FriendShip> friendshipRepo = new InMemoryRepository<>(new FriendshipValidator());

        Service srv = new Service0(userRepo, friendshipRepo);

        UI console = new ConsoleUI(srv);

        console.start();
    }
}


