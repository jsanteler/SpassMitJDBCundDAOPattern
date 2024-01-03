package at.itkollegimst;

import dataaccess.MysqlDatabaseConnection;
import ui.Cli;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        Cli myCli = new Cli();
        myCli.start();

        try {
            Connection myConnection =
                    MysqlDatabaseConnection.getConnection("jdbc:mysql://localhost:3306/kurssystem", "root", "1234");
            System.out.println("Verbindung aufgebaut");

        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}