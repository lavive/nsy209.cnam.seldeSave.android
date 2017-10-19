package nsy209.cnam.seldesave.activity.profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Observable;
import java.util.Observer;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.home.HomeActivity;
import nsy209.cnam.seldesave.models.MyProfileModel;

public class ProfileEditActivity extends AppCompatActivity implements View.OnClickListener,Observer{

    /* controller */
    private ImageButton validate;

    /* model */
    private MyProfileModel myProfileModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        /* button */
        validate = (ImageButton) findViewById(R.id.validateProfile);
        validate.setOnClickListener(this);

        /* model */
        myProfileModel = new MyProfileModel();
        myProfileModel.addObserver(this);
    }



    @Override
    public void onClick(View view) {
        if(view == validate){
            startActivity(new Intent(this,ProfileActivity.class));
        }
    }


    @Override
    public void update(Observable observable, Object object){
        /* update views */

        EditText forname = (EditText) findViewById(R.id.editForname);
        forname.setText(((MyProfileModel) observable).getForname());

        EditText name = (EditText) findViewById(R.id.editName);
        name.setText(((MyProfileModel) observable).getName());

        EditText address = (EditText) findViewById(R.id.editAddress);
        address.setText(((MyProfileModel) observable).getAddress());

        EditText postalCode = (EditText) findViewById(R.id.editPostalCode);
        postalCode.setText(((MyProfileModel) observable).getPostalCode());

        EditText town = (EditText) findViewById(R.id.editTown);
        town.setText(((MyProfileModel) observable).getTown());

        EditText phoneNumber = (EditText) findViewById(R.id.editPhoneNumber);
        phoneNumber.setText(((MyProfileModel) observable).getPhoneNumber());

        EditText email = (EditText) findViewById(R.id.editEmail);
        email.setText(((MyProfileModel) observable).getEmail());

        EditText cellNumber = (EditText) findViewById(R.id.editCellNumber);
        cellNumber.setText(((MyProfileModel) observable).getCellNumber());

    }


    @Override
    protected void onResume() {
        super.onResume();

        myProfileModel.onCreate(getApplicationContext());
        HomeActivity.isInForeGround = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        HomeActivity.isInForeGround = true;
    }
}
