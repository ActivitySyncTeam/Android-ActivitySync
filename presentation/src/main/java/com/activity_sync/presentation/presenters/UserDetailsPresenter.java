package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.models.builders.AdditionalInfoBuilder;
import com.activity_sync.presentation.models.builders.UserBuilder;
import com.activity_sync.presentation.services.CurrentUser;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.views.IUserDetailsView;

import rx.Scheduler;

public class UserDetailsPresenter extends Presenter<IUserDetailsView>
{
    private final IApiService apiService;
    private final Scheduler uiThread;
    private final CurrentUser currentUser;

    private User user;

    public UserDetailsPresenter(IUserDetailsView view, IApiService apiService, Scheduler uiThread, CurrentUser currentUser)
    {
        super(view);
        this.apiService = apiService;
        this.uiThread = uiThread;
        this.currentUser = currentUser;
    }

    @Override
    public void start()
    {
        super.start();

        createUser(0, false, false);
        view.setData(user);

        if (currentUser.clientId().equals(user.getUserId()))        //TODO correct after login api configuration
        {
            view.thumbsAndFollowBtnVisible(false);
        }
        else
        {
            view.thumbsAndFollowBtnVisible(true);
            view.setThumbsColor(user.getAdditionalInfo().getRate());
            view.setFriendBtnAppearance(user);
        }

        subscriptions.add(view.thumbUpClick()
                .observeOn(uiThread)
                .subscribe(o -> {

                    if (user.getAdditionalInfo().getRate() == 1)
                    {
                        view.setThumbsColor(0);
                        //apicall
                        user.setAdditionalInfo(new AdditionalInfoBuilder().setRate(0).createAdditionalInfo());
                    }
                    else
                    {
                        view.setThumbsColor(1);
                        //apicall
                        user.setAdditionalInfo(new AdditionalInfoBuilder().setRate(1).createAdditionalInfo());
                    }
                })
        );

        subscriptions.add(view.thumbDownClick()
                .observeOn(uiThread)
                .subscribe(o -> {

                    if (user.getAdditionalInfo().getRate() == -1)
                    {
                        view.setThumbsColor(0);
                        //apicall
                        user.setAdditionalInfo(new AdditionalInfoBuilder().setRate(0).createAdditionalInfo());
                    }
                    else
                    {
                        view.setThumbsColor(-1);
                        //apicall
                        user.setAdditionalInfo(new AdditionalInfoBuilder().setRate(-1).createAdditionalInfo());
                    }
                })
        );

        subscriptions.add(view.friendsBtnClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    if (user.getAdditionalInfo().isCandidate())
                    {
                        view.displayFriendRequestCanceledMessage();
                        user.setAdditionalInfo(new AdditionalInfoBuilder().setFriend(false).setCandidate(false).createAdditionalInfo());
                        view.setFriendBtnAppearance(user);

                    }
                    else if (user.getAdditionalInfo().isFriend())
                    {
                        view.displayFriendRemovedMessage();
                        user.setAdditionalInfo(new AdditionalInfoBuilder().setFriend(false).setCandidate(false).createAdditionalInfo());
                        view.setFriendBtnAppearance(user);
                    }
                    else
                    {
                        view.displayFriendRequestSentMessage();
                        user.setAdditionalInfo(new AdditionalInfoBuilder().setFriend(false).setCandidate(true).createAdditionalInfo());
                        view.setFriendBtnAppearance(user);
                    }
                })
        );
    }

    public void createUser(int rate, boolean isFriend, boolean isCandidate)
    {
        user = new UserBuilder()
                .setName("Marcin")
                .setSurname("Zielinski")
                .setUsername("mzielu")
                .setEmail("kmarcinzielnski@gmail.com")
                .setRegisterDate("2015-12-12")
                .setSignature("Randomly written text")
                .setEvents(23)
                .setUserId("123")
                .setCredibility(85)
                .setAdditionalInfo(new AdditionalInfoBuilder()
                        .setRate(rate)
                        .setFriend(isFriend)
                        .setCandidate(isCandidate)
                        .createAdditionalInfo())
                .createUser();
    }
}
