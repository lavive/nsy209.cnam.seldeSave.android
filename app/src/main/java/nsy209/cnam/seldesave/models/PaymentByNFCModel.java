package nsy209.cnam.seldesave.models;

import java.util.Observable;

/**
 * Created by lavive on 06/06/17.
 */

public class PaymentByNFCModel extends Observable {

    private String title;

    private String debtor;

    private String creditor;

    private String supplyDemand;

    private String amount;

    private boolean visibleButton;

    private boolean payment;

    /* affect values to attributes */
    public void onCreate(String title, String debtor, String creditor, String supplyDemand, String amount, boolean visibleButton, boolean payment){
        this.title = title;
        this.debtor = debtor;
        this.creditor = creditor;
        this.supplyDemand = supplyDemand;
        this.amount = amount;
        this.visibleButton = visibleButton;
        this.payment = payment;

        setChanged();
        notifyObservers();

    }

    /* getters */

    public String getTitle() {
        return title;
    }

    public String getDebtor() {
        return debtor;
    }

    public String getCreditor() {
        return creditor;
    }

    public String getSupplyDemand() {
        return supplyDemand;
    }

    public String getAmount() {
        return amount;
    }

    public boolean isVisibleButton() {
        return visibleButton;
    }

    public boolean isPayment() {
        return payment;
    }
}
