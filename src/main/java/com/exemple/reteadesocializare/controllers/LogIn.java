package com.exemple.reteadesocializare.controllers;

import com.exemple.reteadesocializare.domain.User;
import com.exemple.reteadesocializare.service.MessageService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.exemple.reteadesocializare.service.Service;
import javafx.scene.input.KeyEvent;
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

    private MessageService messageService;

    public void setService(Service service) {
        this.service = service;
    }

    public Service getService() {
        return service;
    }

    public MessageService getMessageService() {
        return messageService;
    }

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @FXML
    protected void onLogInButtonCLick(ActionEvent event) throws IOException {
        User user = service.getUserByEmail(email.getText());
        System.out.println(user);

        if(user == null) // show a message
        {
            emailErrorText.setVisible(true);
            passwordErrorText.setVisible(false);
        }
        else if(!password.getText().equals(user.getPassword())) { // show a message
            passwordErrorText.setVisible(true);
            emailErrorText.setVisible(false);
        }
        else { // enter Application
            emailErrorText.setVisible(false);
            passwordErrorText.setVisible(false);

            FXMLLoader stageLoader = new FXMLLoader();
            stageLoader.setLocation(getClass().getResource("/com.example.reteadesocializare/Application.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            AnchorPane appLayout = stageLoader.load();
            Scene scene = new Scene(appLayout);
            stage.setScene(scene);

            Application appController = stageLoader.getController();
            appController.setService(this.service);
            appController.setMessageService(messageService);
            appController.initApp(user);

            stage.show();
        }
    }

    @FXML
    public void onSignInClick(ActionEvent event) throws IOException {
        FXMLLoader stageLoader = new FXMLLoader();
        stageLoader.setLocation(getClass().getResource("/com.example.reteadesocializare/SignIn.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        AnchorPane singUpLayout = stageLoader.load();
        Scene scene = new Scene(singUpLayout);
        stage.setScene(scene);

        SignUp signUpController = stageLoader.getController();
        signUpController.setService(this.service);
        signUpController.setMessageService(messageService);

        stage.show();
    }

    public void onTextChanged(KeyEvent evt) {
        emailErrorText.setVisible(false);
        passwordErrorText.setVisible(false);
    }

    public void onPasswordChanged(KeyEvent evt) {
        passwordErrorText.setVisible(false);
    }
}
