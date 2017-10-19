package nsy209.cnam.seldesave.models;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.bean.GeolocationBean;
import nsy209.cnam.seldesave.bean.MemberBean;
import nsy209.cnam.seldesave.bean.helper.ApplyFilterBuilder;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 15/10/17.
 */

public class GeolocationMembersModel extends Observable {

    private List<GeolocationBean> geolocationsBean;

    private String applyFilter;

    /* affect values to attributes */
    public void onCreate(Context context, DaoFactory daoFactory, boolean applyFilters){
        daoFactory.open();
        List<GeolocationBean> geolocationsBean = new ArrayList<GeolocationBean>();
        if(applyFilters){
            this.applyFilter = context.getString(R.string.disable_filters_button);
            ApplyFilterBuilder filterBuilder = new ApplyFilterBuilder(daoFactory.getMemberDao().getAllMembers(),daoFactory);
            List<MemberBean> members = filterBuilder.apply(daoFactory.getMyProfileDao().getMyFilter().isCategoriesChecked(),
                                                            daoFactory.getMyProfileDao().getMyFilter().isMembersChecked(),
                                                            daoFactory.getMyProfileDao().getMyFilter().isDistanceChecked())
                    .getMembersFiltered();
            for(MemberBean memberBean:members){
                geolocationsBean.add(daoFactory.getMemberDao().getGeolocation(memberBean.getId()));
            }
        } else {
            this.applyFilter = context.getString(R.string.apply_filters_button);
            for(MemberBean memberBean:daoFactory.getMemberDao().getAllMembers()){
                geolocationsBean.add(daoFactory.getMemberDao().getGeolocation(memberBean.getId()));
            }
        }
        this.geolocationsBean = geolocationsBean;

        setChanged();
        notifyObservers();
    }



    /* getters */

    public List<GeolocationBean> getGeolocationsBean() {
        return geolocationsBean;
    }

    public String getApplyFilter() {
        return applyFilter;
    }
}
