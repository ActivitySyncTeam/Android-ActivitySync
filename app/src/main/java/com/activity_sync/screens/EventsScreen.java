package com.activity_sync.screens;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.adapters.ViewPagerAdapter;
import com.activity_sync.presentation.presenters.EventsPresenter;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventsView;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;

public class EventsScreen extends ScreenWithMenu implements IEventsView
{
    @Inject
    INavigator navigator;

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;

    @Bind(R.id.view_pager)
    ViewPager viewPager;

    @Bind(R.id.add_new_event_button)
    FloatingActionButton addNewEventButton;

    private ViewPagerAdapter adapter;

    public EventsScreen()
    {
        super(R.layout.events_screen);
    }

    @Override
    protected int getMenuId()
    {
        return R.id.menu_events;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MyEventsFragment(), getString(R.string.tab_my_events));
        adapter.addFragment(new AllEventsFragment(), getString(R.string.tab_all_events));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        setTitle(R.string.title_events);
    }

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return new EventsPresenter(this, navigator, AndroidSchedulers.mainThread());
    }

    @Override
    protected void inject(Context screen)
    {
        App.component(this).inject(this);
    }

    @Override
    public Observable addNewEventClick()
    {
        return ViewObservable.clicks(addNewEventButton);
    }
}