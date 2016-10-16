/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.models.User;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;

/**
 *
 * @author vladislavvoicehovic
 */
@WebServlet(name = "ProfilePicture", urlPatterns = {"/Profile/ProfilePicture"})
public class ProfilePicture extends HttpServlet {

    private Cluster cluster;

    public void init(ServletConfig config) throws ServletException {
            super.init(config);
            cluster = CassandraHosts.getCluster();
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        
        
            HttpSession session = request.getSession();
            String username = ((LoggedIn) session.getAttribute("LoggedIn")).getUsername();
            User u = new User();
            u.setCluster(cluster);
            ServletContext context = getServletContext();
            Pic profilePic = u.getProfilePicture(username, context);

            ByteBuffer bb = profilePic.getBuffer();
            byte[] picAsBytes = new byte[bb.remaining()];
            bb.get(picAsBytes);

           
            
            response.setContentType(profilePic.getType());
            response.setContentLength(picAsBytes.length);

            OutputStream out = response.getOutputStream();
            out.write(picAsBytes);
            out.close();
        
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
