package nsy209.cnam.seldesave.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import nsy209.cnam.seldesave.activity.utils.ActivityConstant;
import nsy209.cnam.seldesave.bean.TransactionBean;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.dao.TransactionDao;
import nsy209.cnam.seldesave.dao.enumTable.EnumTransactionTable;

import static nsy209.cnam.seldesave.activity.utils.ActivityConstant.NOTEXIST;

/**
 * Created by lavive on 02/06/17.
 */

public class TransactionDaoImpl implements TransactionDao {

    public static  TransactionDao getInstance(DaoFactory daoFactory){

            return new TransactionDaoImpl(daoFactory);

    }

    /* attribute */
    private DaoFactory daoFactory;

    private TransactionDaoImpl(DaoFactory daoFactory){
        this.daoFactory = daoFactory;
    }


    @Override
    public List<TransactionBean> getNewTransactions(){
        daoFactory.open();
        List<TransactionBean> transactions = new ArrayList<TransactionBean>();

        Cursor cursor = this.daoFactory.getDatabase().query(EnumTransactionTable.getTableNameNew(), EnumTransactionTable.getAllColumns(),
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TransactionBean transaction = cursorToTransaction(cursor);
            transactions.add(transaction);
            cursor.moveToNext();
        }

        cursor.close();
        return transactions;
    }

    @Override
    public void addNewTransaction(TransactionBean transaction){
        daoFactory.open();
        ContentValues values = new ContentValues();
        values.put(EnumTransactionTable.COLUMN_REMOTE_ID.getColumnName(), NOTEXIST);
        values.put(EnumTransactionTable.COLUMN_ID_DEBTOR.getColumnName(), transaction.getDebtor().getRemote_id());
        values.put(EnumTransactionTable.COLUMN_ID_CREDITOR.getColumnName(), transaction.getCreditor().getRemote_id());
        values.put(EnumTransactionTable.COLUMN_ID_SUPPLYDEMAND.getColumnName(), transaction.getSupplyDemand().getRemote_id());
        values.put(EnumTransactionTable.COLUMN_AMOUNT.getColumnName(),  transaction.getAmount().setScale(2, RoundingMode.CEILING).floatValue());
        this.daoFactory.getDatabase().insert(EnumTransactionTable.getTableNameNew(), null, values);
    }

    @Override
    public void deleteNewTransaction(long id){
        daoFactory.open();
        this.daoFactory.getDatabase().delete(EnumTransactionTable.getTableNameNew(),
                EnumTransactionTable.COLUMN_ID.getColumnName()+"="+id,null);
    }

    @Override
    public List<TransactionBean> getCheckedTransactions(){
        daoFactory.open();
        List<TransactionBean> transactions = new ArrayList<TransactionBean>();

        Cursor cursor = this.daoFactory.getDatabase().query(EnumTransactionTable.getTableNameChecked(), EnumTransactionTable.getAllColumns(),
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TransactionBean transaction = cursorToTransaction(cursor);
            transactions.add(transaction);
            cursor.moveToNext();
        }

        cursor.close();
        return transactions;
    }

    @Override
    public void addCheckedTransaction(TransactionBean transaction){
        daoFactory.open();
        ContentValues values = new ContentValues();
        values.put(EnumTransactionTable.COLUMN_REMOTE_ID.getColumnName(), transaction.getRemote_id());
        values.put(EnumTransactionTable.COLUMN_ID_DEBTOR.getColumnName(), transaction.getDebtor().getId());
        values.put(EnumTransactionTable.COLUMN_ID_CREDITOR.getColumnName(), transaction.getCreditor().getRemote_id());
        values.put(EnumTransactionTable.COLUMN_ID_SUPPLYDEMAND.getColumnName(), transaction.getSupplyDemand().getRemote_id());
        values.put(EnumTransactionTable.COLUMN_AMOUNT.getColumnName(), transaction.getAmount().setScale(2, RoundingMode.CEILING).floatValue());
        this.daoFactory.getDatabase().insert(EnumTransactionTable.getTableNameChecked(), null, values);
    }

    @Override
    public void createCheckedTransaction(List<TransactionBean> transactions){
        daoFactory.open();

        this.daoFactory.getDatabase().execSQL("DROP TABLE IF EXISTS " + EnumTransactionTable.getTableNameChecked());
        this.daoFactory.getDatabase().execSQL(EnumTransactionTable.getCreateCommandChecked());
        for(TransactionBean transactionBean:transactions) {
            ContentValues values = new ContentValues();
            values.put(EnumTransactionTable.COLUMN_REMOTE_ID.getColumnName(), transactionBean.getRemote_id());
            values.put(EnumTransactionTable.COLUMN_ID_DEBTOR.getColumnName(), transactionBean.getDebtor().getRemote_id());
            values.put(EnumTransactionTable.COLUMN_ID_CREDITOR.getColumnName(), transactionBean.getCreditor().getRemote_id());
            values.put(EnumTransactionTable.COLUMN_ID_SUPPLYDEMAND.getColumnName(), transactionBean.getSupplyDemand().getRemote_id());
            values.put(EnumTransactionTable.COLUMN_AMOUNT.getColumnName(), transactionBean.getAmount().setScale(2, RoundingMode.CEILING).floatValue());
            this.daoFactory.getDatabase().insert(EnumTransactionTable.getTableNameChecked(), null, values);

            /* remove unchecked transaction */
            long id = getNewTransactionId(transactionBean);
            if(id != ActivityConstant.NOTEXIST){
                deleteNewTransaction(id);
            }
        }
    }

