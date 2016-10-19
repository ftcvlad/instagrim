/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Bugs
 * 1) if login exists, password updated
 * 2) using is.available() for determining byte array size is wrong (). Use Apache Commons-io instead
 * 3) close() on InputStream does nothing
 * 4) filetypes other than image
 * 5) <input type="file" multiple /> to have multiple
 * 
 */

//http://stackoverflow.com/questions/2229757/maven-add-a-dependency-to-a-jar-by-relative-path
//add non-maven central .jar to local repository available for builds!


//http://stackoverflow.com/questions/4323981/how-to-install-jars-in-maven-repository-which-is-eclipse-embedded
//fucking eclipse
/*
 * 1) add Bootstrap badge -- number of commetns per user in a list!
 * 
 * 
 * 
 * 
 * 
 */

// <!--        <article>
//            <h1>Your Pics</h1>
//        <%
//            java.util.LinkedList<Pic> lsPics = (java.util.LinkedList<Pic>) request.getAttribute("Pics");
//            if (lsPics == null) {
//        %>
//        <p>No Pictures found</p>
//        <%
//        } else {
//            Iterator<Pic> iterator;
//            iterator = lsPics.iterator();
//            while (iterator.hasNext()) {
//                Pic p = (Pic) iterator.next();
//
//        %>
//        <a href="/Instagrim/Image/<%=p.getSUUID()%>" ><img src="/Instagrim/Thumb/<%=p.getSUUID()%>"></a><br/><%
//
//            }
//            }
//        %>
//        </article>-->