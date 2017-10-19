package nsy209.cnam.seldesave.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;


/**
 * Created by lavive on 01/06/17.
 */

public class TransactionBean implements Parcelable{

    private long id;

    private long remote_id;

    private MemberBean debtor;

    private MemberBean creditor;

    private SupplyDemandBean supplyDemand;

    private BigDecimal amount;

    private boolean active;

    public TransactionBean(){}

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

    public MemberBean getDebtor() {
        return debtor;
    }

    public void setDebtor(MemberBean debtor) {
        this.debtor = debtor;
    }

    public MemberBean getCreditor() {
        return creditor;
    }

    public void setCreditor(MemberBean creditor) {
        this.creditor = creditor;
    }

    public SupplyDemandBean getSupplyDemand() {
        return supplyDemand;
    }

    public void setSupplyDemand(SupplyDemandBean supplyDemand) {
        this.supplyDemand = supplyDemand;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
        dest.writeParcelable(getDebtor(),0);
        dest.writeParcelable(getCreditor(),0);
        dest.writeParcelable(getSupplyDemand(),0);
        dest.writeDouble(getAmount().doubleValue());
        dest.writeByte((byte) (isActive() ? 1:0));
    }

    /* creator */
    public static final Parcelable.Creator<TransactionBean> CREATOR = new Parcelable.Creator<TransactionBean>(){
        @Override
        public TransactionBean createFromParcel(Parcel source){
            return new TransactionBean(source);
        }
        @Override
        public  TransactionBean[] newArray(int size){
            return new TransactionBean[size];
        }
    };

    public TransactionBean(Parcel in){
        this();

        setId(in.readLong());
        setRemote_id(in.readLong());
        setDebtor((MemberBean) in.readParcelable(MemberBean.class.getClassLoader()));
        setCreditor((MemberBean) in.readParcelable(MemberBean.class.getClassLoader()));
        setSupplyDemand((SupplyDemandBean) in.readParcelable(SupplyDemandBean.class.getClassLoader()));
        setAmount(BigDecimal.valueOf(in.readDouble()));
        setActive(in.readByte() != 0);
    }

}


