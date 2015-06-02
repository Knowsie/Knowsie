package cs499.knowsie.data.twitter;

import com.google.gson.annotations.SerializedName;

import cs499.knowsie.data.Update;

public class Tweet extends Update {
    private TwitterUser user;
    private Entity entity;
    private long id;

    public Tweet() {
    }

    @Override
    public String getPrimaryName() {
        return user.name;
    }

    @Override
    public String getSecondaryName() {
        return user.screenName;
    }

    @Override
    public Source getSource() {
        return Source.TWITTER;
    }

    public Media getMedia() {
        return entity.media;
    }

    public String getMediaURL() {
        return entity.media.mediaURL;
    }

    @Override
    public long getID() {
        return id;
    }

    private class TwitterUser {
        String name;

        @SerializedName("screen_name")
        String screenName;
    }

    private class Entity {
        Media media;
    }

    private class Media {
        @SerializedName("media_url_https")
        String mediaURL;
    }
}
