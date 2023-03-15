package com.example.my_project;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;
public class Controller3 implements Initializable {
    @FXML
    private AnchorPane Pane;

    void  Splash(){
        new Thread(() -> {
            try {
                Thread.sleep(2652);

            }catch (Exception e){
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                Controller1.AdminPage("LoginScreen");
                Pane.getScene().getWindow().hide();

            });
        }).start();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Splash();

    }
}
