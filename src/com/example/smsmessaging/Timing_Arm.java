package com.example.smsmessaging;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;



public class Timing_Arm extends Activity implements OnClickListener
{
	TimePicker startTime;
	TimePicker endTime;
	Button Setbtn;
	Button Backbtn;
	Button morebtn;
	int hr;
	int min;
	int hr1;
	int min1;
	int moreValue = 0;
	String Start_T;
	String End_T;
	String sHr;
	String sMin;
	String sHr1;
	String sMin1;
	String[] myArray;
	String phno;
	String message;
	
	private ProgressDialog pDialog;
	private static String url = "http://10.10.3.123:8888/insert3.php";
	    
	    // JSON Node names
	    private static final String TAG_SUCCESS = "success";
	    
	    jsonParser jsonParser = new jsonParser();
	    
	    
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timing_arm);
        
        Intent intent = getIntent();
        phno = intent.getStringExtra("phno");
        
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        myArray = new String[3];
        startTime = (TimePicker)findViewById(R.id.start_time);
        endTime = (TimePicker)findViewById(R.id.end_time);
        Setbtn = (Button)findViewById(R.id.setBtn);
        Backbtn = (Button)findViewById(R.id.backBtn);
        morebtn = (Button)findViewById(R.id.morebtn);
    
        startTime.setIs24HourView(true);
        endTime.setIs24HourView(true);
        
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
  
        startTime.setCurrentHour(hour);
        startTime.setCurrentMinute(minute);

        endTime.setCurrentHour(hour);
        endTime.setCurrentMinute(minute);
        
        morebtn.setOnClickListener(this);
        Setbtn.setOnClickListener(this);
        Backbtn.setOnClickListener(this);
       // addListenerOnButton();
      
    }
	
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.morebtn :
			if(moreValue < 2)
			{
				 addListenerOnButton();
			final Calendar c = Calendar.getInstance();
			        int hour = c.get(Calendar.HOUR_OF_DAY);
			        int minute = c.get(Calendar.MINUTE);
			        startTime.setCurrentHour(hour);
			        startTime.setCurrentMinute(minute);

			        endTime.setCurrentHour(hour);
			        endTime.setCurrentMinute(minute);
    
			}
			
		else{

			showMessage();

		}
			break;
		case R.id.setBtn:
			if(moreValue == 2){
				
				hr = startTime.getCurrentHour();
				min = startTime.getCurrentMinute();
				if(hr < 10){
					sHr = "0"+hr;
				}else {
					sHr = String.valueOf(hr);
				}
				
				if(min < 10){
					sMin = "0"+min;
				}else {
					sMin = String.valueOf(min);
				}
				
				hr1 = endTime.getCurrentHour();
				min1 = endTime.getCurrentMinute();
				
				if(hr1 < 10){
					sHr1 = "0"+hr1;
				}else {
					sHr1 = String.valueOf(hr1);
				}
				
				if(min1 < 10){
					sMin1 = "0"+min1;
				}else {
					sMin1 = String.valueOf(min1);
				}
				
				Start_T = sHr + ":" + sMin;
				End_T =  sHr1 + ":" + sMin1;
				
				String phoneNo = phno;
				String message = "TIME,"+myArray[0]+","+myArray[1]+","+Start_T+"-"+End_T+"#";

				sendSMS(phoneNo, message);
				new insert().execute();
			}
			else if(moreValue == 1){
				
				hr = startTime.getCurrentHour();
				min = startTime.getCurrentMinute();
				if(hr < 10){
					sHr = "0"+hr;
				}else {
					sHr = String.valueOf(hr);
				}
				
				if(min < 10){
					sMin = "0"+min;
				}else {
					sMin = String.valueOf(min);
				}
				
				hr1 = endTime.getCurrentHour();
				min1 = endTime.getCurrentMinute();
				
				if(hr1 < 10){
					sHr1 = "0"+hr1;
				}else {
					sHr1 = String.valueOf(hr1);
				}
				
				if(min1 < 10){
					sMin1 = "0"+min1;
				}else {
					sMin1 = String.valueOf(min1);
				}
				
				Start_T = sHr + ":" + sMin;
				End_T =  sHr1 + ":" + sMin1;
				
				String phoneNo = phno;
				String message = "TIME,"+myArray[0]+","+Start_T+"-"+End_T+"#";

				sendSMS(phoneNo, message);
				new insert().execute();
			}else{
				hr = startTime.getCurrentHour();
				min = startTime.getCurrentMinute();
				if(hr < 10){
					sHr = "0"+hr;
				}else {
					sHr = String.valueOf(hr);
				}
				
				if(min < 10){
					sMin = "0"+min;
				}else {
					sMin = String.valueOf(min);
				}
				
				hr1 = endTime.getCurrentHour();
				min1 = endTime.getCurrentMinute();
				
				if(hr1 < 10){
					sHr1 = "0"+hr1;
				}else {
					sHr1 = String.valueOf(hr1);
				}
				
				if(min1 < 10){
					sMin1 = "0"+min1;
				}else {
					sMin1 = String.valueOf(min1);
				}
				
				Start_T = sHr + ":" + sMin;
				End_T =  sHr1 + ":" + sMin1;
				
				String phoneNo = phno;
				 message = "TIME,"+Start_T+"-"+End_T+"#";

				sendSMS(phoneNo, message);
				new insert().execute();
			}
			break;
		case R.id.backBtn: 
			Intent intent = new Intent(this, MainActivity.class);
			this.startActivity(intent);
			break;
		}
		// TODO Auto-generated method stub
	
	}
	
