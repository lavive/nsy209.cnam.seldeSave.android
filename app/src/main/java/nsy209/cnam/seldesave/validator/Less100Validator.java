package nsy209.cnam.seldesave.validator;

import android.content.Context;

import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.validator.helper.EnumCheck;

/**
 * Created by lavive on 08/06/17.
 */

public class Less100Validator implements IValidator {

    @Override
    public EnumCheck validate(String stringToValidate){
        if(stringToValidate.length() >= 100){
            return EnumCheck.LESS_THAN_100;
        }
        return null;
    }
}
