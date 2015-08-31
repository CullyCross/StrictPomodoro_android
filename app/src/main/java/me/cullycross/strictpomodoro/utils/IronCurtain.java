package me.cullycross.strictpomodoro.utils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import me.cullycross.strictpomodoro.R;

/**
 * Created by: cullycross
 * Date: 8/31/15
 * For my shining stars!
 */
public class IronCurtain implements View.OnKeyListener {

    private final Context mContext;
    private final WindowManager mWindowManager;
    private final LayoutInflater mInflater;
    private final View mCurtain;

    private IronCurtainListener mListener;

    private boolean mShown = false;

    public IronCurtain(Context context) {
        mContext = context;
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCurtain = mInflater.inflate(R.layout.curtain_layout, null);
    }

    public void show() {

        mCurtain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });

        WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 0, 0,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                PixelFormat.RGBA_8888);

        mCurtain.setOnKeyListener(this);

        // finally, add the view to window
        mWindowManager.addView(mCurtain, mLayoutParams);

        mShown = true;

        if (mListener != null) {
            mListener.onShow();
        }
    }

    public void hide() {
        if (mCurtain != null) {
            mWindowManager.removeView(mCurtain);
            mShown = false;

            if (mListener != null) {
                mListener.onHide();
            }
        }
    }

    public boolean isShown() {
        return mShown;
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            hide();
            return true;
        }
        return false;
    }

    public void setListener(IronCurtainListener listener) {
        mListener = listener;
    }

    public interface IronCurtainListener {
        void onShow();
        void onHide();
    }
}
