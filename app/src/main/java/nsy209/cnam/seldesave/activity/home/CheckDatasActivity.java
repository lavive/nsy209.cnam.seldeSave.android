package nsy209.cnam.seldesave.activity.home;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;
import nsy209.cnam.seldesave.activity.utils.Connected;
import nsy209.cnam.seldesave.dao.DaoFactory;

public class CheckDatasActivity extends AppCompatActivity {

    /* daoFactory */
    private DaoFactory daoFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_datas);

        /* database connexion */
        daoFactory = DaoFactory.getInstance(this);
        daoFactory.open();

        /* for testing first connexion */
        daoFactory.clear();
        /****************/

        /* Checking */
        if(checkFirstConnection()){
            if(internetConnection()){
                toDoWithFirstAndConnexion();
            } else{
                toDoWithFirstAndNoConnexion();
            }
        } else {
            if(internetConnection()){
                toDoWithConnexion();
            } else{
                toDoWithNoConnexion();
            }
        }
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


    /* helpers method */

    private boolean checkFirstConnection(){
        return (daoFactory.getMyProfileDao().getMyProfile().getId() == ActivityConstant.NOTEXIST);
    }

    private boolean internetConnection(){
        return Connected.isConnectedInternet(this);
    }

    /* finish activity */
    private void toDoWithFirstAndNoConnexion(){
        /* display error message */
        Toast.makeText(getApplicationContext(),getString(R.string.first_connection),Toast.LENGTH_LONG).show();

        /* finish after end message display */
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                finish();
            }
        }, Toast.LENGTH_LONG);

    }

    /* go to authentication */
    private void toDoWithFirstAndConnexion(){
        startActivity(new Intent(getApplicationContext(),AuthenticationActivity.class));
        finish();

    }

    private void toDoWithNoConnexion(){
        /* display error message */
        Toast.makeText(getApplicationContext(),getString(R.string.no_connection_update_fail),Toast.LENGTH_LONG).show();

        /* go to home activity after end message display */
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                finish();
            }
        }, Toast.LENGTH_LONG);

    }

    /* go to home activity */
    private void toDoWithConnexion(){

        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        finish();

    }
}
