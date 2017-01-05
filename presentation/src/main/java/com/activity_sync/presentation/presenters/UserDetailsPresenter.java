package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.models.body_models.UserIDBody;
import com.activity_sync.presentation.services.CurrentUser;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.views.IUserDetailsView;

import rx.Scheduler;
import timber.log.Timber;

import static com.activity_sync.presentation.views.IUserDetailsView.DISLIKE;
import static com.activity_sync.presentation.views.IUserDetailsView.LIKE;
import static com.activity_sync.presentation.views.IUserDetailsView.NO_ASSESSMENT;

public class UserDetailsPresenter extends Presenter<IUserDetailsView>
{
    private final IApiService apiService;
    private final Scheduler uiThread;
    private final CurrentUser currentUser;
    private final int userId;

    private User user;

    public UserDetailsPresenter(IUserDetailsView view, IApiService apiService, Scheduler uiThread, CurrentUser currentUser, int userId)
    {
        super(view);
        this.apiService = apiService;
        this.uiThread = uiThread;
        this.currentUser = currentUser;
        this.userId = userId;
    }

    @Override
    public void start()
    {
        super.start();
        view.showProgressBar();
        view.buttonsLayoutVisible(false);

        apiService.getUserData(userId)
                .observeOn(uiThread)
                .subscribe(result -> {
                    user = result;
                    view.setData(result);
                    view.thumbsAndFollowBtnVisible(true);
                    view.setThumbsColor(user.getRate());
                    view.setFriendBtnAppearance(user);

                    view.buttonsLayoutVisible(true);
                    view.hideProgressBar();
                }, this::handleError);

        subscriptions.add(view.thumbUpClick()
                .observeOn(uiThread)
                .subscribe(o -> {

                    if (user.getRate() == LIKE)
                    {
                        view.showProgressBar();

                        apiService.rateUser(userId, NO_ASSESSMENT)
                                .observeOn(uiThread)
                                .subscribe(result -> {

                                    view.setThumbsColor(NO_ASSESSMENT);
                                    user.setRate(NO_ASSESSMENT);

                                    view.hideProgressBar();

                                }, this::handleError);
                    }
                    else
                    {
                        view.showProgressBar();

                        apiService.rateUser(userId, LIKE)
                                .observeOn(uiThread)
                                .subscribe(result -> {

                                    view.setThumbsColor(LIKE);
                                    user.setRate(LIKE);

                                    view.hideProgressBar();

                                }, this::handleError);
                    }
                })
        );

        subscriptions.add(view.thumbDownClick()
                .observeOn(uiThread)
                .subscribe(o -> {

                    if (user.getRate() == DISLIKE)
                    {
                        view.showProgressBar();

                        apiService.rateUser(userId, NO_ASSESSMENT)
                                .observeOn(uiThread)
                                .subscribe(result -> {

                                    view.setThumbsColor(NO_ASSESSMENT);
                                    user.setRate(NO_ASSESSMENT);

                                    view.hideProgressBar();

                                }, this::handleError);
                    }
                    else
                    {
                        view.showProgressBar();

                        apiService.rateUser(userId, DISLIKE)
                                .observeOn(uiThread)
                                .subscribe(result -> {

                                    view.setThumbsColor(DISLIKE);
                                    user.setRate(DISLIKE);

                                    view.hideProgressBar();

                                }, this::handleError);
                    }
                })
        );

        subscriptions.add(view.friendsBtnClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    if (user.isCandidate())
                    {
                        view.showProgressBar();

                        apiService.cancelFriendInvitation(new UserIDBody(user.getUserId()))
                                .observeOn(uiThread)
                                .subscribe(friends -> {

                                    view.displayFriendRequestCanceledMessage();
                                    user.setCandidate(false);
                                    user.setFriend(false);
                                    user.setInvited(false);
                                    view.setFriendBtnAppearance(user);

                                    view.hideProgressBar();

                                }, this::handleError);
                    }
                    else if (user.isFriend())
                    {
                        view.showProgressBar();

                        apiService.deleteFriend(new UserIDBody(user.getUserId()))
                                .observeOn(uiThread)
                                .subscribe(friends -> {

                                    view.displayFriendRemovedMessage();
                                    user.setCandidate(false);
                                    user.setFriend(false);
                                    user.setInvited(false);
                                    view.setFriendBtnAppearance(user);

                                    view.hideProgressBar();

                                }, this::handleError);
                    }
                    else if (user.isInvited())
                    {
                        view.showProgressBar();

                        apiService.acceptFriendInvitation(user.getUserId())
                                .observeOn(uiThread)
                                .subscribe(friends -> {

                                    view.displayFriendRequestAcceptedMessage();
                                    user.setCandidate(false);
                                    user.setFriend(true);
                                    user.setInvited(false);
                                    view.setFriendBtnAppearance(user);

                                    view.hideProgressBar();

                                }, this::handleError);
                    }
                    else
                    {
                        view.showProgressBar();

                        apiService.sendFriendRequest(user.getUserId())
                                .observeOn(uiThread)
                                .subscribe(friends -> {

                                    view.displayFriendRequestSentMessage();
                                    user.setCandidate(true);
                                    user.setFriend(false);
                                    user.setInvited(false);
                                    view.setFriendBtnAppearance(user);

                                    view.hideProgressBar();

                                }, this::handleError);
                    }
                })
        );

        subscriptions.add(view.rejectInvitationClick()
                .observeOn(uiThread)
                .subscribe(o -> {

                    view.showProgressBar();

                    apiService.rejectFriendRequest(new UserIDBody(user.getUserId()))
                            .observeOn(uiThread)
                            .subscribe(result -> {

                                view.displayFriendRequestRejectedMessage();
                                user.setCandidate(false);
                                user.setFriend(false);
                                user.setInvited(false);
                                view.setFriendBtnAppearance(user);

                                view.hideProgressBar();

                            }, this::handleError);
                })
        );
    }

    private void handleError(Throwable error)
    {
        error.printStackTrace();
        Timber.d(error.getMessage());
        view.displayDefaultError();
        view.hideProgressBar();
    }
}
