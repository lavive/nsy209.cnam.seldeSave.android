package nsy209.cnam.seldesave.background.intentService;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import java.util.ArrayList;

import nsy209.cnam.seldesave.activity.utils.ActivityConstant;
import nsy209.cnam.seldesave.background.helper.BackgroundConstant;
import nsy209.cnam.seldesave.background.webService.RetrofitBuilder;
import nsy209.cnam.seldesave.background.webService.WebService;
import nsy209.cnam.seldesave.bean.NotificationBean;
import nsy209.cnam.seldesave.shared.transform.BeanToDto;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by lavive on 07/10/17.
 */

public class RemoteDeleteNotificationIntentService extends IntentService {


    public RemoteDeleteNotificationIntentService() {
        super(RemoteDeleteNotificationIntentService.class.getName());
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        /* get receiver */
        final ResultReceiver resultReceiver = intent.getParcelableExtra(BackgroundConstant.RECEIVER_REMOTE_NOTIFICATION);

        /* get my Id */
        final long myRemoteId = intent.getLongExtra(BackgroundConstant.MY_PROFILE_BEAN, ActivityConstant.NOTEXIST);

        /* get notifications to delete */
        final ArrayList<NotificationBean> notificationsBean = intent.getParcelableArrayListExtra(BackgroundConstant.NOTIFICATION_BEAN);

        /* get retrofit service*/
        final WebService webService = RetrofitBuilder.getClient();

        /* call service method */
        Call getDeleteNotificationCall = webService.deleteNotificationFromMember(myRemoteId,BeanToDto.notificationBeanToDto(notificationsBean));

        /* get informations from response */
        int responseCode = 0;
        try {
            Response getDeleteNotificationResponse = getDeleteNotificationCall.execute();
            responseCode = getDeleteNotificationResponse.code();

        } catch(Exception e){
            e.printStackTrace();
        }

        /* send information to receiver */
        if (responseCode == BackgroundConstant.CODE_RESPONSE_OK) {

            deliverResultToReceiver(resultReceiver, BackgroundConstant.SUCCESS_RESULT);

        } else {

            deliverResultToReceiver(resultReceiver, BackgroundConstant.FAILURE_RESULT);
        }

    }


    private void deliverResultToReceiver(ResultReceiver resultReceiver,int resultCode) {
        Bundle bundle = new Bundle();
        resultReceiver.send(resultCode, bundle);
    }


}
