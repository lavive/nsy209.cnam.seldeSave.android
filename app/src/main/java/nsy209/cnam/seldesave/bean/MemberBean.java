package nsy209.cnam.seldesave.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lavive on 30/04/17.
 */

public class MemberBean extends Person implements Parcelable {

    private String forname;

    private boolean active;

    private boolean checked;

    public MemberBean(){

    }

    /* getter and setter */

    public void setForname(String forname) {
        this.forname = forname;
    }

    public String getForname() {
        return forname;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString(){
        return id+":"+remote_id+": "+forname+ " "+name;
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
        dest.writeByte((byte) (isActive() ? 1:0));
    }

    /* creator */
    public static final Parcelable.Creator<MemberBean> CREATOR = new Parcelable.Creator<MemberBean>(){
        @Override
        public MemberBean createFromParcel(Parcel source){
            return new MemberBean(source);
        }
        @Override
        public  MemberBean[] newArray(int size){
            return new MemberBean[size];
        }
    };

    public MemberBean(Parcel in){
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
        setActive(in.readByte() != 0);
    }

}



