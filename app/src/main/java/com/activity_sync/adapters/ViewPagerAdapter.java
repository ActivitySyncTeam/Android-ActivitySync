package com.activity_sync.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter
{
    private ArrayList<String> titles;
    private ArrayList<Fragment> fragments;

    public ViewPagerAdapter(FragmentManager fragmentManager)
    {
        super(fragmentManager);
        titles = new ArrayList<>();
        fragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position)
    {
        return fragments.get(position);
    }

    @Override
    public int getCount()
    {
        return fragments.size();
    }

    public void addFragment(Fragment fragment, String title)
    {
        fragments.add(fragment);
        titles.add(title);
    }
    @Override
    public CharSequence getPageTitle(int position)
    {
        return titles.get(position);
    }

}
