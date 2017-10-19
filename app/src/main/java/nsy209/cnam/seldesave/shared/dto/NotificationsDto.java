package nsy209.cnam.seldesave.shared.dto;

import com.squareup.moshi.Json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lavive on 23/09/17.
 */

public class NotificationsDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Json(name ="notifications")
    private List<NotificationDto> notifications = new ArrayList<NotificationDto>();

    /* getter and setter */

    public List<NotificationDto> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationDto> notifications) {
        this.notifications = notifications;
    }

    @Override
    public String toString(){
        String result ="{ ";
        for(NotificationDto notificationDto:notifications){
            result += notificationDto.toString()+" , ";
        }
        result = result.substring(0,result.length()-1);
        result +="}";
        return result;
    }
}
