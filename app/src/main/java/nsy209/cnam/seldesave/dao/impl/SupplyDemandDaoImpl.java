package nsy209.cnam.seldesave.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import nsy209.cnam.seldesave.bean.CategoryBean;
import nsy209.cnam.seldesave.bean.MemberBean;
import nsy209.cnam.seldesave.bean.helper.EnumSupplyDemand;
import nsy209.cnam.seldesave.bean.SupplyDemandBean;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.dao.SupplyDemandDao;
import nsy209.cnam.seldesave.dao.enumTable.EnumCategoryTable;
import nsy209.cnam.seldesave.dao.enumTable.EnumMemberTable;
import nsy209.cnam.seldesave.dao.enumTable.EnumSupplyDemandTable;

/**
 * Created by lavive on 02/06/17.
 */

public class SupplyDemandDaoImpl implements SupplyDemandDao {

    public static  SupplyDemandDao getInstance(DaoFactory daoFactory){

            return new SupplyDemandDaoImpl(daoFactory);

    }

    /* attribute */
    private DaoFactory daoFactory;

    private SupplyDemandDaoImpl(DaoFactory daoFactory){
        this.daoFactory = daoFactory;
    }


    @Override
    public List<SupplyDemandBean> getAllSuppliesDemands(){
        daoFactory.open();
        List<SupplyDemandBean> suppliesDemands = new ArrayList<SupplyDemandBean>();

        Cursor cursor = this.daoFactory.getDatabase().query(EnumSupplyDemandTable.getTableNameAll(), EnumSupplyDemandTable.getAllColumns(),
                                        null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SupplyDemandBean supplyDemand = cursorToSupplyDemand(cursor);
            suppliesDemands.add(supplyDemand);
            cursor.moveToNext();
        }

        cursor.close();
        return suppliesDemands;
    }

    @Override
    public List<SupplyDemandBean> getAllSupplies(){
        daoFactory.open();
        List<SupplyDemandBean> supplies = new ArrayList<SupplyDemandBean>();

        Cursor cursor = this.daoFactory.getDatabase().query(EnumSupplyDemandTable.getTableNameAll(), EnumSupplyDemandTable.getAllColumns(),
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SupplyDemandBean supplyDemand = cursorToSupplyDemand(cursor);
            if(supplyDemand.getType().equals(EnumSupplyDemand.SUPPLY)) {
                supplies.add(supplyDemand);
            }
            cursor.moveToNext();
        }

        cursor.close();
        return supplies;
    }

    @Override
    public List<SupplyDemandBean> getAllDemands(){
        daoFactory.open();
        List<SupplyDemandBean> demands = new ArrayList<SupplyDemandBean>();

        Cursor cursor = this.daoFactory.getDatabase().query(EnumSupplyDemandTable.getTableNameAll(), EnumSupplyDemandTable.getAllColumns(),
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SupplyDemandBean supplyDemand = cursorToSupplyDemand(cursor);
            if(supplyDemand.getType().equals(EnumSupplyDemand.DEMAND)) {
                demands.add(supplyDemand);
            }
            cursor.moveToNext();
        }

        cursor.close();

        return demands;
    }

    @Override
    public List<SupplyDemandBean> getAllSuppliesDemands(MemberBean memberBean){
        daoFactory.open();

        List<SupplyDemandBean> suppliesDemands = new ArrayList<SupplyDemandBean>();

        Cursor cursor = this.daoFactory.getDatabase().query(EnumSupplyDemandTable.getTableNameAll(), EnumSupplyDemandTable.getAllColumns(),
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SupplyDemandBean supplyDemand = cursorToSupplyDemand(cursor);
            if(supplyDemand.getMember().getRemote_id() == memberBean.getRemote_id()) {
                suppliesDemands.add(supplyDemand);
            }
            cursor.moveToNext();
        }

        cursor.close();

        return suppliesDemands;
    }

    @Override
    public SupplyDemandBean getSupplyDemand(long id){
        daoFactory.open();
        Cursor cursor = this.daoFactory.getDatabase().query(EnumSupplyDemandTable.getTableNameAll(), EnumSupplyDemandTable.getAllColumns(),
                EnumSupplyDemandTable.COLUMN_ID.getColumnName() + " = " + id, null,
                null, null, null);

        cursor.moveToFirst();
        SupplyDemandBean supplyDemand = cursorToSupplyDemand(cursor);

        cursor.close();
        return supplyDemand;

    }

    @Override
    public SupplyDemandBean getSupplyDemandByRemoteId(long remoteId){
        daoFactory.open();
        Cursor cursor = this.daoFactory.getDatabase().query(EnumSupplyDemandTable.getTableNameAll(), EnumSupplyDemandTable.getAllColumns(),
                EnumSupplyDemandTable.COLUMN_REMOTE_ID.getColumnName() + " = " + remoteId, null,
                null, null, null);

        cursor.moveToFirst();
        SupplyDemandBean supplyDemand = cursorToSupplyDemand(cursor);

        cursor.close();
        return supplyDemand;

    }

