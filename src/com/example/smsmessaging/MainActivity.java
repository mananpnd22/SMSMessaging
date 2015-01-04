package com.example.smsmessaging;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.smsmessaging.SettingActivity.insert;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;
 
@SuppressWarnings("deprecation")
public class MainActivity extends Activity implements OnClickListener, OnCheckedChangeListener
{
    Button btnSendSMS;
    EditText txtPhoneNo;
    EditText txtMessage;
    //ImageButton btnArmOn;
    //ImageButton btnArmOff;
    Switch btnArm;
    ImageButton btnCall;
    Switch btnArmTime;
    ImageButton btnPhoto;
    ImageButton btnEmerg;
    ImageButton btnTime;
    
    JSONArray jArray = null;
    String result = null;
    private ProgressDialog pDialog;
    static String out_contact = null;
    
    StringBuilder sb = null;
    InputStream is = null;
    
    JSONObject json_data = null;
    int flag =0;
    Context mContext;
    String getContact;
    
    String message;
    String message2;
 
private static String url = "http://10.10.3.123:8888/insert3.php";
    
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    
    jsonParser jsonParser = new jsonParser();
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  
        
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
 
        btnSendSMS = (Button) findViewById(R.id.btnSendSMS);
        txtPhoneNo = (EditText) findViewById(R.id.txtPhoneNo);
        txtMessage = (EditText) findViewById(R.id.txtMessage);
        mMessageReceiver.lbl = (EditText) findViewById(R.id.txtMessage);
       // btnArmOn = (ImageButton) findViewById(R.id.imageButton2);
        //btnArmOff = (ImageButton) findViewById(R.id.imageButton5);
        //ToggleButton toggle = (ToggleButton) findViewById(R.id.togglebutton);
        btnArm = (Switch)findViewById(R.id.switch1);
        btnCall = (ImageButton) findViewById(R.id.imageButton1);
        btnArmTime = (Switch)findViewById(R.id.switch2);
        btnPhoto = (ImageButton) findViewById(R.id.imageButton3);
        btnEmerg = (ImageButton)findViewById(R.id.imageButton2);
        btnTime = (ImageButton)findViewById(R.id.imageButton4);
        
        registerReceiver(mMessageReceiver, new IntentFilter("com.anything.custom.ReceiveSMS"));
        
