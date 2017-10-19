package nsy209.cnam.seldesave.dao.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import nsy209.cnam.seldesave.validator.helper.EnumCheck;

/**
 * Created by lavive on 08/06/17.
 */

public class ErrorMessagePreferences {
    private static SharedPreferences errorMessagePreferences;

    private static final String NAME = "error_message";
    private static final String EMPTY = EnumCheck.EMPTY.getWording();
    private static final String LESS_THAN_200 = EnumCheck.LESS_THAN_200.getWording();
    private static final String LESS_THAN_100 = EnumCheck.LESS_THAN_100.getWording();
    private static final String LESS_THAN_50 = EnumCheck.LESS_THAN_50.getWording();
    private static final String BIGDECIMAL_WELL_FORMED = EnumCheck.BIGDECIMAL_WELL_FORMED.getWording();
    private static final String POSTAL_CODE__WELL_FORMED = EnumCheck.POSTAL_CODE__WELL_FORMED.getWording();
    private static final String PHONE_NUMBER_WELL_FORMED = EnumCheck.PHONE_NUMBER_WELL_FORMED.getWording();
    private static final String EMAIL_WELL_FORMED = EnumCheck.EMAIL_WELL_FORMED.getWording();
    private static final String NUMBER_WELL_FORMED = EnumCheck.NUMBER_WELL_FORMED.getWording();

    public static void loadErrorMessage(Context context) {
        errorMessagePreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    /* methods */
    public static void putEmpty(String errorMessage) {
        errorMessagePreferences.edit().putString(EMPTY, errorMessage).apply();
    }

    public static void putLess200(String errorMessage) {
        errorMessagePreferences.edit().putString(LESS_THAN_200, errorMessage).apply();
    }

    public static void putLess100(String errorMessage) {
        errorMessagePreferences.edit().putString(LESS_THAN_100, errorMessage).apply();
    }

    public static void putLess50(String errorMessage) {
        errorMessagePreferences.edit().putString(LESS_THAN_50, errorMessage).apply();
    }

    public static void putBigDecimal(String errorMessage) {
        errorMessagePreferences.edit().putString(BIGDECIMAL_WELL_FORMED, errorMessage).apply();
    }

    public static void putPostalCode(String errorMessage) {
        errorMessagePreferences.edit().putString(POSTAL_CODE__WELL_FORMED, errorMessage).apply();
    }

    public static void putNumber(String errorMessage) {
        errorMessagePreferences.edit().putString(NUMBER_WELL_FORMED, errorMessage).apply();
    }

    public static void putEmail(String errorMessage) {
        errorMessagePreferences.edit().putString(EMAIL_WELL_FORMED, errorMessage).apply();
    }

    public static void putPhoneNumber(String errorMessage) {
        errorMessagePreferences.edit().putString(PHONE_NUMBER_WELL_FORMED, errorMessage).apply();
    }

    /* getter */
    public static String getEmpty() {
        return errorMessagePreferences.getString(EMPTY, null);
    }

    public static String getLess200() {
        return errorMessagePreferences.getString(LESS_THAN_200, null);
    }

    public static String getLess100() {
        return errorMessagePreferences.getString(LESS_THAN_100, null);
    }

    public static String getLess50() {
        return errorMessagePreferences.getString(LESS_THAN_50, null);
    }

    public static String getPostalcode() {
        return errorMessagePreferences.getString(POSTAL_CODE__WELL_FORMED, null);
    }

    public static String getBigDecimal() {
        return errorMessagePreferences.getString(BIGDECIMAL_WELL_FORMED, null);
    }

    public static String getNumber() {
        return errorMessagePreferences.getString(NUMBER_WELL_FORMED, null);
    }

    public static String getEmail() {
        return errorMessagePreferences.getString(EMAIL_WELL_FORMED, null);
    }

    public static String getPhoneNumber() {
        return errorMessagePreferences.getString(PHONE_NUMBER_WELL_FORMED, null);
    }
}

