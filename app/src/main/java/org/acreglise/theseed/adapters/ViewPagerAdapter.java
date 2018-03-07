package org.acreglise.theseed.adapters;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.acreglise.theseed.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private String activityName;

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    public ViewPagerAdapter(FragmentManager manager, String activityName) {
        super(manager);
        this.activityName = activityName;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        /*if (activityName.equals(MainActivity.class.getCanonicalName())) {
            return null;
        }*/
        return mFragmentTitleList.get(position);
    }
}
