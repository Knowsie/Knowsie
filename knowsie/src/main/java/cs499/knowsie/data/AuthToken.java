package cs499.knowsie.data;

import com.google.gson.annotations.SerializedName;

public class AuthToken {
    @SerializedName("token_type")
    public String tokenType;

    @SerializedName("access_token")
    public String accessToken;
}
