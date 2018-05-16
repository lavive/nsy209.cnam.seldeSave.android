package nsy209.cnam.seldesave.bean.helper;


import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nsy209.cnam.seldesave.bean.CategoryBean;
import nsy209.cnam.seldesave.bean.GeolocationBean;
import nsy209.cnam.seldesave.bean.MemberBean;
import nsy209.cnam.seldesave.bean.NotificationBean;
import nsy209.cnam.seldesave.bean.SupplyDemandBean;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 15/10/17.
 */

public class ApplyFilterBuilder {

    private DaoFactory daoFactory;

    private List<MemberBean> membersFiltered;

    public ApplyFilterBuilder(List<MemberBean> membersToFilter,DaoFactory daoFactory){
        this.membersFiltered = membersToFilter;
        this.daoFactory = daoFactory;
    }

    /* apply filters */
    public ApplyFilterBuilder apply(boolean applyCategory, boolean applyMember, boolean applyDistance, List<NotificationBean> notificationsBean){
        daoFactory.open();

        if(applyCategory == true || applyMember == true || applyDistance == true){

            List<MemberBean> memberBeanList = new ArrayList<MemberBean>();
            if(applyCategory){
                for(MemberBean memberBean:applyCategoryFilter(notificationsBean)){
                    if(!memberIsInside(memberBean,memberBeanList)){
                        memberBeanList.add(memberBean);
                    }
                }
            }
            if(applyMember){
                for(MemberBean memberBean:applyMemberFilter()){
                    if(!memberIsInside(memberBean,memberBeanList)){
                        memberBeanList.add(memberBean);
                    }
                }
            }
            if(applyDistance){
                for(MemberBean memberBean:applyDistanceFilter()){
                    if(!memberIsInside(memberBean,memberBeanList)){
                        memberBeanList.add(memberBean);
                    }
                }
            }
            this.membersFiltered = memberBeanList;
        }
        return this;

    }

    public ApplyFilterBuilder apply(boolean applyCategory, boolean applyMember, boolean applyDistance){
        return apply(applyCategory, applyMember, applyDistance, null);
    }

    /* getter */

    public List<MemberBean> getMembersFiltered() {
        return membersFiltered;
    }

    /* apply category filter */
    private List<MemberBean> applyCategoryFilter(List<NotificationBean> notificationsBean){
        List<MemberBean> memberBeanList = copy(membersFiltered);
        Iterator<MemberBean> iterator = memberBeanList.iterator();
        while (iterator.hasNext()) {
            MemberBean memberBean = iterator.next();
            boolean isInside = false;
            for (CategoryBean categoryBean : daoFactory.getMyProfileDao().getMyFilter().getCategoriesFilter()) {
                for (SupplyDemandBean supplyDemandBean : daoFactory.getSupplyDemandDao().getAllSuppliesDemands(memberBean)) {
                    boolean categoryMatch = false;
                    if(notificationsBean != null) {
                        for (NotificationBean notification : notificationsBean) {
                            if (categoryBean.getCategory().equals(notification.getCategory())) {
                                categoryMatch = true;
                                break;
                            }
                        }
                    }
                    else {
                        categoryMatch = true;
                    }
                    if (supplyDemandBean.getCategory().equals(categoryBean.getCategory()) && categoryMatch) {
                        isInside = true;
                        break;
                    }
                }
                if (isInside) {
                    break;
                }
            }
            if (!isInside) {
                iterator.remove();
            }
        }

        return memberBeanList;
    }

    /* apply member filter */
    private List<MemberBean> applyMemberFilter(){
        List<MemberBean> memberBeanList = copy(membersFiltered);
        Iterator<MemberBean> iterator = memberBeanList.iterator();
        while (iterator.hasNext()) {
            MemberBean memberBean = iterator.next();
            boolean isInside = false;
            for (MemberBean memberBeanFiltered : daoFactory.getMyProfileDao().getMyFilter().getMembersFilter()) {
                if (memberBeanFiltered.getRemote_id() == memberBean.getRemote_id()) {
                    isInside = true;
                    break;
                }

            }
            if (!isInside) {
                iterator.remove();
            }
        }

        return memberBeanList;
    }

    /* apply distance filter */
    private  List<MemberBean> applyDistanceFilter(){
        List<MemberBean> memberBeanList = copy(membersFiltered);
        Iterator<MemberBean> iterator = memberBeanList.iterator();
        while (iterator.hasNext()) {
            MemberBean memberBean = iterator.next();
            if (daoFactory.getMyProfileDao().getMyFilter().getDistance() != 0d &&
                    daoFactory.getMyProfileDao().getMyFilter().getDistance() <= computeDistance(memberBean)) {
                iterator.remove();
            }
        }

        return memberBeanList;
    }



    /* calculate distance between a member home and my home */
    private double computeDistance(MemberBean memberBean){
        if(memberBean.getRemote_id() == daoFactory.getMyProfileDao().getMyProfile().getRemote_id()){
            return 0d;
        } else {
            GeolocationBean geolocationMember = daoFactory.getMemberDao().getGeolocation(memberBean.getId());
            GeolocationBean geolocationMe = daoFactory.getMemberDao().getGeolocation(daoFactory.getMyProfileDao().getMyProfile().getId());
            LatLng memberLatLng = new LatLng(geolocationMember.getLatitude(),geolocationMember.getLongitude());
            LatLng meLatLng = new LatLng(geolocationMe.getLatitude(),geolocationMe.getLongitude());

            return SphericalUtil.computeDistanceBetween(meLatLng,memberLatLng);
        }
    }

    /* check if a member is in a list */
    private boolean memberIsInside(MemberBean memberBean,List<MemberBean> memberBeanList){
        boolean isInside = false;
        for(MemberBean member:memberBeanList){
            if(member.getRemote_id() == memberBean.getRemote_id()){
                isInside = true;
                break;
            }
        }
        return isInside;
    }

    /* copy list of members */
    private List<MemberBean> copy(List<MemberBean> listToCopy){
        List<MemberBean> listResult = new ArrayList<MemberBean>();
        for(MemberBean memberBean:listToCopy){
            listResult.add(memberBean);
        }

        return listResult;
    }


}
