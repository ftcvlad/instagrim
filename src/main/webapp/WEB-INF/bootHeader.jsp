
   
       <nav class="navbar navbar-default">
            <div class="container-fluid">
              <!-- Brand and toggle get grouped for better mobile display -->
              <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                  <span class="sr-only">Toggle navigation</span>
                  <span class="icon-bar"></span>
                  <span class="icon-bar"></span>
                  <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/Instagrim/">Instagrim</a>
               
              </div>

              <!-- Collect the nav links, forms, and other content for toggling -->
              <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                
                
                <div class="navbar-form navbar-left"  >
                  <div class="form-group">
                    <input type="text" class="form-control"  placeholder="Search" name="searchUsername">
                  </div>
                  <button type="submit" class="btn btn-default"  onclick="showUser('${pageContext.request.contextPath}')">Submit</button>
                </div>
                
               
                
                
                <ul class="nav navbar-nav navbar-right">
                 
                  
                  <%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
                    
                  
                  <%
                        
                        LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn"); /*session created when jsp accessed*/
                        if (lg != null) {
                            String UserName = lg.getUsername();
                          
                    %>
                <li><a href="${pageContext.request.contextPath}/Profile/Upload">Profile</a></li>
                <li><a href="${pageContext.request.contextPath}/Images/<%=lg.getUsername()%>">Your Images</a></li>
                <li><a><span onclick="logoutF();">Logout</span></a></li>
                    <%
                            }else{
                                %>
                 <li><a href="${pageContext.request.contextPath}/Register">Register</a></li>
                <li><a href="${pageContext.request.contextPath}/Login">Login</a></li>
                <%
                                        
                            
                    }%>
                  
                  
                  
                  
                  
                </ul>
              </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>