    @Override
    public void updateCheckedTransaction(List<TransactionBean> transactions){
        daoFactory.open();

        for(TransactionBean transactionBean:transactions) {
            if(transactionBean.isActive()) {
                ContentValues values = new ContentValues();
                values.put(EnumTransactionTable.COLUMN_REMOTE_ID.getColumnName(), transactionBean.getRemote_id());
                values.put(EnumTransactionTable.COLUMN_ID_DEBTOR.getColumnName(), transactionBean.getDebtor().getRemote_id());
                values.put(EnumTransactionTable.COLUMN_ID_CREDITOR.getColumnName(), transactionBean.getCreditor().getRemote_id());
                values.put(EnumTransactionTable.COLUMN_ID_SUPPLYDEMAND.getColumnName(), transactionBean.getSupplyDemand().getRemote_id());
                values.put(EnumTransactionTable.COLUMN_AMOUNT.getColumnName(), transactionBean.getAmount().setScale(2, RoundingMode.CEILING).floatValue());
                this.daoFactory.getDatabase().insert(EnumTransactionTable.getTableNameChecked(), null, values);
            } else {
                this.daoFactory.getDatabase().delete(EnumTransactionTable.getTableNameChecked(),
                        EnumTransactionTable.COLUMN_REMOTE_ID.getColumnName()+"="+ transactionBean.getRemote_id(),null);
            }
        }
    }

    /* helper method */
    private TransactionBean cursorToTransaction(Cursor cursor) {
        TransactionBean transaction = new TransactionBean();
        transaction.setId(cursor.getLong(0));
        transaction.setRemote_id(cursor.getLong(1));
        transaction.setDebtor(this.daoFactory.getMemberDao().getMemberByRemoteId(cursor.getLong(2)));
        transaction.setCreditor(this.daoFactory.getMemberDao().getMemberByRemoteId(cursor.getLong(3)));
        transaction.setSupplyDemand(this.daoFactory.getSupplyDemandDao().getSupplyDemandByRemoteId(cursor.getLong(4)));
        transaction.setAmount(BigDecimal.valueOf(cursor.getFloat(5)));
        return transaction;
    }

    private boolean transactionIsUnChecked(TransactionBean transactionBean){
        List<TransactionBean> transactionBeanList = getNewTransactions();

        boolean isInside = false;
        for(TransactionBean transactionBean1:transactionBeanList){
            if((transactionBean1.getCreditor().getRemote_id() ==  transactionBean.getCreditor().getRemote_id()) &&
                    (transactionBean1.getAmount().setScale(2, RoundingMode.CEILING).equals(transactionBean.getAmount().setScale(2, RoundingMode.CEILING))) &&
                    (transactionBean1.getDebtor().getRemote_id() ==  transactionBean.getDebtor().getRemote_id()) &&
                    (transactionBean1.getSupplyDemand().getRemote_id() ==  transactionBean.getSupplyDemand().getRemote_id())){
                isInside = true;
            }
        }
        return isInside;

    }

    private long getNewTransactionId(TransactionBean transactionBean){
        if(transactionIsUnChecked(transactionBean)){
            List<TransactionBean> transactions = new ArrayList<TransactionBean>();

            Cursor cursor = this.daoFactory.getDatabase().query(EnumTransactionTable.getTableNameNew(), EnumTransactionTable.getAllColumns(),
                    EnumTransactionTable.COLUMN_ID_CREDITOR.getColumnName() + " = " + transactionBean.getCreditor().getRemote_id() +" AND "+
                            EnumTransactionTable.COLUMN_ID_DEBTOR.getColumnName() + " = " + transactionBean.getDebtor().getRemote_id() +" AND "+
                            EnumTransactionTable.COLUMN_ID_SUPPLYDEMAND.getColumnName() + " = " + transactionBean.getSupplyDemand().getRemote_id(),
                    null, null, null, null);

            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                TransactionBean transaction = cursorToTransaction(cursor);
                if(transaction.getAmount().setScale(2, RoundingMode.CEILING).equals(transactionBean.getAmount().setScale(2, RoundingMode.CEILING))) {
                    transactions.add(transaction);
                }
                cursor.moveToNext();
            }
            cursor.close();
            return transactions.get(0).getId();
        }
        return ActivityConstant.NOTEXIST;
    }
}
