package nsy209.cnam.seldesave.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.geolocation.AMemberDisplayActivity;
import nsy209.cnam.seldesave.activity.geolocation.GeolocationMemberActivity;
import nsy209.cnam.seldesave.bean.MemberBean;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 07/06/17.
 */

public class MembersAdapter extends ArrayAdapter<MemberBean> {

    /* daoFactory */
    private DaoFactory daoFactory;

    /* List to adapt */
    private List<MemberBean> membersBean;


    public MembersAdapter(Context context, List<MemberBean> membersBean,DaoFactory daoFactory) {
        super(context, 0, membersBean);
        this.membersBean = membersBean;
        this.daoFactory = daoFactory;
    }

    @Override
    public View getView(final int position, View convertView,final ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_member,parent, false);
            }

        MemberViewHolder viewHolder = (MemberViewHolder) convertView.getTag();

            if (viewHolder == null) {
                viewHolder = new MemberViewHolder();
                viewHolder.forname = (TextView) convertView.findViewById(R.id.fornameMember);
                viewHolder.name = (TextView) convertView.findViewById(R.id.nameMember);
                viewHolder.town = (TextView) convertView.findViewById(R.id.townMember);
                viewHolder.look = (ImageButton) convertView.findViewById(R.id.imageButtonlook);
                viewHolder.map = (ImageButton) convertView.findViewById(R.id.imageButtonMap);
                convertView.setTag(viewHolder);
            }

            final MemberBean memberBean = getItem(position);

            viewHolder.forname.setText(memberBean.getForname());
            viewHolder.name.setText(memberBean.getName());
            viewHolder.town.setText(memberBean.getTown());
            viewHolder.look.setImageResource(R.drawable.look);
            viewHolder.look.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MemberBean memberBean = (MemberBean) getItem(position);
                    Intent intent = new Intent(parent.getContext(), AMemberDisplayActivity.class);
                    intent.putExtra(ActivityConstant.KEY_INTENT_ID,memberBean.getId());
                    parent.getContext().startActivity(intent);

                }
            });
            viewHolder.map.setImageResource(R.drawable.map);
            viewHolder.map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(parent.getContext(), GeolocationMemberActivity.class);
                    long memberId = memberBean.getId();
                    intent.putExtra(ActivityConstant.KEY_INTENT_GEOLOCATION_NAME,daoFactory.getMemberDao().getGeolocation(memberId));
                    parent.getContext().startActivity(intent);
                }
            });

        return convertView;

    }

    /* getter */

    public List<MemberBean> getMembersBean() {
        return membersBean;
    }

    private class MemberViewHolder {
        public TextView forname;
        public TextView name;
        public TextView town;
        public ImageButton look;
        public ImageButton map;
    }
}

