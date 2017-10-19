package nsy209.cnam.seldesave.background.receiver;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import java.util.List;

import android.os.ResultReceiver;
import android.widget.Toast;
import nsy209.cnam.seldesave.bean.CategoryBean;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.background.helper.BackgroundConstant;

/**
 * Created by lavive on 11/07/2017.
 */

public class UpdateCategoryResultReceiver extends ResultReceiver {

    /* daoFactory */
    private DaoFactory daoFactory;

    private Activity activity;

    public UpdateCategoryResultReceiver(Handler handler, Activity activity, DaoFactory daoFactory) {
        super(handler);
        this.activity = activity;
        this.daoFactory = daoFactory;
    }

    @Override
    protected void onReceiveResult(int resultCode, final Bundle resultData) {

        if (resultCode == BackgroundConstant.SUCCESS_RESULT) {
            List<CategoryBean> categories = resultData.getParcelableArrayList(BackgroundConstant.RESULT_CATEGORY);
            daoFactory.getCategoryDao().createCategories(categories);
            /* display message toast  */
            Toast.makeText(this.activity,resultData.getString(BackgroundConstant.RECEIVER_UPDATE_CATEGORY),Toast.LENGTH_LONG).show();
        }
    }
}