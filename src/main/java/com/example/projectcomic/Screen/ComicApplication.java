package com.example.projectcomic.Screen;

import com.example.projectcomic.DBConnect.ConnectDatabase;
import com.example.projectcomic.model.Admin;
import com.example.projectcomic.model.Comic;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;


public class ComicApplication extends Application {
    private static final String EMPTY = "";
    private Scene login, homePage;
    public  TextField username, pass_word;
    private static final ConnectDatabase connect = new ConnectDatabase();
    private Stage stage;

    Scene displayLogin(){
        VBox loginPage = new VBox();
        Label lbLogin =new Label("Please enter your username and password");
        Label lbUsername = new Label("User Name: ");
        Label lbPassword = new Label("Password: ");
        username = new TextField();
        pass_word= new TextField();

        HBox fdName = new HBox();
        fdName.getChildren().addAll(lbUsername,username);
        fdName.setSpacing(20);
        fdName.setAlignment(Pos.BASELINE_CENTER);

        HBox fdPass = new HBox();
        fdPass.getChildren().addAll(lbPassword,pass_word);
        fdPass.setSpacing(10);
        fdPass.setAlignment(Pos.BASELINE_CENTER);

        Button btnGoBack = new Button("LOG OUT");
        btnGoBack.setOnAction(actionEventBack -> {
            stage.setScene(homePage);
            stage.centerOnScreen();
        });
        Button btnLogin = new Button("LOGIN");
        btnLogin.setOnAction(actionEvent -> {
            this.checkLogin();
        });
        HBox btnLoginPage = new HBox();
        btnLoginPage.getChildren().addAll(btnLogin, btnGoBack );
        btnLoginPage.setSpacing(10);
        btnLoginPage.setAlignment(Pos.BASELINE_CENTER);
        loginPage.getChildren().addAll(lbLogin,fdName,fdPass,btnLoginPage);
        loginPage.setSpacing(15);
        loginPage.setAlignment(Pos.BASELINE_CENTER);

        return new Scene(loginPage, 300,300);
    }
    private void checkLogin(){
        ArrayList<Admin> ad = connect.getAdmin();
        String inputUserName = username.getText();
        String inputPass_word = pass_word.getText();
        if(inputUserName.equals(ad.get(0).username)&& inputPass_word.equals(ad.get(0).pass_word)){
            LoginSuccess();
            stage.setScene(homePage);
            stage.centerOnScreen();
        }else{
            LoginError();
        }
    }
    private void LoginSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login");
        alert.setHeaderText("Hi "+ username.getText());
        alert.setContentText("Login successfully!");
        alert.show();
    }
    private void LoginError() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ERROR");
        alert.setContentText("Login fail. Please enter again!");
        alert.show();
    }

    Scene displayProduct() {
        //Add
        VBox home = new VBox();
        ArrayList<Comic> bookList = connect.getComic();
        GridPane root = new GridPane();

        root.add(new Label("Name Book:"), 0, 0);
        var tfName = new TextField();
        root.add(tfName, 0, 1);

        root.add(new Label("Image:"), 1, 0);
        var tfImage = new TextField();
        root.add(tfImage, 1, 1);

        root.add(new Label("Author:"), 2, 0);
        var tfAuthor = new TextField();
        root.add(tfAuthor, 2, 1);

        root.add(new Label("Quantity:"),3,  0);
        var tfQuantity = new TextField();
        root.add(tfQuantity, 3, 1);

        root.add(new Label("Price:"), 4, 0);
        var tfPrice = new TextField();
        root.add(tfPrice, 4, 1);

        var btnAdd = new Button("Add");
        btnAdd.setPadding(new Insets(4, 14, 4, 14));
        btnAdd.setOnAction(e -> {
            String nameC = tfName.getText();
            String image = tfImage.getText();
            String author = tfAuthor.getText();
            Integer quantity = 0;
            try {
                quantity = Integer.parseInt(tfQuantity.getText());
                } catch (Exception ex) {
            }
            Double price = 0.0;
            try {
                price = Double.parseDouble(tfPrice.getText());
            }
            catch (Exception ex) {
            }

            if (!nameC.equals(EMPTY) && !image.equals(EMPTY) && !author.equals(EMPTY) && !(quantity == 0) && !(price == 0.0)) {
                try {
                    connect.insertBook(new Comic(nameC, image, author, quantity, price ));
                    homePage = displayProduct();
                    stage.setScene(homePage);
                    stage.centerOnScreen();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                return;
            }
            var alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank!");
            alert.showAndWait();
        });
        root.add(btnAdd, 5, 1);

        //Display
        for(int i = 0; i < bookList.size(); i++){

            Image image = new Image(bookList.get(i).getImage());
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);

            root.add(new Label (bookList.get(i).getNameC()), 0, i+2);
            root.add(imageView, 1, i+2);
            root.add(new Label (bookList.get(i).getAuthor()), 2, i+2);
            root.add(new Label (" "+ bookList.get(i).getQuantity()), 3, i+2);
            root.add(new Label (" "+ bookList.get(i).getPrice()), 4, i+2);

            // Update
            var btnUpdate = new Button("Update");
            btnUpdate.setBackground(new Background(new BackgroundFill(Color.YELLOW, new CornerRadii(0), Insets.EMPTY)));
            btnUpdate.setId(String.valueOf(i));
            btnUpdate.setOnAction(e -> {
                btnAdd.setVisible(false);
                int newId = Integer.parseInt(btnUpdate.getId());

                TextField tfNameC = (TextField) root.getChildren().get(1);
                tfNameC.setText("" + bookList.get(newId).getNameC());

                TextField tfImageC = (TextField) root.getChildren().get(3);
                tfImageC.setText("" + bookList.get(newId).getImage());

                TextField tfAuthorC = (TextField) root.getChildren().get(5);
                tfAuthorC.setText("" + bookList.get(newId).getAuthor());

                TextField tfQuantityC = (TextField) root.getChildren().get(7);
                tfQuantityC.setText("" + bookList.get(newId).getQuantity());

                TextField tfPriceC = (TextField) root.getChildren().get(9);
                tfPriceC.setText("" + bookList.get(newId).getPrice());

                var btnNewAdd = new Button("Update");
                btnNewAdd.setPadding(new Insets(4, 14, 4, 14));

                btnNewAdd.setOnAction(actionEvent -> {
                    Integer new_id = bookList.get(newId).id;
                    String newNamC = tfNameC.getText();
                    String newImage = tfImageC.getText();
                    String newAuthor = tfAuthorC.getText();
                    Integer newQuantity = 0;
                    try {
                        newQuantity = Integer.parseInt(tfQuantity.getText());
                    } catch (Exception ex) {
                    }
                    Double newPrice = 0.0;
                    try {
                        newPrice = Double.parseDouble(tfPrice.getText());
                    } catch (Exception ex) {
                    }
                    if (!new_id.equals(EMPTY) && !newNamC.equals(EMPTY) && !newImage.equals(EMPTY) && !newAuthor.equals(EMPTY) && !(newQuantity == 0) && !(newPrice == 0.0)) {
                        try {
                            connect.updateBook(new Comic(new_id, newNamC, newImage, newAuthor, newQuantity, newPrice));
                            homePage = displayProduct();
                            stage.setScene(homePage);
                            stage.centerOnScreen();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        return;
                    }
                    var alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill all blank!");
                    alert.showAndWait();
                });
                root.add(btnNewAdd, 5, 1);
            });
            root.add(btnUpdate, 5, i+2);

            //Delete
            var btnDelete = new Button("Delete");
            btnDelete.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(0), Insets.EMPTY)));
            btnDelete.setId(String.valueOf(bookList.get(i).id));
            btnDelete.setOnAction(e -> {
                int idDel = Integer.parseInt(btnDelete.getId());
                connect.deleteBook(idDel);
                var alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Do you want to delete it?");
                alert.showAndWait();
                try {
                    homePage = displayProduct();
                    stage.setScene(homePage);
                    stage.centerOnScreen();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
            root.add(btnDelete, 6, i+2);
        }

        root.setPadding(new Insets(50));
        root.setHgap(30);
        root.setVgap(20);
        home.getChildren().add(root);
        return new Scene(home, 1200,600);
    }

    @Override
    public void start(Stage primaryStage) {
        homePage = this.displayProduct();
        login = this.displayLogin();
        stage = primaryStage;
        primaryStage.setTitle("Mange comic book");
        stage.setScene(this.displayLogin());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}