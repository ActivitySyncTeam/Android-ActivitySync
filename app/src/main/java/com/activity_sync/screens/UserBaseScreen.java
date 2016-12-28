package com.activity_sync.screens;


import android.widget.ImageView;
import android.widget.TextView;

import com.activity_sync.R;
import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.services.CurrentUser;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.views.IUserBaseView;
import com.activity_sync.utils.CredibilityService;
import com.amulyakhare.textdrawable.TextDrawable;

import javax.inject.Inject;

import butterknife.Bind;

abstract public class UserBaseScreen extends Screen implements IUserBaseView
{
    @Inject
    IApiService apiService;

    @Inject
    CurrentUser currentUser;

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


    protected UserBaseScreen(int layoutResId)
    {
        super(layoutResId);
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
}
