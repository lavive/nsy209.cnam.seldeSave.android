package nsy209.cnam.seldesave.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import nsy209.cnam.seldesave.adapter.MembersAdapter;
import nsy209.cnam.seldesave.bean.MemberBean;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 16/06/17.
 */

public class MembersPaymentModel extends Observable {

    private List<MemberBean> members;

    public void onCreate(DaoFactory daoFactory,long supplyDemandId){
        members = daoFactory.getMemberDao().getMembersAbleToBePayed(supplyDemandId);

        setChanged();
        notifyObservers();
    }

    /* getters */

    public List<MemberBean> getMembers() {
        return members;
    }
}

