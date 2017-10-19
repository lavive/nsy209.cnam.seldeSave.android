package nsy209.cnam.seldesave.background.helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.ResultReceiver;

import nsy209.cnam.seldesave.background.intentService.RemoteAddMySupplyDemandIntentService;
import nsy209.cnam.seldesave.background.receiver.RemoteAddMySupplyDemandReceiver;
import nsy209.cnam.seldesave.bean.SupplyDemandBean;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 06/10/17.
 */

public class MySupplyDemandToRemoteAdd implements IBaseToUpdate {

    /* daoFactory */
    private DaoFactory daoFactory;

    /* activity launching intent service */
    private Activity activity;

    /* data to send */
    private SupplyDemandBean supplyDemandBean;

    public MySupplyDemandToRemoteAdd(Activity activity,DaoFactory daoFactory,SupplyDemandBean supplyDemandBean) {
        this.activity = activity;
        this.daoFactory = daoFactory;
        this.supplyDemandBean = supplyDemandBean;
    }

    @Override
    public void update() {

        Intent intent = new Intent(this.activity, RemoteAddMySupplyDemandIntentService.class);

        /* put receiver */
        final ResultReceiver mResultReceiver = new RemoteAddMySupplyDemandReceiver(new Handler(), this.activity, this.daoFactory);
        intent.putExtra(BackgroundConstant.RECEIVER_REMOTE_MYSUPPLYDEMAND, mResultReceiver);

        /* put my supply demand to send */
        intent.putExtra(BackgroundConstant.MY_SUPPLYDEMAND_BEAN,this.supplyDemandBean);

        this.activity.startService(intent);
    }
}
