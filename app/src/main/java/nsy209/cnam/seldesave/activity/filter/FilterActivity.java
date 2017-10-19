package nsy209.cnam.seldesave.activity.filter;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.home.HomeActivity;
import nsy209.cnam.seldesave.activity.helper.ConfirmActivity;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.models.FilterModel;
import nsy209.cnam.seldesave.validator.helper.EnumField;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;

/**
 * Created by lavive on 30/04/17.
 */

public class FilterActivity extends AppCompatActivity implements View.OnClickListener,Observer{

    /* local constant */
    private static final String ERROR = "error";
    private static final String COLOR_DISTANCE = "color_distance";

    /* daoFactory */
    private DaoFactory daoFactory;

    /* controllers */
    private ImageButton toMembers;
    private ImageButton toCategories;
    private ImageButton validate;

    /* views */
    private EditText distanceValue;
    private CheckBox member;
    private CheckBox category;
    private CheckBox distance;
    private TextView errorDistance;

    /* model */
    private FilterModel filterModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        /* database connexion */
        daoFactory = DaoFactory.getInstance(this);
        daoFactory.open();

        /* views */
        distanceValue = (EditText) findViewById(R.id.editDistance);
        member = (CheckBox) findViewById(R.id.checkBoxMember);
        category = (CheckBox) findViewById(R.id.checkBoxCategory);
        distance = (CheckBox) findViewById(R.id.checkBoxDistance);
        errorDistance = (TextView) findViewById(R.id.errorDistance);

        /* controllers */
        toMembers = (ImageButton) findViewById(R.id.goMember);
        toMembers.setOnClickListener(this);

        toCategories = (ImageButton) findViewById(R.id.goCategory);
        toCategories.setOnClickListener(this);

        validate = (ImageButton) findViewById(R.id.validateFilters);
        validate.setOnClickListener(this);

        /* model */
        filterModel = new FilterModel();
        filterModel.addObserver(this);
        filterModel.onCreate(daoFactory);

        /* if start from ConfirmActivity */
        if(getIntent().getBooleanExtra(ActivityConstant.KEY_INTENT_CONFIRM,false)){

            member.setChecked(getIntent().getBooleanExtra(ActivityConstant.KEY_INTENT_MEMBER_CHECKED,false));
            category.setChecked(getIntent().getBooleanExtra(ActivityConstant.KEY_INTENT_CATEGORY_CHECKED,false));
            distance.setChecked(getIntent().getBooleanExtra(ActivityConstant.KEY_INTENT_DISTANCE_CHECKED,false));
            distanceValue.setText(getIntent().getStringExtra(EnumField.DISTANCE.getWording()));
        }
    }

    @Override
    public void onClick(View view) {
        if(view == toMembers){
            /* go to members filter */
            Intent intent = new Intent(this,FilterMemberActivity.class);
            startActivityForResult(intent, ActivityConstant.REQUEST_EXIT);
        } else if (view == toCategories){
            /* go to categories filter */
            Intent intent = new Intent(this,FilterCategoryActivity.class);
            startActivityForResult(intent, ActivityConstant.REQUEST_EXIT);
        } else if (view == validate){
            /* check error */
            filterModel.onChange(distanceValue.getText().toString(),member.isChecked(),category.isChecked(),distance.isChecked());
            /* confirm */
            if(errorDistance.getText().equals("")) {
                Intent intent = new Intent(this, ConfirmActivity.class);
                /* to inform origin */
                intent.putExtra(ActivityConstant.KEY_INTENT_MY_SUPPLYDEMAND,false);
                intent.putExtra(ActivityConstant.KEY_INTENT_NOTIFICATIONS,false);
                intent.putExtra(ActivityConstant.KEY_INTENT_FILTER,true);
                intent.putExtra(ActivityConstant.KEY_INTENT_PAYMENT,false);
                /* transfer datas */
                intent.putExtra(ActivityConstant.KEY_INTENT_DISTANCE_CHECKED,distance.isChecked());
                intent.putExtra(ActivityConstant.KEY_INTENT_MEMBER_CHECKED,member.isChecked());
                intent.putExtra(ActivityConstant.KEY_INTENT_CATEGORY_CHECKED,category.isChecked());
                intent.putExtra(EnumField.DISTANCE.getWording(),distanceValue.getText().toString());
                startActivityForResult(intent, ActivityConstant.REQUEST_EXIT);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /* to finish this activity if result is ok */
        if (requestCode == ActivityConstant.REQUEST_EXIT) {
            if (resultCode == RESULT_OK) {
                this.finish();

            }
        }
    }

    @Override
    public void update(Observable observable,Object object){
        /* update views */
        member.setChecked(((FilterModel) observable).isMembersChecked());

        category.setChecked(((FilterModel) observable).isCategoriesChecked());

        distance.setChecked(((FilterModel) observable).isDistanceChecked());

        distanceValue.setText(String.valueOf(((FilterModel) observable).getDistance()));

        errorDistance.setText( DaoFactory.getInstance(getApplicationContext()).getErrorMessageDao()
                .getErrorMessage( ((FilterModel) observable).getErrorCheck() ) );

        if(((FilterModel) observable).getErrorCheck() != null) {
            distanceValue.setBackgroundColor(ContextCompat.getColor(FilterActivity.this,R.color.orange));
        } else {
            distanceValue.setBackgroundColor(ContextCompat.getColor(FilterActivity.this,R.color.transparent));
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        /* store values */
        savedInstanceState.putBoolean(ActivityConstant.KEY_INTENT_DISTANCE_CHECKED,distance.isChecked());
        savedInstanceState.putBoolean(ActivityConstant.KEY_INTENT_MEMBER_CHECKED,member.isChecked());
        savedInstanceState.putBoolean(ActivityConstant.KEY_INTENT_CATEGORY_CHECKED,category.isChecked());
        savedInstanceState.putString(EnumField.DISTANCE.getWording(), distanceValue.getText().toString());
        savedInstanceState.putString(ERROR, errorDistance.getText().toString());
        Drawable backgroundDistanceValue = distanceValue.getBackground();
        savedInstanceState.putString(COLOR_DISTANCE,String.valueOf(((ColorDrawable) backgroundDistanceValue).getColor()));
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){

        distance.setChecked(savedInstanceState.getBoolean(ActivityConstant.KEY_INTENT_DISTANCE_CHECKED,false));
        member.setChecked(savedInstanceState.getBoolean(ActivityConstant.KEY_INTENT_MEMBER_CHECKED,false));
        category.setChecked(savedInstanceState.getBoolean(ActivityConstant.KEY_INTENT_CATEGORY_CHECKED,false));
        distanceValue.setText(savedInstanceState.getString(EnumField.DISTANCE.getWording(),""));
        errorDistance.setText(savedInstanceState.getString(ERROR,""));
        distanceValue.setBackgroundColor(Integer.parseInt((String) savedInstanceState.get(COLOR_DISTANCE)));
    }

    @Override
    protected void onResume() {
        daoFactory.open();
        super.onResume();
        filterModel.onCreate(daoFactory);
        HomeActivity.isInForeGround = false;
    }

    @Override
    protected void onPause() {
        daoFactory.close();
        super.onPause();
        HomeActivity.isInForeGround = true;
    }
}
