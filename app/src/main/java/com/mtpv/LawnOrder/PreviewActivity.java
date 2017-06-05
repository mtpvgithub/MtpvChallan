package com.mtpv.LawnOrder;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.button.R;

public class PreviewActivity extends Activity {
	
	LinearLayout respondent, sections, seized, witness;
	ImageView edit, confirm, witnesss_edit, respondent_edit, section_edit, seized_edit;
	GPSTracker gps;
	final int PROGRESS_DIALOG = 1;
	public static TextView respondent_text, sections_text, seized_text, witness_text, leaser_text, manager_text;
	public static String latitude,longitude, shopname ;
	public static String challanGenresp ="";
    public static String NAMESPACE = "http://service.mother.com";
	public static String SOAP_ACTION = NAMESPACE;
	public static String SOAP_ACTION_up = NAMESPACE + "generatePettyCase", Opdata_Chalana, spot_final_res_status;
	public static StringBuffer secBuffer=new StringBuffer();
	public static StringBuffer secBufferPreivew=new StringBuffer();
	public static boolean confirmFlg = false, witnesFlg=false;
	
	LinearLayout leaser_preview, manager_preview ;
	
	ImageView leaser_Edit_btn, manager_Edit_btn ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_preview);
		
		leaser_preview = (LinearLayout)findViewById(R.id.leaser_preview);
		manager_preview = (LinearLayout)findViewById(R.id.manager_preview);
		
		leaser_Edit_btn = (ImageView)findViewById(R.id.leaser_edit);
		manager_Edit_btn  = (ImageView)findViewById(R.id.manager_edit);
		
		leaser_preview.setVisibility(View.GONE);
		manager_preview.setVisibility(View.GONE);
		
		gps = new GPSTracker(PreviewActivity.this);
		secBuffer=new StringBuffer();
		
		gps = new GPSTracker(PreviewActivity.this);
		if(gps.canGetLocation()){
			 latitude = gps.getLatitude()+"";
             longitude = gps.getLongitude()+"";
		}else{
            
            gps.showSettingsAlert();
        }
		
	//	Log.i("Buffer :::",WitnessFormActivity.previewWitnessPromtBuf.toString());

		respondent_text = (TextView)findViewById(R.id.respondent_text);
		leaser_text = (TextView)findViewById(R.id.leaser_text);
		manager_text = (TextView)findViewById(R.id.manager_text);
		sections_text = (TextView)findViewById(R.id.sections_text);
		seized_text = (TextView)findViewById(R.id.seized_text);
		witness_text = (TextView)findViewById(R.id.witness_text);
		
		respondent = (LinearLayout)findViewById(R.id.respondent);
		sections = (LinearLayout)findViewById(R.id.sections);
		seized = (LinearLayout)findViewById(R.id.seized);
		witness = (LinearLayout)findViewById(R.id.witness);
		
		respondent_edit = (ImageView)findViewById(R.id.respondent_edit);
		section_edit = (ImageView)findViewById(R.id.section_edit);
		seized_edit = (ImageView)findViewById(R.id.seized_edit);
		witnesss_edit = (ImageView)findViewById(R.id.witnesss_edit);
		
		respondent_edit.setOnClickListener(new OnClickListener() {
			
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
			OffenderActivity.shop_name.setText("");
			OffenderActivity.shop_Ownername.setText("");
			OffenderActivity.previewrespondentBuf=null;
			OffenderActivity.preview_LeaserBuf=null;
			OffenderActivity.preview_ManagerBuf=null;
			OffenderActivity.secBufferPreivew=null;
			OffenderActivity.seizedBuff=null;
			OffenderActivity.seizedItems=null;
			OffenderActivity.secBuffer=null;
			}
		});
		
		
		leaser_preview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				OffenderActivity.shop_name.setText("");
				OffenderActivity.shop_Ownername.setText("");
				OffenderActivity.previewrespondentBuf=null;
				OffenderActivity.preview_LeaserBuf=null;
				OffenderActivity.preview_ManagerBuf=null;
				OffenderActivity.secBufferPreivew=null;
				OffenderActivity.seizedBuff=null;
				OffenderActivity.seizedItems =null;
				OffenderActivity.secBuffer=null;
				}
			});
		
		manager_preview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				OffenderActivity.shop_name.setText("");
				OffenderActivity.shop_Ownername.setText("");
				OffenderActivity.previewrespondentBuf=null;
				OffenderActivity.preview_LeaserBuf=null;
				OffenderActivity.preview_ManagerBuf=null;
				OffenderActivity.secBufferPreivew=null;
				OffenderActivity.seizedBuff=null;
				OffenderActivity.seizedItems =null;
				OffenderActivity.secBuffer=null;
				}
			});
		
		
		witnesss_edit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			// TODO Auto-generated method stub
			/*witnesFlg=true;*/
			finish();
			
			WitnessFormActivity.previewWitnessPromtBuf=null;
			WitnessFormActivity.ET_WitnessDetails="";
			
			Intent wt_edit = new Intent(getApplicationContext(), WitnessFormActivity.class);
			startActivity(wt_edit);
			
			
			}
		});
		
		seized_edit.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent seiz_edit = new Intent(getApplicationContext(), SiezedItemsActivity.class);
			startActivity(seiz_edit);
			finish();
			SiezedItemsActivity.Ditenditems=null;

			}
		});
		
		section_edit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent sectn_edit = new Intent(getApplicationContext(), Add_SectionsActivity.class);
				startActivity(sectn_edit);
				
				finish();
				
				Add_SectionsActivity.checkedList=null;
				Add_SectionsActivity.secMap=null;
				PreviewActivity.secBuffer=null;
				PreviewActivity.secBufferPreivew=null;
	
				}
		});
		
	
		/**********Respondent Buffer*************/
		OffenderActivity.previewrespondentBuf=new StringBuffer();
		
		if(OffenderActivity.id_off_name.getText()!=null && OffenderActivity.id_off_name.getText().toString().trim().length()>3){
			if(OffenderActivity.shop_based.getVisibility()==View.VISIBLE){
				if (!OffenderActivity.shop_name.getText().toString().trim().equals("") &&
						(OffenderActivity.idproof_yes.isChecked() || OffenderActivity.idproof_no.isChecked())) {
					OffenderActivity.previewrespondentBuf.append("\n Respondant Shop Name : ").append(OffenderActivity.shop_name.getText().toString());
					OffenderActivity.previewrespondentBuf.append("\n Respondant Shop Owner Name : ").append(OffenderActivity.shop_Ownername.getText().toString());
					OffenderActivity.previewrespondentBuf.append("\n Respondant 1 Name : ").append(OffenderActivity.id_off_name.getText().toString());
					OffenderActivity.previewrespondentBuf.append("\n Respondant 1 Father Name : ").append(OffenderActivity.off_fname.getText().toString());
					OffenderActivity.previewrespondentBuf.append("\n Respondant 1 DOB : ").append(OffenderActivity.off_age.getText().toString()!=null?OffenderActivity.off_age.getText().toString():"");
					OffenderActivity.previewrespondentBuf.append("\n Respondant 1 Gender : ").append(OffenderActivity.group1_text.getText().toString());
					OffenderActivity.previewrespondentBuf.append("\n Respondant 1 Mobile : ").append(OffenderActivity.off_Pnum.getText().toString());
					OffenderActivity.previewrespondentBuf.append("\n Respondant 1 Address : ").append(OffenderActivity.off_address.getText().toString());	
				}else {
					OffenderActivity.previewrespondentBuf.append("\n Respondant 1 Name : ").append(OffenderActivity.id_off_name.getText().toString());
					OffenderActivity.previewrespondentBuf.append("\n Respondant 1 Father Name : ").append(OffenderActivity.off_fname.getText().toString());
					OffenderActivity.previewrespondentBuf.append("\n Respondant 1 DOB : ").append(OffenderActivity.off_age.getText().toString()!=null?OffenderActivity.off_age.getText().toString():"");
					OffenderActivity.previewrespondentBuf.append("\n Respondant 1 Gender : ").append(OffenderActivity.group1_text.getText().toString());
					OffenderActivity.previewrespondentBuf.append("\n Respondant 1 Mobile : ").append(OffenderActivity.off_Pnum.getText().toString());
					OffenderActivity.previewrespondentBuf.append("\n Respondant 1 Address : ").append(OffenderActivity.off_address.getText().toString());
				}
				
			}else if(OffenderActivity.shop_based.getVisibility()==View.GONE){
				if (OffenderActivity.idproof_yes.isChecked() || OffenderActivity.idproof_no.isChecked()) {
					OffenderActivity.previewrespondentBuf.append("\n Respondant 1 Name : ").append(OffenderActivity.id_off_name.getText().toString());
					OffenderActivity.previewrespondentBuf.append("\n Respondant 1 Father Name : ").append(OffenderActivity.off_fname.getText().toString());
					OffenderActivity.previewrespondentBuf.append("\n Respondant 1 DOB : ").append(OffenderActivity.off_age.getText().toString()!=null?OffenderActivity.off_age.getText().toString():"");
					OffenderActivity.previewrespondentBuf.append("\n Respondant 1 Gender : ").append(OffenderActivity.group1_text.getText().toString());
					OffenderActivity.previewrespondentBuf.append("\n Respondant 1 Mobile : ").append(OffenderActivity.off_Pnum.getText().toString());
					OffenderActivity.previewrespondentBuf.append("\n Respondant 1 Address : ").append(OffenderActivity.off_address.getText().toString());	
				}				
				
			//previewrespondentBuf.append("Respondant 1 FPS :").append();
			}
		}
		if(OffenderActivity.id_off_name2.getText()!=null && OffenderActivity.id_off_name2.getText().toString().trim().length()>3){
				OffenderActivity.previewrespondentBuf.append("\n\n Respondant 2 Name : ").append(OffenderActivity.id_off_name2.getText().toString());
				OffenderActivity.previewrespondentBuf.append("\n Respondant 2 Father Name : ").append(OffenderActivity.off_fname2.getText().toString());
				OffenderActivity.previewrespondentBuf.append("\n Respondant 2 DOB : ").append(OffenderActivity.off_age2.getText().toString()!=null?OffenderActivity.off_age2.getText().toString():"");
				OffenderActivity.previewrespondentBuf.append("\n Respondant 2 Gender : ").append(OffenderActivity.group2_text.getText().toString());
				OffenderActivity.previewrespondentBuf.append("\n Respondant 2 Mobile : ").append(OffenderActivity.off_Pnum2.getText().toString());
				OffenderActivity.previewrespondentBuf.append("\n Respondant 2 Address : ").append(OffenderActivity.off_address2.getText().toString());
				//previewrespondentBuf.append("Respondant 2 FPS :").append();
			}
		if(OffenderActivity.id_off_name3.getText()!=null && OffenderActivity.id_off_name3.getText().toString().trim().length()>3){
				OffenderActivity.previewrespondentBuf.append("\n\n Respondant 3 Name :").append(OffenderActivity.id_off_name3.getText().toString());
				OffenderActivity.previewrespondentBuf.append("\n Respondant 3 Father Name :").append(OffenderActivity.off_fname3.getText().toString());
				OffenderActivity.previewrespondentBuf.append("\n Respondant 3 DOB :").append(OffenderActivity.off_age3.getText().toString()!=null?OffenderActivity.off_age3.getText().toString():"");
				OffenderActivity.previewrespondentBuf.append("\n Respondant 3 Gender :").append(OffenderActivity.group3_text.getText().toString());
				OffenderActivity.previewrespondentBuf.append("\n Respondant 3 Mobile :").append(OffenderActivity.off_Pnum3.getText().toString());
				OffenderActivity.previewrespondentBuf.append("\n Respondant 3 Address :").append(OffenderActivity.off_address3.getText().toString());
				//previewrespondentBuf.append("Respondant 2 FPS :").append();
		}
		if(OffenderActivity.id_off_name4.getText()!=null && OffenderActivity.id_off_name4.getText().toString().trim().length()>3){
				OffenderActivity.previewrespondentBuf.append("\n\n Respondant 4 Name :").append(OffenderActivity.id_off_name4.getText().toString());
				OffenderActivity.previewrespondentBuf.append("\n Respondant 4 Father Name :").append(OffenderActivity.off_fname4.getText().toString());
				OffenderActivity.previewrespondentBuf.append("\n Respondant 4 DOB :").append(OffenderActivity.off_age4.getText().toString()!=null?OffenderActivity.off_age4.getText().toString():"");
				OffenderActivity.previewrespondentBuf.append("\n Respondant 4 Gender :").append(OffenderActivity.group4_text.getText().toString());
				OffenderActivity.previewrespondentBuf.append("\n Respondant 4 Mobile :").append(OffenderActivity.off_Pnum4.getText().toString());
				OffenderActivity.previewrespondentBuf.append("\n Respondant 4 Address :").append(OffenderActivity.off_address4.getText().toString());
				//previewrespondentBuf.append("Respondant 2 FPS :").append();
		}
		
		if(OffenderActivity.leaser_name.getText()!=null && OffenderActivity.leaser_name.getText().toString().trim().length()>2){
			leaser_preview.setVisibility(View.VISIBLE);
			
			OffenderActivity.preview_LeaserBuf.append("\n Leaser Name :").append(OffenderActivity.leaser_name.getText().toString());
			OffenderActivity.preview_LeaserBuf.append("\n Leaser Father Name :").append(OffenderActivity.leaser_fname.getText().toString());
			OffenderActivity.preview_LeaserBuf.append("\n Leaser DOB :").append(OffenderActivity.leaser_age.getText().toString()!=null?OffenderActivity.off_age4.getText().toString():"");
			OffenderActivity.preview_LeaserBuf.append("\n Leaser Gender :").append(OffenderActivity.leaser_gender_txt.getText().toString());
			OffenderActivity.preview_LeaserBuf.append("\n Leaser Mobile :").append(OffenderActivity.leaser_mobileNo.getText().toString());
			OffenderActivity.preview_LeaserBuf.append("\n Leaser Address :").append(OffenderActivity.leaser_address.getText().toString());
		}
		
		if(OffenderActivity.manager_name.getText()!=null && OffenderActivity.manager_name.getText().toString().trim().length()>2){
			
			manager_preview.setVisibility(View.VISIBLE);
			
			OffenderActivity.preview_ManagerBuf.append("\n manager Name :").append(OffenderActivity.manager_name.getText().toString());
			OffenderActivity.preview_ManagerBuf.append("\n manager Father Name :").append(OffenderActivity.manager_fname.getText().toString());
			OffenderActivity.preview_ManagerBuf.append("\n manager DOB :").append(OffenderActivity.manager_age.getText().toString()!=null?OffenderActivity.off_age4.getText().toString():"");
			OffenderActivity.preview_ManagerBuf.append("\n manager Gender :").append(OffenderActivity.manager_gender_txt.getText().toString());
			OffenderActivity.preview_ManagerBuf.append("\n manager Mobile :").append(OffenderActivity.manager_mobileNo.getText().toString());
			OffenderActivity.preview_ManagerBuf.append("\n manager Address :").append(OffenderActivity.manager_address.getText().toString());
		}
		
	/*	if (OffenderActivity.list_layout_shop.getVisibility()==View.VISIBLE && !OffenderActivity.shop_name.getText().toString().trim().equals("")) {
			OffenderActivity.previewrespondentBuf.append("\n Respondant Shop Name : ").append(OffenderActivity.shop_name.getText().toString());
			OffenderActivity.previewrespondentBuf.append("\n Respondant Shop Owner Name : ").append(OffenderActivity.shop_Ownername.getText().toString());
			OffenderActivity.previewrespondentBuf.append("\n Respondant 1 Name : ").append(OffenderActivity.id_off_name.getText().toString());
			OffenderActivity.previewrespondentBuf.append("\n Respondant 1 Father Name : ").append(OffenderActivity.off_fname.getText().toString());
			OffenderActivity.previewrespondentBuf.append("\n Respondant 1 DOB : ").append(OffenderActivity.off_age.getText().toString()!=null?OffenderActivity.off_age.getText().toString():"");
			OffenderActivity.previewrespondentBuf.append("\n Respondant 1 Gender : ").append(OffenderActivity.group1_text.getText().toString());
			OffenderActivity.previewrespondentBuf.append("\n Respondant 1 Mobile : ").append(OffenderActivity.off_Pnum.getText().toString());
			OffenderActivity.previewrespondentBuf.append("\n Respondant 1 Address : ").append(OffenderActivity.off_address.getText().toString());
		}*/
		
		/**********Section Buffer*************/
		secBufferPreivew=new StringBuffer();
		int i=1;
		if(Add_SectionsActivity.secMap!=null && Add_SectionsActivity.secMap.size()>0){
			for (String secCode:Add_SectionsActivity.secMap.keySet()){
	        	 List<String> secDesc = Add_SectionsActivity.checkedList;
	        	 if(secDesc!=null){  
		        	 for(String secDesction : secDesc){
		        	    	if(Add_SectionsActivity.secMap.get(secCode).equals(secDesction)){
		        	    		System.out.println("selected secDesction : " + secDesction + " map value : : " + Add_SectionsActivity.secMap.get(secCode));
		        	    		if(i==1){
		        	    			//OffenderActivity.secBuffer.append(secCode+":"+secDesction);
		        	    			secBufferPreivew.append(secDesction);
		        	    		}else{
		        	    			//OffenderActivity.secBuffer.append("@"+secCode+":"+secDesction);
		        	    			secBufferPreivew.append("\n"+secDesction);
		        	    			}
		        	    	//	previewrespondentBuf.append(secCode+":"+secDesction);
		        	    		i++;
		        	    }
		       	    }
	        	 }
	        }
		}
		
		/**********Seized Items Buffer****************/
		
		 ArrayList<String> seizedItemsList =SiezedItemsActivity.Ditenditems;
	     //   Log.i("seizedItems size : ",""+seizedItemsList.size() );
	        OffenderActivity.seizedBuff=new StringBuffer();
	        OffenderActivity.seizedItems=new StringBuffer();
	        
	        if(seizedItemsList!=null){
		        if(seizedItemsList.size()>0){
		        	OffenderActivity.seizedBuff.append("    SEIZED ITEM DETAILS \n");
		        	OffenderActivity.seizedBuff.append("ITEM_NAME   QUANTITY\n");
		        }
		        for(String siezedItem:seizedItemsList){
		        	Log.i("siezedItem :",siezedItem);
		        	OffenderActivity.seizedItems.append(siezedItem.toString()).append("@");
		        	OffenderActivity.seizedBuff.append(siezedItem.toString().replaceAll("@", "\n").replaceAll(":", "  "));
		        }
	        }
	        
		respondent_text.setText(OffenderActivity.previewrespondentBuf!=null?OffenderActivity.previewrespondentBuf.toString():"");
		
		leaser_text.setText(OffenderActivity.preview_LeaserBuf!=null?OffenderActivity.preview_LeaserBuf.toString():"");
		manager_text.setText(OffenderActivity.preview_ManagerBuf!=null?OffenderActivity.preview_ManagerBuf.toString():"");
		
		witness_text.setText(WitnessFormActivity.previewWitnessPromtBuf!=null?WitnessFormActivity.previewWitnessPromtBuf.toString():"");		
		sections_text.setText(secBufferPreivew!=null?secBufferPreivew.toString():"");
		seized_text.setText(OffenderActivity.seizedBuff!=null?OffenderActivity.seizedBuff.toString():"");
		
		Log.i("Items ::::", OffenderActivity.seizedBuff!=null?OffenderActivity.seizedBuff.toString():"");
   
		confirm = (ImageView)findViewById(R.id.confirm);
		confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				confirmFlg = true;
				new AsyncGeneratePettycase().execute();
			}
		});
	}
	class AsyncGeneratePettycase extends AsyncTask<Void,Void, String>{
		ProgressDialog dialog = new ProgressDialog(PreviewActivity.this);
		
		@SuppressWarnings("unused")
		@Override
		protected String doInBackground(Void... params) {	
				Log.i("generate method called ", "YES");
				String pidCode="";
				String pidName="";
				String psCode="";
				String psName="";
				String cadreCd="";
				String cadreName="";
				String unitCd="";
				String unitName="";
				
				try {
					String ip="";
					
					/*Log.i("ET_gender before ", "YES");
					Log.i("ET_gender 1 ",OffenderActivity.group1_text.getText()!=null?OffenderActivity.group1_text.getText().toString():"");
					Log.i("ET_gender 2 ",OffenderActivity.group2_text.getText()!=null?OffenderActivity.group2_text.getText().toString():"");
					Log.i("ET_gender 3 ",OffenderActivity.group3_text.getText()!=null?OffenderActivity.group3_text.getText().toString():"");
					Log.i("ET_gender 4 ",OffenderActivity.group4_text.getText()!=null?OffenderActivity.group4_text.getText().toString():"");
					Log.i("ET_gender after ", "YES");*/
					
					try {
						SQLiteDatabase db = openOrCreateDatabase(DataBase.DATABASE_NAME,MODE_PRIVATE, null);
						String selectQuery = "SELECT  * FROM " + DataBase.LOGIN_TABLE;
					     // SQLiteDatabase db = this.getWritableDatabase();
					    Cursor cursor = db.rawQuery(selectQuery, null);
					    
	    		        if (cursor.moveToFirst()) {
	    		            do {
	    		            	 Log.i("1 :",""+ cursor.getString(0));
	       		            	 Log.i("2 :",""+cursor.getString(1));
	       		            	 Log.i("3 :",""+cursor.getString(2));
	       		            	 Log.i("4 :",""+cursor.getString(3));
	       		            	 Log.i("5 :",""+cursor.getString(4));
	       		            	 Log.i("6 :",""+cursor.getString(5));
	    		            	
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
				        
				        int i=1;
				        secBuffer=new StringBuffer();
				        if(Add_SectionsActivity.secMap!=null && Add_SectionsActivity.secMap.size()>0){
				        	for (String secCode:Add_SectionsActivity.secMap.keySet()){
				        	 List<String> secDesc = Add_SectionsActivity.checkedList;
				        	 if(secDesc!=null){   
					        	 for(String secDesction : secDesc){
					        	    	if(Add_SectionsActivity.secMap.get(secCode).equals(secDesction)){
					        	    		System.out.println("selected secDesction : " + secDesction + " map value : : " + Add_SectionsActivity.secMap.get(secCode));
					        	    		if(i==1){
					        	    			secBuffer.append(secCode+":"+secDesction);
					        	    			
					        	    			//OffenderActivity.secBufferPreivew.append(secDesction);
					        	    		}else{
					        	    			secBuffer.append("@"+secCode+":"+secDesction);
					        	    		//	OffenderActivity.secBufferPreivew.append("\n"+secDesction);
					        	    		}
					        	    		
					        	    	//	previewrespondentBuf.append(secCode+":"+secDesction);
					        	    		i++;
					        	    }
					       	    }//end of for loop
				        	 }
				        }
					}

				        SoapObject request = new SoapObject(NAMESPACE, "generatePettyCase");
						//System.out.println("off name :::::::::::::"+id_off_name.getText().toString());
						// Log.i("shop_name",ET_Shop );
						
						// Respondent Details
			            String ET_RespondentDetails = "NA";
			            String ET_ID_RespondentDetails = OffenderActivity.respondent_id_details.getText().toString()!=null?OffenderActivity.respondent_id_details.getText().toString():"";
			            
			        	try {
			        		JSONObject respondent1 = new JSONObject();
			        		JSONObject respondent2 = new JSONObject();
			        		JSONObject respondent3 = new JSONObject();
			        		JSONObject respondent4 = new JSONObject();
			        		JSONObject leaserDetails = new JSONObject();
			        		JSONObject managerDetails = new JSONObject();
				        		
						if(OffenderActivity.id_off_name.getText()!=null && OffenderActivity.id_off_name.getText().toString().trim().length()>3){
							
							respondent1.put("SNO", "1");
							respondent1.put("ET_Name", OffenderActivity.id_off_name.getText().toString());
							respondent1.put("ET_Fname", OffenderActivity.off_fname.getText().toString());
							respondent1.put("ET_AGE", OffenderActivity.off_age.getText().toString()!=null?OffenderActivity.off_age.getText().toString():"");
							Log.i("GENDER ***************************",""+ OffenderActivity.group1_text.getText().toString());
							respondent1.put("ET_Gender", OffenderActivity.group1_text.getText()!=null?OffenderActivity.group1_text.getText().toString():"");
							respondent1.put("ET_Mobile", OffenderActivity.off_Pnum.getText().toString());
							respondent1.put("ET_address", OffenderActivity.off_address.getText().toString());
							respondent1.put("ET_fps", ""+OffenderActivity.bufvalue1);
							//respondent1.put("ET_fps", getBase64ApacheCommonsEncodedString(OffenderActivity.bufvalue1));
							//respondent1.put("ET_fps", OffenderActivity.bufvalue1!=null?OffenderActivity.bufvalue1:null);
							respondent1.put("ET_id_details",ET_ID_RespondentDetails);
							respondent1.put("ET_id_code", getIDCode(OffenderActivity.id1_Spinner));
							respondent1.put("ET_image", getBase64EncodedString(OffenderActivity.image1ByteArray));
						//	Log.i("ET_image 1 ::", ""+getBase64EncodedString(image1ByteArray));
							Log.i("FPS Value ::::::::: :: ",OffenderActivity.bufvalue1+"");
							
							
								OffenderActivity.previewrespondentBuf.append("\n Respondant 1 Name : ").append(OffenderActivity.id_off_name.getText().toString());
								OffenderActivity.previewrespondentBuf.append("\n Respondant 1 Father Name : ").append(OffenderActivity.off_fname.getText().toString());
								OffenderActivity.previewrespondentBuf.append("\n Respondant 1 DOB : ").append(OffenderActivity.off_age.getText().toString()!=null?OffenderActivity.off_age.getText().toString():"");
								OffenderActivity.previewrespondentBuf.append("\n Respondant 1 Gender : ").append(OffenderActivity.group1_text.getText().toString());
								OffenderActivity.previewrespondentBuf.append("\n Respondant 1 Mobile : ").append(OffenderActivity.off_Pnum.getText().toString());
								OffenderActivity.previewrespondentBuf.append("\n Respondant 1 Address : ").append(OffenderActivity.off_address.getText().toString());
								//previewrespondentBuf.append("Respondant 1 FPS :").append();
							}
							//****************************************
								
						if(OffenderActivity.id_off_name2.getText()!=null && OffenderActivity.id_off_name2.getText().toString().trim().length()>3){
							
							respondent2.put("SNO", "2");
							respondent2.put("ET_Name", OffenderActivity.id_off_name2.getText().toString());
							respondent2.put("ET_Fname", OffenderActivity.off_fname2.getText().toString());
							respondent2.put("ET_DOB", OffenderActivity.off_age2.getText().toString()!=null?OffenderActivity.off_age2.getText().toString():"");
							respondent2.put("ET_gender", OffenderActivity.group2_text.getText()!=null?OffenderActivity.group2_text.getText().toString():"");
							respondent2.put("ET_Mobile", OffenderActivity.off_Pnum2.getText().toString());
							respondent2.put("ET_address", OffenderActivity.off_address2.getText().toString());
							respondent2.put("ET_fps", ""+OffenderActivity.bufvalue2);
							//respondent2.put("ET_fps", getBase64ApacheCommonsEncodedString(OffenderActivity.bufvalue2));
							//respondent2.put("ET_fps", OffenderActivity.bufvalue2!=null?OffenderActivity.bufvalue2:null);
							respondent2.put("ET_id_details", OffenderActivity.respondent_id_details2.getText().toString());
							respondent2.put("ET_id_code", getIDCode2(OffenderActivity.id2_Spinner));
							respondent2.put("ET_image", getBase64EncodedString(OffenderActivity.image2ByteArray));
							//Log.i("ET_image 2 ::", ""+getBase64EncodedString(image2ByteArray));
							//Log.i("id1_Spinner 2 value ::::::::: ::", ""+id2_Spinner);
							
								OffenderActivity.previewrespondentBuf.append("\n Respondant 2 Name : ").append(OffenderActivity.id_off_name2.getText().toString());
								OffenderActivity.previewrespondentBuf.append("\n Respondant 2 Father Name : ").append(OffenderActivity.off_fname2.getText().toString());
								OffenderActivity.previewrespondentBuf.append("\n Respondant 2 DOB : ").append(OffenderActivity.off_age2.getText().toString()!=null?OffenderActivity.off_age2.getText().toString():"");
								OffenderActivity.previewrespondentBuf.append("\n Respondant 2 Gender : ").append(OffenderActivity.group2_text.getText().toString());
								OffenderActivity.previewrespondentBuf.append("\n Respondant 2 Mobile : ").append(OffenderActivity.off_Pnum2.getText().toString());
								OffenderActivity.previewrespondentBuf.append("\n Respondant 2 Address : ").append(OffenderActivity.off_address2.getText().toString());
								//previewrespondentBuf.append("Respondant 2 FPS :").append();
							}
							
							//***********************************
								
						if(OffenderActivity.id_off_name3.getText()!=null && OffenderActivity.id_off_name3.getText().toString().trim().length()>3){
							
							respondent3.put("SNO", "3");
							respondent3.put("ET_Name", OffenderActivity.id_off_name3.getText().toString());
							respondent3.put("ET_Fname", OffenderActivity.off_fname3.getText().toString());
							respondent3.put("ET_DOB", OffenderActivity.off_age3.getText().toString()!=null?OffenderActivity.off_age3.getText().toString():"");
							respondent3.put("ET_gender", OffenderActivity.group3_text.getText()!=null?OffenderActivity.group3_text.getText().toString():"");
							respondent3.put("ET_Mobile", OffenderActivity.off_Pnum3.getText().toString());
							respondent3.put("ET_address", OffenderActivity.off_address3.getText().toString());
							respondent3.put("ET_fps", ""+OffenderActivity.bufvalue3);
							//respondent3.put("ET_fps",getBase64ApacheCommonsEncodedString(OffenderActivity.bufvalue3));
							respondent3.put("ET_id_details", OffenderActivity.respondent_id_details3.getText().toString());
							respondent3.put("ET_id_code", getIDCode3(OffenderActivity.id3_Spinner));
							respondent3.put("ET_image", getBase64EncodedString(OffenderActivity.image3ByteArray));
							//Log.i("id1_Spinner 2 value ::::::::: ::", ""+id3_Spinner);
							
								OffenderActivity.previewrespondentBuf.append("\n Respondant 3 Name :").append(OffenderActivity.id_off_name3.getText().toString());
								OffenderActivity.previewrespondentBuf.append("\n Respondant 3 Father Name :").append(OffenderActivity.off_fname3.getText().toString());
								OffenderActivity.previewrespondentBuf.append("\n Respondant 3 DOB :").append(OffenderActivity.off_age3.getText().toString()!=null?OffenderActivity.off_age3.getText().toString():"");
								OffenderActivity.previewrespondentBuf.append("\n Respondant 3 Gender :").append(OffenderActivity.group3_text.getText().toString());
								OffenderActivity.previewrespondentBuf.append("\n Respondant 3 Mobile :").append(OffenderActivity.off_Pnum3.getText().toString());
								OffenderActivity.previewrespondentBuf.append("\n Respondant 3 Address :").append(OffenderActivity.off_address3.getText().toString());
								//previewrespondentBuf.append("Respondant 2 FPS :").append();
							}
							
						if(OffenderActivity.id_off_name4.getText()!=null && OffenderActivity.id_off_name4.getText().toString().trim().length()>3){
							respondent4.put("SNO", "4");
							respondent4.put("ET_Name", OffenderActivity.id_off_name4.getText().toString());
							respondent4.put("ET_Fname", OffenderActivity.off_fname4.getText().toString());
							respondent4.put("ET_DOB", OffenderActivity.off_age4.getText().toString()!=null?OffenderActivity.off_age4.getText().toString():"");
							respondent4.put("ET_gender", OffenderActivity.group4_text.getText()!=null?OffenderActivity.group4_text.getText().toString():"");
							respondent4.put("ET_Mobile", OffenderActivity.off_Pnum4.getText().toString());
							respondent4.put("ET_address", OffenderActivity.off_address4.getText().toString());
							respondent4.put("ET_fps", ""+OffenderActivity.bufvalue4);
							//respondent4.put("ET_fps", getBase64ApacheCommonsEncodedString(OffenderActivity.bufvalue4));
							respondent4.put("ET_id_details", OffenderActivity.respondent_id_details4.getText().toString());
							respondent4.put("ET_id_code", getIDCode4(OffenderActivity.id4_Spinner));
							respondent4.put("ET_image", getBase64EncodedString(OffenderActivity.image4ByteArray));
							//Log.i("id1_Spinner 2 value ::::::::: ::", ""+id4_Spinner);
							
								OffenderActivity.previewrespondentBuf.append("\n Respondant 4 Name :").append(OffenderActivity.id_off_name4.getText().toString());
								OffenderActivity.previewrespondentBuf.append("\n Respondant 4 Father Name :").append(OffenderActivity.off_fname4.getText().toString());
								OffenderActivity.previewrespondentBuf.append("\n Respondant 4 DOB :").append(OffenderActivity.off_age4.getText().toString()!=null?OffenderActivity.off_age4.getText().toString():"");
								OffenderActivity.previewrespondentBuf.append("\n Respondant 4 Gender :").append(OffenderActivity.group4_text.getText().toString());
								OffenderActivity.previewrespondentBuf.append("\n Respondant 4 Mobile :").append(OffenderActivity.off_Pnum4.getText().toString());
								OffenderActivity.previewrespondentBuf.append("\n Respondant 4 Address :").append(OffenderActivity.off_address4.getText().toString());
								//previewrespondentBuf.append("Respondant 2 FPS :").append();
							}
						
					/********LEASER DETAILS*******/	
						String leaserID = OffenderActivity.leaser_id_details.getText().toString()!=null?OffenderActivity.leaser_id_details.getText().toString():"";
						
					if(OffenderActivity.leaser_name.getText()!=null && OffenderActivity.leaser_name.getText().toString().trim().length()>2){
						

						leaserDetails.put("ET_Name", OffenderActivity.leaser_name.getText().toString());
						leaserDetails.put("ET_Fname", OffenderActivity.leaser_fname.getText().toString());
						leaserDetails.put("ET_DOB", OffenderActivity.leaser_age.getText().toString()!=null?OffenderActivity.leaser_age.getText().toString():"");
						leaserDetails.put("ET_gender", OffenderActivity.leaser_gender_txt.getText()!=null?OffenderActivity.leaser_gender_txt.getText().toString():"");
						leaserDetails.put("ET_Mobile", OffenderActivity.leaser_mobileNo.getText().toString());
						leaserDetails.put("ET_address", OffenderActivity.leaser_address.getText().toString());
						leaserDetails.put("ET_id_details",ET_ID_RespondentDetails);
						
						
						OffenderActivity.preview_LeaserBuf.append("\n Leaser Name :").append(OffenderActivity.leaser_name.getText().toString());
						OffenderActivity.preview_LeaserBuf.append("\n Leaser Father Name :").append(OffenderActivity.leaser_fname.getText().toString());
						OffenderActivity.preview_LeaserBuf.append("\n Leaser DOB :").append(OffenderActivity.leaser_age.getText().toString()!=null?OffenderActivity.off_age4.getText().toString():"");
						OffenderActivity.preview_LeaserBuf.append("\n Leaser Gender :").append(OffenderActivity.leaser_gender_txt.getText().toString());
						OffenderActivity.preview_LeaserBuf.append("\n Leaser Mobile :").append(OffenderActivity.leaser_mobileNo.getText().toString());
						OffenderActivity.preview_LeaserBuf.append("\n Leaser Address :").append(OffenderActivity.leaser_address.getText().toString());
					}
					
					/********MANAGER DETAILS*******/	
					String managerID = OffenderActivity.manager_id_details.getText().toString()!=null?OffenderActivity.manager_id_details.getText().toString():"";
					
					if(OffenderActivity.manager_name.getText()!=null && OffenderActivity.manager_name.getText().toString().trim().length()>2){
						managerDetails.put("ET_Name", OffenderActivity.manager_name.getText().toString());
						managerDetails.put("ET_Fname", OffenderActivity.manager_fname.getText().toString());
						managerDetails.put("ET_DOB", OffenderActivity.manager_age.getText().toString()!=null?OffenderActivity.manager_age.getText().toString():"");
						managerDetails.put("ET_gender", OffenderActivity.manager_gender_txt.getText()!=null?OffenderActivity.manager_gender_txt.getText().toString():"");
						managerDetails.put("ET_Mobile", OffenderActivity.manager_mobileNo.getText().toString());
						managerDetails.put("ET_address", OffenderActivity.manager_address.getText().toString());
						managerDetails.put("ET_id_details",ET_ID_RespondentDetails);
						
						
						OffenderActivity.preview_ManagerBuf.append("\n manager Name :").append(OffenderActivity.manager_name.getText().toString());
						OffenderActivity.preview_ManagerBuf.append("\n manager Father Name :").append(OffenderActivity.manager_fname.getText().toString());
						OffenderActivity.preview_ManagerBuf.append("\n manager DOB :").append(OffenderActivity.manager_age.getText().toString()!=null?OffenderActivity.off_age4.getText().toString():"");
						OffenderActivity.preview_ManagerBuf.append("\n manager Gender :").append(OffenderActivity.manager_gender_txt.getText().toString());
						OffenderActivity.preview_ManagerBuf.append("\n manager Mobile :").append(OffenderActivity.manager_mobileNo.getText().toString());
						OffenderActivity.preview_ManagerBuf.append("\n manager Address :").append(OffenderActivity.manager_address.getText().toString());
					}
					//****************************************
						
						
						
								JSONArray idDetails = new JSONArray();
								
								idDetails.put(respondent1);
								if (!OffenderActivity.id_off_name2.getText().toString().trim().equals("")) {
									idDetails.put(respondent2);	
								}
								if (!OffenderActivity.id_off_name3.getText().toString().trim().equals("")) {
									idDetails.put(respondent3);	
								}
								if (!OffenderActivity.id_off_name4.getText().toString().trim().equals("")) {
									idDetails.put(respondent4);	
								}
								
								if (!OffenderActivity.leaser_name.getText().toString().trim().equals("")) {
									idDetails.put(leaserDetails);
									idDetails.put(managerDetails);
										
								}
								
								JSONObject respondentObj = new JSONObject();
								respondentObj.put("Respondents", idDetails);
								ET_RespondentDetails = respondentObj.toString();
								Log.i("ET_RespondentDetails ::",ET_RespondentDetails);
								Log.i("Respondent ID Details ::",OffenderActivity.respondent_id_details.getText().toString());
			        			}
							catch (JSONException e) {
							    // TODO Auto-generated catch block
							    e.printStackTrace();
							}
			        	
							String ET_Name = OffenderActivity.id_off_name.getText().toString();
							String ET_Fname = OffenderActivity.off_fname.getText().toString();
							String ET_Age = OffenderActivity.off_age.getText().toString();
							String ET_Mobile = OffenderActivity.off_Pnum.getText().toString();
							String ET_address = OffenderActivity.off_address.getText().toString();
							String ET_Shop = OffenderActivity.shop_name.getText().toString();
							String ET_shopOwnerName = OffenderActivity.shop_Ownername.getText().toString();
							String ET_Tin = OffenderActivity.tin_no.getText().toString();
							String ET_date = OffenderActivity.id_date.getText().toString();
							String ET_Landmark = OffenderActivity.id_landmark.getText().toString();
							String ET_PassportNo = OffenderActivity.id_passport_no.getText().toString();
							String ET_International_driv = OffenderActivity.id_int_drive_lic.getText().toString();
							String ET_pscode = (UserLogin_Activity.Ps_code!=null?UserLogin_Activity.Ps_code:"");
							String ET_Lattitude = "";
							String ET_Longitude ="";
							
							
							TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
							WifiManager manager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
		    				// check if GPS enabled		
		    		        if(gps.canGetLocation()){
		    		        	double latitude = gps.getLatitude();
		    		        	double longitude = gps.getLongitude();
		    		        	ET_Lattitude=latitude+"";
		    		        	ET_Longitude=longitude+"";
		    		        }else{
		    		        	gps.showSettingsAlert();
		    		        }
		    		   
		    		        byte[] seizedImageInbyteArray =SiezedItemsActivity.seizedImageInbyteArray;

		    		        Log.i("WitnessFormActivity.ET_WitnessDetails  ::", WitnessFormActivity.ET_WitnessDetails );
							String ET_IMEI = telephonyManager.getDeviceId().toString();
							String ET_SIM = telephonyManager.getSimSerialNumber().toString();
							String ET_MAC = manager.getConnectionInfo().getMacAddress().toString();
							Log.i("ET_Lattitude ::", ET_Lattitude);
							Log.i("ET_Longitude ::", ET_Longitude);
							Log.i("ET_IMEI ::", ET_IMEI);
							Log.i("ET_SIM ::", ET_SIM);
							Log.i("ET_MAC ::", ET_MAC);
							Log.i("PS NAme ::", psName);
							Log.i("PS Code ::", psCode);
							Log.i("Pid Code ::", pidCode);
							Log.i("Pid Name ::", pidName);
							
							request.addProperty("shopName", ET_Shop);
							request.addProperty("shopTIN", ET_Tin);
							request.addProperty("shopOwnerName",ET_shopOwnerName);
							request.addProperty("shopAddress",ET_Shop!=null?ET_address:"");
							request.addProperty("gender",OffenderActivity.group1_text.getText()!=null?OffenderActivity.group1_text.getText().toString():"");
							request.addProperty("offenceDt", ET_date);
							request.addProperty("offdrName", ET_Name);
							request.addProperty("offdrFName", ET_Fname);
							request.addProperty("offdrDOB", ET_Age); 
							request.addProperty("offdrAddress", ET_address);  				
							request.addProperty("offdrAge", ET_Age);
							request.addProperty("offdrHNO", "");
							request.addProperty("offdrStreet", "");
							request.addProperty("offdrVillage", "");
							request.addProperty("offdrMandal", "");
							request.addProperty("offdrCity", "");
							request.addProperty("offdrDist", "");
							request.addProperty("offdrState", "");
							request.addProperty("offdrNation", OffenderActivity.ET_Nationality);
							request.addProperty("offgender", OffenderActivity.ET_gender);
							request.addProperty("offdrPIN", "");
							request.addProperty("offdrQualification", "");
							request.addProperty("offdrOccupation", "");
							request.addProperty("gpsDate", "");
							request.addProperty("gpsLatti", latitude);
							request.addProperty("gpsLongi", longitude);
							request.addProperty("offenceLocation", "");
							request.addProperty("offenceLandMark", ET_Landmark);
							request.addProperty("offdrImage", "");
							request.addProperty("offdrMobileNo",ET_Mobile);
							request.addProperty("offdrContactNo","");
							request.addProperty("stateCode", "");
							request.addProperty("unitCode", unitCd);
							request.addProperty("unitName", unitName);
							request.addProperty("psCode",psCode);
							request.addProperty("psName",psName);
							request.addProperty("offdrMail","");
							request.addProperty("deptCode", "2");
							request.addProperty("deptName", "L and O");
							request.addProperty("mc_imeiNo", ET_IMEI);
							request.addProperty("mc_simNo", ET_SIM);
							request.addProperty("mc_mobileNo", ET_Mobile);
							request.addProperty("mc_ipNo", "");
							request.addProperty("mc_macId", ET_MAC);
							request.addProperty("remarks", "");
							request.addProperty("description", Add_SectionsActivity.decription_text.getText().toString());
							request.addProperty("pid_code", pidCode);
							request.addProperty("pid_name", pidName);
							request.addProperty("acmdAmount", "");
							request.addProperty("userCharges", "");
							request.addProperty("cmdAmount", "");
							
							String aadhaar_idvalue=null;
							String driving_idvalue=null;
							String vehicle_idvalue=null;
							String passport_idvalue=null;
							String pan_idvalue=null;
							String voter_idvalue=null;
							
	//						Log.d("id1_Spinner Spinner Value ::", OffenderActivity.id1_Spinner);
							if("AADHAAR NO".equals(OffenderActivity.id1_Spinner)){
								aadhaar_idvalue=OffenderActivity.respondent_id_details.getText().toString();
								}
							else if("DRIVING LICENCE".equals(OffenderActivity.id1_Spinner)){
								driving_idvalue=OffenderActivity.respondent_id_details.getText().toString();
							}
							else if("VEHICLE NO".equals(OffenderActivity.id1_Spinner)){
								vehicle_idvalue=OffenderActivity.respondent_id_details.getText().toString();
							}
							else if("PASSPORT NO".equals(OffenderActivity.id1_Spinner)){
								passport_idvalue=OffenderActivity.respondent_id_details.getText().toString();
							}
							else if("PAN CARD NO".equals(OffenderActivity.id1_Spinner)){
								pan_idvalue=OffenderActivity.respondent_id_details.getText().toString();
							}
							else if("VOTER ID".equals(OffenderActivity.id1_Spinner)){
								voter_idvalue=OffenderActivity.respondent_id_details.getText().toString();
							}

							request.addProperty("offdrAadharNo", aadhaar_idvalue);
							request.addProperty("offdrLicenceNo", driving_idvalue);
							request.addProperty("offdrRegnCd", vehicle_idvalue);
							request.addProperty("offdrPAN", pan_idvalue);
							request.addProperty("offdrPassport", ET_PassportNo);
							request.addProperty("offdrVoterId", voter_idvalue);
							request.addProperty("idDetails", ET_RespondentDetails);//form json array of 4 respondant to string
							request.addProperty("detainedItems", OffenderActivity.seizedItems!=null?OffenderActivity.seizedItems.toString():"NA"); 
							request.addProperty("sectionDetails",secBuffer!=null?secBuffer.toString():"NA");
							request.addProperty("fpDetails", "");
							request.addProperty("witnessDetails",WitnessFormActivity.ET_WitnessDetails!=null?WitnessFormActivity.ET_WitnessDetails:"NA" );//form json array of 4 witness to string
							request.addProperty("image1",""+getBase64EncodedString(OffenderActivity.respImage1ByteArray));
							request.addProperty("image2",""+getBase64EncodedString(OffenderActivity.respImage2ByteArray));
							request.addProperty("image3",""+getBase64EncodedString(OffenderActivity.seizedImageInbyteArray));
						
				
							SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
							envelope.dotNet = true;
							envelope.setOutputSoapObject(request);
							Log.i("Preview Status ::",OffenderActivity.previewrespondentBuf.toString());
						//	Log.i("request", "" + request);
							//Log.i("WebService.SOAP_ADDRESS ::::::::::::::", "" + WebService.SOAP_ADDRESS);
							HttpTransportSE androidHttpTransport = new HttpTransportSE(ip+"/HydPettyCaseService/services/PettyCaseServiceImpl?wsdl");
							Log.i("androidHttpTransport", "" + androidHttpTransport);
							//SOAP_ACTION = NAMESPACE + "generatePettyCase";
							androidHttpTransport.call(SOAP_ACTION_up, envelope);
							Object result = envelope.getResponse();
							Opdata_Chalana=result.toString();
							challanGenresp="";
							challanGenresp = Opdata_Chalana;
							
							Log.i("** generatePettyCase response***", "" + challanGenresp);
							//Toast.makeText(getApplicationContext(),"Response"+ challanGenresp, Toast.LENGTH_LONG).show();
							if(challanGenresp.equals("NA")){
								Toast toast = Toast.makeText(getApplicationContext(), "No data Found", Toast.LENGTH_LONG);
								toast.setGravity(Gravity.CENTER, 0, -200);
							    toast.show();
							}
						
							runOnUiThread(new Runnable() {
							    public void run(){  
							if(challanGenresp==null){
								Log.i("Response challanGenresp", challanGenresp);
	
								AlertDialog alertDialog = new AlertDialog.Builder(PreviewActivity.this).create();
								alertDialog.setTitle("Alert");
								alertDialog.setMessage("NO RESPONSE FROM SERVER");
								alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
								    new DialogInterface.OnClickListener() {
								        public void onClick(DialogInterface dialog, int which) {
								            dialog.dismiss();
								        }
								    });
								alertDialog.show();
								}/*else{
								AlertDialog alertDialog = new AlertDialog.Builder(OffenderActivity.this).create();
								alertDialog.setTitle("Alert");
								alertDialog.setMessage("NO RESPONSE FROM SERVER");
								alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
								    new DialogInterface.OnClickListener() {
								        public void onClick(DialogInterface dialog, int which) {
								            dialog.dismiss();
								        }
								    });
								alertDialog.show();
								}*/
							    }
							});	
							//					// INSERT ID PROOF DETAILS  END
							} catch (Exception e) {
								e.printStackTrace();
							}
							} catch (Exception e) {
								e.printStackTrace();
							}
								return null;
						}

			private String getBase64ApacheCommonsEncodedString(byte[] bs) {
				// TODO Auto-generated method stub
				 String imgString ="NA";
				 try {
					 if(bs!=null){
						 imgString=org.apache.commons.codec.binary.Base64.encodeBase64(bs).toString();
						 Log.i("FPS byte[] :::", bs+"");
						 Log.i("ENCODED FPS String :::", imgString);
						 //imgString = Base64.encodeToString(bs, Base64.NO_WRAP);
					 }
				
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			return imgString.trim();
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
				Intent off = new Intent(getApplicationContext(), Challan_GenerationActivity.class);
			//	finish();
				witnesFlg=false;
				startActivity(off);
				}
			}
				
			public String getIDCode(String eT_ID) {
				String idCd="NA";
				 try {
					for(String idCode: OffenderActivity.idMap.keySet()){
					if(eT_ID.equals(OffenderActivity.idMap.get(idCode))){
						idCd=idCode;
					}
				  }
				} catch (Exception e) {
					// TODO: handle exception
				}
				 Log.i("idCd for "+eT_ID+" :", idCd);
				return idCd;
			}
			public String getIDCode2(String eT_ID) {
			String idCd="NA";
				 try {
					Log.i("SPinner Value", eT_ID);
					for(String idCode: OffenderActivity.idMap.keySet()){
						if(eT_ID.equals(OffenderActivity.idMap.get(idCode))){
							idCd=idCode;
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
					 Log.i("idCd for SPinner Value  :", idCd);
					return idCd;
				}

			public String getIDCode3(String eT_ID) {
				String idCd="NA";
				 try {
					for(String idCode: OffenderActivity.idMap.keySet()){
						if(eT_ID.equals(OffenderActivity.idMap.get(idCode))){
							idCd=idCode;
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				 Log.i("idCd for "+eT_ID+" :", idCd);
				return idCd;
			}
			public String getIDCode4(String eT_ID) {
				String idCd="NA";
				 try {
					for(String idCode: OffenderActivity.idMap.keySet()){
						if(eT_ID.equals(OffenderActivity.idMap.get(idCode))){
							idCd=idCode;
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				 Log.i("idCd for "+eT_ID+" :", idCd);
				return idCd;
			}

		 
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
			}