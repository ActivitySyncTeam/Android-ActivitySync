package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.utils.StringUtils;
import com.activity_sync.presentation.views.IRegisterView;

import rx.Scheduler;

public class RegisterPresenter extends Presenter<IRegisterView>
{
    private final Scheduler uiThread;
    private final INavigator navigator;

    public RegisterPresenter(Scheduler uiThread, IRegisterView view, INavigator navigator)
    {
        super(view);
        this.navigator = navigator;
        this.uiThread = uiThread;
    }

    @Override
    public void start()
    {
        super.start();

        subscriptions.add(view.registerBtnClick()
                .observeOn(uiThread)
                .subscribe(o -> {

                    boolean canContinue = true;

                    if (StringUtils.isNullOrEmpty(view.fullName()))
                    {
                        view.fullNameErrorEnabled(true);
                        view.fullNameErrorText("Please provide your full name");
                        canContinue = false;
                    }

                    if (StringUtils.isNullOrEmpty(view.login()))
                    {
                        view.loginErrorEnabled(true);
                        view.loginErrorText("Please provide your login");
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

        subscriptions.add(view.alreadyRegisteredClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    navigator.openLoginScreen();
                })
        );

    }
}
