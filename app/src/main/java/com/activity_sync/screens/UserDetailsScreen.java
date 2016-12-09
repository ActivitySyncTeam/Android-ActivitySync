package com.activity_sync.screens;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.presenters.UserDetailsPresenter;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.views.IUserDetailsView;
import com.activity_sync.utils.CredibilityService;
import com.amulyakhare.textdrawable.TextDrawable;

import javax.inject.Inject;

import butterknife.Bind;

public class UserDetailsScreen extends Screen implements IUserDetailsView
{
    @Inject
    IApiService apiService;

    @Bind(R.id.tv_username)
    TextView username;

    @Bind(R.id.tv_name)
    TextView name;

    @Bind(R.id.tv_lastname)
    TextView lastname;

    @Bind(R.id.tv_email)
    TextView email;

    @Bind(R.id.tv_credibility)
    TextView credibilityTextView;

    @Bind(R.id.image_credibility)
    ImageView credibilityImageView;

    public UserDetailsScreen()
    {
        super(R.layout.user_details_screen);
    }

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return new UserDetailsPresenter(this, apiService);
    }

    @Override
    protected void inject(Context screen)
    {
        App.component(this).inject(this);
    }

    @Override
    public void setData(User user)
    {
        username.setText(user.getUserDetails().getUserName());
        name.setText(user.getUserDetails().getFirstName());
        lastname.setText(user.getUserDetails().getLastName());
        email.setText(user.getUserDetails().getEmail());

        CredibilityService credibilityService = new CredibilityService(this, user.getCredibility());

        TextDrawable drawable = TextDrawable.builder().buildRound(String.format("%d", user.getCredibility()), credibilityService.getColor());
        credibilityImageView.setImageDrawable(drawable);

        credibilityTextView.setText(credibilityService.getDescription());
    }
}
