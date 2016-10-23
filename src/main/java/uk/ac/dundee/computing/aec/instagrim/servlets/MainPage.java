/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.models.FollowersModel;
import uk.ac.dundee.computing.aec.instagrim.models.User;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;

/**
 *
 * @author vladislavvoicehovic
 */
@WebServlet(name = "MainPage", urlPatterns = {""})
public class MainPage extends HttpServlet {

    
    
    private Cluster cluster;

    @Override
    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }
  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        
       
        
        HttpSession session = request.getSession(false);
        if (session!=null && session.getAttribute("LoggedIn")!=null){
            String currentUsername = ((LoggedIn)session.getAttribute("LoggedIn")).getUsername();
            
            
            FollowersModel fm = new FollowersModel();
            fm.setCluster(cluster);
            List<String> followed = fm.getFollowedUsernames(currentUsername);
            
            request.setAttribute("followed",followed);
        }
        
        
        

        
        
        request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request,response);
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
