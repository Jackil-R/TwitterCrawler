import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.json.*;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import twitter4j.Friendship;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterObjectFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.*;
/*

Application Settings
Keep the "Consumer Secret" a secret. This key should never be human-readable in your application.
Consumer Key (API Key)	
Consumer Secret (API Secret)	
Access Level	Read and write (modify app permissions)
Owner	
Owner ID	
===========================================
Your Access Token
This access token can be used to make API requests on your own account's behalf. Do not share your access token secret with anyone.
Access Token	-
Access Token Secret	
Access Level	Read and write
Owner	
Owner ID	
 */


public class ProfileCrawler {
	
	
	private static void storeJSON(String rawJSON, String fileName) throws IOException{
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            fos = new FileOutputStream(fileName);
            osw = new OutputStreamWriter(fos, "UTF-8");
            bw = new BufferedWriter(osw);
            bw.write(rawJSON);
            bw.flush();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ignore) {
                }
            }
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException ignore) {
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ignore) {
                }
            }
        }
    }
	
	public static void main(String[] args) throws IOException{

		//Authentication key
        String consumerKey = "";
        String consumerSecret = "";
        String accessToken = "";
        String accessTokenSecret = "";
        

        //Enabling json to retrieve the object
        Configuration  config = new ConfigurationBuilder().setJSONStoreEnabled(true).build();
        TwitterFactory twitterFactory = new TwitterFactory(config);
        
        Twitter twitter = twitterFactory.getInstance();
        twitter.setOAuthConsumer(consumerKey, consumerSecret);
        twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));
       
        System.out.println("START:");
        
        
        /*System.out.println("Profile:");
        
       try {
    	    new File("Details").mkdir();
        	String username = twitter.verifyCredentials().getScreenName();
        	System.out.println("Showing @" + username + "'s user details.");
            User user = twitter.showUser(username);
            String rawJSON = TwitterObjectFactory.getRawJSON(user);
            //System.out.println(TwitterObjectFactory.getRawJSON(user));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(rawJSON);
            String json = gson.toJson(je);
            String fileName = "Details/" + "details" + ".json";
            storeJSON(json,fileName);
            System.out.println(json);
            
          
        } catch (TwitterException e) {
            System.out.println("Failed to get timeline: " + e.getMessage());
        }*/
        
        /*
        
        System.out.println("User Timeline:");
        
        try {
            new File("UserTimeline").mkdir();
            String user = twitter.verifyCredentials().getScreenName();
            System.out.println("Showing @" + user + "'s user timeline.");
            //System.out.println(TwitterObjectFactory.getRawJSON(twitter.getUserTimeline()));
            String rawJSON = TwitterObjectFactory.getRawJSON(twitter.getUserTimeline());
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(rawJSON);
            String json = gson.toJson(je);
            String fileName = "UserTimeline/" + "userTimeline" + ".json";
            storeJSON(json,fileName);
            System.out.println(json);
           
        } catch (TwitterException e1) {
            System.out.println("Failed to get timeline: " + e1.getMessage());
        }
        */
        
        //System.out.println("Mentions: ");
        /*
        try {
        	new File("MentionsTimeline").mkdir();
            User user = twitter.verifyCredentials();
            System.out.println("Showing @" + user.getScreenName() + "'s mentions.");
            String rawJSON = TwitterObjectFactory.getRawJSON(twitter.getMentionsTimeline());
        	//System.out.println(rawJSON);
        	Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(rawJSON);
            String json = gson.toJson(je);
            String fileName = "MentionsTimeline/" + "mentionsTimeline" + ".json";
            storeJSON(json,fileName);
        	
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
        }
        */
        
        //System.out.println("Home Timeline: ");
     	/*
        try {
        	new File("HomeTimeline").mkdir();
            User user = twitter.verifyCredentials();
        	System.out.println("Showing @" + user.getScreenName() + "'s home timeline.");
            String rawJSON = TwitterObjectFactory.getRawJSON(twitter.getHomeTimeline());
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(rawJSON);
            String json = gson.toJson(je);
            String fileName = "HomeTimeline/" + "homeTimeline" + ".json";
            storeJSON(json,fileName);
            
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
        }
        */
        
        
        
        System.out.println("Followers:");
        try {
        	new File("Followers").mkdir();
        	PagableResponseList<User> followersList;
            long cursor = -1;
           
            followersList = twitter.getFollowersList(twitter.getScreenName(),cursor);
            String rawJSON = TwitterObjectFactory.getRawJSON(followersList); 
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(rawJSON);
            String json = gson.toJson(je);
            String fileName = "Followers/" + "followersList" + ".json";
            storeJSON(json,fileName); 
            System.out.println(json);
        } catch (TwitterException e1) {
            System.out.println("Failed to get followers' ids: " + e1.getMessage());
        }
        
        
        
        /*
        System.out.println("Followings: ");
        
        try {
        	new File("Followings").mkdir();
        	PagableResponseList<User> followingList;
            long cursor = -1;
            
            followingList = twitter.getFriendsList(twitter.getScreenName(),cursor);
            String rawJSON = TwitterObjectFactory.getRawJSON(followingList);
            //System.out.println(rawJSON);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(rawJSON);
            String json = gson.toJson(je);
            String fileName = "Followings/" + "friendsList" + ".json";
            storeJSON(json,fileName); 
            System.out.println(json);
            
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get friends' ids: " + te.getMessage());
        }  
        
           
        
        //System.out.println("Friends: ");
        /*
        try {
        	new File("Friendships").mkdir();
        	PagableResponseList<User> followingList;
        	ResponseList<Friendship> friendships;
            long cursor = -1;
            followingList = twitter.getFriendsList(twitter.getScreenName(),cursor);
            String name = "";
            for(User a : followingList){
            	name = name + (a.getScreenName() + ",");
            }
        
            friendships = twitter.lookupFriendships(name.split(","));
            String rawJSON = TwitterObjectFactory.getRawJSON(friendships);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(rawJSON);
            String json = gson.toJson(je);
            String fileName = "Friendships/" + "Connections" + ".json";
            storeJSON(json,fileName);
        } catch (TwitterException e1) {
            System.out.println("Failed to get followers' ids: " + e1.getMessage());
        }    
        */
        
        System.out.println("DONE: ");
	}
}
