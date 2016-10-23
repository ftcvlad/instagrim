package uk.ac.dundee.computing.aec.instagrim.lib;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.*;

public final class Keyspaces {

    public Keyspaces() {

    }

    public static void SetUpKeySpaces(Cluster c) {
        try {
            //Add some keyspaces here
            String createkeyspace = "create keyspace if not exists instagrim  WITH replication = {'class':'SimpleStrategy', 'replication_factor':1}";
            String CreatePicTable = "CREATE TABLE if not exists instagrim.Pics ("
                    + " user varchar,"
                    + " picid uuid, "
                    + " interaction_time timestamp,"
                    + " title varchar,"
                    + " image blob,"
                    + " thumb blob,"
                    + " imagelength int,"
                    + " thumblength int,"
                    + " type  varchar,"
                    + " name  varchar,"
                    + " PRIMARY KEY (picid)"
                    + ")";
            String Createuserpiclist = "CREATE TABLE if not exists instagrim.userpiclist (\n"
                    + "picid uuid,\n"
                    + "user varchar,\n"
                    + "pic_added timestamp,\n"
                    + "PRIMARY KEY (user,pic_added)\n"
                    + ") WITH CLUSTERING ORDER BY (pic_added desc);";
            String CreateAddressType = "CREATE TYPE if not exists instagrim.address (\n"
                    + "      street text,\n"
                    + "      city text,\n"
                    + "      zip int\n"
                    + "  );";
            
            String CreateComments = "CREATE TABLE if not exists instagrim.comments (\n"
                    + "      comment text,\n"
                    + "      username text,\n"
                    + "      picid uuid, \n"
                    + "      pic_added timestamp, \n"
                    + "      PRIMARY KEY (picid,pic_added)\n"//default clustering order ASC -- what we need
                    + "  );";
            
            
            String CreateFollowers = "CREATE TABLE if not exists instagrim.followers (\n"
                    + "      username text,\n"
                    + "      followedUser text, \n"
                    + "      PRIMARY KEY (username,followedUser)\n"
                    + "  );";
            
            //can't increment non-counters, and can't have counters and non-part-of-composite-key columns in the same table :(
             String profileFollowCounters = "CREATE TABLE if not exists instagrim.follow_counts (\n"
                    + "      login varchar,\n"
                    + "      following counter,\n"
                    + "      followers counter,\n"
                    + "      PRIMARY KEY (login)\n"
                    + "  );";
            
   
            
            String CreateUserProfile = "CREATE TABLE if not exists instagrim.userprofiles (\n"
                    + "      login varchar PRIMARY KEY,\n"
                    + "      password varchar,\n"
                    + "      profilePicture blob,\n"
                    + "      picType varchar,\n"
                    + "      first_name varchar,\n"
                    + "      last_name varchar,\n"
                    + "      email varchar,\n"
                    + "      status varchar,\n"
                    + "      addresses  map<text, frozen <address>>\n"
                    + "  );";
            Session session = c.connect();
            try {
                PreparedStatement statement = session.prepare(createkeyspace);
                BoundStatement boundStatement = new BoundStatement(statement);
                ResultSet rs = session.execute(boundStatement);
                System.out.println("created instagrim ");
            } catch (Exception et) {
                System.out.println("Can't create instagrim " + et);
            }

            //now add some column families 
            //System.out.println("" + CreatePicTable);

            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreatePicTable);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create tweet table " + et);
            }
            //System.out.println("" + Createuserpiclist);

            try {
                SimpleStatement cqlQuery = new SimpleStatement(Createuserpiclist);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create user pic list table " + et);
            }
            
            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreateComments);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create comments table " + et);
            }
            
            
            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreateFollowers);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create followers table " + et);
            }
            
            
             try {
                SimpleStatement cqlQuery = new SimpleStatement(profileFollowCounters);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create followers table " + et);
            }
            
            
            //System.out.println("" + CreateAddressType);
            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreateAddressType);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create Address type " + et);
            }
            //System.out.println("" + CreateUserProfile);
            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreateUserProfile);
                session.execute(cqlQuery);
                System.out.println("USERORfiles is created!");
            } catch (Exception et) {
                System.out.println("Can't create UserProfile " + et);
            }
            session.close();

        } catch (Exception et) {
            System.out.println("Other keyspace or coulm definition error" + et);
        }

    }
}
