package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TwitterDaoIntTest {

    private static TwitterDao dao;
    private String idString;
    private Tweet tweet;


    @BeforeClass
    public static void setUp() throws JsonProcessingException {

        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");

        System.out.println(CONSUMER_KEY + " | " + CONSUMER_SECRET + " | " + ACCESS_TOKEN
                + " | " + TOKEN_SECRET);

        HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);


        dao = new TwitterDao(httpHelper);
    }

    @Before
    public void postTweet() throws JsonProcessingException {


        String text = "SAMPLE_TWEET_WELCOME_TO_TWITTER" + System.currentTimeMillis();
        Double lat = 1d;
        Double lon = -1d;

        Tweet postTweet = new Tweet();
        Coordinates coordinates = new Coordinates();
        coordinates.setCoordinates(Arrays.asList(lon, lat));
        coordinates.setType("Point");

        postTweet.setText(text);
        postTweet.setCoordinates(coordinates);

        System.out.println(JsonUtil.toJson(postTweet, true, true));

        tweet = dao.create(postTweet);
        System.out.println(JsonUtil.toJson(tweet, true, true));

        //check if text match
        assertEquals(text, tweet.getText());

        //check if coordinates match
        assertEquals(lon, tweet.getCoordinates().getCoordinates().get(0));
        assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));

        //set id string for tweet
        idString = tweet.getIdStr();
    }

    @Test
    public void findTweet() throws JsonProcessingException {

        //find tweet created from postTweet method
        Tweet findTweet = dao.findById(tweet.getIdStr());

        System.out.println(JsonUtil.toJson(findTweet, true, true));

        assertEquals(tweet.getText(), findTweet.getText());
        assertEquals(idString, findTweet.getIdStr());
    }

    @After
    public void deleteTweet() throws JsonProcessingException {
        //delete tweet by tweet id
        Tweet deleteTweet = dao.deleteById(tweet.getIdStr());

        System.out.println(JsonUtil.toJson(deleteTweet, true, false));

        assertEquals(tweet.getText(), deleteTweet.getText());
        assertEquals(idString, deleteTweet.getIdStr());
    }
}