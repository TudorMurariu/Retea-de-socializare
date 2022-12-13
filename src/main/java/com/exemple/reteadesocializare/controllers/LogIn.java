package com.exemple.reteadesocializare.controllers;

import com.exemple.reteadesocializare.MainApp;
import com.exemple.reteadesocializare.domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.exemple.reteadesocializare.service.Service;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LogIn {

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Text emailErrorText;

    @FXML
    private Text passwordErrorText;

    private Service service;

    public void setService(Service service) {
        this.service = service;
    }

    public Service getService() {
        return service;
    }

    @FXML
    protected void onLogInButtonCLick(ActionEvent event) throws IOException {
        User u = service.getUserByEmail(email.getText());
        System.out.println(u);

        if(u == null) // show a message
        {
            emailErrorText.setVisible(true);
            passwordErrorText.setVisible(false);
        }
        else if(!password.getText().equals(u.getPassword())) { // show a message
            passwordErrorText.setVisible(true);
            emailErrorText.setVisible(false);
        }
        else { // enter Application
            emailErrorText.setVisible(false);
            passwordErrorText.setVisible(false);


            System.out.println("yay!");
        }
    }

    @FXML
    public void onSignInClick(ActionEvent event) throws IOException {
        FXMLLoader stageLoader = new FXMLLoader();
        stageLoader.setLocation(getClass().getResource("/com.example.reteadesocializare/SignIn.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        AnchorPane singInLayout = stageLoader.load();
        Scene scene = new Scene(singInLayout);
        stage.setScene(scene);

        SignIn signInController = stageLoader.getController();
        signInController.setService(this.service);

        stage.show();
    }

    public void onTextEntered(ActionEvent actionEvent) {
        emailErrorText.setVisible(false);
        passwordErrorText.setVisible(false);
    }

    public void onPasswordEnterd(ActionEvent actionEvent) {
        passwordErrorText.setVisible(false);
    }
}
