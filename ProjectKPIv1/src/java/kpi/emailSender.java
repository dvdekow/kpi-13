/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kpi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 *
 * @author Kevin
 */
public class emailSender {
    
    String host = "localhost";    // Assuming you are sending email from localhost
    String to;
    String from = "kemapi.tugas@gmail.com";
    String msg;
    String userid;
    
    
    private void getEmail(String user){
    MySQLConnector connector = new MySQLConnector();
    String query = "select * from user where Username = ?";
    connector.prepareQuery(query);
    connector.setStringToQuery(1, user);
    connector.executeQuery();
    ResultSet result = connector.getResult();
    try {
        if (result.isBeforeFirst()){
            result.first();
            to = result.getString("E-Mail");
        }
    }catch(Exception e) {
        
    }
    }
    
    public void setRecipient(String userID){
        userid = userID;
        getEmail(userID);
    }
        
    private String generateToken(){
        char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 32; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String token = sb.toString();
        MySQLConnector connector = new MySQLConnector();
        connector.prepareQuery("insert into token (Username,Token) value (?,?)");
        connector.setStringToQuery(1, userid);
        connector.setStringToQuery(2, token);
        connector.executeUpdate();
        return token;
    }
    
    public void setMessage(String message) {
        msg = message;
    }
        
    public void SendEmail()
    {   // Assuming you are sending email from localhost
      
      Properties properties = System.getProperties();   // Get system properties

      //properties.setProperty("mail.smtp.host", host);   // Setup mail server

      properties.put("mail.smtp.host", "smtp.gmail.com"); 
      properties.put("mail.smtp.socketFactory.port", "465");
      properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
      properties.put("mail.smtp.auth", "true");
      properties.put("mail.smtp.port", "465");
      
      Session session = Session.getInstance(properties,
              new javax.mail.Authenticator() {  
                protected PasswordAuthentication getPasswordAuthentication() {  
                    return new PasswordAuthentication("kemapi.tugas@gmail.com","hanyatest");  
                }} 
            ); // Get the default Session object.

      try{
         MimeMessage message = new MimeMessage(session);    // Create a default MimeMessage object.

         message.setFrom(new InternetAddress(from));        // Set From: header field of the header.

         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); // Set To: header field of the header.

         message.setSubject("Reset Password | Tugas KPI");     // Set Subject: header field

         message.setText(msg + " http://localhost:8080/ProjectKPIv1/ResetPassword.xhtml?key=" + generateToken());

         Transport.send(message);
      }catch (MessagingException mex) {
         mex.printStackTrace();
        }
    }
}
