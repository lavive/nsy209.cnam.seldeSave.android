package nsy209.cnam.seldesave.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.bean.NotificationBean;

/**
 * Created by lavive on 07/06/17.
 */

public class NotificationsAdapter extends ArrayAdapter<NotificationBean> {

    /* VIEW TYPE */
    private static final int ITEM_VIEW_TYPE_OLD = 0;
    private static final int ITEM_VIEW_TYPE_NEW = 1;
    private static final int ITEM_VIEW_TYPE_COUNT = 2;

    /* List to adapt */
    private List<NotificationBean> notificationsBean;

    /* list of data checked */
    final private List<NotificationBean> notificationsBeanChecked = new ArrayList<NotificationBean>();
    private List<Long> ids;


    public NotificationsAdapter(Context context, List<NotificationBean> notifications,final List<Long> ids) {
        super(context, 0, notifications);
        this.notificationsBean = notifications;
        if(ids != null) {
            this.ids = ids;
            for (NotificationBean notificationBean : notificationsBean) {
                if (ids.contains(Long.valueOf(notificationBean.getId()))) {
                    notificationsBeanChecked.add(notificationBean);
                }
            }
        }
    }

    @Override
    public int getCount() {
        return notificationsBean.size();
    }

    @Override
    public NotificationBean getItem(int position) {
        return notificationsBean.get(position);
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
        NotificationBean notificationBean = notificationsBean.get(position);
        if (notificationBean.isRead() )
            return ITEM_VIEW_TYPE_OLD;
        else if (!notificationBean.isRead() )
            return ITEM_VIEW_TYPE_NEW;

        return -1;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final int type = getItemViewType(position);

        if (type == ITEM_VIEW_TYPE_OLD) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_notification,parent, false);
            }

            NotificationViewHolder viewHolder = (NotificationViewHolder) convertView.getTag();

            if (viewHolder == null) {
                viewHolder = new NotificationViewHolder();
                viewHolder.subject = (TextView) convertView.findViewById(R.id.subject);
                viewHolder.subjectText = (TextView) convertView.findViewById(R.id.subjectText);
                viewHolder.body = (TextView) convertView.findViewById(R.id.body);
                viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
                convertView.setTag(viewHolder);
            }

            NotificationBean notificationBean = getItem(position);

            viewHolder.subject.setText(R.string.subject);
            viewHolder.subjectText.setText(notificationBean.getTitle());
            viewHolder.body.setText(notificationBean.getText());
            if(ids != null && ids.contains(getItem(position).getId())){
                viewHolder.checkBox.setChecked(true);
            }else {
                viewHolder.checkBox.setChecked(false);
            }
            final CheckBox checkBoxItem = viewHolder.checkBox;
            checkBoxItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NotificationBean notificationBean = getItem(position);
                    if(checkBoxItem.isChecked()){
                        if(!notificationsBeanChecked.contains(notificationBean)){
                            notificationsBeanChecked.add(notificationBean);
                        }
                    } else {
                        if(notificationsBeanChecked.contains(notificationBean)){
                            notificationsBeanChecked.remove(notificationBean);
                        }
                    }
                }
            });

        } else if (type == ITEM_VIEW_TYPE_NEW) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_notification_new, parent, false);
            }

            NotificationViewHolder viewHolder = (NotificationViewHolder) convertView.getTag();

            if (viewHolder == null) {
                viewHolder = new NotificationViewHolder();
                viewHolder.subject = (TextView) convertView.findViewById(R.id.subject);
                viewHolder.subjectText = (TextView) convertView.findViewById(R.id.subjectText);
                viewHolder.body = (TextView) convertView.findViewById(R.id.body);
                viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
                convertView.setTag(viewHolder);
            }

            NotificationBean notificationBean = getItem(position);

            viewHolder.subject.setText(R.string.subject);
            viewHolder.subjectText.setText(notificationBean.getTitle());
            viewHolder.body.setText(notificationBean.getText());
            if(ids != null && ids.contains(getItem(position).getId())){
                viewHolder.checkBox.setChecked(true);
            }else {
                viewHolder.checkBox.setChecked(false);
            }
            final CheckBox checkBoxItem = viewHolder.checkBox;
            checkBoxItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NotificationBean notificationBean = getItem(position);
                   if(checkBoxItem.isChecked()){
                        if(!notificationsBeanChecked.contains(notificationBean)){
                            notificationsBeanChecked.add(notificationBean);
                        }
                    } else {
                        if(notificationsBeanChecked.contains(notificationBean)){
                            notificationsBeanChecked.remove(notificationBean);
                        }
                    }
                }
            });
        }

        return convertView;

    }

    /* getter */

    public List<NotificationBean> getNotificationsBeanChecked() {
        return notificationsBeanChecked;
    }

    public List<NotificationBean> getNotificationsBean() {
        return notificationsBean;
    }

    private class NotificationViewHolder {
        public TextView subject;
        public TextView subjectText;
        public TextView body;
        public CheckBox checkBox;
    }
}

