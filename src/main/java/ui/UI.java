package ui;

import domain.User;

import java.util.UUID;

public interface UI {
    /**
     * starts the user interface
     */
    void start();

    User readUser();

    String readEmail();
}
