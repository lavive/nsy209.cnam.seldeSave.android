package nsy209.cnam.seldesave.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.bean.MemberBean;

/**
 * Created by lavive on 11/06/17.
 */

public class MembersPaymentAdapter extends ArrayAdapter<MemberBean> {



    public MembersPaymentAdapter(Context context, List<MemberBean> membersBean) {
        super(context, 0, membersBean);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_payment_member,parent, false);
        }

        MemberViewHolder viewHolder = (MemberViewHolder) convertView.getTag();

        if (viewHolder == null) {
            viewHolder = new MemberViewHolder();
            viewHolder.forname = (TextView) convertView.findViewById(R.id.fornameMember);
            viewHolder.name = (TextView) convertView.findViewById(R.id.nameMember);
            viewHolder.town = (TextView) convertView.findViewById(R.id.townMember);
            convertView.setTag(viewHolder);
        }

        MemberBean memberBean = getItem(position);

        viewHolder.forname.setText(memberBean.getForname());
        viewHolder.name.setText(memberBean.getName());
        viewHolder.town.setText(memberBean.getTown());
        return convertView;

    }

    private class MemberViewHolder {
        public TextView forname;
        public TextView name;
        public TextView town;
    }
}

