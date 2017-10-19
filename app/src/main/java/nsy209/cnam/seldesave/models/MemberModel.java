package nsy209.cnam.seldesave.models;


import java.util.Observable;

import nsy209.cnam.seldesave.bean.MemberBean;
import nsy209.cnam.seldesave.dao.DaoFactory;

import static android.R.attr.id;

/**
 * Created by lavive on 06/06/17.
 */

public class MemberModel extends Observable {

    private String forname;

    private String name;

    private String address;

    private String postalCode;

    private String town;

    private String email;

    private String cellNumber;

    private String phoneNumber;

    /* affect values to attributes */
    public void onCreate(DaoFactory daoFactory, long id){
        MemberBean memberBean = daoFactory.getMemberDao().getMember(id);

        forname = memberBean.getForname();
        name = memberBean.getName();
        address = memberBean.getAddress();
        postalCode = memberBean.getPostalCode();
        town = memberBean.getTown();
        email = memberBean.getEmail();
        cellNumber = memberBean.getCellNumber();
        phoneNumber = memberBean.getPhoneNumber();

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


