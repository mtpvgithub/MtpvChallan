package com.mtpv.LawnOrder;

import java.util.Calendar;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

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
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.analogics.thermalAPI.Bluetooth_Printer_3inch_ThermalAPI;
import com.analogics.thermalprinter.AnalogicsThermalPrinter;
import com.esys.leoparddemo.Act_BTDiscovery;
import com.evolute.bluetooth.BluetoothComm;
import com.example.button.R;
import com.leopard.api.FPS;
import com.leopard.api.Setup;

public class ReportsActivity extends Activity {
	final int PROGRESS_DIALOG = 1;
	public static final int TIME_DIALOG_ID = 0;
	EditText from_date, to_date, select_date;
	@SuppressWarnings("unused")
	private Calendar cal;
	@SuppressWarnings("unused")
	private int day,month,year;
	ImageView report_submit, report_print, back_btn;
	TextView reports_text;
	Setup set ;
	Spinner reports_type;
	com.leopard.api.Printer ptr;
	public static boolean fromFLG=false, toFLG=false ;
	public static boolean offcerFLG=false, psdsrFLG=false ;
	FPS fps;
	@SuppressWarnings("unused")
	private static String challanGenresp ="";
	
	public static String officerdsr, psdsr, reporttype;
	private static String NAMESPACE = "http://service.mother.com";
	public static String SOAP_ACTION = NAMESPACE;
	public static String SOAP_ACTION_ID = NAMESPACE + "getOfficerReport", officerchallanResponse;
	public static String SOAP_ACTION_ID2 = NAMESPACE + "getPSDsrReport", dsrchallanResponse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_reports);
		
/*		cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);*/
		try {
		//	//set=new Setup();
			boolean bl = set.blActivateLibrary(ReportsActivity.this, R.raw.licence_full);
			Log.e("Setup", "Activation Status: "+bl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		reports_type = (Spinner)findViewById(R.id.reports_type);
		
		reports_type.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3){
				// TODO Auto-generated method stub
				switch(arg2) {
				case 0 :
					break;
				case 1 :
						officerdsr = reports_type.getSelectedItem().toString();
						offcerFLG = true;
					break;
				case 2 :
						psdsr = reports_type.getSelectedItem().toString();
						psdsrFLG = true;
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
				}
			});
		reports_text = (TextView)findViewById(R.id.reports_text);
		
		back_btn = (ImageView)findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent i = new Intent(getApplicationContext(),Menu_Dashboard_Activity.class);
				 i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				 startActivity(i);
				}
			});
		
		report_print = (ImageView)findViewById(R.id.report_print);
		report_print.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AsyncGetPrintDetails().execute();
				}
			});
		report_submit = (ImageView)findViewById(R.id.report_submit);
		report_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(offcerFLG){
				new AsyncGetOFFicerReports().execute();
				}
				if(psdsrFLG){
				new AsyncGetPSReports().execute();
				}
			}
		});
		
		 
		
		 from_date=(EditText)findViewById(R.id.from_date);
		 from_date.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				fromFLG= true;
				toFLG = false;
				showDialog(0);
			}
		}) ;
		 
		 to_date=(EditText)findViewById(R.id.to_date);
		 to_date.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				toFLG=true;
				fromFLG = false;
				showDialog(TIME_DIALOG_ID);
			}
		}); 
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch (id) {
		case PROGRESS_DIALOG:
			ProgressDialog pd = ProgressDialog.show(this, "", "",	true);
			pd.setContentView(R.layout.custom_progress_dialog);
			pd.setCancelable(false); 
			
			return pd;
			
			
		case TIME_DIALOG_ID:
			Calendar c = Calendar.getInstance();
		    int cyear = c.get(Calendar.YEAR);
		    int cmonth = c.get(Calendar.MONTH);
		    int cday = c.get(Calendar.DAY_OF_MONTH);

		    DatePickerDialog dialog = new DatePickerDialog(this, datePickerListener, cyear, cmonth, cday);
	        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
	        return dialog;

		default:
			break;
		}
		return super.onCreateDialog(id);
	}
	
	/*protected Dialog onCreateDialog(int id) {

		
		Calendar c = Calendar.getInstance();
	    int cyear = c.get(Calendar.YEAR);
	    int cmonth = c.get(Calendar.MONTH);
	    int cday = c.get(Calendar.DAY_OF_MONTH);

	    DatePickerDialog dialog = new DatePickerDialog(this, datePickerListener, cyear, cmonth, cday);
        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        return dialog;
	}*/

