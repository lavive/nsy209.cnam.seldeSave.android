package nsy209.cnam.seldesave.background.receiver;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.widget.Toast;

import java.util.List;
import nsy209.cnam.seldesave.activity.utils.NotificationDisplay;
import nsy209.cnam.seldesave.bean.NotificationBean;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.background.helper.BackgroundConstant;

/**
 * Created by lavive on 11/07/2017.
 */

public class UpdateNotificationResultReceiver extends ResultReceiver {

    /* daoFactory */
    private DaoFactory daoFactory;

    private Activity activity;

    public UpdateNotificationResultReceiver(Handler handler, Activity activity, DaoFactory daoFactory) {
        super(handler);
        this.activity = activity;
        this.daoFactory = daoFactory;
    }

    @Override
    protected void onReceiveResult(int resultCode, final Bundle resultData) {

        if (resultCode == BackgroundConstant.SUCCESS_RESULT) {
            List<NotificationBean> notifications = resultData.getParcelableArrayList(BackgroundConstant.RESULT_NOTIFICATION);
            List<NotificationBean> notificationsFiltered = NotificationBean.applyFilters(notifications,daoFactory/*daoFactory.getMyProfileDao().getMyFilter()*/);
            daoFactory.getMyProfileDao().addNotifications(notificationsFiltered);
            daoFactory.getMyProfileDao().addNotificationsToBuffer(notifications);
            /* display notification and message toast  */
            for(NotificationBean notificationBean:notificationsFiltered){
                NotificationDisplay.createNotification(this.activity,notificationBean.getTitle(),notificationBean.getText());
            }
            Toast.makeText(this.activity,resultData.getString(BackgroundConstant.RECEIVER_UPDATE_NOTIFICATION),Toast.LENGTH_LONG).show();
        }
    }

}