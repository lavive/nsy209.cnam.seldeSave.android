package nsy209.cnam.seldesave.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.bean.SupplyDemandBean;

/**
 * Created by lavive on 11/06/17.
 */

public class SuppliesDemandsPaymentAdapter extends ArrayAdapter<SupplyDemandBean> {



    public SuppliesDemandsPaymentAdapter(Context context, List<SupplyDemandBean> suppliesDemandsBean) {
        super(context, 0, suppliesDemandsBean);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_payment_supplydemand,parent, false);
        }

        SupplyDemandViewHolder viewHolder = (SupplyDemandViewHolder) convertView.getTag();

        if (viewHolder == null) {
            viewHolder = new SupplyDemandViewHolder();
            viewHolder.type = (TextView) convertView.findViewById(R.id.type);
            viewHolder.category = (TextView) convertView.findViewById(R.id.category);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(viewHolder);
        }

        SupplyDemandBean supplyDemandBean = getItem(position);

        viewHolder.type.setText(supplyDemandBean.getType().getWording());
        viewHolder.category.setText(supplyDemandBean.getCategory());
        viewHolder.title.setText(supplyDemandBean.getTitle());
        return convertView;

    }

    private class SupplyDemandViewHolder {
        public TextView type;
        public TextView category;
        public TextView title;
    }
}