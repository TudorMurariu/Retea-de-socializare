package com.exemple.reteadesocializare.controllers;

import com.exemple.reteadesocializare.domain.FriendShip;
import com.exemple.reteadesocializare.domain.User;
import com.exemple.reteadesocializare.service.MessageService;
import com.exemple.reteadesocializare.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import java.util.concurrent.atomic.AtomicReference;

public class Application implements Initializable {

    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Text username;

    @FXML
    private ListView<String> friendRequestsSent;
    @FXML
    private ListView<String> friendsList;

    @FXML
    private ListView<String> friendsRequestList;

    @FXML
    private ListView<String> userList;

    @FXML
    private ListView<String> messagesFriendList;

    private final ObservableList<String> friendsObs = FXCollections.observableArrayList();

    private final ObservableList<String> friendsReqObs = FXCollections.observableArrayList();

    private final ObservableList<String> friendsReqSentObs = FXCollections.observableArrayList();

    private final ObservableList<String> userObs = FXCollections.observableArrayList();
    private User user;
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

    public void initApp(User user) {
        this.user = user;
        username.setText(user.getFirstName() + " " + user.getLastName());

        user.getFriends().forEach(u -> friendsObs.add(u.getFirstName() + " " + u.getLastName() + " " + u.getEmail()));

        service.getAllFriendships().forEach(f -> {
            FriendShip friendShip = (FriendShip) f;
            if(friendShip.getUser2().getId().equals(user.getId()))
                friendsReqObs
                        .add(friendShip.getUser1().getFirstName() + " " + friendShip.getUser1().getLastName() +
                                " " + friendShip.getUser1().getEmail() + " " + friendShip.getFriendsFrom() + " "
                                + friendShip.getAcceptance());
        });

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

            userObs.add(user1.getFirstName() + " " + user1.getFirstName() + " " + user1.getEmail() + " " + additionalMessage);
        });

        service.getAllFriendships().forEach(f -> {
            FriendShip friendShip = (FriendShip) f;
            if(friendShip.getUser1().getId().equals(user.getId()))
                friendsReqSentObs
                        .add(friendShip.getUser2().getFirstName() + " " + friendShip.getUser2().getLastName() +
                                " " + friendShip.getUser2().getEmail() + " " + friendShip.getFriendsFrom() + " "
                                + friendShip.getAcceptance());
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        friendsList.setItems(friendsObs);
        friendsRequestList.setItems(friendsReqObs);
        friendRequestsSent.setItems(friendsReqSentObs);
        userList.setItems(userObs);
        messagesFriendList.setItems(friendsObs);
    }

    public void removeFriend() {
        if(friendsList.getSelectionModel().getSelectedItem() == null)
            return;

        String userInfo = friendsList.getSelectionModel().getSelectedItem().toString();
        String email = userInfo.split(" ")[2];
        service.deleteFriendship(email, user.getEmail());

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
        logInController.setMessageService(messageService);

        stage.show();
    }

    public void deleteAccount(ActionEvent actionEvent) throws IOException {
        // delete user from db
        service.deleteUser(user.getEmail());
        // change scene
        FXMLLoader stageLoader = new FXMLLoader();
        stageLoader.setLocation(getClass().getResource("/com.example.reteadesocializare/LogIn.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        AnchorPane logInLayout = stageLoader.load();
        Scene scene = new Scene(logInLayout);
        stage.setScene(scene);

        LogIn logInController = stageLoader.getController();
        logInController.setService(this.service);
        logInController.setMessageService(messageService);

        stage.show();
    }

    public void chooseColorTheme(DragEvent dragEvent) {
        Stage stage = (Stage)((Node)dragEvent.getSource()).getScene().getWindow();
        Scene scene = stage.getScene();
        scene.setFill(colorPicker.getValue());
    }

    public void sendRequest(ActionEvent actionEvent) {
        if(userList.getSelectionModel().getSelectedItem() == null)
            return;

        String userInfo = userList.getSelectionModel().getSelectedItem().toString();
        String email = userInfo.split(" ")[2];

        service.createFriendRequest(user.getEmail(), email);
        friendsReqSentObs.removeAll(friendsReqSentObs.stream().toList());
        userObs.removeAll(userObs.stream().toList());
        initApp(this.user);
    }

    public void cancelFriendRequest(ActionEvent actionEvent) {
        if(friendRequestsSent.getSelectionModel().getSelectedItem() == null)
            return;

        String userInfo = friendRequestsSent.getSelectionModel().getSelectedItem().toString();
        String email = userInfo.split(" ")[2];

        service.deleteFriendship(user.getEmail(), email);
        friendsReqSentObs.removeAll(friendsReqSentObs.stream().toList());
        userObs.removeAll(userObs.stream().toList());
        initApp(this.user);
    }

    public void enterMessages(Event event) {
        //Alert a = new Alert(Alert.AlertType.NONE);
        //a.show();
    }

    public void sendMessage(KeyEvent event) {
        if(event.getCode() != KeyCode.ENTER)
            return;

        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.show();
    }
}
