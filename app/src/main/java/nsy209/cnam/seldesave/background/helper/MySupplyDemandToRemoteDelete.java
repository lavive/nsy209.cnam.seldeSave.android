package nsy209.cnam.seldesave.background.helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.ResultReceiver;

import nsy209.cnam.seldesave.background.intentService.RemoteDeleteMySupplyDemandIntentService;
import nsy209.cnam.seldesave.background.receiver.RemoteDeleteMySupplyDemandReceiver;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 07/10/17.
 */

public class MySupplyDemandToRemoteDelete implements IBaseToUpdate {

    /* daoFactory */
    private DaoFactory daoFactory;

    /* activity launching intent service */
    private Activity activity;

    /* data to send */
    private long supplyDemandBeanRemoteId;

    public MySupplyDemandToRemoteDelete(Activity activity,DaoFactory daoFactory,long supplyDemandBeanRemoteId) {
        this.activity = activity;
        this.daoFactory = daoFactory;
        this.supplyDemandBeanRemoteId = supplyDemandBeanRemoteId;
    }

    @Override
    public void update() {

        Intent intent = new Intent(this.activity, RemoteDeleteMySupplyDemandIntentService.class);

        /* put receiver */
        final ResultReceiver mResultReceiver = new RemoteDeleteMySupplyDemandReceiver(new Handler(), this.activity, this.daoFactory);
        intent.putExtra(BackgroundConstant.RECEIVER_REMOTE_MYSUPPLYDEMAND, mResultReceiver);

        /* put my supply demand to send */
        intent.putExtra(BackgroundConstant.MY_SUPPLYDEMAND_BEAN,this.supplyDemandBeanRemoteId);

        this.activity.startService(intent);
    }
}
