package me.cullycross.strictpomodoro.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import java.util.List;

/**
 * Created by: cullycross
 * Date: 8/27/15
 * For my shining stars!
 */
public class PackageHelper {

    private Context mContext;

    public PackageHelper(Context context) {
        mContext = context;
    }

    public List<ApplicationInfo> getInstalledPackages() {
        return mContext.getPackageManager().getInstalledApplications(0);
    }
}
