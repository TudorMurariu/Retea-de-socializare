package com.exemple.reteadesocializare;

public class Main {
    public static void main(String[] args) {

//        Repository<UUID, User> userRepo;
//        Repository<UUID, FriendShip> friendshipRepo;
//
//        switch(RepoStrategy.valueOf(args[0]))
//        {
//
//            case file :
//                userRepo = new UserFile("user.txt", new UserValidator());
//                friendshipRepo = new FriendshipFile("friendship.txt", new FriendshipValidator(), userRepo);
//                break;
//
//            case database:
//
//                userRepo = new UserDataBase("jdbc:postgresql://localhost:5432/Retea_De_Socializare",
//                        "postgres", "postgres",  new UserValidator());
//                friendshipRepo = new FriendShipDataBase("jdbc:postgresql://localhost:5432/Retea_De_Socializare",
//                        "postgres", "postgres",  new FriendshipValidator(), userRepo);
//                break;
//
//            case memory:
//            default:
//                userRepo = new InMemoryRepository<>(new UserValidator());
//                friendshipRepo = new InMemoryRepository<>(new FriendshipValidator());
//        }
//
//        Service srv = new Service0(userRepo, friendshipRepo);

        //UI console = new ConsoleUI(srv);
        //console.start();

        MainApp.main(args);
    }
}


