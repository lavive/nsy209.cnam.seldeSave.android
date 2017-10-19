package nsy209.cnam.seldesave.activity.utils;

import android.content.Context;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.bean.helper.EnumSupplyDemand;

/**
 * Created by lavive on 11/10/17.
 */

public class TypeSupplyDemand {

    /* to get Enum supply demand by locale wording */
    public static EnumSupplyDemand getByWording(Context context,String type){
        if(type.equals(context.getString(R.string.enum_demand))){
            return EnumSupplyDemand.DEMAND;
        } else if(type.equals(context.getString(R.string.enum_supply))){
            return EnumSupplyDemand.SUPPLY;
        }
        return null;
    }
}
