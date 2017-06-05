package com.mtpv.LawnOrder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.button.R;

public class DuplicatePrintActivity extends Activity {
	final int PROGRESS_DIALOG = 1;
	Button duplicate_date_btn,get_dup_print,cancel_dup_print,back_dup_print;
	TextView dup_text;
	ImageView dup_print;
	private Calendar cal;
	private int day,month,year;
	public static String selecteddate, pettyCaseNo, challansListStr;
	private static String NAMESPACE = "http://service.mother.com";
	public static String SOAP_ACTION_ID = NAMESPACE + "getDuplicatePrint", challansListResponse;
	public static String SOAP_ACTION_DUPLICATE = NAMESPACE + "getDuplicatePrintByPettyCase", ecaseResponse;
	
	LinearLayout layout;
	LinearLayout ll;
	LinearLayout.LayoutParams params;
	String PidCode =null;
	String []DuplicatePrint1;
	Button[] Btn;
	LinearLayout.LayoutParams ll_params =null;
	 List<String> duplicateChallan=new ArrayList<String>();
	    //List<String> DuplicatePrint1=new ArrayList<String>();
	    List<String> textdata=new ArrayList<String>();
	    HashMap<String,String> duplicateChallanMap=new HashMap<String,String>();

	static String resp;
	int buttonId=0;
	@SuppressWarnings("unused")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_duplicate_print);
		
		ll_params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		
		final WebService WS = new WebService();
		
		cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);
		
		dup_text = (TextView)findViewById(R.id.dup_text);
		dup_print = (ImageView)findViewById(R.id.dup_print);
		
		duplicate_date_btn = (Button)findViewById(R.id.duplicate_date_btn);
		duplicate_date_btn.setOnTouchListener(new OnTouchListener() {
			
			@SuppressWarnings("deprecation")
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				showDialog(0);
				return false;
			}
		});

		
		cancel_dup_print = (Button)findViewById(R.id.cancel_dup_print);
		cancel_dup_print.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		back_dup_print = (Button)findViewById(R.id.back_dup_print);
		back_dup_print.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		layout = (LinearLayout ) findViewById(R.id.h1);
		params = new LinearLayout.LayoutParams(
        		LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		get_dup_print = (Button)findViewById(R.id.get_dup_print);
		get_dup_print.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				layout.removeAllViews();
				
			//	Toast.makeText(getApplicationContext(), "Please Wait...!", Toast.LENGTH_SHORT).show();
				selecteddate = duplicate_date_btn.getText().toString();
				String pidCode = UserLogin_Activity.Pid_code;

				getDuplicatePrint(selecteddate,pidCode);
				//Toast.makeText(getApplicationContext(), "Please Wait...!", Toast.LENGTH_SHORT).show();
				
			}
		});
	}
	protected void getDuplicatePrint(String selecteddate, String pidCode) {
			new AsyncGetPrint().execute();
		}
	class AsyncGetPrint extends AsyncTask<Void,Void, String>{
		
		ProgressDialog dialog = new ProgressDialog(DuplicatePrintActivity.this);
		
		@Override
		protected void onPreExecute() {
			showDialog(PROGRESS_DIALOG);
		
			super.onPreExecute();
		}
	
	@SuppressWarnings("unused")
	@Override
	protected String doInBackground(Void... params) {
		String  response="";
		String pidCode="";
		String pidName="";
		String psCode="";
		String psName="";
		String cadreCd="";
		String cadreName="";
		String unitCd="";
		String unitName="";
	
	try {
		SQLiteDatabase db = openOrCreateDatabase(DataBase.DATABASE_NAME,MODE_PRIVATE, null);
		String selectQuery = "SELECT  * FROM " + DataBase.LOGIN_TABLE;
	     //SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
       // looping through all rows and adding to list
	    
        if (cursor.moveToFirst()) {
            do {
            	
            	Log.i("1 :",""+ cursor.getString(0));
           	 	Log.i("2 :",""+cursor.getString(1));
           	 	Log.i("3 :",""+cursor.getString(2));
           	 	Log.i("4 :",""+cursor.getString(3));
           	 	Log.i("5 :",""+cursor.getString(4));
           	 	Log.i("6 :",""+cursor.getString(5));
           	 
            	 pidCode = cursor.getString(0);
            	 pidName = cursor.getString(1);
            	 psCode = cursor.getString(2);
            	 psName = cursor.getString(3);
            	 cadreCd = cursor.getString(4);
            	 cadreName = cursor.getString(5);
            	 unitCd = cursor.getString(6);
            	 unitName = cursor.getString(7);
            	 
            		} while (cursor.moveToNext());
        		}
				db.close(); 
			} catch (Exception e) {
				e.printStackTrace();
		
			}
			try {
				String ip = "";
				SQLiteDatabase db = openOrCreateDatabase(DataBase.DATABASE_NAME,MODE_PRIVATE, null);
				String selectQuery = "SELECT  * FROM " + DataBase.IP_TABLE;
			     // SQLiteDatabase db = this.getWritableDatabase();
			    Cursor cursor = db.rawQuery(selectQuery, null);
		       // looping through all rows and adding to list
		        if (cursor.moveToFirst()) {
		            do {
		            	 Log.i("DATABASE   IP VALUE :",""+ cursor.getString(0));
		            	 ip=cursor.getString(0);
		            } while (cursor.moveToNext());
		        }
		        db.close(); 
		        
				// INSERT ID PROOF DETAILS START
				SoapObject request = new SoapObject(NAMESPACE, "getDuplicatePrint");
			    request.addProperty("offenceDate",selecteddate);
				request.addProperty("pidCode", pidCode);
				
				Log.i("PID CODE ::", pidCode);
				Log.i("Date :::", selecteddate);
				 
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				Log.i("request", "" + request);
				HttpTransportSE androidHttpTransport = new HttpTransportSE(ip+"/HydPettyCaseService/services/PettyCaseServiceImpl?wsdl");
				Log.i("androidHttpTransport", "" + androidHttpTransport);
				androidHttpTransport.call(SOAP_ACTION_ID, envelope);
				Object result = envelope.getResponse();
				challansListResponse=result.toString();
				response=challansListResponse;
				DuplicatePrintActivity.challansListStr=challansListResponse;
				
				Log.i("REsponse ::", DuplicatePrintActivity.challansListStr);
				try{
					//((LinearLayout)ll.getParent()).removeView(ll);
					if (DuplicatePrintActivity.challansListStr!=null&&DuplicatePrintActivity.challansListStr.length()>0){	
						DuplicatePrint1 = DuplicatePrintActivity.challansListStr.replace("|", ":").split(":");
						
					  if(DuplicatePrint1!=null && DuplicatePrint1.length>0){
						
						  Btn = new Button[DuplicatePrint1.length];
						for (int j=0;j<DuplicatePrint1.length;j++) {	
							String []Dprint = DuplicatePrint1[j].split("\\@");
							Log.i("DuplicatePrint1:", DuplicatePrint1[j]);
							
							pettyCaseNo = Dprint[0];
							String date=Dprint[1];
							String offenderName = Dprint[2];
							String sections = Dprint[3];
							
							duplicateChallan.add(pettyCaseNo+"\t\t"+offenderName+"\t\t\t"+sections);
							textdata.add(""+offenderName+" "+sections);
							duplicateChallanMap.put(pettyCaseNo+"\t\t"+offenderName+"\t\t\t"+sections, pettyCaseNo);
						    
						    Btn[j] = new Button(getApplicationContext());
							Btn[j].setId(j);
							Btn[j].setLayoutParams(ll_params);
							Btn[j].setText(pettyCaseNo+"\t\t"+offenderName+"\t\t\t"+sections);
							//layout.addView(Btn[j]);
							buttonId=j;
							Btn[j].setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								CharSequence selectedButtonValue = ((Button) v).getText();
								Log.e("text :",selectedButtonValue.toString());
							
								for(String buttonText:duplicateChallanMap.keySet()){
									if(buttonText.equals(selectedButtonValue.toString())){
										Log.i("etcicket ",duplicateChallanMap.get(buttonText));
										getDuplicatePrintByPettyCase(duplicateChallanMap.get(buttonText));
												
										}
									}
								}
							});
							layout.addView(Btn[j]);	
							/*runOnUiThread(new Runnable() {
							    public void run(){ 
							    	layout.addView(Btn[buttonId]);
							    }
							});*/
								}//end of for loop
					  		}
								
					}
					else{
						
					}
				}catch(Exception error){
					error.printStackTrace();
					//System.out.println("Exception : "+ error);
					Toast.makeText(getApplicationContext(), "Data Not Found",Toast.LENGTH_LONG).show();
			
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return response;
			}//end of doinBack
	@Override
	protected void onPostExecute(String result) {

				removeDialog(PROGRESS_DIALOG);
				super.onPostExecute(result);
			
			}
		}

	

	protected void getDuplicatePrintByPettyCase(String string) {
		new AsyncGetPrintePettyCase().execute();
				
			}
		
		class AsyncGetPrintePettyCase extends AsyncTask<Void,Void, String>{
			ProgressDialog dialog = new ProgressDialog(DuplicatePrintActivity.this);
				
			@Override
			protected void onPreExecute() {
				showDialog(PROGRESS_DIALOG);
		            super.onPreExecute();
			}
		@Override
		protected String doInBackground(Void... params) {try {
			String ip = "";
			SQLiteDatabase db = openOrCreateDatabase(DataBase.DATABASE_NAME,MODE_PRIVATE, null);
			String selectQuery = "SELECT  * FROM " + DataBase.IP_TABLE;
		     // SQLiteDatabase db = this.getWritableDatabase();
		    Cursor cursor = db.rawQuery(selectQuery, null);
	       // looping through all rows and adding to list
	        if (cursor.moveToFirst()) {
	            do {
	            	 Log.i("DATABASE   IP VALUE :",""+ cursor.getString(0));
	            	 ip=cursor.getString(0);
	            } while (cursor.moveToNext());
	        }
	        db.close(); 
			// INSERT ID PROOF DETAILS START
			SoapObject request = new SoapObject(NAMESPACE, "getDuplicatePrintByPettyCase");
		    request.addProperty("pettyCaseNO",DuplicatePrintActivity.pettyCaseNo);
			
		    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			
			Log.i("request", "" + request);
			
			HttpTransportSE androidHttpTransport = new HttpTransportSE(ip+"/HydPettyCaseService/services/PettyCaseServiceImpl?wsdl");
			Log.i("androidHttpTransport", "" + androidHttpTransport);
			androidHttpTransport.call(SOAP_ACTION_DUPLICATE, envelope);
			Object result = envelope.getResponse();
			ecaseResponse=result.toString();
			
			Intent i = new Intent(DuplicatePrintActivity.this, PrintDispaly.class);
			i.putExtra("generateChallan", ecaseResponse);
			startActivity(i);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ecaseResponse;
		}//end of doinBack

			@Override
			protected void onPostExecute(String result) {
						removeDialog(PROGRESS_DIALOG);
						super.onPostExecute(result);
					
					}
				}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case PROGRESS_DIALOG:
			ProgressDialog pd = ProgressDialog.show(this, "", "",	true);
			pd.setContentView(R.layout.custom_progress_dialog);
			pd.setCancelable(false); 
			
			return pd;

		default:
			break;
		}
		return new DatePickerDialog(this, datePickerListener, year, month, day);
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			duplicate_date_btn.setText(selectedDay + "/" + (selectedMonth + 1) + "/"
					+ selectedYear);
		}
	};
	
	@Override
	public void onBackPressed() {
		
	}
}
