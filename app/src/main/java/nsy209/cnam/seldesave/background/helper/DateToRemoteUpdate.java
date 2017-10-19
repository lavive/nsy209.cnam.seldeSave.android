package nsy209.cnam.seldesave.background.helper;

import android.app.Activity;
import android.content.Intent;

import nsy209.cnam.seldesave.background.intentService.RemoteUpdateDateIntentService;

/**
 * Created by lavive on 09/10/17.
 */

public class DateToRemoteUpdate implements IBaseToUpdate {

    /* activity launching intent service */
    private Activity activity;

    /* data */
    private long remoteId;

    public DateToRemoteUpdate(Activity activity,long remoteId) {
        this.activity = activity;
        this.remoteId = remoteId;
    }

    @Override
    public void update(){
        Intent intent = new Intent(this.activity, RemoteUpdateDateIntentService.class);
        intent.putExtra(BackgroundConstant.MY_PROFILE_BEAN,remoteId);

        this.activity.startService(intent);
    }
}
