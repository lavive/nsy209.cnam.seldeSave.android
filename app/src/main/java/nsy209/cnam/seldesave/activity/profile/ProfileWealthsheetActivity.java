package nsy209.cnam.seldesave.activity.profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.home.HomeActivity;
import nsy209.cnam.seldesave.adapter.WealthSheetAdapter;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.models.WealthSheetModel;

public class ProfileWealthsheetActivity extends AppCompatActivity implements Observer,View.OnClickListener{

    /* daoFactory */
    private DaoFactory daoFactory;

    /* controllers */
    private Button toMyTransaction;

    /* view */
    private ListView wealthSheetList;
    private TextView startAmount;
    private TextView finalAmount;
    private TextView emptyTransaction;

    /* model */
    private WealthSheetModel wealthSheetModel;

    /* adapter */
    private  WealthSheetAdapter wealthSheetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_wealthsheet);

        /* database connexion */
        daoFactory = DaoFactory.getInstance(this);
        daoFactory.open();

        /* view */
        wealthSheetList = (ListView) findViewById(R.id.transactionsList);
        startAmount = (TextView) findViewById(R.id.startAmount);
        finalAmount = (TextView) findViewById(R.id.finalAmount);
        emptyTransaction = (TextView) findViewById(R.id.emptyTransaction);

        /* controller */
        toMyTransaction = (Button) findViewById(R.id.gotoMyTransactions);
        toMyTransaction.setOnClickListener(this);

        /* model */
        wealthSheetModel = new WealthSheetModel();
        wealthSheetModel.addObserver(this);
    }

    @Override
    public void onClick(View view) {
        if(view == toMyTransaction){
                /* go to my transaction list */
                Intent intent = new Intent(this, MyTransactionsActivity.class);
                startActivity(intent);
        }
    }

    @Override
    public void update(Observable observable,Object object){
        /* update view with adapter */
        startAmount.setText(wealthSheetModel.getInitialAccount().toString());
        finalAmount.setText(wealthSheetModel.getFinalAccount().toString());
        if(wealthSheetModel.getTransactions().isEmpty()){
            emptyTransaction.setText(getString(R.string.empty_transaction));
        } else {
            emptyTransaction.setText("");
        }
        wealthSheetAdapter = new WealthSheetAdapter(ProfileWealthsheetActivity.this,wealthSheetModel.getTransactions(),
                daoFactory.getMyProfileDao().getMyProfile().getRemote_id());
        wealthSheetList.setAdapter(wealthSheetAdapter);
    }

    @Override
    protected void onResume() {
        daoFactory.open();
        super.onResume();

        wealthSheetModel.onCreate(daoFactory);

        HomeActivity.isInForeGround = false;
    }

    @Override
    protected void onPause() {
        daoFactory.close();
        super.onPause();
        HomeActivity.isInForeGround = true;
    }
}
