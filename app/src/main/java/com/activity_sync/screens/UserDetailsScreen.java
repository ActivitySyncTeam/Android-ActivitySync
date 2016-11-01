package com.activity_sync.screens;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.activity_sync.R;
import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.presenters.UserDetailsPresenter;
import com.activity_sync.presentation.views.IUserDetailsView;
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

        int creditabilityScore = user.getCreditability();
        int color;

        if (creditabilityScore >= 90)
        {
            creditabilityTextView.setText(R.string.txt_very_good);
            color = ContextCompat.getColor(this, R.color.user_details_very_good);
        }
        else if (creditabilityScore >= 80 && creditabilityScore < 90)
        {
            creditabilityTextView.setText(R.string.txt_good);
            color = ContextCompat.getColor(this, R.color.user_details_good);
        }
        else if (creditabilityScore >= 65 && creditabilityScore < 80)
        {
            creditabilityTextView.setText(R.string.txt_ok);
            color = ContextCompat.getColor(this, R.color.user_details_ok);
        }
        else if (creditabilityScore >= 50 && creditabilityScore < 65)
        {
            creditabilityTextView.setText(R.string.txt_poor);
            color = ContextCompat.getColor(this, R.color.user_details_poor);
        }
        else if (creditabilityScore >= 30 && creditabilityScore < 50)
        {
            creditabilityTextView.setText(R.string.txt_bad);
            color = ContextCompat.getColor(this, R.color.user_details_bad);
        }
        else
        {
            creditabilityTextView.setText(R.string.txt_terrible);
            color = ContextCompat.getColor(this, R.color.user_details_terrible);
        }

        TextDrawable drawable = TextDrawable.builder().buildRound(String.format("%d", creditabilityScore), color);
        creditabilityImageView.setImageDrawable(drawable);
    }
}
