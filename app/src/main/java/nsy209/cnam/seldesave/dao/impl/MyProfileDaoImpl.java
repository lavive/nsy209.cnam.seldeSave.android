package nsy209.cnam.seldesave.dao.impl;


import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import nsy209.cnam.seldesave.bean.CategoryBean;
import nsy209.cnam.seldesave.bean.FilterBean;
import nsy209.cnam.seldesave.bean.MemberBean;
import nsy209.cnam.seldesave.bean.MyProfileBean;
import nsy209.cnam.seldesave.bean.SupplyDemandBean;
import nsy209.cnam.seldesave.bean.WealthSheetBean;
import nsy209.cnam.seldesave.bean.helper.EnumSupplyDemand;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.dao.MyProfileDao;
import nsy209.cnam.seldesave.bean.NotificationBean;
import nsy209.cnam.seldesave.dao.enumTable.EnumCategoryTable;
import nsy209.cnam.seldesave.dao.enumTable.EnumMemberTable;
import nsy209.cnam.seldesave.dao.enumTable.EnumNotificationTable;
import nsy209.cnam.seldesave.dao.enumTable.EnumSupplyDemandTable;
import nsy209.cnam.seldesave.dao.sharedPreferences.FilterPreferences;
import nsy209.cnam.seldesave.dao.sharedPreferences.MyProfilePreferences;
import nsy209.cnam.seldesave.dao.sharedPreferences.WealthSheetPreferences;
import static nsy209.cnam.seldesave.dao.enumTable.EnumMemberTable.getTableNameFilter;
import static nsy209.cnam.seldesave.dao.enumTable.EnumSupplyDemandTable.getTableNameMy;


/**
 * Created by lavive on 02/06/17.
 */

public class MyProfileDaoImpl implements MyProfileDao {

    public static  MyProfileDao getInstance(DaoFactory daoFactory){

            return new MyProfileDaoImpl(daoFactory);

    }

    /* attribute */
    private DaoFactory daoFactory;

    private MyProfileDaoImpl(DaoFactory daoFactory){
        this.daoFactory = daoFactory;
    }

    /* my datas */

    @Override
    public MyProfileBean getMyProfile(){
        MyProfileBean myProfile = new MyProfileBean();

            myProfile.setId(MyProfilePreferences.getId());
            myProfile.setRemote_id(MyProfilePreferences.getRemoteId());
            myProfile.setMobileId(MyProfilePreferences.getMobileId());
            myProfile.setName(MyProfilePreferences.getName());
            myProfile.setForname(MyProfilePreferences.getForname());
            myProfile.setMobileId(MyProfilePreferences.getMobile());
            myProfile.setAddress(MyProfilePreferences.getAddress());
            myProfile.setPostalCode(MyProfilePreferences.getPostalcode());
            myProfile.setTown(MyProfilePreferences.getTown());
            myProfile.setCellNumber(MyProfilePreferences.getCellnumber());
            myProfile.setEmail(MyProfilePreferences.getEmail());
            myProfile.setPhoneNumber(MyProfilePreferences.getPhoneNumber());

        return myProfile;
    }

    @Override
    public void setMyRemoteId(long id){
        MyProfilePreferences.putRemoteId(id);
    }

    @Override
    public void setMyMobileId(String myMobileId){
        MyProfilePreferences.putMobileId(myMobileId);
    }

    @Override
    public void createMyProfile(MyProfileBean myProfile){
        MyProfilePreferences.putId(myProfile.getId());
        MyProfilePreferences.putRemoteId(myProfile.getRemote_id());
        MyProfilePreferences.putMobileId(myProfile.getMobileId());
        MyProfilePreferences.putName(myProfile.getName());
        MyProfilePreferences.putForname(myProfile.getForname());
        MyProfilePreferences.putMobile(myProfile.getMobileId());
        MyProfilePreferences.putAddress(myProfile.getAddress());
        MyProfilePreferences.putPostalCode(myProfile.getPostalCode());
        MyProfilePreferences.putTown(myProfile.getTown());
        MyProfilePreferences.putCellNumber(myProfile.getCellNumber());
        MyProfilePreferences.putEmail(myProfile.getEmail());
        MyProfilePreferences.putPhoneNumber(myProfile.getPhoneNumber());
    }

