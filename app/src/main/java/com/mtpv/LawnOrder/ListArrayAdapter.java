package com.mtpv.LawnOrder;

import java.util.List;  

import com.example.button.R;

import android.content.Context;  
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.ArrayAdapter;  
import android.widget.CheckBox;  
import android.widget.TextView;  
  
/** Custom adapter for displaying an array of Planet objects. */  
public class ListArrayAdapter extends ArrayAdapter<mItems_list> {  
  
 private LayoutInflater inflater;  
  
 public ListArrayAdapter(Context context, List<mItems_list> planetList) {  
 super(context, R.layout.list_row, R.id.rowTextView, planetList);  
 //Cache the LayoutInflate to avoid asking for a new one each time.  
  inflater = LayoutInflater.from(context);  
 }  
  
 @Override  
 public View getView(int position, View convertView, ViewGroup parent){  
  mItems_list planet = (mItems_list) this.getItem(position);  
  CheckBox checkBox;  
  TextView textView;  
  
  // Create a new row view  
  if (convertView == null) {  
  convertView = inflater.inflate(R.layout.list_row, null);  
  
  textView = (TextView) convertView.findViewById(R.id.rowTextView);  
  checkBox = (CheckBox) convertView.findViewById(R.id.CheckBox01);  
  
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
  textView.setText(planet.getName());  
  
  return convertView;  
 }  
  
}  