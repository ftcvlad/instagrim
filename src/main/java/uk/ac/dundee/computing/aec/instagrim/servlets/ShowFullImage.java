/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.lib.Convertors;

/**
 *
 * @author Vlad
 */
@WebServlet(name = "ShowFullImage", urlPatterns = {"/ShowFullImage/*"})
public class ShowFullImage extends HttpServlet {

    
    
    private Cluster cluster;

    public void init(ServletConfig config) throws ServletException {
            super.init(config);
            cluster = CassandraHosts.getCluster();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        
        
         String args[] = Convertors.SplitRequestPath(request);// Instagrim ShowFullImage SUUID
         
         String SUUID = args[2];
         
         request.setAttribute("SUUID", SUUID);
         
         request.getRequestDispatcher("/WEB-INF/imagePage.jsp").forward(request,response);
        
        
        
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
