package nsy209.cnam.seldesave.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import nsy209.cnam.seldesave.bean.GeolocationBean;
import nsy209.cnam.seldesave.bean.MemberBean;
import nsy209.cnam.seldesave.bean.SupplyDemandBean;
import nsy209.cnam.seldesave.bean.helper.EnumSupplyDemand;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.dao.MemberDao;
import nsy209.cnam.seldesave.dao.enumTable.EnumGeolocationTable;
import nsy209.cnam.seldesave.dao.enumTable.EnumMemberTable;

/**
 * Created by lavive on 02/06/17.
 */

public class MemberDaoImpl implements MemberDao {

    public static  MemberDao getInstance(DaoFactory daoFactory){

            return new MemberDaoImpl(daoFactory);

    }

    /* attribute */
    private DaoFactory daoFactory;

    private MemberDaoImpl(DaoFactory daoFactory){
        this.daoFactory = daoFactory;
    }


    @Override
    public List<MemberBean> getAllMembers(){
        daoFactory.open();
        List<MemberBean> members = new ArrayList<MemberBean>();

        Cursor cursor = this.daoFactory.getDatabase().query(EnumMemberTable.getTableName(), EnumMemberTable.getAllColumns(),
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MemberBean member = cursorToMember(cursor);
            members.add(member);
            cursor.moveToNext();
        }

        cursor.close();
        return members;
    }

    @Override
    public MemberBean getMember(long id){
        daoFactory.open();
        Cursor cursor = this.daoFactory.getDatabase().query(EnumMemberTable.getTableName(), EnumMemberTable.getAllColumns(),
                                        EnumMemberTable.COLUMN_ID.getColumnName() + " = " + id, null,
                                        null, null, null);

        cursor.moveToFirst();
        MemberBean newMember = cursorToMember(cursor);

        cursor.close();
        return newMember;
    }

    @Override
    public MemberBean getMemberByRemoteId(long remoteId){
        daoFactory.open();
        Cursor cursor = this.daoFactory.getDatabase().query(EnumMemberTable.getTableName(), EnumMemberTable.getAllColumns(),
                EnumMemberTable.COLUMN_REMOTE_ID.getColumnName() + " = " + remoteId, null,
                null, null, null);

        cursor.moveToFirst();
        MemberBean newMember = cursorToMember(cursor);

        cursor.close();
        return newMember;
    }

    @Override
    public List<MemberBean> getMembersAbleToBePayed(long supplyDemandId){
        daoFactory.open();
        List<MemberBean> members = new ArrayList<MemberBean>();
        SupplyDemandBean supplyDemandBean = this.daoFactory.getSupplyDemandDao().getSupplyDemand(supplyDemandId);
        long myRemoteId = this.daoFactory.getMyProfileDao().getMyProfile().getRemote_id();

        Cursor cursor = this.daoFactory.getDatabase().query(EnumMemberTable.getTableName(), EnumMemberTable.getAllColumns(),
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MemberBean member = cursorToMember(cursor);
            if((supplyDemandBean.getType().equals(EnumSupplyDemand.DEMAND) && member.getRemote_id() != myRemoteId) ||
                    (supplyDemandBean.getType().equals(EnumSupplyDemand.SUPPLY) && supplyDemandBean.getMember().getRemote_id() == member.getRemote_id()))
            {
                members.add(member);
            }
            cursor.moveToNext();
        }

        cursor.close();

        return members;
    }

    @Override
    public List<GeolocationBean> getAllGeolocation(){
        daoFactory.open();
        List<GeolocationBean> geolocations = new ArrayList<GeolocationBean>();

        Cursor cursor = this.daoFactory.getDatabase().query(EnumGeolocationTable.getTableName(), EnumGeolocationTable.getAllColumns(),
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            GeolocationBean geolocationBean = cursorToGeolocation(cursor);
            geolocations.add(geolocationBean);
            cursor.moveToNext();
        }

        cursor.close();
        return geolocations;
    }

    @Override
    public GeolocationBean getGeolocation(long memberId){
        daoFactory.open();
        Cursor cursor = this.daoFactory.getDatabase().query(EnumGeolocationTable.getTableName(), EnumGeolocationTable.getAllColumns(),
                EnumGeolocationTable.COLUMN_MEMBER_ID.getColumnName() + " = " + memberId, null,
                null, null, null);

        cursor.moveToFirst();
        GeolocationBean newGeolocation = cursorToGeolocation(cursor);

        cursor.close();
        return newGeolocation;
    }

    @Override
    public void addMembers(List<MemberBean> memberBeanList){
        daoFactory.open();
        for(MemberBean memberBean:memberBeanList) {
            ContentValues values = new ContentValues();
            values.put(EnumMemberTable.COLUMN_REMOTE_ID.getColumnName(), memberBean.getRemote_id());
            values.put(EnumMemberTable.COLUMN_FORNAME.getColumnName(), memberBean.getForname());
            values.put(EnumMemberTable.COLUMN_NAME.getColumnName(), memberBean.getName());
            values.put(EnumMemberTable.COLUMN_ADDRESS.getColumnName(), memberBean.getAddress());
            values.put(EnumMemberTable.COLUMN_POSTAL_CODE.getColumnName(), memberBean.getPostalCode());
            values.put(EnumMemberTable.COLUMN_TOWN.getColumnName(), memberBean.getTown());
            values.put(EnumMemberTable.COLUMN_CELLNUMBER.getColumnName(), memberBean.getCellNumber());
            values.put(EnumMemberTable.COLUMN_EMAIL.getColumnName(), memberBean.getEmail());
            values.put(EnumMemberTable.COLUMN_PHONENUMBER.getColumnName(), memberBean.getPhoneNumber());
            this.daoFactory.getDatabase().insert(EnumMemberTable.getTableName(), null, values);
        }
    }

