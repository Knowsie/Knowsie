package cs499.knowsie.data;

public class Update {

    public static final String TWITTER = "Twitter";
    public static final String INSTAGRAM = "Instagram";
    private String userName;
    private String userHandle;
    private String textContent;
    private String source;

    public Update(String userName, String userHandle, String textContent, String source) {
        this.userName = userName;
        this.userHandle = userHandle;
        this.textContent = textContent;
        this.source = source;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHandle() {
        return userHandle;
    }

    public void setUserHandle(String userHandle) {
        this.userHandle = userHandle;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
