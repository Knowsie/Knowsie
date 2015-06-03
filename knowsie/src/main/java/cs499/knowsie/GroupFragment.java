package cs499.knowsie;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Comparator;
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
    private boolean hasTwitterUsers;
    private boolean hasInstagramUsers;
    private String twitterAccessToken;
    private String instaAccessToken;
    private int count = 3;
    private Long tweetMaxID;
    private String instaMaxID;
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
            if (twitterUsers != null) {
                hasTwitterUsers = true;
            }
            instaUsers = getArguments().getStringArray("instagramUsers");
            if (instaUsers != null) {
                hasInstagramUsers = true;
            }
            twitterAccessToken = getArguments().getString("twitterAccessToken");
            instaAccessToken = getArguments().getString("instagramAccessToken");
            initAdaptersAndServices();
            new LoadUpdatesTask().execute();
//            loadTweets();
//            loadInstaPosts();
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
                new LoadUpdatesTask().execute();
//                loadMoreTweets(--tweetMaxID);
//                loadMoreInstaPosts(instaMaxID);
            }
        });
    }

    public void initAdaptersAndServices() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Authorization", "Bearer " + twitterAccessToken);
            }
        };
        RestAdapter twitterRestAdapter = new RestAdapter.Builder()
                .setEndpoint(TwitterApi.baseURL)
                .setRequestInterceptor(requestInterceptor)
                .build();
        twitterService = twitterRestAdapter.create(TwitterApi.class);

        RestAdapter instaRestAdapter = new RestAdapter.Builder()
                .setEndpoint(InstagramApi.baseURL)
                .build();
        instagramService = instaRestAdapter.create(InstagramApi.class);
    }

    public void loadTweets() {
        if (!hasTwitterUsers) {
            return;
        }
        twitterService.getUserTimeline(twitterUsers[0], count, new Callback<List<Tweet>>() {
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

    public void loadMoreTweets(long id) {
        if (!hasTwitterUsers) {
            return;
        }

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
        if (!hasInstagramUsers) {
            return;
        }
        instagramService.getUser(instaUsers[0], 1, instaAccessToken, new Callback<InstagramUser>() {
            @Override
            public void success(final InstagramUser instagramUser, Response response) {
                String userID = instagramUser.getID();
                if (userID == null) {
                    return;
                }
                instagramService.getUserFeed(userID, count, instaAccessToken,
                                             new Callback<Envelope>() {
                                                 @Override
                                                 public void success(Envelope envelope,
                                                                     Response response) {
                                                     envelope.setAllFullNames(instagramUser.getFullName());

                                                     if (envelope.getData().isEmpty()) {
                                                         return;
                                                     }

                                                     updateList.addAll(envelope.getData());

                                                     if (envelope.getLastID() != null) {
                                                         instaMaxID = envelope.getLastID();
                                                     }

                                                     updateListAdapter.notifyDataSetChanged();
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
        if (id == null || !hasInstagramUsers) {
            return;
        }
        instagramService.getUser(instaUsers[0], 1, instaAccessToken, new Callback<InstagramUser>() {
            @Override
            public void success(final InstagramUser instagramUser, Response response) {
                String userID = instagramUser.getID();
                if (userID == null) {
                    return;
                }
                instagramService.getUserFeed(userID, count, id, instaAccessToken,
                                             new Callback<Envelope>() {
                                                 @Override
                                                 public void success(Envelope envelope,
                                                                     Response response) {
                                                     envelope.setAllFullNames(instagramUser.getFullName());

                                                     if (envelope.getData().isEmpty()) {
                                                         return;
                                                     }

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
        new LoadUpdatesTask().execute();
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
            case R.id.action_delete:
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.deleteGroup();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class LoadUpdatesTask extends AsyncTask<Void, Void, List<Update>> {

        @Override
        protected List<Update> doInBackground(Void... params) {
            List<Update> updates = new ArrayList<>();
            if (hasTwitterUsers) {
                if (tweetMaxID != null) {
                    tweetMaxID--;
                }
                List<Tweet> tweets = twitterService.getUserTimeline(twitterUsers[0],
                                                                    count,
                                                                    tweetMaxID);
                if (!tweets.isEmpty()) {
                    updates.addAll(tweets);
                    tweetMaxID = tweets.get(tweets.size() - 1).getID();
                }
            }

            if (hasInstagramUsers) {
                InstagramUser user = instagramService.getUser(instaUsers[0], 1, instaAccessToken);
                String userID = user.getID();
                if (userID != null) {
                    Envelope envelope = instagramService.getUserFeed(userID,
                                                                     count,
                                                                     instaMaxID,
                                                                     instaAccessToken);
                    if (!envelope.getData().isEmpty()) {
                        envelope.setAllFullNames(user.getFullName());
                        updates.addAll(envelope.getData());
                        instaMaxID = envelope.getLastID();
                    }
                }
            }
            Log.d("HELP", updates.size() + "");
            return updates;
        }

        @Override
        protected void onPostExecute(List<Update> updates) {
            updateList.addAll(updates);
            updateListAdapter.sort(new Comparator<Update>() {
                @Override
                public int compare(Update lhs, Update rhs) {
                    return rhs.getDate().compareTo(lhs.getDate());
                }
            });
            updateListAdapter.notifyDataSetChanged();
        }
    }
}
