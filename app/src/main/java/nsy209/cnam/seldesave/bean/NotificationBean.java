package nsy209.cnam.seldesave.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import nsy209.cnam.seldesave.bean.helper.ApplyFilterBuilder;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.dao.sharedPreferences.AssociationPreferences;

/**
 * Created by lavive on 01/06/17.
 */

public class NotificationBean implements Parcelable{

    private long id;

    private long remote_id;

    private String title;

    private String Text;

    private long personOriginId;

    private String category;

    private boolean read;

    public NotificationBean(){}

    /* getter and Setter */

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

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPersonOriginId() {
        return personOriginId;
    }

    public void setPersonOriginId(long personOriginId) {
        this.personOriginId = personOriginId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    @Override
    public String toString(){
        return getTitle()+" "+getText();
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
        dest.writeString(getTitle());
        dest.writeString(getText());
        dest.writeLong(getPersonOriginId());
        dest.writeString(getCategory());
        dest.writeByte((byte) (isRead() ? 1:0));
    }

    /* creator */
    public static final Parcelable.Creator<NotificationBean> CREATOR = new Parcelable.Creator<NotificationBean>(){
        @Override
        public NotificationBean createFromParcel(Parcel source){
            return new NotificationBean(source);
        }
        @Override
        public  NotificationBean[] newArray(int size){
            return new NotificationBean[size];
        }
    };

    public NotificationBean(Parcel in){
        this();

        setId(in.readLong());
        setRemote_id(in.readLong());
        setTitle(in.readString());
        setText(in.readString());
        setPersonOriginId(in.readLong());
        setCategory(in.readString());
        setRead(in.readByte() != 0);
    }

    /* helper method */
    public static List<NotificationBean> applyFilters(List<NotificationBean> notificationsBean,DaoFactory daoFactory){
        ApplyFilterBuilder applyFilterBuilder = new ApplyFilterBuilder(daoFactory.getMemberDao().getAllMembers(),daoFactory);
        applyFilterBuilder =applyFilterBuilder.apply(daoFactory.getMyProfileDao().getMyFilter().isCategoriesChecked(),
                daoFactory.getMyProfileDao().getMyFilter().isMembersChecked(),
                daoFactory.getMyProfileDao().getMyFilter().isDistanceChecked(),
                notificationsBean);
        List<MemberBean> membersFiltered = applyFilterBuilder.getMembersFiltered();
        List<NotificationBean> notificationBeanFilteredList = new ArrayList<NotificationBean>();
        for(NotificationBean notificationBean:notificationsBean){
            boolean isInside = false;
            for(MemberBean memberBean:membersFiltered){
                if(notificationBean.getPersonOriginId() == memberBean.getRemote_id() ||
                        notificationBean.getPersonOriginId() == AssociationPreferences.getRemoteId()){
                    isInside = true;
                    break;
                }
            }
            if(isInside){
                notificationBeanFilteredList.add(notificationBean);
            }
        }
        return notificationBeanFilteredList;
    }
}

