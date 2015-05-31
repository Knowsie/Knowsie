package cs499.knowsie.services;

import java.util.List;

import cs499.knowsie.data.AuthToken;
import cs499.knowsie.data.Tweet;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.mime.TypedString;

public interface TwitterApi {

    String baseURL = "https://api.twitter.com";

    @Headers({"Content-Type: application/x-www-form-urlencoded;charset=UTF-8"})
    @POST("/oauth2/token")
    void authorize(@Header("Authorization") String authorization,
                   @Body TypedString grantType,
                   Callback<AuthToken> cb);

    @GET("/1.1/statuses/user_timeline.json")
    void getUserTimeline(@Query("screen_name") String screenName,
                         @Query("count") int count,
                         @Query("max_id") long maxID,
                         Callback<List<Tweet>> cb);

    @GET("/1.1/statuses/user_timeline.json")
    void getUserTimeline(@Query("screen_name") String screenName,
                         @Query("count") int count,
                         Callback<List<Tweet>> cb);
}
