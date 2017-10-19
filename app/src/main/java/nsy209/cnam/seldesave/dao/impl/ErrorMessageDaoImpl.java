package nsy209.cnam.seldesave.dao.impl;

import java.util.List;

import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.dao.ErrorMessageDao;
import nsy209.cnam.seldesave.dao.sharedPreferences.ErrorMessagePreferences;
import nsy209.cnam.seldesave.validator.helper.EnumCheck;

/**
 * Created by lavive on 08/06/17.
 */

public class ErrorMessageDaoImpl implements ErrorMessageDao {


    public static  ErrorMessageDao getInstance(DaoFactory daoFactory){

        return new ErrorMessageDaoImpl(daoFactory);

    }

    /* attribute */
    private DaoFactory daoFactory;

    private ErrorMessageDaoImpl(DaoFactory daoFactory){
        this.daoFactory = daoFactory;
    }


    @Override
    public void createErrorMessage(List<String> errorMessages){

        ErrorMessagePreferences.putEmpty(errorMessages.get(0));
        ErrorMessagePreferences.putLess200(errorMessages.get(1));
        ErrorMessagePreferences.putLess100(errorMessages.get(2));
        ErrorMessagePreferences.putLess50(errorMessages.get(3));
        ErrorMessagePreferences.putBigDecimal(errorMessages.get(4));
        ErrorMessagePreferences.putPostalCode(errorMessages.get(5));
        ErrorMessagePreferences.putPhoneNumber(errorMessages.get(6));
        ErrorMessagePreferences.putEmail(errorMessages.get(7));
        ErrorMessagePreferences.putNumber(errorMessages.get(8));
    }

    @Override
    public String getErrorMessage(EnumCheck check){
        if(check == null) return "";
        if(check.equals(EnumCheck.EMPTY)) return ErrorMessagePreferences.getEmpty();
        if(check.equals(EnumCheck.LESS_THAN_200)) return ErrorMessagePreferences.getLess200();
        if(check.equals(EnumCheck.LESS_THAN_100)) return ErrorMessagePreferences.getLess100();
        if(check.equals(EnumCheck.LESS_THAN_50)) return ErrorMessagePreferences.getLess50();
        if(check.equals(EnumCheck.BIGDECIMAL_WELL_FORMED)) return ErrorMessagePreferences.getBigDecimal();
        if(check.equals(EnumCheck.POSTAL_CODE__WELL_FORMED)) return ErrorMessagePreferences.getPostalcode();
        if(check.equals(EnumCheck.PHONE_NUMBER_WELL_FORMED)) return ErrorMessagePreferences.getPhoneNumber();
        if(check.equals(EnumCheck.EMAIL_WELL_FORMED)) return ErrorMessagePreferences.getEmail();
        if(check.equals(EnumCheck.NUMBER_WELL_FORMED)) return ErrorMessagePreferences.getNumber();

        return "";
    }
}
