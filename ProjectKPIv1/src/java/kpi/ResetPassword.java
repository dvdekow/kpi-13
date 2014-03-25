/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kpi;

/**
 *
 * @author Kevin
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;


@ManagedBean(name = "ResetPassword", eager = true) 
@RequestScoped

public class ResetPassword {
    public String user;
    
    public String newPassword;
    public String newConfPassword;
    
    public String notifPassword;
    public String notifConfPassword;
    
    public String passedParameter;
    
    public String getPassedParameter(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        this.passedParameter = (String) facesContext.getExternalContext().getRequestParameterMap().get("token");
        return this.passedParameter;
    }
    
    public void setPassedParameter(String passedParameter){
        this.passedParameter = passedParameter;
    }
    
    public String getUser(){
        return user;
    }
    
    public void setUser(String usr){
        this.user = usr;
    }
    
    public String getNewPassword(){
        return newPassword;
    }
    
    public void setNewPassword(String password){
        this.newPassword = password;
    }
    
    public String getNewConfPassword(){
        return newConfPassword;
    }
    
    public void setNewConfPassword(String pass){
        this.newConfPassword = pass;
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
    
    public String Reset(){
        emailSender resetMailer = new emailSender();
        resetMailer.setMessage("Tautan untuk melakukan reset password anda adalah");
        resetMailer.setRecipient(user);
        resetMailer.SendEmail();
        return "reset";
    }
    
    public String searchUsername() {
        MySQLConnector connector = new MySQLConnector();
        String query = "select * from token where token = ?";
        connector.prepareQuery(query);
        connector.setStringToQuery(1, getPassedParameter());
        connector.executeQuery();
        ResultSet result = connector.getResult();
        try {
            if (result.isBeforeFirst()){
                result.first();
                return result.getString("Username");
            }
        }catch(SQLException e) {
            return null;
        }
        return null;
    }
    
    public void updateBlocked(){
        MySQLConnector connector = new MySQLConnector();
        String query = "update user set Blocked = ? where Username = ?";
        connector.setIntToQuery(1, 0);
        connector.setStringToQuery(2, searchUsername());
        connector.executeQuery();
        ResultSet result = connector.getResult();
    }
    
    public String createNewPassword(){
        MySQLConnector connector = new MySQLConnector();
        String query = "update user set password = ? where Username = ?";
        connector.prepareQuery(query);
        connector.setStringToQuery(1, Hash.hasher(newPassword));
        connector.setStringToQuery(2, searchUsername());
        connector.executeQuery();
        ResultSet result = connector.getResult();
        try {
            if (result.isBeforeFirst()){
                result.first();
                updateBlocked();
                return "index";
            }
        }catch(SQLException e) {
            return null;
        }
        return "index";
    }
}
