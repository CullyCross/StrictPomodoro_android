package me.cullycross.strictpomodoro.content;

import android.text.TextUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by: cullycross
 * Date: 8/27/15
 * For my shining stars!
 */

@Table(name = "Rules")
public class Rule extends Model {

    @Column(name = "Name")
    private String mName;

    @Column(name = "Running")
    private boolean mRunning;

    @Column(name = "Packages")
    private Set<String> mPackages;

    public Rule() {
        super();
        mPackages = new HashSet<>();
        mRunning = false;
    }

    public String getName() {
        return mName;
    }

    public Rule setName(String name) {
        mName = name;
        return this;
    }

    public boolean isRunning() {
        return mRunning;
    }

    public Rule setRunning(boolean running) {
        mRunning = running;
        return this;
    }

    public Set<String> getPackages() {
        return mPackages;
    }

    public Rule setPackages(Set<String> packages) {
        mPackages = packages;
        return this;
    }

    public Rule addPackage(String s) {
        mPackages.add(s);
        return this;
    }

    public Rule addPackages(Collection<? extends String> collection) {
        mPackages.addAll(collection);
        return this;
    }

    public Rule removePackage(String o) {
        mPackages.remove(o);
        return this;
    }

    public Rule removePackages(Collection<String> collection) {
        mPackages.removeAll(collection);
        return this;
    }

    @Override
    public String toString() {
        return "Name: " + mName + " (" + mRunning + "), Packages:\n" +
                TextUtils.join(", ", mPackages);
    }
}
