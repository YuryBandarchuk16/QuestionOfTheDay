package com.bandarchuk.yury.questionoftheday;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "QOD/MainActivity";

    private Map<Integer, Class<? extends Fragment>> fragments;
    private Map<Integer, Fragment> createdFragments = new HashMap<>();

    {
        fragments = new HashMap<>();
        fragments.put(R.id.navigation_home, QuestionOfTheDayFragment.class);
        fragments.put(R.id.navigation_history, QuestionsHistoryFragment.class);
        fragments.put(R.id.navigation_profile, UserProfileFragment.class);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            Fragment currentFragment = createdFragments.get(itemId);
            if (currentFragment != null) {
                replaceWithFragment(currentFragment);
                return true;
            }
            Class<? extends Fragment> currentTabFragmentClass = fragments.get(itemId);
            if (currentTabFragmentClass == null) {
                return false;
            }
            currentFragment = createFragmentWithArgs(currentTabFragmentClass);
            createdFragments.put(itemId, currentFragment);
            replaceWithFragment(currentFragment);
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState != null) {
            return;
        }

        if (findViewById(R.id.fragment_container) != null) {
            Fragment homeFragment = createFragmentWithArgs(QuestionOfTheDayFragment.class, getIntent().getExtras());
            addFragment(homeFragment);
            createdFragments.put(R.id.navigation_home, homeFragment);
        }

    }

    private void replaceWithFragment(Fragment fragment) {
        replaceWithFragment(R.id.fragment_container, fragment);
    }

    private void replaceWithFragment(@IdRes int containerViewId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(containerViewId, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void addFragment(Fragment fragment) {
        addFragment(R.id.fragment_container, fragment);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void addFragment(@IdRes int containerViewId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(containerViewId, fragment)
                .commit();
    }

    private <T extends Fragment> Fragment createFragmentWithArgs(Class<T> instanceClass) {
        return createFragmentWithArgs(instanceClass, getIntent().getExtras());
    }

    private <T extends Fragment> Fragment createFragmentWithArgs(Class<T> instanceClass, Bundle args) {
        T instance = null;
        try {
            instance = instanceClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            Log.e(LOG_TAG, "Error while creating fragment: ", e);
        }
        if (instance == null) {
            Log.e(LOG_TAG, "Error, created fragment is null");
        }
        assert instance != null;
        instance.setArguments(args);
        return instance;
    }

}
