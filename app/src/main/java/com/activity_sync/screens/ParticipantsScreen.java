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

public class ParticipantsScreen extends Screen
{
    public static final String IS_ORGANIZER = "is_organizer";
    public static final String EVENT_ID = "event_id";

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;

    @Bind(R.id.view_pager)
    ViewPager viewPager;

    private ViewPagerAdapter adapter;

    public ParticipantsScreen()
    {
        super(R.layout.users_screen);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        boolean isOrganizer = getIntent().getBooleanExtra(ParticipantsScreen.IS_ORGANIZER, false);
        int eventId = getIntent().getIntExtra(ParticipantsScreen.EVENT_ID, 0);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RegisteredParticipantsFragment(isOrganizer, eventId), getString(R.string.txt_registered));
        adapter.addFragment(new EventCandidatesFragment(isOrganizer, eventId), getString(R.string.txt_candidates));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        setTitle(getString(R.string.title_participants));
    }

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return null;
    }

    @Override
    protected void inject(Context screen) {}
}
