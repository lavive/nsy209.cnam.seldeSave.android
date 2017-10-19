package nsy209.cnam.seldesave.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 01/06/17.
 */

public class MyProfileBean extends Person implements Parcelable,Comparable<MyProfileBean>{

    private String mobileId;

    private String forname;

    private boolean checked;

    private WealthSheetBean wealthSheet;

    private List<SupplyDemandBean> suppliesDemands;

    private List<NotificationBean> notifications;

    public MyProfileBean(){}

    /* getter and setter */

    public String getMobileId() {
        return mobileId;
    }

    public void setMobileId(String mobileId) {
        this.mobileId = mobileId;
    }

    public String getForname() {
        return forname;
    }

    public void setForname(String forname) {
        this.forname = forname;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public WealthSheetBean getWealthSheet() {
        return wealthSheet;
    }

    public void setWealthSheet(WealthSheetBean wealthSheet) {
        this.wealthSheet = wealthSheet;
    }

    public List<SupplyDemandBean> getSuppliesDemands() {
        return suppliesDemands;
    }

    public void setSuppliesDemands(List<SupplyDemandBean> suppliesDemands) {
        this.suppliesDemands = suppliesDemands;
    }

    public List<NotificationBean> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationBean> notifications) {
        this.notifications = notifications;
    }

    @Override
    public String toString(){
        return id+":"+remote_id+": "+forname+ " "+name;
    }

    /* comparator */
    @Override
    public int compareTo(MyProfileBean myProfileBeanToCompare){
        if(this.getId()>myProfileBeanToCompare.getId()) return 1;
        if(this.getId() == myProfileBeanToCompare.getId()){ return 0;}
        else {return -1;}
    }

    /* helper method */
    public static  MemberBean myProfileToMember(DaoFactory daoFactory){
        MemberBean memberBean = new MemberBean();
        MyProfileBean myProfileBean = daoFactory.getMyProfileDao().getMyProfile();

        memberBean.setId(myProfileBean.getId());
        memberBean.setRemote_id(myProfileBean.getRemote_id());
        memberBean.setForname(myProfileBean.getForname());
        memberBean.setName(myProfileBean.getName());
        memberBean.setAddress(myProfileBean.getAddress());
        memberBean.setPostalCode(myProfileBean.getPostalCode());
        memberBean.setTown(myProfileBean.getTown());
        memberBean.setCellNumber(myProfileBean.getCellNumber());
        memberBean.setEmail(myProfileBean.getEmail());
        memberBean.setPhoneNumber(myProfileBean.getPhoneNumber());

        return memberBean;
    }

    /* implements parcelable */
    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeLong(getId());
        dest.writeLong(getRemote_id());
        dest.writeString(getName());
        dest.writeString(getForname());
        dest.writeString(getAddress());
        dest.writeString(getPostalCode());
        dest.writeString(getTown());
        dest.writeString(getEmail());
        dest.writeString(getCellNumber());
        dest.writeString(getPhoneNumber());
    }

    /* creator */
    public static final Parcelable.Creator<MyProfileBean> CREATOR = new Parcelable.Creator<MyProfileBean>(){
        @Override
        public MyProfileBean createFromParcel(Parcel source){
            return new MyProfileBean(source);
        }
        @Override
        public  MyProfileBean[] newArray(int size){
            return new MyProfileBean[size];
        }
    };

    public MyProfileBean(Parcel in){
        this();

        setId(in.readLong());
        setRemote_id(in.readLong());
        setName(in.readString());
        setForname(in.readString());
        setAddress(in.readString());
        setPostalCode(in.readString());
        setTown(in.readString());
        setEmail(in.readString());
        setCellNumber(in.readString());
        setPhoneNumber(in.readString());
    }

}
