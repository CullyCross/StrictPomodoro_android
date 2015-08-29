package me.cullycross.strictpomodoro.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import me.cullycross.strictpomodoro.content.Rule;
import me.cullycross.strictpomodoro.utils.Pomodoro;

public class PomodoroService extends Service implements Pomodoro.Pomorunnable.OnLazyUserFindListener {

    private Handler mHandler;

    private Pomodoro.Pomorunnable mPomorunnable;
    public void onCreate() {
        super.onCreate();

        mHandler = new Handler();

        //todo(CullyCross): pass rule as argument to this service and pass it here
        mPomorunnable = new Pomodoro.Pomorunnable(new Rule(), mHandler, this);
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

        // todo(CullyCross): show screen notification and ads
    }
}
