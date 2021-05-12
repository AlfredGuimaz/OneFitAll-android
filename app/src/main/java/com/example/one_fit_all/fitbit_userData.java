package com.example.one_fit_all;

import com.fitbit.authentication.AuthenticationManager;
import com.example.one_fit_all.databinding.ActivityFitbitUserDataBinding;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;


public class fitbit_userData extends Activity {

    private ActivityFitbitUserDataBinding binding;
    private UserDataPagerAdapter userDataPagerAdapter;

    public static Intent newIntent(Context context) {
        return new Intent(context, fitbit_userData.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_fitbit_user_data);
        binding.setLoading(false);

        userDataPagerAdapter = new UserDataPagerAdapter(getFragmentManager());
        binding.viewPager.setAdapter(userDataPagerAdapter);

        binding.viewPager.addOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.

                        getActionBar().setSelectedNavigationItem(position);
                    }
                });

//        addTabs();
    }

    private void addTabs() {
        final ActionBar actionBar = getActionBar();
        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        int numberOfTabs = userDataPagerAdapter.getCount();
        for (int i = 0; i < numberOfTabs; i++) {
            final int index = i;
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(getString(userDataPagerAdapter.getTitleResourceId(i)))
                            .setTabListener(new ActionBar.TabListener() {
                                @Override
                                public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                                    binding.viewPager.setCurrentItem(index);
                                }

                                @Override
                                public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

                                }

                                @Override
                                public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

                                }
                            }));
        }
    }

    public void onLogoutClick(View view) {
        binding.setLoading(true);
        AuthenticationManager.logout(this);
    }
}