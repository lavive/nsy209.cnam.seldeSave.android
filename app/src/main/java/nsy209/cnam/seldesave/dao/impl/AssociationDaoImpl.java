package nsy209.cnam.seldesave.dao.impl;

import nsy209.cnam.seldesave.bean.AssociationBean;
import nsy209.cnam.seldesave.dao.AssociationDao;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.dao.sharedPreferences.AssociationPreferences;

/**
 * Created by lavive on 02/06/17.
 */

public class AssociationDaoImpl implements AssociationDao {

    public static  AssociationDao getInstance(DaoFactory daoFactory){
        return new AssociationDaoImpl(daoFactory);
    }

    /* attribute */
    private DaoFactory daoFactory;

    private AssociationDaoImpl(DaoFactory daoFactory){
        this.daoFactory = daoFactory;
    }

    @Override
    public AssociationBean getAssociation(){
;
        AssociationBean association = new AssociationBean();
        association.setId(AssociationPreferences.getId());
        association.setRemote_id(AssociationPreferences.getRemoteId());
        association.setName(AssociationPreferences.getName());
        association.setAddress(AssociationPreferences.getAddress());
        association.setPostalCode(AssociationPreferences.getPostalcode());
        association.setTown(AssociationPreferences.getTown());
        association.setCellNumber(AssociationPreferences.getCellnumber());
        association.setEmail(AssociationPreferences.getEmail());
        association.setPhoneNumber(AssociationPreferences.getPhoneNumber());
        association.setWebSite(AssociationPreferences.getWebsite());

        return association;
    }

    @Override
    public void createAssociation(AssociationBean association){
        AssociationPreferences.putId(association.getId());
        AssociationPreferences.putRemoteId(association.getRemote_id());
        AssociationPreferences.putName(association.getName());
        AssociationPreferences.putAddress(association.getAddress());
        AssociationPreferences.putPostalCode(association.getPostalCode());
        AssociationPreferences.putTown(association.getTown());
        AssociationPreferences.putCellNumber(association.getCellNumber());
        AssociationPreferences.putEmail(association.getEmail());
        AssociationPreferences.putPhoneNumber(association.getPhoneNumber());
        AssociationPreferences.putWebSite(association.getWebSite());
    }

}
