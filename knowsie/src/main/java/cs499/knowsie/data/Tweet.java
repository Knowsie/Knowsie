package cs499.knowsie.data;

import com.google.gson.annotations.SerializedName;

public class Tweet extends Update {
    private TwitterUser user;
    private Media media;

    public Tweet() {
        super(Source.TWITTER);
    }

    @Override
    public String getUserName() {
        return user.name;
    }

    @Override
    public String getScreenName() {
        return user.screenName;
    }

    private class TwitterUser {
        String name;

        @SerializedName("screen_name")
        String screenName;
    }

    private class Media {
        @SerializedName("media_url_https")
        String mediaURL;
    }
}
