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
        <script src="../static/imagePageScript.js"></script>
        <%@ include file="headSources.jsp" %>
    </head>
    <body>
        
        

        
       
<div id="main">
    <div id="firstFlexContainer">
        
        <div id="imageDiv" >
            <div >
                 <img src="/Instagrim/Image/${SUUID}"  class="img-responsive" >
            </div>
           
        </div>
            <div id="commentPane">
                <div id="commentList">
                    
                    
                    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
                    <ul>
                        <c:choose>

                            <c:when test="${commentList!=null}">

                                <c:forEach items="${commentList}" var="nextComment"> 
                                    <li><strong><a href="${pageContext.request.contextPath}/Images/${nextComment.user}">${nextComment.user}</a></strong> ${nextComment.commentText}</li>                         

                                </c:forEach>

                            </c:when>

                        </c:choose>
                    </ul>
                  
                </div>
                <div id="enterComment">
                    
                         <c:choose>

                             <c:when test="${username!=null}">
                                <div>
                                    <textarea rows="3" placeholder="Comment"></textarea>
                                </div>
                                <div>
                                   <button type="button" id="huj" class="btn btn-primary" data-imagesuuid="${SUUID}" onclick="addComment('${pageContext.request.contextPath}','${username}')" >Send</button>
                                </div>
                             </c:when>
                             <c:otherwise>
                                <div>
                                    <a href="${pageContext.request.contextPath}/Login">Log in</a> to comment
                                </div>
                             </c:otherwise>
                         </c:choose>
                    
                    

                   
                </div>
        </div>
        
    </div>
    
    
</div>
        
     
        
                   
        
    </body>
</html>
