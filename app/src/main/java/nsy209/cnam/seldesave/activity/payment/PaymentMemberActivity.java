package nsy209.cnam.seldesave.activity.payment;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Observable;
import java.util.Observer;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.home.HomeActivity;
import nsy209.cnam.seldesave.adapter.MembersPaymentAdapter;
import nsy209.cnam.seldesave.bean.MemberBean;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.models.MembersPaymentModel;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;

public class PaymentMemberActivity extends AppCompatActivity implements Observer,AdapterView.OnItemClickListener{

    /* to control history */
    public static Activity firstPaymentMemberActivity;

    /* daoFactory */
    private DaoFactory daoFactory;

    /* model */
    private MembersPaymentModel membersPaymentModel;

    /* view controller */
    private ListView membersListView;

    /* adapter */
    private MembersPaymentAdapter membersPaymentAdapter;

    /* data */
    private long supplyDemandId;
    //private long supplyDemandRemoteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_member);
        firstPaymentMemberActivity = this;

        /* database connexion */
        daoFactory = DaoFactory.getInstance(this);
        daoFactory.open();


        /* view */
        membersListView = (ListView) findViewById(R.id.membersList);

        /* controller */
        membersListView.setOnItemClickListener(this);

        /* data */
        supplyDemandId = getIntent().getLongExtra(ActivityConstant.KEY_INTENT_SUPPLYDEMAND_ID,ActivityConstant.BAD_ID);
        //supplyDemandRemoteId = getIntent().getLongExtra(ActivityConstant.KEY_INTENT_SUPPLYDEMAND_REMOTEID,ActivityConstant.BAD_ID);

        /* model */
        membersPaymentModel = new MembersPaymentModel();
        membersPaymentModel.addObserver(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent,View view,int position, long id){
        long member_id = ((MemberBean) parent.getItemAtPosition(position)).getId();
        Intent intent = new Intent(this,PaymentAmountActivity.class);
        intent.putExtra(ActivityConstant.KEY_INTENT_MEMBER_ID,member_id);
        intent.putExtra(ActivityConstant.KEY_INTENT_SUPPLYDEMAND_ID,supplyDemandId);
        intent.putExtra(ActivityConstant.KEY_INTENT_NFC,getIntent().getBooleanExtra(ActivityConstant.KEY_INTENT_NFC,false));
        startActivity(intent);

    }

    @Override
    public void update(Observable observable, Object object){
        /* update view with adapter */
        membersPaymentAdapter = new MembersPaymentAdapter(this, ((MembersPaymentModel) observable).getMembers());
        membersListView.setAdapter(membersPaymentAdapter);
    }

    @Override
    protected void onResume() {
        daoFactory.open();
        super.onResume();

        membersPaymentModel.onCreate(daoFactory,supplyDemandId);
        HomeActivity.isInForeGround = false;
    }

    @Override
    protected void onPause() {
        daoFactory.close();
        super.onPause();
        HomeActivity.isInForeGround = true;
    }
}
