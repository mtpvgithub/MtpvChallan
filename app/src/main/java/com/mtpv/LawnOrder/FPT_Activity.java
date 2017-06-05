package com.mtpv.LawnOrder;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.esys.leoparddemo.Act_BTDiscovery;
import com.evolute.bluetooth.BluetoothComm;
import com.example.button.R;
import com.leopard.api.FPS;
import com.leopard.api.FpsConfig;
import com.leopard.api.HexString;
import com.leopard.api.Setup;
import com.morpho.android.usb.USBManager;
import com.morpho.morphosample.MorphoBiometrics;

@SuppressLint("ShowToast")
public class FPT_Activity extends Activity implements OnClickListener{

	Setup set ;
	//Printer_GEN prn;
	com.leopard.api.Printer ptr;
	FPS fps;
	public static Map<String,byte[]>fpsmap1=new HashMap<String,byte[] >();
	public static Map<String,byte[]>fpsmap2=new HashMap<String,byte[] >();
	public static Map<String,byte[]>fpsmap3=new HashMap<String,byte[] >();
	public static Map<String,byte[]>fpsmap4=new HashMap<String,byte[] >();
	private int iRetVal;
	public static String DEVICE_NAME="";
	@SuppressWarnings("unused")
	private static ProgressDialog prgDialog;
	public static String typeOfFinger="";
	private static byte[] bufvalue = {};
	private boolean verifyfinger = false;
	@SuppressWarnings("unused")
	private boolean verifycompressed = false;
	@SuppressWarnings("unused")
	private boolean verifyuncompressed = false, fpsFlg=false;
	public static final int DEVICE_NOTCONNECTED = -100;
	private static final int MESSAGE_BOX = 1;
	static ProgressDialog dlgCustom,dlgpd;
	MorphoBiometrics oE = new MorphoBiometrics();
	
	ProgressDialog progressDialog;
	Context context = this;
	
	CheckBox left_tf, left_if, left_mf, left_rf, left_pf,
				right_tf, right_if, right_mf, right_rf, right_pf, no_left_arm,no_right_arm ;
	
	LinearLayout fpt_more;
	
	ImageView back_btn, submit_btn;
	
