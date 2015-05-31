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
import cs499.knowsie.data.Tweet;
import cs499.knowsie.data.Update;
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
    private String accessToken;
    private int count = 5;
    private long maxID;
    private RequestInterceptor requestInterceptor;
    private RestAdapter restAdapter;
    private TwitterApi service;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        updateList = new ArrayList<>();
        Bundle args = getArguments();
        if (args != null) {
            twitterUsers = getArguments().getStringArray("twitterUsers");
            accessToken = getArguments().getString("accessToken");
            load();
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
                loadMoreUpdates(maxID - 1);
            }
        });
    }

    public void load() {
        requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Authorization", "Bearer " + accessToken);
            }
        };

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(TwitterApi.baseURL)
                .setRequestInterceptor(requestInterceptor)
                .build();

        service = restAdapter.create(TwitterApi.class);
        service.getUserTimeline("aphromoo", count, new Callback<List<Tweet>>() {
            @Override
            public void success(List<Tweet> tweets, Response response) {
                updateList.addAll(tweets);
                updateListAdapter.notifyDataSetChanged();

                maxID = updateList.get(updateList.size() - 1).getID();
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    public void loadMoreUpdates(long id) {
        if (service == null) {
            return;
        }

        Log.d("GroupFragment", "Loading more");
        service.getUserTimeline("aphromoo", count, id, new Callback<List<Tweet>>() {
            @Override
            public void success(List<Tweet> tweets, Response response) {
                updateList.addAll(tweets);
                updateListAdapter.notifyDataSetChanged();

                maxID = updateList.get(updateList.size() - 1).getID();
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }
}
