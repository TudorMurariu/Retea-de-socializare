package com.exemple.reteadesocializare;

import com.exemple.reteadesocializare.controllers.LogIn;
import com.exemple.reteadesocializare.domain.FriendShip;
import com.exemple.reteadesocializare.domain.User;
import com.exemple.reteadesocializare.domain.validators.FriendshipValidator;
import com.exemple.reteadesocializare.domain.validators.UserValidator;
import com.exemple.reteadesocializare.repository.file.FriendshipFile;
import com.exemple.reteadesocializare.repository.file.UserFile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.exemple.reteadesocializare.repository.Repository;
import com.exemple.reteadesocializare.service.Service;
import com.exemple.reteadesocializare.service.Service0;

import java.io.IOException;
import java.util.UUID;

public class MainApp extends Application {
    private Service service;
    public void setService(Service service) {
        this.service = service;
    }
    public Service getService() {
        return service;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Repository<UUID, User> userRepo;
        Repository<UUID, FriendShip> friendshipRepo;

        userRepo = new UserFile("C:\\Users\\tudor\\OneDrive\\Desktop\\Retea de socializare\\src\\main\\java\\com\\exemple\\reteadesocializare\\user.txt", new UserValidator());
        friendshipRepo = new FriendshipFile("C:\\Users\\tudor\\OneDrive\\Desktop\\Retea de socializare\\src\\main\\java\\com\\exemple\\reteadesocializare\\friendship.txt", new FriendshipValidator(), userRepo);

        //userRepo = new InMemoryRepository<>(new UserValidator());
        //friendshipRepo = new InMemoryRepository<>(new FriendshipValidator());

        service = new Service0(userRepo, friendshipRepo);

        initView(primaryStage);
        primaryStage.show();
    }

    private void initView(Stage primaryStage) throws IOException {
        FXMLLoader stageLoader = new FXMLLoader();
        stageLoader.setLocation(getClass().getResource("/com.example.reteadesocializare/LogIn.fxml"));
        AnchorPane LogInLayout = stageLoader.load();
        primaryStage.setScene(new Scene(LogInLayout));

        LogIn logInController = stageLoader.getController();
        logInController.setService(this.service);
    }
}
