package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class TwitterControllerIntTest {


    private TwitterController controller;
    private List<Tweet> tweets;

    @Before
    public void setup() {
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");

        System.out.println(CONSUMER_KEY + " | " + CONSUMER_SECRET + " | " + ACCESS_TOKEN + " | " + TOKEN_SECRET);

        HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);

        TwitterDao dao = new TwitterDao(httpHelper);

        Service twitterService = new TwitterService(dao);

        controller = new TwitterController(twitterService);

        tweets = new ArrayList<>();
        String[] testTweet;

        //post 5 tweets using a loop
        for(int i = 0; i < 5; i++) {
            testTweet = new String[] {"post", "SAMPLE_TWEET_WELCOME_TO_TWITTER" + System.currentTimeMillis(), "-1.0:1.0" };
            // pass testTweets to method postTweet in order to post tweets
            Tweet tweet = controller.postTweet(testTweet);

            //make sure tweets are not null
            assertNotNull(tweet);
            tweets.add(tweet);
        }
    }

    @Test
    public void showTweets() throws Exception {
        String[] testTweet;

        //Find and show tweets
        for (Tweet foundTweet : tweets) {

            long id = foundTweet.getId();
            testTweet = new String[]{"show", Long.toString(id)};
            Tweet tweet = controller.showTweet(testTweet);
            assertNotNull(tweet);

        }
    }

    @After
    public void deleteTweets() throws Exception {
        String[] idList  = new String[5];
        String[] testTweet;
        int i = 0;
        // delete all created tweets
        for (Tweet tweet : tweets) {
            idList[i] = Long.toString(tweet.getId());
            i++;
        }

        testTweet = new String[]{"delete", String.join(",", idList)};

        List<Tweet> deleteTweets = controller.deleteTweet(testTweet);

    }
}