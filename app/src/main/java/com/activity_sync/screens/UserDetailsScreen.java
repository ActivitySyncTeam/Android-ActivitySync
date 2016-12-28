package com.activity_sync.screens;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
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
    @Bind(R.id.follow_btn)
    Button followBtn;

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
        return new UserDetailsPresenter(this, apiService, AndroidSchedulers.mainThread(), currentUser);
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
            followBtn.setVisibility(View.VISIBLE);
        }
        else
        {
            thumbDown.setVisibility(View.INVISIBLE);
            thumbUp.setVisibility(View.INVISIBLE);
            followBtn.setVisibility(View.GONE);
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
        if (user.getAdditionalInfo().isFriend())
        {
            followBtn.setText(getString(R.string.btn_remove_from_friends));
            followBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.selector_default_negative));
        }
        else if (user.getAdditionalInfo().isCandidate())
        {
            followBtn.setText(getString(R.string.btn_cancel_friend_request));
            followBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.selector_default_negative));
        }
        else
        {
            followBtn.setText(getString(R.string.btn_add_to_friends));
            followBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.selector_default_positive));
        }
    }

    @Override
    public void displayFriendRequestSentMessage()
    {
        Toast.makeText(this, getString(R.string.txt_friends_request_sent), Toast.LENGTH_LONG).show();
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
    public Observable friendsBtnClick()
    {
        return ViewObservable.clicks(followBtn);
    }
}
