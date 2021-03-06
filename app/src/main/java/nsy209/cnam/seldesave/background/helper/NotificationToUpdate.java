package nsy209.cnam.seldesave.background.helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.ResultReceiver;

import nsy209.cnam.seldesave.background.intentService.UpdateNotificationIntentService;
import nsy209.cnam.seldesave.background.receiver.UpdateNotificationResultReceiver;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 11/07/2017.
 */

public class NotificationToUpdate implements IBaseToUpdate {

    /* daoFactory */
    private DaoFactory daoFactory;

    /* activity launching intent service */
    private Activity activity;

    /* data */
    private long remoteId;

    public NotificationToUpdate(Activity activity,DaoFactory daoFactory,long remoteId) {
        this.activity = activity;
        this.daoFactory = daoFactory;
        this.remoteId = remoteId;
    }

    @Override
    public void update(){
        Intent intent = new Intent(this.activity, UpdateNotificationIntentService.class);
        ResultReceiver mResultReceiver = new UpdateNotificationResultReceiver(new Handler(), this.activity,daoFactory);
        intent.putExtra(BackgroundConstant.RECEIVER_UPDATE_NOTIFICATION,mResultReceiver);
        intent.putExtra(BackgroundConstant.MY_PROFILE_BEAN,remoteId);

        this.activity.startService(intent);
    }
}
