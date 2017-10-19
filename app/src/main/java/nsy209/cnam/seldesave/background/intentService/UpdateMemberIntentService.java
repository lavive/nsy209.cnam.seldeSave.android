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
import nsy209.cnam.seldesave.bean.MemberBean;
import nsy209.cnam.seldesave.background.helper.BackgroundConstant;
import nsy209.cnam.seldesave.shared.dto.MembersDto;
import nsy209.cnam.seldesave.shared.transform.DtoToBean;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by lavive on 10/07/2017.
 */

public class UpdateMemberIntentService extends IntentService {

    public UpdateMemberIntentService() {
        super(UpdateMemberIntentService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        /* get receiver */
        final ResultReceiver resultReceiver = intent.getParcelableExtra(BackgroundConstant.RECEIVER_UPDATE_MEMBER);

        /* get retrofit service*/
        WebService webService = RetrofitBuilder.getClient();

        /* call service method */
        Call<MembersDto> getUpdateMemberListCall = webService.getAllMembers();

        /* get informations from response */
        MembersDto members = new MembersDto();
        try {
            Response<MembersDto> getUpdateMemberListResponse = getUpdateMemberListCall.execute();
            members = getUpdateMemberListResponse.body();
        } catch (Exception e){
            e.printStackTrace();
        }

        /* send information to receiver */
        if(!members.getMembers().isEmpty()){
            deliverResultToReceiver(resultReceiver, BackgroundConstant.SUCCESS_RESULT, getResources().getString(R.string.members_update),
                    DtoToBean.memberDtoToBean(members));
        }

    }


    private void deliverResultToReceiver(ResultReceiver resultReceiver, int resultCode, String message,  List<MemberBean> members) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(BackgroundConstant.RESULT_MEMBER,(ArrayList<MemberBean>) members);
        bundle.putString(BackgroundConstant.RECEIVER_UPDATE_MEMBER, message);
        resultReceiver.send(resultCode, bundle);
    }

}