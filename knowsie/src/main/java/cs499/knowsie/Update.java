package cs499.knowsie;

/**
 * Created by eric on 4/21/2015.
 */
public class Update {
    private String userName;
    private String userHandle;
    private String textContent;

    public Update(String userName, String userHandle, String textContent) {
        this.userName = userName;
        this.userHandle = userHandle;
        this.textContent = textContent;
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
}
