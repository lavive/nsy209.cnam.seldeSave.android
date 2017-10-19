package nsy209.cnam.seldesave.background.intentService;

import android.content.Intent;


import android.app.IntentService;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;
import nsy209.cnam.seldesave.background.helper.BackgroundConstant;
import nsy209.cnam.seldesave.background.webService.RetrofitBuilder;
import nsy209.cnam.seldesave.background.webService.WebService;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by lavive on 09/10/17.
 */

public class RemoteUpdateDateIntentService extends IntentService {


    public RemoteUpdateDateIntentService() {
        super(RemoteUpdateDateIntentService.class.getName());
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        /* get my Id */
        final long myRemoteId = intent.getLongExtra(BackgroundConstant.MY_PROFILE_BEAN, ActivityConstant.NOTEXIST);

        /* get retrofit service*/
        final WebService webService = RetrofitBuilder.getClient();

        /* call service method */
        Call getDateLastUpdateCall = webService.updateDateLastUpdate(myRemoteId);

        /* get informations from response */
        try {
            Response getDateLastUpdateResponse = getDateLastUpdateCall.execute();

        } catch(Exception e){
            e.printStackTrace();
        }

    }


}
