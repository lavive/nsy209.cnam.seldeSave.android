package nsy209.cnam.seldesave.background.helper;

/**
 * Created by lavive on 13/07/2017.
 */

public class BackgroundConstant {

    /* data */
    public static final long PERIOD_UPDATE = 1 * 20 * 1000;

    /* result code */
    public static final int SUCCESS_RESULT = 0;
    public static final int FAILURE_RESULT = 1;
    public static final int CODE_RESPONSE_OK = 200;
    public static final int CODE_RESPONSE_CREATED = 201;
    public static final int CODE_RESPONSE_NOT_CONTENT = 204;
    public static final int CODE_RESPONSE_NOT_FOUND = 404;

    /* result data from webService */
    public static final String RESULT_UPDATE_DATABASE = "result_update_database";
    public static final String RESULT_ASSOCIATION = "result_association";
    public static final String RESULT_MEMBER = "result_member";
    public static final String RESULT_SUPPLYDEMAND = "result_supplyDemand";
    public static final String RESULT_CATEGORY = "result_category";
    public static final String RESULT_NOTIFICATION = "result_notification";
    public static final String RESULT_WEALTHSHEET = "result_wealthsheet";
    public static final String RESULT_TRANSACTION = "result_transaction";
    public static final String RESULT_REMOTE = "result_remote";

    /* reference to receiver */
    public static final String RECEIVER_CHECK_DATABASE = "checkDataBase_receiver";
    public static final String RECEIVER_UPDATE_ASSOCIATION = "updateAssociation_receiver";
    public static final String RECEIVER_UPDATE_CATEGORY = "updateCategory_receiver";
    public static final String RECEIVER_UPDATE_MEMBER = "updateMember_receiver";
    public static final String RECEIVER_GEOCODE = "geocode_receiver";
    public static final String RECEIVER_UPDATE_NOTIFICATION = "updateNotification_receiver";
    public static final String RECEIVER_UPDATE_SUPPLYDEMAND = "updateSupplyDemand_receiver";
    public static final String RECEIVER_UPDATE_WEALTHSHEET = "updateWealthSheet_receiver";
    public static final String RECEIVER_REMOTE_MYPROFILE = "remote_receiver_my_profile";
    public static final String RECEIVER_REMOTE_MYSUPPLYDEMAND = "remote_receiver_my_supplydemand";
    public static final String RECEIVER_REMOTE_TRANSACTION= "remote_receiver_transaction";
    public static final String RECEIVER_REMOTE_NOTIFICATION= "remote_receiver_notification";
    public static final String RECEIVER_GET_PROFILE = "get_profile_receiver";

    /* reference to local table to update */
    public static final String ASSOCIATION_TABLE= "association_table";
    public static final String MEMBER_TABLE= "member_table";
    public static final String SUPPLYDEMAND_TABLE= "supplyDemand_table";
    public static final String CATEGORY_TABLE= "category_table";
    public static final String NOTIFICATION_TABLE= "notification_table";
    public static final String WEALTHSHEET_TABLE= "wealthSheet_table";
    public static final String MY_SUPPLYDEMAND_TABLE= "my_supplyDemand_table";
    public static final String NOTIFICATION_BUFFER_TABLE= "notification_buffer_table";
    public static final String NEW_TRANCACTION_TABLE= "new_transaction_table";

    /* reference to data to send or receive */
    public static final String MY_PROFILE_BEAN = "my_profile_bean";
    public static final String MY_SUPPLYDEMAND_BEAN = "my_supplydemand_bean";
    public static final String TRANSACTION_BEAN = "transaction_bean";
    public static final String NOTIFICATION_BEAN = "notification_bean";
    public static final String GEOLOCATION_BEAN = "geolocation_bean";
    public static final String MESSAGE = "message";
}
