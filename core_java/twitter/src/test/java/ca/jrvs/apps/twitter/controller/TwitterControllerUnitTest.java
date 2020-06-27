package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {

    @Mock
    TwitterService mock;

    @InjectMocks
    TwitterController controller;

    @Test
    public void postTweet() {
        Tweet expectedTweet = new Tweet();

        when(mock.postTweet(any())).thenReturn(expectedTweet);

        //This test should pass
        Tweet tweet = controller.postTweet(new String[] {"post", "sample post", "1.0:-1.0"});

        try {
            //this test should fail coordinates not provided
            controller.postTweet(new String[] {"post", "sample post"});
            fail();
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }

    }

    @Test
    public void showTweet() {
        Tweet expectedTweet = new Tweet();

        when(mock.showTweet(any(), any())).thenReturn(expectedTweet);

        //pass test
        Tweet tweet = controller.showTweet(new String[]{"show", "5207069947664319452"});

        try {
            //no tweet id provided  test should fail
            controller.showTweet(new String[]{"show", ""});
            fail();
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }

    }

    @Test
    public void deleteTweet() {

        Tweet expectedTweet = new Tweet();
        List<Tweet> tweetsList = new ArrayList<Tweet>();
        when(mock.deleteTweets(any())).thenReturn(tweetsList);

        //pass case
        List<Tweet> deleteTweets = controller.deleteTweet(new String[] {"delete", "2245064947774019457"});


        try {
            // fail test no id given
            controller.deleteTweet(new String[] {"delete"});
            fail();
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }

    }
}