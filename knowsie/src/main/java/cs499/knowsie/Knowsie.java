package cs499.knowsie;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseTwitterUtils;

import cs499.knowsie.data.Group;

public class Knowsie extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Group.class);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this,
                         "SwPZ3zBLSW9FxLOw3sccKI7MPplfUhTPg4zmOzQf",
                         "VlqXfYuv3BrPrnypGhBKvEcFN5jFIt6cG0uRqKyN");
        ParseTwitterUtils.initialize("YrWhtaKPpL9rgYCZI0ykP99Kl",
                                     "Yvzr7n3qSOQ55dYEMsuzlXSpCRnfBZdhhO1dexJuv7AzaTnPkd");


        Log.i("Application", "Initialized!");
    }

}