package cs499.knowsie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.util.Log;

import com.parse.ParseTwitterUtils;
import com.parse.ParseException;
import com.parse.LogInCallback;
import com.parse.ParseUser;


public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWithTwitter();
            }
        });
    }

    public void loginWithTwitter() {
        ParseTwitterUtils.logIn(this, new LogInCallback()
        {
            @Override
            public void done(ParseUser user,ParseException err)
            {
                if (user == null)
                {
                    Log.d("Knowsie","Uh oh. The user cancelled the Twitter login.");
                }
                else if (user.isNew())
                {
                    Log.d("Knowsie","User signed up and logged in through Twitter!");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Log.d("Knowsie", "User logged in through Twitter!");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
