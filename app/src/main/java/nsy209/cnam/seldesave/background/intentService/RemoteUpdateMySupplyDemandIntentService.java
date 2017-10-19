package nsy209.cnam.seldesave.background.intentService;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.background.helper.BackgroundConstant;
import nsy209.cnam.seldesave.background.webService.RetrofitBuilder;
import nsy209.cnam.seldesave.background.webService.WebService;
import nsy209.cnam.seldesave.bean.SupplyDemandBean;
import nsy209.cnam.seldesave.shared.transform.BeanToDto;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by lavive on 07/10/17.
 */

public class RemoteUpdateMySupplyDemandIntentService extends IntentService {


    public RemoteUpdateMySupplyDemandIntentService() {
        super(RemoteUpdateMySupplyDemandIntentService.class.getName());
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        /* get receiver */
        final ResultReceiver resultReceiver = intent.getParcelableExtra(BackgroundConstant.RECEIVER_REMOTE_MYSUPPLYDEMAND);

        /* get my supply demand to send */
        final SupplyDemandBean supplyDemandBean = intent.getParcelableExtra(BackgroundConstant.MY_SUPPLYDEMAND_BEAN);

        /* get retrofit service*/
        final WebService webService = RetrofitBuilder.getClient();

        /* call service method */
        Call getUpdateSupplyDemandCall = webService.updateSupplyDemand(BeanToDto.supplyDemandBeanToDto(supplyDemandBean));

        /* get informations from response */
        int responseCode = 0;
        try {
            Response getUpdateSupplyDemandResponse = getUpdateSupplyDemandCall.execute();
            responseCode = getUpdateSupplyDemandResponse.code();

        } catch(Exception e){
            e.printStackTrace();
            deliverResultToReceiver(resultReceiver, BackgroundConstant.FAILURE_RESULT, getResources().getString(R.string.datas_lost));
        }

        /* send information to receiver */
        if (responseCode == BackgroundConstant.CODE_RESPONSE_OK) {

            deliverResultToReceiver(resultReceiver, BackgroundConstant.SUCCESS_RESULT, getResources().getString(R.string.datas_transferred));

        } else {

            deliverResultToReceiver(resultReceiver, BackgroundConstant.FAILURE_RESULT, getResources().getString(R.string.datas_lost));
        }

    }


    private void deliverResultToReceiver(ResultReceiver resultReceiver,int resultCode, String message) {
        Bundle bundle = new Bundle();
        bundle.putString(BackgroundConstant.RECEIVER_REMOTE_MYSUPPLYDEMAND, message);
        resultReceiver.send(resultCode, bundle);
    }


}
