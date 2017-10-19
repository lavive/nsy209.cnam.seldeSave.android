package nsy209.cnam.seldesave.validator.helper;

/**
 * Created by lavive on 08/06/17.
 */

public enum EnumCheck {
    EMPTY("empty"),
    LESS_THAN_200("less200"),
    LESS_THAN_100("less100"),
    LESS_THAN_50("less50"),
    BIGDECIMAL_WELL_FORMED("bigDecimalWellFormed"),
    POSTAL_CODE__WELL_FORMED("postalCodeWellFormed"),
    PHONE_NUMBER_WELL_FORMED("phoneNumberWellFormed"),
    EMAIL_WELL_FORMED("emailWellFormed"),
    NUMBER_WELL_FORMED("numberWellFormed");

    String wording;

    EnumCheck(String wording){
        this.wording = wording;
    }

    public String getWording(){
        return this.wording;
    }

    public static EnumCheck getByWording(String wording){
        for(EnumCheck enumAttribute : values()){
            if(enumAttribute.getWording().equals(wording))
                return enumAttribute;
        }

        return null;
    }
}