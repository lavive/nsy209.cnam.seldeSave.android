package nsy209.cnam.seldesave.background.intentService;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import java.util.ArrayList;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.background.webService.RetrofitBuilder;
import nsy209.cnam.seldesave.background.webService.WebService;
import nsy209.cnam.seldesave.background.helper.BackgroundConstant;
import nsy209.cnam.seldesave.bean.MyProfileBean;
import nsy209.cnam.seldesave.shared.transform.BeanToDto;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by lavive on 11/07/2017.
 */

public class RemoteUpdateMyProfileIntentService extends IntentService {


    public RemoteUpdateMyProfileIntentService() {
        super(RemoteUpdateMyProfileIntentService.class.getName());
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        /* get receiver */
        final ResultReceiver resultReceiver = intent.getParcelableExtra(BackgroundConstant.RECEIVER_REMOTE_MYPROFILE);

        /* get my profile */
        final ArrayList<MyProfileBean> myProfilesBean = intent.getParcelableArrayListExtra(BackgroundConstant.MY_PROFILE_BEAN);

        /* get retrofit service*/
        final WebService webService = RetrofitBuilder.getClient();

        /* profile buffer id 's array */
        long[] bufferIds = new long[myProfilesBean.size()];
        for(int i =0;i<bufferIds.length;i++){
            bufferIds[i] = myProfilesBean.get(i).getId();
        }

        /* call service method */
        Call getUpdateMemberCall = webService.updateProfile(BeanToDto.myProfileBeanToDto(myProfilesBean));

        /* get informations from response */
        int responseCode = 0;
        try {
            Response getUpdateMemberResponse = getUpdateMemberCall.execute();
            responseCode = getUpdateMemberResponse.code();

        } catch(Exception e){
                e.printStackTrace();
                deliverResultToReceiver(resultReceiver, BackgroundConstant.FAILURE_RESULT, getResources().getString(R.string.datas_lost), null);
        }

        /* send information to receiver */
        if (responseCode == BackgroundConstant.CODE_RESPONSE_OK) {

            deliverResultToReceiver(resultReceiver, BackgroundConstant.SUCCESS_RESULT, getResources().getString(R.string.datas_transferred), bufferIds);

        } else {

            deliverResultToReceiver(resultReceiver, BackgroundConstant.FAILURE_RESULT, getResources().getString(R.string.datas_lost), null);
        }

    }


    private void deliverResultToReceiver(ResultReceiver resultReceiver,int resultCode, String message,long[] bufferIds) {
        Bundle bundle = new Bundle();
        bundle.putString(BackgroundConstant.RECEIVER_REMOTE_MYPROFILE, message);
        bundle.putLongArray(BackgroundConstant.MY_PROFILE_BEAN,bufferIds);
        resultReceiver.send(resultCode, bundle);
    }


}
