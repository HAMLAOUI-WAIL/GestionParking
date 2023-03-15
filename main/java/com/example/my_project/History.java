package com.example.my_project;

import com.example.my_project.parkingDB.DataBaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class History implements Initializable {
    @FXML
    private TableColumn<HistoryInformation, String> ID;

    @FXML
    private TableColumn<HistoryInformation, String> bill;

    @FXML
    private TableColumn<HistoryInformation, String> date;

    @FXML
    private TableColumn<HistoryInformation, String> floor;

    @FXML
    private TableColumn<HistoryInformation, String> place;

    @FXML
    private TableColumn<HistoryInformation, String> registration_number;

    @FXML
    private TableColumn<HistoryInformation, String> Car_Information;

    @FXML
    private Label FloorNumber;

    @FXML
    private Label PlaceNumber;

    @FXML
    private Label RegestrationNumber;

    @FXML
    private Label EntryTime;

    @FXML
    private Label Bill;

    @FXML
    private AnchorPane Pane;

    @FXML
    private TextField txt_Search;

    @FXML
    private TableView<HistoryInformation> car_information;

    Tooltip TooltipButton2 = new Tooltip();


    ObservableList<HistoryInformation> CarList = FXCollections.observableArrayList();
    ObservableList<HistoryInformation> CarList_ = FXCollections.observableArrayList();
    String sql = null;
    ResultSet resultSet = null ;
    HistoryInformation Car = null ;

    @FXML
    public void refreshTable() {
        try {
            CarList.clear();
            sql = "SELECT * FROM `historique`";
            Connection con = DataBaseConnection.con;
            PreparedStatement Prest = con.prepareStatement(sql);
            resultSet = Prest.executeQuery();

            while (resultSet.next()) {
                CarList.add(new HistoryInformation(
                        resultSet.getInt("Id"),
                        resultSet.getString("Etage"),
                        resultSet.getString("Place"),
                        resultSet.getString("Matricule"),
                        resultSet.getString("Date"),
                        resultSet.getString("Facture")));
                car_information.setItems(CarList);

            }

            ID.setCellValueFactory(new PropertyValueFactory<>("id"));
            floor.setCellValueFactory(new PropertyValueFactory<>("Etage"));
            place.setCellValueFactory(new PropertyValueFactory<>("Place"));
            registration_number.setCellValueFactory(new PropertyValueFactory<>("Matricule"));
            date.setCellValueFactory(new PropertyValueFactory<>("Date"));
            bill.setCellValueFactory(new PropertyValueFactory<>("Facture"));
            addButtonToTable();

        } catch (SQLException ex) {
            Logger.getLogger(Controller2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    void Select(int Floor){
        try {
            CarList.clear();
            sql = "SELECT * FROM `historique` WHERE `Etage` ="+Floor;
            Connection con = DataBaseConnection.con;
            PreparedStatement Prest = con.prepareStatement(sql);
            resultSet = Prest.executeQuery();
            while (resultSet.next()) {
                CarList.add(new HistoryInformation(
                        resultSet.getInt("Id"),
                        resultSet.getString("Etage"),
                        resultSet.getString("Place"),
                        resultSet.getString("Matricule"),
                        resultSet.getString("Date"),
                        resultSet.getString("Facture")));
                car_information.setItems(CarList);

            }

            ID.setCellValueFactory(new PropertyValueFactory<>("id"));
            floor.setCellValueFactory(new PropertyValueFactory<>("Etage"));
            place.setCellValueFactory(new PropertyValueFactory<>("Place"));
            registration_number.setCellValueFactory(new PropertyValueFactory<>("Matricule"));
            date.setCellValueFactory(new PropertyValueFactory<>("Date"));
            bill.setCellValueFactory(new PropertyValueFactory<>("Facture"));

        } catch (SQLException ex) {
            Logger.getLogger(Controller2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    public void addButtonToTable () {

        Callback<TableColumn<HistoryInformation, String>, TableCell<HistoryInformation, String>> cellFactory = new Callback<>() {
            @Override
            public TableCell<HistoryInformation, String> call(final TableColumn<HistoryInformation, String> param) {
                return new TableCell<>() {
                    private final Button btn1 = new Button();

                    {
                        btn1.setOnAction((ActionEvent event) -> {
                            Car = car_information.getItems().get(getIndex());
                            ShowInformation(Car.getId());
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
                            Image image2 = null;
                            try {
                                image2 = new Image(Objects.requireNonNull(getClass().getResource("Car.png")).toURI().toString());
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                            ImageView view2 = new ImageView(image2);
                            view2.setFitHeight(35);
                            view2.setFitWidth(35);
                            btn1.setGraphic(view2);
                            btn1.setTooltip(TooltipButton2);
                            HBox managebtn = new HBox(btn1);
                            managebtn.setStyle("-fx-alignment:center");
                            HBox.setMargin(btn1, new Insets(2, 2, 0, 2));
                            setGraphic(managebtn);
                        }
                    }
                };
            }
        };

        Car_Information.setCellFactory(cellFactory);

    }
    @FXML
    void Floor1(){
        Select(1);
    }
    @FXML
    void Floor2(){
        Select(2);
    }
    @FXML
    void Floor3(){
        Select(3);
    }
    @FXML
    void Floor4(){
        Select(4);
    }
    @FXML
    void Cancel1(){
        Pane.setVisible(false);
    }

    @FXML
    public void ShowInformation(int Car) {
        try {
            CarList_.clear();
            sql = "SELECT * FROM `historique` WHERE Id  =" + Car;
            Connection con = DataBaseConnection.con;
            PreparedStatement Prest = con.prepareStatement(sql);
            resultSet = Prest.executeQuery();
            while (resultSet.next()) {
                CarList_.add(new HistoryInformation(
                        resultSet.getInt("Id"),
                        resultSet.getString("Etage"),
                        resultSet.getString("Place"),
                        resultSet.getString("Matricule"),
                        resultSet.getString("Date"),
                        resultSet.getString("Facture")));
            }
            RegestrationNumber.setText(CarList_.get(0).Matricule);
            FloorNumber.setText(CarList_.get(0).Etage);
            PlaceNumber.setText(CarList_.get(0).Place);
            EntryTime.setText(CarList_.get(0).Date);
            Bill.setText(CarList_.get(0).Facture);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void Search() {
        txt_Search.setOnKeyReleased(e->{
            if (txt_Search.getText().equals("")) {
                try {
                    CarList.clear();
                    sql = "SELECT * FROM `historique` ";
                    Connection con = DataBaseConnection.con;
                    PreparedStatement Prest = con.prepareStatement(sql);
                    resultSet = Prest.executeQuery();
                    while (resultSet.next()) {
                        CarList_.add(new HistoryInformation(
                                resultSet.getInt("Id"),
                                resultSet.getString("Etage"),
                                resultSet.getString("Place"),
                                resultSet.getString("Matricule"),
                                resultSet.getString("Date"),
                                resultSet.getString("Facture")));
                    }
                    ID.setCellValueFactory(new PropertyValueFactory<>("id"));
                    floor.setCellValueFactory(new PropertyValueFactory<>("Etage"));
                    place.setCellValueFactory(new PropertyValueFactory<>("Place"));
                    registration_number.setCellValueFactory(new PropertyValueFactory<>("Matricule"));
                    date.setCellValueFactory(new PropertyValueFactory<>("Date"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }else {
                FilteredList<HistoryInformation> filteredList = new FilteredList<>(CarList, b->true);
                txt_Search.setOnKeyReleased(e2-> {
                    txt_Search.textProperty().addListener((observable, oldValue, newValue) -> filteredList.setPredicate((Predicate<? super HistoryInformation>) e1 -> {
                        if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                            return true;
                        }
                        String searchedKey = newValue.toLowerCase();
                        return e1.getEtage().contains(searchedKey) || e1.getMatricule().contains(searchedKey) || e1.getDate().contains(searchedKey);
                    }));
                    SortedList<HistoryInformation> sortedList = new SortedList<>(filteredList);
                    sortedList.comparatorProperty().bind(car_information.comparatorProperty());
                    car_information.setItems(sortedList);
                });
            }
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshTable();
        TooltipButton2.setStyle("-fx-background-color :  #6aa6ff ;");
        TooltipButton2.setText("Car Information");
        Search();
    }
}
