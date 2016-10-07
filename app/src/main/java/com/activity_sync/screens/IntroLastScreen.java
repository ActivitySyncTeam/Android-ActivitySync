package com.activity_sync.screens;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.presenters.IntroLastPresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IIntroLastView;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;
import javax.inject.Inject;
import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;

public class IntroLastScreen extends FragmentScreenWithLogic implements ISlideBackgroundColorHolder, IIntroLastView
{
    @Inject
    INavigator navigator;

    @Bind(R.id.intro_last_screen_relative_layout)
    RelativeLayout introLastScreenLayout;

    @Bind(R.id.image_check)
    ImageView checkImageView;

    public IntroLastScreen() {
        super(R.layout.intro_last_screen);
    }

    @Override
    public int getDefaultBackgroundColor()
    {
        return ContextCompat.getColor(getActivity(), R.color.intro_last_screen_bg);
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor)
    {
        introLastScreenLayout.setBackgroundColor(backgroundColor);
    }

    @Override
    protected IPresenter createPresenter(FragmentScreen fragmentScreen, Bundle savedInstanceState)
    {
        App.component(getContext()).inject(this);
        return new IntroLastPresenter(AndroidSchedulers.mainThread(), this, navigator);
    }

    @Override
    public Observable checkImageClick()
    {
        return ViewObservable.clicks(checkImageView);
    }
}
