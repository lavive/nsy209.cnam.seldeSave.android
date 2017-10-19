package nsy209.cnam.seldesave.models;

import java.math.BigDecimal;
import java.util.List;
import java.util.Observable;

import nsy209.cnam.seldesave.bean.TransactionBean;
import nsy209.cnam.seldesave.bean.WealthSheetBean;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 06/06/17.
 */

public class WealthSheetModel extends Observable {

    private BigDecimal initialAccount;

    private BigDecimal finalAccount;

    private List<TransactionBean> transactions;

    public void onCreate(DaoFactory daoFactory){

        WealthSheetBean wealthSheetBean = daoFactory.getMyProfileDao().getMyWealthSheet();

        initialAccount = wealthSheetBean.getInitialAccount();

        finalAccount = wealthSheetBean.getFinalAccount();

        transactions = wealthSheetBean.getTransactions();

        setChanged();
        notifyObservers();
    }

    /* getters */

    public BigDecimal getInitialAccount() {
        return initialAccount;
    }

    public BigDecimal getFinalAccount() {
        return finalAccount;
    }

    public List<TransactionBean> getTransactions() {
        return transactions;
    }
}
