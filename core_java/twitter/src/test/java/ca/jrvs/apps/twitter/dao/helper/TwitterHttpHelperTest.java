package ca.jrvs.apps.twitter.dao.helper;

import com.google.gdata.util.common.base.PercentEscaper;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.net.URI;

public class TwitterHttpHelperTest {

    @Test
    public void httpPost() throws Exception {
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");

        System.out.println(CONSUMER_KEY + " | " + CONSUMER_SECRET + " | " + ACCESS_TOKEN
                + " | " + TOKEN_SECRET);

        HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
        String status = "WELCOME TO TWITTER!!! #twitter";
        PercentEscaper percentEscaper = new PercentEscaper("", false);
        HttpResponse response = httpHelper.
                httpPost(new URI(("https://api.twitter.com/1.1/statuses/update.json?status=") + percentEscaper.escape(status)));
        System.out.println(EntityUtils.toString(response.getEntity()));
    }
}