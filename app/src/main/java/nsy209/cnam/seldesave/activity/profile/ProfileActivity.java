package nsy209.cnam.seldesave.activity.profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.home.HomeActivity;
import nsy209.cnam.seldesave.activity.helper.EditFieldActivity;
import nsy209.cnam.seldesave.activity.mysuppliesdemands.MySupplyDemandActivity;
import nsy209.cnam.seldesave.models.MyProfileModel;
import nsy209.cnam.seldesave.validator.helper.EnumField;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener,Observer {

    /* controllers */
    private ImageButton editForname;
    private ImageButton editname;
    private ImageButton editAddress;
    private ImageButton editPostalCode;
    private ImageButton editTown;
    private ImageButton editCellNumber;
    private ImageButton editEmail;
    private ImageButton editPhoneNumber;
    private ImageButton mySuppliesDemands;
    private ImageButton wealthSheet;

    /* views */
    private TextView forname;
    private TextView name;
    private TextView address;
    private TextView postalCode;
    private TextView town;
    private TextView cellNumber;
    private TextView email;
    private TextView phoneNumber;

    /* model */
    private MyProfileModel myProfileModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        /* retrieve the buttons */
        editForname = (ImageButton) findViewById(R.id.editForname);
        editForname.setOnClickListener(this);

        editname = (ImageButton) findViewById(R.id.editName);
        editname.setOnClickListener(this);

        editAddress = (ImageButton) findViewById(R.id.editAddress);
        editAddress.setOnClickListener(this);

        editPostalCode = (ImageButton) findViewById(R.id.editPostalCode);
        editPostalCode.setOnClickListener(this);

        editTown = (ImageButton) findViewById(R.id.editTown);
        editTown.setOnClickListener(this);

        editCellNumber = (ImageButton) findViewById(R.id.editCellNumber);
        editCellNumber.setOnClickListener(this);

        editEmail = (ImageButton) findViewById(R.id.editEmail);
        editEmail.setOnClickListener(this);

        editPhoneNumber = (ImageButton) findViewById(R.id.editPhoneNumber);
        editPhoneNumber.setOnClickListener(this);

        mySuppliesDemands = (ImageButton) findViewById(R.id.mySupplyDemand);
        mySuppliesDemands.setOnClickListener(this);

        wealthSheet = (ImageButton) findViewById(R.id.wealthSheet);
        wealthSheet.setOnClickListener(this);

        /* model */
        myProfileModel = new MyProfileModel();
        myProfileModel.addObserver(this);
        myProfileModel.onCreate(getApplicationContext());
    }



    @Override
    public void onClick(View view) {
        if(view == editForname){
            Intent intent = new Intent(this, EditFieldActivity.class);
            intent.putExtra(ActivityConstant.KEY_INTENT_FIELD,EnumField.FORNAME.getWording());
            intent.putExtra(ActivityConstant.KEY_INTENT_FIELD_VALUE,forname.getText());
            startActivityForResult(intent, ActivityConstant.REQUEST_EXIT);
        } else if(view == editname){
            Intent intent = new Intent(this, EditFieldActivity.class);
            intent.putExtra(ActivityConstant.KEY_INTENT_FIELD,EnumField.NAME.getWording());
            intent.putExtra(ActivityConstant.KEY_INTENT_FIELD_VALUE,name.getText());
            startActivityForResult(intent, ActivityConstant.REQUEST_EXIT);
        } else if(view == editEmail){
            Intent intent = new Intent(this, EditFieldActivity.class);
            intent.putExtra(ActivityConstant.KEY_INTENT_FIELD,EnumField.EMAIL.getWording());
            intent.putExtra(ActivityConstant.KEY_INTENT_FIELD_VALUE,email.getText());
            startActivityForResult(intent, ActivityConstant.REQUEST_EXIT);
        } else if(view == editAddress){
            Intent intent = new Intent(this, EditFieldActivity.class);
            intent.putExtra(ActivityConstant.KEY_INTENT_FIELD,EnumField.ADDRESS.getWording());
            intent.putExtra(ActivityConstant.KEY_INTENT_FIELD_VALUE,address.getText());
            startActivityForResult(intent, ActivityConstant.REQUEST_EXIT);
        } else if(view == editPostalCode){
            Intent intent = new Intent(this, EditFieldActivity.class);
            intent.putExtra(ActivityConstant.KEY_INTENT_FIELD,EnumField.POSTAL_CODE.getWording());
            intent.putExtra(ActivityConstant.KEY_INTENT_FIELD_VALUE,postalCode.getText());
            startActivityForResult(intent, ActivityConstant.REQUEST_EXIT);
        } else if(view == editTown){
            Intent intent = new Intent(this, EditFieldActivity.class);
            intent.putExtra(ActivityConstant.KEY_INTENT_FIELD,EnumField.TOWN.getWording());
            intent.putExtra(ActivityConstant.KEY_INTENT_FIELD_VALUE,town.getText());
            startActivityForResult(intent, ActivityConstant.REQUEST_EXIT);
        } else if(view == editCellNumber){
            Intent intent = new Intent(this, EditFieldActivity.class);
            intent.putExtra(ActivityConstant.KEY_INTENT_FIELD,EnumField.CELL_NUMBER.getWording());
            intent.putExtra(ActivityConstant.KEY_INTENT_FIELD_VALUE,cellNumber.getText());
            startActivityForResult(intent, ActivityConstant.REQUEST_EXIT);
        } else if(view == editPhoneNumber){
            Intent intent = new Intent(this, EditFieldActivity.class);
            intent.putExtra(ActivityConstant.KEY_INTENT_FIELD,EnumField.PHONE_NUMBER.getWording());
            intent.putExtra(ActivityConstant.KEY_INTENT_FIELD_VALUE,phoneNumber.getText());
            startActivityForResult(intent, ActivityConstant.REQUEST_EXIT);
        } else if(view == mySuppliesDemands){
            Intent intent = new Intent(this, MySupplyDemandActivity.class);
            startActivity(intent);
        } else if(view == wealthSheet){
            Intent intent = new Intent(this, ProfileWealthsheetActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /* finish this activity if result ok */
        if (requestCode == ActivityConstant.REQUEST_EXIT) {
            if (resultCode == RESULT_OK) {
                this.finish();

            }
        }
    }

    @Override
    public void update(Observable observable, Object object){
        /* update views */

        forname = (TextView) findViewById(R.id.forname);
        forname.setText(((MyProfileModel) observable).getForname());

        name = (TextView) findViewById(R.id.name);
        name.setText(((MyProfileModel) observable).getName());

        address = (TextView) findViewById(R.id.address);
        address.setText(((MyProfileModel) observable).getAddress());

        postalCode = (TextView) findViewById(R.id.postalCode);
        postalCode.setText(((MyProfileModel) observable).getPostalCode());

        town = (TextView) findViewById(R.id.town);
        town.setText(((MyProfileModel) observable).getTown());

        phoneNumber = (TextView) findViewById(R.id.phoneNumber);
        phoneNumber.setText(((MyProfileModel) observable).getPhoneNumber());

        email = (TextView) findViewById(R.id.email);
        email.setText(((MyProfileModel) observable).getEmail());

        cellNumber = (TextView) findViewById(R.id.cellNumber);
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
