package com.activity_sync.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.adapters.ViewPagerAdapter;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.services.INavigator;

import javax.inject.Inject;

import butterknife.Bind;

public class ParticipantsScreen extends Screen
{
    public static final String IS_ORGANIZER = "is_organizer";

    @Inject
    INavigator navigator;

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;

    @Bind(R.id.view_pager)
    ViewPager viewPager;

    private ViewPagerAdapter adapter;

    public ParticipantsScreen()
    {
        super(R.layout.particpants_screen);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        App.component(this).inject(this);
        super.onCreate(savedInstanceState);

        boolean isOrganizer = getIntent().getBooleanExtra(ParticipantsScreen.IS_ORGANIZER, false);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RegisteredParticipantsFragment(isOrganizer), getString(R.string.txt_registered));
        adapter.addFragment(new CandidatesFragment(isOrganizer), getString(R.string.txt_candidates));
        adapter.addFragment(new DeclinedParticipantsFragment(isOrganizer), getString(R.string.txt_declined));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        setTitle(getString(R.string.title_participants));
    }

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return null;
    }
}
