package nsy209.cnam.seldesave.background.intentService;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import java.util.ArrayList;
import java.util.List;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;
import nsy209.cnam.seldesave.background.webService.RetrofitBuilder;
import nsy209.cnam.seldesave.background.webService.WebService;
import nsy209.cnam.seldesave.bean.NotificationBean;
import nsy209.cnam.seldesave.background.helper.BackgroundConstant;
import nsy209.cnam.seldesave.shared.dto.NotificationsDto;
import nsy209.cnam.seldesave.shared.transform.DtoToBean;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Admin on 10/07/2017.
 */

public class UpdateNotificationIntentService extends IntentService{

        public UpdateNotificationIntentService() {
            super(UpdateNotificationIntentService.class.getName());
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            /* get receiver */
            final ResultReceiver resultReceiver = intent.getParcelableExtra(BackgroundConstant.RECEIVER_UPDATE_NOTIFICATION);

            /* get my Id */
            final long myRemoteId = intent.getLongExtra(BackgroundConstant.MY_PROFILE_BEAN, ActivityConstant.NOTEXIST);

            /* get retrofit service*/
            WebService webService = RetrofitBuilder.getClient();

            /* call checkUpdate service method */
            Call<NotificationsDto> getUpdateNotificationListCall = webService.getNotifications(myRemoteId);

            /* get informations from response */
            NotificationsDto notifications = new NotificationsDto();
            try {
                Response<NotificationsDto> getUpdateNotificationListResponse = getUpdateNotificationListCall.execute();
                notifications = getUpdateNotificationListResponse.body();
            } catch (Exception e){
                e.printStackTrace();
            }

            /* send information to receiver */
            if(!notifications.getNotifications().isEmpty()){
                deliverResultToReceiver(resultReceiver, BackgroundConstant.SUCCESS_RESULT, getResources().getString(R.string.notifications_update),
                        DtoToBean.notificationDtoToBean(notifications));
            }

        }

        private void deliverResultToReceiver(ResultReceiver resultReceiver, int resultCode, String message, List<NotificationBean> notifications) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(BackgroundConstant.RESULT_NOTIFICATION,(ArrayList<NotificationBean>) notifications);
            bundle.putString(BackgroundConstant.RECEIVER_UPDATE_NOTIFICATION, message);
            resultReceiver.send(resultCode, bundle);
        }

}

