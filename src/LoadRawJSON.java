import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.TwitterObjectFactory;
import java.io.*;
import java.util.List;

public final class LoadRawJSON {
    public static void main(String[] args) {
    	  String consumerKey = "";
          String consumerSecret = "";
          String accessToken = "";
          String accessTokenSecret = "";
    	///TwitterFactory twitterFactory = new TwitterFactory();
      
        
        Twitter twitter = new TwitterFactory(new ConfigurationBuilder().setJSONStoreEnabled(true).build()).getInstance();
        twitter.setOAuthConsumer(consumerKey, consumerSecret);
        twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));
        
        System.out.println("Saving public timeline.");
        try {
            new File("statuses").mkdir();
            List<Status> statuses = twitter.getHomeTimeline();
            for (Status status : statuses) {
            	System.out.println(status);
                String rawJSON = TwitterObjectFactory.getRawJSON(status);
                String fileName = "statuses/" + status.getId() + ".json";
                storeJSON(rawJSON, fileName);
                System.out.println(fileName + " - " + status.getText());
            }
            System.out.print("\ndone.");
            System.exit(0);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("Failed to store tweets: " + ioe.getMessage());
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
    }

    private static void storeJSON(String rawJSON, String fileName) throws IOException {
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
}
