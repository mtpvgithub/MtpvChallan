package com.mtpv.LawnOrder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.button.R;
import com.mtpv.LawnOrder.OffenderActivity.AsyncGetDLDetails;
import com.mtpv.LawnOrder.OffenderActivity.AsyncGetIDDetails;
import com.mtpv.LawnOrder.OffenderActivity.AsyncGetRCDetails;

public class WitnessFormActivity extends Activity {

	public static final String witnessDetails = null;
	public static String ET_WitnessDetails ="NA";
	List<String> list;
	
	public static TextView wt_gender, wt_gender2, wt_gender3, wt_gender4;
	public static String wt_id1_Spinner = null;
	public static String wt_id2_Spinner = null;
	public static String wt_id3_Spinner = null;
	public static String wt_id4_Spinner = null, ET_Gender=null, ET_Gender2=null, ET_Gender3=null, ET_Gender4=null;
	public static StringBuffer previewWitnessPromtBuf=new StringBuffer();	

	public static EditText witness_name, witness_fname, witness_age, witness_dob, witness_phone,
					witness_address, witness_city, witness_state, id_text_wtns,
					witness_name2, witness_fname2, witness_age2, witness_phone2, witness_address2, witness_city2, witness_id2,id_text_wtns2,
					witness_name3, witness_fname3, witness_age3, witness_phone3, witness_address3, witness_city3, witness_id3,id_text_wtns3,
					witness_name4, witness_fname4, witness_age4, witness_phone4, witness_address4, witness_city4, witness_id4,id_text_wtns4 ;
	Button wt_submit_btn, add_witns, remve_witns2,remve_witns3, remve_witns4;
	ImageView wt_details,wt_details2,wt_details3,wt_details4,back_btn, submit_btn;
	LinearLayout wt_layout2, wt_layout3, wt_layout4 ;
	RadioGroup wt_gender_group, wt_gender_group2, wt_gender_group3, wt_gender_group4;
	RadioButton wt_male, wt_female, wt_others,
				wt_male2, wt_female2, wt_others2,
				wt_male3, wt_female3, wt_others3,
				wt_male4, wt_female4, wt_others4;
	
	@SuppressWarnings("unused")
	private Calendar cal;
	@SuppressWarnings("unused")
	private int day;
	@SuppressWarnings("unused")
	private int month;
	@SuppressWarnings("unused")
	private int year;
	int clickcount = 0;
	Spinner witness_id_options, witness_id_options2, witness_id_options3, witness_id_options4;
	public static String selectedID="";
	public static String selectedID2="";
	public static String selectedID3="";
	public static String selectedID4="";
	static Map<String,String> idMap=null;
	TextView witness;
	
	ImageView details, details2, details3, details4;
	EditText wt_id_details, wt_id_details2, wt_id_details3, wt_id_details4 ;
	
	public static String id1_Spinner = null;
	public static String id2_Spinner = null;
	public static String id3_Spinner = null;
	public static String id4_Spinner = null;
	
	public static Map<String,String>get_detailsmap1=new HashMap<String,String >();
	public static Map<String,String>get_detailsmap2=new HashMap<String,String >();
	public static Map<String,String>get_detailsmap3=new HashMap<String,String >();
	public static Map<String,String>get_detailsmap4=new HashMap<String,String >();
	
	public static boolean get_detailsFlg1=false,get_detailsFlg2=false,get_detailsFlg3=false,get_detailsFlg4=false;
	
	public static String NAMESPACE = "http://service.mother.com";
	public static String SOAP_ACTION_ID = NAMESPACE + "getDetailsByAADHAR", challanResponse;
	
	public static String challanGenresp ="";
	final int PROGRESS_DIALOG = 1;
	
