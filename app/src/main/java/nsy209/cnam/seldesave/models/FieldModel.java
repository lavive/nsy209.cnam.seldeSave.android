package nsy209.cnam.seldesave.models;

import java.util.Observable;

import nsy209.cnam.seldesave.validator.helper.EnumCheck;
import nsy209.cnam.seldesave.validator.helper.EnumField;
import nsy209.cnam.seldesave.validator.helper.MapFieldCheck;

/**
 * Created by lavive on 07/06/17.
 */

public class FieldModel extends Observable {

    private int fieldId;

    private String fieldValue;

    private EnumCheck errorCheck;

    /* affect values to attributes */
    public void onCreate(String field, String fieldValue) {
        this.fieldId = EnumField.getByWording(field).getStringXMLId();
        this.fieldValue = fieldValue;
        this.errorCheck = null;

        setChanged();
        notifyObservers();
    }

    /* affect values to attributes */
    public void onChange(EnumField enumfield, String fieldValue) {
        this.fieldValue = fieldValue;
        if (MapFieldCheck.getValidators(enumfield).validate(fieldValue) != null) {
            this.errorCheck = MapFieldCheck.getValidators(enumfield).validate(fieldValue);
        } else {
            this.errorCheck = null;
        }

        setChanged();
        notifyObservers();

    }

    /* getters */

    public int getFieldId() {
        return fieldId;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public EnumCheck getErrorCheck() {
        return errorCheck;
    }
}
