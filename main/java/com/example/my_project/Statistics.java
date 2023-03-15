package com.example.my_project;

import com.example.my_project.parkingDB.DataBaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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

public class Statistics implements Initializable {
    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart barChart;

    @FXML
    private javafx.scene.layout.Pane pane1;

    @FXML
    private Pane pane2;

    @FXML
    private Label Places1;

    @FXML
    private Label Places2;

    @FXML
    private ImageView image;

    @FXML
    private Label TotalPlaces1;

    @FXML
    private Label TotalPlaces2;
    @FXML
    private TableColumn<Users, String> User;

    @FXML
    private TableColumn<Users, String> ID;

    @FXML
    private TableColumn<Users, String> UserName;

    @FXML
    private TableView<Users> RevenueTable;

    @FXML
    private Label UserRvenue;

    @FXML
    private Label User_name;

    @FXML
    private AnchorPane Pane;

    @FXML
    private AnchorPane Pane4;

    final Tooltip TooltipButton = new Tooltip();


    int Floor1 = 0;
    int Floor2 = 0;
    int Floor3 = 0;
    int Floor4 = 0;
    int NumberOfPlace = 0;


    ObservableList<Users> UsersList_ = FXCollections.observableArrayList();
    String sql = null;
    ResultSet resultSet = null ;
    Users users = null ;
    @FXML
    public void refreshTable() {
        try {
            UsersList_.clear();
            sql = "SELECT * FROM `user`";
            Connection con = DataBaseConnection.con;
            PreparedStatement Prest = con.prepareStatement(sql);
            resultSet = Prest.executeQuery();

            while (resultSet.next()) {
                UsersList_.add(new Users(
                        resultSet.getInt("Id"),
                        resultSet.getString("UserName"),
                        resultSet.getString("Name"),
                        resultSet.getString("Email"),
                        resultSet.getString("PhoneNumber"), resultSet.getString("Password")));
                RevenueTable.setItems(UsersList_);

            }

            ID.setCellValueFactory(new PropertyValueFactory<>("id"));
            UserName.setCellValueFactory(new PropertyValueFactory<>("FullName"));

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
                            users = RevenueTable.getItems().get(getIndex());
                                    try {
                                        UserRvenue.setText(getRevenueOfUser(users.getId())+"0 DA");
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                            User_name.setText(users.getFullName());
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
                            view1.setFitHeight(100);
                            view1.setFitWidth(100);
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

        User.setCellFactory(cellFactory);

    }
    double getRevenueOfUser(int Id) throws SQLException {
        double TotalRevenue = 0.0;
        Connection con = DataBaseConnection.con;
        if (con == null){
            System.out.println("Connection Failed");
        }
        String sql = "SELECT * FROM `revenu` WHERE IdUser= "+Id;
        con = DataBaseConnection.con;
        PreparedStatement Prest = con.prepareStatement(sql);
        resultSet = Prest.executeQuery();
        while (resultSet.next()) {
            String Revenue = resultSet.getString("IdFacture");
            StringBuilder rev =new StringBuilder(Revenue);
            String revenueResult = String.valueOf(rev.reverse());
            revenueResult = revenueResult.substring(2);
            rev = new StringBuilder(revenueResult);
            revenueResult = String.valueOf(rev.reverse());
            TotalRevenue = TotalRevenue + Double.parseDouble(revenueResult);
        }
        return  TotalRevenue;
    }
    @FXML
    void Cancel(){
        Pane.setVisible(false);
    }



    @FXML
    void getStatistic1(){
        pieChart.setVisible(true);
        Statistics dashboard = new Statistics();
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        data.add(new PieChart.Data("Availabale places",200));
        data.add(new PieChart.Data("Occupied places",dashboard.getNumberOfPlace(1)));
        pieChart.setData(data);
        pane1.setVisible(true);
        pane2.setVisible(true);
        Places1.setText(String.valueOf(dashboard.getNumberOfPlace(1)));
        Places2.setText(String.valueOf(200-Integer.parseInt(Places1.getText())));
        image.setVisible(true);
        barChart.setVisible(false);

    }
    @FXML
    void getStatistic2(){
        pieChart.setVisible(true);
        Statistics dashboard = new Statistics();
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        data.add(new PieChart.Data("Availabale places",200));
        data.add(new PieChart.Data("Occupied places",dashboard.getNumberOfPlace(2)));
        pieChart.setData(data);
        pane1.setVisible(true);
        pane2.setVisible(true);
        Places1.setText(String.valueOf(dashboard.getNumberOfPlace(2)));
        Places2.setText(String.valueOf(200-Integer.parseInt(Places1.getText())));
        image.setVisible(true);
        barChart.setVisible(false);

    }
    @FXML
    void getStatistic3(){
        pieChart.setVisible(true);
        Statistics dashboard = new Statistics();
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        data.add(new PieChart.Data("Availabale places",200));
        data.add(new PieChart.Data("Occupied places",dashboard.getNumberOfPlace(3)));
        pieChart.setData(data);
        pane1.setVisible(true);
        pane2.setVisible(true);
        Places1.setText(String.valueOf(dashboard.getNumberOfPlace(3)));
        Places2.setText(String.valueOf(200-Integer.parseInt(Places1.getText())));
        image.setVisible(true);
        barChart.setVisible(false);

    }
    @FXML
    void getStatistic4(){
        pieChart.setVisible(true);
        Statistics dashboard = new Statistics();
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        data.add(new PieChart.Data("Availabale places",200));
        data.add(new PieChart.Data("Occupied places",dashboard.getNumberOfPlace(4)));
        pieChart.setData(data);
        pane1.setVisible(true);
        pane2.setVisible(true);
        Places1.setText(String.valueOf(dashboard.getNumberOfPlace(4)));
        Places2.setText(String.valueOf(200-Integer.parseInt(Places1.getText())));
        image.setVisible(true);
        barChart.setVisible(false);
    }
    void LoadBarChart(){
        Connection con = DataBaseConnection.con;
        if (con == null){
            System.out.println("Connection Failed");
        }
        String sql = "SELECT * FROM `resevation` ORDER BY Etage asc ";
        try {
            assert con != null;
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()) {
                switch (rs.getInt("Etage")) {
                    case 1 -> Floor1++;
                    case 2 -> Floor2++;
                    case 3 -> Floor3++;
                    case 4 -> Floor4++;
                }

            }
            XYChart.Series XYOccupiedPlaces = new XYChart.Series();
            XYOccupiedPlaces.setName("OccupiedPlaces");
            XYOccupiedPlaces.getData().add(new XYChart.Data<>("Floor1", Floor1));
            XYOccupiedPlaces.getData().add(new XYChart.Data<>("Floor2", Floor2));
            XYOccupiedPlaces.getData().add(new XYChart.Data<>("Floor3", Floor3));
            XYOccupiedPlaces.getData().add(new XYChart.Data<>("Floor4", Floor4));
            barChart.getData().addAll(XYOccupiedPlaces);
            TotalPlaces1.setText(String.valueOf(Floor1+Floor2+Floor3+Floor4));
            TotalPlaces2.setText(String.valueOf(800-(Floor1+Floor2+Floor3+Floor4)));
        }catch (SQLException SE){
            System.out.println("Connection faild");
        }
    }
    @FXML
    void ReLoadBarChart(){
        Floor1 = 0;
        Floor2 = 0;
        Floor3 = 0;
        Floor4 = 0;
        barChart.getData().clear();
        pane1.setVisible(false);
        pane2.setVisible(false);
        image.setVisible(false);
        pieChart.setVisible(false);
        barChart.setVisible(true);
        LoadBarChart();

    }

    int getNumberOfPlace(int Floor) {
        Connection con = DataBaseConnection.con;
        if (con == null){
            System.out.println("Connection Failed");
        }
        String sql = "SELECT * FROM `resevation` ORDER BY Etage asc ";
        try {
            assert con != null;
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()) {
                switch (rs.getInt("Etage")) {
                    case 1 -> Floor1++;
                    case 2 -> Floor2++;
                    case 3 -> Floor3++;
                    case 4 -> Floor4++;
                }

            }

        } catch (SQLException SE) {
            System.out.println("Connection faild");
        }
        if (Floor == 1 ){
            NumberOfPlace = Floor1;
        }
        if (Floor == 2 ){
            NumberOfPlace = Floor2;
        }
        if (Floor == 3 ){
            NumberOfPlace = Floor3;
        }
        if (Floor == 4 ){
            NumberOfPlace = Floor4;
        }
        return NumberOfPlace/2;
    }

    @FXML
    void ShowPane2(){
        Floor1 = 0;
        Floor2 = 0;
        Floor3 = 0;
        Floor4 = 0;
        getStatistic1();
        LoadBarChart();
        Pane4.setVisible(true);

    }
    @FXML
    void Back(){
        Pane4.setVisible(false);
        pieChart.getData().clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshTable();
        TooltipButton.setStyle("-fx-background-color :  #6aa6ff ;");
        TooltipButton.setText("User revenue");
    }
}
