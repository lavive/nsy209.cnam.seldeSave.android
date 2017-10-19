package nsy209.cnam.seldesave.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lavive on 06/06/17.
 */

public class CategoryBean implements Parcelable{

    private long id;

    protected long remote_id;

    private String category;

    private boolean active;

    public CategoryBean(){}

    public CategoryBean(String category){
        this.category = category;
    }

    /* getters and setters */

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString(){
        return id+": "+category;
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
        dest.writeString(getCategory());
        dest.writeByte((byte) (isActive() ? 1:0));
    }

    /* creator */
    public static final Parcelable.Creator<CategoryBean> CREATOR = new Parcelable.Creator<CategoryBean>(){
        @Override
        public CategoryBean createFromParcel(Parcel source){
            return new CategoryBean(source);
        }
        @Override
        public  CategoryBean[] newArray(int size){
            return new CategoryBean[size];
        }
    };

    public CategoryBean(Parcel in){
        this();

        setId(in.readLong());
        setRemote_id(in.readLong());
        setCategory(in.readString());
        setActive(in.readByte() != 0);
    }

}
