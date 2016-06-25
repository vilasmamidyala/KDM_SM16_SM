/**
 * Created by vilas on 6/24/2016.
 */
import java.io.BufferedReader;
        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.FileWriter;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.io.PrintStream;
        import java.io.PrintWriter;

        import twitter4j.FilterQuery;
        import twitter4j.StallWarning;
        import twitter4j.Status;
        import twitter4j.StatusDeletionNotice;
        import twitter4j.StatusListener;
        import twitter4j.TwitterStream;
        import twitter4j.TwitterStreamFactory;
        import twitter4j.User;
        import twitter4j.conf.ConfigurationBuilder;

public class datac {
    public static void main(String[] args) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("ZXia2FH1Ngj2vkRoZX1dlrxzh");
        cb.setOAuthConsumerSecret("Ahux6OMyJtHqDASTzFKoCEfMhvtrAX01HyfXIEIh1QXJGz8zlb");
        cb.setOAuthAccessToken("220954604-3kKoMiUTYGiHDtS21YqNTQV8rJnaMXnTuGe6guyj");
        cb.setOAuthAccessTokenSecret("hFdQsTQjazG31ysJnHy5BEIGyDOy4swX9qcffRoJhfy2g");

        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

        StatusListener listener = new StatusListener() {

            @Override
            public void onException(Exception arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrubGeo(long arg0, long arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStatus(Status status) {
                User user = status.getUser();

                // gets Username
                String Username = user.getScreenName();
                //System.out.println(username);
                String uName = user.getName();
                String UserLocation = user.getLocation();
             //   System.out.println(Username + "" + uName + "" + UserLocation);
                long tweetId = status.getId();
                //System.out.println(tweetId);
                String content = status.getText();
                System.out.println(content +"\n");

            }

            @Override
            public void onTrackLimitationNotice(int arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStallWarning(StallWarning arg0) {
                // TODO Auto-generated method stub

            }

        };

        FilterQuery fq = new FilterQuery();

        String keywords[] = {"USA politics","HillaryClinton","realDonaldTrump","elections","elections in usa"};

        fq.track(keywords);
        twitterStream.addListener(listener);
        twitterStream.filter(fq);

        try {

            PrintStream out = new PrintStream(new FileOutputStream("TwitterTweets.txt",true));
            System.setOut(out);

        }
        catch(IOException e1) {
            System.out.println("Error during reading/writing");
        }

    }
}