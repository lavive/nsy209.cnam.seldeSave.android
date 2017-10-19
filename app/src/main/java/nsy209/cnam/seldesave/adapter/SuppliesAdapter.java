package nsy209.cnam.seldesave.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.geolocation.AMemberDisplayActivity;
import nsy209.cnam.seldesave.bean.MemberBean;
import nsy209.cnam.seldesave.bean.SupplyDemandBean;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;
import nsy209.cnam.seldesave.models.SuppliesModel;

/**
 * Created by lavive on 06/05/17.
 */

public class SuppliesAdapter extends BaseAdapter {

    /* VIEW TYPE */
    private static final int ITEM_VIEW_TYPE_CATEGORY = 0;
    private static final int ITEM_VIEW_TYPE_MAIN = 1;
    private static final int ITEM_VIEW_TYPE_COUNT = 2;

    /* DaoFactory */
    private DaoFactory daoFactory;

    /* List to adapt */
    private List<Object> supplies;

    /* context */
    private Context context;


    public SuppliesAdapter(Context context, List<Object> supplies, DaoFactory daoFactory) {
        super();
        this.supplies = supplies;
        this.context = context;
        this.daoFactory = daoFactory;
    }

    @Override
    public int getCount() {
        return this.supplies.size();
    }

    @Override
    public Object getItem(int position) {
        return this.supplies.get(position);
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
        if (this.supplies.get(position) instanceof String)
            return ITEM_VIEW_TYPE_CATEGORY;
        else if (this.supplies.get(position) instanceof SuppliesModel.Title)
            return ITEM_VIEW_TYPE_MAIN;

        return -1;
    }

    @Override
    public boolean isEnabled(int position) {
        return getItemViewType(position) != ITEM_VIEW_TYPE_CATEGORY;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final int type = getItemViewType(position);

        if (type == ITEM_VIEW_TYPE_MAIN) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_supplydemand_main, parent, false);
            }

            SupplyViewHolder viewHolder = (SupplyViewHolder) convertView.getTag();
            if (viewHolder == null) {
                viewHolder = new SupplyViewHolder();
                viewHolder.code = (TextView) convertView.findViewById(R.id.code);
                viewHolder.title = (TextView) convertView.findViewById(R.id.title);
                viewHolder.member = (ImageButton) convertView.findViewById(R.id.imageButtonMember);

                convertView.setTag(viewHolder);
            }

            viewHolder.code.setText(String.valueOf(((SuppliesModel.Title) getItem(position)).getCode()));
            viewHolder.title.setText(String.valueOf(((SuppliesModel.Title) getItem(position)).getTitle()));
            viewHolder.member.setImageResource(R.drawable.profil);

            viewHolder.member.setFocusable(false);
            viewHolder.member.setClickable(false);
            viewHolder.member.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SupplyDemandBean supplyDemandBean = daoFactory.getSupplyDemandDao().getSupplyDemand(((SuppliesModel.Title) getItem(position)).getCode());
                    MemberBean memberBean = supplyDemandBean.getMember();
                    Intent intent = new Intent(parent.getContext(), AMemberDisplayActivity.class);
                    intent.putExtra(ActivityConstant.KEY_INTENT_ID,memberBean.getId());
                    parent.getContext().startActivity(intent);
                }
            });

        } else if (type == ITEM_VIEW_TYPE_CATEGORY) {

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_supplydemand_category, parent, false);
            }

            SupplyViewHolder viewHolder = (SupplyViewHolder) convertView.getTag();
            if (viewHolder == null) {
                viewHolder = new SupplyViewHolder();
                viewHolder.category = (TextView) convertView.findViewById(R.id.category);
                convertView.setTag(viewHolder);
            }

            viewHolder.category.setText((String) getItem(position));

        }

        return convertView;

    }

    private class SupplyViewHolder {
        public TextView code;
        public TextView title;
        public TextView category;
        public ImageButton member;
    }

}