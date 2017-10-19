package nsy209.cnam.seldesave.background.receiver;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.widget.Toast;

import nsy209.cnam.seldesave.activity.utils.Connected;
import nsy209.cnam.seldesave.background.helper.BackgroundConstant;
import nsy209.cnam.seldesave.background.helper.SupplyDemandToUpdate;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 06/10/17.
 */

public class RemoteAddMySupplyDemandReceiver extends ResultReceiver {

    /* daoFactory */
    private DaoFactory daoFactory;

    /* activity */
    private Activity activity;

    public RemoteAddMySupplyDemandReceiver(Handler handler, Activity activity, DaoFactory daoFactory) {
        super(handler);
        this.activity = activity;
        this.daoFactory = daoFactory;
    }

    @Override
    protected void onReceiveResult(int resultCode, final Bundle resultData) {

        if (resultCode == BackgroundConstant.SUCCESS_RESULT) {
            /* display message toast  */
            Toast.makeText(this.activity,resultData.getString(BackgroundConstant.RECEIVER_REMOTE_MYSUPPLYDEMAND),Toast.LENGTH_LONG).show();

            /* update local base */
            if(Connected.isConnectedInternet(this.activity)) {
                SupplyDemandToUpdate supplyDemandToUpdate = new SupplyDemandToUpdate(this.activity, this.daoFactory);
                supplyDemandToUpdate.update();
            }

        } else{
            /* display message toast  */
            Toast.makeText(this.activity,resultData.getString(BackgroundConstant.RECEIVER_REMOTE_MYSUPPLYDEMAND),Toast.LENGTH_LONG).show();
        }
    }
}
