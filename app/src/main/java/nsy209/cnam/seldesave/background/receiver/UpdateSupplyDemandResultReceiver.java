package nsy209.cnam.seldesave.background.receiver;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.widget.Toast;

import java.util.List;
import nsy209.cnam.seldesave.bean.SupplyDemandBean;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.background.helper.BackgroundConstant;

/**
 * Created by lavive on 11/07/2017.
 */

public class UpdateSupplyDemandResultReceiver extends ResultReceiver {

    /* daoFactory */
    private DaoFactory daoFactory;

    private Activity activity;

    public UpdateSupplyDemandResultReceiver(Handler handler, Activity activity, DaoFactory daoFactory) {
        super(handler);
        this.activity = activity;
        this.daoFactory = daoFactory;
    }

    @Override
    protected void onReceiveResult(int resultCode, final Bundle resultData) {

        if (resultCode == BackgroundConstant.SUCCESS_RESULT) {
            List<SupplyDemandBean> suppliesDemands = resultData.getParcelableArrayList(BackgroundConstant.RESULT_SUPPLYDEMAND);
            daoFactory.getSupplyDemandDao().createSuppliesDemands(suppliesDemands);
            /* display message toast  */
            Toast.makeText(this.activity,resultData.getString(BackgroundConstant.RECEIVER_UPDATE_SUPPLYDEMAND),Toast.LENGTH_LONG).show();
        }
    }
}