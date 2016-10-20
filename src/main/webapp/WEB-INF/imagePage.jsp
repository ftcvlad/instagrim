<%-- 
    Document   : imagePage
    Created on : 19-Oct-2016, 23:25:28
    Author     : Vlad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="../static/imagePageStyle.css" />
        <%@ include file="headSources.jsp" %>
    </head>
    <body>
        
        
        <div id="main">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-8 col-xs-8" >
                        <img src="/Instagrim/Image/${SUUID}"  class="img-responsive" >

                    </div>
                    <div class="col-md-4 col-xs-4">



                    </div>

                </div>
            </div>
        </div>
        
       
      
        
     
        
                   
        
    </body>
</html>
