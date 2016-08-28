package com.activity_sync.screens;

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

public class IntroPermissionScreen extends FragmentScreen implements IIntroWelcomeView, ISlideBackgroundColorHolder
{
    @Bind(R.id.test_btn)
    Button noEloBtn;

    @Bind(R.id.permis_linear)
    LinearLayout permisLinear;

    public IntroPermissionScreen() {
        super(R.layout.intro_permission_screen);
    }

    @Override
    protected IPresenter createPresenter(FragmentScreen fragmentScreen, Bundle savedInstanceState) {
        return new IntroWelcomePresenter(AndroidSchedulers.mainThread(), this);
    }

    @Override
    public Observable noEloClick() {
        return ViewObservable.clicks(noEloBtn);
    }

    @Override
    public int getDefaultBackgroundColor()
    {
        return ContextCompat.getColor(getActivity(), R.color.yellow);
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor)
    {
permisLinear.setBackgroundColor(backgroundColor);
    }
}