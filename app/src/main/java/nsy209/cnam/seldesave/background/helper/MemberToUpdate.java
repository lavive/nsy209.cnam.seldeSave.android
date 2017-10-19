package nsy209.cnam.seldesave.background.helper;

import android.app.Activity;
import android.content.Intent;;
import android.os.Handler;
import android.os.ResultReceiver;

import nsy209.cnam.seldesave.background.intentService.UpdateMemberIntentService;
import nsy209.cnam.seldesave.background.receiver.UpdateMemberResultReceiver;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 11/07/2017.
 */

public class MemberToUpdate implements IBaseToUpdate {

    /* daoFactory */
    private DaoFactory daoFactory;

    /* activity launching intent service */
    private Activity activity;

    public MemberToUpdate(Activity activity,DaoFactory daoFactory) {
        this.activity = activity;
        this.daoFactory = daoFactory;
    }

    @Override
    public void update(){
        Intent intent = new Intent(this.activity, UpdateMemberIntentService.class);
        ResultReceiver mResultReceiver = new UpdateMemberResultReceiver(new Handler(), this.activity,daoFactory);
        intent.putExtra(BackgroundConstant.RECEIVER_UPDATE_MEMBER,mResultReceiver);

        this.activity.startService(intent);
    }
}