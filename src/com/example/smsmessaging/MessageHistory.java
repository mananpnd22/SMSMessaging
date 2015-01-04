package com.example.smsmessaging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.example.smsmessaging.MainActivity;
import com.example.smsmessaging.R;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;





public class MessageHistory extends Activity {
	
	
	ListView list;
	String str;
	//TextView dt;
	TextView msg;
	Button Btngetdata;
	ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
	
	
	private static final String TAG_CONTENT = "content"; //as per db field name as in table
	private static final String TAG_DATE = "date";
	
	JSONArray android = null;


	static InputStream is = null;
	//static JSONArray jObj = null;
	static String json = "";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_history);
		
		
		oslist = new ArrayList<HashMap<String, String>>();
        list=(ListView)findViewById(R.id.list);
        
        Btngetdata = (Button)findViewById(R.id.getdata);
        Btngetdata.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
		         new JSONParse().execute();

				
			}
		});
        
	}
	
	  private class JSONParse extends AsyncTask<Void, Void, Void> {
	    	 private ProgressDialog pDialog;
	    	 
	    	
	        protected void onPreExecute() {
	            super.onPreExecute();
	             
				 msg = (TextView) findViewById(R.id.content);
				 //dt = (TextView) findViewById(R.id.date);
	            pDialog = new ProgressDialog(MessageHistory.this);
	            pDialog.setMessage("Getting Data ...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(true);
	            pDialog.show();
	            
	            
	            
	    	}
	    	
	    	
	        protected Void doInBackground(Void... arg0) {
	    		
	    		
	    		try {
	    			// defaultHttpClient
	    			Log.d("PF0", "connecting");  // http://File.php
			    	DefaultHttpClient client = new DefaultHttpClient();
			        HttpGet request = new HttpGet("http://10.10.3.123:8888/fetch.php");//change as per ur internet ip
			        HttpResponse response = client.execute(request);
	    			HttpEntity httpEntity = response.getEntity();
	    			is = httpEntity.getContent();			

	    			 if(httpEntity.getContentLength() == 0)
				        {
				    	Log.d("cot","Entity Length 0");
				        }
				    else
				    {
				    	Log.d("cot","Entity Length Found");
				    }
	    		
	    		try {
	    			BufferedReader reader = new BufferedReader(new InputStreamReader(
	    					is, "iso-8859-1"), 8);
	    			StringBuilder sb = new StringBuilder();
	    			String line = null;
	    			while ((line = reader.readLine()) != null) {
	    				sb.append(line + "\n");
	    			}
	    			is.close();
	    			json = sb.toString();
	    		} catch (Exception e) {
	    			Log.e("Buffer Error", "Error converting result " + e.toString());
	    		}

	    		
	    			
	    		try
	    		{
	    			
	    			android = new JSONArray(json);
	    			JSONObject json_data=null;
	    			for(int i=0;i<android.length();i++){
		            	
		                   json_data = android.getJSONObject(i);
		                   
		                  String ct_content = json_data.getString("content");//here "Name" is the column name in database
		                  String ct_date = json_data.get("date").toString();
		                  
		                  
		                 
		                  try {
		                	  String format="yyyy-MM-dd hh:mm:ss";
			                  SimpleDateFormat sdf = new SimpleDateFormat(format);
							Date date = sdf.parse(ct_date);
							 str = sdf.format(date);
							Log.d("Date",str);

						} catch (java.text.ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		                  
		                   Log.d("PFName", ct_content);
		                  // Log.d("PFPass", ct_date);
		                   
		                   HashMap<String, String> map = new HashMap<String, String>();
		                   
		                   map.put("content", ct_content);
		                  map.put("date", str);
		                 
		                  oslist.add(map);
		                 
		                   Log.d("string4","in return");       
		            }
	    		  }
	            catch(JSONException e1){
	          	  Log.d("PF2", "ERROR HERE");   
	            } catch (ParseException e1) {
	         e1.printStackTrace();
	       }
	     // return 1;
	      
	      
	  } catch (ClientProtocolException e) {
	      Log.d("HTTPCLIENT", "Not Connected");
	      //e.getLocalizedMessage()
	  } catch (IOException e) {
	      Log.d("HTTPCLIENT",e.getLocalizedMessage());
	  }
	return null;
	    			
	    		}
	    		
	    		
	    		
	    		
	    
	         protected void onPostExecute(Void result) {
	    		 pDialog.dismiss();
	    		
	    				
	    				
	   
	    				ListAdapter adapter = new SimpleAdapter(MessageHistory.this, oslist,
	    						R.layout.list_view,
	    						new String[] { TAG_CONTENT, TAG_DATE }, new int[] {
	    								R.id.content, R.id.date});

	    				list.setAdapter(adapter);
	    				list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	    		            @Override
	    		            public void onItemClick(AdapterView<?> parent, View view,
	    		                                    int position, long id) {
	    		                Toast.makeText(MessageHistory.this, "You Clicked at "+oslist.get(+position).get("content"), Toast.LENGTH_SHORT).show();

	    		                
	    		                String cnt = ((TextView) view.findViewById(R.id.content)).getText().toString();
	    		                String dt = ((TextView) view.findViewById(R.id.date)).getText().toString();
	    		                
	    		                Intent intent = new Intent(getApplicationContext(),MessageDisplay.class);
	    		                intent.putExtra(TAG_CONTENT, cnt);
	    		                intent.putExtra(TAG_DATE, dt);
	    		                startActivity(intent);
	    		                
	    		                
	    		            }
	    		        });

	    				}
	    		
	    		 
	    }
	    

}
