package com.mtpv.LawnOrder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.Manifest;
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
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.analogics.n5library.N5Library;
import com.esys.leoparddemo.Act_BTDiscovery;
import com.evolute.bluetooth.BluetoothComm;
import com.example.button.R;
import com.leopard.api.FPS;
import com.leopard.api.Setup;


public class UserLogin_Activity extends Activity implements LocationListener {


	Setup set;
	final int PROGRESS_DIALOG = 1;

	com.leopard.api.Printer ptr;
	FPS fps;
	protected static final String URL = null;
	Button cancel, login, print;
	ImageView settings;
	TextView ip_text;
	EditText userID_ET, password_ET;
	static String resp;
	static String PIdCode;

	public static String IMEI;
	public static String sim_No;
	public static String uintCode = "23";
	public static String uintName = "Hyderabad";
	public static String Pid_code, Pid_Name, Ps_code, Ps_Name, CADRE_CODE, CADRE_NAME, UNIT_CODE, UNIT_NAME,
			SECURITY_CD;
	DataBase dbj;
	SQLiteDatabase sqlite;
	private static String NAMESPACE = "http://service.mother.com";
	public static String SOAP_ACTION = NAMESPACE + "authenticateUser", Opdata_Chalana;
	String pidCdl;
	String pidpwd;
	public static String bt_device = "";
	BluetoothComm btcomm = null;
	Context context = this;
	final int EXIT_DIALOG = 0;

	public static String DEVICE_TABLE = "DEVICE_TABLE";
	public static final String CREATE_DEVICE_TABLE = "CREATE TABLE  IF NOT EXISTS "
			+ DEVICE_TABLE + "(DEVICENAME  VARCHAR )";

	public static String IP_TABLE = "IP_TABLE";


	//CREATE_DEVICE_TABLE_DETAILS

	public static final String CREATE_IP_TABLE = "CREATE TABLE  IF NOT EXISTS "
			+ IP_TABLE + "(IP  VARCHAR )";

	public static boolean BT_Connect_flag = false;
	public static boolean ipStatus = false;
	public static boolean bluetooth_Flg = false;

	private static final int REQUEST_ENABLE_BT = 1;
	private BluetoothAdapter myBluetoothAdapter;
	private ArrayAdapter<String> BTArrayAdapter;

	public static String checkselfpermission;
	public static int REQUEST_ID_MULTIPLE_PERMISSIONS;
	private static final int REQUEST_APP_SETTINGS = 168;

	public static String alert_string = null;

	private static final String[] requiredPermissions = new String[]{
			Manifest.permission.READ_PHONE_STATE,
			Manifest.permission.ACCESS_FINE_LOCATION,
			Manifest.permission.ACCESS_NETWORK_STATE,
			Manifest.permission.ACCESS_WIFI_STATE,
			Manifest.permission.ACCESS_COARSE_LOCATION,
			Manifest.permission.INTERNET,
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.INSTALL_SHORTCUT

	};

