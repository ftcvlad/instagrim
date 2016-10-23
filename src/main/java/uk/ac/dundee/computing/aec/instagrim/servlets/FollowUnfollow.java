/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.lib.Convertors;
import uk.ac.dundee.computing.aec.instagrim.models.FollowersModel;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;

/**
 *
 * @author vladislavvoicehovic
 */
@WebServlet(name = "FollowUnfollow", urlPatterns = {"/FollowUnfollow/*"})
public class FollowUnfollow extends HttpServlet {

    private Cluster cluster;

    @Override
    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }
    
    
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        String targetUser = request.getParameter("targetUser");
        String args[] = Convertors.SplitRequestPath(request);//Instagrim FollowUnfollow follow/unfollow
        
        HttpSession session=request.getSession(false);
        String currentUsername = ((LoggedIn)session.getAttribute("LoggedIn")).getUsername();
        
        if (args[2].equals("follow")){
            
            FollowersModel fm = new FollowersModel();
            fm.setCluster(cluster);
            boolean success = fm.addFollower(currentUsername, targetUser);
            
            if (!success){
                response.setStatus(400);
            }
            
        }
        else if (args[2].equals("unfollow")){
            FollowersModel fm = new FollowersModel();
            fm.setCluster(cluster);
            boolean success = fm.removeFollower(currentUsername, targetUser);
            
            if (!success){
                response.setStatus(400);
            }
        }
        
        
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
