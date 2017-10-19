package nsy209.cnam.seldesave.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.bean.MemberBean;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 30/04/17.
 */

public class MembersFilterAdapter extends ArrayAdapter<MemberBean> {

    /* categories checked */
    private List<MemberBean> membersBeanChecked;


    public MembersFilterAdapter(Context context, List<MemberBean> membersBean, DaoFactory daoFactory) {
        super(context, 0, membersBean);
        this.membersBeanChecked = daoFactory.getMyProfileDao().getMyFilter().getMembersFilter();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_filter_member,parent, false);
        }

        MemberViewHolder viewHolder = (MemberViewHolder) convertView.getTag();

        if (viewHolder == null) {
            viewHolder = new MemberViewHolder();
            viewHolder.forname = (TextView) convertView.findViewById(R.id.fornameMember);
            viewHolder.name = (TextView) convertView.findViewById(R.id.nameMember);
            viewHolder.town = (TextView) convertView.findViewById(R.id.townMember);
            viewHolder.checkMember = (CheckBox) convertView.findViewById(R.id.checkMember);
            convertView.setTag(viewHolder);
        }

        final MemberBean memberBean = getItem(position);

        viewHolder.forname.setText(memberBean.getForname());
        viewHolder.name.setText(memberBean.getName());
        viewHolder.town.setText(memberBean.getTown());
        if(membersCheckedContains(memberBean)) {
            viewHolder.checkMember.setChecked(true);
        } else {
            viewHolder.checkMember.setChecked(false);
        }

        final CheckBox checkBoxProv = viewHolder.checkMember;
        checkBoxProv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxProv.isChecked()) {
                    if (!membersCheckedContains(memberBean)) {
                        membersBeanChecked.add(memberBean);
                    }
                } else {
                    if(membersCheckedContains(memberBean)){
                        membersBeanChecked.remove(getMemberFrom(memberBean));
                    }
                }
            }
        });

        return convertView;

    }

    /* getter */

    public List<MemberBean> getMembersBeanChecked() {
        return membersBeanChecked;
    }

    /* helper method */
    private boolean membersCheckedContains(MemberBean memberBean){
        for(MemberBean memberBeanChecked:membersBeanChecked){
            if(memberBeanChecked.getForname().equals(memberBean.getForname()) &&
                    memberBeanChecked.getName().equals(memberBean.getName()) &&
                    memberBeanChecked.getAddress().equals(memberBean.getAddress()) &&
                    memberBeanChecked.getPostalCode().equals(memberBean.getPostalCode()) &&
                    memberBeanChecked.getTown().equals(memberBean.getTown())){
                return true;
            }
        }
        return false;
    }

    private MemberBean getMemberFrom(MemberBean memberBean){

        for(MemberBean memberBeanChecked:membersBeanChecked){
            if(memberBeanChecked.getForname().equals(memberBean.getForname()) &&
                    memberBeanChecked.getName().equals(memberBean.getName()) &&
                    memberBeanChecked.getAddress().equals(memberBean.getAddress()) &&
                    memberBeanChecked.getPostalCode().equals(memberBean.getPostalCode()) &&
                    memberBeanChecked.getTown().equals(memberBean.getTown())){
                return memberBeanChecked;
            }
        }
        return null;
    }

    private class MemberViewHolder {
        public TextView forname;
        public TextView name;
        public TextView town;
        public CheckBox checkMember;
    }
}

