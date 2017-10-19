package nsy209.cnam.seldesave.background.intentService;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import java.util.ArrayList;
import java.util.List;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.background.webService.RetrofitBuilder;
import nsy209.cnam.seldesave.background.webService.WebService;
import nsy209.cnam.seldesave.bean.SupplyDemandBean;
import nsy209.cnam.seldesave.background.helper.BackgroundConstant;
import nsy209.cnam.seldesave.shared.dto.SuppliesDemandsDto;
import nsy209.cnam.seldesave.shared.transform.DtoToBean;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by lavive on 10/07/2017.
 */

public class UpdateSupplyDemandIntentService extends IntentService {

    public UpdateSupplyDemandIntentService() {
        super(UpdateSupplyDemandIntentService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        /* get receiver */
        final ResultReceiver resultReceiver = intent.getParcelableExtra(BackgroundConstant.RECEIVER_UPDATE_SUPPLYDEMAND);

        /* get retrofit service*/
        WebService webService = RetrofitBuilder.getClient();

        /* call  service method */
        Call<SuppliesDemandsDto> getUpdateSupplyDemandListCall = webService.getAllSuppliesDemands();

        /* get informations from response */
        SuppliesDemandsDto supplyDemands = new SuppliesDemandsDto();
        try {
            Response<SuppliesDemandsDto> getUpdateSupplyDemandListResponse = getUpdateSupplyDemandListCall.execute();
            supplyDemands = getUpdateSupplyDemandListResponse.body();
        } catch (Exception e){
            e.printStackTrace();
        }

        /* send information to receiver */
        if(!supplyDemands.getSuppliesDemands().isEmpty()){
            deliverResultToReceiver(resultReceiver, BackgroundConstant.SUCCESS_RESULT, getResources().getString(R.string.suppliesDemands_update),
                    DtoToBean.supplyDemandDtoToBean(supplyDemands));
        }

    }


    private void deliverResultToReceiver(ResultReceiver resultReceiver, int resultCode, String message, List<SupplyDemandBean> supplyDemands) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(BackgroundConstant.RESULT_SUPPLYDEMAND,(ArrayList<SupplyDemandBean>) supplyDemands);
        bundle.putString(BackgroundConstant.RECEIVER_UPDATE_SUPPLYDEMAND, message);
        resultReceiver.send(resultCode, bundle);
    }

}