package com.example.reteadesocializare;

import domain.Constants.RepoStrategy;
import domain.FriendShip;
import domain.User;
import domain.validators.FriendshipValidator;
import domain.validators.UserValidator;
import repository.Repository;
import repository.database.FriendShipDataBase;
import repository.database.UserDataBase;
import repository.file.FriendshipFile;
import repository.file.UserFile;
import repository.memory.InMemoryRepository;
import service.Service;
import service.Service0;
import ui.ConsoleUI;
import ui.UI;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        Repository<UUID, User> userRepo;
        Repository<UUID, FriendShip> friendshipRepo;

        switch(RepoStrategy.valueOf(args[0]))
        {

            case file :
                userRepo = new UserFile("user.txt", new UserValidator());
                friendshipRepo = new FriendshipFile("friendship.txt", new FriendshipValidator(), userRepo);
                break;

            case database:

                userRepo = new UserDataBase("jdbc:postgresql://localhost:5432/Retea_De_Socializare",
                        "postgres", "postgres",  new UserValidator());
                friendshipRepo = new FriendShipDataBase("jdbc:postgresql://localhost:5432/Retea_De_Socializare",
                        "postgres", "postgres",  new FriendshipValidator(), userRepo);
                break;

            case memory:
            default:
                userRepo = new InMemoryRepository<>(new UserValidator());
                friendshipRepo = new InMemoryRepository<>(new FriendshipValidator());
        }

        Service srv = new Service0(userRepo, friendshipRepo);

        UI console = new ConsoleUI(srv);

        console.start();
    }
}


