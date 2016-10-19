<%-- 
    Document   : UsersPics
    Created on : Sep 24, 2014, 2:52:48 PM
    Author     : Administrator
--%>

<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instagrim</title>
        <%@ include file="WEB-INF/headSources.jsp" %>
         <link rel="stylesheet" type="text/css" href="../static/Styles.css" />

        <script src="../static/javascript.js"></script>
    </head>
    <body>
        
         <%@ include file="WEB-INF/bootHeader.jsp" %>
        
 
<!--        <article>
            <h1>Your Pics</h1>
        <%
            java.util.LinkedList<Pic> lsPics = (java.util.LinkedList<Pic>) request.getAttribute("Pics");
            if (lsPics == null) {
        %>
        <p>No Pictures found</p>
        <%
        } else {
            Iterator<Pic> iterator;
            iterator = lsPics.iterator();
            while (iterator.hasNext()) {
                Pic p = (Pic) iterator.next();

        %>
        <a href="/Instagrim/Image/<%=p.getSUUID()%>" ><img src="/Instagrim/Thumb/<%=p.getSUUID()%>"></a><br/><%

            }
            }
        %>
        </article>-->
        
        
        <div id="main">
            <div id="stuffContainer">

                 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
                    <c:if test="${updateSuccess != null}">
                        <c:choose>
                            <c:when test="${userInfo==true}">

                                <div class="alert alert-danger">
                                    <strong>Error!</strong> User does not exist
                                </div>
                            </c:when>    
                            <c:otherwise>
                                <div class="panel-heading">
                                    <div><img src="${pageContext.request.contextPath}/ProfilePicture/${userInfo.get("username")}"> </div>

                                    <div id="profileInfo">
                                        <h1>${userInfo.get("username")}</h1>
                                        <h2>${userInfo.get("name")} ${userInfo.get("surname")}</h2>
                                        <p>${userInfo.get("status")}</p>
                                    </div>



                                </div>
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-xs-6 col-md-3">
                                            <a href="#" class="thumbnail">
                                                <img src="/Instagrim/static/images/nopic.png" >
                                            </a>
                                            <a href="#" class="thumbnail">
                                                <img src="/Instagrim/static/images/nopic.png" >
                                            </a>

                                        </div>
                                        <div class="col-xs-3 col-md-1">
                                            <a href="#" class="thumbnail">
                                                <img src="/Instagrim/static/images/nopic.png" >
                                            </a>
                                            <a href="#" class="thumbnail">
                                                <img src="/Instagrim/static/images/nopic.png" >
                                            </a>

                                        </div>

                                    </div>
                                </div>

                            </c:otherwise>
                        </c:choose>

                    </c:if>
                
                
                
                
            </div>
        </div>
        
        
       
    </div>

        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        

        
        
        
        <footer>
            <ul>
                <li class="footer"><a href="/Instagrim">Home</a></li>
            </ul>
        </footer>
    </body>
</html>
