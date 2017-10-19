package nsy209.cnam.seldesave.validator;

import nsy209.cnam.seldesave.validator.helper.EnumCheck;

/**
 * Created by lavive on 08/06/17.
 */

public class PhoneNumberValidator implements IValidator {
    private final String regex ="^0[1-9][0-9]{8}$";

    @Override
    public EnumCheck validate(String stringToValidate){
        if(!stringToValidate.matches(regex)){
            return EnumCheck.PHONE_NUMBER_WELL_FORMED;
        }
        return null;
    }
}
