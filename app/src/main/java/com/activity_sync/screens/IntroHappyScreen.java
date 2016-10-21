package com.activity_sync.screens;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.widget.LinearLayout;

import com.activity_sync.R;
import com.activity_sync.presentation.presenters.IPresenter;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;

import butterknife.Bind;

public class IntroHappyScreen extends FragmentScreen implements ISlideBackgroundColorHolder
{
    @Bind(R.id.intro_happy_main_linear_layout)
    LinearLayout introHappyMainLayout;

    @Bind(R.id.intro_happy_upper_linear_layout)
    LinearLayout introHappyUpperLayout;

    public IntroHappyScreen() {
        super(R.layout.intro_happy_screen);
    }

    @Override
    public int getDefaultBackgroundColor()
    {
        return ContextCompat.getColor(getActivity(), R.color.intro_happy_bg);
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor)
    {
        introHappyMainLayout.setBackgroundColor(backgroundColor);
        introHappyUpperLayout.setBackgroundColor(backgroundColor);
    }

    @Override
    protected IPresenter createPresenter(FragmentScreen screen, Bundle savedInstanceState)
    {
        return null;
    }
}