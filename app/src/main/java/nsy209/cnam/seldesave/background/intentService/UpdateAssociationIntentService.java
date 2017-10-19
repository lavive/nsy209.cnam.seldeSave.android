package nsy209.cnam.seldesave.background.intentService;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.background.webService.RetrofitBuilder;
import nsy209.cnam.seldesave.background.webService.WebService;
import nsy209.cnam.seldesave.bean.AssociationBean;
import nsy209.cnam.seldesave.background.helper.BackgroundConstant;
import nsy209.cnam.seldesave.shared.dto.AssociationDto;
import nsy209.cnam.seldesave.shared.transform.DtoToBean;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by lavive on 10/07/2017.
 */

public class UpdateAssociationIntentService extends IntentService {

    public UpdateAssociationIntentService() {
        super(UpdateAssociationIntentService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        /* get receiver */
        final ResultReceiver resultReceiver = intent.getParcelableExtra(BackgroundConstant.RECEIVER_UPDATE_ASSOCIATION);

        /* get retrofit service*/
        WebService webService = RetrofitBuilder.getClient();

        /* call service method */
        Call<AssociationDto> getUpdateAssociationCall = webService.getAssociation();

        /* get informations from response */
        AssociationDto associationDto = new AssociationDto();
        try {
            Response<AssociationDto> getUpdateAssociationResponse = getUpdateAssociationCall.execute();
            associationDto = getUpdateAssociationResponse.body();
        } catch (Exception e){
            e.printStackTrace();
        }

        /* send information to receiver */
        if(associationDto.getId() > 0){
            deliverResultToReceiver(resultReceiver,BackgroundConstant.SUCCESS_RESULT,getResources().getString(R.string.association_update),
                    DtoToBean.associationDtoToBean(associationDto));
        }

    }


    private void deliverResultToReceiver(ResultReceiver resultReceiver, int resultCode, String message, AssociationBean associationBean) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BackgroundConstant.RESULT_ASSOCIATION, associationBean);
        bundle.putString(BackgroundConstant.RECEIVER_UPDATE_ASSOCIATION, message);
        resultReceiver.send(resultCode, bundle);
    }

}

