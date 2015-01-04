package com.example.smsmessaging;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.smsmessaging.MainActivity;
import com.example.smsmessaging.jsonParser;
import com.example.smsmessaging.SettingActivity.insert;

import android.os.AsyncTask;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;

public class SettingActivity extends Activity {

	Button btnAddNo;
    EditText txtAddPhoneNo;
    private ProgressDialog pDialog;
	 
    jsonParser jsonParser = new jsonParser();
    
    private static String url = "http://10.10.3.123:8888/insert.php";
    
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		btnAddNo = (Button)findViewById(R.id.btnAddno);
		txtAddPhoneNo = (EditText)findViewById(R.id.txtAddPhoneNo);
		
		
		btnAddNo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				new insert().execute();
			}
		});
	}

	class insert extends AsyncTask<String, String, String>
    {
    	
    	@Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SettingActivity.this);
            pDialog.setMessage("Adding...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    	
    	
    	protected String doInBackground(String... args)
    	{
    		
    		String no1 = txtAddPhoneNo.getText().toString();
           
         
 
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
           
            params.add(new BasicNameValuePair("phno", no1));
            
 
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
            pDialog.dismiss();
        }
    		
    	}
 
}
