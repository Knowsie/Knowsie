package cs499.knowsie.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InstagramPosts {
    private Pagination pagination;
    private List<Post> data;

    public InstagramPosts() {
    }

    public List<Post> getData() {
        return data;
    }

    public void setAllFullNames(String fullName) {
        for (Post p : data) {
            p.setUserName(fullName);
        }
    }

    public String getLastID() {
        return pagination.nextMaxID;
    }

    private class Post extends Update {

        public String id;
        private User user;
        private Caption caption;
        private Images images;
        private String fullName;

        @Override
        public String getUserName() {
            return user.fullName;
        }

        @Override
        public void setUserName(String fullName) {
            super.setUserName(fullName);
            user.fullName = fullName;
        }

        @Override
        public String getScreenName() {
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

    }

    private class Pagination {
        @SerializedName("next_max_id")
        String nextMaxID;
    }

    private class Caption {
        String text;
    }

    private class Images {
        @SerializedName("low_resolution")
        LowResolution lowResolution;
    }

    private class LowResolution {
        String url;
    }

    private class User {
        String username;
        String fullName;
    }
}
