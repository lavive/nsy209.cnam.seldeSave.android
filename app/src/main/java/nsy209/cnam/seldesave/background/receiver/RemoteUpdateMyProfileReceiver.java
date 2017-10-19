package nsy209.cnam.seldesave.background.receiver;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.widget.Toast;

import nsy209.cnam.seldesave.background.helper.BackgroundConstant;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 04/10/17.
 */

public class RemoteUpdateMyProfileReceiver extends ResultReceiver {

    /* daoFactory */
    private DaoFactory daoFactory;

    /* activity */
    private Activity activity;

    public RemoteUpdateMyProfileReceiver(Handler handler, Activity activity, DaoFactory daoFactory) {
        super(handler);
        this.activity = activity;
        this.daoFactory = daoFactory;
    }

    @Override
    protected void onReceiveResult(int resultCode, final Bundle resultData) {

        if (resultCode == BackgroundConstant.SUCCESS_RESULT) {
            /* delete my profile from buffer  */
            final long[] bufferIds = resultData.getLongArray(BackgroundConstant.MY_PROFILE_BEAN);
            for(long id:bufferIds) {
                daoFactory.getMyProfileDao().deleteProfileFromBuffer(id);
            }
            /* display message toast  */
            Toast.makeText(this.activity,resultData.getString(BackgroundConstant.RECEIVER_REMOTE_MYPROFILE),Toast.LENGTH_LONG).show();

        } else{
            /* display message toast  */
            Toast.makeText(this.activity,resultData.getString(BackgroundConstant.RECEIVER_REMOTE_MYPROFILE),Toast.LENGTH_LONG).show();
        }
    }
}