    @Override
    public void updateMyProfile(MyProfileBean myProfile){
        if(myProfile.getRemote_id() > 0)
            MyProfilePreferences.putRemoteId(myProfile.getRemote_id());
        if(myProfile.getMobileId() !=null)
            MyProfilePreferences.putMobileId(myProfile.getMobileId());
        if(myProfile.getName() !=null)
            MyProfilePreferences.putName(myProfile.getName());
        if(myProfile.getForname() !=null)
            MyProfilePreferences.putForname(myProfile.getForname());
        if(myProfile.getMobileId() !=null)
            MyProfilePreferences.putMobile(myProfile.getMobileId());
        if(myProfile.getAddress() !=null)
            MyProfilePreferences.putAddress(myProfile.getAddress());
        if(myProfile.getPostalCode() !=null)
            MyProfilePreferences.putPostalCode(myProfile.getPostalCode());
        if(myProfile.getTown() !=null)
            MyProfilePreferences.putTown(myProfile.getTown());
        if(myProfile.getCellNumber() !=null)
            MyProfilePreferences.putCellNumber(myProfile.getCellNumber());
        if(myProfile.getEmail() !=null)
            MyProfilePreferences.putEmail(myProfile.getEmail());
        if(myProfile.getPhoneNumber() !=null)
            MyProfilePreferences.putPhoneNumber(myProfile.getPhoneNumber());

        addMyProfileOnBuffer();

    }

    @Override
    public WealthSheetBean getMyWealthSheet(){
        daoFactory.open();
        WealthSheetBean wealthSheet = new WealthSheetBean();
        wealthSheet.setTransactions(this.daoFactory.getTransactionDao().getCheckedTransactions());

        wealthSheet.setId(WealthSheetPreferences.getId());
        wealthSheet.setRemote_id(WealthSheetPreferences.getRemoteId());
        wealthSheet.setInitialAccount(WealthSheetPreferences.getInitial());
        wealthSheet.setFinalAccount(WealthSheetPreferences.getFinal());

        return wealthSheet;
    }

    @Override
    public void createWealthSheet(WealthSheetBean wealthSheet){
        WealthSheetPreferences.putId(wealthSheet.getId());
        WealthSheetPreferences.putRemoteId(wealthSheet.getRemote_id());
        WealthSheetPreferences.putInitial(wealthSheet.getInitialAccount());
        WealthSheetPreferences.putFinal(wealthSheet.getFinalAccount());
        
    }

    @Override
    public void addMyProfileOnBuffer(){
        daoFactory.open();
        ContentValues values = new ContentValues();
        values.put(EnumMemberTable.COLUMN_REMOTE_ID.getColumnName(), getMyProfile().getRemote_id());
        values.put(EnumMemberTable.COLUMN_FORNAME.getColumnName(), getMyProfile().getForname());
        values.put(EnumMemberTable.COLUMN_NAME.getColumnName(), getMyProfile().getName());
        values.put(EnumMemberTable.COLUMN_ADDRESS.getColumnName(), getMyProfile().getAddress());
        values.put(EnumMemberTable.COLUMN_POSTAL_CODE.getColumnName(), getMyProfile().getPostalCode());
        values.put(EnumMemberTable.COLUMN_TOWN.getColumnName(), getMyProfile().getTown());
        values.put(EnumMemberTable.COLUMN_CELLNUMBER.getColumnName(), getMyProfile().getCellNumber());
        values.put(EnumMemberTable.COLUMN_EMAIL.getColumnName(), getMyProfile().getEmail());
        values.put(EnumMemberTable.COLUMN_PHONENUMBER.getColumnName(), getMyProfile().getPhoneNumber());
        this.daoFactory.getDatabase().insert(EnumMemberTable.getTableNameBuffer(), null, values);
    }

    @Override
    public List<MyProfileBean> getAllMyProfileBuffer(){
        daoFactory.open();
        List<MyProfileBean> profiles = new ArrayList<MyProfileBean>();

        Cursor cursor = this.daoFactory.getDatabase().query(EnumMemberTable.getTableNameBuffer(), EnumMemberTable.getAllColumns(),
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MyProfileBean profile = cursorToProfile(cursor);
            profiles.add(profile);
            cursor.moveToNext();
        }

        cursor.close();
        return profiles;
    }

