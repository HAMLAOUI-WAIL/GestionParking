package com.example.my_project;

import com.example.my_project.parkingDB.DataBaseConnection;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Floors implements Initializable {
    @FXML
    private TableColumn<CarInformation, String> ID;

    @FXML
    private TableColumn<CarInformation, String> date;

    @FXML
    private TableColumn<CarInformation, String> edit;

    @FXML
    private TableColumn<CarInformation, String> floor;

    @FXML
    private TableColumn<CarInformation, String> place;

    @FXML
    private TableColumn<CarInformation, String> registration_number;

    @FXML
    private TableView<CarInformation> car_information;

    @FXML
    private Pane PricePane;

    @FXML
    private AnchorPane Pane;

    @FXML
    private Label PriceText;

    @FXML
    private Label TextId;

    @FXML
    private Label FloorNumber;

    @FXML
    private Label PlaceNumber;

    @FXML
    private Label RegestrationNumber;

    @FXML
    private Label EntryTime;

    @FXML
    private Label Name;

    final Tooltip TooltipButton2 = new Tooltip();
    final Tooltip TooltipButton1 = new Tooltip();

    @FXML
    private TextField txt_Search;


    ObservableList<CarInformation> CarList = FXCollections.observableArrayList();
    ObservableList<CarInformation> CarInformationList = FXCollections.observableArrayList();
    ObservableList<HistoryInformation> Revenu = FXCollections.observableArrayList();
    ObservableList<Users> User = FXCollections.observableArrayList();
    String sql = null;
    ResultSet resultSet = null;
    CarInformation Car = null;

    @FXML
    public void refreshTable() {
        try {
            CarList.clear();
            sql = "SELECT * FROM `resevation`";
            Connection con = DataBaseConnection.con;
            PreparedStatement Prest = con.prepareStatement(sql);
            resultSet = Prest.executeQuery();

            while (resultSet.next()) {
                CarList.add(new CarInformation(
                        resultSet.getInt("Id"),
                        resultSet.getString("Etage"),
                        resultSet.getString("Place"),
                        resultSet.getString("Matricule"),
                        resultSet.getString("Date")));
                car_information.setItems(CarList);

            }

            ID.setCellValueFactory(new PropertyValueFactory<>("id"));
            floor.setCellValueFactory(new PropertyValueFactory<>("Etage"));
            place.setCellValueFactory(new PropertyValueFactory<>("Place"));
            registration_number.setCellValueFactory(new PropertyValueFactory<>("Matricule"));
            date.setCellValueFactory(new PropertyValueFactory<>("Date"));
            addButtonToTable();

        } catch (SQLException ex) {
            Logger.getLogger(Controller2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void addButtonToTable() {

        Callback<TableColumn<CarInformation, String>, TableCell<CarInformation, String>> cellFactory = new Callback<>() {
            @Override
            public TableCell<CarInformation, String> call(final TableColumn<CarInformation, String> param) {
                return new TableCell<>() {

                    private final Button btn1 = new Button();
                    private final Button btn2 = new Button();

                    {
                        btn1.setOnAction((ActionEvent event) -> {

                                    try {
                                        Car = car_information.getItems().get(getIndex());
                                        sql = "SELECT Date FROM `resevation` WHERE Id  =" + Car.getId();
                                        Connection con = DataBaseConnection.con;
                                        PreparedStatement Prest = con.prepareStatement(sql);
                                        resultSet = Prest.executeQuery();
                                        String time = null;
                                        while (resultSet.next()) {
                                            time = resultSet.getString("Date").substring(0,19);
                                        }
                                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                        assert time != null;
                                        LocalDateTime Start = LocalDateTime.parse(time, formatter);
                                        LocalDateTime End = LocalDateTime.now();
                                        String Price = ChronoUnit.MINUTES.between(Start, End) + ".00 DA";
                                        TextId.setText(Car.getId() + "");
                                        PriceText.setText(Price);
                                        PricePane.setVisible(true);


                                    } catch (SQLException ex) {
                                        ex.printStackTrace();
                                    }

                                }
                        );
                        btn2.setOnAction((ActionEvent event) -> {
                            Car = car_information.getItems().get(getIndex());
                            Pane.setVisible(true);
                            ShowInformation(Car.getId());

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
                                image1 = new Image(Objects.requireNonNull(getClass().getResource("payments.png")).toURI().toString());
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                            ImageView view1 = new ImageView(image1);
                            view1.setFitHeight(25);
                            view1.setFitWidth(25);
                            btn1.setGraphic(view1);
                            btn1.setTooltip(TooltipButton1);
                            btn2.setStyle(
                                    "  -fx-background-color: transparent;"
                            );

                            btn2.setCursor(Cursor.HAND);
                            Image image2 = null;
                            try {
                                image2 = new Image(Objects.requireNonNull(getClass().getResource("Car.png")).toURI().toString());
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                            ImageView view2 = new ImageView(image2);
                            view2.setFitHeight(25);
                            view2.setFitWidth(25);
                            btn2.setGraphic(view2);
                            btn2.setTooltip(TooltipButton2);
                            HBox managebtn = new HBox(btn2, btn1);
                            managebtn.setStyle("-fx-alignment:center");
                            HBox.setMargin(btn1, new Insets(2, 2, 0, 2));
                            HBox.setMargin(btn2, new Insets(2, 2, 0, 2));
                            setGraphic(managebtn);
                        }
                    }
                };
            }
        };

        edit.setCellFactory(cellFactory);

    }

    @FXML
    void Select(int Floor) {
        try {
            CarList.clear();
            sql = "SELECT * FROM `resevation` WHERE `Etage` =" + Floor;
            Connection con = DataBaseConnection.con;
            PreparedStatement Prest = con.prepareStatement(sql);
            resultSet = Prest.executeQuery();
            while (resultSet.next()) {
                CarList.add(new CarInformation(
                        resultSet.getInt("Id"),
                        resultSet.getString("Etage"),
                        resultSet.getString("Place"),
                        resultSet.getString("Matricule"),
                        resultSet.getString("Date")));
                car_information.setItems(CarList);

            }

            ID.setCellValueFactory(new PropertyValueFactory<>("id"));
            floor.setCellValueFactory(new PropertyValueFactory<>("Etage"));
            place.setCellValueFactory(new PropertyValueFactory<>("Place"));
            registration_number.setCellValueFactory(new PropertyValueFactory<>("Matricule"));
            date.setCellValueFactory(new PropertyValueFactory<>("Date"));
            addButtonToTable();

        } catch (SQLException ex) {
            Logger.getLogger(Controller2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void Print2() {
        try {
            CarList.clear();
            sql = "SELECT * FROM `resevation` WHERE Id  =" + TextId.getText();
            Connection con = DataBaseConnection.con;
            PreparedStatement Prest = con.prepareStatement(sql);
            resultSet = Prest.executeQuery();

            while (resultSet.next()) {
                CarList.add(new CarInformation(
                        resultSet.getInt("Id"),
                        resultSet.getString("Etage"),
                        resultSet.getString("Place"),
                        resultSet.getString("Matricule"),
                        resultSet.getString("Date")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        String etage = CarList.get(0).Etage;
        String place = CarList.get(0).Place;
        String matricule = CarList.get(0).Matricule;
        String date = CarList.get(0).Date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate actualDate = LocalDate.now();
        String actualeTime = LocalDateTime.now().format(formatter);
        InsertInHistory(Integer.parseInt(TextId.getText()), PriceText.getText());
        InsertInRevenue();
        Delete(Integer.parseInt(TextId.getText()));
        Document Doc = new Document();
        try {
            PdfWriter.getInstance(Doc, new FileOutputStream("Bill.pdf"));
            Rectangle one = new Rectangle(250, 300);
            Doc.setPageSize(one);
            Doc.setMargins(10, 10, 10, 10);
            Doc.open();
            Doc.add(new Paragraph("\n                    Bill for " + matricule +
                    "\n---------------------------------------------------------" +
                    "\n                    Date :  " + actualDate +
                    "\n        Entry time :  " + date +
                    "\n          Exit time :  " + actualeTime +
                    "\n---------------------------------------------------------" +
                    "\n                      Price " + PriceText.getText() +
                    "\n---------------------------------------------------------" +
                    "\n                           Floor :  " + etage +
                    "\n                          Place : " + place +
                    "\n---------------------------------------------------------" +
                    "\n                      Have a nice day"));
            Doc.close();
            Desktop.getDesktop().open(new File("Bill.pdf"));

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        PricePane.setVisible(false);
    }

    @FXML
    void Floor1() {
        Select(1);
    }

    @FXML
    void Floor2() {
        Select(2);
    }

    @FXML
    void Floor3() {
        Select(3);
    }

    @FXML
    void Floor4() {
        Select(4);
    }

    @FXML
    void InsertInHistory(int CarId, String facture) {
        try {
            CarList.clear();
            sql = "SELECT * FROM `resevation` WHERE Id  =" + CarId;
            Connection con = DataBaseConnection.con;
            PreparedStatement Prest = con.prepareStatement(sql);
            resultSet = Prest.executeQuery();

            while (resultSet.next()) {
                CarList.add(new CarInformation(
                        resultSet.getInt("Id"),
                        resultSet.getString("Etage"),
                        resultSet.getString("Place"),
                        resultSet.getString("Matricule"),
                        resultSet.getString("Date")));

            }
            String etage = CarList.get(0).Etage;
            String place = CarList.get(0).Place;
            String matricule = CarList.get(0).Matricule;
            String date = CarList.get(0).Date;
            getQuery();
            try {
                Prest = con.prepareStatement(sql);
                Prest.setString(1, etage);
                Prest.setString(2, place);
                Prest.setString(3, matricule);
                Prest.setString(4, date);
                Prest.setString(5, facture);
                Prest.execute();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void getQuery() {
        sql = "INSERT INTO `historique`( `Etage`, `Place`, `Matricule`, `Date`,`Facture`) VALUES (?,?,?,?,?)";
    }

    void Delete(int CarId) {

        try {
            sql = "DELETE FROM `resevation` WHERE Id  =" + CarId;
            Connection con = DataBaseConnection.con;
            PreparedStatement Prest = con.prepareStatement(sql);
            Prest.execute();
            refreshTable();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void Cancel() {
        PricePane.setVisible(false);
    }

    @FXML
    void Cancel1() {
        Pane.setVisible(false);
    }

    @FXML
    public void ShowInformation(int Car) {
        try {
            CarInformationList.clear();
            sql = "SELECT * FROM `resevation` WHERE Id  =" + Car;
            Connection con = DataBaseConnection.con;
            PreparedStatement Prest = con.prepareStatement(sql);
            resultSet = Prest.executeQuery();
            while (resultSet.next()) {
                CarInformationList.add(new CarInformation(
                        resultSet.getInt("Id"),
                        resultSet.getString("Etage"),
                        resultSet.getString("Place"),
                        resultSet.getString("Matricule"),
                        resultSet.getString("Date")));
            }
            RegestrationNumber.setText(CarInformationList.get(0).Matricule);
            FloorNumber.setText(CarInformationList.get(0).Etage);
            PlaceNumber.setText(CarInformationList.get(0).Place);
            EntryTime.setText(CarInformationList.get(0).Date);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void getQuery2() {
        sql = "INSERT INTO `revenu`( `IdUser`,`IdFacture`) VALUES (?,?)";
    }

    void InsertInRevenue() {
        try {
            Revenu.clear();
            sql = "SELECT * FROM `historique` WHERE Facture = '"+PriceText.getText()+"'";
            Connection con = DataBaseConnection.con;
            PreparedStatement Prest = con.prepareStatement(sql);
            resultSet = Prest.executeQuery();

            while (resultSet.next()) {
                Revenu.add(new HistoryInformation(
                        resultSet.getInt("Id"),
                        resultSet.getString("Etage"),
                        resultSet.getString("Place"),
                        resultSet.getString("Matricule"),
                        resultSet.getString("Date"),
                        resultSet.getString("Facture")));
            }
            String facture = Revenu.get(0).Facture;
            int UserId = getUserId();
            getQuery2();
            try {
                Prest = con.prepareStatement(sql);
                Prest.setInt(1, UserId);
                Prest.setString(2, facture);
                Prest.execute();

            } catch (SQLException ex) {
                Logger.getLogger(Controller2.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    int getUserId() {
        try {
            Revenu.clear();
            sql = "SELECT * FROM `User` WHERE UserName = '"+Name.getText()+"'";
            Connection con = DataBaseConnection.con;
            PreparedStatement Prest = con.prepareStatement(sql);
            resultSet = Prest.executeQuery();
            while (resultSet.next()) {
                User.add(new Users(
                        resultSet.getInt("Id"),
                        resultSet.getString("UserName"),
                        resultSet.getString("Name"),
                        resultSet.getString("Email"),
                        resultSet.getString("PhoneNumber"),
                        resultSet.getString("Password")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return User.get(0).id;
    }
    public void displayUserName(String username) {
        Name.setText(username);
    }

    @FXML
    void Search() {
        txt_Search.setOnKeyReleased(e->{
            if (txt_Search.getText().equals("")) {
                try {
                    CarList.clear();
                    sql = "SELECT * FROM `resevation` ";
                    Connection con = DataBaseConnection.con;
                    PreparedStatement Prest = con.prepareStatement(sql);
                    resultSet = Prest.executeQuery();
                    while (resultSet.next()) {
                        CarList.add(new CarInformation(
                                resultSet.getInt("Id"),
                                resultSet.getString("Etage"),
                                resultSet.getString("Place"),
                                resultSet.getString("Matricule"),
                                resultSet.getString("Date")));
                        car_information.setItems(CarList);

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
                FilteredList<CarInformation> filteredList = new FilteredList<>(CarList, b->true);
                txt_Search.setOnKeyReleased(e2-> {
                    txt_Search.textProperty().addListener((observable, oldValue, newValue) -> filteredList.setPredicate((Predicate<? super CarInformation>) e1 -> {
                        if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                            return true;
                        }
                        String searchedKey = newValue.toLowerCase();
                        return e1.getEtage().contains(searchedKey) || e1.getMatricule().contains(searchedKey) || e1.getDate().contains(searchedKey);
                    }));
                    SortedList<CarInformation> sortedList = new SortedList<>(filteredList);
                    sortedList.comparatorProperty().bind(car_information.comparatorProperty());
                    car_information.setItems(sortedList);
                });
            }
        });
    }


    @Override
        public void initialize (URL url, ResourceBundle resourceBundle){
            refreshTable();
            TooltipButton2.setStyle("-fx-background-color :  #6aa6ff ;");
            TooltipButton2.setText("Car Information");
            TooltipButton1.setStyle("-fx-background-color :  #6aa6ff ;");
            TooltipButton1.setText("The Bill");
            Search();

        }
    }
