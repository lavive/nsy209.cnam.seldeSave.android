package nsy209.cnam.seldesave.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import nsy209.cnam.seldesave.dao.enumTable.EnumCategoryTable;
import nsy209.cnam.seldesave.dao.enumTable.EnumGeolocationTable;
import nsy209.cnam.seldesave.dao.enumTable.EnumMemberTable;
import nsy209.cnam.seldesave.dao.enumTable.EnumNotificationTable;
import nsy209.cnam.seldesave.dao.enumTable.EnumTransactionTable;
import nsy209.cnam.seldesave.dao.impl.AssociationDaoImpl;
import nsy209.cnam.seldesave.dao.impl.CategoryDaoImpl;
import nsy209.cnam.seldesave.dao.impl.ErrorMessageDaoImpl;
import nsy209.cnam.seldesave.dao.impl.MemberDaoImpl;
import nsy209.cnam.seldesave.dao.impl.MyProfileDaoImpl;
import nsy209.cnam.seldesave.dao.impl.SupplyDemandDaoImpl;
import nsy209.cnam.seldesave.dao.enumTable.EnumSupplyDemandTable;
import nsy209.cnam.seldesave.dao.impl.TransactionDaoImpl;
import nsy209.cnam.seldesave.dao.sharedPreferences.AssociationPreferences;
import nsy209.cnam.seldesave.dao.sharedPreferences.ErrorMessagePreferences;
import nsy209.cnam.seldesave.dao.sharedPreferences.FilterPreferences;
import nsy209.cnam.seldesave.dao.sharedPreferences.MyProfileBufferPreferences;
import nsy209.cnam.seldesave.dao.sharedPreferences.MyProfilePreferences;
import nsy209.cnam.seldesave.dao.sharedPreferences.WealthSheetPreferences;

/**
 * Created by lavive on 01/06/17.
 */

public class DaoFactory extends SQLiteOpenHelper {

    /* static attributes */
    private static final String DATABASE_NAME = "sel_services.db";
    private static final int DATABASE_VERSION = 1;

    /* attribute */
    private SQLiteDatabase database;

    /* singleton */
    private static DaoFactory instance;

    public  static synchronized DaoFactory getInstance(Context context) {
        if(instance == null){
            instance = new DaoFactory(context);
        }
        return instance;
    }

    private DaoFactory(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        AssociationPreferences.loadAssociation(context);
        MyProfilePreferences.loadMyProfile(context);
        MyProfileBufferPreferences.loadMyProfileBuffer(context);
        WealthSheetPreferences.loadWealthSheet(context);
        FilterPreferences.loadFilter(context);
        ErrorMessagePreferences.loadErrorMessage(context);
    }

    /* getter */

    public static String getDATABASE_NAME() {
        return DATABASE_NAME;
    }

    public static int getDATABASE_VERSION() {
        return DATABASE_VERSION;
    }

