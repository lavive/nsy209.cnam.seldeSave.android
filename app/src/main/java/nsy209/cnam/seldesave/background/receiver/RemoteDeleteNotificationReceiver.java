package nsy209.cnam.seldesave.background.receiver;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import nsy209.cnam.seldesave.background.helper.BackgroundConstant;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 07/10/17.
 */

public class RemoteDeleteNotificationReceiver extends ResultReceiver {

    /* daoFactory */
    private DaoFactory daoFactory;

    /* activity */
    private Activity activity;

    public RemoteDeleteNotificationReceiver(Handler handler, Activity activity, DaoFactory daoFactory) {
        super(handler);
        this.activity = activity;
        this.daoFactory = daoFactory;
    }

    @Override
    protected void onReceiveResult(int resultCode, final Bundle resultData) {

        if (resultCode == BackgroundConstant.SUCCESS_RESULT) {
            /* delete notifications from buffer  */
            daoFactory.getMyProfileDao().deleteNotificationsFromBuffer();

        }
    }
}
