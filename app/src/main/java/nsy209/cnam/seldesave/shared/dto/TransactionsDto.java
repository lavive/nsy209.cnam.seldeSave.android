package nsy209.cnam.seldesave.shared.dto;

import com.squareup.moshi.Json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lavive on 23/09/17.
 */

public class TransactionsDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Json(name = "transactions")
    private List<TransactionDto> transactions = new ArrayList<TransactionDto>();

    /* getter and setter */

    public List<TransactionDto> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDto> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString(){
        String result ="{ ";
        for(TransactionDto transactionDto:transactions){
            result += transactionDto.toString()+" , ";
        }
        result = result.substring(0,result.length()-1);
        result +="}";
        return result;
    }
}
