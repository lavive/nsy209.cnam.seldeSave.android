package nsy209.cnam.seldesave.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import nsy209.cnam.seldesave.bean.CategoryBean;
import nsy209.cnam.seldesave.dao.CategoryDao;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.dao.enumTable.EnumCategoryTable;

/**
 * Created by lavive on 27/09/17.
 */

public class CategoryDaoImpl implements CategoryDao {

    public static CategoryDao getInstance(DaoFactory daoFactory){
        return new CategoryDaoImpl(daoFactory);
    }

    /* attribute */
    private DaoFactory daoFactory;

    private CategoryDaoImpl(DaoFactory daoFactory){
        this.daoFactory = daoFactory;
    }

    public List<CategoryBean> getAllCategories(){
        daoFactory.open();
        List<CategoryBean> categoriesBean = new ArrayList<CategoryBean>();

        Cursor cursor = this.daoFactory.getDatabase().query(EnumCategoryTable.getTableName(), EnumCategoryTable.getAllColumns(),
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            CategoryBean categoryBean = cursorToCategory(cursor);
            categoriesBean.add(categoryBean);
            cursor.moveToNext();
        }

        cursor.close();
        return categoriesBean;
    }

    public void createCategories(List<CategoryBean> categoriesBean){
        daoFactory.open();
        this.daoFactory.getDatabase().execSQL("DROP TABLE IF EXISTS " + EnumCategoryTable.getTableName());
        this.daoFactory.getDatabase().execSQL(EnumCategoryTable.getCreateCommand());
        for(CategoryBean categoryBean:categoriesBean) {
            ContentValues values = new ContentValues();
            values.put(EnumCategoryTable.COLUMN_REMOTE_ID.getColumnName(), categoryBean.getRemote_id());
            values.put(EnumCategoryTable.COLUMN_CATEGORY.getColumnName(), categoryBean.getCategory());
            this.daoFactory.getDatabase().insert(EnumCategoryTable.getTableName(), null, values);
        }
    }

    public void removeCategory(String category){
        daoFactory.open();

        this.daoFactory.getDatabase().delete(EnumCategoryTable.getTableName(),
                EnumCategoryTable.COLUMN_CATEGORY.getColumnName() + "=" + category, null);
    }

    public void addCategory(CategoryBean categoryBean){
        daoFactory.open();
        ContentValues values = new ContentValues();
        values.put(EnumCategoryTable.COLUMN_REMOTE_ID.getColumnName(), categoryBean.getRemote_id());
        values.put(EnumCategoryTable.COLUMN_CATEGORY.getColumnName(), categoryBean.getCategory());
        this.daoFactory.getDatabase().insert(EnumCategoryTable.getTableName(), null, values);
    }


    /* helper method */

    private CategoryBean cursorToCategory(Cursor cursor) {
        CategoryBean categoryBean = new CategoryBean();
        categoryBean.setId(cursor.getLong(0));
        categoryBean.setRemote_id(cursor.getLong(1));
        categoryBean.setCategory(cursor.getString(2));
        return categoryBean;
    }
}
