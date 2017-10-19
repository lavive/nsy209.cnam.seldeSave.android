package nsy209.cnam.seldesave.background.helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.ResultReceiver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nsy209.cnam.seldesave.background.intentService.RemoteUpdateMyProfileIntentService;
import nsy209.cnam.seldesave.background.receiver.RemoteUpdateMyProfileReceiver;
import nsy209.cnam.seldesave.bean.MyProfileBean;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 04/10/17.
 */

public class MyProfileToRemoteUpdate implements IBaseToUpdate {

    /* daoFactory */
    private DaoFactory daoFactory;

    /* activity launching intent service */
    private Activity activity;

    public MyProfileToRemoteUpdate(Activity activity,DaoFactory daoFactory) {
        this.activity = activity;
        this.daoFactory = daoFactory;
    }

    @Override
    public void update() {

        Intent intent = new Intent(this.activity, RemoteUpdateMyProfileIntentService.class);

        /* put receiver */
        final ResultReceiver mResultReceiver = new RemoteUpdateMyProfileReceiver(new Handler(), this.activity,daoFactory);
        intent.putExtra(BackgroundConstant.RECEIVER_REMOTE_MYPROFILE, mResultReceiver);

        /* put my profile to send */
        ArrayList<MyProfileBean> myProfilesBeanSorted = new ArrayList<MyProfileBean>();
        List<MyProfileBean> myProfilesBean = daoFactory.getMyProfileDao().getAllMyProfileBuffer();
        Collections.sort(myProfilesBean); //to apply modification in the right order
        myProfilesBeanSorted.addAll(myProfilesBean);
        intent.putParcelableArrayListExtra(BackgroundConstant.MY_PROFILE_BEAN,myProfilesBeanSorted);

        this.activity.startService(intent);
    }
}
