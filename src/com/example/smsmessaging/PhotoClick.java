package com.example.smsmessaging;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class PhotoClick extends Activity implements OnClickListener, OnItemSelectedListener {
    Spinner spClick;
    Spinner inClick; 
    Button okClick;
    Button backClick;
    int i;
    String photoNo;
    String photoInter;
    String phno;
    String message;
    private ProgressDialog pDialog;
    private static String url = "http://10.10.3.123:8888/insert3.php";
        
        // JSON Node names
        private static final String TAG_SUCCESS = "success";
        
        jsonParser jsonParser = new jsonParser();
        
        
	@Override
	protected void onCreate(Bundle savedInstanceState){
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.photo_click); 
	        
	        Intent intent = getIntent();
	        phno = intent.getStringExtra("phno");
	        
	        spClick = (Spinner)findViewById(R.id.spinner);
	        inClick = (Spinner)findViewById(R.id.spinner1);
	        okClick = (Button)findViewById(R.id.btnSendSMS_p);
	        backClick = (Button)findViewById(R.id.btnBack);
	        List<String> list = new ArrayList<String>();
	        for(i=0 ; i<=60 ; i++)
	        {
	        	list.add(String.valueOf(i));
	        }
	        
	        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
            (this, android.R.layout.simple_spinner_item,list);
	        
	        inClick.setAdapter(dataAdapter);
	        
	        addListenerOnButton();
	        backClick.setOnClickListener(this);
	       
	        
	}

	 public void addListenerOnButton() {
		     
		    photoNo = (String) spClick.getSelectedItem();
		    photoInter = (String) inClick.getSelectedItem();
		    
	        okClick.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View v) {
	            	String phoneNo = phno;
					 message = "PHOTOSET,"+photoInter+","+photoNo+"#";

					sendSMS(phoneNo, message);
					new insert().execute();
					
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
				 
	 
	        });
	 }
	 
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, MainActivity.class);
		this.startActivity(intent);
		
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		Toast.makeText(arg0.getContext(), 
                "On Item Select : \n" + arg0.getItemAtPosition(arg2).toString(),
                Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
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
	            pDialog = new ProgressDialog(PhotoClick.this);
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
