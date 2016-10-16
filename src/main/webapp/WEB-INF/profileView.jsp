<%-- 
    Document   : profile
    Created on : 15-Oct-2016, 20:06:29
    Author     : vladislavvoicehovic
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
         <%@ include file="headSources.jsp" %>
         <link rel="stylesheet" type="text/css" href="../static/profileStyle.css" />
         <link rel="stylesheet" type="text/css" href="../static/Styles.css" />
         <script src="../static/javascript.js"></script>
    </head>
    <body>
         <%@ include file="bootHeader.jsp" %>
         
         
         <div id="main">
             <div id="stuffContainer">
                    <div class="list-group">
                        <a href="/Instagrim/Profile/View" class="list-group-item active">View Profile</a>
                        <a href="/Instagrim/Profile/Edit" class="list-group-item">Edit</a>

                    </div>

                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <img src="${pageContext.request.contextPath}/Profile/ProfilePicture"> 
                            
                            ${sessionScope.LoggedIn.getUsername()}
                        
                        
                        </div>
                    </div>
                 
             </div>
           
         </div>
         
       

    </body>
</html>
