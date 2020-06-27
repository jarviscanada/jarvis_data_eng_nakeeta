package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest {

    @Mock
    HttpHelper mockHelper;

    @InjectMocks
    TwitterDao dao;

    //Define tweetJsonStr to use later
    String tweetJsonStr = "{\n"
            + "   \"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
            + "   \"id\":1097607853932564480,\n"
            + "   \"id_str\":\"1097607853932564480\",\n"
            + "   \"text\":\"test with loc223\",\n"
            + "   \"entities\":{\n"
            + "      \"hashtags\":[],\n"
            + "      \"user_mentions\":[]\n"
            + "   },\n"
            + "   \"coordinates\":null,\n"
            + "   \"retweet_count\":0,\n"
            + "   \"favorite_count\":0,\n"
            + "   \"favorited\":false,\n"
            + "   \"retweeted\":false\n"
            + "}";

    //Set a sample ID to test later
    String testId = "1097607853932564480";

    @Test
    public void createTweet() throws Exception {

        String text = "SAMPLE_TWEET_WELCOME_TO_TWITTER" + System.currentTimeMillis();
        Double lat = 1d;
        Double lon = -1d;

        when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
        try {
            Tweet postTweet = new Tweet();
            Coordinates coordinates = new Coordinates();
            coordinates.setCoordinates(Arrays.asList(lon, lat));
            coordinates.setType("Point");

            postTweet.setText(text);
            postTweet.setCoordinates(coordinates);

            dao.create(postTweet);
            fail();
        }
        catch (RuntimeException e){
            assertTrue(true);
        }

        when(mockHelper.httpPost(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);
        Tweet expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr, Tweet.class);

        // mock parseResponseBody
        doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());

        Tweet tweetpost = new Tweet();
        Coordinates coordinates1 = new Coordinates();
        coordinates1.setCoordinates(Arrays.asList(lon, lat));
        coordinates1.setType("Point");
        tweetpost.setText(text);
        tweetpost.setCoordinates(coordinates1);
        Tweet tweet = spyDao.create(tweetpost);
        assertNotNull(tweet.getText());
    }

    @Test
    public void findTweet() throws Exception {

        when(mockHelper.httpGet(isNotNull())).thenThrow(new RuntimeException("mock"));

        try {
            dao.findById(testId);
            fail();
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }

        when(mockHelper.httpGet(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);

        Tweet expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr, Tweet.class);

        //mock parseResponseBody
        doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
        Tweet tweet = spyDao.findById(testId);
        assertNotNull(tweet);
    }

    @Test
    public void deleteTweet() throws Exception {

        when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));

        try {
            dao.deleteById(testId);
            fail();
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }

        when(mockHelper.httpPost(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);
        Tweet expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr, Tweet.class);
        // mock parseResponseBody
        doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
        Tweet tweet = spyDao.deleteById(testId);
        assertNotNull(tweet.getText());
    }
}