@SuppressWarnings("unused")
private void showMessage() {
		// TODO Auto-generated method stub
	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	builder.setTitle("Alert");
	builder.setMessage("You can only set three time interval");
	builder.setPositiveButton("OK", null);
	AlertDialog dialog = builder.show();
	}


	private void sendSMS(String phoneNo, String message) {
		// TODO Auto-generated method stub
		String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";
 
        PendingIntent sentPI = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(SENT), 0);
 
        PendingIntent deliveredPI = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(DELIVERED), 0);
 
        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent", 
                                Toast.LENGTH_SHORT).show();			                        
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off", 
                                Toast.LENGTH_SHORT).show();
                        break;
                }
                
            }
        }, new IntentFilter(SENT));
 
        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered", 
                                Toast.LENGTH_SHORT).show();
                        break;                        
                }
            }
        }, new IntentFilter(DELIVERED));        
 
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNo, null, message, sentPI, deliveredPI);        

	}
	

	private void addListenerOnButton() {
		
		hr = startTime.getCurrentHour();
		min = startTime.getCurrentMinute();
		
		if(hr < 10){
			sHr = "0"+hr;
		}else {
			sHr = String.valueOf(hr);
		}
		
		if(min < 10){
			sMin = "0"+min;
		}else {
			sMin = String.valueOf(min);
		}
		
		
		hr1 = endTime.getCurrentHour();
		min1 = endTime.getCurrentMinute();
		
		if(hr1 < 10){
			sHr1 = "0"+hr1;
		}else {
			sHr1 = String.valueOf(hr1);
		}
		
		if(min1 < 10){
			sMin1 = "0"+min1;
		}else {
			sMin1 = String.valueOf(min1);
		}
		
		Start_T = sHr + ":" + sMin;
		End_T =  sHr1 + ":" + sMin1;
		
		myArray[moreValue] = Start_T + "-" + End_T;
		moreValue ++;
	}

	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }
	
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle item selection
	        switch (item.getItemId()) {
	            
	            case R.id.menu_about:
	                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
	                return true;
	            case R.id.menu_help:
	                startActivity(new Intent(getApplicationContext(), HelpActivity.class));
	                return true;
	            
	            default:
	                return super.onOptionsItemSelected(item);
	        }
	    }
	 
	 
	 class insert extends AsyncTask<String, String, String>
	    {
	    	
	    	@Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(Timing_Arm.this);
	            pDialog.setMessage("Adding...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(true);
	            pDialog.show();
	        }
	    	
	    	
	    	protected String doInBackground(String... args)
	    	{
	    		
	    		String no1 = message;
	           
	         
	 
	            // Building Parameters
	            List<NameValuePair> params = new ArrayList<NameValuePair>();
	           
	            params.add(new BasicNameValuePair("content", no1));
	            
	 
	            // getting JSON Object
	            // Note that create product url accepts POST method
	            JSONObject json = jsonParser.makeHttpRequest(url,
	                    "POST", params);
	 
	            // check log cat fro response
	            Log.d("Create Response", json.toString());
	    		
	            try {
	                int success = json.getInt(TAG_SUCCESS);
	 
	                if (success == 1) {
	                    // successfully created product
	                   
	                	Log.d("Pf0","Successfully Inserted");
	                    
	                } else {
	                    // failed to create product
	                }
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
	 
	            return null;
	        }
	    	
	    	protected void onPostExecute(String file_url) {
	            // dismiss the dialog once done
	           // pDialog.dismiss();
	        }
	    		
	    	}

}
