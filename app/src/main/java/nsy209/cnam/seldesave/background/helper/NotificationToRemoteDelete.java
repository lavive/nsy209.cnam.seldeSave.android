package nsy209.cnam.seldesave.background.helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.ResultReceiver;

import java.util.ArrayList;

import nsy209.cnam.seldesave.background.intentService.RemoteDeleteNotificationIntentService;
import nsy209.cnam.seldesave.background.receiver.RemoteDeleteNotificationReceiver;
import nsy209.cnam.seldesave.bean.NotificationBean;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 07/10/17.
 */

public class NotificationToRemoteDelete implements IBaseToUpdate {

    /* daoFactory */
    private DaoFactory daoFactory;

    /* activity launching intent service */
    private Activity activity;

    /* data */
    private long remoteId;

    public NotificationToRemoteDelete(Activity activity,DaoFactory daoFactory,long remoteId) {
        this.activity = activity;
        this.daoFactory = daoFactory;
        this.remoteId = remoteId;
    }

    @Override
    public void update() {

        Intent intent = new Intent(this.activity, RemoteDeleteNotificationIntentService.class);

        /* put receiver */
        final ResultReceiver mResultReceiver = new RemoteDeleteNotificationReceiver(new Handler(), this.activity,daoFactory);
        intent.putExtra(BackgroundConstant.RECEIVER_REMOTE_NOTIFICATION, mResultReceiver);
        intent.putExtra(BackgroundConstant.MY_PROFILE_BEAN,remoteId);
        intent.putParcelableArrayListExtra(BackgroundConstant.NOTIFICATION_BEAN,(ArrayList< NotificationBean>)daoFactory.getMyProfileDao().getMyNotificationsFromBuffer());

        this.activity.startService(intent);
    }
}
