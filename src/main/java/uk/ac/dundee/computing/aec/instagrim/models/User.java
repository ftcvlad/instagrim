/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.dundee.computing.aec.instagrim.models;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import uk.ac.dundee.computing.aec.instagrim.lib.AeSimpleSHA1;
import uk.ac.dundee.computing.aec.instagrim.lib.ImageResizer;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;
/**
 *
 * @author Administrator
 */
public class User {
    Cluster cluster;
    public User(){
        
    }
    
    public  String RegisterUser(String username, String Password){
        AeSimpleSHA1 sha1handler=  new AeSimpleSHA1();
        String EncodedPassword=null;
       
        try {
            EncodedPassword= sha1handler.SHA1(Password);
        }catch (UnsupportedEncodingException | NoSuchAlgorithmException et){
            return "Can't check your password";
        }
        Session session = cluster.connect("instagrim");
        
        
        
        
        
        PreparedStatement ps = session.prepare("insert into userprofiles (login,password) Values(?,?) IF NOT EXISTS");
       
        BoundStatement boundStatement = new BoundStatement(ps);
        ResultSet rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username,EncodedPassword));
        
        if (rs.wasApplied()==false){
            return "Username exists";
        }
        
        return null; //??? username and password inserted if username doesn't exist
      
        
//... Also a transaction would be good here !
        
        
    }
    
    public boolean IsValidUser(String username, String Password){
        AeSimpleSHA1 sha1handler=  new AeSimpleSHA1();
        String EncodedPassword=null;
        try {
            EncodedPassword= sha1handler.SHA1(Password);
        }catch (UnsupportedEncodingException | NoSuchAlgorithmException et){
            System.out.println("Can't check your password");
            return false;
        }
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select password from userprofiles where login =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username));
        if (rs.isExhausted()) {
            return false;
        } else {
            for (Row row : rs) {
               
                String StoredPass = row.getString("password");
                if (StoredPass.compareTo(EncodedPassword) == 0)
                    return true;
            }
        }
   
    
    return false;  
    }
    
    public void editUserProfile(byte[] b, String type,String firstName, String secondName, String email, String status, String currentUsername){
        
    
        
        Session session = cluster.connect("instagrim");
        BoundStatement bs ;
        if (b.length>0){
            BufferedImage bi = null;
            try{
                InputStream in = new ByteArrayInputStream(b);
                bi = ImageIO.read(in);
            }
            catch(IOException ioe){
            }
            
            byte[] resizedBytes = ImageResizer.resizeToSquare(bi, type.substring(type.lastIndexOf("/") + 1, type.length()), 150);
            
            ByteBuffer buffer = ByteBuffer.wrap(resizedBytes);
            PreparedStatement ps = session.prepare("UPDATE instagrim.userprofiles "
                + "SET first_name = ?,last_name=?,email=?,status=?, profilePicture=?, picType=?  WHERE login=?");
            bs = new BoundStatement(ps);
            bs.bind(firstName, secondName, email, status, buffer, type, currentUsername);
        }
        else{
            
            PreparedStatement ps = session.prepare("UPDATE instagrim.userprofiles "
                + "SET first_name = ?,last_name=?,email=?,status=?  WHERE login=?");
            bs = new BoundStatement(ps);
            bs.bind(firstName, secondName, email, status, currentUsername);
        }
        
       
        session.execute(bs);

    }
    
    
    
    
    
    
    
    
    
    
    public Map<String,String> getUserInfo(String username){
        Session session = cluster.connect("instagrim");
       
         
        PreparedStatement  ps = session.prepare("select first_name,last_name, status,email from userprofiles where login =?");
        BoundStatement bs = new BoundStatement(ps);
        bs.bind(username);
        ResultSet rs = session.execute(bs);        
        
        if (rs.isExhausted()){
            return null;
        }
        else{
            Row singleRow = rs.one();


             Map<String,String> map = new HashMap<String,String>();
             String name = singleRow.getString("first_name");
             String surname = singleRow.getString("last_name");
             String email = singleRow.getString("email");
             String status = singleRow.getString("status");

             map.put("name", name==null?"":name);
             map.put("surname",surname==null?"":surname );
             map.put("email",email==null?"":email );
             map.put("status",status==null?"":status);
             map.put("username", username);
             
           
             FollowersModel fm = new FollowersModel();
             fm.setCluster(cluster);
             long[] counts =  fm.countFollowFollowers(username);
             
             map.put("following", counts[0]+"");
             map.put("followers", counts[1]+"");
             
             return map;
        }
        
            
    }
    
    public Pic getProfilePicture(String username ,ServletContext context){

          Session session = cluster.connect("instagrim");
          PreparedStatement  ps = session.prepare("select profilePicture, picType from userprofiles where login =?");
          BoundStatement bs = new BoundStatement(ps);
          bs.bind(username);
          
          
          ResultSet rs = session.execute(bs);    
          
          if (!rs.isExhausted()){
              Row singleRow = rs.one();
              ByteBuffer bImage = singleRow.getBytes("profilePicture");
              String type = singleRow.getString("picType");

              if (bImage == null) {
                  type = "image/png";//type

                  try {
                      Path path = Paths.get(context.getRealPath("/static/images/nopic.png"));
                      byte[] data = Files.readAllBytes(path);
                      bImage = ByteBuffer.wrap(data);//buffer

                  } catch (IOException io) {
                      System.out.println("*****BAD PATH*****");
                  }

              }

              Pic p = new Pic();
              p.setPic(bImage, type);
              return p;
          }
          else{
              return null;
          }

     }
    
    
    
    
    
    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    
}
