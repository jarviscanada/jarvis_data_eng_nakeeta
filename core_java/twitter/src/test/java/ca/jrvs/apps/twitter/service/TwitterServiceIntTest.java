package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TwitterServiceIntTest {

    private static TwitterService twitterService;
    private List<String> IdList = new ArrayList<String>();

    @Before
    public void setUp() throws JsonProcessingException {

        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");

        System.out.println(CONSUMER_KEY + " | " + CONSUMER_SECRET + " | " + ACCESS_TOKEN
                + " | " + TOKEN_SECRET);

        HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);

        TwitterDao dao = new TwitterDao(httpHelper);

        twitterService = new TwitterService(dao);


        String tweetText;
        Double lat = 1d;
        Double lon = -1d;

        for (int i = 0; i < 2; i++) {
            tweetText = "SAMPLE_TWEET_WELCOME_TO_TWITTER" + System.currentTimeMillis();

            Tweet postTweet = new Tweet();
            Coordinates coordinates = new Coordinates();
            coordinates.setCoordinates(Arrays.asList(lon, lat));
            coordinates.setType("Point");
            postTweet.setText(tweetText);
            postTweet.setCoordinates(coordinates);

            Tweet postedTweet = twitterService.postTweet(postTweet);
            assertEquals(postedTweet.getText(), postTweet.getText());

            IdList.add(postedTweet.getIdStr());
            System.out.println(JsonUtil.toJson(postedTweet, true, true));
        }
    }

    @Test
    public void findTweets() throws JsonProcessingException {
        //find tweet by id
        for (String id : IdList) {
            Tweet tweet = twitterService.showTweet(id, null);
            assertNotNull(tweet);

            //check if ids match
            assertEquals(tweet.getIdStr(), id);
            System.out.println(JsonUtil.toJson(tweet, true, true));
        }
    }

    @After
    public void deleteTweets() throws JsonProcessingException {
        // delete tweets by ids
        List<Tweet> deleteTweets = twitterService.deleteTweets(IdList.toArray(new String[0]));

        //check if id match
        for (int i = 0; i < IdList.size(); i++) {
            assertEquals(deleteTweets.get(i).getIdStr(), IdList.get(i));
            System.out.println(JsonUtil.toJson(deleteTweets.get(i), true, true));
        }
    }
}