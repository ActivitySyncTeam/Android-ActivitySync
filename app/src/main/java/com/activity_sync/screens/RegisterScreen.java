package com.activity_sync.screens;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.presenters.RegisterPresenter;
import com.activity_sync.presentation.services.CurrentUser;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.IErrorHandler;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IRegisterView;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;

public class RegisterScreen extends Screen implements IRegisterView
{
    @Inject
    INavigator navigator;

    @Inject
    IApiService apiService;

    @Inject
    CurrentUser currentUser;

    @Inject
    IErrorHandler errorHandler;

    @Bind(R.id.register_btn)
    Button registerBtn;

    @Bind(R.id.already_registered)
    TextView alreadyRegisteredTv;

    @Bind(R.id.text_input_layout_first_name)
    TextInputLayout inputFirstNameLayout;

    @Bind(R.id.text_input_layout_last_name)
    TextInputLayout inputLastNameLayout;

    @Bind(R.id.text_input_layout_email)
    TextInputLayout inputLoginLayout;

    @Bind(R.id.text_input_layout_username)
    TextInputLayout inputUserNameLayout;

    @Bind(R.id.text_input_layout_password)
    TextInputLayout inputPasswordLayout;

    @Bind(R.id.first_name)
    AppCompatEditText firstNameEditText;

    @Bind(R.id.last_name)
    AppCompatEditText lastNameEditText;

    @Bind(R.id.email)
    AppCompatEditText emailEditText;

    @Bind(R.id.username)
    AppCompatEditText userNameEditText;

    @Bind(R.id.password)
    AppCompatEditText passwordEditText;

    @Bind(R.id.register_progress_bar)
    ProgressBar progressBar;

    public RegisterScreen()
    {
        super(R.layout.register_screen);
    }

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return new RegisterPresenter(AndroidSchedulers.mainThread(), this, navigator, apiService, currentUser, errorHandler);
    }

    @Override
    protected void inject(Context screen)
    {
        App.component(this).inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        prepareEditTexts();
    }

    @Override
    public Observable registerBtnClick()
    {
        return ViewObservable.clicks(registerBtn);
    }

    @Override
    public Observable alreadyRegisteredClick()
    {
        return ViewObservable.clicks(alreadyRegisteredTv);
    }

    @Override
    public String firstName()
    {
        return firstNameEditText.getText().toString();
    }

    @Override
    public String lastName()
    {
        return lastNameEditText.getText().toString();
    }

    @Override
    public String userName()
    {
        return userNameEditText.getText().toString();
    }

    @Override
    public String email()
    {
        return emailEditText.getText().toString();
    }

    @Override
    public String password()
    {
        return passwordEditText.getText().toString();
    }

    @Override
    public void firstNameErrorText(String error)
    {
        inputFirstNameLayout.setError(error);
    }

    @Override
    public void lastNameErrorText(String error)
    {
        inputLastNameLayout.setError(error);
    }

    @Override
    public void userNameErrorText(String error)
    {
        inputUserNameLayout.setError(error);
    }

    @Override
    public void emailErrorText(String error)
    {
        inputLoginLayout.setError(error);
    }

    @Override
    public void passwordErrorText(String error)
    {
        inputPasswordLayout.setError(error);
    }

    @Override
    public void firstNameErrorEnabled(boolean enabled)
    {
        inputFirstNameLayout.setErrorEnabled(enabled);
    }

    @Override
    public void lastNameErrorEnabled(boolean enabled)
    {
        inputLastNameLayout.setErrorEnabled(enabled);
    }

    @Override
    public void userNameErrorEnabled(boolean enabled)
    {
        inputUserNameLayout.setErrorEnabled(enabled);
    }

    @Override
    public void emailErrorEnabled(boolean enabled)
    {
        inputLoginLayout.setErrorEnabled(enabled);
    }

    @Override
    public void passwordErrorEnabled(boolean enabled)
    {
        inputPasswordLayout.setErrorEnabled(enabled);
    }

    @Override
    public void progressBarVisible(boolean isVisible)
    {
        if (isVisible)
        {
            progressBar.setVisibility(View.VISIBLE);
        }
        else
        {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public String emptyFieldErrorText()
    {
        return getString(R.string.err_empty_field);
    }

    private void prepareEditTexts()
    {
        firstNameEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                firstNameErrorEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });

        lastNameEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                lastNameErrorEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });

        emailEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                emailErrorEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });

        userNameEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                userNameErrorEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                passwordErrorEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
    }
}