	@SuppressWarnings("unused")
	@SuppressLint("ShowToast")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_witness_form);
		
		cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);
		
		WitnessFormActivity.previewWitnessPromtBuf=new StringBuffer();
		
		details = (ImageView)findViewById(R.id.details);
		details2 = (ImageView)findViewById(R.id.details2);
		details3 = (ImageView)findViewById(R.id.details3);
		details4 = (ImageView)findViewById(R.id.details4);
		
		wt_id_details = (EditText)findViewById(R.id.wt_id_details);
		wt_id_details2 = (EditText)findViewById(R.id.wt_id_details2);
		wt_id_details3 = (EditText)findViewById(R.id.wt_id_details3);
		wt_id_details4 = (EditText)findViewById(R.id.wt_id_details4);
		
		witness = (TextView)findViewById(R.id.witness);
		
		witness_name = (EditText)findViewById(R.id.witness_name);
		witness_fname = (EditText)findViewById(R.id.witness_fname);
		witness_age = (EditText)findViewById(R.id.witness_age);
		witness_phone = (EditText)findViewById(R.id.witness_phone);
		witness_address = (EditText)findViewById(R.id.witness_address);
		witness_city = (EditText)findViewById(R.id.witness_city);
		//wt_id_details = (EditText)findViewById(R.id.wt_id_details);
		witness_id_options = (Spinner)findViewById(R.id.witness_id_options);
		
		witness_name2 = (EditText)findViewById(R.id.witness_name2);
		witness_fname2 = (EditText)findViewById(R.id.witness_fname2);
		witness_age2 = (EditText)findViewById(R.id.witness_age2);
		witness_phone2 = (EditText)findViewById(R.id.witness_phone2);
		witness_address2 = (EditText)findViewById(R.id.witness_address2);
		witness_city2 = (EditText)findViewById(R.id.witness_city2);
		//wt_id_details2 = (EditText)findViewById(R.id.wt_id_details2);	
		witness_id_options2 = (Spinner)findViewById(R.id.witness_id_options2);
		
		witness_name3 = (EditText)findViewById(R.id.witness_name3);
		witness_fname3 = (EditText)findViewById(R.id.witness_fname3);
		witness_age3 = (EditText)findViewById(R.id.witness_age3);
		witness_phone3 = (EditText)findViewById(R.id.witness_phone3);
		witness_address3 = (EditText)findViewById(R.id.witness_address3);
		witness_city3 = (EditText)findViewById(R.id.witness_city3);
		//wt_id_details3 = (EditText)findViewById(R.id.wt_id_details3);	
		witness_id_options3 = (Spinner)findViewById(R.id.witness_id_options3);
		
		witness_name4 = (EditText)findViewById(R.id.witness_name4);
		witness_fname4 = (EditText)findViewById(R.id.witness_fname4);
		witness_age4 = (EditText)findViewById(R.id.witness_age4);
		witness_phone4 = (EditText)findViewById(R.id.witness_phone4);
		witness_address4 = (EditText)findViewById(R.id.witness_address4);
		witness_city4 = (EditText)findViewById(R.id.witness_city4);
		//wt_id_details4 = (EditText)findViewById(R.id.wt_id_details4);
		witness_id_options4 = (Spinner)findViewById(R.id.witness_id_options4);
		
		wt_layout2 = (LinearLayout)findViewById(R.id.wt_layout2);
		wt_layout3 = (LinearLayout)findViewById(R.id.wt_layout3);
		wt_layout4 = (LinearLayout)findViewById(R.id.wt_layout4);
		
		wt_gender_group = (RadioGroup)findViewById(R.id.wt_gender_group);
		wt_male = (RadioButton)findViewById(R.id.wt_male);
		wt_female = (RadioButton)findViewById(R.id.wt_female);
		wt_others = (RadioButton)findViewById(R.id.wt_other);
		
        wt_gender = (TextView)findViewById(R.id.wt_gender);
		wt_gender_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
			       case R.id.wt_male:
			        	wt_gender.setText("M");
			    	//   Toast.makeText(getApplicationContext(), "Your gender is Male", Toast.LENGTH_SHORT).show();
			          break;
			     
			       case R.id.wt_female:
			    	   wt_gender.setText("F");
			       // 	Toast.makeText(getApplicationContext(), "Your gender is Female", Toast.LENGTH_SHORT).show();
			      break;
			   
			       case R.id.wt_other:
			    	   wt_gender.setText("O");
			       // 	Toast.makeText(getApplicationContext(), "Your gender is other", Toast.LENGTH_SHORT).show();
			      break;
			        }
			}
		});
		
		wt_gender_group2 = (RadioGroup)findViewById(R.id.wt_gender_group2);
		wt_male2 = (RadioButton)findViewById(R.id.wt_male2);
		wt_female2 = (RadioButton)findViewById(R.id.wt_female2);
		wt_others2 = (RadioButton)findViewById(R.id.wt_other2);
		wt_gender2 = (TextView)findViewById(R.id.wt_gender2);
		wt_gender_group2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
			       case R.id.wt_male2:
			        	wt_gender2.setText("M");
			    //	   Toast.makeText(getApplicationContext(), "Your gender is Male", Toast.LENGTH_SHORT).show();
			          break;
			       case R.id.wt_female2:
			    	   wt_gender2.setText("F");
			       // 	Toast.makeText(getApplicationContext(), "Your gender is Female", Toast.LENGTH_SHORT).show();
			      break;
			       case R.id.wt_other2:
			    	   wt_gender2.setText("O");
			      //  	Toast.makeText(getApplicationContext(), "Your gender is other", Toast.LENGTH_SHORT).show();
			      break;
			        }
			}
		});
		
		wt_gender_group3 = (RadioGroup)findViewById(R.id.wt_gender_group3);
		wt_male3 = (RadioButton)findViewById(R.id.wt_male3);
		wt_female3 = (RadioButton)findViewById(R.id.wt_female3);
		wt_others3 = (RadioButton)findViewById(R.id.wt_other3);
		wt_gender3 = (TextView)findViewById(R.id.wt_gender3);
		wt_gender_group3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
			       case R.id.wt_male3:
			        	wt_gender3.setText("M");
			    	 //  Toast.makeText(getApplicationContext(), "Your gender is Male", Toast.LENGTH_SHORT).show();
			          break;
			     
			       case R.id.wt_female3:
			    	   wt_gender3.setText("F");
			        //	Toast.makeText(getApplicationContext(), "Your gender is Female", Toast.LENGTH_SHORT).show();
			      break;
			   
			       case R.id.wt_other3:
			    	   wt_gender3.setText("O");
			        //	Toast.makeText(getApplicationContext(), "Your gender is other", Toast.LENGTH_SHORT).show();
			      break;
			        }
			}
		});
		
		wt_gender_group4 = (RadioGroup)findViewById(R.id.wt_gender_group4);
		wt_male4 = (RadioButton)findViewById(R.id.wt_male4);
		wt_female4 = (RadioButton)findViewById(R.id.wt_female4);
		wt_others4 = (RadioButton)findViewById(R.id.wt_other4);
		wt_gender4 = (TextView)findViewById(R.id.wt_gender4);
		wt_gender_group4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
			       case R.id.wt_male4:
			        	wt_gender4.setText("M");
			    	//   Toast.makeText(getApplicationContext(), "Your gender is Male", Toast.LENGTH_SHORT).show();
			          break;
			     
			       case R.id.wt_female4:
			    	   wt_gender4.setText("F");
			       // 	Toast.makeText(getApplicationContext(), "Your gender is Female", Toast.LENGTH_SHORT).show();
			      break;
			   
			       case R.id.wt_other4:
			    	   wt_gender4.setText("O");
			        //	Toast.makeText(getApplicationContext(), "Your gender is other", Toast.LENGTH_SHORT).show();
			      break;
			        }
			}
		});
		
		witness_id_options = (Spinner)findViewById(R.id.witness_id_options);
		idMap=new DataBase(getApplicationContext()).getIdMap(getApplicationContext());
		list = new ArrayList<String>();
		/*list.add("-Select ID");*/
		for(String idCode: idMap.keySet()){
			list.add(idMap.get(idCode));
		}
		ArrayAdapter<String> adp = new ArrayAdapter<String>
		(this, R.layout.spinner_item, list);
		adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		
		witness_id_options.setAdapter(adp);
		witness_id_options.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				switch(arg2) {
				/*case 0 :
					wt_id_details.setHint("Enter ID Details");
					wt_id_details.setText("");
					break;*/
				case 0 :
					wt_id_details.setHint(getLabel("1"));
					wt_id_details.setFilters(new InputFilter[] { new InputFilter.LengthFilter(12) });
					wt_id_details.setInputType(InputType.TYPE_CLASS_NUMBER);
					wt_id_details.setText("");
					
					
					break;
				case 1 :
					wt_id_details.setHint(getLabel("2"));
					wt_id_details.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details.setText("");
					break;
				case 2 :
					wt_id_details.setHint(getLabel("3"));
					wt_id_details.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details.setText("");
					break;
				case 3 :
					wt_id_details.setHint(getLabel("4"));
					wt_id_details.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details.setText("");
					break;
				case 4 :
					wt_id_details.setHint(getLabel("5"));
					wt_id_details.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details.setText("");
					break;
				case 5 :
					wt_id_details.setHint(getLabel("6"));
					wt_id_details.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details.setText("");
					break;
				case 6 :
					wt_id_details.setHint(getLabel("7"));
					wt_id_details.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details.setText("");
					break;
				case 7 :
					wt_id_details.setHint(getLabel("8"));
					wt_id_details.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });
					wt_id_details.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details.setInputType(InputType.TYPE_CLASS_NUMBER);
					wt_id_details.setText("");
					break;
				case 8 :
					wt_id_details.setHint(getLabel("9"));
					wt_id_details.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details.setText("");
					break;
				case 9 :
					wt_id_details.setHint(getLabel("10"));
					wt_id_details.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details.setText("");
					break;
				default :
					wt_id_details.setHint("Nothing Selected");
					break;
				}
			}
			public CharSequence getLabel(String selected) {
				String label="";
				try {
					for(String idCode: WitnessFormActivity.idMap.keySet()){
						if(idCode.equals(selected)){
							label="ENTER "+WitnessFormActivity.idMap.get(idCode);
							WitnessFormActivity.selectedID=WitnessFormActivity.idMap.get(idCode).toString();
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
		
		witness_id_options2 = (Spinner)findViewById(R.id.witness_id_options2);
		idMap=new DataBase(getApplicationContext()).getIdMap(getApplicationContext());
		list = new ArrayList<String>();
		/*list.add("-Select ID");*/
		for(String idCode: idMap.keySet()){
			list.add(idMap.get(idCode));
		}
		ArrayAdapter<String> adp2 = new ArrayAdapter<String>
		(this, R.layout.spinner_item, list);
		adp2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		witness_id_options2.setAdapter(adp2);
		witness_id_options2.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				switch(arg2) {
				/*case 0 :
					wt_id_details2.setHint("Enter ID Details");
					wt_id_details2.setText("");
					break;*/
				case 0 :
					wt_id_details2.setHint(getLabel2("1"));
					wt_id_details2.setFilters(new InputFilter[] { new InputFilter.LengthFilter(12) });
					wt_id_details2.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details2.setInputType(InputType.TYPE_CLASS_NUMBER);
					wt_id_details2.setText("");
					break;
				case 1 :
					wt_id_details2.setHint(getLabel2("2"));
					wt_id_details2.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details2.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details2.setText("");
					break;
				case 2 :
					wt_id_details2.setHint(getLabel2("3"));
					wt_id_details2.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details2.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details2.setText("");
					break;
				case 3 :
					wt_id_details2.setHint(getLabel2("4"));
					wt_id_details2.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details2.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details2.setText("");
					break;
				case 4 :
					wt_id_details2.setHint(getLabel2("5"));
					wt_id_details2.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details2.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details2.setText("");
					break;
				case 5 :
					wt_id_details2.setHint(getLabel2("6"));
					wt_id_details2.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details2.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details2.setText("");
					break;
				case 6 :
					wt_id_details2.setHint(getLabel2("7"));
					wt_id_details2.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details2.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details2.setText("");
					break;
				case 7 :
					wt_id_details2.setHint(getLabel2("8"));
					wt_id_details2.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });
					wt_id_details2.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details2.setInputType(InputType.TYPE_CLASS_NUMBER);
					wt_id_details2.setText("");
					break;
				case 8 :
					wt_id_details2.setHint(getLabel2("9"));
					wt_id_details2.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details2.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details2.setText("");
					break;
				case 9 :
					wt_id_details2.setHint(getLabel2("10"));
					wt_id_details2.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details2.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details2.setText("");
					break;
				default :
					wt_id_details2.setHint("Nothing Selected");
					break;
				}
			}
			public CharSequence getLabel2(String selected) {
				String label="";
				try {
					for(String idCode: WitnessFormActivity.idMap.keySet()){
						if(idCode.equals(selected)){
							label="ENTER "+WitnessFormActivity.idMap.get(idCode);
							WitnessFormActivity.selectedID2=WitnessFormActivity.idMap.get(idCode).toString();
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
		
		witness_id_options3 = (Spinner)findViewById(R.id.witness_id_options3);
		idMap=new DataBase(getApplicationContext()).getIdMap(getApplicationContext());
		list = new ArrayList<String>();
		/*list.add("-Select ID");*/
		for(String idCode: idMap.keySet()){
			list.add(idMap.get(idCode));
		}
		
		ArrayAdapter<String> adp3 = new ArrayAdapter<String>
		(this, R.layout.spinner_item, list);
		adp3.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		
		witness_id_options3.setAdapter(adp2);
		witness_id_options3.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				switch(arg2) {
				/*case 0 :
					wt_id_details3.setHint("Enter ID Details");
					wt_id_details3.setText("");
					break;*/
				case 0 :
					wt_id_details3.setHint(getLabel3("1"));
					wt_id_details3.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });
					wt_id_details3.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details3.setInputType(InputType.TYPE_CLASS_NUMBER);
					wt_id_details3.setText("");
					
					
					break;
				case 1 :
					wt_id_details3.setHint(getLabel3("2"));
					wt_id_details3.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details3.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details3.setText("");
					break;
				case 2 :
					wt_id_details3.setHint(getLabel3("3"));
					wt_id_details3.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details3.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details3.setText("");
					break;
				case 3 :
					wt_id_details3.setHint(getLabel3("4"));
					wt_id_details3.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details3.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details3.setText("");
					break;
				case 4 :
					wt_id_details3.setHint(getLabel3("5"));
					wt_id_details3.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details3.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details3.setText("");
					break;
				case 5 :
					wt_id_details3.setHint(getLabel3("6"));
					wt_id_details3.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details3.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details3.setText("");
					break;
				case 6 :
					wt_id_details3.setHint(getLabel3("7"));
					wt_id_details3.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details3.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details3.setText("");
					break;
				case 7 :
					wt_id_details3.setHint(getLabel3("8"));
					wt_id_details3.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });
					wt_id_details3.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details3.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details3.setText("");
					break;
				case 8 :
					wt_id_details3.setHint(getLabel3("9"));
					wt_id_details3.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details3.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details3.setText("");
					break;
				case 9 :
					wt_id_details3.setHint(getLabel3("10"));
					wt_id_details3.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details3.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details3.setText("");
					break;
				default :
					wt_id_details3.setHint("Nothing Selected");
					break;
				}
			}
			public CharSequence getLabel3(String selected) {
				String label="";
				try {
					for(String idCode: WitnessFormActivity.idMap.keySet()){
						if(idCode.equals(selected)){
							label="ENTER "+WitnessFormActivity.idMap.get(idCode);
							WitnessFormActivity.selectedID3=WitnessFormActivity.idMap.get(idCode).toString();
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
		witness_id_options4 = (Spinner)findViewById(R.id.witness_id_options4);
		idMap=new DataBase(getApplicationContext()).getIdMap(getApplicationContext());
		list = new ArrayList<String>();
		/*list.add("-Select ID");*/
		for(String idCode: idMap.keySet()){
			list.add(idMap.get(idCode));
		}
		
		ArrayAdapter<String> adp4 = new ArrayAdapter<String>
		(this,R.layout.spinner_item, list);
		adp3.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		
		witness_id_options4.setAdapter(adp4);
		witness_id_options4.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				switch(arg2) {
				/*case 0 :
					wt_id_details4.setHint("Enter ID Details");
					wt_id_details4.setText("");
					break;*/
				case 0 :
					wt_id_details4.setHint(getLabel4("1"));
					wt_id_details4.setFilters(new InputFilter[] { new InputFilter.LengthFilter(12) });
					wt_id_details3.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details3.setInputType(InputType.TYPE_CLASS_NUMBER);
					wt_id_details4.setText("");
					
					
					break;
				case 1 :
					wt_id_details4.setHint(getLabel4("2"));
					wt_id_details3.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details3.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details4.setText("");
					break;
				case 2 :
					wt_id_details4.setHint(getLabel4("3"));
					wt_id_details3.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details3.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details4.setText("");
					break;
				case 3 :
					wt_id_details4.setHint(getLabel4("4"));
					wt_id_details3.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details3.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details4.setText("");
					break;
				case 4 :
					wt_id_details4.setHint(getLabel4("5"));
					wt_id_details3.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details3.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details4.setText("");
					break;
				case 5 :
					wt_id_details4.setHint(getLabel4("6"));
					wt_id_details3.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details3.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details4.setText("");
					break;
				case 6 :
					wt_id_details4.setHint(getLabel4("7"));
					wt_id_details3.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details3.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details4.setText("");
					break;
				case 7 :
					wt_id_details4.setHint(getLabel4("8"));
					wt_id_details4.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });
					wt_id_details3.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details3.setInputType(InputType.TYPE_CLASS_NUMBER);
					wt_id_details4.setText("");
					break;
				case 8 :
					wt_id_details4.setHint(getLabel4("9"));
					wt_id_details3.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details3.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details4.setText("");
					break;
				case 9 :
					wt_id_details4.setHint(getLabel4("10"));
					wt_id_details3.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
					wt_id_details3.setInputType(InputType.TYPE_CLASS_TEXT);
					wt_id_details4.setText("");
					break;
				default :
					wt_id_details4.setHint("Nothing Selected");
					break;
				}
			}
			public CharSequence getLabel4(String selected) {
				String label="";
				try {
					for(String idCode: WitnessFormActivity.idMap.keySet()){
						if(idCode.equals(selected)){
							label="ENTER "+WitnessFormActivity.idMap.get(idCode);
							WitnessFormActivity.selectedID4=WitnessFormActivity.idMap.get(idCode).toString();
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
		add_witns = (Button)findViewById(R.id.add_witns);
		add_witns.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 clickcount=clickcount+1;
				 if(clickcount>3){
			     	Toast toast = Toast.makeText(getApplicationContext(), "You Can Add Only Four Witness Details", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, -200);
					View toastView = toast.getView();
			    	toastView.setBackgroundResource(R.drawable.toast_background);
				    toast.show();
				 	}
				 else{
					 if(clickcount==1){
						 wt_layout2.setVisibility(View.VISIBLE);
					 }
					 if(clickcount==2){
						 wt_layout3.setVisibility(View.VISIBLE);
					 }
					 if(clickcount==3){
						 wt_layout4.setVisibility(View.VISIBLE);
						 add_witns.setVisibility(View.GONE);
					 }
				 }
			}
		});
		
		remve_witns2 = (Button)findViewById(R.id.remve_witns2);
		remve_witns2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickcount=clickcount-1;
				wt_layout2.setVisibility(View.GONE);
			}
		});
		
		remve_witns3 = (Button)findViewById(R.id.remve_witns3);
		remve_witns3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickcount=clickcount-1;
				wt_layout3.setVisibility(View.GONE);
			}
		});
		
		remve_witns4 = (Button)findViewById(R.id.remve_witns4);
		remve_witns4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickcount=clickcount-1;
				wt_layout4.setVisibility(View.GONE);
			}
		});
		
		
		details.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				get_detailsFlg2=false;
				get_detailsFlg3=false;
				get_detailsFlg4=false;
				get_detailsFlg1=true;
				
				if(WitnessFormActivity.selectedID.toString().trim().equals("AADHAAR NO")){
					Log.i("id service called", "AADHAAR NO");
					//AADHAR 
					new AsyncGetIDDetails().execute();
				}else if(WitnessFormActivity.selectedID.toString().trim().equals("DRIVING LICENCE")){
					Log.i("id service called", "DRIVING LICENCE");
					// DRIVING LICENSE 
					new AsyncGetDLDetails().execute();
				}else if(WitnessFormActivity.selectedID.toString().trim().equals("VEHICLE NO")){
					Log.i("id service called", "VEHCILE NO");
					// VEHICLLE RC
					new AsyncGetRCDetails().execute();
				}
				
			}
		});
		
		details2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				get_detailsFlg2=true;
				get_detailsFlg1=false;
				get_detailsFlg3=false;
				get_detailsFlg4=false;
				
				if(WitnessFormActivity.selectedID.toString().trim().equals("AADHAAR NO")){
					Log.i("id service called", "AADHAAR NO");
					//AADHAR 
					new AsyncGetIDDetails().execute();
				}else if(WitnessFormActivity.selectedID.toString().trim().equals("DRIVING LICENCE")){
					Log.i("id service called", "DRIVING LICENCE");
					// DRIVING LICENSE 
					new AsyncGetDLDetails().execute();
				}else if(WitnessFormActivity.selectedID.toString().trim().equals("VEHICLE NO")){
					Log.i("id service called", "VEHCILE NO");
					// VEHICLLE RC
					new AsyncGetRCDetails().execute();
				}
				
			}
		});
		
		
		details3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				get_detailsFlg3=true;
				get_detailsFlg1=false;
				get_detailsFlg2=false;
				get_detailsFlg4=false;
				
				if(WitnessFormActivity.selectedID.toString().trim().equals("AADHAAR NO")){
					Log.i("id service called", "AADHAAR NO");
					//AADHAR 
					new AsyncGetIDDetails().execute();
				}else if(WitnessFormActivity.selectedID.toString().trim().equals("DRIVING LICENCE")){
					Log.i("id service called", "DRIVING LICENCE");
					// DRIVING LICENSE 
					new AsyncGetDLDetails().execute();
				}else if(WitnessFormActivity.selectedID.toString().trim().equals("VEHICLE NO")){
					Log.i("id service called", "VEHCILE NO");
					// VEHICLLE RC
					new AsyncGetRCDetails().execute();
				}
			}
		});
		
		details4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				get_detailsFlg4=true;
				get_detailsFlg1=false;
				get_detailsFlg3=false;
				get_detailsFlg2=false;
				
				if(WitnessFormActivity.selectedID.toString().trim().equals("AADHAAR NO")){
					Log.i("id service called", "AADHAAR NO");
					//AADHAR 
					new AsyncGetIDDetails().execute();
				}else if(WitnessFormActivity.selectedID.toString().trim().equals("DRIVING LICENCE")){
					Log.i("id service called", "DRIVING LICENCE");
					// DRIVING LICENSE 
					new AsyncGetDLDetails().execute();
				}else if(WitnessFormActivity.selectedID.toString().trim().equals("VEHICLE NO")){
					Log.i("id service called", "VEHCILE NO");
					// VEHICLLE RC
					new AsyncGetRCDetails().execute();
				}
			}
		});
		witness_phone = (EditText)findViewById(R.id.witness_phone);
		witness_address = (EditText)findViewById(R.id.witness_address);
		witness_city = (EditText)findViewById(R.id.witness_city);
		
