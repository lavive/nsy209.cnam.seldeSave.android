package nsy209.cnam.seldesave.activity.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;
import nsy209.cnam.seldesave.background.helper.GeolocationToUpdate;
import nsy209.cnam.seldesave.background.webService.RetrofitBuilder;
import nsy209.cnam.seldesave.background.webService.WebService;
import nsy209.cnam.seldesave.bean.AssociationBean;
import nsy209.cnam.seldesave.bean.CategoryBean;
import nsy209.cnam.seldesave.bean.MemberBean;
import nsy209.cnam.seldesave.bean.MyProfileBean;
import nsy209.cnam.seldesave.bean.NotificationBean;
import nsy209.cnam.seldesave.bean.SupplyDemandBean;
import nsy209.cnam.seldesave.bean.TransactionBean;
import nsy209.cnam.seldesave.bean.WealthSheetBean;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.models.AuthenticationModel;
import nsy209.cnam.seldesave.shared.dto.AssociationDto;
import nsy209.cnam.seldesave.shared.dto.CategoriesDto;
import nsy209.cnam.seldesave.shared.dto.MemberDto;
import nsy209.cnam.seldesave.shared.dto.MembersDto;
import nsy209.cnam.seldesave.shared.dto.NotificationsDto;
import nsy209.cnam.seldesave.shared.dto.SuppliesDemandsDto;
import nsy209.cnam.seldesave.shared.dto.TransactionsDto;
import nsy209.cnam.seldesave.shared.dto.WealthSheetDto;
import nsy209.cnam.seldesave.shared.transform.DtoToBean;
import retrofit2.Call;
import retrofit2.Response;


public class AuthenticationActivity extends AppCompatActivity implements View.OnClickListener,Observer {

    /* daoFactory */
    private DaoFactory daoFactory;

    /* controller */
    private Button validate;

    /* model */
    private AuthenticationModel authenticationModel;

    /* view */
    private EditText mobileIdEdit;
    private EditText cellNumberEdit;
    private TextView mobileIdError;
    private TextView cellNumberError;
    private ProgressBar waiting;
    private TextView messageInfo;

    /* data */
    private Long myRemoteId = ActivityConstant.NOTEXIST;

    /* REST service */
    private WebService webService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        /* database connexion */
        daoFactory = DaoFactory.getInstance(this);
        daoFactory.open();

        /* view */
        mobileIdEdit = (EditText) findViewById(R.id.mobileIdEditText);
        mobileIdEdit.setOnClickListener(this);
        cellNumberEdit = (EditText) findViewById(R.id.cellNumberEditText);
        cellNumberEdit.setOnClickListener(this);

        /* controller */
        validate = (Button) findViewById(R.id.mobileIdValidate);
        validate.setOnClickListener(this);

        /* REST Services */
        webService = RetrofitBuilder.getClient();

