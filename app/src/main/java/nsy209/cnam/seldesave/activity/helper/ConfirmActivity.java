package nsy209.cnam.seldesave.activity.helper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.math.BigDecimal;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.home.HomeActivity;
import nsy209.cnam.seldesave.activity.filter.FilterActivity;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;
import nsy209.cnam.seldesave.activity.mysuppliesdemands.MySupplyDemandActivity;
import nsy209.cnam.seldesave.activity.mysuppliesdemands.MySupplyDemandEditActivity;
import nsy209.cnam.seldesave.activity.notifications.MyNotificationsActivity;
import nsy209.cnam.seldesave.activity.payment.PaymentActivity;
import nsy209.cnam.seldesave.activity.payment.PaymentAmountActivity;
import nsy209.cnam.seldesave.activity.payment.PaymentMemberActivity;
import nsy209.cnam.seldesave.activity.payment.PaymentSupplyDemandActivity;
import nsy209.cnam.seldesave.activity.utils.Connected;
import nsy209.cnam.seldesave.activity.utils.TypeSupplyDemand;
import nsy209.cnam.seldesave.background.helper.MySupplyDemandToRemoteAdd;
import nsy209.cnam.seldesave.background.helper.MySupplyDemandToRemoteUpdate;
import nsy209.cnam.seldesave.background.helper.TransactionToRemoteAdd;
import nsy209.cnam.seldesave.bean.FilterBean;
import nsy209.cnam.seldesave.bean.MyProfileBean;
import nsy209.cnam.seldesave.bean.SupplyDemandBean;
import nsy209.cnam.seldesave.bean.TransactionBean;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.validator.helper.EnumField;


public class ConfirmActivity extends AppCompatActivity implements View.OnClickListener {

    /* daoFactory */
    private DaoFactory daoFactory;

    /* controllers */
    private Button confirm;
    private Button cancel;

    /* datas */
    boolean mySupplyDemand;
    boolean filter;
    boolean payment;
    boolean notifications;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        /* database connexion */
        daoFactory = DaoFactory.getInstance(this);
        daoFactory.open();