    @Override
    public List<CategoryBean> getAllCategories(){
        daoFactory.open();
        List<CategoryBean> categoriesBean = new ArrayList<CategoryBean>();

        Cursor cursor = this.daoFactory.getDatabase().query(EnumCategoryTable.getTableName(), EnumCategoryTable.getAllColumns(),
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            CategoryBean categoryBean = cursorToCategory(cursor);
            categoriesBean.add(categoryBean);
            cursor.moveToNext();
        }

        cursor.close();
        return categoriesBean;

    }

    @Override
    public List<SupplyDemandBean> getSuppliesDemandsAbleToBePayed(){
        daoFactory.open();
        List<SupplyDemandBean> suppliesDemandsBean = new ArrayList<SupplyDemandBean>();
        long remote_id = this.daoFactory.getMyProfileDao().getMyProfile().getRemote_id();

        Cursor cursor = this.daoFactory.getDatabase().query(EnumSupplyDemandTable.getTableNameAll(), EnumSupplyDemandTable.getAllColumns(),
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SupplyDemandBean supplyDemand = cursorToSupplyDemand(cursor);
            if((supplyDemand.getType().equals(EnumSupplyDemand.SUPPLY) && supplyDemand.getMember().getRemote_id() != remote_id) ||
                    (supplyDemand.getMember().getRemote_id() == remote_id && supplyDemand.getType().equals(EnumSupplyDemand.DEMAND))) {
                suppliesDemandsBean.add(supplyDemand);
            }
            cursor.moveToNext();
        }

        cursor.close();

        return suppliesDemandsBean;
    }

    @Override
    public List<SupplyDemandBean> getMySuppliesDemands(){
        daoFactory.open();
        long memberRemoteId = daoFactory.getMyProfileDao().getMyProfile().getRemote_id();

        List<SupplyDemandBean> suppliesDemands = new ArrayList<SupplyDemandBean>();

        Cursor cursor = this.daoFactory.getDatabase().query(EnumSupplyDemandTable.getTableNameAll(), EnumSupplyDemandTable.getAllColumns(),
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SupplyDemandBean supplyDemand = cursorToSupplyDemand(cursor);
            if(supplyDemand.getMember().getRemote_id() == memberRemoteId) {
                suppliesDemands.add(supplyDemand);
            }
            cursor.moveToNext();
        }

        cursor.close();

        return suppliesDemands;
    }

    @Override
    public void addSuppliesDemands(List<SupplyDemandBean> supplyDemandBeanList){
        daoFactory.open();
        List<SupplyDemandBean> mySupplyDemandBeanList = new ArrayList<SupplyDemandBean>();
        for(SupplyDemandBean supplyDemandBean:supplyDemandBeanList) {
            if(supplyDemandBean.getMember().getRemote_id() == daoFactory.getMyProfileDao().getMyProfile().getRemote_id()){
                mySupplyDemandBeanList.add(supplyDemandBean);
            }
            ContentValues values = new ContentValues();
            values.put(EnumSupplyDemandTable.COLUMN_REMOTE_ID.getColumnName(), supplyDemandBean.getRemote_id());
            values.put(EnumSupplyDemandTable.COLUMN_TYPE.getColumnName(), supplyDemandBean.getType().getWording());
            values.put(EnumSupplyDemandTable.COLUMN_CATEGORY.getColumnName(), supplyDemandBean.getCategory());
            values.put(EnumSupplyDemandTable.COLUMN_TITLE.getColumnName(), supplyDemandBean.getTitle());
            values.put(EnumSupplyDemandTable.COLUMN_ID_MEMBER.getColumnName(), supplyDemandBean.getMember().getRemote_id());
            if(supplyDemandBean.isChecked()) {
                values.put(EnumSupplyDemandTable.COLUMN_CHECKED.getColumnName(), 1);
            }else {
                values.put(EnumSupplyDemandTable.COLUMN_CHECKED.getColumnName(), 0);
            }
            if(supplyDemandBean.isActive()){
                values.put(EnumSupplyDemandTable.COLUMN_ACTIVE.getColumnName(), 1);
            }else {
                values.put(EnumSupplyDemandTable.COLUMN_ACTIVE.getColumnName(), 0);
            }
            this.daoFactory.getDatabase().insert(EnumSupplyDemandTable.getTableNameAll(), null, values);
        }
        addMySuppliesDemands(mySupplyDemandBeanList);

    }
    @Override
    public void addMySuppliesDemands(List<SupplyDemandBean> supplyDemandBeanList){
        daoFactory.open();
        for(SupplyDemandBean supplyDemandBean:supplyDemandBeanList) {
            ContentValues values = new ContentValues();
            values.put(EnumSupplyDemandTable.COLUMN_REMOTE_ID.getColumnName(), supplyDemandBean.getRemote_id());
            values.put(EnumSupplyDemandTable.COLUMN_TYPE.getColumnName(), supplyDemandBean.getType().getWording());
            values.put(EnumSupplyDemandTable.COLUMN_CATEGORY.getColumnName(), supplyDemandBean.getCategory());
            values.put(EnumSupplyDemandTable.COLUMN_TITLE.getColumnName(), supplyDemandBean.getTitle());
            values.put(EnumSupplyDemandTable.COLUMN_ID_MEMBER.getColumnName(), supplyDemandBean.getMember().getRemote_id());
            if(supplyDemandBean.isChecked()) {
                values.put(EnumSupplyDemandTable.COLUMN_CHECKED.getColumnName(), 1);
            }else {
                values.put(EnumSupplyDemandTable.COLUMN_CHECKED.getColumnName(), 0);
            }
            if(supplyDemandBean.isActive()){
                values.put(EnumSupplyDemandTable.COLUMN_ACTIVE.getColumnName(), 1);
            }else {
                values.put(EnumSupplyDemandTable.COLUMN_ACTIVE.getColumnName(), 0);
            }
            this.daoFactory.getDatabase().insert(EnumSupplyDemandTable.getTableNameMy(), null, values);
        }

    }

