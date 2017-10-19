package nsy209.cnam.seldesave.dao.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lavive on 29/09/17.
 */

public class MyProfileBufferPreferences {
    private static SharedPreferences myProfileBufferPreferences;

    private static final String MYPROFILE_NAME="myProfile_buffer";
    private static final String NAME="name";
    private static final String FORNAME="forname";
    private static final String ADDRESS="address";
    private static final String POSTALCODE="postal_code";
    private static final String TOWN="town";
    private static final String CELLNUMBER="cellNumber";
    private static final String EMAIL="email";
    private static final String PHONENUMBER="phoneNumber";

    public static void loadMyProfileBuffer(Context context){
        myProfileBufferPreferences = context.getSharedPreferences(MYPROFILE_NAME,Context.MODE_PRIVATE);
    }

    /* methods */

    public static void putName(String name ){
        myProfileBufferPreferences.edit().putString(NAME,name).apply();
    }
    public static void putForname(String forname ){
        myProfileBufferPreferences.edit().putString(FORNAME,forname).apply();
    }
    public static void putAddress(String address ){
        myProfileBufferPreferences.edit().putString(ADDRESS,address).apply();
    }
    public static void putPostalCode(String postalCode ){
        myProfileBufferPreferences.edit().putString(POSTALCODE,postalCode).apply();
    }
    public static void putTown(String town ){
        myProfileBufferPreferences.edit().putString(TOWN,town).apply();
    }
    public static void putCellNumber(String cellNumber ){
        myProfileBufferPreferences.edit().putString(CELLNUMBER,cellNumber).apply();
    }
    public static void putEmail(String email ){
        myProfileBufferPreferences.edit().putString(EMAIL,email).apply();
    }
    public static void putPhoneNumber(String phoneNumber ){
        myProfileBufferPreferences.edit().putString(PHONENUMBER,phoneNumber).apply();
    }

    public static void clear(){
        putForname("");
        putPhoneNumber("");
        putCellNumber("");
        putTown("");
        putPostalCode("");
        putAddress("");
        putEmail("");
        putName("");
    }

    /* getter */
    public static String getName(){
        return myProfileBufferPreferences.getString(NAME,null);
    }
    public static String getForname(){
        return myProfileBufferPreferences.getString(FORNAME,null);
    }
    public static String getAddress(){
        return myProfileBufferPreferences.getString(ADDRESS,null);
    }
    public static String getPostalcode(){
        return myProfileBufferPreferences.getString(POSTALCODE,null);
    }
    public static String getTown(){
        return myProfileBufferPreferences.getString(TOWN,null);
    }
    public static String getCellnumber(){
        return myProfileBufferPreferences.getString(CELLNUMBER,null);
    }
    public static String getEmail(){
        return myProfileBufferPreferences.getString(EMAIL,null);
    }
    public static String getPhoneNumber(){
        return myProfileBufferPreferences.getString(PHONENUMBER,null);
    }

}