    @Override
    public void deleteProfileFromBuffer(long id){
        daoFactory.open();
        this.daoFactory.getDatabase().delete(EnumMemberTable.getTableNameBuffer(),
                EnumMemberTable.COLUMN_ID.getColumnName() + "=" + id, null);
    }

    @Override
    public void clearBuffer() {
        for(MyProfileBean profileBean:getAllMyProfileBuffer()) {
            daoFactory.open();
            this.daoFactory.getDatabase().delete(EnumMemberTable.getTableNameBuffer(),
                    EnumMemberTable.COLUMN_ID.getColumnName() + "=" + profileBean.getId(), null);
        }
    }


    /* my supplies and demands */

    @Override
    public List<SupplyDemandBean> getMySuppliesDemands(){
        daoFactory.open();

        List<SupplyDemandBean> suppliesDemands = new ArrayList<SupplyDemandBean>();

        Cursor cursor = this.daoFactory.getDatabase().query(getTableNameMy(), EnumSupplyDemandTable.getAllColumns(),
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SupplyDemandBean supplyDemand = cursorToSupplyDemand(cursor);
            if(supplyDemand.isActive()) {
                suppliesDemands.add(supplyDemand);
            }
            cursor.moveToNext();
        }
        cursor.close();

        return suppliesDemands;
    }

    @Override
    public List<SupplyDemandBean> getMySuppliesDemandsUnchecked(){
        daoFactory.open();

        List<SupplyDemandBean> suppliesDemands = new ArrayList<SupplyDemandBean>();

        Cursor cursor = this.daoFactory.getDatabase().query(getTableNameMy(), EnumSupplyDemandTable.getAllColumns(),
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SupplyDemandBean supplyDemand = cursorToSupplyDemand(cursor);
            if(supplyDemand.isActive() && !supplyDemand.isChecked()) {
                suppliesDemands.add(supplyDemand);
            }
            cursor.moveToNext();
        }
        cursor.close();

        return suppliesDemands;
    }

    @Override
    public SupplyDemandBean getMySupplyDemand(long id){
        daoFactory.open();
        Cursor cursor = this.daoFactory.getDatabase().query(getTableNameMy(), EnumSupplyDemandTable.getAllColumns(),
                EnumSupplyDemandTable.COLUMN_ID.getColumnName() + " = " + id, null,
                null, null, null);

        cursor.moveToFirst();
        SupplyDemandBean supplyDemand = cursorToSupplyDemand(cursor);

        cursor.close();
        return supplyDemand;
    }

    @Override
    public void deleteSupplyDemand(SupplyDemandBean supplyDemand){
        daoFactory.open();
        if(supplyDemand.isChecked()){
            /* don't delete supplyDemand from my table but set inactive to delete from server first */
            ContentValues values = new ContentValues();
            if(supplyDemand.getRemote_id() >0)
                values.put(EnumSupplyDemandTable.COLUMN_REMOTE_ID.getColumnName(), supplyDemand.getRemote_id());
            if(supplyDemand.getType() != null)
                values.put(EnumSupplyDemandTable.COLUMN_TYPE.getColumnName(), supplyDemand.getType().getWording());
            if(supplyDemand.getCategory() != null)
                values.put(EnumSupplyDemandTable.COLUMN_CATEGORY.getColumnName(), supplyDemand.getCategory());
            if(supplyDemand.getTitle() != null)
                values.put(EnumSupplyDemandTable.COLUMN_TITLE.getColumnName(), supplyDemand.getTitle());
            if(supplyDemand.getMember() != null)
                values.put(EnumSupplyDemandTable.COLUMN_ID_MEMBER.getColumnName(), supplyDemand.getMember().getRemote_id());
            values.put(EnumSupplyDemandTable.COLUMN_CHECKED.getColumnName(), 1);
            values.put(EnumSupplyDemandTable.COLUMN_ACTIVE.getColumnName(), 0);
            /* update supplydemand to inactive */
            this.daoFactory.getDatabase().update(getTableNameMy(), values,
                    EnumSupplyDemandTable.COLUMN_ID.getColumnName() + " = " + supplyDemand.getId(), null);
        }else {
            /* supplydemand is not check in server yet so it can be deleted from local data base */
            this.daoFactory.getDatabase().delete(getTableNameMy(),
                    EnumSupplyDemandTable.COLUMN_ID.getColumnName() + "=" + supplyDemand.getId(), null);
        }
    }

