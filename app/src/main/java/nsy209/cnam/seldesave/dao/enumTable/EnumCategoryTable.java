package nsy209.cnam.seldesave.dao.enumTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lavive on 06/06/17.
 */

public enum EnumCategoryTable {

    COLUMN_ID("_id","integer primary key autoincrement"),
    COLUMN_REMOTE_ID("remote_id","integer not null"),
    COLUMN_CATEGORY("category","text not null");

    /* attributes */
    private String columnName;
    private String typeSQL;

    /* tables name */
    private static final String TABLE_CATEGORY = "categories";
    private static final String TABLE_CATEGORY_FILTER = "categories_filter";

    EnumCategoryTable(String columnName,String typeSQL){
        this.columnName = columnName;
        this.typeSQL = typeSQL;
    }

    /* all columns */
    public static String[] getAllColumns(){
        List<String> columns = new ArrayList<String>();
        for(EnumCategoryTable enumTable:EnumCategoryTable.values()){
            columns.add(enumTable.getColumnName());
        }
        return columns.toArray(new String[0]);
    }

    /* tables creation command */
    public static String getCreateCommand(){
        String command = "create table "
                + TABLE_CATEGORY +"(";
        for(EnumCategoryTable enumTable:EnumCategoryTable.values()){
            command += enumTable.getColumnName() + " " + enumTable.getTypeSQL() + ", ";
        }
        //delete the last ', '
        command = command.substring(0,command.length()-2);
        command += ");";

        return command;

    }

    public static String getCreateCommandFilter(){
        String command = "create table "
                + TABLE_CATEGORY_FILTER +"(";
        for(EnumCategoryTable enumTable:EnumCategoryTable.values()){
            command += enumTable.getColumnName() + " " + enumTable.getTypeSQL() + ", ";
        }
        //delete the last ', '
        command = command.substring(0,command.length()-2);
        command += ");";

        return command;

    }


    /* getter */
    public static String getTableName(){
        return TABLE_CATEGORY;
    }

    public static String getTableNameFilter(){
        return TABLE_CATEGORY_FILTER;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getTypeSQL() {
        return typeSQL;
    }
}

