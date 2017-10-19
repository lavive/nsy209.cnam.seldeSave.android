package nsy209.cnam.seldesave.dao.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;


import nsy209.cnam.seldesave.activity.utils.ActivityConstant;

/**
 * Created by lavive on 02/06/17.
 */

public class MyProfilePreferences {
    private static SharedPreferences myProfilePreferences;

    private static final String MYPROFILE_NAME="myProfile";
    private static final String ID="id";
    private static final String REMOTE_ID="remote_id";
    private static final String MOBILE_ID="mobile_id";
    private static final String NAME="name";
    private static final String FORNAME="forname";
    private static final String MOBILE="mobile";
    private static final String ADDRESS="address";
    private static final String POSTALCODE="postal_code";
    private static final String TOWN="town";
    private static final String CELLNUMBER="cellNumber";
    private static final String EMAIL="email";
    private static final String PHONENUMBER="phoneNumber";

    public static void loadMyProfile(Context context){
        myProfilePreferences = context.getSharedPreferences(MYPROFILE_NAME,Context.MODE_PRIVATE);
    }

    /* methods */
    public static void putId(long id){
        myProfilePreferences.edit().putLong(ID,id).apply();
    }
    public static void putRemoteId(long remoteId){
        myProfilePreferences.edit().putLong(REMOTE_ID,remoteId).apply();
    }
    public static void putMobileId(String mobileId){
        myProfilePreferences.edit().putString(MOBILE_ID,mobileId).apply();
    }
    public static void putName(String name ){
        myProfilePreferences.edit().putString(NAME,name).apply();
    }
    public static void putForname(String forname ){
        myProfilePreferences.edit().putString(FORNAME,forname).apply();
    }
    public static void putMobile(String mobile ){
        myProfilePreferences.edit().putString(MOBILE,mobile).apply();
    }
    public static void putAddress(String address ){
        myProfilePreferences.edit().putString(ADDRESS,address).apply();
    }
    public static void putPostalCode(String postalCode ){
        myProfilePreferences.edit().putString(POSTALCODE,postalCode).apply();
    }
    public static void putTown(String town ){
        myProfilePreferences.edit().putString(TOWN,town).apply();
    }
    public static void putCellNumber(String cellNumber ){
        myProfilePreferences.edit().putString(CELLNUMBER,cellNumber).apply();
    }
    public static void putEmail(String email ){
        myProfilePreferences.edit().putString(EMAIL,email).apply();
    }
    public static void putPhoneNumber(String phoneNumber ){
        myProfilePreferences.edit().putString(PHONENUMBER,phoneNumber).apply();
    }

    public static void clear(){
        putId(ActivityConstant.NOTEXIST);
        putRemoteId(ActivityConstant.NOTEXIST);
        putForname("");
        putPhoneNumber("");
        putCellNumber("");
        putTown("");
        putPostalCode("");
        putAddress("");
        putEmail("");
        putName("");
        putMobileId("");
        putMobile("");
    }

    /* getter */
    public static long getId(){
        return myProfilePreferences.getLong(ID, ActivityConstant.NOTEXIST);
    }
    public static long getRemoteId(){
        return myProfilePreferences.getLong(REMOTE_ID,ActivityConstant.NOTEXIST);
    }
    public static String getMobileId(){
        return myProfilePreferences.getString(MOBILE_ID,null);
    }
    public static String getName(){
        return myProfilePreferences.getString(NAME,null);
    }
    public static String getForname(){
        return myProfilePreferences.getString(FORNAME,null);
    }
    public static String getMobile(){
        return myProfilePreferences.getString(MOBILE,null);
    }
    public static String getAddress(){
        return myProfilePreferences.getString(ADDRESS,null);
    }
    public static String getPostalcode(){
        return myProfilePreferences.getString(POSTALCODE,null);
    }
    public static String getTown(){
        return myProfilePreferences.getString(TOWN,null);
    }
    public static String getCellnumber(){
        return myProfilePreferences.getString(CELLNUMBER,null);
    }
    public static String getEmail(){
        return myProfilePreferences.getString(EMAIL,null);
    }
    public static String getPhoneNumber(){
        return myProfilePreferences.getString(PHONENUMBER,null);
    }

}