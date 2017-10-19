package nsy209.cnam.seldesave.validator;

import nsy209.cnam.seldesave.validator.helper.EnumCheck;

/**
 * Created by lavive on 08/06/17.
 */

public class PostalCodeValidator implements IValidator {
    private final String regex ="^((0[1-9])|([1-8][0-9])|(9[0-8])|(2A)|(2B))[0-9]{3}$";

    @Override
    public EnumCheck validate(String stringToValidate){
        if(!stringToValidate.matches(regex)){
            return EnumCheck.POSTAL_CODE__WELL_FORMED;
        }
        return null;
    }
}
