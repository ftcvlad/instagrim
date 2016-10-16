/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.lib.ExtValidator;
import uk.ac.dundee.computing.aec.instagrim.models.User;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;

/**
 *
 * @author vladislavvoicehovic
 */
@WebServlet(name = "ProfileEdit", urlPatterns = {"/Profile/Edit"})
@MultipartConfig
public class ProfileEdit extends HttpServlet {
    private Cluster cluster;
  
    public void init(ServletConfig config) throws ServletException {
           // TODO Auto-generated method stub
           cluster = CassandraHosts.getCluster();
    }
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            HttpSession session = request.getSession();
            String username = ((LoggedIn) session.getAttribute("LoggedIn")).getUsername();
            User u = new User();
            u.setCluster(cluster);
            Map<String,String> inputFieldValues = u.getUserInfo(username);
          
           System.out.println("   ========"+inputFieldValues.get("name"));
            request.setAttribute("inputFieldValues",inputFieldValues);
        
          request.getRequestDispatcher("/WEB-INF/profileEdit.jsp").forward(request, response);
        
        
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
     
        
           

            String firstname =request.getParameter("firstname");
            String secondname =request.getParameter("lastname");
           
            String email =request.getParameter("email");
            String status =request.getParameter("status");
            HttpSession session=request.getSession();
            String currentUsername = ((LoggedIn)session.getAttribute("LoggedIn")).getUsername();
            
            //http://stackoverflow.com/questions/2422468/how-to-upload-files-to-server-using-jsp-servlet/2424824#2424824
            Part filePart = request.getPart("profilePic"); 
            
            
            String type = filePart.getContentType();
            String filename = filePart.getSubmittedFileName();
            
             
            if (!ExtValidator.validate(type, filename)){
                 request.setAttribute("updateSuccess", false); 
                 request.getRequestDispatcher("/WEB-INF/profileEdit.jsp").forward(request, response);
                 return;
            }
            
            
            InputStream is = filePart.getInputStream();
            
            
            byte[] bytes = IOUtils.toByteArray(is);

            User u = new User();
            u.setCluster(cluster);
            u.editUserProfile(bytes,type, firstname, secondname, email,status,currentUsername);
            
            
            request.setAttribute("updateSuccess", true); 
            request.getRequestDispatcher("/WEB-INF/profileEdit.jsp").forward(request, response);
             
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
