package nsy209.cnam.seldesave.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.bean.TransactionBean;

/**
 * Created by lavive on 07/06/17.
 */

public class WealthSheetAdapter extends ArrayAdapter<TransactionBean> {

    /* VIEW TYPE */
    private static final int ITEM_VIEW_TYPE_CREDIT = 0;
    private static final int ITEM_VIEW_TYPE_DEBIT = 1;
    private static final int ITEM_VIEW_TYPE_COUNT = 2;

    /* List to adapt */
    private List<TransactionBean> transactionsBean;

    /* my profile remote id */
    private long remoteId;


    public WealthSheetAdapter(Context context, List<TransactionBean> transactions,long remoteId) {
        super(context, 0, transactions);
        this.transactionsBean = transactions;
        this.remoteId = remoteId;
    }

    @Override
    public int getCount() {
        return transactionsBean.size();
    }

    @Override
    public TransactionBean getItem(int position) {
        return transactionsBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return ITEM_VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        TransactionBean transactionBean = transactionsBean.get(position);
        if (transactionBean.getDebtor().getRemote_id() == this.remoteId)
            return ITEM_VIEW_TYPE_DEBIT;
        else if (transactionBean.getCreditor().getRemote_id() == this.remoteId )
            return ITEM_VIEW_TYPE_CREDIT;

        return -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int type = getItemViewType(position);

        if (type == ITEM_VIEW_TYPE_DEBIT) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_wealthsheet_debit,parent, false);
            }

            TransactionViewHolder viewHolder = (TransactionViewHolder) convertView.getTag();

            if (viewHolder == null) {
                viewHolder = new TransactionViewHolder();
                viewHolder.supplyDemand = (TextView) convertView.findViewById(R.id.supplyDemandPayed);
                viewHolder.forname = (TextView) convertView.findViewById(R.id.fornameMember);
                viewHolder.name = (TextView) convertView.findViewById(R.id.nameMember);
                viewHolder.sign = (TextView) convertView.findViewById(R.id.sign);
                viewHolder.amount = (TextView) convertView.findViewById(R.id.amount);
                convertView.setTag(viewHolder);
            }

            TransactionBean transactionBean = getItem(position);

            viewHolder.supplyDemand.setText(transactionBean.getSupplyDemand().getTitle());
            viewHolder.forname.setText(transactionBean.getCreditor().getForname());
            viewHolder.name.setText(transactionBean.getCreditor().getName());
            viewHolder.sign.setText("-");
            viewHolder.amount.setText(String.valueOf(transactionBean.getAmount().floatValue()));

        } else if (type == ITEM_VIEW_TYPE_CREDIT) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_wealthsheet_credit,parent, false);
            }

            TransactionViewHolder viewHolder = (TransactionViewHolder) convertView.getTag();

            if (viewHolder == null) {
                viewHolder = new TransactionViewHolder();
                viewHolder.supplyDemand = (TextView) convertView.findViewById(R.id.supplyDemandPayed);
                viewHolder.forname = (TextView) convertView.findViewById(R.id.fornameMember);
                viewHolder.name = (TextView) convertView.findViewById(R.id.nameMember);
                viewHolder.sign = (TextView) convertView.findViewById(R.id.sign);
                viewHolder.amount = (TextView) convertView.findViewById(R.id.amount);
                convertView.setTag(viewHolder);
            }

            TransactionBean transactionBean = getItem(position);

            viewHolder.supplyDemand.setText(transactionBean.getSupplyDemand().getTitle());
            viewHolder.forname.setText(transactionBean.getDebtor().getForname());
            viewHolder.name.setText(transactionBean.getDebtor().getName());
            viewHolder.sign.setText("+");
            viewHolder.amount.setText(String.valueOf(transactionBean.getAmount().floatValue()));

        }

        return convertView;

    }

    private class TransactionViewHolder {
        public TextView supplyDemand;
        public TextView forname;
        public TextView name;
        public TextView sign;
        public TextView amount;
    }
}

