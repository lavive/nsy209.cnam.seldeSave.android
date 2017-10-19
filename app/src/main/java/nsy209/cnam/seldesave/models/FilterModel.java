package nsy209.cnam.seldesave.models;


import java.util.List;
import java.util.Observable;

import nsy209.cnam.seldesave.activity.utils.ActivityConstant;
import nsy209.cnam.seldesave.bean.CategoryBean;
import nsy209.cnam.seldesave.bean.FilterBean;
import nsy209.cnam.seldesave.bean.MemberBean;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.validator.helper.EnumCheck;
import nsy209.cnam.seldesave.validator.helper.EnumField;
import nsy209.cnam.seldesave.validator.helper.MapFieldCheck;

/**
 * Created by lavive on 06/06/17.
 */

public class FilterModel extends Observable {

    private String distance;

    private List<MemberBean> membersFilter;

    private List<CategoryBean> categoriesFilter;

    private boolean distanceChecked;

    private boolean membersChecked;

    private boolean categoriesChecked;

    private EnumCheck errorCheck;

    /* affect values to attributes */
    public void onCreate(DaoFactory daoFactory){
        FilterBean filterBean = daoFactory.getMyProfileDao().getMyFilter();

        if(filterBean.getDistance() != ActivityConstant.NOTEXIST) {
            distance = String.valueOf(filterBean.getDistance());
        } else {
            distance = "";
        }
        membersFilter = filterBean.getMembersFilter();
        categoriesFilter = filterBean.getCategoriesFilter();
        distanceChecked = filterBean.isDistanceChecked();
        membersChecked = filterBean.isMembersChecked();
        categoriesChecked = filterBean.isCategoriesChecked();

        setChanged();
        notifyObservers();
    }

    /* affect values to attributes */
    public void onChange(String distance,boolean membersChecked,boolean categoriesChecked,boolean distanceChecked){

        this.distance = distance;
        this.membersChecked = membersChecked;
        this.categoriesChecked = categoriesChecked;
        this.distanceChecked = distanceChecked;
        if(MapFieldCheck.getValidators(EnumField.DISTANCE).validate(distance) != null) {
            this.errorCheck = MapFieldCheck.getValidators(EnumField.DISTANCE).validate(distance);
        } else {
            this.errorCheck = null;
        }

        setChanged();
        notifyObservers();

    }

    /* getters */

    public String getDistance() {
        return distance;
    }

    public List<MemberBean> getMembersFilter() {
        return membersFilter;
    }

    public List<CategoryBean> getCategoriesFilter() {
        return categoriesFilter;
    }

    public boolean isDistanceChecked() {
        return distanceChecked;
    }

    public boolean isMembersChecked() {
        return membersChecked;
    }

    public boolean isCategoriesChecked() {
        return categoriesChecked;
    }

    public EnumCheck getErrorCheck() {
        return errorCheck;
    }
}