	LocationManager m_locationlistner;
	android.location.Location location;
	public static double latitude = 0.0;
	public static double longitude = 0.0;
	/* GPS VALUES */
	// flag for GPS status
	boolean isGPSEnabled = false;
	// flag for network status
	boolean isNetworkEnabled = false;
	boolean canGetLocation = false;
	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_userlogin);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		N5Library.initialize(UserLogin_Activity.this);


		if (Build.VERSION.SDK_INT > 22 && !hasPermissions(requiredPermissions)) {
			showToast("Please grant all permissions");

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			// set title
			alertDialogBuilder.setTitle("Android Alert");
			alertDialogBuilder.setIcon(R.drawable.hyd_city_small);
			alertDialogBuilder.setMessage("Please Enable all the required Permissions");
			alertDialogBuilder.setCancelable(false);
			alertDialogBuilder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {

					goToSettings();

				}
			});

			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();

		}
    	/*//call the gps enabled and network enabled  
		getLocation();
		Geocoder geocoder = new Geocoder(this, Locale.getDefault());
		
		try {
			Log.i("geocoder :::::::", "****geocoder****");
			
			Log.i("LONGITUTDE", ""+longitude);
			Log.i("LATITUDE", ""+latitude);
			
			
			List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
			Log.i("address&&&&&&&&&&&&&&&&", ""+addresses);
			if (addresses.size() > 0) {
				Address returnedAddress = addresses.get(0);
				StringBuilder strReturnedAddress = new StringBuilder("\n");
				Log.i("geocoder 2:::::::", "****geocoder****+"
						+ strReturnedAddress);

				for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
					strReturnedAddress
							.append(returnedAddress.getAddressLine(i)).append(
									"\n");
				}
				
				
				Log.i("Address**********", ""+strReturnedAddress);
				Toast.makeText(getApplicationContext(), ""+strReturnedAddress, Toast.LENGTH_SHORT).show();
			} 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		/**************Adding Shortcut of Application**************/
		SharedPreferences prefs = getSharedPreferences("ShortCutPrefs", MODE_PRIVATE);
		if (!prefs.getBoolean("isFirstTime", false)) {
			Log.i("**addShortcut******", "**********addShortcut*******");
			addShortcut();
			SharedPreferences.Editor editor = prefs.edit();
			editor.putBoolean("isFirstTime", true);
			editor.commit();
		}
		/**************Adding Shortcut of Application**************/

		/**************TAB BLUETOOTH FUNCTIONALITY**************/
		myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (myBluetoothAdapter == null) {
			Toast.makeText(getApplicationContext(), "Your device does not support Bluetooth",
					Toast.LENGTH_LONG).show();
		} else {
			on();
		}

		/**************TAB BLUETOOTH FUNCTIONALITY**************/

		/*****************GPS FUNCTIONALITY******************/

		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			//Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
		} else {
			showGPSDisabledAlertToUser();
		}


		/*****************GPS FUNCTIONALITY******************/

		userID_ET = (EditText) findViewById(R.id.user_id);
		password_ET = (EditText) findViewById(R.id.password);

		userID_ET.setText("23001234");
		password_ET.setText("2016");


		login = (Button) findViewById(R.id.login);
		if (android.os.Build.VERSION.SDK_INT > 11) {
			StrictMode.ThreadPolicy polocy = new StrictMode.ThreadPolicy.Builder().build();
			StrictMode.setThreadPolicy(polocy);
		}

		/*******************FIELD'S VALIDATIONS*************************/
		login.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("null")
			@Override

			public void onClick(View v) {


				TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
				IMEI = getDeviceID(telephonyManager);

				if (telephonyManager.getSimState() != TelephonyManager.SIM_STATE_ABSENT) {
					sim_No = "" + telephonyManager.getSimSerialNumber();
					Log.i("SIM Number", "" + sim_No);
				} else {
					sim_No = "";
				}


				if ((userID_ET.getText().length() == 0 && password_ET.getText().length() == 0)) {
					//Toast.makeText(getApplicationContext(),  "Please Enter User Id and Password", Toast.LENGTH_LONG).show();
				/*Toast toast = Toast.makeText(getApplicationContext(), "Please Enter User Id and Password", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 400);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();*/
					userID_ET.setError(Html.fromHtml("<font color='black'>Please Enter User ID</font>"));
					userID_ET.requestFocus();
				}
				if ((userID_ET.getText().length() == 0)) {
					//Toast.makeText(getApplicationContext(),  "Please Enter Valid User Id", Toast.LENGTH_LONG).show();
				/*Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Valid User Id", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 400);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();*/
					userID_ET.setError(Html.fromHtml("<font color='black'>Please Enter User ID</font>"));
					userID_ET.requestFocus();
				}
				if ((password_ET.getText().length() == 0)) {
					//Toast.makeText(getApplicationContext(),  "Please Enter Valid Password", Toast.LENGTH_LONG).show();
				/*Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Valid Password", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 400);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();*/
					password_ET.setError(Html.fromHtml("<font color='black'>Please Enter Password</font>"));
					password_ET.requestFocus();
				} else if (userID_ET.getText().toString().trim().length() != 0 && password_ET.getText().toString().trim().length() != 0) {
					userID_ET.getText().toString().trim();
					password_ET.getText().toString().trim();


					Log.i("latitude********", "" + latitude);
					Log.i("longitude*******", "" + longitude);

					new AsyncLoginDetails().execute();

				}

			}

		});

		/*******************FIELD'S VALIDATIONS*************************/

		settings = (ImageView) findViewById(R.id.settings);
		settings.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent ip = new Intent(getApplicationContext(), IP_SettingsActivity.class);
				startActivity(ip);
			}
		});

		/**************CLOSING APPLICATION**************/

		cancel = (Button) findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {/*

				new AlertDialog.Builder(UserLogin_Activity.this)
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle("Exit")
						.setMessage("Are you sure?")
						.setPositiveButton("yes",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										Intent intent = new Intent(Intent.ACTION_MAIN);
										intent.addCategory(Intent.CATEGORY_HOME);
										intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
										startActivity(intent);
										finish();
									}
								}).setNegativeButton("no", null).show();
						
			*/
				dlgExit();
			}
		});
	}

	private void getLocation() {
		// TODO Auto-generated method stub

		m_locationlistner = (LocationManager) this.getSystemService(LOCATION_SERVICE);

		isGPSEnabled = m_locationlistner.isProviderEnabled(LocationManager.GPS_PROVIDER);
		isNetworkEnabled = m_locationlistner.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


		if (!isGPSEnabled && !isNetworkEnabled) {

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UserLogin_Activity.this);
			alertDialogBuilder.setMessage("    Mobile Data is Disabled in your Device \n            Please Enable Mobile Data?").setCancelable(false)
					.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int id) {

							Intent i = new Intent(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS);
							startActivity(i);
						}
					});
			AlertDialog alert = alertDialogBuilder.create();
			alert.show();


			latitude = 0.0;
			longitude = 0.0;

		} else {
			this.canGetLocation = true;
			String current_time;
			String current_date = null;
			// First get location from Network Provider
			if (isNetworkEnabled) {
				if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
					// TODO: Consider calling
					//    ActivityCompat#requestPermissions
					// here to request the missing permissions, and then overriding
					//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
					//                                          int[] grantResults)
					// to handle the case where the user grants the permission. See the documentation
					// for ActivityCompat#requestPermissions for more details.
					return;
				}
				m_locationlistner.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);
				Log.i("Network", "Network");
                if (m_locationlistner != null) {
                    location = m_locationlistner.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        
                        long time = location.getTime();
                    	Date date = new Date(time);
                    	
                    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    	String gps_Date = sdf.format(date);
                        
                        
                    }
                }
            }
            // if GPS Enabled get lat/long using GPS Services
            if (isGPSEnabled) {
                if (location != null) {
                    m_locationlistner.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);
                    Log.d("GPS Enabled", "GPS Enabled");
                    if (m_locationlistner != null) {
                        location = m_locationlistner.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            
                             
                        }
                    }
                }
            }		
				
			}
		}
		
	
	String getDeviceID(TelephonyManager phonyManager) {

		String id = phonyManager.getDeviceId();
		if (id == null) {
		id = "not available";
		}

		int phoneType = phonyManager.getPhoneType();
		switch (phoneType) {
		case TelephonyManager.PHONE_TYPE_NONE:
		return id;

		case TelephonyManager.PHONE_TYPE_GSM:
		return id;

		case TelephonyManager.PHONE_TYPE_CDMA:
		return id;

		default:
		return "UNKNOWN:ID=" + id;
		}

		}
	/**************CLOSING APPLICATION**************/
	
	private void goToSettings() {
        Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
        myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(myAppSettings, REQUEST_APP_SETTINGS);
    }

   public boolean hasPermissions(String... permissions) {
        for (String permission : permissions)
            if (PackageManager.PERMISSION_GRANTED != checkCallingOrSelfPermission(permission))
                return false;
        return true;
    }

 @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_APP_SETTINGS) {
            if (hasPermissions(requiredPermissions)) {
                showToast("All permissions granted!");
            } else {
            	showToast("Permissions not granted!");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
	


	/**************Adding Shortcut of Application**************/
	private void addShortcut() {
		 Intent shortcutIntent = new Intent(getApplicationContext(), UserLogin_Activity.class);
	        shortcutIntent.setAction(Intent.ACTION_MAIN);
	        shortcutIntent.addCategory(Intent.CATEGORY_LAUNCHER);
	        int flags = Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT;
	        shortcutIntent.addFlags(flags);

	        Intent addIntent = new Intent();
	        addIntent.putExtra("duplicate", false);
	        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
	        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getResources().getString(R.string.app_name));
	        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource
	                .fromContext(getApplicationContext(), R.drawable.hyd_city));
	        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
	        getApplicationContext().sendBroadcast(addIntent);
	}
	/**************Adding Shortcut of Application**************/


	/**************BLUETOOTH ACTIVATION**************/
	
	 public void on(){
	      if (!myBluetoothAdapter.isEnabled()) {
		         Intent turnOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		         startActivityForResult(turnOnIntent, REQUEST_ENABLE_BT);
	      	}
		      else{
		      }
		   }
		 
	 
   
	   final BroadcastReceiver bReceiver = new BroadcastReceiver() {
	    public void onReceive(Context context, Intent intent) {
	        String action = intent.getAction();
	        // When discovery finds a device
	        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
	             // Get the BluetoothDevice object from the Intent
	        	 BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	        	 // add the name and the MAC address of the object to the arrayAdapter
	             BTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
	             BTArrayAdapter.notifyDataSetChanged();
		        }
		    }
		};
	
   public void find(View view) {
	   if (myBluetoothAdapter.isDiscovering()) {
		   // the button is pressed when it discovers, so cancel the discovery
		   myBluetoothAdapter.cancelDiscovery();
	   }
	   else {
			BTArrayAdapter.clear();
			myBluetoothAdapter.startDiscovery();
			registerReceiver(bReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));	
		}    
   }
   
   /*****************BLUETOOTH ACTIVATION******************/
   
   /**************NETWORK(GPS) AVAILABILITY**************/
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
	/**************NETWORK(GPS) AVAILABILITY CLOSE******************/
	
	
	/*****************LOGIN SERVICE ASYNCTASK CALLED**************************/
	
	class AsyncLoginDetails extends AsyncTask<Void,Void, String>{
	//ProgressDialog dialog = new ProgressDialog(UserLogin_Activity.this);
	boolean authStatus=false;
	@SuppressWarnings({ "static-access", "resource" })
	@Override
	protected String doInBackground(Void... params) { 
	
		SQLiteDatabase db = openOrCreateDatabase(DataBase.DATABASE_NAME,MODE_PRIVATE, null);
		
		Log.i("IMEI*****************", ""+IMEI);
		Log.i("sim No************", ""+sim_No);
		Log.i("LATITUDE**********", ""+latitude);
		Log.i("LONGITUDE*********", ""+longitude);
		
		try {
			if (userID_ET.getText().length() != 0 && userID_ET.getText().toString() != "") {
			if(password_ET.getText().length() != 0 && password_ET.getText().toString() != ""){
			String ET_userID = userID_ET.getText().toString();
			String ET_password = password_ET.getText().toString();
			String ip="";
			
			db.execSQL(CREATE_DEVICE_TABLE);
			
			String selectQuery = "SELECT  * FROM " + DEVICE_TABLE;
		     // SQLiteDatabase db = this.getWritableDatabase();
		    Cursor cursor = db.rawQuery(selectQuery, null);
	        
	        if (cursor.moveToFirst()) {
	            do {
	            
	            	bt_device = cursor.getString(0);
	            	Act_BTDiscovery.DEVICE_NAME=bt_device;
	            	Log.i("Device Name ::::", Act_BTDiscovery.DEVICE_NAME);
	            		            	 
	            } while (cursor.moveToNext());
	        }
			
	        try {

	            if(bt_device!=null && bt_device.length()>5){

					Log.e("TAG", "bt is connected");
					BT_Connect_flag = true;
					//SQLiteDatabase db = openOrCreateDatabase(DataBase.DATABASE_NAME,MODE_PRIVATE, null);
					selectQuery = "SELECT  * FROM " + DataBase.IP_TABLE;
	    		     // SQLiteDatabase db = this.getWritableDatabase();
	    		     cursor = db.rawQuery(selectQuery, null);
	    		    // looping through all rows and adding to list
			        if (cursor.moveToFirst()) {
			            do {
			            	 Log.i("DATABASE   IP VALUE :",""+ cursor.getString(0));
			            	 ip=cursor.getString(0);
			            	 ipStatus=true;
			            } while (cursor.moveToNext());
			        }
			    
			        
			        
				 
			      //  boolean authStatus=false;
			        if( ipStatus && WebService.dyamic_Ip!=null){
			        	try {
				    		SoapObject request = new SoapObject(NAMESPACE, "authenticateUser");
							request.addProperty("pidCd", ET_userID);
							request.addProperty("password", ET_password);
							request.addProperty("IMEI", IMEI);
							request.addProperty("sim_No", sim_No);
							SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
							envelope.dotNet = true;
							envelope.setOutputSoapObject(request);
							
							Log.i("request", "" + request);
							Log.i("WebSOAP_ADDRESS :", "" + WebService.SOAP_ADDRESS);
							
							HttpTransportSE androidHttpTransport = new HttpTransportSE(ip+"/HydPettyCaseService/services/PettyCaseServiceImpl?wsdl");
							Log.i("androidHttpTransport", "" + androidHttpTransport);
							androidHttpTransport.call(SOAP_ACTION, envelope);
							Object result = envelope.getResponse();
							
							Opdata_Chalana = result.toString();
							Log.i("**LOGIN response***", "" + Opdata_Chalana);
							if (Opdata_Chalana.equals("NA")) {
								authStatus=false;
								//Toast.makeText(getApplicationContext(), "Please check Ip settings", Toast.LENGTH_LONG).show();
							}else if (Opdata_Chalana.toString().trim().equals("1")) {
								showToast("Invalid User id and Password");
								
							}else if (Opdata_Chalana.toString().trim().equals("2")) {
								showToast("Unauthorized Device");
								
							}else if (Opdata_Chalana.toString().trim().equals("3")) {
								showToast("Error, Please Contact E Challan Team at 040-27852721F");
								
							}else{
								authStatus=true;
								
								if (authStatus){						
									
									new DataBase(getApplicationContext()).insertAuthenticateUser(Opdata_Chalana,getApplicationContext());
									Intent i = new Intent(getApplicationContext(), Menu_Dashboard_Activity.class);
								
									startActivity(i);
									Log.i("LOGIN REPONSE", Opdata_Chalana);
								}
							}
							
		    		}catch (java.lang.NumberFormatException e) {
			    			authStatus=false;
			    			e.printStackTrace();
			    			/*Toast toast = Toast.makeText(getApplicationContext(), "Please check Ip settings", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.CENTER, 0, 400);
							View toastView = toast.getView();
					    	toastView.setBackgroundResource(R.drawable.toast_background);dfd
						    toast.show();*/
			    			showToast("Please Check IP Settings");
			    		}
					Log.i("authStatus :::", ""+authStatus);
					if ( authStatus){						
						try {
							BluetoothComm btcomm = new BluetoothComm(bt_device);
							int iRetVal = btcomm.createConn();
							Log.e("iretvalue", "iretvalue:"+iRetVal);
							bluetooth_Flg = true;
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
						new DataBase(getApplicationContext()).insertAuthenticateUser(Opdata_Chalana,getApplicationContext());
						Intent i = new Intent(getApplicationContext(), Menu_Dashboard_Activity.class);
						startActivity(i);
						Log.i("LOGIN REPONSE", Opdata_Chalana);
						
						}else{
							
						}
		    		}else{
		    			/*Toast toast = Toast.makeText(getApplicationContext(), "Please check Ip settings", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 400);
						View toastView = toast.getView();
				    	toastView.setBackgroundResource(R.drawable.toast_background);
					    toast.show();*/
		    			showToast("Please Check IP Settings");
		    			}
					//prn=new Printer_ESC(instSetup, outSt, inSt)
					//ptr = new com.leopard.api.Printer(set, btcomm.mosOut,btcomm.misIn);
					//fps=new com.leopard.api.FPS(set, btcomm.mosOut,btcomm.misIn);
					Log.e("TAG", "printer is instantiated");
						
	        	}else{
	        		BT_Connect_flag = false;
	        		//SQLiteDatabase db = openOrCreateDatabase(DataBase.DATABASE_NAME,MODE_PRIVATE, null);
					db.execSQL(CREATE_IP_TABLE);
	        		selectQuery = "SELECT  * FROM " + IP_TABLE;
	    		     // SQLiteDatabase db = this.getWritableDatabase();
	    		     cursor = db.rawQuery(selectQuery, null);
	    		    // looping through all rows and adding to list
			        if (cursor.moveToFirst()) {
			            do {
			            	 Log.i("DATABASE   IP VALUE :",""+ cursor.getString(0));
			            	 ip=cursor.getString(0);
			            	 ipStatus=true;
			            } while (cursor.moveToNext());
			        }
			        
			        if( ipStatus && WebService.dyamic_Ip!=null){
			        	try {
				    		SoapObject request = new SoapObject(NAMESPACE, "authenticateUser");
							request.addProperty("pidCd", ET_userID);
							request.addProperty("password", ET_password);
							request.addProperty("IMEI", IMEI);
							request.addProperty("sim_No", sim_No);
							SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
							envelope.dotNet = true;
							envelope.setOutputSoapObject(request);
							
							Log.i("request", "" + request);
							Log.i("Web SOAP_ADDRESS :", "" + WebService.SOAP_ADDRESS);
							
							HttpTransportSE androidHttpTransport = new HttpTransportSE(ip+"/HydPettyCaseService/services/PettyCaseServiceImpl?wsdl");
							Log.i("androidHttpTransport", "" + androidHttpTransport);
							androidHttpTransport.call(SOAP_ACTION, envelope);
							Object result = envelope.getResponse();
							
							Opdata_Chalana = result.toString();
							Log.i("**LOGIN response***", "" + Opdata_Chalana);
							
		    		}catch (java.lang.NumberFormatException e) {
		    			authStatus=false;
		    			e.printStackTrace();
		    			/*Toast toast = Toast.makeText(getApplicationContext(), "Please check Ip settings", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 400);
						View toastView = toast.getView();
				    	toastView.setBackgroundResource(R.drawable.toast_background);
					    toast.show();*/
		    			showToast("Please Check IP Settings");
		    		}
					Log.i("authStatus :::", ""+authStatus);
					
		    		}else{
		    		//	Toast.makeText(getApplicationContext(), "Please check Ip setting", Toast.LENGTH_LONG).show();
		    		}
	        	}
			} catch (Exception e) {
				Log.i("Bluetooth Connection ::", "Failed");
				
				e.printStackTrace();
			}
	    
				}else{
					/*Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Proper Login details", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 400);
					View toastView = toast.getView();
			    	toastView.setBackgroundResource(R.drawable.toast_background);
				    toast.show();*/
					showToast("Please Enter Proper Login Details");
				}
				}	
	} catch (Exception E) {
		E.printStackTrace();
		Opdata_Chalana = "0";
		Log.e("Error", "" + E.toString());
		}
	finally{
		try {
			if(db!=null)
				db.close();
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	return null;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	       /* dialog.setMessage("Please wait.....!");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();*/
		showDialog(PROGRESS_DIALOG);
		}

	@Override
	protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			removeDialog(PROGRESS_DIALOG);
			if (isNetworkAvailable()) {
				
				if (Opdata_Chalana.equals("NA")) {
					authStatus=false;
				
				}else if (Opdata_Chalana.toString().trim().equals("1")) {
					showToast("Invalid User_id or Password");
					
				}else if (Opdata_Chalana.toString().trim().equals("2")) {
					showToast("Unauthorized Device");
					
				}else if (Opdata_Chalana.toString().trim().equals("3")) {
					showToast("Error, Please Contact E Challan Team at 040-27852721");
					
				}else{
					authStatus=true;
					
					if (authStatus){						
						
						new DataBase(getApplicationContext()).insertAuthenticateUser(Opdata_Chalana,getApplicationContext());
						Intent i = new Intent(getApplicationContext(), Menu_Dashboard_Activity.class);
					
						startActivity(i);
						Log.i("LOGIN REPONSE", Opdata_Chalana);
					}
				}
			/*if(!authStatus){
				Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Valid Login Details", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 400);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();
				showToast("Please Enter Valid Login Details");
			}
*/			if(!ipStatus){
				/*Toast toast = Toast.makeText(getApplicationContext(), "Please Save IP Settings ", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 400);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();*/
				showToast("Please Save IP Settings");
			}
			/*if(!BT_Connect_flag){
				Toast toast = Toast.makeText(getApplicationContext(), "Ensure Bluetooth is Switched On ", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 400);
				View toastView = toast.getView();
		    	toastView.setBackgroundResource(R.drawable.toast_background);
			    toast.show();
				showToast("Ensure Bluetooth is Switched On");
			}*/
			
			    // do something
		      
				}else {
					  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UserLogin_Activity.this);
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
	
	/************************LOGIN SERVICE END******************************/
	
	
	/************************EXIT METHOD CALLED******************************/
	public void dlgExit() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		// set title
		alertDialogBuilder.setTitle("Hyderabad ePetty Case Application");
		alertDialogBuilder.setIcon(R.drawable.hyd_city_small);
		alertDialogBuilder.setMessage(
				"Do you want to exit from Application Completely");
		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
			
				try {
					BluetoothComm.mosOut = null;
					BluetoothComm.misIn = null;
				} catch(NullPointerException e) { }
				System.gc();
				Intent intent = new Intent(Intent.ACTION_MAIN);
		          intent.addCategory(Intent.CATEGORY_HOME);
		          intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP & Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);//***Change Here***
		          startActivity(intent);
		          finish();
		          System.exit(0);
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
	
	/************************EXIT METHOD CALLED******************************/
	
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
	
	@SuppressWarnings("unused")
	private void showToast(String msg) {
		// TODO Auto-generated method stub
	//	Toast.makeText(getApplicationContext(), "" + msg, Toast.LENGTH_SHORT).show();
		Toast toast = Toast.makeText(getApplicationContext(), "" + msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		View toastView = toast.getView();
		
		ViewGroup group = (ViewGroup) toast.getView();
	    TextView messageTextView = (TextView) group.getChildAt(0);
	    //messageTextView.setTextSize(24);
		
    	toastView.setBackgroundResource(R.drawable.permissionstoast_background);
	    toast.show();
	}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
}