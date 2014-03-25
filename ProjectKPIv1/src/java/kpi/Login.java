/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kpi;

/**
 *
 * @author David Eko
 */
 
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.faces.bean.ManagedBean; 
import javax.faces.bean.ManagedProperty; 
import javax.faces.bean.RequestScoped;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "Login", eager = true) 
@RequestScoped
public class Login {
    
    public String userID;
    public String password;
    
    public FacesContext facesContext = FacesContext.getCurrentInstance();
    public HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
    
    public Login(){}
    
    public String getUserID(){
        return userID;
    }
    
    public void setUserID(String userID){
        this.userID = userID;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public void setSessionString(String name, String value) {
        session.setAttribute(name, value);
    }
    
    public void setSessionInt(String name, int value) {
        session.setAttribute(name, value);
    }

    public String checkValidUser() throws SQLException {
        MySQLConnector sqlCon = new MySQLConnector();
        sqlCon.prepareQuery("select * from user where Username = ?");
        sqlCon.setStringToQuery(1, userID);
        sqlCon.executeQuery();
        ResultSet resultSet = sqlCon.getResult();
        if (resultSet.isBeforeFirst()){
            resultSet.first();
            boolean isBlocked = resultSet.getBoolean("Blocked");
            if (isBlocked) {
                return "fail";
            } else {
                String userSalt = resultSet.getString("Salt");
                String userPass = resultSet.getString("Password");
                if (Hash.hasher(password.concat(userSalt)).equalsIgnoreCase(userPass)) {
                    //facesContext.getExternalContext().invalidateSession();
                    session = (HttpSession) facesContext.getExternalContext().getSession(true);
                    setSessionString("user", userID);
                    return "success";
                } else {
                    Integer count = (Integer)session.getAttribute(userID);
                    if (count == null) {
                        setSessionInt(userID, 1);
                    } else {
                        int countVal = count.intValue();
                        setSessionInt(userID, countVal + 1);
                        if (countVal >= 4) {
                            sqlCon.prepareQuery("update user set Blocked = true where Username = ?");
                            sqlCon.setStringToQuery(1, userID);
                            sqlCon.executeUpdate();
                            emailSender resetMailer = new emailSender();
                            resetMailer.setMessage("Akun Anda telah diblokir karena 5 kali gagal log-in. Untuk mengaktifasi kembali akun Anda, silahkan ikuti tautan berikut ini");
                            resetMailer.setRecipient(userID);
                            resetMailer.SendEmail();
                        }
                    }
                    return "fail";
                }
            }
        }
        return "fail";
    }
    
    public String clearSession() {
        session.removeAttribute("user");
        return "index";
    }
    
    
    public String forgotPassword(){
        //emailSender test = new emailSender();
        //test.setRecipient(userID);
        //test.SendEmail();
        return "success";
    }

    
}
