package nsy209.cnam.seldesave.background.helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.ResultReceiver;

import nsy209.cnam.seldesave.background.intentService.UpdateWealthSheetIntentService;
import nsy209.cnam.seldesave.background.receiver.UpdateWealthSheetResultReceiver;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 11/07/2017.
 */

public class WealthSheetToUpdate implements IBaseToUpdate {

    /* daoFactory */
    private DaoFactory daoFactory;

    /* activity launching intent service */
    private Activity activity;

    /* data */
    private long remoteId;

    public WealthSheetToUpdate(Activity activity,DaoFactory daoFactory,long remoteId) {
        this.activity = activity;
        this.daoFactory = daoFactory;
        this.remoteId = remoteId;
    }

    @Override
    public void update(){
        Intent intent = new Intent(this.activity, UpdateWealthSheetIntentService.class);
        ResultReceiver mResultReceiver = new UpdateWealthSheetResultReceiver(new Handler(), this.activity,daoFactory);
        intent.putExtra(BackgroundConstant.RECEIVER_UPDATE_WEALTHSHEET,mResultReceiver);
        intent.putExtra(BackgroundConstant.MY_PROFILE_BEAN,remoteId);

        this.activity.startService(intent);
    }
}