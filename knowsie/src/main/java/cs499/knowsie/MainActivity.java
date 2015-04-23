package cs499.knowsie;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity";
    private ArrayList<Update> updates;
    private UpdateListAdapter updateListAdapter;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        initToolbar();
        initNavDrawer();
        initUpdateListView();
    }

    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_drawer);
        setSupportActionBar(toolbar);
        Log.d(TAG, "Toolbar set");
    }

    public void initNavDrawer() {
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
        Log.d(TAG, "setDrawerListener()");
    }

    public void initUpdateListView() {
        String msg = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";

        Log.d(TAG, "Filling list of updates.");
        updates = new ArrayList<Update>();
        for (int i = 0; i < 15; i++) {
            updates.add(new Update("MonteCristo", "@ggCMonteCristo", msg));
        }

        ListView listView = (ListView) findViewById(R.id.update_list);

        Log.d(TAG, "Initializing and setting adapter");
        updateListAdapter = new UpdateListAdapter(this, updates);
        listView.setAdapter(updateListAdapter);
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
