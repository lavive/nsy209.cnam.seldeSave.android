package nsy209.cnam.seldesave.background.helper;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 11/07/2017.
 */


public class BaseToUpdate implements IBaseToUpdate {

    private List<IBaseToUpdate> baseToUpdateList = new ArrayList<IBaseToUpdate>();

    private DaoFactory daoFactory;

    public BaseToUpdate(List<String> tables, Activity activity, DaoFactory daoFactory){
        this.daoFactory = daoFactory;
        long remoteId = this.daoFactory.getMyProfileDao().getMyProfile().getRemote_id();
        for(String table:tables){
            if(table.equals(BackgroundConstant.ASSOCIATION_TABLE)){
                baseToUpdateList.add(new AssociationToUpdate(activity,this.daoFactory));
            }else if(table.equals(BackgroundConstant.CATEGORY_TABLE)){
                baseToUpdateList.add(new CategoryToUpdate(activity,this.daoFactory));
            }else if(table.equals(BackgroundConstant.MEMBER_TABLE)){
                baseToUpdateList.add(new MemberToUpdate(activity,this.daoFactory));
            }else if(table.equals(BackgroundConstant.NOTIFICATION_TABLE)){
                baseToUpdateList.add(new NotificationToUpdate(activity,this.daoFactory,remoteId));
            }else if(table.equals(BackgroundConstant.SUPPLYDEMAND_TABLE)){
                baseToUpdateList.add(new SupplyDemandToUpdate(activity,this.daoFactory));
            }else if(table.equals(BackgroundConstant.WEALTHSHEET_TABLE)){
                baseToUpdateList.add(new WealthSheetToUpdate(activity,this.daoFactory,remoteId));
            }else if(table.equals(BackgroundConstant.MY_SUPPLYDEMAND_TABLE)){
                baseToUpdateList.add(new SupplyDemandToUpdate(activity,this.daoFactory));
            }
            else if(table.equals(BackgroundConstant.NOTIFICATION_BUFFER_TABLE)){
                baseToUpdateList.add(new NotificationToRemoteDelete(activity,this.daoFactory,remoteId));
            }
            else if(table.equals(BackgroundConstant.NEW_TRANSACTION_TABLE)){
                baseToUpdateList.add(new WealthSheetToUpdate(activity,this.daoFactory,remoteId));
            }
        }
        if(!tables.isEmpty() ){
            baseToUpdateList.add(new DateToRemoteUpdate(activity,remoteId));
        }
    }

    @Override
    public void update(){
        this.daoFactory.open();
        for(IBaseToUpdate iBaseToUpdate:baseToUpdateList){
            iBaseToUpdate.update();
        }
        this.daoFactory.close();
    }

}
