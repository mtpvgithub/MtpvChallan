package com.mtpv.LawnOrder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.R.drawable;
import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.button.R;
import com.leopard.api.FPS;
import com.leopard.api.FpsConfig;

@SuppressLint({ "SimpleDateFormat", "InflateParams" })
public class OffenderActivity extends Activity implements OnClickListener {

	final int PROGRESS_DIALOG = 1;
	public static byte[] bufvalue1, bufvalue2, bufvalue3, bufvalue4;
	@SuppressWarnings("unused")
	private ListView mainListView = null;
	private mItems_list[] sections = null;
	@SuppressWarnings("unused")
	private ArrayAdapter<mItems_list> listAdapter = null;
    
	public static String NAMESPACE = "http://service.mother.com";
	
	static ArrayList<String> Ditenditems = new ArrayList<String>(); // = null;
	static LinearLayout detendLinearLayout;
	static LayoutInflater layoutInflater;
	static View addView;
	String Textviewitems = null;
	public static String challanGenresp = "";
	@SuppressWarnings("unused")
	private static List<String> checkedList = new ArrayList<String>();
	static Map<String, String> idMap = null;
	@SuppressWarnings("unused")
	private static Map<String, String> secMap = null;
	private Calendar cal;
	@SuppressWarnings("unused")
	private int day;
	@SuppressWarnings("unused")
	private int month;
	@SuppressWarnings("unused")
	private int year;
	private static String SelPicId = "1", verified_status = null,
			otp_no = null, otp_input_no = null;
	public static String RESP_Gender = null, RESP_Gender2 = null,
			RESP_Gender3 = null, RESP_Gender4 = null;
	public static StringBuffer previewrespondentBuf = new StringBuffer();
	public static StringBuffer seizedItems = new StringBuffer();
	public static StringBuffer secBuffer = new StringBuffer();
	public static StringBuffer secBufferPreivew = new StringBuffer();
	public static byte[] seizedImageInbyteArray = null;
	public static byte[] image1ByteArray = null;
	public static byte[] image2ByteArray = null;
	public static byte[] image3ByteArray = null;
	public static byte[] image4ByteArray = null;
	public static byte[] respImage1ByteArray = null;
	public static byte[] respImage2ByteArray = null;

	int checkedId;

	public static byte[] leaserImage1ByteArray = null;
	public static byte[] managerImage2ByteArray = null;

	public static String id1_Spinner = null;
	public static String id2_Spinner = null;
	public static String id3_Spinner = null;
	public static String id4_Spinner = null;

	public static String id_Spinner_Leaser = null;
	public static String id_Spinner_Manager = null;

	public static Map<String, String> get_detailsmap1 = new HashMap<String, String>();
	public static Map<String, String> get_detailsmap2 = new HashMap<String, String>();
	public static Map<String, String> get_detailsmap3 = new HashMap<String, String>();
	public static Map<String, String> get_detailsmap4 = new HashMap<String, String>();

	public static boolean get_detailsFlg1 = false, get_detailsFlg2 = false,
			get_detailsFlg3 = false, get_detailsFlg4 = false,
			get_LeaserdetailsFlg = false, get_ManagerdetailsFlg = false;
	public static boolean off_ageFlg1 = false, off_ageFlg2 = false,
			off_ageFlg3 = false, off_ageFlg4 = false;
	public static boolean mob_noFlg1 = false, mob_noFlg2 = false,
			mob_noFlg3 = false, mob_noFlg4 = false;

	public static boolean otpFLG = false;

	public static boolean leaserFLG = false;
	public static boolean managerFLG = false;

	public static boolean layoutFlg2 = false, layoutFlg3 = false,
			layoutFlg4 = false;

	public static String selectedID = "";
	public static String selectedID2 = "";
	public static String selectedID3 = "";
	public static String selectedID4 = "";

	public static String selectedLeaserID = "";
	public static String selectedManagerID = "";
	@SuppressWarnings("unused")
	private Uri fileUri;
	public static TextView group1_text, group2_text, group3_text, group4_text;
	public static final String captFing_txt1 = "fps1", captFing_txt2 = "fps2",
			captFing_txt3 = "fps3", captFing_txt4 = "fps4";
	SharedPreferences sharedPreferences;
	public static final String MYPREFERENCES = "MyPrefs";

	protected static final String TAG = "RadioGroupActivity";
	protected static final int SELECT_FILE = 0;
	public static String SOAP_ACTION_up = NAMESPACE + "generatePettyCase",
			Opdata_Chalana, spot_final_res_status;

	public static String SOAP_ACTION = NAMESPACE;
	public static String SOAP_ACTION_OTP = NAMESPACE + "sendOTP", OtpStatus;

	public static String SOAP_ACTION_VERIFYOTP = NAMESPACE + "verifyOTP",
			VerifyOtpStatus;

	public static String SOAP_ACTION_ID = NAMESPACE + "getDetailsByAADHAR",
			challanResponse;
	@SuppressWarnings("unused")
	private static final String TAG2 = "FPSAPI";
	@SuppressWarnings("unused")
	private Button btn_capturefinger, btn_Verify, btn_Imagecompressed,
			btn_ImageUncompressed;
	FPS fps;
	@SuppressWarnings("unused")
	private LinearLayout llImage;
	OutputStream outputStream;
	InputStream inputstream;
	Context context = this;
	static ProgressDialog dlgCustom, dlgpd;
	FpsConfig fpsconfig = new FpsConfig();
	@SuppressWarnings("unused")
	private byte[] bufvalue = {};
	@SuppressWarnings("unused")
	private byte[] image = {};
	@SuppressWarnings("unused")
	private byte[] image1 = {};
	@SuppressWarnings("unused")
	private int iRetVal;
	public static final int DEVICE_NOTCONNECTED = -10;
	@SuppressWarnings("unused")
	private static final int MESSAGE_BOX = 1;
	public static StringBuffer seizedBuff = new StringBuffer();
	@SuppressWarnings("unused")
	private boolean verifyfinger = false;
	@SuppressWarnings("unused")
	private boolean verifycompressed = false;
	@SuppressWarnings("unused")
	private boolean verifyuncompressed = false;
	@SuppressWarnings("unused")
	private ImageView imgCapture;

	public static String ET_gender;
	public static String ET_Nationality;

	Spinner id_proof_options, off_id_options, off_qualification,
			off_occupation, off_id_options2, off_id_options3, off_id_options4;
	CheckBox checkBox1;
	ListView mylist;
	List<String> list;
	public static LinearLayout list_layout_loc, list_layout_landmark,
			list_layout_date, list_layout_off_name, list_layout_off_name2,
			list_layout_father, list_layout_dob, list_layout_gender,
			list_layout_mobile, list_layout_alt_phn, list_layout_addrss,
			id_proof_yes, respondent_1, list_layout_email,
			list_layout_occupation, get_the_details, list_layout_qualification,
			list_layout_internatnl, list_layout_passport,
			list_layout_nationality, list_layout_nationality2,
			list_layout_nationality3, list_layout_nationality4,
			list_layout_internatnl2, list_layout_internatnl3,
			list_layout_internatnl4, list_layout_passport2,
			list_layout_passport3, list_layout_passport4, list_layout_shop_tin,
			list_layout_shop, wt_address_layout, off_address_layout,
			list_off_alt_id, list_layout_age, respondent_2, respondent_3,
			respondent_4, get_the_details2, get_the_details3, get_the_details4,
			list_layout_shopOwner, fulllayout, addleaser_layout,
			addmanager_layLayout;

	public static EditText respondent_id_details, respondent_id_details2,
			respondent_id_details3, respondent_id_details4, id_loc,
			id_landmark, id_off_name, off_fname, id_passport_no,
			id_passport_no2, id_passport_no3, id_passport_no4, off_age,
			off_Pnum, off_alt_num, off_landline_num, off_address,
			id_int_drive_lic, id_int_drive_lic2, id_int_drive_lic3,
			id_int_drive_lic4, off_house_no, off_street_no, off_village,
			off_mandal, off_district, off_state, off_pincode, off_email,
			Itemname_ET, qty_ET, amt_ET, datepicker_Btn, shop_name, tin_no,
			id_off_name2, id_off_name3, id_off_name4, off_fname2, off_fname3,
			off_fname4, off_age2, off_age3, off_age4, off_Pnum2, off_Pnum3,
			off_Pnum4, off_address2, off_address3, off_address4,
			shop_Ownername;

	public static ImageView details, geo_details, details2, details3, details4,
			edit_btn_loc, edit_btn_landmark, edit_btn_gender, edit_btn_altnum,
			edit_btn_landnum, edit_btn_addrs, edit_btn_shop, edit_btn_tin,
			edit_btn_off_name, edit_btn_fname, edit_btn_dob, otp_btn_num,
			edit_btn_address,

			edit_btn_off_name2, edit_btn_fname2, edit_btn_age2, otp_btn_num2,
			edit_btn_address2,

			edit_btn_off_name3, edit_btn_fname3, edit_btn_age3, otp_btn_num3,
			edit_btn_address3,

			edit_btn_off_name4, edit_btn_fname4, edit_btn_age4, otp_btn_num4,
			edit_btn_address4, cpture_tick1, cpture_tick2, cpture_tick3,
			cpture_tick4,

			addresp_tick, add_sections_tick, add_witness_tick, add_siezed_tick,
			edit_btn_email, next_btn, back_btn, submit_btn, picture1, picture2,
			picture3, edit_bpicture1tn_int_drv_lic, edit_btn_pssprt_num,
			capture_fpt, capture_fpt2, capture_fpt3, capture_fpt4,
			respondent_img1, respondent_img2, respondent_img3, respondent_img4,
			edit_btn_shopOwner;

	LinearLayout capture_layout1, capture_layout2, capture_layout3,
			capture_layout4;

	RadioGroup id_first, id_secnd, gender_group, nationality, nationality2,
			nationality3, nationality4, select_type, gender_group2,
			gender_group3, gender_group4, idproof_options, idproof_options2, idproof_options3, idproof_options4;

	public static RadioButton id_aadhaar, id_license, id_pan, id_RC, id_voter,
			id_ration, male, female, others, others2, others3, others4, indian,
			indian2, indian3, indian4, person_based, shop_based, resp_male,
			resp_female, resp_other, resp_male2, resp_male3, resp_male4,
			resp_female2, resp_female3, resp_female4, resp_other2, resp_,
			resp_other3, resp_other4, idproof_yes, idproof_no, idproof_yes2,
			idproof_no2, idproof_yes3,idproof_no3,idproof_yes4,idproof_no4;

	Button add_Btn, witness_details, add_idProof, remove_idProof2,
			remove_idProof3, remove_idProof4, add_sections_btn, siezed_items;

	public static TextView id_date;
	public static String Current_Date;

	int clickcount = 0;

	GPSTracker gps;
	public static String latitude = "0", longitude = "0";
	public static String date;

	// LEASER DETAILS
	public static LinearLayout leaser_layout, leaser_details_id_layout;
	public static Spinner leaser_id_options;
	public static EditText leaser_id_details;
	static EditText leaser_name;
	public static EditText leaser_fname;
	public static EditText leaser_age;
	public static EditText leaser_mobileNo;
	public static EditText leaser_address;
	public static EditText leaser_city;
	public static ImageView get_leaser_details_btn, leaser_img;
	public static RadioGroup leaser_gender_group;
	public static RadioButton leaser_male, leaser_female, leaser_other;

	// MANAGER DETAILS
	public static LinearLayout manager_layout, manager_details_id_layout;
	public static Spinner manager_id_options;
	public static EditText manager_id_details, manager_name, manager_fname,
			manager_age, manager_mobileNo, manager_address, manager_city;
	public static ImageView get_manager_details_btn, manager_img, manager_tick,
			leaser_tick;
	public static RadioGroup manager_gender_group;
	public static RadioButton manager_male, manager_female, manager_other;
	public static Button add_manager, add_leaser;

	boolean btnFLG = false;
	boolean btnFLG2 = false;

	public static StringBuffer preview_LeaserBuf = new StringBuffer();
	public static StringBuffer preview_ManagerBuf = new StringBuffer();

	public static TextView leaser_gender_txt, manager_gender_txt;

	@SuppressWarnings({ "unused" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_offender);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		gps = new GPSTracker(OffenderActivity.this);
		sharedPreferences = getSharedPreferences(MYPREFERENCES,
				Context.MODE_PRIVATE);

		cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);

