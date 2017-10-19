package nsy209.cnam.seldesave.dao.enumTable;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by lavive on 01/06/17.
 */

public enum EnumSupplyDemandTable {

    COLUMN_ID("_id","integer primary key autoincrement"),
    COLUMN_REMOTE_ID("remote_id","integer not null"),
    COLUMN_TYPE("type","text not null"),
    COLUMN_CATEGORY("category","text not null"),
    COLUMN_TITLE("title","text not null"),
    COLUMN_ID_MEMBER("member_id","integer not null"),
    COLUMN_CHECKED("checked","integer not null"),
    COLUMN_ACTIVE("active","integer not null");

    /* attributes */
    private String columnName;
    private String typeSQL;

    /* tables name */
    private static final String TABLE_ALL_SUPPLYDEMAND = "all_supplies_demands";
    private static final String TABLE_MY_SUPPLYDEMAND = "my_supplies_demands";

    EnumSupplyDemandTable(String columnName,String typeSQL){
        this.columnName = columnName;
        this.typeSQL = typeSQL;
    }

    /* all columns */
    public static String[] getAllColumns(){
        List<String> columns = new ArrayList<String>();
        for(EnumSupplyDemandTable enumTable:EnumSupplyDemandTable.values()){
            columns.add(enumTable.getColumnName());
        }
        return columns.toArray(new String[0]);
    }

    /* tables creation command */
    public static String getCreateCommandAll(){
        String command = "create table "
                + TABLE_ALL_SUPPLYDEMAND +"(";
        for(EnumSupplyDemandTable enumTable:EnumSupplyDemandTable.values()) {
            command += enumTable.getColumnName() + " " + enumTable.getTypeSQL() + ", ";
        }
        //delete the last ', '
        command = command.substring(0,command.length()-2);
        command += ");";

        return command;

    }


    public static String getCreateCommandMy(){
        String command = "create table "
                + TABLE_MY_SUPPLYDEMAND +"(";
        for(EnumSupplyDemandTable enumTable:EnumSupplyDemandTable.values()) {
            command += enumTable.getColumnName() + " " + enumTable.getTypeSQL() + ", ";
        }
        //delete the last ', '
        command = command.substring(0,command.length()-2);
        command += ");";

        return command;

    }


    /* getter */
    public static String getTableNameAll(){
        return TABLE_ALL_SUPPLYDEMAND;
    }

    public static String getTableNameMy(){
        return TABLE_MY_SUPPLYDEMAND;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getTypeSQL() {
        return typeSQL;
    }
}
