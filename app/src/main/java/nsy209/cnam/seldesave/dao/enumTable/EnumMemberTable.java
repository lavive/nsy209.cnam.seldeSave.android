package nsy209.cnam.seldesave.dao.enumTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lavive on 02/06/17.
 */

public enum EnumMemberTable {

    COLUMN_ID("_id","integer primary key autoincrement"),
    COLUMN_REMOTE_ID("remote_id","integer not null"),
    COLUMN_NAME("name","text not null"),
    COLUMN_FORNAME("forname","text not null"),
    COLUMN_ADDRESS("adress","text not null"),
    COLUMN_POSTAL_CODE("postal_code","text not null"),
    COLUMN_TOWN("town","text not null"),
    COLUMN_CELLNUMBER("cellNumber","text not null"),
    COLUMN_EMAIL("email","text"),
    COLUMN_PHONENUMBER("phoneNumber","text");

    /* attributes */
    private String columnName;
    private String typeSQL;

    /* tables name */
    private static final String TABLE_MEMBER = "members";
    private static final String TABLE_MEMBER_FILTER = "members_filter";
    private static final String TABLE_PROFILE_BUFFER = "profile_buffer";

    EnumMemberTable(String columnName,String typeSQL){
        this.columnName = columnName;
        this.typeSQL = typeSQL;
    }

    /* all columns */
    public static String[] getAllColumns(){
        List<String> columns = new ArrayList<String>();
        for(EnumMemberTable enumTable:EnumMemberTable.values()){
            columns.add(enumTable.getColumnName());
        }
        return columns.toArray(new String[0]);
    }

    /* tables creation command */
    public static String getCreateCommand(){
        String command = "create table "
                + TABLE_MEMBER +"(";
        for(EnumMemberTable enumTable:EnumMemberTable.values()){
            command += enumTable.getColumnName() + " " + enumTable.getTypeSQL() + ", ";
        }
        //delete the last ', '
        command = command.substring(0,command.length()-2);
        command += ");";

        return command;

    }

    public static String getCreateCommandFilter(){
        String command = "create table "
                + TABLE_MEMBER_FILTER +"(";
        for(EnumMemberTable enumTable:EnumMemberTable.values()){
            command += enumTable.getColumnName() + " " + enumTable.getTypeSQL() + ", ";
        }
        //delete the last ', '
        command = command.substring(0,command.length()-2);
        command += ");";

        return command;

    }

    public static String getCreateCommandBuffer(){
        String command = "create table "
                + TABLE_PROFILE_BUFFER +"(";
        for(EnumMemberTable enumTable:EnumMemberTable.values()){
            command += enumTable.getColumnName() + " " + enumTable.getTypeSQL() + ", ";
        }
        //delete the last ', '
        command = command.substring(0,command.length()-2);
        command += ");";

        return command;

    }


    /* getter */
    public static String getTableName(){
        return TABLE_MEMBER;
    }

    public static String getTableNameFilter() { return TABLE_MEMBER_FILTER; }

    public static String getTableNameBuffer() { return TABLE_PROFILE_BUFFER; }

    public String getColumnName() {
        return columnName;
    }

    public String getTypeSQL() {
        return typeSQL;
    }
}
