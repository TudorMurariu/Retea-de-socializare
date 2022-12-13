package com.exemple.reteadesocializare.ui;

import com.exemple.reteadesocializare.domain.User;

public interface UI {
    /**
     * starts the user interface
     */
    void start();

    User readUser();

    String readEmail();
}
