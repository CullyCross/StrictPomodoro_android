package me.cullycross.strictpomodoro.content;

import android.text.TextUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by: cullycross
 * Date: 8/27/15
 * For my shining stars!
 */

@Table(name = "Rules")
public class Rule extends Model {

    @Column(name = "Name")
    private String mName;

    @Column(name = "Packages")
    private List<String> mPackages;

    public Rule() {
        super();
        mPackages = new ArrayList<String>();
    }

    public String getName() {
        return mName;
    }

    public Rule setName(String name) {
        mName = name;
        return this;
    }

    public List<String> getPackages() {
        return mPackages;
    }

    public Rule setPackages(List<String> packages) {
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

    public Rule removePackage(int i) {
        mPackages.remove(i);
        return this;
    }

    public Rule removePackages(Collection<?> collection) {
        mPackages.removeAll(collection);
        return this;
    }

    @Override
    public String toString() {
        return "Name: " + mName + ", Packages:\n" +
                TextUtils.join(", ", mPackages);
    }
}