    @Override
    public void createSuppliesDemands(List<SupplyDemandBean> supplyDemandBeanList){
        daoFactory.open();

        this.daoFactory.getDatabase().execSQL("DROP TABLE IF EXISTS " + EnumSupplyDemandTable.getTableNameAll());
        this.daoFactory.getDatabase().execSQL(EnumSupplyDemandTable.getCreateCommandAll());

        List<SupplyDemandBean> mySupplyDemandBeanList = new ArrayList<SupplyDemandBean>();
        for(SupplyDemandBean supplyDemandBean:supplyDemandBeanList) {
            if(supplyDemandBean.getMember().getRemote_id() == daoFactory.getMyProfileDao().getMyProfile().getRemote_id()){
                mySupplyDemandBeanList.add(supplyDemandBean);
            }
            ContentValues values = new ContentValues();
            values.put(EnumSupplyDemandTable.COLUMN_REMOTE_ID.getColumnName(), supplyDemandBean.getRemote_id());
            values.put(EnumSupplyDemandTable.COLUMN_TYPE.getColumnName(), supplyDemandBean.getType().getWording());
            values.put(EnumSupplyDemandTable.COLUMN_CATEGORY.getColumnName(), supplyDemandBean.getCategory());
            values.put(EnumSupplyDemandTable.COLUMN_TITLE.getColumnName(), supplyDemandBean.getTitle());
            values.put(EnumSupplyDemandTable.COLUMN_ID_MEMBER.getColumnName(), supplyDemandBean.getMember().getRemote_id());
            if(supplyDemandBean.isChecked()) {
                values.put(EnumSupplyDemandTable.COLUMN_CHECKED.getColumnName(), 1);
            }else {
                values.put(EnumSupplyDemandTable.COLUMN_CHECKED.getColumnName(), 0);
            }
            if(supplyDemandBean.isActive()){
                values.put(EnumSupplyDemandTable.COLUMN_ACTIVE.getColumnName(), 1);
            }else {
                values.put(EnumSupplyDemandTable.COLUMN_ACTIVE.getColumnName(), 0);
            }
            this.daoFactory.getDatabase().insert(EnumSupplyDemandTable.getTableNameAll(), null, values);
        }
        createMySuppliesDemands(mySupplyDemandBeanList);

    }

    @Override
    public void createMySuppliesDemands(List<SupplyDemandBean> supplyDemandBeanList){
        daoFactory.open();

        this.daoFactory.getDatabase().execSQL("DROP TABLE IF EXISTS " + EnumSupplyDemandTable.getTableNameMy());
        this.daoFactory.getDatabase().execSQL(EnumSupplyDemandTable.getCreateCommandMy());
        for(SupplyDemandBean supplyDemandBean:supplyDemandBeanList) {
            ContentValues values = new ContentValues();
            values.put(EnumSupplyDemandTable.COLUMN_REMOTE_ID.getColumnName(), supplyDemandBean.getRemote_id());
            values.put(EnumSupplyDemandTable.COLUMN_TYPE.getColumnName(), supplyDemandBean.getType().getWording());
            values.put(EnumSupplyDemandTable.COLUMN_CATEGORY.getColumnName(), supplyDemandBean.getCategory());
            values.put(EnumSupplyDemandTable.COLUMN_TITLE.getColumnName(), supplyDemandBean.getTitle());
            values.put(EnumSupplyDemandTable.COLUMN_ID_MEMBER.getColumnName(), supplyDemandBean.getMember().getRemote_id());
            if(supplyDemandBean.isChecked()) {
                values.put(EnumSupplyDemandTable.COLUMN_CHECKED.getColumnName(), 1);
            }else {
                values.put(EnumSupplyDemandTable.COLUMN_CHECKED.getColumnName(), 0);
            }
            if(supplyDemandBean.isActive()){
                values.put(EnumSupplyDemandTable.COLUMN_ACTIVE.getColumnName(), 1);
            }else {
                values.put(EnumSupplyDemandTable.COLUMN_ACTIVE.getColumnName(), 0);
            }
            this.daoFactory.getDatabase().insert(EnumSupplyDemandTable.getTableNameMy(), null, values);
        }

    }