	Button more_fpt, verify;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_fpt_);
		
	//	oE.bioOn();
		USBManager.getInstance().initialize(this,"com.morpho.morphosample.USB_ACTION");

		MorphoBiometrics oE = new MorphoBiometrics();
		String sensorName = oE.enumarate();
		TextView textViewSensorName = (TextView) findViewById(R.id.textView_serialNumber);
		textViewSensorName.setText(sensorName);
		
		 try {
				//set=new Setup();
				boolean bl = set.blActivateLibrary(FPT_Activity.this, R.raw.licence_full);
				Log.e("Setup", "Activation Status: "+bl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		 
		 
		fpt_more = (LinearLayout)findViewById(R.id.fpt_more);
		
		more_fpt = (Button)findViewById(R.id.more_fpt);
		
		verify = (Button)findViewById(R.id.verify);
		
		no_right_arm = (CheckBox)findViewById(R.id.no_right_arm);
		
		no_left_arm = (CheckBox)findViewById(R.id.no_left_arm);
		
		left_tf = (CheckBox)findViewById(R.id.left_tf);//
		
		left_if = (CheckBox)findViewById(R.id.left_if);
		
		left_mf = (CheckBox)findViewById(R.id.left_mf);
		
		left_rf = (CheckBox)findViewById(R.id.left_rf);
		
		left_pf = (CheckBox)findViewById(R.id.left_pf);
		
		right_tf = (CheckBox)findViewById(R.id.right_tf);//
		
		right_if = (CheckBox)findViewById(R.id.right_if);
		
		right_mf = (CheckBox)findViewById(R.id.right_mf);
		
		right_rf = (CheckBox)findViewById(R.id.right_rf);
		
		right_pf = (CheckBox)findViewById(R.id.right_pf);
		
		back_btn = (ImageView)findViewById(R.id.back_btn);
		//submit_btn = (ImageView)findViewById(R.id.submit_btn);
		
		back_btn.setOnClickListener(this);
	//	submit_btn.setOnClickListener(this);
		more_fpt.setOnClickListener(this);
		verify.setOnClickListener(this);
		
		left_if.setOnClickListener(this);
		left_mf.setOnClickListener(this);
		left_rf.setOnClickListener(this);
		left_tf.setOnClickListener(this);
		left_pf.setOnClickListener(this);
		no_left_arm.setOnClickListener(this);
		
		right_if.setOnClickListener(this);
		right_mf.setOnClickListener(this);
		right_rf.setOnClickListener(this);
		right_tf.setOnClickListener(this);
		right_pf.setOnClickListener(this);
		no_right_arm.setOnClickListener(this);
		
	}

	/* Handler to display UI response messages */
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MESSAGE_BOX:
				String str = (String) msg.obj;
				ShowDialog(str);
				break;
			default:
				break;
			}
		}

		/* To show response messages */
		public void ShowDialog(String str) {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			alertDialogBuilder.setTitle("L & O Application");
			alertDialogBuilder.setMessage(str).setCancelable(false)
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.dismiss();
						}
					});
			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
			// show it
			alertDialog.show();
		}
		;
	};
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.left_tf:
			
			if (left_tf.isChecked() == true) {
				Toast toast = Toast.makeText(getApplicationContext(), "Place Left Thumb Finger on Long Blink of Red Light", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, -200);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();
			    fpsFlg = true;
				//n5connection("LEFT_THUMB");
				evoluteConnection("LEFT THUMB");
			}
			else if(left_tf.isChecked()!= true){
				Toast toast = Toast.makeText(getApplicationContext(), "Removed Left Thumb Finger on Long Blink of Red Light", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, -200);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();
			}
			break;

		case R.id.left_if:
			
			if (left_if.isChecked() == true) {
				Toast toast = Toast.makeText(getApplicationContext(), "Place Left Index Finger on Long Blink of Red Light", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, -200);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();
				//n5connection("LEFT_INDEX");
				evoluteConnection("LEFT INDEX");
			}
			else if(left_if.isChecked()!= true){
				Toast toast = Toast.makeText(getApplicationContext(), "Removed Left Index Finger on Long Blink of Red Light", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, -200);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();
			}
			break;
			
		case R.id.left_mf:
			
			if (left_mf.isChecked() == true) {
				Toast toast = Toast.makeText(getApplicationContext(), "Place Left Middle Finger on Long Blink of Red Light", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, -200);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();
				//n5connection("LEFT_MIDDLE");
				evoluteConnection("LEFT MIDDLE");
			}
			else if(left_mf.isChecked()!= true){
				Toast toast = Toast.makeText(getApplicationContext(), "Removed Left Middle Finger on Long Blink of Red Light", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, -200);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();
			}
			
			break;
			
		case R.id.left_rf:
			
			if (left_rf.isChecked() == true) {
				Toast toast = Toast.makeText(getApplicationContext(), "Removed Left Thumb Finger on Long Blink of Red Light", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, -200);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();
				//n5connection("LEFT_RING");
				evoluteConnection("LEFT RING");
			}
			else if(left_rf.isChecked()!= true){
				Toast toast = Toast.makeText(getApplicationContext(), "Removed Left Thumb Finger on Long Blink of Red Light", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, -200);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();
			}
			
			break;
			
		case R.id.left_pf:
			
			if (left_pf.isChecked() == true) {
				Toast toast = Toast.makeText(getApplicationContext(), "Removed Left Thumb Finger on Long Blink of Red Light", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, -200);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();
				//n5connection("LEFT_LITTLE");
				evoluteConnection("LEFT LITTLE");
			}
			else if(left_pf.isChecked()!= true){
				Toast toast = Toast.makeText(getApplicationContext(), "Removed Left Little Finger on Long Blink of Red Light", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, -200);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();
			}
			break;
			
		case R.id.right_tf:
			
			if (right_tf.isChecked() == true) {
				Toast toast = Toast.makeText(getApplicationContext(), "Place Right Thumb Finger on Long Blink of Red Light", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, -200);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();
				//n5connection("RIGHT_THUMB");
				evoluteConnection("RIGHT THUMB");
			}
			else if(right_tf.isChecked()!= true){
				Toast toast = Toast.makeText(getApplicationContext(), "Removed Right Thumb Finger on Long Blink of Red Light", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, -200);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();
			}
			break;

		case R.id.right_if:
			
			if (right_if.isChecked() == true) {
				Toast toast = Toast.makeText(getApplicationContext(), "Place Right Index Finger on Long Blink of Red Light", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, -200);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();
			    fpsFlg = true;
				//n5connection("RIGHT_INDEX");
				evoluteConnection("RIGHT INDEX");
			}
			else if(right_if.isChecked()!= true){
				Toast toast = Toast.makeText(getApplicationContext(), "Removed Right Index Finger on Long Blink of Red Light", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, -200);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();toast.setGravity(Gravity.CENTER, 0, -200);
			    toast.show();
			}
			break;
			
		case R.id.right_mf:
			
			if (right_mf.isChecked() == true) {
				Toast toast = Toast.makeText(getApplicationContext(), "Place Right Middle Finger on Long Blink of Red Light", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, -200);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();
				//n5connection("RIGHT_MIDDLE");
				evoluteConnection("RIGHT MIDDLE");
			}
			else if(right_mf.isChecked()!= true){
				Toast toast = Toast.makeText(getApplicationContext(), "Removed Right Middle Finger on Long Blink of Red Light", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, -200);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();
			}
			break;
			
		case R.id.right_rf:
			
			if (right_rf.isChecked() == true) {
				Toast toast = Toast.makeText(getApplicationContext(), "Place Right Ring Finger on Long Blink of Red Light", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, -200);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();
				//n5connection("RIGHT_RING");
				evoluteConnection("RIGHT RING");
			}
			else if(right_rf.isChecked()!= true){
				Toast toast = Toast.makeText(getApplicationContext(), "Removed Right Ring Finger on Long Blink of Red Light", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, -200);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();
			}
			
			break;
			
		case R.id.right_pf:
			if (right_pf.isChecked() == true) {
				Toast toast = Toast.makeText(getApplicationContext(), "Place Right Little Finger on Long Blink of Red Light", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, -200);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();
				//n5connection("RIGHT_LITTLE");
				evoluteConnection("RIGHT LITTLE");
			}
			else if(right_pf.isChecked()!= true){
				Toast toast = Toast.makeText(getApplicationContext(), "Removed Right Little Finger on Long Blink of Red Light", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, -200);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();
			}
			break;
			
			
		case R.id.no_left_arm:
			if (no_left_arm.isChecked() == true) {
				Toast toast = Toast.makeText(getApplicationContext(), "No Left Arm", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, -200);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();
				//n5connection("NO_LEFT");
				//evoluteConnection("NO LEFT");
			}
			else if(no_left_arm.isChecked()!= true){
				Toast toast = Toast.makeText(getApplicationContext(), "No Left Arm", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, -200);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();
			}
			
			break;
			
		case R.id.no_right_arm:
			if (no_right_arm.isChecked() == true) {
				Toast toast = Toast.makeText(getApplicationContext(), "No Right Arm", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, -200);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();
				//n5connection("NO_RIGHT");
				//evoluteConnection("NO RIGHT");
			}
			else if(no_right_arm.isChecked()!= true){
				Toast toast = Toast.makeText(getApplicationContext(), "No Right Arm", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, -200);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();
			}
			
			break;
			
			
		case R.id.back_btn:
			/*Intent back = new Intent(getApplicationContext(), OffenderActivity.class);
			startActivity(back);*/
			finish();
			if(fpsFlg){
				OffenderActivity.cpture_tick1.setVisibility(View.VISIBLE);
			}else if(!fpsFlg){
				OffenderActivity.cpture_tick1.setVisibility(View.GONE);
			}
			break;
			
	/*case R.id.submit_btn:
			VerifyTempleAsync verifyTemp = new VerifyTempleAsync();
			verifyTemp.execute(0);
			
			break;	*/
		case R.id.more_fpt:
		
			fpt_more.setVisibility(View.VISIBLE);
			break;	

		case R.id.verify:
			
			new VerifyTempleAsync().execute();
			
			break;		
			
		default:
			break;
		}
	}
	public void displayToast(View view) {
    	Toast toast = Toast.makeText(getApplicationContext(),
    	        "Custom toast background color",
    	        Toast.LENGTH_SHORT);
    	
    	View toastView = toast.getView();
    	toastView.setBackgroundResource(R.drawable.toast_background);
    	toast.show();
    	
    }

	@SuppressLint("SdCardPath")
	public void n5connection(String typeOfFinger ) {

		byte[] bb = oE.connection();
		if (bb.length > 100) {
			System.out.println("finger:: " + new String(bb));
			TextView textView_serialNumber = (TextView) findViewById(R.id.textView_serialNumber);
			//TextView textView_serialNumber_title = (TextView) findViewById(R.id.textView_serialNumber_title);
			//textView_serialNumber_title.setText("Status");
			textView_serialNumber.setText(typeOfFinger+" Captured success");
			FileOutputStream out = null;
			try {
				out = new FileOutputStream("/sdcard"+"/+"+typeOfFinger+".iso-fmr");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  try {
				out.write(bb);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			  try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			}

		/*
		 * startActivity(new Intent(APIActivity.this, sampleActivity.class));
		 * finish();
		 */

	}
	
	
	/* This method shows the VerifyTempleAsync AsynTask operation */
	public class VerifyTempleAsync extends AsyncTask<Integer, Integer, Integer> {
		/* displays the progress dialog until background task is completed */
		//int iRetVal = 0;
		@Override
		protected void onPreExecute() {
			
			progressDialog(context, "Place your finger on FPS ...");
			super.onPreExecute();
		}

		/* Task of VerifyTempleAsync performing in the background */
		@Override
		protected Integer doInBackground(Integer... params) {
			if (verifyfinger == false) {
				dlgCustom.dismiss();
				handler.obtainMessage(MESSAGE_BOX,"Please capture finger and then verify").sendToTarget();
			} else if (verifyfinger == true) {
				try {
					iRetVal = fps.iFpsVerifyTemplate(FPT_Activity.bufvalue,new FpsConfig(1, (byte) 0x0f));
				} catch (NullPointerException e) {
					iRetVal = DEVICE_NOTCONNECTED;
					return iRetVal;
				}
			}
			return iRetVal;
		}

		/*
		 * This function sends message to handler to display the status messages
		 * of Diagnose in the dialog box
		 */
		@Override
		protected void onPostExecute(Integer result) {
			dlgCustom.dismiss();
			if (iRetVal == DEVICE_NOTCONNECTED) {
				handler.obtainMessage(DEVICE_NOTCONNECTED,"Device not connected").sendToTarget();
			} else if (iRetVal == FPS.SUCCESS) {
				handler.obtainMessage(MESSAGE_BOX,"Captured template verification is success").sendToTarget();
			} else if (iRetVal == FPS.FPS_INACTIVE_PERIPHERAL) {
				handler.obtainMessage(MESSAGE_BOX,"Peripheral is inactive").sendToTarget();
			} else if (iRetVal == FPS.TIME_OUT) {
				handler.obtainMessage(MESSAGE_BOX, "Capture finger time out").sendToTarget();
			} else if (iRetVal == FPS.FPS_ILLEGAL_LIBRARY) {
				handler.obtainMessage(MESSAGE_BOX, "Illegal library").sendToTarget();
			} else if (iRetVal == FPS.FAILURE) {
				handler.obtainMessage(MESSAGE_BOX,"Captured template verification is failed,\nWrong finger placed").sendToTarget();
			} else if (iRetVal == FPS.PARAMETER_ERROR) {
				handler.obtainMessage(MESSAGE_BOX, "Parameter error").sendToTarget();
			} else if (iRetVal == FPS.FPS_INVALID_DEVICE_ID) {
				handler.obtainMessage(MESSAGE_BOX,"Library is in demo version").sendToTarget();
			} else if (iRetVal == FPS.FPS_INVALID_DEVICE_ID) {
				handler.obtainMessage(MESSAGE_BOX,"Connected  device is not license authenticated.").sendToTarget();
			} else if (iRetVal == FPS.FPS_ILLEGAL_LIBRARY) {
				handler.obtainMessage(MESSAGE_BOX,"Library not valid").sendToTarget();
			}
			super.onPostExecute(result);
		}
	}
	
	public void evoluteConnection(final String typeOfFing ) {
		progressDialog = ProgressDialog.show(FPT_Activity.this, "FPS Status",  "Please wait ...Processing!", true);
        progressDialog.setCancelable(true);
		Thread t1 = new Thread(new Runnable() {
		     public void run() {
		          // code goes here.
		    	 try {
	             Thread.sleep(3000);
		    	 FPT_Activity.typeOfFinger=typeOfFing;
		    	 new AsyncGetFPS().execute();
		    	 }
		    	 catch (Exception e) {
	                }
		    	 progressDialog.dismiss();
		     }
		});  
		t1.start();
	}
		/* This performs Progress dialog box to show the progress of operation */
		public static void progressDialog(Context context, String msg) {
			dlgCustom = new ProgressDialog(context);
			dlgCustom.setMessage(msg);
			dlgCustom.setIndeterminate(true);
			dlgCustom.setCancelable(false);
			dlgCustom.show();
		}

	class AsyncGetFPS extends AsyncTask<Void,Void, String>{
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			//showDialog(PROGRESS_DIALOG);
		}
		
		@SuppressLint("SdCardPath")
		@SuppressWarnings("static-access")
		@Override
			protected String doInBackground(Void... params) {
				Log.i("DoInBackground Called ::", "AsyncGetPrintDetails");
    			Log.i("Act_BTDiscovery.DEVICE_NAME ::", Act_BTDiscovery.DEVICE_NAME);
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
	    					//ptr = new com.leopard.api.Printer(set, btcomm.mosOut,btcomm.misIn);
	    					fps=new com.leopard.api.FPS(set, btcomm.mosOut,btcomm.misIn);
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
						//ptr = new com.leopard.api.Printer(set, btcomm.mosOut,btcomm.misIn);
						fps=new com.leopard.api.FPS(set, btcomm.mosOut,btcomm.misIn);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
	    	
				runOnUiThread(new Runnable() {
				    @SuppressWarnings("unused")
					public void run(){
				    	FPT_Activity.bufvalue = new byte[3500]; 
				    	byte[] bMinutiaeData =null;
				    	byte[] bufvalue =null;
				try {
					Log.e("inside fps click", "FPS API---->");
					FpsConfig fpsconfig = new FpsConfig(0, (byte) 0x0F);
				
					FPT_Activity.bufvalue = fps.bFpsCaptureTemplate(fpsconfig);//
					bMinutiaeData =fps.bGetMinutiaeData();// fmr
					
					
					int iRetVal = fps.iGetReturnCode();
					Log.d("pROWESS LEO", "IRETVAL"+iRetVal);
					
					String str1 = HexString.bufferToHex(bMinutiaeData);
					Log.i("FPT_Activity.bufvalue string ","Finger Data:\n"+str1);
					Log.i("FPT_Activity.bufvalue byte [] ",""+FPT_Activity.bufvalue);
				
				if (FPT_Activity.bufvalue!=null&&FPT_Activity.bufvalue.length > 100) {

					OffenderActivity.bufvalue1=FPT_Activity.bufvalue;
					
					/*10-03-2016*/
					try {
						 Log.i("File Closed", "no");
						BufferedWriter  filename=new BufferedWriter(new FileWriter("test.bmp"));
				        filename.write(""+str1);
				        if(filename!=null){
				            filename.close();
				            
				            Log.i("File Closed", "Yes");
				           }
						
					} catch (Exception e) {
						// TODO: handle exception
					}
					
			        /*10-03-2016*/
				
					
					TextView textView_serialNumber = (TextView) findViewById(R.id.textView_serialNumber);
					textView_serialNumber.setText(FPT_Activity.typeOfFinger+" Captured successfully");
					
					FileOutputStream out = null;
					try {
						out = new FileOutputStream("/sdcard/DCIM"+"/+"+FPT_Activity.typeOfFinger+".iso-fmr");
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					  try {
						out.write(FPT_Activity.bufvalue);
						verifyfinger=true;
						Log.i("fps", "SUCCESS");
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					  try {
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					}
				} catch (NullPointerException e) {
				e.printStackTrace();
				}
			    }
				});
			}
				return null;
			}


			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
			}
		}
	
	@Override
	public void onBackPressed() {
		
	}

}