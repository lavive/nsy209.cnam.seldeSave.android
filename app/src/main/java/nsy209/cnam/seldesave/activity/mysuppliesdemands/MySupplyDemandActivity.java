package nsy209.cnam.seldesave.activity.mysuppliesdemands;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.home.HomeActivity;
import nsy209.cnam.seldesave.adapter.MySupplyDemandAdapter;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.models.MySuppliesDemandsModel;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;

/**
 * Created by lavive on 01/05/17.
 */

public class MySupplyDemandActivity extends AppCompatActivity implements View.OnClickListener,Observer,AdapterView.OnItemClickListener{

    /* to control history */
    public static Activity myFirstSupplyDemandActivity;

    /* constant */
    public final static int REQUEST_EXIT = 0;

    /* DaoFactory */
    private DaoFactory daoFactory;

    /* controllers */
    private ImageButton add;

    /* view */
    private TextView error;

    /* view/controller */
    private ListView mySupplyDemandListView;

    /* model */
    private MySuppliesDemandsModel mySuppliesDemandsModel;

    /* adapter */
    MySupplyDemandAdapter mySupplyDemandAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_supplydemand_display);
        myFirstSupplyDemandActivity = this;

        /* database connexion */
        daoFactory = DaoFactory.getInstance(this);
        daoFactory.open();

        /* view */
        mySupplyDemandListView = (ListView) findViewById(R.id.suppliesList);
        error = (TextView) findViewById(R.id.mySupplyDemandError);

        /* controllers */
        add = (ImageButton) findViewById(R.id.addSupplyDemand) ;
        add.setOnClickListener(this);
        mySupplyDemandListView.setOnItemClickListener(this);

        /* model */
        mySuppliesDemandsModel = new MySuppliesDemandsModel();
        mySuppliesDemandsModel.addObserver(this);
    }

    @Override
    public void onClick(View view) {
        if(view == add){
            /* go to create a supply demand */
            Intent intent = new Intent(this,MySupplyDemandEditActivity.class);
            intent.putExtra(ActivityConstant.KEY_INTENT_ADD,true);
            intent.putExtra(ActivityConstant.KEY_INTENT_EDIT,false);
            startActivityForResult(intent, REQUEST_EXIT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /* finish this activity if result ok */
        if (requestCode == REQUEST_EXIT) {
            if (resultCode == RESULT_OK) {
                this.finish();

            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent,View view,int position, long id){
        long mySupplyDemand_id = ((MySuppliesDemandsModel.Title) parent.getItemAtPosition(position)).getCode();
        long mySupplyDemand_remoteId = ((MySuppliesDemandsModel.Title) parent.getItemAtPosition(position)).getRemoteId();
        Intent intent = new Intent(this,MySupplyDemandCardActivity.class);
        intent.putExtra(ActivityConstant.KEY_INTENT_ID,mySupplyDemand_id);
        intent.putExtra(ActivityConstant.KEY_INTENT_REMOTE_ID,mySupplyDemand_remoteId);
        startActivity(intent);

    }

    @Override
    public void update(Observable observable,Object object){
        /* update views with adapter */
        if(!mySuppliesDemandsModel.getMySuppliesDemands().isEmpty()) {
            error.setText("");
            mySupplyDemandAdapter = new MySupplyDemandAdapter(MySupplyDemandActivity.this,
                    ((MySuppliesDemandsModel) observable).getMySuppliesDemandsSorted(),daoFactory,getString(R.string.delete));

            mySupplyDemandListView.setAdapter(mySupplyDemandAdapter);
        } else {
            error = (TextView) findViewById(R.id.mySupplyDemandError);
            error.setText(getString(R.string.error_my_supply_demand));
        }
    }

    @Override
    protected void onResume() {
        daoFactory.open();
        super.onResume();
        mySuppliesDemandsModel.onCreate(daoFactory);
        HomeActivity.isInForeGround = false;
    }

    @Override
    protected void onPause() {
        daoFactory.close();
        super.onPause();
        HomeActivity.isInForeGround = true;
    }

}
