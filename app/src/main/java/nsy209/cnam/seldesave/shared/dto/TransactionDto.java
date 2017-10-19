package nsy209.cnam.seldesave.shared.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class TransactionDto implements Serializable,Parcelable{

	/**
	 * For checking version
	 */
	private static final long serialVersionUID = 1L;

	@Json(name = "id")
	private Long id;

	@Json(name = "debtorMemberId")
	private Long debtorMemberId;

	@Json(name = "creditorMemberId")
	private Long creditorMemberId;

	@Json(name = "supplyDemandId")
	private Long supplyDemandId;

	@Json(name = "amount")
	private String amount;

	public TransactionDto(){}

	/* getter and setter */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreditorMemberId() {
		return creditorMemberId;
	}

	public void setCreditorMemberId(Long creditorMemberId) {
		this.creditorMemberId = creditorMemberId;
	}

	public Long getDebtorMemberId() {
		return debtorMemberId;
	}

	public void setDebtorMemberId(Long debtorMemberId) {
		this.debtorMemberId = debtorMemberId;
	}

	public Long getSupplyDemandId() {
		return supplyDemandId;
	}

	public void setSupplyDemandId(Long supplyDemandId) {
		this.supplyDemandId= supplyDemandId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "amount: "+getAmount();
	}


	/* implements parcelable */
	@Override
	public int describeContents(){
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags){
		dest.writeLong(getId());
		dest.writeLong(getDebtorMemberId());
		dest.writeLong(getCreditorMemberId());
		dest.writeLong(getSupplyDemandId());
		dest.writeString(getAmount());
	}

	/* creator */
	public static final Parcelable.Creator<TransactionDto> CREATOR = new Parcelable.Creator<TransactionDto>(){
		@Override
		public TransactionDto createFromParcel(Parcel source){
			return new TransactionDto(source);
		}
		@Override
		public  TransactionDto[] newArray(int size){
			return new TransactionDto[size];
		}
	};

	public TransactionDto(Parcel in){
		this();

		setId(in.readLong());
		setDebtorMemberId(in.readLong());
		setCreditorMemberId(in.readLong());
		setSupplyDemandId(in.readLong());
		setAmount(in.readString());
	}


}
