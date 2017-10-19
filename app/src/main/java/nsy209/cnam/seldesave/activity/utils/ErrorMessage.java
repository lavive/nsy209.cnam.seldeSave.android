package nsy209.cnam.seldesave.activity.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import nsy209.cnam.seldesave.R;

/**
 * Created by lavive on 18/07/2017.
 */

public class ErrorMessage {

    /* load all error message fom string.xml */
    public static List<String> getErrorMessage(Context context){
        List<String> errorMessage = new ArrayList<String>();

        errorMessage.add(context.getString(R.string.empty));
        errorMessage.add(context.getString(R.string.less200));
        errorMessage.add(context.getString(R.string.less100));
        errorMessage.add(context.getString(R.string.less50));
        errorMessage.add(context.getString(R.string.bigDecimalWellFormed));
        errorMessage.add(context.getString(R.string.postalCodeWellFormed));
        errorMessage.add(context.getString(R.string.phoneNumberWellFormed));
        errorMessage.add(context.getString(R.string.emailWellFormed));
        errorMessage.add(context.getString(R.string.numberWellFormed));

        return errorMessage;
    }
}