    @Override
    public void deleteSupplyDemand(long id){
        deleteSupplyDemand(getMySupplyDemand(id));
    }

    @Override
    public void updateSupplyDemand(SupplyDemandBean supplyDemand){
        daoFactory.open();
        if(supplyDemand.isChecked()){
            try {
                daoFactory.getDatabase().beginTransaction();
                 /* prepare delete supplyDemand from all supplyDemand table */
                deleteSupplyDemand(supplyDemand);
                /* add new supplyDemand in my supplyDemand Table */
                addSupplyDemand(supplyDemand);
                daoFactory.getDatabase().setTransactionSuccessful();
            } finally {
                daoFactory.getDatabase().endTransaction();
            }
        } else {
            ContentValues values = new ContentValues();
            if(supplyDemand.getRemote_id() >0)
                values.put(EnumSupplyDemandTable.COLUMN_REMOTE_ID.getColumnName(), supplyDemand.getRemote_id());
            if(supplyDemand.getType() != null)
                values.put(EnumSupplyDemandTable.COLUMN_TYPE.getColumnName(), supplyDemand.getType().getWording());
            if(supplyDemand.getCategory() != null)
                values.put(EnumSupplyDemandTable.COLUMN_CATEGORY.getColumnName(), supplyDemand.getCategory());
            if(supplyDemand.getTitle() != null)
                values.put(EnumSupplyDemandTable.COLUMN_TITLE.getColumnName(), supplyDemand.getTitle());
            if(supplyDemand.getMember() != null)
                values.put(EnumSupplyDemandTable.COLUMN_ID_MEMBER.getColumnName(), supplyDemand.getMember().getRemote_id());

            if(supplyDemand.isChecked()) {
                values.put(EnumSupplyDemandTable.COLUMN_CHECKED.getColumnName(), 1);
            }else {
                values.put(EnumSupplyDemandTable.COLUMN_CHECKED.getColumnName(), 0);
            }
            if(supplyDemand.isActive()){
                values.put(EnumSupplyDemandTable.COLUMN_ACTIVE.getColumnName(), 1);
            }else {
                values.put(EnumSupplyDemandTable.COLUMN_ACTIVE.getColumnName(), 0);
            }
            /* update my supplyDemand table */
            this.daoFactory.getDatabase().update(getTableNameMy(), values,
                    EnumSupplyDemandTable.COLUMN_ID.getColumnName() + " = " + supplyDemand.getId(), null);
        }

    }

    @Override
    public void addSupplyDemand(SupplyDemandBean supplyDemand){
        daoFactory.open();
        ContentValues values = new ContentValues();
        values.put(EnumSupplyDemandTable.COLUMN_REMOTE_ID.getColumnName(), supplyDemand.getRemote_id());
        values.put(EnumSupplyDemandTable.COLUMN_TYPE.getColumnName(), supplyDemand.getType().getWording());
        values.put(EnumSupplyDemandTable.COLUMN_CATEGORY.getColumnName(), supplyDemand.getCategory());
        values.put(EnumSupplyDemandTable.COLUMN_TITLE.getColumnName(), supplyDemand.getTitle());
        values.put(EnumSupplyDemandTable.COLUMN_ID_MEMBER.getColumnName(), supplyDemand.getMember().getRemote_id());

        if(supplyDemand.isChecked()) {
            values.put(EnumSupplyDemandTable.COLUMN_CHECKED.getColumnName(), 1);
        }else {
            values.put(EnumSupplyDemandTable.COLUMN_CHECKED.getColumnName(), 0);
        }
        if(supplyDemand.isActive()){
            values.put(EnumSupplyDemandTable.COLUMN_ACTIVE.getColumnName(), 1);
        }else {
            values.put(EnumSupplyDemandTable.COLUMN_ACTIVE.getColumnName(), 0);
        }
        this.daoFactory.getDatabase().insert(EnumSupplyDemandTable.getTableNameMy(), null, values);

    }


    /* my notifications */

