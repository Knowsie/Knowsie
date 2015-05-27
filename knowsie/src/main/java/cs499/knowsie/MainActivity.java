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

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;

import java.util.List;

import cs499.knowsie.adapters.GroupListAdapter;
import cs499.knowsie.data.Group;
import cs499.knowsie.services.TwitterApi;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedString;

public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity";
    private ParseUser user;
    private List<Group> groups;
    private DrawerLayout drawerLayout;
    private GroupListAdapter groupListAdapter;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private ListView drawerListView;
    private ViewGroup drawerListHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        user = ParseUser.getCurrentUser();

        initToolbar();
        queryGroups();
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

                // Select the first item by default when opening the app, ignoring header view.
                selectItem(drawerListView.getHeaderViewsCount());
            }
        });
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

        groupListAdapter = new GroupListAdapter(this, groups);

        drawerListView = (ListView) findViewById(R.id.nav_drawer);

        // Add non-selectable header to ListView.
        Log.d(TAG, "addHeaderView");
        drawerListHeader = (ViewGroup) getLayoutInflater().inflate(R.layout.listview_header,
                                                                   drawerListView,
                                                                   false);

        drawerListView.addHeaderView(drawerListHeader, null, false);
        drawerListView.setHeaderDividersEnabled(true);

        Log.d(TAG, "drawerListView setAdapter");
        drawerListView.setAdapter(groupListAdapter);
        groupListAdapter.notifyDataSetChanged();
        drawerListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        Log.d(TAG, "drawerListView.setOnItemClickListener");
        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
    }

    private void selectItem(int position) {
        GroupFragment fragment = new GroupFragment();

        this.getFragmentManager().beginTransaction()
            .replace(R.id.group_fragment, fragment)
            .commit();

        drawerListView.setItemChecked(position, true);
        Log.d(TAG, "Position: " + position + " , " + groups.toString());

        // This method is used to account for the header view being counted.
        Group g = (Group) drawerListView.getItemAtPosition(position);
        setTitle(g.getGroupName());

        Log.d(TAG, "Starting " + getTitle() + " Fragment");
        drawerLayout.closeDrawer(drawerListView);
    }

    public void logOut() {
        Log.d(TAG, "logout()");
        ParseUser.logOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void testTwitterApi() {
        String consumerKey = ParseTwitterUtils.getTwitter().getConsumerKey();
        String consumerSecret = ParseTwitterUtils.getTwitter().getConsumerSecret();
        String bearerToken = consumerKey + ":" + consumerSecret;
        TypedString typedString = new TypedString("grant_type=client_credentials");

        byte[] encodedToken = bearerToken.getBytes();
        String base64Token = Base64.encodeToString(encodedToken, Base64.NO_WRAP);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(TwitterApi.baseURL)
                .build();

        TwitterApi service = restAdapter.create(TwitterApi.class);

        service.authorize("Basic " + base64Token,
                          String.valueOf(typedString.length()),
                          typedString,
                          new Callback<Response>() {

                              @Override
                              public void success(Response response,
                                                  Response response2) {
                                  Log.d(TAG, response.getStatus() + "");
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
