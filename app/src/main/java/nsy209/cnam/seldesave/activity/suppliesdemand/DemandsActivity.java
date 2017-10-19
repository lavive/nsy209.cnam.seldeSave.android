package nsy209.cnam.seldesave.activity.suppliesdemand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.home.HomeActivity;
import nsy209.cnam.seldesave.adapter.DemandsAdapter;
import nsy209.cnam.seldesave.bean.SupplyDemandBean;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.models.DemandsModel;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;

public class DemandsActivity extends AppCompatActivity implements View.OnClickListener,Observer,AdapterView.OnItemClickListener {

    /* daoFactory */
    private DaoFactory daoFactory;

    /* controllers */
    private Button toSupplies;

    /* view */
    private ListView demandsListView;

    /* model */
    private DemandsModel demandsModel;

    /* adapter */
    private DemandsAdapter demandsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demands_display);

        /* database connexion */
        daoFactory = DaoFactory.getInstance(this);
        daoFactory.open();

        /* view */
        demandsListView = (ListView) findViewById(R.id.demandsList);

        /* controller */
        toSupplies = (Button) findViewById(R.id.gotoSupply);
        toSupplies.setOnClickListener(this);
        demandsListView.setOnItemClickListener(this);

        /* model */
        demandsModel = new DemandsModel();
        demandsModel.addObserver(this);
    }

    @Override
    public void onClick(View view) {
        if(view == toSupplies){
            List<SupplyDemandBean> supplies = daoFactory.getSupplyDemandDao().getAllSupplies();
            if(supplies.isEmpty()){
                Toast.makeText(this,getString(R.string.error_supply),Toast.LENGTH_LONG).show();
            }else {
                Intent intent = new Intent(this, SuppliesActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent,View view,int position, long id){
        long demand_id = ((DemandsModel.Title) parent.getItemAtPosition(position)).getCode();
        Intent intent = new Intent(this,SupplyDemandCardActivity.class);
        intent.putExtra(ActivityConstant.KEY_INTENT_ID,demand_id);
        startActivity(intent);

    }

    @Override
    public  void update(Observable observable, Object object){
        /* update view with adapter */
        demandsAdapter = new DemandsAdapter(DemandsActivity.this,((DemandsModel) observable).getDemandsSorted(),daoFactory);

        demandsListView.setAdapter(demandsAdapter);
    }

    @Override
    protected void onResume() {
        daoFactory.open();
        super.onResume();

        demandsModel.onCreate(daoFactory);

        HomeActivity.isInForeGround = false;
    }

    @Override
    protected void onPause() {
        daoFactory.close();
        super.onPause();
        HomeActivity.isInForeGround = true;
    }


}