    @Override
    public List<NotificationBean> getMyNotifications(){
        daoFactory.open();

        List<NotificationBean> notifications = new ArrayList<NotificationBean>();

        Cursor cursor = this.daoFactory.getDatabase().query(EnumNotificationTable.getTableName(), EnumNotificationTable.getAllColumns(),
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NotificationBean notification = cursorToNotification(cursor);
            notifications.add(notification);
            cursor.moveToNext();
        }

        cursor.close();
        return notifications;
    }

    @Override
    public NotificationBean getMyNotification(long id){
        daoFactory.open();

        Cursor cursor = this.daoFactory.getDatabase().query(EnumNotificationTable.getTableName(), EnumNotificationTable.getAllColumns(),
                EnumNotificationTable.COLUMN_ID.getColumnName() + " = " + id, null,
                null, null, null);

        cursor.moveToFirst();
        NotificationBean notification = cursorToNotification(cursor);

        cursor.close();
        return notification;
    }

    @Override
    public void deleteNotification(long id){
        daoFactory.open();
        this.daoFactory.getDatabase().delete(EnumNotificationTable.getTableName(),
                EnumNotificationTable.COLUMN_ID.getColumnName() + "=" + id, null);

    }

    @Override
    public void isRead(NotificationBean notificationBean){
        daoFactory.open();
        ContentValues values = new ContentValues();
        values.put(EnumNotificationTable.COLUMN_TITLE.getColumnName(), notificationBean.getTitle());
        values.put(EnumNotificationTable.COLUMN_TEXT.getColumnName(), notificationBean.getText());
        values.put(EnumNotificationTable.COLUMN_ORIGIN.getColumnName(), notificationBean.getPersonOriginId());
        values.put(EnumNotificationTable.COLUMN_CATEGORY.getColumnName(), notificationBean.getCategory());
        values.put(EnumNotificationTable.COLUMN_READ.getColumnName(), 1);

        this.daoFactory.getDatabase().update(EnumNotificationTable.getTableName(), values,
                EnumNotificationTable.COLUMN_ID.getColumnName() + " = " + notificationBean.getId(), null);
    }

    @Override
    public void addNotifications(List<NotificationBean> notificationBeanList){
        daoFactory.open();
        for(NotificationBean notificationBean:notificationBeanList) {
            ContentValues values = new ContentValues();
            values.put(EnumNotificationTable.COLUMN_REMOTE_ID.getColumnName(),notificationBean.getRemote_id());
            values.put(EnumNotificationTable.COLUMN_TITLE.getColumnName(), notificationBean.getTitle());
            values.put(EnumNotificationTable.COLUMN_TEXT.getColumnName(), notificationBean.getText());
            values.put(EnumNotificationTable.COLUMN_ORIGIN.getColumnName(), notificationBean.getPersonOriginId());
            values.put(EnumNotificationTable.COLUMN_CATEGORY.getColumnName(), notificationBean.getCategory());
            if(notificationBean.isRead()) {
                values.put(EnumNotificationTable.COLUMN_READ.getColumnName(), 1);
            } else {
                values.put(EnumNotificationTable.COLUMN_READ.getColumnName(), 0);
            }
            this.daoFactory.getDatabase().insert(EnumNotificationTable.getTableName(), null, values);
        }

    }

    @Override
    public void addNotificationsToBuffer(List<NotificationBean> notificationBeanList){
        daoFactory.open();
        for(NotificationBean notificationBean:notificationBeanList) {
            ContentValues values = new ContentValues();
            values.put(EnumNotificationTable.COLUMN_REMOTE_ID.getColumnName(),notificationBean.getRemote_id());
            values.put(EnumNotificationTable.COLUMN_TITLE.getColumnName(), notificationBean.getTitle());
            values.put(EnumNotificationTable.COLUMN_TEXT.getColumnName(), notificationBean.getText());
            values.put(EnumNotificationTable.COLUMN_ORIGIN.getColumnName(), notificationBean.getPersonOriginId());
            values.put(EnumNotificationTable.COLUMN_CATEGORY.getColumnName(), notificationBean.getCategory());
            if(notificationBean.isRead()) {
                values.put(EnumNotificationTable.COLUMN_READ.getColumnName(), 1);
            } else {
                values.put(EnumNotificationTable.COLUMN_READ.getColumnName(), 0);
            }
            this.daoFactory.getDatabase().insert(EnumNotificationTable.getTableNameBuffer(), null, values);
        }

    }

