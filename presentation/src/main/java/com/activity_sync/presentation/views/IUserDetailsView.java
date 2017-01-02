package com.activity_sync.presentation.views;

import com.activity_sync.presentation.models.User;

import rx.Observable;

public interface IUserDetailsView extends IUserBaseView
{
    void thumbsAndFollowBtnVisible(boolean areVisible);
    Observable thumbUpClick();
    Observable thumbDownClick();
    void setThumbsColor(int rating);

    Observable friendsBtnClick();
    void setFriendBtnAppearance(User user);

    void displayFriendRequestSentMessage();
    void displayFriendRequestCanceledMessage();
    void displayFriendRemovedMessage();
}
