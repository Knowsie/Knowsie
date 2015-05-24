package cs499.knowsie.data;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

@ParseClassName("Group")
public class Group extends ParseObject {

    public Group() {

    }

    public Group(String groupName) {
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
}
