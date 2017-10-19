package nsy209.cnam.seldesave.background.intentService;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import java.util.ArrayList;
import java.util.List;

import nsy209.cnam.seldesave.activity.utils.ActivityConstant;
import nsy209.cnam.seldesave.background.webService.RetrofitBuilder;
import nsy209.cnam.seldesave.background.webService.WebService;
import nsy209.cnam.seldesave.background.helper.BackgroundConstant;
import nsy209.cnam.seldesave.shared.dto.RestListStringDto;
import retrofit2.Call;
import retrofit2.Response;

public class CheckRemoteDataBaseIntentService extends IntentService {


    public CheckRemoteDataBaseIntentService() {
        super(CheckRemoteDataBaseIntentService.class.getName());
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        /* get receiver */
        final ResultReceiver resultReceiver = intent.getParcelableExtra(BackgroundConstant.RECEIVER_CHECK_DATABASE);

        /* get my id */
        final long myRemoteId = intent.getLongExtra(BackgroundConstant.MY_PROFILE_BEAN, ActivityConstant.NOTEXIST);

        /* get retrofit service*/
        WebService webService = RetrofitBuilder.getClient();

        /* call checkUpdate service method */
        Call<RestListStringDto> getUpdateListCall = webService.checkUpdates(myRemoteId);

        /* get informations from response */
        RestListStringDto tablesToUpdate = new RestListStringDto();
        try {
            Response<RestListStringDto> getUpdateListResponse = getUpdateListCall.execute();
            tablesToUpdate = getUpdateListResponse.body();
            if(tablesToUpdate == null){
                tablesToUpdate = new RestListStringDto();
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        /* send information to receiver */
        if(!tablesToUpdate.getListString().isEmpty()){
            deliverResultToReceiver(resultReceiver,BackgroundConstant.SUCCESS_RESULT,tablesToUpdate.getListString());
        } else {
            deliverResultToReceiver(resultReceiver,BackgroundConstant.FAILURE_RESULT,tablesToUpdate.getListString());
        }


    }


    private void deliverResultToReceiver(ResultReceiver resultReceiver,int resultCode, List<String> tablesToUpdate) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(BackgroundConstant.RESULT_UPDATE_DATABASE, (ArrayList<String>)tablesToUpdate);
        resultReceiver.send(resultCode, bundle);
    }


}
