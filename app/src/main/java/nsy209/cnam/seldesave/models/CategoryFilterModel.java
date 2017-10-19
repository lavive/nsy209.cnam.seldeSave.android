package nsy209.cnam.seldesave.models;


import java.util.List;
import java.util.Observable;

import nsy209.cnam.seldesave.bean.CategoryBean;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 07/06/17.
 */

public class CategoryFilterModel extends Observable {

    private List<CategoryBean> categoriesBean;

    /* affect values to attributes */
    public void onCreate(DaoFactory daoFactory){
        categoriesBean = daoFactory.getSupplyDemandDao().getAllCategories();

        setChanged();
        notifyObservers();

    }

    /* getters */

    public List<CategoryBean> getCategoriesBean() {
        return categoriesBean;
    }
}
