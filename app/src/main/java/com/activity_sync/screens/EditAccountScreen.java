package com.activity_sync.screens;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.presenters.EditAccountPresenter;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.views.IEditAccountView;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;

public class EditAccountScreen extends Screen implements IEditAccountView
{
    @Inject
    IApiService apiService;

    @Bind(R.id.ea_first_name)
    AppCompatEditText firstNameEditText;

    @Bind(R.id.ea_last_name)
    AppCompatEditText lastNameEditText;

    @Bind(R.id.ea_user_name)
    TextView userNameTextView;

    @Bind(R.id.ea_email)
    AppCompatEditText emailEditText;

    @Bind(R.id.ea_signature)
    AppCompatEditText signatureEditText;

    @Bind(R.id.ea_save)
    Button saveButton;

    @Bind(R.id.ea_first_name_layout)
    TextInputLayout firstNameLayout;

    @Bind(R.id.ea_last_name_layout)
    TextInputLayout lastNameLayout;

    @Bind(R.id.ea_email_layout)
    TextInputLayout emailLayout;

    public static final String USER_DETAILS = "user_details";

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        User user = (User) getIntent().getSerializableExtra(USER_DETAILS);
        return new EditAccountPresenter(this, AndroidSchedulers.mainThread(), apiService, user);
    }

    @Override
    protected void inject(Context screen)
    {
        App.component(this).inject(this);
    }

    public EditAccountScreen()
    {
        super(R.layout.edit_account_screen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        prepareEditTexts();
        setTitle(R.string.title_edit_account);
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
    }

    @Override
    public void firstNameErrorEnabled(boolean enabled)
    {
        firstNameLayout.setErrorEnabled(enabled);
    }

    @Override
    public void lastNameErrorEnabled(boolean enabled)
    {
        lastNameLayout.setErrorEnabled(enabled);
    }

    @Override
    public void emailErrorEnabled(boolean enabled)
    {
        emailLayout.setErrorEnabled(enabled);
    }

    @Override
    public void firstNameErrorText(String error)
    {
        firstNameLayout.setError(error);
    }

    @Override
    public void lastNameErrorText(String error)
    {
        lastNameLayout.setError(error);
    }

    @Override
    public void emailErrorText(String error)
    {
        emailLayout.setError(error);
    }

    @Override
    public Observable onSaveClick()
    {
        return ViewObservable.clicks(saveButton);
    }

    @Override
    public void saveSucceded()
    {
        Toast.makeText(this, R.string.ea_save_succ, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveFailed()
    {
        Toast.makeText(this, R.string.ea_save_fail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void close()
    {
        this.finish();
    }

    @Override
    public void setUserUpdateDetails(User user)
    {
        firstNameEditText.setText(user.getName());
        lastNameEditText.setText(user.getSurname());
        userNameTextView.setText(user.getUsername());
        emailEditText.setText(user.getEmail());
        signatureEditText.setText(user.getSignature());
    }

    @Override
    public String emptyFieldErrorText()
    {
        return getString(R.string.err_empty_field);
    }

    @Override
    public String getFirstName()
    {
        return firstNameEditText.getText().toString();
    }

    @Override
    public String getLastName()
    {
        return lastNameEditText.getText().toString();
    }

    @Override
    public String getEmail()
    {
        return emailEditText.getText().toString();
    }

    @Override
    public String getSignature()
    {
        return signatureEditText.getText().toString();
    }
}
