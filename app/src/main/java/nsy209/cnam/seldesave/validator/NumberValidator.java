package nsy209.cnam.seldesave.validator;

import nsy209.cnam.seldesave.validator.helper.EnumCheck;

/**
 * Created by lavive on 08/06/17.
 */

public class NumberValidator implements IValidator {
    private final String regex = "^([1-9][0-9]*)|0$";

    @Override
    public EnumCheck validate(String stringToValidate) {
        if (!stringToValidate.matches(regex)) {
            return EnumCheck.NUMBER_WELL_FORMED;
        }
        return null;
    }
}