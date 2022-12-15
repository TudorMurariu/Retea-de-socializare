package com.exemple.reteadesocializare.controllers;

import com.exemple.reteadesocializare.domain.FriendShip;
import com.exemple.reteadesocializare.domain.User;
import com.exemple.reteadesocializare.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Application implements Initializable {

    @FXML
    private Text username;

    @FXML
    private ListView<String> friendsList;

    @FXML
    private ListView<String> friendsRequestList;

    @FXML
    private ListView<String> userList;

    private ObservableList<String> friendsObs = FXCollections.observableArrayList();

    private ObservableList<String> friendsReqObs = FXCollections.observableArrayList();
    private User user;
    private Service service;

    public void setService(Service service) {
        this.service = service;
    }

    public Service getService() {
        return service;
    }

    public void initApp(User user) {
        this.user = user;
        username.setText(user.getFirstName() + " " + user.getLastName());

        //List<String> friends_str = new ArrayList<>();
        user.getFriends().forEach(u -> friendsObs.add(u.getFirstName() + " " + u.getLastName() + " " + u.getEmail()));
        //friendsList.getItems().addAll(friends_str);

        //List<String> friends_req_str = new ArrayList<>();
        service.getAllFriendships().forEach(f -> {
            FriendShip friendShip = (FriendShip) f;
            if(friendShip.getUser2().getId().equals(user.getId()))
                friendsReqObs
                        .add(friendShip.getUser1().getFirstName() + " " + friendShip.getUser1().getLastName() +
                                " " + friendShip.getUser1().getEmail() + " " + friendShip.getFriendsFrom() + " "
                                + friendShip.getAcceptance());
        });
        //friendsRequestList.getItems().addAll(friends_req_str);

        List<String> users = new ArrayList<>();
        service.getAllUsers().forEach(u -> {
            User user1 = (User) u;
            AtomicReference<String> additionalMessage = new AtomicReference<>("");
            if(user1.equals(this.user))
                additionalMessage.set("YOU");

            this.user.getFriends().forEach(u2 -> {
                User user2 = (User) u2;
                if(u2.equals(u))
                    additionalMessage.set("FRIEND");
            });

            users.add(user1.getFirstName() + " " + user1.getFirstName() + " " + user1.getEmail() + " " + additionalMessage);
        });
        userList.getItems().addAll(users);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        friendsList.setItems(friendsObs);
        friendsRequestList.setItems(friendsReqObs);
    }

    public void removeFriend() {
        if(friendsList.getSelectionModel().getSelectedItem() == null)
            return;

        String userInfo = friendsList.getSelectionModel().getSelectedItem().toString();
        String email = userInfo.split(" ")[2];
        service.deleteFriendship(email, user.getEmail());

        //System.out.println(user.getEmail());
        //System.out.println(email);

        friendsObs.remove(userInfo);
        friendsReqObs.removeIf(line -> {
            return line.split(" ")[2].equals(email);
        });
    }

    public void acceptFriendRequest(ActionEvent actionEvent) {
        if(friendsRequestList.getSelectionModel().getSelectedItem() == null)
            return;

        String userInfo = friendsRequestList.getSelectionModel().getSelectedItem().toString();
        String email = userInfo.split(" ")[2];
        String status = userInfo.split(" ")[4];

        if(!status.equals("PENDING"))
            return;

        service.acceptFriendship(email, user.getEmail());

        System.out.println(email);

        friendsObs.removeAll(friendsObs.stream().toList());
        friendsReqObs.removeAll(friendsReqObs.stream().toList());
        initApp(this.user);
    }

    public void declineFriendRequest(ActionEvent actionEvent) {
        if(friendsRequestList.getSelectionModel().getSelectedItem() == null)
            return;

        String userInfo = friendsRequestList.getSelectionModel().getSelectedItem().toString();
        String email = userInfo.split(" ")[2];
        String status = userInfo.split(" ")[4];

        if(!status.equals("PENDING"))
            return;

        service.declineFriendRequest(user.getEmail(), email);

        friendsObs.removeAll(friendsObs.stream().toList());
        friendsReqObs.removeAll(friendsReqObs.stream().toList());
        initApp(this.user);
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
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

    public void deleteAccount(ActionEvent actionEvent) throws IOException {
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

    public void chooseColorTheme(DragEvent dragEvent) {

    }

    public void sendRequest(ActionEvent actionEvent) {
        if(friendsRequestList.getSelectionModel().getSelectedItem() == null)
            return;

        String userInfo = friendsRequestList.getSelectionModel().getSelectedItem().toString();
        String email = userInfo.split(" ")[2];

        service.createFriendRequest(user.getEmail(), email);
    }
}
