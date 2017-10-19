package nsy209.cnam.seldesave.background.receiver;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.widget.Toast;

import nsy209.cnam.seldesave.background.helper.BackgroundConstant;

/**
 * Created by lavive on 11/07/2017.
 */

public class RemoteDataBaseResultReceiver extends ResultReceiver {

    /* context */
    private Context context;

    public RemoteDataBaseResultReceiver(Handler handler, Context context) {
        super(handler);
        this.context = context;
    }

    @Override
    protected void onReceiveResult(int resultCode, final Bundle resultData) {

        /* display message toast  */
        Toast.makeText(this.context,resultData.getString(BackgroundConstant.RESULT_REMOTE),Toast.LENGTH_LONG).show();
    }
}