        new sendRequest().execute();
        if (getContact == null){
        	btnSendSMS.setOnClickListener(this);
            btnArm.setOnCheckedChangeListener(this);
            btnCall.setOnClickListener(this);
            btnArmTime.setOnCheckedChangeListener(this);
            btnPhoto.setOnClickListener(this);
            btnEmerg.setOnClickListener(this);
            btnTime.setOnClickListener(this);
            
            txtPhoneNo.setText(getContact);
        }
        else{
        	showMessage();
        }
        
    }
    
    public class sendRequest extends AsyncTask<Void, Void, Integer>
	{
		@Override
		protected void onPreExecute() 
		{
			
		};
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.d("result", result.toString());
			
			if(result == 0)
			{
				showMessage();
				//Log.d("condition", getContact);
					
//				Intent i = new Intent(MainActivity.this,NextPage.class);
//				i.putExtra("username", getusername);
//				i.putExtra("userid", userid);
//				startActivity(i);
					
			}
			
		}
		@Override
		protected Integer doInBackground(Void... params) {
			
			 try {
		    	 
				 Log.d("PF0", "connecting");  // http://File.php
			    	DefaultHttpClient client = new DefaultHttpClient();
			        HttpGet request = new HttpGet("http://10.10.3.123:8888/addPhone.php");//Change as per ur internet ip
			        HttpResponse response = client.execute(request);
			        
			        HttpEntity entity = response.getEntity();
			        is = entity.getContent();
			        
			      //  long entity_length = response.getEntity().getContentLength();
			    if(entity.getContentLength() == 0)
			        {
			    	Log.d("cot","Entity Length 0");
			        }
			    else
			    {
			    	Log.d("cot","Entity Length Found");
			    }
			       
			        
			      //convert response to string
			        try{
			              BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			               sb = new StringBuilder();
			               sb.append(reader.readLine() + "\n");

			               String line="0";
			               while ((line = reader.readLine()) != null) {
			                              sb.append(line + "\n");
			                }
			                is.close();
			                result=sb.toString();
			                Log.d("String", result);
			                }catch(Exception e){
			                      Log.e("log_tag", "Error converting result "+e.toString());
			                }
			        
			       // String name;
			        try{
			        	
			            jArray = new JSONArray(result);
			            JSONObject json_data=null;
			            for(int i=0;i<jArray.length();i++){
			                   json_data = jArray.getJSONObject(i);
			                   //here "Name" is the column name in database
			                   getContact = json_data.getString("phno");
			                  
			                   Log.d("PFPass", getContact);
			                  
			                 //Toast.makeText(getBaseContext(), out, Toast.LENGTH_LONG).show();
			                   return 1;
			            }
			              }
			              catch(JSONException e1){
			            	  Log.d("PF2", "ERROR HERE");   
			              } catch (ParseException e1) {
			           e1.printStackTrace();
			         }
			        
			        
			        
			    } catch (ClientProtocolException e) {
			        Log.d("HTTPCLIENT", "Not Connected");
			        //e.getLocalizedMessage()
			    } catch (IOException e) {
			        Log.d("HTTPCLIENT",e.getLocalizedMessage());
			    }
			
			
			return 0;
		}
			
		}
   
    @SuppressWarnings("unused")
    private void showMessage() {
    		// TODO Auto-generated method stub
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Alert");
    	builder.setMessage("First you have to set the phone number \n Menu -> Setting");
    	builder.setPositiveButton("OK", null);
    	AlertDialog dialog = builder.show();
    	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
	public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch(arg0.getId()){
        case R.id.btnSendSMS : String phoneNo = txtPhoneNo.getText().toString();
        					String message = txtMessage.getText().toString();                 
        					if (phoneNo.length()>0 && message.length()>0) 
        					{
        							sendSMS(phoneNo, message);    
        							new insert().execute();
        					}
        					else
        							Toast.makeText(getBaseContext(), 
        									"Please enter both phone number and message.", 
        									Toast.LENGTH_SHORT).show(); 
        
            break;
        case R.id.imageButton1 :
        	Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:"+getContact));
			startActivity(callIntent);
			break;
        case R.id.imageButton2 :
        	Intent intent_arm = new Intent(this,Alarm_capture.class);
        	intent_arm.putExtra("phno", getContact);
        	this.startActivity(intent_arm);
        	break;
        case R.id.imageButton3 :
        	Intent intent = new Intent(this, PhotoClick.class);
        	intent.putExtra("phno", getContact);
        	this.startActivity(intent);
        	break;
        case R.id.imageButton4 :
        	Intent intent_time = new Intent(this, Timing_Arm.class);
        	intent_time.putExtra("phno", getContact);
        	this.startActivity(intent_time);
        	break;
        
        						
        }
         
    }
	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.switch1 : if(arg1){
						String phoneNo = getContact;
						message = "ON#";
	
						sendSMS(phoneNo, message);
						new insert2().execute();
					}
					else{
						String phoneNo = getContact;
						 message = "OFF#";
	
						sendSMS(phoneNo, message);
						new insert2().execute();
	
					}
				break;
		case R.id.switch2 : if(arg1){
						String phoneNo = getContact;
						 message2 = "TIMEON#";

						sendSMS(phoneNo, message);
						new insert3().execute();
				}
				else{
						String phoneNo = getContact;
						message2 = "TIMEOFF#";

						sendSMS(phoneNo, message);
						new insert3().execute();

				}
		}
		
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
		
	  private CustomReceiverForSMS mMessageReceiver = new CustomReceiverForSMS();

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
	            case R.id.menu_setting:
	            	startActivity(new Intent(getApplicationContext(), SettingActivity.class));
	            	return true;
	            case R.id.menu_message_history:
	            	startActivity(new Intent(getApplicationContext(), MessageHistory.class));
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
	            pDialog = new ProgressDialog(MainActivity.this);
	            pDialog.setMessage("Adding...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(true);
	            pDialog.show();
	        }
	    	
	    	
	    	protected String doInBackground(String... args)
	    	{
	    		
	    		String no1 = txtMessage.getText().toString();
	           
	         
	 
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
	 
	  
	  class insert2 extends AsyncTask<String, String, String>
	    {
	    	
	    	@Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(MainActivity.this);
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
	 
	  
	  class insert3 extends AsyncTask<String, String, String>
	    {
	    	
	    	@Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(MainActivity.this);
	            pDialog.setMessage("Adding...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(true);
	            pDialog.show();
	        }
	    	
	    	
	    	protected String doInBackground(String... args)
	    	{
	    		
	    		String no1 = message2;
	           
	         
	 
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
   