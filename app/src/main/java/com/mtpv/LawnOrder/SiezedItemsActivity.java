package com.mtpv.LawnOrder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.button.R;

public class SiezedItemsActivity extends Activity implements OnClickListener {

	public static ArrayList<String> Ditenditems = new ArrayList<String>();
	static LinearLayout detendL;
	static LayoutInflater layoutInflater;
	private static String SelPicId="1";
	
	public static String Seized_Items ="NA";
	public static String Current_Date;
	
	GPSTracker gps;
	public static String latitude = "0", longitude = "0";
	SharedPreferences sharedPreferences;
	public static final String MYPREFERENCES = "MyPrefs";
	
	private Calendar cal;
	@SuppressWarnings("unused")
	private int day;
	@SuppressWarnings("unused")
	private int month;
	@SuppressWarnings("unused")
	private int year;
	
	public static byte[] seizedImageInbyteArray = null;
	static View addView;
	String Textviewitems = "";
	public static EditText Itemname_ET, Itemname_ET2, Itemname_ET3, Itemname_ET4, Itemname_ET5, Itemname_ET6,Itemname_ET7,
				Itemname_ET8, Itemname_ET9, Itemname_ET10, qty_ET, qty_ET2, qty_ET3, qty_ET4,
				qty_ET5, qty_ET6, qty_ET7, qty_ET8, qty_ET9, qty_ET10;
	Button add_Btn, remove_Btn, remove_Btn2, remove_Btn3, remove_Btn4, remove_Btn5, remove_Btn6,
				remove_Btn7, remove_Btn8, remove_Btn9, remove_Btn10;
	ImageView camera_seize, reset_btnseized, save_seized, back_seized;
	LinearLayout container, seize_layout, seize_layout2, seize_layout3, seize_layout4, seize_layout5,
					seize_layout6, seize_layout7, seize_layout8, seize_layout9, seize_layout10;
	
