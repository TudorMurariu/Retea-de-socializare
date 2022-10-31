package ui;

import domain.User;

public interface UI {
    /**
     * starts the user interface
     */
    void start();

    User readUser();
}
