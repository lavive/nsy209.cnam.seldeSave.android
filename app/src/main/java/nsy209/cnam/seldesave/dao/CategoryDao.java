package nsy209.cnam.seldesave.dao;

import java.util.List;

import nsy209.cnam.seldesave.bean.CategoryBean;

/**
 * Created by lavive on 27/09/17.
 */

public interface CategoryDao {

    public List<CategoryBean> getAllCategories();

    public void createCategories(List<CategoryBean> categoriesBean);

    public void removeCategory(String category);

    public void addCategory(CategoryBean categoryBean);
}