    @Override
    public void deleteNotificationsFromBuffer(){
        daoFactory.open();

        this.daoFactory.getDatabase().execSQL("DROP TABLE IF EXISTS " + EnumNotificationTable.getTableNameBuffer());
        this.daoFactory.getDatabase().execSQL(EnumNotificationTable.getCreateCommandBuffer());
    }

    @Override
    public List<NotificationBean> getMyNotificationsFromBuffer(){
        daoFactory.open();

        List<NotificationBean> notifications = new ArrayList<NotificationBean>();

        Cursor cursor = this.daoFactory.getDatabase().query(EnumNotificationTable.getTableNameBuffer(), EnumNotificationTable.getAllColumns(),
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NotificationBean notification = cursorToNotification(cursor);
            notifications.add(notification);
            cursor.moveToNext();
        }

        cursor.close();
        return notifications;
    }

    /* my filters */
    @Override
    public FilterBean getMyFilter(){
        daoFactory.open();
        FilterBean filterBean = new FilterBean();

        /* member filter */
        List<MemberBean> membersFilter = new ArrayList<MemberBean>();

        Cursor cursorMember = this.daoFactory.getDatabase().query(getTableNameFilter(), EnumMemberTable.getAllColumns(),
                null, null, null, null, null);
        cursorMember.moveToFirst();
        while (!cursorMember.isAfterLast()) {
            MemberBean memberBean = cursorToMember(cursorMember);
            membersFilter.add(memberBean);
            cursorMember.moveToNext();
        }
        cursorMember.close();

        /* category filter */
        List<CategoryBean> categoriesFilter = new ArrayList<CategoryBean>();

        Cursor cursorCategory = this.daoFactory.getDatabase().query(EnumCategoryTable.getTableNameFilter(), EnumCategoryTable.getAllColumns(),
                null, null, null, null, null);
        cursorCategory.moveToFirst();
        while (!cursorCategory.isAfterLast()) {
            CategoryBean categoryBean = cursorToCategory(cursorCategory);
            categoriesFilter.add(categoryBean);
            cursorCategory.moveToNext();
        }
        cursorCategory.close();

        /* distance and checked */
        Long distance = FilterPreferences.getDistance();
        boolean distanceChecked = FilterPreferences.getDistanceChecked();
        boolean membersChecked = FilterPreferences.getMembersChecked();
        boolean categoriesChecked = FilterPreferences.getCategoriesChecked();

        filterBean.setCategoriesFilter(categoriesFilter);
        filterBean.setMembersFilter(membersFilter);
        filterBean.setDistance(distance);
        filterBean.setDistanceChecked(distanceChecked);
        filterBean.setMembersChecked(membersChecked);
        filterBean.setCategoriesChecked(categoriesChecked);

        return filterBean;

    }

    @Override
    public void createFilter(FilterBean filterBean){
        FilterPreferences.putDistance(filterBean.getDistance());
        FilterPreferences.putDistanceChecked(filterBean.isDistanceChecked());
        FilterPreferences.putMembersChecked(filterBean.isMembersChecked());
        FilterPreferences.putCategoriesChecked(filterBean.isCategoriesChecked());
    }

    @Override
    public void setMemberFilter(List<MemberBean> membersFilter){
        daoFactory.open();
        /* delete all values */
        String clearDBMemberFilter = "DELETE FROM "+ EnumMemberTable.getTableNameFilter();
        this.daoFactory.getDatabase().execSQL(clearDBMemberFilter);

        /* set values */
        for(MemberBean memberBean:membersFilter){
            ContentValues values = new ContentValues();
            values.put(EnumMemberTable.COLUMN_REMOTE_ID.getColumnName(),memberBean.getRemote_id());
            values.put(EnumMemberTable.COLUMN_FORNAME.getColumnName(),memberBean.getForname());
            values.put(EnumMemberTable.COLUMN_NAME.getColumnName(),memberBean.getName());
            values.put(EnumMemberTable.COLUMN_ADDRESS.getColumnName(),memberBean.getAddress());
            values.put(EnumMemberTable.COLUMN_POSTAL_CODE.getColumnName(),memberBean.getPostalCode());
            values.put(EnumMemberTable.COLUMN_TOWN.getColumnName(),memberBean.getTown());
            values.put(EnumMemberTable.COLUMN_CELLNUMBER.getColumnName(),memberBean.getCellNumber());
            values.put(EnumMemberTable.COLUMN_EMAIL.getColumnName(),memberBean.getEmail());
            values.put(EnumMemberTable.COLUMN_PHONENUMBER.getColumnName(),memberBean.getPhoneNumber());
            this.daoFactory.getDatabase().insert(EnumMemberTable.getTableNameFilter(), null, values);

        }
    }

