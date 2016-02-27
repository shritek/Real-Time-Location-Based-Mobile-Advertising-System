package com.lba;

import static com.lba.CommonUtilities.SENDER_ID;
import static com.lba.CommonUtilities.SERVER_URL;

import com.lba.R;
import com.google.android.gcm.GCMRegistrar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	// alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	

	ConnectionDetector cd;
	

	EditText txtName;
	EditText txtEmail;
	String  checkregId;

	Button btnRegister;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String checkregId = GCMRegistrar.getRegistrationId(this);
		
		if (!checkregId.equals("")) 
		{
			if (GCMRegistrar.isRegisteredOnServer(this)) {
				// Skips registration.				
				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(i);
				finish();
			}
		} 
		
		
		setContentView(R.layout.activity_register);
		
		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(RegisterActivity.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}

		
		txtName = (EditText) findViewById(R.id.txtName);
		txtEmail = (EditText) findViewById(R.id.txtEmail);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		

		btnRegister.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			
				String name = txtName.getText().toString();
				String email = txtEmail.getText().toString();
				
				// Check if user filled the form
				if(name.trim().length() > 0 && email.trim().length() > 0){
					// Launch Main Activity
					Intent i = new Intent(getApplicationContext(), MainActivity.class);
					
					// Registering user on our server					
					// Sending registration details to MainActivity
					i.putExtra("name", name);
					i.putExtra("email", email);
					startActivity(i);
					finish();
				}else{
					// user doen't filled that data
					// ask him to fill the form
					alert.showAlertDialog(RegisterActivity.this, "Registration Error!", "Please enter your details", false);
				}
			}
		});
	}

}
