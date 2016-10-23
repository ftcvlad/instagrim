<%-- 
    Document   : index
    Created on : Sep 28, 2014, 7:01:44 PM
    Author     : Administrator
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Instagrim</title>
       
         <%@ include file="headSources.jsp" %>
<!--        http://stackoverflow.com/questions/3655316/browser-cant-access-find-relative-resources-like-css-images-and-links-when-cal-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/Styles.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/mainPageStyle.css" />
        <script src="${pageContext.request.contextPath}/static/javascript.js"></script>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        
        <%@ include file="bootHeader.jsp" %>

        
        <div id="main">
            <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

            
            <c:choose>
                <c:when test="${followed!=null}">
                    <div id="stuffContainer">
                        <div class="panel panel-default">

                            <div class="panel-heading">
                                <div id="headContent">
                                    You are following
                                </div>
                            </div>
                            <div class="panel-body">

                                <div class="list-group">

                                    <c:forEach items="${followed}" var="nextFollowedUser"> 


                                        <a href="${pageContext.request.contextPath}/Images/${nextFollowedUser}" class="list-group-item ">
                                            <div id="shortUserInfoContent">
                                                <div id="avatarDiv">
                                                    <img class="img-responsive" src="${pageContext.request.contextPath}/ProfilePicture/${nextFollowedUser}"/>
                                                </div>
                                                <div id="usernameDiv">
                                                    <div>
                                                        <h4 class="list-group-item-heading">${nextFollowedUser}</h4>
                                                    </div>
                                                   
                                                </div>
                                            </div>
                                           
                                            
                                        </a>

                                    </c:forEach>

                                </div>  
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    
                    <h2 style="margin:100px; color:#bfbfbf;">Login to follow users, upload pictures ... <span style="color:white;">we all die anyway, you can add some pictures now (c) Instagrim</span></h2>
                </c:otherwise>
            </c:choose>
            
           
               

          
        </div>


        
        
        
        
        
        

        
    </body>
</html>