    @Override
    public void addGeolocation(GeolocationBean geolocationBean){
        daoFactory.open();
        ContentValues values = new ContentValues();
        values.put(EnumGeolocationTable.COLUMN_MEMBER_ID.getColumnName(), geolocationBean.getMemberId());
        values.put(EnumGeolocationTable.COLUMN_LATITUDE.getColumnName(), geolocationBean.getLatitude());
        values.put(EnumGeolocationTable.COLUMN_LONGITUDE.getColumnName(), geolocationBean.getLongitude());
        this.daoFactory.getDatabase().insert(EnumGeolocationTable.getTableName(), null, values);

    }

    @Override
    public void createMembers(List<MemberBean> memberBeanList){
        daoFactory.open();

        this.daoFactory.getDatabase().execSQL("DROP TABLE IF EXISTS " + EnumMemberTable.getTableName());
        this.daoFactory.getDatabase().execSQL(EnumMemberTable.getCreateCommand());
        for(MemberBean memberBean:memberBeanList) {
            ContentValues values = new ContentValues();
            values.put(EnumMemberTable.COLUMN_REMOTE_ID.getColumnName(), memberBean.getRemote_id());
            values.put(EnumMemberTable.COLUMN_FORNAME.getColumnName(), memberBean.getForname());
            values.put(EnumMemberTable.COLUMN_NAME.getColumnName(), memberBean.getName());
            values.put(EnumMemberTable.COLUMN_ADDRESS.getColumnName(), memberBean.getAddress());
            values.put(EnumMemberTable.COLUMN_POSTAL_CODE.getColumnName(), memberBean.getPostalCode());
            values.put(EnumMemberTable.COLUMN_TOWN.getColumnName(), memberBean.getTown());
            values.put(EnumMemberTable.COLUMN_CELLNUMBER.getColumnName(), memberBean.getCellNumber());
            values.put(EnumMemberTable.COLUMN_EMAIL.getColumnName(), memberBean.getEmail());
            values.put(EnumMemberTable.COLUMN_PHONENUMBER.getColumnName(), memberBean.getPhoneNumber());
            this.daoFactory.getDatabase().insert(EnumMemberTable.getTableName(), null, values);
        }

    }

    @Override
    public void updateMembers(List<MemberBean> memberBeanList){
        daoFactory.open();

        for(MemberBean memberBean:memberBeanList) {
            if(memberBean.isActive()) {
                ContentValues values = new ContentValues();
                values.put(EnumMemberTable.COLUMN_REMOTE_ID.getColumnName(), memberBean.getRemote_id());
                values.put(EnumMemberTable.COLUMN_FORNAME.getColumnName(), memberBean.getForname());
                values.put(EnumMemberTable.COLUMN_NAME.getColumnName(), memberBean.getName());
                values.put(EnumMemberTable.COLUMN_ADDRESS.getColumnName(), memberBean.getAddress());
                values.put(EnumMemberTable.COLUMN_POSTAL_CODE.getColumnName(), memberBean.getPostalCode());
                values.put(EnumMemberTable.COLUMN_TOWN.getColumnName(), memberBean.getTown());
                values.put(EnumMemberTable.COLUMN_CELLNUMBER.getColumnName(), memberBean.getCellNumber());
                values.put(EnumMemberTable.COLUMN_EMAIL.getColumnName(), memberBean.getEmail());
                values.put(EnumMemberTable.COLUMN_PHONENUMBER.getColumnName(), memberBean.getPhoneNumber());
                this.daoFactory.getDatabase().insert(EnumMemberTable.getTableName(), null, values);
            } else {
                this.daoFactory.getDatabase().delete(EnumMemberTable.getTableName(),
                        EnumMemberTable.COLUMN_REMOTE_ID.getColumnName()+"="+ memberBean.getRemote_id(),null);
            }
        }

    }

    @Override
    public void createGeolocations(List<GeolocationBean> geolocationBeanList){
        daoFactory.open();

        this.daoFactory.getDatabase().execSQL("DROP TABLE IF EXISTS " + EnumGeolocationTable.getTableName());
        this.daoFactory.getDatabase().execSQL(EnumGeolocationTable.getCreateCommand());
        for(GeolocationBean geolocationBean:geolocationBeanList) {
            ContentValues values = new ContentValues();
            values.put(EnumGeolocationTable.COLUMN_MEMBER_ID.getColumnName(), geolocationBean.getMemberId());
            values.put(EnumGeolocationTable.COLUMN_LATITUDE.getColumnName(), geolocationBean.getLatitude());
            values.put(EnumGeolocationTable.COLUMN_LONGITUDE.getColumnName(), geolocationBean.getLongitude());
            this.daoFactory.getDatabase().insert(EnumGeolocationTable.getTableName(), null, values);
        }

    }

    /* helper methods */
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

    private GeolocationBean cursorToGeolocation(Cursor cursor) {
        GeolocationBean geolocationBean = new GeolocationBean();
        geolocationBean.setId(cursor.getLong(0));
        geolocationBean.setMemberId(cursor.getLong(1));
        geolocationBean.setLatitude(cursor.getFloat(2));
        geolocationBean.setLongitude(cursor.getFloat(3));
        return geolocationBean;
    }
}
