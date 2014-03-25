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

import javax.faces.bean.ManagedBean; 
import javax.faces.bean.RequestScoped;
import javax.faces.event.AjaxBehaviorEvent;

@ManagedBean(name = "Register", eager = true) 
@RequestScoped
public class Register {
    
    public String newUserID;
    public String newEmail;
    public String newPassword;
    public String newConfPassword;
    
    public String notifUsr;
    public String notifEmail;
    public String notifPassword;
    public String notifConfPassword;
    
    
    public String getNewUserID(){
        return newUserID;
    }
    
    public void setNewUserID(String userID){
        this.newUserID = userID;
    }
    
    public String getNewPassword(){
        return newPassword;
    }
    
    public void setNewPassword(String password){
        this.newPassword = password;
    }
    
    public String getNewEmail(){
        return newEmail;
    }
    
    public void setNewEmail(String mail) {
        this.newEmail = mail;
    }
    
    public String getNewConfPassword(){
        return newConfPassword;
    }
    
    public void setNewConfPassword(String pass){
        this.newConfPassword = pass;
    }
    
    public String getNotifUsr(){
        return notifUsr;
    }
    
    public void setNotifUsr(String pass){
        this.notifUsr = pass;
    }
    
    public String getNotifEmail(){
        return notifEmail;
    }
    
    public void setNotifEmail(String mail){
        this.notifEmail = mail;
    }
    
    public String getNotifPassword(){
        return notifPassword;
    }
    
    public void setNotifPassword(String pwd){
        this.notifPassword = pwd;
    }
    
    public String getNotifConfPassword(){
        return notifConfPassword;
    }
    
    public void setNotifConfPassword(String cpwd){
        this.notifConfPassword = cpwd;
    }
    
    public void checkUserID(AjaxBehaviorEvent event){
        if (getNewUserID().length() < 6) {
            notifUsr = "user minimal 6 karakter";
        }
    }
    
    public void checkEmail(AjaxBehaviorEvent event){
        if (!getNewEmail().contains("@")) {
            notifEmail = "email anda tidak valid";
        }
    }
    
    public void checkPassword(AjaxBehaviorEvent event){
        if (getNewPassword().length() < 6) {
            notifPassword = "password minimal 6 karakter";
        }
    }
    
    public void checkConfPassword(AjaxBehaviorEvent event){
        /*
        if (newEmail == null) {
            notifConfPassword = "Email also null";
        }
        */
        if (getNewConfPassword() == null) {
            notifConfPassword = "Confirmation Pass is null";
        } else if (getNewPassword() == null) {
            notifConfPassword = "Pass is null";
        }
        /*
        if (!getNewConfPassword().contentEquals(getNewPassword())) {
            notifConfPassword = "password tidak sama";
        }
        */
    }
    
    public String checkValidRegister(){
        if (newEmail == null) {
            notifEmail = "EMAIL IS NULL";
        } else {
            notifEmail = "EMAIL IS NOT NULL";
        }
        if ((notifUsr.contentEquals("")) && (notifPassword.contentEquals("")) && (notifConfPassword.contentEquals("")) && (notifEmail.contentEquals(""))){
            MySQLConnector sqlCon = new MySQLConnector();
            //sqlCon.prepareQuery("insert into user values (" + new_userID + "," + "" ")");
        }
        return "fail";
    }
}
