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
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.button.R;

@SuppressLint("ClickableViewAccessibility")
public class Duplicate_SampleActivity extends Activity {
	
	final int PROGRESS_DIALOG = 1;
	
	LinearLayout ll_main;
	Button[] btn_arr;
	Button get_dup_print,cancel_dup_print, back_dup_print;
	EditText duplicate_date_btn, select_date;
	String Date;
	TextView dup_text;
	ImageView dup_print;
	
	ProgressDialog progressDialog;

	@SuppressWarnings("unused")
	private Calendar cal;
	@SuppressWarnings("unused")
	private int day,month,year;
	LinearLayout.LayoutParams ll_params = null;
	public static String selecteddate, pettyCaseNo, challansListStr, Date_Dup, DupPrint_Date, buttonText;
	private static String NAMESPACE = "http://service.mother.com";
	public static String SOAP_ACTION_ID = NAMESPACE + "getDuplicatePrint", challansListResponse;
	public static String SOAP_ACTION_DUPLICATE = NAMESPACE + "getDuplicatePrintByPettyCase", ecaseResponse;
	List<String> duplicateChallan=new ArrayList<String>();
    //List<String> DuplicatePrint1=new ArrayList<String>();
    List<String> textdata=new ArrayList<String>();
    HashMap<String,String> duplicateChallanMap=new HashMap<String,String>();
    int DATE_DIALOG_ID;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_duplicate_print);
		ll_main = (LinearLayout) findViewById(R.id.h1);
		ll_params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
	/*	cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);*/
		back_dup_print = (Button)findViewById(R.id.back_dup_print);
		back_dup_print.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent i = new Intent(getApplicationContext(),Menu_Dashboard_Activity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});
		
	/************************GET DUPLICATE PRINT AND LIST AS BUTTON******************************/
		get_dup_print = (Button)findViewById(R.id.get_dup_print);
		get_dup_print.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("unused")
			@Override
			public void onClick(View v) {
					progressDialog = ProgressDialog.show(Duplicate_SampleActivity.this, "Data Loading",  "Please wait ...Processing!", true);
			        progressDialog.setCancelable(true);
					new Thread(new Runnable() {
	                    @Override
	                    public void run() {
	                        try {
	                            Thread.sleep(3000); 
	                        	} catch (Exception e) {
	                        }
	                        progressDialog.dismiss();
	                    }
	                }).start();
	                
					String pidCode = UserLogin_Activity.Pid_code;
				//	String response = "HYD00PC16100263@2016-01-28@KATTA MADHUSUDHAN@59|HYD00PC16100264@2016-01-28@KATTA MADHUSUDHAN@70A,70B|HYD00PC16100265@2016-01-28@BATHINOJU KIRAN KUMAR@71,72|HYD00PC16100266@2016-01-28@YALAGANDULA,RAMACHARY@67E,69,70A,70B,70C|HYD00PC16100267@2016-01-28@PERALA SUNIL KUMAR@70B|HYD00PC16100268@2016-01-28@BHHN@45,61A|HYD00PC16100269@2016-01-28@KATTA MADHUSUDHAN@43,45|HYD00PC16100270@2016-01-28@KIRAN@59,61A|HYD00PC16100271@2016-01-28@PERALA SUNIL KUMAR@70A|HYD00PC16100272@2016-01-28@KATTA MADHUSUDHAN@61A|HYD00PC16100273@2016-01-28@KATTA MADHUSUDHAN@39B|HYD00PC16100274@2016-01-28@BATHINOJU KIRAN KUMAR@59,61A|HYD00PC16100275@2016-01-28@BATHINOJU KIRAN KUMAR@59,61A|HYD00PC16100276@2016-01-28@KIRAN@59|HYD00PC16100277@2016-01-28@KIRAN@43|HYD00PC16100278@2016-01-28@KATTA MADHUSUDHAN@61A|HYD00PC16100279@2016-01-28@BATHINOJU KIRAN KUMAR@43,45|HYD00PC16100280@2016-01-28@KATTA MADHUSUDHAN@62,66A,66B,66B,66E|HYD00PC16100281@2016-01-28@BATHINOJU KIRAN KUMAR@43,59";
					Log.i("DupPrint_Date :::", select_date.getText().toString());
					String response =getServiceData(select_date.getText().toString(), pidCode);
					
					if(response.trim().length()>10){
							String[] response_1 = response.replace("|", ":").split(":");
							Log.i("response_1", "" + response_1.length);
							String[][] response_2=new String[response_1.length][4];
						
						for (int i = 0; i < response_1.length; i++) {
							response_2[i]=response_1[i].split("@");
							//Log.i("response_1", "" + response_2[i][0]);
							System.out.println("response_:" + response_2[i][0]);
						}
							ll_main.removeAllViews();
							btn_arr = new Button[response_2.length];
						for (int i = 0; i < response_2.length; i++) {
							pettyCaseNo = response_2[i][0];
							String date=response_2[i][1];
							String offenderName = response_2[i][2];
							String sections = response_2[i][3];
							
							duplicateChallan.add(pettyCaseNo+"\t\t"+offenderName+"\t\t\t"+sections);
							textdata.add(""+offenderName+" "+sections);
							duplicateChallanMap.put(pettyCaseNo+"\t\t"+offenderName+"\t\t\t"+sections, pettyCaseNo);
							// Log.i("response_1", "" + response_1[i]);
							btn_arr[i] = new Button(getApplicationContext());
							btn_arr[i].setText(pettyCaseNo+"\t\t"+offenderName+"\t\t\t"+sections);
							btn_arr[i].setLayoutParams(ll_params);
							btn_arr[i].setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									CharSequence selectedButtonValue = ((Button) v).getText();
									Log.e("text :",selectedButtonValue.toString());
								
									for(String buttonText:duplicateChallanMap.keySet()){
										if(buttonText.equals(selectedButtonValue.toString())){
											Log.i("etcicket ",duplicateChallanMap.get(buttonText));
													pettyCaseNo = duplicateChallanMap.get(buttonText);
													getDuplicatePrintByPettyCase(duplicateChallanMap.get(buttonText));
													
												}
											}
										}
									});
							ll_main.addView(btn_arr[i]);
							
						}
			}else {
				ll_main.removeAllViews();
				Toast toast = Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 400);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();
			}
			}
		});
		/************************GET DUPLICATE PRINT AND LIST AS BUTTON CLOSED******************************/
		
		dup_text = (TextView)findViewById(R.id.dup_text);
		dup_print = (ImageView)findViewById(R.id.dup_print);
		
		
		/************************DATE PICKER DIALOG CALL******************************/
		select_date=(EditText)findViewById(R.id.duplicate_date_btn);
		select_date.setOnTouchListener(new OnTouchListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				showDialog(0);
				return false;
				}
			});
		}
	/************************DATE PICKER DIALOG CALL ENDS******************************/
		
	/************************DUPLICATE PRINT ASYNCTASK SERVICE CALL******************************/
		protected void getDuplicatePrintByPettyCase(String string) {
			new AsyncGetPrintePettyCase().execute();
		}
	/************************DUPLICATE PRINT ASYNCTASK SERVICE CALL ENDS**************************/	
	/************************DUPLICATE PRINT ASYNCTASK******************************/	
	class AsyncGetPrintePettyCase extends AsyncTask<Void,Void, String>{
		ProgressDialog dialog = new ProgressDialog(Duplicate_SampleActivity.this);
					
		@Override
		protected void onPreExecute() {
	            dialog.setMessage("Please wait.....!");
	            dialog.setIndeterminate(true);
	            dialog.setCancelable(true);
	            dialog.show();
	            super.onPreExecute();
		}
			@Override
			protected String doInBackground(Void... params) {try {
					String ip = "";
					buttonText = duplicateChallanMap.get(buttonText);
						
					Log.i("PETTYCASE :",pettyCaseNo);
					
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
					
				    request.addProperty("pettyCaseNO",Duplicate_SampleActivity.pettyCaseNo);
					
				    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.dotNet = true;
					envelope.setOutputSoapObject(request);
					Log.i("request", "" + request);
					HttpTransportSE androidHttpTransport = new HttpTransportSE(ip+"/HydPettyCaseService/services/PettyCaseServiceImpl?wsdl");
					Log.i("androidHttpTransport", "" + androidHttpTransport);
					androidHttpTransport.call(SOAP_ACTION_DUPLICATE, envelope);
					Object result = envelope.getResponse();
					ecaseResponse=result.toString();
					
					Intent i = new Intent(Duplicate_SampleActivity.this, PrintDispaly.class);
					i.putExtra("generateChallan", ecaseResponse);
					startActivity(i);
				} catch (Exception e) {
					e.printStackTrace();
				}
					return ecaseResponse;
				}//end of doinBack
	
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				dialog.dismiss();
				if (!isNetworkAvailable()) {
				    // do something
			        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Duplicate_SampleActivity.this);
			        alertDialogBuilder.setMessage("    Mobile Data is Disabled in your Device \n            Please Enable Mobile Data?")
			        .setCancelable(false)
			        .setPositiveButton("Ok",
			            new DialogInterface.OnClickListener(){
			            public void onClick(DialogInterface dialog, int id){
			            }
			        });
			        	AlertDialog alert = alertDialogBuilder.create();
			        	alert.show();
						}
			}
		}
	/************************DUPLICATE PRINT ASYNCTASK******************************/
	
	/************************NETWORK AVAILABILITY******************************/
		private boolean isNetworkAvailable() {
		    ConnectivityManager connectivityManager 
		          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
		}
	/************************NETWORK AVAILABILITY******************************/
		
		
	/************************NETWORK AVAILABILITY******************************/	
	@SuppressWarnings("unused")
	protected String getServiceData(String selecteddate, String pidCd) {
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
				request.addProperty("pidCode", pidCd);
				
				Log.i("PID CODE ::", pidCd);
				 
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
				//Log.i("REsponse ::", DuplicatePrintActivity.challansListStr);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return response;
			}

	protected Dialog onCreateDialog(int id) {
	//	return new DatePickerDialog(this, datePickerListener, year, month, day);
		
		 Calendar c = Calendar.getInstance();
		    int cyear = c.get(Calendar.YEAR);
		    int cmonth = c.get(Calendar.MONTH);
		    int cday = c.get(Calendar.DAY_OF_MONTH);

		    DatePickerDialog dialog = new DatePickerDialog(this, datePickerListener, cyear, cmonth, cday);
	        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
	        return dialog;
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			select_date.setText(selectedDay + "/" + (selectedMonth + 1) + "/"
					+ selectedYear);

			Log.i("select_date :::", select_date.getText().toString());
			DupPrint_Date = select_date.getText().toString();
			
			Toast toast = Toast.makeText(getApplicationContext(), "Click Get and Please Wait for a Moment......!", Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 400);
			View toastView = toast.getView();
	    	toastView.setBackgroundResource(R.drawable.toast_background);
		    toast.show();
		}
	};
}