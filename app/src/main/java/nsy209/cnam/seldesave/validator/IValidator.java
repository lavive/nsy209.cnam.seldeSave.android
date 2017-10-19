package nsy209.cnam.seldesave.validator;

import nsy209.cnam.seldesave.validator.helper.EnumCheck;

/**
 * Created by lavive on 08/06/17.
 */

public interface IValidator {

    public EnumCheck validate(String stringToValidate);
}
