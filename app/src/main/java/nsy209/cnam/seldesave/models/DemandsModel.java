package nsy209.cnam.seldesave.models;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import nsy209.cnam.seldesave.bean.SupplyDemandBean;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 06/06/17.
 */

public class DemandsModel extends Observable {

    private List<SupplyDemandBean> demands;
    private List<Object> demandsSorted;


    /* affect values to attributes */
    public void onCreate(DaoFactory daoFactory){

        demands = daoFactory.getSupplyDemandDao().getAllDemands();
        demandsSorted = new ArrayList<Object>();

        /* list of category sorted */
        List<String> categories = new ArrayList<String>();
        for(SupplyDemandBean supplyDemandBean:demands){
            if(!categories.contains(supplyDemandBean.getCategory())) {
                categories.add(supplyDemandBean.getCategory());
            }
        }
        Collections.sort(categories, Collator.getInstance());

        /* list all sorted */
        for(String category:categories){
            demandsSorted.add(category);
                /* list of title sorted */
            List<DemandsModel.Title> titles = new ArrayList<DemandsModel.Title>();
            for(SupplyDemandBean supplyDemandBean:demands){
                if(supplyDemandBean.getCategory().equals(category)){
                    titles.add(new DemandsModel.Title(supplyDemandBean.getTitle(),supplyDemandBean.getId()));
                }
            }
            Collections.sort(titles);
            for(DemandsModel.Title title:titles){
                demandsSorted.add(title);
            }
        }

        setChanged();
        notifyObservers();

    }

    /* getters */

    public List<SupplyDemandBean> getDemands() {
        return demands;
    }

    public List<Object> getDemandsSorted() {
        return demandsSorted;
    }


    public class Title implements Comparable<Title>{
        private String title;
        private long code;

        public Title(String title,long code){
            Title.this.title = title;
            Title.this.code = code;
        }

        public String getTitle(){
            return Title.this.title;
        }

        public long getCode() {
            return code;
        }

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
