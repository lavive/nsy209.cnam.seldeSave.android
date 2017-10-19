package nsy209.cnam.seldesave.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.utils.Connected;
import nsy209.cnam.seldesave.background.helper.MySupplyDemandToRemoteDelete;
import nsy209.cnam.seldesave.bean.helper.EnumSupplyDemand;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.models.MySuppliesDemandsModel;

/**
 * Created by lavive on 01/05/17.
 */

public class MySupplyDemandAdapter extends BaseAdapter {

    /* VIEW TYPE */
    private static final int ITEM_VIEW_TYPE_TYPE = 0;
    private static final int ITEM_VIEW_TYPE_CATEGORY = 1;
    private static final int ITEM_VIEW_TYPE_MAIN = 2;
    private static final int ITEM_VIEW_TYPE_COUNT = 3;

    /* DaoFactory */
    private DaoFactory daoFactory;

    /* List to adapt */
    private List<Object> suppliesDemands;

    /* message well deleted */
    private String messageDeleted;

    /* context */
    private Context context;


    public MySupplyDemandAdapter(Context context, List<Object> suppliesDemands, DaoFactory daoFactory, String message) {
        super();
        this.suppliesDemands = suppliesDemands;
        this.context = context;
        this.daoFactory = daoFactory;
        this.messageDeleted = message;
    }

    @Override
    public int getCount() {
        return this.suppliesDemands.size();
    }

    @Override
    public Object getItem(int position) {
        return this.suppliesDemands.get(position);
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
        if (this.suppliesDemands.get(position) instanceof EnumSupplyDemand)
            return ITEM_VIEW_TYPE_TYPE;
        else if (this.suppliesDemands.get(position) instanceof String)
            return ITEM_VIEW_TYPE_CATEGORY;
        else if (this.suppliesDemands.get(position) instanceof MySuppliesDemandsModel.Title)
            return ITEM_VIEW_TYPE_MAIN;

        return -1;
    }

    @Override
    public boolean isEnabled(int position) {
        return getItemViewType(position) != ITEM_VIEW_TYPE_TYPE && getItemViewType(position) != ITEM_VIEW_TYPE_CATEGORY;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final int type = getItemViewType(position);

        if (type == ITEM_VIEW_TYPE_MAIN) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_my_supplydemand_main, parent, false);
            }

            MySupplyDemandViewHolder viewHolder = (MySupplyDemandViewHolder) convertView.getTag();
            if (viewHolder == null) {
                viewHolder = new MySupplyDemandViewHolder();
                viewHolder.code = (TextView) convertView.findViewById(R.id.code);
                viewHolder.title = (TextView) convertView.findViewById(R.id.title);
                viewHolder.delete = (ImageButton) convertView.findViewById(R.id.imageButtonDelete);
                convertView.setTag(viewHolder);
            }

            viewHolder.code.setText(String.valueOf(((MySuppliesDemandsModel.Title) getItem(position)).getCode()));
            viewHolder.title.setText(String.valueOf(((MySuppliesDemandsModel.Title) getItem(position)).getTitle()));
            viewHolder.delete.setImageResource(R.drawable.delete);

            viewHolder.delete.setFocusable(false);
            viewHolder.delete.setClickable(false);
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /* delete supply demand from view */
                    long titleCode = ((MySuppliesDemandsModel.Title) getItem(position)).getCode();
                    long remoteId = ((MySuppliesDemandsModel.Title) getItem(position)).getRemoteId();
                    String title = ((MySuppliesDemandsModel.Title) getItem(position)).getTitle();
                    suppliesDemands.remove(getItem(position));
                    updateTypeAndCategory();
                    /* delete supply demand from local database */
                    MySupplyDemandAdapter.this.notifyDataSetChanged();
                    daoFactory.open();
                    daoFactory.getMyProfileDao().deleteSupplyDemand(titleCode);
                    /* delete supply demand from server */
                    if(Connected.isConnectedInternet((Activity) context)){
                        MySupplyDemandToRemoteDelete mySupplyDemandToRemoteDelete = new MySupplyDemandToRemoteDelete((Activity) context,daoFactory,remoteId);
                        mySupplyDemandToRemoteDelete.update();
                    }
                    Toast.makeText(context,title+" "+messageDeleted,Toast.LENGTH_LONG).show();
                }
            });

        } else if (type == ITEM_VIEW_TYPE_TYPE) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_my_supplydemand_type, parent, false);
            }

            MySupplyDemandViewHolder viewHolder = (MySupplyDemandViewHolder) convertView.getTag();
            if (viewHolder == null) {
                viewHolder = new MySupplyDemandViewHolder();
                viewHolder.type = (TextView) convertView.findViewById(R.id.type);
                convertView.setTag(viewHolder);
            }

            viewHolder.type.setText(((EnumSupplyDemand) getItem(position)).getWording());

        } else if (type == ITEM_VIEW_TYPE_CATEGORY) {

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_supplydemand_category, parent, false);
            }

            MySupplyDemandViewHolder viewHolder = (MySupplyDemandViewHolder) convertView.getTag();
            if (viewHolder == null) {
                viewHolder = new MySupplyDemandViewHolder();
                viewHolder.category = (TextView) convertView.findViewById(R.id.category);
                convertView.setTag(viewHolder);
            }

            viewHolder.category.setText((String) getItem(position));

        }

        return convertView;

    }

    /* helper method */
    private void updateTypeAndCategory(){
        /* get positions categories to remove (if no title corresponding) */
        List<Integer> positionsCategoriesToRemove = new ArrayList<Integer>();
        int i = 0;
        for(Object object:suppliesDemands){
            if(object instanceof String ){
                if( i < suppliesDemands.size()-1) {
                    Object nextobject = suppliesDemands.get(i + 1);
                    if (nextobject instanceof String || nextobject instanceof EnumSupplyDemand) {
                        positionsCategoriesToRemove.add(i);
                    }
                } else {
                    positionsCategoriesToRemove.add(i);
                }
            }
            i++;
        }
        /* remove categories */
        Iterator<Object> iteratorCategory = suppliesDemands.iterator();
        i = 0;
        while ( iteratorCategory.hasNext() ) {
            Object o = iteratorCategory.next();
            if (positionsCategoriesToRemove.contains(i)) {
                iteratorCategory.remove();
            }
            i++;
        }
        /* get positions Type to remove (if no title and category corresponding */
        List<Integer> positionsTypesToRemove = new ArrayList<Integer>();
        i = 0;
        for(Object object:suppliesDemands){
            if(object instanceof EnumSupplyDemand){
                if(i < suppliesDemands.size()-1) {
                    Object nextobject = suppliesDemands.get(i + 1);
                    if (nextobject instanceof EnumSupplyDemand) {
                        positionsTypesToRemove.add(i);
                    }
                } else {
                    positionsTypesToRemove.add(i);
                }
            }
            i++;
        }
        /* remove types */
        Iterator<Object> iteratorType = suppliesDemands.iterator();
        i = 0;
        while ( iteratorType.hasNext() ) {
            Object o = iteratorType.next();
            if (positionsTypesToRemove.contains(i)) {
                iteratorType.remove();
            }
            i++;
        }
    }

    private class MySupplyDemandViewHolder {
        public TextView code;
        public TextView title;
        public ImageButton delete;
        public TextView type;
        public TextView category;
    }

}