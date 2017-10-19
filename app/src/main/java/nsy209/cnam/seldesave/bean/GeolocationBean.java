package nsy209.cnam.seldesave.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lavive on 21/06/17.
 */

public class GeolocationBean implements Parcelable {

    private long id;

    private long memberId;

    private double latitude;

    private double longitude;

    public GeolocationBean(){}

    /* getter and setter */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /* implements parcelable */
    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeLong(getId());
        dest.writeLong(getMemberId());
        dest.writeDouble(getLatitude());
        dest.writeDouble(getLongitude());
    }

    /* creator */
    public static final Parcelable.Creator<GeolocationBean> CREATOR = new Parcelable.Creator<GeolocationBean>(){
        @Override
        public GeolocationBean createFromParcel(Parcel source){
            return new GeolocationBean(source);
        }
        @Override
        public  GeolocationBean[] newArray(int size){
            return new GeolocationBean[size];
        }
    };

    public GeolocationBean(Parcel in){
        this();

        setId(in.readLong());
        setMemberId(in.readLong());
        setLatitude(in.readDouble());
        setLongitude(in.readDouble());
    }
}
