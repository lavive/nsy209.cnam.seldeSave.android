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
import nsy209.cnam.seldesave.adapter.SuppliesDemandsPaymentAdapter;
import nsy209.cnam.seldesave.bean.SupplyDemandBean;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.models.SuppliesDemandsModel;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;

public class PaymentSupplyDemandActivity extends AppCompatActivity implements Observer,AdapterView.OnItemClickListener{

    /* to control history */
    public static Activity firstPaymentSupplyDemandActivity;

    /* daoFactory */
    private DaoFactory daoFactory;

    /* model */
    private SuppliesDemandsModel suppliesDemandsModel;

    /* view controller */
    private ListView suppliesDemandsListView;

    /* adapter */
    private SuppliesDemandsPaymentAdapter suppliesDemandsPaymentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_supply_demand);
        firstPaymentSupplyDemandActivity = this;

        /* database connexion */
        daoFactory = DaoFactory.getInstance(this);
        daoFactory.open();

        /* view */
        suppliesDemandsListView = (ListView) findViewById(R.id.suppliesDemandsList);

        /* controller */
        suppliesDemandsListView.setOnItemClickListener(this);

        /* model */
        suppliesDemandsModel = new SuppliesDemandsModel();
        suppliesDemandsModel.addObserver(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        long supplyDemand_id = ((SupplyDemandBean) parent.getItemAtPosition(position)).getId();
        Intent intent = new Intent(this,PaymentMemberActivity.class);
        intent.putExtra(ActivityConstant.KEY_INTENT_SUPPLYDEMAND_ID,supplyDemand_id);
        intent.putExtra(ActivityConstant.KEY_INTENT_NFC,getIntent().getBooleanExtra(ActivityConstant.KEY_INTENT_NFC,false));
        startActivity(intent);

    }

    @Override
    public void update(Observable observable, Object object){
        /* update view with adapter */
        suppliesDemandsPaymentAdapter = new SuppliesDemandsPaymentAdapter(this, ((SuppliesDemandsModel) observable).getSuppliesDemandsSorted());
        suppliesDemandsListView.setAdapter(suppliesDemandsPaymentAdapter);
    }

    @Override
    protected void onResume() {
        daoFactory.open();
        super.onResume();

        suppliesDemandsModel.onCreate(daoFactory);
        HomeActivity.isInForeGround = false;
    }

    @Override
    protected void onPause() {
        daoFactory.close();
        super.onPause();
        HomeActivity.isInForeGround = true;
    }
}
