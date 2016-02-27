package com.lba;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {

  public static final String TABLE_MESSAGES = "messages";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_MESSAGE = "message";

  private static final String DATABASE_NAME = "messages.db";
  private static final int DATABASE_VERSION = 1;
  private String[] allColumns = {COLUMN_ID,
	      COLUMN_MESSAGE };
  // Database creation sql statement
  private static final String DATABASE_CREATE = "create table "
      + TABLE_MESSAGES + "(" + COLUMN_ID
      + " integer primary key autoincrement, " + COLUMN_MESSAGE
      + " text not null);";
private Context cnt;
  public SQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    cnt=context;
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(SQLiteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
    onCreate(db);
  }
  
  public void insertMsg(String msg) {
	  
	 

	    SQLiteDatabase db = getWritableDatabase();

	    ContentValues cv = new ContentValues();

	    cv.put(COLUMN_MESSAGE, msg);

	    db.insert(TABLE_MESSAGES, null, cv);

	    db.close();
	    MessageSource datasource = new MessageSource(cnt);
	    datasource.open();
	    datasource.database = datasource.dbHelper.getWritableDatabase();
	 
	    Cursor cursor2 = datasource.database.query( TABLE_MESSAGES,
		        allColumns, null, null, null, null, null);
	    if(cursor2.getCount()>5)
	    {
	    cursor2.moveToFirst();
	    Message oldMessage = datasource.cursorToMessage(cursor2);
	    datasource.deleteMessage(oldMessage);
	    }
	    cursor2.close();
	 

	  }

} 
