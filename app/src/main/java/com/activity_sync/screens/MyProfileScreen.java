package com.activity_sync.screens;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.presenters.MyProfilePresenter;
import com.activity_sync.presentation.services.CurrentUser;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IMyProfileView;
import com.activity_sync.utils.CredibilityService;
import com.amulyakhare.textdrawable.TextDrawable;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;

public class MyProfileScreen extends ScreenWithMenu implements IMyProfileView
{
    @Inject
    INavigator navigator;

    @Inject
    CurrentUser currentUser;

    @Bind(R.id.edit_account_btn)
    Button editAccountButton;

    @Bind(R.id.change_password_btn)
    Button changePasswordButton;

    @Bind(R.id.tv_username)
    TextView username;

    @Bind(R.id.tv_name)
    TextView name;

    @Bind(R.id.tv_lastname)
    TextView lastname;

    @Bind(R.id.tv_email)
    TextView email;

    @Bind(R.id.tv_register_date)
    TextView registerDate;

    @Bind(R.id.tv_signature)
    TextView signature;

    @Bind(R.id.tv_events_number)
    TextView eventsNumber;

    @Bind(R.id.tv_credibility)
    TextView credibilityTextView;

    @Bind(R.id.image_credibility)
    ImageView credibilityImageView;

    @Bind(R.id.thumb_up)
    ImageView thumbUp;

    @Bind(R.id.thumb_down)
    ImageView thumbDown;

    public MyProfileScreen()
    {
        super(R.layout.my_profile_screen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setTitle(R.string.title_my_profile);
        hideThumbs();
    }

    private void hideThumbs()
    {
        thumbDown.setVisibility(View.GONE);
        thumbUp.setVisibility(View.GONE);
    }

    @Override
    protected int getMenuId()
    {
        return R.id.menu_my_profile;
    }

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return new MyProfilePresenter(this, navigator, AndroidSchedulers.mainThread(), currentUser);
    }

    @Override
    protected void inject(Context screen)
    {
        App.component(this).inject(this);
    }

    @Override
    public void setData(User user)
    {
        username.setText(user.getUsername());
        name.setText(user.getName());
        lastname.setText(user.getSurname());
        email.setText(user.getEmail());
        registerDate.setText(user.getRegisterDate());
        eventsNumber.setText(String.valueOf(user.getEvents()));
        signature.setText(user.getSignature());

        CredibilityService credibilityService = new CredibilityService(this, user.getCredibility());

        TextDrawable drawable = TextDrawable.builder().buildRound(String.format("%d", user.getCredibility()), credibilityService.getColor());
        credibilityImageView.setImageDrawable(drawable);

        credibilityTextView.setText(credibilityService.getDescription());
    }

    @Override
    public Observable editUserAccount()
    {
        return ViewObservable.clicks(editAccountButton);
    }

    @Override
    public Observable changeUserPassword()
    {
        return ViewObservable.clicks(changePasswordButton);
    }
}
