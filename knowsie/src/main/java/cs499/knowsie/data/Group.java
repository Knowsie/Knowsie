package cs499.knowsie.data;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

@ParseClassName("Group")
public class Group extends ParseObject {

    public Group() {

    }

    public Group(ParseUser user, String groupName) {
        put("user", user);
        put("groupName", groupName);
    }

    public void addTwitterUser(String twitterUser) {
        add("twitterUsers", twitterUser);
    }

    public List<String> getTwitterUsers() {
        return getList("twitterUsers");
    }

    public void addInstagramUser(String instagramUser) {
        add("instagramUsers", instagramUser);
    }

    public List<String> getInstagramUsers() {
        return getList("instagramUsers");
    }

    public String getGroupName() {
        return getString("groupName");
    }

    public void setGroupName(String groupName) {
        put("groupName", groupName);
    }

    public ParseUser getUser() {
        return getParseUser("user");
    }

    public void setUser(ParseUser user) {
        put("user", user);
    }

    public String getGroupID() {
        return getObjectId();
    }

    @Override
    public String toString() {
        return getGroupName();
    }
}
