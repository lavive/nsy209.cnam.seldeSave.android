package nsy209.cnam.seldesave.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lavive on 01/06/17.
 */

public class AssociationBean extends Person implements Parcelable{

    private String webSite;

    public AssociationBean(){}

    /* getter and setter */

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    /* implements parcelable */
    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeLong(getId());
        dest.writeString(getName());
        dest.writeString(getAddress());
        dest.writeString(getPostalCode());
        dest.writeString(getTown());
        dest.writeString(getEmail());
        dest.writeString(getCellNumber());
        dest.writeString(getPhoneNumber());
        dest.writeString(getWebSite());
    }

    /* creator */
    public static final Parcelable.Creator<AssociationBean> CREATOR = new Parcelable.Creator<AssociationBean>(){
        @Override
        public AssociationBean createFromParcel(Parcel source){
            return new AssociationBean(source);
        }
        @Override
        public  AssociationBean[] newArray(int size){
            return new AssociationBean[size];
        }
    };

    public AssociationBean(Parcel in){
        this();

        setId(in.readLong());
        setName(in.readString());
        setAddress(in.readString());
        setPostalCode(in.readString());
        setTown(in.readString());
        setEmail(in.readString());
        setCellNumber(in.readString());
        setPhoneNumber(in.readString());
        setWebSite(in.readString());
    }

}
