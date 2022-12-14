package com.exemple.reteadesocializare;

import com.exemple.reteadesocializare.controllers.LogIn;
import com.exemple.reteadesocializare.domain.FriendShip;
import com.exemple.reteadesocializare.domain.Message;
import com.exemple.reteadesocializare.domain.User;
import com.exemple.reteadesocializare.domain.validators.FriendshipValidator;
import com.exemple.reteadesocializare.domain.validators.MessageValidator;
import com.exemple.reteadesocializare.domain.validators.UserValidator;
import com.exemple.reteadesocializare.repository.file.FriendshipFile;
import com.exemple.reteadesocializare.repository.file.MessageFile;
import com.exemple.reteadesocializare.repository.file.UserFile;
import com.exemple.reteadesocializare.service.MessageService;
import com.exemple.reteadesocializare.service.MessageService0;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.exemple.reteadesocializare.repository.Repository;
import com.exemple.reteadesocializare.service.Service;
import com.exemple.reteadesocializare.service.Service0;

import java.io.IOException;
import java.util.UUID;

public class MainApp extends Application {
    private Color mainColorTheme;
    private Service service;
    private MessageService messageService;
    public void setService(Service service) {
        this.service = service;
    }
    public Service getService() {
        return service;
    }

    public Color getMainColorTheme() {
        return mainColorTheme;
    }

    public void setMainColorTheme(Color mainColorTheme) {
        this.mainColorTheme = mainColorTheme;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Repository<UUID, User> userRepo;
        Repository<UUID, FriendShip> friendshipRepo;
        Repository<UUID, Message> messageRepo;

        userRepo = new UserFile("C:\\Users\\tudor\\OneDrive\\Desktop\\Retea de socializare\\src\\main\\java\\com\\exemple\\reteadesocializare\\user.txt", new UserValidator());
        friendshipRepo = new FriendshipFile("C:\\Users\\tudor\\OneDrive\\Desktop\\Retea de socializare\\src\\main\\java\\com\\exemple\\reteadesocializare\\friendship.txt", new FriendshipValidator(), userRepo);
        messageRepo = new MessageFile("C:\\Users\\tudor\\OneDrive\\Desktop\\Retea de socializare\\src\\main\\java\\com\\exemple\\reteadesocializare\\messages.txt", new MessageValidator(), userRepo);

        service = new Service0(userRepo, friendshipRepo);
        messageService = new MessageService0(messageRepo);

        initView(primaryStage);
        primaryStage.show();
    }

    private void initView(Stage primaryStage) throws IOException {
        FXMLLoader stageLoader = new FXMLLoader();
        stageLoader.setLocation(getClass().getResource("/com.example.reteadesocializare/LogIn.fxml"));
        AnchorPane LogInLayout = stageLoader.load();
        primaryStage.setScene(new Scene(LogInLayout, service.getColor()));
        primaryStage.setTitle("App");

        Image icon = new Image("/com.example.reteadesocializare/imgs/Soboclan.jpg");
        primaryStage.getIcons().add(icon);

        LogIn logInController = stageLoader.getController();
        logInController.setService(this.service);
        logInController.setMessageService(messageService);
    }
}
