package nsy209.cnam.seldesave.activity.notifications;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.home.HomeActivity;
import nsy209.cnam.seldesave.activity.helper.ConfirmActivity;
import nsy209.cnam.seldesave.activity.utils.Connected;
import nsy209.cnam.seldesave.adapter.NotificationsAdapter;
import nsy209.cnam.seldesave.bean.NotificationBean;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.models.NotificationModel;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;

public class MyNotificationsActivity extends AppCompatActivity implements View.OnClickListener,Observer {

    /* daoFactory */
    private DaoFactory daoFactory;

    /* controller */
    private ImageButton delete;

    /* view */
    ListView notificationsListView;

    /* model */
    private NotificationModel notificationModel;

    /* adapter */
    private NotificationsAdapter notificationsAdapter;

    /* datas (notifications id to delete) */
    private List<Long> ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notification_display);

        /* database connexion */
        daoFactory = DaoFactory.getInstance(this);
        daoFactory.open();

        /* controller */
        delete = (ImageButton) findViewById(R.id.deleteNotification);
        delete.setOnClickListener(this);

        /* view */
        notificationsListView = (ListView) findViewById(R.id.notificationList);

        /* datas */
        long[] idArray = getIntent().getLongArrayExtra(ActivityConstant.KEY_INTENT_IDS);
        if(idArray != null){
            ids = new ArrayList<Long>();
            for(long id: idArray){
                ids.add(id);
            }
        }

        /* model */
        notificationModel = new NotificationModel();
        notificationModel.addObserver(this);
        notificationModel.onCreate(daoFactory);

    }

    @Override
    public void onClick(View view) {
        if(view == delete){
            /* delete selected notification */
            long[] notifications_id = new long[notificationsAdapter.getNotificationsBeanChecked().size()];
            int i = 0;
            for(NotificationBean notificationBean:notificationsAdapter.getNotificationsBeanChecked()){
                notifications_id[i] = notificationBean.getId();
                i++;
            }
            if(notifications_id.length != 0) {
                Intent intent = new Intent(this, ConfirmActivity.class);
                intent.putExtra(ActivityConstant.KEY_INTENT_MY_SUPPLYDEMAND, false);
                intent.putExtra(ActivityConstant.KEY_INTENT_NOTIFICATIONS, true);
                intent.putExtra(ActivityConstant.KEY_INTENT_FILTER, false);
                intent.putExtra(ActivityConstant.KEY_INTENT_PAYMENT, false);
                intent.putExtra(ActivityConstant.KEY_INTENT_IDS, notifications_id);
                startActivityForResult(intent, ActivityConstant.REQUEST_EXIT);
            } else{
                Toast.makeText(this,getString(R.string.error_my_notification_delete),Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /* finish this activity if result ok */
        if (requestCode == ActivityConstant.REQUEST_EXIT) {
            if (resultCode == RESULT_OK) {
                this.finish();

            }
        }
    }

    @Override
    public void update(Observable observable,Object object){
        /* update view with adapter */
        notificationsAdapter = new NotificationsAdapter(MyNotificationsActivity.this,((NotificationModel) observable).getNotifications(),ids);
        notificationsListView.setAdapter(notificationsAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        /* store values */
        if(!notificationsAdapter.getNotificationsBeanChecked().isEmpty()) {
            long[] idArray = new long[notificationsAdapter.getNotificationsBeanChecked().size()];
            int i = 0;
            for (NotificationBean notificationBean : notificationsAdapter.getNotificationsBeanChecked()) {
                idArray[i] = notificationBean.getId();
                i++;
            }
            savedInstanceState.putLongArray(ActivityConstant.KEY_INTENT_IDS, idArray);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){

        long[] idArray = savedInstanceState.getLongArray(ActivityConstant.KEY_INTENT_IDS);
        ids = new ArrayList<Long>();
        if(idArray != null) {
            /* restore ids */
            for (long id : idArray) {
                ids.add(Long.valueOf(id));
            }
        }
        /* update */
        notificationModel.onCreate(daoFactory);

    }


    @Override
    protected void onResume() {
        daoFactory.open();
        super.onResume();

        /* draw attention to network connection default */
        if (!Connected.isConnectedToInternet(this)) {
            Toast.makeText(this,getString(R.string.no_connection_update_fail),Toast.LENGTH_LONG).show();
        }
        HomeActivity.isInForeGround = false;
    }

    @Override
    protected void onPause() {
        daoFactory.open();
        /* notifications have been reed before pause */
        for(NotificationBean notificationBean:notificationsAdapter.getNotificationsBean()){
            daoFactory.getMyProfileDao().isRead(notificationBean);
        }
        daoFactory.close();
        super.onPause();
        HomeActivity.isInForeGround = true;
    }
}
