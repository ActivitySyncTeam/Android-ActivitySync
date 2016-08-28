package com.activity_sync.screens;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.widget.LinearLayout;

import com.activity_sync.R;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.presenters.IntroWelcomePresenter;
import com.activity_sync.presentation.views.IIntroWelcomeView;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;

import butterknife.Bind;

public class IntroWelcomeScreen extends FragmentScreen implements IIntroWelcomeView, ISlideBackgroundColorHolder
{
    @Bind(R.id.intro_welcome_linear_layout)
    LinearLayout introWelcomeMainLayout;

    public IntroWelcomeScreen()
    {
        super(R.layout.intro_welcome_screen);
    }

    @Override
    protected IPresenter createPresenter(FragmentScreen fragmentScreen, Bundle savedInstanceState)
    {
        return new IntroWelcomePresenter(this);
    }

    @Override
    public int getDefaultBackgroundColor()
    {
        return ContextCompat.getColor(getActivity(), R.color.intro_welcome_bg);
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor)
    {
        introWelcomeMainLayout.setBackgroundColor(backgroundColor);
    }
}
