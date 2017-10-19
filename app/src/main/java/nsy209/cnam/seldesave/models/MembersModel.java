package nsy209.cnam.seldesave.models;

import android.content.Context;
import java.util.List;
import java.util.Observable;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.bean.MemberBean;
import nsy209.cnam.seldesave.bean.helper.ApplyFilterBuilder;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 06/06/17.
 */

public class MembersModel extends Observable {

    private List<MemberBean> members;

    private String applyFilter;

    /* affect values to attributes */
    public void onCreate(Context context, DaoFactory daoFactory, boolean applyFilters){
        daoFactory.open();
        if(applyFilters){
            this.applyFilter = context.getString(R.string.disable_filters_button);
            ApplyFilterBuilder filterBuilder = new ApplyFilterBuilder(daoFactory.getMemberDao().getAllMembers(),daoFactory);
            members = filterBuilder.apply(daoFactory.getMyProfileDao().getMyFilter().isCategoriesChecked(),
                    daoFactory.getMyProfileDao().getMyFilter().isMembersChecked(),
                    daoFactory.getMyProfileDao().getMyFilter().isDistanceChecked())
                    .getMembersFiltered();
        } else {
            this.applyFilter = context.getString(R.string.apply_filters_button);

            members = daoFactory.getMemberDao().getAllMembers();
        }

        setChanged();
        notifyObservers();
    }

    /* getters */

    public List<MemberBean> getMembers() {
        return members;
    }

    public String getApplyFilter() {
        return applyFilter;
    }
}
