/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
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
 * @author Administrator
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    Cluster cluster=null;


    public void init(ServletConfig config) throws ServletException {//called when first accessed /Login
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        
        Map<String, String> errorMessages = new HashMap<String, String>();
        if (username == null || username.isEmpty()) {
            errorMessages.put("username", "Please enter username");
        }

        if (password == null || password.isEmpty()) {
            errorMessages.put("password", "Please enter password");
        }

        if (errorMessages.isEmpty()) {
        
        
                User us=new User();
                us.setCluster(cluster);
                boolean isValid=us.IsValidUser(username, password);
                HttpSession session=request.getSession();
                System.out.println("Session in servlet "+session);
                
                if (isValid){
                    LoggedIn lg= new LoggedIn();
                    lg.setUsername(username);
                    session.setAttribute("LoggedIn", lg);
                   
                
                    response.sendRedirect(request.getContextPath()+"/");
                    return;

                }else{
                     errorMessages.put("login", "Login or password incorrect");
                     
                }
        }
        
        request.setAttribute("messages", errorMessages);
        request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
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
