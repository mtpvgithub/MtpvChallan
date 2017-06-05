package com.mtpv.LawnOrder;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.analogics.thermalAPI.Bluetooth_Printer_3inch_ThermalAPI;
import com.analogics.thermalprinter.AnalogicsThermalPrinter;
import com.esys.leoparddemo.Act_BTDiscovery;
import com.evolute.bluetooth.BluetoothComm;

import com.example.button.R;
import com.leopard.api.FPS;
import com.leopard.api.Setup;    /// ESDAA0026

public class Challan_GenerationActivity extends Activity {

	public static TextView challan_text;
	Setup set ;
	//Printer_GEN prn;
	com.leopard.api.Printer ptr;
	FPS fps;
	public static String DEVICE_NAME="";
	@SuppressWarnings("unused")
	private static ProgressDialog prgDialog;
	ImageView back_status, print;
	
	final int PROGRESS_DIALOG = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_challan__generation);
		
		PreviewActivity.sections_text.setText(PreviewActivity.secBufferPreivew!=null?PreviewActivity.secBufferPreivew.toString():"");

		OffenderActivity.previewrespondentBuf=null;
		OffenderActivity.secBufferPreivew=null;
		OffenderActivity.seizedBuff=null;
		WitnessFormActivity.previewWitnessPromtBuf = null;
		WitnessFormActivity.ET_WitnessDetails="";
		OffenderActivity.seizedItems =null;
		OffenderActivity.secBuffer=null;
		SiezedItemsActivity.Ditenditems=null;
		PreviewActivity.secBuffer=null;
		PreviewActivity.secBufferPreivew=null;
		Add_SectionsActivity.checkedList=null;
		
		PreviewActivity.witnesFlg=false;
		
		challan_text = (TextView)findViewById(R.id.challan_text);
		challan_text.setText(PreviewActivity.challanGenresp);
		
		if(PreviewActivity.challanGenresp.length()>100){
				OffenderActivity.bufvalue1=null;
				OffenderActivity.bufvalue2=null;
				OffenderActivity.bufvalue3=null;
				OffenderActivity.bufvalue4=null;
			}
		/* try {
			//set=new Setup();
			boolean bl = set.blActivateLibrary(Challan_GenerationActivity.this, R.raw.licence_full);
			Log.e("Setup", "Activation Status: "+bl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		*/
		 
			
		back_status = (ImageView)findViewById(R.id.back_status);
		back_status.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(PreviewActivity.challanGenresp.length()<25){
					Intent back = new Intent(getApplicationContext(), PreviewActivity.class);
					finish();
					startActivity(back);
					
					PreviewActivity.challanGenresp="";
				}
				else {
					OffenderActivity.previewrespondentBuf=null;
					
					OffenderActivity.bufvalue1 = null ;
					OffenderActivity.bufvalue2 = null ;
					OffenderActivity.bufvalue3 = null ;
					OffenderActivity.bufvalue4 = null ;
					
					OffenderActivity.seizedImageInbyteArray = null;
					OffenderActivity.image1ByteArray = null;
					OffenderActivity.image2ByteArray = null;
					OffenderActivity.image3ByteArray = null;
					OffenderActivity.image4ByteArray = null;
					OffenderActivity.respImage1ByteArray = null;
					OffenderActivity.respImage2ByteArray = null;
					
					OffenderActivity.leaserImage1ByteArray = null;
					OffenderActivity.managerImage2ByteArray = null;
					
					OffenderActivity.secBufferPreivew=null;
					OffenderActivity.seizedBuff=null;
					
					WitnessFormActivity.previewWitnessPromtBuf = null;
					WitnessFormActivity.ET_WitnessDetails="";
					
					OffenderActivity.seizedItems =null;
					OffenderActivity.secBuffer=null;
					
					SiezedItemsActivity.Ditenditems=null;
					
					PreviewActivity.secBuffer=null;
					PreviewActivity.secBufferPreivew=null;
					
					Add_SectionsActivity.checkedList=null;
					Add_SectionsActivity.secMap=null;
					
					Intent complete_back = new Intent(getApplicationContext(), Menu_Dashboard_Activity.class);
					finish();
					startActivity(complete_back);
					PreviewActivity.challanGenresp="";
				}
			}
		});
		
		
		print = (ImageView)findViewById(R.id.print);
		print.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AsyncGetPrintDetails().execute();
				
				OffenderActivity.previewrespondentBuf=null;
				OffenderActivity.secBufferPreivew=null;
				OffenderActivity.seizedBuff=null;
				WitnessFormActivity.previewWitnessPromtBuf = null;
				WitnessFormActivity.ET_WitnessDetails="";
				OffenderActivity.seizedItems =null;
				OffenderActivity.secBuffer=null;
				SiezedItemsActivity.Ditenditems=null;
				PreviewActivity.secBuffer=null;
				PreviewActivity.secBufferPreivew=null;
				Add_SectionsActivity.checkedList=null;
				
	    	}
		});
	}
		
		/*class AsyncGetPrintDetails extends AsyncTask<Void,Void, String>{
			
			ProgressDialog dialog = new ProgressDialog(Challan_GenerationActivity.this);
				@SuppressWarnings("static-access")
				@Override
				protected String doInBackground(Void... params) {
					
					SQLiteDatabase db = openOrCreateDatabase(DataBase.DATABASE_NAME,MODE_PRIVATE, null);
					Log.i("DoInBackground Called ::", "AsyncGetPrintDetails");
	    			Log.i("Act_BTDiscovery.DEVICE_NAME ::", Act_BTDiscovery.DEVICE_NAME);
	    			try {
						String selectQuery = "SELECT  * FROM " + UserLogin_Activity.DEVICE_TABLE;
					     // SQLiteDatabase db = this.getWritableDatabase();
					    Cursor cursor = db.rawQuery(selectQuery, null);
				        
				        if (cursor.moveToFirst()) {
				            do {
				            
				            	UserLogin_Activity.bt_device = cursor.getString(0);
				            	Act_BTDiscovery.DEVICE_NAME=UserLogin_Activity.bt_device;
				            	Log.i("Device Name ::::", Act_BTDiscovery.DEVICE_NAME);
				            		            	 
				            } while (cursor.moveToNext());
				        }
					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						if(UserLogin_Activity.bt_device!=null && UserLogin_Activity.bt_device.length()>5){
							Log.e("TAG", "bt is connected");
							UserLogin_Activity.BT_Connect_flag = true;
					     try {
							if (UserLogin_Activity.BT_Connect_flag){						
								try {
									BluetoothComm btcomm = new BluetoothComm(UserLogin_Activity.bt_device);
									int iRetVal = btcomm.createConn();
									Log.e("iretvalue", "iretvalue:"+iRetVal);
									UserLogin_Activity.bluetooth_Flg = true;
									if (iRetVal==BluetoothComm.BT_CONNECTED){
										Log.e("TAG", "bt is connected");
				    					Log.i("set object ", ""+set);
				    					Log.i(" btcomm.mosOut ", ""+ btcomm.mosOut);
				    					Log.i("btcomm.misIn ", ""+btcomm.misIn);
				    					//Toast.makeText(getApplicationContext(), "Connection success", Toast.LENGTH_SHORT).show();
				    			//		ptr = new com.leopard.api.Printer(set, btcomm.mosOut,btcomm.misIn);
				    			//		fps=new com.leopard.api.FPS(set, btcomm.mosOut,btcomm.misIn);
									} else if (iRetVal == BluetoothComm.BT_NAME_INVALID){
										Toast.makeText(getApplicationContext(), "Invalid BT Name", Toast.LENGTH_SHORT).show();
									} else if (iRetVal == BluetoothComm.BT_NOT_AVAILABLE){
										Toast.makeText(getApplicationContext(), "Bluetooth Not Available", Toast.LENGTH_SHORT).show();
									} else if (iRetVal == BluetoothComm.BT_FAIL){
										Toast.makeText(getApplicationContext(), "Connection Failue", Toast.LENGTH_SHORT).show();
									} else if (iRetVal == BluetoothComm.BT_NOT_PAIRED){
										Toast.makeText(getApplicationContext(), "BT Device Not Paired", Toast.LENGTH_SHORT).show();
									} else if (iRetVal == -27){
										Toast.makeText(getApplicationContext(), "BT Device Not Switched On", Toast.LENGTH_SHORT).show();
									} else {
										Toast.makeText(getApplicationContext(), "Connection Failure", Toast.LENGTH_SHORT).show();
									}
									if(iRetVal==-27){
										Log.i("Entered 27 :::", "iRetVal==-27");
										btcomm.closeConn();
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							Log.e("TAG", "printer is instantiated");
								
					}catch (Exception e) {
						// TODO: handle exception
					}
						}
					}catch (Exception e) {
						// TODO: handle exception
					}
			if (!Act_BTDiscovery.DEVICE_NAME.equals("")) {
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
    				ptr.iPrinterAddData(com.leopard.api.Printer.PR_FONTSMALLNORMAL, challan_text.getText().toString());
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
					
					//PreviewActivity.challanGenresp="";
			}
			}*/
	
class AsyncGetPrintDetails extends AsyncTask<Void,Void, String>{
		
		ProgressDialog dialog = new ProgressDialog(Challan_GenerationActivity.this);
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
				
				String printdata=preparePrintData.font_Courier_36(""+ challan_text.getText().toString());
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
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				removeDialog(PROGRESS_DIALOG);	
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