//		witness_state = (EditText)findViewById(R.id.witness_state);
		back_btn = (ImageView)findViewById(R.id.back_btn);
		if(!PreviewActivity.witnesFlg){
			back_btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					WitnessFormActivity.previewWitnessPromtBuf=null;
					finish();
				}
			});
		}else {
			back_btn.setVisibility(View.GONE);
		}
		submit_btn = (ImageView)findViewById(R.id.submit_btn);
		submit_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Boolean valdflg=true; 
				
				Boolean wtFLG2 =false ;
				Boolean wtFLG3 =false ;
				Boolean wtFLG4 =false ;
				
				if (wt_layout2.getVisibility() == View.VISIBLE) {
					wtFLG2 = true ;
				}else if (wt_layout3.getVisibility() == View.VISIBLE) {
					wtFLG3 = true ;
				}else if (wt_layout4.getVisibility() == View.VISIBLE) {
					wtFLG4 = true ;
				}
				
				if(witness.getVisibility() == View.VISIBLE){
					if(witness_name.getText().toString().length()==0)
						{
						/*Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, -200);
						View toastView = toast.getView();
				    	toastView.setBackgroundResource(R.drawable.toast_background);
					    toast.show();*/
					    witness_name.setError(Html.fromHtml("<font color='black'>Please Enter Name</font>"));
						witness_name.requestFocus();
						valdflg=false; 
						}
					else if(witness_fname.getText().toString().length()==0)
						{
						/*Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, -200);
						View toastView = toast.getView();
				    	toastView.setBackgroundResource(R.drawable.toast_background);
					    toast.show();*/
					    witness_fname.setError(Html.fromHtml("<font color='black'>Please Enter Father Name</font>"));
						witness_fname.requestFocus();
						valdflg=false; 
						}	
					else if(witness_age.getText().toString().length()==0)
						{
						/*Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, -200);
						View toastView = toast.getView();
				    	toastView.setBackgroundResource(R.drawable.toast_background);
					    toast.show();*/
					    witness_age.setError(Html.fromHtml("<font color='black'>Please Enter Age</font>"));
						witness_age.requestFocus();
						valdflg=false; 
						}		
						
					else if(witness_phone.getText().toString().length()==0)
						{
						/*Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Phone Number", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, -200);
						View toastView = toast.getView();
				    	toastView.setBackgroundResource(R.drawable.toast_background);
					    toast.show();*/
					    witness_phone.setError(Html.fromHtml("<font color='black'>Please Enter Mobile No</font>"));
						witness_phone.requestFocus();
						valdflg=false;
						}
					else if(!validateMobileNo(witness_phone.getText().toString())){
						new AlertDialog.Builder(WitnessFormActivity.this)
						 .setTitle("Mobile Number Status")
						 .setMessage("Please Enter Valid Mobile Number")
						 .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						     public void onClick(DialogInterface dialog, int which) { 
						         // Your code
						         
						         witness_phone.requestFocus();
						     }
						  })
						 .show();
						valdflg=false;
					}	
					else if(witness_address.getText().toString().length()==0)
						{
						/*Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Phone Number", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, -200);
						View toastView = toast.getView();
				    	toastView.setBackgroundResource(R.drawable.toast_background);
					    toast.show();*/
					    witness_address.setError(Html.fromHtml("<font color='black'>Please Enter Address</font>"));
						witness_address.requestFocus();
						valdflg=false;
						}
					else if(witness_city.getText().toString().length()==0)
						{
						/*Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Phone Number", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, -200);
						View toastView = toast.getView();
				    	toastView.setBackgroundResource(R.drawable.toast_background);
					    toast.show();*/
					    witness_city.setError(Html.fromHtml("<font color='black'>Please Enter City/District</font>"));
						witness_city.requestFocus();
						valdflg=false;
						}
					else if(wt_gender_group.getCheckedRadioButtonId() == -1)
						{
							/*Toast toast = Toast.makeText(getApplicationContext(), "Please Select Gender", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.CENTER, 0, -200);
							View toastView = toast.getView();
					    	toastView.setBackgroundResource(R.drawable.toast_background);
						    toast.show();*/
						    showToast("Please Select Gender");
							valdflg=false;
						} 
					
				}
				
			else if (wt_layout2.getVisibility()==View.VISIBLE && wtFLG2) 
				{
					if(witness_name2.getText().toString().length()==0)
						{
						/*Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, -200);
						View toastView = toast.getView();
				    	toastView.setBackgroundResource(R.drawable.toast_background);
					    toast.show();*/
					    witness_name2.setError(Html.fromHtml("<font color='black'>Please Enter Name</font>"));
						witness_name2.requestFocus();
						valdflg=false; 
						}
					else if(witness_fname2.getText().toString().length()==0)
						{
						/*Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, -200);
						View toastView = toast.getView();
				    	toastView.setBackgroundResource(R.drawable.toast_background);
					    toast.show();*/
					    witness_fname2.setError(Html.fromHtml("<font color='black'>Please Enter Father Name</font>"));
						witness_fname2.requestFocus();
						valdflg=false; 
						}	
					else if(witness_age2.getText().toString().length()==0)
						{
						/*Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, -200);
						View toastView = toast.getView();
				    	toastView.setBackgroundResource(R.drawable.toast_background);
					    toast.show();*/
					    witness_age2.setError(Html.fromHtml("<font color='black'>Please Enter Age</font>"));
						witness_age2.requestFocus();
						valdflg=false; 
						}		
						
					else if(witness_phone2.getText().toString().length()==0)
						{
						/*Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Phone Number", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, -200);
						View toastView = toast.getView();
				    	toastView.setBackgroundResource(R.drawable.toast_background);
					    toast.show();*/
					    witness_phone2.setError(Html.fromHtml("<font color='black'>Please Enter Mobile No</font>"));
						witness_phone2.requestFocus();
						valdflg=false;
						}
					else if(!validateMobileNo(witness_phone2.getText().toString())){
						new AlertDialog.Builder(WitnessFormActivity.this)
						 .setTitle("Mobile Number Status")
						 .setMessage("Please Enter Valid Mobile Number")
						 .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						     public void onClick(DialogInterface dialog, int which) { 
						         // Your code
						         witness_phone2.requestFocus();
						     }
						  })
						 .show();
						valdflg=false;
					}	
					else if(witness_address2.getText().toString().length()==0)
						{
						/*Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Phone Number", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, -200);
						View toastView = toast.getView();
				    	toastView.setBackgroundResource(R.drawable.toast_background);
					    toast.show();*/
					    witness_address2.setError(Html.fromHtml("<font color='black'>Please Enter Address</font>"));
						witness_address2.requestFocus();
						valdflg=false;
						}
					else if(witness_city2.getText().toString().length()==0)
						{
						/*Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Phone Number", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, -200);
						View toastView = toast.getView();
				    	toastView.setBackgroundResource(R.drawable.toast_background);
					    toast.show();*/
					    witness_city2.setError(Html.fromHtml("<font color='black'>Please Enter City/District</font>"));
						witness_city2.requestFocus();
						valdflg=false;
						}
					else if(wt_gender_group2.getCheckedRadioButtonId() == -1)
						{
							/*Toast toast = Toast.makeText(getApplicationContext(), "Please Select Gender", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.CENTER, 0, -200);
							View toastView = toast.getView();
					    	toastView.setBackgroundResource(R.drawable.toast_background);
						    toast.show();*/
						    showToast("Please Select Gender");
							valdflg=false;
						} 
					
				}
				else if (wt_layout3.getVisibility()==View.VISIBLE && wtFLG3) {
					if(witness_name3.getText().toString().length()==0)
						{
						/*Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, -200);
						View toastView = toast.getView();
				    	toastView.setBackgroundResource(R.drawable.toast_background);
					    toast.show();*/
					    witness_name3.setError(Html.fromHtml("<font color='black'>Please Enter Name</font>"));
						witness_name3.requestFocus();
						valdflg=false; 
						}
					else if(witness_fname3.getText().toString().length()==0)
						{
						/*Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, -200);
						View toastView = toast.getView();
				    	toastView.setBackgroundResource(R.drawable.toast_background);
					    toast.show();*/
					    witness_fname3.setError(Html.fromHtml("<font color='black'>Please Enter Father Name</font>"));
						witness_fname3.requestFocus();
						valdflg=false; 
						}	
					else if(witness_age3.getText().toString().length()==0)
						{
						/*Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, -200);
						View toastView = toast.getView();
				    	toastView.setBackgroundResource(R.drawable.toast_background);
					    toast.show();*/
					    witness_age3.setError(Html.fromHtml("<font color='black'>Please Enter Age</font>"));
						witness_age3.requestFocus();
						valdflg=false; 
						}		
						
					else if(witness_phone3.getText().toString().length()==0)
						{
						/*Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Phone Number", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, -200);
						View toastView = toast.getView();
				    	toastView.setBackgroundResource(R.drawable.toast_background);
					    toast.show();*/
					    witness_phone3.setError(Html.fromHtml("<font color='black'>Please Enter Mobile No</font>"));
						witness_phone3.requestFocus();
						valdflg=false;
						}
					else if(!validateMobileNo(witness_phone3.getText().toString())){
						new AlertDialog.Builder(WitnessFormActivity.this)
						 .setTitle("Mobile Number Status")
						 .setMessage("Please Enter Valid Mobile Number")
						 .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						     public void onClick(DialogInterface dialog, int which) { 
						         // Your code
						         
						         witness_phone3.requestFocus();
						     }
						  })
						 .show();
						valdflg=false;
					}	
					else if(witness_address3.getText().toString().length()==0)
						{
						/*Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Phone Number", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, -200);
						View toastView = toast.getView();
				    	toastView.setBackgroundResource(R.drawable.toast_background);
					    toast.show();*/
					    witness_address3.setError(Html.fromHtml("<font color='black'>Please Enter Address</font>"));
						witness_address3.requestFocus();
						valdflg=false;
						}
					else if(witness_city3.getText().toString().length()==0)
						{
						/*Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Phone Number", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, -200);
						View toastView = toast.getView();
				    	toastView.setBackgroundResource(R.drawable.toast_background);
					    toast.show();*/
					    witness_city3.setError(Html.fromHtml("<font color='black'>Please Enter City/District</font>"));
						witness_city3.requestFocus();
						valdflg=false;
						}
					else if(wt_gender_group3.getCheckedRadioButtonId() == -1)
						{
							/*Toast toast = Toast.makeText(getApplicationContext(), "Please Select Gender", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.CENTER, 0, -200);
							View toastView = toast.getView();
					    	toastView.setBackgroundResource(R.drawable.toast_background);
						    toast.show();*/
						    showToast("Please Select Gender");
							valdflg=false;
						} 
					
				}
				else if (wt_layout4.getVisibility()==View.VISIBLE && wtFLG4) {
					if(witness_name4.getText().toString().length()==0)
						{
						/*Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, -200);
						View toastView = toast.getView();
				    	toastView.setBackgroundResource(R.drawable.toast_background);
					    toast.show();*/
					    witness_name4.setError(Html.fromHtml("<font color='black'>Please Enter Name</font>"));
						witness_name4.requestFocus();
						valdflg=false; 
						}
					else if(witness_fname4.getText().toString().length()==0)
						{
						/*Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, -200);
						View toastView = toast.getView();
				    	toastView.setBackgroundResource(R.drawable.toast_background);
					    toast.show();*/
					    witness_fname4.setError(Html.fromHtml("<font color='black'>Please Enter Father Name</font>"));
						witness_fname4.requestFocus();
						valdflg=false; 
						}	
					else if(witness_age4.getText().toString().length()==0)
						{
						/*Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, -200);
						View toastView = toast.getView();
				    	toastView.setBackgroundResource(R.drawable.toast_background);
					    toast.show();*/
					    witness_age4.setError(Html.fromHtml("<font color='black'>Please Enter Age</font>"));
						witness_age4.requestFocus();
						valdflg=false; 
						}		
						
					else if(witness_phone4.getText().toString().length()==0)
						{
						/*Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Phone Number", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, -200);
						View toastView = toast.getView();
				    	toastView.setBackgroundResource(R.drawable.toast_background);
					    toast.show();*/
					    witness_phone4.setError(Html.fromHtml("<font color='black'>Please Enter Mobile No</font>"));
						witness_phone4.requestFocus();
						valdflg=false;
						}
					else if(!validateMobileNo(witness_phone4.getText().toString())){
						new AlertDialog.Builder(WitnessFormActivity.this)
						 .setTitle("Mobile Number Status")
						 .setMessage("Please Enter Valid Mobile Number")
						 .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						     public void onClick(DialogInterface dialog, int which) { 
						         // Your code
						         
						         witness_phone4.requestFocus();
						     }
						  })
						 .show();
						valdflg=false;
					}	
					else if(witness_address4.getText().toString().length()==0)
						{
						/*Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Phone Number", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, -200);
						View toastView = toast.getView();
				    	toastView.setBackgroundResource(R.drawable.toast_background);
					    toast.show();*/
					    witness_address4.setError(Html.fromHtml("<font color='black'>Please Enter Address</font>"));
						witness_address4.requestFocus();
						valdflg=false;
						}
					else if(witness_city4.getText().toString().length()==0)
						{
						/*Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Phone Number", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, -200);
						View toastView = toast.getView();
				    	toastView.setBackgroundResource(R.drawable.toast_background);
					    toast.show();*/
					    witness_city4.setError(Html.fromHtml("<font color='black'>Please Enter City/District</font>"));
						witness_city4.requestFocus();
						valdflg=false;
						}
					else if(wt_gender_group4.getCheckedRadioButtonId() == -1)
						{
							/*Toast toast = Toast.makeText(getApplicationContext(), "Please Select Gender", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.CENTER, 0, -200);
							View toastView = toast.getView();
					    	toastView.setBackgroundResource(R.drawable.toast_background);
						    toast.show();*/
						    showToast("Please Select Gender");
							valdflg=false;
						} 
					
				}
				if(valdflg){
		            try {
		            	Log.i("WitnessFormActivity.selectedID :", WitnessFormActivity.selectedID);
						wt_id1_Spinner=WitnessFormActivity.selectedID.trim().length()>1?WitnessFormActivity.selectedID.toString().trim():"NA";
						wt_id2_Spinner=WitnessFormActivity.selectedID2.trim().length()>1?WitnessFormActivity.selectedID2.toString().trim():"NA";
						wt_id3_Spinner=WitnessFormActivity.selectedID3.trim().length()>1?WitnessFormActivity.selectedID3.toString().trim():"NA";
						wt_id4_Spinner=WitnessFormActivity.selectedID4.trim().length()>1?WitnessFormActivity.selectedID4.toString().trim():"NA";
		            	
		            	ET_Gender = wt_gender.getText().toString();
		            	
		            	JSONObject witnessobj1 = new JSONObject();
		            	witnessobj1.put("witness_name", WitnessFormActivity.witness_name!=null?WitnessFormActivity.witness_name.getText().toString():"");
		            	witnessobj1.put("witness_fname", WitnessFormActivity.witness_fname!=null?WitnessFormActivity.witness_fname.getText().toString():"");
		            	witnessobj1.put("witness_age", WitnessFormActivity.witness_age!=null?WitnessFormActivity.witness_age.getText().toString():"");
		            	witnessobj1.put("witness_gender",ET_Gender);
		            	Log.i("Witness Gender ::",ET_Gender);
		            	witnessobj1.put("witness_phone", WitnessFormActivity.witness_phone!=null?WitnessFormActivity.witness_phone.getText().toString():"");
		            	witnessobj1.put("witness_address", WitnessFormActivity.witness_address!=null?WitnessFormActivity.witness_address.getText().toString():"");
		            	witnessobj1.put("witness_city", WitnessFormActivity.witness_city!=null?WitnessFormActivity.witness_city.getText().toString():"");
		            	witnessobj1.put("wt_id_details", wt_id_details.getText()!=null?wt_id_details.getText().toString():"NA");
		            	witnessobj1.put("witness_id_code", getIDCode(wt_id1_Spinner)!=null?getIDCode(wt_id1_Spinner):"NA");
		            	Log.i("witness_name.getText() Value :::",getIDCode(wt_id1_Spinner));
		            	//Log.i("WitnessFormActivity.previewWitnessPromtBuf Value :::", WitnessFormActivity.previewWitnessPromtBuf+"");
		            	//
		            	
		            	
		            	if(witness_name.getText()!=null && witness_name.getText().toString().trim().length()>2){
		            		
		            		WitnessFormActivity.previewWitnessPromtBuf.append("\n Witness 1 Name :").append(witness_name.getText().toString());
							WitnessFormActivity.previewWitnessPromtBuf.append("\n Witness 1 Father Name :").append(witness_fname.getText().toString());
							WitnessFormActivity.previewWitnessPromtBuf.append("\n Witness 1 DOB :").append(witness_age.getText().toString());
							WitnessFormActivity.previewWitnessPromtBuf.append("\n Witness 1 Gender :").append(wt_gender.getText().toString());
							WitnessFormActivity.previewWitnessPromtBuf.append("\n Witness 1 Mobile :").append(witness_phone.getText().toString());
							WitnessFormActivity.previewWitnessPromtBuf.append("\n Witness 1 Address :").append(witness_address.getText().toString());
							WitnessFormActivity.previewWitnessPromtBuf.append("\n Witness 1 City :").append(witness_city.getText().toString());
							//WitnessFormActivity.previewWitnessPromtBuf.append("\n Witness 2 FPS :").append();
						}
		            	
		            	ET_Gender2 = wt_gender2.getText().toString();
		              	JSONObject witnessobj2 = new JSONObject();
		            	witnessobj2.put("witness_name", WitnessFormActivity.witness_name2!=null?WitnessFormActivity.witness_name2.getText().toString():"");
		            	witnessobj2.put("witness_fname", WitnessFormActivity.witness_fname2!=null?WitnessFormActivity.witness_fname2.getText().toString():"");
		            	witnessobj2.put("witness_age", WitnessFormActivity.witness_name2!=null?WitnessFormActivity.witness_name2.getText().toString():"");
		            	witnessobj2.put("witness_gender", wt_gender2.getText().toString());
		            	witnessobj2.put("witness_phone", WitnessFormActivity.witness_age2!=null?WitnessFormActivity.witness_age2.getText().toString():"");
		            	witnessobj2.put("witness_address", WitnessFormActivity.witness_address2!=null?WitnessFormActivity.witness_address2.getText().toString():"");
		            	witnessobj2.put("witness_city", WitnessFormActivity.witness_name2!=null?WitnessFormActivity.witness_name2.getText().toString():"");
		            	witnessobj2.put("wt_id_details", wt_id_details2.getText()!=null?wt_id_details2.getText().toString():"NA");
		            	witnessobj2.put("witness_id_code", wt_id2_Spinner!=null?getIDCode2(wt_id2_Spinner):"NA");
		            	Log.i("Spinner Value 2:::", getIDCode2(wt_id2_Spinner));
		            	if(witness_name2.getText()!=null && witness_name2.getText().toString().trim().length()>2){
							WitnessFormActivity.previewWitnessPromtBuf.append("\n\n Witness 2 Name :").append(witness_name2.getText().toString());
							WitnessFormActivity.previewWitnessPromtBuf.append("\n Witness 2 Father Name :").append(witness_fname2.getText().toString());
							WitnessFormActivity.previewWitnessPromtBuf.append("\n Witness 2 DOB :").append(witness_age2.getText().toString());
							WitnessFormActivity.previewWitnessPromtBuf.append("\n Witness 2 Gender :").append(wt_gender2.getText().toString());
							WitnessFormActivity.previewWitnessPromtBuf.append("\n Witness 2 Mobile :").append(witness_phone2.getText().toString());
							WitnessFormActivity.previewWitnessPromtBuf.append("\n Witness 2 Address :").append(witness_address2.getText().toString());
							WitnessFormActivity.previewWitnessPromtBuf.append("\n Witness 2 City :").append(witness_city2.getText().toString());
							//WitnessFormActivity.previewWitnessPromtBuf.append("\n Witness 2 FPS :").append();
						}
		          
		            	ET_Gender3 = wt_gender3.getText().toString();
		              	JSONObject witnessobj3 = new JSONObject();
		            	witnessobj3.put("witness_name", WitnessFormActivity.witness_name3!=null?WitnessFormActivity.witness_name3.getText().toString():"");
		            	witnessobj3.put("witness_fname", WitnessFormActivity.witness_fname3!=null?WitnessFormActivity.witness_fname3.getText().toString():"");
		            	witnessobj3.put("witness_age", WitnessFormActivity.witness_age3!=null?WitnessFormActivity.witness_age3.getText().toString():"");
		            	witnessobj3.put("witness_gender", wt_gender3.getText()!=null?WitnessFormActivity.wt_gender3.getText().toString():"M");
		            	witnessobj3.put("witness_phone", WitnessFormActivity.witness_phone3!=null?WitnessFormActivity.witness_phone3.getText().toString():"");
		            	witnessobj3.put("witness_address", WitnessFormActivity.witness_address3!=null?WitnessFormActivity.witness_address3.getText().toString():"");
		            	witnessobj3.put("witness_city", WitnessFormActivity.witness_city3!=null?WitnessFormActivity.witness_city3.getText().toString():"");
		            	witnessobj3.put("wt_id_details", wt_id_details3.getText()!=null?wt_id_details3.getText().toString():"NA");
		            	witnessobj3.put("witness_id_code", wt_id3_Spinner!=null?getIDCode3(wt_id3_Spinner):"NA");
		            	if(witness_name3.getText()!=null && witness_name3.getText().toString().trim().length()>2){
							WitnessFormActivity.previewWitnessPromtBuf.append("\n\n Witness 3 Name :").append(witness_name3.getText().toString());
							WitnessFormActivity.previewWitnessPromtBuf.append("\n Witness 3 Father Name :").append(witness_fname3.getText().toString());
							WitnessFormActivity.previewWitnessPromtBuf.append("\n Witness 3 DOB :").append(witness_age3.getText().toString());
							WitnessFormActivity.previewWitnessPromtBuf.append("\n Witness 3 Gender :").append(wt_gender3.getText().toString());
							WitnessFormActivity.previewWitnessPromtBuf.append("\n Witness 3 Mobile :").append(witness_phone3.getText().toString());
							WitnessFormActivity.previewWitnessPromtBuf.append("\n Witness 3 Address :").append(witness_address3.getText().toString());
							WitnessFormActivity.previewWitnessPromtBuf.append("\n Witness 3 City :").append(witness_city3.getText().toString());
							//WitnessFormActivity.previewWitnessPromtBuf.append("\n Witness 2 FPS :").append();
						}
		          
		            	ET_Gender4 = wt_gender4.getText().toString();
		              	JSONObject witnessobj4 = new JSONObject();
		            	witnessobj4.put("witness_name", WitnessFormActivity.witness_name4!=null?WitnessFormActivity.witness_name4.getText().toString():"");
		            	witnessobj4.put("witness_fname", WitnessFormActivity.witness_fname4!=null?WitnessFormActivity.witness_fname4.getText().toString():"");
		            	witnessobj4.put("witness_age", WitnessFormActivity.witness_age4!=null?WitnessFormActivity.witness_age4.getText().toString():"");
		            	witnessobj4.put("witness_gender", wt_gender4.getText().toString());
		            	witnessobj4.put("witness_phone", WitnessFormActivity.witness_phone4!=null?WitnessFormActivity.witness_phone4.getText().toString():"");
		            	witnessobj4.put("witness_address", WitnessFormActivity.witness_address4!=null?WitnessFormActivity.witness_address4.getText().toString():"");
		            	witnessobj4.put("witness_city", WitnessFormActivity.witness_city4!=null?WitnessFormActivity.witness_city4.getText().toString():"");
		            	witnessobj4.put("wt_id_details", wt_id_details4.getText()!=null?wt_id_details4.getText().toString():"NA");
		            	witnessobj4.put("witness_id_code", wt_id4_Spinner!=null?getIDCode4(wt_id4_Spinner):"NA");
		            	if(witness_name4.getText()!=null && witness_name4.getText().toString().trim().length()>2){
							WitnessFormActivity.previewWitnessPromtBuf.append("\n\n Witness 4 Name :").append(witness_name4.getText().toString());
							WitnessFormActivity.previewWitnessPromtBuf.append("\n Witness 4 Father Name :").append(witness_fname4.getText().toString());
							WitnessFormActivity.previewWitnessPromtBuf.append("\n Witness 4 DOB :").append(witness_age4.getText().toString());
							WitnessFormActivity.previewWitnessPromtBuf.append("\n Witness 4 Gender :").append(wt_gender4.getText().toString());
							WitnessFormActivity.previewWitnessPromtBuf.append("\n Witness 4 Mobile :").append(witness_phone4.getText().toString());
							WitnessFormActivity.previewWitnessPromtBuf.append("\n Witness 4 Address :").append(witness_address4.getText().toString());
							WitnessFormActivity.previewWitnessPromtBuf.append("\n Witness 4 City :").append(witness_city4.getText().toString());
							//WitnessFormActivity.previewWitnessPromtBuf.append("\n Witness 2 FPS :").append();
						}
		          
		            	JSONArray witnessDetails = new JSONArray();
		            	witnessDetails.put(witnessobj1);
		            	witnessDetails.put(witnessobj2);
		            	witnessDetails.put(witnessobj3);
		            	witnessDetails.put(witnessobj4);
						
		            	JSONObject witnessObj = new JSONObject();
		            	witnessObj.put("Witness", witnessDetails);
						ET_WitnessDetails = witnessObj.toString();
						/*String emailStr = email.getText().toString()*/
						Log.i("ET_WitnessDetails ::",ET_WitnessDetails);
						
						Log.i("Witness Gender ::",WitnessFormActivity.wt_gender.getText().toString());
						
						finish();
						
						OffenderActivity.add_witness_tick.setVisibility(View.VISIBLE);
		            } catch (JSONException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            }
					}
			}
		});
		
		try {
			if(WitnessFormActivity.ET_WitnessDetails!=null && WitnessFormActivity.ET_WitnessDetails.length()>10){
           
				JSONObject jsonObj = new JSONObject(WitnessFormActivity.ET_WitnessDetails);
            // Getting JSON Array node
				JSONArray jsonArray = jsonObj.getJSONArray("Witness");
            // looping through All Witness
               for (int i = 0, size = jsonArray.length(); i < size; i++)
                 {
                   JSONObject c = jsonArray.getJSONObject(i);
                   //witness_id_code
                 if(c.getString("witness_name")!=null &&  c.getString("witness_name").trim().length()>2){
                    
	                    String idCode=c.getString("witness_id_code");
	                    String idDet=c.getString("wt_id_details");
	                    
                    if(i==0){
	                    WitnessFormActivity.witness_name.setText(c.getString("witness_name"));
	                    WitnessFormActivity.witness_fname.setText(c.getString("witness_name"));
	                   WitnessFormActivity.witness_age.setText(c.getString("witness_age").toString());
	                   if("M".equals(ET_Gender)){
	                	  wt_male.setChecked(true);
	                   }else if("F".equals(ET_Gender)){
	                	   wt_female.setChecked(true);
	                   }else if("O".equals(ET_Gender)){
	                	   wt_others.setChecked(true);
	                   }
	                	WitnessFormActivity.witness_phone.setText(c.getString("witness_phone").toString());
	                	WitnessFormActivity.witness_address.setText(c.getString("witness_address").toString());
	                	WitnessFormActivity.witness_city.setText(c.getString("witness_city").toString());
                    }
	                	
                    else if(i==1){
                    	wt_layout2.setVisibility(View.VISIBLE);
                    	WitnessFormActivity.witness_name2.setText(c.getString("witness_name"));
	                    WitnessFormActivity.witness_fname2.setText(c.getString("witness_name"));
	                	WitnessFormActivity.witness_age2.setText(c.getString("witness_age").toString());
	                	 if("M".equals(ET_Gender2)){
		                	  wt_male2.setChecked(true);
		                   }else if("F".equals(ET_Gender2)){
		                	   wt_female2.setChecked(true);
		                   }else if("O".equals(ET_Gender2)){
		                	   wt_others2.setChecked(true);
		                   }
	                	WitnessFormActivity.witness_phone2.setText(c.getString("witness_phone").toString());
	                	WitnessFormActivity.witness_address2.setText(c.getString("witness_address").toString());
	                	WitnessFormActivity.witness_city2.setText(c.getString("witness_city").toString());
                    }else if(i==2){
                    	wt_layout3.setVisibility(View.VISIBLE);
                    	WitnessFormActivity.witness_name3.setText(c.getString("witness_name"));
	                    WitnessFormActivity.witness_fname3.setText(c.getString("witness_name"));
	                	WitnessFormActivity.witness_age3.setText(c.getString("witness_age").toString());
	                	 if("M".equals(ET_Gender3)){
		                	  wt_male3.setChecked(true);
		                   }else if("F".equals(ET_Gender3)){
		                	   wt_female3.setChecked(true);
		                   }else if("O".equals(ET_Gender3)){
		                	   wt_others3.setChecked(true);
		                   }
	                	WitnessFormActivity.witness_phone3.setText(c.getString("witness_phone").toString());
	                	WitnessFormActivity.witness_address3.setText(c.getString("witness_address").toString());
	                	WitnessFormActivity.witness_city3.setText(c.getString("witness_city").toString());
                    }
                    else if(i==3){
                    	wt_layout4.setVisibility(View.VISIBLE);
                    	WitnessFormActivity.witness_name4.setText(c.getString("witness_name"));
	                    WitnessFormActivity.witness_fname4.setText(c.getString("witness_name"));
	                	WitnessFormActivity.witness_age4.setText(c.getString("witness_age").toString());
	                	 if("M".equals(ET_Gender4)){
		                	  wt_male4.setChecked(true);
		                   }else if("F".equals(ET_Gender4)){
		                	   wt_female4.setChecked(true);
		                   }else if("O".equals(ET_Gender4)){
		                	   wt_others4.setChecked(true);
		                   }
	                	WitnessFormActivity.witness_phone4.setText(c.getString("witness_phone").toString());
	                	WitnessFormActivity.witness_address4.setText(c.getString("witness_address").toString());
	                	WitnessFormActivity.witness_city4.setText(c.getString("witness_city").toString());
                    }
                 }
            }
			}      
		} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}//on create end
	
	
	
	 /******************** Get Details BY AADHAAR NO Class AsyncGetIDDetails Starts****************************/
	
		class AsyncGetIDDetails extends AsyncTask<Void,Void, String>{
			ProgressDialog dialog = new ProgressDialog(WitnessFormActivity.this);
				/* (non-Javadoc)
				 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
				 */
				@Override
				protected String doInBackground(Void... params) {
					// TODO Auto-generated method stub
				Log.i("getIDDetails method called ", "YES");
				 
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
								SoapObject request = new SoapObject(NAMESPACE, "getDetailsByAADHAR");
								Log.i("respondent_id_details :::::::::::::",wt_id_details.getText().toString());
							    
								 if(get_detailsFlg1){
									request.addProperty("UID", wt_id_details.getText().toString());
									request.addProperty("EID", wt_id_details.getText().toString());  						
									}
								 if(get_detailsFlg2){
										request.addProperty("UID", wt_id_details2.getText().toString());
										request.addProperty("EID", wt_id_details2.getText().toString());
									 }
								 if(get_detailsFlg3){
										request.addProperty("UID", wt_id_details3.getText().toString());
										request.addProperty("EID", wt_id_details3.getText().toString());
									 }
								 if(get_detailsFlg4){
										request.addProperty("UID", wt_id_details4.getText().toString());
										request.addProperty("EID", wt_id_details4.getText().toString());								
										}
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
								    public void run(){   			// http://service.mother.com/HydPettyCaseService/services/PettyCaseServiceImpl?wsdl
								    	if(challanGenresp!=null && challanGenresp.length()>25 ){
								    		try {
								                    JSONObject jsonObj = new JSONObject(challanGenresp);
								                    // Getting JSON Array node
								                    JSONArray contacts = jsonObj.getJSONArray("ID_DETAILS");
								                    // looping through All Contacts
								                        JSONObject c = contacts.getJSONObject(0);
								                        Log.i("Name :::", c.getString("NAME")) ;
								                        Log.i("Father Name :::", c.getString("FNAME")) ;
								                        Log.i("DOB :::", c.getString("DOB")) ;
								                        Log.i("get_detailsFlg1 :::", ""+get_detailsFlg1) ;
								                      
								                        //15/05/1994
								                        if(get_detailsFlg1){
									                        witness_name.setText(c.getString("NAME"));
									                        witness_fname.setText(c.getString("FNAME"));
									                       /* witness_age.setText(c.getString("DOB").toString());*/
									                        String date_birth = c.getString("DOB");
															String[] split_dob = date_birth.split("/");
															String service_year = "" + split_dob[2];

															int final_age = year - Integer.parseInt(service_year);
															Log.i("final_age ::::::::", "" + final_age);

															witness_age.setText("" + final_age);
									                        
									                        if("M".equals(c.getString("GENDER"))){
											                	  wt_male.setChecked(true);
											                   }else if("F".equals(c.getString("GENDER"))){
											                	   wt_female.setChecked(true);
											                   }else if("O".equals(c.getString("GENDER"))){
											                	   wt_others.setChecked(true);
											                   }
									                        String completeAddress=c.getString("HNO")+c.getString("STREET")+c.getString("VILLAGE")+c.getString("MANDAL")
									                        		+c.getString("DISTRICT")+c.getString("STATE")+c.getString("PIN_CODE");
									                        witness_address.setText(completeAddress);
									                        witness_phone.setText(c.getString("PHONE_NO").toString());
									                        witness_city.setText(c.get("DISTRICT").toString());
									                       
									                        Log.i("Image 1 byte[]",""+ Base64.decode(c.getString("IMAGE").toString().trim().getBytes(),1));
									                        
									                        /*if(c.getString("IMAGE")!=null && c.getString("IMAGE").toString().trim().length()>100 ){
									                        	image1ByteArray=Base64.decode(c.getString("IMAGE").toString().trim().getBytes(),1);
										                        Bitmap bmp = BitmapFactory.decodeByteArray(image1ByteArray, 0, image1ByteArray.length);
										                        respondent_img1.setImageBitmap(bmp);
									                        }else {
									                        	respondent_img1.setImageResource(R.drawable.photo);
															}*/
									                        
								                        }  if(get_detailsFlg2){
									                        witness_name2.setText(c.getString("NAME"));
									                        witness_fname2.setText(c.getString("FNAME"));
									                       /* witness_age2.setText(c.getString("DOB").toString());*/
									                        String date_birth = c.getString("DOB");
									                        String [] split_dob = date_birth.split("\\/");
									                        String service_year = ""+split_dob[2];
									                        
									                        int final_age = year-Integer.parseInt(service_year);
									                        Log.i("final_age ::::::::", ""+final_age);
									                        
									                        witness_age2.setText(""+final_age);
									                        
									                        if("M".equals(c.getString("GENDER"))){
											                	  wt_male2.setChecked(true);
											                   }else if("F".equals(c.getString("GENDER"))){
											                	   wt_female2.setChecked(true);
											                   }else if("O".equals(c.getString("GENDER"))){
											                	   wt_others2.setChecked(true);
											                   }
									                        String completeAddress=c.getString("HNO")+c.getString("STREET")+c.getString("VILLAGE")+c.getString("MANDAL")
									                        		+c.getString("DISTRICT")+c.getString("STATE")+c.getString("PIN_CODE");
									                        witness_address2.setText(completeAddress);
									                        witness_phone2.setText(c.getString("PHONE_NO").toString());
									                        witness_city2.setText(c.get("DISTRICT").toString());
									                       
									                        Log.i("Image 1 byte[]",""+ Base64.decode(c.getString("IMAGE").toString().trim().getBytes(),1));
									                        
									                        /*if(c.getString("IMAGE")!=null && c.getString("IMAGE").toString().trim().length()>100 ){
									                        	image1ByteArray=Base64.decode(c.getString("IMAGE").toString().trim().getBytes(),1);
										                        Bitmap bmp = BitmapFactory.decodeByteArray(image1ByteArray, 0, image1ByteArray.length);
										                        respondent_img1.setImageBitmap(bmp);
									                        }else {
									                        	respondent_img1.setImageResource(R.drawable.photo);
															}*/
									                        
								                        }  if(get_detailsFlg3){
								                        	
									                        witness_name3.setText(c.getString("NAME"));
									                        witness_fname3.setText(c.getString("FNAME"));
									                       /* witness_age3.setText(c.getString("DOB").toString());*/
									                        String date_birth = c.getString("DOB");
									                        String [] split_dob = date_birth.split("\\/");
									                        String service_year = ""+split_dob[2];
									                        
									                        int final_age = year-Integer.parseInt(service_year);
									                        Log.i("final_age ::::::::", ""+final_age);
									                        
									                        witness_age3.setText(""+final_age);
									                        
									                        if("M".equals(c.getString("GENDER"))){
											                	  wt_male3.setChecked(true);
											                   }else if("F".equals(c.getString("GENDER"))){
											                	   wt_female3.setChecked(true);
											                   }else if("O".equals(c.getString("GENDER"))){
											                	   wt_others3.setChecked(true);
											                   }
									                        String completeAddress=c.getString("HNO")+c.getString("STREET")+c.getString("VILLAGE")+c.getString("MANDAL")
									                        		+c.getString("DISTRICT")+c.getString("STATE")+c.getString("PIN_CODE");
									                        witness_address3.setText(completeAddress);
									                        witness_phone3.setText(c.getString("PHONE_NO").toString());
									                        witness_city3.setText(c.get("DISTRICT").toString());
									                       
									                        Log.i("Image 1 byte[]",""+ Base64.decode(c.getString("IMAGE").toString().trim().getBytes(),1));
									                        
									                        /*if(c.getString("IMAGE")!=null && c.getString("IMAGE").toString().trim().length()>100 ){
									                        	image1ByteArray=Base64.decode(c.getString("IMAGE").toString().trim().getBytes(),1);
										                        Bitmap bmp = BitmapFactory.decodeByteArray(image1ByteArray, 0, image1ByteArray.length);
										                        respondent_img1.setImageBitmap(bmp);
									                        }else {
									                        	respondent_img1.setImageResource(R.drawable.photo);
															}*/
									                        
								                        
									                        
								                        } if(get_detailsFlg4){
								                        	
									                        witness_name4.setText(c.getString("NAME"));
									                        witness_fname4.setText(c.getString("FNAME"));
									                       /* witness_age4.setText(c.getString("DOB").toString());*/
									                        
									                        String date_birth = c.getString("DOB");
									                        String [] split_dob = date_birth.split("\\/");
									                        String service_year = ""+split_dob[2];
									                        
									                        int final_age = year-Integer.parseInt(service_year);
									                        Log.i("final_age ::::::::", ""+final_age);
									                        
									                        witness_age4.setText(""+final_age);
									                        
									                        if("M".equals(c.getString("GENDER"))){
											                	  wt_male4.setChecked(true);
											                   }else if("F".equals(c.getString("GENDER"))){
											                	   wt_female4.setChecked(true);
											                   }else if("O".equals(c.getString("GENDER"))){
											                	   wt_others4.setChecked(true);
											                   }
									                        String completeAddress=c.getString("HNO")+c.getString("STREET")+c.getString("VILLAGE")+c.getString("MANDAL")
									                        		+c.getString("DISTRICT")+c.getString("STATE")+c.getString("PIN_CODE");
									                        witness_address4.setText(completeAddress);
									                        witness_phone4.setText(c.getString("PHONE_NO").toString());
									                        witness_city4.setText(c.get("DISTRICT").toString());
									                       
									                        Log.i("Image 1 byte[]",""+ Base64.decode(c.getString("IMAGE").toString().trim().getBytes(),1));
									                        
									                        /*if(c.getString("IMAGE")!=null && c.getString("IMAGE").toString().trim().length()>100 ){
									                        	image1ByteArray=Base64.decode(c.getString("IMAGE").toString().trim().getBytes(),1);
										                        Bitmap bmp = BitmapFactory.decodeByteArray(image1ByteArray, 0, image1ByteArray.length);
										                        respondent_img1.setImageBitmap(bmp);
									                        }else {
									                        	respondent_img1.setImageResource(R.drawable.photo);
															}*/
									                        
								                        
								                        }
								                    	}
											   		catch(Exception e){
								                    	e.printStackTrace();
								                    }
											}else{
												Log.i("no data from service", "no ");
												/*Toast toast = Toast.makeText(getApplicationContext(), "No data Found", Toast.LENGTH_LONG);
												toast.setGravity(Gravity.CENTER, 0, -200);
												View toastView = toast.getView();
										    	toastView.setBackgroundResource(R.drawable.toast_background);
											    toast.show();*/
												showToast("No Data Found");
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
					//showDialog(PROGRESS_DIALOG);
						getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
						showDialog(PROGRESS_DIALOG);
				}

				@Override
				protected void onPostExecute(String result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					removeDialog(PROGRESS_DIALOG);			
					if (!isNetworkAvailable()) {
					    // do something
				        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(WitnessFormActivity.this);
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
		
		 /******************** Get Details BY RC Class AsyncGetIDDetails Ends****************************/
		 
		 
		 /******************** Get Details BY Driving License Class AsyncGetDLDetails Starts****************************/
		 
		 class AsyncGetDLDetails extends AsyncTask<Void,Void, String>{
				
			 ProgressDialog dialog = new ProgressDialog(WitnessFormActivity.this);
			 
			 @Override
				protected String doInBackground(Void... params) {
					// TODO Auto-generated method stub
				Log.i("getDetailsByDrivingLicence method called ", "YES");
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
								SoapObject request = new SoapObject(NAMESPACE, "getDetailsByDrivingLicence");
								Log.i("respondent_id_details :::::::::::::",wt_id_details.getText().toString());
								  if(get_detailsFlg1){
									  request.addProperty("dl_no", wt_id_details.getText().toString());
								  }
								  if(get_detailsFlg2){
									  request.addProperty("dl_no", wt_id_details2.getText().toString());
								  }
								  
								  if(get_detailsFlg3){
									  request.addProperty("dl_no", wt_id_details3.getText().toString());
								  }
								  
								  if(get_detailsFlg4){
									  request.addProperty("dl_no", wt_id_details4.getText().toString());
								  }
							    //	request.addProperty("EID", respondent_id_details.getText().toString());
								SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
										SoapEnvelope.VER11);
								envelope.dotNet = true;
								envelope.setOutputSoapObject(request);
								Log.i("request", "" + request);
								HttpTransportSE androidHttpTransport = new HttpTransportSE(ip+"/HydPettyCaseService/services/PettyCaseServiceImpl?wsdl");
								Log.i("androidHttpTransport", "" + androidHttpTransport);
								androidHttpTransport.call(SOAP_ACTION_ID, envelope);
								Object result = envelope.getResponse();
								challanGenresp=result.toString();
								Log.i("** getDetailsByDrivingLicence response***", "" + challanGenresp);
								
								runOnUiThread(new Runnable() {
								    public void run(){   
								    	if(challanGenresp!=null && challanGenresp.length()>25 ){
											   try {
								                    JSONObject jsonObj = new JSONObject(challanGenresp);
								                    // Getting JSON Array node
								                    JSONArray contacts = jsonObj.getJSONArray("LICENSE_DETAILS");
								                    // looping through All Contacts
								                        JSONObject c = contacts.getJSONObject(0);
								                        
								                        if(get_detailsFlg1){
									                        witness_name.setText(c.getString("NAME"));
									                        witness_fname.setText(c.getString("FNAME"));
									                        witness_age.setText(c.getString("DOB").toString());
									                        if("M".equals(c.getString("GENDER"))){
											                	  wt_male.setChecked(true);
											                   }else if("F".equals(c.getString("GENDER"))){
											                	   wt_female.setChecked(true);
											                   }else if("O".equals(c.getString("GENDER"))){
											                	   wt_others.setChecked(true);
											                   }
									                        String completeAddress=c.getString("HNO")+c.getString("STREET")+c.getString("VILLAGE")+c.getString("MANDAL")
									                        		+c.getString("DISTRICT")+c.getString("STATE")+c.getString("PIN_CODE");
									                        witness_address.setText(completeAddress);
									                        witness_phone.setText(c.getString("PHONE_NO").toString());
								                        }
								                          if(get_detailsFlg2){
								                        	 witness_name2.setText(c.getString("NAME"));
									                        witness_fname2.setText(c.getString("FNAME"));
									                        witness_age2.setText(c.getString("DOB").toString());
									                        if("M".equals(c.getString("GENDER"))){
											                	  wt_male2.setChecked(true);
											                   }else if("F".equals(c.getString("GENDER"))){
											                	   wt_female2.setChecked(true);
											                   }else if("O".equals(c.getString("GENDER"))){
											                	   wt_others2.setChecked(true);
											                   }
									                        String completeAddress=c.getString("HNO")+c.getString("STREET")+c.getString("VILLAGE")+c.getString("MANDAL")
									                        		+c.getString("DISTRICT")+c.getString("STATE")+c.getString("PIN_CODE");
									                        witness_address2.setText(completeAddress);
									                        witness_phone2.setText(c.getString("PHONE_NO").toString());
								                        }
								                          if(get_detailsFlg3){
								                        	 witness_name3.setText(c.getString("NAME"));
									                        witness_fname3.setText(c.getString("FNAME"));
									                        witness_age3.setText(c.getString("DOB").toString());
									                        if("M".equals(c.getString("GENDER"))){
											                	  wt_male3.setChecked(true);
											                   }else if("F".equals(c.getString("GENDER"))){
											                	   wt_female3.setChecked(true);
											                   }else if("O".equals(c.getString("GENDER"))){
											                	   wt_others3.setChecked(true);
											                   }
									                        String completeAddress=c.getString("HNO")+c.getString("STREET")+c.getString("VILLAGE")+c.getString("MANDAL")
									                        		+c.getString("DISTRICT")+c.getString("STATE")+c.getString("PIN_CODE");
									                        witness_address3.setText(completeAddress);
									                        witness_phone3.setText(c.getString("PHONE_NO").toString());
								                        }
								                          if(get_detailsFlg4){
								                        	 witness_name4.setText(c.getString("NAME"));
									                        witness_fname4.setText(c.getString("FNAME"));
									                        witness_age4.setText(c.getString("DOB").toString());
									                        if("M".equals(c.getString("GENDER"))){
											                	  wt_male4.setChecked(true);
											                   }else if("F".equals(c.getString("GENDER"))){
											                	   wt_female4.setChecked(true);
											                   }else if("O".equals(c.getString("GENDER"))){
											                	   wt_others4.setChecked(true);
											                   }
									                        String completeAddress=c.getString("HNO")+c.getString("STREET")+c.getString("VILLAGE")+c.getString("MANDAL")
									                        		+c.getString("DISTRICT")+c.getString("STATE")+c.getString("PIN_CODE");
									                        witness_address4.setText(completeAddress);
									                        witness_phone4.setText(c.getString("PHONE_NO").toString());
								                        }
								                    }
											   		catch(Exception e){
								                    	e.printStackTrace();
								                    }
											}else{
												Log.i("no data from service", "no ");
												/*Toast toast = Toast.makeText(getApplicationContext(), "No data Found", Toast.LENGTH_LONG);
												toast.setGravity(Gravity.CENTER, 0, -200);
												View toastView = toast.getView();
										    	toastView.setBackgroundResource(R.drawable.toast_background);
											    toast.show();*/
												showToast("No Data Found");
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
					//showDialog(PROGRESS_DIALOG);
						getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
						showDialog(PROGRESS_DIALOG);
				}

				@Override
				protected void onPostExecute(String result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					removeDialog(PROGRESS_DIALOG);
					if (!isNetworkAvailable()) {
					    // do something
				        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(WitnessFormActivity.this);
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
		 
		 /******************** Get Details BY Driving License Class AsyncGetDLDetails Ends****************************/
		 
		/******************** Get Details BY RC Class AsyncGetRCDetails Starts****************************/
		 
		 class AsyncGetRCDetails extends AsyncTask<Void,Void, String>{
			
			 ProgressDialog dialog = new ProgressDialog(WitnessFormActivity.this);	
			 @Override
				protected String doInBackground(Void... params) {
					// TODO Auto-generated method stub
				Log.i("getDetailsByRC method called ", "YES");
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
								SoapObject request = new SoapObject(NAMESPACE, "getDetailsByRC");
								Log.i("respondent_id_details :::::::::::::",wt_id_details.getText().toString());
								 if(get_detailsFlg1){
									 request.addProperty("regn_no", wt_id_details.getText().toString());
								 }
								 if(get_detailsFlg2){
									 request.addProperty("regn_no", wt_id_details2.getText().toString());
								 }
								 if(get_detailsFlg3){
									 request.addProperty("regn_no", wt_id_details3.getText().toString());
								 }
								 if(get_detailsFlg4){
									 request.addProperty("regn_no", wt_id_details4.getText().toString());
								 }
							    //	request.addProperty("EID", respondent_id_details.getText().toString());
								SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
										SoapEnvelope.VER11);
								envelope.dotNet = true;
								envelope.setOutputSoapObject(request);
								Log.i("request", "" + request);
								HttpTransportSE androidHttpTransport = new HttpTransportSE(ip+"/HydPettyCaseService/services/PettyCaseServiceImpl?wsdl");
								Log.i("androidHttpTransport", "" + androidHttpTransport);
								androidHttpTransport.call(SOAP_ACTION_ID, envelope);
								Object result = envelope.getResponse();
								challanGenresp=result.toString();
								Log.i("** getDetailsByRC response***", "" + challanGenresp);
								
								runOnUiThread(new Runnable() {
								    public void run(){   
								    	if(challanGenresp!=null && challanGenresp.length()>25 ){
											   try {
								                    JSONObject jsonObj = new JSONObject(challanGenresp);
								                    // Getting JSON Array node
								                    JSONArray contacts = jsonObj.getJSONArray("RC_DETAILS");
								                    // looping through All Contacts
								                        JSONObject c = contacts.getJSONObject(0);
								                        if(get_detailsFlg1){
									                        witness_name.setText(c.getString("NAME"));
									                        witness_fname.setText(c.getString("FNAME"));
									                        witness_age.setText(c.getString("DOB").toString());
									                        if("M".equals(c.getString("GENDER"))){
											                	  wt_male.setChecked(true);
											                   }else if("F".equals(c.getString("GENDER"))){
											                	   wt_female.setChecked(true);
											                   }else if("O".equals(c.getString("GENDER"))){
											                	   wt_others.setChecked(true);
											                   }
									                        String completeAddress=c.getString("HNO")+c.getString("STREET")+c.getString("VILLAGE")+c.getString("MANDAL")
									                        		+c.getString("DISTRICT")+c.getString("STATE")+c.getString("PIN_CODE");
									                        witness_address.setText(completeAddress);
									                        witness_phone.setText(c.getString("PHONE_NO").toString());
								                        }
								                         if(get_detailsFlg2){
								                        	witness_name2.setText(c.getString("NAME"));
									                        witness_fname2.setText(c.getString("FNAME"));
									                        witness_age2.setText(c.getString("DOB").toString());
									                        if("M".equals(c.getString("GENDER"))){
											                	  wt_male2.setChecked(true);
											                   }else if("F".equals(c.getString("GENDER"))){
											                	   wt_female.setChecked(true);
											                   }else if("O".equals(c.getString("GENDER"))){
											                	   wt_others2.setChecked(true);
											                   }
									                        String completeAddress=c.getString("HNO")+c.getString("STREET")+c.getString("VILLAGE")+c.getString("MANDAL")
									                        		+c.getString("DISTRICT")+c.getString("STATE")+c.getString("PIN_CODE");
									                        witness_address2.setText(completeAddress);
									                        witness_phone2.setText(c.getString("PHONE_NO").toString());
								                        }
								                         if(get_detailsFlg3){
								                        	witness_name3.setText(c.getString("NAME"));
									                        witness_fname3.setText(c.getString("FNAME"));
									                        witness_age3.setText(c.getString("DOB").toString());
									                        if("M".equals(c.getString("GENDER"))){
											                	  wt_male3.setChecked(true);
											                   }else if("F".equals(c.getString("GENDER"))){
											                	   wt_female3.setChecked(true);
											                   }else if("O".equals(c.getString("GENDER"))){
											                	   wt_others3.setChecked(true);
											                   }
									                        String completeAddress=c.getString("HNO")+c.getString("STREET")+c.getString("VILLAGE")+c.getString("MANDAL")
									                        		+c.getString("DISTRICT")+c.getString("STATE")+c.getString("PIN_CODE");
									                        witness_address3.setText(completeAddress);
									                        witness_phone3.setText(c.getString("PHONE_NO").toString());
								                        }
								                         if(get_detailsFlg4){
								                        	witness_name4.setText(c.getString("NAME"));
									                        witness_fname4.setText(c.getString("FNAME"));
									                        witness_age4.setText(c.getString("DOB").toString());
									                        if("M".equals(c.getString("GENDER"))){
											                	  wt_male4.setChecked(true);
											                   }else if("F".equals(c.getString("GENDER"))){
											                	   wt_female4.setChecked(true);
											                   }else if("O".equals(c.getString("GENDER"))){
											                	   wt_others4.setChecked(true);
											                   }
									                        String completeAddress=c.getString("HNO")+c.getString("STREET")+c.getString("VILLAGE")+c.getString("MANDAL")
									                        		+c.getString("DISTRICT")+c.getString("STATE")+c.getString("PIN_CODE");
									                        witness_address4.setText(completeAddress);
									                        witness_phone4.setText(c.getString("PHONE_NO").toString());
								                        }
								                    }
											   		catch(Exception e){
								                    	e.printStackTrace();
								                    }
												
											}else{
												Log.i("no data from service", "no ");
												/*Toast toast = Toast.makeText(getApplicationContext(), "No data Found", Toast.LENGTH_LONG);
												toast.setGravity(Gravity.CENTER, 0, -200);
												View toastView = toast.getView();
										    	toastView.setBackgroundResource(R.drawable.toast_background);
											    toast.show();*/
												showToast("No Data Found");
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
					//showDialog(PROGRESS_DIALOG);
						getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
						showDialog(PROGRESS_DIALOG);
				}

				@Override
				protected void onPostExecute(String result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					removeDialog(PROGRESS_DIALOG);
					if (!isNetworkAvailable()) {
					    // do something
				        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(WitnessFormActivity.this);
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
		 /******************** Get Details BY RC Class AsyncGetRCDetails Ends****************************/
	
	
	 protected boolean validateMobileNo(String mobileNo) {
			boolean flg=false;
			try {
				if(mobileNo!=null &&  mobileNo.trim().length()==10
					&&( "7".equals(mobileNo.trim().substring(0,1)) 
					|| "8".equals(mobileNo.trim().substring(0,1))
					|| "9".equals(mobileNo.trim().substring(0,1)))){
					flg=true;
				}else if(mobileNo!=null &&  mobileNo.trim().length()==11 && "0".equals(mobileNo.trim().substring(0,1)) ){
					flg=true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				flg=false;
				
			}
			Log.i("mobile number",mobileNo+" Length "+mobileNo.trim().length()+"1 letter"+mobileNo.trim().substring(0,1));
			Log.i("mobile verify flag",flg+"");
			
			return flg;
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
				idCd="NA";
				e.printStackTrace();
			}
			 Log.i("idCd for "+eT_ID+" :", idCd);
			return idCd;
		}
	 
	 public String getIDCode2(String eT_ID) {
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
	 public String getIDCode3(String eT_ID) {
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
	 public String getIDCode4(String eT_ID) {
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
	
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	/*protected Dialog onCreateDialog(int id) {
		return new DatePickerDialog(this, datePickerListener, year, month, day);
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			witness_dob.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
					+ selectedYear);
		}
	};*/
	
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
			
	    	toastView.setBackgroundResource(R.drawable.toast_background);
		    toast.show();
		}
	
	
	@Override
	public void onBackPressed() {
		
	}

}