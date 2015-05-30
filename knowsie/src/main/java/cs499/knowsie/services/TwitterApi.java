package cs499.knowsie.services;

import cs499.knowsie.data.AuthToken;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.mime.TypedString;

public interface TwitterApi {

    String baseURL = "https://api.twitter.com";

    @Headers({"Content-Type: application/x-www-form-urlencoded;charset=UTF-8"})
    @POST("/oauth2/token")
    void authorize(@Header("Authorization") String authorization,
                   @Body TypedString grantType,
                   Callback<AuthToken> cb);
}
