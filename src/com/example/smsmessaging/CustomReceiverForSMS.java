package com.example.smsmessaging;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

public class CustomReceiverForSMS extends BroadcastReceiver {

	public TextView lbl;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		String message = intent.getStringExtra("incomingSms");
 	   	lbl.setText(message);
 	   	
 	   
	}

}
