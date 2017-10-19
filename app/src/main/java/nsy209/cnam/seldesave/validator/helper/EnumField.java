package nsy209.cnam.seldesave.validator.helper;

import nsy209.cnam.seldesave.R;

/**
 * Created by lavive on 08/06/17.
 */

public enum EnumField {
    FORNAME("forname",R.string.forname),
    MOBILE_ID("mobile_id",R.string.mobile_id),
    NAME("name",R.string.name),
    ADDRESS("address",R.string.address),
    POSTAL_CODE("postal_code",R.string.postal_code),
    TOWN("town",R.string.town),
    CELL_NUMBER("cell_number",R.string.cell_number),
    EMAIL("email",R.string.email),
    PHONE_NUMBER("phone_number",R.string.phone_number),
    TYPE("type",0),
    CATEGORY("category",0),
    TITLE("title",0),
    DISTANCE("distance",0),
    AMOUNT("amount",0);

    String wording;
    int stringXMLId;

    EnumField(String wording,int id){
        this.wording = wording;
        this.stringXMLId = id;
    }

    public String getWording(){
        return this.wording;
    }

    public int getStringXMLId() {
        return stringXMLId;
    }

    public static EnumField getByWording(String wording){
        for(EnumField enumAttribute : values()){
            if(enumAttribute.getWording().equals(wording))
                return enumAttribute;
        }

        return null;
    }

}