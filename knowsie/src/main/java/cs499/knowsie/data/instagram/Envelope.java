package cs499.knowsie.data.instagram;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Envelope {
    private Pagination pagination;
    private List<Post> data;

    public Envelope() {
    }

    public List<Post> getData() {
        return data;
    }

    public void setAllFullNames(String fullName) {
        for (Post p : data) {
            p.setPrimaryName(fullName);
        }
    }

    public String getLastID() {
        return pagination.nextMaxID;
    }

    private class Pagination {
        @SerializedName("next_max_id")
        String nextMaxID;
    }


}
