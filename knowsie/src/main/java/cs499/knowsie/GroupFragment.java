package cs499.knowsie;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cs499.knowsie.adapters.UpdateListAdapter;
import cs499.knowsie.data.Update;
import cs499.knowsie.data.instagram.Envelope;
import cs499.knowsie.data.instagram.InstagramUser;
import cs499.knowsie.data.twitter.Tweet;
import cs499.knowsie.listeners.InfiniteScrollListener;
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
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        updateList = new ArrayList<>();
        Bundle args = getArguments();
        if (args != null) {
            twitterUsers = getArguments().getStringArray("twitterUsers");
            instaUsers = getArguments().getStringArray("instagramUsers");
            twitterAccessToken = getArguments().getString("twitterAccessToken");
            instaAccessToken = getArguments().getString("instagramAccessToken");
            initAdaptersAndServices();
            loadTweets();
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

    public void initAdaptersAndServices() {
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

        instaRestAdapter = new RestAdapter.Builder()
                .setEndpoint(InstagramApi.baseURL)
                .build();
        instagramService = instaRestAdapter.create(InstagramApi.class);
    }

    public void loadTweets() {
        if (twitterUsers[0].equals("")) {
            return;
        }
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
        if (twitterUsers[0].equals("")) {
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
        if (instaUsers[0].equals("")) {
            return;
        }
        instagramService.getUser(instaUsers[0], 1, instaAccessToken, new Callback<InstagramUser>() {
            @Override
            public void success(final InstagramUser instagramUser, Response response) {
                instagramService.getUserFeed(instagramUser.getID(), 5, instaAccessToken,
                                             new Callback<Envelope>() {
                                                 @Override
                                                 public void success(Envelope envelope,
                                                                     Response response) {
                                                     envelope.setAllFullNames(instagramUser.getFullName());
                                                     updateList.addAll(envelope.getData());
                                                     updateListAdapter.notifyDataSetChanged();

                                                     if (envelope.getLastID() != null) {
                                                         instaMaxID = envelope.getLastID();
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

    public void loadMoreInstaPosts(final String id) {
        if (id == null && instaUsers[0].equals("")) {
            return;
        }
        instagramService.getUser(instaUsers[0], 1, instaAccessToken, new Callback<InstagramUser>() {
            @Override
            public void success(final InstagramUser instagramUser, Response response) {
                instagramService.getUserFeed(instagramUser.getID(), 5, id, instaAccessToken,
                                             new Callback<Envelope>() {
                                                 @Override
                                                 public void success(Envelope envelope,
                                                                     Response response) {
                                                     envelope.setAllFullNames(instagramUser.getFullName());
                                                     updateList.addAll(envelope.getData());
                                                     updateListAdapter.notifyDataSetChanged();

                                                     instaMaxID = envelope.getLastID();
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

    public void refresh() {
        updateList.clear();
        loadTweets();
        loadInstaPosts();
        updateListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_group, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_refresh:
                refresh();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
