package com.example.my_project;

import com.example.my_project.parkingDB.DataBaseConnection;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Reserve {

    @FXML
    private TextField NbFloor;

    @FXML
    private Label AlertMessage;

    @FXML
    private TextField NbPlace;

    @FXML
    private TextField NbRegester;


    @FXML
    void getRegestrationNumber() {
        // Captur();
        Tesseract tr = new Tesseract();
        tr.setDatapath("Lib\\Tess4J\\tessdata");
        String text = null;
        try {
            text = tr.doOCR(new File("Matricule.png"));
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        NbRegester.setText("Num : [" + text + "]");
        getPlace();
        getFloor();
    }

    @FXML
    void Camera() {
        VideoCapture capture = new VideoCapture(0);
        Mat image = new Mat();
        while (true) {
            capture.read(image);
            final MatOfByte buf = new MatOfByte();
            Imgcodecs.imencode("Matricule.png", image, buf);
            Imgcodecs.imwrite("r1.png", image);
        }
    }

    @FXML
    void Captur() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        final Reserve camera = new Reserve();
        new Thread(camera::Camera).start();
    }

    public List<Integer> getRandomElement(List<Integer> list, int totalItems) {
        Random rand = new Random();
        List<Integer> newList = new ArrayList<>();
        for (int i = 0; i < totalItems; i++) {
            int randomIndex = rand.nextInt(list.size());
            newList.add(list.get(randomIndex));
            list.remove(randomIndex);
        }
        return newList;
    }

    @FXML
    void getPlace() {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 200; i++) {
            list.add(i);
        }
        Reserve obj = new Reserve();
        int numberOfElements = 1;
        NbPlace.setText("Place : " + obj.getRandomElement(list, numberOfElements));
    }

    @FXML
    void getFloor() {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            list.add(i);
        }
        Reserve obj = new Reserve();
        int numberOfElements = 1;
        NbFloor.setText("Floor : " + obj.getRandomElement(list, numberOfElements));
    }

    @FXML
    void ValidateRegistration() {
        String Nb_register = NbRegester.getText();
        String Nb_place = NbPlace.getText();
        String Nb_floor = NbFloor.getText();
        if (Nb_register.isEmpty() || Nb_place.isEmpty() || Nb_floor.isEmpty()) {
            AlertMessage.setText("Please Fill All DATA");
        } else {
            AddCar();
            getReservation();
            clean();
            AlertMessage.setText("");
        }
    }

    String sql = null;
    Connection con = DataBaseConnection.con;

    @FXML
    void AddCar() {
        String Nb_register = NbRegester.getText();
        String Nb_place = NbPlace.getText();
        String Nb_floor = NbFloor.getText();
        if (Nb_register.isEmpty() || Nb_place.isEmpty() || Nb_floor.isEmpty()) {
            AlertMessage.setText("Please Fill All DATA");
        } else {
            getQuery();
            insert();
            Controller1.AdminPage("Register");
            AlertMessage.setText("");
        }

    }

    void getQuery() {
        sql = "INSERT INTO `resevation`( `Etage`, `Place`, `Matricule`) VALUES (?,?,?)";
    }

    @FXML
    private void clean() {
        NbRegester.setText(null);
        NbPlace.setText(null);
        NbFloor.setText(null);
    }


    void insert() {

        try {

            PreparedStatement Prest = con.prepareStatement(sql);
            Prest.setString(1, NbFloor.getText().replaceAll("[^0-9]", ""));
            Prest.setString(2, NbPlace.getText().replaceAll("[^0-9]", ""));
            Prest.setString(3, NbRegester.getText().replaceAll("[^0-9]", ""));
            Prest.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    void getReservation() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDate actualDate = LocalDate.now();
            String actualeTime = LocalDateTime.now().format(formatter);
            Document Doc = new Document();
            try {
                PdfWriter.getInstance(Doc, new FileOutputStream("Reservation.pdf"));
                Rectangle one = new Rectangle(250, 250);
                Doc.setPageSize(one);
                Doc.setMargins(10, 10, 10, 10);
                Doc.open();
                Doc.add(new Paragraph("\n                   Ticket for " + NbRegester.getText().replaceAll("[^0-9]", "") +
                        "\n---------------------------------------------------------" +
                        "\n                    Date :  " + actualDate +
                        "\n        Entry time :  " + actualeTime +
                        "\n---------------------------------------------------------" +
                        "\n                           Floor :  " + NbFloor.getText().replaceAll("[^0-9]", "") +
                        "\n                          Place : " + NbPlace.getText().replaceAll("[^0-9]", "") +
                        "\n---------------------------------------------------------" +
                        "\n                      Have a nice day"));
                Doc.close();
                Desktop.getDesktop().open(new File("Reservation.pdf"));

            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }
        }


    }