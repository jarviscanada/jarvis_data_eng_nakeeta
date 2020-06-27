package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@org.springframework.stereotype.Controller
public class TwitterController implements Controller {

    private static final String COLON = ":";
    private static final String COMMA = ",";

    private Service service;

    @Autowired
    public TwitterController(Service service) {
        this.service = service;
    }

    /**
     * Parse user argument and post a tweet by calling service classes
     *
     * @param args
     * @return a posted tweet
     * @throws IllegalArgumentException if args are invalid
     */
    @Override
    public Tweet postTweet(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException(
                    "ERROR incorrect format: TwitterCLIApp post, tweet, location(longitude:latitude)");
        }

        String tweet_text = args[1];
        String coordinates = args[2];
        String coordArray[] = coordinates.split(COLON);

        //checks if longitude and latitude are given
        if (coordArray.length != 2 || StringUtils.isEmpty(tweet_text)) {
            throw new IllegalArgumentException("Error incorrect location format: longitude:latitude");
        }

        Double lon = null;
        Double lat = null;

        try {
            lon = Double.parseDouble(coordArray[0]);
            lat = Double.parseDouble(coordArray[1]);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Error incorrect location format: longitude:latitude");
        }

        //Create and post a tweet with set coordinates
        Tweet postTweet = new Tweet();
        Coordinates tweetcoordinates = new Coordinates();
        tweetcoordinates.setCoordinates(Arrays.asList(lon, lat));
        tweetcoordinates.setType("Point");
        postTweet.setText(tweet_text);
        postTweet.setCoordinates(tweetcoordinates);
        // call post tweet Method
        return service.postTweet(postTweet);
    }

    /**
     * Parse user argument and search a tweet by calling service classes
     *
     * @param args
     * @return a tweet
     * @throws IllegalArgumentException if args are invalid
     */
    @Override
    public Tweet showTweet(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException(
                    "ERROR please the following: TwitterCLIApp show ,tweet id" );
        }

        String id = args[1];

        //Checks if tweet id is correct
        if (!Pattern.matches("^\\d+$", id)) {
            throw new IllegalArgumentException("Error:tweet id is not valid ");
        }

        //call show tweet method
        return service.showTweet(id, null);
    }

    /**
     * Parse user argument and delete tweets by calling service classes
     *
     * @param args
     * @return a list of deleted tweets
     * @throws IllegalArgumentException if args are invalid
     */
    @Override
    public List<Tweet> deleteTweet(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException(
                    "Error, check format: TwitterCLIApp delete tweet id, tweet id ");
        }

        String[] allIDs = args[1].split(COMMA);

        //Check if id is valid
        for (String id : allIDs){
            if (!Pattern.matches("^\\d+$", id)) {
                throw new IllegalArgumentException("Error:tweet id is not valid");
            }
        }

        // call delete tweet method
        return service.deleteTweets(allIDs);
    }
}