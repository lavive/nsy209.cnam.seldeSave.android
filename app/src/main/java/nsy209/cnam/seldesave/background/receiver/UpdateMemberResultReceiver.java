package nsy209.cnam.seldesave.background.receiver;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.widget.Toast;

import java.util.List;
import nsy209.cnam.seldesave.background.helper.GeolocationToUpdate;
import nsy209.cnam.seldesave.bean.MemberBean;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.background.helper.BackgroundConstant;

/**
 * Created by lavive on 10/07/2017.
 */

public class UpdateMemberResultReceiver extends ResultReceiver {

    /* daoFactory */
    private DaoFactory daoFactory;

    private Activity activity;

    public UpdateMemberResultReceiver(Handler handler, Activity activity, DaoFactory daoFactory) {
        super(handler);
        this.activity = activity;
        this.daoFactory = daoFactory;
    }

    @Override
    protected void onReceiveResult(int resultCode, final Bundle resultData) {

        if (resultCode == BackgroundConstant.SUCCESS_RESULT) {
            List<MemberBean> members = resultData.getParcelableArrayList(BackgroundConstant.RESULT_MEMBER);
            daoFactory.getMemberDao().createMembers(members);
            /* display message toast  */
            Toast.makeText(this.activity,resultData.getString(BackgroundConstant.RECEIVER_UPDATE_MEMBER),Toast.LENGTH_LONG).show();

            /* update geolocation table */
            GeolocationToUpdate geolocationToUpdate = new GeolocationToUpdate(this.activity,this.daoFactory);
            geolocationToUpdate.update();
        }
    }
}
