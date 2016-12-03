package com.activity_sync.screens;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.activity_sync.R;
import com.activity_sync.presentation.models.UserUpdate;
import com.activity_sync.presentation.presenters.EditAccountPresenter;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.views.IEditAccountView;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;

public class EditAccountScreen extends Screen implements IEditAccountView
{
    @Bind(R.id.ea_first_name)
    AppCompatEditText firstNameEditText;

    @Bind(R.id.ea_last_name)
    AppCompatEditText lastNameEditText;

    @Bind(R.id.ea_user_name)
    TextView userNameTextView;

    @Bind(R.id.ea_email)
    AppCompatEditText emailEditText;

    @Bind(R.id.ea_password)
    AppCompatEditText passwordEditText;

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

    @Bind(R.id.ea_password_layout)
    TextInputLayout passwordLayout;

    public static final String USER_UPDATE_DETAILS = "user_update_details";
    private UserUpdate userUpdateDetails;

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        this.userUpdateDetails = (UserUpdate) getIntent().getSerializableExtra(USER_UPDATE_DETAILS);
        return new EditAccountPresenter(this, AndroidSchedulers.mainThread());
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
        setUserUpdateDetails();
        setTitle(R.string.title_edit_account);
    }

    private void setUserUpdateDetails()
    {
        firstNameEditText.setText(userUpdateDetails.getFirstName());
        lastNameEditText.setText(userUpdateDetails.getUserName());
        userNameTextView.setText(userUpdateDetails.getUserName());
        emailEditText.setText(userUpdateDetails.getEmail());
        signatureEditText.setText(userUpdateDetails.getSignature());
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
    public void passwordErrorEnabled(boolean enabled)
    {
        passwordLayout.setErrorEnabled(enabled);
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
    public void passwordErrorText(String error)
    {
        passwordLayout.setError(error);
    }

    @Override
    public UserUpdate getUserUpdateDetails()
    {
        userUpdateDetails.setFirstName(firstNameEditText.getText().toString());
        userUpdateDetails.setLastName(lastNameEditText.getText().toString());
        userUpdateDetails.setEmail(emailEditText.getText().toString());
        userUpdateDetails.setSignature(signatureEditText.getText().toString());
        return userUpdateDetails;
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
    public void saveFailed(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void close()
    {
        this.finish();
    }

    @Override
    public String emptyFieldErrorText()
    {
        return getString(R.string.err_empty_field);
    }

    @Override
    public String getPassword()
    {
        return passwordEditText.getText().toString();
    }
}
