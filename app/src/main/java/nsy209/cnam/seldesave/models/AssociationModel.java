package nsy209.cnam.seldesave.models;

import android.content.Context;

import java.util.Observable;

import nsy209.cnam.seldesave.bean.AssociationBean;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 06/06/17.
 */

public class AssociationModel extends Observable {

    private String name;

    private String address;

    private String postalCode;

    private String town;

    private String email;

    private String cellNumber;

    private String phoneNumber;

    private String webSite;

    /* affect values to attributes */
    public void onCreate(Context context){
        AssociationBean associationBean = DaoFactory.getInstance(context).getAssociationDao().getAssociation();

        name = associationBean.getName();
        address = associationBean.getAddress();
        postalCode = associationBean.getPostalCode();
        town = associationBean.getTown();
        email = associationBean.getEmail();
        cellNumber = associationBean.getCellNumber();
        phoneNumber = associationBean.getPhoneNumber();
        webSite = associationBean.getWebSite();

        setChanged();
        notifyObservers();

    }

    /* getters */

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

    public String getWebSite() {
        return webSite;
    }
}
