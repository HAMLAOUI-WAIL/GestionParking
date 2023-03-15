package com.example.my_project;

import com.example.my_project.parkingDB.DataBaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dashboard implements Initializable {

    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart barChart;

    @FXML
    private Pane pane1;

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

    int Floor1 = 0;
    int Floor2 = 0;
    int Floor3 = 0;
    int Floor4 = 0;
    int NumberOfPlaceInFloor;
    int NumberOfPlace = 0;
    ResultSet resultSet = null ;


    @FXML
    void getStatistic1(){
        pieChart.setVisible(true);
        Dashboard dashboard = new Dashboard();
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        data.add(new PieChart.Data("Availabale places",dashboard.getNumberOfPlaceInFloor(1)));
        data.add(new PieChart.Data("Occupied places",dashboard.getNumberOfPlace(1)));
        pieChart.setData(data);
        pane1.setVisible(true);
        pane2.setVisible(true);
        Places1.setText(String.valueOf(dashboard.getNumberOfPlace(1)));
        Places2.setText(String.valueOf(dashboard.getNumberOfPlaceInFloor(1)-Integer.parseInt(Places1.getText())));
        image.setVisible(true);
        barChart.setVisible(false);

    }
    @FXML
    void getStatistic2(){
        pieChart.setVisible(true);
        Dashboard dashboard = new Dashboard();
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        data.add(new PieChart.Data("Availabale places",dashboard.getNumberOfPlaceInFloor(2)));
        data.add(new PieChart.Data("Occupied places",dashboard.getNumberOfPlace(2)));
        pieChart.setData(data);
        pane1.setVisible(true);
        pane2.setVisible(true);
        Places1.setText(String.valueOf(dashboard.getNumberOfPlace(2)));
        Places2.setText(String.valueOf(dashboard.getNumberOfPlaceInFloor(2)-Integer.parseInt(Places1.getText())));
        image.setVisible(true);
        barChart.setVisible(false);

    }
    @FXML
    void getStatistic3(){
        pieChart.setVisible(true);
        Dashboard dashboard = new Dashboard();
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        data.add(new PieChart.Data("Availabale places",dashboard.getNumberOfPlaceInFloor(3)));
        data.add(new PieChart.Data("Occupied places",dashboard.getNumberOfPlace(3)));
        pieChart.setData(data);
        pane1.setVisible(true);
        pane2.setVisible(true);
        Places1.setText(String.valueOf(dashboard.getNumberOfPlace(3)));
        Places2.setText(String.valueOf(dashboard.getNumberOfPlaceInFloor(3)-Integer.parseInt(Places1.getText())));
        image.setVisible(true);
        barChart.setVisible(false);

    }
    @FXML
    void getStatistic4(){
        pieChart.setVisible(true);
        Dashboard dashboard = new Dashboard();
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        data.add(new PieChart.Data("Availabale places",dashboard.getNumberOfPlaceInFloor(4)));
        data.add(new PieChart.Data("Occupied places",dashboard.getNumberOfPlace(4)));
        pieChart.setData(data);
        pane1.setVisible(true);
        pane2.setVisible(true);
        Places1.setText(String.valueOf(dashboard.getNumberOfPlace(4)));
        Places2.setText(String.valueOf(dashboard.getNumberOfPlaceInFloor(4)-Integer.parseInt(Places1.getText())));
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
            Dashboard dashboard = new Dashboard();
            XYChart.Series XYOccupiedPlaces = new XYChart.Series();
            XYOccupiedPlaces.setName("OccupiedPlaces");
            XYOccupiedPlaces.getData().add(new XYChart.Data<>("Floor1", Floor1));
            XYOccupiedPlaces.getData().add(new XYChart.Data<>("Floor2", Floor2));
            XYOccupiedPlaces.getData().add(new XYChart.Data<>("Floor3", Floor3));
            XYOccupiedPlaces.getData().add(new XYChart.Data<>("Floor4", Floor4));
            barChart.getData().addAll(XYOccupiedPlaces);
            TotalPlaces1.setText(String.valueOf(Floor1+Floor2+Floor3+Floor4));
            TotalPlaces2.setText(String.valueOf((dashboard.getNumberOfPlaceInFloor(1)+dashboard.getNumberOfPlaceInFloor(2)+dashboard.getNumberOfPlaceInFloor(3)+dashboard.getNumberOfPlaceInFloor(4))-(Floor1+Floor2+Floor3+Floor4)));
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

    int getNumberOfPlaceInFloor(int Floor) {
        try {
        String sql = "SELECT * FROM `etage` WHERE idEtage =" +Floor;
        Connection con = DataBaseConnection.con;
        PreparedStatement Prest = con.prepareStatement(sql);
        resultSet = Prest.executeQuery();

        while (resultSet.next()) {
            NumberOfPlaceInFloor = resultSet.getInt("nombreDePlace");
        }
    }catch (SQLException ex) {
            Logger.getLogger(Controller2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return NumberOfPlaceInFloor;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoadBarChart();
    }
}
