package nsy209.cnam.seldesave.dao.enumTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lavive on 02/06/17.
 */

public enum EnumNotificationTable {

    COLUMN_ID("_id","integer primary key autoincrement"),
    COLUMN_REMOTE_ID("remote_id","integer not null"),
    COLUMN_TITLE("title","text not null"),
    COLUMN_TEXT("text","text not null"),
    COLUMN_ORIGIN("origin_id","integer not null"),
    COLUMN_CATEGORY("category","text not null"),
    COLUMN_READ("read","integer not null");

    /* attributes */
    private String columnName;
    private String typeSQL;

    /* table name */
    private static final String TABLE_NOTIFICATION = "notifications";
    private static final String TABLE_NOTIFICATION_BUFFER = "notifications_buffer";

    EnumNotificationTable(String columnName,String typeSQL){
        this.columnName = columnName;
        this.typeSQL = typeSQL;
    }

    /* all columns */
    public static String[] getAllColumns(){
        List<String> columns = new ArrayList<String>();
        for(EnumNotificationTable enumTable:EnumNotificationTable.values()){
            columns.add(enumTable.getColumnName());
        }
        return columns.toArray(new String[0]);
    }

    /* table creation command */
    public static String getCreateCommand(){
        String command = "create table "
                + TABLE_NOTIFICATION +"(";
        for(EnumNotificationTable enumTable:EnumNotificationTable.values()){
            command += enumTable.getColumnName() + " " + enumTable.getTypeSQL() + ", ";
        }
        //delete the last ', '
        command = command.substring(0,command.length()-2);
        command += ");";

        return command;

    }

    public static String getCreateCommandBuffer(){
        String command = "create table "
                + TABLE_NOTIFICATION_BUFFER +"(";
        for(EnumNotificationTable enumTable:EnumNotificationTable.values()){
            command += enumTable.getColumnName() + " " + enumTable.getTypeSQL() + ", ";
        }
        //delete the last ', '
        command = command.substring(0,command.length()-2);
        command += ");";

        return command;

    }


    /* getter */
    public static String getTableName(){
        return TABLE_NOTIFICATION;
    }

    public static String getTableNameBuffer(){
        return TABLE_NOTIFICATION_BUFFER;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getTypeSQL() {
        return typeSQL;
    }
}
