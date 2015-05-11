package cs499.knowsie.services;

import cs499.knowsie.data.TwitterData;
import retrofit.http.GET;
import retrofit.http.Query;

public interface TwitterService {
    @GET("/statuses/user_timeline.json")
    TwitterData getUserTimelineByScreenName(@Query("screen_name") String screenName,
                                            @Query("max_id") int maxID);
}
