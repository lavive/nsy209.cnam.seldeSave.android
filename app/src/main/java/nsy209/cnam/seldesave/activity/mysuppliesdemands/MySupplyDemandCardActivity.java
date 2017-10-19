package nsy209.cnam.seldesave.activity.mysuppliesdemands;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.home.HomeActivity;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.models.MySupplyDemandModel;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;

import static android.R.attr.id;

public class MySupplyDemandCardActivity extends AppCompatActivity implements View.OnClickListener,Observer{

    /* constant */
    public final static int REQUEST_EXIT = 0;

    /* DaoFactory */
    private DaoFactory daoFactory;

    /* controller */
    private ImageButton edit;

    /* model */
    private MySupplyDemandModel mySupplyDemandModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_supplydemand_card);

        /* database connexion */
        daoFactory = DaoFactory.getInstance(this);
        daoFactory.open();

        /* controler */
        edit = (ImageButton) findViewById(R.id.updateSupplyDemand);
        edit.setOnClickListener(this);

        /* model */
        long id = getIntent().getLongExtra(ActivityConstant.KEY_INTENT_ID,ActivityConstant.BAD_ID);
        if(id < 0){
            Toast.makeText(this,getString(R.string.error_data),Toast.LENGTH_LONG).show();
            finish();
        }
        mySupplyDemandModel = new MySupplyDemandModel();
        mySupplyDemandModel.addObserver(this);
    }

    @Override
    public void onClick(View view){
        if(view == edit){
            /* go to edit supply demand */
            Intent intent = new Intent(this, MySupplyDemandEditActivity.class);
            intent.putExtra(ActivityConstant.KEY_INTENT_ADD,false);
            intent.putExtra(ActivityConstant.KEY_INTENT_EDIT,true);
            intent.putExtra(ActivityConstant.KEY_INTENT_ID, mySupplyDemandModel.getId());
            intent.putExtra(ActivityConstant.KEY_INTENT_REMOTE_ID, mySupplyDemandModel.getRemoteId());
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
    public void update(Observable observable, Object object){
        /* update views */
        TextView type = (TextView) findViewById(R.id.typeValue);
        type.setText(mySupplyDemandModel.getType().getWording());

        TextView category = (TextView) findViewById(R.id.categoryValue);
        category.setText(mySupplyDemandModel.getCategory());

        TextView title = (TextView) findViewById(R.id.titleValue);
        title.setText(mySupplyDemandModel.getTitle());
    }

    @Override
    protected void onResume() {
        daoFactory.open();
        super.onResume();
        long remoteId = getIntent().getLongExtra(ActivityConstant.KEY_INTENT_REMOTE_ID,ActivityConstant.BAD_ID);
        mySupplyDemandModel.onCreate(daoFactory,id,remoteId);

        HomeActivity.isInForeGround = false;
    }

    @Override
    protected void onPause() {
        daoFactory.close();
        super.onPause();
        HomeActivity.isInForeGround = true;
    }
}
