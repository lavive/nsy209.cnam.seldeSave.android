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
import nsy209.cnam.seldesave.adapter.SuppliesAdapter;
import nsy209.cnam.seldesave.bean.SupplyDemandBean;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.models.SuppliesModel;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;

public class SuppliesActivity extends AppCompatActivity implements View.OnClickListener,Observer,AdapterView.OnItemClickListener{

    /* daoFactory */
    private DaoFactory daoFactory;

    /* controllers */
    private Button toDemands;

    /* view */
    private ListView suppliesListView;

    /* model */
    private SuppliesModel suppliesModel;

    /* adapter */
    private SuppliesAdapter suppliesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplies_display);

        /* database connexion */
        daoFactory = DaoFactory.getInstance(this);
        daoFactory.open();

        /* view */
        suppliesListView = (ListView) findViewById(R.id.suppliesList);

        /* controller */
        toDemands = (Button) findViewById(R.id.gotoDemand);
        toDemands.setOnClickListener(this);
        suppliesListView.setOnItemClickListener(this);

        /* model */
        suppliesModel = new SuppliesModel();
        suppliesModel.addObserver(this);


    }

    @Override
    public void onClick(View view) {
        if(view == toDemands){
            daoFactory.open();
            List<SupplyDemandBean> demands = daoFactory.getSupplyDemandDao().getAllDemands();
            if(demands.isEmpty()){
                Toast.makeText(this,getString(R.string.error_demand),Toast.LENGTH_LONG).show();
            }else {
                Intent intent = new Intent(this, DemandsActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent,View view,int position, long id){
        long supply_id = ((SuppliesModel.Title) parent.getItemAtPosition(position)).getCode();
        Intent intent = new Intent(this,SupplyDemandCardActivity.class);
        intent.putExtra(ActivityConstant.KEY_INTENT_ID,supply_id);
        startActivity(intent);

    }

    @Override
    public  void update(Observable observable,Object object){
        /* update view with adapter */
        suppliesAdapter = new SuppliesAdapter(SuppliesActivity.this, suppliesModel.getSuppliesSorted(),daoFactory);
        suppliesListView.setAdapter(suppliesAdapter);
    }

    @Override
    protected void onResume() {
        daoFactory.open();
        super.onResume();

        suppliesModel.onCreate(daoFactory);

        HomeActivity.isInForeGround = false;
    }

    @Override
    protected void onPause() {
        daoFactory.close();
        super.onPause();
        HomeActivity.isInForeGround = true;
    }

}
