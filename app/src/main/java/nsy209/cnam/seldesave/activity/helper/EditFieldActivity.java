package nsy209.cnam.seldesave.activity.helper;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.home.HomeActivity;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;
import nsy209.cnam.seldesave.activity.profile.ProfileActivity;
import nsy209.cnam.seldesave.activity.utils.Connected;
import nsy209.cnam.seldesave.background.helper.MyProfileToRemoteUpdate;
import nsy209.cnam.seldesave.bean.MyProfileBean;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.models.FieldModel;
import nsy209.cnam.seldesave.validator.helper.EnumField;

public class EditFieldActivity extends AppCompatActivity implements View.OnClickListener,Observer{

    /* daoFactory */
    private DaoFactory daoFactory;

    /* local constant */
    private static final String VALUE = "value";
    private static final String ERROR = "error";

    /* data */
    private EnumField enumField;

    /* controller */
    private ImageButton validate;

    /* view */
    private TextView field;
    private EditText fieldValue;
    private TextView error;

    /* model */
    private FieldModel fieldModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_field);

        /* database connexion */
        daoFactory = DaoFactory.getInstance(this);
        daoFactory.open();

        /* controller */
        validate = (ImageButton) findViewById(R.id.validateField);
        validate.setOnClickListener(this);

        /* model */
        fieldModel = new FieldModel();
        fieldModel.addObserver(this);
    }



    @Override
    public void onClick(View view) {
        if(view == validate){
            /* check error */
            fieldModel.onChange(enumField,fieldValue.getText().toString());

            /* no error */
            if(error.getText().equals("")) {
                /* update my profile */
                daoFactory.getMyProfileDao().updateMyProfile(update(enumField));

                /* update server if connected to internet */
                if(Connected.isConnectedInternet(this)) {
                    MyProfileToRemoteUpdate myProfileToRemoteUpdate = new MyProfileToRemoteUpdate(this, daoFactory);
                    myProfileToRemoteUpdate.update();
                }

                /* back to my profile */
                setResult(RESULT_OK, null);
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                finish();

            }
        }
    }

    @Override
    public void update(Observable observable,Object object){
        /* update views */
        field =(TextView) findViewById(R.id.fieldName);
        field.setText(getString(((FieldModel) observable).getFieldId()));

        fieldValue = (EditText) findViewById(R.id.editField);
        fieldValue.setText(((FieldModel) observable).getFieldValue());

        error = (TextView) findViewById(R.id.error);
        error.setText( DaoFactory.getInstance(getApplicationContext()).getErrorMessageDao()
                .getErrorMessage( ((FieldModel) observable).getErrorCheck() ) );

        if(((FieldModel) observable).getErrorCheck() != null) {
            fieldValue.setBackgroundColor(ContextCompat.getColor(EditFieldActivity.this,R.color.orange));
        } else {
            fieldValue.setBackgroundColor(ContextCompat.getColor(EditFieldActivity.this,R.color.transparent));
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        /* store value */
        savedInstanceState.putString(VALUE,fieldValue.getText().toString());
        savedInstanceState.putString(ERROR,error.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){

        /* put value in fieldValue */
        fieldValue.setText((String) savedInstanceState.get(VALUE));
        error.setText((String) savedInstanceState.get(ERROR));

        if(!error.getText().toString().equals("")) {
            fieldValue.setBackgroundColor(ContextCompat.getColor(EditFieldActivity.this,R.color.orange));
        } else {
            fieldValue.setBackgroundColor(ContextCompat.getColor(EditFieldActivity.this,R.color.transparent));
        }

    }

    /* helper method */
    private MyProfileBean update(EnumField field){
        MyProfileBean myProfileBean = DaoFactory.getInstance(this).getMyProfileDao().getMyProfile();
        if(field.equals(EnumField.FORNAME)){
            myProfileBean.setForname(fieldValue.getText().toString());
        }
        if(field.equals(EnumField.NAME)){
            myProfileBean.setName(fieldValue.getText().toString());
        }
        if(field.equals(EnumField.ADDRESS)){
            myProfileBean.setAddress(fieldValue.getText().toString());
        }
        if(field.equals(EnumField.POSTAL_CODE)){
            myProfileBean.setPostalCode(fieldValue.getText().toString());
        }
        if(field.equals(EnumField.TOWN)){
            myProfileBean.setTown(fieldValue.getText().toString());
        }
        if(field.equals(EnumField.CELL_NUMBER)){
            myProfileBean.setCellNumber(fieldValue.getText().toString());
        }
        if(field.equals(EnumField.EMAIL)){
            myProfileBean.setEmail(fieldValue.getText().toString());
        }
        if(field.equals(EnumField.PHONE_NUMBER)){
            myProfileBean.setPhoneNumber(fieldValue.getText().toString());
        }

        return myProfileBean;
    }


    @Override
    protected void onResume() {
        daoFactory.open();
        super.onResume();

        /* get field to update */
        String field = getIntent().getStringExtra(ActivityConstant.KEY_INTENT_FIELD);
        String fieldValue = getIntent().getStringExtra(ActivityConstant.KEY_INTENT_FIELD_VALUE);
        enumField = EnumField.getByWording(field);
        fieldModel.onCreate(field,fieldValue);
        HomeActivity.isInForeGround = false;
    }

    @Override
    protected void onPause() {
        daoFactory.close();
        super.onPause();
        HomeActivity.isInForeGround = true;
    }
}
