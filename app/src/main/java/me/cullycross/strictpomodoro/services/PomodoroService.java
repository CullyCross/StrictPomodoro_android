package me.cullycross.strictpomodoro.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.activeandroid.query.Select;

import me.cullycross.strictpomodoro.content.Rule;
import me.cullycross.strictpomodoro.utils.IronCurtain;
import me.cullycross.strictpomodoro.utils.Pomodoro;

public class PomodoroService extends Service implements Pomodoro.Pomorunnable.OnLazyUserFindListener, IronCurtain.IronCurtainListener {

    private static final String TAG = PomodoroService.class.getCanonicalName();
    private Handler mHandler;

    private IronCurtain mCurtain;

    private Pomodoro.Pomorunnable mPomorunnable;


    public void onCreate() {
        super.onCreate();

        mHandler = new Handler();
        mCurtain = new IronCurtain(this);
        Rule rule = new Select().from(Rule.class).where("Running=?", true).executeSingle();

        mPomorunnable = new Pomodoro.Pomorunnable(rule, mHandler, this);
        mPomorunnable.setListener(this);
        mCurtain.setListener(this);
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
        if(!mCurtain.isShown()) {
            mCurtain.show();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mCurtain.isShown()) {
            mCurtain.hide();
        }
    }

    @Override
    public void onShow() {
        // currently do nothing
    }

    @Override
    public void onHide() {
        openHomeScreen();
    }

    private void openHomeScreen() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
