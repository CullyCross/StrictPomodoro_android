package me.cullycross.strictpomodoro.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import java.util.ArrayList;
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

    public List<String> getInstalledPackages() {
        List<ApplicationInfo> listOfApps = mContext.getPackageManager().getInstalledApplications(0);
        List<String> listOfAppsStrings = new ArrayList<String>(listOfApps.size());
        for (ApplicationInfo app :
                listOfApps) {
            listOfAppsStrings.add(app.packageName);
        }
        return listOfAppsStrings;
    }
}
