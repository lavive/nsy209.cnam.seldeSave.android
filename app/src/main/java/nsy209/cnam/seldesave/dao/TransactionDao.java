package nsy209.cnam.seldesave.dao;

import java.util.List;

import nsy209.cnam.seldesave.bean.TransactionBean;

/**
 * Created by lavive on 01/06/17.
 */

public interface TransactionDao {

    public List<TransactionBean> getNewTransactions();

    public void addNewTransaction(TransactionBean transaction);

    public void deleteNewTransaction(long id);

    public List<TransactionBean> getCheckedTransactions();

    public void addCheckedTransaction(TransactionBean transaction);

    public void createCheckedTransaction(List<TransactionBean> transactions);

    public void updateCheckedTransaction(List<TransactionBean> transactions);
}
