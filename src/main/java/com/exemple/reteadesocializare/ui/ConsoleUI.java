package com.exemple.reteadesocializare.ui;

import com.exemple.reteadesocializare.domain.FriendShip;
import com.exemple.reteadesocializare.domain.User;
import com.exemple.reteadesocializare.service.Service;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ConsoleUI extends AbstractUI {

    private Scanner cin;
    public ConsoleUI(Service<UUID> srv) {
        super(srv);
        cin = new Scanner(System.in);
    }

    /**
     * starts the console user interface
     * where all the functionalities are represented by an integer
     */
    @Override
    public void start() {
        cin = new Scanner(System.in);

        System.out.println("The functionalities are : ");
        System.out.println("0 - exit");
        System.out.println("1 - show all functionalities");
        System.out.println("2 - Add a user");
        System.out.println("3 - Remove a user by its email");
        System.out.println("4 - Add a friendship");
        System.out.println("5 - Remove a friendship");
        System.out.println("6 - Show all users");
        System.out.println("7 - Show all friendships");
        System.out.println("8 - Number of communities");
        System.out.println("9 - Show the most sociable community");
        System.out.println("10 - add predefined values");
        System.out.println("11 - Show all communities");

        String email1, email2, email;

        while(true)
        {
            System.out.print("Give a command: ");
            int command = cin.nextInt();

            switch (command)
            {
                case 0:
                    cin.close();
                    return;
                case 1:
                    System.out.println("The functionalities are : ");
                    System.out.println("1 - show all functionalities");
                    System.out.println("2 - Add a user");
                    System.out.println("3 - Remove a user by its email");
                    System.out.println("4 - Add a friendship");
                    System.out.println("5 - Remove a friendship");
                    System.out.println("6 - Show all users");
                    System.out.println("7 - Show all friendships");
                    System.out.println("8 - Number of communities");
                    System.out.println("9 - Show the most sociable community");
                    System.out.println("10 - add predefined values");
                    System.out.println("11 - Show all communities");
                    break;

                case 2:
                    User user = readUser();
                    srv.addUser(user);
                    break;

                case 3:
                    email = readEmail();
                    srv.deleteUser(email);
                    break;

                case 4:
                    email1 = readEmail();
                    email2 = readEmail();
                    srv.createFriendship(email1, email2);
                    break;

                case 5:
                    email1 = readEmail();
                    email2 = readEmail();
                    srv.deleteFriendship(email1, email2);
                    break;

                case 6:
                    Iterable<User> it = srv.getAllUsers();
                    it.forEach(System.out::println);
                    break;

                case 7:
                    Iterable<FriendShip> itf = srv.getAllFriendships();
                    itf.forEach(System.out::println);
                    break;

                case 8:
                    System.out.println("The number of communities is: " + srv.numberOfCommunities());
                    break;

                case 9:
                    {
                        List<Iterable<User>> it1 = (List) srv.mostSociableCommunity();
                        if(it1.size() == 1)
                            System.out.println("The most sociable community is:");
                        else
                            System.out.println("The most sociable communities are:");

                        for(var x : it1) {
                            x.forEach(System.out::println);
                            System.out.println("\n");
                        }
                    }
                    break;

                case 10:
                    System.out.print("Choose what iteration of predefined values: ");
                    int i = cin.nextInt();
                    srv.add_Predefined_Values(i);
                    break;

                case 11:
                    System.out.print("Communities:");
                    List<List<User>> l = srv.getAllCommunities();
                    l.stream().forEach(System.out::println);
                    break;
                    
                default:
                    System.out.println("Wrong Command!");
            }
        }
    }

    /**
     * Reads a User from the console and returns it.
     *
     * @return  The User that was read.
     *
     */
    @Override
    public User readUser() {
        System.out.print("Firstname: ");
        String firstname = cin.next();
        System.out.print("Lastname: ");
        String lastname = cin.next();
        System.out.print("email: ");
        String email = cin.next();

        User user = new User(firstname, lastname, email);
        return user;
    }


    /**
     * Reads an email from the console and returns it.
     *
     * @return  The email that was read.
     *
     * @throws
     *
     */

    @Override
    public String readEmail() {
        System.out.print("Give the email: ");
        String email = cin.next();

        return email;
    }


}


