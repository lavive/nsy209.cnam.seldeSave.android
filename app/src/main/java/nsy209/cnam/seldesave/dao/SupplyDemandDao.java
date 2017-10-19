package nsy209.cnam.seldesave.dao;

import java.util.List;

import nsy209.cnam.seldesave.bean.CategoryBean;
import nsy209.cnam.seldesave.bean.MemberBean;
import nsy209.cnam.seldesave.bean.SupplyDemandBean;

/**
 * Created by lavive on 01/06/17.
 */

public interface SupplyDemandDao {

    public List<SupplyDemandBean> getAllSuppliesDemands();

    public List<SupplyDemandBean> getAllSupplies();

    public List<SupplyDemandBean> getAllDemands();

    public List<SupplyDemandBean> getAllSuppliesDemands(MemberBean memberBean);

    public SupplyDemandBean getSupplyDemand(long id);

    public SupplyDemandBean getSupplyDemandByRemoteId(long remoteId);

    public List<CategoryBean> getAllCategories();

    public List<SupplyDemandBean> getSuppliesDemandsAbleToBePayed();

    public List<SupplyDemandBean> getMySuppliesDemands();

    /* for the Tests */
    public void addSuppliesDemands(List<SupplyDemandBean> supplyDemandBeanList);

    public void addMySuppliesDemands(List<SupplyDemandBean> supplyDemandBeanList);

    public void createSuppliesDemands(List<SupplyDemandBean> supplyDemandBeanList);

    public void createMySuppliesDemands(List<SupplyDemandBean> supplyDemandBeanList);

    public void createCategories(List<CategoryBean> categories);

    public void updateCategories(List<CategoryBean> categories);

    public void updateSuppliesDemands(List<SupplyDemandBean> supplyDemandBeanList);


}
