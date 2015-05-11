package cs499.knowsie.data;

import java.util.ArrayList;
import java.util.HashMap;

public class Group {

    private String groupName;
    private HashMap<String, String> usernames;
    private ArrayList<Update> listOfUpdates;

    public Group(String groupName) {
        this.groupName = groupName;
        this.usernames = new HashMap<String, String>();
        this.listOfUpdates = new ArrayList<>();
    }

    public void addUsername(String username, String source) {
        this.usernames.put(username, source);
    }

    public void addUpdate(Update update) {
        this.listOfUpdates.add(update);
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public HashMap<String, String> getUsernames() {
        return usernames;
    }

    public void setUsernames(HashMap<String, String> usernames) {
        this.usernames = usernames;
    }

    public ArrayList<Update> getListOfUpdates() {
        return listOfUpdates;
    }

    public void setListOfUpdates(ArrayList<Update> listOfUpdates) {
        this.listOfUpdates = listOfUpdates;
    }
}
