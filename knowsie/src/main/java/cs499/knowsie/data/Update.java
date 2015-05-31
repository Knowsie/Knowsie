package cs499.knowsie.data;

import java.util.Date;

public class Update {
    private String userName;
    private String screenName;
    private String text;
    private Source source;
    private Date date;
    private long id;

    public Update() {
    }

    public Update(Source source) {
        this.source = source;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public enum Source {
        TWITTER,
        INSTAGRAM
    }
}
