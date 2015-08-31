package me.cullycross.strictpomodoro.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.activeandroid.query.Select;

import me.cullycross.strictpomodoro.content.Rule;
import me.cullycross.strictpomodoro.utils.Pomodoro;

public class PomodoroService extends Service implements Pomodoro.Pomorunnable.OnLazyUserFindListener {

    private static final String TAG = PomodoroService.class.getCanonicalName();
    private Handler mHandler;

    private Pomodoro.Pomorunnable mPomorunnable;
    public void onCreate() {
        super.onCreate();

        mHandler = new Handler();
        Rule rule = new Select().from(Rule.class).where("Running=?", true).executeSingle();

        mPomorunnable = new Pomodoro.Pomorunnable(rule, mHandler, this);
        mPomorunnable.setListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mHandler.post(mPomorunnable);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLazyUserFind(String packageName) {

        Log.d(TAG, "Package name: " + packageName);
        // todo(CullyCross): show screen notification and ads
    }
}
