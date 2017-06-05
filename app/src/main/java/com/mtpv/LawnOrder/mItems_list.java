package com.mtpv.LawnOrder;

import android.widget.Toast;

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