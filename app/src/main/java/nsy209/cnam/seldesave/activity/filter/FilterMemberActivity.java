package nsy209.cnam.seldesave.activity.filter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.Observable;
import java.util.Observer;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.home.HomeActivity;
import nsy209.cnam.seldesave.adapter.MembersFilterAdapter;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.models.MembersModel;

public class FilterMemberActivity extends AppCompatActivity implements View.OnClickListener,Observer {

    /* daoFactory */
    private DaoFactory daoFactory;

    /* controller */
    private ImageButton validate;

    /* model */
    private MembersModel membersModel;

    /* view */
    private ListView filterMemberListView;

    /* adapter */
    private MembersFilterAdapter membersFilterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_member);

        /* database connexion */
        daoFactory = DaoFactory.getInstance(this);
        daoFactory.open();


        /* controller */
        validate = (ImageButton) findViewById(R.id.validateMemberFilter);
        validate.setOnClickListener(this);

        /* view */
        filterMemberListView = (ListView) findViewById(R.id.membersList);

        /* model */
        membersModel = new MembersModel();
        membersModel.addObserver(this);
    }

    @Override
    public void onClick(View view){
        if(view == validate){
            setResult(RESULT_OK, null);
            daoFactory.getMyProfileDao().setMemberFilter(membersFilterAdapter.getMembersBeanChecked());
            Intent intent = new Intent(this,FilterActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void update(Observable observable, Object object){
        /* update view with adapter */
        membersFilterAdapter = new MembersFilterAdapter(FilterMemberActivity.this,((MembersModel) observable).getMembers(),daoFactory);
        filterMemberListView.setAdapter(membersFilterAdapter);
    }

    @Override
    protected void onResume() {
        daoFactory.open();
        super.onResume();
        membersModel.onCreate(this,daoFactory,false);
        HomeActivity.isInForeGround = false;
    }

    @Override
    protected void onPause() {
        daoFactory.close();
        super.onPause();
        HomeActivity.isInForeGround = true;
    }

}
