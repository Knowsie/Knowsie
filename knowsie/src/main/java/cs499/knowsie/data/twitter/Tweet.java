package cs499.knowsie.data.twitter;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

import cs499.knowsie.data.Update;

public class Tweet extends Update {
    private TwitterUser user;
    private Entity entity;
    private long id;

    @SerializedName("created_at")
    private String createdAt;

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
    public Date getDate() {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
            return sdf.parse(createdAt);
        }
        catch (Exception e) {
            return null;
        }
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