        /* model */
        authenticationModel = new AuthenticationModel();
        authenticationModel.addObserver(this);
        authenticationModel.onCreate(getApplicationContext());
    }

    @Override
    public void onClick(View view) {
        if(view == validate){
            /* check error */
            authenticationModel.onChange(mobileIdEdit.getText().toString(),cellNumberEdit.getText().toString());

            /* no error */
            if(mobileIdError.getText().equals("") && cellNumberError.getText().equals("")) {
                /* check if profile exists */
                authenticationModel.onLookingForProfile(getApplicationContext());
                checkProfile(webService,mobileIdEdit.getText().toString(),cellNumberEdit.getText().toString());
            }
        } else if(view == mobileIdEdit || view == cellNumberEdit){
            /* edit code and cell number */
            authenticationModel.onCreate(getApplicationContext());
        }
    }

    @Override
    public void update(Observable observable, Object object) {
        /* update views */

        mobileIdError = (TextView) findViewById(R.id.mobileIdTextViewError);
        cellNumberError = (TextView) findViewById(R.id.cellNumberTextViewError);

        if(((AuthenticationModel) observable).getMobileIdError() != null) {
            mobileIdError.setText(DaoFactory.getInstance(getApplicationContext()).getErrorMessageDao()
                    .getErrorMessage( ((AuthenticationModel) observable).getMobileIdError() ) );
        } else {
            mobileIdError.setText("");
        }

        if(((AuthenticationModel) observable).getCellNumberError() != null) {
            cellNumberError.setText(DaoFactory.getInstance(getApplicationContext()).getErrorMessageDao()
                    .getErrorMessage( ((AuthenticationModel) observable).getCellNumberError() ) );
        } else {
            cellNumberError.setText("");
        }

        /* show while rest services are running */
        waiting = (ProgressBar) findViewById(R.id.waiting);
        if(((AuthenticationModel) observable).isWaiting()){
            waiting.setVisibility(View.VISIBLE);
        } else {
            waiting.setVisibility(View.GONE);
        }

        messageInfo = (TextView) findViewById(R.id.infoTextView);
        messageInfo.setText(((AuthenticationModel) observable).getMessageInfo());
    }

    @Override
    protected void onResume() {
        daoFactory.open();
        super.onResume();
        HomeActivity.isInForeGround = false;
    }

    @Override
    protected void onPause() {
        daoFactory.close();
        super.onPause();
        HomeActivity.isInForeGround = true;
    }

    /* helper method */

    /* retrieve remote id from server by REST Service */
    private void checkProfile(final WebService webService,final String mobileId,final String cellNumber){

        new Thread(new Runnable() {

            @Override
            public void run() {
                Call<Long> call = webService.getMyId(mobileId,cellNumber);

                try {
                    Response<Long> response = call.execute();
                    final Long remoteId = response.body();

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            resultCheckProfile(remoteId);
                        }
                    });
                } catch (final IOException e) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            resultCheckProfile(ActivityConstant.NOTEXIST);
                        }
                    });
                }
            }
        }).start();

    }

    /* check if profile exists and perform */
    private void resultCheckProfile(Long remoteId){
        myRemoteId = remoteId;
        if(myRemoteId == null){
            myRemoteId = ActivityConstant.NOTEXIST;
        }
        if(myRemoteId.equals(ActivityConstant.NOTEXIST)){
            authenticationModel.onErrorLookingForProfile(getApplicationContext());
        } else {
            authenticationModel.onLookingForDatas(getApplicationContext());
            getAllRemoteData();
        }
    }


    /* retrieve datas from server by REST Service */
    private void getAllRemoteData(){
        final WebService webService = RetrofitBuilder.getClient();
        /* retrieve all data */
        new Thread(new Runnable() {

            @Override
            public void run() {

                daoFactory.open();
                /* call Rest Services */
                Call<AssociationDto> callAssociation = webService.getAssociation();
                Call<MemberDto> callMyProfile = webService.getMyProfile(myRemoteId);
                Call<SuppliesDemandsDto> callSuppliesDemands = webService.getAllSuppliesDemands();
                Call<MembersDto> callMembers = webService.getAllMembers();
                Call<CategoriesDto> callCategories = webService.getAllCategories();
                Call<WealthSheetDto> callWealthSheet = webService.getWealthSheet(myRemoteId);
                Call<TransactionsDto> calltransactions = webService.getTransactions(myRemoteId);
                Call<NotificationsDto> callNotifications = webService.getNotifications(myRemoteId);

                try {
                    /* execute response */
                    Response<AssociationDto> responseAssociation = callAssociation.execute();
                    Response<MemberDto> responseMyProfile = callMyProfile.execute();
                    Response<SuppliesDemandsDto> responseSuppliesDemands = callSuppliesDemands.execute();
                    Response<MembersDto> responseMembers = callMembers.execute();
                    Response<CategoriesDto> responseCategories = callCategories.execute();
                    Response<WealthSheetDto> responseWealthSheet = callWealthSheet.execute();
                    Response<TransactionsDto> responseTransactions = calltransactions.execute();
                    Response<NotificationsDto> responseNotifications = callNotifications.execute();

                    /* get remote datas */
                    AssociationBean associationBean = DtoToBean.associationDtoToBean(responseAssociation.body());
                    MyProfileBean myProfileBean = DtoToBean.myProfileDtoToBean(responseMyProfile.body());
                    List<SupplyDemandBean> supplyDemandBeanList = DtoToBean.supplyDemandDtoToBean(responseSuppliesDemands.body());
                    List<MemberBean> memberBeanList = DtoToBean.memberDtoToBean(responseMembers.body());
                    List<CategoryBean> categoryBeanList = DtoToBean.categoryDtoToBean(responseCategories.body());
                    WealthSheetBean wealthSheetBean = DtoToBean.wealthSheetDtoToBean(responseWealthSheet.body());
                    List<NotificationBean> notificationBeanList = DtoToBean.notificationDtoToBean(responseNotifications.body());

                    /* clear database */
                    daoFactory.clear();

                    /* update local database */
                    daoFactory.getAssociationDao().createAssociation(associationBean);
                    daoFactory.getMemberDao().createMembers(memberBeanList);
                    daoFactory.getSupplyDemandDao().createSuppliesDemands(supplyDemandBeanList);
                    daoFactory.getCategoryDao().createCategories(categoryBeanList);
                    List<TransactionBean> transactionBeanList = DtoToBean.transactionDtoToBean(responseTransactions.body(),daoFactory);
                    daoFactory.getTransactionDao().createCheckedTransaction(transactionBeanList);
                    myProfileBean.setId(daoFactory.getMemberDao().getMemberByRemoteId(myRemoteId).getId());
                    daoFactory.getMyProfileDao().createMyProfile(myProfileBean);
                    daoFactory.getMyProfileDao().createWealthSheet(wealthSheetBean);
                    daoFactory.getMyProfileDao().addNotifications(notificationBeanList);

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            resultOKGetAllRemoteData();
                        }
                    });

                } catch (IOException e) {
                    resultNOKGetAllRemoteData();
                }


            }
        }).start();

    }

    private void resultOKGetAllRemoteData(){
        /* launch geolocation service */
        GeolocationToUpdate geolocationToUpdate = new GeolocationToUpdate(AuthenticationActivity.this,daoFactory);
        geolocationToUpdate.update();
        /* start home activity */
        Intent intent = new Intent(this,HomeActivity.class);
        daoFactory.getMyProfileDao().setMyRemoteId(myRemoteId);
        startActivity(intent);
        finish();

    }

    private void resultNOKGetAllRemoteData(){
        authenticationModel.onErrorLookingForDatas(getApplicationContext());
    }


}
