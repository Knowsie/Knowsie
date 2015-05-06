package cs499.knowsie;

import java.util.ArrayList;

/**
 * Created by eric on 5/5/2015.
 */
public class Group {

    private String groupName;
    private ArrayList<String> listOfUsernames;
    private ArrayList<Update> listOfUpdates;

    public Group(String groupName) {
        this.groupName = groupName;
        this.listOfUsernames = new ArrayList<>();
        this.listOfUpdates = new ArrayList<>();
    }

    public void addUsername(String username) {
        this.listOfUsernames.add(username);
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

    public ArrayList<String> getListOfUsernames() {
        return listOfUsernames;
    }

    public void setListOfUsernames(ArrayList<String> listOfUsernames) {
        this.listOfUsernames = listOfUsernames;
    }

    public ArrayList<Update> getListOfUpdates() {
        return listOfUpdates;
    }

    public void setListOfUpdates(ArrayList<Update> listOfUpdates) {
        this.listOfUpdates = listOfUpdates;
    }
}
