package com.activity_sync.screens;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.widget.LinearLayout;
import com.activity_sync.R;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.presenters.IntroHappyPresenter;
import com.activity_sync.presentation.views.IIntroHappyView;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;

import butterknife.Bind;

public class IntroHappyScreen extends FragmentScreen implements IIntroHappyView, ISlideBackgroundColorHolder
{
    @Bind(R.id.intro_happy_linear_layout)
    LinearLayout introHappyMainLayout;

    public IntroHappyScreen() {
        super(R.layout.intro_happy_screen);
    }

    @Override
    protected IPresenter createPresenter(FragmentScreen fragmentScreen, Bundle savedInstanceState) {
        return new IntroHappyPresenter(this);
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
    }
}