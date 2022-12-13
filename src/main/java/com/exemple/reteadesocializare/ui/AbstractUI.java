package com.exemple.reteadesocializare.ui;

import com.exemple.reteadesocializare.service.Service;

import java.util.UUID;

public abstract class AbstractUI implements UI{
    Service<UUID> srv;

    public AbstractUI(Service<UUID>  srv) {
        this.srv = srv;
    }
}
