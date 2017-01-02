package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.services.CurrentUser;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.views.IUserDetailsView;

import rx.Scheduler;
import timber.log.Timber;

public class UserDetailsPresenter extends Presenter<IUserDetailsView>
{
    private final static int LIKE = 1;
    private final static int DISLIKE = -1;
    private final static int NO_ASSESMENT = 0;

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

        apiService.getUserData(userId)
                .observeOn(uiThread)
                .subscribe(result -> {
                    user = result;
                    view.setData(result);
                    view.thumbsAndFollowBtnVisible(true);
                    view.setThumbsColor(user.getRate());
                    view.setFriendBtnAppearance(user);
                }, this::handleError);

        subscriptions.add(view.thumbUpClick()
                .observeOn(uiThread)
                .subscribe(o -> {

                    if (user.getRate() == 1)
                    {
                        apiService.rateUser(userId, NO_ASSESMENT)
                                .observeOn(uiThread)
                                .subscribe(result -> {

                                    view.setThumbsColor(NO_ASSESMENT);
                                    user.setRate(NO_ASSESMENT);

                                }, this::handleError);
                    }
                    else
                    {
                        apiService.rateUser(userId, LIKE)
                                .observeOn(uiThread)
                                .subscribe(result -> {

                                    view.setThumbsColor(LIKE);
                                    user.setRate(LIKE);
                                }, this::handleError);
                    }
                })
        );

        subscriptions.add(view.thumbDownClick()
                .observeOn(uiThread)
                .subscribe(o -> {

                    if (user.getRate() == -1)
                    {
                        apiService.rateUser(userId, NO_ASSESMENT)
                                .observeOn(uiThread)
                                .subscribe(result -> {

                                    view.setThumbsColor(NO_ASSESMENT);
                                    user.setRate(NO_ASSESMENT);
                                }, this::handleError);
                    }
                    else
                    {
                        apiService.rateUser(userId, DISLIKE)
                                .observeOn(uiThread)
                                .subscribe(result -> {

                                    view.setThumbsColor(DISLIKE);
                                    user.setRate(DISLIKE);
                                }, this::handleError);
                    }
                })
        );

        subscriptions.add(view.friendsBtnClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    if (user.isCandidate())
                    {
                        view.displayFriendRequestCanceledMessage();
                        user.setCandidate(false);
                        user.setFriend(false);
                        view.setFriendBtnAppearance(user);

                    }
                    else if (user.isFriend())
                    {
                        view.displayFriendRemovedMessage();
                        user.setCandidate(false);
                        user.setFriend(false);
                        view.setFriendBtnAppearance(user);
                    }
                    else
                    {
                        view.displayFriendRequestSentMessage();
                        user.setCandidate(true);
                        user.setFriend(false);
                        view.setFriendBtnAppearance(user);
                    }
                })
        );
    }

    private void handleError(Throwable error)
    {
        error.printStackTrace();
        Timber.d(error.getMessage());
        view.displayDefaultError();
    }
}
