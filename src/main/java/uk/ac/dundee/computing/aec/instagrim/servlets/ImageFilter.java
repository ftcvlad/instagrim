/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;
import uk.ac.dundee.computing.aec.instagrim.lib.Convertors;
import uk.ac.dundee.computing.aec.instagrim.lib.ExtValidator;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;


import marvin.image.MarvinImage; 
import marvin.plugin.MarvinImagePlugin; 
import marvin.util.MarvinPluginLoader;
/**
 *
 * @author vladislavvoicehovic
 */
@WebServlet(name = "imageFilter", urlPatterns = {"/filters/*"})
@MultipartConfig
public class ImageFilter extends HttpServlet {

 

 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
            HttpSession session=request.getSession();
            byte[] filteredImage = (byte[])session.getAttribute("filteredImage");
            
            response.setContentType((String)session.getAttribute("filteredImageType"));
            response.setContentLength(filteredImage.length);

            OutputStream out = response.getOutputStream();
            out.write(filteredImage);
            out.close();
       
    }

   
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
            String pathParts[] = Convertors.SplitRequestPath(request);//Instagrim filters (sepia, ...)
            
          
            if (pathParts[2].equals("save")){
                
            }
            else{
                
                for (Part p:request.getParts()){
                    System.out.println(p.getName()+" "+p.getContentType());
                }
                
                Part filePart = request.getPart("fileToUpload"); 
                String type = filePart.getContentType();
                String filename = filePart.getSubmittedFileName();

                System.out.println(type+" 111 "+filename);


                if (filename.equals("")){
                    response.setContentType("text/plain");
                    response.setStatus(400);
                    response.getWriter().write("Select file!");
                    return;
                }
                else if ( !ExtValidator.validate(type, filename)){
                    response.setContentType("text/plain");
                    response.setStatus(400);
                    response.getWriter().write("Selected file extension is not accepted!");
                    return;
                }

                HttpSession session=request.getSession();
               


                InputStream is = filePart.getInputStream();
                BufferedImage bi = ImageIO.read(is);
                //byte[] bytes = IOUtils.toByteArray(is);


                
                BufferedImage processed = applyFilter(bi, pathParts[2]);
                
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write( processed, type, baos );
                baos.flush();
                byte[] imageInByte = baos.toByteArray();
                baos.close();
                
                
                session.setAttribute("filteredImage", imageInByte);
                session.setAttribute("filteredImageType", type);
                
            }
            
            
            
            
            
            
            
    
    }
    
    
    public BufferedImage applyFilter(BufferedImage bi, String name){
        
            MarvinImage image = new MarvinImage(bi);
            if (name.equals("sepia")){
                  MarvinImagePlugin imagePlugin = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.color.grayScale.jar");
                  imagePlugin.process(image, image);
            }


            return	image.getBufferedImage(); 
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
