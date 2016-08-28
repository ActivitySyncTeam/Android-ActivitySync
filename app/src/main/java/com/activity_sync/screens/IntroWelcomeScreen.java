package com.activity_sync.screens;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.LinearLayout;

import com.activity_sync.R;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.presenters.IntroWelcomePresenter;
import com.activity_sync.presentation.views.IIntroWelcomeView;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;

public class IntroWelcomeScreen extends FragmentScreen implements IIntroWelcomeView, ISlideBackgroundColorHolder
{
    @Bind(R.id.no_elo_btn)
    Button noEloBtn;

    @Bind(R.id.welcome_linear)
    LinearLayout welcomeLienar;

    public IntroWelcomeScreen()
    {
        super(R.layout.intro_welcome_screen);
    }

    @Override
    protected IPresenter createPresenter(FragmentScreen fragmentScreen, Bundle savedInstanceState)
    {
        return new IntroWelcomePresenter(AndroidSchedulers.mainThread(), this);
    }

    @Override
    public Observable noEloClick()
    {
        return ViewObservable.clicks(noEloBtn);
    }

    @Override
    public int getDefaultBackgroundColor()
    {
        return ContextCompat.getColor(getActivity(), R.color.orange);
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor)
    {
        welcomeLienar.setBackgroundColor(backgroundColor);
    }
}
