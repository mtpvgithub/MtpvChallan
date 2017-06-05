package com.esys.leoparddemo;

import java.util.Hashtable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.evolute.bluetooth.BluetoothComm;
import com.evolute.bluetooth.BluetoothPair;
import com.example.button.R;
import com.leopard.api.BaudChange;
import com.leopard.api.Printer;
import com.leopard.api.Setup;
import com.mtpv.LawnOrder.Menu_Dashboard_Activity;

/**
  * The main interface <br /> 
  * Maintain a connection with the Bluetooth communication operations,
  * check Bluetooth status after the first entry, did not start then turn on Bluetooth, 
  * then immediately into the search interface. <br/> 
  * The need to connect the device to get built on the main interface paired with a connection, 
  * Bluetooth object is stored in globalPool so that other functional modules of different 
  * communication modes calls.
  *////bufvalue fpsconfig bufvalue iRetVal
public class Act_Main extends Activity{
	/**CONST: scan device menu id*/
	private static final String TAG = "Prow LeopardImp App";
	private GlobalPool mGP = null;
	public static BluetoothAdapter mBT = BluetoothAdapter.getDefaultAdapter();
	public static BluetoothDevice mBDevice = null;
	private TextView mtvDeviceInfo = null;
	private TextView mtvServiceUUID = null;
	private LinearLayout mllDeviceCtrl = null;
	private Button mbtnPair = null;
	private Button mbtnComm = null;
    public static final byte REQUEST_DISCOVERY = 0x01;
	public static final byte REQUEST_ABOUT = 0x05;
	private Hashtable<String, String> mhtDeviceInfo = new Hashtable<String, String>();
	private boolean mbBonded = false;
	public final static String EXTRA_DEVICE_TYPE = "android.bluetooth.device.extra.DEVICE_TYPE";
	private boolean mbBleStatusBefore = false;
	final Context context = this;
	Dialog dlgRadioBtn;
	ImageView back_btn;
	static BaudChange bdchange;
	public static int iRetVal;
	public static ProgressDialog prgDialog;
	private Button btn_Exit,btn_Scanbt;
	static Setup setupInstance = null;
	private final int MESSAGE_BOX = 1;
	public static boolean blnResetBtnEnable = false;
	private BroadcastReceiver _mPairingRequest = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent){
			BluetoothDevice device = null;
			if (intent.getAction().equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)){	
				device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if (device.getBondState() == BluetoothDevice.BOND_BONDED)
					mbBonded = true;
				else
					mbBonded = false;
			}
		}
	};
	
	/**
	 * add top menu
	 * */
	ScrollView mainlay;
	@SuppressLint("NewApi")
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mainlay = (ScrollView)findViewById(R.id.mainlay);
		if (null == mBT){ 
			Toast.makeText(this, "Bluetooth module not found", Toast.LENGTH_LONG).show();
			this.finish();
		}
		this.mtvDeviceInfo = (TextView)this.findViewById(R.id.actMain_tv_device_info);
		this.mllDeviceCtrl = (LinearLayout)this.findViewById(R.id.actMain_ll_device_ctrl);
		this.mbtnPair = (Button)this.findViewById(R.id.actMain_btn_pair);
		this.mbtnComm = (Button)this.findViewById(R.id.actMain_btn_conn);
		try {
			setupInstance = new Setup();
			boolean activate = setupInstance.blActivateLibrary(context,R.raw.licence_full);
			if (activate == true) {
				 Log.d(TAG,"Leopard Library Activated......");
			} else if (activate == false) {
				 Log.d(TAG,"Leopard Library Not Activated...");
			}
		} catch (Exception e) { }
		
		btn_Exit = (Button)findViewById(R.id.btn_Exit);
		btn_Exit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//finish();
				dlgExit();
			}
		});
		
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
		
		this.mGP = ((GlobalPool)this.getApplicationContext());
		btn_Scanbt = (Button)findViewById(R.id.scanbt_but);
		
		btn_Scanbt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new startBluetoothDeviceTask().execute(""); 
			}
		});
		TextView scanbt_tv = (TextView)findViewById(R.id.scanbt_tv);
		scanbt_tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new startBluetoothDeviceTask().execute(""); 
			}
		});
		//new startBluetoothDeviceTask().execute(""); 
			
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.mGP.closeConn();
		if (null != mBT && !this.mbBleStatusBefore)
			mBT.disable();
	}

	
	private void openDiscovery(){
		Intent intent = new Intent(this, Act_BTDiscovery.class);
		this.startActivityForResult(intent, REQUEST_DISCOVERY);
		
		
	}
	
	private void showDeviceInfo(){
		
		this.mtvDeviceInfo.setText(
			String.format(getString(R.string.actMain_device_info), 
				this.mhtDeviceInfo.get("NAME"),
				this.mhtDeviceInfo.get("MAC"),
				this.mhtDeviceInfo.get("COD"),
				this.mhtDeviceInfo.get("RSSI"),
				this.mhtDeviceInfo.get("DEVICE_TYPE"),
				this.mhtDeviceInfo.get("BOND"))
		);
	}
	
	private void showServiceUUIDs(){
		if (Build.VERSION.SDK_INT >= 15){
		}else{	
			this.mtvServiceUUID.setText(getString(R.string.actMain_msg_does_not_support_uuid_service));
		}
	}
	
	@SuppressWarnings("static-access")
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		mainlay.setVisibility(View.VISIBLE);
		if (requestCode == REQUEST_DISCOVERY){
			if (Activity.RESULT_OK == resultCode){
				this.mllDeviceCtrl.setVisibility(View.VISIBLE);
				this.mhtDeviceInfo.put("NAME", data.getStringExtra("NAME"));
				this.mhtDeviceInfo.put("MAC", data.getStringExtra("MAC"));
				this.mhtDeviceInfo.put("COD", data.getStringExtra("COD"));
				this.mhtDeviceInfo.put("RSSI", data.getStringExtra("RSSI"));
				this.mhtDeviceInfo.put("DEVICE_TYPE", data.getStringExtra("DEVICE_TYPE"));
				this.mhtDeviceInfo.put("BOND", data.getStringExtra("BOND"));
				this.showDeviceInfo();
				if (this.mhtDeviceInfo.get("BOND").equals(getString(R.string.actDiscovery_bond_nothing))){
					this.mbtnPair.setVisibility(View.VISIBLE); 
					this.mbtnComm.setVisibility(View.GONE); 
				}else{
					this.mBDevice = this.mBT.getRemoteDevice(this.mhtDeviceInfo.get("MAC"));
					this.mbtnPair.setVisibility(View.GONE); 
					this.mbtnComm.setVisibility(View.VISIBLE); 
				}
			}else if (Activity.RESULT_CANCELED == resultCode){
				this.finish();
			}
		}
		else if (requestCode==3) {
			finish();
		}
		
	}
	
	/**
	 * Pairing button click event
	 * @return void
	 * */
	public void onClickBtnPair(View v){
		new PairTask().execute(this.mhtDeviceInfo.get("MAC"));
		this.mbtnPair.setEnabled(false); 
	}
	/**
	 * Connect button click event
	 * @return void
	 * */
	@SuppressWarnings("static-access")
	public void onClickBtnConn(View v){
		//Log.i("onClickBtnConn called ::", "");
		Log.i("this.mBDevice.getAddress() >>>::", this.mBDevice.getAddress()+" name" +this.mBDevice.getName());
		//new connSocketTask().execute(this.mBDevice.getAddress());
		
		
    }
	
    private class startBluetoothDeviceTask extends AsyncTask<String, String, Integer>{
    	private static final int RET_BULETOOTH_IS_START = 0x0001;
    	private static final int RET_BLUETOOTH_START_FAIL = 0x04;
    	private static final int miWATI_TIME = 15;
    	private static final int miSLEEP_TIME = 150;
    	private ProgressDialog mpd;
    	@Override
		public void onPreExecute(){
	     	mpd = new ProgressDialog(Act_Main.this);
			mpd.setMessage(getString(R.string.actDiscovery_msg_starting_device));
			mpd.setCancelable(false);
			mpd.setCanceledOnTouchOutside(false);
			mpd.show();
			mbBleStatusBefore = mBT.isEnabled(); 
		}
    	@Override
		protected Integer doInBackground(String... arg0){
			int iWait = miWATI_TIME * 1000;
			/* BT isEnable */
			if (!mBT.isEnabled()){
				mBT.enable();
				//Wait miSLEEP_TIME seconds, start the Bluetooth device before you start scanning
				while(iWait > 0){
					if (!mBT.isEnabled())
						iWait -= miSLEEP_TIME; 
					else
						break;
					SystemClock.sleep(miSLEEP_TIME);
				}
				if (iWait < 0) 
					return RET_BLUETOOTH_START_FAIL;
			}
			return RET_BULETOOTH_IS_START;
		}
			
		/**
		  * After blocking cleanup task execution
		  */
		@Override
		public void onPostExecute(Integer result){
			if (mpd.isShowing())
				mpd.dismiss();
			
			if (RET_BLUETOOTH_START_FAIL == result){
				AlertDialog.Builder builder = new AlertDialog.Builder(Act_Main.this); 
    	    	builder.setTitle(getString(R.string.dialog_title_sys_err));
    	    	builder.setMessage(getString(R.string.actDiscovery_msg_start_bluetooth_fail));
    	    	builder.setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener(){
    	            @Override
    	            public void onClick(DialogInterface dialog, int which){
    	            	mBT.disable();
    	            
    	            	finish();
    	            }
    	    	}); 
    	    	builder.create().show();
			}
			else if (RET_BULETOOTH_IS_START == result){	
				openDiscovery(); 
				
				
			}
		}
    }
    
    
    private class PairTask extends AsyncTask<String, String, Integer>{
		/**Constants: the pairing is successful*/
		static private final int RET_BOND_OK = 0x00;
		/**Constants: Pairing failed*/
		static private final int RET_BOND_FAIL = 0x01;
		/**Constants: Pairing waiting time (15 seconds)*/
		static private final int iTIMEOUT = 1000 * 15; 
		/**
		 * Thread start initialization
		 */
		@Override
		public void onPreExecute(){
    		
			Toast.makeText(Act_Main.this,getString(R.string.actMain_msg_bluetooth_Bonding),Toast.LENGTH_SHORT).show();
    		registerReceiver(_mPairingRequest, new IntentFilter(BluetoothPair.PAIRING_REQUEST));
    		registerReceiver(_mPairingRequest, new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED));
		}
		
		@Override
		protected Integer doInBackground(String... arg0){
    		final int iStepTime = 150;
    		int iWait = iTIMEOUT; 
    		try{	
    			mBDevice = mBT.getRemoteDevice(arg0[0]);//arg0[0] is MAC address
				BluetoothPair.createBond(mBDevice);
				mbBonded = false; 
			}catch (Exception e1){	
				Log.d(getString(R.string.app_name), "create Bond failed!");
				e1.printStackTrace();
				return RET_BOND_FAIL;
			}
			while(!mbBonded && iWait > 0){
				SystemClock.sleep(iStepTime);
				iWait -= iStepTime;
			}
			if(iWait > 0){ 
				//RET_BOND_OK 
				Log.e("Application", "create Bond failed! RET_BOND_OK ");
			}else{ 
				//RET_BOND_FAIL
				Log.e("Application", "create Bond failed! RET_BOND_FAIL ");
			}
			return (int) ((iWait > 0)? RET_BOND_OK : RET_BOND_FAIL);
		}
    	
		
		@Override
		public void onPostExecute(Integer result){
			unregisterReceiver(_mPairingRequest);   
			
        	if (RET_BOND_OK == result){
				Toast.makeText(Act_Main.this,getString(R.string.actMain_msg_bluetooth_Bond_Success),Toast.LENGTH_SHORT).show();
				mbtnPair.setVisibility(View.GONE); 
				mbtnComm.setVisibility(View.VISIBLE);
				mhtDeviceInfo.put("BOND", getString(R.string.actDiscovery_bond_bonded));
				showDeviceInfo();
				showServiceUUIDs();
        	}else{	
				Toast.makeText(Act_Main.this,getString(R.string.actMain_msg_bluetooth_Bond_fail),Toast.LENGTH_LONG).show();
				try{
					BluetoothPair.removeBond(mBDevice);
				}catch (Exception e){
					Log.d(getString(R.string.app_name), "removeBond failed!");
					e.printStackTrace();
				}
				mbtnPair.setEnabled(true); 
				new connSocketTask().execute(mBDevice.getAddress());
        	}
		}
    }
    
    private class connSocketTask extends AsyncTask<String, String, Integer>{
    	/**Process waits prompt box*/
    	private ProgressDialog mpd = null;
    	/**Constants: connection fails*/
    	private static final int CONN_FAIL = 0x01;
    	/**Constant: the connection is established*/
    	private static final int CONN_SUCCESS = 0x02;
    	
		/**
		 *Thread start initialization
		 */
		@Override
		public void onPreExecute(){
	    	
			this.mpd = new ProgressDialog(Act_Main.this);
			this.mpd.setMessage(getString(R.string.actMain_msg_device_connecting));
			this.mpd.setCancelable(false);
			this.mpd.setCanceledOnTouchOutside(false);
			this.mpd.show();
		}
		
		@Override
		protected Integer doInBackground(String... arg0){
			/*if (mGP.createConn(mBDevice.getAddress())){
				return CONN_SUCCESS; 
			}
			else{
				return CONN_FAIL;
			}*/
			return CONN_FAIL;
		}
    	
		/**
		  * After blocking cleanup task execution
		  */
		@Override
		public void onPostExecute(Integer result){
			this.mpd.dismiss();
			
			if (CONN_SUCCESS == result){	
				mbtnComm.setVisibility(View.GONE); 
				Toast.makeText(Act_Main.this,getString(R.string.actMain_msg_device_connect_succes),Toast.LENGTH_SHORT).show();
				showBaudRateSelection();
				
			}else{	
				Toast.makeText(Act_Main.this, getString(R.string.actMain_msg_device_connect_fail),Toast.LENGTH_SHORT).show();
			}
		}
    }
    
    // dialog box will display options to select the baud rate
 	public void showBaudRateSelection() { //TODO
 		dlgRadioBtn = new Dialog(context);
 		dlgRadioBtn.setCancelable(false);
 		dlgRadioBtn.setTitle("Law and Order Application");
 		dlgRadioBtn.setContentView(R.layout.dlg_bardchange);
 		/* when the application is started it is presumed that device is started 
 		 * along with it (i.e. Switched ON) hence by default the device will be in 
 		 * 9600bps so entering directly to next activity 
 		 */
 		RadioButton radioBtn9600 = (RadioButton) dlgRadioBtn.findViewById(R.id.first_radio);
 		radioBtn9600.setOnClickListener(new OnClickListener() {
 			@Override
 			public void onClick(View v) {
 				/* ResetBtnEnable will disable the reset button in Exit dialog box as 
 				 * the connection is not made in bps */
 				
 				blnResetBtnEnable = false;
 				dlgRadioBtn.dismiss();
 				Intent all_intent = new Intent(getApplicationContext(),Menu_Dashboard_Activity.class);
 				all_intent.putExtra("connected", false);
 				startActivityForResult(all_intent, 3);
 			}
 		});
 	/*	RadioButton radioBtn1152 = (RadioButton) dlgRadioBtn.findViewById(R.id.second_radio);
 		radioBtn1152.setOnClickListener(new OnClickListener() {
 			@Override
 			public void onClick(View v) {
 				 ResetBtnEnable will enable the reset button in Exit dialog box as 
 				 * the connection will be made in  	115200bps 
 				//ResetBtnEnable = true;
 				blnResetBtnEnable = true;
 				dlgRadioBtn.dismiss();
 				try {
 					bdchange = new BaudChange(setupInstance,
 							BluetoothComm.mosOut,BluetoothComm.misIn);
 				} catch (Exception e) { }
 				
 				BaudRateTask increaseBaudRate = new BaudRateTask();
 				increaseBaudRate.execute(0);
 			}
 		});
 		RadioButton ibc = (RadioButton) dlgRadioBtn.findViewById(R.id.ibc_radio);
 		ibc.setOnClickListener(new OnClickListener() {
 			@Override
 			public void onClick(View v) {
 				 ResetBtnEnable will disable the reset button in Exit dialog box as 
 				 * the connection is not made in 115200bps 
 				//ResetBtnEnable = false;
 				dlgRadioBtn.dismiss();
 				Intent all_intent = new Intent(getApplicationContext(),Act_SelectPeripherals.class);
 				all_intent.putExtra("connected", false);
 				startActivityForResult(all_intent, 3);
 			}
 		});*/
 		dlgRadioBtn.show();
 	}
	// increases the device baud rate from 9600bps to 115200bps
	public class BaudRateTask extends AsyncTask<Integer, Integer, Integer> {
		@Override
		protected void onPreExecute() { //TODO
			// shows a progress dialog until the baud rate process is complete 
			ProgressDialog(context, "Please Wait ...");
			super.onPreExecute();
		}
		@Override
		protected Integer doInBackground(Integer... params) {
			try {
				//Log.d(TAG, "Change the peripheral Speed");
				iRetVal = bdchange.iSwitchPeripheral1152();
				if(iRetVal==BaudChange.BC_SUCCESS){
				 Thread.sleep(3000);
				 BluetoothComm.mosOut=null;
				 BluetoothComm.misIn=null;
				 mGP.closeConn();
				Thread.sleep(3000);
				if (mBT != null) {
					mBT.cancelDiscovery();
				}
				/*Thread.sleep(3000);
				boolean b =mGP.createConn(mBDevice.getAddress());
				if(b==true)
				mGP.mBTcomm.isConnect();
				Thread.sleep(3000);
				bdchange.iSwitchBT1152(BluetoothComm.mosOut,BluetoothComm.misIn);*/
					}
				} catch (Exception e) {
				e.printStackTrace();
			}
			return iRetVal;
		}

		/* goes to next activity after setting the new baud rate*/
		@Override
		protected void onPostExecute(Integer result) {
			prgDialog.dismiss();
			if(iRetVal==BaudChange.BC_SUCCESS){
			Intent all_intent = new Intent(getApplicationContext(),Act_SelectPeripherals.class);
			all_intent.putExtra("connected", false);
			startActivityForResult(all_intent, 3);
			}else if (iRetVal==BaudChange.BC_FAILURE) {
				Toast.makeText(getApplicationContext(), "Switch Baud Rate 115200 Failed", Toast.LENGTH_LONG).show();
			}else if (iRetVal == Printer.PR_NO_RESPONSE) {
				hander.obtainMessage(MESSAGE_BOX,"No response from Leopard device").sendToTarget();
			}else if (iRetVal== Printer.PR_DEMO_VERSION) {
				hander.obtainMessage(MESSAGE_BOX,"Library is in demo version").sendToTarget();
			}else if (iRetVal==Printer.PR_INVALID_DEVICE_ID) {
				hander.obtainMessage(MESSAGE_BOX,"Connected  device is not license authenticated.").sendToTarget();
			}else if (iRetVal==Printer.PR_ILLEGAL_LIBRARY) {
				hander.obtainMessage(MESSAGE_BOX,"Library not valid").sendToTarget();
			}
		}
	}
	
	/* Handler to display UI response messages   */
	@SuppressLint("HandlerLeak")
	Handler hander = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MESSAGE_BOX:
				String str = (String) msg.obj;
				showdialog(str);
			
			}
		};
	};
	
	public static void ProgressDialog(Context context, String msg) {
		prgDialog = new ProgressDialog(context);
		prgDialog.setMessage(msg);
		prgDialog.setIndeterminate(true);
		prgDialog.setCancelable(false);
		prgDialog.show();
	}
	//Exit confirmation dialog box
			public void dlgExit() {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				// set title
				alertDialogBuilder.setTitle("Law and Order Application");
				//alertDialogBuilder.setIcon(R.drawable.icon);
				alertDialogBuilder.setMessage("Do you want to exit from Law and Order application");
				alertDialogBuilder.setCancelable(false);
				alertDialogBuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
					
						try {
							BluetoothComm.mosOut = null;
							BluetoothComm.misIn = null;
						} catch(NullPointerException e) { }
						System.gc();
						Act_Main.this.finish();
					}
				});
				alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
						
					}
				});
				
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
				
			}
			/*  To show response messages  */
			public void showdialog(String str) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				alertDialogBuilder.setTitle("Law and Order Application");
				alertDialogBuilder.setMessage(str).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});
				/* create alert dialog*/
				AlertDialog alertDialog = alertDialogBuilder.create();
				/* show alert dialog*/
				alertDialog.show();
			}
			public boolean onKeyDown(int keyCode, KeyEvent event) { //TODO
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					//dlgExit();
				}
				return super.onKeyDown(keyCode, event);
			}
			
			@Override
			public void onBackPressed() {
				// TODO Auto-generated method stub
			}
			
}