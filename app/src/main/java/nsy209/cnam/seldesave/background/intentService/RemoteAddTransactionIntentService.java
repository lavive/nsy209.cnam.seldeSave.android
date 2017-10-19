package nsy209.cnam.seldesave.background.intentService;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.background.webService.RetrofitBuilder;
import nsy209.cnam.seldesave.background.webService.WebService;
import nsy209.cnam.seldesave.bean.TransactionBean;
import nsy209.cnam.seldesave.background.helper.BackgroundConstant;
import nsy209.cnam.seldesave.shared.transform.BeanToDto;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by lavive on 11/07/2017.
 */

public class RemoteAddTransactionIntentService extends IntentService {

    public RemoteAddTransactionIntentService() {
        super(RemoteAddTransactionIntentService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        /* get receiver */
        final ResultReceiver resultReceiver = intent.getParcelableExtra(BackgroundConstant.RECEIVER_REMOTE_TRANSACTION);

        /* get transaction to send */
        final TransactionBean transactionBean = intent.getParcelableExtra(BackgroundConstant.TRANSACTION_BEAN);

        /* get retrofit service*/
        WebService webService = RetrofitBuilder.getClient();

        /* call service method */
        Call getAddTransactionCall = webService.addTransaction(BeanToDto.transactionBeanToDto(transactionBean));

        /* get informations from response */
        int responseCode = 0;
        try {
            Response getAddTransactionResponse = getAddTransactionCall.execute();
            responseCode = getAddTransactionResponse.code();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* send information to receiver */
        if (responseCode == BackgroundConstant.CODE_RESPONSE_CREATED) {

            deliverResultToReceiver(resultReceiver, BackgroundConstant.SUCCESS_RESULT, getResources().getString(R.string.datas_transferred));

        } else {

            deliverResultToReceiver(resultReceiver, BackgroundConstant.FAILURE_RESULT, getResources().getString(R.string.datas_lost));
        }

    }


    private void deliverResultToReceiver(ResultReceiver resultReceiver,int resultCode, String message) {
        Bundle bundle = new Bundle();
        bundle.putString(BackgroundConstant.RECEIVER_REMOTE_TRANSACTION, message);
        resultReceiver.send(resultCode, bundle);
    }

}

