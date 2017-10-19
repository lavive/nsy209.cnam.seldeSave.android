package nsy209.cnam.seldesave.validator;


import java.util.ArrayList;
import java.util.List;

import nsy209.cnam.seldesave.validator.helper.EnumCheck;

/**
 * Created by lavive on 08/06/17.
 */

public class Validators implements IValidator {

    private List<IValidator> validators = new ArrayList<IValidator>();

    public void addValidator(IValidator validator){
        validators.add(validator);
    }

    public void deleteValidator(IValidator validator){
        validators.remove(validator);
    }

    @Override
    public EnumCheck validate(String stringToValidate){
        for(IValidator validator:validators){
            if(validator.validate(stringToValidate) != null){
                return validator.validate(stringToValidate);
            }
        }
        return null;
    }

    /* getter */

    public List<IValidator> getValidators() {
        return validators;
    }
}
