package com.mtpv.LawnOrder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.analogics.n5library.N5Information;
import com.analogics.n5library.N5Library;
import com.analogics.n5library.printer.Fonts;
import com.analogics.n5library.printer.PrtFormatting;
import com.analogics.n5library.printer.PrtTextStream;
import com.analogics.thermalAPI.Bluetooth_Printer_3inch_ThermalAPI;
import com.analogics.thermalprinter.AnalogicsThermalPrinter;
import com.esys.leoparddemo.Act_BTDiscovery;
import com.evolute.bluetooth.BluetoothComm;
import com.example.button.R;
import com.leopard.api.FPS;
import com.leopard.api.FpsConfig;
import com.leopard.api.Setup;

@SuppressWarnings("unused")
public class PreviousHistoryActivity extends Activity {
	
	final int PROGRESS_DIALOG = 1;
	static ProgressDialog dlgCustom,dlgpd;
	public static String selectedID = "";
	EditText id_text_prev_hist2, firm_text_prev_hist2;
	Spinner prevH_id_options_Phistry, prevH_id_options_Phistry2;
	TextView  id_text_prev_hist,
				prevH_off_name, 
				prevH_off_fname,
				off_dob, 
				prevH_off_Pnum, 
				prevH_off_address,
				prevH_off_age, prevH_text, off_id_options_Phistry2;
	ImageView details, details2, prevH_fps_image, pH_print, history_details2, back_btn;
	RadioGroup prevshistry_type;
	RadioButton prevH_id_based, prevH_fps_based, prevH_firm_based;
	LinearLayout respondent, prevH_id_layout, prevH_fps_layout, prevH_id_layout2, prevH_firm_layout, prevH_firm_layout2;
	List<String> list;
	Setup set ;
	com.leopard.api.Printer ptr;
	Button fpt_btn, firm_btn;
	//Printer_GEN prn;
	FPS fps;
	public static final int DEVICE_NOTCONNECTED = -100;
	public static String typeOfFinger="";
	public static Map<String,byte[]>fpsmap1=new HashMap<String,byte[] >();
	public static Map<String,byte[]>fpsmap2=new HashMap<String,byte[] >();
	public static Map<String,byte[]>fpsmap3=new HashMap<String,byte[] >();
	public static Map<String,byte[]>fpsmap4=new HashMap<String,byte[] >();
	public static byte[] bufvalue = {};
	
	Context context = this;
	private static final int MESSAGE_BOX = 1;
	private boolean verifycompressed = false;
	private boolean verifyuncompressed = false;
	private int iRetVal;
	private byte[] image = {};
	private boolean verifyfinger = false;
	private static Map<String,String> secMap=null;
	private static List<String> checkedList=new  ArrayList<String>();
	private static Map<String,String> idMap=null;
	private static String challanGenresp ="";
	private static String NAMESPACE = "http://service.mother.com";
	public static String SOAP_ACTION = NAMESPACE;
	public static String SOAP_ACTION_ID = NAMESPACE + "getHistory", challanResponse;
	public static String SOAP_ACTION_FIRM = NAMESPACE + "getFirmBasedHistory", firmchallanResponse;
	public static String SOAP_ACTION_FPS = NAMESPACE + "getHistoryfps", fpsResponse;
	public static String SOAP_ACTION_up = NAMESPACE + "generatePettyCase", Opdata_Chalana,
            spot_final_res_status;
	
