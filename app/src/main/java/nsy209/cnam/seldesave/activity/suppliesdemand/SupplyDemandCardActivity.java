package nsy209.cnam.seldesave.activity.suppliesdemand;

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
import nsy209.cnam.seldesave.activity.geolocation.AMemberDisplayActivity;
import nsy209.cnam.seldesave.bean.MemberBean;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.models.SupplyDemandModel;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;

public class SupplyDemandCardActivity extends AppCompatActivity implements View.OnClickListener,Observer {

    /* DaoFactory */
    private DaoFactory daoFactory;

    /* controller */
    private ImageButton member;

    /* model */
    private SupplyDemandModel supplyDemandModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplydemand_card);

        /* database connexion */
        daoFactory = DaoFactory.getInstance(this);
        daoFactory.open();

        /* controler */
        member = (ImageButton)findViewById(R.id.member);
        member.setOnClickListener(this);

        /* model */
        supplyDemandModel = new SupplyDemandModel();
        supplyDemandModel.addObserver(this);
    }

    @Override
    public void onClick(View view){
        if(view == member){
            /* go to display selected member */
            MemberBean memberBean = supplyDemandModel.getMember();
            Intent intent = new Intent(this, AMemberDisplayActivity.class);
            intent.putExtra(ActivityConstant.KEY_INTENT_ID,memberBean.getId());
            startActivity(intent);
        }
    }

    @Override
    public void update(Observable observable, Object object){
        /* update views */
        TextView type = (TextView) findViewById(R.id.typeValue);
        type.setText(supplyDemandModel.getType().getWording());

        TextView category = (TextView) findViewById(R.id.categoryValue);
        category.setText(supplyDemandModel.getCategory());

        TextView title = (TextView) findViewById(R.id.titleValue);
        title.setText(supplyDemandModel.getTitle());
    }

    @Override
    protected void onResume() {
        daoFactory.open();
        super.onResume();


        long id = getIntent().getLongExtra(ActivityConstant.KEY_INTENT_ID,ActivityConstant.BAD_ID);
        if(id < 0){
            Toast.makeText(this,getString(R.string.error_data),Toast.LENGTH_LONG).show();
            finish();
        }
        supplyDemandModel.onCreate(daoFactory,id);
        HomeActivity.isInForeGround = false;
    }

    @Override
    protected void onPause() {
        daoFactory.close();
        super.onPause();
        HomeActivity.isInForeGround = true;
    }
}
