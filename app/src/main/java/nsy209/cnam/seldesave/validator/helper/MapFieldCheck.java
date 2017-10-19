package nsy209.cnam.seldesave.validator.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nsy209.cnam.seldesave.validator.BigDecimalValidator;
import nsy209.cnam.seldesave.validator.EmailValidator;
import nsy209.cnam.seldesave.validator.EmptyValidator;
import nsy209.cnam.seldesave.validator.IValidator;
import nsy209.cnam.seldesave.validator.Less100Validator;
import nsy209.cnam.seldesave.validator.Less200Validator;
import nsy209.cnam.seldesave.validator.Less50Validator;
import nsy209.cnam.seldesave.validator.NumberValidator;
import nsy209.cnam.seldesave.validator.PhoneNumberValidator;
import nsy209.cnam.seldesave.validator.PostalCodeValidator;
import nsy209.cnam.seldesave.validator.Validators;

/**
 * Created by lavive on 08/06/17.
 */

public class MapFieldCheck {

    /* retrieve field checks */
    public static Validators getValidators(EnumField field){
        Validators validators = new Validators();
        for(EnumCheck check:getChecks(field)){
            validators.addValidator(MapCheckValidator.getValidator(check));
        }
        return validators;
    }

    /* helper methods */
    private static Map<EnumField,List<EnumCheck>> mapFieldCheck = new HashMap<EnumField,List<EnumCheck>>();

    private static List<EnumCheck> getChecks(EnumField field){
        return mapFieldCheck.get(field);
    }

    /* helper inner class */
    private static class MapCheckValidator{
        private static Map<EnumCheck,IValidator> mapCheckValidator = new HashMap<EnumCheck,IValidator>();
        public static IValidator getValidator(EnumCheck check) { return mapCheckValidator.get(check);}
        public static void putValidator(EnumCheck check,IValidator validator) { mapCheckValidator.put(check,validator);}
    }

    static{

        /* EnumField.FORNAME */
        List<EnumCheck> forname = new ArrayList<EnumCheck>();
        forname.add(EnumCheck.EMPTY);
        forname.add(EnumCheck.LESS_THAN_50);
        mapFieldCheck.put(EnumField.FORNAME,forname);

        /* EnumField.MOBILE_ID */
        List<EnumCheck> mobileId = new ArrayList<EnumCheck>();
        mobileId.add(EnumCheck.EMPTY);
        mobileId.add(EnumCheck.LESS_THAN_50);
        mapFieldCheck.put(EnumField.MOBILE_ID,mobileId);

        /* EnumField.NAME */
        List<EnumCheck> name = new ArrayList<EnumCheck>();
        name.add(EnumCheck.EMPTY);
        name.add(EnumCheck.LESS_THAN_50);
        mapFieldCheck.put(EnumField.NAME,name);

        /* EnumField.ADDRESS */
        List<EnumCheck> address = new ArrayList<EnumCheck>();
        address.add(EnumCheck.EMPTY);
        address.add(EnumCheck.LESS_THAN_100);
        mapFieldCheck.put(EnumField.ADDRESS,address);

        /* EnumField.POSTAL_CODE */
        List<EnumCheck> postalCode = new ArrayList<EnumCheck>();
        postalCode.add(EnumCheck.EMPTY);
        postalCode.add(EnumCheck.POSTAL_CODE__WELL_FORMED);
        mapFieldCheck.put(EnumField.POSTAL_CODE,postalCode);

        /* EnumField.TOWN */
        List<EnumCheck> town = new ArrayList<EnumCheck>();
        town.add(EnumCheck.EMPTY);
        town.add(EnumCheck.LESS_THAN_50);
        mapFieldCheck.put(EnumField.TOWN,town);

        /* EnumField.CELL_NUMBER */
        List<EnumCheck> cellNumber = new ArrayList<EnumCheck>();
        cellNumber.add(EnumCheck.EMPTY);
        cellNumber.add(EnumCheck.PHONE_NUMBER_WELL_FORMED);
        mapFieldCheck.put(EnumField.CELL_NUMBER,cellNumber);

        /* EnumField.EMAIL */
        List<EnumCheck> email = new ArrayList<EnumCheck>();
        email.add(EnumCheck.EMAIL_WELL_FORMED);
        mapFieldCheck.put(EnumField.EMAIL,email);

        /* EnumField.PHONE_NUMBER */
        List<EnumCheck> phoneNumber = new ArrayList<EnumCheck>();
        phoneNumber.add(EnumCheck.PHONE_NUMBER_WELL_FORMED);
        mapFieldCheck.put(EnumField.PHONE_NUMBER,phoneNumber);

        /* EnumField.TYPE */
        List<EnumCheck> type = new ArrayList<EnumCheck>();
        type.add(EnumCheck.EMPTY);
        mapFieldCheck.put(EnumField.TYPE,type);

        /* EnumField.CATEGORY */
        List<EnumCheck> category = new ArrayList<EnumCheck>();
        category.add(EnumCheck.EMPTY);
        mapFieldCheck.put(EnumField.CATEGORY,category);

        /* EnumField.TITLE */
        List<EnumCheck> title = new ArrayList<EnumCheck>();
        title.add(EnumCheck.EMPTY);
        title.add(EnumCheck.LESS_THAN_200);
        mapFieldCheck.put(EnumField.TITLE,title);

        /* EnumField.DISTANCE */
        List<EnumCheck> distance = new ArrayList<EnumCheck>();
        distance.add(EnumCheck.NUMBER_WELL_FORMED);
        mapFieldCheck.put(EnumField.DISTANCE,distance);

        /* EnumField.AMOUNT */
        List<EnumCheck> amount = new ArrayList<EnumCheck>();
        amount.add(EnumCheck.EMPTY);
        amount.add(EnumCheck.BIGDECIMAL_WELL_FORMED);
        mapFieldCheck.put(EnumField.AMOUNT,amount);


        /* MapCheckValidator */
        MapCheckValidator.putValidator(EnumCheck.EMPTY,new EmptyValidator());
        MapCheckValidator.putValidator(EnumCheck.LESS_THAN_200,new Less200Validator());
        MapCheckValidator.putValidator(EnumCheck.LESS_THAN_100,new Less100Validator());
        MapCheckValidator.putValidator(EnumCheck.LESS_THAN_50,new Less50Validator());
        MapCheckValidator.putValidator(EnumCheck.BIGDECIMAL_WELL_FORMED,new BigDecimalValidator());
        MapCheckValidator.putValidator(EnumCheck.POSTAL_CODE__WELL_FORMED,new PostalCodeValidator());
        MapCheckValidator.putValidator(EnumCheck.PHONE_NUMBER_WELL_FORMED,new PhoneNumberValidator());
        MapCheckValidator.putValidator(EnumCheck.EMAIL_WELL_FORMED,new EmailValidator());
        MapCheckValidator.putValidator(EnumCheck.NUMBER_WELL_FORMED,new NumberValidator());
    }
}
