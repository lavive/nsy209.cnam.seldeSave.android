package nsy209.cnam.seldesave.dao.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import nsy209.cnam.seldesave.activity.utils.ActivityConstant;

/**
 * Created by lavive on 02/06/17.
 */

public class AssociationPreferences {
    private static SharedPreferences associationPreferences;

    private static final String ASSOCIATION_NAME = "association";
    private static final String ID = "id";
    private static final String REMOTE_ID = "remote_id";
    private static final String NAME = "name";
    private static final String WEBSITE = "webSite";
    private static final String ADDRESS = "address";
    private static final String POSTALCODE = "postal_code";
    private static final String TOWN = "town";
    private static final String CELLNUMBER = "cellNumber";
    private static final String EMAIL = "email";
    private static final String PHONENUMBER = "phoneNumber";

    public static void loadAssociation(Context context) {
        associationPreferences = context.getSharedPreferences(ASSOCIATION_NAME, Context.MODE_PRIVATE);
    }

    /* methods */
    public static void putId(long id) {
        associationPreferences.edit().putLong(ID, id).apply();
    }

    public static void putRemoteId(long remoteId) {
        associationPreferences.edit().putLong(REMOTE_ID, remoteId).apply();
    }

    public static void putName(String name) {
        associationPreferences.edit().putString(NAME, name).apply();
    }

    public static void putWebSite(String webSite) {
        associationPreferences.edit().putString(WEBSITE, webSite).apply();
    }

    public static void putAddress(String address) {
        associationPreferences.edit().putString(ADDRESS, address).apply();
    }

    public static void putPostalCode(String postalCode) {
        associationPreferences.edit().putString(POSTALCODE, postalCode).apply();
    }

    public static void putTown(String town) {
        associationPreferences.edit().putString(TOWN, town).apply();
    }

    public static void putCellNumber(String cellNumber) {
        associationPreferences.edit().putString(CELLNUMBER, cellNumber).apply();
    }

    public static void putEmail(String email) {
        associationPreferences.edit().putString(EMAIL, email).apply();
    }

    public static void putPhoneNumber(String phoneNumber) {
        associationPreferences.edit().putString(PHONENUMBER, phoneNumber).apply();
    }

    public static void clear(){
        putId(ActivityConstant.NOTEXIST);
        putRemoteId(ActivityConstant.NOTEXIST);
        putWebSite("");
        putPhoneNumber("");
        putCellNumber("");
        putTown("");
        putPostalCode("");
        putAddress("");
        putEmail("");
        putName("");
    }

    /* getter */
    public static long getId() {
        return associationPreferences.getLong(ID, ActivityConstant.NOTEXIST);
    }

    public static long getRemoteId() {
        return associationPreferences.getLong(REMOTE_ID, ActivityConstant.NOTEXIST);
    }

    public static String getName() {
        return associationPreferences.getString(NAME, null);
    }

    public static String getWebsite() {
        return associationPreferences.getString(WEBSITE, null);
    }

    public static String getAddress() {
        return associationPreferences.getString(ADDRESS, null);
    }

    public static String getPostalcode() {
        return associationPreferences.getString(POSTALCODE, null);
    }

    public static String getTown() {
        return associationPreferences.getString(TOWN, null);
    }

    public static String getCellnumber() {
        return associationPreferences.getString(CELLNUMBER, null);
    }

    public static String getEmail() {
        return associationPreferences.getString(EMAIL, null);
    }

    public static String getPhoneNumber() {
        return associationPreferences.getString(PHONENUMBER, null);
    }
}

