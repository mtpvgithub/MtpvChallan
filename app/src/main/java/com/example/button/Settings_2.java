package com.example.button;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mtpv.LawnOrder.DataBase;
import com.mtpv.LawnOrder.DeviceListActivity;
import com.mtpv.LawnOrder.DeviceListAdapter;

public class Settings_2 extends Activity {
	private Button mActivateBtn;
	private Button mPairedBtn;
	private Button mScanBtn;
	
	Button save, cancel_btn, scan_bluetooth ;
	public static ListView bluetooth_list ;
	public static EditText et_bt_address;
	
	BluetoothAdapter bluetoothAdapter;
	static final UUID MY_UUID = UUID.randomUUID();
	ArrayAdapter<String> btArrayAdapter;
	@SuppressWarnings("unused")
	private static final int REQUEST_ENABLE_BT = 1;
	String address = "";
	
	private ProgressDialog mProgressDlg;
	
	private ArrayList<BluetoothDevice> mDeviceList = new ArrayList<BluetoothDevice>();
	
	public static BluetoothAdapter mBluetoothAdapter;
	
	SharedPreferences sharedpreferences;
	public static final String mypreference = "mypref";
	public static String BLT_Name = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_settings_2);
		   
		save = (Button)findViewById(R.id.save);
		cancel_btn = (Button)findViewById(R.id.cancel_Btn);
		scan_bluetooth = (Button)findViewById(R.id.scan_btn);
		
		mActivateBtn 		= (Button) findViewById(R.id.btn_enable);
		mPairedBtn 			= (Button) findViewById(R.id.btn_view_paired);
		mScanBtn 			= (Button) findViewById(R.id.btn_scan);
		
		et_bt_address = (EditText) findViewById(R.id.buletooth_text);
		
		 Intent intent = getIntent();

		 String bt_intent = intent.getStringExtra("device_paired");
		 
		 if (bt_intent != null) {
			et_bt_address.setText(bt_intent);
		}
		
		if (et_bt_address.getText().toString().equals("")) {
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
		           	 	BLT_Name = cursor.getString(0);
		           	 	
		           	 	et_bt_address.setText(BLT_Name);
		           	 
	            		} while (cursor.moveToNext());
		        		}
						db.close(); 
					} catch (Exception e) {
						e.printStackTrace();
				
				}
		   
		}
	
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			   
			   /*String n = et_bt_address.getText().toString();
	
			   SharedPreferences.Editor editor = getSharedPreferences(mypreference, MODE_PRIVATE).edit();
			   editor.putString("BLT_Name", n);
			   editor.commit();*/
			   
				DataBase helper= new DataBase(getApplicationContext());
				
				ContentValues values = new ContentValues();
				values.put("BT_Name", et_bt_address.getText().toString().trim());
				SQLiteDatabase db = openOrCreateDatabase(DataBase.DATABASE_NAME,MODE_PRIVATE, null);
				//db.execSQL("delete from " + DataBase.USER_TABLE);    
				db.execSQL("DROP TABLE IF EXISTS " + DataBase.Bluetooth);
				db.execSQL(DataBase.CREATE_Bluetooth);
	  			db.insert(DataBase.Bluetooth, null, values);
	  			System.out.println("*********************OFFICER TABLE Insertion Successfully **********************************");
	  			showToast("Blutooth Device Saved Successfully..!");
			   finish();
			}
		});
		
		cancel_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		bluetooth_list = (ListView)findViewById(R.id.listview_devicesfound);
		
		mBluetoothAdapter	= BluetoothAdapter.getDefaultAdapter();
		
		mProgressDlg 		= new ProgressDialog(this);
		
		mProgressDlg.setMessage("Please Wait Bluetooth is Scanning...");
		mProgressDlg.setCancelable(false);
		mProgressDlg.setInverseBackgroundForced(false);
		mProgressDlg.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        dialog.dismiss();
		        
		        mBluetoothAdapter.cancelDiscovery();
		    }
		});
		
		if (mBluetoothAdapter == null) {
			showUnsupported();
		} else {
			mPairedBtn.setOnClickListener(new View.OnClickListener() {				
				@Override
				public void onClick(View v) {
					Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
					
					if (pairedDevices == null || pairedDevices.size() == 0) { 
						showToast("No Paired Devices Found");
					} else {
						ArrayList<BluetoothDevice> list = new ArrayList<BluetoothDevice>();
						
						list.addAll(pairedDevices);
						
						Intent intent = new Intent(Settings_2.this, DeviceListActivity.class);
						
						intent.putParcelableArrayListExtra("device.list", list);
						
						startActivity(intent);						
					}
				}
			});
			
			scan_bluetooth.setOnClickListener(new View.OnClickListener() {				
				@Override
				public void onClick(View arg0) {
					mBluetoothAdapter.startDiscovery();
					
					IntentFilter filter = new IntentFilter();
					
					filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
					filter.addAction(BluetoothDevice.ACTION_FOUND);
					filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
					filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
					
					registerReceiver(mReceiver, filter);
				}
			});
			
			mActivateBtn.setOnClickListener(new View.OnClickListener() {				
				@Override
				public void onClick(View v) {
					if (mBluetoothAdapter.isEnabled()) {
						mBluetoothAdapter.disable();
						
						showDisabled();
					} else {
						Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
						
					    startActivityForResult(intent, 1000);
					}
				}
			});
			
			if (mBluetoothAdapter.isEnabled()) {
				showEnabled();
			} else {
				showDisabled();
			}
		}
		
		
		
	}
	
	/*@Override
	public void onDestroy() {
		unregisterReceiver(mReceiver);
		
		super.onDestroy();
	}*/
	
		
		private void showEnabled() {
			showToast("Bluetooth is On");
			
			mActivateBtn.setText("Disable");		
			mActivateBtn.setEnabled(false);
			
			mPairedBtn.setEnabled(false);
			mScanBtn.setEnabled(false);
		}
		
		private void showDisabled() {
			showToast("Bluetooth is Off");
			
			mActivateBtn.setText("Enable");
			mActivateBtn.setEnabled(false);
			
			mPairedBtn.setEnabled(false);
			mScanBtn.setEnabled(false);
		}
		
		private void showUnsupported() {
			showToast("Bluetooth is unsupported by this device");
			
			mActivateBtn.setText("Enable");
			mActivateBtn.setEnabled(false);
			
			mPairedBtn.setEnabled(false);
			mScanBtn.setEnabled(false);
		}
		
	
	
	private void showToast(String msg) {
		Toast toast = Toast.makeText(getApplicationContext(), "" + msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		View toastView = toast.getView();
		
		ViewGroup group = (ViewGroup) toast.getView();
	    TextView messageTextView = (TextView) group.getChildAt(0);
	    messageTextView.setTextSize(24);
		
    	toastView.setBackgroundResource(R.drawable.toast_background);
	    toast.show();
	}
	
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
	    public void onReceive(Context context, Intent intent) {	    	
	        String action = intent.getAction();
	        
	        if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
	        	final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
	        	 
	        	if (state == BluetoothAdapter.STATE_ON) {
	        		showToast("Enabled");
	        		 
	        		showEnabled();
	        	 }
	        } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
	        	mDeviceList = new ArrayList<BluetoothDevice>();
				
				mProgressDlg.show();
	        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
	        	mProgressDlg.dismiss();
	        	
	        	Intent newIntent = new Intent(Settings_2.this, DeviceListActivity.class);
	        	newIntent.putParcelableArrayListExtra("device.list", mDeviceList);
				startActivity(newIntent);

	        	//new DeviceListActivity().execute();
	        	
	        } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
	        	BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		        
	        	mDeviceList.add(device);
	        	
	        	showToast("Found device " + device.getName());
	        }
	    }
	};
	
	
	/*public class DeviceListActivity extends Activity {
		private ListView mListView;
		private DeviceListAdapter mAdapter;
		private ArrayList<BluetoothDevice> mDeviceList;
		BluetoothDevice device ;
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.activity_bluetooth__paired_list);
			this.setFinishOnTouchOutside(false);
			
			mDeviceList		= getIntent().getExtras().getParcelableArrayList("device.list");
			
			mListView		= (ListView) findViewById(R.id.lv_paired);
			
			mAdapter		= new DeviceListAdapter(this);
			
			mAdapter.setData(mDeviceList);
			mAdapter.setListener(new DeviceListAdapter.OnPairButtonClickListener() {			
				@Override
				public void onPairButtonClick(int position) {
					device = mDeviceList.get(position);
					
					if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
						unpairDevice(device);
					} else {
						showToast("Pairing...");
						
						pairDevice(device);
					}
				}
			});
			
			mListView.setAdapter(mAdapter);
			
			registerReceiver(mPairReceiver, new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED)); 
		}
		
		public void execute() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onDestroy() {
			unregisterReceiver(mPairReceiver);
			
			super.onDestroy();
		}
		
		
		private void showToast(String message) {
			Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
		}
		
	    private void pairDevice(BluetoothDevice device) {
	        try {
	            Method method = device.getClass().getMethod("createBond", (Class[]) null);
	            method.invoke(device, (Object[]) null);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    private void unpairDevice(BluetoothDevice device) {
	        try {
	            Method method = device.getClass().getMethod("removeBond", (Class[]) null);
	            method.invoke(device, (Object[]) null);
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    private final BroadcastReceiver mPairReceiver = new BroadcastReceiver() {
		    public void onReceive(Context context, Intent intent) {
		        String action = intent.getAction();
		        
		        if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {	        	
		        	 final int state 		= intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
		        	 final int prevState	= intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.ERROR);
		        	 
		        	 if (state == BluetoothDevice.BOND_BONDED && prevState == BluetoothDevice.BOND_BONDING) {
		        		 showToast("Paired");
		        		 Log.i("device.getAddress() ::::", ""+device.getAddress());
		        		 Settings_2.et_bt_address.setText(""+device.getAddress());
		        		 Intent send = new Intent(getApplicationContext(), Settings_2.class);
		        		 String BT = device.getAddress();
		        		 send.putExtra("Blutooth_value", BT);
		        		 BtFLG = true ;
		        		 startActivity(send);
		        		 
		        		 Log.i("device.getAddress() ::::", ""+device.getAddress());
		        		 Settings_2.et_bt_address.setText(""+device.getAddress());
		        		 
		        	 } else if (state == BluetoothDevice.BOND_NONE && prevState == BluetoothDevice.BOND_BONDED){
		        		 showToast("Unpaired");
		        	 }
		        	 
		        	 mAdapter.notifyDataSetChanged();
		        }
		    }
		};
	}*/
	
}
