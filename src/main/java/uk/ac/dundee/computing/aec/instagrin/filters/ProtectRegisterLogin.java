/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrin.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import uk.ac.dundee.computing.aec.instagrin.stores.LoggedIn; // this does not exist lol
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;
/**
 *
 * @author vladislavvoicehovic
 */
@WebFilter(filterName = "ProtectRegisterLogin")
public class ProtectRegisterLogin implements Filter {
    
    private FilterConfig filterConfig = null;  
    
    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
    }

   
    public void doFilter(ServletRequest request, ServletResponse response,
              FilterChain chain)
              throws IOException, ServletException {

        
        
        
            HttpServletRequest httpReq = (HttpServletRequest) request;
            HttpServletResponse httpResp = (HttpServletResponse) response;
            HttpSession session=httpReq.getSession(false);


            if (session != null && session.getAttribute("LoggedIn")!=null){//forbid entering login/register if logged in!
                httpResp.sendRedirect("/Instagrim");

            }
            else{
                chain.doFilter(request, response);
            }
           

    }
        
 

   
    
    
    public void destroy() {        
    }
}
