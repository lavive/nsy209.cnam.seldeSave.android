package nsy209.cnam.seldesave.background.intentService;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import java.util.ArrayList;
import java.util.List;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.background.webService.RetrofitBuilder;
import nsy209.cnam.seldesave.background.webService.WebService;
import nsy209.cnam.seldesave.bean.CategoryBean;
import nsy209.cnam.seldesave.background.helper.BackgroundConstant;
import nsy209.cnam.seldesave.shared.dto.CategoriesDto;
import nsy209.cnam.seldesave.shared.transform.DtoToBean;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by lavive on 10/07/2017.
 */

public class UpdateCategoryIntentService extends IntentService {

    public UpdateCategoryIntentService() {
        super(UpdateCategoryIntentService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        /* get receiver */
        final ResultReceiver resultReceiver = intent.getParcelableExtra(BackgroundConstant.RECEIVER_UPDATE_CATEGORY);

        /* get retrofit service*/
        WebService webService = RetrofitBuilder.getClient();

        /* call service method */
        Call<CategoriesDto> getUpdateCategoryListCall = webService.getAllCategories();

        /* get informations from response */
        CategoriesDto categories = new CategoriesDto();
        try {
            Response<CategoriesDto> getUpdateCategoryListResponse = getUpdateCategoryListCall.execute();
            categories = getUpdateCategoryListResponse.body();
        } catch (Exception e){
            e.printStackTrace();
        }

        /* send information to receiver */
        if(!categories.getCategories().isEmpty()){
            deliverResultToReceiver(resultReceiver, BackgroundConstant.SUCCESS_RESULT, getResources().getString(R.string.categories_update),DtoToBean.categoryDtoToBean(categories));
        }

    }

    private void deliverResultToReceiver(ResultReceiver resultReceiver, int resultCode, String message, List<CategoryBean> categories) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(BackgroundConstant.RESULT_CATEGORY,(ArrayList<CategoryBean>) categories);
        bundle.putString(BackgroundConstant.RECEIVER_UPDATE_CATEGORY, message);
        resultReceiver.send(resultCode, bundle);
    }

}
