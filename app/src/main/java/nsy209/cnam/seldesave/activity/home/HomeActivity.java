package nsy209.cnam.seldesave.activity.home;


import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.utils.Connected;
import nsy209.cnam.seldesave.activity.utils.ErrorMessage;
import nsy209.cnam.seldesave.background.helper.JobToScheduleUtil;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.models.AssociationModel;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;
import nsy209.cnam.seldesave.activity.utils.MenuMethod;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener,Observer{

    /* to check if app is ininForeground */
    public static boolean isInForeGround;

    /* to control history */
    public static Activity firstHomeActivity;

    /* daoFactory */
    private DaoFactory daoFactory;

    /* controllers */
    private ImageButton profile;
    private ImageButton mySuppliesDemands;
    private ImageButton suppliesDemands;
    private ImageButton notifications;
    private ImageButton geolocation;
    private ImageButton filter;
    private ImageButton payment;

    /* model */
    private AssociationModel associationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firstHomeActivity = this;

        /* if start from payment by NFC */
        if(getIntent().getBooleanExtra(ActivityConstant.KEY_INTENT_NFC,false)){
            Toast.makeText(this,"",Toast.LENGTH_LONG).show();
        }

        /* database connexion */
        daoFactory = DaoFactory.getInstance(this);
        daoFactory.open();

        /* controllers */
        profile = (ImageButton) findViewById(R.id.profile);
        profile.setOnClickListener(this);

        mySuppliesDemands = (ImageButton) findViewById(R.id.mySupplyDemand);
        mySuppliesDemands.setOnClickListener(this);

        suppliesDemands = (ImageButton) findViewById(R.id.supplyDemand);
        suppliesDemands.setOnClickListener(this);

        notifications = (ImageButton) findViewById(R.id.notification);
        notifications.setOnClickListener(this);

        geolocation = (ImageButton) findViewById(R.id.geolocation);
        geolocation.setOnClickListener(this);

        filter = (ImageButton) findViewById(R.id.filter);
        filter.setOnClickListener(this);

        payment = (ImageButton) findViewById(R.id.payment);
        payment.setOnClickListener(this);

        /* model */
        associationModel = new AssociationModel();
        associationModel.addObserver(this);
        associationModel.onCreate(getApplicationContext());


        /* to retrieve error message from strings.xml */
        daoFactory.getErrorMessageDao().createErrorMessage(ErrorMessage.getErrorMessage(this));
    }

    @Override
    public void onClick(View view) {
        if (view == profile){
            MenuMethod.goToProfile(this);
        } else if(view == mySuppliesDemands){
            MenuMethod.goToMySupplyDemand(this);
        } else if(view == suppliesDemands){
            MenuMethod.goToSupplyDemand(this,daoFactory);
        } else if(view == notifications){
            MenuMethod.goToNotification(this,daoFactory);
        } else if(view == geolocation){
            MenuMethod.goToGeolocation(this,daoFactory);
        } else if(view == filter){
            MenuMethod.goToFilter(this);
        } else if(view == payment){
            MenuMethod.goToPayment(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_profile:
                MenuMethod.goToProfile(this);
                return true;
            case R.id.menu_item_mySupplyDemand:
                MenuMethod.goToMySupplyDemand(this);
                return true;
            case R.id.menu_item_supplyDemand:
                MenuMethod.goToSupplyDemand(this,daoFactory);
                return true;
            case R.id.menu_item_notification:
                MenuMethod.goToNotification(this,daoFactory);
                return true;
            case R.id.menu_item_geolocation:
                MenuMethod.goToGeolocation(this,daoFactory);
                return true;
            case R.id.menu_item_filter:
                MenuMethod.goToFilter(this);
                return true;
            case R.id.menu_item_pay:
                MenuMethod.goToPayment(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void update(Observable observable,Object object){
        /* update views */
        TextView associationName = (TextView) findViewById(R.id.associationName);
        associationName.setText(((AssociationModel) observable).getName());

        TextView associationAddress = (TextView) findViewById(R.id.associationAddress);
        associationAddress.setText(((AssociationModel) observable).getAddress());

        TextView associationPostalCode = (TextView) findViewById(R.id.associationPostalCode);
        associationPostalCode.setText(((AssociationModel) observable).getPostalCode());

        TextView associationTown = (TextView) findViewById(R.id.associationTown);
        associationTown.setText(((AssociationModel) observable).getTown());

        TextView associationPhoneNumber = (TextView) findViewById(R.id.associationPhoneNumber);
        associationPhoneNumber.setText(((AssociationModel) observable).getPhoneNumber());

        TextView associationEmail = (TextView) findViewById(R.id.associationEmail);
        associationEmail.setText(((AssociationModel) observable).getEmail());

        TextView associationWebSite = (TextView) findViewById(R.id.associationWebSite);
        associationWebSite.setText(((AssociationModel) observable).getWebSite());

    }

    @Override
    protected void onResume() {
        daoFactory.open();
        associationModel.onCreate(getApplicationContext());

        /* draw attention to network connection default */
        if (!Connected.isConnectedInternet(this)) {
            Toast.makeText(this,getString(R.string.no_connection_update_fail),Toast.LENGTH_LONG).show();
        }

        /* periodic server examining */
        JobToScheduleUtil.scheduleJob(this);

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
