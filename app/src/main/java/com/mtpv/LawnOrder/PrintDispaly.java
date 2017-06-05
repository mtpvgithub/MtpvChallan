package com.mtpv.LawnOrder;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.analogics.thermalAPI.Bluetooth_Printer_3inch_ThermalAPI;
import com.analogics.thermalprinter.AnalogicsThermalPrinter;
import com.esys.leoparddemo.Act_BTDiscovery;
import com.evolute.bluetooth.BluetoothComm;
import com.example.button.R;
import com.leopard.api.Setup;

public class PrintDispaly extends Activity {
	
	public static TextView Tv;
	Button back_Btn;
	String Pidcode , PidName, PsCode, PsName, cadreCD,cadre,password;
	String generateChallan;
	ImageView dup_print;
	
	Setup set ;
	com.leopard.api.Printer ptr;
	//String BT_Name1 = com.tab39b.Settings.BT_Name;
	
	String bluetoot_ID;
	String BT_Name;
	
	final int PROGRESS_DIALOG = 1;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.printdisplay);
		
		try {
			//set=new Setup();
			boolean bl = set.blActivateLibrary(PrintDispaly.this, R.raw.licence_full);
			Log.e("Setup", "Activation Status: "+bl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	
		back_Btn = (Button)findViewById(R.id.back_Btn);
		dup_print = (ImageView)findViewById(R.id.dup_print);
		
		dup_print.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//new AsyncGetPrintDetails().execute();
				
				new AsyncPrintDetails().execute();
			}
		});
	

		Tv = (TextView)findViewById(R.id.printTv);
		
		DataBase DBH = new DataBase(getApplicationContext());
		String bluetoothQuery = "select BT_Name from bluetooth";
		Cursor res = DBH.Eq(bluetoothQuery);
		res.moveToFirst();
		 while(res.isAfterLast() == false){
			 BT_Name = res.getString(res.getColumnIndex("BT_Name"));		
	         res.moveToNext();
		 }
		try{
			
		bluetoot_ID = BT_Name.split("\\r?\\n")[1];
		
		}catch(Exception e){
		//Toast.makeText(getApplicationContext(), "Please set Bluetooth in setting", Toast.LENGTH_LONG).show();
		}
		
		back_Btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(PrintDispaly.this, Menu_Dashboard_Activity.class); 
				finish();
				startActivity(i);
				
			}
		});
		
		
		 Intent intent = getIntent();
		 generateChallan = intent.getStringExtra("generateChallan");
		    Log.e("generateChallan TEXTVIEW:: ", generateChallan);
		    if(!"0^NA^NA".equals(generateChallan.trim())){
		    	Tv.setText(generateChallan);
		     }else{
		    	 
		    	 Tv.setText("Ticket Generation failed");
		     }
		   
		
	}
		
	class AsyncGetPrintDetails extends AsyncTask<Void,Void, String>{
	ProgressDialog dialog = new ProgressDialog(PrintDispaly.this);
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
				ptr.iPrinterAddData(com.leopard.api.Printer.PR_FONTSMALLNORMAL, Tv.getText().toString());
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
		}
	
	class AsyncPrintDetails extends AsyncTask<Void,Void, String>{
		//ProgressDialog progress = ProgressDialog.show(Reports.this, "Loading...!", "Please wait......Processing!!!");
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showDialog(PROGRESS_DIALOG);
		}
		
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
			
			String printdata=preparePrintData.font_Courier_36(""+Tv.getText().toString());
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
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			removeDialog(PROGRESS_DIALOG);
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
			return null; 
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