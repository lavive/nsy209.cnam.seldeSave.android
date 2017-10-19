package nsy209.cnam.seldesave.background.helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.ResultReceiver;

import java.util.ArrayList;
import java.util.List;

import nsy209.cnam.seldesave.activity.utils.ActivityConstant;
import nsy209.cnam.seldesave.background.intentService.GeocodeAddressIntentService;
import nsy209.cnam.seldesave.background.receiver.GeocodeAddressResultReceiver;
import nsy209.cnam.seldesave.bean.MemberBean;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 14/10/17.
 */

public class GeolocationToUpdate implements IBaseToUpdate {

    /* daoFactory */
    private DaoFactory daoFactory;

    /* activity launching intent service */
    private Activity activity;

    public GeolocationToUpdate(Activity activity,DaoFactory daoFactory) {
        this.activity = activity;
        this.daoFactory = daoFactory;
    }

    @Override
    public void update() {

        Intent intent = new Intent(this.activity, GeocodeAddressIntentService.class);

        /* put receiver */
        final ResultReceiver mResultReceiver = new GeocodeAddressResultReceiver(new Handler(), this.activity, daoFactory);
        intent.putExtra(BackgroundConstant.RECEIVER_GEOCODE, mResultReceiver);

        /* put data to send*/
        intent.putExtra(BackgroundConstant.GEOLOCATION_BEAN,getAddressToGeocode());

        /* start service */
        this.activity.startService(intent);
    }

    /* helper method */
    private ArrayList<String> getAddressToGeocode(){
        ArrayList<String> addressToGeocode = new ArrayList<String>();
        List<MemberBean> members = daoFactory.getMemberDao().getAllMembers();

        for(MemberBean memberBean:members){
            String memberToGeocode = memberBean.getId()+ ActivityConstant.REGEX+
                    memberBean.getAddress()+" "+memberBean.getPostalCode()+" "+memberBean.getTown();
            addressToGeocode.add(memberToGeocode);
        }
        return addressToGeocode;
    }
}
