package com.activity_sync.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import com.activity_sync.R;
import com.activity_sync.presentation.models.ApiMessage;
import com.activity_sync.presentation.services.INotificationService;
import com.activity_sync.presentation.services.IPermanentStorage;
import com.activity_sync.screens.EventDetailsScreen;
import com.activity_sync.screens.EventsScreen;
import com.activity_sync.screens.UserDetailsScreen;

public class NotificationService implements INotificationService
{
    private final Context context;
    private final NotificationManager notificationManager;
    private final IPermanentStorage permanentStorage;

    public NotificationService(Context context, NotificationManager notificationManager, IPermanentStorage permanentStorage)
    {
        this.context = context;
        this.notificationManager = notificationManager;
        this.permanentStorage = permanentStorage;
    }

    @Override
    public void handleMessage(ApiMessage apiMessage)
    {
        if (apiMessage.getMessage().equals(ApiMessage.EVENT_APPROVED))
        {
            Intent intent = new Intent(context, EventDetailsScreen.class);
            intent.putExtra(EventDetailsScreen.EVENT_ID, apiMessage.getId());
            sendNotification(context.getString(R.string.txt_event_approved_notifi_title), context.getString(R.string.txt_event_approved_notifi_content), intent);
        }
        else if(apiMessage.getMessage().equals(ApiMessage.EVENT_CANCEL))
        {
            Intent intent = new Intent(context, EventsScreen.class);
            sendNotification(context.getString(R.string.txt_event_cancel_notifi_title), context.getString(R.string.txt_event_cancel_notifi_content), intent);
        }
        else if(apiMessage.getMessage().equals(ApiMessage.EVENT_DELETE))
        {
            Intent intent = new Intent(context, EventsScreen.class);
            sendNotification(context.getString(R.string.txt_event_cancel_notifi_title), context.getString(R.string.txt_event_cancel_notifi_content), intent);
        }
        else if(apiMessage.getMessage().equals(ApiMessage.EVENT_EDITED))
        {
            Intent intent = new Intent(context, EventDetailsScreen.class);
            intent.putExtra(EventDetailsScreen.EVENT_ID, apiMessage.getId());
            sendNotification(context.getString(R.string.txt_event_edit_notifi_title), context.getString(R.string.txt_event_edit_notifi_content), intent);
        }
        else if(apiMessage.getMessage().equals(ApiMessage.EVENT_INVITE))
        {
            Intent intent = new Intent(context, EventDetailsScreen.class);
            intent.putExtra(EventDetailsScreen.EVENT_ID, apiMessage.getId());
            sendNotification(context.getString(R.string.txt_event_invite_notifi_title), context.getString(R.string.txt_event_invite_notifi_content), intent);
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

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setTicker(title)
                .setSmallIcon(android.R.drawable.ic_menu_view)
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(pendingIntent)
                .setLights(Color.BLUE, 3000, 3000)
                .setAutoCancel(true);

        if (permanentStorage.retrieveBoolean(IPermanentStorage.IS_NOTIFICATION_VIBRATION_ENABLED, IPermanentStorage.IS_NOTIFICATION_VIBRATION_ENABLED_DEFAULT))
        {
            notificationBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
        }

        if (permanentStorage.retrieveBoolean(IPermanentStorage.IS_NOTIFICATION_SOUND_ENABLED, IPermanentStorage.IS_NOTIFICATION_SOUND_ENABLED_DEFAULT))
        {
            notificationBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        }

        Notification notification = notificationBuilder.build();

        notificationManager.notify(0, notification);
    }
}
