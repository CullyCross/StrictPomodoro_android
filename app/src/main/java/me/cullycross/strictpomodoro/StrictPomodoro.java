package me.cullycross.strictpomodoro;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by: cullycross
 * Date: 8/27/15
 * For my shining stars!
 */
public class StrictPomodoro extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ActiveAndroid.initialize(this);
    }
}
