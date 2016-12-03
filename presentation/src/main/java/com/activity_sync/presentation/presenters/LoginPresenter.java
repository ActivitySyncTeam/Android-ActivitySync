package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.utils.StringUtils;
import com.activity_sync.presentation.views.ILoginView;

import rx.Scheduler;

public class LoginPresenter extends Presenter<ILoginView>
{
    private final Scheduler uiThread;
    private final INavigator navigator;
    private final IApiService apiService;

    public LoginPresenter(Scheduler uiThread, ILoginView view, INavigator navigator, IApiService apiService)
    {
        super(view);
        this.navigator = navigator;
        this.uiThread = uiThread;
        this.apiService = apiService;
    }

    @Override
    public void start()
    {
        super.start();

        subscriptions.add(view.loginBtnClick()
                .observeOn(uiThread)
                .subscribe(o -> {

                    boolean canContinue = true;

                    if (StringUtils.isNullOrEmpty(view.login()))
                    {
                        view.loginErrorEnabled(true);
                        view.loginErrorText(view.emptyFieldErrorText());
                        canContinue = false;
                    }

                    if (StringUtils.isNullOrEmpty(view.password()))
                    {
                        view.passwordErrorEnabled(true);
                        view.passwordErrorText(view.emptyFieldErrorText());
                        canContinue = false;
                    }

                    if (canContinue)
                    {
                        navigator.openEventsScreen();
                    }
                })
        );

        subscriptions.add(view.createAccountClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    navigator.openRegisterScreen();
                })
        );
    }
}