    @Override
    public void createCategories(List<CategoryBean> categories){
        daoFactory.open();

        this.daoFactory.getDatabase().execSQL("DROP TABLE IF EXISTS " + EnumCategoryTable.getTableName());
        this.daoFactory.getDatabase().execSQL(EnumCategoryTable.getCreateCommand());
        for(CategoryBean categoryBean:categories) {
            ContentValues values = new ContentValues();
            values.put(EnumCategoryTable.COLUMN_REMOTE_ID.getColumnName(), categoryBean.getRemote_id());
            values.put(EnumCategoryTable.COLUMN_CATEGORY.getColumnName(), categoryBean.getCategory());
            this.daoFactory.getDatabase().insert(EnumCategoryTable.getTableName(), null, values);
        }
    }

    @Override
    public void updateCategories(List<CategoryBean> categories){
        daoFactory.open();

        for(CategoryBean categoryBean:categories) {
            if(categoryBean.isActive()) {
                ContentValues values = new ContentValues();
                values.put(EnumCategoryTable.COLUMN_REMOTE_ID.getColumnName(), categoryBean.getRemote_id());
                values.put(EnumCategoryTable.COLUMN_CATEGORY.getColumnName(), categoryBean.getCategory());
                this.daoFactory.getDatabase().insert(EnumMemberTable.getTableName(), null, values);
            }else{
                this.daoFactory.getDatabase().delete(EnumCategoryTable.getTableName(),
                        EnumCategoryTable.COLUMN_REMOTE_ID.getColumnName()+"="+ categoryBean.getRemote_id(),null);
            }
        }
    }

    @Override
    public void updateSuppliesDemands(List<SupplyDemandBean> supplyDemandBeanList){
        daoFactory.open();

        for(SupplyDemandBean supplyDemandBean:supplyDemandBeanList) {
            if(supplyDemandBean.isActive()) {
                ContentValues values = new ContentValues();
                values.put(EnumSupplyDemandTable.COLUMN_REMOTE_ID.getColumnName(), supplyDemandBean.getRemote_id());
                values.put(EnumSupplyDemandTable.COLUMN_TYPE.getColumnName(), supplyDemandBean.getType().getWording());
                values.put(EnumSupplyDemandTable.COLUMN_CATEGORY.getColumnName(), supplyDemandBean.getCategory());
                values.put(EnumSupplyDemandTable.COLUMN_TITLE.getColumnName(), supplyDemandBean.getTitle());
                values.put(EnumSupplyDemandTable.COLUMN_ID_MEMBER.getColumnName(), supplyDemandBean.getMember().getRemote_id());
                if(supplyDemandBean.isChecked()) {
                    values.put(EnumSupplyDemandTable.COLUMN_CHECKED.getColumnName(), 1);
                }else {
                    values.put(EnumSupplyDemandTable.COLUMN_CHECKED.getColumnName(), 0);
                }
                if(supplyDemandBean.isActive()){
                    values.put(EnumSupplyDemandTable.COLUMN_ACTIVE.getColumnName(), 1);
                }else {
                    values.put(EnumSupplyDemandTable.COLUMN_ACTIVE.getColumnName(), 0);
                }
                this.daoFactory.getDatabase().insert(EnumSupplyDemandTable.getTableNameAll(), null, values);
            } else {
                this.daoFactory.getDatabase().delete(EnumSupplyDemandTable.getTableNameAll(),
                        EnumSupplyDemandTable.COLUMN_REMOTE_ID.getColumnName()+"="+ supplyDemandBean.getRemote_id(),null);
            }
        }

    }


    /* helper method */

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

    private CategoryBean cursorToCategory(Cursor cursor) {
        CategoryBean categoryBean = new CategoryBean();
        categoryBean.setId(cursor.getLong(0));
        categoryBean.setRemote_id(cursor.getLong(1));
        categoryBean.setCategory(cursor.getString(2));
        return categoryBean;
    }
}
