package nsy209.cnam.seldesave.dao;

import nsy209.cnam.seldesave.bean.AssociationBean;

/**
 * Created by lavive on 01/06/17.
 */

public interface AssociationDao {

    public AssociationBean getAssociation();

    public void createAssociation(AssociationBean association);
}
