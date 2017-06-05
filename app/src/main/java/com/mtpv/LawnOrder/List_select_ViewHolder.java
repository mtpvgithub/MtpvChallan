package com.mtpv.LawnOrder;

import android.widget.CheckBox;
import android.widget.TextView;

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