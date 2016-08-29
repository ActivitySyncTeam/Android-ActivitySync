package com.activity_sync.screens;

import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.widget.LinearLayout;

import com.activity_sync.R;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;

import butterknife.Bind;

public class IntroFriendsScreen extends FragmentScreen implements ISlideBackgroundColorHolder
{
    @Bind(R.id.intro_friends_main_linear_layout)
    LinearLayout introFriendsMainLayout;

    @Bind(R.id.intro_friends_upper_linear_layout)
    LinearLayout introFriendsUpperLayout;

    public IntroFriendsScreen()
    {
        super(R.layout.intro_friends_screen);
    }

    @Override
    public int getDefaultBackgroundColor()
    {
        return ContextCompat.getColor(getActivity(), R.color.intro_friends_bg);
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor)
    {
        introFriendsMainLayout.setBackgroundColor(backgroundColor);
        introFriendsUpperLayout.setBackgroundColor(backgroundColor);
    }
}
