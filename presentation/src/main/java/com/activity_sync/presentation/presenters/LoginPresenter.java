package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.CurrentUser;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.utils.StringUtils;
import com.activity_sync.presentation.views.ILoginView;

import rx.Scheduler;
import timber.log.Timber;

public class LoginPresenter extends Presenter<ILoginView>
{
    private final Scheduler uiThread;
    private final INavigator navigator;
    private final IApiService apiService;
    private final CurrentUser currentUser;

    public LoginPresenter(Scheduler uiThread, ILoginView view, INavigator navigator, IApiService apiService, CurrentUser currentUser)
    {
        super(view);
        this.navigator = navigator;
        this.uiThread = uiThread;
        this.apiService = apiService;
        this.currentUser = currentUser;
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
                        apiService.getClientDetails()
                                .observeOn(uiThread)
                                .subscribe(clientDetails -> {

                                    currentUser.clientId(clientDetails.getClientId());
                                    currentUser.clientSecret(clientDetails.getClientSecret());

                                    apiService.login(view.login(), view.password())
                                            .observeOn(uiThread)
                                            .subscribe((result) -> {

                                                currentUser.accessToken(result.getAccessToken());

                                                navigator.startBackgroundService();
                                                navigator.openEventsScreen();

                                            }, this::handleError);
                                }, this::handleError);
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

    private void handleError(Throwable error)
    {
        error.printStackTrace();
        Timber.d(error.getMessage());
        view.displayDefaultError();
    }
}
