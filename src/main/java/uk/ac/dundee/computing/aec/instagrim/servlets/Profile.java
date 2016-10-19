/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.models.User;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;

/**
 *
 * @author vladislavvoicehovic
 */
@WebServlet(name = "ProfileUpload", urlPatterns = {"/Profile/Upload"})
public class Profile extends HttpServlet {

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
        Map<String, String> userInfo = u.getUserInfo(username);
        
        request.setAttribute("userInfo", userInfo);

        request.getRequestDispatcher("/WEB-INF/profileUploadImage.jsp").forward(request, response);


    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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
