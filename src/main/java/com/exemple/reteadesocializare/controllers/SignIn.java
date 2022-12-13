package com.exemple.reteadesocializare.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.exemple.reteadesocializare.service.Service;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SignIn {

    private Service service;
    public void setService(Service service) {
        this.service = service;
    }
    public Service getService() {
        return service;
    }

    @FXML
    protected void onCreateAccountClick() {

    }

    public void goBackToLogIn(ActionEvent actionEvent) throws IOException {
        FXMLLoader stageLoader = new FXMLLoader();
        stageLoader.setLocation(getClass().getResource("/com.example.reteadesocializare/LogIn.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        AnchorPane logInLayout = stageLoader.load();
        Scene scene = new Scene(logInLayout);
        stage.setScene(scene);

        LogIn logInController = stageLoader.getController();
        logInController.setService(this.service);

        stage.show();
    }
}
