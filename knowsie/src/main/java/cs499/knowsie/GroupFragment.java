package cs499.knowsie;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cs499.knowsie.adapters.InfiniteScrollListener;
import cs499.knowsie.adapters.UpdateListAdapter;
import cs499.knowsie.data.InstagramPosts;
import cs499.knowsie.data.InstagramUser;
import cs499.knowsie.data.Tweet;
import cs499.knowsie.data.Update;
import cs499.knowsie.services.InstagramApi;
import cs499.knowsie.services.TwitterApi;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GroupFragment extends ListFragment {

    private UpdateListAdapter updateListAdapter;
    private List<Update> updateList;
    private String[] twitterUsers;
    private String[] instaUsers;
    private String twitterAccessToken;
    private String instaAccessToken;
    private int count = 5;
    private long tweetMaxID;
    private String instaMaxID;
    private RequestInterceptor requestInterceptor;
    private RestAdapter twitterRestAdapter;
    private RestAdapter instaRestAdapter;
    private InstagramApi instagramService;
    private TwitterApi twitterService;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        updateList = new ArrayList<>();
        Bundle args = getArguments();
        if (args != null) {
            twitterUsers = getArguments().getStringArray("twitterUsers");
            instaUsers = getArguments().getStringArray("instagramUsers");
            twitterAccessToken = getArguments().getString("twitterAccessToken");
            instaAccessToken = getArguments().getString("instagramAccessToken");
            loadTwitter();
            loadInstaPosts();
        }

        updateListAdapter = new UpdateListAdapter(view.getContext(), updateList);
        setListAdapter(updateListAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnScrollListener(new InfiniteScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadMoreTweets(tweetMaxID - 1);
                loadMoreInstaPosts(instaMaxID);
            }
        });
    }

    public void loadTwitter() {
        requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Authorization", "Bearer " + twitterAccessToken);
            }
        };

        twitterRestAdapter = new RestAdapter.Builder()
                .setEndpoint(TwitterApi.baseURL)
                .setRequestInterceptor(requestInterceptor)
                .build();

        twitterService = twitterRestAdapter.create(TwitterApi.class);

        twitterService.getUserTimeline(twitterUsers[0], count, new Callback<List<Tweet>>() {
            @Override
            public void success(List<Tweet> tweets, Response response) {
                updateList.addAll(tweets);
                updateListAdapter.notifyDataSetChanged();

                tweetMaxID = updateList.get(updateList.size() - 1).getID();
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    public void loadMoreTweets(long id) {
        if (twitterService == null) {
            return;
        }

        Log.d("GroupFragment", "Loading more");
        twitterService.getUserTimeline(twitterUsers[0], count, id, new Callback<List<Tweet>>() {
            @Override
            public void success(List<Tweet> tweets, Response response) {
                updateList.addAll(tweets);
                updateListAdapter.notifyDataSetChanged();

                if (!tweets.isEmpty()) {
                    tweetMaxID = tweets.get(tweets.size() - 1).getID();
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    public void loadInstaPosts() {
        instaRestAdapter = new RestAdapter.Builder()
                .setEndpoint(InstagramApi.baseURL)
                .build();

        instagramService = instaRestAdapter.create(InstagramApi.class);

        instagramService.getUser(instaUsers[0], 1, instaAccessToken, new Callback<InstagramUser>() {
            @Override
            public void success(final InstagramUser instagramUser, Response response) {
                instagramService.getUserFeed(instagramUser.getID(), 5, instaAccessToken,
                                             new Callback<InstagramPosts>() {
                                                 @Override
                                                 public void success(InstagramPosts instagramPosts,
                                                                     Response response) {
                                                     instagramPosts.setAllFullNames(instagramUser.getFullName());
                                                     updateList.addAll(instagramPosts.getData());
                                                     updateListAdapter.notifyDataSetChanged();

                                                     if (instagramPosts.getLastID() != null) {
                                                         instaMaxID = instagramPosts.getLastID();
                                                     }
                                                 }

                                                 @Override
                                                 public void failure(RetrofitError error) {
                                                     Log.d("GroupFragment", error.getMessage());

                                                 }
                                             });
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("GroupFragment", error.getMessage());
            }
        });
    }

    public void loadMoreInstaPosts(String id) {
        if (instaMaxID == null) {
            return;
        }
        instagramService.getUser(instaUsers[0], 1, instaAccessToken, new Callback<InstagramUser>() {
            @Override
            public void success(final InstagramUser instagramUser, Response response) {
                instagramService.getUserFeed(instagramUser.getID(), 5, instaMaxID, instaAccessToken,
                                             new Callback<InstagramPosts>() {
                                                 @Override
                                                 public void success(InstagramPosts instagramPosts,
                                                                     Response response) {
                                                     instagramPosts.setAllFullNames(instagramUser.getFullName());
                                                     updateList.addAll(instagramPosts.getData());
                                                     updateListAdapter.notifyDataSetChanged();

                                                     if (instagramPosts.getLastID() != null) {
                                                         instaMaxID = instagramPosts.getLastID();
                                                     }
                                                 }

                                                 @Override
                                                 public void failure(RetrofitError error) {
                                                     Log.d("GroupFragment", error.getMessage());

                                                 }
                                             });
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("GroupFragment", error.getMessage());
            }
        });
    }
}
