import domain.User;
import domain.validators.UserValidator;
import repository.Repository;
import repository.memory.InMemoryRepository;
import service.Service;
import ui.ConsoleUI;
import ui.UI;

public class Main {
    public static void main(String[] args) {

        UserValidator userValidator = new UserValidator();

        Repository<Long, User> repo = new InMemoryRepository<>(userValidator);

        Service<Long, User> srv = new Service<>(repo);

        UI console = new ConsoleUI(srv);
    }
}


