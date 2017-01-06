package com.activity_sync.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.activity_sync.R;
import com.activity_sync.presentation.models.ApiMessage;
import com.activity_sync.presentation.services.INotificationService;
import com.activity_sync.screens.EventDetailsScreen;
import com.activity_sync.screens.UserDetailsScreen;

public class NotificationService implements INotificationService
{
    private final Context context;
    private final NotificationManager notificationManager;

    public NotificationService(Context context, NotificationManager notificationManager)
    {
        this.context = context;
        this.notificationManager = notificationManager;
    }

    @Override
    public void handleMessage(ApiMessage apiMessage)
    {
        if (apiMessage.getMessage().equals(ApiMessage.EVENT_APPROVED))
        {
            Intent intent = new Intent(context, EventDetailsScreen.class);
            intent.putExtra(EventDetailsScreen.EVENT_ID, apiMessage.getId());

            sendNotification("You have been approved!", "Organizer of the event approved you as a event participant. Check it right now!", intent);
        }
        else if(apiMessage.getMessage().equals(ApiMessage.EVENT_CANCEL))
        {
            Intent intent = new Intent(context, EventDetailsScreen.class);
            intent.putExtra(EventDetailsScreen.EVENT_ID, apiMessage.getId());

            sendNotification("You have been approved!", "Organizer of the event approved you as a event participant. Check it right now!", intent);
        }
        else if(apiMessage.getMessage().equals(ApiMessage.EVENT_DELETE))
        {
            Intent intent = new Intent(context, EventDetailsScreen.class);
            intent.putExtra(EventDetailsScreen.EVENT_ID, apiMessage.getId());

            sendNotification("You have been approved!", "Organizer of the event approved you as a event participant. Check it right now!", intent);
        }
        else if(apiMessage.getMessage().equals(ApiMessage.EVENT_EDITED))
        {
            Intent intent = new Intent(context, EventDetailsScreen.class);
            intent.putExtra(EventDetailsScreen.EVENT_ID, apiMessage.getId());

            sendNotification("You have been approved!", "Organizer of the event approved you as a event participant. Check it right now!", intent);
        }
        else if(apiMessage.getMessage().equals(ApiMessage.EVENT_INVITE))
        {
            Intent intent = new Intent(context, EventDetailsScreen.class);
            intent.putExtra(EventDetailsScreen.EVENT_ID, apiMessage.getId());

            sendNotification("You have been approved!", "Organizer of the event approved you as a event participant. Check it right now!", intent);
        }
        else if(apiMessage.getMessage().equals(ApiMessage.FRIEND_REQUEST))
        {

            Intent intent = new Intent(context, UserDetailsScreen.class);
            intent.putExtra(UserDetailsScreen.USER_ID, apiMessage.getId());

            sendNotification(context.getString(R.string.txt_friends_notif_title), context.getString(R.string.txt_friends_notif_content), intent);
        }
    }

    public void sendNotification(String title, String content, Intent intent)
    {
        int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, uniqueInt, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context)
                .setTicker(title)
                .setSmallIcon(android.R.drawable.ic_menu_view)
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(0, notification);
    }
}
