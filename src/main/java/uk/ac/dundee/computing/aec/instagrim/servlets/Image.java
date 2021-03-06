package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
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
import uk.ac.dundee.computing.aec.instagrim.lib.Convertors;
import uk.ac.dundee.computing.aec.instagrim.lib.ExtValidator;
import uk.ac.dundee.computing.aec.instagrim.models.FollowersModel;
import uk.ac.dundee.computing.aec.instagrim.models.PicModel;
import uk.ac.dundee.computing.aec.instagrim.models.User;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;

/**
 * Servlet implementation class Image
 */
@WebServlet(urlPatterns = {
    "/Image",
    "/Image/*",
    "/Thumb/*",
    "/Images",
    "/Images/*"
})
@MultipartConfig

public class Image extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Cluster cluster;
    private final HashMap CommandsMap = new HashMap();
    
    

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Image() {
        super();
        // TODO Auto-generated constructor stub
        CommandsMap.put("Image", 1);
        CommandsMap.put("Images", 2);
        CommandsMap.put("Thumb", 3);

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String args[] = Convertors.SplitRequestPath(request);
       
        
      
        int command;
        try {
            command = (Integer) CommandsMap.get(args[1]);
        } catch (Exception et) {
            error("Bad Operator", response);
            return;
        }
        
        
        //System.out.println(args[0]+" "+args[1]+" "+args[2]);
        //Instagrim Images koza
        //Instagrim Thumb 5096fef0-93b7-11e6-b691-0a0027000003
        //Instagrim Image 5096fef0-93b7-11e6-b691-0a0027000003
        switch (command) {
            case 1:
                DisplayImage(Convertors.DISPLAY_IMAGE,args[2], response);// returns to img src
                break;
            case 2:
                DisplayImageList(args[2], request, response);//Images create many "img src=\\\thumb"
                break;
            case 3:
                DisplayImage(Convertors.DISPLAY_THUMB,args[2],  response);//image printed to OutputStream
                break;
            default:
                error("Bad Operator", response);
        }
    }

    private void DisplayImageList(String username, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PicModel tm = new PicModel();
        tm.setCluster(cluster);
        java.util.LinkedList<Pic> lsPics = tm.getPicsForUser(username);
        
        request.setAttribute("Pics", lsPics);
        
        //here can be logged in or not
        HttpSession session=request.getSession(false);
        String viewingUser = null;
        if (session!=null && session.getAttribute("LoggedIn")!=null ){
            LoggedIn lg= (LoggedIn)session.getAttribute("LoggedIn");
            viewingUser = lg.getUsername();
        }
        
        //for displayed user
        User u = new User();
        u.setCluster(cluster);
        Map<String,String> userInfo = u.getUserInfo(username);
        request.setAttribute("userInfo", userInfo);
        
        //for viewing user
        request.setAttribute("viewingUser",viewingUser);
        
        if (viewingUser!=null && !viewingUser.equals(username)){
            FollowersModel fm = new FollowersModel();
            fm.setCluster(cluster);
            boolean alreadyFollowing = fm.checkIfFollowing(viewingUser,username);
          
            request.setAttribute("alreadyFollowing",alreadyFollowing);
        }
        
        
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/UsersPics.jsp");
        rd.forward(request, response);
        
     

    }

    private void DisplayImage(int type,String Image, HttpServletResponse response) throws ServletException, IOException {
        PicModel tm = new PicModel();
        tm.setCluster(cluster);
  
        
        Pic p = tm.getPic(type,java.util.UUID.fromString(Image));//type, ByteBuffer, length
        try (OutputStream out = response.getOutputStream()) {
            response.setContentType(p.getType());
            response.setContentLength(p.getLength());
            //out.write(Image);
            InputStream is = new ByteArrayInputStream(p.getBytes());
            BufferedInputStream input = new BufferedInputStream(is);
            byte[] buffer = new byte[8192];
            for (int length ; (length = input.read(buffer)) > 0;) {
                out.write(buffer, 0, length);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        for (Part part : request.getParts()) { 
           

            String type = part.getContentType();
            String filename = part.getSubmittedFileName();
           
             
            
            
            if (!ExtValidator.validate(type, filename)){
                response.setContentType("text/plain");
                response.setStatus(400);
                response.getWriter().write("Selected file extension is not accepted!");
                return;
            }
             
            InputStream is = request.getPart(part.getName()).getInputStream();
           
           
            HttpSession session=request.getSession();
            LoggedIn lg= (LoggedIn)session.getAttribute("LoggedIn");
            String  username=lg.getUsername();
           
            
            byte[] bytes = IOUtils.toByteArray(is);
            
          
           
            if (bytes.length > 0) {
                
                PicModel tm = new PicModel();
                tm.setCluster(cluster);
                tm.insertPic(bytes, type, filename, username);
                
                
            }

        }

    }

    private void error(String mess, HttpServletResponse response) throws ServletException, IOException {

        try (PrintWriter out = new PrintWriter(response.getOutputStream())) {
            out.println("<h1>You have a na error in your input</h1>");
            out.println("<h2>" + mess + "</h2>");
        }
    }
}
