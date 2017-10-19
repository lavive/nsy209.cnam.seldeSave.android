package nsy209.cnam.seldesave.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import nsy209.cnam.seldesave.bean.TransactionBean;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 01/10/17.
 */

public class MyTransactionModel extends Observable {

    private List<TransactionBean> transactions = new ArrayList<TransactionBean>();

    private boolean empty;

    /* affect values to attributes */
    public void onCreate(DaoFactory daoFactory){

        transactions = daoFactory.getTransactionDao().getNewTransactions();
        empty = (transactions.isEmpty())?true:false;

        setChanged();
        notifyObservers();

    }

    /* getters */

    public List<TransactionBean> getTransactions() {
        return transactions;
    }

    public boolean isEmpty() {
        return empty;
    }

}