    public synchronized SQLiteDatabase getDatabase() {
        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(EnumSupplyDemandTable.getCreateCommandAll());
        database.execSQL(EnumSupplyDemandTable.getCreateCommandMy());
        database.execSQL(EnumMemberTable.getCreateCommand());
        database.execSQL(EnumMemberTable.getCreateCommandFilter());
        database.execSQL(EnumMemberTable.getCreateCommandBuffer());
        database.execSQL(EnumTransactionTable.getCreateCommandNew());
        database.execSQL(EnumTransactionTable.getCreateCommandChecked());
        database.execSQL(EnumNotificationTable.getCreateCommand());
        database.execSQL(EnumNotificationTable.getCreateCommandBuffer());
        database.execSQL(EnumCategoryTable.getCreateCommand());
        database.execSQL(EnumCategoryTable.getCreateCommandFilter());
        database.execSQL(EnumGeolocationTable.getCreateCommand());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + EnumSupplyDemandTable.getTableNameAll());
        db.execSQL("DROP TABLE IF EXISTS " + EnumSupplyDemandTable.getTableNameMy());
        db.execSQL("DROP TABLE IF EXISTS " + EnumMemberTable.getTableName());
        db.execSQL("DROP TABLE IF EXISTS " + EnumMemberTable.getTableNameFilter());
        db.execSQL("DROP TABLE IF EXISTS " + EnumMemberTable.getTableNameBuffer());
        db.execSQL("DROP TABLE IF EXISTS " + EnumTransactionTable.getTableNameNew());
        db.execSQL("DROP TABLE IF EXISTS " + EnumTransactionTable.getTableNameChecked());
        db.execSQL("DROP TABLE IF EXISTS " + EnumNotificationTable.getTableName());
        db.execSQL("DROP TABLE IF EXISTS " + EnumNotificationTable.getTableNameBuffer());
        db.execSQL("DROP TABLE IF EXISTS " + EnumCategoryTable.getTableName());
        db.execSQL("DROP TABLE IF EXISTS " + EnumCategoryTable.getTableNameFilter());
        db.execSQL("DROP TABLE IF EXISTS " + EnumGeolocationTable.getTableName());
        onCreate(db);
    }

    /* Clear database */
    public void clear(){
        if(database != null) {
            /* clear all sharedpreferences */
            AssociationPreferences.clear();
            MyProfilePreferences.clear();
            MyProfileBufferPreferences.clear();
            WealthSheetPreferences.clear();
            FilterPreferences.clear();
            /* drop all tables */
            database.execSQL("DROP TABLE IF EXISTS " + EnumSupplyDemandTable.getTableNameAll());
            database.execSQL("DROP TABLE IF EXISTS " + EnumSupplyDemandTable.getTableNameMy());
            database.execSQL("DROP TABLE IF EXISTS " + EnumMemberTable.getTableName());
            database.execSQL("DROP TABLE IF EXISTS " + EnumMemberTable.getTableNameFilter());
            database.execSQL("DROP TABLE IF EXISTS " + EnumMemberTable.getTableNameBuffer());
            database.execSQL("DROP TABLE IF EXISTS " + EnumTransactionTable.getTableNameNew());
            database.execSQL("DROP TABLE IF EXISTS " + EnumTransactionTable.getTableNameChecked());
            database.execSQL("DROP TABLE IF EXISTS " + EnumNotificationTable.getTableName());
            database.execSQL("DROP TABLE IF EXISTS " + EnumNotificationTable.getTableNameBuffer());
            database.execSQL("DROP TABLE IF EXISTS " + EnumCategoryTable.getTableName());
            database.execSQL("DROP TABLE IF EXISTS " + EnumCategoryTable.getTableNameFilter());
            database.execSQL("DROP TABLE IF EXISTS " + EnumGeolocationTable.getTableName());
            /* recreate all tables */
            database.execSQL(EnumSupplyDemandTable.getCreateCommandAll());
            database.execSQL(EnumSupplyDemandTable.getCreateCommandMy());
            database.execSQL(EnumMemberTable.getCreateCommand());
            database.execSQL(EnumMemberTable.getCreateCommandFilter());
            database.execSQL(EnumMemberTable.getCreateCommandBuffer());
            database.execSQL(EnumTransactionTable.getCreateCommandNew());
            database.execSQL(EnumTransactionTable.getCreateCommandChecked());
            database.execSQL(EnumNotificationTable.getCreateCommand());
            database.execSQL(EnumNotificationTable.getCreateCommandBuffer());
            database.execSQL(EnumCategoryTable.getCreateCommand());
            database.execSQL(EnumCategoryTable.getCreateCommandFilter());
            database.execSQL(EnumGeolocationTable.getCreateCommand());
        }
    }


    /* DAOs */

    public SupplyDemandDao getSupplyDemandDao(){
        return SupplyDemandDaoImpl.getInstance(this);
    }

    public MemberDao getMemberDao(){
        return MemberDaoImpl.getInstance(this);
    }

    public TransactionDao getTransactionDao() { return TransactionDaoImpl.getInstance(this); }

    public AssociationDao getAssociationDao() { return AssociationDaoImpl.getInstance(this); }

    public MyProfileDao getMyProfileDao() { return MyProfileDaoImpl.getInstance(this); }

    public ErrorMessageDao getErrorMessageDao() {return ErrorMessageDaoImpl.getInstance(this); }

    public CategoryDao getCategoryDao() {return CategoryDaoImpl.getInstance(this); }


    /* connection */

    public void open() throws SQLException {
        database = this.getWritableDatabase();
    }

    public boolean isOpen(){
        return this.database.isOpen();
    }

}
