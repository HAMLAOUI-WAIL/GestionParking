package com.example.my_project;

import com.example.my_project.parkingDB.DataBaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller5 implements Initializable {

    @FXML
    private TableColumn<Users, String> Email_adress;

    @FXML
    private TableColumn<Users, String> Full_name;

    @FXML
    private TableColumn<Users, String> Phone_number;

    @FXML
    private TableColumn<Users, String> User_name;

    @FXML
    private TableColumn<Users, String> ID;

    @FXML
    private TableColumn<Users, String> Edit;

    @FXML
    private TableView<Users> Tabel_user;

    @FXML
    private AnchorPane Pane;

    @FXML
    private Button NotVisible;

    @FXML
    private Button Visible;

    @FXML
    private PasswordField Password;

    @FXML
    private TextField PasswordText;

    @FXML
    private TextField EmailAdress;

    @FXML
    private TextField FullName;

    @FXML
    private TextField Name;

    @FXML
    private Label AlertMessage;

    @FXML
    private TextField PhoneNumber;

    @FXML
    private TextField id;

    final Tooltip TooltipButton = new Tooltip();


    Connection con = DataBaseConnection.con;
    ObservableList<Users> UsersList = FXCollections.observableArrayList();
    ObservableList<Users> UserList = FXCollections.observableArrayList();
    String sql = null;
    ResultSet resultSet = null ;
    Users users = null ;
    @FXML
    public void refreshTable() {
        try {
            UsersList.clear();
            sql = "SELECT * FROM `user`";
            Connection con = DataBaseConnection.con;
            PreparedStatement Prest = con.prepareStatement(sql);
            resultSet = Prest.executeQuery();

            while (resultSet.next()) {
                UsersList.add(new Users(
                        resultSet.getInt("Id"),
                        resultSet.getString("UserName"),
                        resultSet.getString("Name"),
                        resultSet.getString("Email"),
                        resultSet.getString("PhoneNumber"), resultSet.getString("Password")));
                Tabel_user.setItems(UsersList);

            }

            ID.setCellValueFactory(new PropertyValueFactory<>("id"));
            Full_name.setCellValueFactory(new PropertyValueFactory<>("FullName"));
            User_name.setCellValueFactory(new PropertyValueFactory<>("UserName"));
            Email_adress.setCellValueFactory(new PropertyValueFactory<>("EmailAdress"));
            Phone_number.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
            addButtonToTable();

        } catch (SQLException ex) {
            Logger.getLogger(Controller2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        @FXML
        public void addButtonToTable () {

            Callback<TableColumn<Users, String>, TableCell<Users, String>> cellFactory = new Callback<>() {
                @Override
                public TableCell<Users, String> call(final TableColumn<Users, String> param) {
                    return new TableCell<>() {

                        private final Button btn1 = new Button();

                        {
                            btn1.setOnAction((ActionEvent event) -> {
                                AlertMessage.setText("");
                                users = Tabel_user.getItems().get(getIndex());
                                refreshUser(users.getId());
                                Pane.setVisible(true);
                            }
                            );
                        }

                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {

                                btn1.setStyle(
                                        "  -fx-background-color: transparent;"
                                );
                                btn1.setCursor(Cursor.HAND);

                                Image image1 = null;
                                try {
                                    image1 = new Image(Objects.requireNonNull(getClass().getResource("Profile.png")).toURI().toString());
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                                ImageView view1 = new ImageView(image1);
                                view1.setFitHeight(50);
                                view1.setFitWidth(50);
                                btn1.setGraphic(view1);
                                btn1.setTooltip(TooltipButton);

                                HBox managebtn = new HBox(btn1);
                                managebtn.setStyle("-fx-alignment:center");
                                HBox.setMargin(btn1, new Insets(2, 2, 0, 2));
                                setGraphic(managebtn);
                            }
                        }
                    };
                }
            };

            Edit.setCellFactory(cellFactory);

        }


    @FXML
    public void refreshUser(int UserId) {
        try {
            UserList.clear();
            sql = "SELECT * FROM `user` WHERE Id  =" +UserId;
            Connection con = DataBaseConnection.con;
            PreparedStatement Prest = con.prepareStatement(sql);
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
            id.setText(UserList.get(0).id+"");
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
    void Cancel() {
       Pane.setVisible(false);
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
    void UpdateUser(int UserID) {
        String User_name = Name.getText();
        String Full_name = FullName.getText();
        String Email_adress = EmailAdress.getText();
        String Phone_number = PhoneNumber.getText();
        String Password1 = Password.getText();

        String paramtre = "[a-zA-Z0-9]{0,20}$";
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
            AlertMessage.setText("Enter a Valid Full_name !");
        }else if (!matcher2.matches()){
            AlertMessage.setText("Enter a Valid Phone_number !");
        }else if (!matcher3.matches()){
            AlertMessage.setText("Enter a Valid Email !");
        }
        else {
            Query(UserID);
            insert();
            AlertMessage.setText("Update Sccess !!");

        }

    }

    void Query(int UserId) {
        sql = "UPDATE `User` SET "
                + "`UserName`=?,"
                + "`Name`=?,"
                + "`Email`=?,"
                + "`PhoneNumber`=?,"
                + "`Password`= ? WHERE id = '" + UserId + "'";

    }

    void insert() {

        try {

            PreparedStatement Prest = con.prepareStatement(sql);
            Prest.setString(1, Name.getText());
            Prest.setString(2, FullName.getText());
            Prest.setString(3, EmailAdress.getText());
            Prest.setString(4, PhoneNumber.getText());
            Prest.setString(5, Decrypting.encrypt(Password.getText()));
            Prest.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    @FXML
    void Update(){
        int UserId = Integer.parseInt(id.getText());
        UpdateUser(UserId);
        refreshTable();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshTable();
        TooltipButton.setStyle("-fx-background-color :  #6aa6ff ;");
        TooltipButton.setText("User Information");
    }
}
