package me.cullycross.strictpomodoro.activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.cullycross.strictpomodoro.R;
import me.cullycross.strictpomodoro.content.Rule;
import me.cullycross.strictpomodoro.fragments.PackagesListFragment;
import me.cullycross.strictpomodoro.fragments.RuleListFragment;
import me.cullycross.strictpomodoro.utils.PackageHelper;

public class MainActivity extends AppCompatActivity
        implements RuleListFragment.OnFragmentInteractionListener,
        PackagesListFragment.OnFragmentInteractionListener {

    private final static String TAG = MainActivity.class.getCanonicalName();

    @Bind(R.id.main_toolbar)
    protected Toolbar mToolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        testAddRules();

        initToolbar();
        initFragment();

        PackageHelper helper = new PackageHelper(this);

        /*for (ApplicationInfo pack:
             helper.getInstalledPackages()) {
            Log.v(TAG, pack.name + "\n" + pack.packageName + "\n" + pack.logo);
        }*/
    }

    private void testAddRules() {
        new Delete().from(Rule.class).execute();

        ActiveAndroid.beginTransaction();

        Rule rule = new Rule();
        rule.setName("rule1").
                addPackage("rule1.package1").
                addPackage("rule1.package2").
                addPackage("rule1.package3").
                addPackage("rule1.package4").
                addPackage("rule1.package5").
                save();
        rule = new Rule();
        rule.setName("rule2").
                addPackage("rule2.package1").
                addPackage("rule2.package2").
                addPackage("rule2.package3").
                save();
        rule = new Rule();
        rule.setName("rule3").
                addPackage("rule3.package1").
                addPackage("rule3.package2").
                addPackage("rule3.package3").
                addPackage("rule3.package4").
                save();
        rule = new Rule();
        rule.setName("rule4").
                addPackage("rule4.package1").
                addPackage("rule4.package2").
                addPackage("rule4.package3").
                addPackage("rule4.package4").
                addPackage("rule4.package5").
                save();
        ActiveAndroid.setTransactionSuccessful();
        ActiveAndroid.endTransaction();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settings = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settings);
        }

        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);

        //noinspection ConstantConditions
        getSupportActionBar().setTitle("");
    }

    private void initFragment() {
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(R.id.fragment_frame, RuleListFragment.newInstance())
                .commit();
    }

    @Override
    public void onItemClick(long id) {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(R.id.fragment_frame, PackagesListFragment.newInstance(((int) id)))
                .commit();
    }
}
