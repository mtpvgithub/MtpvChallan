package com.mtpv.LawnOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.button.R;

@SuppressLint({ "InflateParams", "ShowToast" })
public class Add_SectionsActivity extends Activity {

	public static EditText add, decription_text, remarks_text;
	Button btn;
	ImageView save_btn,reset_btn,back_btn;
	ListView list;
	ArrayList<String> arr;
	ListAdapter aliAdapter;
	private ListView mainListView;
	@SuppressWarnings("unused")
	private mItems_list[] itemss;
	private ArrayAdapter<mItems_list> listAdapter = null; 
	ArrayList<String> checked = new ArrayList<String>();
	public static List<String> checkedList=new  ArrayList<String>();
	
//	public static Map<String,String> idMap=null;
	public static Map<String,String> secMap=null;
	 private String blockCharacterSet = ",'";
	
	Button add_section_btn, remove_section_btn;
	LinearLayout linear1,linear2;
	ListView mylist;
	
	 CheckBox checkBox;  
	  TextView textView;  
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add__sections);
		checkedList=new  ArrayList<String>();
		
		mainListView = (ListView) findViewById(R.id.mylist);
		mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {  
		 
		@Override  
		public void onItemClick(AdapterView<?> parent, View item,  
		   int position, long id) {  

			   mItems_list planet = listAdapter.getItem(position); 
			   Log.i("checked name :",planet.getName());
			   
			   if(Add_SectionsActivity.checkedList.contains((planet.getName()))){
				   Log.i("already checked name :",planet.getName());
				   Add_SectionsActivity.checkedList.remove(planet.getName());
				   planet.toggleChecked();
				   
				   List_select_ViewHolder viewHolder = (List_select_ViewHolder) item.getTag();  
				   viewHolder.getCheckBox().setChecked(planet.isChecked());
				   viewHolder.getCheckBox().setVisibility(View.GONE);
			   }else {
				   Add_SectionsActivity.checkedList.add(planet.getName());
				   planet.toggleChecked();  
				   List_select_ViewHolder viewHolder = (List_select_ViewHolder) item.getTag();  
				   viewHolder.getCheckBox().setChecked(planet.isChecked());
				   viewHolder.getCheckBox().setVisibility(View.VISIBLE);
				    
     			}
			 }  
		  });  
		 
		 Add_SectionsActivity.secMap=new DataBase(getApplicationContext()).getSecMap(getApplicationContext());
			List<String> seclist = new ArrayList<String>();
			List<mItems_list> mlist=new ArrayList<mItems_list>();
			for(String secCode: secMap.keySet()){
				seclist.add(secMap.get(secCode));
				mlist.add( new mItems_list(secMap.get(secCode)));
			}

		  ArrayList<mItems_list> planetList = new ArrayList<mItems_list>();  
		  //planetList.addAll(Arrays.asList(sections));  
		  planetList.addAll(mlist); 
		  // Set our custom array adapter as the ListView's adapter.  
		  listAdapter = new ListArrayAdapter(this, planetList);  
		  mainListView.setAdapter(listAdapter); 

		  decription_text = (EditText)findViewById(R.id.decription_text);
		  decription_text.setFilters(new InputFilter[] { filter });
		
		  back_btn = (ImageView)findViewById(R.id.back_offender);
		  back_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(Add_SectionsActivity.secMap==null){
					OffenderActivity.add_sections_tick.setVisibility(View.GONE);
					Toast toast = Toast.makeText(getApplicationContext(), "Not Select atleast One Section", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, -200);
					View toastView = toast.getView();
			    	toastView.setBackgroundResource(R.drawable.toast_background);
				    toast.show();
				    finish();
				}else if(Add_SectionsActivity.checkedList!=null && Add_SectionsActivity.checkedList.size()==0){
					OffenderActivity.add_sections_tick.setVisibility(View.GONE);
					Toast toast = Toast.makeText(getApplicationContext(), "Not Select atleast One Section", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, -200);
					View toastView = toast.getView();
			    	toastView.setBackgroundResource(R.drawable.toast_background);
				    toast.show();
				    finish();
				}
			}
		});
		
		save_btn = (ImageView)findViewById(R.id.save_offender);
		save_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(Add_SectionsActivity.secMap==null){
					Toast toast = Toast.makeText(getApplicationContext(), "Please Click on Violation Name to Select", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, -200);
					View toastView = toast.getView();
			    	toastView.setBackgroundResource(R.drawable.toast_background);
				    toast.show();
				}else if(Add_SectionsActivity.checkedList!=null && Add_SectionsActivity.checkedList.size()==0){
					Toast toast = Toast.makeText(getApplicationContext(), "Please Click on Violation Name to Select", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, -200);
					View toastView = toast.getView();
			    	toastView.setBackgroundResource(R.drawable.toast_background);
				    toast.show();
				}else{
					finish();
					OffenderActivity.add_sections_tick.setVisibility(View.VISIBLE);
				}
			}
		});
		
		reset_btn = (ImageView)findViewById(R.id.reset_btn);
		reset_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				decription_text.setText("");
			}
		});
	}

	 private InputFilter filter = new InputFilter() {
	public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
		// TODO Auto-generated method stub
	    if (source != null && blockCharacterSet.contains(("" + source))) {
	    	Toast.makeText(getApplicationContext(), "Not Allowed", Toast.LENGTH_LONG);
	    	
            return "";
        }
		return null;
	}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 1, Menu.NONE, "Products");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case 1:

			for (int i = 0; i < checked.size(); i++) {
				Log.d("pos : ", "" + checked.get(i));
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/** Holds planet data. */
	public class mItems_list {  
		 private String name = "";  
		 private boolean checked = false;  
		  
		 public mItems_list() {  
		 }  
		  
		 public mItems_list(String name) {  
		  this.name = name;  
		  
		 }  
		  
		 public mItems_list(String name, boolean checked) {  
		  this.name = name;  
		  this.checked = checked; 
		   Toast.makeText(null, "Hi", Toast.LENGTH_LONG).show();
		 }  
		  
		 public String getName() {  
		  return name;  
		 }  
		  
		 public void setName(String name) {  
		  this.name = name;  
		 }  
		  
		 public boolean isChecked() {  
		  return checked;  
		 }  
		  
		 public void setChecked(boolean checked) {  
		  this.checked = checked;  
		 }  
		  
		 public String toString() {  
		  return name;  
		 }  
		  
		 public void toggleChecked() {  
		  checked = !checked;  
		 }  
		} 

	/** Holds child views for one row. */
	public class List_select_ViewHolder {  
		 private CheckBox checkBox;  
		 private TextView textView;  
		  
		 public List_select_ViewHolder() {  
		 }  
		  
		 public List_select_ViewHolder(TextView textView, CheckBox checkBox) {  
		  this.checkBox = checkBox;  
		  this.textView = textView;  
		 }  
		  
		 public CheckBox getCheckBox() {  
		  return checkBox;

		 }  
		  
		 public void setCheckBox(CheckBox checkBox) {  
		  this.checkBox = checkBox;  
		 }  
		  
		 public TextView getTextView() {  
		  return textView;  
		 }  
		  
		 public void setTextView(TextView textView) {  
		  this.textView = textView;  
		 }  
		}  

	/** Custom adapter for displaying an array of Planet objects. */  
	public class ListArrayAdapter extends ArrayAdapter<mItems_list> {  
		  
		 private LayoutInflater inflater;  
		  
		 public ListArrayAdapter(Context context, List<mItems_list> planetList) {  
		 super(context, R.layout.section_list, R.id.section_text, planetList);  
		 //Cache the LayoutInflate to avoid asking for a new one each time.  
		  inflater = LayoutInflater.from(context);  
		 }  
		  
		 @Override  
		 public View getView(int position, View convertView, ViewGroup parent){  
		  mItems_list planet = (mItems_list) this.getItem(position);  
		  
		  // Create a new row view  
		  if (convertView == null) {  
		  convertView = inflater.inflate(R.layout.section_list, null);  
		  
		  textView = (TextView) convertView.findViewById(R.id.section_text);  
		  checkBox = (CheckBox) convertView.findViewById(R.id.CheckBox_sections);  
		  
		  checkBox.setVisibility(View.GONE);
		  
		  convertView.setTag(new List_select_ViewHolder(textView, checkBox));  
		  
		  // If CheckBox is toggled, update the planet it is tagged with.  
		  checkBox.setOnClickListener(new View.OnClickListener() {  
		   public void onClick(View v) {  
		      
			CheckBox cb = (CheckBox) v;  
		    mItems_list planet = (mItems_list) cb.getTag();  
		    planet.setChecked(cb.isChecked());  
		   }  
		  });  
		  }  
		  // Re-use existing row view  
		  else {  
		  
		  List_select_ViewHolder viewHolder = (List_select_ViewHolder) convertView  
		     .getTag();  
		   checkBox = viewHolder.getCheckBox();  
		   textView = viewHolder.getTextView();  
		  }  
		  
		  checkBox.setTag(planet);  
		  
		  // Display planet data  
		  checkBox.setChecked(planet.isChecked());
		  if (checkBox.isChecked()) {
			  checkBox.setVisibility(View.VISIBLE);
			  textView.setText(planet.getName());  
		  }else {
			  checkBox.setVisibility(View.GONE);
			  textView.setText(planet.getName());
		 }
		  
		  return convertView;  
		 }  
		  
		}  

	@Override
	public void onBackPressed() {
		
	}
}
