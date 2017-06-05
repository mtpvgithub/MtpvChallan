package com.esys.leoparddemo;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.evolute.bluetooth.BluetoothComm;
import com.example.button.R;
import com.leopard.api.FPS;
import com.leopard.api.Setup;
import com.mtpv.LawnOrder.DataBase;
import com.mtpv.LawnOrder.Menu_Dashboard_Activity;


public class Act_BTDiscovery extends Activity implements OnClickListener{
	Setup set ;
	//Printer_GEN prn;
	com.leopard.api.Printer ptr;
	FPS fps;
	public static String DEVICE;
	
	public static String DEVICE_NAME="ESDAA0026";
	private static ProgressDialog prgDialog;
	/**CONST: device type bluetooth 2.1*/
	public static final int DEVICE_TYPE_BREDR = 0x01;
	/**CONST: device type bluetooth 4.0 ble*/
	public static final int DEVICE_TYPE_BLE = 0x02;
	/**CONST: device type bluetooth double mode*/
	public static final int DEVICE_TYPE_DUMO = 0x03;
	public final static String EXTRA_DEVICE_TYPE = "android.bluetooth.device.extra.DEVICE_TYPE";
	/** Discovery is Finished */
	private boolean _discoveryFinished;
	public static BluetoothAdapter mBT = BluetoothAdapter.getDefaultAdapter();
	/**bluetooth List View*/
	private ListView mlvList = null;
	/**
	 * Storage the found bluetooth devices
	 * format:<MAC, <Key,Val>>;Key=[RSSI/NAME/COD(class od device)/BOND/UUID]
	 * */
	private Hashtable<String, Hashtable<String, String>> mhtFDS = null;
	
	/**(List of storage arrays for display) dynamic array of objects / ** ListView's */
	private ArrayList<HashMap<String, Object>> malListItem = null;
	/**SimpleAdapter object (list display container objects)*/
	Context context = this;
	private SimpleAdapter msaListItemAdapter = null;

