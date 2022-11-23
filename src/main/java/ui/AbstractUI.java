package ui;

import service.Service;
import service.Service0;

import java.util.UUID;

public abstract class AbstractUI implements UI{
    Service<UUID> srv;

    public AbstractUI(Service<UUID>  srv) {
        this.srv = srv;
    }
}
