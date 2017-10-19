package nsy209.cnam.seldesave.activity.geolocation;

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
import nsy209.cnam.seldesave.models.MemberModel;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;

public class AMemberDisplayActivity extends AppCompatActivity implements View.OnClickListener,Observer {

    /* daoFactory */
    private DaoFactory daoFactory;

    /* controller */
    private ImageButton  geolocateMember;

    /* model */
    private MemberModel memberModel;

    /* data */
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_member_display);

        /* database connexion */
        daoFactory = DaoFactory.getInstance(this);
        daoFactory.open();

        /* controller */
        geolocateMember = (ImageButton) findViewById(R.id.geolocateMember);
        geolocateMember.setOnClickListener(this);

        /* model */
        memberModel = new MemberModel();
        memberModel.addObserver(this);

        /* member's id to display if activity launch from ggogle map */
        id = getIntent().getLongExtra(ActivityConstant.KEY_INTENT_ID,ActivityConstant.BAD_ID);
        if(id < 0){
            Toast.makeText(this,getString(R.string.error_data),Toast.LENGTH_LONG).show();
            finish();
        }else {
            memberModel.onCreate(daoFactory, id);
        }
    }

    @Override
    public void onClick(View view){
        if(view == geolocateMember){
            Intent intent = new Intent(this, GeolocationMemberActivity.class);
            intent.putExtra(ActivityConstant.KEY_INTENT_GEOLOCATION_NAME,daoFactory.getMemberDao().getGeolocation(id));
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void update(Observable observable,Object object) {
        /* update views */

        TextView forname = (TextView) findViewById(R.id.forname);
        forname.setText(((MemberModel) observable).getForname());

        TextView name = (TextView) findViewById(R.id.name);
        name.setText(((MemberModel) observable).getName());

        TextView address = (TextView) findViewById(R.id.address);
        address.setText(((MemberModel) observable).getAddress());

        TextView postalCode = (TextView) findViewById(R.id.postalCode);
        postalCode.setText(((MemberModel) observable).getPostalCode());

        TextView town = (TextView) findViewById(R.id.town);
        town.setText(((MemberModel) observable).getTown());

        TextView phoneNumber = (TextView) findViewById(R.id.phoneNumber);
        phoneNumber.setText(((MemberModel) observable).getPhoneNumber());

        TextView email = (TextView) findViewById(R.id.email);
        email.setText(((MemberModel) observable).getEmail());

        TextView cellNumber = (TextView) findViewById(R.id.cellNumber);
        cellNumber.setText(((MemberModel) observable).getCellNumber());
    }

    @Override
    protected void onResume() {
        daoFactory.open();
        super.onResume();
        HomeActivity.isInForeGround = false;
    }

    @Override
    protected void onPause() {
        daoFactory.close();
        super.onPause();
        HomeActivity.isInForeGround = true;
    }
}
