package cs499.knowsie.data.instagram;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InstagramUser {
    private List<UserData> data;

    public InstagramUser() {
    }

    public String getFullName() {
        if (!data.isEmpty()) {
            return data.get(0).fullName;
        }
        return null;
    }

    public String getID() {
        if (!data.isEmpty()) {
            return data.get(0).id;
        }
        return null;
    }

    private class UserData {
        String username;
        @SerializedName("full_name")
        String fullName;
        String id;
    }

}
