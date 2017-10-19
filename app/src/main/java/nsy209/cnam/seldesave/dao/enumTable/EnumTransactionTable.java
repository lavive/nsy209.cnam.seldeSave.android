package nsy209.cnam.seldesave.dao.enumTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lavive on 02/06/17.
 */

public enum EnumTransactionTable {

    COLUMN_ID("_id","integer primary key autoincrement"),
    COLUMN_REMOTE_ID("remote_id","integer not null"),
    COLUMN_ID_DEBTOR("debtor_id","integer not null"),
    COLUMN_ID_CREDITOR("creditor_id","integer not null"),
    COLUMN_ID_SUPPLYDEMAND("suppplyDemand_id","integer not null"),
    COLUMN_AMOUNT("amount","real not null");

    /* attributes */
    private String columnName;
    private String typeSQL;

    /* tables name */
    private static final String TABLE_NEW_TRANSACTION = "new_transactions";
    private static final String TABLE_CHECKED_TRANSACTION = "checked_transactions";

    EnumTransactionTable(String columnName,String typeSQL){
        this.columnName = columnName;
        this.typeSQL = typeSQL;
    }

    /* all columns */
    public static String[] getAllColumns(){
        List<String> columns = new ArrayList<String>();
        for(EnumTransactionTable enumTable:EnumTransactionTable.values()){
            columns.add(enumTable.getColumnName());
        }
        return columns.toArray(new String[0]);
    }

    /* tables creation command */
    public static String getCreateCommandNew(){
        String command = "create table "
                + TABLE_NEW_TRANSACTION +"(";
        for(EnumTransactionTable enumTable:EnumTransactionTable.values()){
            command += enumTable.getColumnName() + " " + enumTable.getTypeSQL() + ", ";
        }
        //delete the last ', '
        command = command.substring(0,command.length()-2);
        command += ");";

        return command;

    }

    public static String getCreateCommandChecked(){
        String command = "create table "
                + TABLE_CHECKED_TRANSACTION +"(";
        for(EnumTransactionTable enumTable:EnumTransactionTable.values()){
            command += enumTable.getColumnName() + " " + enumTable.getTypeSQL() + ", ";
        }
        //delete the last ', '
        command = command.substring(0,command.length()-2);
        command += ");";

        return command;

    }


    /* getter */
    public static String getTableNameNew(){
        return TABLE_NEW_TRANSACTION;
    }

    public static String getTableNameChecked(){
        return TABLE_CHECKED_TRANSACTION;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getTypeSQL() {
        return typeSQL;
    }
}
