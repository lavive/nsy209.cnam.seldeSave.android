package nsy209.cnam.seldesave.dao;

import java.util.List;

import nsy209.cnam.seldesave.bean.CategoryBean;
import nsy209.cnam.seldesave.bean.FilterBean;
import nsy209.cnam.seldesave.bean.MemberBean;
import nsy209.cnam.seldesave.bean.MyProfileBean;
import nsy209.cnam.seldesave.bean.SupplyDemandBean;
import nsy209.cnam.seldesave.bean.WealthSheetBean;
import nsy209.cnam.seldesave.bean.NotificationBean;

/**
 * Created by lavive on 01/06/17.
 */

public interface MyProfileDao {

    /* my datas */

    public MyProfileBean getMyProfile();

    public void setMyMobileId(String mobileId);

    public void setMyRemoteId(long id);

    public void createMyProfile(MyProfileBean myProfile);

    public void updateMyProfile(MyProfileBean myProfile);

    public WealthSheetBean getMyWealthSheet();

    public void createWealthSheet(WealthSheetBean wealthSheet);

    public void addMyProfileOnBuffer();

    public List<MyProfileBean> getAllMyProfileBuffer();

    public void deleteProfileFromBuffer(long id);

    public void clearBuffer();


    /* my supplies and demands */

    public List<SupplyDemandBean> getMySuppliesDemands();

    public List<SupplyDemandBean> getMySuppliesDemandsUnchecked();

    public SupplyDemandBean getMySupplyDemand(long id);

    public void deleteSupplyDemand(SupplyDemandBean supplyDemand);

    public void deleteSupplyDemand(long id);

    public void updateSupplyDemand(SupplyDemandBean supplyDemand);

    public void addSupplyDemand(SupplyDemandBean supplyDemand);


    /* my notifications */

    public List<NotificationBean> getMyNotifications();

    public NotificationBean getMyNotification(long id);

    public void deleteNotification(long id);

    public void isRead(NotificationBean notificationBean);

    public void addNotifications(List<NotificationBean> notificationBeanList);

    public void addNotificationsToBuffer(List<NotificationBean> notificationBeanList);

    public void deleteNotificationsFromBuffer();

    public List<NotificationBean> getMyNotificationsFromBuffer();



    /* my filters */

    public FilterBean getMyFilter();

    public void createFilter(FilterBean filterBean);

    public void setMemberFilter(List<MemberBean> membersFilter);

    public void setCategoryFilter(List<CategoryBean> categoriesFilter);




}
