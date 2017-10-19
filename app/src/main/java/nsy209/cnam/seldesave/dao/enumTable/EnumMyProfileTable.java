package nsy209.cnam.seldesave.dao.enumTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lavive on 02/06/17.
 */

public enum EnumMyProfileTable {

    COLUMN_ID("_id","integer primary key autoincrement"),
    COLUMN_REMOTE_ID("remote_id","integer not null"),
    COLUMN_ID_SUPPLYDEMAND("supplyDemand_id","integer not null"),
    COLUMN_ID_NOTIFICATION("notification_id","integer not null");

    /* attributes */
    private String columnName;
    private String typeSQL;

    /* table name */
    private static final String TABLE_MYPROFILE = "myProfile";

    EnumMyProfileTable(String columnName,String typeSQL){
        this.columnName = columnName;
        this.typeSQL = typeSQL;
    }

    /* all columns */
    public static String[] getAllColumns(){
        List<String> columns = new ArrayList<String>();
        for(EnumMyProfileTable enumTable:EnumMyProfileTable.values()){
            columns.add(enumTable.getColumnName());
        }
        return columns.toArray(new String[0]);
    }

    /* table creation command */
    public static String getCreateCommand(){
        String command = "create table "
                + TABLE_MYPROFILE +"(";
        for(EnumMyProfileTable enumTable:EnumMyProfileTable.values()){
            command += enumTable.getColumnName() + " " + enumTable.getTypeSQL() + ", ";
        }
        //delete the last ', '
        command = command.substring(0,command.length()-2);
        command += ");";

        return command;

    }


    /* getter */
    public static String getTableName(){
        return TABLE_MYPROFILE;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getTypeSQL() {
        return typeSQL;
    }
}
