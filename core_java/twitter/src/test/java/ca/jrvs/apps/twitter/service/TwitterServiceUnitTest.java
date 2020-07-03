package ca.jrvs.apps.twitter.service;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {

    @Mock
    CrdDao dao;

    @InjectMocks
    TwitterService service;

    @Test
    public void postTweets() throws JsonProcessingException {

        when(dao.create(any())).thenReturn(new Tweet());
        Tweet tweet = new Tweet();
        Coordinates coordinates = new Coordinates();
        coordinates.setCoordinates(Arrays.asList(1d, -1d));
        coordinates.setType("Point");
        tweet.setText("SAMPLE_TWEET_WELCOME_TO_TWITTER");
        tweet.setCoordinates(coordinates);

        service.postTweet(tweet);
        System.out.println(JsonUtil.toJson(tweet, true, true));
        assertEquals(tweet.getId(), null);
    }

    @Test
    public void findTweets(){
        Tweet tweet = new Tweet();
        String id = "2997647854932567780";

        when(dao.findById(any())).thenReturn(tweet);

        tweet = service.showTweet(id, null);
        assertEquals(tweet.getId(), null);
        assertEquals(tweet.getText(), null);
    }

    @Test
    public void deleteTweets(){

        String[] idList = {"1997707855932163481", "2341207065289656375"};
        when(dao.deleteById(any())).thenReturn(new Tweet());
        List<Tweet> tweets = service.deleteTweets(idList);

        for (Tweet tweet : tweets) {
            assertEquals(tweet.getId(), null);
            assertEquals(tweet.getText(), null);
        }
    }
}