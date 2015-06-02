package cs499.knowsie.data.instagram;

import com.google.gson.annotations.SerializedName;

import cs499.knowsie.data.Update;

public class Post extends Update {
    public String id;
    private User user;
    private Caption caption;
    private Images images;
    private String fullName;

    @Override
    public String getPrimaryName() {
        return user.fullName;
    }

    @Override
    public void setPrimaryName(String fullName) {
        super.setPrimaryName(fullName);
        user.fullName = fullName;
    }

    @Override
    public String getSecondaryName() {
        return user.username;
    }

    @Override
    public Source getSource() {
        return Source.INSTAGRAM;
    }

    @Override
    public String getText() {
        return caption.text;
    }

    @Override
    public String getMediaURL() {
        return images.standardResolution.url;
    }

    private class Caption {
        String text;
    }

    private class Images {
        @SerializedName("low_resolution")
        ImageInfo lowResolution;

        @SerializedName("standard_resolution")
        ImageInfo standardResolution;

    }

    private class ImageInfo {
        String url;
    }

    private class User {
        String username;
        String fullName;
    }
}
