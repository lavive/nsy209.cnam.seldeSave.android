package nsy209.cnam.seldesave.activity.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.notifications.MyNotificationsActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by lavive on 11/10/17.
 */

public class NotificationDisplay {

    public static void createNotification(Context context,String title,String text){

        // activity to trigger when notification is selected
        Intent intent = new Intent(context, MyNotificationsActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);

        //to managevibration
        long[] patternVibration = {0,200,100,200,100,200};

        // Build notification
        Notification notification = new Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(text).setSmallIcon(R.drawable.logo)
                .setContentIntent(pIntent)
                .setVibrate(patternVibration)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        // hide the notification after its selected
        notification.flags = Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, notification);

    }
}
