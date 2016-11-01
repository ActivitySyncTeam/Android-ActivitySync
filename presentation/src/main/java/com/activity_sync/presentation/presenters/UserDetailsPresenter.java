package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.builders.UserBuilder;
import com.activity_sync.presentation.models.builders.UserDetailsBuilder;
import com.activity_sync.presentation.views.IUserDetailsView;

public class UserDetailsPresenter extends Presenter<IUserDetailsView>
{
    public UserDetailsPresenter(IUserDetailsView view)
    {
        super(view);
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
                .setCreditability(32)
                .createUser());
    }
}
