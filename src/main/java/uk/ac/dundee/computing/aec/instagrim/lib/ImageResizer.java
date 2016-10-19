/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.lib;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;
import static org.imgscalr.Scalr.OP_ANTIALIAS;

/**
 *
 * @author Vlad
 */
public class ImageResizer {
    
    
    
    
    public static byte[] resizeToSquare(BufferedImage bi, String type, int squareSide ){
       
        try{
           
            
            
            //1) resize maintaining aspect to make image smaller side equal to squareSide
            //2) crop from center of the longest side to get AxA square
           
            
            if(bi.getWidth()<bi.getHeight()){
                 bi = Scalr.resize(bi, Scalr.Method.SPEED,Scalr.Mode.FIT_TO_WIDTH, squareSide,1, OP_ANTIALIAS);//1 is recalculated
            }
            else{
                 bi = Scalr.resize(bi, Scalr.Method.SPEED,Scalr.Mode.FIT_TO_HEIGHT, 1,squareSide, OP_ANTIALIAS);//1 is recalculated
            }
            
            if (bi.getWidth()<bi.getHeight()){
                bi = Scalr.crop(bi,0,bi.getHeight()/2-squareSide/2,squareSide,squareSide);
            }
            else{
                bi = Scalr.crop(bi,bi.getWidth()/2-squareSide/2,0,squareSide,squareSide);
            }
            
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( bi, type, baos );
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        }
        catch(IOException ioe){
            return null;
        }
        
        
    }
    
    
    
}
