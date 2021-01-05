import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import twitter4j.IDs;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.RateLimitStatusEvent;
import twitter4j.RateLimitStatusListener;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.json.*;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

//Mention - count number of 
public class JsonLoader {
	 String consumerKey = "";
     String consumerSecret = "";
     String accessToken = ""
     String accessTokenSecret = "";

	public JsonLoader() throws InterruptedException, TwitterException, IOException {
		Twitter twitter = new TwitterFactory(new ConfigurationBuilder().setJSONStoreEnabled(true).build()).getInstance();

		twitter.setOAuthConsumer(consumerKey, consumerSecret);
		AccessToken token = new AccessToken(accessToken, accessTokenSecret);
		twitter.setOAuthAccessToken(token);


		long lCursor = -1;
		IDs followersIDs;
		FileWriter writer;
		//List<Status> statuses;
		try {
			
			writer = new FileWriter("info.txt");
			do {
				followersIDs = twitter.getFollowersIDs("JackilR", lCursor);
				System.out.println(twitter.showUser("JackilR").getName());
				System.out.println("==========================");
			
				Query query = new Query("from:JackilR");
				query.setCount(100);
				QueryResult qr = twitter.search(query);
			
				List<Status> statuses ;
				//statuses = twitter.getHomeTimeline();	
				statuses = qr.getTweets();
				//Paging paging = new Paging(1);
				//statuses = twitter.getUserTimeline("riotgames", paging);
			
				System.out.print(statuses.size());
			
				for (Status status : statuses) {
					
					long id = status.getId();
					int retweetCount = status.getRetweetCount();
					String createdAt = status.getCreatedAt().toString();
					int userMention = status.getUserMentionEntities().length;
					int favourites = status.getFavoriteCount();
					int media = status.getMediaEntities().length;
					int url = status.getURLEntities().length;
					int hashtag = status.getHashtagEntities().length;
					System.out.println(status.getUser().getName() + ":" +
			                           id + " Retweet Count: "+ retweetCount
			                           + " Mention Count: "+ userMention  + " Favorite: "+ favourites
			                           + " CreatedAt: "+createdAt + " media: "+media +" URL: "+url + " Hashtag: "+hashtag);
			        
					
					writer.write(id +"\t"+ retweetCount + "\t"+ createdAt+  "\t"+ userMention
							+ "\t"+ favourites+ "\t"+ media+"\t"+ url+"\t"+ hashtag+"\n");
			        
			    }
				
			/*	for (long i : followersIDs.getIDs())
				{
					String name = twitter.showUser(i).getScreenName();
					int follower = twitter.showUser(i).getFollowersCount();
					String createdAt = twitter.showUser(i).getCreatedAt().toString();
					int friends = twitter.showUser(i).getFriendsCount();
					int favourites = twitter.showUser(i).getFavouritesCount();
					int statusCount = twitter.showUser(i).getStatusesCount();
					
					System.out.println("Follower ID #" + i);
					System.out.println("MY Followers: "+name +" Follower Count ["+ follower+"]" 
					+ " CreatedAt: "+createdAt +  " User friends Count: ["+ friends+"]");
					
					
			/*	IDs followersIDs2 = twitter.getFollowersIDs(i, lCursor);
					for(long j : followersIDs2.getIDs()){
						System.out.println("His follower ID #" + j);
						System.out.println("== Their Followers: "+twitter.showUser(j).getScreenName()+" Follower Count ["+ twitter.showUser(j).getFollowersCount()+"]" 
						+" CreatedAt: "+twitter.showUser(i).getCreatedAt()+ " User Retweet Count: ["+ twitter.showUser(i).getStatus().getRetweetCount()+"]");	
					}


						//System.out.println("ID: "+status.getId()+"\t"+"Tweet: "+ status.getText());
						writer.write(i +"\t"+ name +"\t"+ follower+ "\t"+ createdAt+  "\t"+ friends
								+ "\t"+ favourites+ "\t"+ statusCount+"\n");
						
						
						
					

				}*/
writer.close();	
	
		}while (followersIDs.hasNext());
		
//writer.close();
		} catch (TwitterException e) {

			e.printStackTrace();
            System.out.println("Failed to get rate limit status: " + e.getMessage());
            //System.exit(-1);
		}
			/*long id;
			int retweetCount;
			String createdAt ;
			int userMention;
			int favourites;
			int media;
			 
		            //Twitter twitter = new TwitterFactory().getInstance();
		            int page = 1;
		            ResponseList<User> users;
		            do {
		                users = twitter.searchUsers("riotgames", page);
		                System.out.println(users.size());
		                for (User user : users) {
		                    if (user.getStatus() != null && user.getStatus().getText() != null) {
		                        System.out.println("@" + user.getScreenName() + " - " + user.getStatus().getText());
		                        id = user.getStatus().getId();
		    					retweetCount = user.getStatus().getRetweetCount();
		    					createdAt = user.getStatus().getCreatedAt().toString();
		    					userMention = user.getStatus().getUserMentionEntities().length;
		    					favourites = user.getStatus().getFavoriteCount();
		    					media = user.getStatus().getMediaEntities().length;
		                        
		                        
		                        System.out.println(  ":" +
				                           id + " Retweet Count: "+ retweetCount
				                           + " Mention Count: "+ userMention  + " Favorite: "+ favourites
				                           + " CreatedAt: "+createdAt + " media: "+media);
		                        writer.write(id +"\t"+ retweetCount + "\t"+ createdAt+  "\t"+ userMention
		    							+ "\t"+ favourites+ "\t"+ media+"\n");
		                    } else {
		                        // the user is protected
		                        System.out.println("@" + user.getScreenName());
		                    }
		                }
		                
		                page++;
		            } while (users.size() != 0 && page < 20);
		            writer.close();
		            System.out.println("done.");
		            System.exit(0);
		        } catch (TwitterException te) {
		            te.printStackTrace();
		            System.out.println("Failed to search users: " + te.getMessage());
		            System.exit(-1);
		        }*/
	}

	public static void main (String [] args) throws TwitterException, InterruptedException, IOException{
		JsonLoader t = new JsonLoader();
	}
}
