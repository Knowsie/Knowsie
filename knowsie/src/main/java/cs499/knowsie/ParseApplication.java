package cs499.knowsie;

import android.app.Application;
import android.util.Log;
import com.parse.Parse;
import com.parse.ParseTwitterUtils;

/**
 * Created by Kelly on 5/10/2015.
 */
public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        //Added for Parse Initialization
        Parse.initialize(this, "SwPZ3zBLSW9FxLOw3sccKI7MPplfUhTPg4zmOzQf", "VlqXfYuv3BrPrnypGhBKvEcFN5jFIt6cG0uRqKyN");

        //Added for Parse and Twitter Login
        ParseTwitterUtils.initialize("YrWhtaKPpL9rgYCZI0ykP99Kl", "Yvzr7n3qSOQ55dYEMsuzlXSpCRnfBZdhhO1dexJuv7AzaTnPkd");

        Log.i("Application", "Initialized!");
    }

}