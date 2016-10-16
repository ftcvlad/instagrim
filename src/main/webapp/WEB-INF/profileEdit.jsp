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
                        <a href="/Instagrim/Profile/View" class="list-group-item ">View Profile</a>
                        <a href="/Instagrim/Profile/Edit" class="list-group-item active">Edit</a>

                    </div>

                    <div class="panel panel-default">
                        <div class="panel-heading">HERE PIC and ${sessionScope.LoggedIn.getUsername()}</div>
                        <div class="panel-body">
                            <form  method="POST" enctype="multipart/form-data" action="/Instagrim/Profile/Edit" >
                                
                                <div class="form-group">
                                  <label for="usernameInput">Username</label>
                                  <input  class="form-control" id="usernameInput" name="newUsername" placeholder="30 characters max">
                                </div>
                                
                                
                                    
                                <div class="form-group">
                                     <label for="usernameInput">First name</label>
                                    <input class="form-control" id="nameInput" name="firstname"  placeholder="30 characters max">
                                </div>
                                <div class="form-group">
                                      <label for="usernameInput">Second name</label>
                                    <input  class="form-control" id="surnameInput" name="lastname"  placeholder="30 characters max">
                                </div>
                               
                                
                                <div class="form-group">
                                    <label for="emailInput">Email address</label>
                                    <input type="email" class="form-control" id="emailInput" name="email" aria-describedby="emailHelp" placeholder="Enter email">
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
                                  <textarea class="form-control" id="exampleTextarea" name="status"  rows="2"></textarea>
                                </div>
                                <div class="form-group">
                                  <label for="exampleInputFile">Set profile picture</label>
                                  <input type="file" class="form-control-file" id="exampleInputFile" name="profilePic" aria-describedby="fileHelp">
                                  <small id="fileHelp" class="form-text text-muted">Select your look!</small>
                                </div>
                               
                              
                                <button type="submit" class="btn btn-primary">Submit</button>
                            </form>
                        </div>
                    </div>
                 
             </div>
           
         </div>
         
       

    </body>
</html>