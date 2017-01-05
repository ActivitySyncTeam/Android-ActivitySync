package com.activity_sync.screens;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.presenters.UserDetailsPresenter;
import com.activity_sync.presentation.views.IUserDetailsView;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;

public class UserDetailsScreen extends UserBaseScreen implements IUserDetailsView
{
    public static final String USER_ID = "user_id";

    @Bind(R.id.action_btn)
    Button actionBtn;

    @Bind(R.id.reject_btn)
    Button rejectBtn;

    @Bind(R.id.user_details_buttons_layout)
    LinearLayout buttonsLayout;

    public UserDetailsScreen()
    {
        super(R.layout.user_details_screen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.title_user_details));
    }

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        int userId = getIntent().getIntExtra(UserDetailsScreen.USER_ID, 0);
        return new UserDetailsPresenter(this, apiService, AndroidSchedulers.mainThread(), currentUser, userId, errorHandler);
    }

    @Override
    protected void inject(Context screen)
    {
        App.component(this).inject(this);
    }

    @Override
    public void thumbsAndFollowBtnVisible(boolean areVisible)
    {
        if (areVisible)
        {
            thumbDown.setVisibility(View.VISIBLE);
            thumbUp.setVisibility(View.VISIBLE);
            actionBtn.setVisibility(View.VISIBLE);
        }
        else
        {
            thumbDown.setVisibility(View.INVISIBLE);
            thumbUp.setVisibility(View.INVISIBLE);
            actionBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public Observable thumbUpClick()
    {
        return ViewObservable.clicks(thumbUp);
    }

    @Override
    public Observable thumbDownClick()
    {
        return ViewObservable.clicks(thumbDown);
    }

    @Override
    public void setThumbsColor(int rating)
    {
        if (rating == 1)
        {
            thumbUp.setColorFilter(ContextCompat.getColor(this, R.color.green));
            thumbDown.setColorFilter(ContextCompat.getColor(this, R.color.user_details_thumb_default));
        }
        else if (rating == -1)
        {
            thumbUp.setColorFilter(ContextCompat.getColor(this, R.color.user_details_thumb_default));
            thumbDown.setColorFilter(ContextCompat.getColor(this, R.color.red));
        }
        else
        {
            thumbUp.setColorFilter(ContextCompat.getColor(this, R.color.user_details_thumb_default));
            thumbDown.setColorFilter(ContextCompat.getColor(this, R.color.user_details_thumb_default));
        }
    }

    @Override
    public void setFriendBtnAppearance(User user)
    {
        if (user.isFriend())
        {
            actionBtn.setText(getString(R.string.btn_remove_from_friends));
            rejectBtn.setVisibility(View.GONE);
            actionBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.selector_default_negative));
        }
        else if (user.isCandidate())
        {
            actionBtn.setText(getString(R.string.btn_cancel_friend_request));
            rejectBtn.setVisibility(View.GONE);
            actionBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.selector_default_negative));
        }
        else if (user.isInvited())
        {
            actionBtn.setText(getString(R.string.btn_accept_friends_request));
            rejectBtn.setVisibility(View.VISIBLE);
            actionBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.selector_default_positive));
        }
        else
        {
            actionBtn.setText(getString(R.string.btn_add_to_friends));
            rejectBtn.setVisibility(View.GONE);
            actionBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.selector_default_positive));
        }
    }

    @Override
    public void displayFriendRequestSentMessage()
    {
        Toast.makeText(this, getString(R.string.txt_friends_request_sent), Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayFriendRequestAcceptedMessage()
    {
        Toast.makeText(this, getString(R.string.txt_friend_request_accepted), Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayFriendRequestCanceledMessage()
    {
        Toast.makeText(this, getString(R.string.txt_friends_request_turned_down), Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayFriendRemovedMessage()
    {
        Toast.makeText(this, getString(R.string.txt_friend_removed), Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayFriendRequestRejectedMessage()
    {
        Toast.makeText(this, getString(R.string.txt_friend_request_rejected), Toast.LENGTH_LONG).show();
    }

    @Override
    public void buttonsLayoutVisible(boolean isVisible)
    {
        if (isVisible)
        {
            buttonsLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            buttonsLayout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public Observable friendsBtnClick()
    {
        return ViewObservable.clicks(actionBtn);
    }

    @Override
    public Observable rejectInvitationClick()
    {
        return ViewObservable.clicks(rejectBtn);
    }
}