		gps = new GPSTracker(OffenderActivity.this);

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
		} else {
			showGPSDisabledAlertToUser();
		}

		id_date = (TextView) findViewById(R.id.id_date);

		Calendar c1 = Calendar.getInstance();
		int mSec = c1.get(Calendar.MILLISECOND);
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String strdate1 = sdf1.format(c1.getTime());
		date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		id_date.setText(strdate1);
		Current_Date = strdate1;

		/******************************* UI COMPONENTS DECLARATION *****************************************/

		shop_based = (RadioButton) findViewById(R.id.shop_based);
		person_based = (RadioButton) findViewById(R.id.person_based);

		witness_details = (Button) findViewById(R.id.witness_details);

		capture_layout1 = (LinearLayout) findViewById(R.id.capture_layout1);
		capture_layout2 = (LinearLayout) findViewById(R.id.capture_layout2);
		capture_layout3 = (LinearLayout) findViewById(R.id.capture_layout3);
		capture_layout4 = (LinearLayout) findViewById(R.id.capture_layout4);

		capture_layout1.setVisibility(View.GONE);
		capture_layout2.setVisibility(View.GONE);
		capture_layout3.setVisibility(View.GONE);
		capture_layout4.setVisibility(View.GONE);

		details = (ImageView) findViewById(R.id.details);
		details2 = (ImageView) findViewById(R.id.details2);
		details3 = (ImageView) findViewById(R.id.details3);
		details4 = (ImageView) findViewById(R.id.details4);

		/* geo_details = (ImageView)findViewById(R.id.geo_details); */

		list_layout_landmark = (LinearLayout) findViewById(R.id.list_layout_landmark);
		list_layout_off_name = (LinearLayout) findViewById(R.id.list_layout_off_name);
		list_layout_off_name2 = (LinearLayout) findViewById(R.id.list_layout_off_name2);
		list_layout_father = (LinearLayout) findViewById(R.id.list_layout_father);
		list_layout_gender = (LinearLayout) findViewById(R.id.list_layout_gender);
		list_layout_mobile = (LinearLayout) findViewById(R.id.list_layout_mobile);
		list_layout_shop_tin = (LinearLayout) findViewById(R.id.list_layout_shop_tin);
		list_layout_shop = (LinearLayout) findViewById(R.id.list_layout_shop);

		list_layout_internatnl = (LinearLayout) findViewById(R.id.list_layout_internatnl);
		list_layout_passport = (LinearLayout) findViewById(R.id.list_layout_passport);
		list_layout_nationality = (LinearLayout) findViewById(R.id.list_layout_nationality);
		list_layout_age = (LinearLayout) findViewById(R.id.list_layout_age);
		off_address_layout = (LinearLayout) findViewById(R.id.off_address_layout);
		detendLinearLayout = (LinearLayout) findViewById(R.id.detendL);

		back_btn = (ImageView) findViewById(R.id.back_btn);

		submit_btn = (ImageView) findViewById(R.id.submit_btn);
		off_id_options = (Spinner) findViewById(R.id.off_id_options);

		addleaser_layout = (LinearLayout) findViewById(R.id.addleaser_layout);
		addmanager_layLayout = (LinearLayout) findViewById(R.id.addmanager_layout);
		addleaser_layout.setVisibility(View.GONE);

		others = (RadioButton) findViewById(R.id.others);
		indian = (RadioButton) findViewById(R.id.indian);
		nationality = (RadioGroup) findViewById(R.id.nationality);

		picture1 = (ImageView) findViewById(R.id.picture1);
		picture2 = (ImageView) findViewById(R.id.picture2);

		get_the_details = (LinearLayout) findViewById(R.id.get_the_details);
		get_the_details2 = (LinearLayout) findViewById(R.id.get_the_details2);
		get_the_details3 = (LinearLayout) findViewById(R.id.get_the_details3);
		get_the_details4 = (LinearLayout) findViewById(R.id.get_the_details4);

		capture_fpt = (ImageView) findViewById(R.id.capture_fpt);
		capture_fpt2 = (ImageView) findViewById(R.id.capture_fpt2);
		capture_fpt3 = (ImageView) findViewById(R.id.capture_fpt3);
		capture_fpt4 = (ImageView) findViewById(R.id.capture_fpt4);

		// **LEASER DETAILS**//*
		leaser_layout = (LinearLayout) findViewById(R.id.leaser_layout);
		leaser_details_id_layout = (LinearLayout) findViewById(R.id.leaser_details_id_layout);

		leaser_id_options = (Spinner) findViewById(R.id.leaser_id_options);

		leaser_id_details = (EditText) findViewById(R.id.leaser_id_details);
		leaser_name = (EditText) findViewById(R.id.leaser_name);
		leaser_fname = (EditText) findViewById(R.id.leaser_fname);
		leaser_age = (EditText) findViewById(R.id.leaser_age);
		leaser_mobileNo = (EditText) findViewById(R.id.leaser_mobileNo);
		leaser_address = (EditText) findViewById(R.id.leaser_address);
		leaser_city = (EditText) findViewById(R.id.leaser_city);

		get_leaser_details_btn = (ImageView) findViewById(R.id.get_leaser_details_btn);
		leaser_img = (ImageView) findViewById(R.id.leaser_img);

		leaser_gender_group = (RadioGroup) findViewById(R.id.leaser_gender_group);
		leaser_male = (RadioButton) findViewById(R.id.leaser_male);
		leaser_female = (RadioButton) findViewById(R.id.leaser_female);
		leaser_other = (RadioButton) findViewById(R.id.leaser_other);

		add_leaser = (Button) findViewById(R.id.add_leaser);
		leaser_tick = (ImageView) findViewById(R.id.leaser_tick);

		add_leaser.setVisibility(View.GONE);
		leaser_tick.setVisibility(View.GONE);

		// **MANAGER DETAILS**//
		manager_layout = (LinearLayout) findViewById(R.id.manager_layout);
		manager_details_id_layout = (LinearLayout) findViewById(R.id.manager_details_id_layout);

		manager_id_options = (Spinner) findViewById(R.id.manager_id_options);

		manager_id_details = (EditText) findViewById(R.id.manager_id_details);
		manager_name = (EditText) findViewById(R.id.manager_name);
		manager_fname = (EditText) findViewById(R.id.manager_fname);
		manager_age = (EditText) findViewById(R.id.manager_age);
		manager_mobileNo = (EditText) findViewById(R.id.manager_mobileNo);
		manager_address = (EditText) findViewById(R.id.manager_address);
		manager_city = (EditText) findViewById(R.id.manager_city);

		get_manager_details_btn = (ImageView) findViewById(R.id.get_manager_details_btn);
		manager_img = (ImageView) findViewById(R.id.manager_img);

		manager_gender_group = (RadioGroup) findViewById(R.id.manager_gender_group);
		manager_male = (RadioButton) findViewById(R.id.manager_male);
		manager_female = (RadioButton) findViewById(R.id.manager_female);
		manager_other = (RadioButton) findViewById(R.id.manager_other);

		add_manager = (Button) findViewById(R.id.add_manager);
		manager_tick = (ImageView) findViewById(R.id.manager_tick);

		add_manager.setVisibility(View.GONE);
		manager_tick.setVisibility(View.GONE);

		/**** ID PROOF RADIO BUTTONS *****/
		idproof_options = (RadioGroup) findViewById(R.id.idproof_options);
		idproof_options2 = (RadioGroup) findViewById(R.id.idproof_options2);
		idproof_options3 = (RadioGroup) findViewById(R.id.idproof_options3);
		idproof_options4 = (RadioGroup) findViewById(R.id.idproof_options4);
		idproof_yes = (RadioButton) findViewById(R.id.idproof_yes);
		idproof_no = (RadioButton) findViewById(R.id.idproof_no);
		idproof_yes2 = (RadioButton) findViewById(R.id.idproof_yes2);
		idproof_no2 = (RadioButton) findViewById(R.id.idproof_no2);
		idproof_yes3 = (RadioButton) findViewById(R.id.idproof_yes3);
		idproof_no3 = (RadioButton) findViewById(R.id.idproof_no3);
		idproof_yes4 = (RadioButton) findViewById(R.id.idproof_yes4);
		idproof_no4 = (RadioButton) findViewById(R.id.idproof_no4);

		list_layout_shopOwner = (LinearLayout) findViewById(R.id.list_layout_shopOwner);
		edit_btn_shopOwner = (ImageView) findViewById(R.id.edit_btn_shopOwner);
		shop_Ownername = (EditText) findViewById(R.id.shop_Ownername);
		respondent_1 = (LinearLayout) findViewById(R.id.respondent_1);

		respondent_id_details = (EditText) findViewById(R.id.respondent_id_details);
		shop_name = (EditText) findViewById(R.id.shop_name);
		tin_no = (EditText) findViewById(R.id.tin_no);
		gender_group = (RadioGroup) findViewById(R.id.gender_group);
		id_date = (TextView) findViewById(R.id.id_date);
		id_off_name = (EditText) findViewById(R.id.id_off_name);
		off_address = (EditText) findViewById(R.id.off_address);
		off_Pnum = (EditText) findViewById(R.id.off_Pnum);
		id_landmark = (EditText) findViewById(R.id.id_landmark);
		id_passport_no = (EditText) findViewById(R.id.id_passport_no);
		id_int_drive_lic = (EditText) findViewById(R.id.id_int_drive_lic);
		id_int_drive_lic = (EditText) findViewById(R.id.id_int_drive_lic);
		id_landmark = (EditText) findViewById(R.id.id_landmark);
		id_off_name = (EditText) findViewById(R.id.id_off_name);
		id_passport_no = (EditText) findViewById(R.id.id_passport_no);
		off_fname = (EditText) findViewById(R.id.off_fname);
		off_Pnum = (EditText) findViewById(R.id.off_Pnum);
		off_age = (EditText) findViewById(R.id.off_age);
		Itemname_ET = (EditText) findViewById(R.id.Itemname_ET);
		qty_ET = (EditText) findViewById(R.id.qty_ET);

		nationality = (RadioGroup) findViewById(R.id.nationality);

		respondent_2 = (LinearLayout) findViewById(R.id.respondent_2);
		respondent_3 = (LinearLayout) findViewById(R.id.respondent_3);
		respondent_4 = (LinearLayout) findViewById(R.id.respondent_4);

		edit_btn_off_name = (ImageView) findViewById(R.id.edit_btn_off_name);
		edit_btn_fname = (ImageView) findViewById(R.id.edit_btn_fname);
		edit_btn_dob = (ImageView) findViewById(R.id.edit_btn_dob);
		otp_btn_num = (ImageView) findViewById(R.id.otp_btn);
		edit_btn_address = (ImageView) findViewById(R.id.edit_btn_address);
		respondent_img1 = (ImageView) findViewById(R.id.respondent_img1);

		/******************** Offender Details 2 ************************/

		respondent_img2 = (ImageView) findViewById(R.id.respondent_img2);
		off_id_options2 = (Spinner) findViewById(R.id.off_id_options2);

		respondent_id_details2 = (EditText) findViewById(R.id.respondent_id_details2);
		id_off_name2 = (EditText) findViewById(R.id.id_off_name2);
		off_fname2 = (EditText) findViewById(R.id.off_fname2);
		off_age2 = (EditText) findViewById(R.id.off_age2);
		off_Pnum2 = (EditText) findViewById(R.id.off_Pnum2);
		off_address2 = (EditText) findViewById(R.id.off_address2);
		id_int_drive_lic2 = (EditText) findViewById(R.id.id_int_drive_lic2);

		edit_btn_off_name2 = (ImageView) findViewById(R.id.edit_btn_off_name2);
		edit_btn_fname2 = (ImageView) findViewById(R.id.edit_btn_fname2);
		edit_btn_age2 = (ImageView) findViewById(R.id.edit_btn_age2);
		otp_btn_num2 = (ImageView) findViewById(R.id.otp_btn2);
		edit_btn_address2 = (ImageView) findViewById(R.id.edit_btn_address2);

		list_layout_nationality2 = (LinearLayout) findViewById(R.id.list_layout_nationality2);
		list_layout_internatnl2 = (LinearLayout) findViewById(R.id.list_layout_internatnl2);
		list_layout_passport2 = (LinearLayout) findViewById(R.id.list_layout_passport2);

		id_passport_no2 = (EditText) findViewById(R.id.id_passport_no2);
		id_int_drive_lic2 = (EditText) findViewById(R.id.id_int_drive_lic2);

		nationality2 = (RadioGroup) findViewById(R.id.nationality2);
		others2 = (RadioButton) findViewById(R.id.others2);
		indian2 = (RadioButton) findViewById(R.id.indian2);

		/******************** Offender Details 2 ************************/

		/******************** Offender Details 3 ************************/

		respondent_img3 = (ImageView) findViewById(R.id.respondent_img3);
		off_id_options3 = (Spinner) findViewById(R.id.off_id_options3);

		respondent_id_details3 = (EditText) findViewById(R.id.respondent_id_details3);
		id_off_name3 = (EditText) findViewById(R.id.id_off_name3);
		off_fname3 = (EditText) findViewById(R.id.off_fname3);
		off_age3 = (EditText) findViewById(R.id.off_age3);
		off_Pnum3 = (EditText) findViewById(R.id.off_Pnum3);
		off_address3 = (EditText) findViewById(R.id.off_address3);
		id_int_drive_lic3 = (EditText) findViewById(R.id.id_int_drive_lic3);
		id_passport_no3 = (EditText) findViewById(R.id.id_passport_no3);

		edit_btn_off_name3 = (ImageView) findViewById(R.id.edit_btn_off_name3);
		edit_btn_fname3 = (ImageView) findViewById(R.id.edit_btn_fname3);
		edit_btn_age3 = (ImageView) findViewById(R.id.edit_btn_age3);
		otp_btn_num3 = (ImageView) findViewById(R.id.otp_btn3);
		edit_btn_address3 = (ImageView) findViewById(R.id.edit_btn_address3);

		list_layout_nationality3 = (LinearLayout) findViewById(R.id.list_layout_nationality3);
		list_layout_internatnl3 = (LinearLayout) findViewById(R.id.list_layout_internatnl3);
		list_layout_passport3 = (LinearLayout) findViewById(R.id.list_layout_passport3);

		nationality3 = (RadioGroup) findViewById(R.id.nationality3);
		others3 = (RadioButton) findViewById(R.id.others3);
		indian3 = (RadioButton) findViewById(R.id.indian3);

		/******************** Offender Details 3 ************************/

		/******************** Offender Details 4 ************************/

		respondent_img4 = (ImageView) findViewById(R.id.respondent_img4);

		off_id_options4 = (Spinner) findViewById(R.id.off_id_options4);

		respondent_id_details4 = (EditText) findViewById(R.id.respondent_id_details4);
		id_off_name4 = (EditText) findViewById(R.id.id_off_name4);
		off_fname4 = (EditText) findViewById(R.id.off_fname4);
		off_age4 = (EditText) findViewById(R.id.off_age4);
		off_Pnum4 = (EditText) findViewById(R.id.off_Pnum4);
		off_address4 = (EditText) findViewById(R.id.off_address4);
		id_int_drive_lic4 = (EditText) findViewById(R.id.id_int_drive_lic4);
		id_passport_no4 = (EditText) findViewById(R.id.id_passport_no4);

		edit_btn_off_name4 = (ImageView) findViewById(R.id.edit_btn_off_name4);
		edit_btn_fname4 = (ImageView) findViewById(R.id.edit_btn_fname4);
		edit_btn_age4 = (ImageView) findViewById(R.id.edit_btn_age4);
		otp_btn_num4 = (ImageView) findViewById(R.id.otp_btn4);
		edit_btn_address4 = (ImageView) findViewById(R.id.edit_btn_address4);

		list_layout_nationality4 = (LinearLayout) findViewById(R.id.list_layout_nationality4);
		list_layout_internatnl4 = (LinearLayout) findViewById(R.id.list_layout_internatnl4);
		list_layout_passport4 = (LinearLayout) findViewById(R.id.list_layout_passport4);

		nationality4 = (RadioGroup) findViewById(R.id.nationality4);
		others4 = (RadioButton) findViewById(R.id.others4);
		indian4 = (RadioButton) findViewById(R.id.indian4);

		/******************** Offender Details 4 ************************/

		addresp_tick = (ImageView) findViewById(R.id.addresp_tick);
		add_sections_tick = (ImageView) findViewById(R.id.add_sections_tick);
		add_witness_tick = (ImageView) findViewById(R.id.add_witness_tick);
		add_siezed_tick = (ImageView) findViewById(R.id.add_siezed_tick);

		cpture_tick1 = (ImageView) findViewById(R.id.cpture_tick1);
		cpture_tick2 = (ImageView) findViewById(R.id.cpture_tick2);
		cpture_tick3 = (ImageView) findViewById(R.id.cpture_tick3);
		cpture_tick4 = (ImageView) findViewById(R.id.cpture_tick4);

		siezed_items = (Button) findViewById(R.id.siezed_items);

		add_sections_btn = (Button) findViewById(R.id.add_sections_btn);
		add_idProof = (Button) findViewById(R.id.add_idProof);
		add_idProof.setVisibility(View.VISIBLE);

		remove_idProof2 = (Button) findViewById(R.id.remove_idProof2);
		remove_idProof3 = (Button) findViewById(R.id.remove_idProof3);
		remove_idProof4 = (Button) findViewById(R.id.remove_idProof4);
		group1_text = (TextView) findViewById(R.id.group1_text);
		resp_male = (RadioButton) findViewById(R.id.resp_male);
		resp_female = (RadioButton) findViewById(R.id.resp_female);
		resp_other = (RadioButton) findViewById(R.id.resp_other);

		group2_text = (TextView) findViewById(R.id.group2_text);
		gender_group2 = (RadioGroup) findViewById(R.id.gender_group2);
		resp_male2 = (RadioButton) findViewById(R.id.resp_male2);
		resp_female2 = (RadioButton) findViewById(R.id.resp_female2);
		resp_other2 = (RadioButton) findViewById(R.id.resp_other2);

		group3_text = (TextView) findViewById(R.id.group3_text);
		gender_group3 = (RadioGroup) findViewById(R.id.gender_group3);
		resp_male3 = (RadioButton) findViewById(R.id.resp_male3);
		resp_female3 = (RadioButton) findViewById(R.id.resp_female3);
		resp_other3 = (RadioButton) findViewById(R.id.resp_other3);

		group4_text = (TextView) findViewById(R.id.group4_text);
		gender_group4 = (RadioGroup) findViewById(R.id.gender_group4);
		resp_male4 = (RadioButton) findViewById(R.id.resp_male4);
		resp_female4 = (RadioButton) findViewById(R.id.resp_female4);
		resp_other4 = (RadioButton) findViewById(R.id.resp_other4);

		select_type = (RadioGroup) findViewById(R.id.select_type);

		get_the_details.setVisibility(View.VISIBLE);
		respondent_1.setVisibility(View.VISIBLE);
		add_idProof.setVisibility(View.VISIBLE);
		respondent_2.setVisibility(View.GONE);
		respondent_3.setVisibility(View.GONE);
		respondent_4.setVisibility(View.GONE);
		leaser_layout.setVisibility(View.GONE);
		manager_layout.setVisibility(View.GONE);
		add_manager.setVisibility(View.GONE);
		add_leaser.setVisibility(View.GONE);
		list_layout_shop.setVisibility(View.GONE);
		list_layout_shop_tin.setVisibility(View.GONE);
		list_layout_shopOwner.setVisibility(View.GONE);
		addleaser_layout.setVisibility(View.GONE);
		addmanager_layLayout.setVisibility(View.GONE);

		Geocoder geocoder = new Geocoder(getBaseContext(), Locale.ENGLISH);
		try {
			Log.i("geocoder :::::::", "****geocoder****");

			List<Address> addresses = geocoder.getFromLocation(
					Double.parseDouble(latitude),
					Double.parseDouble(longitude), 1);
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
				id_landmark.setText(strReturnedAddress.toString());
			} else {
				id_landmark.setText("");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		idproof_yes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickcount = 0;
				respondent_id_details.setVisibility(View.VISIBLE);
				get_the_details.setVisibility(View.VISIBLE);
				list_layout_off_name.setVisibility(View.VISIBLE);
				list_layout_father.setVisibility(View.VISIBLE);
				list_layout_gender.setVisibility(View.VISIBLE);
				list_layout_mobile.setVisibility(View.VISIBLE);
				// list_layout_loc.setVisibility(View.VISIBLE);
				list_layout_age.setVisibility(View.VISIBLE);
				list_layout_landmark.setVisibility(View.VISIBLE);
				off_address_layout.setVisibility(View.VISIBLE);
				list_layout_internatnl.setVisibility(View.GONE);
				list_layout_passport.setVisibility(View.GONE);
				list_layout_nationality.setVisibility(View.VISIBLE);
				capture_fpt.setVisibility(View.VISIBLE);
				respondent_img1.setVisibility(View.VISIBLE);
				
				add_leaser.setText("Add Leaser");
				add_manager.setText("Add manager");
				
				 /*respondent_2.setVisibility(View.GONE);
				 respondent_3.setVisibility(View.GONE);
				 respondent_4.setVisibility(View.GONE);*/
				
				leaser_layout.setVisibility(View.GONE);
				manager_layout.setVisibility(View.GONE);
				indian.setChecked(true);

				if (person_based.isChecked() && clickcount == 0) {

					add_leaser.setVisibility(View.GONE);
					add_manager.setVisibility(View.GONE);
					addleaser_layout.setVisibility(View.GONE);
					addmanager_layLayout.setVisibility(View.GONE);
					add_idProof.setVisibility(View.VISIBLE);

				} else if (shop_based.isChecked() && clickcount == 0) {

					add_leaser.setVisibility(View.VISIBLE);
					add_manager.setVisibility(View.VISIBLE);
				}

				respondent_id_details.setText("");

				shop_name.setText("");
				shop_Ownername.setText("");
				tin_no.setText("");

				off_address.setText("");
				off_Pnum.setText("");
				off_age.setText("");
				off_fname.setText("");
				id_off_name.setText("");
				off_Pnum.setText("");
				
				
				shop_name.setEnabled(true);
				shop_Ownername.setEnabled(true);
				tin_no.setEnabled(true);
				off_address.setEnabled(true);
				off_Pnum.setEnabled(true);
				off_age.setEnabled(true);
				off_fname.setEnabled(true);
				id_off_name.setEnabled(true);
				off_Pnum.setEnabled(true);
				
				
				respondent_img1.setImageResource(R.drawable.photo);

				/*
				 * off_address2.setText(""); off_Pnum2.setText("");
				 * off_age2.setText(""); off_fname2.setText("");
				 * id_off_name2.setText(""); off_Pnum2.setText("");
				 * respondent_img2.setImageResource(R.drawable.photo);
				 * 
				 * off_address3.setText(""); off_Pnum3.setText("");
				 * off_age3.setText(""); off_fname3.setText("");
				 * id_off_name3.setText(""); off_Pnum3.setText("");
				 * respondent_img3.setImageResource(R.drawable.photo);
				 * 
				 * off_address4.setText(""); off_Pnum4.setText("");
				 * off_age4.setText(""); off_fname4.setText("");
				 * id_off_name4.setText(""); off_Pnum4.setText("");
				 * respondent_img4.setImageResource(R.drawable.photo);
				 */

			}
		});
		idproof_yes2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickcount = 0;
				get_the_details2.setVisibility(View.VISIBLE);
				respondent_1.setVisibility(View.VISIBLE);
				respondent_2.setVisibility(View.VISIBLE);
				/*respondent_3.setVisibility(View.GONE);
				respondent_4.setVisibility(View.GONE);*/
				leaser_layout.setVisibility(View.GONE);
				manager_layout.setVisibility(View.GONE);

				if (person_based.isChecked()) {
					get_the_details2.setVisibility(View.VISIBLE);
					respondent_2.setVisibility(View.VISIBLE);
					indian.setChecked(true);
				}
				off_address2.setText("");
				off_Pnum2.setText("");
				off_age2.setText("");
				off_fname2.setText("");
				id_off_name2.setText("");
				off_Pnum2.setText("");
				respondent_img2.setImageResource(R.drawable.photo);

			}
		});
		idproof_no2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickcount = 0;
				get_the_details2.setVisibility(View.GONE);
				respondent_2.setVisibility(View.VISIBLE);
				indian.setChecked(true);
				off_address2.setText("");
				off_Pnum2.setText("");
				off_age2.setText("");
				off_fname2.setText("");
				id_off_name2.setText("");
				off_Pnum2.setText("");
				respondent_img2.setImageResource(R.drawable.photo);

			}
		});

		idproof_no.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickcount = 0;
				get_the_details.setVisibility(View.GONE);
				/*
				 * get_the_details2.setVisibility(View.GONE);
				 * get_the_details3.setVisibility(View.GONE);
				 * get_the_details4.setVisibility(View.GONE);
				 */

				list_layout_off_name.setVisibility(View.VISIBLE);
				list_layout_father.setVisibility(View.VISIBLE);
				list_layout_gender.setVisibility(View.VISIBLE);
				list_layout_mobile.setVisibility(View.VISIBLE);
				// list_layout_loc.setVisibility(View.VISIBLE);
				list_layout_landmark.setVisibility(View.VISIBLE);
				list_layout_age.setVisibility(View.VISIBLE);
				off_address_layout.setVisibility(View.VISIBLE);
				list_layout_internatnl.setVisibility(View.GONE);
				list_layout_passport.setVisibility(View.GONE);
				list_layout_nationality.setVisibility(View.VISIBLE);
				add_idProof.setVisibility(View.VISIBLE);
				capture_fpt.setVisibility(View.VISIBLE);
				respondent_img1.setVisibility(View.VISIBLE);
				/*respondent_2.setVisibility(View.GONE);
				respondent_3.setVisibility(View.GONE);
				respondent_4.setVisibility(View.GONE);*/
				leaser_layout.setVisibility(View.GONE);
				manager_layout.setVisibility(View.GONE);
				indian.setChecked(true);

				/*id_off_name.setFocusable(true);*/
				
				add_leaser.setText("Add Leaser");
				add_manager.setText("Add manager");

				if (person_based.isChecked() && clickcount == 0) {
					add_idProof.setVisibility(View.VISIBLE);
					leaser_layout.setVisibility(View.GONE);
					manager_layout.setVisibility(View.GONE);
					addleaser_layout.setVisibility(View.GONE);
					addmanager_layLayout.setVisibility(View.GONE);

				} else if (shop_based.isChecked() && clickcount == 0) {
					add_idProof.setVisibility(View.GONE);
					addleaser_layout.setVisibility(View.VISIBLE);
					addmanager_layLayout.setVisibility(View.VISIBLE);
				}

				respondent_id_details.setText("");
				shop_name.setText("");
				shop_Ownername.setText("");
				tin_no.setText("");

				off_address.setText("");
				off_Pnum.setText("");
				off_age.setText("");
				off_fname.setText("");
				id_off_name.setText("");
				off_Pnum.setText("");
				respondent_img1.setImageResource(R.drawable.photo);

				/*off_address2.setText("");
				off_Pnum2.setText("");
				off_age2.setText("");
				off_fname2.setText("");
				id_off_name2.setText("");
				off_Pnum2.setText("");
				respondent_img2.setImageResource(R.drawable.photo);

				off_address3.setText("");
				off_Pnum3.setText("");
				off_age3.setText("");
				off_fname3.setText("");
				id_off_name3.setText("");
				off_Pnum3.setText("");
				respondent_img3.setImageResource(R.drawable.photo);

				off_address4.setText("");
				off_Pnum4.setText("");
				off_age4.setText("");
				off_fname4.setText("");
				id_off_name4.setText("");
				off_Pnum4.setText("");
				respondent_img4.setImageResource(R.drawable.photo);*/
			}
		});
		idproof_yes3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickcount = 0;
				get_the_details3.setVisibility(View.VISIBLE);
				respondent_1.setVisibility(View.VISIBLE);
				respondent_2.setVisibility(View.VISIBLE);
				respondent_3.setVisibility(View.VISIBLE);
				/*respondent_4.setVisibility(View.GONE);*/
				leaser_layout.setVisibility(View.GONE);
				manager_layout.setVisibility(View.GONE);
				indian.setChecked(true);

				if (person_based.isChecked() && clickcount == 0) {
					get_the_details3.setVisibility(View.VISIBLE);
					respondent_3.setVisibility(View.VISIBLE);
				}
				off_address3.setText("");
				off_Pnum3.setText("");
				off_age3.setText("");
				off_fname3.setText("");
				id_off_name3.setText("");
				off_Pnum3.setText("");
				respondent_img3.setImageResource(R.drawable.photo);

			}
		});
		idproof_no3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickcount = 0;
				get_the_details3.setVisibility(View.GONE);
				respondent_3.setVisibility(View.VISIBLE);
				indian.setChecked(true);
				off_address3.setText("");
				off_Pnum3.setText("");
				off_age3.setText("");
				off_fname3.setText("");
				id_off_name3.setText("");
				off_Pnum3.setText("");
				respondent_img3.setImageResource(R.drawable.photo);

			}
		});
		
		idproof_yes4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickcount = 0;
				get_the_details4.setVisibility(View.VISIBLE);
				respondent_1.setVisibility(View.VISIBLE);
				respondent_2.setVisibility(View.VISIBLE);
				respondent_3.setVisibility(View.VISIBLE);
				respondent_4.setVisibility(View.VISIBLE);
				leaser_layout.setVisibility(View.GONE);
				manager_layout.setVisibility(View.GONE);
				indian.setChecked(true);
				add_idProof.setVisibility(View.GONE);

				if (person_based.isChecked() && clickcount == 0) {
					get_the_details4.setVisibility(View.VISIBLE);
					respondent_4.setVisibility(View.VISIBLE);
				}
				off_address4.setText("");
				off_Pnum4.setText("");
				off_age4.setText("");
				off_fname4.setText("");
				id_off_name4.setText("");
				off_Pnum4.setText("");
				respondent_img4.setImageResource(R.drawable.photo);

			}
		});
		idproof_no4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickcount = 0;
				get_the_details4.setVisibility(View.GONE);
				respondent_4.setVisibility(View.VISIBLE);
				indian.setChecked(true);
				off_address4.setText("");
				off_Pnum4.setText("");
				off_age4.setText("");
				off_fname4.setText("");
				id_off_name4.setText("");
				off_Pnum4.setText("");
				respondent_img4.setImageResource(R.drawable.photo);
				add_idProof.setVisibility(View.GONE);
			}
		});


		siezed_items.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent seized = new Intent(getApplicationContext(),
						SiezedItemsActivity.class);
				startActivity(seized);
			}
		});

		capture_fpt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.putString("captFing_txt1", "1");
				editor.commit();
				Intent fpt = new Intent(getApplicationContext(),
						FPT_Activity.class);
				startActivity(fpt);
			}
		});

		capture_fpt2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.putString("captFing_txt2", "1");
				editor.commit();
				Intent fpt2 = new Intent(getApplicationContext(),
						FPT2_Activity.class);
				startActivity(fpt2);
			}
		});

		capture_fpt3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.putString("captFing_txt3", "1");
				editor.commit();
				Intent fpt2 = new Intent(getApplicationContext(),
						FPT3_Activity.class);
				startActivity(fpt2);
			}
		});

		capture_fpt4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.putString("captFing_txt4", "1");
				editor.commit();
				Intent fpt2 = new Intent(getApplicationContext(),
						FPT4_Activity.class);
				startActivity(fpt2);
			}
		});

		add_sections_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent add_sections = new Intent(getApplicationContext(),
						Add_SectionsActivity.class);
				startActivity(add_sections);
			}
		});

		add_idProof.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickcount = clickcount + 1;

				if (clickcount > 3) {
					/*
					 * Toast toast = Toast.makeText(getApplicationContext(),
					 * "You Can Add Only Four Respondent's", Toast.LENGTH_LONG);
					 * toast.setGravity(Gravity.CENTER, 0, -200); View toastView
					 * = toast.getView();
					 * toastView.setBackgroundResource(R.drawable
					 * .toast_background); toast.show();
					 */

					showToast("You Can Add Only Four Respondents");

					add_idProof.setVisibility(View.GONE);
				} else {
					if (clickcount == 1) {
						layoutFlg2 = true;
						idproof_yes2.isChecked();
						if (person_based.isChecked() && idproof_yes.isChecked()) {
							Log.i("person_based_idproof_yes:::::::::::", ""
									+ person_based);

							get_the_details2.setVisibility(View.VISIBLE);
							respondent_2.setVisibility(View.VISIBLE);
							add_idProof.setVisibility(View.VISIBLE);

						} 
						if (remove_idProof2.isClickable() && add_idProof.isClickable()) {
							respondent_2.setVisibility(View.VISIBLE);
							
						}
						else if (person_based.isChecked()
								&& idproof_no.isChecked()) {
							Log.i("person_based_idproof_no:::::::::::", ""
									+ person_based);
							respondent_2.setVisibility(View.VISIBLE);
							add_idProof.setVisibility(View.VISIBLE);

							/*get_the_details.setVisibility(View.GONE);
							 get_the_details2.setVisibility(View.GONE); */
						}
						

					}
					if (clickcount == 2) {
						layoutFlg3 = true;
						if (person_based.isChecked() && idproof_yes.isChecked()) {
							Log.i("person_based_idproof_yes:::::::::::", ""
									+ person_based);

							get_the_details3.setVisibility(View.VISIBLE);
							respondent_3.setVisibility(View.VISIBLE);
							add_idProof.setVisibility(View.VISIBLE);

							/*get_the_details.setVisibility(View.VISIBLE);
							get_the_details2.setVisibility(View.VISIBLE);
							get_the_details3.setVisibility(View.VISIBLE);*/
						}
						if (remove_idProof3.isClickable() && add_idProof.isClickable()) {
							respondent_3.setVisibility(View.VISIBLE);
							
						}

						else if (person_based.isChecked()
								&& idproof_no.isChecked()) {
							Log.i("person_based_idproof_no:::::::::::", ""
									+ person_based);
							respondent_3.setVisibility(View.VISIBLE);
							add_idProof.setVisibility(View.VISIBLE);

							/*get_the_details.setVisibility(View.GONE);
							get_the_details2.setVisibility(View.GONE);
							get_the_details3.setVisibility(View.GONE);*/
						}
						 

					}
					if (clickcount == 3) {
						layoutFlg4 = true;
						if (person_based.isChecked() && idproof_yes.isChecked()) {
							Log.i("person_based_idproof_yes:::::::::::", ""
									+ person_based);

							get_the_details4.setVisibility(View.VISIBLE);
							respondent_4.setVisibility(View.VISIBLE);

							/*get_the_details.setVisibility(View.VISIBLE);
							get_the_details2.setVisibility(View.VISIBLE);
							get_the_details3.setVisibility(View.VISIBLE);*/
							get_the_details4.setVisibility(View.VISIBLE);
							add_idProof.setVisibility(View.GONE);
						}

						if (remove_idProof4.isClickable() && add_idProof.isClickable()) {
							respondent_4.setVisibility(View.VISIBLE);
							
							
						}

						else if (person_based.isChecked()
								&& idproof_no.isChecked()) {
							Log.i("person_based_idproof_no:::::::::::", ""
									+ person_based);
							respondent_4.setVisibility(View.VISIBLE);

							/* */
							get_the_details4.setVisibility(View.VISIBLE);
							add_idProof.setVisibility(View.GONE);

						}
						
						

					}
				}

			}
		});

		remove_idProof2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickcount = clickcount - 1;
				respondent_2.setVisibility(View.GONE);
				/*
				 * Toast toast = Toast.makeText(getApplicationContext(),
				 * "ID Removed", Toast.LENGTH_LONG);
				 * toast.setGravity(Gravity.CENTER, 0, -200); View toastView =
				 * toast.getView();
				 * toastView.setBackgroundResource(R.drawable.toast_background);
				 * toast.show();
				 */
				showToast("Respondent Form Removed");
				add_idProof.setVisibility(View.VISIBLE);
				

			}
		});

		remove_idProof3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickcount = clickcount - 1;
				respondent_3.setVisibility(View.GONE);
				/*
				 * Toast toast = Toast.makeText(getApplicationContext(),
				 * "ID Removed", Toast.LENGTH_LONG);
				 * toast.setGravity(Gravity.CENTER, 0, -200); View toastView =
				 * toast.getView();
				 * toastView.setBackgroundResource(R.drawable.toast_background);
				 * toast.show();
				 */
				showToast("Respondent Form Removed");
				add_idProof.setVisibility(View.VISIBLE);

			}
		});

		remove_idProof4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickcount = clickcount - 1;
				respondent_4.setVisibility(View.GONE);
				/*
				 * Toast toast = Toast.makeText(getApplicationContext(),
				 * "ID Removed", Toast.LENGTH_LONG);
				 * toast.setGravity(Gravity.CENTER, 0, -200); View toastView =
				 * toast.getView();
				 * toastView.setBackgroundResource(R.drawable.toast_background);
				 * toast.show();
				 */
				showToast("Respondent Form Removed");
				add_idProof.setVisibility(View.VISIBLE);

			}
		});

		leaser_gender_txt = (TextView) findViewById(R.id.leaser_gender_txt);
		manager_gender_txt = (TextView) findViewById(R.id.manager_gender_txt);

		leaser_gender_group
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					public void onCheckedChanged(RadioGroup arg0, int checkedId) {
						switch (checkedId) {
						case R.id.leaser_male:
							leaser_gender_txt.setText("M");
							// Toast.makeText(getApplicationContext(),
							// "Your Selected Gender is Male",
							// Toast.LENGTH_SHORT).show();
							break;

						case R.id.leaser_female:
							leaser_gender_txt.setText("F");
							// Toast.makeText(getApplicationContext(),
							// "Your Selected Gender is Female",
							// Toast.LENGTH_SHORT).show();
							break;

						case R.id.leaser_other:
							leaser_gender_txt.setText("O");
							// Toast.makeText(getApplicationContext(),
							// "Your Selected Gender is other",
							// Toast.LENGTH_SHORT).show();
							break;
						}
					}
				});

		manager_gender_group
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					public void onCheckedChanged(RadioGroup arg0, int checkedId) {
						switch (checkedId) {
						case R.id.manager_male:
							manager_gender_txt.setText("M");
							// Toast.makeText(getApplicationContext(),
							// "Your Selected Gender is Male",
							// Toast.LENGTH_SHORT).show();
							break;

						case R.id.manager_female:
							manager_gender_txt.setText("F");
							// Toast.makeText(getApplicationContext(),
							// "Your Selected Gender is Female",
							// Toast.LENGTH_SHORT).show();
							break;

						case R.id.manager_other:
							manager_gender_txt.setText("O");
							// Toast.makeText(getApplicationContext(),
							// "Your Selected Gender is other",
							// Toast.LENGTH_SHORT).show();
							break;
						}
					}
				});
		gender_group
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					public void onCheckedChanged(RadioGroup arg0, int checkedId) {
						switch (checkedId) {
						case R.id.resp_male:
							group1_text.setText("M");
							// Toast.makeText(getApplicationContext(),
							// "Your Selected Gender is Male",
							// Toast.LENGTH_SHORT).show();
							break;

						case R.id.resp_female:
							group1_text.setText("F");
							// Toast.makeText(getApplicationContext(),
							// "Your Selected Gender is Female",
							// Toast.LENGTH_SHORT).show();
							break;

						case R.id.resp_other:
							group1_text.setText("O");
							// Toast.makeText(getApplicationContext(),
							// "Your Selected Gender is other",
							// Toast.LENGTH_SHORT).show();
							break;
						}
					}
				});

		/*
		 * gender_group2.setOnCheckedChangeListener(new
		 * RadioGroup.OnCheckedChangeListener() { public void
		 * onCheckedChanged(RadioGroup arg0, int checkedId) { switch (checkedId)
		 * { case R.id.resp_male2: group2_text.setText("M"); //
		 * Toast.makeText(getApplicationContext(),
		 * "Your Selected Gender is Male", Toast.LENGTH_SHORT).show(); break;
		 * 
		 * case R.id.resp_female2: group2_text.setText("F"); //
		 * Toast.makeText(getApplicationContext(),
		 * "Your Selected Gender is Female", Toast.LENGTH_SHORT).show(); break;
		 * 
		 * case R.id.resp_other2: group2_text.setText("O"); //
		 * Toast.makeText(getApplicationContext(),
		 * "Your Selected Gender is other", Toast.LENGTH_SHORT).show(); break; }
		 * } });
		 * 
		 * gender_group3.setOnCheckedChangeListener(new
		 * RadioGroup.OnCheckedChangeListener() { public void
		 * onCheckedChanged(RadioGroup arg0, int checkedId) { switch (checkedId)
		 * { case R.id.resp_male3: group3_text.setText("M"); //
		 * Toast.makeText(getApplicationContext(),
		 * "Your Selected Gender is Male", Toast.LENGTH_SHORT).show(); break;
		 * 
		 * case R.id.resp_female3: group3_text.setText("F"); //
		 * Toast.makeText(getApplicationContext(),
		 * "Your Selected Gender is Female", Toast.LENGTH_SHORT).show(); break;
		 * 
		 * case R.id.resp_other3: group3_text.setText("O"); //
		 * Toast.makeText(getApplicationContext(),
		 * "Your Selected Gender is other", Toast.LENGTH_SHORT).show(); break; }
		 * } });
		 * 
		 * gender_group4.setOnCheckedChangeListener(new
		 * RadioGroup.OnCheckedChangeListener() { public void
		 * onCheckedChanged(RadioGroup arg0, int checkedId) { switch (checkedId)
		 * { case R.id.resp_male4: RadioButton select =
		 * (RadioButton)findViewById(gender_group4.getCheckedRadioButtonId());
		 * Log.i("Selected Radio Button Value",select.getText().toString() );
		 * group4_text.setText("M"); // Toast.makeText(getApplicationContext(),
		 * "Your Selected Gender is Male", Toast.LENGTH_SHORT).show(); break;
		 * 
		 * case R.id.resp_female4: group4_text.setText("F"); //
		 * Toast.makeText(getApplicationContext(),
		 * "Your Selected Gender is Female", Toast.LENGTH_SHORT).show(); break;
		 * 
		 * case R.id.resp_other4: group4_text.setText("O"); //
		 * Toast.makeText(getApplicationContext(),
		 * "Your Selected Gender is other", Toast.LENGTH_SHORT).show(); break; }
		 * } });
		 */

		person_based.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				otpFLG = false;
				clickcount = 0;
				
				

				if (idproof_yes.isChecked()) {
					clickcount = 0;
					get_the_details.setVisibility(View.VISIBLE);
					respondent_1.setVisibility(View.VISIBLE);
					add_idProof.setVisibility(View.VISIBLE);
					respondent_2.setVisibility(View.GONE);
					respondent_3.setVisibility(View.GONE);
					respondent_4.setVisibility(View.GONE);
					leaser_layout.setVisibility(View.GONE);
					manager_layout.setVisibility(View.GONE);
					add_manager.setVisibility(View.GONE);
					add_leaser.setVisibility(View.GONE);
					list_layout_shop.setVisibility(View.GONE);
					list_layout_shop_tin.setVisibility(View.GONE);
					list_layout_shopOwner.setVisibility(View.GONE);
					addleaser_layout.setVisibility(View.GONE);
					addmanager_layLayout.setVisibility(View.GONE);
					indian.setChecked(true);
					

				} else if (idproof_no.isChecked()) {
					clickcount = 0;
					get_the_details.setVisibility(View.GONE);
					/*get_the_details2.setVisibility(View.GONE);
					get_the_details3.setVisibility(View.GONE);
					get_the_details4.setVisibility(View.GONE);*/
					respondent_1.setVisibility(View.VISIBLE);
					respondent_2.setVisibility(View.GONE);
					respondent_3.setVisibility(View.GONE);
					respondent_4.setVisibility(View.GONE);
					add_idProof.setVisibility(View.VISIBLE);
					leaser_layout.setVisibility(View.GONE);
					manager_layout.setVisibility(View.GONE);
					add_manager.setVisibility(View.GONE);
					add_leaser.setVisibility(View.GONE);
					list_layout_shop.setVisibility(View.GONE);
					list_layout_shop_tin.setVisibility(View.GONE);
					list_layout_shopOwner.setVisibility(View.GONE);
					addleaser_layout.setVisibility(View.GONE);
					addmanager_layLayout.setVisibility(View.GONE);
					indian.setChecked(true);

				}
				else if (idproof_yes2.isChecked()) {
					clickcount = 0;
					get_the_details2.setVisibility(View.VISIBLE);
					respondent_2.setVisibility(View.VISIBLE);
				}
				else if (idproof_no2.isChecked()) {
					clickcount = 0;
					get_the_details2.setVisibility(View.GONE);
					respondent_2.setVisibility(View.VISIBLE);
				}
				else if (idproof_yes3.isChecked()) {
					clickcount = 0;
					get_the_details3.setVisibility(View.VISIBLE);
					respondent_3.setVisibility(View.VISIBLE);
				}
				else if (idproof_no3.isChecked()) {
					clickcount = 0;
					get_the_details3.setVisibility(View.GONE);
					respondent_3.setVisibility(View.VISIBLE);
				}
				else if (idproof_yes4.isChecked()) {
					clickcount = 0;
					get_the_details4.setVisibility(View.VISIBLE);
					respondent_4.setVisibility(View.VISIBLE);
					add_idProof.setVisibility(View.GONE);
				}
				else if (idproof_no4.isChecked()) {
					clickcount = 0;
					get_the_details4.setVisibility(View.GONE);
					respondent_4.setVisibility(View.VISIBLE);
					add_idProof.setVisibility(View.GONE);
				}
				
				list_layout_internatnl.setVisibility(View.GONE);
				list_layout_passport.setVisibility(View.GONE);

				/* ************************
				 * 
				 * list_layout_shop.setVisibility(View.GONE);
				 * list_layout_shop_tin.setVisibility(View.GONE);
				 * list_layout_shopOwner.setVisibility(View.GONE);
				 * 
				 * add_idProof.setVisibility(View.GONE);
				 * leaser_layout.setVisibility(View.GONE);
				 * manager_layout.setVisibility(View.GONE);
				 * respondent_2.setVisibility(View.GONE);
				 * respondent_3.setVisibility(View.GONE);
				 * respondent_4.setVisibility(View.GONE);
				 */

				add_manager.setVisibility(View.GONE);
				add_leaser.setVisibility(View.GONE);
				add_idProof.setVisibility(View.VISIBLE);
				indian.setChecked(true);

				manager_name.setText("");
				manager_address.setText("");
				manager_age.setText("");
				manager_city.setText("");
				manager_fname.setText("");
				manager_mobileNo.setText("");
				manager_img.setImageResource(R.drawable.photo);

				leaser_name.setText("");
				leaser_address.setText("");
				leaser_age.setText("");
				leaser_city.setText("");
				leaser_fname.setText("");
				leaser_mobileNo.setText("");
				leaser_img.setImageResource(R.drawable.photo);

				respondent_id_details.setText("");
				shop_name.setText("");
				shop_Ownername.setText("");
				tin_no.setText("");

				off_address.setText("");
				off_Pnum.setText("");
				off_age.setText("");
				off_fname.setText("");
				id_off_name.setText("");
				off_Pnum.setText("");
				id_passport_no.setText("");
				id_int_drive_lic.setText("");
				respondent_img1.setImageResource(R.drawable.photo);

				off_address2.setText("");
				off_Pnum2.setText("");
				off_age2.setText("");
				off_fname2.setText("");
				id_off_name2.setText("");
				off_Pnum2.setText("");
				id_passport_no2.setText("");
				id_int_drive_lic2.setText("");
				respondent_img2.setImageResource(R.drawable.photo);

				off_address3.setText("");
				off_Pnum3.setText("");
				off_age3.setText("");
				off_fname3.setText("");
				id_off_name3.setText("");
				off_Pnum3.setText("");
				id_passport_no3.setText("");
				id_int_drive_lic3.setText("");
				respondent_img3.setImageResource(R.drawable.photo);

				off_address4.setText("");
				off_Pnum4.setText("");
				off_age4.setText("");
				off_fname4.setText("");
				id_off_name4.setText("");
				off_Pnum4.setText("");
				id_passport_no4.setText("");
				id_int_drive_lic4.setText("");
				respondent_img4.setImageResource(R.drawable.photo);

			}
		});

		shop_based.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				otpFLG = false;
				clickcount = 0;
				
				list_layout_internatnl.setVisibility(View.GONE);
				list_layout_passport.setVisibility(View.GONE);
				
				add_leaser.setText("Add Leaser");
				add_manager.setText("Add Manager");
				

				get_detailsFlg1 = false;
				get_detailsFlg2 = false;
				get_detailsFlg3 = false;
				get_detailsFlg4 = false;

				if (idproof_yes.isChecked()) {
					clickcount = 0;
					get_the_details.setVisibility(View.VISIBLE);
					respondent_1.setVisibility(View.VISIBLE);
					respondent_2.setVisibility(View.GONE);
					respondent_3.setVisibility(View.GONE);
					respondent_4.setVisibility(View.GONE);
					add_idProof.setVisibility(View.GONE);
					manager_layout.setVisibility(View.GONE);
					leaser_layout.setVisibility(View.GONE);
					add_manager.setVisibility(View.VISIBLE);
					addmanager_layLayout.setVisibility(View.VISIBLE);
					addleaser_layout.setVisibility(View.VISIBLE);
					add_leaser.setVisibility(View.VISIBLE);
					list_layout_shop.setVisibility(View.VISIBLE);
					list_layout_shop_tin.setVisibility(View.VISIBLE);
					list_layout_shopOwner.setVisibility(View.VISIBLE);
				}

				else if (idproof_no.isChecked()) {
					clickcount = 0;
					get_the_details.setVisibility(View.GONE);
					respondent_1.setVisibility(View.VISIBLE);
					respondent_2.setVisibility(View.GONE);
					respondent_3.setVisibility(View.GONE);
					respondent_4.setVisibility(View.GONE);
					manager_layout.setVisibility(View.GONE);
					leaser_layout.setVisibility(View.GONE);
					add_manager.setVisibility(View.VISIBLE);
					add_leaser.setVisibility(View.VISIBLE);
					list_layout_shop.setVisibility(View.VISIBLE);
					list_layout_shop_tin.setVisibility(View.VISIBLE);
					list_layout_shopOwner.setVisibility(View.VISIBLE);
					addmanager_layLayout.setVisibility(View.VISIBLE);
					addleaser_layout.setVisibility(View.VISIBLE);
					add_idProof.setVisibility(View.GONE);
					
					
					
				}

				/*
				 * list_layout_shop.setVisibility(View.VISIBLE);
				 * list_layout_shop_tin.setVisibility(View.VISIBLE);
				 * list_layout_shopOwner.setVisibility(View.VISIBLE);
				 */
				/*
				 * add_idProof.setVisibility(View.GONE);
				 * respondent_2.setVisibility(View.GONE);
				 * respondent_3.setVisibility(View.GONE);
				 * respondent_4.setVisibility(View.GONE);
				 * get_the_details.setVisibility(View.VISIBLE);
				 * 
				 * add_manager.setVisibility(View.VISIBLE);
				 * add_leaser.setVisibility(View.VISIBLE);
				 */

				add_idProof.setVisibility(View.GONE);

				manager_name.setText("");
				manager_address.setText("");
				manager_age.setText("");
				manager_city.setText("");
				manager_fname.setText("");
				manager_mobileNo.setText("");

				leaser_name.setText("");
				leaser_address.setText("");
				leaser_age.setText("");
				leaser_city.setText("");
				leaser_fname.setText("");
				leaser_mobileNo.setText("");

				respondent_id_details.setText("");
				shop_name.setText("");
				shop_Ownername.setText("");
				tin_no.setText("");

				off_address.setText("");
				off_Pnum.setText("");
				off_age.setText("");
				off_fname.setText("");
				id_off_name.setText("");
				off_Pnum.setText("");
				id_passport_no.setText("");
				id_int_drive_lic.setText("");
				respondent_img1.setImageResource(R.drawable.photo);

				off_address2.setText("");
				off_Pnum2.setText("");
				off_age2.setText("");
				off_fname2.setText("");
				id_off_name2.setText("");
				off_Pnum2.setText("");
				id_passport_no2.setText("");
				id_int_drive_lic2.setText("");
				respondent_img2.setImageResource(R.drawable.photo);

				off_address3.setText("");
				off_Pnum3.setText("");
				off_age3.setText("");
				off_fname3.setText("");
				id_off_name3.setText("");
				off_Pnum3.setText("");
				id_passport_no3.setText("");
				id_int_drive_lic3.setText("");
				respondent_img3.setImageResource(R.drawable.photo);

				off_address4.setText("");
				off_Pnum4.setText("");
				off_age4.setText("");
				off_fname4.setText("");
				id_off_name4.setText("");
				off_Pnum4.setText("");
				id_passport_no4.setText("");
				id_int_drive_lic4.setText("");
				respondent_img4.setImageResource(R.drawable.photo);
			}
		});

		add_leaser.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!leaserFLG) {
					leaserFLG = true;
					leaser_layout.setVisibility(View.VISIBLE);
					add_leaser.setText("Remove Leaser");

					leaser_id_details.setText("");
					leaser_name.setText("");
					leaser_fname.setText("");
					leaser_age.setText("");
					leaser_mobileNo.setText("");
					leaser_address.setText("");
					leaser_city.setText("");

				} else if (leaserFLG) {
					leaserFLG = false;
					leaser_layout.setVisibility(View.GONE);
					add_leaser.setText("Add Leaser");

					leaser_id_details.setText("");
					leaser_name.setText("");
					leaser_fname.setText("");
					leaser_age.setText("");
					leaser_mobileNo.setText("");
					leaser_address.setText("");
					leaser_city.setText("");
				}

				/*
				 * btnFLG = true; leaser_layout.setVisibility(View.VISIBLE);
				 * add_leaser.setVisibility(View.GONE);
				 */

			}
		});

		add_manager.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!managerFLG) {
					managerFLG = true;
					manager_layout.setVisibility(View.VISIBLE);
					add_manager.setText("Remove Manager");
				} else if (managerFLG) {
					managerFLG = false;
					manager_layout.setVisibility(View.GONE);
					add_manager.setText("Add Manager");

				}
				/*
				 * btnFLG2 = true; manager_layout.setVisibility(View.VISIBLE);
				 * add_manager.setVisibility(View.GONE);
				 */

			}
		});

		witness_details.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent witness = new Intent(getApplicationContext(),
						WitnessFormActivity.class);
				startActivity(witness);
			}
		});

		back_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*
				 * Intent back = new Intent(getApplicationContext(),
				 * Menu_Dashboard_Activity.class);
				 * back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
				 * Intent.FLAG_ACTIVITY_NEW_TASK); startActivity(back);
				 */
				if (SiezedItemsActivity.Ditenditems != null
						&& SiezedItemsActivity.Ditenditems.size() > 0) {
					SiezedItemsActivity.Ditenditems.clear();
				}
				OffenderActivity.previewrespondentBuf = null;
				OffenderActivity.preview_LeaserBuf = null;
				OffenderActivity.preview_ManagerBuf = null;
				OffenderActivity.secBufferPreivew = null;
				OffenderActivity.seizedBuff = null;
				WitnessFormActivity.previewWitnessPromtBuf = null;
				WitnessFormActivity.ET_WitnessDetails = "";
				OffenderActivity.seizedItems = null;
				OffenderActivity.secBuffer = null;
				SiezedItemsActivity.Ditenditems = null;
				PreviewActivity.secBuffer = null;
				PreviewActivity.secBufferPreivew = null;
				Add_SectionsActivity.checkedList = null;
				Add_SectionsActivity.secMap = null;

				PreviewActivity.witnesFlg = false;

				finish();
			}
		});

		picture1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				OffenderActivity.SelPicId = "1";
				selectImage();
			}
		});

		picture2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				OffenderActivity.SelPicId = "2";
				selectImage();
			}
		});

		submit_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				Boolean validationFlg = true;
				if (person_based.isChecked() && idproof_yes.isChecked()) {

					if (respondent_1.getVisibility() == View.VISIBLE) {
						if (id_off_name.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Name", Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							id_off_name.setError(Html
									.fromHtml("<font color='black'>Please Enter Name</font>"));
							id_off_name.requestFocus();
							validationFlg = false;
						} else if (off_fname.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Father Name", Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							off_fname.setError(Html
									.fromHtml("<font color='black'>Please Enter Name</font>"));
							off_fname.requestFocus();
							validationFlg = false;
						} else if (off_age.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Age", Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							off_age.setError(Html
									.fromHtml("<font color='black'>Please Enter Name</font>"));
							off_age.requestFocus();
							validationFlg = false;
						} else if (off_Pnum.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Mobile No", Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							off_Pnum.setError(Html
									.fromHtml("<font color='black'>Please Enter Name</font>"));
							off_Pnum.requestFocus();
							validationFlg = false;
						} else if (picture1.getDrawable().getConstantState() == getResources()
								.getDrawable(R.drawable.camera)
								.getConstantState()) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Capture Image", Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("Please Capture Image");
							validationFlg = false;
						} else if (!otpFLG) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Verify OTP", Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("Please Verify OTP!");
							validationFlg = false;
							otpFLG = false;
						}// otpFLG
						/*
						 * else if
						 * (off_address.getText().toString().length()==0) {
						 * Toast toast = Toast.makeText(getApplicationContext(),
						 * "Please Enter Address", Toast.LENGTH_SHORT);
						 * toast.setGravity(Gravity.CENTER, 0, -200); View
						 * toastView = toast.getView();
						 * toastView.setBackgroundResource
						 * (R.drawable.toast_background); toast.show();
						 * validationFlg=false; } else if
						 * (id_landmark.getText().toString().length()==0) {
						 * Toast toast = Toast.makeText(getApplicationContext(),
						 * "Please Enter Land Mark", Toast.LENGTH_SHORT);
						 * toast.setGravity(Gravity.CENTER, 0, -200); View
						 * toastView = toast.getView();
						 * toastView.setBackgroundResource
						 * (R.drawable.toast_background); toast.show();
						 * validationFlg=false; } else
						 * if(respondent_id_details.getText
						 * ().toString().length()==0) { Toast toast =
						 * Toast.makeText(getApplicationContext(),
						 * "Please Select Valid ID and Enter Valid Details",
						 * Toast.LENGTH_SHORT); toast.setGravity(Gravity.CENTER,
						 * 0, -200); View toastView = toast.getView();
						 * toastView.
						 * setBackgroundResource(R.drawable.toast_background);
						 * toast.show(); validationFlg=false; }
						 */
						else if (gender_group.getCheckedRadioButtonId() == -1) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Select Gender", Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("Please Select Gender");
							validationFlg = false;
						}
					}

					else if (respondent_2.getVisibility() == View.VISIBLE) {
						if (id_off_name2.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Name for Respondent 2",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							id_off_name2.setError(Html
									.fromHtml("<font color='black'>Please Enter Name</font>"));
							id_off_name2.requestFocus();
							validationFlg = false;
						}
						/*
						 * else
						 * if(respondent_id_details2.getText().toString().length
						 * ()==0) { Toast toast =
						 * Toast.makeText(getApplicationContext(),
						 * "Please Select Valid ID and Enter Valid Details Respondent 2"
						 * , Toast.LENGTH_SHORT);
						 * toast.setGravity(Gravity.CENTER, 0, -200); View
						 * toastView = toast.getView();
						 * toastView.setBackgroundResource
						 * (R.drawable.toast_background); toast.show();
						 * validationFlg=false; }
						 */
						else if (off_fname2.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Father Name for Respondent 2",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							off_fname2.setError(Html
									.fromHtml("<font color='black'>Please Enter Father Name</font>"));
							off_fname2.requestFocus();
							validationFlg = false;
						} else if (off_age2.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Age for Respondent 2",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							off_age2.setError(Html
									.fromHtml("<font color='black'>Please Enter Age</font>"));
							off_age2.requestFocus();
							validationFlg = false;
						} else if (off_Pnum2.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Mobile No for Respondent 2",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							off_Pnum2.setError(Html
									.fromHtml("<font color='black'>Please Enter Mobile No</font>"));
							off_Pnum2.requestFocus();
							validationFlg = false;
						} else if (picture1.getDrawable().getConstantState() == getResources()
								.getDrawable(R.drawable.camera)
								.getConstantState()) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Capture Image", Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("Please Capture Image of Respondent 2");
							validationFlg = false;
						} else if (!otpFLG) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Verify Respondent 2's OTP",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("Please Verify Respondent 2's OTP ");
							validationFlg = false;
							otpFLG = false;
						}
						/*
						 * else if
						 * (off_address2.getText().toString().length()==0) {
						 * Toast toast = Toast.makeText(getApplicationContext(),
						 * "Please Enter Address for Respondent 2",
						 * Toast.LENGTH_SHORT); toast.setGravity(Gravity.CENTER,
						 * 0, -200); View toastView = toast.getView();
						 * toastView.
						 * setBackgroundResource(R.drawable.toast_background);
						 * toast.show(); validationFlg=false; }
						 */
						else if (gender_group2.getCheckedRadioButtonId() == -1) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Select Gender for Respondent 2",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("Please Select Gender for Respondent 2");
							validationFlg = false;
						}
					} else if (respondent_3.getVisibility() == View.VISIBLE) {
						if (id_off_name3.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Name for Respondent 3",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							id_off_name3.setError(Html
									.fromHtml("<font color='black'>Please Enter Name</font>"));
							id_off_name3.requestFocus();
							validationFlg = false;
						}
						/*
						 * else
						 * if(respondent_id_details3.getText().toString().length
						 * ()==0) { Toast toast =
						 * Toast.makeText(getApplicationContext(),
						 * "Please Select Valid ID and Enter Valid Details Respondent 3"
						 * , Toast.LENGTH_SHORT);
						 * toast.setGravity(Gravity.CENTER, 0, -200); View
						 * toastView = toast.getView();
						 * toastView.setBackgroundResource
						 * (R.drawable.toast_background); toast.show();
						 * validationFlg=false; }
						 */
						else if (off_fname3.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Father Name for Respondent 3",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							off_fname3.setError(Html
									.fromHtml("<font color='black'>Please Enter Father Name</font>"));
							off_fname3.requestFocus();
							validationFlg = false;
						} else if (off_age3.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Age for Respondent 3",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							off_age3.setError(Html
									.fromHtml("<font color='black'>Please Enter Age</font>"));
							off_age3.requestFocus();
							validationFlg = false;
						} else if (off_Pnum3.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Mobile No for Respondent 3",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							off_Pnum3.setError(Html
									.fromHtml("<font color='black'>Please Enter Mobile No</font>"));
							off_Pnum3.requestFocus();
							validationFlg = false;
						} else if (picture1.getDrawable().getConstantState() == getResources()
								.getDrawable(R.drawable.camera)
								.getConstantState()) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Capture Image", Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("Please Capture Image");
							validationFlg = false;
						} else if (!otpFLG) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Verify Respondent 3's OTP",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("Please Verify Respondent 3's OTP ");
							validationFlg = false;
							otpFLG = false;
						}
						/*
						 * else if
						 * (off_address3.getText().toString().length()==0) {
						 * Toast toast = Toast.makeText(getApplicationContext(),
						 * "Please Enter Address for Respondent 3",
						 * Toast.LENGTH_SHORT); toast.setGravity(Gravity.CENTER,
						 * 0, -200); View toastView = toast.getView();
						 * toastView.
						 * setBackgroundResource(R.drawable.toast_background);
						 * toast.show(); validationFlg=false; }
						 */
						else if (gender_group3.getCheckedRadioButtonId() == -1) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Select Gender for Respondent 3",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("Please Select Gender for Respondent 3");
							validationFlg = false;
						}

					} else if (respondent_4.getVisibility() == View.VISIBLE) {
						if (id_off_name4.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Name for Respondent 4",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							id_off_name4.setError(Html
									.fromHtml("<font color='black'>Please Enter Name</font>"));
							id_off_name4.requestFocus();
							validationFlg = false;
						}
						/*
						 * else
						 * if(respondent_id_details4.getText().toString().length
						 * ()==0) { Toast toast =
						 * Toast.makeText(getApplicationContext(),
						 * "Please Select Valid ID and Enter Valid Details Respondent 4"
						 * , Toast.LENGTH_SHORT);
						 * toast.setGravity(Gravity.CENTER, 0, -200); View
						 * toastView = toast.getView();
						 * toastView.setBackgroundResource
						 * (R.drawable.toast_background); toast.show();
						 * validationFlg=false; }
						 */
						else if (off_fname4.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Father Name for Respondent 4",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							off_fname4.setError(Html
									.fromHtml("<font color='black'>Please Enter Father Name</font>"));
							off_fname4.requestFocus();
							validationFlg = false;
						} else if (off_age4.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Age for Respondent 4",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							off_age4.setError(Html
									.fromHtml("<font color='black'>Please Enter Age</font>"));
							off_age4.requestFocus();
							validationFlg = false;
						} else if (off_Pnum4.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Mobile No for Respondent 4",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							off_Pnum4.setError(Html
									.fromHtml("<font color='black'>Please Enter Mobile No</font>"));
							off_Pnum4.requestFocus();
							validationFlg = false;
						} else if (picture1.getDrawable().getConstantState() == getResources()
								.getDrawable(R.drawable.camera)
								.getConstantState()) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Capture Image", Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("Please Capture Image");
							validationFlg = false;
						} else if (!otpFLG) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Verify Respondent 4's OTP",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("Please Verify Respondent 4's OTP ");
							validationFlg = false;
							otpFLG = false;
						}
						/*
						 * else if
						 * (off_address4.getText().toString().length()==0) {
						 * Toast toast = Toast.makeText(getApplicationContext(),
						 * "Please Enter Address for Respondent 4",
						 * Toast.LENGTH_SHORT); toast.setGravity(Gravity.CENTER,
						 * 0, -200); View toastView = toast.getView();
						 * toastView.
						 * setBackgroundResource(R.drawable.toast_background);
						 * toast.show(); validationFlg=false; }
						 */
						else if (gender_group4.getCheckedRadioButtonId() == -1) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Select Gender for Respondent 4",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("Please Select Gender for Respondent 4");
							validationFlg = false;
						}
					}
				}
				if (idproof_no.isChecked()) {
					if (respondent_1.getVisibility() == View.VISIBLE) {
						if (id_off_name.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Name", Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							id_off_name.setError(Html
									.fromHtml("<font color='black'>Please Enter Name</font>"));
							id_off_name.requestFocus();
							validationFlg = false;
						} else if (off_fname.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Father Name", Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							off_fname.setError(Html
									.fromHtml("<font color='black'>Please Enter Father Name</font>"));
							off_fname.requestFocus();
							validationFlg = false;
						} else if (off_age.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Age", Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							off_age.setError(Html
									.fromHtml("<font color='black'>Please Enter Age</font>"));
							off_age.requestFocus();
							validationFlg = false;
						} else if (off_Pnum.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Mobile No", Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							off_Pnum.setError(Html
									.fromHtml("<font color='black'>Please Enter Mobile no</font>"));
							off_Pnum.requestFocus();
							validationFlg = false;
						} else if (picture1.getDrawable().getConstantState() == getResources()
								.getDrawable(R.drawable.camera)
								.getConstantState()) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Capture Image", Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("Please Capture Image");
							validationFlg = false;
						} else if (!otpFLG) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Verify OTP", Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("Please Verify OTP");
							validationFlg = false;
							otpFLG = false;
						}
						/*
						 * else if
						 * (off_address.getText().toString().length()==0) {
						 * Toast toast = Toast.makeText(getApplicationContext(),
						 * "Please Enter Address", Toast.LENGTH_SHORT);
						 * toast.setGravity(Gravity.CENTER, 0, -200); View
						 * toastView = toast.getView();
						 * toastView.setBackgroundResource
						 * (R.drawable.toast_background); toast.show();
						 * validationFlg=false; } else if
						 * (id_landmark.getText().toString().length()==0) {
						 * Toast toast = Toast.makeText(getApplicationContext(),
						 * "Please Enter Land Mark", Toast.LENGTH_SHORT);
						 * toast.setGravity(Gravity.CENTER, 0, -200); View
						 * toastView = toast.getView();
						 * toastView.setBackgroundResource
						 * (R.drawable.toast_background); toast.show();
						 * validationFlg=false; }
						 */
						else if (gender_group.getCheckedRadioButtonId() == -1) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Select Gender", Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("Please Select Gender");
							validationFlg = false;
						}
					}

					else if (respondent_2.getVisibility() == View.VISIBLE) {
						if (id_off_name2.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Name for Respondent 2",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							id_off_name2.setError(Html
									.fromHtml("<font color='black'>Please Enter Name</font>"));
							id_off_name2.requestFocus();
							validationFlg = false;
						} else if (off_fname2.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Father Name for Respondent 2",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							off_fname2.setError(Html
									.fromHtml("<font color='black'>Please Enter Father Name</font>"));
							off_fname2.requestFocus();
							validationFlg = false;
						} else if (off_age2.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Age for Respondent 2",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							off_age2.setError(Html
									.fromHtml("<font color='black'>Please Enter Age</font>"));
							off_age2.requestFocus();
							validationFlg = false;
						} else if (off_Pnum2.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Mobile No for Respondent 2",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							off_Pnum2.setError(Html
									.fromHtml("<font color='black'>Please Enter Mobile No</font>"));
							off_Pnum2.requestFocus();
							validationFlg = false;
						} else if (picture1.getDrawable().getConstantState() == getResources()
								.getDrawable(R.drawable.camera)
								.getConstantState()) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Capture Image", Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("Please Capture Image");
							validationFlg = false;
						} else if (!otpFLG) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Verify Respondent 2's OTP",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("Please Verify Respondent 2's OTP");
							validationFlg = false;
							otpFLG = false;
						}
						/*
						 * else if
						 * (off_address2.getText().toString().length()==0) {
						 * Toast toast = Toast.makeText(getApplicationContext(),
						 * "Please Enter Address for Respondent 2",
						 * Toast.LENGTH_SHORT); toast.setGravity(Gravity.CENTER,
						 * 0, -200); View toastView = toast.getView();
						 * toastView.
						 * setBackgroundResource(R.drawable.toast_background);
						 * toast.show(); validationFlg=false; }
						 */
						else if (gender_group2.getCheckedRadioButtonId() == -1) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Select Gender for Respondent 2",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("Please Select Gender for Respondent 2");
							validationFlg = false;
						}
					} else if (respondent_3.getVisibility() == View.VISIBLE) {
						if (id_off_name3.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Name for Respondent 3",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							id_off_name3.setError(Html
									.fromHtml("<font color='black'>Please Enter Name</font>"));
							id_off_name3.requestFocus();
							validationFlg = false;
						} else if (off_fname3.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Father Name for Respondent 3",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							off_fname3.setError(Html
									.fromHtml("<font color='black'>Please Enter Father Name</font>"));
							off_fname3.requestFocus();
							validationFlg = false;
						} else if (off_age3.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Age for Respondent 3",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							off_age3.setError(Html
									.fromHtml("<font color='black'>Please Enter Age</font>"));
							off_age3.requestFocus();
							validationFlg = false;
						} else if (off_Pnum3.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Mobile No for Respondent 3",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							off_Pnum3.setError(Html
									.fromHtml("<font color='black'>Please Enter Mobile No</font>"));
							off_Pnum3.requestFocus();
							validationFlg = false;
						} else if (picture1.getDrawable().getConstantState() == getResources()
								.getDrawable(R.drawable.camera)
								.getConstantState()) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Capture Image", Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("Please Capture Image");
							validationFlg = false;
						} else if (!otpFLG) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Verify Respondent 3's OTP",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("Please Verify Respondent 3's OTP ");
							validationFlg = false;
							otpFLG = false;
						}
						/*
						 * else if
						 * (off_address3.getText().toString().length()==0) {
						 * Toast toast = Toast.makeText(getApplicationContext(),
						 * "Please Enter Address for Respondent 3",
						 * Toast.LENGTH_SHORT); toast.setGravity(Gravity.CENTER,
						 * 0, -200); View toastView = toast.getView();
						 * toastView.
						 * setBackgroundResource(R.drawable.toast_background);
						 * toast.show(); validationFlg=false; }
						 */
						else if (gender_group3.getCheckedRadioButtonId() == -1) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Select Gender for Respondent 3",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("Please Select Gender for Respondent 3");
							validationFlg = false;
						}
					} else if (respondent_4.getVisibility() == View.VISIBLE) {
						if (id_off_name4.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Name for Respondent 4",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							id_off_name4.setError(Html
									.fromHtml("<font color='black'>Please Enter Name</font>"));
							id_off_name4.requestFocus();
							validationFlg = false;
						} else if (off_fname4.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Father Name for Respondent 4",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							off_fname4.setError(Html
									.fromHtml("<font color='black'>Please Enter Father Name</font>"));
							off_fname4.requestFocus();
							validationFlg = false;
						} else if (off_age4.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Age for Respondent 4",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							off_age4.setError(Html
									.fromHtml("<font color='black'>Please Enter Age</font>"));
							off_age4.requestFocus();
							validationFlg = false;
						} else if (off_Pnum4.getText().toString().length() == 0) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Enter Mobile No for Respondent 4",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							off_Pnum4.setError(Html
									.fromHtml("<font color='black'>Please Enter Mobile No</font>"));
							off_Pnum4.requestFocus();
							validationFlg = false;
						} else if (picture1.getDrawable().getConstantState() == getResources()
								.getDrawable(R.drawable.camera)
								.getConstantState()) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Capture Image", Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("Please Capture Image");
							validationFlg = false;
						} else if (!otpFLG) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Verify Respondent 4's OTP",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("Please Verify Respondent 4's OTP ");
							validationFlg = false;
							otpFLG = false;
						}
						/*
						 * else if
						 * (off_address4.getText().toString().length()==0) {
						 * Toast toast = Toast.makeText(getApplicationContext(),
						 * "Please Enter Address for Respondent 4",
						 * Toast.LENGTH_SHORT); toast.setGravity(Gravity.CENTER,
						 * 0, -200); View toastView = toast.getView();
						 * toastView.
						 * setBackgroundResource(R.drawable.toast_background);
						 * toast.show(); validationFlg=false; }
						 */
						else if (gender_group4.getCheckedRadioButtonId() == -1) {
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "Please Select Gender for Respondent 4",
							 * Toast.LENGTH_SHORT);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("Please Select Gender for Respondent 4");
							validationFlg = false;
						}

					}
				} else if (shop_based.isChecked()) {

					if (shop_name.getText().toString().length() == 0) {
						/*
						 * Toast toast = Toast.makeText(getApplicationContext(),
						 * "Please Enter Shop Name", Toast.LENGTH_SHORT);
						 * toast.setGravity(Gravity.CENTER, 0, -200); View
						 * toastView = toast.getView();
						 * toastView.setBackgroundResource
						 * (R.drawable.toast_background); toast.show();
						 */
						shop_name.setError(Html
								.fromHtml("<font color='black'>Please Enter Shop Name</font>"));
						shop_name.requestFocus();
						validationFlg = false;
					} else if (shop_Ownername.getText().toString().length() == 0) {
						/*
						 * Toast toast = Toast.makeText(getApplicationContext(),
						 * "Please Enter Shop Owner Name", Toast.LENGTH_SHORT);
						 * toast.setGravity(Gravity.CENTER, 0, -200); View
						 * toastView = toast.getView();
						 * toastView.setBackgroundResource
						 * (R.drawable.toast_background); toast.show();
						 */
						shop_Ownername.setError(Html
								.fromHtml("<font color='black'>Please Enter Shop Owner Name</font>"));
						shop_Ownername.requestFocus();
						validationFlg = false;
					} else if (id_off_name.getText().toString().length() == 0) {
						/*
						 * Toast toast = Toast.makeText(getApplicationContext(),
						 * "Please Enter Name", Toast.LENGTH_SHORT);
						 * toast.setGravity(Gravity.CENTER, 0, -200); View
						 * toastView = toast.getView();
						 * toastView.setBackgroundResource
						 * (R.drawable.toast_background); toast.show();
						 */
						id_off_name.setError(Html
								.fromHtml("<font color='black'>Please Enter Name</font>"));
						id_off_name.requestFocus();
						validationFlg = false;
					} else if (off_fname.getText().toString().length() == 0) {
						/*
						 * Toast toast = Toast.makeText(getApplicationContext(),
						 * "Please Enter Father Name", Toast.LENGTH_SHORT);
						 * toast.setGravity(Gravity.CENTER, 0, -200); View
						 * toastView = toast.getView();
						 * toastView.setBackgroundResource
						 * (R.drawable.toast_background); toast.show();
						 */
						off_fname.setError(Html
								.fromHtml("<font color='black'>Please Enter Father Name</font>"));
						off_fname.requestFocus();
						validationFlg = false;
					} else if (off_age.getText().toString().length() == 0) {
						/*
						 * Toast toast = Toast.makeText(getApplicationContext(),
						 * "Please Enter Age", Toast.LENGTH_SHORT);
						 * toast.setGravity(Gravity.CENTER, 0, -200); View
						 * toastView = toast.getView();
						 * toastView.setBackgroundResource
						 * (R.drawable.toast_background); toast.show();
						 */
						off_age.setError(Html
								.fromHtml("<font color='black'>Please Enter Age</font>"));
						off_age.requestFocus();
						validationFlg = false;
					} else if (off_Pnum.getText().toString().length() == 0) {
						/*
						 * Toast toast = Toast.makeText(getApplicationContext(),
						 * "Please Enter Mobile No", Toast.LENGTH_SHORT);
						 * toast.setGravity(Gravity.CENTER, 0, -200); View
						 * toastView = toast.getView();
						 * toastView.setBackgroundResource
						 * (R.drawable.toast_background); toast.show();
						 */
						off_Pnum.setError(Html
								.fromHtml("<font color='black'>Please Enter Mobile No</font>"));
						off_Pnum.requestFocus();
						validationFlg = false;
					} else if (!otpFLG) {
						/*
						 * Toast toast = Toast.makeText(getApplicationContext(),
						 * "Please Verify OTP", Toast.LENGTH_SHORT);
						 * toast.setGravity(Gravity.CENTER, 0, -200); View
						 * toastView = toast.getView();
						 * toastView.setBackgroundResource
						 * (R.drawable.toast_background); toast.show();
						 */
						showToast("Please Verify OTP");
						validationFlg = false;
						otpFLG = false;
					} else if (picture1.getDrawable().getConstantState() == getResources()
							.getDrawable(R.drawable.camera).getConstantState()) {
						/*
						 * Toast toast = Toast.makeText(getApplicationContext(),
						 * "Please Capture Image", Toast.LENGTH_SHORT);
						 * toast.setGravity(Gravity.CENTER, 0, -200); View
						 * toastView = toast.getView();
						 * toastView.setBackgroundResource
						 * (R.drawable.toast_background); toast.show();
						 */
						showToast("Please Capture Image");
						validationFlg = false;
					}
					/*
					 * else if (off_address.getText().toString().length()==0) {
					 * Toast toast = Toast.makeText(getApplicationContext(),
					 * "Please Enter Address", Toast.LENGTH_SHORT);
					 * toast.setGravity(Gravity.CENTER, 0, -200); View toastView
					 * = toast.getView();
					 * toastView.setBackgroundResource(R.drawable
					 * .toast_background); toast.show(); validationFlg=false; }
					 * else if (id_landmark.getText().toString().length()==0) {
					 * Toast toast = Toast.makeText(getApplicationContext(),
					 * "Please Enter Land Mark", Toast.LENGTH_SHORT);
					 * toast.setGravity(Gravity.CENTER, 0, -200); View toastView
					 * = toast.getView();
					 * toastView.setBackgroundResource(R.drawable
					 * .toast_background); toast.show(); validationFlg=false; }
					 */
					else if (gender_group.getCheckedRadioButtonId() == -1) {
						/*
						 * Toast toast = Toast.makeText(getApplicationContext(),
						 * "Please Select Gender", Toast.LENGTH_SHORT);
						 * toast.setGravity(Gravity.CENTER, 0, -200); View
						 * toastView = toast.getView();
						 * toastView.setBackgroundResource
						 * (R.drawable.toast_background); toast.show();
						 */
						showToast("Please Select Gender");
						validationFlg = false;
					} else if (leaser_layout.getVisibility() == View.VISIBLE) {
						if (leaser_name.getText().toString().trim().equals("")) {
							leaser_name.setError(Html
									.fromHtml("<font color='black'>Please Enter Leaser Name</font>"));
							leaser_name.requestFocus();
						} else if (leaser_fname.getText().toString().trim()
								.equals("")) {
							leaser_fname.setError(Html
									.fromHtml("<font color='black'>Please Enter Leaser Father Name</font>"));
							leaser_fname.requestFocus();
						} else if (leaser_age.getText().toString().trim()
								.equals("")) {
							leaser_age.setError(Html
									.fromHtml("<font color='black'>Please Enter Age</font>"));
							leaser_age.requestFocus();
						} else if (leaser_mobileNo.getText().toString().trim()
								.equals("")) {
							leaser_mobileNo.setError(Html
									.fromHtml("<font color='black'>Please Enter Mobile No</font>"));
							leaser_mobileNo.requestFocus();
						} else if (leaser_address.getText().toString().trim()
								.equals("")) {
							leaser_address.setError(Html
									.fromHtml("<font color='black'>Please Enter Address</font>"));
							leaser_address.requestFocus();
						}
					} else if (manager_layout.getVisibility() == View.VISIBLE) {
						if (manager_name.getText().toString().trim().equals("")) {
							manager_name.setError(Html
									.fromHtml("<font color='black'>Please Enter manager Name</font>"));
							manager_name.requestFocus();
						} else if (manager_fname.getText().toString().trim()
								.equals("")) {
							manager_fname.setError(Html
									.fromHtml("<font color='black'>Please Enter manager Father Name</font>"));
							manager_fname.requestFocus();
						} else if (manager_age.getText().toString().trim()
								.equals("")) {
							manager_age.setError(Html
									.fromHtml("<font color='black'>Please Enter Age</font>"));
							manager_age.requestFocus();
						} else if (manager_mobileNo.getText().toString().trim()
								.equals("")) {
							manager_mobileNo.setError(Html
									.fromHtml("<font color='black'>Please Enter Mobile No</font>"));
							manager_mobileNo.requestFocus();
						} else if (manager_address.getText().toString().trim()
								.equals("")) {
							manager_address.setError(Html
									.fromHtml("<font color='black'>Please Enter Address</font>"));
							manager_address.requestFocus();
						}
					}
				}

				if (Add_SectionsActivity.secMap == null) {
					/*
					 * Toast toast = Toast.makeText(getApplicationContext(),
					 * "Please Select atleast One Section", Toast.LENGTH_SHORT);
					 * toast.setGravity(Gravity.CENTER, 0, -200); View toastView
					 * = toast.getView();
					 * toastView.setBackgroundResource(R.drawable
					 * .toast_background); toast.show();
					 */
					showToast("Please Select Atleast One Section");
					validationFlg = false;
				} else if (Add_SectionsActivity.checkedList != null
						&& Add_SectionsActivity.checkedList.size() == 0) {
					/*
					 * Toast toast = Toast.makeText(getApplicationContext(),
					 * "Please Select atleast One Section", Toast.LENGTH_SHORT);
					 * toast.setGravity(Gravity.CENTER, 0, -200); View toastView
					 * = toast.getView();
					 * toastView.setBackgroundResource(R.drawable
					 * .toast_background); toast.show();
					 */
					showToast("Please Select atleast One Section");
					validationFlg = false;
				}
				Log.i("validation flg", validationFlg + "");
				if (validationFlg) {
					Intent preview = new Intent(getApplicationContext(),
							PreviewActivity.class);
					startActivity(preview);
				}
			}
		});

		respondent_img1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				OffenderActivity.SelPicId = "3";
				selectImage();
			}
		});

		respondent_img2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				OffenderActivity.SelPicId = "4";
				selectImage();
			}
		});

		respondent_img3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				OffenderActivity.SelPicId = "5";
				selectImage();
			}
		});
		respondent_img4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				OffenderActivity.SelPicId = "6";
				selectImage();
			}
		});

		leaser_img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				OffenderActivity.SelPicId = "7";
				selectImage();
			}
		});

		manager_img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				OffenderActivity.SelPicId = "8";
				selectImage();
			}
		});

		/*
		 * picture3=(ImageView)findViewById(R.id.picture3);
		 * picture3.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub OffenderActivity.SelPicId="3"; selectImage(); } });
		 */

		// id_proof_options = (Spinner)findViewById(R.id.id_proof_options);

		idMap = new DataBase(getApplicationContext())
				.getIdMap(getApplicationContext());
		list = new ArrayList<String>();
		/* list.add("-Select ID"); */
		for (String idCode : idMap.keySet()) {
			list.add(idMap.get(idCode));
		}
		ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
				R.layout.spinner_item, list);
		adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		off_id_options.setAdapter(adp);
		off_id_options.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
				/*
				 * case 0 : respondent_id_details.setHint("Enter ID Details");
				 * break;
				 */
				case 0:
					// Toast.makeText(getApplicationContext(),
					// "Please Enter Valid Aadhaar Number",
					// Toast.LENGTH_SHORT).show();
					respondent_id_details.setHint(getLabel("1"));
					respondent_id_details.setText("");
					respondent_id_details
							.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
									12) });
					respondent_id_details
							.setInputType(InputType.TYPE_CLASS_NUMBER);
					respondent_id_details.requestFocus();
					id_off_name.setText("");
					off_fname.setText("");
					off_age.setText("");
					off_Pnum.setText("");
					off_address.setText("");

					break;
				case 1:
					// Toast.makeText(getApplicationContext(),
					// "Please Enter Valid Driving Licence Number",
					// Toast.LENGTH_SHORT).show();
					respondent_id_details.setHint(getLabel("2"));
					respondent_id_details.setText("");
					respondent_id_details
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details
							.setInputType(InputType.TYPE_CLASS_TEXT);
					respondent_id_details.requestFocus();
					id_off_name.setText("");
					off_fname.setText("");
					off_age.setText("");
					off_Pnum.setText("");
					off_address.setText("");
					respondent_img1.setImageResource(R.drawable.photo);
					break;
				case 2:
					// Toast.makeText(getApplicationContext(),
					// "Please Enter Valid Vehicle Number",
					// Toast.LENGTH_SHORT).show();
					respondent_id_details.setHint(getLabel("3"));
					respondent_id_details.setText("");
					respondent_id_details
							.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
									10) });
					respondent_id_details
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details
							.setInputType(InputType.TYPE_CLASS_TEXT);
					respondent_id_details.requestFocus();
					id_off_name.setText("");
					off_fname.setText("");
					off_age.setText("");
					off_Pnum.setText("");
					off_address.setText("");
					respondent_img1.setImageResource(R.drawable.photo);
					break;
				case 3:
					// Toast.makeText(getApplicationContext(),
					// "Please Enter Valid PassPort Number",
					// Toast.LENGTH_SHORT).show();
					respondent_id_details.setHint(getLabel("4"));
					respondent_id_details.setText("");
					respondent_id_details
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details
							.setInputType(InputType.TYPE_CLASS_TEXT);
					respondent_id_details.requestFocus();
					id_off_name.setText("");
					off_fname.setText("");
					off_age.setText("");
					off_Pnum.setText("");
					off_address.setText("");
					respondent_img1.setImageResource(R.drawable.photo);
					break;
				case 4:
					// Toast.makeText(getApplicationContext(),
					// "Please Enter Valid PAN CARD Number",
					// Toast.LENGTH_SHORT).show();
					respondent_id_details.setHint(getLabel("5"));
					respondent_id_details.setText("");
					respondent_id_details
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details
							.setInputType(InputType.TYPE_CLASS_TEXT);
					respondent_id_details.requestFocus();
					id_off_name.setText("");
					off_fname.setText("");
					off_age.setText("");
					off_Pnum.setText("");
					off_address.setText("");
					respondent_img1.setImageResource(R.drawable.photo);
					break;
				case 5:
					// Toast.makeText(getApplicationContext(),
					// "Please Enter Valid Voter ID Number",
					// Toast.LENGTH_SHORT).show();
					respondent_id_details.setHint(getLabel("6"));
					respondent_id_details.setText("");
					respondent_id_details
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details
							.setInputType(InputType.TYPE_CLASS_TEXT);
					respondent_id_details.requestFocus();
					id_off_name.setText("");
					off_fname.setText("");
					off_age.setText("");
					off_Pnum.setText("");
					off_address.setText("");
					respondent_img1.setImageResource(R.drawable.photo);
					break;
				case 6:
					// Toast.makeText(getApplicationContext(),
					// "Please Enter Valid Ration Card Number",
					// Toast.LENGTH_SHORT).show();
					respondent_id_details.setHint(getLabel("7"));
					respondent_id_details
							.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
									16) });
					respondent_id_details
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details
							.setInputType(InputType.TYPE_CLASS_TEXT);
					respondent_id_details.requestFocus();
					respondent_id_details.setText("");
					id_off_name.setText("");
					off_fname.setText("");
					off_age.setText("");
					off_Pnum.setText("");
					off_address.setText("");
					respondent_img1.setImageResource(R.drawable.photo);
					break;
				case 7:
					// Toast.makeText(getApplicationContext(),
					// "Please Enter Valid Mobile Number",
					// Toast.LENGTH_SHORT).show();
					respondent_id_details.setHint(getLabel("8"));
					respondent_id_details
							.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
									10) });
					respondent_id_details
							.setInputType(InputType.TYPE_CLASS_NUMBER);
					respondent_id_details.requestFocus();
					respondent_id_details.setText("");
					id_off_name.setText("");
					off_fname.setText("");
					off_age.setText("");
					off_Pnum.setText("");
					off_address.setText("");
					respondent_img1.setImageResource(R.drawable.photo);
					break;
				case 8:
					respondent_id_details.setHint(getLabel("9"));
					respondent_id_details.setText("");
					respondent_id_details
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details
							.setInputType(InputType.TYPE_CLASS_TEXT);
					respondent_id_details.requestFocus();
					id_off_name.setText("");
					off_fname.setText("");
					off_age.setText("");
					off_Pnum.setText("");
					off_address.setText("");
					respondent_img1.setImageResource(R.drawable.photo);
					break;
				case 9:
					respondent_id_details.setHint(getLabel("10"));
					respondent_img1.setImageResource(R.drawable.photo);
					respondent_id_details.requestFocus();
					break;
				default:
					respondent_id_details.setHint("Nothing Selected");
					break;
				}
			}

			public CharSequence getLabel(String selected) {
				String label = "";
				try {
					for (String idCode : OffenderActivity.idMap.keySet()) {
						if (idCode.equals(selected)) {
							label = "ENTER "
									+ OffenderActivity.idMap.get(idCode);
							OffenderActivity.selectedID = OffenderActivity.idMap
									.get(idCode).toString();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				Log.i("selected label name " + selected, label);
				return label;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

		idMap = new DataBase(getApplicationContext())
				.getIdMap(getApplicationContext());
		list = new ArrayList<String>();
		/* list.add("-Select ID"); */
		for (String idCode : idMap.keySet()) {
			list.add(idMap.get(idCode));
		}
		ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this,
				R.layout.spinner_item, list);
		adp2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

		off_id_options2.setAdapter(adp2);
		off_id_options2.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
				/*
				 * case 0 : respondent_id_details2.setHint("Enter ID Details");
				 * respondent_id_details2.setText("");
				 * respondent_id_details2.setFilters(new InputFilter[] {new
				 * InputFilter.AllCaps()}); id_off_name2.setText("");
				 * off_fname2.setText(""); off_age2.setText("");
				 * off_Pnum2.setText(""); off_address2.setText(""); break;
				 */
				case 0:
					respondent_id_details2.setHint(getLabel2("1"));
					respondent_id_details2.setText("");
					respondent_id_details2
							.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
									12) });
					respondent_id_details2
							.setInputType(InputType.TYPE_CLASS_NUMBER);
					id_off_name2.setText("");
					off_fname2.setText("");
					off_age2.setText("");
					off_Pnum2.setText("");
					off_address2.setText("");
					break;
				case 1:
					respondent_id_details2.setHint(getLabel2("2"));
					respondent_id_details2.setText("");
					respondent_id_details2
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details2
							.setInputType(InputType.TYPE_CLASS_TEXT);
					id_off_name2.setText("");
					off_fname2.setText("");
					off_age2.setText("");
					off_Pnum2.setText("");
					off_address2.setText("");
					respondent_img2.setImageResource(R.drawable.photo);
					break;
				case 2:
					respondent_id_details2.setHint(getLabel2("3"));
					respondent_id_details2.setText("");
					respondent_id_details2
							.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
									10) });
					respondent_id_details2
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details2
							.setInputType(InputType.TYPE_CLASS_TEXT);
					id_off_name2.setText("");
					off_fname2.setText("");
					off_age2.setText("");
					off_Pnum2.setText("");
					off_address2.setText("");
					respondent_img2.setImageResource(R.drawable.photo);
					break;
				case 3:
					respondent_id_details2.setHint(getLabel2("4"));
					respondent_id_details2.setText("");
					respondent_id_details2
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details2
							.setInputType(InputType.TYPE_CLASS_TEXT);
					id_off_name2.setText("");
					off_fname2.setText("");
					off_age2.setText("");
					off_Pnum2.setText("");
					off_address2.setText("");
					respondent_img2.setImageResource(R.drawable.photo);
					break;
				case 4:
					respondent_id_details2.setHint(getLabel2("5"));
					respondent_id_details2.setText("");
					respondent_id_details2
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details2
							.setInputType(InputType.TYPE_CLASS_TEXT);
					id_off_name2.setText("");
					off_fname2.setText("");
					off_age2.setText("");
					off_Pnum2.setText("");
					off_address2.setText("");
					respondent_img2.setImageResource(R.drawable.photo);
					break;
				case 5:
					respondent_id_details2.setHint(getLabel2("6"));
					respondent_id_details2.setText("");
					respondent_id_details2
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details2
							.setInputType(InputType.TYPE_CLASS_TEXT);
					id_off_name2.setText("");
					off_fname2.setText("");
					off_age2.setText("");
					off_Pnum2.setText("");
					off_address2.setText("");
					respondent_img2.setImageResource(R.drawable.photo);
					break;
				case 6:
					respondent_id_details2.setHint(getLabel2("7"));
					respondent_id_details2.setText("");
					respondent_id_details2
							.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
									26) });
					respondent_id_details2
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details2
							.setInputType(InputType.TYPE_CLASS_TEXT);
					id_off_name2.setText("");
					off_fname2.setText("");
					off_age2.setText("");
					off_Pnum2.setText("");
					off_address2.setText("");
					respondent_img2.setImageResource(R.drawable.photo);
					break;
				case 7:
					respondent_id_details2.setHint(getLabel2("8"));
					respondent_id_details2.setText("");
					respondent_id_details2
							.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
									10) });
					respondent_id_details2
							.setInputType(InputType.TYPE_CLASS_NUMBER);
					id_off_name2.setText("");
					off_fname2.setText("");
					off_age2.setText("");
					off_Pnum2.setText("");
					off_address2.setText("");
					respondent_img2.setImageResource(R.drawable.photo);
					break;
				case 8:
					respondent_id_details2.setHint(getLabel2("9"));
					respondent_id_details2.setText("");
					respondent_id_details2
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details2
							.setInputType(InputType.TYPE_CLASS_TEXT);
					id_off_name2.setText("");
					off_fname2.setText("");
					off_age2.setText("");
					off_Pnum2.setText("");
					off_address2.setText("");
					respondent_img2.setImageResource(R.drawable.photo);
					break;
				case 9:
					respondent_id_details2.setHint(getLabel2("10"));
					respondent_id_details2.setText("");
					respondent_id_details2
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details2
							.setInputType(InputType.TYPE_CLASS_TEXT);
					id_off_name2.setText("");
					off_fname2.setText("");
					off_age2.setText("");
					off_Pnum2.setText("");
					off_address2.setText("");
					respondent_img2.setImageResource(R.drawable.photo);
					break;
				default:
					respondent_id_details2.setHint("Nothing Selected");
					break;
				}

			}

			public CharSequence getLabel2(String selected) {
				String label = "";
				try {
					for (String idCode : OffenderActivity.idMap.keySet()) {
						if (idCode.equals(selected)) {
							label = "ENTER "
									+ OffenderActivity.idMap.get(idCode);
							OffenderActivity.selectedID2 = OffenderActivity.idMap
									.get(idCode).toString();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				Log.i("selected label name " + selected, label);
				return label;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

		idMap = new DataBase(getApplicationContext())
				.getIdMap(getApplicationContext());
		list = new ArrayList<String>();
		/* list.add("-Select ID"); */
		for (String idCode : idMap.keySet()) {
			list.add(idMap.get(idCode));
		}
		ArrayAdapter<String> adp3 = new ArrayAdapter<String>(this,
				R.layout.spinner_item, list);
		adp3.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		off_id_options3.setAdapter(adp3);
		off_id_options3.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
				/*
				 * case 0 : respondent_id_details3.setHint("Enter ID Details");
				 * respondent_id_details3.setText(""); id_off_name3.setText("");
				 * off_fname3.setText(""); off_age3.setText("");
				 * off_Pnum3.setText(""); off_address3.setText(""); break;
				 */
				case 0:
					respondent_id_details3.setHint(getLabel3("1"));
					respondent_id_details3.setText("");
					respondent_id_details3
							.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
									12) });
					respondent_id_details3
							.setInputType(InputType.TYPE_CLASS_NUMBER);
					id_off_name3.setText("");
					off_fname3.setText("");
					off_age3.setText("");
					off_Pnum3.setText("");
					off_address3.setText("");

					break;
				case 1:
					respondent_id_details3.setHint(getLabel3("2"));
					respondent_id_details3.setText("");
					respondent_id_details3
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details3
							.setInputType(InputType.TYPE_CLASS_TEXT);
					id_off_name3.setText("");
					off_fname3.setText("");
					off_age3.setText("");
					off_Pnum3.setText("");
					off_address3.setText("");
					break;
				case 2:
					respondent_id_details3.setHint(getLabel3("3"));
					respondent_id_details3.setText("");
					respondent_id_details
							.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
									10) });
					respondent_id_details3
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details3
							.setInputType(InputType.TYPE_CLASS_TEXT);
					id_off_name3.setText("");
					off_fname3.setText("");
					off_age3.setText("");
					off_Pnum3.setText("");
					off_address3.setText("");
					break;
				case 3:
					respondent_id_details3.setHint(getLabel3("4"));
					respondent_id_details3.setText("");
					respondent_id_details3
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details3
							.setInputType(InputType.TYPE_CLASS_TEXT);
					id_off_name3.setText("");
					off_fname3.setText("");
					off_age3.setText("");
					off_Pnum3.setText("");
					off_address3.setText("");
					break;
				case 4:
					respondent_id_details3.setHint(getLabel3("5"));
					respondent_id_details3.setText("");
					respondent_id_details3
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details3
							.setInputType(InputType.TYPE_CLASS_TEXT);
					id_off_name3.setText("");
					off_fname3.setText("");
					off_age3.setText("");
					off_Pnum3.setText("");
					off_address3.setText("");
					break;
				case 5:
					respondent_id_details3.setHint(getLabel3("6"));
					respondent_id_details3.setText("");
					respondent_id_details3
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details3
							.setInputType(InputType.TYPE_CLASS_TEXT);
					id_off_name3.setText("");
					off_fname3.setText("");
					off_age3.setText("");
					off_Pnum3.setText("");
					off_address3.setText("");
					break;

				case 6:
					respondent_id_details3.setHint(getLabel3("7"));
					respondent_id_details3.setText("");
					respondent_id_details3
							.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
									16) });
					respondent_id_details3
							.setInputType(InputType.TYPE_CLASS_TEXT);
					id_off_name3.setText("");
					off_fname3.setText("");
					off_age3.setText("");
					off_Pnum3.setText("");
					off_address3.setText("");
					break;
				case 7:
					respondent_id_details3.setHint(getLabel3("8"));
					respondent_id_details3.setText("");
					respondent_id_details3
							.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
									10) });
					respondent_id_details3
							.setInputType(InputType.TYPE_CLASS_NUMBER);
					id_off_name3.setText("");
					off_fname3.setText("");
					off_age3.setText("");
					off_Pnum3.setText("");
					off_address3.setText("");
					break;
				case 8:
					respondent_id_details3.setHint(getLabel3("9"));
					respondent_id_details3.setText("");
					respondent_id_details3
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details3
							.setInputType(InputType.TYPE_CLASS_TEXT);
					id_off_name3.setText("");
					off_fname3.setText("");
					off_age3.setText("");
					off_Pnum3.setText("");
					off_address3.setText("");
					break;
				case 9:
					respondent_id_details3.setHint(getLabel3("10"));
					respondent_id_details3.setText("");
					respondent_id_details3
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details3
							.setInputType(InputType.TYPE_CLASS_TEXT);
					id_off_name3.setText("");
					off_fname3.setText("");
					off_age3.setText("");
					off_Pnum3.setText("");
					off_address3.setText("");
					break;
				default:
					respondent_id_details3.setHint("Nothing Selected");
					break;
				}
			}

			public CharSequence getLabel3(String selected) {
				String label = "";
				try {
					for (String idCode : OffenderActivity.idMap.keySet()) {
						if (idCode.equals(selected)) {
							label = "ENTER "
									+ OffenderActivity.idMap.get(idCode);
							OffenderActivity.selectedID3 = OffenderActivity.idMap
									.get(idCode).toString();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				Log.i("selected label name " + selected, label);
				return label;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

		idMap = new DataBase(getApplicationContext())
				.getIdMap(getApplicationContext());
		list = new ArrayList<String>();
		/* list.add("-Select ID"); */
		for (String idCode : idMap.keySet()) {
			list.add(idMap.get(idCode));
		}
		ArrayAdapter<String> adp4 = new ArrayAdapter<String>(this,
				R.layout.spinner_item, list);
		adp4.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		off_id_options4.setAdapter(adp4);
		off_id_options4.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
				/*
				 * case 0 : respondent_id_details4.setHint("Enter ID Details");
				 * respondent_id_details4.setText(""); id_off_name4.setText("");
				 * off_fname4.setText(""); off_age4.setText("");
				 * off_Pnum4.setText(""); off_address4.setText(""); break;
				 */
				case 0:
					respondent_id_details4.setHint(getLabel4("1"));
					respondent_id_details4.setText("");
					respondent_id_details4
							.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
									16) });
					respondent_id_details4
							.setInputType(InputType.TYPE_CLASS_NUMBER);
					id_off_name4.setText("");
					off_fname4.setText("");
					off_age4.setText("");
					off_Pnum4.setText("");
					off_address4.setText("");

					break;
				case 1:
					respondent_id_details4.setHint(getLabel4("2"));
					respondent_id_details4.setText("");
					respondent_id_details4
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details4
							.setInputType(InputType.TYPE_CLASS_TEXT);
					id_off_name4.setText("");
					off_fname4.setText("");
					off_age4.setText("");
					off_Pnum4.setText("");
					off_address4.setText("");
					break;
				case 2:
					respondent_id_details4.setHint(getLabel4("3"));
					respondent_id_details4.setText("");
					respondent_id_details4
							.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
									10) });
					respondent_id_details4
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details4
							.setInputType(InputType.TYPE_CLASS_TEXT);
					id_off_name4.setText("");
					off_fname4.setText("");
					off_age4.setText("");
					off_Pnum4.setText("");
					off_address4.setText("");
					break;
				case 3:
					respondent_id_details4.setHint(getLabel4("4"));
					respondent_id_details4.setText("");
					respondent_id_details4
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details4
							.setInputType(InputType.TYPE_CLASS_TEXT);
					id_off_name4.setText("");
					off_fname4.setText("");
					off_age4.setText("");
					off_Pnum4.setText("");
					off_address4.setText("");
					break;
				case 4:
					respondent_id_details4.setHint(getLabel4("5"));
					respondent_id_details4.setText("");
					respondent_id_details4
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details4
							.setInputType(InputType.TYPE_CLASS_TEXT);
					id_off_name4.setText("");
					off_fname4.setText("");
					off_age4.setText("");
					off_Pnum4.setText("");
					off_address4.setText("");
					break;
				case 5:
					respondent_id_details4.setHint(getLabel4("6"));
					respondent_id_details4.setText("");
					respondent_id_details4
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details4
							.setInputType(InputType.TYPE_CLASS_TEXT);
					id_off_name4.setText("");
					off_fname4.setText("");
					off_age4.setText("");
					off_Pnum4.setText("");
					off_address4.setText("");
					break;
				case 6:
					respondent_id_details4.setHint(getLabel4("7"));
					respondent_id_details4.setText("");
					respondent_id_details4
							.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
									16) });
					respondent_id_details4
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details4
							.setInputType(InputType.TYPE_CLASS_TEXT);
					id_off_name4.setText("");
					off_fname4.setText("");
					off_age4.setText("");
					off_Pnum4.setText("");
					off_address4.setText("");
					break;
				case 7:
					respondent_id_details4.setHint(getLabel4("8"));
					respondent_id_details4.setText("");
					respondent_id_details4
							.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
									10) });
					respondent_id_details4
							.setInputType(InputType.TYPE_CLASS_NUMBER);
					id_off_name4.setText("");
					off_fname4.setText("");
					off_age4.setText("");
					off_Pnum4.setText("");
					off_address4.setText("");
					break;
				case 8:
					respondent_id_details4.setHint(getLabel4("9"));
					respondent_id_details4.setText("");
					respondent_id_details4
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details4
							.setInputType(InputType.TYPE_CLASS_TEXT);
					id_off_name4.setText("");
					off_fname4.setText("");
					off_age4.setText("");
					off_Pnum4.setText("");
					off_address4.setText("");
					break;
				case 9:
					respondent_id_details4.setHint(getLabel4("10"));
					respondent_id_details4.setText("");
					respondent_id_details4
							.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					respondent_id_details4
							.setInputType(InputType.TYPE_CLASS_TEXT);
					id_off_name4.setText("");
					off_fname4.setText("");
					off_age4.setText("");
					off_Pnum4.setText("");
					off_address4.setText("");
					break;
				default:
					respondent_id_details4.setHint("Nothing Selected");
					break;
				}
			}

			public CharSequence getLabel4(String selected) {
				String label = "";
				try {
					for (String idCode : OffenderActivity.idMap.keySet()) {
						if (idCode.equals(selected)) {
							label = "ENTER "
									+ OffenderActivity.idMap.get(idCode);
							OffenderActivity.selectedID4 = OffenderActivity.idMap
									.get(idCode).toString();

						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				Log.i("selected label name " + selected, label);
				return label;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

		idMap = new DataBase(getApplicationContext())
				.getIdMap(getApplicationContext());
		list = new ArrayList<String>();
		/* list.add("-Select ID"); */
		for (String idCode : idMap.keySet()) {
			list.add(idMap.get(idCode));
		}

		ArrayAdapter<String> adp5 = new ArrayAdapter<String>(this,
				R.layout.spinner_item, list);
		adp5.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		leaser_id_options.setAdapter(adp5);
		leaser_id_options
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						switch (arg2) {
						/*
						 * case 0 :
						 * respondent_id_details.setHint("Enter ID Details");
						 * break;
						 */
						case 0:
							// Toast.makeText(getApplicationContext(),
							// "Please Enter Valid Aadhaar Number",
							// Toast.LENGTH_SHORT).show();
							leaser_id_details.setHint(getLabel("1"));
							leaser_id_details.setText("");
							leaser_id_details
									.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
											12) });
							leaser_id_details
									.setInputType(InputType.TYPE_CLASS_NUMBER);
							leaser_id_details.requestFocus();
							leaser_name.setText("");
							leaser_fname.setText("");
							leaser_age.setText("");
							leaser_mobileNo.setText("");
							leaser_address.setText("");

							break;
						case 1:
							// Toast.makeText(getApplicationContext(),
							// "Please Enter Valid Driving Licence Number",
							// Toast.LENGTH_SHORT).show();
							leaser_id_details.setHint(getLabel("2"));
							leaser_id_details.setText("");
							leaser_id_details
									.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
							leaser_id_details
									.setInputType(InputType.TYPE_CLASS_TEXT);
							leaser_id_details.requestFocus();
							leaser_name.setText("");
							leaser_fname.setText("");
							leaser_age.setText("");
							leaser_mobileNo.setText("");
							leaser_address.setText("");
							leaser_img.setImageResource(R.drawable.photo);
							break;
						case 2:
							// Toast.makeText(getApplicationContext(),
							// "Please Enter Valid Vehicle Number",
							// Toast.LENGTH_SHORT).show();
							leaser_id_details.setHint(getLabel("3"));
							leaser_id_details.setText("");
							leaser_id_details
									.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
											10) });
							leaser_id_details
									.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
							leaser_id_details
									.setInputType(InputType.TYPE_CLASS_TEXT);
							leaser_id_details.requestFocus();
							leaser_name.setText("");
							leaser_fname.setText("");
							leaser_age.setText("");
							leaser_mobileNo.setText("");
							leaser_address.setText("");
							leaser_img.setImageResource(R.drawable.photo);
							break;
						case 3:
							// Toast.makeText(getApplicationContext(),
							// "Please Enter Valid PassPort Number",
							// Toast.LENGTH_SHORT).show();
							leaser_id_details.setHint(getLabel("4"));
							leaser_id_details.setText("");
							leaser_id_details
									.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
							leaser_id_details
									.setInputType(InputType.TYPE_CLASS_TEXT);
							leaser_id_details.requestFocus();
							leaser_name.setText("");
							leaser_fname.setText("");
							leaser_age.setText("");
							leaser_mobileNo.setText("");
							leaser_address.setText("");
							leaser_img.setImageResource(R.drawable.photo);
							break;
						case 4:
							// Toast.makeText(getApplicationContext(),
							// "Please Enter Valid PAN CARD Number",
							// Toast.LENGTH_SHORT).show();
							leaser_id_details.setHint(getLabel("5"));
							leaser_id_details.setText("");
							leaser_id_details
									.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
							leaser_id_details
									.setInputType(InputType.TYPE_CLASS_TEXT);
							leaser_id_details.requestFocus();
							leaser_name.setText("");
							leaser_fname.setText("");
							leaser_age.setText("");
							leaser_mobileNo.setText("");
							leaser_address.setText("");
							leaser_img.setImageResource(R.drawable.photo);
							break;
						case 5:
							// Toast.makeText(getApplicationContext(),
							// "Please Enter Valid Voter ID Number",
							// Toast.LENGTH_SHORT).show();
							leaser_id_details.setHint(getLabel("6"));
							leaser_id_details.setText("");
							leaser_id_details
									.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
							leaser_id_details
									.setInputType(InputType.TYPE_CLASS_TEXT);
							leaser_id_details.requestFocus();
							leaser_name.setText("");
							leaser_fname.setText("");
							leaser_age.setText("");
							leaser_mobileNo.setText("");
							leaser_address.setText("");
							leaser_img.setImageResource(R.drawable.photo);
							break;
						case 6:
							// Toast.makeText(getApplicationContext(),
							// "Please Enter Valid Ration Card Number",
							// Toast.LENGTH_SHORT).show();
							leaser_id_details.setHint(getLabel("7"));
							leaser_id_details
									.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
											16) });
							leaser_id_details
									.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
							leaser_id_details
									.setInputType(InputType.TYPE_CLASS_TEXT);
							leaser_id_details.requestFocus();
							leaser_id_details.setText("");
							leaser_name.setText("");
							leaser_fname.setText("");
							leaser_age.setText("");
							leaser_mobileNo.setText("");
							leaser_address.setText("");
							leaser_img.setImageResource(R.drawable.photo);
							break;
						case 7:
							// Toast.makeText(getApplicationContext(),
							// "Please Enter Valid Mobile Number",
							// Toast.LENGTH_SHORT).show();
							leaser_id_details.setHint(getLabel("8"));
							leaser_id_details
									.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
											10) });
							leaser_id_details
									.setInputType(InputType.TYPE_CLASS_NUMBER);
							leaser_id_details.requestFocus();
							leaser_id_details.setText("");
							leaser_name.setText("");
							leaser_fname.setText("");
							leaser_age.setText("");
							leaser_mobileNo.setText("");
							leaser_address.setText("");
							leaser_img.setImageResource(R.drawable.photo);
							break;
						case 8:
							leaser_id_details.setHint(getLabel("9"));
							leaser_id_details.setText("");
							leaser_id_details
									.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
							leaser_id_details
									.setInputType(InputType.TYPE_CLASS_TEXT);
							leaser_id_details.requestFocus();
							leaser_name.setText("");
							leaser_fname.setText("");
							leaser_age.setText("");
							leaser_mobileNo.setText("");
							leaser_address.setText("");
							leaser_img.setImageResource(R.drawable.photo);
							break;
						case 9:
							leaser_id_details.setHint(getLabel("10"));
							leaser_img.setImageResource(R.drawable.photo);
							leaser_id_details.requestFocus();
							break;
						default:
							leaser_id_details.setHint("Nothing Selected");
							break;
						}
					}

					public CharSequence getLabel(String selected) {
						String label = "";
						try {
							for (String idCode : OffenderActivity.idMap
									.keySet()) {
								if (idCode.equals(selected)) {
									label = "ENTER "
											+ OffenderActivity.idMap
													.get(idCode);
									OffenderActivity.selectedLeaserID = OffenderActivity.idMap
											.get(idCode).toString();
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						Log.i("selected label name " + selected, label);
						return label;
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					}
				});

		idMap = new DataBase(getApplicationContext())
				.getIdMap(getApplicationContext());
		list = new ArrayList<String>();
		/* list.add("-Select ID"); */
		for (String idCode : idMap.keySet()) {
			list.add(idMap.get(idCode));
		}

		ArrayAdapter<String> adp6 = new ArrayAdapter<String>(this,
				R.layout.spinner_item, list);
		adp6.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		manager_id_options.setAdapter(adp6);
		manager_id_options
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						switch (arg2) {
						/*
						 * case 0 :
						 * respondent_id_details.setHint("Enter ID Details");
						 * break;
						 */
						case 0:
							// Toast.makeText(getApplicationContext(),
							// "Please Enter Valid Aadhaar Number",
							// Toast.LENGTH_SHORT).show();
							manager_id_details.setHint(getLabel("1"));
							manager_id_details.setText("");
							manager_id_details
									.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
											12) });
							manager_id_details
									.setInputType(InputType.TYPE_CLASS_NUMBER);
							manager_id_details.requestFocus();
							manager_name.setText("");
							manager_fname.setText("");
							manager_age.setText("");
							manager_mobileNo.setText("");
							manager_address.setText("");

							break;
						case 1:
							// Toast.makeText(getApplicationContext(),
							// "Please Enter Valid Driving Licence Number",
							// Toast.LENGTH_SHORT).show();
							manager_id_details.setHint(getLabel("2"));
							manager_id_details.setText("");
							manager_id_details
									.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
							manager_id_details
									.setInputType(InputType.TYPE_CLASS_TEXT);
							manager_id_details.requestFocus();
							manager_name.setText("");
							manager_fname.setText("");
							manager_age.setText("");
							manager_mobileNo.setText("");
							manager_address.setText("");
							manager_img.setImageResource(R.drawable.photo);
							break;
						case 2:
							// Toast.makeText(getApplicationContext(),
							// "Please Enter Valid Vehicle Number",
							// Toast.LENGTH_SHORT).show();
							manager_id_details.setHint(getLabel("3"));
							manager_id_details.setText("");
							manager_id_details
									.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
											10) });
							manager_id_details
									.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
							manager_id_details
									.setInputType(InputType.TYPE_CLASS_TEXT);
							manager_id_details.requestFocus();
							manager_name.setText("");
							manager_fname.setText("");
							manager_age.setText("");
							manager_mobileNo.setText("");
							manager_address.setText("");
							manager_img.setImageResource(R.drawable.photo);
							break;
						case 3:
							// Toast.makeText(getApplicationContext(),
							// "Please Enter Valid PassPort Number",
							// Toast.LENGTH_SHORT).show();
							manager_id_details.setHint(getLabel("4"));
							manager_id_details.setText("");
							manager_id_details
									.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
							manager_id_details
									.setInputType(InputType.TYPE_CLASS_TEXT);
							manager_id_details.requestFocus();
							manager_name.setText("");
							manager_fname.setText("");
							manager_age.setText("");
							manager_mobileNo.setText("");
							manager_address.setText("");
							manager_img.setImageResource(R.drawable.photo);
							break;
						case 4:
							// Toast.makeText(getApplicationContext(),
							// "Please Enter Valid PAN CARD Number",
							// Toast.LENGTH_SHORT).show();
							manager_id_details.setHint(getLabel("5"));
							manager_id_details.setText("");
							manager_id_details
									.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
							manager_id_details
									.setInputType(InputType.TYPE_CLASS_TEXT);
							manager_id_details.requestFocus();
							manager_name.setText("");
							manager_fname.setText("");
							manager_age.setText("");
							manager_mobileNo.setText("");
							manager_address.setText("");
							manager_img.setImageResource(R.drawable.photo);
							break;
						case 5:
							// Toast.makeText(getApplicationContext(),
							// "Please Enter Valid Voter ID Number",
							// Toast.LENGTH_SHORT).show();
							manager_id_details.setHint(getLabel("6"));
							manager_id_details.setText("");
							manager_id_details
									.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
							manager_id_details
									.setInputType(InputType.TYPE_CLASS_TEXT);
							manager_id_details.requestFocus();
							manager_name.setText("");
							manager_fname.setText("");
							manager_age.setText("");
							manager_mobileNo.setText("");
							manager_address.setText("");
							manager_img.setImageResource(R.drawable.photo);
							break;
						case 6:
							// Toast.makeText(getApplicationContext(),
							// "Please Enter Valid Ration Card Number",
							// Toast.LENGTH_SHORT).show();
							manager_id_details.setHint(getLabel("7"));
							manager_id_details
									.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
											16) });
							manager_id_details
									.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
							manager_id_details
									.setInputType(InputType.TYPE_CLASS_TEXT);
							manager_id_details.requestFocus();
							manager_id_details.setText("");
							manager_name.setText("");
							manager_fname.setText("");
							manager_age.setText("");
							manager_mobileNo.setText("");
							manager_address.setText("");
							manager_img.setImageResource(R.drawable.photo);
							break;
						case 7:
							// Toast.makeText(getApplicationContext(),
							// "Please Enter Valid Mobile Number",
							// Toast.LENGTH_SHORT).show();
							manager_id_details.setHint(getLabel("8"));
							manager_id_details
									.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
											10) });
							manager_id_details
									.setInputType(InputType.TYPE_CLASS_NUMBER);
							manager_id_details.requestFocus();
							manager_id_details.setText("");
							manager_name.setText("");
							manager_fname.setText("");
							manager_age.setText("");
							manager_mobileNo.setText("");
							manager_address.setText("");
							manager_img.setImageResource(R.drawable.photo);
							break;
						case 8:
							manager_id_details.setHint(getLabel("9"));
							manager_id_details.setText("");
							manager_id_details
									.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
							manager_id_details
									.setInputType(InputType.TYPE_CLASS_TEXT);
							manager_id_details.requestFocus();
							manager_name.setText("");
							manager_fname.setText("");
							manager_age.setText("");
							manager_mobileNo.setText("");
							manager_address.setText("");
							manager_img.setImageResource(R.drawable.photo);
							break;
						case 9:
							manager_id_details.setHint(getLabel("10"));
							manager_img.setImageResource(R.drawable.photo);
							manager_id_details.requestFocus();
							break;
						default:
							leaser_id_details.setHint("Nothing Selected");
							break;
						}
					}

					public CharSequence getLabel(String selected) {
						String label = "";
						try {
							for (String idCode : OffenderActivity.idMap
									.keySet()) {
								if (idCode.equals(selected)) {
									label = "ENTER "
											+ OffenderActivity.idMap
													.get(idCode);
									OffenderActivity.selectedManagerID = OffenderActivity.idMap
											.get(idCode).toString();
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						Log.i("selected label name " + selected, label);
						return label;
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					}
				});

		nationality
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					public void onCheckedChanged(RadioGroup arg0, int id) {
						switch (id) {

						case R.id.indian:
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "You are an Indian", Toast.LENGTH_LONG);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("You have Selected Indian");
							list_layout_internatnl.setVisibility(View.GONE);
							list_layout_passport.setVisibility(View.GONE);
							break;

						case R.id.others:
							/*
							 * Toast toast2 =
							 * Toast.makeText(getApplicationContext(),
							 * "Enter your Passport and International driving License number"
							 * , Toast.LENGTH_LONG);
							 * toast2.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView2 = toast2.getView();
							 * toastView2.setBackgroundResource
							 * (R.drawable.toast_background); toast2.show();
							 */
							showToast("Enter Offender's Passport and International Driving Licence Number");
							list_layout_internatnl.setVisibility(View.VISIBLE);
							list_layout_passport.setVisibility(View.VISIBLE);
							id_int_drive_lic.setText("");
							break;
						}
					}
				});

		nationality2
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					public void onCheckedChanged(RadioGroup arg0, int id) {
						switch (id) {

						case R.id.indian2:
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "You are an Indian", Toast.LENGTH_LONG);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("You have Selected Indian");
							list_layout_internatnl2.setVisibility(View.GONE);
							list_layout_passport2.setVisibility(View.GONE);
							break;

						case R.id.others2:
							/*
							 * Toast toast2 =
							 * Toast.makeText(getApplicationContext(),
							 * "Enter your Passport and International driving License number"
							 * , Toast.LENGTH_LONG);
							 * toast2.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView2 = toast2.getView();
							 * toastView2.setBackgroundResource
							 * (R.drawable.toast_background); toast2.show();
							 */
							showToast("Enter Offender's Passport and International Driving Licnece Number");
							list_layout_internatnl2.setVisibility(View.VISIBLE);
							list_layout_passport2.setVisibility(View.VISIBLE);
							id_int_drive_lic2.setText("");
							break;
						}
					}
				});

		nationality3
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					public void onCheckedChanged(RadioGroup arg0, int id) {
						switch (id) {

						case R.id.indian3:
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "You are an Indian", Toast.LENGTH_LONG);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("You have Selected Indian");
							list_layout_internatnl3.setVisibility(View.GONE);
							list_layout_passport3.setVisibility(View.GONE);
							break;

						case R.id.others3:
							/*
							 * Toast toast2 =
							 * Toast.makeText(getApplicationContext(),
							 * "Enter your Passport and International driving License number"
							 * , Toast.LENGTH_LONG);
							 * toast2.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView2 = toast2.getView();
							 * toastView2.setBackgroundResource
							 * (R.drawable.toast_background); toast2.show();
							 */
							showToast("Enter Offender's Passport and International Driving Licence Number ");
							list_layout_internatnl3.setVisibility(View.VISIBLE);
							list_layout_passport3.setVisibility(View.VISIBLE);
							id_int_drive_lic3.setText("");
							break;
						}
					}
				});

		nationality4
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					public void onCheckedChanged(RadioGroup arg0, int id) {
						switch (id) {

						case R.id.indian4:
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "You are an Indian", Toast.LENGTH_LONG);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("You have Selected Indian");
							list_layout_internatnl4.setVisibility(View.GONE);
							list_layout_passport4.setVisibility(View.GONE);
							break;

						case R.id.others4:
							/*
							 * Toast toast2 =
							 * Toast.makeText(getApplicationContext(),
							 * "Enter your Passport and International driving License number"
							 * , Toast.LENGTH_LONG);
							 * toast2.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView2 = toast2.getView();
							 * toastView2.setBackgroundResource
							 * (R.drawable.toast_background); toast2.show();
							 */
							showToast("Enter Offender's Passport and International Driving Licence Number");
							list_layout_internatnl4.setVisibility(View.VISIBLE);
							list_layout_passport4.setVisibility(View.VISIBLE);
							id_int_drive_lic4.setText("");
							break;
						}
					}
				});
		Log.e(ET_Nationality, "NAtionality Display");

		details.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				get_detailsFlg2 = false;
				get_detailsFlg3 = false;
				get_detailsFlg4 = false;
				get_detailsFlg1 = true;
				get_LeaserdetailsFlg = false;
				get_ManagerdetailsFlg = false;
				respondent_id_details.setVisibility(View.VISIBLE);
				get_the_details.setVisibility(View.VISIBLE);
				list_layout_landmark.setVisibility(View.VISIBLE);
				list_layout_nationality.setVisibility(View.VISIBLE);
				list_layout_off_name.setVisibility(View.VISIBLE);
				list_layout_father.setVisibility(View.VISIBLE);
				list_layout_gender.setVisibility(View.VISIBLE);
				list_layout_mobile.setVisibility(View.VISIBLE);
				off_address_layout.setVisibility(View.VISIBLE);
				// list_layout_internatnl.setVisibility(View.GONE);
				// list_layout_passport.setVisibility(View.GONE);
				// list_layout_age.setVisibility(View.VISIBLE);
				/* add_idProof.setVisibility(View.VISIBLE); */
				capture_fpt.setVisibility(View.VISIBLE);
				respondent_img1.setVisibility(View.VISIBLE);
				respondent_id_details2.setText("");
				respondent_id_details3.setText("");
				respondent_id_details4.setText("");
				indian.setChecked(true);
				Log.i("selectedID :::::::::::: ", OffenderActivity.selectedID);

				id1_Spinner = OffenderActivity.selectedID.toString().trim();

				if (OffenderActivity.selectedID.toString().trim()
						.equals("AADHAAR NO")) {
					Log.i("id service called", "AADHAAR NO");
					// AADHAR
					new AsyncGetIDDetails().execute();
				} else if (OffenderActivity.selectedID.toString().trim()
						.equals("DRIVING LICENCE")) {
					Log.i("id service called", "DRIVING LICENCE");
					// DRIVING LICENSE
					new AsyncGetDLDetails().execute();
				} else if (OffenderActivity.selectedID.toString().trim()
						.equals("VEHICLE NO")) {
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
				get_detailsFlg2 = true;
				get_detailsFlg1 = false;
				get_detailsFlg3 = false;
				get_detailsFlg4 = false;
				get_LeaserdetailsFlg = false;
				get_ManagerdetailsFlg = false;
				id2_Spinner = OffenderActivity.selectedID2.toString().trim();
				Log.i("selectedID2 :::::::::::: ", OffenderActivity.selectedID2);
				if (OffenderActivity.selectedID2.toString().trim()
						.equals("AADHAAR NO")) {
					Log.i("id service called", "AADHAAR NO");
					// AADHAR
					new AsyncGetIDDetails().execute();
				} else if (OffenderActivity.selectedID2.toString().trim()
						.equals("DRIVING LICENCE")) {
					Log.i("id service called", "DRIVING LICENCE");
					// DRIVING LICENSE
					new AsyncGetDLDetails().execute();
				} else if (OffenderActivity.selectedID2.toString().trim()
						.equals("VEHICLE NO")) {
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
				get_detailsFlg3 = true;
				get_detailsFlg1 = false;
				get_detailsFlg2 = false;
				get_detailsFlg4 = false;
				get_LeaserdetailsFlg = false;
				get_ManagerdetailsFlg = false;
				id3_Spinner = OffenderActivity.selectedID3.toString().trim();
				Log.i("selectedID3 :::::::::::: ", OffenderActivity.selectedID3);
				if (OffenderActivity.selectedID3.toString().trim()
						.equals("AADHAAR NO")) {
					Log.i("id service called", "AADHAAR NO");
					// AADHAR
					new AsyncGetIDDetails().execute();
				} else if (OffenderActivity.selectedID3.toString().trim()
						.equals("DRIVING LICENCE")) {
					Log.i("id service called", "DRIVING LICENCE");
					// DRIVING LICENSE
					new AsyncGetDLDetails().execute();
				} else if (OffenderActivity.selectedID3.toString().trim()
						.equals("VEHICLE NO")) {
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
				get_detailsFlg4 = true;
				get_detailsFlg1 = false;
				get_detailsFlg3 = false;
				get_detailsFlg2 = false;
				get_LeaserdetailsFlg = false;
				get_ManagerdetailsFlg = false;
				id4_Spinner = OffenderActivity.selectedID4.toString().trim();
				Log.i("selectedID4 :::::::::::: ", OffenderActivity.selectedID4);

				if (OffenderActivity.selectedID4.toString().trim()
						.equals("AADHAAR NO")) {
					Log.i("id service called", "AADHAAR NO");
					// AADHAR
					new AsyncGetIDDetails().execute();
				} else if (OffenderActivity.selectedID4.toString().trim()
						.equals("DRIVING LICENCE")) {
					Log.i("id service called", "DRIVING LICENCE");
					// DRIVING LICENSE
					new AsyncGetDLDetails().execute();
				} else if (OffenderActivity.selectedID4.toString().trim()
						.equals("VEHICLE NO")) {
					Log.i("id service called", "VEHCILE NO");
					// VEHICLLE RC
					new AsyncGetRCDetails().execute();
				}
			}
		});

		get_leaser_details_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				get_detailsFlg2 = false;
				get_detailsFlg3 = false;
				get_detailsFlg4 = false;
				get_detailsFlg1 = false;
				get_LeaserdetailsFlg = true;
				get_ManagerdetailsFlg = false;

				id_Spinner_Leaser = OffenderActivity.selectedLeaserID
						.toString().trim();

				if (OffenderActivity.selectedID.toString().trim()
						.equals("AADHAAR NO")) {
					Log.i("id service called", "AADHAAR NO");
					// AADHAR
					new AsyncGetIDDetails().execute();
				} else if (OffenderActivity.selectedID.toString().trim()
						.equals("DRIVING LICENCE")) {
					Log.i("id service called", "DRIVING LICENCE");
					// DRIVING LICENSE
					new AsyncGetDLDetails().execute();
				} else if (OffenderActivity.selectedID.toString().trim()
						.equals("VEHICLE NO")) {
					Log.i("id service called", "VEHCILE NO");
					// VEHICLLE RC
					new AsyncGetRCDetails().execute();
				}
			}
		});

		get_manager_details_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				get_detailsFlg2 = false;
				get_detailsFlg3 = false;
				get_detailsFlg4 = false;
				get_detailsFlg1 = false;
				get_LeaserdetailsFlg = false;
				get_ManagerdetailsFlg = true;
				respondent_id_details.setVisibility(View.VISIBLE);

				id_Spinner_Manager = OffenderActivity.selectedManagerID
						.toString().trim();

				if (OffenderActivity.selectedID.toString().trim()
						.equals("AADHAAR NO")) {
					Log.i("id service called", "AADHAAR NO");
					// AADHAR
					new AsyncGetIDDetails().execute();
				} else if (OffenderActivity.selectedID.toString().trim()
						.equals("DRIVING LICENCE")) {
					Log.i("id service called", "DRIVING LICENCE");
					// DRIVING LICENSE
					new AsyncGetDLDetails().execute();
				} else if (OffenderActivity.selectedID.toString().trim()
						.equals("VEHICLE NO")) {
					Log.i("id service called", "VEHCILE NO");
					// VEHICLLE RC
					new AsyncGetRCDetails().execute();
				}
			}
		});

		/*
		 * id_proof_options = (Spinner)findViewById(R.id.id_proof_options);
		 * id_proof_options.setOnItemSelectedListener(new
		 * OnItemSelectedListener() {
		 * 
		 * @Override public void onItemSelected(AdapterView<?> arg0, View arg1,
		 * int position, long arg3){ // TODO Auto-generated method stub if
		 * (position==0) { } else if (position==1) { } }
		 * 
		 * @Override public void onNothingSelected(AdapterView<?> parent) { //
		 * TODO Auto-generated method stub
		 * 
		 * } });
		 */

		capture_fpt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				/*
				 * try {
				 * 
				 * BluetoothComm btcomm = new
				 * BluetoothComm(Act_BTDiscovery.DEVICE_NAME);
				 * 
				 * 
				 * Log.i("BluetoothComm.mosOut   :::", BluetoothComm.mosOut
				 * +""); if(BluetoothComm.mosOut==null &&
				 * BluetoothComm.misIn==null){
				 * Toast.makeText(getApplicationContext(),
				 * "Please Set Bluetooth Connection",
				 * Toast.LENGTH_SHORT).show(); }else{
				 */
				Intent fpt = new Intent(getApplicationContext(),
						FPT_Activity.class);
				startActivity(fpt);
				/*
				 * }
				 * 
				 * } catch(NullPointerException e) { e.printStackTrace(); }
				 */
			}
		});

		otp_btn_num.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// get prompts.xml view
				mob_noFlg1 = true;
				if (validateMobileNo(off_Pnum.getText().toString())) {
					new AsynchOTP().execute();
				} else {
					new AlertDialog.Builder(OffenderActivity.this)
							.setTitle("Mobile Number Status")
							.setMessage("Please Enter Valid Mobile Number")
							.setPositiveButton("Ok",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											// Your code

										}
									}).show();
				}
			}
		});

		otp_btn_num2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mob_noFlg2 = true;
				if (validateMobileNo(off_Pnum2.getText().toString())) {
					new AsynchOTP().execute();
				} else {
					new AlertDialog.Builder(OffenderActivity.this)
							.setTitle("Mobile Number Status")
							.setMessage("Please Enter Valid Mobile Number")
							.setPositiveButton("Ok",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											// Your code

										}
									}).show();
				}
			}
		});
		otp_btn_num3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mob_noFlg3 = true;
				if (validateMobileNo(off_Pnum3.getText().toString())) {
					new AsynchOTP().execute();
				} else {
					new AlertDialog.Builder(OffenderActivity.this)
							.setTitle("Mobile Number Status")
							.setMessage("Please Enter Valid Mobile Number")
							.setPositiveButton("Ok",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											// Your code

										}
									}).show();
				}
			}
		});
		otp_btn_num4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mob_noFlg4 = true;
				if (validateMobileNo(off_Pnum4.getText().toString())) {
					new AsynchOTP().execute();
				} else {
					new AlertDialog.Builder(OffenderActivity.this)
							.setTitle("Mobile Number Status")
							.setMessage("Please Enter Valid Mobile Number")
							.setPositiveButton("Ok",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											// Your code

										}
									}).show();
				}
			}
		});

		/*id_off_name.setFocusable(true);*/
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
	}

	/*********************** Mobile Number Validation Method ***************************/
	protected boolean validateMobileNo(String mobileNo) {
		boolean flg = false;
		try {
			if (mobileNo != null
					&& mobileNo.trim().length() == 10
					&& ("7".equals(mobileNo.trim().substring(0, 1))
							|| "8".equals(mobileNo.trim().substring(0, 1)) || "9"
								.equals(mobileNo.trim().substring(0, 1)))) {
				flg = true;
			} else if (mobileNo != null && mobileNo.trim().length() == 11
					&& "0".equals(mobileNo.trim().substring(0, 1))) {
				flg = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			flg = false;

		}
		Log.i("mobile number", mobileNo + " Length " + mobileNo.trim().length()
				+ "1 letter" + mobileNo.trim().substring(0, 1));
		Log.i("mobile verify flag", flg + "");

		return flg;
	}

	/*********************** Mobile Number Validation Method Ends ***************************/

	class AsynchOTP extends AsyncTask<Void, Void, String> {
		ProgressDialog dialog = new ProgressDialog(OffenderActivity.this);

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showDialog(PROGRESS_DIALOG);
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
			/*
			 * dialog.setContentView(R.layout.custom_progress_dialog);
			 * dialog.setCancelable(false); dialog.show();
			 */
		}

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				String ip = "";
				SQLiteDatabase db = openOrCreateDatabase(
						DataBase.DATABASE_NAME, MODE_PRIVATE, null);
				String selectQuery = "SELECT  * FROM " + DataBase.IP_TABLE;
				// SQLiteDatabase db = this.getWritableDatabase();
				Cursor cursor = db.rawQuery(selectQuery, null);
				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						Log.i("DATABASE   IP VALUE :", "" + cursor.getString(0));
						ip = cursor.getString(0);
					} while (cursor.moveToNext());
				}
				db.close();

				SoapObject otp_request = new SoapObject(NAMESPACE, "sendOTP");
				Log.i("Mobile No :::", off_Pnum.getText().toString());
				Log.i("Date :::", id_date.getText().toString());
				if (mob_noFlg1) {
					otp_request.addProperty("mobileNo", off_Pnum.getText()
							.toString());
					otp_request.addProperty("date", id_date.getText()
							.toString());
				}
				if (mob_noFlg2) {
					otp_request.addProperty("mobileNo", off_Pnum2.getText()
							.toString());
					otp_request.addProperty("date", id_date.getText()
							.toString());
				}
				if (mob_noFlg3) {
					otp_request.addProperty("mobileNo", off_Pnum3.getText()
							.toString());
					otp_request.addProperty("date", id_date.getText()
							.toString());
				}
				if (mob_noFlg4) {
					otp_request.addProperty("mobileNo", off_Pnum4.getText()
							.toString());
					otp_request.addProperty("date", id_date.getText()
							.toString());
				}
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(otp_request);

				Log.i("otp_request", "" + otp_request);
				Log.i("WebService.SOAP_ADDRESS ::::::::::::::", ""
						+ WebService.SOAP_ADDRESS);
				try {
					HttpTransportSE androidHttpTransport = new HttpTransportSE(
							ip
									+ "/HydPettyCaseService/services/PettyCaseServiceImpl?wsdl");
					Log.i("androidHttpTransport", "" + androidHttpTransport);
					androidHttpTransport.call(SOAP_ACTION_OTP, envelope);
					Object result = envelope.getResponse();
					OtpStatus = result.toString();
					otp_no = OtpStatus.toString();
					Log.i("**OTP response***", "" + OtpStatus);

					if (OtpStatus != null) {
						otpFLG = true;
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (java.lang.NumberFormatException e) {

			}
			return null;
		}

		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// dialog.dismiss();
			removeDialog(PROGRESS_DIALOG);
			if (!isNetworkAvailable()) {
				// do something
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						OffenderActivity.this);
				alertDialogBuilder
						.setMessage(
								"    Mobile Data is Disabled in your Device \n            Please Enable Mobile Data?")
						.setCancelable(false)
						.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
									}
								});
				AlertDialog alert = alertDialogBuilder.create();
				alert.show();
			}

			LayoutInflater li = LayoutInflater.from(context);
			View promptsView = li.inflate(R.layout.activity_otp, null);

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					context);
			// set prompts.xml to alertdialog builder
			alertDialogBuilder.setView(promptsView);
			final EditText userInput = (EditText) promptsView
					.findViewById(R.id.otp_input);

			alertDialogBuilder
					.setTitle("Confirm OTP")
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// get user input and set it to result
									// edit text
									otp_input_no = userInput.getText()
											.toString();

									Log.i("OTP Verify ::", otp_input_no);
									if (otp_no.equals(otp_input_no)) {
										otpFLG = true;
										verified_status = "Y";
										Log.i("Verify Status :::",
												verified_status);
									} else {
										verified_status = "N";
										Log.i("Verify Status :::",
												verified_status);
									}
									new AsyncVerifyOtp().execute();
								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});
			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
			// show it
			alertDialog.show();
		}

	}

	class AsyncVerifyOtp extends AsyncTask<Void, Void, String> {
		ProgressDialog dialog = new ProgressDialog(OffenderActivity.this);

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showDialog(PROGRESS_DIALOG);
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
			/*
			 * dialog.setContentView(R.layout.custom_progress_dialog);
			 * dialog.setCancelable(false); dialog.show();
			 */
		}

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				String ip = "";
				SQLiteDatabase db = openOrCreateDatabase(
						DataBase.DATABASE_NAME, MODE_PRIVATE, null);
				String selectQuery = "SELECT  * FROM " + DataBase.IP_TABLE;
				// SQLiteDatabase db = this.getWritableDatabase();
				Cursor cursor = db.rawQuery(selectQuery, null);
				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						Log.i("DATABASE   IP VALUE :", "" + cursor.getString(0));
						ip = cursor.getString(0);
					} while (cursor.moveToNext());
				}
				db.close();

				SoapObject verify_otp_request = new SoapObject(NAMESPACE,
						"verifyOTP");
				Log.i("Mobile No :::", off_Pnum.getText().toString());
				Log.i("Date :::", id_date.getText().toString());
				Log.i("otp_input :::", otp_input_no);
				if (mob_noFlg1) {
					verify_otp_request.addProperty("mobileNo", off_Pnum
							.getText().toString());
					verify_otp_request.addProperty("date", id_date.getText()
							.toString());
					verify_otp_request.addProperty("otp", otp_input_no);
					verify_otp_request.addProperty("verify_status",
							verified_status);
				}
				if (mob_noFlg2) {
					verify_otp_request.addProperty("mobileNo", off_Pnum2
							.getText().toString());
					verify_otp_request.addProperty("date", id_date.getText()
							.toString());
					verify_otp_request.addProperty("otp", otp_input_no);
					verify_otp_request.addProperty("verify_status",
							verified_status);
				}
				if (mob_noFlg3) {
					verify_otp_request.addProperty("mobileNo", off_Pnum3
							.getText().toString());
					verify_otp_request.addProperty("date", id_date.getText()
							.toString());
					verify_otp_request.addProperty("otp", otp_input_no);
					verify_otp_request.addProperty("verify_status",
							verified_status);
				}
				if (mob_noFlg4) {
					verify_otp_request.addProperty("mobileNo", off_Pnum4
							.getText().toString());
					verify_otp_request.addProperty("date", id_date.getText()
							.toString());
					verify_otp_request.addProperty("otp", otp_input_no);
					verify_otp_request.addProperty("verify_status",
							verified_status);
				}

				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(verify_otp_request);

				Log.i("otp_request", "" + verify_otp_request);
				Log.i("WebService.SOAP_ADDRESS ::::::::::::::", ""
						+ WebService.SOAP_ADDRESS);
				try {
					HttpTransportSE androidHttpTransport = new HttpTransportSE(
							ip
									+ "/HydPettyCaseService/services/PettyCaseServiceImpl?wsdl");
					Log.i("androidHttpTransport", "" + androidHttpTransport);
					androidHttpTransport.call(SOAP_ACTION_VERIFYOTP, envelope);
					Object result = envelope.getResponse();
					VerifyOtpStatus = result.toString();
					Log.i("**Verify OTP response***", "" + VerifyOtpStatus);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (java.lang.NumberFormatException e) {

			}
			return null;
		}

		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (VerifyOtpStatus.equals("NA")) {
				// dialog.dismiss();
				removeDialog(PROGRESS_DIALOG);
				new AlertDialog.Builder(OffenderActivity.this)
						.setTitle("OTP Status")
						.setMessage("Invalid OTP")
						.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										// Your code
										LayoutInflater li = LayoutInflater
												.from(context);
										View promptsView = li.inflate(
												R.layout.activity_otp, null);
										AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
												context);
										alertDialogBuilder.setView(promptsView);
										final EditText userInput = (EditText) promptsView
												.findViewById(R.id.otp_input);
										alertDialogBuilder
												.setTitle("Confirm OTP")
												.setCancelable(false)
												.setPositiveButton(
														"OK",
														new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int id) {
																otp_input_no = userInput
																		.getText()
																		.toString();
																Log.i("OTP Verify ::",
																		otp_input_no);
																if (otp_no
																		.equals(otp_input_no)) {
																	verified_status = "Y";
																	Log.i("Verify Status :::",
																			verified_status);
																} else {
																	verified_status = "N";
																	Log.i("Verify Status :::",
																			verified_status);
																}
																new AsyncVerifyOtp()
																		.execute();
															}
														})
												.setNegativeButton(
														"Cancel",
														new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int id) {
																dialog.cancel();
															}
														});
										AlertDialog alertDialog = alertDialogBuilder
												.create();
										alertDialog.show();
									}
								}).show();

			} else if (VerifyOtpStatus.equals("0")) {
				removeDialog(PROGRESS_DIALOG);
				new AlertDialog.Builder(OffenderActivity.this)
						.setTitle("OTP Status")
						.setMessage("Invalid OTP")
						.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										// Your code
										LayoutInflater li = LayoutInflater
												.from(context);
										View promptsView = li.inflate(
												R.layout.activity_otp, null);
										AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
												context);
										alertDialogBuilder.setView(promptsView);
										final EditText userInput = (EditText) promptsView
												.findViewById(R.id.otp_input);
										alertDialogBuilder
												.setTitle("Confirm OTP")
												.setCancelable(false)
												.setPositiveButton(
														"OK",
														new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int id) {
																otp_input_no = userInput
																		.getText()
																		.toString();
																Log.i("OTP Verify ::",
																		otp_input_no);
																if (otp_no
																		.equals(otp_input_no)) {
																	verified_status = "Y";
																	Log.i("Verify Status :::",
																			verified_status);
																} else {
																	verified_status = "N";
																	Log.i("Verify Status :::",
																			verified_status);
																}
																new AsyncVerifyOtp()
																		.execute();
															}
														})
												.setNegativeButton(
														"Cancel",
														new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int id) {
																dialog.cancel();
															}
														});
										AlertDialog alertDialog = alertDialogBuilder
												.create();
										alertDialog.show();
									}
								}).show();

			} else if (VerifyOtpStatus.equals("1")) {

				removeDialog(PROGRESS_DIALOG);
				new AlertDialog.Builder(OffenderActivity.this)
						.setTitle("OTP Status")
						.setMessage("OTP Verified Successfully")
						.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										// Your code
										otpFLG = true;
									}

								}).show();
			}
		}
	}

	@SuppressWarnings("unused")
	private String GetAddress() {
		new AsynchGetAddress().execute();
		return null;

	}

	/*
	 * class AsyncGeneratePettycase extends AsyncTask<Void,Void, String>{
	 * ProgressDialog dialog = new ProgressDialog(OffenderActivity.this);
	 * 
	 * @Override protected String doInBackground(Void... params) {
	 * Log.i("generate method called ", "YES");
	 * 
	 * 
	 * String pidCode=""; String pidName=""; String psCode=""; String psName="";
	 * String cadreCd=""; String cadreName=""; String unitCd=""; String
	 * unitName="";
	 * 
	 * try { String ip="";
	 * 
	 * Log.i("ET_gender before ", "YES");
	 * Log.i("ET_gender 1 ",group1_text.getText
	 * ()!=null?group1_text.getText().toString():"");
	 * Log.i("ET_gender 2 ",group2_text
	 * .getText()!=null?group2_text.getText().toString():"");
	 * Log.i("ET_gender 3 "
	 * ,group3_text.getText()!=null?group3_text.getText().toString():"");
	 * Log.i("ET_gender 4 "
	 * ,group4_text.getText()!=null?group4_text.getText().toString():"");
	 * Log.i("ET_gender after ", "YES");
	 * 
	 * try {
	 * 
	 * 
	 * SQLiteDatabase db =
	 * openOrCreateDatabase(DataBase.DATABASE_NAME,MODE_PRIVATE, null); String
	 * selectQuery = "SELECT  * FROM " + DataBase.LOGIN_TABLE; // SQLiteDatabase
	 * db = this.getWritableDatabase(); Cursor cursor = db.rawQuery(selectQuery,
	 * null); // looping through all rows and adding to list
	 * 
	 * if (cursor.moveToFirst()) { do { pidCode = cursor.getString(0); pidName =
	 * cursor.getString(1); psCode = cursor.getString(2); psName =
	 * cursor.getString(3); cadreCd = cursor.getString(4); cadreName =
	 * cursor.getString(5); unitCd = cursor.getString(6); unitName =
	 * cursor.getString(7);
	 * 
	 * } while (cursor.moveToNext()); } db.close(); } catch (Exception e) {
	 * e.printStackTrace();
	 * 
	 * }
	 * 
	 * try { SQLiteDatabase db =
	 * openOrCreateDatabase(DataBase.DATABASE_NAME,MODE_PRIVATE, null); String
	 * selectQuery = "SELECT  * FROM " + DataBase.IP_TABLE; // SQLiteDatabase db
	 * = this.getWritableDatabase(); Cursor cursor = db.rawQuery(selectQuery,
	 * null); // looping through all rows and adding to list if
	 * (cursor.moveToFirst()) { do { Log.i("DATABASE   IP VALUE :",""+
	 * cursor.getString(0)); ip=cursor.getString(0); } while
	 * (cursor.moveToNext()); } db.close();
	 * 
	 * int i=1; Log.i("Add_SectionsActivity.secMap size ::",""+
	 * Add_SectionsActivity.secMap.size());
	 * Log.i("Add_SectionsActivity.checkedList size ::",""+
	 * Add_SectionsActivity.checkedList.size());
	 * 
	 * for (String secCode:Add_SectionsActivity.secMap.keySet()){ List<String>
	 * secDesc = Add_SectionsActivity.checkedList; for(String secDesction :
	 * secDesc){
	 * if(Add_SectionsActivity.secMap.get(secCode).equals(secDesction)){
	 * System.out.println("selected secDesction : " + secDesction +
	 * " map value : : " + Add_SectionsActivity.secMap.get(secCode)); if(i==1){
	 * secBuffer.append(secCode+":"+secDesction);
	 * 
	 * secBufferPreivew.append(secDesction); }else{
	 * secBuffer.append("@"+secCode+":"+secDesction);
	 * secBufferPreivew.append("\n"+secDesction); }
	 * 
	 * // previewrespondentBuf.append(secCode+":"+secDesction); i++; } } } //
	 * Toast.makeText(getApplicationContext(), secBuffer, 1000).show();
	 * Log.i("selected Sections :: ::",""+ secBuffer.toString());
	 * 
	 * ArrayList<String> seizedItemsList =SiezedItemsActivity.Ditenditems;
	 * Log.i("seizedItems size : ",""+seizedItemsList.size() ); for(String
	 * siezedItem:seizedItemsList){ Log.i("siezedItem :",siezedItem);
	 * seizedItems.append(siezedItem.toString()).append("@"); }
	 * Log.i("seizedItems",seizedItems.toString() ); // INSERT ID PROOF DETAILS
	 * START SoapObject request = new SoapObject(NAMESPACE,
	 * "generatePettyCase");
	 * System.out.println("off name :::::::::::::"+id_off_name
	 * .getText().toString()); // Log.i("shop_name",ET_Shop );
	 * 
	 * // Respondent Details String ET_RespondentDetails = "NA"; String
	 * ET_ID_RespondentDetails = respondent_id_details.getText().toString();
	 * 
	 * try { JSONObject respondent1 = new JSONObject(); respondent1.put("SNO",
	 * "1"); respondent1.put("ET_Name", id_off_name.getText().toString());
	 * respondent1.put("ET_Fname", off_fname.getText().toString());
	 * respondent1.put("ET_DOB", off_age.getText().toString());
	 * respondent1.put("ET_gender",
	 * group1_text.getText()!=null?group1_text.getText().toString():"");
	 * respondent1.put("ET_Mobile", off_Pnum.getText().toString());
	 * respondent1.put("ET_address", off_address.getText().toString());
	 * respondent1.put("ET_fps",
	 * getBase64ApacheCommonsEncodedString(OffenderActivity.bufvalue1));
	 * //respondent1.put("ET_fps",
	 * OffenderActivity.bufvalue1!=null?OffenderActivity.bufvalue1:null);
	 * respondent1.put("ET_id_details",ET_ID_RespondentDetails);
	 * respondent1.put("ET_id_code", getIDCode(id1_Spinner));
	 * respondent1.put("ET_image", getBase64EncodedString(image1ByteArray)); //
	 * Log.i("ET_image 1 ::", ""+getBase64EncodedString(image1ByteArray));
	 * Log.i("FPS Value ::::::::: :: ",OffenderActivity.bufvalue1+"");
	 * if(id_off_name.getText()!=null &&
	 * id_off_name.getText().toString().trim().length()>3){
	 * previewrespondentBuf.
	 * append("\n Respondant 1 Name : ").append(id_off_name.
	 * getText().toString());
	 * previewrespondentBuf.append("\n Respondant 1 Father Name : "
	 * ).append(off_fname.getText().toString());
	 * previewrespondentBuf.append("\n Respondant 1 DOB : "
	 * ).append(off_age.getText().toString());
	 * previewrespondentBuf.append("\n Respondant 1 Gender : "
	 * ).append(group1_text.getText().toString());
	 * previewrespondentBuf.append("\n Respondant 1 Mobile : "
	 * ).append(off_Pnum.getText().toString());
	 * previewrespondentBuf.append("\n Respondant 1 Address : "
	 * ).append(off_address.getText().toString());
	 * //previewrespondentBuf.append("Respondant 1 FPS :").append(); }
	 * //**************************************** JSONObject respondent2 = new
	 * JSONObject(); respondent2.put("SNO", "2"); respondent2.put("ET_Name",
	 * id_off_name2.getText().toString()); respondent2.put("ET_Fname",
	 * off_fname2.getText().toString()); respondent2.put("ET_DOB",
	 * off_age2.getText().toString()); respondent2.put("ET_gender",
	 * group2_text.getText()!=null?group2_text.getText().toString():"");
	 * respondent2.put("ET_Mobile", off_Pnum2.getText().toString());
	 * respondent2.put("ET_address", off_address2.getText().toString());
	 * respondent2.put("ET_fps",
	 * getBase64ApacheCommonsEncodedString(OffenderActivity.bufvalue2));
	 * //respondent2.put("ET_fps",
	 * OffenderActivity.bufvalue2!=null?OffenderActivity.bufvalue2:null);
	 * respondent2.put("ET_id_details",
	 * respondent_id_details2.getText().toString());
	 * respondent2.put("ET_id_code", getIDCode2(id2_Spinner));
	 * respondent2.put("ET_image", getBase64EncodedString(image2ByteArray));
	 * //Log.i("ET_image 2 ::", ""+getBase64EncodedString(image2ByteArray));
	 * //Log.i("id1_Spinner 2 value ::::::::: ::", ""+id2_Spinner);
	 * if(id_off_name2.getText()!=null &&
	 * id_off_name2.getText().toString().trim().length()>3){
	 * previewrespondentBuf
	 * .append("\n Respondant 2 Name : ").append(id_off_name2
	 * .getText().toString());
	 * previewrespondentBuf.append("\n Respondant 2 Father Name : "
	 * ).append(off_fname2.getText().toString());
	 * previewrespondentBuf.append("\n Respondant 2 DOB : "
	 * ).append(off_age2.getText().toString());
	 * previewrespondentBuf.append("\n Respondant 2 Gender : "
	 * ).append(group2_text.getText().toString());
	 * previewrespondentBuf.append("\n Respondant 2 Mobile : "
	 * ).append(off_Pnum2.getText().toString());
	 * previewrespondentBuf.append("\n Respondant 2 Address : "
	 * ).append(off_address2.getText().toString());
	 * //previewrespondentBuf.append("Respondant 2 FPS :").append(); }
	 * 
	 * //***********************************
	 * 
	 * JSONObject respondent3 = new JSONObject(); respondent3.put("SNO", "3");
	 * respondent3.put("ET_Name", id_off_name3.getText().toString());
	 * respondent3.put("ET_Fname", off_fname3.getText().toString());
	 * respondent3.put("ET_DOB", off_age3.getText().toString());
	 * respondent3.put("ET_gender",
	 * group3_text.getText()!=null?group3_text.getText().toString():"");
	 * respondent3.put("ET_Mobile", off_Pnum3.getText().toString());
	 * respondent3.put("ET_address", off_address3.getText().toString());
	 * respondent3
	 * .put("ET_fps",getBase64ApacheCommonsEncodedString(OffenderActivity
	 * .bufvalue3)); respondent3.put("ET_id_details",
	 * respondent_id_details3.getText().toString());
	 * respondent3.put("ET_id_code", getIDCode3(id3_Spinner));
	 * respondent3.put("ET_image", getBase64EncodedString(image3ByteArray));
	 * //Log.i("id1_Spinner 2 value ::::::::: ::", ""+id3_Spinner);
	 * if(id_off_name3.getText()!=null &&
	 * id_off_name3.getText().toString().trim().length()>3){
	 * previewrespondentBuf
	 * .append("\n Respondant 3 Name :").append(id_off_name3.
	 * getText().toString());
	 * previewrespondentBuf.append("\n Respondant 3 Father Name :"
	 * ).append(off_fname3.getText().toString());
	 * previewrespondentBuf.append("\n Respondant 3 DOB :"
	 * ).append(off_age3.getText().toString());
	 * previewrespondentBuf.append("\n Respondant 3 Gender :"
	 * ).append(group3_text.getText().toString());
	 * previewrespondentBuf.append("\n Respondant 3 Mobile :"
	 * ).append(off_Pnum3.getText().toString());
	 * previewrespondentBuf.append("\n Respondant 3 Address :"
	 * ).append(off_address3.getText().toString());
	 * //previewrespondentBuf.append("Respondant 2 FPS :").append(); }
	 * 
	 * JSONObject respondent4 = new JSONObject(); respondent4.put("SNO", "4");
	 * respondent4.put("ET_Name", id_off_name4.getText().toString());
	 * respondent4.put("ET_Fname", off_fname4.getText().toString());
	 * respondent4.put("ET_DOB", off_age4.getText().toString());
	 * respondent4.put("ET_gender",
	 * group4_text.getText()!=null?group4_text.getText().toString():"");
	 * respondent4.put("ET_Mobile", off_Pnum4.getText().toString());
	 * respondent4.put("ET_address", off_address4.getText().toString());
	 * respondent4.put("ET_fps",
	 * getBase64ApacheCommonsEncodedString(OffenderActivity.bufvalue4));
	 * respondent4.put("ET_id_details",
	 * respondent_id_details4.getText().toString());
	 * respondent4.put("ET_id_code", getIDCode4(id4_Spinner));
	 * respondent4.put("ET_image", getBase64EncodedString(image4ByteArray));
	 * //Log.i("id1_Spinner 2 value ::::::::: ::", ""+id4_Spinner);
	 * if(id_off_name4.getText()!=null &&
	 * id_off_name4.getText().toString().trim().length()>3){
	 * previewrespondentBuf
	 * .append("\n Respondant 4 Name :").append(id_off_name4.
	 * getText().toString());
	 * previewrespondentBuf.append("\n Respondant 4 Father Name :"
	 * ).append(off_fname4.getText().toString());
	 * previewrespondentBuf.append("\n Respondant 4 DOB :"
	 * ).append(off_age4.getText().toString());
	 * previewrespondentBuf.append("\n Respondant 4 Gender :"
	 * ).append(group4_text.getText().toString());
	 * previewrespondentBuf.append("\n Respondant 4 Mobile :"
	 * ).append(off_Pnum4.getText().toString());
	 * previewrespondentBuf.append("\n Respondant 4 Address :"
	 * ).append(off_address4.getText().toString());
	 * //previewrespondentBuf.append("Respondant 2 FPS :").append(); }
	 * 
	 * JSONArray idDetails = new JSONArray(); idDetails.put(respondent1);
	 * idDetails.put(respondent2); idDetails.put(respondent3);
	 * idDetails.put(respondent4);
	 * 
	 * JSONObject respondentObj = new JSONObject();
	 * respondentObj.put("Respondents", idDetails); ET_RespondentDetails =
	 * respondentObj.toString();
	 * Log.i("ET_RespondentDetails ::",ET_RespondentDetails);
	 * Log.i("Respondent ID Details ::"
	 * ,respondent_id_details.getText().toString()); } catch (JSONException e) {
	 * // TODO Auto-generated catch block e.printStackTrace(); }
	 * 
	 * //previewrespondentBuf.append(WitnessFormActivity.previewWitnessPromtBuf.
	 * toString()); //previewrespondentBuf.append(seizedItems.toString());
	 * 
	 * 
	 * String ET_Name = id_off_name.getText().toString(); String ET_Fname =
	 * off_fname.getText().toString(); String ET_Age =
	 * off_age.getText().toString(); String ET_Mobile =
	 * off_Pnum.getText().toString(); String ET_address =
	 * off_address.getText().toString(); String ET_Shop =
	 * shop_name.getText().toString(); String ET_shopOwnerName =
	 * shop_Ownername.getText().toString(); String ET_Tin =
	 * tin_no.getText().toString(); String ET_date =
	 * id_date.getText().toString(); String ET_Landmark =
	 * id_landmark.getText().toString(); String ET_PassportNo =
	 * id_passport_no.getText().toString(); String ET_International_driv =
	 * id_int_drive_lic.getText().toString(); String ET_pscode =
	 * (UserLogin_Activity.Ps_code!=null?UserLogin_Activity.Ps_code:""); String
	 * ET_Lattitude = ""; String ET_Longitude ="";
	 * 
	 * 
	 * TelephonyManager telephonyManager =
	 * (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
	 * WifiManager manager =
	 * (WifiManager)getSystemService(Context.WIFI_SERVICE); // check if GPS
	 * enabled if(gps.canGetLocation()){ double latitude = gps.getLatitude();
	 * double longitude = gps.getLongitude(); ET_Lattitude=latitude+"";
	 * ET_Longitude=longitude+""; }else{ gps.showSettingsAlert();
	 * 
	 * } // Log.i("image1ByteArray ::",
	 * ""+getBase64EncodedString(image1ByteArray)); //
	 * Log.i("image2ByteArray ::", ""+getBase64EncodedString(image2ByteArray));
	 * byte[] seizedImageInbyteArray
	 * =SiezedItemsActivity.seizedImageInbyteArray;
	 * //Log.i("seizedImageInbyteArray ::",
	 * ""+getBase64EncodedString(seizedImageInbyteArray));
	 * 
	 * Log.i("Respondent 1 FPS bye[]",""+ FPT_Activity.fpsmap1.get("FPS1"));
	 * Log.i("Respondent 2 FPS bye[]",""+ FPT_Activity.fpsmap2.get("FPS2"));
	 * Log.i("Respondent 3 FPS bye[]",""+ FPT_Activity.fpsmap3.get("FPS3"));
	 * Log.i("Respondent 4 FPS bye[]",""+ FPT_Activity.fpsmap4.get("FPS4"));
	 * 
	 * Log.i("WitnessFormActivity.ET_WitnessDetails  ::",
	 * WitnessFormActivity.ET_WitnessDetails ); String ET_IMEI =
	 * telephonyManager.getDeviceId().toString(); String ET_SIM =
	 * telephonyManager.getSimSerialNumber().toString(); String ET_MAC =
	 * manager.getConnectionInfo().getMacAddress().toString();
	 * Log.i("ET_Lattitude ::", ET_Lattitude); Log.i("ET_Longitude ::",
	 * ET_Longitude); Log.i("ET_IMEI ::", ET_IMEI); Log.i("ET_SIM ::", ET_SIM);
	 * Log.i("ET_MAC ::", ET_MAC); Log.i("PS NAme ::", psName);
	 * Log.i("PS Code ::", psCode); Log.i("Pid Code ::", pidCode);
	 * Log.i("Pid Name ::", pidName);
	 * 
	 * request.addProperty("shopName", ET_Shop); request.addProperty("shopTIN",
	 * ET_Tin); request.addProperty("shopOwnerName",ET_shopOwnerName);
	 * request.addProperty("shopAddress",ET_Shop!=null?ET_address:"");
	 * request.addProperty
	 * ("gender",group1_text.getText()!=null?group1_text.getText
	 * ().toString():""); request.addProperty("offenceDt", ET_date);
	 * request.addProperty("offdrName", ET_Name);
	 * request.addProperty("offdrFName", ET_Fname);
	 * request.addProperty("offdrDOB", ET_Age);
	 * request.addProperty("offdrAddress", ET_address);
	 * request.addProperty("offdrAge", ET_Age); request.addProperty("offdrHNO",
	 * ""); request.addProperty("offdrStreet", "");
	 * request.addProperty("offdrVillage", "");
	 * request.addProperty("offdrMandal", ""); request.addProperty("offdrCity",
	 * ""); request.addProperty("offdrDist", "");
	 * request.addProperty("offdrState", ""); request.addProperty("offdrNation",
	 * ET_Nationality); request.addProperty("offgender", ET_gender);
	 * request.addProperty("offdrPIN", "");
	 * request.addProperty("offdrQualification", "");
	 * request.addProperty("offdrOccupation", "");
	 * request.addProperty("gpsDate", ""); request.addProperty("gpsLatti",
	 * ET_Lattitude); request.addProperty("gpsLongi", ET_Longitude);
	 * request.addProperty("offenceLocation", "");
	 * request.addProperty("offenceLandMark", ET_Landmark);
	 * request.addProperty("offdrImage", "");
	 * request.addProperty("offdrMobileNo",ET_Mobile);
	 * request.addProperty("offdrContactNo","");
	 * request.addProperty("stateCode", ""); request.addProperty("unitCode",
	 * unitCd); request.addProperty("unitName", unitName);
	 * request.addProperty("psCode",psCode);
	 * request.addProperty("psName",psName);
	 * request.addProperty("offdrMail",""); request.addProperty("deptCode",
	 * "2"); request.addProperty("deptName", "L and O");
	 * request.addProperty("mc_imeiNo", ET_IMEI);
	 * request.addProperty("mc_simNo", ET_SIM);
	 * request.addProperty("mc_mobileNo", ET_Mobile);
	 * request.addProperty("mc_ipNo", ""); request.addProperty("mc_macId",
	 * ET_MAC); request.addProperty("remarks", "");
	 * request.addProperty("description",
	 * Add_SectionsActivity.decription_text.getText().toString());
	 * request.addProperty("pid_code", pidCode); request.addProperty("pid_name",
	 * pidName); request.addProperty("acmdAmount", "");
	 * request.addProperty("userCharges", ""); request.addProperty("cmdAmount",
	 * "");
	 * 
	 * String aadhaar_idvalue=null; String driving_idvalue=null; String
	 * vehicle_idvalue=null; String passport_idvalue=null; String
	 * pan_idvalue=null; String voter_idvalue=null;
	 * 
	 * Log.i("id1_Spinner Spinner Value ::", id1_Spinner);
	 * if("AADHAAR NO".equals(id1_Spinner)){
	 * aadhaar_idvalue=respondent_id_details.getText().toString(); }else
	 * if("DRIVING LICENCE".equals(id1_Spinner)){
	 * driving_idvalue=respondent_id_details.getText().toString(); }else
	 * if("VEHICLE NO".equals(id1_Spinner)){
	 * vehicle_idvalue=respondent_id_details.getText().toString(); }else
	 * if("PASSPORT NO".equals(id1_Spinner)){
	 * passport_idvalue=respondent_id_details.getText().toString(); }else
	 * if("PAN CARD NO".equals(id1_Spinner)){
	 * pan_idvalue=respondent_id_details.getText().toString(); }else
	 * if("VOTER ID".equals(id1_Spinner)){
	 * voter_idvalue=respondent_id_details.getText().toString(); }
	 * request.addProperty("offdrAadharNo", aadhaar_idvalue);
	 * request.addProperty("offdrLicenceNo", driving_idvalue);
	 * request.addProperty("offdrRegnCd", vehicle_idvalue);
	 * request.addProperty("offdrPAN", pan_idvalue);
	 * request.addProperty("offdrPassport", ET_PassportNo);
	 * request.addProperty("offdrVoterId", voter_idvalue);
	 * 
	 * request.addProperty("idDetails", ET_RespondentDetails);//form json array
	 * of 4 respondant to string request.addProperty("detainedItems",
	 * seizedItems.toString()); request.addProperty("sectionDetails",
	 * secBuffer.toString()); request.addProperty("fpDetails", "");
	 * request.addProperty
	 * ("witnessDetails",WitnessFormActivity.ET_WitnessDetails );//form json
	 * array of 4 witness to string
	 * request.addProperty("image1",""+getBase64EncodedString
	 * (respImage1ByteArray));
	 * request.addProperty("image2",""+getBase64EncodedString
	 * (respImage2ByteArray));
	 * request.addProperty("image3",""+getBase64EncodedString
	 * (seizedImageInbyteArray));
	 * 
	 * SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
	 * SoapEnvelope.VER11); envelope.dotNet = true;
	 * envelope.setOutputSoapObject(request);
	 * Log.i("Preview Status ::",previewrespondentBuf.toString()); //
	 * Log.i("request", "" + request);
	 * //Log.i("WebService.SOAP_ADDRESS ::::::::::::::", "" +
	 * WebService.SOAP_ADDRESS); HttpTransportSE androidHttpTransport = new
	 * HttpTransportSE
	 * (ip+"/HydPettyCaseService/services/PettyCaseServiceImpl?wsdl");
	 * Log.i("androidHttpTransport", "" + androidHttpTransport); //SOAP_ACTION =
	 * NAMESPACE + "generatePettyCase";
	 * androidHttpTransport.call(SOAP_ACTION_up, envelope); Object result =
	 * envelope.getResponse(); Opdata_Chalana=result.toString();
	 * challanGenresp=""; challanGenresp = Opdata_Chalana;
	 * 
	 * Log.i("** generatePettyCase response***", "" + challanGenresp);
	 * //Toast.makeText(getApplicationContext(),"Response"+ challanGenresp,
	 * Toast.LENGTH_SHORT).show(); runOnUiThread(new Runnable() { public void
	 * run(){ if(challanGenresp==null){ Log.i("OffenderActivity.challanGenresp",
	 * challanGenresp);
	 * 
	 * AlertDialog alertDialog = new
	 * AlertDialog.Builder(OffenderActivity.this).create();
	 * alertDialog.setTitle("Alert");
	 * alertDialog.setMessage("NO RESPONSE FROM SERVER");
	 * alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new
	 * DialogInterface.OnClickListener() { public void onClick(DialogInterface
	 * dialog, int which) { dialog.dismiss(); } }); alertDialog.show();
	 * 
	 * 
	 * }else{ AlertDialog alertDialog = new
	 * AlertDialog.Builder(OffenderActivity.this).create();
	 * alertDialog.setTitle("Alert");
	 * alertDialog.setMessage("NO RESPONSE FROM SERVER");
	 * alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new
	 * DialogInterface.OnClickListener() { public void onClick(DialogInterface
	 * dialog, int which) { dialog.dismiss(); } }); alertDialog.show(); } } });
	 * // // INSERT ID PROOF DETAILS END } catch (Exception e) {
	 * e.printStackTrace(); } } catch (Exception e) { e.printStackTrace(); }
	 * return null;
	 * 
	 * }
	 * 
	 * private Object getBase64ApacheCommonsEncodedString(byte[] bs) { // TODO
	 * Auto-generated method stub String imgString ="NA"; try { if(bs!=null){
	 * imgString
	 * =org.apache.commons.codec.binary.Base64.encodeBase64(bs).toString();
	 * //imgString = Base64.encodeToString(bs, Base64.NO_WRAP); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); // TODO: handle exception }
	 * return imgString.trim(); }
	 * 
	 * @Override protected void onPreExecute() { // TODO Auto-generated method
	 * stub super.onPreExecute(); dialog.setMessage("Please wait.....!");
	 * dialog.setIndeterminate(true); dialog.setCancelable(false);
	 * dialog.show(); }
	 * 
	 * @Override protected void onPostExecute(String result) { // TODO
	 * Auto-generated method stub super.onPostExecute(result); dialog.dismiss();
	 * 
	 * } }
	 */

	/******************** Get Details BY AADHAAR NO Class AsyncGetIDDetails Starts ****************************/

	class AsyncGetIDDetails extends AsyncTask<Void, Void, String> {
		ProgressDialog dialog = new ProgressDialog(OffenderActivity.this);

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
		 */
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Log.i("getIDDetails method called ", "YES");

			try {
				String ip = "";
				SQLiteDatabase db = openOrCreateDatabase(
						DataBase.DATABASE_NAME, MODE_PRIVATE, null);
				String selectQuery = "SELECT  * FROM " + DataBase.IP_TABLE;
				// SQLiteDatabase db = this.getWritableDatabase();
				Cursor cursor = db.rawQuery(selectQuery, null);
				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						Log.i("DATABASE   IP VALUE :", "" + cursor.getString(0));
						ip = cursor.getString(0);
					} while (cursor.moveToNext());
				}
				db.close();
				// INSERT ID PROOF DETAILS STARTds
				SoapObject request = new SoapObject(NAMESPACE,
						"getDetailsByAADHAR");
				Log.i("respondent_id_details :::::::::::::",
						respondent_id_details.getText().toString());

				if (get_detailsFlg1) {
					request.addProperty("UID", respondent_id_details.getText()
							.toString());
					request.addProperty("EID", respondent_id_details.getText()
							.toString());
				}
				if (get_detailsFlg2) {
					request.addProperty("UID", respondent_id_details2.getText()
							.toString());
					request.addProperty("EID", respondent_id_details2.getText()
							.toString());
				}
				if (get_detailsFlg3) {
					request.addProperty("UID", respondent_id_details3.getText()
							.toString());
					request.addProperty("EID", respondent_id_details3.getText()
							.toString());
				}
				if (get_detailsFlg4) {
					request.addProperty("UID", respondent_id_details4.getText()
							.toString());
					request.addProperty("EID", respondent_id_details4.getText()
							.toString());
				}

				if (get_LeaserdetailsFlg) {
					Log.i("leaser_id_details :::", ""
							+ leaser_id_details.getText().toString().trim());
					request.addProperty("UID", leaser_id_details.getText()
							.toString());
					request.addProperty("EID", leaser_id_details.getText()
							.toString());
				}

				if (get_ManagerdetailsFlg) {
					Log.i("manager_id_details :::", ""
							+ manager_id_details.getText().toString().trim());
					request.addProperty("UID", manager_id_details.getText()
							.toString());
					request.addProperty("EID", manager_id_details.getText()
							.toString());
				}

				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				Log.i("request", "" + request);
				HttpTransportSE androidHttpTransport = new HttpTransportSE(
						ip
								+ "/HydPettyCaseService/services/PettyCaseServiceImpl?wsdl");
				Log.i("androidHttpTransport", "" + androidHttpTransport);
				androidHttpTransport.call(SOAP_ACTION_ID, envelope);
				Object result = envelope.getResponse();
				challanGenresp = result.toString();
				Log.i("** getDetailsByAADHAR response***", "" + challanGenresp);

				runOnUiThread(new Runnable() {
					public void run() { // http://service.mother.com/HydPettyCaseService/services/PettyCaseServiceImpl?wsdl
						if (challanGenresp != null
								&& challanGenresp.length() > 25) {
							try {
								JSONObject jsonObj = new JSONObject(
										challanGenresp);
								// Getting JSON Array node
								JSONArray contacts = jsonObj
										.getJSONArray("ID_DETAILS");
								// looping through All Contacts
								JSONObject c = contacts.getJSONObject(0);
								Log.i("Name :::", c.getString("NAME"));
								Log.i("Father Name :::", c.getString("FNAME"));
								Log.i("Gender :::", c.getString("GENDER"));
								Log.i("off_Pnum :::", c.getString("PHONE_NO"));

								if (get_detailsFlg1) {
									id_off_name.setText(c.getString("NAME"));
									/*id_off_name.setFocusable(false);*/
									off_fname.setText(c.getString("FNAME"));

									/*
									 * off_age.setText(c.getString("DOB").toString
									 * ());
									 */
									String date_birth = c.getString("DOB");
									String[] split_dob = date_birth
											.split("\\/");
									String service_year = "" + split_dob[2];

									int final_age = year
											- Integer.parseInt(service_year);
									Log.i("final_age ::::::::", "" + final_age);

									off_age.setText("" + final_age);

									if ("M".equals(c.getString("GENDER"))) {
										resp_male.setChecked(true);
										group1_text.setText("M");
									} else if ("F"
											.equals(c.getString("GENDER"))) {
										resp_female.setChecked(true);
										group1_text.setText("F");

									} else if ("O"
											.equals(c.getString("GENDER"))) {
										resp_other.setChecked(true);
										group1_text.setText("O");
									}
									String completeAddress = c.getString("HNO")
											+ c.getString("STREET")
											+ c.getString("VILLAGE")
											+ c.getString("MANDAL")
											+ c.getString("DISTRICT")
											+ c.getString("STATE")
											+ c.getString("PIN_CODE");
									off_address.setText(completeAddress);
									off_Pnum.setText(c.getString("PHONE_NO")
											.toString());
									manager_city.setText(c
											.getString("DISTRICT").toString());
									leaser_city.setText(c.getString("DISTRICT")
											.toString());

									Log.i("Image 1 byte[]",
											""
													+ Base64.decode(c
															.getString("IMAGE")
															.toString().trim()
															.getBytes(), 1));

									if (c.getString("IMAGE") != null
											&& c.getString("IMAGE").toString()
													.trim().length() > 100) {
										image1ByteArray = Base64.decode(c
												.getString("IMAGE").toString()
												.trim().getBytes(), 1);
										Bitmap bmp = BitmapFactory
												.decodeByteArray(
														image1ByteArray, 0,
														image1ByteArray.length);
										respondent_img1.setImageBitmap(bmp);
									} else {
										respondent_img1
												.setImageResource(R.drawable.photo);
									}

								}
								if (id_off_name.getText().toString().trim().length()>1) {
									
									id_off_name.setEnabled(false);
									
								}
								else {
									id_off_name.setEnabled(true);
								}
								
								if (off_fname.getText().toString().trim().length()>1) {
									
									off_fname.setEnabled(false);
									
								}
								else {
									off_fname.setEnabled(true);
								}
								if (off_age.getText().toString().trim().length()>1) {
									
									off_age.setEnabled(false);
									
								}
								else {
									off_age.setEnabled(true);
								}
								if (off_address.getText().toString().trim().length()>1) {
									
									off_address.setEnabled(false);
									
								}
								else {
									off_address.setEnabled(true);
								}
								if (off_Pnum.getText().toString().trim().length()>1 && off_Pnum.getText().toString().trim().length()== 0 && off_Pnum.getText().toString().trim()!=null) {
									
									off_Pnum.setEnabled(true);
								}else if (off_Pnum.getText().toString().trim().equals("0")) {
									off_Pnum.setEnabled(true);	
								}
								else {
									off_Pnum.setEnabled(true);
								}
								if (manager_city.getText().toString().trim().length()>1) {
									
									manager_city.setEnabled(false);
									
								}
								else {
									manager_city.setEnabled(true);
								}
								if (leaser_city.getText().toString().trim().length()>1) {
									
									leaser_city.setEnabled(false);
									
								}
								else {
									leaser_city.setEnabled(true);
								}
								

								if (get_detailsFlg2) {
									Log.i("Name2 :::", c.getString("NAME"));
									Log.i("Father Name2 :::",
											c.getString("FNAME"));
									Log.i("Gender2 :::", c.getString("GENDER"));
									Log.i("off_Pnum2 :::",
											c.getString("PHONE_NO"));
									id_off_name2.setText(c.getString("NAME"));
									off_fname2.setText(c.getString("FNAME"));

									String date_birth = c.getString("DOB");
									String[] split_dob = date_birth
											.split("\\/");
									String service_year = "" + split_dob[2];

									int final_age = year
											- Integer.parseInt(service_year);
									Log.i("final_age ::::::::", "" + final_age);

									off_age2.setText("" + final_age);

									if ("M".equals(c.getString("GENDER"))) {
										resp_male2.setChecked(true);
									} else if ("F"
											.equals(c.getString("GENDER"))) {
										resp_female2.setChecked(true);
									} else if ("O"
											.equals(c.getString("GENDER"))) {
										others2.setChecked(true);
									}
									String completeAddress = c.getString("HNO")
											+ c.getString("STREET")
											+ c.getString("VILLAGE")
											+ c.getString("MANDAL")
											+ c.getString("DISTRICT")
											+ c.getString("STATE")
											+ c.getString("PIN_CODE");
									off_address2.setText(completeAddress);
									off_Pnum2.setText(c.getString("PHONE_NO")
											.toString());

									Log.i("Image 2 byte[]",
											""
													+ Base64.decode(c
															.getString("IMAGE")
															.toString().trim()
															.getBytes(), 1));

									if (c.getString("IMAGE") != null
											&& c.getString("IMAGE").toString()
													.trim().length() > 100) {
										image1ByteArray = Base64.decode(c
												.getString("IMAGE").toString()
												.trim().getBytes(), 1);
										Bitmap bmp = BitmapFactory
												.decodeByteArray(
														image1ByteArray, 0,
														image1ByteArray.length);
										respondent_img2.setImageBitmap(bmp);
									} else {
										respondent_img2
												.setImageResource(R.drawable.photo);
									}

								}
								if (get_detailsFlg3) {
									id_off_name3.setText(c.getString("NAME"));
									off_fname3.setText(c.getString("FNAME"));
									String date_birth = c.getString("DOB");
									String[] split_dob = date_birth
											.split("\\/");
									String service_year = "" + split_dob[2];

									int final_age = year
											- Integer.parseInt(service_year);
									Log.i("final_age ::::::::", "" + final_age);

									off_age3.setText("" + final_age);

									if ("M".equals(c.getString("GENDER"))) {
										resp_male3.setChecked(true);
									} else if ("F"
											.equals(c.getString("GENDER"))) {
										resp_female3.setChecked(true);
									} else if ("O"
											.equals(c.getString("GENDER"))) {
										resp_other3.setChecked(true);
									}
									String completeAddress = c.getString("HNO")
											+ c.getString("STREET")
											+ c.getString("VILLAGE")
											+ c.getString("MANDAL")
											+ c.getString("DISTRICT")
											+ c.getString("STATE")
											+ c.getString("PIN_CODE");
									off_address3.setText(completeAddress);
									off_Pnum3.setText(c.getString("PHONE_NO")
											.toString());

									if (c.getString("IMAGE") != null
											&& c.getString("IMAGE").toString()
													.trim().length() > 100) {
										image3ByteArray = Base64.decode(c
												.getString("IMAGE").toString()
												.trim().getBytes(), 1);
										Log.i("Image 3 byte[]",
												""
														+ Base64.decode(
																c.getString(
																		"IMAGE")
																		.toString()
																		.trim()
																		.getBytes(),
																1));
										Bitmap bmp = BitmapFactory
												.decodeByteArray(
														image3ByteArray, 0,
														image3ByteArray.length);
										respondent_img3.setImageBitmap(bmp);
									}

								}
								if (get_detailsFlg4) {
									id_off_name4.setText(c.getString("NAME"));
									off_fname4.setText(c.getString("FNAME"));
									String date_birth = c.getString("DOB");
									String[] split_dob = date_birth
											.split("\\/");
									String service_year = "" + split_dob[2];

									int final_age = year
											- Integer.parseInt(service_year);
									Log.i("final_age ::::::::", "" + final_age);

									off_age4.setText("" + final_age);
									if ("M".equals(c.getString("GENDER"))) {
										resp_male4.setChecked(true);
									} else if ("F"
											.equals(c.getString("GENDER"))) {
										resp_female4.setChecked(true);
									} else if ("O"
											.equals(c.getString("GENDER"))) {
										resp_other4.setChecked(true);
									}
									String completeAddress = c.getString("HNO")
											+ c.getString("STREET")
											+ c.getString("VILLAGE")
											+ c.getString("MANDAL")
											+ c.getString("DISTRICT")
											+ c.getString("STATE")
											+ c.getString("PIN_CODE");
									off_address4.setText(completeAddress);
									off_Pnum4.setText(c.getString("PHONE_NO")
											.toString());

									if (c.getString("IMAGE") != null
											&& c.getString("IMAGE").toString()
													.trim().length() > 100) {
										image4ByteArray = Base64.decode(c
												.getString("IMAGE").toString()
												.trim().getBytes(), 1);
										Log.i("Image 4 byte[]",
												""
														+ Base64.decode(
																c.getString(
																		"IMAGE")
																		.toString()
																		.trim()
																		.getBytes(),
																1));
										Bitmap bmp = BitmapFactory
												.decodeByteArray(
														image4ByteArray, 0,
														image4ByteArray.length);
										respondent_img4.setImageBitmap(bmp);
									}
								}

								if (get_LeaserdetailsFlg) {
									leaser_name.setText(c.getString("NAME"));
									leaser_fname.setText(c.getString("FNAME"));
									String date_birth = c.getString("DOB");
									String[] split_dob = date_birth
											.split("\\/");
									String service_year = "" + split_dob[2];

									int final_age = year
											- Integer.parseInt(service_year);
									Log.i("final_age ::::::::", "" + final_age);

									leaser_age.setText("" + final_age);
									if ("M".equals(c.getString("GENDER"))) {
										leaser_male.setChecked(true);
									} else if ("F"
											.equals(c.getString("GENDER"))) {
										leaser_female.setChecked(true);
									} else if ("O"
											.equals(c.getString("GENDER"))) {
										leaser_other.setChecked(true);
									}
									String completeAddress = c.getString("HNO")
											+ c.getString("STREET")
											+ c.getString("VILLAGE")
											+ c.getString("MANDAL")
											+ c.getString("DISTRICT")
											+ c.getString("STATE")
											+ c.getString("PIN_CODE");
									leaser_address.setText(completeAddress);
									leaser_city.setText(c.getString("DISTRICT"));
									leaser_mobileNo.setText(c.getString(
											"PHONE_NO").toString());

									if (c.getString("IMAGE") != null
											&& c.getString("IMAGE").toString()
													.trim().length() > 100) {
										leaserImage1ByteArray = Base64.decode(c
												.getString("IMAGE").toString()
												.trim().getBytes(), 1);
										Log.i("Image 4 byte[]",
												""
														+ Base64.decode(
																c.getString(
																		"IMAGE")
																		.toString()
																		.trim()
																		.getBytes(),
																1));
										Bitmap bmp = BitmapFactory
												.decodeByteArray(
														leaserImage1ByteArray,
														0,
														leaserImage1ByteArray.length);
										leaser_img.setImageBitmap(bmp);
									}
								}

								if (get_ManagerdetailsFlg) {
									manager_name.setText(c.getString("NAME"));
									manager_fname.setText(c.getString("FNAME"));
									String date_birth = c.getString("DOB");
									String[] split_dob = date_birth
											.split("\\/");
									String service_year = "" + split_dob[2];

									int final_age = year
											- Integer.parseInt(service_year);
									Log.i("final_age ::::::::", "" + final_age);

									manager_age.setText("" + final_age);
									if ("M".equals(c.getString("GENDER"))) {
										manager_male.setChecked(true);
									} else if ("F"
											.equals(c.getString("GENDER"))) {
										manager_female.setChecked(true);
									} else if ("O"
											.equals(c.getString("GENDER"))) {
										manager_other.setChecked(true);
									}
									String completeAddress = c.getString("HNO")
											+ c.getString("STREET")
											+ c.getString("VILLAGE")
											+ c.getString("MANDAL")
											+ c.getString("DISTRICT")
											+ c.getString("STATE")
											+ c.getString("PIN_CODE");
									manager_address.setText(completeAddress);
									
									manager_mobileNo.setText(c.getString(
											"PHONE_NO").toString());

									if (c.getString("IMAGE") != null
											&& c.getString("IMAGE").toString()
													.trim().length() > 100) {
										managerImage2ByteArray = Base64.decode(
												c.getString("IMAGE").toString()
														.trim().getBytes(), 1);
										Log.i("Image 4 byte[]",
												""
														+ Base64.decode(
																c.getString(
																		"IMAGE")
																		.toString()
																		.trim()
																		.getBytes(),
																1));
										Bitmap bmp = BitmapFactory
												.decodeByteArray(
														managerImage2ByteArray,
														0,
														managerImage2ByteArray.length);
										manager_img.setImageBitmap(bmp);
										manager_city.setText(""+c.get("DISTRICT"));
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							Log.i("no data from service", "no ");
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "No data Found", Toast.LENGTH_LONG);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("No Data Found");
						}
					}
				});
				// // INSERT ID PROOF DETAILS END
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// showDialog(PROGRESS_DIALOG);
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
			showDialog(PROGRESS_DIALOG);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			removeDialog(PROGRESS_DIALOG);
			if (!isNetworkAvailable()) {
				// do something
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						OffenderActivity.this);
				alertDialogBuilder
						.setMessage(
								"    Mobile Data is Disabled in your Device \n            Please Enable Mobile Data?")
						.setCancelable(false)
						.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
									}
								});
				AlertDialog alert = alertDialogBuilder.create();
				alert.show();
			}
		}
	}

	/******************** Get Details BY RC Class AsyncGetIDDetails Ends ****************************/

	/******************** Get Details BY Driving License Class AsyncGetDLDetails Starts ****************************/

	class AsyncGetDLDetails extends AsyncTask<Void, Void, String> {

		ProgressDialog dialog = new ProgressDialog(OffenderActivity.this);

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Log.i("getDetailsByDrivingLicence method called ", "YES");
			try {
				String ip = "";
				SQLiteDatabase db = openOrCreateDatabase(
						DataBase.DATABASE_NAME, MODE_PRIVATE, null);
				String selectQuery = "SELECT  * FROM " + DataBase.IP_TABLE;
				// SQLiteDatabase db = this.getWritableDatabase();
				Cursor cursor = db.rawQuery(selectQuery, null);
				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						Log.i("DATABASE   IP VALUE :", "" + cursor.getString(0));
						ip = cursor.getString(0);
					} while (cursor.moveToNext());
				}
				db.close();
				// INSERT ID PROOF DETAILS START
				SoapObject request = new SoapObject(NAMESPACE,
						"getDetailsByDrivingLicence");
				Log.i("respondent_id_details :::::::::::::",
						respondent_id_details.getText().toString());
				if (get_detailsFlg1) {
					request.addProperty("dl_no", respondent_id_details
							.getText().toString());
				}
				if (get_detailsFlg2) {
					request.addProperty("dl_no", respondent_id_details2
							.getText().toString());
				}

				if (get_detailsFlg3) {
					request.addProperty("dl_no", respondent_id_details3
							.getText().toString());
				}

				if (get_detailsFlg4) {
					request.addProperty("dl_no", respondent_id_details4
							.getText().toString());
				}
				// request.addProperty("EID",
				// respondent_id_details.getText().toString());
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				Log.i("request", "" + request);
				HttpTransportSE androidHttpTransport = new HttpTransportSE(
						ip
								+ "/HydPettyCaseService/services/PettyCaseServiceImpl?wsdl");
				Log.i("androidHttpTransport", "" + androidHttpTransport);
				androidHttpTransport.call(SOAP_ACTION_ID, envelope);
				Object result = envelope.getResponse();
				challanGenresp = result.toString();
				Log.i("** getDetailsByDrivingLicence response***", ""
						+ challanGenresp);

				runOnUiThread(new Runnable() {
					public void run() {
						if (challanGenresp != null
								&& challanGenresp.length() > 25) {
							try {
								JSONObject jsonObj = new JSONObject(
										challanGenresp);
								// Getting JSON Array node
								JSONArray contacts = jsonObj
										.getJSONArray("LICENSE_DETAILS");
								// looping through All Contacts
								JSONObject c = contacts.getJSONObject(0);

								if (get_detailsFlg1) {
									id_off_name.setText(c.getString("NAME"));
									off_fname.setText(c.getString("FNAME"));
									off_age.setText(c.getString("DOB")
											.toString());
									if ("M".equals(c.getString("GENDER"))) {
										resp_male.setChecked(true);
									} else if ("F"
											.equals(c.getString("GENDER"))) {
										resp_female.setChecked(true);
									} else if ("O"
											.equals(c.getString("GENDER"))) {
										resp_other.setChecked(true);
									}
									String completeAddress = c.getString("HNO")
											+ c.getString("STREET")
											+ c.getString("VILLAGE")
											+ c.getString("MANDAL")
											+ c.getString("DISTRICT")
											+ c.getString("STATE")
											+ c.getString("PIN_CODE");
									off_address.setText(completeAddress);
									off_Pnum.setText(c.getString("PHONE_NO")
											.toString());
								}
								if (get_detailsFlg2) {
									id_off_name2.setText(c.getString("NAME"));
									off_fname2.setText(c.getString("FNAME"));
									off_age2.setText(c.getString("DOB")
											.toString());
									if ("M".equals(c.getString("GENDER"))) {
										resp_male2.setChecked(true);
									} else if ("F"
											.equals(c.getString("GENDER"))) {
										resp_female2.setChecked(true);
									} else if ("O"
											.equals(c.getString("GENDER"))) {
										resp_other2.setChecked(true);
									}
									String completeAddress = c.getString("HNO")
											+ c.getString("STREET")
											+ c.getString("VILLAGE")
											+ c.getString("MANDAL")
											+ c.getString("DISTRICT")
											+ c.getString("STATE")
											+ c.getString("PIN_CODE");
									off_address2.setText(completeAddress);
									off_Pnum2.setText(c.getString("PHONE_NO")
											.toString());
								}
								if (get_detailsFlg3) {
									id_off_name3.setText(c.getString("NAME"));
									off_fname3.setText(c.getString("FNAME"));
									off_age3.setText(c.getString("DOB")
											.toString());
									if ("M".equals(c.getString("GENDER"))) {
										resp_male3.setChecked(true);
									} else if ("F"
											.equals(c.getString("GENDER"))) {
										resp_female3.setChecked(true);
									} else if ("O"
											.equals(c.getString("GENDER"))) {
										resp_other3.setChecked(true);
									}
									String completeAddress = c.getString("HNO")
											+ c.getString("STREET")
											+ c.getString("VILLAGE")
											+ c.getString("MANDAL")
											+ c.getString("DISTRICT")
											+ c.getString("STATE")
											+ c.getString("PIN_CODE");
									off_address3.setText(completeAddress);
									off_Pnum3.setText(c.getString("PHONE_NO")
											.toString());
								}
								if (get_detailsFlg4) {
									id_off_name4.setText(c.getString("NAME"));
									off_fname4.setText(c.getString("FNAME"));
									off_age4.setText(c.getString("DOB")
											.toString());
									if ("M".equals(c.getString("GENDER"))) {
										resp_male4.setChecked(true);
									} else if ("F"
											.equals(c.getString("GENDER"))) {
										resp_female4.setChecked(true);
									} else if ("O"
											.equals(c.getString("GENDER"))) {
										resp_other4.setChecked(true);
									}
									String completeAddress = c.getString("HNO")
											+ c.getString("STREET")
											+ c.getString("VILLAGE")
											+ c.getString("MANDAL")
											+ c.getString("DISTRICT")
											+ c.getString("STATE")
											+ c.getString("PIN_CODE");
									off_address4.setText(completeAddress);
									off_Pnum4.setText(c.getString("PHONE_NO")
											.toString());
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							Log.i("no data from service", "no ");
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "No data Found", Toast.LENGTH_LONG);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("No Data Found");
						}
					}
				});

				// // INSERT ID PROOF DETAILS END
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// showDialog(PROGRESS_DIALOG);
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
			showDialog(PROGRESS_DIALOG);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			removeDialog(PROGRESS_DIALOG);
			if (!isNetworkAvailable()) {
				// do something
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						OffenderActivity.this);
				alertDialogBuilder
						.setMessage(
								"    Mobile Data is Disabled in your Device \n            Please Enable Mobile Data?")
						.setCancelable(false)
						.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
									}
								});
				AlertDialog alert = alertDialogBuilder.create();
				alert.show();
			}
		}
	}

	/******************** Get Details BY Driving License Class AsyncGetDLDetails Ends ****************************/

	/******************** Get Details BY RC Class AsyncGetRCDetails Starts ****************************/

	class AsyncGetRCDetails extends AsyncTask<Void, Void, String> {

		ProgressDialog dialog = new ProgressDialog(OffenderActivity.this);

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Log.i("getDetailsByRC method called ", "YES");
			try {
				String ip = "";
				SQLiteDatabase db = openOrCreateDatabase(
						DataBase.DATABASE_NAME, MODE_PRIVATE, null);
				String selectQuery = "SELECT  * FROM " + DataBase.IP_TABLE;
				// SQLiteDatabase db = this.getWritableDatabase();
				Cursor cursor = db.rawQuery(selectQuery, null);
				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						Log.i("DATABASE   IP VALUE :", "" + cursor.getString(0));
						ip = cursor.getString(0);
					} while (cursor.moveToNext());
				}
				db.close();
				// INSERT ID PROOF DETAILS START
				SoapObject request = new SoapObject(NAMESPACE, "getDetailsByRC");
				Log.i("respondent_id_details :::::::::::::",
						respondent_id_details.getText().toString());
				if (get_detailsFlg1) {
					request.addProperty("regn_no", respondent_id_details
							.getText().toString());
				}
				if (get_detailsFlg2) {
					request.addProperty("regn_no", respondent_id_details2
							.getText().toString());
				}
				if (get_detailsFlg3) {
					request.addProperty("regn_no", respondent_id_details3
							.getText().toString());
				}
				if (get_detailsFlg4) {
					request.addProperty("regn_no", respondent_id_details4
							.getText().toString());
				}
				// request.addProperty("EID",
				// respondent_id_details.getText().toString());
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				Log.i("request", "" + request);
				HttpTransportSE androidHttpTransport = new HttpTransportSE(
						ip
								+ "/HydPettyCaseService/services/PettyCaseServiceImpl?wsdl");
				Log.i("androidHttpTransport", "" + androidHttpTransport);
				androidHttpTransport.call(SOAP_ACTION_ID, envelope);
				Object result = envelope.getResponse();
				challanGenresp = result.toString();
				Log.i("** getDetailsByRC response***", "" + challanGenresp);

				runOnUiThread(new Runnable() {
					public void run() {
						if (challanGenresp != null
								&& challanGenresp.length() > 25) {
							try {
								JSONObject jsonObj = new JSONObject(
										challanGenresp);
								// Getting JSON Array node
								JSONArray contacts = jsonObj
										.getJSONArray("RC_DETAILS");
								// looping through All Contacts
								JSONObject c = contacts.getJSONObject(0);
								if (get_detailsFlg1) {
									id_off_name.setText(c.getString("NAME"));
									off_fname.setText(c.getString("FNAME"));
									off_age.setText(c.getString("DOB")
											.toString());
									if ("M".equals(c.getString("GENDER"))) {
										resp_male.setChecked(true);
									} else if ("F"
											.equals(c.getString("GENDER"))) {
										resp_female.setChecked(true);
									} else if ("O"
											.equals(c.getString("GENDER"))) {
										resp_other.setChecked(true);
									}
									String completeAddress = c.getString("HNO")
											+ c.getString("STREET")
											+ c.getString("VILLAGE")
											+ c.getString("MANDAL")
											+ c.getString("DISTRICT")
											+ c.getString("STATE")
											+ c.getString("PIN_CODE");
									off_address.setText(completeAddress);
									off_Pnum.setText(c.getString("PHONE_NO")
											.toString());
								}
								if (get_detailsFlg2) {
									id_off_name2.setText(c.getString("NAME"));
									off_fname2.setText(c.getString("FNAME"));
									off_age2.setText(c.getString("DOB")
											.toString());
									if ("M".equals(c.getString("GENDER"))) {
										resp_male2.setChecked(true);
									} else if ("F"
											.equals(c.getString("GENDER"))) {
										resp_female2.setChecked(true);
									} else if ("O"
											.equals(c.getString("GENDER"))) {
										resp_other2.setChecked(true);
									}
									String completeAddress = c.getString("HNO")
											+ c.getString("STREET")
											+ c.getString("VILLAGE")
											+ c.getString("MANDAL")
											+ c.getString("DISTRICT")
											+ c.getString("STATE")
											+ c.getString("PIN_CODE");
									off_address2.setText(completeAddress);
									off_Pnum2.setText(c.getString("PHONE_NO")
											.toString());
								}
								if (get_detailsFlg3) {
									id_off_name3.setText(c.getString("NAME"));
									off_fname3.setText(c.getString("FNAME"));
									off_age3.setText(c.getString("DOB")
											.toString());
									if ("M".equals(c.getString("GENDER"))) {
										resp_male3.setChecked(true);
									} else if ("F"
											.equals(c.getString("GENDER"))) {
										resp_female3.setChecked(true);
									} else if ("O"
											.equals(c.getString("GENDER"))) {
										resp_other3.setChecked(true);
									}
									String completeAddress = c.getString("HNO")
											+ c.getString("STREET")
											+ c.getString("VILLAGE")
											+ c.getString("MANDAL")
											+ c.getString("DISTRICT")
											+ c.getString("STATE")
											+ c.getString("PIN_CODE");
									off_address3.setText(completeAddress);
									off_Pnum3.setText(c.getString("PHONE_NO")
											.toString());
								}
								if (get_detailsFlg4) {
									id_off_name4.setText(c.getString("NAME"));
									off_fname4.setText(c.getString("FNAME"));
									off_age4.setText(c.getString("DOB")
											.toString());
									if ("M".equals(c.getString("GENDER"))) {
										resp_male4.setChecked(true);
									} else if ("F"
											.equals(c.getString("GENDER"))) {
										resp_female4.setChecked(true);
									} else if ("O"
											.equals(c.getString("GENDER"))) {
										resp_other4.setChecked(true);
									}
									String completeAddress = c.getString("HNO")
											+ c.getString("STREET")
											+ c.getString("VILLAGE")
											+ c.getString("MANDAL")
											+ c.getString("DISTRICT")
											+ c.getString("STATE")
											+ c.getString("PIN_CODE");
									off_address4.setText(completeAddress);
									off_Pnum4.setText(c.getString("PHONE_NO")
											.toString());
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

						} else {
							Log.i("no data from service", "no ");
							/*
							 * Toast toast =
							 * Toast.makeText(getApplicationContext(),
							 * "No data Found", Toast.LENGTH_LONG);
							 * toast.setGravity(Gravity.CENTER, 0, -200); View
							 * toastView = toast.getView();
							 * toastView.setBackgroundResource
							 * (R.drawable.toast_background); toast.show();
							 */
							showToast("No Data Found");
						}
					}
				});

				// // INSERT ID PROOF DETAILS END
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// showDialog(PROGRESS_DIALOG);
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
			showDialog(PROGRESS_DIALOG);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			removeDialog(PROGRESS_DIALOG);
			if (!isNetworkAvailable()) {
				// do something
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						OffenderActivity.this);
				alertDialogBuilder
						.setMessage(
								"    Mobile Data is Disabled in your Device \n            Please Enable Mobile Data?")
						.setCancelable(false)
						.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
									}
								});
				AlertDialog alert = alertDialogBuilder.create();
				alert.show();
			}
		}
	}

	/******************** Get Details BY RC Class AsyncGetRCDetails Ends ****************************/

	@SuppressWarnings("deprecation")
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch (id) {
		case PROGRESS_DIALOG:
			ProgressDialog pd = ProgressDialog.show(this, "", "", true);
			pd.setContentView(R.layout.custom_progress_dialog);
			pd.setCancelable(false);

			return pd;

		default:
			break;
		}
		return super.onCreateDialog(id);
	}

	class ListAdapter extends BaseAdapter {
		@SuppressWarnings("unused")
		private Context mContext;
		ArrayList<String> alist;

		public ListAdapter(Context c, ArrayList<String> list) {
			mContext = c;
			this.alist = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return alist.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			View grid = convertView;
			if (convertView == null) {
			} else {
				grid = convertView;
			}
			id_int_drive_lic = (EditText) findViewById(R.id.id_int_drive_lic);
			id_landmark = (EditText) findViewById(R.id.id_landmark);
			id_off_name = (EditText) findViewById(R.id.id_off_name);
			id_passport_no = (EditText) findViewById(R.id.id_passport_no);
			off_Pnum = (EditText) findViewById(R.id.off_Pnum);
			// off_alt_num=(EditText)findViewById(R.id.off_alt_num);
			Itemname_ET = (EditText) findViewById(R.id.Itemname_ET);
			qty_ET = (EditText) findViewById(R.id.qty_ET);
			add_idProof = (Button) findViewById(R.id.add_idProof);
			off_id_options
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							// TODO Auto-generated method stub

							switch (arg2) {

							case 0:
								respondent_id_details.setText("");
								break;
							case 1:
								respondent_id_details.setText(getLabel("1"));
								break;
							case 2:
								respondent_id_details.setText(getLabel("2"));
								break;
							case 3:
								respondent_id_details.setText(getLabel("3"));
								break;
							case 4:
								respondent_id_details.setText(getLabel("4"));
								break;

							case 5:
								respondent_id_details.setText(getLabel("5"));
								break;
							case 6:
								respondent_id_details.setText(getLabel("6"));
								break;
							case 7:
								respondent_id_details.setText(getLabel("7"));
								break;

							default:
								respondent_id_details
										.setText("Nothing Selected");
								break;
							}
						}

						public CharSequence getLabel(String selected) {
							String label = "";
							try {
								for (String idCode : OffenderActivity.idMap
										.keySet()) {
									if (idCode.equals(selected)) {
										label = "ENTER "
												+ OffenderActivity.idMap
														.get(idCode);
										OffenderActivity.selectedID = OffenderActivity.idMap
												.get(idCode).toString();
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							Log.i("selected label name " + selected, label);
							return label;
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}
					});
			return grid;
		}
	}

	class AsynchGetAddress extends AsyncTask<Void, Void, String> {
		ProgressDialog dialog = new ProgressDialog(OffenderActivity.this);

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// showDialog(PROGRESS_DIALOG);
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
			dialog.setContentView(R.layout.custom_progress_dialog);
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		protected String doInBackground(Void... params) {

			Geocoder geocoder = new Geocoder(OffenderActivity.this,
					Locale.ENGLISH);
			String ret = "";
			try {
				List<Address> addresses = geocoder.getFromLocation(
						Double.parseDouble(latitude),
						Double.parseDouble(longitude), 1);
				if (addresses != null) {
					Address returnedAddress = addresses.get(0);
					StringBuilder strReturnedAddress = new StringBuilder("\n");
					for (int i = 0; i < returnedAddress
							.getMaxAddressLineIndex(); i++) {
						strReturnedAddress.append(
								returnedAddress.getAddressLine(i)).append("\n");
					}
					ret = strReturnedAddress.toString();
					Log.i("Location ::", strReturnedAddress.toString());
				} else {
					ret = "";
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ret = "Can't get Address!";
			}
			return ret;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			dialog.dismiss();
		}
	}

	public Object onRetainNonConfigurationInstance() {
		return sections;
	}

	public String getIDCode(String eT_ID) {
		String idCd = "NA";
		try {
			for (String idCode : OffenderActivity.idMap.keySet()) {
				if (eT_ID.equals(OffenderActivity.idMap.get(idCode))) {
					idCd = idCode;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		Log.i("idCd for " + eT_ID + " :", idCd);
		return idCd;
	}

	public String getIDCode2(String eT_ID) {
		String idCd = "NA";
		try {
			Log.i("SPinner Value", eT_ID);
			for (String idCode : OffenderActivity.idMap.keySet()) {
				if (eT_ID.equals(OffenderActivity.idMap.get(idCode))) {
					idCd = idCode;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		Log.i("idCd for SPinner Value  :", idCd);
		return idCd;
	}

	public String getIDCode3(String eT_ID) {
		String idCd = "NA";
		try {
			for (String idCode : OffenderActivity.idMap.keySet()) {
				if (eT_ID.equals(OffenderActivity.idMap.get(idCode))) {
					idCd = idCode;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		Log.i("idCd for " + eT_ID + " :", idCd);
		return idCd;
	}

	public String getIDCode4(String eT_ID) {
		String idCd = "NA";
		try {
			for (String idCode : OffenderActivity.idMap.keySet()) {
				if (eT_ID.equals(OffenderActivity.idMap.get(idCode))) {
					idCd = idCode;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		Log.i("idCd for " + eT_ID + " :", idCd);
		return idCd;
	}

	public String getBase64EncodedString(byte[] bs) {
		// TODO Auto-generated method stub
		String imgString = "NA";
		try {
			if (bs != null) {
				imgString = Base64.encodeToString(bs, Base64.NO_WRAP);
			}

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return imgString.trim();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds options to the action bar if it is
		// present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;

	}

	/***************************** Date Picker Starts ***********************/

	/***************************** Date Picker Close ***********************/

	protected int REQUEST_CAMERA;

	protected void selectImage() {
		// TODO Auto-generated method stub
		final CharSequence[] options = { "Open Camera", "Choose from Gallery",
				"Cancel" };
		AlertDialog.Builder builder = new AlertDialog.Builder(
				OffenderActivity.this);
		builder.setTitle("Add Photo!");
		builder.setItems(options, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (options[item].equals("Open Camera")) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File f = new File(android.os.Environment
							.getExternalStorageDirectory(), "temp.jpg");
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
					startActivityForResult(intent, 1);
				} else if (options[item].equals("Choose from Gallery")) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(intent, 2);
				} else if (options[item].equals("Cancel")) {
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
			String picturePath = "";
			if (requestCode == 1) {
				File f = new File(Environment.getExternalStorageDirectory()
						.toString());

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
							+ "ePettyCase" + File.separator + current_date;
					File camerapath = new File(path);

					if (!camerapath.exists()) {
						camerapath.mkdirs();
					}
					f.delete();
					OutputStream outFile = null;
					File file = new File(path, String.valueOf(System
							.currentTimeMillis()) + ".jpg");
					try {
						Log.i("Camera Path :::", "" + file.getAbsolutePath());

						outFile = new FileOutputStream(file);
						Bitmap mutableBitmap = bitmap.copy(
								Bitmap.Config.ARGB_8888, true);
						Canvas canvas = new Canvas(mutableBitmap); // bmp is the
																	// bitmap to
																	// dwaw into

						Paint paint = new Paint();
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
						/*
						 * canvas.drawText("Date & Time: "+Current_Date+"\n"+
						 * " Lat :"+latitude+ " Long :"+longitude,1250, 1500,
						 * paint);
						 */

						mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
								outFile);
						outFile.flush();
						outFile.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

					if ("1".equals(OffenderActivity.SelPicId)) {
						// Finally, we can draw text with this font via the
						// following Canvas method:
						Bitmap mutableBitmap = bitmap.copy(
								Bitmap.Config.ARGB_8888, true);
						Canvas canvas = new Canvas(mutableBitmap); // bmp is the
																	// bitmap to
																	// dwaw into

						Paint paint = new Paint();
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
						picture1.setImageBitmap(mutableBitmap);
						/* picture1.setRotation(90); */

						ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
						mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 80,
								stream1);
						respImage1ByteArray = stream1.toByteArray();

						Log.i("respImage1ByteArray 1::", ""
								+ respImage1ByteArray);
						// Bitmap.createScaledBitmap(bitmap, 786, 1024, false);
					} else if ("2".equals(OffenderActivity.SelPicId)) {
						Log.i("Picture 2 :::", "" + picture2);
						Log.i("BitMap ::", "" + bitmap);

						Bitmap mutableBitmap = bitmap.copy(
								Bitmap.Config.ARGB_8888, true);
						Canvas canvas = new Canvas(mutableBitmap); // bmp is the
																	// bitmap to
																	// dwaw into

						Paint paint = new Paint();
						paint.setColor(Color.RED);
						paint.setTextSize(100);
						paint.setTextAlign(Paint.Align.CENTER);
						int xPos = (canvas.getWidth() / 2);
						int yPos = (int) ((canvas.getHeight() / 2) - ((paint
								.descent() + paint.ascent()) / 2));

						canvas.drawText("Date & Time: " + Current_Date, xPos,
								yPos + 400, paint);
						canvas.drawText("Lat :" + latitude + " Long :"
								+ longitude, xPos, yPos + 500, paint);
						picture2.setImageBitmap(mutableBitmap);
						/* picture2.setRotation(90); */
						ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
						mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 80,
								stream2);
						respImage2ByteArray = stream2.toByteArray();
						Log.i("respImage2ByteArray 2::", ""
								+ respImage2ByteArray);
						// Bitmap.createScaledBitmap(bitmap, 786, 1024, false);
					} else if ("3".equals(OffenderActivity.SelPicId)) {
						Log.i("respondent_img1 :::", "" + respondent_img1);
						Log.i("BitMap ::", "" + bitmap);
						Bitmap mutableBitmap = bitmap.copy(
								Bitmap.Config.ARGB_8888, true);
						Canvas canvas = new Canvas(mutableBitmap); // bmp is the
																	// bitmap to
																	// dwaw into

						Paint paint = new Paint();
						paint.setColor(Color.RED);
						paint.setTextSize(100);
						paint.setTextAlign(Paint.Align.CENTER);
						int xPos = (canvas.getWidth() / 2);
						int yPos = (int) ((canvas.getHeight() / 2) - ((paint
								.descent() + paint.ascent()) / 2));

						canvas.drawText("Date & Time: " + Current_Date, xPos,
								yPos + 400, paint);
						canvas.drawText("Lat :" + latitude + " Long :"
								+ longitude, xPos, yPos + 500, paint);
						respondent_img1.setImageBitmap(mutableBitmap);
						/* respondent_img1.setRotation(90); */
						ByteArrayOutputStream stream3 = new ByteArrayOutputStream();
						mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 80,
								stream3);
						image1ByteArray = stream3.toByteArray();
						Log.i("imag1ByteArray::", "" + image1ByteArray);
						// Bitmap.createScaledBitmap(bitmap, 786, 1024, false);
					} else if ("4".equals(OffenderActivity.SelPicId)) {
						Log.i("respondent_img2 :::", "" + respondent_img2);
						Log.i("BitMap ::", "" + bitmap);
						Bitmap mutableBitmap = bitmap.copy(
								Bitmap.Config.ARGB_8888, true);
						Canvas canvas = new Canvas(mutableBitmap);

						Paint paint = new Paint();
						paint.setColor(Color.RED);
						paint.setTextSize(100);
						paint.setTextAlign(Paint.Align.CENTER);
						int xPos = (canvas.getWidth() / 2);
						int yPos = (int) ((canvas.getHeight() / 2) - ((paint
								.descent() + paint.ascent()) / 2));

						canvas.drawText("Date & Time: " + Current_Date, xPos,
								yPos + 400, paint);
						canvas.drawText("Lat :" + latitude + " Long :"
								+ longitude, xPos, yPos + 500, paint);
						respondent_img2.setImageBitmap(mutableBitmap);
						/* respondent_img2.setRotation(90); */

						ByteArrayOutputStream stream4 = new ByteArrayOutputStream();
						mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 80,
								stream4);
						image2ByteArray = stream4.toByteArray();
						Log.i("respondent_img3 ::", "" + image2ByteArray);
						// Bitmap.createScaledBitmap(bitmap, 786, 1024, false);
						// date/ccaseno/ecaseno_resp1,case_wit
					} else if ("5".equals(OffenderActivity.SelPicId)) {
						Log.i("respondent_img3 :::", "" + respondent_img3);
						Log.i("BitMap ::", "" + bitmap);
						Bitmap mutableBitmap = bitmap.copy(
								Bitmap.Config.ARGB_8888, true);
						Canvas canvas = new Canvas(mutableBitmap); // bmp is the
																	// bitmap to
																	// dwaw into

						Paint paint = new Paint();
						paint.setColor(Color.RED);
						paint.setTextSize(100);
						paint.setTextAlign(Paint.Align.CENTER);
						int xPos = (canvas.getWidth() / 2);
						int yPos = (int) ((canvas.getHeight() / 2) - ((paint
								.descent() + paint.ascent()) / 2));

						canvas.drawText("Date & Time: " + Current_Date, xPos,
								yPos + 400, paint);
						canvas.drawText("Lat :" + latitude + " Long :"
								+ longitude, xPos, yPos + 500, paint);
						respondent_img3.setImageBitmap(mutableBitmap);
						/* respondent_img3.setRotation(90); */
						ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
						mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 50,
								stream2);
						image3ByteArray = stream2.toByteArray();
						Log.i("respondent_img4::", "" + image3ByteArray);
						// Bitmap.createScaledBitmap(bitmap, 786, 1024, false);
					} else if ("6".equals(OffenderActivity.SelPicId)) {
						Log.i("respondent_img4 :::", "" + respondent_img4);
						Log.i("BitMap ::", "" + bitmap);
						Bitmap mutableBitmap = bitmap.copy(
								Bitmap.Config.ARGB_8888, true);
						Canvas canvas = new Canvas(mutableBitmap); // bmp is the
																	// bitmap to
																	// dwaw into

						Paint paint = new Paint();
						paint.setColor(Color.RED);
						paint.setTextSize(100);
						paint.setTextAlign(Paint.Align.CENTER);
						int xPos = (canvas.getWidth() / 2);
						int yPos = (int) ((canvas.getHeight() / 2) - ((paint
								.descent() + paint.ascent()) / 2));

						canvas.drawText("Date & Time: " + Current_Date, xPos,
								yPos + 400, paint);
						canvas.drawText("Lat :" + latitude + " Long :"
								+ longitude, xPos, yPos + 500, paint);
						respondent_img4.setImageBitmap(mutableBitmap);
						/* respondent_img4.setRotation(90); */
						ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
						mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 80,
								stream2);
						image4ByteArray = stream2.toByteArray();
						Log.i("respondent_img4::", "" + image4ByteArray);
						// Bitmap.createScaledBitmap(bitmap, 786, 1024, false);
					} else if ("7".equals(OffenderActivity.SelPicId)) {
						Bitmap mutableBitmap = bitmap.copy(
								Bitmap.Config.ARGB_8888, true);
						Canvas canvas = new Canvas(mutableBitmap); // bmp is the
																	// bitmap to
																	// dwaw into

						Paint paint = new Paint();
						paint.setColor(Color.RED);
						paint.setTextSize(100);
						paint.setTextAlign(Paint.Align.CENTER);
						int xPos = (canvas.getWidth() / 2);
						int yPos = (int) ((canvas.getHeight() / 2) - ((paint
								.descent() + paint.ascent()) / 2));

						canvas.drawText("Date & Time: " + Current_Date, xPos,
								yPos + 400, paint);
						canvas.drawText("Lat :" + latitude + " Long :"
								+ longitude, xPos, yPos + 500, paint);
						leaser_img.setImageBitmap(mutableBitmap);
						/* respondent_img4.setRotation(90); */
						ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
						mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 80,
								stream2);
						leaserImage1ByteArray = stream2.toByteArray();
						Log.i("leaserImage1ByteArray::", ""
								+ leaserImage1ByteArray);
						// Bitmap.createScaledBitmap(bitmap, 786, 1024, false);
					} else if ("8".equals(OffenderActivity.SelPicId)) {
						Bitmap mutableBitmap = bitmap.copy(
								Bitmap.Config.ARGB_8888, true);
						Canvas canvas = new Canvas(mutableBitmap); // bmp is the
																	// bitmap to
																	// dwaw into

						Paint paint = new Paint();
						paint.setColor(Color.RED);
						paint.setTextSize(100);
						paint.setTextAlign(Paint.Align.CENTER);
						int xPos = (canvas.getWidth() / 2);
						int yPos = (int) ((canvas.getHeight() / 2) - ((paint
								.descent() + paint.ascent()) / 2));

						canvas.drawText("Date & Time: " + Current_Date, xPos,
								yPos + 400, paint);
						canvas.drawText("Lat :" + latitude + " Long :"
								+ longitude, xPos, yPos + 500, paint);
						manager_img.setImageBitmap(mutableBitmap);
						/* respondent_img4.setRotation(90); */
						ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
						mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 80,
								stream2);
						managerImage2ByteArray = stream2.toByteArray();
						Log.i("managerImage2ByteArray::", ""
								+ managerImage2ByteArray);
						// Bitmap.createScaledBitmap(bitmap, 786, 1024, false);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (requestCode == 2) {
				Uri selectedImage = data.getData();
				String[] filePath = { MediaStore.Images.Media.DATA };
				Cursor c = getContentResolver().query(selectedImage, filePath,
						null, null, null);
				c.moveToFirst();
				int columnIndex = c.getColumnIndex(filePath[0]);
				picturePath = c.getString(columnIndex);
				c.close();

				Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
				Log.w("path of image from gallery......******************.........",
						picturePath + "");
				// picture1.setImageBitmap(thumbnail);

				if ("1".equals(OffenderActivity.SelPicId)) {
					Bitmap mutableBitmap = thumbnail.copy(
							Bitmap.Config.ARGB_8888, true);
					Canvas canvas = new Canvas(mutableBitmap); // bmp is the
																// bitmap to
																// dwaw into

					Paint paint = new Paint();
					paint.setColor(Color.RED);
					paint.setTextSize(100);
					paint.setTextAlign(Paint.Align.CENTER);
					int xPos = (canvas.getWidth() / 2);
					int yPos = (int) ((canvas.getHeight() / 2) - ((paint
							.descent() + paint.ascent()) / 2));

					canvas.drawText("Date & Time: " + Current_Date, xPos,
							yPos + 400, paint);
					canvas.drawText("Lat :" + latitude + " Long :" + longitude,
							xPos, yPos + 500, paint);
					picture1.setImageBitmap(mutableBitmap);
					/* picture1.setRotation(270); */
					ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
					mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 80,
							stream1);
					respImage1ByteArray = stream1.toByteArray();
					Log.i("respImage1ByteArray 2::", "" + respImage1ByteArray);
					// mutableBitmap.createScaledBitmap(thumbnail, 786, 1024,
					// false);

				} else if ("2".equals(OffenderActivity.SelPicId)) {
					Bitmap mutableBitmap = thumbnail.copy(
							Bitmap.Config.ARGB_8888, true);
					Canvas canvas = new Canvas(mutableBitmap); // bmp is the
																// bitmap to
																// dwaw into

					Paint paint = new Paint();
					paint.setColor(Color.RED);
					paint.setTextSize(100);
					paint.setTextAlign(Paint.Align.CENTER);
					int xPos = (canvas.getWidth() / 2);
					int yPos = (int) ((canvas.getHeight() / 2) - ((paint
							.descent() + paint.ascent()) / 2));

					canvas.drawText("Date & Time: " + Current_Date, xPos,
							yPos + 400, paint);
					canvas.drawText("Lat :" + latitude + " Long :" + longitude,
							xPos, yPos + 500, paint);
					picture2.setImageBitmap(mutableBitmap);
					/* picture2.setRotation(270); */
					ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
					mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 80,
							stream2);
					respImage2ByteArray = stream2.toByteArray();
					Log.i("respImage2ByteArray 2::", "" + respImage2ByteArray);
					// mutableBitmap.createScaledBitmap(thumbnail, 786, 1024,
					// false);
				} else if ("3".equals(OffenderActivity.SelPicId)) {
					Log.i("respondent_img1 :::", "" + respondent_img1);
					Log.i("BitMap ::", "" + thumbnail);
					Bitmap mutableBitmap = thumbnail.copy(
							Bitmap.Config.ARGB_8888, true);
					Canvas canvas = new Canvas(mutableBitmap); // bmp is the
																// bitmap to
																// dwaw into

					Paint paint = new Paint();
					paint.setColor(Color.RED);
					paint.setTextSize(100);
					paint.setTextAlign(Paint.Align.CENTER);
					int xPos = (canvas.getWidth() / 2);
					int yPos = (int) ((canvas.getHeight() / 2) - ((paint
							.descent() + paint.ascent()) / 2));

					canvas.drawText("Date & Time: " + Current_Date, xPos,
							yPos + 400, paint);
					canvas.drawText("Lat :" + latitude + " Long :" + longitude,
							xPos, yPos + 500, paint);
					respondent_img1.setImageBitmap(mutableBitmap);
					/* respondent_img1.setRotation(90); */
					ByteArrayOutputStream stream3 = new ByteArrayOutputStream();
					mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 80,
							stream3);
					image1ByteArray = stream3.toByteArray();
					Log.i("imag1ByteArray::", "" + image1ByteArray);
					// Bitmap.createScaledBitmap(bitmap, 786, 1024, false);
				} else if ("4".equals(OffenderActivity.SelPicId)) {
					Log.i("respondent_img2 :::", "" + respondent_img2);
					Log.i("BitMap ::", "" + thumbnail);
					Bitmap mutableBitmap = thumbnail.copy(
							Bitmap.Config.ARGB_8888, true);
					Canvas canvas = new Canvas(mutableBitmap); // bmp is the
																// bitmap to
																// dwaw into

					Paint paint = new Paint();
					paint.setColor(Color.RED);
					paint.setTextSize(100);
					paint.setTextAlign(Paint.Align.CENTER);
					int xPos = (canvas.getWidth() / 2);
					int yPos = (int) ((canvas.getHeight() / 2) - ((paint
							.descent() + paint.ascent()) / 2));

					canvas.drawText("Date & Time: " + Current_Date, xPos,
							yPos + 400, paint);
					canvas.drawText("Lat :" + latitude + " Long :" + longitude,
							xPos, yPos + 500, paint);
					respondent_img2.setImageBitmap(mutableBitmap);
					/* respondent_img2.setRotation(90); */
					ByteArrayOutputStream stream4 = new ByteArrayOutputStream();
					mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 80,
							stream4);
					image2ByteArray = stream4.toByteArray();
					Log.i("respondent_img2 ::", "" + image2ByteArray);
					// Bitmap.createScaledBitmap(bitmap, 786, 1024, false);
				} else if ("5".equals(OffenderActivity.SelPicId)) {
					Log.i("respondent_img3 :::", "" + respondent_img3);
					Log.i("BitMap ::", "" + thumbnail);
					Bitmap mutableBitmap = thumbnail.copy(
							Bitmap.Config.ARGB_8888, true);
					Canvas canvas = new Canvas(mutableBitmap); // bmp is the
																// bitmap to
																// dwaw into

					Paint paint = new Paint();
					paint.setColor(Color.RED);
					paint.setTextSize(100);
					paint.setTextAlign(Paint.Align.CENTER);
					int xPos = (canvas.getWidth() / 2);
					int yPos = (int) ((canvas.getHeight() / 2) - ((paint
							.descent() + paint.ascent()) / 2));

					canvas.drawText("Date & Time: " + Current_Date, xPos,
							yPos + 400, paint);
					canvas.drawText("Lat :" + latitude + " Long :" + longitude,
							xPos, yPos + 500, paint);
					respondent_img3.setImageBitmap(mutableBitmap);
					/* respondent_img3.setRotation(90); */
					ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
					mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 80,
							stream2);
					image3ByteArray = stream2.toByteArray();
					Log.i("respondent_img3::", "" + image3ByteArray);
					// Bitmap.createScaledBitmap(bitmap, 786, 1024, false);
				} else if ("6".equals(OffenderActivity.SelPicId)) {
					Log.i("respondent_img4 :::", "" + respondent_img4);
					Log.i("BitMap ::", "" + thumbnail);
					Bitmap mutableBitmap = thumbnail.copy(
							Bitmap.Config.ARGB_8888, true);
					Canvas canvas = new Canvas(mutableBitmap); // bmp is the
																// bitmap to
																// dwaw into

					Paint paint = new Paint();
					paint.setColor(Color.RED);
					paint.setTextSize(100);
					paint.setTextAlign(Paint.Align.CENTER);
					int xPos = (canvas.getWidth() / 2);
					int yPos = (int) ((canvas.getHeight() / 2) - ((paint
							.descent() + paint.ascent()) / 2));

					canvas.drawText("Date & Time: " + Current_Date, xPos,
							yPos + 400, paint);
					canvas.drawText("Lat :" + latitude + " Long :" + longitude,
							xPos, yPos + 500, paint);
					respondent_img4.setImageBitmap(mutableBitmap);
					/* respondent_img4.setRotation(90); */
					ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
					mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 80,
							stream2);
					image4ByteArray = stream2.toByteArray();
					Log.i("respondent_img4::", "" + image4ByteArray);
					// Bitmap.createScaledBitmap(bitmap, 786, 1024, false);
				} else if ("7".equals(OffenderActivity.SelPicId)) {
					Bitmap mutableBitmap = thumbnail.copy(
							Bitmap.Config.ARGB_8888, true);
					Canvas canvas = new Canvas(mutableBitmap); // bmp is the
																// bitmap to
																// dwaw into

					Paint paint = new Paint();
					paint.setColor(Color.RED);
					paint.setTextSize(100);
					paint.setTextAlign(Paint.Align.CENTER);
					int xPos = (canvas.getWidth() / 2);
					int yPos = (int) ((canvas.getHeight() / 2) - ((paint
							.descent() + paint.ascent()) / 2));

					canvas.drawText("Date & Time: " + Current_Date, xPos,
							yPos + 400, paint);
					canvas.drawText("Lat :" + latitude + " Long :" + longitude,
							xPos, yPos + 500, paint);
					leaser_img.setImageBitmap(mutableBitmap);
					/* respondent_img4.setRotation(90); */
					ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
					mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 80,
							stream2);
					leaserImage1ByteArray = stream2.toByteArray();
					Log.i("leaserImage1ByteArray::", "" + leaserImage1ByteArray);
					// Bitmap.createScaledBitmap(bitmap, 786, 1024, false);
				} else if ("8".equals(OffenderActivity.SelPicId)) {
					Bitmap mutableBitmap = thumbnail.copy(
							Bitmap.Config.ARGB_8888, true);
					Canvas canvas = new Canvas(mutableBitmap); // bmp is the
																// bitmap to
																// dwaw into

					Paint paint = new Paint();
					paint.setColor(Color.RED);
					paint.setTextSize(100);
					paint.setTextAlign(Paint.Align.CENTER);
					int xPos = (canvas.getWidth() / 2);
					int yPos = (int) ((canvas.getHeight() / 2) - ((paint
							.descent() + paint.ascent()) / 2));

					canvas.drawText("Date & Time: " + Current_Date, xPos,
							yPos + 400, paint);
					canvas.drawText("Lat :" + latitude + " Long :" + longitude,
							xPos, yPos + 500, paint);
					manager_img.setImageBitmap(mutableBitmap);
					// respondent_img4.setRotation(90);
					ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
					mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 80,
							stream2);
					managerImage2ByteArray = stream2.toByteArray();
					Log.i("managerImage2ByteArray::", ""
							+ managerImage2ByteArray);
					// Bitmap.createScaledBitmap(bitmap, 786, 1024, false);
				}
			}
		}
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unused")
	private void showToast(String msg) {
		// TODO Auto-generated method stub
		// Toast.makeText(getApplicationContext(), "" + msg,
		// Toast.LENGTH_SHORT).show();
		Toast toast = Toast.makeText(getApplicationContext(), "" + msg,
				Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		View toastView = toast.getView();

		ViewGroup group = (ViewGroup) toast.getView();
		TextView messageTextView = (TextView) group.getChildAt(0);
		// messageTextView.setTextSize(24);

		toastView.setBackgroundResource(R.drawable.toast_background);
		toast.show();
	}

	@Override
	public void onBackPressed() {

	}

}