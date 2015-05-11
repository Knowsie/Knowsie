package cs499.knowsie.services;

import java.util.List;

import cs499.knowsie.data.Tweet;
import retrofit.http.GET;
import retrofit.http.Query;

public interface TwitterApi {

    @GET("/statuses/user_timeline.json")
    List<Tweet> getUserTimelineByScreenName(@Query("screen_name") String screenName,
                                            @Query("max_id") int maxID,
                                            @Query("count") int count);
}
