package com.activity_sync.screens;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.activity_sync.R;
import com.activity_sync.adapters.ViewPagerAdapter;
import com.activity_sync.presentation.presenters.IPresenter;

import butterknife.Bind;

public class FriendsScreen extends Screen
{
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;

    @Bind(R.id.view_pager)
    ViewPager viewPager;

    private ViewPagerAdapter adapter;

    public FriendsScreen()
    {
        super(R.layout.users_screen);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AddedFriendsFragment(true), getString(R.string.txt_added_friends));
        adapter.addFragment(new FriendsRequestsFragment(true), getString(R.string.txt_friends_requests));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        setTitle(getString(R.string.title_friends));
    }

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return null;
    }

    @Override
    protected void inject(Context screen) {}
}
