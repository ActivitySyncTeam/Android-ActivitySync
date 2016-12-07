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

        createUser(0);
        view.setData(user);

        if (currentUser.userID() == user.getUserId())
        {
            view.thumbsVisible(false);
        }
        else
        {
            view.thumbsVisible(true);
            view.setThumbsColor(user.getAdditionalInfo().getRate());
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
    }

    public void createUser(int rate)
    {
        user = new UserBuilder()
                .setName("Marcin")
                .setSurname("Zielinski")
                .setUsername("mzielu")
                .setEmail("kmarcinzielnski@gmail.com")
                .setUserId(1234)
                .setCredibility(85)
                .setAdditionalInfo(new AdditionalInfoBuilder()
                        .setRate(rate)
                        .createAdditionalInfo())
                .createUser();
    }
}
