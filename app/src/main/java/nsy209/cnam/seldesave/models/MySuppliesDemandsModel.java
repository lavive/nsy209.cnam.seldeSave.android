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
 * Created by lavive on 06/06/17.
 */

public class MySuppliesDemandsModel extends Observable {

    private List<SupplyDemandBean> mySuppliesDemands;
    private List<Object> mySuppliesDemandsSorted;

    /* affect values to attributes */
    public void onCreate(DaoFactory daoFactory){

        mySuppliesDemands = daoFactory.getMyProfileDao().getMySuppliesDemands();
        mySuppliesDemandsSorted = new ArrayList<Object>();

        if(!mySuppliesDemands.isEmpty()) {
        /* list of type sorted */
            List<EnumSupplyDemand> types = new ArrayList<EnumSupplyDemand>();
            for (SupplyDemandBean supplyDemandBean : mySuppliesDemands) {
                if (!types.contains(supplyDemandBean.getType())) {
                    types.add(supplyDemandBean.getType());
                }
            }
            Collections.sort(types);

        /* list of category sorted */
            List<String> categories = new ArrayList<String>();
            for (SupplyDemandBean supplyDemandBean : mySuppliesDemands) {
                if (!categories.contains(supplyDemandBean.getCategory())) {
                    categories.add(supplyDemandBean.getCategory());
                }
            }
            Collections.sort(categories, Collator.getInstance());

        /* list all sorted */
            for (EnumSupplyDemand type : types) {
                mySuppliesDemandsSorted.add(type);
                for (String category : categories) {
                    boolean categoryExist = false;
                    /* list of title sorted */
                    List<Title> titles = new ArrayList<Title>();
                    for (SupplyDemandBean supplyDemandBean : mySuppliesDemands) {
                        if (supplyDemandBean.getType().equals(type) &&
                                supplyDemandBean.getCategory().equals(category)) {
                            titles.add(new Title(supplyDemandBean.getTitle(), supplyDemandBean.getId(), supplyDemandBean.getRemote_id()));
                            categoryExist = true;
                        }
                    }
                    Collections.sort(titles);
                    if(categoryExist){
                        mySuppliesDemandsSorted.add(category);
                    }
                    for (Title title : titles) {
                        mySuppliesDemandsSorted.add(title);
                    }
                }
            }
        }

        setChanged();
        notifyObservers();

    }

    /* getters */

    public List<SupplyDemandBean> getMySuppliesDemands() {
        return mySuppliesDemands;
    }

    public List<Object> getMySuppliesDemandsSorted() {
        return mySuppliesDemandsSorted;
    }


    public class Title implements Comparable<Title>{
        private String title;
        private long code;
        private long remoteId;

        public Title(String title,long code,long remoteId){
            Title.this.title = title;
            Title.this.code = code;
            Title.this.remoteId = remoteId;
        }

        public String getTitle(){
            return Title.this.title;
        }

        public long getCode() {
            return code;
        }

        public long getRemoteId() { return remoteId; }

        @Override
        public String toString(){
            return Title.this.title;
        }

        @Override
        public int compareTo(Title t){
            return Long.valueOf(Title.this.code).compareTo(Long.valueOf(t.getCode()));
        }
    }
}
