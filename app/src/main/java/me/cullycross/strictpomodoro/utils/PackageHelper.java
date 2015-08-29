package me.cullycross.strictpomodoro.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by: cullycross
 * Date: 8/27/15
 * For my shining stars!
 */
public class PackageHelper {

    private final Context mContext;

    private final PackageManager mPackageManager;

    private final ActivityManager mActivityManager;

    public PackageHelper(Context context) {
        mContext = context;
        mPackageManager = context.getPackageManager();
        mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
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

    @Nullable
    public synchronized String[] getRunningPackages() {
        String[] activePackages;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            activePackages = getActivePackages();
        } else {
            activePackages = getActivePackagesCompat();
        }
        return activePackages;
    }

    @SuppressWarnings("deprecation")
    private String[] getActivePackagesCompat() {
        final List<ActivityManager.RunningTaskInfo> taskInfo = mActivityManager.getRunningTasks(1);
        final ComponentName componentName = taskInfo.get(0).topActivity;
        final String[] activePackages = new String[1];
        activePackages[0] = componentName.getPackageName();
        return activePackages;
    }

    private String[] getActivePackages() {
        final Set<String> activePackages = new HashSet<>();
        final List<ActivityManager.RunningAppProcessInfo> processInfos = mActivityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                activePackages.addAll(Arrays.asList(processInfo.pkgList));
            }
        }
        return activePackages.toArray(new String[activePackages.size()]);
    }
}
