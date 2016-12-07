package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.models.builders.AdditionalInfoBuilder;
import com.activity_sync.presentation.models.builders.UserBuilder;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.views.IUserDetailsView;

import rx.Scheduler;

public class UserDetailsPresenter extends Presenter<IUserDetailsView>
{
    private final IApiService apiService;
    private final Scheduler uiThread;

    private User user;

    public UserDetailsPresenter(IUserDetailsView view, IApiService apiService, Scheduler uiThread)
    {
        super(view);
        this.apiService = apiService;
        this.uiThread = uiThread;
    }

    @Override
    public void start()
    {
        super.start();

        user = createUser();
        view.setData(user);
        view.setThumbsColor(0);

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

    private User createUser()
    {
        return new UserBuilder()
                .setName("Marcin")
                .setSurname("Zielinski")
                .setUsername("mzielu")
                .setEmail("kmarcinzielnski@gmail.com")
                .setUserId(123)
                .setCredibility(85)
                .setAdditionalInfo(new AdditionalInfoBuilder()
                        .setRate(0)
                        .createAdditionalInfo())
                .createUser();
    }
}
