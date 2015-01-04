package com.example.smsmessaging;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;


public class MessageDisplay extends Activity {

	private static final String TAG_CONTENT = "content";
	private static final String TAG_DATE = "date";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_display);
		
		Intent intent = getIntent();
		
		String cnt = intent.getStringExtra(TAG_CONTENT);
		String dt = intent.getStringExtra(TAG_DATE); 
		
		TextView msgcnt = (TextView) findViewById(R.id.msgcontent);
		TextView msgdt = (TextView) findViewById(R.id.msgdate);
		
		msgcnt.setText(cnt);
		msgdt.setText(dt);
		
		//TextView msgdt = (TextView) findViewById(R.id.msgdate);
		//msgdt.setVisibility(View.GONE);
		
	}

}
