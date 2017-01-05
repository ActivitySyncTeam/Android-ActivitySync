package com.activity_sync.custom;

import android.content.Context;
import android.widget.Toast;

import com.activity_sync.R;
import com.activity_sync.presentation.services.IErrorHandler;

import java.net.HttpURLConnection;

import retrofit2.adapter.rxjava.HttpException;

public class ErrorHandler implements IErrorHandler
{
    Context context;

    public ErrorHandler(Context context)
    {
        this.context = context;
    }

    @Override
    public void handleError(Throwable throwable)
    {
        if (throwable instanceof HttpException)
        {
            int errorCode = ((HttpException) throwable).code();

            if (errorCode == HttpURLConnection.HTTP_UNAUTHORIZED)
            {
                Toast.makeText(context, context.getString(R.string.err_authorization), Toast.LENGTH_LONG).show();
            }
            else if (errorCode == HttpURLConnection.HTTP_NOT_ACCEPTABLE && errorCode == HttpURLConnection.HTTP_BAD_METHOD)
            {
                Toast.makeText(context, R.string.err_permission_denied, Toast.LENGTH_LONG).show();
            }
            else if (errorCode == HttpURLConnection.HTTP_INTERNAL_ERROR && errorCode == HttpURLConnection.HTTP_GATEWAY_TIMEOUT)
            {
                Toast.makeText(context, R.string.err_servers_cannot_be_reached, Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(context, R.string.err_something_went_wrong, Toast.LENGTH_LONG).show();
            }
        }
    }
}
