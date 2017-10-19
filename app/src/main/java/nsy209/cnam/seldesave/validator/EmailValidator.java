package nsy209.cnam.seldesave.validator;

import nsy209.cnam.seldesave.validator.helper.EnumCheck;

/**
 * Created by lavive on 08/06/17.
 */

public class EmailValidator implements IValidator {
    private final String regex ="^[a-z0-9]+(.[a-z0-9]+)*@[a-z0-9]+(\\.[a-z0-9]+)*(\\.[a-z]{2,4})$";

    @Override
    public EnumCheck validate(String stringToValidate){
        if(!stringToValidate.matches(regex)){
            return EnumCheck.EMAIL_WELL_FORMED;
        }
        return null;
    }
}