    @Override
    public void setCategoryFilter(List<CategoryBean> categoriesFilter){
        daoFactory.open();
        /* delete all values */
        String clearDBCategoryFilter = "DELETE FROM "+EnumCategoryTable.getTableNameFilter();
        this.daoFactory.getDatabase().execSQL(clearDBCategoryFilter);

        /* set values */
        for(CategoryBean categoryBean:categoriesFilter){
            ContentValues values = new ContentValues();
            values.put(EnumCategoryTable.COLUMN_REMOTE_ID.getColumnName(),categoryBean.getRemote_id());
            values.put(EnumCategoryTable.COLUMN_CATEGORY.getColumnName(),categoryBean.getCategory());
            this.daoFactory.getDatabase().insert(EnumCategoryTable.getTableNameFilter(), null, values);

        }

    }


    /* helper methods */
    private SupplyDemandBean cursorToSupplyDemand(Cursor cursor) {
        SupplyDemandBean supplyDemand = new SupplyDemandBean();
        supplyDemand.setId(cursor.getLong(0));
        supplyDemand.setRemote_id(cursor.getLong(1));
        supplyDemand.setType(EnumSupplyDemand.getByWording(cursor.getString(2)));
        supplyDemand.setCategory(cursor.getString(3));
        supplyDemand.setTitle(cursor.getString(4));
        supplyDemand.setMember(this.daoFactory.getMemberDao().getMemberByRemoteId(cursor.getLong(5)));
        supplyDemand.setChecked(cursor.getInt(6)>0);
        supplyDemand.setActive(cursor.getInt(7)>0);
        return supplyDemand;
    }

    private NotificationBean cursorToNotification(Cursor cursor) {
        NotificationBean notification = new NotificationBean();
        notification.setId(cursor.getLong(0));
        notification.setRemote_id(cursor.getLong(1));
        notification.setTitle(cursor.getString(2));
        notification.setText(cursor.getString(3));
        notification.setPersonOriginId(cursor.getLong(4));
        notification.setCategory(cursor.getString(5));
        notification.setRead(cursor.getInt(6) > 0);
        return notification;
    }

    private MemberBean cursorToMember(Cursor cursor) {
        MemberBean member = new MemberBean();
        member.setId(cursor.getLong(0));
        member.setRemote_id(cursor.getLong(1));
        member.setName(cursor.getString(2));
        member.setForname(cursor.getString(3));
        member.setAddress(cursor.getString(4));
        member.setPostalCode(cursor.getString(5));
        member.setTown(cursor.getString(6));
        member.setCellNumber(cursor.getString(7));
        member.setEmail(cursor.getString(8));
        member.setPhoneNumber(cursor.getString(9));
        return member;
    }

    private MyProfileBean cursorToProfile(Cursor cursor) {
        MyProfileBean profile = new MyProfileBean();
        profile.setId(cursor.getLong(0));
        profile.setRemote_id(cursor.getLong(1));
        profile.setName(cursor.getString(2));
        profile.setForname(cursor.getString(3));
        profile.setAddress(cursor.getString(4));
        profile.setPostalCode(cursor.getString(5));
        profile.setTown(cursor.getString(6));
        profile.setCellNumber(cursor.getString(7));
        profile.setEmail(cursor.getString(8));
        profile.setPhoneNumber(cursor.getString(9));
        return profile;
    }

    private CategoryBean cursorToCategory(Cursor cursor) {
        CategoryBean category = new CategoryBean();
        category.setId(cursor.getLong(0));
        category.setRemote_id(cursor.getLong(1));
        category.setCategory(cursor.getString(2));
        return category;
    }

}
