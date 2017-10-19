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

public class SuppliesModel extends Observable {

    private List<SupplyDemandBean> supplies;
    private List<Object> suppliesSorted;

    /* affect values to attributes */
    public void onCreate(DaoFactory daoFactory){

        supplies = daoFactory.getSupplyDemandDao().getAllSupplies();
        suppliesSorted = new ArrayList<Object>();

        /* list of category sorted */
        List<String> categories = new ArrayList<String>();
        for(SupplyDemandBean supplyDemandBean:supplies){
            if(!categories.contains(supplyDemandBean.getCategory())) {
                categories.add(supplyDemandBean.getCategory());
            }
        }
        Collections.sort(categories, Collator.getInstance());

        /* list all sorted */
            for(String category:categories){
                suppliesSorted.add(category);
                /* list of title sorted */
                List<Title> titles = new ArrayList<Title>();
                for(SupplyDemandBean supplyDemandBean:supplies){
                    if(supplyDemandBean.getCategory().equals(category)){
                        titles.add(new Title(supplyDemandBean.getTitle(),supplyDemandBean.getId()));
                    }
                }
                Collections.sort(titles);
                for(Title title:titles){
                    suppliesSorted.add(title);
                }
            }

        setChanged();
        notifyObservers();

    }

    /* getters */

    public List<SupplyDemandBean> getSupplies() {
        return supplies;
    }

    public List<Object> getSuppliesSorted() {
        return suppliesSorted;
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
