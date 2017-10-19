package nsy209.cnam.seldesave.background.intentService;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.ResultReceiver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;
import nsy209.cnam.seldesave.background.helper.BackgroundConstant;
import nsy209.cnam.seldesave.bean.GeolocationBean;


/**
 * Created by lavive on 13/10/17.
 */

public class GeocodeAddressIntentService extends IntentService {

    public GeocodeAddressIntentService() {
        super(GeocodeAddressIntentService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        /* get receiver */
        final ResultReceiver resultReceiver = intent.getParcelableExtra(BackgroundConstant.RECEIVER_GEOCODE);

        /* get data */
        final ArrayList<String> addressToGeocode = intent.getStringArrayListExtra(BackgroundConstant.GEOLOCATION_BEAN);

        /* get Address */
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String errorMessage = "";
        List<GeolocationBean> geolocationBeanList = new ArrayList<GeolocationBean>();
        try {
            for(String geolocation:addressToGeocode) {
                /* parse information */
                String[] infos = geolocation.split(ActivityConstant.REGEX);
                GeolocationBean geolocationBean = new GeolocationBean();
                geolocationBean.setMemberId(Long.parseLong(infos[0]));
                Address address = geocoder.getFromLocationName(infos[1], 1).get(0);
                geolocationBean.setLatitude(address.getLatitude());
                geolocationBean.setLongitude(address.getLongitude());
                geolocationBeanList.add(geolocationBean);
            }
        } catch (IOException e) {
            errorMessage = getString(R.string.service_not_available);
            e.printStackTrace();
        }

        if (geolocationBeanList.isEmpty()) {
            if (errorMessage.isEmpty()) {
                errorMessage = getString(R.string.address_not_found);
            }
            deliverResultToReceiver(resultReceiver,BackgroundConstant.FAILURE_RESULT, errorMessage, null);

        } else {
            deliverResultToReceiver(resultReceiver,BackgroundConstant.SUCCESS_RESULT,getString(R.string.address_found),geolocationBeanList);
        }
    }

    private void deliverResultToReceiver(ResultReceiver resultReceiver,int resultCode, String message, List<GeolocationBean> geolocationBeanList) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(BackgroundConstant.GEOLOCATION_BEAN, (ArrayList<GeolocationBean>) geolocationBeanList);
        bundle.putString(BackgroundConstant.MESSAGE, message);
        resultReceiver.send(resultCode, bundle);
    }

}