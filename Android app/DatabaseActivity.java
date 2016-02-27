package com.lba;

import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

public class DatabaseActivity extends ListActivity {
  
	private MessageSource datasource;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    datasource = new MessageSource(this);
    datasource.open();

    List<Message> values = datasource.getAllMessages();

    // Use the SimpleCursorAdapter to show the
    // elements in a ListView
    ArrayAdapter<Message> adapter = new ArrayAdapter<Message>(this,
        android.R.layout.simple_list_item_1, values);
    
    setListAdapter(adapter);
    
    
    @SuppressWarnings("unchecked")
    ArrayAdapter<Message>  adapter1 = (ArrayAdapter<Message>) getListAdapter();
    Message message = null;
    switch (GCMIntentService.msg) {
    case 1:
      String message1 = GCMIntentService.message;
      // Save the new message to the database
      message = datasource.createMessage(message1);

      break;
    }
    adapter1.notifyDataSetChanged();
  }

  @Override
  protected void onResume() {
    datasource.open();
    super.onResume();
  }

  @Override
  protected void onPause() {
    datasource.close();
    super.onPause();
  }

} 
