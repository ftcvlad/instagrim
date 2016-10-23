/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.imgscalr.Scalr.*;
import static org.imgscalr.Scalr.OP_ANTIALIAS;
import static org.imgscalr.Scalr.OP_GRAYSCALE;
import static org.imgscalr.Scalr.resize;

import uk.ac.dundee.computing.aec.instagrim.lib.Convertors;
import uk.ac.dundee.computing.aec.instagrim.lib.ExtValidator;

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

           
           
    }

   
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        

            String pathParts[] = Convertors.SplitRequestPath(request);//Instagrim filters (sepia, ...)
            
          
                
            for (Part p:request.getParts()){
                System.out.println(p.getName()+" "+p.getContentType());
            }

            Part filePart = request.getPart("fileToUpload"); 
            String type = filePart.getContentType();
            String filename = filePart.getSubmittedFileName();




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

            
            if (pathParts[2].equals("sepia")){
                bi = toSepia(bi,10);
            }
            else if(pathParts[2].equals("grayscale")){
                bi = toGrayScale(bi);
            }
            else if(pathParts[2].equals("negative")){
                bi = toNegative(bi);
            }
            else if(pathParts[2].equals("2")){
                bi = two(bi);
            }
         
           

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( bi, Convertors.SplitFiletype(type)[1], baos );
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();

            String base64bytes = Base64.getEncoder().encodeToString(imageInByte);
            String src = "data:"+type+";base64," + base64bytes;


            response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
            response.getWriter().write(src);       


                
                        
 
    
    }
    //filter algorithms are taken off the net 
    //Sepia  -- http://stackoverflow.com/questions/21899824/java-convert-a-greyscale-and-sepia-version-of-an-image-with-bufferedimage

  public static BufferedImage toSepia(BufferedImage img, int sepiaIntensity) {

        BufferedImage sepia = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        // Play around with this.  20 works well and was recommended
        //   by another developer. 0 produces black/white image
        int sepiaDepth = 20;

        int w = img.getWidth();
        int h = img.getHeight();

        WritableRaster raster = sepia.getRaster();

        // We need 3 integers (for R,G,B color values) per pixel.
        int[] pixels = new int[w * h * 3];
        img.getRaster().getPixels(0, 0, w, h, pixels);

        //  Process 3 ints at a time for each pixel.  Each pixel has 3 RGB
        //    colors in array
        for (int i = 0; i < pixels.length; i += 3) {
            int r = pixels[i];
            int g = pixels[i + 1];
            int b = pixels[i + 2];

            int gry = (r + g + b) / 3;
            r = g = b = gry;
            r = r + (sepiaDepth * 2);
            g = g + sepiaDepth;

            if (r > 255) {
                r = 255;
            }
            if (g > 255) {
                g = 255;
            }
            if (b > 255) {
                b = 255;
            }

            // Darken blue color to increase sepia effect
            b -= sepiaIntensity;

            // normalize if out of bounds
            if (b < 0) {
                b = 0;
            }
            if (b > 255) {
                b = 255;
            }

            pixels[i] = r;
            pixels[i + 1] = g;
            pixels[i + 2] = b;
        }
        raster.setPixels(0, 0, w, h, pixels);

        return sepia;
    }

  
    public static BufferedImage toGrayScale(BufferedImage img) {
        int Width=img.getWidth()-1;
        return  resize(img, Method.SPEED, Width, OP_ANTIALIAS, OP_GRAYSCALE);
    }
     
    //Negative -- https://www.dyclassroom.com/image-processing-project/how-to-convert-a-color-image-into-negative
    public static BufferedImage toNegative(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        //convert to negative
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = img.getRGB(x, y);
                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;
                //subtract RGB from 255
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;
                //set new RGB value
                p = (a << 24) | (r << 16) | (g << 8) | b;
                img.setRGB(x, y, p);
            }
        }
        return img;
    }
   
     public static BufferedImage two(BufferedImage master) {
        BufferedImage gray = new BufferedImage(master.getWidth(), master.getHeight(), BufferedImage.TYPE_INT_ARGB);

        // Automatic converstion....
        ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_PYCC), null);
        op.filter(master, gray);

        return gray;
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
