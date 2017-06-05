package com.mtpv.LawnOrder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
public class FPT4_Activity extends Activity implements OnClickListener{

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
	private boolean verifyuncompressed = false;
	public static final int DEVICE_NOTCONNECTED = -100;
	private static final int MESSAGE_BOX = 1;
	static ProgressDialog dlgCustom,dlgpd;
	MorphoBiometrics oE = new MorphoBiometrics();
	
	
	Context context = this;
	
	CheckBox left_tf, left_if, left_mf, left_rf, left_pf,
				right_tf, right_if, right_mf, right_rf, right_pf, no_left_arm,no_right_arm ;
	
	LinearLayout fpt_more;
	
	ImageView back_btn, submit_btn;
	
	Button more_fpt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_fpt4_);
		
	//	oE.bioOn();
		USBManager.getInstance().initialize(this,"com.morpho.morphosample.USB_ACTION");

		MorphoBiometrics oE = new MorphoBiometrics();
		String sensorName = oE.enumarate();
		TextView textViewSensorName = (TextView) findViewById(R.id.textView_serialNumber);
		textViewSensorName.setText(sensorName);
		
		
		 try {
				//set=new Setup();
				boolean bl = set.blActivateLibrary(FPT4_Activity.this, R.raw.licence_full);
				Log.e("Setup", "Activation Status: "+bl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		 
		 
		fpt_more = (LinearLayout)findViewById(R.id.fpt_more4);
		
		more_fpt = (Button)findViewById(R.id.more_fpt4);
		
		no_right_arm = (CheckBox)findViewById(R.id.no_right_arm4);
		
		no_left_arm = (CheckBox)findViewById(R.id.no_left_arm4);
		
		left_tf = (CheckBox)findViewById(R.id.left_tf4);//
		
		left_if = (CheckBox)findViewById(R.id.left_if4);
		
		left_mf = (CheckBox)findViewById(R.id.left_mf4);
		
		left_rf = (CheckBox)findViewById(R.id.left_rf4);
		
		left_pf = (CheckBox)findViewById(R.id.left_pf4);
		
		right_tf = (CheckBox)findViewById(R.id.right_tf4);//
		
		right_if = (CheckBox)findViewById(R.id.right_if4);
		
		right_mf = (CheckBox)findViewById(R.id.right_mf4);
		
		right_rf = (CheckBox)findViewById(R.id.right_rf4);
		
		right_pf = (CheckBox)findViewById(R.id.right_pf4);
		
		back_btn = (ImageView)findViewById(R.id.back_btn4);
		//submit_btn = (ImageView)findViewById(R.id.submit_btn);
		
		back_btn.setOnClickListener(this);
	//	submit_btn.setOnClickListener(this);
		more_fpt.setOnClickListener(this);
		
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
		case R.id.left_tf4:
			
			if (left_tf.isChecked() == true) {
				Toast.makeText(getApplicationContext(), "Place Left Thumb Finger", 1000).show();
				//n5connection("LEFT_THUMB");
				evoluteConnection("LEFT_THUMB");
			}
			else if(left_tf.isChecked()!= true){
				Toast.makeText(getApplicationContext(), "Removed Left Thumb Finger", 1000).show();
			}
			break;

		case R.id.left_if4:
			
			if (left_if.isChecked() == true) {
				Toast.makeText(getApplicationContext(), "Place Left Index Finger", 1000).show();
				//n5connection("LEFT_INDEX");
				evoluteConnection("LEFT_INDEX");
			}
			else if(left_if.isChecked()!= true){
				Toast.makeText(getApplicationContext(), "Removed Left Index Finger", 1000).show();
			}
			break;
			
		case R.id.left_mf4:
			
			if (left_mf.isChecked() == true) {
				Toast.makeText(getApplicationContext(), "Place Left Middle Finger", 1000).show();
				//n5connection("LEFT_MIDDLE");
				evoluteConnection("LEFT_MIDDLE");
			}
			else if(left_mf.isChecked()!= true){
				Toast.makeText(getApplicationContext(), "Removed Left Middle Finger", 1000).show();
			}
			break;
			
		case R.id.left_rf4:
			
			if (left_rf.isChecked() == true) {
				Toast.makeText(getApplicationContext(), "Place Left Ring Finger", 1000).show();
				//n5connection("LEFT_RING");
				evoluteConnection("LEFT_RING");
			}
			else if(left_rf.isChecked()!= true){
				Toast.makeText(getApplicationContext(), "Removed Left Ring Finger", 1000).show();
			}
			
			break;
			
		case R.id.left_pf4:
			
			if (left_pf.isChecked() == true) {
				Toast.makeText(getApplicationContext(), "Place Left Little Finger", 1000).show();
				//n5connection("LEFT_LITTLE");
				evoluteConnection("LEFT_LITTLE");
			}
			else if(left_pf.isChecked()!= true){
				Toast.makeText(getApplicationContext(), "Removed Left Pinky Finger", 1000).show();
			}
			break;
			
		case R.id.right_tf4:
			
			if (right_tf.isChecked() == true) {
				Toast.makeText(getApplicationContext(), "Place Right Thumb Finger", 1000).show();
				//n5connection("RIGHT_THUMB");
				evoluteConnection("RIGHT_THUMB");
			}
			else if(right_tf.isChecked()!= true){
				Toast.makeText(getApplicationContext(), "Removed Right Thumb Finger", 1000).show();
			}
			break;

		case R.id.right_if4:
			
			if (right_if.isChecked() == true) {
				Toast.makeText(getApplicationContext(), "Place Right Index Finger", 1000).show();
				//n5connection("RIGHT_INDEX");
				evoluteConnection("RIGHT_INDEX");
			}
			else if(right_if.isChecked()!= true){
				Toast.makeText(getApplicationContext(), "Removed Right Index Finger", 1000).show();
			}
			break;
			
		case R.id.right_mf4:
			
			if (right_mf.isChecked() == true) {
				Toast.makeText(getApplicationContext(), "Place Right Middle Finger", 1000).show();
				//n5connection("RIGHT_MIDDLE");
				evoluteConnection("RIGHT_MIDDLE");
			}
			else if(right_mf.isChecked()!= true){
				Toast.makeText(getApplicationContext(), "Removed Right Middle Finger", 1000).show();
			}
			break;
			
		case R.id.right_rf4:
			
			if (right_rf.isChecked() == true) {
				Toast.makeText(getApplicationContext(), "Place Right Ring Finger", 1000).show();
				//n5connection("RIGHT_RING");
				evoluteConnection("RIGHT_RING");
			}
			else if(right_rf.isChecked()!= true){
				Toast.makeText(getApplicationContext(), "Removed Right Ring Finger", 1000).show();
			}
			
			break;
			
		case R.id.right_pf4:
			if (right_pf.isChecked() == true) {
				Toast.makeText(getApplicationContext(), "Place Right Little Finger", 1000).show();
				//n5connection("RIGHT_LITTLE");
				evoluteConnection("RIGHT_LITTLE");
			}
			else if(right_pf.isChecked()!= true){
				Toast.makeText(getApplicationContext(), "Removed Right Pinky Finger", 1000).show();
			}
			
			break;
			
			
		case R.id.no_left_arm4:
			if (no_left_arm.isChecked() == true) {
				Toast.makeText(getApplicationContext(), "No Left Arm", 1000).show();
				//n5connection("NO_LEFT");
				evoluteConnection("NO_LEFT");
			}
			else if(no_left_arm.isChecked()!= true){
				Toast.makeText(getApplicationContext(), "No Left Arm", 1000).show();
			}
			
			break;
			
		case R.id.no_right_arm4:
			if (no_right_arm.isChecked() == true) {
				Toast.makeText(getApplicationContext(), "No Right Arm", 1000).show();
				//n5connection("NO_RIGHT");
				evoluteConnection("NO_RIGHT");
			}
			else if(no_right_arm.isChecked()!= true){
				Toast.makeText(getApplicationContext(), "No Right Arm", 1000).show();
			}
			
			break;
			
			
		case R.id.back_btn4:
			/*Intent back = new Intent(getApplicationContext(), OffenderActivity.class);
			startActivity(back);*/
			finish();
			
			break;
			
			
	/*case R.id.submit_btn:
			VerifyTempleAsync verifyTemp = new VerifyTempleAsync();
			verifyTemp.execute(0);
			
			break;	*/
		case R.id.more_fpt4:
		
			fpt_more.setVisibility(View.VISIBLE);
			
			
			break;	
			
		default:
			break;
		}
	}
	
	@SuppressLint("SdCardPath")
	public void n5connection(String typeOfFinger ) {

		byte[] bb = oE.connection();
		if (bb.length > 100) {
			System.out.println("finger:: " + new String(bb));
			TextView textView_serialNumber = (TextView) findViewById(R.id.textView_serialNumber4);
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
					iRetVal = fps.iFpsVerifyTemplate(FPT4_Activity.bufvalue,new FpsConfig(1, (byte) 0x0f));
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
	
	
	
	
	
	public void evoluteConnection(String typeOfFing ) {
		FPT4_Activity.typeOfFinger=typeOfFing;
		new AsyncGetFPS4().execute();
		
	}
		/* This performs Progress dialog box to show the progress of operation */
		public static void progressDialog(Context context, String msg) {
			dlgCustom = new ProgressDialog(context);
			dlgCustom.setMessage(msg);
			dlgCustom.setIndeterminate(true);
			dlgCustom.setCancelable(false);
			dlgCustom.show();
		}

	class AsyncGetFPS4 extends AsyncTask<Void,Void, String>{
		
		ProgressDialog dialog = new ProgressDialog(FPT4_Activity.this);
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			//showDialog(PROGRESS_DIALOG);
	            dialog.setMessage("Please wait FPS in Progress.....!");
	            dialog.setIndeterminate(true);
	            dialog.setCancelable(false);
	            dialog.show();
		}
		
		@SuppressLint("SdCardPath")
		@SuppressWarnings("static-access")
		@Override
			protected String doInBackground(Void... params) {
				Log.i("DoInBackground Called ::", "AsyncGetPrintDetails");
    			Log.i("Act_BTDiscovery.DEVICE_NAME ::", Act_BTDiscovery.DEVICE_NAME);
				if (!Act_BTDiscovery.DEVICE_NAME.equals("")) {
				//Toast.makeText(getApplicationContext(), "Scan device", Toast.LENGTH_SHORT).show();
			
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
			    	FPT4_Activity.bufvalue = new byte[3500]; 
			    //	byte[] bMinutiaeData =null;
			    	byte[] bufvalue =null;
			try {
				Log.e("inside fps click", "FPS API---->");
				FpsConfig fpsconfig = new FpsConfig(0, (byte) 0x0F);
				//FPT4_Activity.bufvalue = fps.bFpsCaptureTemplate(fpsconfig);
				//Log.i("Flag 1 :::", OffenderActivity.captFlg1);
				
				bufvalue = fps.bFpsCaptureTemplate(fpsconfig);
				FPT4_Activity.bufvalue =fps.bGetMinutiaeData();//
				
				int iRetVal = fps.iGetReturnCode();
				Log.d("pROWESS LEO", "IRETVAL"+iRetVal);
				
				String str1 = HexString.bufferToHex(FPT4_Activity.bufvalue);
				Log.i("FPT4_Activity.bufvalue string ","Finger Data:\n"+str1);
				Log.i("FPT4_Activity.bufvalue byte [] ",""+FPT4_Activity.bufvalue);
				
				if (FPT4_Activity.bufvalue!=null&&FPT4_Activity.bufvalue.length > 100) {

					OffenderActivity.bufvalue4=FPT4_Activity.bufvalue;
					/*OffenderActivity.bufvalue2=FPT4_Activity.bufvalue;
					OffenderActivity.bufvalue3=FPT4_Activity.bufvalue;
					OffenderActivity.bufvalue4=FPT4_Activity.bufvalue;*/
					
					TextView textView_serialNumber = (TextView) findViewById(R.id.textView_serialNumber4);
					textView_serialNumber.setText(FPT4_Activity.typeOfFinger+" Captured success");
					
					FileOutputStream out = null;
					try {
						out = new FileOutputStream("/sdcard/DCIM"+"/+"+FPT4_Activity.typeOfFinger+".iso-fmr");
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					  try {
						out.write(FPT4_Activity.bufvalue);
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
		/*		//Log.i("Finger 1 :::", OffenderActivity.captFing_txt1.getText().toString());
				SharedPreferences sharedPreferences =  getSharedPreferences(OffenderActivity.MYPREFERENCES, context.MODE_PRIVATE);
				Log.i("OffenderActivity.captFing_txt1 ::", sharedPreferences.getString("captFing_txt1",null));
				//SharedPreferences.Editor editor = sharedPreferences.edit();
				//editor.
				 if("1".equals(sharedPreferences.getString("captFing_txt1",null))){
					 OffenderActivity.bufvalue1=FPT4_Activity.bufvalue;
					 Log.i("FPT4_Activity.bufvalue byte []1 ",""+FPT4_Activity.bufvalue);
				}else if("1".equals(sharedPreferences.getString("captFing_txt2",null))){
					OffenderActivity.bufvalue2=FPT4_Activity.bufvalue;
					 Log.i("FPT4_Activity.bufvalue byte []2",""+FPT4_Activity.bufvalue);
				}else if("1".equals(sharedPreferences.getString("captFing_txt3",null))){
					OffenderActivity.bufvalue3=FPT4_Activity.bufvalue;
					 Log.i("FPT4_Activity.bufvalue byte []3 ",""+FPT4_Activity.bufvalue);
				}else if("1".equals(sharedPreferences.getString("captFing_txt4",null))){
					OffenderActivity.bufvalue4=FPT4_Activity.bufvalue;
					 Log.i("FPT4_Activity.bufvalue byte []4 ",""+FPT4_Activity.bufvalue);
				}*/
				
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
				dialog.dismiss();			
			}
		}
}