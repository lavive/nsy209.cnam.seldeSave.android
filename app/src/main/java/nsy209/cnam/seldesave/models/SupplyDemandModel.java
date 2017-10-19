package nsy209.cnam.seldesave.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import nsy209.cnam.seldesave.bean.MemberBean;
import nsy209.cnam.seldesave.bean.SupplyDemandBean;
import nsy209.cnam.seldesave.bean.helper.EnumSupplyDemand;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.validator.helper.EnumCheck;
import nsy209.cnam.seldesave.validator.helper.EnumField;
import nsy209.cnam.seldesave.validator.helper.MapFieldCheck;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;

/**
 * Created by lavive on 06/06/17.
 */

public class SupplyDemandModel extends Observable {

    private long id;

    private EnumSupplyDemand type;

    private String category;

    private String title;

    private MemberBean member;

    private EnumCheck errorCheck;

    private List<EnumField> errorFields;

    /* affect values to attributes */
    public void onCreate(DaoFactory daoFactory, long idSupplyDemand){

        SupplyDemandBean supplyDemandBean = daoFactory.getSupplyDemandDao().getSupplyDemand(idSupplyDemand);

        id = supplyDemandBean.getId();

        type = supplyDemandBean.getType();

        category = supplyDemandBean.getCategory();

        title = supplyDemandBean.getTitle();

        member = supplyDemandBean.getMember();

        errorFields = new ArrayList<EnumField>();

        errorCheck = null;

        setChanged();
        notifyObservers();
    }

    /* affect values to attributes */
    public void onCreate(){

        id = ActivityConstant.BAD_ID;

        type = null;

        category = "";

        title = "";

        member = null;

        errorFields = new ArrayList<EnumField>();

        errorCheck = null;

        setChanged();
        notifyObservers();
    }

    /* affect values to attributes */
    public void onChange(String type, String category, String title){

        errorFields = new ArrayList<EnumField>();
        errorFields.clear();
        EnumCheck provisionalError = null;
        if(MapFieldCheck.getValidators(EnumField.TYPE).validate(type) != null){
            errorFields.add(EnumField.TYPE);
            provisionalError =  MapFieldCheck.getValidators(EnumField.TYPE).validate(type);
        }
        if(MapFieldCheck.getValidators(EnumField.CATEGORY).validate(category) != null){
            errorFields.add(EnumField.CATEGORY);
            provisionalError =  MapFieldCheck.getValidators(EnumField.CATEGORY).validate(category);
        }
        if(MapFieldCheck.getValidators(EnumField.TITLE).validate(title) != null){
            errorFields.add(EnumField.TITLE);
            provisionalError =  MapFieldCheck.getValidators(EnumField.TITLE).validate(title);
        }
        errorCheck = provisionalError;

        this.type = EnumSupplyDemand.getByWording(type);
        this.category = category;
        this.title = title;


        setChanged();
        notifyObservers();

    }

    /* getters */

    public long getId() {
        return id;
    }

    public EnumSupplyDemand getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public MemberBean getMember() {
        return member;
    }

    public EnumCheck getErrorCheck() {
        return errorCheck;
    }

    public List<EnumField> getErrorFields() {
        return errorFields;
    }
}
