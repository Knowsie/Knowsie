package cs499.knowsie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;

import net.londatiga.android.instagram.Instagram;
import net.londatiga.android.instagram.InstagramSession;
import net.londatiga.android.instagram.InstagramUser;


public class LoginActivity extends Activity {
    private static final String TAG = "LoginActivity";
    private Instagram instagram;
    private InstagramSession instagramSession;
    private Button twitLoginButton;
    private Button instaLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        instagram = new Instagram(this,
                                  "d1be3596b8e4414995c16a113ee9b31a",
                                  "14ffb076a1034f6ca6013aba8ce4ca1e",
                                  "https://knowsie.github.io");
        instagramSession = instagram.getSession();

        startMainActivity();

        setContentView(R.layout.activity_login);

        twitLoginButton = (Button) findViewById(R.id.twit_login_button);
        twitLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWithTwitter();
            }
        });

        instaLoginButton = (Button) findViewById(R.id.inst_login_button);
        instaLoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                instagram.authorize(new Instagram.InstagramAuthListener() {
                    @Override
                    public void onSuccess(InstagramUser user) {
                        Log.d(TAG, "Logged in with Instagram");
                        instaLoginButton.setBackgroundColor(getResources().getColor(R.color.mainGray));
                        startMainActivity();
                    }

                    @Override
                    public void onError(String error) {

                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });
    }

    public void loginWithTwitter() {
        ParseTwitterUtils.logIn(this, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                if (user == null) {
                    Log.d(TAG, "Uh oh. The user cancelled the Twitter login.");
                } else if (user.isNew()) {
                    Log.d(TAG, "User signed up and logged in through Twitter!");
                    twitLoginButton.setBackgroundColor(getResources().getColor(R.color.mainGray));
                    startMainActivity();
                } else {
                    Log.d(TAG, "User logged in through Twitter!");
                    twitLoginButton.setBackgroundColor(getResources().getColor(R.color.mainGray));
                    startMainActivity();
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

    public void startMainActivity() {
        Log.d(TAG, "Logged into both Twitter and Instagram");
        if (instagramSession.isActive() && ParseUser.getCurrentUser() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("instagramAccessToken", instagramSession.getAccessToken());
            startActivity(intent);
            finish();
        }
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
