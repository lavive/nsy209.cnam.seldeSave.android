package nsy209.cnam.seldesave.background.helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.ResultReceiver;

import nsy209.cnam.seldesave.background.intentService.RemoteAddTransactionIntentService;
import nsy209.cnam.seldesave.background.receiver.RemoteAddTransactionReceiver;
import nsy209.cnam.seldesave.bean.TransactionBean;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 07/10/17.
 */

public class TransactionToRemoteAdd implements IBaseToUpdate {

    /* daoFactory */
    private DaoFactory daoFactory;

    /* activity launching intent service */
    private Activity activity;

    /* data to send */
    private TransactionBean transactionBean;

    public TransactionToRemoteAdd(Activity activity,DaoFactory daoFactory,TransactionBean transactionBean) {
        this.activity = activity;
        this.daoFactory = daoFactory;
        this.transactionBean = transactionBean;
    }

    @Override
    public void update() {

        Intent intent = new Intent(this.activity, RemoteAddTransactionIntentService.class);

        /* put receiver */
        final ResultReceiver mResultReceiver = new RemoteAddTransactionReceiver(new Handler(), this.activity, this.daoFactory);
        intent.putExtra(BackgroundConstant.RECEIVER_REMOTE_TRANSACTION, mResultReceiver);

        /* put my supply demand to send */
        intent.putExtra(BackgroundConstant.TRANSACTION_BEAN,this.transactionBean);

        this.activity.startService(intent);
    }
}
