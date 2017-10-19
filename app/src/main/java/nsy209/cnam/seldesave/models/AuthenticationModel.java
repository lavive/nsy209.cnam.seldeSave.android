package nsy209.cnam.seldesave.models;

import android.content.Context;

import java.util.Observable;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.validator.helper.EnumCheck;
import nsy209.cnam.seldesave.validator.helper.EnumField;
import nsy209.cnam.seldesave.validator.helper.MapFieldCheck;

/**
 * Created by lavive on 27/09/17.
 */

public class AuthenticationModel extends Observable {

    private EnumCheck mobileIdError;

    private EnumCheck cellNumberError;

    private boolean waiting;

    private String messageInfo;

    /* initialize values */
    public void onCreate(Context context){

        mobileIdError = null;
        cellNumberError = null;
        waiting = false;
        messageInfo = "";

        setChanged();
        notifyObservers();

    }

    /* change values after input checking */
    public void onChange(String mobileIdValue, String cellNumberValue) {
        if (MapFieldCheck.getValidators(EnumField.MOBILE_ID).validate(mobileIdValue) != null) {
            this.mobileIdError = MapFieldCheck.getValidators(EnumField.MOBILE_ID).validate(mobileIdValue);
        } else {
            this.mobileIdError = null;
        }
        if (MapFieldCheck.getValidators(EnumField.CELL_NUMBER).validate(cellNumberValue) != null) {
            this.cellNumberError = MapFieldCheck.getValidators(EnumField.CELL_NUMBER).validate(cellNumberValue);
        } else {
            this.cellNumberError = null;
        }
        waiting = false;
        messageInfo = "";

        setChanged();
        notifyObservers();

    }

    public void onLookingForProfile(Context context){
        waiting = true;
        messageInfo = context.getString(R.string.look_for_profile);

        setChanged();
        notifyObservers();
    }

    public void onErrorLookingForProfile(Context context){
        waiting = false;
        messageInfo = context.getString(R.string.error_authentication);

        setChanged();
        notifyObservers();
    }

    public void onLookingForDatas(Context context){
        waiting = true;
        messageInfo = context.getString(R.string.retrieve_data);

        setChanged();
        notifyObservers();
    }

    public void onErrorLookingForDatas(Context context){
        waiting = false;
        messageInfo = context.getString(R.string.error_data);

        setChanged();
        notifyObservers();
    }

    /* getter and setter */

    public EnumCheck getMobileIdError() {
        return mobileIdError;
    }

    public void setMobileIdError(EnumCheck mobileIdError) {
        this.mobileIdError = mobileIdError;
    }

    public EnumCheck getCellNumberError() {
        return cellNumberError;
    }

    public void setCellNumberError(EnumCheck cellNumberError) {
        this.cellNumberError = cellNumberError;
    }

    public boolean isWaiting() {
        return waiting;
    }

    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }

    public String getMessageInfo() {
        return messageInfo;
    }

    public void setMessageInfo(String messageInfo) {
        this.messageInfo = messageInfo;
    }
}
