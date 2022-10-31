package ui;

import domain.User;
import service.Service;

import java.util.Scanner;

public class ConsoleUI extends AbstractUI {

    private Scanner cin;
    public ConsoleUI(Service srv) {
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
        System.out.println("3 - Remove a user by its ID");
        System.out.println("4 - Add a friendship");
        System.out.println("5 - Remove a friendship\n");


        while(true)
        {
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
                    System.out.println("3 - Remove a user by its ID");
                    System.out.println("4 - Add a friendship");
                    System.out.println("5 - Remove a friendship\n");
                    break;

                case 2:
                    User user = readUser();
                    srv.addUser(user);
                    break;

                case 3:
                    break;
                    
                default:
                    System.out.println("Wrong Command!");
            }
        }
    }

    @Override
    public User readUser() {
        System.out.print("Firstname: ");
        String firstname = cin.next();
        System.out.print("Lastname: ");
        String lastname = cin.next();

        User user = new User(firstname, lastname);
        return user;
    }
}
