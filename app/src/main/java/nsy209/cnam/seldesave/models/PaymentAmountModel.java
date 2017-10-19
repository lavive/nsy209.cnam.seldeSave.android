package nsy209.cnam.seldesave.models;

import java.util.Observable;

import nsy209.cnam.seldesave.bean.MemberBean;
import nsy209.cnam.seldesave.bean.SupplyDemandBean;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.validator.helper.EnumCheck;
import nsy209.cnam.seldesave.validator.helper.EnumField;
import nsy209.cnam.seldesave.validator.helper.MapFieldCheck;

/**
 * Created by lavive on 08/06/17.
 */

public class PaymentAmountModel extends Observable {

    private String amount;

    private String member;

    private String supplyDemand;

    private EnumCheck errorCheck;

    /* affect values to attributes */
    public void onCreate(DaoFactory daoFactory, long member_id, long supplyDemand_id){
        MemberBean memberBean = daoFactory.getMemberDao().getMember(member_id);
        this.member = memberBean.getForname()+" "+memberBean.getName();
        SupplyDemandBean supplyDemandBean = daoFactory.getSupplyDemandDao().getSupplyDemand(supplyDemand_id);
        this.supplyDemand = supplyDemandBean.getTitle();
        this.amount ="";
        this.errorCheck = null;

        setChanged();
        notifyObservers();
    }

    /* affect values to attributes */
    public void onChange(String amount){
        if(MapFieldCheck.getValidators(EnumField.AMOUNT).validate(amount) != null) {
            this.errorCheck = MapFieldCheck.getValidators(EnumField.AMOUNT).validate(amount);
        }
        else{
            this.errorCheck = null;
        }

        setChanged();
        notifyObservers();

    }

    /* getters */

    public String getAmount() {
        return amount;
    }

    public EnumCheck getErrorCheck() {
        return errorCheck;
    }

    public String getMember() {
        return member;
    }

    public String getSupplyDemand() {
        return supplyDemand;
    }
}
