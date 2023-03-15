package com.example.my_project;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;


public class Controller2{


    @FXML
    private Button AccountSetting;

    @FXML
    private Button AddUser;

    @FXML
    private Button ManageUsers;

    @FXML
    private Button Statistics;


    @FXML
    private Button AccountSettingUser;

    @FXML
    private Button Dashboard;

    @FXML
    private Button Floors;

    @FXML
    private Button History;

    @FXML
    private Button Reservation;



    @FXML
    private BorderPane LodingPlace;

    @FXML
    private Label UserNameText;

    @FXML
    private AnchorPane Pane;

    @FXML
    private Button Yes;



    @FXML
    void Cancel() {
        Controller1.AdminPage("ExitScreen");
    }

    @FXML
    void SettingPage() throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AccountSetting.fxml")));
        Scene scene = AccountSetting.getScene();
        root.translateYProperty().set(scene.getHeight());
        LodingPlace.setCenter(root);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.3), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    @FXML
    void StatisticsPage() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Statistics.fxml")));
        Scene scene = Statistics.getScene();
        root.translateYProperty().set(scene.getHeight());
        LodingPlace.setCenter(root);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.3), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    @FXML
    void AddUser() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddUser.fxml")));
        Scene scene = AddUser.getScene();
        root.translateYProperty().set(scene.getHeight());
        LodingPlace.setCenter(root);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.3), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    @FXML
    void ManageUsers() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ManageUsers.fxml")));
        Scene scene = ManageUsers.getScene();
        root.translateYProperty().set(scene.getHeight());
        LodingPlace.setCenter(root);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.3), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    @FXML
    void ShowFloors(){
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Floors.fxml"));
        try {
            root = loader.load();
            Scene scene = Floors.getScene();
            root.translateYProperty().set(scene.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
        LodingPlace.setCenter(root);
        Timeline timeline = new Timeline();
        assert root != null;
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.3), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        Floors Controller = loader.getController();
        Controller.displayUserName(UserNameText.getText());
        }

    @FXML
    void ShowHistory() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("History.fxml")));
        Scene scene = History.getScene();
        root.translateYProperty().set(scene.getHeight());
        LodingPlace.setCenter(root);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.3), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
    @FXML
    void ShowDashboard() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Dashboard.fxml")));
        Scene scene = Dashboard.getScene();
        root.translateYProperty().set(scene.getHeight());
        LodingPlace.setCenter(root);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.3), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    @FXML
    void Reserve() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Reserve.fxml")));
        Scene scene = Reservation.getScene();
        root.translateYProperty().set(scene.getHeight());
        LodingPlace.setCenter(root);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.3), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
    @FXML
    void AccountSettingUser() {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AccountSettingUser.fxml"));
        try {
            root = loader.load();
            Scene scene = AccountSettingUser.getScene();
            root.translateYProperty().set(scene.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
        LodingPlace.setCenter(root);
        Timeline timeline = new Timeline();
        assert root != null;
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.3), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        AccountSettingUser Controller = loader.getController();
        Controller.displayUserName(UserNameText.getText());
    }
    public void displayName(String username) {
        UserNameText.setText(username);
    }
    @FXML
    void Signout(){
        Pane.setVisible(true);
    }
    @FXML
    void No(){
        Pane.setVisible(false);
    }
    @FXML
    void Yes(){
        Stage stage = (Stage) Yes.getScene().getWindow();
        stage.close();
        Controller1.AdminPage("LoginScreen");
    }


}
