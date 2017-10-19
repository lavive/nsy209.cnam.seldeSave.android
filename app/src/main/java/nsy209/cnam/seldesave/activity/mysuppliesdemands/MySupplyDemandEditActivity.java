package nsy209.cnam.seldesave.activity.mysuppliesdemands;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.home.HomeActivity;
import nsy209.cnam.seldesave.activity.helper.ConfirmActivity;
import nsy209.cnam.seldesave.bean.CategoryBean;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.models.MySupplyDemandModel;
import nsy209.cnam.seldesave.validator.helper.EnumField;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;

public class MySupplyDemandEditActivity extends AppCompatActivity implements View.OnClickListener,Observer{

    /* constants */
    private static final String TYPE_VALUE = "type";
    private static final String CATEGORY_VALUE = "category";
    private static final String TITLE_VALUE = "title";
    private static final String ERROR = "error";
    private static final String COLOR_TYPE = "color_type";
    private static final String COLOR_CATEGORY = "color_category";
    private static final String COLOR_TITLE = "color_title";

    public final static int REQUEST_EXIT = 0;

    /* DaoFactory */
    private DaoFactory daoFactory;

    /* controller */
    private ImageButton validate;
    private boolean add;
    private boolean edit;

    /* views */
    Spinner type;
    Spinner category;
    EditText title;
    TextView error;

