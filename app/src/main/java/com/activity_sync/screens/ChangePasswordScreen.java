package com.activity_sync.screens;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.Toast;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.presenters.ChangePasswordPresenter;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.views.IChangePasswordView;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;

public class ChangePasswordScreen extends Screen implements IChangePasswordView
{
    @Inject
    IApiService apiService;

    @Bind(R.id.cp_old_password)
    AppCompatEditText oldPasswordEditText;

    @Bind(R.id.cp_new_password)
    AppCompatEditText newPasswordEditText;

    @Bind(R.id.cp_confirm_new_password)
    AppCompatEditText confirmNewPasswordEditText;

    @Bind(R.id.cp_old_password_layout)
    TextInputLayout oldPasswordLayout;

    @Bind(R.id.cp_new_password_layout)
    TextInputLayout newPasswordLayout;

    @Bind(R.id.cp_confirm_new_password_layout)
    TextInputLayout confirmNewPasswordLayout;

    @Bind(R.id.cp_save)
    Button saveButton;

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return new ChangePasswordPresenter(this, AndroidSchedulers.mainThread(), apiService);
    }

    @Override
    protected void inject(Context screen)
    {
        App.component(this).inject(this);
    }

    public ChangePasswordScreen()
    {
        super(R.layout.change_password_screen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        prepareEditTexts();
        setTitle(R.string.title_change_password);
    }

    private void prepareEditTexts()
    {
        oldPasswordEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                oldPasswordErrorEnabled(false);
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

        newPasswordEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                newPasswordErrorEnabled(false);
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

        confirmNewPasswordEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                confirmNewPasswordErrorEnabled(false);
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

    @Override
    public void oldPasswordErrorEnabled(boolean enabled)
    {
        oldPasswordLayout.setErrorEnabled(enabled);
    }

    @Override
    public void newPasswordErrorEnabled(boolean enabled)
    {
        newPasswordLayout.setErrorEnabled(enabled);
    }

    @Override
    public void confirmNewPasswordErrorEnabled(boolean enabled)
    {
        confirmNewPasswordLayout.setErrorEnabled(enabled);
    }

    @Override
    public void oldPasswordErrorText(String error)
    {
        oldPasswordLayout.setError(error);
    }

    @Override
    public void newPasswordErrorText(String error)
    {
        newPasswordLayout.setError(error);
    }

    @Override
    public void confirmNewPasswordErrorText(String error)
    {
        confirmNewPasswordLayout.setError(error);
    }

    @Override
    public String getOldPassword()
    {
        return oldPasswordEditText.getText().toString();
    }

    @Override
    public String getNewPassword()
    {
        return newPasswordEditText.getText().toString();
    }

    @Override
    public String getConfirmNewPassword()
    {
        return confirmNewPasswordEditText.getText().toString();
    }

    @Override
    public String emptyFieldErrorText()
    {
        return getString(R.string.err_empty_field);
    }

    @Override
    public String confirmedNotMatchingErrorText()
    {
        return getString(R.string.err_confirmed_not_matching);
    }

    @Override
    public String samePasswordsErrorText()
    {
        return getString(R.string.err_same_passwords);
    }

    @Override
    public Observable onSaveClick()
    {
        return ViewObservable.clicks(saveButton);
    }

    @Override
    public void saveSucceded()
    {
        Toast.makeText(this, R.string.cp_save_succ, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveFailed()
    {
        Toast.makeText(this, R.string.cp_save_fail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void close()
    {
        this.finish();
    }
}

