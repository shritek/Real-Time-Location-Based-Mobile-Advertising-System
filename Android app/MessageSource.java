package com.lba;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MessageSource {

	  // Database fields
	  public SQLiteDatabase database;
	  public SQLiteHelper dbHelper;
	  private String[] allColumns = { SQLiteHelper.COLUMN_ID,
	      SQLiteHelper.COLUMN_MESSAGE };

	  public MessageSource(Context context) {
	    dbHelper = new SQLiteHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public Message createMessage(String Message) {
	    
	    Cursor cursor1 = database.query(SQLiteHelper.TABLE_MESSAGES,
		        allColumns, null, null,
		        null, null, null);
	    cursor1.moveToLast();
	    Message newMessage = cursorToMessage(cursor1);
	    cursor1.close();
	    return newMessage;
	  }

	  public void deleteMessage(Message Message) {
	    long id = Message.getId();
	    System.out.println("Message deleted with id: " + id);
	    database.delete(SQLiteHelper.TABLE_MESSAGES, SQLiteHelper.COLUMN_ID
	        + " = " + id, null);
	  }

	  public List<Message> getAllMessages() {
	    List<Message> Messages = new ArrayList<Message>();

	    Cursor cursor = database.query(SQLiteHelper.TABLE_MESSAGES,
	        allColumns, null, null, null, null, null);

	    cursor.moveToLast();
	    while (!cursor.isBeforeFirst()) {
	      Message Message = cursorToMessage(cursor);
	      Messages.add(Message);
	      cursor.moveToPrevious();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return Messages;
	  }

	  public Message cursorToMessage(Cursor cursor) {
	    Message Message = new Message();
	    Message.setId(cursor.getLong(0));
	    Message.setMessage(cursor.getString(1));
	    return Message;
	  }
}

