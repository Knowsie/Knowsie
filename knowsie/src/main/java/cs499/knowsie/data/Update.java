package cs499.knowsie.data;

public class Update {
    private String primaryName;
    private String secondaryName;
    private String text;
    private Source source;

    public Update() {
    }

    public String getPrimaryName() {
        return primaryName;
    }

    public void setPrimaryName(String primaryName) {
        this.primaryName = primaryName;
    }

    public String getSecondaryName() {
        return secondaryName;
    }

    public String getText() {
        return text;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getMediaURL() {
        return "";
    }

    public long getID() {
        return 0;
    }

    public enum Source {
        TWITTER,
        INSTAGRAM
    }
}
