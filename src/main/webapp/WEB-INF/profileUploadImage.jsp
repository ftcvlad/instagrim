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
         <script src="../static/profileUploadPic.js"></script>
    </head>
    <body>
         <%@ include file="bootHeader.jsp" %>
         
         
         <div id="main">
             <div id="stuffContainer">
                    <div class="list-group">
                        <a href="/Instagrim/Profile/Upload" class="list-group-item active">Upload image</a>
                        <a href="/Instagrim/Profile/Edit" class="list-group-item">Edit</a>

                    </div>

                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <img src="${pageContext.request.contextPath}/Profile/ProfilePicture"> 
                            
                            <div id="profileInfo">
                                <h1>${sessionScope.LoggedIn.getUsername()}</h1>
                                <h2>${inputFieldValues.get("name")} ${inputFieldValues.get("surname")}</h2>
                                <p>${inputFieldValues.get("status")}</p>
                            </div>
                        
                        
                        </div>
                            
                        <div class="panel-body">
                            

                             <div class="form-group">
                                <label for="exampleInputFile">Upload picture</label>
                                <input type="file" class="form-control-file" id="exampleInputFile"  aria-describedby="fileHelp">
                                <small class="form-text text-muted"> .jpg .png .jpeg .gif accepted</small>
                             </div>

                            <div id="exTab2" class="container">	
                                            <ul class="nav nav-tabs">
                                                    <li class="active"><a  href="#1" data-toggle="tab">Upload</a></li>
                                                    <li><a href="#2" data-toggle="tab">Filters</a></li>

                                            </ul>

                                            <div class="tab-content ">
                                                        <div class="tab-pane active" id="1">
<!--                                                            <form method="POST" enctype="multipart/form-data" action="${pageContext.request.contextPath}/Image">
                                                                <br/>
                                                           

                                                                <button type="submit" class="btn btn-primary">Upload</button>
                                                            </form>-->
                                                        </div>
                                                        <div class="tab-pane" id="2">
                                                                <br/>
                                                                <div class="alert alert-danger" id="errorDiv" style="display: none;">
                                                                    <strong>Error!</strong> 
                                                                </div>
                                                                <div class="radio">
                                                                    <label><input type="radio" name="filterName" value="original">Original</label>
                                                                </div>
                                                                <div class="radio" >
                                                                    <label><input type="radio" name="filterName" checked="checked" value="sepia">Sepia</label>
                                                                </div>

                                                                <button type="button" class="btn btn-primary" onclick="sendTofilter('${pageContext.request.contextPath}')">View image</button>
                                                                <br/>

                                                                <div id="appendImage"></div>    
                                                        </div>

                                            </div>
                              </div>
                                    
                                    
                                    
                                    
                        </div>
                           
                    </div>
                 
             </div>
           
         </div>
         
       

    </body>
</html>
