package com.exemple.reteadesocializare.controllers;

import com.exemple.reteadesocializare.domain.User;
import com.exemple.reteadesocializare.domain.validators.UserValidator;
import com.exemple.reteadesocializare.domain.validators.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.exemple.reteadesocializare.service.Service;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class SignIn {

    @FXML
    private TextField first_name;
    @FXML
    private TextField last_name;
    @FXML
    private TextField email;
    @FXML
    private TextField password;
    @FXML
    private TextField password_confirm;

    @FXML
    private Text firstnameErrorText;
    @FXML
    private Text lastnameErrorText;
    @FXML
    private Text emailErrorText;
    @FXML
    private Text passwordErrorText;

    private UserValidator userValidator = new UserValidator();
    private Service service;
    public void setService(Service service) {
        this.service = service;
    }
    public Service getService() {
        return service;
    }

    @FXML
    protected void onCreateAccountClick(ActionEvent event) throws IOException {
        User newUser = new User(first_name.getText(), last_name.getText(), email.getText(), password.getText());

        try{
            userValidator.validate(newUser);
        }
        catch (ValidationException exception) {
            String err = exception.toString().split(":")[1];
            switch (err.charAt(1)) {
                case '1' -> {
                    firstnameErrorText.setText(err.substring(1));
                    firstnameErrorText.setVisible(true);
                }
                case '2' -> {
                    lastnameErrorText.setText(err.substring(1));
                    lastnameErrorText.setVisible(true);
                }
                default -> {
                    emailErrorText.setText(err);
                    System.out.println(err);
                    emailErrorText.setVisible(true);
                }
            }
            return;
        }

        if(!password.getText().equals(password_confirm.getText()))
            passwordErrorText.setVisible(true);
        else { // adaugam utilizator
            service.addUser(newUser);

            FXMLLoader stageLoader = new FXMLLoader();
            stageLoader.setLocation(getClass().getResource("/com.example.reteadesocializare/Application.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            AnchorPane appLayout = stageLoader.load();
            Scene scene = new Scene(appLayout);
            stage.setScene(scene);

            Application appController = stageLoader.getController();
            appController.setService(this.service);
            appController.initApp(newUser);

            stage.show();

            System.out.println("yay!");
        }
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

    public void onFirstnameTextChanged() {
        firstnameErrorText.setVisible(false);
    }

    public void onLastnameTextChanged() {
        lastnameErrorText.setVisible(false);
    }

    public void onEmailTextChanged() {
        emailErrorText.setVisible(false);
    }

    public void onPasswordTextChanged() {
        passwordErrorText.setVisible(false);
    }

    public void onConfirmPasswordTextChanged() {
        passwordErrorText.setVisible(false);
    }
}
