package cs499.knowsie.services;

import cs499.knowsie.data.InstagramPosts;
import cs499.knowsie.data.InstagramUser;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface InstagramApi {
    String baseURL = "https://api.instagram.com/v1";

    @GET("/users/{user-id}/media/recent/")
    void getUserFeed(@Path("user-id") String id,
                     @Query("count") int count,
                     @Query("access_token") String accessToken,
                     Callback<InstagramPosts> cb);

    @GET("/users/{user-id}/media/recent/")
    void getUserFeed(@Path("user-id") String id,
                     @Query("count") int count,
                     @Query("max_id") String maxID,
                     @Query("access_token") String accessToken,
                     Callback<InstagramPosts> cb);

    @GET("/users/search")
    void getUser(@Query("q") String query,
                 @Query("count") int count,
                 @Query("access_token") String accessToken,
                 Callback<InstagramUser> cb);
}
