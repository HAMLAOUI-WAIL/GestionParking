package com.example.my_project;

import com.example.my_project.parkingDB.DataBaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller6 {

    @FXML
    private Button Non;

    @FXML
    private Button Oui;

    @FXML
    private PasswordField Password;

    @FXML
    private TextField PasswordText;

    @FXML
    private Button NotVisible;

    @FXML
    private Button Visible;

    @FXML
    private TextField Email_adress;

    @FXML
    private TextField Full_name;

    @FXML
    private TextField Phone_number;

    @FXML
    private TextField User_name;

    @FXML
    private Label AlertMessage;


    String sql = null;
    Connection con = DataBaseConnection.con;

    @FXML
    void AddUser() {
        String UserName = User_name.getText();
        String FullName = Full_name.getText();
        String EmailAdress = Email_adress.getText();
        String PhoneNumber = Phone_number.getText();
        String Password1 = Password.getText();
        String paramtre = "[a-zA-Z0-9]{0,20}$";
        Pattern pattern = Pattern.compile(paramtre);
        Matcher matcher = pattern.matcher(UserName);
        Matcher matcher1 = pattern.matcher(FullName);


        String paramtre1 = "[0-9]{0,10}$";
        Pattern pattern1 = Pattern.compile(paramtre1);
        Matcher matcher2 = pattern1.matcher(PhoneNumber);

        String paramtre3 = "[a-zA-Z0-9._]{0,20}[@][a-zA-Z0-9]{0,7}[.][a-zA-Z]{0,5}";
        Pattern pattern3 = Pattern.compile(paramtre3);
        Matcher matcher3 = pattern3.matcher(EmailAdress);

        if (UserName.isEmpty() || FullName.isEmpty() || EmailAdress.isEmpty() || PhoneNumber.isEmpty() || Objects.requireNonNull(Password1).isEmpty()) {
            AlertMessage.setText("Please Fill All DATA");

        }
        else if (!matcher.matches()){
            AlertMessage.setText("Enter a Correct UserName !");
        }
        else if (!matcher1.matches()){
            AlertMessage.setText("Enter a Correct Full_name !");
        }else if (!matcher2.matches()){
            AlertMessage.setText("Enter Correct Phone_number !");
        }else if (!matcher3.matches()){
            AlertMessage.setText("Enter Correct Gmail !");
        }else{
            int status = DataBaseConnection.CheckExist(UserName);
            switch (status) {
                case 0 -> AlertMessage.setText("User Name Exist");
                case 1 -> {
                    getQuery();
                    insert();
                    clean();
                    AlertMessage.setText("");
                    Controller1.AdminPage("Register");
                }
                case -1 -> AlertMessage.setText("Connection Failed");
            }
        }
       }

    void getQuery() {
            sql = "INSERT INTO `user`( `UserName`, `Name`, `Email`, `PhoneNumber`,`Password`) VALUES (?,?,?,?,?)";
    }

    @FXML
    private void clean() {
        User_name.setText(null);
        Full_name.setText(null);
        Email_adress.setText(null);
        Phone_number.setText(null);
        Password.setText(null);

    }


    void insert() {

        try {

            PreparedStatement Prest = con.prepareStatement(sql);
            Prest.setString(1, User_name.getText());
            Prest.setString(2, Full_name.getText());
            Prest.setString(3, Email_adress.getText());
            Prest.setString(4, Phone_number.getText());
            Prest.setString(5, Decrypting.encrypt(Password.getText()));
            Prest.execute();

        } catch (SQLException ex) {
           ex.printStackTrace();
        }

    }



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
    void Alert(){
        String User_Name = User_name.getText();
        String paramtre = "[a-zA-Z0-9_]{0,20}$";
        Pattern pattern = Pattern.compile(paramtre);
        Matcher matcher = pattern.matcher(User_Name);
        if (!matcher.matches()){
            AlertMessage.setText("Enter a Valid UserName !");
        }else{
            AlertMessage.setText("");
        }

    }
    @FXML
    void Alert1(){
        String User_name = Full_name.getText();
        String paramtre = "[a-zA-Z0-9]{0,20}$";
        Pattern pattern = Pattern.compile(paramtre);
        Matcher matcher = pattern.matcher(User_name);
        if (!matcher.matches()){
            AlertMessage.setText("Enter a valid Full_name !");
        }else{
            AlertMessage.setText("");
        }

    }
    @FXML
    void Alert3(){
        String User_name = Phone_number.getText();
        String paramtre ="[0-9]{0,10}$";
        Pattern pattern = Pattern.compile(paramtre);
        Matcher matcher = pattern.matcher(User_name);
        if (!matcher.matches()){
            AlertMessage.setText("Enter a Valid Phone_number !");
        }else{
            AlertMessage.setText("");
        }

    }
    @FXML
    void Alert2(){
        String User_name = Email_adress.getText();
        String paramtre = "[a-zA-Z0-9._]{0,20}[@][a-zA-Z0-9]{0,7}[.][a-zA-Z]{0,5}";
        Pattern pattern = Pattern.compile(paramtre);
        Matcher matcher = pattern.matcher(User_name);
        if (!matcher.matches()){
            AlertMessage.setText("Enter a Valid Email !");
        }else{
            AlertMessage.setText("");
        }

    }




    @FXML
    void Cancel() {
        Stage stage =(Stage) Oui.getScene().getWindow();
        stage.close();
        System.exit(0);
    }

    @FXML
    void CancelWindow() {
        Stage stage =(Stage) Non.getScene().getWindow();
        stage.close();

    }

}
