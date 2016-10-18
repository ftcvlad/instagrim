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
                        <a href="${pageContext.request.contextPath}/Profile/Upload" class="list-group-item ">Upload image</a>
                        <a href="${pageContext.request.contextPath}/Profile/Edit" class="list-group-item active">Edit</a>

                    </div>

                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div><img src="${pageContext.request.contextPath}/Profile/ProfilePicture"> </div>
                            
                            <div id="profileInfo">
                                <h1>${sessionScope.LoggedIn.getUsername()}</h1>
                                <h2>${inputFieldValues.get("name")} ${inputFieldValues.get("surname")}</h2>
                                <p>${inputFieldValues.get("status")}</p>
                            </div>
                           
                        
                        
                        </div>
                        <div class="panel-body">
                            <form  method="POST" enctype="multipart/form-data" action="${pageContext.request.contextPath}/Profile/Edit" >
                                
                               
                                <div class="form-group">
                                     <label for="usernameInput">First name</label>
                                    <input class="form-control" id="nameInput" name="firstname" value="${inputFieldValues.get("name")}" placeholder="20 characters max" maxlength="20">
                                </div>
                                <div class="form-group">
                                      <label for="usernameInput">Second name</label>
                                    <input  class="form-control" id="surnameInput" name="lastname" value="${inputFieldValues.get("surname")}" placeholder="20 characters max" maxlength="20">
                                </div>
                               
                                
                                <div class="form-group">
                                    <label for="emailInput">Email address</label>
                                    <input type="email" class="form-control" id="emailInput" name="email" aria-describedby="emailHelp" value="${inputFieldValues.get("email")}" placeholder="Enter email">
                                </div>
                                
                                
<!--                                <div class="form-inline">
                                    <label for="street">Street</label>
                                    <input  class="form-control" id="street"  placeholder="15 max" maxlength="15">
                                    <label for="city">City</label>
                                    <input  class="form-control" id="city"  placeholder="15 max" maxlength="15">
                                    <label for="postCode">Post code</label>
                                    <input class="form-control" id="postCode" placeholder="10 max" maxlength="10">
                                </div>-->
                               
                                <div class="form-group">
                                  <label for="exampleTextarea">Status</label>
                                  <textarea class="form-control" id="exampleTextarea" name="status" rows="2" placeholder="100 max" maxlength="100">${inputFieldValues.get("status")}</textarea>
                                </div>
                                <div class="form-group">
                                  <label for="exampleInputFile">Set profile picture</label>
                                  <input type="file" class="form-control-file" id="exampleInputFile" name="profilePic" aria-describedby="fileHelp">
                                  <small id="fileHelp" class="form-text text-muted">Select your look! .jpg .png .jpeg .gif accepted</small>
                                </div>
                               
                              
                                <button type="submit" class="btn btn-primary">Submit</button>
                                
                                <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
                                <c:if test="${updateSuccess != null}">
                                    <c:choose>
                                        <c:when test="${updateSuccess==true}">
                                            <br/>
                                            <div class="alert alert-success">
                                                <strong>Success!</strong> Profile updated!
                                            </div>
                                        </c:when>    
                                        <c:otherwise>
                                            <br/>
                                            <div class="alert alert-danger">
                                                <strong>Error!</strong> Extension is not supported
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                
                                </c:if>
                                    
                                    
                                    
                              
                                
                            </form>
                        </div>
                    </div>
                 
             </div>
           
         </div>
         
       

    </body>
</html>
