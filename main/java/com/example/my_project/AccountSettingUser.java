package com.example.my_project;

import com.example.my_project.parkingDB.DataBaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountSettingUser{
    @FXML
    private TextField EmailAdress;

    @FXML
    private TextField FullName;

    @FXML
    private TextField Name;

    @FXML
    private Label AlertMessage;

    @FXML
    private PasswordField Password;

    @FXML
    private TextField PasswordText;

    @FXML
    private TextField PhoneNumber;

    @FXML
    private Button NotVisible;

    @FXML
    private Button Visible;




    String sql = null;
    ResultSet resultSet = null ;
    Connection con = DataBaseConnection.con;
    ObservableList<Users> UserList = FXCollections.observableArrayList();

    @FXML
    public  void refreshUser() {
            try {
                UserList.clear();
                sql = "SELECT * FROM `user` WHERE `UserName` =?";
                PreparedStatement Prest = con.prepareStatement(sql);
                Prest.setString(1, Name.getText());
                Prest.execute();
                resultSet = Prest.executeQuery();

                while (resultSet.next()) {
                      UserList.add(new Users(
                            resultSet.getInt("Id"),
                            resultSet.getString("UserName"),
                            resultSet.getString("Name"),
                            resultSet.getString("Email"),
                            resultSet.getString("PhoneNumber"),
                            resultSet.getString("Password")));
                }
                Name.setText(UserList.get(0).UserName);
                FullName.setText(UserList.get(0).FullName);
                EmailAdress.setText(UserList.get(0).EmailAdress);
                PhoneNumber.setText(UserList.get(0).PhoneNumber);
                Password.setText(Decrypting.decrypt(UserList.get(0).Password));
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    @FXML
    void UpdateUser() {

        String User_name = Name.getText();
        String Full_name = FullName.getText();
        String Email_adress = EmailAdress.getText();
        String Phone_number = PhoneNumber.getText();
        String Password1 = Password.getText();

        String paramtre = "[a-zA-Z0-9_]{0,20}$";
        Pattern pattern = Pattern.compile(paramtre);
        Matcher matcher1 = pattern.matcher(Full_name);


        String paramtre1 = "[0-9]{0,10}$";
        Pattern pattern1 = Pattern.compile(paramtre1);
        Matcher matcher2 = pattern1.matcher(Phone_number);

        String paramtre3 = "[a-zA-Z0-9._]{0,20}[@][a-zA-Z0-9]{0,7}[.][a-zA-Z]{0,5}";
        Pattern pattern3 = Pattern.compile(paramtre3);
        Matcher matcher3 = pattern3.matcher(Email_adress);

        if (User_name.isEmpty() || Full_name.isEmpty() || Email_adress.isEmpty() || Phone_number.isEmpty() || Password1.isEmpty()) {
            AlertMessage.setText("Please Fill All DATA");
        }else if (!matcher1.matches()){
            AlertMessage.setText("Enter a Correct Full_name !");
        }else if (!matcher2.matches()){
            AlertMessage.setText("Enter Correct Phone_number !");
        }else if (!matcher3.matches()){
            AlertMessage.setText("Enter Correct Gmail !");
        }
        else {
            Query();
            insert();
            AlertMessage.setText("Update Sccess !!");

        }
    }


    void Query() {
        sql = "UPDATE `User` SET "
                + "`UserName`=?,"
                + "`Name`=?,"
                + "`Email`=?,"
                + "`PhoneNumber`=?,"
                + "`Password`= ? WHERE `UserName` = ?";

    }

    void insert() {

        try {

            PreparedStatement Prest = con.prepareStatement(sql);
            Prest.setString(1, Name.getText());
            Prest.setString(2, FullName.getText());
            Prest.setString(3, EmailAdress.getText());
            Prest.setString(4, PhoneNumber.getText());
            Prest.setString(5, Decrypting.encrypt(Password.getText()));
            Prest.setString(6, Name.getText());
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
        String User_name = Name.getText();
        String paramtre = "[a-zA-Z0-9_]{0,20}$";
        Pattern pattern = Pattern.compile(paramtre);
        Matcher matcher = pattern.matcher(User_name);
        if (!matcher.matches()){
            AlertMessage.setText("Enter a Valid UserName !");
        }else{
            AlertMessage.setText("");
        }

    }
    @FXML
    void Alert1(){
        String User_name = FullName.getText();
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
        String User_name = PhoneNumber.getText();
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
        String User_name = EmailAdress.getText();
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
    public void displayUserName(String username) {
        Name.setText(username);
        refreshUser();
    }

}
