package cs499.knowsie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import net.londatiga.android.instagram.InstagramSession;

import java.util.List;

import cs499.knowsie.adapters.GroupListAdapter;
import cs499.knowsie.data.AuthToken;
import cs499.knowsie.data.Group;
import cs499.knowsie.services.TwitterApi;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedString;

public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity";
    private static final int ADD_GROUP = 1;
    private ParseUser user;
    private List<Group> groups;
    private FloatingActionButton fabButton;
    private DrawerLayout drawerLayout;
    private GroupListAdapter groupListAdapter;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private ListView drawerListView;
    private ViewGroup drawerListHeader;
    private AuthToken twitterAuthToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        user = ParseUser.getCurrentUser();

        initToolbar();
        queryGroups();
        initFabButton();
    }

    public void initFabButton() {
        fabButton = (FloatingActionButton) findViewById(R.id.fab_add);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGroup();
            }
        });
    }

    /**
     * Initializes a Toolbar to replace the ActionBar.
     */
    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_drawer);
        setSupportActionBar(toolbar);
        Log.d(TAG, "setSupportActionBar");
    }

    public void initNavDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.mainBlue));

        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        );

        // Open nav drawer when nav icon is tapped
        drawerLayout.setDrawerListener(drawerToggle);
        Log.d(TAG, "setDrawerListener");
    }

    public void initNavDrawerList() {
        groupListAdapter = new GroupListAdapter(this, groups);

        drawerListView = (ListView) findViewById(R.id.nav_drawer);

        // Add non-selectable header to ListView.
        drawerListHeader = (ViewGroup) getLayoutInflater().inflate(R.layout.listview_header,
                                                                   drawerListView,
                                                                   false);
        drawerListView.addHeaderView(drawerListHeader, null, false);
        drawerListView.setHeaderDividersEnabled(true);
        drawerListView.setAdapter(groupListAdapter);
        drawerListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        groupListAdapter.notifyDataSetChanged();
        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
    }

    /**
     * Query the groups associated with the current user
     * to load in the navigation drawer. The first item is selected when
     * opening the app.
     */
    public void queryGroups() {
        Log.d(TAG, "Retrieving user's list of groups");

        ParseQuery<Group> groupsParseQuery = ParseQuery.getQuery(Group.class);
        groupsParseQuery.whereEqualTo("user", user);

        groupsParseQuery.findInBackground(new FindCallback<Group>() {
            @Override
            public void done(List<Group> groupsList, ParseException e) {
                groups = groupsList;
                initNavDrawer();
                initNavDrawerList();
                authorizeTwitter();
            }
        });
    }

    public void addGroup() {
        Intent i = new Intent(this, AddGroupActivity.class);
        startActivityForResult(i, ADD_GROUP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_GROUP) {
            if (resultCode == RESULT_OK) {
                Group group = new Group(user, data.getStringExtra("groupName"));
                if (!data.getStringExtra("twitterUser").equals("")) {
                    group.addTwitterUser(data.getStringExtra("twitterUser"));
                }
                if (!data.getStringExtra("instagramUser").equals("")) {
                    group.addInstagramUser(data.getStringExtra("instagramUser"));
                }

                groups.add(group);

                group.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        groupListAdapter.notifyDataSetChanged();
                        selectItem(groupListAdapter.getCount());
                    }
                });
            }
        }
    }

    private void selectItem(int position) {
        if (groupListAdapter.isEmpty()) {
            return;
        }

        findViewById(R.id.main_empty).setVisibility(View.INVISIBLE);

        // This method is used to account for the header view being counted.
        Group g = (Group) drawerListView.getItemAtPosition(position);
        List<String> twitterUsers = g.getTwitterUsers();
        List<String> instagramUsers = g.getInstagramUsers();

        GroupFragment fragment = new GroupFragment();

        if (twitterUsers != null && instagramUsers != null) {
            Bundle bundle;
            bundle = createTwitterBundle(twitterUsers);
            bundle.putAll(createInstagramBundle(instagramUsers));
            fragment.setArguments(bundle);
        } else if (twitterUsers != null) {
            fragment.setArguments(createTwitterBundle(twitterUsers));
        } else if (instagramUsers != null) {
            fragment.setArguments(createInstagramBundle(instagramUsers));
        }

        this.getFragmentManager().beginTransaction()
            .replace(R.id.group_fragment, fragment)
            .commit();

        drawerListView.setItemChecked(position, true);
        Log.d(TAG, "Position: " + position + " , " + groups.toString());

        toolbar.setSubtitle(g.getGroupName());

        Log.d(TAG, "Starting " + toolbar.getSubtitle() + " Fragment");
        drawerLayout.closeDrawer(drawerListView);
    }

    public Bundle createTwitterBundle(List<String> twitterUsers) {
        Bundle bundle = new Bundle();
        bundle.putString("twitterAccessToken", twitterAuthToken.accessToken);
        bundle.putStringArray("twitterUsers",
                              twitterUsers.toArray(new String[twitterUsers.size()]));
        return bundle;
    }

    public Bundle createInstagramBundle(List<String> instagramUsers) {
        Bundle bundle = new Bundle();
        bundle.putString("instagramAccessToken",
                         getIntent().getStringExtra("instagramAccessToken"));
        bundle.putStringArray("instagramUsers",
                              instagramUsers.toArray(new String[instagramUsers.size()]));
        return bundle;
    }

    public void logOut() {
        Log.d(TAG, "logout()");
        ParseUser.logOut();

        InstagramSession instagram = new InstagramSession(this);
        instagram.reset();

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void authorizeTwitter() {
        // Generate header for token
        String consumerKey = ParseTwitterUtils.getTwitter().getConsumerKey();
        String consumerSecret = ParseTwitterUtils.getTwitter().getConsumerSecret();
        String bearerToken = consumerKey + ":" + consumerSecret;
        String base64Token = Base64.encodeToString(bearerToken.getBytes(), Base64.NO_WRAP);

        TypedString requestBody = new TypedString("grant_type=client_credentials");

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(TwitterApi.baseURL)
                .build();

        TwitterApi service = restAdapter.create(TwitterApi.class);

        service.authorize("Basic " + base64Token,
                          requestBody,
                          new Callback<AuthToken>() {
                              @Override
                              public void success(AuthToken authToken,
                                                  Response response) {
                                  twitterAuthToken = authToken;
                                  // Select the first item by default when opening the app, ignoring header view.
                                  selectItem(drawerListView.getHeaderViewsCount());
                                  Log.d(TAG, "Successfully retrieved auth token");
                              }

                              @Override
                              public void failure(RetrofitError error) {
                                  Log.d(TAG, "Failed: " + error.getMessage());
                              }
                          });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_logout:
                logOut();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "Exiting");
        finish();
    }
}
