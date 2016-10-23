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
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author vladislavvoicehovic
 */
public class FollowersModel {
    
     Cluster cluster;

    public FollowersModel() {
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }
    
    
    
    public boolean addFollower(String username, String userToFollow){
        
        
        try (Session session = cluster.connect("instagrim")) {
            
            
            //????!!! need to make all 3 or none.
            //1) What if user changes javascript and tries to follow a user that does not exist. Need to update followed user first
            //2) What if user changes javascript (or from other tab) and tries to follow someone again. Need to check if followers row exists first
            
           
            //add record to followers table
            PreparedStatement followStat = session.prepare("insert into followers (username,followedUser) values(?,?)  IF NOT EXISTS;");
            BoundStatement bsInsertPic = new BoundStatement(followStat);

            ResultSet rs = session.execute(bsInsertPic.bind(username, userToFollow));
            if (!rs.wasApplied()) {
                return false;
            }            



            //increase counter in userToFollow profile (and make sure it exists). 
            PreparedStatement ps1 = session.prepare("UPDATE instagrim.follow_counts SET followers = followers + 1  WHERE login=? ;");
            BoundStatement incrementHim = new BoundStatement(ps1);
            ResultSet rs1 = session.execute(incrementHim.bind(userToFollow));
            if (!rs1.wasApplied()){//counter update not idempotent => no if => this may be doesn't work
                return false;
            }
            
            
            //increase counter in username profile
            
            PreparedStatement ps2 = session.prepare("UPDATE instagrim.follow_counts SET following = following + 1  WHERE login=? ;");
            BoundStatement incrementMe = new BoundStatement(ps2);
            ResultSet rs2 = session.execute(incrementMe.bind(username));
            
            return true;
            
        }
    }
    
    
    public boolean removeFollower(String username, String userToUnfollow){
        try (Session session = cluster.connect("instagrim")) {
            
            
            
            //remove from followers table
            PreparedStatement followStat = session.prepare("DELETE FROM followers WHERE username=? AND followedUser=? IF EXISTS;");
            BoundStatement bsInsertPic = new BoundStatement(followStat);

            ResultSet rs = session.execute(bsInsertPic.bind(username, userToUnfollow));
            if (!rs.wasApplied()) {
                return false;
            }            



            //decrease counter in userToFollow profile (and make sure it exists). 
            PreparedStatement ps1 = session.prepare("UPDATE instagrim.follow_counts SET followers = followers - 1  WHERE login=? ;");
            BoundStatement incrementHim = new BoundStatement(ps1);
            ResultSet rs1 = session.execute(incrementHim.bind(userToUnfollow));
            if (!rs1.wasApplied()){//counter update not idempotent => no if => this may be doesn't work
                return false;
            }
            
            //increase counter in username profile
            PreparedStatement ps2 = session.prepare("UPDATE instagrim.follow_counts SET following = following - 1  WHERE login=? ;");
            BoundStatement incrementMe = new BoundStatement(ps2);
            ResultSet rs2 = session.execute(incrementMe.bind(username));
            
            return true;
            
        }
    }
    
    
    public boolean checkIfFollowing(String viewingUser, String username){
        
        try (Session session = cluster.connect("instagrim")) {
        
            PreparedStatement ps1 = session.prepare("SELECT count(*) FROM followers WHERE username = ? AND followedUser=?");
            BoundStatement bs1 = new BoundStatement(ps1);

            ResultSet rs = session.execute(bs1.bind(viewingUser, username));
            
            Row r = rs.one();
            long count = r.getLong(0);
            
            return (count>0);
               
        }
    }
    
    
    public long[] countFollowFollowers(String username){
        
        long[] counts = new long[2];
        
        try (Session session = cluster.connect("instagrim")) {
        
            PreparedStatement ps1 = session.prepare("SELECT following, followers FROM follow_counts WHERE login = ?");
            BoundStatement bs1 = new BoundStatement(ps1);

            ResultSet rs = session.execute(bs1.bind(username));
            
           
            if (rs.isExhausted()){
                counts[0] = 0;
                counts[1] = 0;
                return counts; 
            }
            Row r = rs.one();
            counts[0] = r.getLong("following");
            counts[1] = r.getLong("followers");

            return counts;
   
        }
 
    }
    
    public List<String> getFollowedUsernames(String username){
        
        List<String> allFollowed = new LinkedList<>();

        try (Session session = cluster.connect("instagrim")) {

            //remove from followers table
            PreparedStatement pr1 = session.prepare("SELECT followedUser FROM followers WHERE username=?");
            BoundStatement bs1 = new BoundStatement(pr1);

            ResultSet rs = session.execute(bs1.bind(username));
            if (rs.isExhausted()) {
                return null;
            }
            else{
                for (Row r:rs){
                    
                    allFollowed.add(r.getString("followedUser"));
                }
                return allFollowed;
            }
        }
    }
    
    
}