	/**
	 * Scan for Bluetooth devices. (broadcast listener)
	 */
	private BroadcastReceiver _foundReceiver = new BroadcastReceiver(){
		public void onReceive(Context context, Intent intent){
			/* bluetooth device profiles*/
			Hashtable<String, String> htDeviceInfo = new Hashtable<String, String>();
			Log.d(getString(R.string.app_name), ">>Scan for Bluetooth devices");
			
			/* get the search results */
			BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			/* create found device profiles to htDeviceInfo*/
			Bundle b = intent.getExtras();
			htDeviceInfo.put("RSSI", String.valueOf(b.get(BluetoothDevice.EXTRA_RSSI)));
			if (null == device.getName())
				htDeviceInfo.put("NAME", "Null");
			else
			htDeviceInfo.put("NAME", device.getName());
			
			htDeviceInfo.put("COD",  String.valueOf(b.get(BluetoothDevice.EXTRA_CLASS)));
			if (device.getBondState() == BluetoothDevice.BOND_BONDED)
				htDeviceInfo.put("BOND", getString(R.string.actDiscovery_bond_bonded));
			else
				htDeviceInfo.put("BOND", getString(R.string.actDiscovery_bond_nothing));
			
			String sDeviceType = String.valueOf(b.get(EXTRA_DEVICE_TYPE));
			if (!sDeviceType.equals("null"))
				htDeviceInfo.put("DEVICE_TYPE", sDeviceType);
			else
				htDeviceInfo.put("DEVICE_TYPE", "-1"); 

			/*adding scan to the device profiles*/
			mhtFDS.put(device.getAddress(), htDeviceInfo);
			
			/*Refresh show list*/
			showDevices();
		}
	};	
	/**
	 * Bluetooth scanning is finished processing.(broadcast listener)
	 */
	private BroadcastReceiver _finshedReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent){
			Log.d(getString(R.string.app_name), ">>Bluetooth scanning is finished");
			_discoveryFinished = true; //set scan is finished
			unregisterReceiver(_foundReceiver);
			unregisterReceiver(_finshedReceiver);
			
			
			if (null != mhtFDS && mhtFDS.size()>0){	
				Toast.makeText(Act_BTDiscovery.this, 
							   getString(R.string.actDiscovery_msg_select_device),
							   Toast.LENGTH_SHORT).show();
			}else{	
				Toast.makeText(Act_BTDiscovery.this, 
						   getString(R.string.actDiscovery_msg_not_find_device),
						   Toast.LENGTH_LONG).show();
			}
		}
	};
	Button btn_Scan,btn_Exit;
	
	/**
	 * start run
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_btdiscovery);
		
		btn_Scan = (Button)findViewById(R.id.btn_Scan);
		btn_Scan.setOnClickListener(this);
		
		 try {
				set=new Setup();
				boolean bl = set.blActivateLibrary(Act_BTDiscovery.this, R.raw.licence_full);
				Log.e("Setup", "Activation Status: "+bl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		btn_Exit = (Button)findViewById(R.id.btn_Exit);
		btn_Exit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dlgExit();
			}
		});
		
		
		this.mlvList = (ListView)this.findViewById(R.id.actDiscovery_lv);
    	this.mlvList.setOnItemClickListener(new OnItemClickListener(){  
            @SuppressWarnings({ "unused", "static-access" })
			@Override  
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3){  
            	
            	String sMAC = ((TextView)arg1.findViewById(R.id.device_item_ble_mac)).getText().toString();
                Log.i("bluetooth mac id:::", sMAC);
                Log.i("bluetooh name:::",mhtFDS.get(sMAC).get("NAME"));
                
                progressDialog(context, "Please Wait...\nConnecting to "+DEVICE_NAME);
                DEVICE_NAME = mhtFDS.get(sMAC).get("NAME");
    			if (DEVICE_NAME.equals("")) {
    				Toast.makeText(context, "Scan device", Toast.LENGTH_SHORT).show();
    				return;
    			}else{
					String Bluetooth_Device = DEVICE_NAME;
					DataBase DBH= new DataBase(getApplicationContext());
					
					Log.e("DEVICE", Bluetooth_Device);
					finish();
					ContentValues values = new ContentValues();
        			values.put("DEVICENAME", Bluetooth_Device);
					SQLiteDatabase db = openOrCreateDatabase(DataBase.DATABASE_NAME,MODE_PRIVATE, null);
        			db.execSQL(DataBase.CREATE_DEVICE_TABLE);
        			db.execSQL("delete from " + DataBase.DEVICE_TABLE);            			
          			db.insert(DataBase.DEVICE_TABLE, null, values); // Inserting Row
        			
        			 System.out.println("********************* TABLE Insertion Successfully **********************************");
        			 
        			 
        			 String selectQuery = "SELECT  * FROM " + DataBase.DEVICE_TABLE;
        		       // SQLiteDatabase db = this.getWritableDatabase();
        		        Cursor cursor = db.rawQuery(selectQuery, null);
        		 
        		        // looping through all rows and adding to list
        		        if (cursor.moveToFirst()) {
        		            do {
        		            	 Log.i("DEVICE NAME :",""+ cursor.getString(0));
        		            	 Bluetooth_Device = cursor.getString(0);
        		                
        		            } while (cursor.moveToNext());
        		        }
        			 db.close();
        			 
					Toast.makeText(getApplicationContext(), "DEVICE NAME NOT RECOGNISED", Toast.LENGTH_SHORT).show();
					
					} 
    			
    			try {
    				/*SQLiteDatabase db = openOrCreateDatabase(DataBase.DATABASE_NAME,MODE_PRIVATE, null);
    				 String selectQuery = "SELECT  * FROM " + DataBase.DEVICE_TABLE;
      		        Cursor cursor = db.rawQuery(selectQuery, null);
      		        if (cursor.moveToFirst()) {
      		            do {
      		            	 Log.i("DEVICE NAME :",""+ cursor.getString(0));
      		            	 Bluetooth_Device = cursor.getString(0);
      		                
      		            } while (cursor.moveToNext());
      		        }
      			   db.close();*/
    				BluetoothComm btcomm = new BluetoothComm(DEVICE_NAME);
    				
    				int iRetVal = btcomm.createConn();
    				Log.e("TAG", "iretvalue:"+iRetVal);
    				if (iRetVal==BluetoothComm.BT_CONNECTED){
    					Log.e("TAG", "bt is connected");
    					//prn=new Printer_ESC(instSetup, outSt, inSt)
    					ptr = new com.leopard.api.Printer(set, btcomm.mosOut,btcomm.misIn);
    					fps=new com.leopard.api.FPS(set, btcomm.mosOut,btcomm.misIn);
    					Log.e("TAG", "printer is instantiated");
    				} else if (iRetVal == BluetoothComm.BT_NAME_INVALID){
    					Toast.makeText(context, "Invalid BT Name", Toast.LENGTH_SHORT).show();
    				} else if (iRetVal == BluetoothComm.BT_NOT_AVAILABLE){
    					Toast.makeText(context, "Bluetooth Not Available", Toast.LENGTH_SHORT).show();
    				} else if (iRetVal == BluetoothComm.BT_FAIL){
    					Toast.makeText(context, "Connection Failue", Toast.LENGTH_SHORT).show();
    				} else if (iRetVal == BluetoothComm.BT_NOT_PAIRED){
    					Toast.makeText(context, "BT Device Not Paired", Toast.LENGTH_SHORT).show();
    				} else {
    					Toast.makeText(context, "Connection Failure", Toast.LENGTH_SHORT).show();
    				}
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
                
                Intent result = new Intent();
        		result.putExtra("MAC", sMAC);
        		result.putExtra("RSSI", mhtFDS.get(sMAC).get("RSSI"));
        		result.putExtra("NAME", mhtFDS.get(sMAC).get("NAME"));
        		result.putExtra("COD", mhtFDS.get(sMAC).get("COD"));
        		result.putExtra("BOND", mhtFDS.get(sMAC).get("BOND"));
        		result.putExtra("DEVICE_TYPE", toDeviceTypeString(mhtFDS.get(sMAC).get("DEVICE_TYPE")));
        		setResult(Activity.RESULT_OK, result);
        		
        		Intent all_intent = new Intent(getApplicationContext(),Menu_Dashboard_Activity.class);
        		all_intent.putExtra("connected", false);
        		startActivityForResult(all_intent, 3);
            }  
        });
    	
		new scanDeviceTask().execute("");
	}
	
	
	public static void progressDialog(Context context, String msg) {
		prgDialog = new ProgressDialog(context);
		prgDialog.setMessage(msg);
		prgDialog.setIndeterminate(true);
		prgDialog.setCancelable(true);
		prgDialog.show();
	}	

	@Override
	protected void onDestroy(){
		super.onDestroy();
		
		if (mBT.isDiscovering())
			mBT.cancelDiscovery();
	}
	
	
	private void startSearch(){
		_discoveryFinished = false; 
		
		
		if (null == mhtFDS)
			this.mhtFDS = new Hashtable<String, Hashtable<String, String>>();
		else
			this.mhtFDS.clear();
		
		/* Register Receiver*/
		IntentFilter discoveryFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		registerReceiver(_finshedReceiver, discoveryFilter);
		IntentFilter foundFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(_foundReceiver, foundFilter);
		mBT.startDiscovery();//start scan

		this.showDevices(); //the first scan clear show list
	}
	
	
	private String toDeviceTypeString(String sDeviceTypeId){
		Pattern pt = Pattern.compile("^[-\\+]?[\\d]+$");
		if (pt.matcher(sDeviceTypeId).matches()){
	        switch(Integer.valueOf(sDeviceTypeId)){
	        	case DEVICE_TYPE_BREDR:
	        		return getString(R.string.device_type_bredr);
	        	case DEVICE_TYPE_BLE:
	        		return getString(R.string.device_type_ble);
	        	case DEVICE_TYPE_DUMO:
	        		return getString(R.string.device_type_dumo);
	        	default: 
	        		return getString(R.string.device_type_bredr);
	        }
		}
		else
			return sDeviceTypeId;
	}

	/* Show devices list */
	protected void showDevices(){
		if (null == this.malListItem)
			this.malListItem = new ArrayList<HashMap<String, Object>>();
		
        if (null == this.msaListItemAdapter){
	        //Generate adapter Item and dynamic arrays corresponding element
	        this.msaListItemAdapter = new SimpleAdapter(this,malListItem,//Data Sources   
	            R.layout.list_view_item_devices,//ListItem's XML implementation
	            //Child corresponding dynamic arrays and ImageItem     
	            new String[] {"NAME","MAC", "COD", "RSSI", "DEVICE_TYPE", "BOND"},   
	            //A ImageView ImageItem XML file inside, two TextView ID
	            new int[] {R.id.device_item_ble_name,
	        			   R.id.device_item_ble_mac,
	        			   R.id.device_item_ble_cod,
	        			   R.id.device_item_ble_rssi,
	        			   R.id.device_item_ble_device_type,
	        			   R.id.device_item_ble_bond
	        			  }
	        );
	      
	    	this.mlvList.setAdapter(this.msaListItemAdapter);
        }
        
		
        this.malListItem.clear();//Clear history entries
        Enumeration<String> e = this.mhtFDS.keys();
       
        while (e.hasMoreElements()){
            HashMap<String, Object> map = new HashMap<String, Object>();
            String sKey = e.nextElement();
            map.put("MAC", sKey);
            map.put("NAME", this.mhtFDS.get(sKey).get("NAME"));
            map.put("RSSI", this.mhtFDS.get(sKey).get("RSSI"));
            map.put("COD", this.mhtFDS.get(sKey).get("COD"));
            map.put("BOND", this.mhtFDS.get(sKey).get("BOND"));
            map.put("DEVICE_TYPE", toDeviceTypeString(this.mhtFDS.get(sKey).get("DEVICE_TYPE")));
            this.malListItem.add(map);
        }
		this.msaListItemAdapter.notifyDataSetChanged();
	}
   
    private class scanDeviceTask extends AsyncTask<String, String, Integer>{
    	/**Constants: Bluetooth is not turned on*/
    	private static final int RET_BLUETOOTH_NOT_START = 0x0001;
    	/**Constant: the device search complete*/
    	private static final int RET_SCAN_DEVICE_FINISHED = 0x0002;
    	/**Wait a Bluetooth device starts the maximum time (in S)*/
    	private static final int miWATI_TIME = 10;
    	/**Every thread sleep time (in ms)*/
    	private static final int miSLEEP_TIME = 150;
    	/**Process waits prompt box*/
    	private ProgressDialog mpd = null;
    	
		
		@Override
		public void onPreExecute(){
	    	
			this.mpd = new ProgressDialog(Act_BTDiscovery.this);
			this.mpd.setMessage(getString(R.string.actDiscovery_msg_scaning_device));
			this.mpd.setCancelable(true);
			this.mpd.setCanceledOnTouchOutside(true);
			this.mpd.setOnCancelListener(new DialogInterface.OnCancelListener(){
				@Override
				public void onCancel(DialogInterface dialog){	
					_discoveryFinished = true;
				}
			});
			this.mpd.show();
			
			startSearch(); 
		}
		
		@Override
		protected Integer doInBackground(String... params){
			if (!mBT.isEnabled()) 
				return RET_BLUETOOTH_NOT_START;
			
			int iWait = miWATI_TIME * 1000;
			//Wait miSLEEP_TIME seconds, start the Bluetooth device before you start scanning
			while(iWait > 0){
				if (_discoveryFinished)
					return RET_SCAN_DEVICE_FINISHED;
				else
					iWait -= miSLEEP_TIME;
				SystemClock.sleep(miSLEEP_TIME);;
			}
			return RET_SCAN_DEVICE_FINISHED;
		}
		
		@Override
		public void onProgressUpdate(String... progress){
		}
		
		@Override
		public void onPostExecute(Integer result){
			if (this.mpd.isShowing())
				this.mpd.dismiss();
			
			if (mBT.isDiscovering())
				mBT.cancelDiscovery();
			
			if (RET_SCAN_DEVICE_FINISHED == result){
				
			}else if (RET_BLUETOOTH_NOT_START == result){	
				Toast.makeText(Act_BTDiscovery.this, getString(R.string.actDiscovery_msg_bluetooth_not_start), 
	 					   Toast.LENGTH_SHORT).show();
			}
			
		}
    }

	@Override
	public void onClick(View v) {
	switch (v.getId()) {
	case R.id.btn_Scan:
		new scanDeviceTask().execute("");
    	//return true;
		break;
	
	default:
		break;
	}
		
	}
	//Exit confirmation dialog box
		public void dlgExit() {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			// set title
			alertDialogBuilder.setTitle("Law and Order Application");
			//alertDialogBuilder.setIcon(R.drawable.icon);
			alertDialogBuilder.setMessage(
					"Do you want to exit from Law and Order application");
			alertDialogBuilder.setCancelable(false);
			alertDialogBuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
				
					try {
						BluetoothComm.mosOut = null;
						BluetoothComm.misIn = null;
					} catch(NullPointerException e) { }
					System.gc();
					Act_BTDiscovery.this.finish();
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
		public boolean onKeyDown(int keyCode, KeyEvent event) { //TODO
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				dlgExit();
			}
			return super.onKeyDown(keyCode, event);
		}
}
