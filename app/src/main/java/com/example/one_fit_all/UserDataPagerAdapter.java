package com.example.one_fit_all;

import com.fitbit.authentication.AuthenticationManager;
import com.fitbit.authentication.Scope;
import com.example.one_fit_all.fragments.ActivitiesFragment;
import com.example.one_fit_all.fragments.DeviceFragment;
import com.example.one_fit_all.fragments.InfoFragment;
import com.example.one_fit_all.fragments.ProfileFragment;
import com.example.one_fit_all.fragments.WeightLogFragment;

import android.app.Fragment;
import android.app.FragmentManager;


import androidx.legacy.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jboggess on 10/17/16.
 */

public class UserDataPagerAdapter extends FragmentPagerAdapter {

    private final List<InfoFragment> fragments = new ArrayList<>();

    public UserDataPagerAdapter(FragmentManager fm) {
        super(fm);

        fragments.clear();
        if (containsScope(Scope.profile)) {
            fragments.add(new ProfileFragment());
        }
        if (containsScope(Scope.settings)) {
            fragments.add(new DeviceFragment());
        }
        if (containsScope(Scope.activity)) {
            fragments.add(new ActivitiesFragment());
        }
        if (containsScope(Scope.weight)) {
            fragments.add(new WeightLogFragment());
        }
    }

    private boolean containsScope(Scope scope) {
        return AuthenticationManager.getCurrentAccessToken().getScopes().contains(scope);
    }

    @Override
    public Fragment getItem(int position) {
        if (position >= fragments.size()) {
            return null;
        }

        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public int getTitleResourceId(int index) {
        return fragments.get(index).getTitleResourceId();
    }
}