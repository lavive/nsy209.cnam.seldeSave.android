package nsy209.cnam.seldesave.models;

import java.util.List;
import java.util.Observable;

import nsy209.cnam.seldesave.bean.NotificationBean;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 06/06/17.
 */

public class NotificationModel extends Observable {

    private List<NotificationBean> notifications;

    /* affect values to attributes */
    public void onCreate(DaoFactory daoFactory){
        notifications = daoFactory.getMyProfileDao().getMyNotifications();

        setChanged();
        notifyObservers();
    }

    /* getters */

    public List<NotificationBean> getNotifications() {
        return notifications;
    }
}
