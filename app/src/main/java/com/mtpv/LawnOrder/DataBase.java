package com.mtpv.LawnOrder;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

public class DataBase extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;// Database Version 
	    public static final String DATABASE_NAME = "auth_User4.db";// Database Name
	    
	    public static  String LOGIN_TABLE = "LOGIN_TABLE";
	    // Table Names
	    protected static  String LICENSE_DETAILS_TABLE = "LICENSE_DETAILS";
	    
	    public static  String IP_TABLE="IP_TABLE";
	    
	    public static  String DEVICE_TABLE="DEVICE_TABLE";
	    
	    public static  String Bluetooth = "bluetooth";
	    
	    private static  String IpSetting = "ipsetting";
	   
	    protected static final String ID_DETAILS_TABLE = "ID_DETAILS";
	    
	    protected static final String CREATE_ID_DETAILS_TABLE =  "CREATE TABLE  IF NOT EXISTS "
	            + ID_DETAILS_TABLE + "(ID_CODE  VARCHAR , ID_DETAILS VARCHAR)";;
	    
        protected static final String SECTION_DETAILS_TABLE = "SECTION_DETAILS";
	    protected static final String CREATE_SECTION_DETAILS_TABLE =  "CREATE TABLE  IF NOT EXISTS "
	            + SECTION_DETAILS_TABLE + "(SECTION_CD  VARCHAR , SECTION_NAME VARCHAR,"
	            		+ "SECTION_DESC VARCHAR)";;
	    
	  //  private static final String LOGIN_DETAILS = "LOGIN_DETAILS";
	    
	 // AuthenticateUser column names
	    static final String TAG_MAIN = "AUTHENTICATION_DETAILS";
	    static final String PID_CD = "PID_CD";
	    static final String PID_NAME = "PID_NAME";
	    static final String PS_CODE = "PS_CODE";
	    static final String PS_NAME = "PS_NAME";
	    static final String CADRE_CD = "CADRE_CD";
	    static final String CADRE = "CADRE";
	    static final String UNIT_CODE = "UNIT_CODE";
	    static final String UNIT_NAME = "UNIT_NAME";
	    static final String SECURITY_CD = "SECURITY_CD";
	    
	
	    //IpSetting column name
	    
	    static final String IP_Address = "IP_Address";
	    
	
	    //bluetooth column names
	    
	    static final String BT_Name = "BT_Name";
	    
	    static final String DEVICE_NAME = "DEVICE_NAME";
	    
	    
	    
	// Table Create Statements
	    
	    //TABLE
	    public static final String CREATE_LOGIN_TABLE = "CREATE TABLE  IF NOT EXISTS "
	            + LOGIN_TABLE + "(" + PID_CD + " VARCHAR," + PID_NAME
	            + " VARCHAR," + PS_CODE + " VARCHAR," + PS_NAME + " VARCHAR," + CADRE_CD + " VARCHAR," + CADRE + " VARCHAR ,"
	            + UNIT_CODE + " VARCHAR," + UNIT_NAME + " VARCHAR)";
	    
	    
	    
	    //CREATE_DEVICE_TABLE_DETAILS
	
	    public static final String CREATE_IP_TABLE = "CREATE TABLE  IF NOT EXISTS "
	            + IP_TABLE + "(IP  VARCHAR )";
	    
	 // CREATE DEVICE DETAILS
	    
	    public static final String CREATE_DEVICE_TABLE = "CREATE TABLE  IF NOT EXISTS "
	            + DEVICE_TABLE + "(DEVICENAME  VARCHAR)";

	    
	    //Bluetooth table
	    
	    public static final String CREATE_Bluetooth = "CREATE TABLE "
	            + Bluetooth + "(" + BT_Name + " VARCHAR )";
	  
	    
	    //
	    @SuppressWarnings("unused")
		private static final String CREATE_AUTHENTICATION_DETAILS = "CREATE TABLE "
	    		+ LOGIN_TABLE + "(" + BT_Name + " VARCHAR )"; 
	    
	    //IpSettings
	    
	    private static final String CREATE_IPSetting = "CREATE TABLE "
	            + IpSetting + "(" + IP_Address + " VARCHAR )";
	    
	    
	    // DatabaseHelper
	    public DataBase(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }
	    
	    
	 //create SQLiteDataBase db   
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			//db.execSQL("DROP TABLE IF EXISTS TABLE");
			// creating required tables
			Log.i("onCreate called","yes");
			//db.execSQL(CREATE_LOGIN_TABLE);
			db.execSQL(CREATE_Bluetooth);
			db.execSQL(CREATE_IPSetting);
			
		}

		
		
		
		public void createTable(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			//db.execSQL("DROP TABLE IF EXISTS TABLE");
			// creating required tables
			Log.i("onCreate called","yes");
			//db.execSQL(CREATE_LOGIN_TABLE);
			//db.execSQL(CREATE_Bluetooth);
//			db.execSQL(CREATE_IPSetting);
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
			// on upgrade drop older tables
			db.execSQL(DataBase.CREATE_ID_DETAILS_TABLE);
	        db.execSQL("DROP TABLE IF EXISTS " + LOGIN_TABLE);
	        db.execSQL("DROP TABLE IF EXISTS " + Bluetooth);
	        db.execSQL("DROP TABLE IF EXISTS " + IpSetting);
	        onCreate(db);
			
		}
		
		// ------------------------ "CREATE_TABLE_AuthenticateUser" table methods ----------------//
		 
		
		public  String insertAuthenticateUser(String resp, Context context) {
			// TODO Auto-generated method stub
			// Log.i("insertAuthenticateUser called ::",resp);
			if (resp != null) {
                try {
                    JSONObject jsonObj = new JSONObject(resp);
                     
                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray(TAG_MAIN);
 
                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                         
                        String PID_CD1 = c.getString(DataBase.PID_CD);
                        String PID_NAME1 = c.getString(DataBase.PID_NAME);
                        String PS_CODE1 = c.getString(DataBase.PS_CODE);
                        String PS_NAME1 = c.getString(DataBase.PS_NAME);
                        String CADRE1 = c.getString(DataBase.CADRE);
                        String CADRE_CD1 = c.getString(DataBase.CADRE_CD);
                        String UNIT_CODE1 = c.getString(DataBase.UNIT_CODE);
                        String UNIT_NAME1 = c.getString(DataBase.UNIT_NAME);
 
                        UserLogin_Activity.Pid_code= PID_CD1;
                        UserLogin_Activity.Pid_Name=PID_NAME1;
				        UserLogin_Activity.Ps_code=PS_CODE1;
						UserLogin_Activity.Ps_Name=PS_NAME1;
						UserLogin_Activity.CADRE_CODE=CADRE1;
						UserLogin_Activity.CADRE_NAME=CADRE_CD1;
						UserLogin_Activity.UNIT_CODE=UNIT_CODE1;
						UserLogin_Activity.UNIT_NAME=UNIT_NAME1;
						
                      //Log.d("PS_NAME from json", PS_NAME); 
            			ContentValues values = new ContentValues();
            			values.put(PID_CD, PID_CD1);
            			values.put(PID_NAME, PID_NAME1);
            			values.put(PS_CODE, PS_CODE1);
            			values.put(PS_NAME, PS_NAME1);
            			values.put(CADRE_CD, CADRE_CD1);
            			values.put(CADRE,CADRE1);
            			values.put(UNIT_CODE,UNIT_CODE1);
            			values.put(UNIT_NAME,UNIT_NAME1);
              			
            			//SharedPreferences pref = context.getSharedPreferences("Login", ); 
            			SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        			    Editor editor = pref.edit();
        			 
        			/**************** Storing data as KEY/VALUE pair *******************/
        			    editor.putString("pidCode", PID_CD1);  // Saving string
        			    editor.putString("pidName", PID_NAME1);  // Saving string
        			    editor.putString("psCode", PS_CODE1);  // Saving string
        			    editor.putString("psName", PS_NAME1);  // Saving string
        			    editor.putString("cadreCd", CADRE_CD1);  // Saving string
        			    editor.putString("cadreName",CADRE1);
        			    editor.putString("unitCd",UNIT_CODE1);
        			    editor.putString("unitName",UNIT_NAME1);// Saving string
        			    // Save the changes in SharedPreferences
        			    editor.commit(); // commit changes
        			 
        			/**************** Get SharedPreferences data *******************/
        			 
        			// If value for key not exist then return second param value - In this case null
        			 
        			 Log.i("shared response ::::::::::::::", pref.getString("pidName", null))  ;         // getting pidName

        			 /**************** insert  data *******************/
        			 	SQLiteDatabase db = this.getWritableDatabase();
            			db.execSQL(CREATE_LOGIN_TABLE);
            			db.execSQL("delete from " + LOGIN_TABLE);            			
              			db.insert(LOGIN_TABLE, null, values); // Inserting Row
            			
            			 System.out.println("********************* TABLE Insertion Successfully **********************************");
            			 
            			 
            			 String selectQuery = "SELECT  * FROM " + LOGIN_TABLE;//LOGIN_DETAILS
            		       // SQLiteDatabase db = this.getWritableDatabase();
            		        Cursor cursor = db.rawQuery(selectQuery, null);
            		 
            		        // looping through all rows and adding to list
            		        if (cursor.moveToFirst()) {
            		            do {
            		            	 Log.i("1 :",""+ cursor.getString(0));
            		            	 Log.i("2 :",""+cursor.getString(1));
            		            	 Log.i("3 :",""+cursor.getString(2));
            		            	 Log.i("4 :",""+cursor.getString(3));
            		            	 Log.i("5 :",""+cursor.getString(4));
            		            	 Log.i("6 :",""+cursor.getString(5));
            		            	 Log.i("7 :",""+cursor.getString(6));
            		            	 Log.i("8 :",""+cursor.getString(7));
            		                
            		            } while (cursor.moveToNext());
            		        }
            			 db.close();
                      }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
			
		 return PID_CD;
			
		}
	
		public String DeviceName(String deviceName) {
			// TODO Auto-generated method stub
			Log.i(" response :: ", " insertDeviceName called  ");
			SQLiteDatabase db = this.getWritableDatabase();
			db.execSQL(DataBase.CREATE_DEVICE_TABLE);
			db.execSQL("delete from " + DataBase.CREATE_DEVICE_TABLE); 
			ContentValues values = new ContentValues();
			
			values.put(DEVICE_NAME, deviceName);
			Log.i("Devicename ::::::::::::::", deviceName)  ;
			 
			db.close();
			return deviceName;
			
		}
		
		public Cursor Eq(String sQuery) {
			Cursor res = null;
			SQLiteDatabase db = this.getReadableDatabase();
			try{
			res = db.rawQuery(sQuery, null);
			// db.close();
			//return res;
			}catch(Exception e){
				System.out.println("error Eq" +e);//return res;
			}
			return res;
		}


		public String Bluetooth(String bT_Name2) {
			// TODO Auto-generated method stub
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			
			values.put(BT_Name, bT_Name2);
			
			db.insert(Bluetooth, null, values); // Inserting Row
			
			 System.out.println("********************* Buletooth Device Name Insertion Successfully **********************************");
			 
			 db.close();
			  
			 return bT_Name2;
		}


		public String IpSetting(String ipAddress) {
			// TODO Auto-generated method stub
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			
			values.put(IP_Address, ipAddress);
			
			db.execSQL("delete from IpSetting");
			db.insert(IpSetting, null, values); 
			
			Log.i("IP Address ::::::::::::::", ipAddress)  ;
			
			System.out.println("********************* IP Address Insertion Successfully **********************************");
			 
			db.close();
			
			return ipAddress;
			
		}
		
		


		public void insertIDMaster(String resp, Context applicationContext) {
			Log.i(" response :: ", " insertIDMaster called  ");
			try {
				if (resp != null) {
	                try {
	                	SQLiteDatabase db = this.getWritableDatabase();
	                    db.execSQL(DataBase.CREATE_ID_DETAILS_TABLE);
            			db.execSQL("delete from " + DataBase.ID_DETAILS_TABLE);  
            			
	                	JSONObject jsonObj = new JSONObject(resp);
	                     
	                    // Getting JSON Array node
	                    JSONArray contacts = jsonObj.getJSONArray("ID_DETAILS");
	                    ContentValues values = new ContentValues();
	                    
	                    // looping through All Contacts
	                    for (int i = 0; i < contacts.length(); i++) {
	                        JSONObject c = contacts.getJSONObject(i);
	                         
	                        String ID_CODE1 = c.getString("ID_CODE");
	                        String ID_DETAILS1 = c.getString("ID_DETAILS");
	                        Log.i("ID_CODE1 :: ",  ID_CODE1);
	                        Log.i("ID_DETAILS1 :: ",  ID_DETAILS1);
	                      
	            			values.put("ID_CODE", ID_CODE1);
	            			values.put("ID_DETAILS", ID_DETAILS1);
	            			db.insert(DataBase.ID_DETAILS_TABLE, null, values); // Inserting Row
	            			 System.out.println("********************* ID_DETAILS_TABLE TABLE Insertion Successfully **********************************");
	        			
	                      }//end of for loop
	                    
	                    /**************** insert  data *******************/
            			 String selectQuery = "SELECT  * FROM " + DataBase.ID_DETAILS_TABLE;
            		     Cursor cursor = db.rawQuery(selectQuery, null);
            		 
            		        // looping through all rows and adding to list
            		        if (cursor.moveToFirst()) {
            		            do {
            		            	 Log.i("ID CODE FROM TABLE:",""+ cursor.getString(0));
            		            	 Log.i("ID DETAILS  FROM TABLE :",""+cursor.getString(1));
            		            	
            		                
            		            } while (cursor.moveToNext());
            		        }
            			 db.close();
            			 
	                } catch (JSONException e) {
	                    e.printStackTrace();
	                }
	            } else {
	                Log.e("ServiceHandler", "Couldn't get any data from the url  getIDProofMaster  ");
	            }
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}


		public void insertSectionMaster(String resp, Context applicationContext) {
			Log.i(" response :: ", " insertSectionMaster called  ");
			try {
				if (resp != null) {
	                try {
	                	SQLiteDatabase db = this.getWritableDatabase();
	                    db.execSQL(DataBase.CREATE_SECTION_DETAILS_TABLE);
            			db.execSQL("delete from " + DataBase.SECTION_DETAILS_TABLE);  
            			
	                	JSONObject jsonObj = new JSONObject(resp);
	                     
	                    // Getting JSON Array node
	                    JSONArray contacts = jsonObj.getJSONArray("SECTION_DETAILS");
	                    ContentValues values = new ContentValues();
	                    
	                    // looping through All Contacts
	                    for (int i = 0; i < contacts.length(); i++) {
	                        JSONObject c = contacts.getJSONObject(i);
	                         
	                        String SECTION_CD = c.getString("SECTION_CD");
	                        String SECTION_NAME = c.getString("SECTION_NAME");
	                        String SECTION_DESC = c.getString("SECTION_DESC");
	                        
	            			values.put("SECTION_CD", SECTION_CD);
	            			values.put("SECTION_NAME", SECTION_NAME);
	            			values.put("SECTION_DESC", SECTION_DESC);
	            			db.insert(DataBase.SECTION_DETAILS_TABLE, null, values); // Inserting Row
	            			 System.out.println("*********************SECTION_DETAILS_TABLE  TABLE Insertion Successfully **********************************");
	        			
	                      }//end of for loop
	                    
	                    /**************** insert  data *******************/
            			 String selectQuery = "SELECT  * FROM " + DataBase.SECTION_DETAILS_TABLE;
            		     Cursor cursor = db.rawQuery(selectQuery, null);
            		 
            		        // looping through all rows and adding to list
            		        if (cursor.moveToFirst()) {
            		            do {
            		            	 Log.i("SECTION_CD FROM TABLE:",""+ cursor.getString(0));
            		            	 Log.i("SECTION_NAME  FROM TABLE :",""+cursor.getString(1));
            		            	 Log.i("SECTION_DESC FROM TABLE :",""+cursor.getString(2));
            		            	
            		                
            		            } while (cursor.moveToNext());
            		        }
            			 db.close();
            			 
	                } catch (JSONException e) {
	                    e.printStackTrace();
	                }
	            } else {
	                Log.e("ServiceHandler", "Couldn't get any data from the url  getSectionDetails  ");
	            }
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}


		public  Map<String, String> getIdMap(Context context) {
			Map<String, String> idMap=new LinkedHashMap<String, String>();
			try {
				SQLiteDatabase db = this.getWritableDatabase();
				 String selectQuery = "SELECT  * FROM " + DataBase.ID_DETAILS_TABLE;
			     Cursor cursor = db.rawQuery(selectQuery, null);
			 
			        // looping through all rows and adding to list
			        if (cursor.moveToFirst()) {
			            do {
			            	 Log.i("ID CODE FROM TABLE:",""+ cursor.getString(0));
			            	 Log.i("ID DETAILS  FROM TABLE :",""+cursor.getString(1));
			            	 idMap.put(cursor.getString(0).toString(),cursor.getString(1).toString());
			            } while (cursor.moveToNext());
			        }
				 db.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		return idMap;
		}


		public Map<String, String> getSecMap(Context applicationContext) {
			Map<String, String> secMap=new LinkedHashMap<String, String>();
			try {
				SQLiteDatabase db = this.getWritableDatabase();
				 String selectQuery = "SELECT  * FROM " + DataBase.SECTION_DETAILS_TABLE;
			     Cursor cursor = db.rawQuery(selectQuery, null);
			 
			        // looping through all rows and adding to list
			        if (cursor.moveToFirst()) {
			            do {
			            	 Log.i("SECTION CODE FROM TABLE:",""+ cursor.getString(0));
			            	 Log.i("SECTION NAME  FROM TABLE :",""+cursor.getString(1));
			            	 Log.i("SECTION DESC  FROM TABLE :",""+cursor.getString(2));
			            	 secMap.put(cursor.getString(0).toString(),cursor.getString(1).toString()+
			            			 "-("+cursor.getString(2)+")");
			            	
			                
			            } while (cursor.moveToNext());
			        }
				 db.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		return secMap;
		}

	
}