        /* controllers */
        confirm = (Button) findViewById(R.id.confirm);
        cancel = (Button) findViewById(R.id.cancel);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);

        /* datas */
        mySupplyDemand = getIntent().getBooleanExtra(ActivityConstant.KEY_INTENT_MY_SUPPLYDEMAND,false);
        notifications = getIntent().getBooleanExtra(ActivityConstant.KEY_INTENT_NOTIFICATIONS,false);
        filter = getIntent().getBooleanExtra(ActivityConstant.KEY_INTENT_FILTER,false);
        payment = getIntent().getBooleanExtra(ActivityConstant.KEY_INTENT_PAYMENT,false);
    }



    @Override
    public void onClick(View view) {
        daoFactory.open();
        if(view == confirm){
            if(mySupplyDemand){
                /* create a new supplyDemand */
                SupplyDemandBean supplyDemandBean = new SupplyDemandBean();
                supplyDemandBean.setType(TypeSupplyDemand.getByWording(this,getIntent().getStringExtra(EnumField.TYPE.getWording())));
                supplyDemandBean.setCategory(getIntent().getStringExtra(EnumField.CATEGORY.getWording()));
                supplyDemandBean.setTitle(getIntent().getStringExtra(EnumField.TITLE.getWording()));
                supplyDemandBean.setMember(MyProfileBean.myProfileToMember(daoFactory));
                supplyDemandBean.setActive(true); //supplyDemand is no deleted

                /* case edit */
                if(getIntent().getBooleanExtra(ActivityConstant.KEY_INTENT_EDIT,false)){
                    /* edit supply demand in local */
                    supplyDemandBean.setId(getIntent().getLongExtra(ActivityConstant.KEY_INTENT_ID,ActivityConstant.BAD_ID));
                    supplyDemandBean.setRemote_id(getIntent().getLongExtra(ActivityConstant.KEY_INTENT_REMOTE_ID,ActivityConstant.BAD_ID));
                    supplyDemandBean.setChecked(daoFactory.getMyProfileDao().getMySupplyDemand(supplyDemandBean.getId()).isChecked());
                    daoFactory.getMyProfileDao().updateSupplyDemand(supplyDemandBean);

                    /* edit supply demand to server */
                    if(Connected.isConnectedInternet(this)){
                        MySupplyDemandToRemoteUpdate mySupplyDemandToRemoteUpdate = new MySupplyDemandToRemoteUpdate(this,daoFactory,supplyDemandBean);
                        mySupplyDemandToRemoteUpdate.update();
                    }

                    Toast.makeText(this, getString(R.string.update_supplyDemand), Toast.LENGTH_LONG).show();
                    MySupplyDemandActivity.myFirstSupplyDemandActivity.finish();

                /* case add */
                }else if(getIntent().getBooleanExtra(ActivityConstant.KEY_INTENT_ADD,false)) {
                    /* add supply demand in local */
                    supplyDemandBean.setChecked(false); //supplyDemand not yet check in server
                    daoFactory.getMyProfileDao().addSupplyDemand(supplyDemandBean);

                    /* add supply demand to server */
                    if(Connected.isConnectedInternet(this)){
                        MySupplyDemandToRemoteAdd mySupplyDemandToRemoteAdd = new MySupplyDemandToRemoteAdd(this,daoFactory,supplyDemandBean);
                        mySupplyDemandToRemoteAdd.update();
                    }

                    Toast.makeText(this, getString(R.string.create_supplyDemand), Toast.LENGTH_LONG).show();
                }
                /* back to activity origin */
                setResult(RESULT_OK, null);
                startActivity(new Intent(this, MySupplyDemandActivity.class));
                finish();
            } else if(notifications){
                /* update notification */
                setResult(RESULT_OK, null);
                long[] ids = getIntent().getLongArrayExtra(ActivityConstant.KEY_INTENT_IDS);
                for(long id : ids){
                    daoFactory.getMyProfileDao().deleteNotification(id);
                }
                Toast.makeText(this,getString(R.string.delete_notification),Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, MyNotificationsActivity.class));
                finish();
            } else if(filter){
                /* update filters */
                setResult(RESULT_OK, null);
                FilterBean filterBean = new FilterBean();
                filterBean.setMembersChecked(getIntent().getBooleanExtra(ActivityConstant.KEY_INTENT_MEMBER_CHECKED,false));
                filterBean.setCategoriesChecked(getIntent().getBooleanExtra(ActivityConstant.KEY_INTENT_CATEGORY_CHECKED,false));
                filterBean.setDistanceChecked(getIntent().getBooleanExtra(ActivityConstant.KEY_INTENT_DISTANCE_CHECKED,false));
                filterBean.setDistance(Long.parseLong(getIntent().getStringExtra(EnumField.DISTANCE.getWording())));
                daoFactory.getMyProfileDao().createFilter(filterBean);

                Toast.makeText(this,getString(R.string.create_filter),Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, FilterActivity.class));
                finish();
            } else if(payment){
                /* create a transaction */
                long memberId = getIntent().getLongExtra(ActivityConstant.KEY_INTENT_MEMBER_ID,ActivityConstant.BAD_ID);
                long supplyDemandId = getIntent().getLongExtra(ActivityConstant.KEY_INTENT_SUPPLYDEMAND_ID,ActivityConstant.BAD_ID);
                if(memberId <0 || supplyDemandId <0){
                    Toast.makeText(this,getString(R.string.error_data),Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    TransactionBean transactionBean = new TransactionBean();
                    transactionBean.setDebtor(MyProfileBean.myProfileToMember(daoFactory));
                    transactionBean.setCreditor(daoFactory.getMemberDao().getMember(memberId));
                    transactionBean.setSupplyDemand(daoFactory.getSupplyDemandDao().getSupplyDemand(supplyDemandId));
                    BigDecimal amount = new BigDecimal(getIntent().getStringExtra(EnumField.AMOUNT.getWording()));
                    transactionBean.setAmount(amount);
                    daoFactory.getTransactionDao().addNewTransaction(transactionBean);

                    if(Connected.isConnectedInternet(this)){
                        TransactionToRemoteAdd transactionToRemoteAdd = new TransactionToRemoteAdd(this,daoFactory,transactionBean);
                        transactionToRemoteAdd.update();
                    }

                    Toast.makeText(this,getString(R.string.create_transaction),Toast.LENGTH_LONG).show();

                    /* closing intermediate activity */
                    HomeActivity.firstHomeActivity.finish();
                    PaymentActivity.firstPaymentActivity.finish();
                    PaymentSupplyDemandActivity.firstPaymentSupplyDemandActivity.finish();
                    PaymentMemberActivity.firstPaymentMemberActivity.finish();

                    setResult(RESULT_OK, null);
                    startActivity(new Intent(this, HomeActivity.class));
                    finish();
                }
            } else {
                Toast.makeText(this,getString(R.string.error_data),Toast.LENGTH_LONG).show();
            }

        } else if(view == cancel){
            if(mySupplyDemand){
                /* cancel supply demand creation */
                setResult(RESULT_OK, null);
                Intent intent = new Intent(this, MySupplyDemandEditActivity.class);
                intent.putExtra(ActivityConstant.KEY_INTENT_CONFIRM,true);
                intent.putExtra(EnumField.TYPE.getWording(),getIntent().getStringExtra(EnumField.TYPE.getWording()));
                intent.putExtra(EnumField.CATEGORY.getWording(),getIntent().getStringExtra(EnumField.CATEGORY.getWording()));
                intent.putExtra(EnumField.TITLE.getWording(),getIntent().getStringExtra(EnumField.TITLE.getWording()));

                startActivity(intent);
                finish();
            } else if(notifications){
                /* cancel update notification */
                setResult(RESULT_OK, null);
                long[] ids = getIntent().getLongArrayExtra(ActivityConstant.KEY_INTENT_IDS);
                Intent intent = new Intent(this, MyNotificationsActivity.class);
                intent.putExtra(ActivityConstant.KEY_INTENT_IDS,ids);

                startActivity(intent);
                finish();
            } else if(filter){
                /* cancel update filter */
                setResult(RESULT_OK, null);
                Intent intent = new Intent(this, FilterActivity.class);
                intent.putExtra(ActivityConstant.KEY_INTENT_CONFIRM,true);
                intent.putExtra(ActivityConstant.KEY_INTENT_DISTANCE_CHECKED,getIntent().getBooleanExtra(ActivityConstant.KEY_INTENT_DISTANCE_CHECKED,false));
                intent.putExtra(ActivityConstant.KEY_INTENT_MEMBER_CHECKED,getIntent().getBooleanExtra(ActivityConstant.KEY_INTENT_MEMBER_CHECKED,false));
                intent.putExtra(ActivityConstant.KEY_INTENT_CATEGORY_CHECKED,getIntent().getBooleanExtra(ActivityConstant.KEY_INTENT_CATEGORY_CHECKED,false));
                intent.putExtra(EnumField.DISTANCE.getWording(),getIntent().getStringExtra(EnumField.DISTANCE.getWording()));

                startActivity(intent);
                finish();
            } else if(payment){
                /* cancel transaction creation */
                long member_id = getIntent().getLongExtra(ActivityConstant.KEY_INTENT_MEMBER_ID,ActivityConstant.BAD_ID);
                long supplyDemand_id = getIntent().getLongExtra(ActivityConstant.KEY_INTENT_SUPPLYDEMAND_ID,ActivityConstant.BAD_ID);
                if(member_id <0 || supplyDemand_id <0){
                    Toast.makeText(this,getString(R.string.error_data),Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    setResult(RESULT_OK, null);
                    Intent intent = new Intent(this, PaymentAmountActivity.class);
                    intent.putExtra(ActivityConstant.KEY_INTENT_CONFIRM,true);
                    intent.putExtra(ActivityConstant.KEY_INTENT_MEMBER_ID,member_id);
                    intent.putExtra(ActivityConstant.KEY_INTENT_SUPPLYDEMAND_ID,supplyDemand_id);
                    intent.putExtra(EnumField.AMOUNT.getWording(), getIntent().getStringExtra(EnumField.AMOUNT.getWording()));

                    startActivity(intent);
                    finish();
                }
            } else {
                Toast.makeText(this,getString(R.string.error_data),Toast.LENGTH_LONG).show();
            }
        }
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
}
