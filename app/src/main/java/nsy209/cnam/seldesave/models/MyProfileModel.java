package nsy209.cnam.seldesave.models;

import android.content.Context;

import java.util.Observable;

import nsy209.cnam.seldesave.bean.MyProfileBean;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 06/06/17.
 */

public class MyProfileModel extends Observable {

    private String forname;

    private String name;

    private String address;

    private String postalCode;

    private String town;

    private String email;

    private String cellNumber;

    private String phoneNumber;

    /* affect values to attributes */
    public void onCreate(Context context){
        MyProfileBean myProfileBean = DaoFactory.getInstance(context).getMyProfileDao().getMyProfile();

        forname = myProfileBean.getForname();
        name = myProfileBean.getName();
        address = myProfileBean.getAddress();
        postalCode = myProfileBean.getPostalCode();
        town = myProfileBean.getTown();
        email = myProfileBean.getEmail();
        cellNumber = myProfileBean.getCellNumber();
        phoneNumber = myProfileBean.getPhoneNumber();

        setChanged();
        notifyObservers();

    }

    /* getters */

    public String getForname() {
        return forname;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getTown() {
        return town;
    }

    public String getEmail() {
        return email;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
