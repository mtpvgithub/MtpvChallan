package com.mtpv.LawnOrder;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.esys.leoparddemo.Act_Main;
import com.evolute.bluetooth.BluetoothComm;
import com.example.button.R;
import com.example.button.Settings_2;

public class Menu_Dashboard_Activity extends Activity {

	final int PROGRESS_DIALOG = 1;
static final String TAG_MAIN = "ID_DETAILS";
Context context = this;
static final String ID_CODE = "ID_CODE";
static final String ID_DETAILS = "ID_DETAILS";
@SuppressWarnings("unused")
private static final String LOGIN_TABLE = "LOGIN_DETAILS";
public static TextView officername, psname ;

ImageView generate_petty, duplicate_print, settings, download_masters, reports ,logout, previous_reports;
private static String NAMESPACE = "http://service.mother.com";
public static String methodIDMaster =  "getIDProofMaster";
//public static String methodAuth =  "authenticateUser";
public static String SOAP_ACTION = NAMESPACE + methodIDMaster;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_menu__dashboard_);
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		/*if(!UserLogin_Activity.BT_Connect_flag){
			Toast.makeText(getApplicationContext(), "Please do Bluetooth Settings", Toast.LENGTH_LONG).show();
		}*/
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            //Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
        }else{
            showGPSDisabledAlertToUser();
        }
	
		
		officername = (TextView)findViewById(R.id.officername);
		psname = (TextView)findViewById(R.id.psname);
		
		officername.setText(UserLogin_Activity.Pid_Name);
		psname.setText(UserLogin_Activity.Ps_Name);
		
		logout = (ImageView)findViewById(R.id.logout);
		logout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dlgExit();
			}
		});
		generate_petty = (ImageView)findViewById(R.id.generate_petty);
		generate_petty.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), OffenderActivity.class);
				startActivity(i);
			}
		});
		
		duplicate_print = (ImageView)findViewById(R.id.duplicate_print);
		duplicate_print.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*Intent d_print = new Intent(getApplicationContext(), DuplicatePrintActivity.class);
				startActivity(d_print);
				*/
				Intent d_print = new Intent(getApplicationContext(), Duplicate_SampleActivity.class);
				startActivity(d_print);
				
				//Duplicate_SampleActivity
			}
		});
		download_masters = (ImageView)findViewById(R.id.masters);
		download_masters.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AsyncGetMasters().execute();
			}
			@SuppressWarnings("unused")
			private SQLiteDatabase getWritableDatabase() {
				// TODO Auto-generated method stub
				return null;
			}
		});
		previous_reports = (ImageView)findViewById(R.id.previous_reports);
		previous_reports.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i_reports = new Intent(getApplicationContext(), PreviousHistoryActivity.class);
				startActivity(i_reports);
			}
		});
		reports = (ImageView)findViewById(R.id.reports);
		reports.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i_reports = new Intent(getApplicationContext(), ReportsActivity.class);
				startActivity(i_reports);
			}
		});
		settings = (ImageView)findViewById(R.id.settings);
		settings.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			//Intent i_btfp = new Intent(getApplicationContext(), Act_Main.class);
				Intent i_btfp = new Intent(getApplicationContext(), Settings_2.class);
				startActivity(i_btfp);
			}
		});
		}
		
		private void showGPSDisabledAlertToUser(){
	        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	        alertDialogBuilder.setMessage("    GPS is Disabled in your Device \n            Please Enable GPS?")
	        .setCancelable(false)
	        .setPositiveButton("Goto Settings",
	                new DialogInterface.OnClickListener(){
	            public void onClick(DialogInterface dialog, int id){
	                Intent callGPSSettingIntent = new Intent(
	                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	                startActivity(callGPSSettingIntent);
	            }
	        });
	        alertDialogBuilder.setNegativeButton("Cancel",
	                new DialogInterface.OnClickListener(){
	            public void onClick(DialogInterface dialog, int id){
	                dialog.cancel();
	            }
	        });
	        AlertDialog alert = alertDialogBuilder.create();
	        alert.show();
	    }

		public void dlgExit() {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			// set title
			alertDialogBuilder.setTitle("Hederabad ePetty Case Application");
			alertDialogBuilder.setIcon(R.drawable.hyd_city_small);
			alertDialogBuilder.setMessage(
					"Do you want to Logout from Application");
			alertDialogBuilder.setCancelable(false);
			alertDialogBuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
				
					try {
						try {
							BluetoothComm.mosOut.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							BluetoothComm.misIn.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch(NullPointerException e) {
						e.printStackTrace();
					}
					System.gc();
					Intent logout = new Intent(getApplicationContext(), UserLogin_Activity.class);
					finish();
					startActivity(logout);
				}
			});
			alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int id) {
					// if this button is clicked, just close
					// the dialog box and do nothing
					dialog.cancel();
				}
			});
			
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
		}
	
		private boolean isNetworkAvailable() {
		    ConnectivityManager connectivityManager 
		          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
		}
		
		class AsyncGetMasters extends AsyncTask<Void,Void, String>{
		ProgressDialog dialog = new ProgressDialog(Menu_Dashboard_Activity.this);
		@Override
		protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
			Log.d("getMastersDownload method called ", "YES");
			String resp="";
			try {
				String ip="";
				try {
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
				} catch (Exception e) {
					e.printStackTrace();
				}
				// INSERT ID PROOF DETAILS START
				SoapObject request = new SoapObject(NAMESPACE, "getIDProofMaster");
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				Log.i("request", "" + request);
				//Log.i("WebService.SOAP_ADDRESS ::::::::::::::", "" + WebService.SOAP_ADDRESS);
				HttpTransportSE androidHttpTransport = new HttpTransportSE(ip+"/HydPettyCaseService/services/PettyCaseServiceImpl?wsdl");
				Log.i("androidHttpTransport", "" + androidHttpTransport);
				androidHttpTransport.call(SOAP_ACTION, envelope);
				Object result = envelope.getResponse();
				resp = result.toString();
				Log.i("** JSON ID DETAILS master response***", "" + resp);
				if(resp!=null){
					//insertIDMaster(resp,getApplicationContext());
					new DataBase(getApplicationContext()).insertIDMaster(resp,getApplicationContext());
				}
			// INSERT ID PROOF DETAILS  END
				// INSERT SECTION MASTER DETAILS START
				request = new SoapObject(NAMESPACE, "getSectionDetails");
				envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				Log.i("request", "" + request);
				//Log.i("WebService.SOAP_ADDRESS ::::::::::::::", "" + WebService.SOAP_ADDRESS);
				androidHttpTransport = new HttpTransportSE(ip+"/HydPettyCaseService/services/PettyCaseServiceImpl?wsdl");
				Log.i("androidHttpTransport", "" + androidHttpTransport);
				SOAP_ACTION = NAMESPACE + "getSectionDetails";
				androidHttpTransport.call(SOAP_ACTION, envelope);
				result = envelope.getResponse();
				resp = result.toString();
				Log.i("** JSON SECTION DETAILS master response***", "" + resp);
				if(resp!=null){
					//insertIDMaster(resp,getApplicationContext());
					new DataBase(getApplicationContext()).insertSectionMaster(resp,getApplicationContext());
				}
				// INSERT SECTION MASTER DETAILS  END
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
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				removeDialog(PROGRESS_DIALOG);
				if (!isNetworkAvailable()) {
				    // do something
			        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Menu_Dashboard_Activity.this);
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
				
				AlertDialog alertDialog = new AlertDialog.Builder(Menu_Dashboard_Activity.this).create();
				alertDialog.setTitle("Alert");
				alertDialog.setMessage("MASTER DOWNLOAD SUCCESS");
				alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
				    new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
				        }
				    });
				alertDialog.show();
				}
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

			default:
				break;
			}
			return super.onCreateDialog(id);
		}
			@Override
			public void onBackPressed() {
				
			}
		}
