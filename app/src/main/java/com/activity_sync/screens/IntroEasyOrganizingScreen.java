package com.activity_sync.screens;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.widget.LinearLayout;

import com.activity_sync.R;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.presenters.IntroEasyOrganizingPresenter;
import com.activity_sync.presentation.views.IIntroEasyOrganizingView;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;

import butterknife.Bind;

public class IntroEasyOrganizingScreen extends FragmentScreen implements IIntroEasyOrganizingView, ISlideBackgroundColorHolder
{
    @Bind(R.id.intro_easy_organizing_main_linear_layout)
    LinearLayout introEasyOrganizingMainLayout;

    @Bind(R.id.intro_easy_organizing_upper_linear_layout)
    LinearLayout introEasyOrganizingUpperLayout;

    public IntroEasyOrganizingScreen()
    {
        super(R.layout.intro_easy_organizing_screen);
    }

    @Override
    protected IPresenter createPresenter(FragmentScreen fragmentScreen, Bundle savedInstanceState)
    {
        return new IntroEasyOrganizingPresenter(this);
    }

    @Override
    public int getDefaultBackgroundColor()
    {
        return ContextCompat.getColor(getActivity(), R.color.intro_easy_organizing_bg);
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor)
    {
        introEasyOrganizingMainLayout.setBackgroundColor(backgroundColor);
        introEasyOrganizingUpperLayout.setBackgroundColor(backgroundColor);
    }
}
