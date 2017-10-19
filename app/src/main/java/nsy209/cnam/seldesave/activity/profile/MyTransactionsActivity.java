package nsy209.cnam.seldesave.activity.profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.home.HomeActivity;
import nsy209.cnam.seldesave.adapter.WealthSheetAdapter;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.models.MyTransactionModel;

public class MyTransactionsActivity extends AppCompatActivity implements Observer {

    /* daoFactory */
    private DaoFactory daoFactory;

    /* view */
    private ListView myTransactionsListView;
    private TextView myEmptyTransactions;

    /* model */
    private MyTransactionModel myTransactionModel;

    /* adapter */
    private WealthSheetAdapter wealthSheetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_transactions);

        /* database connexion */
        daoFactory = DaoFactory.getInstance(this);
        daoFactory.open();

        /* view */
        myTransactionsListView = (ListView) findViewById(R.id.myTransactionsList);
        myEmptyTransactions = (TextView) findViewById(R.id.myEmptyTransactions);

        /* model */
        myTransactionModel = new MyTransactionModel();
        myTransactionModel.addObserver(this);
    }

    @Override
    public  void update(Observable observable, Object object){
        /* update view with adapter */
        wealthSheetAdapter = new WealthSheetAdapter(MyTransactionsActivity.this,
                ((MyTransactionModel) observable).getTransactions(),daoFactory.getMyProfileDao().getMyProfile().getRemote_id());

        myTransactionsListView.setAdapter(wealthSheetAdapter);
        /* update empty view */
        if(((MyTransactionModel) observable).isEmpty()){
            myEmptyTransactions.setVisibility(View.VISIBLE);
        } else {
            myEmptyTransactions.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        daoFactory.open();
        super.onResume();

        myTransactionModel.onCreate(daoFactory);
        HomeActivity.isInForeGround = false;
    }

    @Override
    protected void onPause() {
        daoFactory.close();
        super.onPause();
        HomeActivity.isInForeGround = true;
    }
}
