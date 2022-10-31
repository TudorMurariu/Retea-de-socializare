package ui;

import service.Service;

public abstract class AbstractUI implements UI{
    Service srv;

    public AbstractUI(Service srv) {
        this.srv = srv;
    }
}
