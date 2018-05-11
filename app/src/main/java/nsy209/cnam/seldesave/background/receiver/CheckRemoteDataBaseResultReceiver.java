package nsy209.cnam.seldesave.background.receiver;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import java.util.ArrayList;
import java.util.List;

import nsy209.cnam.seldesave.background.helper.BaseToUpdate;
import nsy209.cnam.seldesave.background.helper.BackgroundConstant;
import nsy209.cnam.seldesave.bean.SupplyDemandBean;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 10/07/2017.
 */

public class CheckRemoteDataBaseResultReceiver extends ResultReceiver {

    private DaoFactory daoFactory;

    private Activity activity;

    public CheckRemoteDataBaseResultReceiver(Handler handler,Activity activity,DaoFactory daoFactory) {
        super(handler);
        this.activity = activity;
        this.daoFactory = daoFactory;
    }

    @Override
    protected void onReceiveResult(int resultCode, final Bundle resultData) {

        List<String> tablesToUpdate = new ArrayList<String>();

        if (resultCode == BackgroundConstant.SUCCESS_RESULT) {
            /* launch update database service */
            tablesToUpdate = resultData.getStringArrayList(BackgroundConstant.RESULT_UPDATE_DATABASE);
        }
        /* check if server need to update */
        List<SupplyDemandBean> mySupplyDemandBeanServer = daoFactory.getSupplyDemandDao().getMySuppliesDemands();
        List<SupplyDemandBean> mySupplyDemandBeanLocal = daoFactory.getMyProfileDao().getMySuppliesDemands();
        if(!supplyDemandListEqual(mySupplyDemandBeanLocal,mySupplyDemandBeanServer)) {
            tablesToUpdate.add(BackgroundConstant.MY_SUPPLYDEMAND_TABLE);
        }
        if(!daoFactory.getMyProfileDao().getMyNotificationsFromBuffer().isEmpty()){
            tablesToUpdate.add(BackgroundConstant.NOTIFICATION_BUFFER_TABLE);
        }
        if(!daoFactory.getTransactionDao().getNewTransactions().isEmpty()){
            tablesToUpdate.add(BackgroundConstant.NEW_TRANSACTION_TABLE);
        }

        (new BaseToUpdate(tablesToUpdate,this.activity,this.daoFactory)).update();

    }

    /* helper method */
    private boolean supplyDemandListEqual(List<SupplyDemandBean> supplyDemandBeanList1,List<SupplyDemandBean> supplyDemandBeanList2){
        if(supplyDemandBeanList1.size() != supplyDemandBeanList2.size()){
            return false;
        }
        boolean isEqual = true;
        for(SupplyDemandBean supplyDemandBean1:supplyDemandBeanList1){
            boolean isEqualInter = false;
            for(SupplyDemandBean supplyDemandBean2:supplyDemandBeanList2){
                if(supplyDemandBean1.equals(supplyDemandBean2)){
                    isEqualInter = true;
                    break;
                }
            }
            if(!isEqualInter){
                isEqual = false;
                break;
            }
        }
        return isEqual;
    }
}


