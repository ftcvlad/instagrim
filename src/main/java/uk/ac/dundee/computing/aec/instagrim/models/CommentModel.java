/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.models;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import uk.ac.dundee.computing.aec.instagrim.stores.Comment;

/**
 *
 * @author vladislavvoicehovic
 */
public class CommentModel {
    
    
    Cluster cluster;

    public void PicModel() {
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }
    
    
    
    public void addComment(java.util.UUID picid, String comment, String userWhoCommented){
        
        
        Session session = cluster.connect("instagrim");

        PreparedStatement psInsertPic = session.prepare("insert into comments (comment,username, picid, pic_added) values(?,?,?,?);");
        BoundStatement bsInsertPic = new BoundStatement(psInsertPic);

        Date DateAdded = new Date();
        session.execute(bsInsertPic.bind(comment, userWhoCommented, picid, DateAdded));

        session.close();
 
    }
    
    public List<Comment> getComments(java.util.UUID SUUID){
        
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select comment,username from comments where picid =?");

        BoundStatement boundStatement = new BoundStatement(ps);

        boundStatement.bind(SUUID);

        ResultSet rs = session.execute(boundStatement);

        List<Comment> ls = new LinkedList<>();
        
        if (rs.isExhausted()){
            return null;
        }
        
        for (Row nextRow : rs) {

            String text = nextRow.getString("comment");
            String userCommented = nextRow.getString("username");

            Comment c = new Comment();
            c.setUser(userCommented);
            c.setCommentText(text);
            ls.add(c);

        }

        return ls;
        
    }
    
}
