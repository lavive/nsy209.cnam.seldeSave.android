package nsy209.cnam.seldesave.dao.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import nsy209.cnam.seldesave.activity.utils.ActivityConstant;


/**
 * Created by lavive on 06/06/17.
 */

public class FilterPreferences {
    private static SharedPreferences filterPreferences;

    private static final String FILTER_NAME = "filter";

    private static final String DISTANCE = "distance";

    private static final String DISTANCE_CHECKED = "distance_checked";

    private static final String MEMBERS_CHECKED = "members_checked";

    private static final String CATEGORIES_CHECKED = "categories_checked";


    public static void loadFilter(Context context) {
        filterPreferences = context.getSharedPreferences(FILTER_NAME, Context.MODE_PRIVATE);
    }

    /* methods */
    public static void putDistance(long distance) {
        filterPreferences.edit().putLong(DISTANCE, distance).apply();
    }
    public static void putDistanceChecked(boolean distanceChecked) {
        filterPreferences.edit().putBoolean(DISTANCE_CHECKED, distanceChecked).apply();
    }
    public static void putCategoriesChecked(boolean categoriesChecked) {
        filterPreferences.edit().putBoolean(CATEGORIES_CHECKED, categoriesChecked).apply();
    }
    public static void putMembersChecked(boolean membersChecked) {
        filterPreferences.edit().putBoolean(MEMBERS_CHECKED, membersChecked).apply();
    }

    public static void clear(){
        putDistance(0l);
        putDistanceChecked(false);
        putCategoriesChecked(false);
        putMembersChecked(false);
    }

    /* getter */
    public static long getDistance() {
        return filterPreferences.getLong(DISTANCE, ActivityConstant.NOTEXIST);
    }
    public static boolean getDistanceChecked() {
        return filterPreferences.getBoolean(DISTANCE_CHECKED, false);
    }
    public static boolean getMembersChecked() {
        return filterPreferences.getBoolean(MEMBERS_CHECKED, false);
    }
    public static boolean getCategoriesChecked() {
        return filterPreferences.getBoolean(CATEGORIES_CHECKED, false);
    }
}
