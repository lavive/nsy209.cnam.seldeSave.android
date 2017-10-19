package nsy209.cnam.seldesave.dao.enumTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lavive on 21/06/17.
 */

public enum EnumGeolocationTable {

    COLUMN_ID("_id","integer primary key autoincrement"),
    COLUMN_MEMBER_ID("member_id","integer not null"),
    COLUMN_LATITUDE("latitude","real not null"),
    COLUMN_LONGITUDE("longitude","real not null");

    /* attributes */
    private String columnName;
    private String typeSQL;

    /* tables name */
    private static final String TABLE_GEOLOCATION = "geolocation";

    EnumGeolocationTable(String columnName,String typeSQL){
        this.columnName = columnName;
        this.typeSQL = typeSQL;
    }

    /* all columns */
    public static String[] getAllColumns(){
        List<String> columns = new ArrayList<String>();
        for(EnumGeolocationTable enumTable:EnumGeolocationTable.values()){
            columns.add(enumTable.getColumnName());
        }
        return columns.toArray(new String[0]);
    }

    /* tables creation command */
    public static String getCreateCommand(){
        String command = "create table "
                + TABLE_GEOLOCATION +"(";
        for(EnumGeolocationTable enumTable:EnumGeolocationTable.values()) {
            command += enumTable.getColumnName() + " " + enumTable.getTypeSQL() + ", ";
        }
        //delete the last ', '
        command = command.substring(0,command.length()-2);
        command += ");";

        return command;

    }

    /* getter */
    public static String getTableName(){
        return TABLE_GEOLOCATION;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getTypeSQL() {
        return typeSQL;
    }
}
