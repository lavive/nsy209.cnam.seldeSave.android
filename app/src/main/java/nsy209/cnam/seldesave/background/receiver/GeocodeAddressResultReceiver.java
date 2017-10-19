package nsy209.cnam.seldesave.background.receiver;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.widget.Toast;

import java.util.ArrayList;

import nsy209.cnam.seldesave.background.helper.BackgroundConstant;
import nsy209.cnam.seldesave.bean.GeolocationBean;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 13/10/17.
 */

public class GeocodeAddressResultReceiver extends ResultReceiver {

    private DaoFactory daoFactory;

    private Activity activity;

    public GeocodeAddressResultReceiver(Handler handler,Activity activity,DaoFactory daoFactory) {
        super(handler);
        this.activity = activity;
        this.daoFactory = daoFactory;
    }

    @Override
    protected void onReceiveResult(int resultCode, final Bundle resultData) {

        /* get data and message */
        String message = resultData.getString(BackgroundConstant.MESSAGE);
        ArrayList<GeolocationBean> geolocationBeanList = resultData.getParcelableArrayList(BackgroundConstant.GEOLOCATION_BEAN);

        if (resultCode == BackgroundConstant.SUCCESS_RESULT) {
            /* get data */
            daoFactory.getMemberDao().createGeolocations(geolocationBeanList);
        }

        /* display message result */
        Toast.makeText(this.activity,message,Toast.LENGTH_LONG).show();

    }

}


