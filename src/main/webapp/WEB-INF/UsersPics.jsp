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
        <%@ include file="headSources.jsp" %>
         <link rel="stylesheet" type="text/css" href="../static/Styles.css" />
        <link rel="stylesheet" type="text/css" href="../static/profileStyle.css" />
        <link rel="stylesheet" type="text/css" href="../static/viewThumbnails.css" />
        <script src="../static/thumbnailsScript.js"></script>
        <script src="../static/javascript.js"></script>
    </head>
    <body>
        
         <%@ include file="bootHeader.jsp" %>
        
 

        
        
        <div id="main">
            <div id="stuffContainer">

                 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
                   
                        <c:choose>
                            <c:when test="${userInfo==null}">

                                <div class="alert alert-danger">
                                    <strong>Error!</strong> User does not exist
                                </div>
                            </c:when>    
                            <c:otherwise>
                                
                                <div class="panel panel-default">

                                    <div class="panel-heading">
                                        <div id="headContent">
                                            <div><img src="${pageContext.request.contextPath}/ProfilePicture/${userInfo.get("username")}"> </div>

                                            <div id="profileInfo">
                                                <h1>${userInfo.get("username")}</h1>
                                                <h2>${userInfo.get("name")} ${userInfo.get("surname")}</h2>
                                                <p>${userInfo.get("status")}</p>

                                            </div>

                                        </div>
                                        <div id="followersContent">
                                            <div>
                                                <p>Following:<span>${userInfo.get("following")}</span> Followers: <span>${userInfo.get("followers")}</span> </p>
                                            </div>
                                            
                                            <c:if test = "${ viewingUser!=null && userInfo.get('username') != viewingUser}">
                                                <c:choose>
                                                    <c:when test="${alreadyFollowing==false}">
                                                        <div id="followDiv">
                                                            <button type="button" class="btn btn-primary btn-sm" onclick="follow('${userInfo.get('username')}')">Follow</button>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div id="followDiv">
                                                            <button type="button" class="btn btn-primary btn-sm" id="redUnfollow" onclick="unfollow('${userInfo.get('username')}')">Unfollow</button>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>

                                        </div>    

           
                                     
                                     



                                    </div>
                                    <div class="panel-body">
                                        
                                        
                                        <c:set var="lsPics" value="${Pics}"/>                                
        
        
                                        <c:choose>
                                            <c:when test="${lsPics==null}">
                                                User has no pictures
                                            </c:when>
                                            <c:otherwise>
                                                <div class="container-fluid">
                                                    <div class="row">

                                                            <c:forEach items="${lsPics}" var="nextPic"> 
                                                                 <div class="col-xs-4 col-md-4">
                                                                     <a href="/Instagrim/ShowFullImage/${nextPic.getSUUID()}" class="thumbnail"><img src="/Instagrim/Thumb/${nextPic.getSUUID()}" ></a>

                                                                </div>

                                                            </c:forEach>


                                                    </div>
                                                </div>
                                            </c:otherwise>
                                            
                                        </c:choose>   
                                            
                                        

                                        
                                        
                                    </div>
                                </div>
                            </c:otherwise>
                        </c:choose>

                   
                
                
                
                
            </div>
        </div>
        
        
       
    </div>

        
   
        
       
    </body>
</html>
