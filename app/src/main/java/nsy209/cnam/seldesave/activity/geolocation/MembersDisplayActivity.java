package nsy209.cnam.seldesave.activity.geolocation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.Observable;
import java.util.Observer;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.home.HomeActivity;
import nsy209.cnam.seldesave.adapter.MembersAdapter;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.models.MembersModel;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;

public class MembersDisplayActivity extends AppCompatActivity implements Observer,View.OnClickListener{

    /* daoFactory */
    private DaoFactory daoFactory;

    /* controller */
    private ImageButton geolocation;
    private Button filters;

    /* model */
    private MembersModel membersModel;

    /* check if apply filter */
    private boolean applyFilter;

    /* view */
    private ListView membersListView;

    /* adapter */
    private MembersAdapter membersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_display);

        /* database connexion */
        daoFactory = DaoFactory.getInstance(this);
        daoFactory.open();

        /* view */
        membersListView = (ListView) findViewById(R.id.membersList);
        geolocation = (ImageButton) findViewById(R.id.geolocation);
        filters = (Button) findViewById(R.id.applyFilterButton);

        /* controller */
        geolocation.setOnClickListener(this);
        filters.setOnClickListener(this);

        /* applyFilter */
        applyFilter = getIntent().getBooleanExtra(ActivityConstant.KEY_INTENT_FILTER,false);

        /* model */
        membersModel = new MembersModel();
        membersModel.addObserver(this);

    }

    @Override
    public void onClick(View view){
        if(view == geolocation){
            /* go to map */
            Intent intent = new Intent(this, GeolocationMembersActivity.class);
            intent.putExtra(ActivityConstant.KEY_INTENT_FILTER,applyFilter);
            startActivity(intent);
            finish();
        } else if(view == filters){
            /* apply or disable filter */
            applyFilter = !applyFilter;
            membersModel.onCreate(this,daoFactory,applyFilter);
        }
    }

    @Override
    public void update(Observable observable,Object object){
        /* update view with adapter */
        membersAdapter = new MembersAdapter(MembersDisplayActivity.this, ((MembersModel) observable).getMembers(),daoFactory);
        membersListView.setAdapter(membersAdapter);
        /* update text button */
        filters.setText(((MembersModel) observable).getApplyFilter());
    }

    @Override
    protected void onResume() {
        daoFactory.open();
        super.onResume();
        membersModel.onCreate(this, daoFactory, applyFilter);
        HomeActivity.isInForeGround = false;
    }

    @Override
    protected void onPause() {
        daoFactory.close();
        super.onPause();
        HomeActivity.isInForeGround = true;
    }
}
