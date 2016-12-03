package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.builders.UserBuilder;
import com.activity_sync.presentation.models.builders.UserDetailsBuilder;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.views.IUserDetailsView;

public class UserDetailsPresenter extends Presenter<IUserDetailsView>
{
    private final IApiService apiService;

    public UserDetailsPresenter(IUserDetailsView view, IApiService apiService)
    {
        super(view);
        this.apiService = apiService;
    }

    @Override
    public void start()
    {
        super.start();

        view.setData(new UserBuilder()
                .setUserDetails(new UserDetailsBuilder()
                        .setFirstName("Marcin")
                        .setlastName("Zielinski")
                        .setUserName("mzielu")
                        .setEmail("kmarcinzielnski@gmail.com")
                    .createUserDetails())
                .setId(123)
                .setCredibility(85)
                .createUser());
    }
}
