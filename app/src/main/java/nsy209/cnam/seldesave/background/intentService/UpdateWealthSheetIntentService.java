package nsy209.cnam.seldesave.background.intentService;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import java.util.ArrayList;
import java.util.List;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;
import nsy209.cnam.seldesave.background.webService.RetrofitBuilder;
import nsy209.cnam.seldesave.background.webService.WebService;
import nsy209.cnam.seldesave.bean.WealthSheetBean;
import nsy209.cnam.seldesave.background.helper.BackgroundConstant;
import nsy209.cnam.seldesave.shared.dto.TransactionDto;
import nsy209.cnam.seldesave.shared.dto.TransactionsDto;
import nsy209.cnam.seldesave.shared.dto.WealthSheetDto;
import nsy209.cnam.seldesave.shared.transform.DtoToBean;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by lavive on 10/07/2017.
 */

public class UpdateWealthSheetIntentService extends IntentService {

    public UpdateWealthSheetIntentService() {
        super(UpdateWealthSheetIntentService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        /* get receiver */
        final ResultReceiver resultReceiver = intent.getParcelableExtra(BackgroundConstant.RECEIVER_UPDATE_WEALTHSHEET);

        /* get my Id */
        final long myRemoteId = intent.getLongExtra(BackgroundConstant.MY_PROFILE_BEAN, ActivityConstant.NOTEXIST);

        /* get retrofit service*/
        WebService webService = RetrofitBuilder.getClient();

        /* call service method */
        Call<WealthSheetDto> getUpdateWealthSheetCall = webService.getWealthSheet(myRemoteId);
        Call<TransactionsDto> getUpdateTransactionsCall = webService.getTransactions(myRemoteId);

        /* get informations from response */
        WealthSheetDto wealthSheetDto = new WealthSheetDto();
        TransactionsDto transactionsDto = new TransactionsDto();
        try {
            Response<WealthSheetDto> getUpdateWealthSheetResponse = getUpdateWealthSheetCall.execute();
            wealthSheetDto = getUpdateWealthSheetResponse.body();
            Response<TransactionsDto> getUpdateTransactionsResponse = getUpdateTransactionsCall.execute();
            transactionsDto = getUpdateTransactionsResponse.body();
        } catch (Exception e){
            e.printStackTrace();
        }

        /* send information to receiver */
        if(wealthSheetDto.getId() > 0 && !transactionsDto.getTransactions().isEmpty()){
            deliverResultToReceiver(resultReceiver, BackgroundConstant.SUCCESS_RESULT, getResources().getString(R.string.wealthSheet_update),
                    DtoToBean.wealthSheetDtoToBean(wealthSheetDto),transactionsDto.getTransactions());
        }

    }

    private void deliverResultToReceiver(ResultReceiver resultReceiver, int resultCode, String message, WealthSheetBean wealthSheetBean, List<TransactionDto> transactionsDto) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BackgroundConstant.RESULT_WEALTHSHEET, wealthSheetBean);
        bundle.putParcelableArrayList(BackgroundConstant.RESULT_TRANSACTION, (ArrayList<TransactionDto>) transactionsDto);
        bundle.putString(BackgroundConstant.RECEIVER_UPDATE_WEALTHSHEET, message);
        resultReceiver.send(resultCode, bundle);
    }

}

