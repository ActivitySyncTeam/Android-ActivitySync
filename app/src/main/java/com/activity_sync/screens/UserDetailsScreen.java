package com.activity_sync.screens;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.activity_sync.R;
import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.presenters.UserDetailsPresenter;
import com.activity_sync.presentation.views.IUserDetailsView;
import com.activity_sync.utils.CreditabilityService;
import com.amulyakhare.textdrawable.TextDrawable;

import butterknife.Bind;

public class UserDetailsScreen extends Screen implements IUserDetailsView
{
    @Bind(R.id.tv_username)
    TextView username;

    @Bind(R.id.tv_name)
    TextView name;

    @Bind(R.id.tv_lastname)
    TextView lastname;

    @Bind(R.id.tv_email)
    TextView email;

    @Bind(R.id.tv_creditability)
    TextView creditabilityTextView;

    @Bind(R.id.image_creditability)
    ImageView creditabilityImageView;

    public UserDetailsScreen()
    {
        super(R.layout.user_details_screen);
    }

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return new UserDetailsPresenter(this);
    }

    @Override
    public void setData(User user)
    {
        username.setText(user.getUserDetails().getUserName());
        name.setText(user.getUserDetails().getFirstName());
        lastname.setText(user.getUserDetails().getLastName());
        email.setText(user.getUserDetails().getEmail());

        CreditabilityService creditabilityService = new CreditabilityService(this, user.getCreditability());

        TextDrawable drawable = TextDrawable.builder().buildRound(String.format("%d", user.getCreditability()), creditabilityService.getColor());
        creditabilityImageView.setImageDrawable(drawable);

        creditabilityTextView.setText(creditabilityService.getDescription());
    }
}
