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

import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.faces.bean.ManagedBean; 
import javax.faces.bean.ManagedProperty; 
import javax.faces.bean.RequestScoped;
import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@ManagedBean(name = "Upload", eager = true) 
@RequestScoped
public class Upload {
  private Part file;
  private String fileContent;
  
  public void upload() {
    try {
      fileContent = new Scanner(file.getInputStream())
          .useDelimiter("\\A").next();
    } catch (IOException e) {
      // Error handlinge
        e.printStackTrace();
    }
  }
 
  public Part getFile() {
    return file;
  }
 
  public void setFile(Part file) {
    this.file = file;
  }
  
  public void uploadCopy(){
      InputStream inputStr = null;
      
      try{
          inputStr = file.getInputStream();
      }catch (IOException e) {
          
      }
      
      String savePath = "C:/";
      File savedFile = new File(savePath);
      
      OutputStream output = null;
      
      try {
          byte[] buf = new byte[1024];
          int bytesRead;
          while ((bytesRead = inputStr.read(buf)) > 0) {
              output.write(buf, 0, bytesRead);
          }
      }catch (IOException e) {
          //
      }
      //
  }
    
}
