package me.cullycross.strictpomodoro.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.List;

/**
 * Created by: cullycross
 * Date: 8/27/15
 * For my shining stars!
 */
public class PackageHelper {

    private Context mContext;

    private PackageManager mPackageManager;

    public PackageHelper(Context context) {
        mContext = context;
        mPackageManager = context.getPackageManager();
    }

    public List<ApplicationInfo> getInstalledPackages() {
        return mPackageManager.getInstalledApplications(0);
    }

    public Drawable getApplicationIcon(ApplicationInfo applicationInfo) {
        return mPackageManager.getApplicationIcon(applicationInfo);
    }

    public Drawable getApplicationIcon(String packageName) throws PackageManager.NameNotFoundException {
        return mPackageManager.getApplicationIcon(packageName);
    }

    public PackageManager getPackageManager() {
        return mPackageManager;
    }
}
