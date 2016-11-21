package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.utils.StringUtils;
import com.activity_sync.presentation.views.ILoginView;

import rx.Scheduler;

public class LoginPresenter extends Presenter<ILoginView>
{
    private final Scheduler uiThread;
    private final INavigator navigator;

    public LoginPresenter(Scheduler uiThread, ILoginView view, INavigator navigator)
    {
        super(view);
        this.navigator = navigator;
        this.uiThread = uiThread;
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
                        view.loginErrorText("Please provide your email");
                        canContinue = false;
                    }

                    if (StringUtils.isNullOrEmpty(view.password()))
                    {
                        view.passwordErrorEnabled(true);
                        view.passwordErrorText("Please provide your password");
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
