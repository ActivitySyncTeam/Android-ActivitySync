package com.activity_sync.presentation.views;

import com.activity_sync.presentation.models.User;

import rx.Observable;

public interface IUserDetailsView extends IUserBaseView
{
    int LIKE = 1;
    int DISLIKE = -1;
    int NO_ASSESSMENT = 0;

    void thumbsAndFollowBtnVisible(boolean areVisible);
    Observable thumbUpClick();
    Observable thumbDownClick();
    void setThumbsColor(int rating);

    Observable friendsBtnClick();
    Observable rejectInvitationClick();
    void setFriendBtnAppearance(User user);

    void displayFriendRequestSentMessage();
    void displayFriendRequestAcceptedMessage();
    void displayFriendRequestCanceledMessage();
    void displayFriendRemovedMessage();
    void displayFriendRequestRejectedMessage();

    void buttonsLayoutVisible(boolean isVisible);
}
