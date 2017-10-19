package nsy209.cnam.seldesave.activity.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.List;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.filter.FilterActivity;
import nsy209.cnam.seldesave.activity.geolocation.GeolocationMembersActivity;
import nsy209.cnam.seldesave.activity.mysuppliesdemands.MySupplyDemandActivity;
import nsy209.cnam.seldesave.activity.notifications.MyNotificationsActivity;
import nsy209.cnam.seldesave.activity.payment.PaymentActivity;
import nsy209.cnam.seldesave.activity.profile.ProfileActivity;
import nsy209.cnam.seldesave.activity.suppliesdemand.DemandsActivity;
import nsy209.cnam.seldesave.activity.suppliesdemand.SuppliesActivity;
import nsy209.cnam.seldesave.bean.GeolocationBean;
import nsy209.cnam.seldesave.bean.MemberBean;
import nsy209.cnam.seldesave.bean.NotificationBean;
import nsy209.cnam.seldesave.bean.SupplyDemandBean;
import nsy209.cnam.seldesave.dao.DaoFactory;


/**
 * Created by lavive on 18/07/2017.
 *
 * All menu methods used by home activity
 */

public class MenuMethod {

    public static void goToProfile(Context context){
        context.startActivity(new Intent(context, ProfileActivity.class));
    }

    public static void goToMySupplyDemand(Context context){
        context.startActivity(new Intent(context, MySupplyDemandActivity.class));
    }

    public static void goToSupplyDemand(Context context, DaoFactory daoFactory){
        daoFactory.open();
        List<SupplyDemandBean> suppliesDemands = daoFactory.getSupplyDemandDao().getAllSuppliesDemands();
        if(suppliesDemands.isEmpty()){
            Toast.makeText(context,context.getString(R.string.error_my_supply_demand),Toast.LENGTH_LONG).show();
        }else {
            List<SupplyDemandBean> supplies = daoFactory.getSupplyDemandDao().getAllSupplies();
            if(supplies.isEmpty()){
                context.startActivity(new Intent(context, DemandsActivity.class));
            }else {
                context.startActivity(new Intent(context, SuppliesActivity.class));
            }
        }
    }

    public static void goToNotification(Context context, DaoFactory daoFactory){
        daoFactory.open();
        List<NotificationBean> notificationsBean = daoFactory.getMyProfileDao().getMyNotifications();
        if(notificationsBean.isEmpty()){
            Toast.makeText(context,context.getString(R.string.error_my_notification),Toast.LENGTH_LONG).show();
        } else {
            context.startActivity(new Intent(context, MyNotificationsActivity.class));
        }
    }

    public static void goToGeolocation(Context context, DaoFactory daoFactory){
        daoFactory.open();
        List<MemberBean> members = daoFactory.getMemberDao().getAllMembers();
        List<GeolocationBean> geolocations = daoFactory.getMemberDao().getAllGeolocation();
        if(members.isEmpty()){
            Toast.makeText(context,context.getString(R.string.error_member),Toast.LENGTH_LONG).show();
        }else if(geolocations.isEmpty()) {
            Toast.makeText(context, context.getString(R.string.geolocation_not_done), Toast.LENGTH_LONG).show();
        }
        else {
            Intent intent = new Intent(context, GeolocationMembersActivity.class);
            context.startActivity(intent);
        }
    }

    public static void goToFilter(Context context){
        context.startActivity(new Intent(context, FilterActivity.class));
    }

    public static void goToPayment(Context context){
        context.startActivity(new Intent(context, PaymentActivity.class));
    }
}
