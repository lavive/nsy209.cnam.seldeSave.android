package nsy209.cnam.seldesave.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lavive on 01/06/17.
 */

public class WealthSheetBean implements Parcelable{

    private long id;

    private long remote_id;

    private BigDecimal initialAccount;

    private BigDecimal finalAccount;

    private List<TransactionBean> transactions;

    public WealthSheetBean(){}

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

    public BigDecimal getInitialAccount() {
        return initialAccount;
    }

    public void setInitialAccount(BigDecimal initialAccount) {
        this.initialAccount = initialAccount;
    }

    public BigDecimal getFinalAccount() {
        return finalAccount;
    }

    public void setFinalAccount(BigDecimal finalAccount) {
        this.finalAccount = finalAccount;
    }

    public List<TransactionBean> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionBean> transactions) {
        this.transactions = transactions;
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
        dest.writeDouble(getInitialAccount().doubleValue());
        dest.writeDouble(getFinalAccount().doubleValue());
        dest.writeParcelableArray(getTransactions().toArray(new TransactionBean[getTransactions().size()]),0);
    }

    /* creator */
    public static final Parcelable.Creator<WealthSheetBean> CREATOR = new Parcelable.Creator<WealthSheetBean>(){
        @Override
        public WealthSheetBean createFromParcel(Parcel source){
            return new WealthSheetBean(source);
        }
        @Override
        public  WealthSheetBean[] newArray(int size){
            return new WealthSheetBean[size];
        }
    };

    public WealthSheetBean(Parcel in){
        this();

        setId(in.readLong());
        setRemote_id(in.readLong());
        setInitialAccount(BigDecimal.valueOf(in.readDouble()));
        setFinalAccount(BigDecimal.valueOf(in.readDouble()));

        List<TransactionBean> transactions = new ArrayList<TransactionBean>();
        Parcelable[] parcelables = in.readParcelableArray(TransactionBean.class.getClassLoader());
        for(Parcelable parcelable:parcelables){
            transactions.add((TransactionBean) parcelable);
        }
        setTransactions(transactions);
    }

}


