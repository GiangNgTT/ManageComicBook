package com.example.projectcomic.DBConnect;

import com.example.projectcomic.model.Admin;
import com.example.projectcomic.model.Comic;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectDatabase {
    private Connection connection;

    public ConnectDatabase(){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/comicbook", "root","");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("connect successfully!");
    }
    public ArrayList<Comic> getComic(){
        ArrayList<Comic> list = new ArrayList<>();
        String sql = "SELECT * FROM ComicBookInfor";
        try {
            ResultSet results = connection.prepareStatement(sql).executeQuery();
            while (results.next()){
                Comic book = new Comic(
                        results.getInt("id"),
                        results.getString("nameC"),
                        results.getString("image"),
                        results.getString("author"),
                        results.getInt("quantity"),
                        results.getInt("price")

                );
                list.add(book);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
    public  void insertBook(Comic book){
        String sql = " INSERT INTO ComicBookInfor(nameC, image, author, quantity, price) " +
                "VALUES ('" + book.nameC + "', '" + book.image + "', '" + book.author + "', '" + book.quantity + "', '" + book.price + "')";
        System.out.println(sql);
        try {
            connection.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateBook(Comic book) {
        String sql = "UPDATE ComicBookInfor SET nameC = '" + book.nameC + "', image = '" + book.image + "', author = '" + book.author + "', quantity = '" + book.quantity + "', price ='" +book.price + "' WHERE id =" + book.id ;
        System.out.println(sql);
        try {
            connection.prepareStatement(sql).executeUpdate();
            System.out.println("UPDATE student id = "+ book.id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public  void deleteBook(int id){
        String sql = " DELETE FROM ComicBookInfor WHERE id = " + id;
        System.out.println(sql);
        try {
            connection.prepareStatement(sql).executeUpdate();
            System.out.println("Delete book at id = " + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public ArrayList<Admin> getAdmin() {
        ArrayList<Admin> admin = new ArrayList<>();
        try {
            var result = this.connection.prepareStatement("Select * from adminComic").executeQuery();
            while (result.next()) {
                String username = result.getString("username");
                String pass_word = result.getString("pass_word");
                System.out.println(username);
                System.out.println(pass_word);
                admin.add(new Admin( username, pass_word));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return admin;
    }


}
