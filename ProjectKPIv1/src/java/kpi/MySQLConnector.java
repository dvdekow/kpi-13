package kpi;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author David Eko
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLConnector {
    private Connection connect = null;
    private PreparedStatement statement = null;
    private ResultSet result = null;
    
    //credential
    private String DBname = "kpi";
    private String username = "root";
    private String password = "root";
    
    public MySQLConnector() {
        try {
         Class.forName("com.mysql.jdbc.Driver");
            //create connection
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DBname + "?" + "user=" + username + "&password=" + password);
        }catch (ClassNotFoundException e) {
            System.out.println("classnotfound");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public ResultSet getResult() {
        return result;
    }
    
    public void prepareQuery(String query) {
        try {
            statement = connect.prepareStatement(query);
        } catch (SQLException ex) {
            Logger.getLogger(MySQLConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setIntToQuery(int idx, int value) {
        if (statement != null) {
            try {
                statement.setInt(idx, value);
            } catch (SQLException ex) {
                //Logger.getLogger(MySQLConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void setStringToQuery(int idx, String value) {
        if (statement != null) {
            try {
                statement.setString(idx, value);
            } catch (SQLException ex) {
                //Logger.getLogger(MySQLConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void setBooleanToQuery(int idx, boolean value) {
        if (statement != null) {
            try {
                statement.setBoolean(idx, value);
            } catch (SQLException ex) {
                //Logger.getLogger(MySQLConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void executeQuery() {
        if (statement != null) {
            try {
                result = statement.executeQuery();
            } catch (SQLException ex) {
                Logger.getLogger(MySQLConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void executeUpdate() {
        if (statement != null) {
            try {
                statement.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(MySQLConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
