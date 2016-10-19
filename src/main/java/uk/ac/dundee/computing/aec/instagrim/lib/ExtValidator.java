/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.lib;

import java.util.Arrays;

/**
 *
 * @author vladislavvoicehovic
 */
public class ExtValidator {
    
    public static boolean validate(String type, String filename){
        
        type = type.toLowerCase();  
        
        System.out.println("------------>"+type);
        String[] possibleContentTypes = {"image/jpeg", "image/jpg","image/gif","image/png" };   
        String[] possibleExtensions = {"jpg","png","jpeg","gif"};
        
        String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
        
        if (Arrays.asList(possibleContentTypes).contains(type) && Arrays.asList(possibleExtensions).contains(extension)){
            return true;
        }
        return false;
        
    }
    
}
