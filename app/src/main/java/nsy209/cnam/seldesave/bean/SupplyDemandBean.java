package nsy209.cnam.seldesave.bean;

import android.os.Parcel;
import android.os.Parcelable;

import nsy209.cnam.seldesave.bean.helper.EnumSupplyDemand;

/**
 * Created by lavive on 01/05/17.
 */

public class SupplyDemandBean implements Parcelable{

    private long id;

    protected long remote_id;

    private EnumSupplyDemand type;

    private String category;

    private String title;

    private MemberBean member;

    private boolean checked; //check in server

    private boolean active;

    public SupplyDemandBean() {

    }

    /* getter and setter */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRemote_id() {
        return remote_id;
    }

    public void setRemote_id(long remote_id) {
        this.remote_id = remote_id;
    }

    public EnumSupplyDemand getType() {
        return type;
    }

    public void setType(EnumSupplyDemand type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    public static class Type{
        private String type;
        public Type(String type){
            this.type = type;
        }

        public String getType() {
            return this.type;
        }
    }

    public static class Category{
        private String category;
        public Category(String category){
            this.category = category;
        }

        public String getCategory() {
            return this.category;
        }
    }

    @Override
    public String toString(){
        return this.id+","+this.remote_id+": "+type+ " "+category+" "+title+", member: "+member;
    }

    @Override
    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        if(object == null){
            return false;
        }
        if(getClass() != object.getClass()){
            return false;
        }
        SupplyDemandBean bean = (SupplyDemandBean) object;
        if(bean.getMember() == null){
            return false;
        }
        if(getRemote_id() == bean.getRemote_id() && getType().equals(bean.getType()) && getTitle().equals(bean.getTitle()) &&
                getCategory().equals(bean.getCategory()) && getMember().getRemote_id() == bean.getMember().getRemote_id() &&
                isChecked() == bean.isChecked() && isActive() == bean.isActive()){
            return true;
        }

        return false;
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
        dest.writeString(getType().getWording());
        dest.writeString(getCategory());
        dest.writeString(getTitle());
        dest.writeParcelable(getMember(),0);
        dest.writeByte((byte) (isChecked() ? 1:0));
        dest.writeByte((byte) (isActive() ? 1:0));
    }

    /* creator */
    public static final Parcelable.Creator<SupplyDemandBean> CREATOR = new Parcelable.Creator<SupplyDemandBean>(){
        @Override
        public SupplyDemandBean createFromParcel(Parcel source){
            return new SupplyDemandBean(source);
        }
        @Override
        public  SupplyDemandBean[] newArray(int size){
            return new SupplyDemandBean[size];
        }
    };

    public SupplyDemandBean(Parcel in){
        this();

        setId(in.readLong());
        setRemote_id(in.readLong());
        setType(EnumSupplyDemand.getByWording(in.readString()));
        setCategory(in.readString());
        setTitle(in.readString());
        setMember((MemberBean) in.readParcelable(MemberBean.class.getClassLoader()));
        setChecked(in.readByte() != 0);
        setActive(in.readByte() != 0);
    }

}

