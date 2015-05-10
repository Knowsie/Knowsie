package cs499.knowsie;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity";
    private ArrayList<Group> groups;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private ListView drawerListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        initToolbar();
        initNavDrawer();
        selectItem(0);
    }

    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_drawer);
        setSupportActionBar(toolbar);
        Log.d(TAG, "setSupportActionBar");
    }

    public void initNavDrawer() {
        groups = new ArrayList<Group>();
        groups.add(new Group("Family"));
        groups.add(new Group("Friends"));
        groups.add(new Group("Food"));

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.material_deep_teal_500));

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

        drawerListView = (ListView) findViewById(R.id.nav_drawer);
        Log.d(TAG, "drawerListView.setAdapter");
        drawerListView.setAdapter(new GroupListAdapter(this, groups));
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

        FragmentManager fragmentManager = this.getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.group_fragment, fragment).commit();

        drawerListView.setItemChecked(position, true);
        setTitle(groups.get(position).getGroupName());
        drawerLayout.closeDrawer(drawerListView);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