	@SuppressLint("ShowToast")
	@SuppressWarnings({ "static-access" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_previous_history);
	//	getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		N5Library.initialize(PreviousHistoryActivity.this);
		String serviceid=new N5Information().getServiceIdentity();
		boolean platform_status =new N5Information().isPlatformAvailable();
		// Toast.makeText(getApplicationContext(), "platform_status::"+platform_status, 3000).show();
		
		 try {
				//set=new Setup();
				boolean bl = set.blActivateLibrary(PreviousHistoryActivity.this, R.raw.licence_full);
				Log.e("Setup", "Activation Status: "+bl);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		
		prevH_text = (TextView)findViewById(R.id.prevH_text);
		
		prevshistry_type = (RadioGroup)findViewById(R.id.prevshistry_type);
		prevH_id_based = (RadioButton)findViewById(R.id.prevH_id_based);
		prevH_fps_based = (RadioButton)findViewById(R.id.prevH_fps_based);
		
		prevH_firm_based = (RadioButton)findViewById(R.id.firm_based);
		prevH_firm_layout = (LinearLayout)findViewById(R.id.prevH_firm_layout);
		prevH_firm_layout2 = (LinearLayout)findViewById(R.id.prevH_firm_layout2);
		firm_text_prev_hist2 = (EditText)findViewById(R.id.firm_text_prev_hist2);
		firm_btn = (Button)findViewById(R.id.firm_btn);
		
		prevH_id_options_Phistry2 = (Spinner)findViewById(R.id.prevH_id_options_Phistry2);
		id_text_prev_hist2 = (EditText)findViewById(R.id.id_text_prev_hist2);
		
		pH_print = (ImageView)findViewById(R.id.pH_print);
		pH_print.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AsyncGetPrintDetails().execute();
				/*PrtTextStream prtTextStream=new PrtTextStream();
				PrtFormatting prtFormatting=new PrtFormatting();
				Fonts font = null;*/
				
			/*	try {
					prtFormatting.setFont(font.COURIER_12_7_CPI);
					prtTextStream.write(prevH_text.getText().toString()+"\n");
					prtFormatting.setFont(font.COURIER_14_5_CPI);
					prtTextStream.write(prevH_text.getText().toString()+"\n");
					prtFormatting.setFont(font.COURIER_15_6_CPI);
					prtTextStream.write(prevH_text.getText().toString()+"\n");
					prtFormatting.setFont(font.COURIER_18_5_CPI);
					prtTextStream.write(prevH_text.getText().toString()+"\n");
					prtFormatting.setFont(font.COURIER_13_5_CPI);
					prtTextStream.write(prevH_text.getText().toString()+"\n");
					prtTextStream.flush();
					
				} catch (IOException e) {
					e.printStackTrace();
				}*/
				
			
			}
		});
		
		firm_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AsyncGetfirmDetails().execute();
			}
		});
		
		fpt_btn = (Button)findViewById(R.id.fpt_btn);
		fpt_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AsyncGetFPS().execute();
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
		
		prevH_id_layout = (LinearLayout)findViewById(R.id.prevH_id_layout);
		prevH_fps_layout = (LinearLayout)findViewById(R.id.prevH_fps_layout);
		prevH_id_layout2 = (LinearLayout)findViewById(R.id.prevH_id_layout2);
		
		prevH_id_based.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				prevH_id_layout.setVisibility(View.VISIBLE);
				prevH_fps_layout.setVisibility(View.GONE);
				prevH_firm_layout.setVisibility(View.GONE);
				prevH_text.setText("");
				id_text_prev_hist.setText("");
			}
		});
		
		prevH_firm_based.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				prevH_id_layout.setVisibility(View.GONE);
				prevH_fps_layout.setVisibility(View.GONE);
				prevH_id_layout2.setVisibility(View.GONE);
				prevH_id_layout.setVisibility(View.GONE);
				prevH_fps_layout.setVisibility(View.GONE);
				prevH_firm_layout.setVisibility(View.VISIBLE);
				prevH_text.setText("");
			}
		});
		
		prevH_fps_based.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				prevH_id_layout.setVisibility(View.GONE);
				prevH_fps_layout.setVisibility(View.VISIBLE);
				prevH_id_layout2.setVisibility(View.VISIBLE);
				prevH_firm_layout.setVisibility(View.GONE);
				prevH_text.setText("");
				id_text_prev_hist.setText("");
			}
		});
		
		respondent = (LinearLayout)findViewById(R.id.respondent);
	//	prevH_fps_image = (ImageView)findViewById(R.id.prevH_fps_image);
		details = (ImageView)findViewById(R.id.history_details);
		details.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				pH_print.setVisibility(View.VISIBLE);
				prevH_text.setVisibility(View.VISIBLE);
				new AsyncGetPreviousDetails().execute();
			}
		});
		
		/*details2 = (ImageView)findViewById(R.id.history_details2);
		details2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				new AsyncGetFPS().execute();
			}
		});*/
		
		id_text_prev_hist = (EditText)findViewById(R.id.id_text_prev_hist);
		
		prevH_id_options_Phistry = (Spinner)findViewById(R.id.prevH_id_options_Phistry);
		idMap=new DataBase(getApplicationContext()).getIdMap(getApplicationContext());
		list = new ArrayList<String>();
		list.add("-Select ID");
		for(String idCode: idMap.keySet()){
			list.add(idMap.get(idCode));
		}
		 
			ArrayAdapter<String> adp = new ArrayAdapter<String>
			(this, R.layout.spinner_item, list);
		adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		
		prevH_id_options_Phistry.setAdapter(adp);
		prevH_id_options_Phistry.setOnItemSelectedListener(new OnItemSelectedListener() {
 
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				switch(arg2) {
				case 0 :
					id_text_prev_hist.setHint("Enter ID Details");
					id_text_prev_hist.setText("");
					id_text_prev_hist.setInputType(InputType.TYPE_CLASS_TEXT );
					id_text_prev_hist.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

					
					break;
				case 1 :
					id_text_prev_hist.setHint(getLabel("1"));
					id_text_prev_hist.setText("");
					prevH_text.setText("");
					id_text_prev_hist.setInputType(InputType.TYPE_CLASS_NUMBER);
					id_text_prev_hist.setFilters(new InputFilter[] { new InputFilter.LengthFilter(12) });
					
					break;
				case 2 :
					id_text_prev_hist.setHint(getLabel("2"));
					id_text_prev_hist.setText("");
					prevH_text.setText("");
					id_text_prev_hist.setInputType(InputType.TYPE_CLASS_TEXT);
					id_text_prev_hist.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
					break;
				case 3 :
					id_text_prev_hist.setHint(getLabel("3"));
					id_text_prev_hist.setText("");
					prevH_text.setText("");
					id_text_prev_hist.setInputType(InputType.TYPE_CLASS_TEXT);
					id_text_prev_hist.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
					break;
				case 4 :
					id_text_prev_hist.setHint(getLabel("4"));
					id_text_prev_hist.setText("");
					prevH_text.setText("");
					id_text_prev_hist.setInputType(InputType.TYPE_CLASS_TEXT);
					id_text_prev_hist.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
					break;
					
				case 5 :
					id_text_prev_hist.setHint(getLabel("5"));
					id_text_prev_hist.setText("");
					prevH_text.setText("");
					id_text_prev_hist.setInputType(InputType.TYPE_CLASS_TEXT);
					id_text_prev_hist.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
					break;
				case 6 :
					id_text_prev_hist.setHint(getLabel("6"));
					id_text_prev_hist.setText("");
					prevH_text.setText("");
					id_text_prev_hist.setInputType(InputType.TYPE_CLASS_TEXT);
					id_text_prev_hist.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
					break;
				case 7 :
					id_text_prev_hist.setHint(getLabel("7"));
					id_text_prev_hist.setText("");
					prevH_text.setText("");
					id_text_prev_hist.setInputType(InputType.TYPE_CLASS_TEXT);
					id_text_prev_hist.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
					break;
				case 8 :
					id_text_prev_hist.setHint(getLabel("8"));
					id_text_prev_hist.setText("");
					prevH_text.setText("");
					id_text_prev_hist.setInputType(InputType.TYPE_CLASS_NUMBER);
					id_text_prev_hist.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });
					break;
				case 9 :
					id_text_prev_hist.setHint(getLabel("9"));
					id_text_prev_hist.setText("");
					prevH_text.setText("");
					id_text_prev_hist.setInputType(InputType.TYPE_CLASS_TEXT);
					id_text_prev_hist.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
					break;
				case 10 :
					id_text_prev_hist.setHint(getLabel("10"));
					id_text_prev_hist.setText("");
					prevH_text.setText("");
					break;
					
					
				default :
					id_text_prev_hist.setText("Nothing Selected");
					break;
			}	
			}
	
			public CharSequence getLabel(String selected) {
				String label="";
				try {
					for(String idCode: PreviousHistoryActivity.idMap.keySet()){
						if(idCode.equals(selected)){
							label="ENTER "+PreviousHistoryActivity.idMap.get(idCode);
							PreviousHistoryActivity.selectedID=PreviousHistoryActivity.idMap.get(idCode).toString();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				Log.i("selected label name "+selected,label);
				return label;
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		prevH_id_options_Phistry2 = (Spinner)findViewById(R.id.prevH_id_options_Phistry2);
		idMap=new DataBase(getApplicationContext()).getIdMap(getApplicationContext());
		list = new ArrayList<String>();
		list.add("-Select ID");
		for(String idCode: idMap.keySet()){
			list.add(idMap.get(idCode));
		}
		 
			ArrayAdapter<String> adp2 = new ArrayAdapter<String>
			(this, R.layout.spinner_item, list);
		adp2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		
		prevH_id_options_Phistry2.setAdapter(adp2);
		prevH_id_options_Phistry2.setOnItemSelectedListener(new OnItemSelectedListener() {
 
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				switch(arg2) {
				case 0 :
					id_text_prev_hist2.setHint(getLabel("0"));
					id_text_prev_hist2.setText("");
					break;
				case 1 :
					id_text_prev_hist2.setHint(getLabel("1"));
					id_text_prev_hist2.setText("");
					id_text_prev_hist2.setFilters(new InputFilter[] { new InputFilter.LengthFilter(12) });
					id_text_prev_hist2.setInputType(InputType.TYPE_CLASS_NUMBER);
					break;
				case 2 :
					id_text_prev_hist2.setHint(getLabel("2"));
					id_text_prev_hist2.setText("");
					break;
				case 3 :
					id_text_prev_hist2.setHint(getLabel("3"));
					id_text_prev_hist2.setText("");
					break;
				case 4 :
					id_text_prev_hist2.setHint(getLabel("4"));
					id_text_prev_hist2.setText("");
					break;
					
				case 5 :
					id_text_prev_hist2.setHint(getLabel("5"));
					id_text_prev_hist2.setText("");
					break;
				case 6 :
					id_text_prev_hist.setHint(getLabel("6"));
					id_text_prev_hist.setText("");
					break;
				case 7 :
					id_text_prev_hist.setHint(getLabel("7"));
					id_text_prev_hist.setText("");
					break;
				case 8 :
					id_text_prev_hist.setHint(getLabel("8"));
					id_text_prev_hist.setText("");
					id_text_prev_hist2.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });
					id_text_prev_hist2.setInputType(InputType.TYPE_CLASS_NUMBER);
					break;
				case 9 :
					id_text_prev_hist.setHint(getLabel("9"));
					id_text_prev_hist.setText("");
					break;
				case 10 :
					id_text_prev_hist.setHint(getLabel("10"));
					id_text_prev_hist.setText("");
					break;
					
					
				default :
					id_text_prev_hist.setText("Nothing Selected");
					break;
			}	
			}
	
			public CharSequence getLabel(String selected) {
				String label="";
				try {
					for(String idCode: PreviousHistoryActivity.idMap.keySet()){
						if(idCode.equals(selected)){
							label="ENTER "+PreviousHistoryActivity.idMap.get(idCode);
							PreviousHistoryActivity.selectedID=PreviousHistoryActivity.idMap.get(idCode).toString();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				Log.i("selected label name "+selected,label);
				return label;
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		}
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	
	/*class AsyncGetPrintDetails extends AsyncTask<Void,Void, String>{
		
		ProgressDialog dialog = new ProgressDialog(PreviousHistoryActivity.this);
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
				ptr.iPrinterAddData(com.leopard.api.Printer.PR_FONTSMALLNORMAL, prevH_text.getText().toString());
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
				if (!isNetworkAvailable()) {
				    // do something
			        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PreviousHistoryActivity.this);
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
		}*/
	
	
  class AsyncGetPrintDetails extends AsyncTask<Void,Void, String>{
		
		ProgressDialog dialog = new ProgressDialog(PreviousHistoryActivity.this);
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
				
				String printdata=preparePrintData.font_Courier_36(""+ prevH_text.getText().toString());
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
				/*dialog.setContentView(R.layout.custom_progress_dialog);
				dialog.setCancelable(false); 
				dialog.show();*/
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				//dialog.dismiss();
				removeDialog(PROGRESS_DIALOG);
			}
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
	
	
	class AsyncGetfirmDetails extends AsyncTask<Void,Void, String>{
		ProgressDialog dialog = new ProgressDialog(PreviousHistoryActivity.this);
			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
			Log.i("getPreviouysHistory called ", "YES");
			String  response="";
			String pidCode="";
			String pidName="";
			String psCode="";
			String psName="";
			String cadreCd="";
			String cadreName="";
			String unitCd="";
			String unitName="";
			
			try {
				SQLiteDatabase db = openOrCreateDatabase(DataBase.DATABASE_NAME,MODE_PRIVATE, null);
				String selectQuery = "SELECT  * FROM " + DataBase.LOGIN_TABLE;
			     // SQLiteDatabase db = this.getWritableDatabase();
			    Cursor cursor = db.rawQuery(selectQuery, null);
		       // looping through all rows and adding to list
			    
		        if (cursor.moveToFirst()) {
		            do {
		            
		            	 pidCode = cursor.getString(0);
		            	 pidName = cursor.getString(1);
		            	 psCode = cursor.getString(2);
		            	 psName = cursor.getString(3);
		            	 cadreCd = cursor.getString(4);
		            	 cadreName = cursor.getString(5);
		            	 unitCd = cursor.getString(6);
		            	 unitName = cursor.getString(7);
		            	 
		            } while (cursor.moveToNext());
		        }
		        db.close(); 
			} catch (Exception e) {
				e.printStackTrace();
				
			}
			 
				try {
							String ip = "";
							String firm_name = firm_text_prev_hist2.getText().toString();
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
							// INSERT ID PROOF DETAILS START
							SoapObject request = new SoapObject(NAMESPACE, "getFirmBasedHistory");
						   
							request.addProperty("firmName", firm_name);
							
							SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
							envelope.dotNet = true;
							envelope.setOutputSoapObject(request);
							Log.i("request", "" + request);
							HttpTransportSE androidHttpTransport = new HttpTransportSE(ip+"/HydPettyCaseService/services/PettyCaseServiceImpl?wsdl");
							androidHttpTransport.call(SOAP_ACTION_FIRM, envelope);
							Object result = envelope.getResponse();
							firmchallanResponse=result.toString();
							Log.i("** Firm Details response***", "" + firmchallanResponse);
							runOnUiThread(new Runnable() {
							    public void run(){ 
							    	if(firmchallanResponse!=null){
										   try {
											   prevH_text.setText(firmchallanResponse);
											   prevH_text.setVisibility(View.VISIBLE);
										   		}
										   		catch(Exception e){
							                    	e.printStackTrace();
							                    }
										}else{
											Log.i("no data from service", "no ");
									}
							    }
							});
							//					// INSERT ID PROOF DETAILS  END
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
				/*dialog.setContentView(R.layout.custom_progress_dialog);
				dialog.setCancelable(false); 
				dialog.show();*/
			}
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				//dialog.dismiss();
				removeDialog(PROGRESS_DIALOG);
				pH_print.setVisibility(View.VISIBLE);
				if (!isNetworkAvailable()) {
				    // do something
			        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PreviousHistoryActivity.this);
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
	
	class AsyncGetPreviousDetails extends AsyncTask<Void,Void, String>{
		ProgressDialog dialog = new ProgressDialog(PreviousHistoryActivity.this);
			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
			Log.i("getPreviouysHistory called ", "YES");
			String  response="";
			String pidCode="";
			String pidName="";
			String psCode="";
			String psName="";
			String cadreCd="";
			String cadreName="";
			String unitCd="";
			String unitName="";
			
			try {
				SQLiteDatabase db = openOrCreateDatabase(DataBase.DATABASE_NAME,MODE_PRIVATE, null);
				String selectQuery = "SELECT  * FROM " + DataBase.LOGIN_TABLE;
			     // SQLiteDatabase db = this.getWritableDatabase();
			    Cursor cursor = db.rawQuery(selectQuery, null);
		       // looping through all rows and adding to list
			    
		        if (cursor.moveToFirst()) {
		            do {
		            
		            	 pidCode = cursor.getString(0);
		            	 pidName = cursor.getString(1);
		            	 psCode = cursor.getString(2);
		            	 psName = cursor.getString(3);
		            	 cadreCd = cursor.getString(4);
		            	 cadreName = cursor.getString(5);
		            	 unitCd = cursor.getString(6);
		            	 unitName = cursor.getString(7);
		            	 
		            } while (cursor.moveToNext());
		        }
		        db.close(); 
			} catch (Exception e) {
				e.printStackTrace();
				
			}
			 
				try {
							String ip = "";
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
							// INSERT ID PROOF DETAILS START
							SoapObject request = new SoapObject(NAMESPACE, "getHistory");
							Log.i("id_text_prev_hist :::::::::::::",id_text_prev_hist.getText().toString());
						   
							request.addProperty("idType", getIDCode(PreviousHistoryActivity.selectedID));
							request.addProperty("idDetails", id_text_prev_hist.getText().toString());
							
							SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
							envelope.dotNet = true;
							envelope.setOutputSoapObject(request);
							Log.i("request", "" + request);
							HttpTransportSE androidHttpTransport = new HttpTransportSE(ip+"/HydPettyCaseService/services/PettyCaseServiceImpl?wsdl");
							Log.i("androidHttpTransport", "" + androidHttpTransport);
							androidHttpTransport.call(SOAP_ACTION_ID, envelope);
							Object result = envelope.getResponse();
							challanGenresp=result.toString();
							Log.i("** getDetailsByAADHAR response***", "" + challanGenresp);
							runOnUiThread(new Runnable() {
							    public void run(){ 
							    	if(challanGenresp!=null){
										   try {
											   prevH_text.setText(challanGenresp);
											   
										   		}
										   		catch(Exception e){
							                    	e.printStackTrace();
							                    }
										}else{
											Log.i("no data from service", "no ");
									}
							    }
							});
							//					// INSERT ID PROOF DETAILS  END
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
				/*dialog.setContentView(R.layout.custom_progress_dialog);
				dialog.setCancelable(false); 
				dialog.show();*/
			}
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				//dialog.dismiss();
				removeDialog(PROGRESS_DIALOG);
				if (!isNetworkAvailable()) {
				    // do something
			        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PreviousHistoryActivity.this);
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

	
	Bitmap bitmapimage = null;
	byte[] bCmpData;
	byte[] bUncmpData;
	byte[] bBmpData;

	
	class AsyncGetFPS extends AsyncTask<Void,Void, String>{
		byte []fpsResp=null;
		ProgressDialog dialog = new ProgressDialog(PreviousHistoryActivity.this);
		
	@Override
	protected void onPreExecute() {
		
		progressDialog(context, "Place your finger on FPS ...");
		super.onPreExecute();
		}

	@Override
	protected String doInBackground(Void... params) {
		String  response="";
		String pidCode="";
		String pidName="";
		String psCode="";
		String psName="";
		String cadreCd="";
		String cadreName="";
		String unitCd="";
		String unitName="";
		
		try {
			SQLiteDatabase db = openOrCreateDatabase(DataBase.DATABASE_NAME,MODE_PRIVATE, null);
			String selectQuery = "SELECT  * FROM " + DataBase.LOGIN_TABLE;
		     // SQLiteDatabase db = this.getWritableDatabase();
		    Cursor cursor = db.rawQuery(selectQuery, null);
	       // looping through all rows and adding to list
		    
	        if (cursor.moveToFirst()) {
	            do {
	            
	            	 pidCode = cursor.getString(0);
	            	 pidName = cursor.getString(1);
	            	 psCode = cursor.getString(2);
	            	 psName = cursor.getString(3);
	            	 cadreCd = cursor.getString(4);
	            	 cadreName = cursor.getString(5);
	            	 unitCd = cursor.getString(6);
	            	 unitName = cursor.getString(7);
	            	 
	            } while (cursor.moveToNext());
	        }
	        db.close(); 
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		try {
					String ip = "";
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
					// INSERT ID PROOF DETAILS START
					SoapObject request = new SoapObject(NAMESPACE, "getHistoryfps");
					Log.i("id_text_prev_hist :::::::::::::",id_text_prev_hist.getText().toString());
					request.addProperty("idType", getIDCode(PreviousHistoryActivity.selectedID));
					request.addProperty("idDetails", id_text_prev_hist2.getText().toString());
					
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.dotNet = true;
					envelope.setOutputSoapObject(request);
					Log.i("request", "" + request);
					HttpTransportSE androidHttpTransport = new HttpTransportSE(ip+"/HydPettyCaseService/services/PettyCaseServiceImpl?wsdl");
					Log.i("androidHttpTransport", "" + androidHttpTransport);
					androidHttpTransport.call(SOAP_ACTION_FPS, envelope);
					Object result = envelope.getResponse();
					//fpsResp=serialize(result);
					fpsResp=org.apache.commons.codec.binary.Base64.decodeBase64(result.toString().getBytes());
					Log.i("** fpsResponse response***", "" + fpsResp);
					runOnUiThread(new Runnable() {
					    @SuppressWarnings("static-access")
						public void run(){ 
					    	if (!Act_BTDiscovery.DEVICE_NAME.equals("")) {
								try {
									BluetoothComm btcomm = new BluetoothComm(Act_BTDiscovery.DEVICE_NAME);
									if(btcomm.isConnect()){
					    				int iRetVal = btcomm.createConn();
					    				Log.e("TAG", "iretvalue:"+iRetVal);
					    				if (iRetVal==BluetoothComm.BT_CONNECTED){
					    					ptr = new com.leopard.api.Printer(set, btcomm.mosOut,btcomm.misIn);
					    					fps=new com.leopard.api.FPS(set, btcomm.mosOut,btcomm.misIn);
					    				
											//prevH_text.setText(challanGenresp);
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
								}
					    	/*if (verifyfinger == false) {
								dlgCustom.dismiss();
								handler.obtainMessage(MESSAGE_BOX,"Please capture finger and then verify").sendToTarget();
							} else if (verifyfinger == true) {
							}*/
					    	if(true){
								try {
									FpsConfig fpsconfig = new FpsConfig(0, (byte) 0x0F);
									bufvalue = fps.bFpsCaptureTemplate(fpsconfig);
									byte []bminitiaedate =fps.bGetMinutiaeData();//
									
									iRetVal = fps.iFpsVerifyTemplate(fpsResp,new FpsConfig(1, (byte) 0x0f));
								} catch (NullPointerException e) {
									iRetVal = DEVICE_NOTCONNECTED;
									//return iRetVal;
								}
							}
							//return iRetVal;
					    	if(challanGenresp!=null){
								   try {
									   prevH_text.setVisibility(View.VISIBLE);
									   prevH_text.setText(challanGenresp);
								   		}
								   		catch(Exception e){
					                    	e.printStackTrace();
					                    }
								}else{
									Log.i("no data from service", "no ");
									   prevH_text.setText(challanGenresp);
									   prevH_text.setVisibility(View.VISIBLE);
							}
					    }//run end
					});
					//					// INSERT ID PROOF DETAILS  END
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//******************************
				//Log.i("DoInBackground Called ::", "AsyncGetPrintDetails");
    			//Log.i("Act_BTDiscovery.DEVICE_NAME ::", Act_BTDiscovery.DEVICE_NAME);
		
				return null;
			}//end of doinBack

	

			public String getBase64EncodedString(byte[] bs) {
				// TODO Auto-generated method stub
				 String imgString ="NA";
				 try {
					 if(bs!=null){
						 imgString = Base64.encodeToString(bs, Base64.NO_WRAP);
					 }
					
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
				return imgString.trim();
			}

			@Override
			protected void onPostExecute(String result) {

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
					handler.obtainMessage(MESSAGE_BOX,"Captured template verification is failed").sendToTarget();
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
		};
	};
	/* To show response messages */
	public void ShowDialog(String str) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle("Verification Status");
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

	public static byte[] serialize(Object obj) throws IOException {
		 ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
		  
		    ObjectOutputStream os = new ObjectOutputStream(out);
		    os.writeObject(obj);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
	    return out.toByteArray();
	}

	public static void progressDialog(Context context, String msg) {
		dlgCustom = new ProgressDialog(context);
		dlgCustom.setMessage(msg);
		dlgCustom.setIndeterminate(true);
		dlgCustom.setCancelable(false);
		dlgCustom.show();
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

	public String getIDCode(String eT_ID) {
		String idCd="NA";
		 try {
			for(String idCode: idMap.keySet()){
				if(eT_ID.equals(idMap.get(idCode))){
					idCd=idCode;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		 Log.i("idCd for "+eT_ID+" :", idCd);
		return idCd;
	}
	 
	@Override
	public void onBackPressed() {
		
	}
}
