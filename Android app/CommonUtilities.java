package com.lba;

import android.content.Context;
import android.content.Intent;

public final class CommonUtilities {
	
	// server registration url
    static final String SERVER_URL = "http://192.168.1.4//server/register.php"; 

    // Google project id
    static final String SENDER_ID = "986359723754"; 

    
    static final String TAG = "LBA";

    static final String DISPLAY_MESSAGE_ACTION =
            "com.lba.DISPLAY_MESSAGE";

    static final String EXTRA_MESSAGE = "message";

    static void displayMessage(Context context, String message) {
    
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.removeExtra(message);
        intent.putExtra(EXTRA_MESSAGE, message);
       
        context.sendBroadcast(intent);
    }
}
