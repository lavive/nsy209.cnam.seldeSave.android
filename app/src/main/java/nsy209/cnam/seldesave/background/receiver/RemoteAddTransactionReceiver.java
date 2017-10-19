package nsy209.cnam.seldesave.background.receiver;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.widget.Toast;

import nsy209.cnam.seldesave.activity.utils.Connected;
import nsy209.cnam.seldesave.background.helper.BackgroundConstant;
import nsy209.cnam.seldesave.background.helper.WealthSheetToUpdate;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 07/10/17.
 */

public class RemoteAddTransactionReceiver extends ResultReceiver {

    /* daoFactory */
    private DaoFactory daoFactory;

    /* activity */
    private Activity activity;

    public RemoteAddTransactionReceiver(Handler handler, Activity activity, DaoFactory daoFactory) {
        super(handler);
        this.activity = activity;
        this.daoFactory = daoFactory;
    }

    @Override
    protected void onReceiveResult(int resultCode, final Bundle resultData) {

        if (resultCode == BackgroundConstant.SUCCESS_RESULT) {
            /* display message toast  */
            Toast.makeText(this.activity,resultData.getString(BackgroundConstant.RECEIVER_REMOTE_TRANSACTION),Toast.LENGTH_LONG).show();

            /* update local base */
            if(Connected.isConnectedInternet(this.activity)) {
                long remoteId = this.daoFactory.getMyProfileDao().getMyProfile().getRemote_id();
                WealthSheetToUpdate wealthSheetToUpdate = new WealthSheetToUpdate(this.activity, this.daoFactory,remoteId);
                wealthSheetToUpdate.update();
            }

        } else{
            /* display message toast  */
            Toast.makeText(this.activity,resultData.getString(BackgroundConstant.RECEIVER_REMOTE_TRANSACTION),Toast.LENGTH_LONG).show();
        }
    }
}
