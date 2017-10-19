package nsy209.cnam.seldesave.dao;

import java.util.List;

import nsy209.cnam.seldesave.bean.GeolocationBean;
import nsy209.cnam.seldesave.bean.MemberBean;

/**
 * Created by lavive on 01/06/17.
 */

public interface MemberDao {

    public List<MemberBean> getAllMembers();

    public MemberBean getMember(long id);

    public MemberBean getMemberByRemoteId(long remoteId);

    public List<MemberBean> getMembersAbleToBePayed(long supplyDemandId);

    public List<GeolocationBean> getAllGeolocation();

    public GeolocationBean getGeolocation(long memberId);

    public void addMembers(List<MemberBean> memberBeanList);

    public void addGeolocation(GeolocationBean geolocationBean);

    public void createMembers(List<MemberBean> memberBeanList);

    public void updateMembers(List<MemberBean> memberBeanList);

    public void createGeolocations(List<GeolocationBean> geolocationBeanList);
}
