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
import nsy209.cnam.seldesave.adapter.CategoryFilterAdapter;
import nsy209.cnam.seldesave.bean.CategoryBean;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.models.CategoryFilterModel;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;

public class FilterCategoryActivity extends AppCompatActivity implements View.OnClickListener,Observer{

    /* daoFactory */
    private DaoFactory daoFactory;

    /* controller */
    private ImageButton validate;

    /* view */
    private ListView filterCategoryListView;

    /* model */
    private CategoryFilterModel categoryFilterModel;

    /* adapter */
    private CategoryFilterAdapter categoryFilterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_category);

        /* database connexion */
        daoFactory = DaoFactory.getInstance(this);
        daoFactory.open();

        /* controller */
        validate = (ImageButton) findViewById(R.id.validateCategoryFilter);
        validate.setOnClickListener(this);

        /* view */
        filterCategoryListView = (ListView) findViewById(R.id.categoriesList);

        /* model */
        categoryFilterModel = new CategoryFilterModel();
        categoryFilterModel.addObserver(this);
    }

    @Override
    public void onClick(View view){
        if(view == validate){
            setResult(RESULT_OK, null);
            daoFactory.getMyProfileDao().setCategoryFilter(categoryFilterAdapter.getCategoriesBeanChecked());
            Intent intent = new Intent(this,FilterActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void update(Observable observable,Object object){
        /* update view with adapter */
        categoryFilterAdapter = new CategoryFilterAdapter(FilterCategoryActivity.this,((CategoryFilterModel) observable).getCategoriesBean(),daoFactory,false);
        filterCategoryListView.setAdapter(categoryFilterAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        /* store values */
        String[] categories = new String[categoryFilterAdapter.getCategoriesBeanChecked().size()];
        int i = 0;
        for(CategoryBean categoryBean:categoryFilterAdapter.getCategoriesBeanChecked()){
            categories[i] = categoryBean.getCategory();
            i++;
        }
        savedInstanceState.putStringArray(ActivityConstant.KEY_INTENT_IDS,categories);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){

        String[] categories = savedInstanceState.getStringArray(ActivityConstant.KEY_INTENT_IDS);
        categoryFilterAdapter = new CategoryFilterAdapter(FilterCategoryActivity.this,daoFactory.getCategoryDao().getAllCategories()/*.getSupplyDemandDao().getAllCategories()*/,daoFactory,true);
        for(String category: categories){
            categoryFilterAdapter.getCategoriesBeanChecked().add(new CategoryBean(category));
        }
        filterCategoryListView.setAdapter(categoryFilterAdapter);

    }

    @Override
    protected void onResume() {
        daoFactory.open();
        super.onResume();
        categoryFilterModel.onCreate(daoFactory);

        HomeActivity.isInForeGround = false;
    }

    @Override
    protected void onPause() {
        daoFactory.close();
        super.onPause();
        HomeActivity.isInForeGround = true;
    }
}
