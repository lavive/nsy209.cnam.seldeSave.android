package nsy209.cnam.seldesave.background.helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.ResultReceiver;

import nsy209.cnam.seldesave.background.intentService.UpdateCategoryIntentService;
import nsy209.cnam.seldesave.background.receiver.UpdateCategoryResultReceiver;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 11/07/2017.
 */

public class CategoryToUpdate implements IBaseToUpdate {

    /* daoFactory */
    private DaoFactory daoFactory;

    /* activity launching intent service */
    private Activity activity;

    public CategoryToUpdate(Activity activity,DaoFactory daoFactory) {
        this.activity = activity;
        this.daoFactory = daoFactory;
    }

    @Override
    public void update(){
        Intent intent = new Intent(this.activity, UpdateCategoryIntentService.class);
        ResultReceiver mResultReceiver = new UpdateCategoryResultReceiver(new Handler(), this.activity,daoFactory);
        intent.putExtra(BackgroundConstant.RECEIVER_UPDATE_CATEGORY,mResultReceiver);

        this.activity.startService(intent);
    }
}
