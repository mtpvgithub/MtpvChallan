package com.mtpv.LawnOrder;

import java.lang.reflect.Method;
import java.util.ArrayList;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.audiofx.Virtualizer.Settings;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.button.R;
import com.example.button.Settings_2;

/**
 * Device list.
 * 
 * @author Lorensius W. L. T <lorenz@londatiga.net>
 *
 */
public class DeviceListActivity extends Activity {
	private ListView mListView;
	private DeviceListAdapter mAdapter;
	private ArrayList<BluetoothDevice> mDeviceList;
	public static 	BluetoothDevice device ;
	TextView tv_device ;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bluetooth__paired_list);
		
		mDeviceList		= getIntent().getExtras().getParcelableArrayList("device.list");
		
		mListView		= (ListView) findViewById(R.id.lv_paired);
		
		tv_device = (TextView)findViewById(R.id.tv_device);
		
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
	
	@Override
	public void onDestroy() {
		unregisterReceiver(mPairReceiver);
		
		super.onDestroy();
	}
	
	
	private void showToast(String message) {
		Toast toast = Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		View toastView = toast.getView();
		
		ViewGroup group = (ViewGroup) toast.getView();
	    TextView messageTextView = (TextView) group.getChildAt(0);
	    messageTextView.setTextSize(24);
		
    	toastView.setBackgroundResource(R.drawable.toast_background);
	    toast.show();
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
	        		 finish();
	        		 showToast("Paired");
	        		 //finish();
	        		 try {
	        			  Log.i("+device.getAddress() ::::",""+device.getAddress());
	        			 tv_device.setText(""+device.getAddress());
	        			 Log.i("tv_device ::::",""+tv_device.getText().toString().trim());
	        			 String my_bt = tv_device.getText().toString().trim();
		        		 
	        			 Settings_2.et_bt_address.setText(""+my_bt);
	        			 
	        			 DataBase helper= new DataBase(getApplicationContext());
	     				
	     				ContentValues values = new ContentValues();
	     				values.put("BT_Name", tv_device.getText().toString().trim());
	     				SQLiteDatabase db = openOrCreateDatabase(DataBase.DATABASE_NAME,MODE_PRIVATE, null);
	     				//db.execSQL("delete from " + DataBase.USER_TABLE);    
	     				db.execSQL("DROP TABLE IF EXISTS " + DataBase.Bluetooth);
	     				db.execSQL(DataBase.CREATE_Bluetooth);
	     	  			db.insert(DataBase.Bluetooth, null, values);
	     	  			System.out.println("*********************OFFICER TABLE Insertion Successfully **********************************");
	     	  			
	     	  			if (Settings_2.et_bt_address.getText().toString().equals("") || !Settings_2.et_bt_address.getText().toString().equals("")) {
	     	  				//DataBase helper= new DataBase(getApplicationContext());
	     	  				try {
	     	  					//android.database.sqlite.SQLiteDatabase db = openOrCreateDatabase(DataBase.DATABASE_NAME,MODE_PRIVATE, null);
	     	  					String selectQuery = "SELECT  * FROM " + DataBase.Bluetooth;
	     	  				     //SQLiteDatabase db = this.getWritableDatabase();
	     	  				    Cursor cursor = db.rawQuery(selectQuery, null);
	     	  			       // looping through all rows and adding to list
	     	  				    
	     	  			        if (cursor.moveToFirst()) {
	     	  			            do {
	     	  			            	
	     	  			            	Log.i("1 :",""+ cursor.getString(0));
	     	  			           	 	String BLT_Name = cursor.getString(0);
	     	  			           	 	
	     	  			           	 	Settings_2.et_bt_address.setText(BLT_Name);
	     	  			           	 
	     	  		            		} while (cursor.moveToNext());
	     	  			        		}
	     	  							db.close(); 
	     	  						} catch (Exception e) {
	     	  							e.printStackTrace();
	     	  					
	     	  					}
	     	  			   
	     	  			}
		        		 
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
	        		 
	        	 } else if (state == BluetoothDevice.BOND_NONE && prevState == BluetoothDevice.BOND_BONDED){
	        		 showToast("Unpaired");
	        	 }
	        	 
	        	 mAdapter.notifyDataSetChanged();
	        }
	    }
	};
}