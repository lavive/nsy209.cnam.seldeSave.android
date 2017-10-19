package nsy209.cnam.seldesave.shared.dto;

import com.squareup.moshi.Json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class WealthSheetDto implements Serializable{

	/**
	 * For checking version
	 */
	private static final long serialVersionUID = 1L;

	@Json(name = "id")
	private Long id;

	@Json(name = "initialAccount")
	private String initialAccount;

	@Json(name = "finalAccount")
	private String finalAccount;

	@Json(name = "transactions")
	private List<TransactionDto> transactions = new ArrayList<TransactionDto>();


	/* getter and setter */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInitialAccount() {
		return initialAccount;
	}

	public void setInitialAccount(String initialAccount) {
		this.initialAccount = initialAccount;
	}

	public String getFinalAccount() {
		return finalAccount;
	}

	public void setFinalAccount(String finalAccount) {
		this.finalAccount = finalAccount;
	}

	public List<TransactionDto> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionDto> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "initial account: "+getInitialAccount()+"\n"+
				"final account: "+getFinalAccount();
	}
}
