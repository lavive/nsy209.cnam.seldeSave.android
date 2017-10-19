package nsy209.cnam.seldesave.dao.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.math.BigDecimal;

import nsy209.cnam.seldesave.activity.utils.ActivityConstant;

/**
 * Created by lavive on 02/06/17.
 */

public class WealthSheetPreferences {
    private static SharedPreferences wealthSheetPreferences;

    private static final String WEALTHSHEET_NAME="wealthSheet";
    private static final String ID="id";
    private static final String REMOTE_ID="remote_id";
    private static final String INITIAL="initial_account";
    private static final String FINAL="final_account";

    public static void loadWealthSheet(Context context){
        wealthSheetPreferences = context.getSharedPreferences(WEALTHSHEET_NAME,Context.MODE_PRIVATE);
    }

    /* methods */
    public static void putId(long id){
        wealthSheetPreferences.edit().putLong(ID,id).apply();
    }
    public static void putRemoteId(long remoteId){
        wealthSheetPreferences.edit().putLong(REMOTE_ID,remoteId).apply();
    }
    public static void putInitial(BigDecimal initialAccount){
        wealthSheetPreferences.edit().putFloat(INITIAL,initialAccount.floatValue()).apply();
    }
    public static void putFinal(BigDecimal finalAccount){
        wealthSheetPreferences.edit().putFloat (FINAL,finalAccount.floatValue()).apply();
    }

    public static void clear(){
        putId(ActivityConstant.NOTEXIST);
        putRemoteId(ActivityConstant.NOTEXIST);
        putFinal(new BigDecimal(0.0));
        putInitial(new BigDecimal(0.0));
    }

    /* getter */
    public static long getId(){
        return wealthSheetPreferences.getLong(ID, ActivityConstant.NOTEXIST);
    }
    public static long getRemoteId(){
        return wealthSheetPreferences.getLong(REMOTE_ID, ActivityConstant.NOTEXIST);
    }
    public static BigDecimal getInitial(){
        return BigDecimal.valueOf(wealthSheetPreferences.getFloat (INITIAL,0));
    }
    public static BigDecimal getFinal(){
        return BigDecimal.valueOf(wealthSheetPreferences.getFloat(FINAL,0));
    }
}
