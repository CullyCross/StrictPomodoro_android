package me.cullycross.strictpomodoro.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import me.cullycross.strictpomodoro.content.Rule;
import me.cullycross.strictpomodoro.services.PomodoroService;

/**
 * Created by: cullycross
 * Date: 8/30/15
 * For my shining stars!
 *
 * todo(CullyCross): use pattern Strategy or Command to:
 *      - block black list, allow white list only
 *      - block with rest time, simple block for amount of time
 * todo(CullyCross): invent timer with 25 minutes, kinda progress bar, disable all elements that time
 * @see <a href="http://developer.android.com/reference/android/os/CountDownTimer.html">
 *          CountDownTimer class
 *      </a>
 */
public class Pomodoro {

    public static void startPomodoro(Context ctx) {
        Intent pomodoroService = new Intent(ctx, PomodoroService.class);

        ctx.startService(pomodoroService);
    }

    public static class Pomorunnable implements Runnable {

        private static final int LIVE_DIE_REPEAT = 5000;

        private final Rule mRule;
        private final Handler mHandler;
        private final Context mContext;
        private final PackageHelper mPackageHelper;

        private volatile boolean mRunning;

        private OnLazyUserFindListener mListener;

        public Pomorunnable(Rule rule, Handler handler, Context ctx) {
            mRule = rule;
            mHandler = handler;
            mContext = ctx.getApplicationContext();
            mPackageHelper = new PackageHelper(mContext);
            mRunning = true;
        }

        @Override
        public void run() {

            String[] packages = mPackageHelper.getRunningPackages();

            if (packages != null) {
                for (String pack :
                        packages) {
                    if (mRule.getPackages().contains(pack)) {
                        if (mListener != null) {
                            mListener.onLazyUserFind(pack);
                        }
                    }
                }
            }

            if (mRunning) {
                mHandler.postDelayed(this, LIVE_DIE_REPEAT);
            }
        }

        public boolean isRunning() {
            return mRunning;
        }

        public void setRunning(boolean running) {
            mRunning = running;
        }

        public synchronized void setListener(OnLazyUserFindListener listener) {
            mListener = listener;
        }

        public interface OnLazyUserFindListener {
            void onLazyUserFind(String packageName);
        }
    }
}
