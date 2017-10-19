package nsy209.cnam.seldesave.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.bean.CategoryBean;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 07/06/17.
 */

public class CategoryFilterAdapter extends ArrayAdapter<CategoryBean> {

    /* List to adapt */
    private List<CategoryBean> categoriesBean;

    /* categories checked */
    private List<CategoryBean> categoriesBeanChecked = new ArrayList<CategoryBean>();

    public CategoryFilterAdapter(Context context, List<CategoryBean> categoriesBean,DaoFactory daoFactory, boolean rotate) {
        super(context, 0, categoriesBean);
        this.categoriesBean = categoriesBean;
        if(!rotate) {
            this.categoriesBeanChecked = daoFactory.getMyProfileDao().getMyFilter().getCategoriesFilter();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_filter_category,parent, false);
        }

        CategoryViewHolder viewHolder = (CategoryViewHolder) convertView.getTag();

        if (viewHolder == null) {
            viewHolder = new CategoryViewHolder();
            viewHolder.category = (TextView) convertView.findViewById(R.id.category);
            viewHolder.checkBoxCategory = (CheckBox) convertView.findViewById(R.id.checkBoxCategory);
            convertView.setTag(viewHolder);
        }

        final CategoryBean categoryBean = getItem(position);

        viewHolder.category.setText(categoryBean.getCategory());
        if(categoriesCheckedContains(categoryBean)) {
            viewHolder.checkBoxCategory.setChecked(true);
        } else {
            viewHolder.checkBoxCategory.setChecked(false);
        }
        final CheckBox checkBoxProv = viewHolder.checkBoxCategory;
        checkBoxProv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxProv.isChecked()) {
                    if (!categoriesCheckedContains(categoryBean)) {
                        categoriesBeanChecked.add(categoryBean);
                    }
                } else {
                    if(categoriesCheckedContains(categoryBean)){
                        categoriesBeanChecked.remove(getCategoryFrom(categoryBean.getCategory()));
                    }
                }
            }
        });

        return convertView;


    }

    /* getter */

    public List<CategoryBean> getCategoriesBeanChecked() {
        return categoriesBeanChecked;
    }

    /* setter */

    public void setCategoriesBeanChecked(List<CategoryBean> categoriesBeanChecked) {
        this.categoriesBeanChecked = categoriesBeanChecked;
    }

    /* helper method */
    private boolean categoriesCheckedContains(CategoryBean categoryBean){
        for(CategoryBean categoryBeanChecked:categoriesBeanChecked){
            if(categoryBeanChecked.getCategory().equals(categoryBean.getCategory())){
                return true;
            }
        }
        return false;
    }

    private CategoryBean getCategoryFrom(String category){
        for(CategoryBean categoryBean:categoriesBeanChecked){
            if(categoryBean.getCategory().equals(category)){
                return categoryBean;
            }
        }
        return null;
    }

    private class CategoryViewHolder {
        public TextView category;
        public CheckBox checkBoxCategory;
    }
}

