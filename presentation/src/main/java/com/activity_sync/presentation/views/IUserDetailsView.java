package com.activity_sync.presentation.views;

import com.activity_sync.presentation.models.User;

import rx.Observable;

public interface IUserDetailsView
{
    void setData(User user);

    Observable thumbUpClick();

    Observable thumbDownClick();

    void setThumbsColor(int rating);
}
