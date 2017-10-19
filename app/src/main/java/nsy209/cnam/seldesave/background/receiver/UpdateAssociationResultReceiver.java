package nsy209.cnam.seldesave.background.receiver;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.widget.Toast;
import nsy209.cnam.seldesave.bean.AssociationBean;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.background.helper.BackgroundConstant;

/**
 * Created by lavive on 10/07/2017.
 */

public class UpdateAssociationResultReceiver extends ResultReceiver {

    /* daoFactory */
    private DaoFactory daoFactory;

    private Activity activity;

    public UpdateAssociationResultReceiver(Handler handler, Activity activity, DaoFactory daoFactory) {
        super(handler);
        this.activity = activity;
        this.daoFactory = daoFactory;
    }

    @Override
    protected void onReceiveResult(int resultCode, final Bundle resultData) {

        if (resultCode == BackgroundConstant.SUCCESS_RESULT) {
            final AssociationBean associationBean = resultData.getParcelable(BackgroundConstant.RESULT_ASSOCIATION);
            daoFactory.getAssociationDao().createAssociation(associationBean);
            /* display message toast  */
            Toast.makeText(this.activity,resultData.getString(BackgroundConstant.RECEIVER_UPDATE_ASSOCIATION),Toast.LENGTH_LONG).show();
        }
    }
}
