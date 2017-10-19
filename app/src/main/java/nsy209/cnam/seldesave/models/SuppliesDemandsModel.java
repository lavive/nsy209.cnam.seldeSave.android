package nsy209.cnam.seldesave.models;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import nsy209.cnam.seldesave.bean.SupplyDemandBean;
import nsy209.cnam.seldesave.bean.helper.EnumSupplyDemand;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 11/06/17.
 */

public class SuppliesDemandsModel extends Observable {

    private List<SupplyDemandBean> suppliesDemands;
    private List<SupplyDemandBean> suppliesDemandsSorted;

    /* affect values to attributes */
    public void onCreate(DaoFactory daoFactory){

        suppliesDemands = daoFactory.getSupplyDemandDao().getSuppliesDemandsAbleToBePayed();
        suppliesDemandsSorted = new ArrayList<SupplyDemandBean>();

        /* list of category sorted */
        List<String> categories = new ArrayList<String>();
        for(SupplyDemandBean supplyDemandBean:suppliesDemands){
            if(!categories.contains(supplyDemandBean.getCategory())) {
                categories.add(supplyDemandBean.getCategory());
            }
        }
        Collections.sort(categories, Collator.getInstance());

        /* list all sorted */
        for(EnumSupplyDemand enumSupplyDemand:EnumSupplyDemand.values()) {
            for (String category : categories) {
                for(SupplyDemandBean supplyDemandBean:suppliesDemands) {
                    if(supplyDemandBean.getType().equals(enumSupplyDemand) && supplyDemandBean.getCategory().equals(category)){
                        suppliesDemandsSorted.add(supplyDemandBean);
                    }
                }
            }
        }

        setChanged();
        notifyObservers();

    }

    /* getters */

    public List<SupplyDemandBean> getSuppliesDemandsSorted() {
        return suppliesDemandsSorted;
    }
}
