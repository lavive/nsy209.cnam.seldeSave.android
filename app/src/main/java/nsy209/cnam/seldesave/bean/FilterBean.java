package nsy209.cnam.seldesave.bean;

import java.util.List;


/**
 * Created by lavive on 06/06/17.
 */

public class FilterBean {

    private long distance;

    private List<MemberBean> membersFilter;

    private List<CategoryBean> categoriesFilter;

    private boolean distanceChecked;

    private boolean membersChecked;

    private boolean categoriesChecked;

    /* getters and setters */

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public List<MemberBean> getMembersFilter() {
        return membersFilter;
    }

    public void setMembersFilter(List<MemberBean> membersFilter) {
        this.membersFilter = membersFilter;
    }

    public List<CategoryBean> getCategoriesFilter() {
        return categoriesFilter;
    }

    public void setCategoriesFilter(List<CategoryBean> categoriesFilter) {
        this.categoriesFilter = categoriesFilter;
    }

    public boolean isDistanceChecked() {
        return distanceChecked;
    }

    public void setDistanceChecked(boolean distanceChecked) {
        this.distanceChecked = distanceChecked;
    }

    public boolean isMembersChecked() {
        return membersChecked;
    }

    public void setMembersChecked(boolean membersChecked) {
        this.membersChecked = membersChecked;
    }

    public boolean isCategoriesChecked() {
        return categoriesChecked;
    }

    public void setCategoriesChecked(boolean categoriesChecked) {
        this.categoriesChecked = categoriesChecked;
    }

    @Override
    public String toString(){
        return "distance: "+getDistance()+" "+isDistanceChecked()+", "+"membres: "+isMembersChecked()+" "+getMembersFilter()+", "+
                "categorie: "+isCategoriesChecked()+" "+getCategoriesFilter();
    }


}