private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
	public void onDateSet(DatePicker view, int selectedYear,
			int selectedMonth, int selectedDay) {
		if (fromFLG) {
			from_date.setText(selectedDay + "/" + (selectedMonth + 1) + "/"
					+ selectedYear);
		}
		if (toFLG) {
			to_date.setText(selectedDay + "/" + (selectedMonth + 1) + "/"
					+ selectedYear);
		}
	}
};
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	
	
	class AsyncGetOFFicerReports extends AsyncTask<Void,Void, String>{
		ProgressDialog dialog = new ProgressDialog(ReportsActivity.this);
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
				String Report_from_Date = from_date.getText().toString();
				String Report_to_Date = to_date.getText().toString();
				
				try {
					SQLiteDatabase db = openOrCreateDatabase(DataBase.DATABASE_NAME,MODE_PRIVATE, null);
					String selectQuery = "SELECT  * FROM " + DataBase.LOGIN_TABLE;
				     // SQLiteDatabase db = this.getWritableDatabase();
				    Cursor cursor = db.rawQuery(selectQuery, null);
			       // looping through all rows and adding to list
				    
			        if (cursor.moveToFirst()) {
			            do {
			            
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
					SoapObject request = new SoapObject(NAMESPACE, "getOfficerReport");
				//	Log.i("id_text_prev_hist :::::::::::::",reports_text.getText().toString());
					Log.i("fromDate", Report_from_Date);
					Log.i("toDate", Report_to_Date);
					
					
				    request.addProperty("fromDate",Report_from_Date);
				    request.addProperty("toDate",Report_to_Date);
					request.addProperty("pidCode", pidCode);
					
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.dotNet = true;
					envelope.setOutputSoapObject(request);
					Log.i("request", "" + request);
					HttpTransportSE androidHttpTransport = new HttpTransportSE(ip+"/HydPettyCaseService/services/PettyCaseServiceImpl?wsdl");
					Log.i("androidHttpTransport", "" + androidHttpTransport);
					androidHttpTransport.call(SOAP_ACTION_ID, envelope);
					Object result = envelope.getResponse();
					officerchallanResponse=result.toString();
					Log.i("** Officer response***", "" + officerchallanResponse);
					runOnUiThread(new Runnable() {
					    public void run(){ 
					    	if(officerchallanResponse!=null){
								   try {
									   reports_text.setVisibility(View.VISIBLE);
									   reports_text.setText(officerchallanResponse);
									   report_print.setVisibility(View.VISIBLE);
									   
								   		}
								   		catch(Exception e){
					                    	e.printStackTrace();
					                    }
								}else{
									Log.i("no data from service", "no ");
									reports_text.setVisibility(View.VISIBLE);
									reports_text.setText(officerchallanResponse);
							}
					    }
					});
					//					// INSERT ID PROOF DETAILS  END
				} catch (Exception e) {
					e.printStackTrace();
				}
	
				return null;
			}

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				showDialog(PROGRESS_DIALOG);
		        /*    dialog.setMessage("Please wait.....Processing!");
		            dialog.setIndeterminate(true);
		            dialog.setCancelable(false);
		            dialog.show();*/
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				//dialog.dismiss();
				removeDialog(PROGRESS_DIALOG);
				if (!isNetworkAvailable()) {
				    // do something
			        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ReportsActivity.this);
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
	
	class AsyncGetPSReports extends AsyncTask<Void,Void, String>{
		ProgressDialog dialog = new ProgressDialog(ReportsActivity.this);
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
				String Report_from_Date = from_date.getText().toString();
				String Report_to_Date = to_date.getText().toString();
				
				try {
					SQLiteDatabase db = openOrCreateDatabase(DataBase.DATABASE_NAME,MODE_PRIVATE, null);
					String selectQuery = "SELECT  * FROM " + DataBase.LOGIN_TABLE;
				     // SQLiteDatabase db = this.getWritableDatabase();
				    Cursor cursor = db.rawQuery(selectQuery, null);
			       // looping through all rows and adding to list
				    
			        if (cursor.moveToFirst()) {
			            do {
			            
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
					SoapObject request = new SoapObject(NAMESPACE, "getPSDsrReport");
				//	Log.i("id_text_prev_hist :::::::::::::",reports_text.getText().toString());
					Log.i("fromDate", Report_from_Date);
					Log.i("toDate", Report_to_Date);
					
					
				    request.addProperty("fromDate",Report_from_Date);
				    request.addProperty("toDate",Report_to_Date);
					request.addProperty("psCode", psCode);
					
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.dotNet = true;
					envelope.setOutputSoapObject(request);
					Log.i("request", "" + request);
					HttpTransportSE androidHttpTransport = new HttpTransportSE(ip+"/HydPettyCaseService/services/PettyCaseServiceImpl?wsdl");
					Log.i("androidHttpTransport", "" + androidHttpTransport);
					androidHttpTransport.call(SOAP_ACTION_ID2, envelope);
					Object result = envelope.getResponse();
					dsrchallanResponse=result.toString();
					Log.i("** DSR response***", "" + dsrchallanResponse);
					runOnUiThread(new Runnable() {
					    public void run(){ 
					    	if(dsrchallanResponse!=null){
								   try {
									   reports_text.setVisibility(View.VISIBLE);
									   reports_text.setText(dsrchallanResponse);
									   report_print.setVisibility(View.VISIBLE);
									   
								   		}
								   		catch(Exception e){
					                    	e.printStackTrace();
					                    }
								}else{
									Log.i("no data from service", "no ");
									reports_text.setVisibility(View.VISIBLE);
									reports_text.setText(dsrchallanResponse);
							}
					    }
					});
					//					// INSERT ID PROOF DETAILS  END
				} catch (Exception e) {
					e.printStackTrace();
				}
	
				return null;
			}

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				showDialog(PROGRESS_DIALOG);
		        /*    dialog.setMessage("Please wait.....Processing!");
		            dialog.setIndeterminate(true);
		            dialog.setCancelable(false);
		            dialog.show();*/
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				//dialog.dismiss();
				removeDialog(PROGRESS_DIALOG);
				if (!isNetworkAvailable()) {
				    // do something
			        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ReportsActivity.this);
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
	
	/*class AsyncGetReports extends AsyncTask<Void,Void, String>{
		
		ProgressDialog dialog = new ProgressDialog(ReportsActivity.this);
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
				String Report_from_Date = from_date.getText().toString();
				String Report_to_Date = to_date.getText().toString();
				
				try {
					SQLiteDatabase db = openOrCreateDatabase(DataBase.DATABASE_NAME,MODE_PRIVATE, null);
					String selectQuery = "SELECT  * FROM " + DataBase.LOGIN_TABLE;
				     // SQLiteDatabase db = this.getWritableDatabase();
				    Cursor cursor = db.rawQuery(selectQuery, null);
			       // looping through all rows and adding to list
				    
			        if (cursor.moveToFirst()) {
			            do {
			            
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
					SoapObject request = new SoapObject(NAMESPACE, "getReport");
				//	Log.i("id_text_prev_hist :::::::::::::",reports_text.getText().toString());
					
				    request.addProperty("offenceDate",Report_from_Date);
					request.addProperty("pidCode", pidCode);
					
					Log.i("offenceDate", Report_from_Date);
					
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.dotNet = true;
					envelope.setOutputSoapObject(request);
					Log.i("request", "" + request);
					HttpTransportSE androidHttpTransport = new HttpTransportSE(ip+"/HydPettyCaseService/services/PettyCaseServiceImpl?wsdl");
					Log.i("androidHttpTransport", "" + androidHttpTransport);
					androidHttpTransport.call(SOAP_ACTION_ID, envelope);
					Object result = envelope.getResponse();
					challanGenresp=result.toString();
					Log.i("** getDetailsByAADHAR response***", "" + challanGenresp);
					runOnUiThread(new Runnable() {
					    public void run(){ 
					    	if(challanGenresp!=null){
								   try {
									   reports_text.setVisibility(View.VISIBLE);
									   reports_text.setText(challanGenresp);
									   report_print.setVisibility(View.VISIBLE);
									   
								   		}
								   		catch(Exception e){
					                    	e.printStackTrace();
					                    }
								}else{
									Log.i("no data from service", "no ");
									reports_text.setVisibility(View.VISIBLE);
									reports_text.setText(challanGenresp);
							}
					    }
					});
					//					// INSERT ID PROOF DETAILS  END
				} catch (Exception e) {
					e.printStackTrace();
				}
	
				return null;
			}

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				//showDialog(PROGRESS_DIALOG);
		            dialog.setMessage("Please wait.....Processing!");
		            dialog.setIndeterminate(true);
		            dialog.setCancelable(false);
		            dialog.show();
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				dialog.dismiss();	
				if (!isNetworkAvailable()) {
				    // do something
			        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ReportsActivity.this);
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
		}*/
	
	
	
	
	/*class AsyncGetPrintDetails extends AsyncTask<Void,Void, String>{
		
		ProgressDialog dialog = new ProgressDialog(ReportsActivity.this);
			@SuppressWarnings("static-access")
			@Override
			protected String doInBackground(Void... params) {
				
				Log.i("DoInBackground Called ::", "AsyncGetPrintDetails");
    			Log.i("Act_BTDiscovery.DEVICE_NAME ::", Act_BTDiscovery.DEVICE_NAME);
				if (!Act_BTDiscovery.DEVICE_NAME.equals("")) {
				//Toast.makeText(getApplicationContext(), "Scan device", Toast.LENGTH_SHORT).show();
			
		//	progressDialog(getApplicationContext(), "Please Wait...\nConnecting to "+Act_BTDiscovery.DEVICE_NAME);
			try {
				BluetoothComm btcomm = new BluetoothComm(Act_BTDiscovery.DEVICE_NAME);
				if(btcomm.isConnect()){
					Log.i("connection status :", "connected in challan generation class");
    				int iRetVal = btcomm.createConn();
    				Log.e("TAG", "iretvalue:"+iRetVal);
    				if (iRetVal==BluetoothComm.BT_CONNECTED){
    					Log.e("TAG", "bt is connected");
    					//prn=new Printer_ESC(instSetup, outSt, inSt)
    					Log.i("set object ", ""+set);
    					Log.i(" btcomm.mosOut ", ""+ btcomm.mosOut);
    					Log.i("btcomm.misIn ", ""+btcomm.misIn);
    					ptr = new com.leopard.api.Printer(set, btcomm.mosOut,btcomm.misIn);
    					//fps=new com.leopard.api.FPS(set, btcomm.mosOut,btcomm.misIn);
    					Log.e("TAG", "printer is instantiated");
    				} else if (iRetVal == BluetoothComm.BT_NAME_INVALID){
    					Toast.makeText(getApplicationContext(), "Invalid BT Name", Toast.LENGTH_SHORT).show();
    				} else if (iRetVal == BluetoothComm.BT_NOT_AVAILABLE){
    					Toast.makeText(getApplicationContext(), "Bluetooth Not Available", Toast.LENGTH_SHORT).show();
    				} else if (iRetVal == BluetoothComm.BT_FAIL){
    					Toast.makeText(getApplicationContext(), "Connection Failue", Toast.LENGTH_SHORT).show();
    				} else if (iRetVal == BluetoothComm.BT_NOT_PAIRED){
    					Toast.makeText(getApplicationContext(), "BT Device Not Paired", Toast.LENGTH_SHORT).show();
    				} else {
    					Toast.makeText(getApplicationContext(), "Connection Failure", Toast.LENGTH_SHORT).show();
    				}
				}else{
					Log.i("connection status :", "Already  connected");
					ptr = new com.leopard.api.Printer(set, btcomm.mosOut,btcomm.misIn);
					//fps=new com.leopard.api.FPS(set, btcomm.mosOut,btcomm.misIn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
    		try {
				ptr.iFlushBuf();
			//	ptr.iPrinterAddData(com.leopard.api.Printer.PR_FONTSMALLBOLD, challan_text.getText().toString());
			//	ptr.iPrinterAddData(com.leopard.api.Printer.PR_FONT180SMALLBOLD, challan_text.getText().toString());
				ptr.iPrinterAddData(com.leopard.api.Printer.PR_FONTSMALLNORMAL, reports_text.getText().toString());
				ptr.iStartPrinting(1);
				 
				//prn.iUnicodePrint(byteArray);
			
			} catch (Exception e) {
				Log.e("Exception :", "Excep "+e );
				e.printStackTrace();
			}
			}
	
				return null;
			}

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				//showDialog(PROGRESS_DIALOG);
		            dialog.setMessage("Please wait Printing in Progress.....!");
		            dialog.setIndeterminate(true);
		            dialog.setCancelable(false);
		            dialog.show();
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				dialog.dismiss();	
			}
		}*/
	
	class AsyncGetPrintDetails extends AsyncTask<Void,Void, String>{
		
		ProgressDialog dialog = new ProgressDialog(ReportsActivity.this);
			@SuppressWarnings("static-access")
			@Override
			protected String doInBackground(Void... params) {

				// TODO Auto-generated method stub
				String bt = "" ;
				DataBase helper= new DataBase(getApplicationContext());
				try {
					android.database.sqlite.SQLiteDatabase db = openOrCreateDatabase(DataBase.DATABASE_NAME,MODE_PRIVATE, null);
					String selectQuery = "SELECT  * FROM " + DataBase.Bluetooth;
				     //SQLiteDatabase db = this.getWritableDatabase();
				    Cursor cursor = db.rawQuery(selectQuery, null);
			       // looping through all rows and adding to list
				    
			        if (cursor.moveToFirst()) {
			            do {
			            	
			            	Log.i("1 :",""+ cursor.getString(0));
			            	bt = cursor.getString(0);
			           	 	
			           	 	//et_bt_address.setText(BLT_Name);
			           	 
		            		} while (cursor.moveToNext());
			        		}
							db.close(); 
						} catch (Exception e) {
							e.printStackTrace();
					
					}
				//Bluetooth_Printer_3inch_ThermalAPI   preparePrintData= new Bluetooth_Printer_3inch_ThermalAPI();
				try{
					//Toast.makeText(getApplicationContext(), "print BUTTON ", Toast.LENGTH_LONG).show();
				
				Bluetooth_Printer_3inch_ThermalAPI   preparePrintData= new Bluetooth_Printer_3inch_ThermalAPI();
				
					//Toast.makeText(getApplicationContext(), "print " +generateChallan, Toast.LENGTH_LONG).show();
				
				String printdata=preparePrintData.font_Courier_36(""+reports_text.getText().toString());
				AnalogicsThermalPrinter printer=new AnalogicsThermalPrinter();
				printer.Call_PrintertoPrint(bt,printdata);
				
					//Toast.makeText(getApplicationContext(), "print ", Toast.LENGTH_LONG).show();
				}catch(Exception e){
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							showToast("Turn on Bluetooth ");
						}
					});
				}
				
				return null;
			}

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				showDialog(PROGRESS_DIALOG);
				/*dialog.setContentView(R.layout.custom_progress_dialog);
				dialog.setCancelable(false); 
				dialog.show();*/
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				//dialog.dismiss();
				removeDialog(PROGRESS_DIALOG);
			}
		}
	
	
	private void showToast(String msg) {
		// TODO Auto-generated method stub
		Toast toast = Toast.makeText(getApplicationContext(), "" + msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		View toastView = toast.getView();
		
		ViewGroup group = (ViewGroup) toast.getView();
	    TextView messageTextView = (TextView) group.getChildAt(0);
	    messageTextView.setTextSize(24);
		
    	toastView.setBackgroundResource(R.drawable.toast_background);
	    toast.show();
	}
	
	@Override
	public void onBackPressed() {
		
	}


}
