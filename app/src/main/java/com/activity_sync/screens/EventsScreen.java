package com.activity_sync.screens;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.activity_sync.R;
import com.activity_sync.adapters.ViewPagerAdapter;
import com.activity_sync.presentation.presenters.IPresenter;

import butterknife.Bind;

public class EventsScreen extends ScreenWithMenu
{
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;

    @Bind(R.id.view_pager)
    ViewPager viewPager;

    private ViewPagerAdapter adapter;

    public EventsScreen()
    {
        super(R.layout.events_screen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AllEventsFragment(), "All events");
        adapter.addFragment(new MyEventsFragment(), "My events");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        setTitle(R.string.title_events);
    }

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return null;
    }
}