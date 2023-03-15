package com.example.my_project;


import com.example.my_project.parkingDB.DataBaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller1 implements Initializable {

    @FXML
    private Button CancelButton;

    @FXML
    private PasswordField Password;

    @FXML
    private TextField UserName;

    @FXML
    private Label LoginMessage;

    @FXML
    private ChoiceBox<String> Choice;

    @FXML
    private Button LoginButton;

    @FXML
    private Button NotVisible;

    @FXML
    private Button Visible;

    @FXML
    private TextField PasswordText;

    private Parent root;

    final Tooltip TooltipPassword = new Tooltip();
    final Tooltip TooltipUserName = new Tooltip();

    @FXML
    void PasswordVisible() {
        PasswordText.setText(Password.getText());
        PasswordText.setVisible(true);
        Password.setVisible(false);
        Visible.setVisible(false);
        NotVisible.setVisible(true);
    }

    @FXML
    void PasswordNotVisible() {
        Password.setText(PasswordText.getText());
        Password.setVisible(true);
        PasswordText.setVisible(false);
        NotVisible.setVisible(false);
        Visible.setVisible(true);
    }



    @FXML
    void Cancel() {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void Login() {
        if (UserName.getText().isBlank()) {
            LoginMessage.setText("User name is empty");
        }
        if (Password.getText().isBlank()) {
            LoginMessage.setText("Password is empty");
        }
        if (UserName.getText().isBlank() && Password.getText().isBlank()) {
            LoginMessage.setText("User name and Password are empty");
        }
        if (Choice.getValue() == null) {
            LoginMessage.setText("Select Admin/User");
        }
        if (Choice.getValue() != null && !UserName.getText().isBlank() && !Password.getText().isBlank()) {
            LoginMessage.setText("");
            int status = DataBaseConnection.CheckLogin(UserName.getText(), Decrypting.encrypt(Password.getText()), Choice.getValue());
            switch (status) {
                case 0 -> {
                    if (Choice.getValue().equals("Admin")) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("PageAdmin.fxml"));
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Controller2 Controller = loader.getController();
                        Controller.displayName(UserName.getText());
                        Stage stage = new Stage();
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.setScene(new Scene(root));
                        stage.show();
                    }
                    if (Choice.getValue().equals("User")) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("PageUser.fxml"));
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Controller2 Controller = loader.getController();
                        Controller.displayName(UserName.getText());
                        Stage stage = new Stage();
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.setScene(new Scene(root));
                        stage.show();
                    }
                    Stage stage = (Stage) LoginButton.getScene().getWindow();
                    stage.close();
                }
                case 1 -> LoginMessage.setText("UserName Or Password Wrong");
                case -1 -> LoginMessage.setText("Connection Failed");
            }
        }

    }


    @FXML
    public static void AdminPage(String UserOrAdmin) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Controller1.class.getResource(UserOrAdmin + ".fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       Choice.getItems().addAll("Admin", "User");
       TooltipPassword.setStyle("-fx-background-color :  #6aa6ff ;");
       TooltipPassword.setText("Enter Your Password");
       Password.setTooltip(TooltipPassword);
       TooltipUserName.setStyle("-fx-background-color :  #6aa6ff ;");
       TooltipUserName.setText("Enter Your User name");
       UserName.setTooltip(TooltipUserName);
    }


}
