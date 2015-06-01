package cs499.knowsie.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InstagramUser {
    private List<UserData> data;

    public InstagramUser() {
    }

    public String getUsername() {
        return data.get(0).username;
    }

    public String getFullName() {
        return data.get(0).fullName;
    }

    public String getID() {
        return data.get(0).id;
    }

    private class UserData {
        String username;
        @SerializedName("full_name")
        String fullName;
        String id;
    }

}
