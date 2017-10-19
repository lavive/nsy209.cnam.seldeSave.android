package nsy209.cnam.seldesave.background.receiver;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.widget.Toast;

import java.util.ArrayList;

import nsy209.cnam.seldesave.bean.WealthSheetBean;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.background.helper.BackgroundConstant;
import nsy209.cnam.seldesave.shared.dto.TransactionDto;
import nsy209.cnam.seldesave.shared.dto.TransactionsDto;
import nsy209.cnam.seldesave.shared.transform.DtoToBean;

/**
 * Created by lavive on 11/07/2017.
 */

public class UpdateWealthSheetResultReceiver extends ResultReceiver {

    /* daoFactory */
    private DaoFactory daoFactory;

    private Activity activity;

    public UpdateWealthSheetResultReceiver(Handler handler, Activity activity, DaoFactory daoFactory) {
        super(handler);
        this.activity = activity;
        this.daoFactory = daoFactory;
    }

    @Override
    protected void onReceiveResult(int resultCode, final Bundle resultData) {

        if (resultCode == BackgroundConstant.SUCCESS_RESULT) {
            /* data processing */
            WealthSheetBean wealthSheetBean = resultData.getParcelable(BackgroundConstant.RESULT_WEALTHSHEET);
            TransactionsDto transactionsDto= new TransactionsDto();
            ArrayList<TransactionDto> transactionDtos = new ArrayList<TransactionDto>();
            for(Parcelable parcelable:resultData.getParcelableArrayList(BackgroundConstant.RESULT_TRANSACTION)){
                transactionDtos.add((TransactionDto) parcelable);
            }
            transactionsDto.setTransactions(transactionDtos);
            /* update local database */
            daoFactory.getMyProfileDao().createWealthSheet(wealthSheetBean);
            daoFactory.getTransactionDao().createCheckedTransaction(DtoToBean.transactionDtoToBean(transactionsDto,daoFactory));
            /* display message toast  */
            Toast.makeText(this.activity,resultData.getString(BackgroundConstant.RECEIVER_UPDATE_WEALTHSHEET),Toast.LENGTH_LONG).show();
        }
    }
}