    /* model */
    private MySupplyDemandModel mySupplyDemandModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_supplydemand_edit);

        /* database connexion */
        daoFactory = DaoFactory.getInstance(this);
        daoFactory.open();

        /* views */
        type = (Spinner) findViewById(R.id.typeSpinner);
        category = (Spinner) findViewById(R.id.categorySpinner);
        title = (EditText) findViewById(R.id.editTitle);
        error = (TextView) findViewById(R.id.error);

        /* controller */
        validate = (ImageButton) findViewById(R.id.validateSupplyDemand);
        validate.setOnClickListener(this);

        /* adapter */
        List<String> typeItems = new ArrayList<String>();
        typeItems.add(getString(R.string.enum_supply));
        typeItems.add(getString(R.string.enum_demand));

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,typeItems);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(typeAdapter);

        List<String> categoryItems = new ArrayList<String>();
        List<CategoryBean> categories = daoFactory.getCategoryDao().getAllCategories();
        for(CategoryBean categoryBean:categories){
            categoryItems.add(categoryBean.getCategory());
        }
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categoryItems);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(categoryAdapter);

        /* model */
        mySupplyDemandModel = new MySupplyDemandModel();
        mySupplyDemandModel.addObserver(this);
    }

    @Override
    public void onClick(View view){
        if(view == validate){
            /* check error */
            mySupplyDemandModel.onChange((String) type.getSelectedItem(),(String) category.getSelectedItem(),title.getText().toString());
            /* confirm */
            if(error.getText().equals("")) {
                setResult(RESULT_OK, null);
                Intent intent = new Intent(this, ConfirmActivity.class);
                intent.putExtra(ActivityConstant.KEY_INTENT_MY_SUPPLYDEMAND,true);
                intent.putExtra(ActivityConstant.KEY_INTENT_NOTIFICATIONS,false);
                intent.putExtra(ActivityConstant.KEY_INTENT_FILTER,false);
                intent.putExtra(ActivityConstant.KEY_INTENT_PAYMENT,false);
                if(edit){
                    intent.putExtra(ActivityConstant.KEY_INTENT_ID, mySupplyDemandModel.getId());
                    intent.putExtra(ActivityConstant.KEY_INTENT_REMOTE_ID, mySupplyDemandModel.getRemoteId());
                }
                intent.putExtra(EnumField.TYPE.getWording(),(String) type.getSelectedItem());
                intent.putExtra(EnumField.CATEGORY.getWording(),(String) category.getSelectedItem());
                intent.putExtra(EnumField.TITLE.getWording(), title.getText().toString());
                intent.putExtra(ActivityConstant.KEY_INTENT_ADD,add);
                intent.putExtra(ActivityConstant.KEY_INTENT_EDIT,edit);
                startActivityForResult(intent, REQUEST_EXIT);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /* finish this activity if result ok */
        if (requestCode == REQUEST_EXIT) {
            if (resultCode == RESULT_OK) {
                this.finish();

            }
        }
    }

    @Override
    public void update(Observable observable,Object object){
        /* update views */
        type = (Spinner) findViewById(R.id.typeSpinner);
        if(mySupplyDemandModel.getType() != null) {
            setSpinText(type, ((MySupplyDemandModel) observable).getType().getWording());
        }

        category = (Spinner) findViewById(R.id.categorySpinner);
        setSpinText(category,((MySupplyDemandModel) observable).getCategory());

        title = (EditText) findViewById(R.id.editTitle);
        title.setText(((MySupplyDemandModel) observable).getTitle());

        error = (TextView) findViewById(R.id.error);
        if(((MySupplyDemandModel) observable).getErrorCheck() != null) {
            error.setText(daoFactory.getErrorMessageDao()
                    .getErrorMessage(((MySupplyDemandModel) observable).getErrorCheck()));
        } else {
            error.setText("");
        }

        if(((MySupplyDemandModel) observable).getErrorFields().contains(EnumField.TYPE)){
            type.setBackgroundColor(ContextCompat.getColor(MySupplyDemandEditActivity.this,R.color.orange));
        } else {
            type.setBackgroundColor(ContextCompat.getColor(MySupplyDemandEditActivity.this,R.color.transparent));
        }
        if(((MySupplyDemandModel) observable).getErrorFields().contains(EnumField.CATEGORY)){
            category.setBackgroundColor(ContextCompat.getColor(MySupplyDemandEditActivity.this,R.color.orange));
        } else {
            category.setBackgroundColor(ContextCompat.getColor(MySupplyDemandEditActivity.this,R.color.transparent));
        }
        if(((MySupplyDemandModel) observable).getErrorFields().contains(EnumField.TITLE)){
            title.setBackgroundColor(ContextCompat.getColor(MySupplyDemandEditActivity.this,R.color.orange));
        } else {
            title.setBackgroundColor(ContextCompat.getColor(MySupplyDemandEditActivity.this,R.color.transparent));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        /* store values */
        savedInstanceState.putString(TYPE_VALUE,(String) type.getSelectedItem());
        savedInstanceState.putString(CATEGORY_VALUE,(String) category.getSelectedItem());
        savedInstanceState.putString(TITLE_VALUE, title.getText().toString());
        savedInstanceState.putString(ERROR,error.getText().toString());

        /* store colors */
        Drawable backgroundType = type.getBackground();
        savedInstanceState.putString(COLOR_TYPE,String.valueOf(((ColorDrawable) backgroundType).getColor()));
        Drawable backgroundCategory = category.getBackground();
        savedInstanceState.putString(COLOR_CATEGORY,String.valueOf(((ColorDrawable) backgroundCategory).getColor()));
        Drawable backgroundTitle = title.getBackground();
        savedInstanceState.putString(COLOR_TITLE,String.valueOf(((ColorDrawable) backgroundTitle).getColor()));
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){

        /* put value in fieldValue */
        setSpinText(type,(String) savedInstanceState.get(TYPE_VALUE));
        setSpinText(category,(String) savedInstanceState.get(CATEGORY_VALUE));
        title.setText((String) savedInstanceState.get(TITLE_VALUE));
        error.setText((String) savedInstanceState.get(ERROR));
        type.setBackgroundColor(Integer.parseInt((String) savedInstanceState.get(COLOR_TYPE)));
        category.setBackgroundColor(Integer.parseInt((String) savedInstanceState.get(COLOR_CATEGORY)));
        title.setBackgroundColor(Integer.parseInt((String) savedInstanceState.get(COLOR_TITLE)));

    }

    @Override
    protected void onResume() {
        daoFactory.open();
        super.onResume();

        edit = getIntent().getBooleanExtra(ActivityConstant.KEY_INTENT_EDIT,false);
        add = getIntent().getBooleanExtra(ActivityConstant.KEY_INTENT_ADD,false);
        /* if start from MySupplyDemandActivity */
        if(edit) {
            long id = getIntent().getLongExtra(ActivityConstant.KEY_INTENT_ID, ActivityConstant.BAD_ID);
            if (id < 0) {
                Toast.makeText(this, getString(R.string.error_data), Toast.LENGTH_LONG).show();
                finish();
            }
            long remoteId = getIntent().getLongExtra(ActivityConstant.KEY_INTENT_REMOTE_ID,ActivityConstant.BAD_ID);
            mySupplyDemandModel.onCreate(daoFactory, id,remoteId);
        } else if(add){
            mySupplyDemandModel.onCreate();
        }

        /* if start from ConfirmActivity */
        else if(getIntent().getBooleanExtra(ActivityConstant.KEY_INTENT_CONFIRM,false)){
            setSpinText(type, getIntent().getStringExtra(EnumField.TYPE.getWording()));

            setSpinText(category, getIntent().getStringExtra(EnumField.CATEGORY.getWording()));

            title.setText(getIntent().getStringExtra(EnumField.TITLE.getWording()));

        } else {
            Toast.makeText(this, getString(R.string.error_data), Toast.LENGTH_LONG).show();
        }

        HomeActivity.isInForeGround = false;
    }

    @Override
    protected void onPause() {
        daoFactory.close();
        super.onPause();
        HomeActivity.isInForeGround = true;
    }


    /* helper method */
    private void setSpinText(Spinner spin, String text){
        for(int i= 0; i < spin.getAdapter().getCount(); i++)
        {
            if(spin.getAdapter().getItem(i).toString().contains(text))
            {
                spin.setSelection(i);
            }
        }

    }

}
