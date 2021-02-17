package com.example.todolist;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


import androidx.annotation.Nullable;



public class MyDataBaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DB_NAME = "AddedTasks.db";
    private static final int DB_VERSION = 1;



    private static final String TABLE_NAME = "Tasks";
    private static final String COL_ID = "id";
    private static final String COL_TASK_NAME = "Task_Name";


    public MyDataBaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TASK_NAME + " TEXT );" ;

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);

    }

    public long AddItems(String TaskName){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_TASK_NAME,TaskName);

        long result  =  db.insert(TABLE_NAME,null,cv);
        return result;


    }

    public int taskCount(){

        SQLiteDatabase db = this.getReadableDatabase();

        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        db.close();


        return  (int)count;

    }


    Cursor ShowItems(){

        String query = "SELECT * FROM " + TABLE_NAME + " ;";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
//
        if(db != null){
            cursor =   db.rawQuery(query,null);
        }

        return  cursor;


    }

    void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
    }

    void deleteItemRow(String id){

        SQLiteDatabase db = this.getWritableDatabase();
        long result  =  db.delete(TABLE_NAME,"id=?",new String[]{id});
        if(result == -1){
            Toast.makeText(context, "Task not removed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Task removed Successfully", Toast.LENGTH_SHORT).show();
        }

    }





}