	public static boolean itemFlg1=false, itemFlg2=false, itemFlg3=false, itemFlg4=false, itemFlg5=false, itemFlg6=false,
							itemFlg7=false, itemFlg8=false, itemFlg9=false, itemFlg10=false;
	int clickcount =0;
	public static StringBuffer previewseizedPromtBuf=new StringBuffer();
	 @SuppressWarnings("unused")
	private Uri fileUri;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_siezed_items);
		
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		gps = new GPSTracker(SiezedItemsActivity.this);
		sharedPreferences = getSharedPreferences(MYPREFERENCES,
				Context.MODE_PRIVATE);

		cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);

		gps = new GPSTracker(SiezedItemsActivity.this);

		if (gps.canGetLocation()) {
			latitude = gps.getLatitude() + "";
			longitude = gps.getLongitude() + "";
			Log.i("Lattitude  ::", latitude.toString());
			Log.i("Longitude  ::", longitude.toString());
			Log.i("D Lt ::", "" + Double.parseDouble(latitude));
			Log.i("D Lng ::", "" + Double.parseDouble(longitude));
			// Toast.makeText(getApplicationContext(),
			// "Your Location is - \nLat: " + latitude + "\nLong: " + longitude,
			// Toast.LENGTH_SHORT).show();
			// GetAddress();
		} else {
			gps.showSettingsAlert();
		}

		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			// Toast.makeText(this, "GPS is Enabled in your devide",
			// Toast.LENGTH_SHORT).show();
		}/* else {
			showGPSDisabledAlertToUser();
		}
		private void showGPSDisabledAlertToUser() {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
			alertDialogBuilder
					.setMessage(
							"    GPS is Disabled in your Device \n            Please Enable GPS?")
					.setCancelable(false)
					.setPositiveButton("Goto Settings",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									Intent callGPSSettingIntent = new Intent(
											android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
									startActivity(callGPSSettingIntent);
								}
							});
			alertDialogBuilder.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
			AlertDialog alert = alertDialogBuilder.create();
			alert.show();
		}*/
		
		seize_layout = (LinearLayout)findViewById(R.id.seize_layout);
		seize_layout2 = (LinearLayout)findViewById(R.id.seize_layout2);
		seize_layout3 = (LinearLayout)findViewById(R.id.seize_layout3);
		seize_layout4 = (LinearLayout)findViewById(R.id.seize_layout4);
		seize_layout5 = (LinearLayout)findViewById(R.id.seize_layout5);
		seize_layout6 = (LinearLayout)findViewById(R.id.seize_layout6);
		seize_layout7 = (LinearLayout)findViewById(R.id.seize_layout7);
		seize_layout8 = (LinearLayout)findViewById(R.id.seize_layout8);
		seize_layout9 = (LinearLayout)findViewById(R.id.seize_layout9);
		seize_layout10 = (LinearLayout)findViewById(R.id.seize_layout10);
		
		Itemname_ET = (EditText)findViewById(R.id.Itemname_ET);
		Itemname_ET2 = (EditText)findViewById(R.id.Itemname_ET2);
		Itemname_ET3 = (EditText)findViewById(R.id.Itemname_ET3);
		Itemname_ET4 = (EditText)findViewById(R.id.Itemname_ET4);
		Itemname_ET5 = (EditText)findViewById(R.id.Itemname_ET5);
		Itemname_ET6 = (EditText)findViewById(R.id.Itemname_ET6);
		Itemname_ET7 = (EditText)findViewById(R.id.Itemname_ET7);
		Itemname_ET8 = (EditText)findViewById(R.id.Itemname_ET8);
		Itemname_ET9 = (EditText)findViewById(R.id.Itemname_ET9);
		Itemname_ET10 = (EditText)findViewById(R.id.Itemname_ET10);
		
		qty_ET = (EditText)findViewById(R.id.qty_ET);
		qty_ET2 = (EditText)findViewById(R.id.qty_ET2);
		qty_ET3 = (EditText)findViewById(R.id.qty_ET3);
		qty_ET4 = (EditText)findViewById(R.id.qty_ET4);
		qty_ET5 = (EditText)findViewById(R.id.qty_ET5);
		qty_ET6 = (EditText)findViewById(R.id.qty_ET6);
		qty_ET7 = (EditText)findViewById(R.id.qty_ET7);
		qty_ET8 = (EditText)findViewById(R.id.qty_ET8);
		qty_ET9 = (EditText)findViewById(R.id.qty_ET9);
		qty_ET10 = (EditText)findViewById(R.id.qty_ET10);

		remove_Btn2 = (Button)findViewById(R.id.remove_Btn2);
		remove_Btn3 = (Button)findViewById(R.id.remove_Btn3);
		remove_Btn4 = (Button)findViewById(R.id.remove_Btn4);
		remove_Btn5 = (Button)findViewById(R.id.remove_Btn5);
		remove_Btn6 = (Button)findViewById(R.id.remove_Btn6);
		remove_Btn7 = (Button)findViewById(R.id.remove_Btn7);
		remove_Btn8 = (Button)findViewById(R.id.remove_Btn8);
		remove_Btn9 = (Button)findViewById(R.id.remove_Btn9);
		remove_Btn10 = (Button)findViewById(R.id.remove_Btn10);
		
		

		add_Btn = (Button)findViewById(R.id.add_Btn);
		add_Btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickcount=clickcount+1;
				 if(clickcount>4){
				     	Toast toast = Toast.makeText(getApplicationContext(), "You Can Add Only 5 Seized Items", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, -200);
						View toastView = toast.getView();
				    	toastView.setBackgroundResource(R.drawable.toast_background);
					    toast.show();
				 	}
				 else{
					 if(clickcount==1){
						 seize_layout2.setVisibility(View.VISIBLE);
						 Itemname_ET2.setFocusable(true);
					 }
					 if(clickcount==2){
						 seize_layout3.setVisibility(View.VISIBLE);
					 }
					 if(clickcount==3){
						 seize_layout4.setVisibility(View.VISIBLE);
					 }
					 if(clickcount==4){
						 seize_layout5.setVisibility(View.VISIBLE);
					 }
					 /* if(clickcount==5){
						 seize_layout6.setVisibility(View.VISIBLE);
					 }
					 if(clickcount==6){
						 seize_layout7.setVisibility(View.VISIBLE);
					 }
					 if(clickcount==7){
						 seize_layout8.setVisibility(View.VISIBLE);
					 }
					 if(clickcount==8){
						 seize_layout9.setVisibility(View.VISIBLE);
					 }
					 if(clickcount==9){
						 seize_layout10.setVisibility(View.VISIBLE);
					 }*/
				 }
			}
		});
		Log.i("detendL ::::",""+ OffenderActivity.seizedItems);
         
		if(Ditenditems==null){
			Ditenditems = new ArrayList<String>();
		}
		
		 ArrayList<String> seizedItemsList =SiezedItemsActivity.Ditenditems;
	        if(seizedItemsList!=null){
	        	Log.i("seizedItemsList :::", seizedItemsList.size()+"");
	        	int k=1;
		        for(String siezedItem:seizedItemsList){
		        	Log.i("siezedItem :",siezedItem);
		        	 String []prevseizedItems=siezedItem.toString().split("\\:");
		        	 if(k==1){
		        		 seize_layout.setVisibility(View.VISIBLE);
		        		 Itemname_ET.setText(prevseizedItems[0]);
		        		 qty_ET.setText(prevseizedItems[1]);
		        	 }
		        	 if(k==2){
		        		 seize_layout2.setVisibility(View.VISIBLE);
		        		 Itemname_ET2.setText(prevseizedItems[0]);
		        		 qty_ET2.setText(prevseizedItems[1]);
		        	 }
		        	 if(k==3){
		        		 seize_layout3.setVisibility(View.VISIBLE);
		        		 Itemname_ET3.setText(prevseizedItems[0]);
		        		 qty_ET3.setText(prevseizedItems[1]);
		        	 }
		        	 if(k==4){
		        		 seize_layout4.setVisibility(View.VISIBLE);
		        		 Itemname_ET4.setText(prevseizedItems[0]);
		        		 qty_ET4.setText(prevseizedItems[1]);
		        	 }
		        	 if(k==5){
		        		 seize_layout5.setVisibility(View.VISIBLE);
		        		 Itemname_ET5.setText(prevseizedItems[0]);
		        		 qty_ET5.setText(prevseizedItems[1]);
		        	 }
		        	 if(k==6){
		        		 seize_layout6.setVisibility(View.VISIBLE);
		        		 Itemname_ET6.setText(prevseizedItems[0]);
		        		 qty_ET6.setText(prevseizedItems[1]);
		        	 }
		        	 if(k==7){
		        		 seize_layout7.setVisibility(View.VISIBLE);
		        		 Itemname_ET7.setText(prevseizedItems[0]);
		        		 qty_ET7.setText(prevseizedItems[1]);
		        	 }
		        	 if(k==8){
		        		 seize_layout8.setVisibility(View.VISIBLE);
		        		 Itemname_ET8.setText(prevseizedItems[0]);
		        		 qty_ET8.setText(prevseizedItems[1]);
		        	 }
		        	 if(k==9){
		        		 seize_layout9.setVisibility(View.VISIBLE);
		        		 Itemname_ET9.setText(prevseizedItems[0]);
		        		 qty_ET9.setText(prevseizedItems[1]);
		        	 }
		        	 if(k==10){
		        		 seize_layout10.setVisibility(View.VISIBLE);
		        		 Itemname_ET10.setText(prevseizedItems[0]);
		        		 qty_ET10.setText(prevseizedItems[1]);
		        	 }
		        	 k++;
		        }
		        
		        
	        }
		
		Itemname_ET = (EditText)findViewById(R.id.Itemname_ET);
		qty_ET =(EditText)findViewById(R.id.qty_ET);
		reset_btnseized = (ImageView)findViewById(R.id.reset_btnseized);
		save_seized = (ImageView)findViewById(R.id.save_seized);
		back_seized = (ImageView)findViewById(R.id.back_seized);
		detendL = (LinearLayout)findViewById(R.id.detendL);
		
		camera_seize = (ImageView)findViewById(R.id.camera_seize);
		camera_seize.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SiezedItemsActivity.SelPicId="1";
				selectImage();	
			}
		});
		
		/*add_Btn =(Button)findViewById(R.id.add_Btn);
		add_Btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
				 final View addView = layoutInflater.inflate(R.layout.row, null);
				 TextView textOut = (TextView)addView.findViewById(R.id.textout);
				 TextView qty = (TextView)addView.findViewById(R.id.qty);
				 
				 textOut.setText(Itemname_ET.getText().toString());
				 qty.setText((qty_ET.getText().toString()!=null&& qty_ET.getText().toString().trim().length()>0)?qty_ET.getText().toString():"0");
				 
				 Textviewitems = textOut.getText().toString() +":"+qty.getText().toString();
				 
				 Log.e("Textviewitems", Textviewitems);
				 Ditenditems.add(Textviewitems + "\n");
				 
				 Itemname_ET.setText("");
			     qty_ET.setText("");
				 
			     detendL.addView(addView);
				 
				 Button buttonRemove = (Button)addView.findViewById(R.id.remove);
				 buttonRemove.setOnClickListener(new OnClickListener(){
				     @Override
				     public void onClick(View v) {
				      ((LinearLayout)addView.getParent()).removeView(addView);
				     }});
				   
				}});*/
		
				back_seized.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();
					}
				});
				save_seized.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Log.i("Entered", "Yes");
						boolean validFlg=true;
						 if(seize_layout.getVisibility()==View.VISIBLE && Itemname_ET.getText().toString()!=null && !"".equals(Itemname_ET.getText().toString().trim()) &&
								 qty_ET.getText().toString()!=null && !"".equals(qty_ET.getText().toString().trim())){
							
							String Textviewitems = Itemname_ET.getText().toString() +":"+qty_ET.getText().toString();
							 
							 if(SiezedItemsActivity.Ditenditems.contains(Textviewitems)){
								 Log.i("item 1 already added", Textviewitems);
							 }else{
								 SiezedItemsActivity.Ditenditems.add(Textviewitems+"\n");
								 itemFlg1=true;
								 Log.i("ITEM 1 ADDED", Itemname_ET.getText().toString() +":"+qty_ET.getText().toString());
							 }
						 }else if (seize_layout.getVisibility()==View.VISIBLE && Itemname_ET.getText().toString()!=null && "".equals(Itemname_ET.getText().toString()) &&
								 qty_ET.getText().toString()!=null && "".equals(qty_ET.getText().toString())) {
							 validFlg=false;
							 	Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Valid Seized Item Name and Quantity ", Toast.LENGTH_LONG);
								toast.setGravity(Gravity.CENTER, 0, -200);
								View toastView = toast.getView();
						    	toastView.setBackgroundResource(R.drawable.toast_background);
							    toast.show(); 
						}
						 
						 if(seize_layout2.getVisibility()==View.VISIBLE && Itemname_ET2.getText().toString()!=null && !"".equals(Itemname_ET2.getText().toString().trim()) &&
								 qty_ET2.getText().toString()!=null && !"".equals(qty_ET2.getText().toString().trim())){
							
							
							
							 String Textviewitems = Itemname_ET2.getText().toString() +":"+qty_ET2.getText().toString();
							 if(SiezedItemsActivity.Ditenditems.contains(Textviewitems)){
								 Log.i("item 2 already added", Textviewitems);
							 }else{
								 SiezedItemsActivity.Ditenditems.add(Textviewitems+"\n");
								 itemFlg2=true;
								 Log.i("ITEM 2 ADDED", Itemname_ET2.getText().toString() +":"+qty_ET2.getText().toString());
							 }
						 }else if (seize_layout2.getVisibility()==View.VISIBLE && Itemname_ET2.getText().toString()!=null && "".equals(Itemname_ET2.getText().toString().trim()) &&
								 qty_ET2.getText().toString()!=null && "".equals(qty_ET2.getText().toString().trim())) {
							 Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Valid Seized Item Name and Quantity ", Toast.LENGTH_LONG);
								toast.setGravity(Gravity.CENTER, 0, -200);
								View toastView = toast.getView();
						    	toastView.setBackgroundResource(R.drawable.toast_background);
							    toast.show(); 
							 	validFlg=false;
							
						}
						 if(seize_layout3.getVisibility()==View.VISIBLE && Itemname_ET3.getText().toString()!=null && !"".equals(Itemname_ET3.getText().toString().trim()) &&
								 qty_ET3.getText().toString()!=null && !"".equals(qty_ET3.getText().toString().trim())){
							String Textviewitems = Itemname_ET3.getText().toString() +":"+qty_ET3.getText().toString();
							 if(SiezedItemsActivity.Ditenditems.contains(Textviewitems)){
								 Log.i("item 3 already added", Textviewitems);
							 }else{
								 SiezedItemsActivity.Ditenditems.add(Textviewitems+"\n");
								 itemFlg3=true;
								 Log.i("ITEM 3 ADDED", Itemname_ET3.getText().toString() +":"+qty_ET3.getText().toString());
							 }
						 }else if (seize_layout3.getVisibility()==View.VISIBLE && Itemname_ET3.getText().toString()!=null && "".equals(Itemname_ET3.getText().toString().trim()) &&
								 qty_ET3.getText().toString()!=null && "".equals(qty_ET3.getText().toString().trim())) {
							 Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Valid Seized Item Name and Quantity ", Toast.LENGTH_LONG);
								toast.setGravity(Gravity.CENTER, 0, -200);
								View toastView = toast.getView();
						    	toastView.setBackgroundResource(R.drawable.toast_background);
							    toast.show(); 	
							 	 validFlg=false;
							
						}
						 
						 if(seize_layout4.getVisibility()==View.VISIBLE && Itemname_ET4.getText().toString()!=null && !"".equals(Itemname_ET4.getText().toString().trim()) &&
								 qty_ET4.getText().toString()!=null && !"".equals(qty_ET4.getText().toString().trim())){
							 String Textviewitems = Itemname_ET4.getText().toString() +":"+qty_ET4.getText().toString();
							 if(SiezedItemsActivity.Ditenditems.contains(Textviewitems)){
								 Log.i("item 4 already added", Textviewitems);
							 }else{
								 SiezedItemsActivity.Ditenditems.add(Textviewitems+"\n");
								 itemFlg4=true;
								 Log.i("ITEM 4 ADDED", Itemname_ET4.getText().toString() +":"+qty_ET4.getText().toString());
							 }
						 }else if (seize_layout4.getVisibility()==View.VISIBLE && Itemname_ET4.getText().toString()!=null && "".equals(Itemname_ET4.getText().toString().trim()) &&
								 qty_ET4.getText().toString()!=null && "".equals(qty_ET4.getText().toString().trim())) {
							 Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Valid Seized Item Name and Quantity ", Toast.LENGTH_LONG);
								toast.setGravity(Gravity.CENTER, 0, -200);
								View toastView = toast.getView();
						    	toastView.setBackgroundResource(R.drawable.toast_background);
							    toast.show(); 
							 	 validFlg=false;
							
						}
						 
						 if(seize_layout5.getVisibility()==View.VISIBLE && Itemname_ET5.getText().toString()!=null && !"".equals(Itemname_ET5.getText().toString().trim()) &&
								 qty_ET5.getText().toString()!=null && !"".equals(qty_ET5.getText().toString().trim())){
							 String Textviewitems = Itemname_ET5.getText().toString() +":"+qty_ET5.getText().toString();
							 if(SiezedItemsActivity.Ditenditems.contains(Textviewitems)){
								 Log.i("item 5 already added", Textviewitems);
							 }else{
								 SiezedItemsActivity.Ditenditems.add(Textviewitems+"\n");
								 itemFlg5=true;
								 Log.i("ITEM 5 ADDED", Itemname_ET5.getText().toString() +":"+qty_ET5.getText().toString());
							 }
						 }else if (seize_layout5.getVisibility()==View.VISIBLE && Itemname_ET5.getText().toString()!=null && "".equals(Itemname_ET5.getText().toString().trim()) &&
								 qty_ET5.getText().toString()!=null && "".equals(qty_ET5.getText().toString().trim())) {
							 Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Valid Seized Item Name and Quantity ", Toast.LENGTH_LONG);
								toast.setGravity(Gravity.CENTER, 0, -200);
								View toastView = toast.getView();
						    	toastView.setBackgroundResource(R.drawable.toast_background);
							    toast.show(); 	
							 	 validFlg=false;
							
						}
						 if(seize_layout6.getVisibility()==View.VISIBLE && Itemname_ET6.getText().toString()!=null && !"".equals(Itemname_ET6.getText().toString().trim()) &&
								 qty_ET6.getText().toString()!=null && !"".equals(qty_ET6.getText().toString().trim())){
							 String Textviewitems = Itemname_ET6.getText().toString() +":"+qty_ET6.getText().toString();
							 if(SiezedItemsActivity.Ditenditems.contains(Textviewitems)){
								 Log.i("item 6 already added", Textviewitems);
							 }else{
								 SiezedItemsActivity.Ditenditems.add(Textviewitems+"\n");
								 itemFlg6=true;
								 Log.i("ITEM 6 ADDED", Itemname_ET6.getText().toString() +":"+qty_ET6.getText().toString());
							 }
						 }else if (seize_layout6.getVisibility()==View.VISIBLE && Itemname_ET6.getText().toString()!=null && "".equals(Itemname_ET6.getText().toString().trim()) &&
								 qty_ET6.getText().toString()!=null && "".equals(qty_ET6.getText().toString().trim())) {
							 Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Valid Seized Item Name and Quantity ", Toast.LENGTH_LONG);
								toast.setGravity(Gravity.CENTER, 0, -200);
								View toastView = toast.getView();
						    	toastView.setBackgroundResource(R.drawable.toast_background);
							    toast.show(); 
							 	 validFlg=false;
							
						}
						 if(seize_layout7.getVisibility()==View.VISIBLE && Itemname_ET7.getText().toString()!=null && !"".equals(Itemname_ET7.getText().toString().trim()) &&
								 qty_ET7.getText().toString()!=null && !"".equals(qty_ET7.getText().toString().trim())){
							 String Textviewitems = Itemname_ET7.getText().toString() +":"+qty_ET7.getText().toString();
							 if(SiezedItemsActivity.Ditenditems.contains(Textviewitems)){
								 Log.i("item 7 already added", Textviewitems);
							 }else{
								 SiezedItemsActivity.Ditenditems.add(Textviewitems+"\n");
								 itemFlg7=true;
								 Log.i("ITEM 7 ADDED", Itemname_ET7.getText().toString() +":"+qty_ET7.getText().toString());
							 }
						 }else if (seize_layout7.getVisibility()==View.VISIBLE && Itemname_ET7.getText().toString()!=null && "".equals(Itemname_ET7.getText().toString().trim()) &&
								 qty_ET7.getText().toString()!=null && "".equals(qty_ET7.getText().toString().trim())) {
							 Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Valid Seized Item Name and Quantity ", Toast.LENGTH_LONG);
								toast.setGravity(Gravity.CENTER, 0, -200);
								View toastView = toast.getView();
						    	toastView.setBackgroundResource(R.drawable.toast_background);
							    toast.show(); 	
							 	 validFlg=false;
						}
						 
						 if(seize_layout8.getVisibility()==View.VISIBLE && Itemname_ET8.getText().toString()!=null && !"".equals(Itemname_ET8.getText().toString().trim()) &&
								 qty_ET8.getText().toString()!=null && !"".equals(qty_ET8.getText().toString().trim())){
							 String Textviewitems = Itemname_ET8.getText().toString() +":"+qty_ET8.getText().toString();
							 if(SiezedItemsActivity.Ditenditems.contains(Textviewitems)){
								 Log.i("item 8 already added", Textviewitems);
							 }else{
								 SiezedItemsActivity.Ditenditems.add(Textviewitems+"\n");
								 itemFlg8=true;
								 Log.i("ITEM 8 ADDED", Itemname_ET8.getText().toString() +":"+qty_ET8.getText().toString());
							 }
						 }else if (seize_layout8.getVisibility()==View.VISIBLE && Itemname_ET8.getText().toString()!=null && "".equals(Itemname_ET8.getText().toString().trim()) &&
								 qty_ET8.getText().toString()!=null && "".equals(qty_ET8.getText().toString().trim())) {
							 Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Valid Seized Item Name and Quantity ", Toast.LENGTH_LONG);
								toast.setGravity(Gravity.CENTER, 0, -200);
								View toastView = toast.getView();
						    	toastView.setBackgroundResource(R.drawable.toast_background);
							    toast.show(); 	
							 	 validFlg=false;
						}
						 
						 if(seize_layout9.getVisibility()==View.VISIBLE && Itemname_ET9.getText().toString()!=null && !"".equals(Itemname_ET9.getText().toString().trim()) &&
								 qty_ET9.getText().toString()!=null && !"".equals(qty_ET9.getText().toString().trim())){
							 String Textviewitems = Itemname_ET9.getText().toString() +":"+qty_ET9.getText().toString();
							 if(SiezedItemsActivity.Ditenditems.contains(Textviewitems)){
								 Log.i("item 9 already added", Textviewitems);
							 }else{
								 SiezedItemsActivity.Ditenditems.add(Textviewitems+"\n");
								 itemFlg9=true;
								 Log.i("ITEM 9 ADDED", Itemname_ET9.getText().toString() +":"+qty_ET9.getText().toString());
							 }
						 }else if (seize_layout9.getVisibility()==View.VISIBLE && Itemname_ET9.getText().toString()!=null && "".equals(Itemname_ET9.getText().toString().trim()) &&
								 qty_ET9.getText().toString()!=null && "".equals(qty_ET9.getText().toString().trim())) {
							 Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Valid Seized Item Name and Quantity ", Toast.LENGTH_LONG);
								toast.setGravity(Gravity.CENTER, 0, -200);
								View toastView = toast.getView();
						    	toastView.setBackgroundResource(R.drawable.toast_background);
							    toast.show(); 	
							 	 validFlg=false;
						}
						 
						 if(seize_layout10.getVisibility()==View.VISIBLE && Itemname_ET10.getText().toString()!=null && !"".equals(Itemname_ET10.getText().toString().trim()) &&
								 qty_ET10.getText().toString()!=null && !"".equals(qty_ET10.getText().toString().trim())){
							 String Textviewitems = Itemname_ET10.getText().toString() +":"+qty_ET10.getText().toString();
							 if(SiezedItemsActivity.Ditenditems.contains(Textviewitems)){
								 Log.i("item 10 already added", Textviewitems);
							 }else{ 
								 SiezedItemsActivity.Ditenditems.add(Textviewitems+"\n");
								 itemFlg10=true;
								 Log.i("ITEM 10 ADDED", Itemname_ET10.getText().toString() +":"+qty_ET10.getText().toString());
							 }
						 }else if (seize_layout10.getVisibility()==View.VISIBLE && Itemname_ET10.getText().toString()!=null && "".equals(Itemname_ET10.getText().toString().trim()) &&
								 qty_ET10.getText().toString()!=null && "".equals(qty_ET10.getText().toString().trim())) {
							 Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Valid Seized Item Name and Quantity ", Toast.LENGTH_LONG);
								toast.setGravity(Gravity.CENTER, 0, -200);
								View toastView = toast.getView();
						    	toastView.setBackgroundResource(R.drawable.toast_background);
							    toast.show(); 
							 	 validFlg=false;
						}
						 
						 for(String item:SiezedItemsActivity.Ditenditems){
							 Log.i("Ditenditems", item);
						 }
						if(validFlg){
						 finish();
						 OffenderActivity.add_siezed_tick.setVisibility(View.VISIBLE);
						}
						
					}
				});
				
				remove_Btn2.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Log.i("Remove 2 Called ::", "Yes");
							if( itemFlg2){
								Log.i("Remove Called ::", "Yes");
								
								try {
									SiezedItemsActivity.Ditenditems.remove(1);
									itemFlg2=false;
									Itemname_ET2.setText("");
									qty_ET2.setText("");
									seize_layout2.setVisibility(View.GONE);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							
					}
				});
				
				remove_Btn3.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Log.i("Remove 3 Called ::", "Yes");
							if(itemFlg3){
								Log.i("Remove Called ::", "Yes");
								try {
									SiezedItemsActivity.Ditenditems.remove(2);
									itemFlg3=false;	
									Itemname_ET3.setText("");
									qty_ET3.setText("");
									seize_layout3.setVisibility(View.GONE);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							
					}
				});
				remove_Btn4.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Log.i("Remove 4 Called ::", "Yes");
							if(itemFlg4){
								Log.i("Remove Called ::", "Yes");
								try {
									SiezedItemsActivity.Ditenditems.remove(3);
									itemFlg4 = false;	
									Itemname_ET4.setText("");
									qty_ET4.setText("");
									seize_layout4.setVisibility(View.GONE);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							
					}
				});
				remove_Btn5.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Log.i("Remove 5 Called ::", "Yes");
							if(itemFlg5){
								Log.i("Remove Called ::", "Yes");
								try {
									SiezedItemsActivity.Ditenditems.remove(4);
									itemFlg5=false;	
									Itemname_ET5.setText("");
									qty_ET5.setText("");
									seize_layout5.setVisibility(View.GONE);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							
					}
				});
				remove_Btn6.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Log.i("Remove 6 Called ::", "Yes");
							if(itemFlg6){
								Log.i("Remove Called ::", "Yes");
								try {
									SiezedItemsActivity.Ditenditems.remove(5);
									itemFlg6 = false;	
									Itemname_ET6.setText("");
									qty_ET6.setText("");
									seize_layout6.setVisibility(View.GONE);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							
					}
				});
				remove_Btn7.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Log.i("Remove 7 Called ::", "Yes");
							if(itemFlg7){
								Log.i("Remove Called ::", "Yes");
								try {
									SiezedItemsActivity.Ditenditems.remove(6);
									itemFlg7 = false;	
									Itemname_ET7.setText("");
									qty_ET7.setText("");
									seize_layout7.setVisibility(View.GONE);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							
					}
				});
				remove_Btn8.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Log.i("Remove 8 Called ::", "Yes");
							if(itemFlg8){
								Log.i("Remove Called ::", "Yes");
								try {
									SiezedItemsActivity.Ditenditems.remove(7);
									itemFlg8 = false;
									Itemname_ET8.setText("");
									qty_ET8.setText("");
									seize_layout8.setVisibility(View.GONE);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							
					}
				});
				remove_Btn9.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Log.i("Remove 9 Called ::", "Yes");
							if(itemFlg9){
								try {
									SiezedItemsActivity.Ditenditems.remove(8);
									itemFlg9 = false;
									Itemname_ET9.setText("");
									qty_ET9.setText("");
									seize_layout9.setVisibility(View.GONE);
								} catch (Exception e) {
									e.printStackTrace();
								}
								Log.i("Remove Called ::", "Yes");
							}
					}
				});
				remove_Btn10.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Log.i("Remove 10 Called ::", "Yes");
							if(itemFlg10){
								Log.i("Remove Called ::", "Yes");
								try {
									SiezedItemsActivity.Ditenditems.remove(9);
									itemFlg10 = false;	
									Itemname_ET10.setText("");
									qty_ET10.setText("");
									seize_layout10.setVisibility(View.GONE);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							
					}
				});
				
				
				reset_btnseized.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Itemname_ET.setText("");
						qty_ET.setText("");
						Itemname_ET2.setText("");
						qty_ET2.setText("");
						Itemname_ET3.setText("");
						qty_ET3.setText("");
						Itemname_ET4.setText("");
						qty_ET4.setText("");
						Itemname_ET5.setText("");
						qty_ET5.setText("");
						seize_layout2.setVisibility(View.GONE);
						seize_layout3.setVisibility(View.GONE);
						seize_layout4.setVisibility(View.GONE);
						seize_layout5.setVisibility(View.GONE);
					}
				});
			}
	
	protected void selectImage() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		final CharSequence[] options = { "Open Camera", "Choose from Gallery","Cancel" };
		AlertDialog.Builder builder = new AlertDialog.Builder(SiezedItemsActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {

        	@Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Open Camera"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

        	if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());

                for (File temp : f.listFiles()) {

                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                	String current_date = OffenderActivity.date;
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "ePettyCase" + File.separator + current_date + File.separator + "eCase_no" ;
                    File camerapath = new File(path);
                    if(!camerapath.exists()){
                    	camerapath.mkdirs();
                    }
	                    f.delete();
	                    OutputStream outFile = null;
	                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    
                    try {
                    	Log.i("Camera Path :::",""+file.getAbsolutePath());
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                    if("1".equals(SiezedItemsActivity.SelPicId)){
                    	/*camera_seize.setImageBitmap(bitmap);
                    	ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                      	bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream1);
                      	seizedImageInbyteArray = stream1.toByteArray();*/
                      	  
                      	Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                    	Canvas canvas = new Canvas(mutableBitmap); //bmp is the bitmap to dwaw into

                    	Paint paint= new Paint();
                    	paint.setColor(Color.RED);
                    	paint.setTextSize(80);
                    	paint.setTextAlign(Paint.Align.CENTER);
                    	int xPos = (canvas.getWidth() / 2);
						int yPos = (int) ((canvas.getHeight() / 2) - ((paint
								.descent() + paint.ascent()) / 2));

						canvas.drawText("Date & Time: " + Current_Date, xPos,
								yPos + 400, paint);
						canvas.drawText("Lat :" + latitude + " Long :"
								+ longitude, xPos, yPos + 500, paint);
                    	camera_seize.setImageBitmap(mutableBitmap);
                    	/*camera_seize.setRotation(90);*/
                    	ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                    	mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 5, stream1);
                    	seizedImageInbyteArray = stream1.toByteArray();
                        Log.i("seizedImageInbyteArray 1::", ""+seizedImageInbyteArray);

                    }
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
	            	Uri selectedImage = data.getData();
	                String[] filePath = { MediaStore.Images.Media.DATA };
	                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
	                c.moveToFirst();
	                int columnIndex = c.getColumnIndex(filePath[0]);
	                String picturePath = c.getString(columnIndex);
	                c.close();
	                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
	 //               Bitmap image = (Bitmap) data.getExtras().get("data");
	                Log.w("path of image from gallery......******************.........", picturePath+"");
	                //picture1.setImageBitmap(thumbnail);
                if("1".equals(SiezedItemsActivity.SelPicId)){
                    
                	/*camera_seize.setImageBitmap(thumbnail);
                	ByteArrayOutputStream stream = new ByteArrayOutputStream();
                	thumbnail.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                	seizedImageInbyteArray = stream.toByteArray();*/
                	
                	Bitmap mutableBitmap = thumbnail.copy(Bitmap.Config.ARGB_8888, true);
                	Canvas canvas = new Canvas(mutableBitmap); //bmp is the bitmap to dwaw into

                	Paint paint= new Paint();
                	paint.setColor(Color.RED);
                	paint.setTextSize(80);
                	paint.setTextAlign(Paint.Align.CENTER);
                	int xPos = (canvas.getWidth() / 2);
					int yPos = (int) ((canvas.getHeight() / 2) - ((paint
							.descent() + paint.ascent()) / 2));

					canvas.drawText("Date & Time: " + Current_Date, xPos,
							yPos + 400, paint);
					canvas.drawText("Lat :" + latitude + " Long :"
							+ longitude, xPos, yPos + 500, paint);
                	camera_seize.setImageBitmap(mutableBitmap);
                	/*camera_seize.setRotation(90);*/
                	ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                	mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 5, stream1);
                	seizedImageInbyteArray = stream1.toByteArray();
                	
                	
                }
            }
        }
    }  


	@Override
	public void onBackPressed() {
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
}
