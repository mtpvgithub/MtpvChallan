package com.mtpv.LawnOrder;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.button.R;

public class IP_SettingsActivity extends Activity {

	EditText server_ET ;	
	//public static String Test = "http://192.168.11.55:8080";
	//public static String Live = "http://192.168.11.4";
	public static String Live = "http://www.echallan.org/";
	public static String Test = "http://125.16.1.70:8080/";
	Button cancel, save;
	
	RadioButton live, test ;
	RadioGroup liveTest_group ;
	
	public static ImageView update_apk ;

	static String IPA;
	public static String IP;
	final int ALERT_USER = 1;
	final int PROGRESS_DIALOG = 2;
	
	//String server = "192.168.11.9";
	String server = "125.16.1.69";
	int port = 99 ;
	String username = "ftpuser";
	String password = "Dk0r$l1qMp6" ;
	String filename = "Version-1.1.apk";
	
	private static final int BUFFER_SIZE = 4096;
	ProgressBar progress;
	Dialog dialog;
	int downloadedSize = 0;
	int totalSize = 0;
	TextView cur_val, version_text;
	
	public static String apkurl ;
	public static String version;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ip__settings);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		version_text = (TextView)findViewById(R.id.version);
		version  = version_text.getText().toString().trim() + ".apk";
		
		Log.i("APP Version :::", ""+ version);
		
		apkurl = "ftp://192.168.11.9:99/23/TabAPK/"+version ;	

		server_ET = (EditText)findViewById(R.id.servr_url);
		live = (RadioButton)findViewById(R.id.live);
		test = (RadioButton)findViewById(R.id.test);
		
		update_apk = (ImageView)findViewById(R.id.update_apk);
		
		
		live.setChecked(true);
		if (live.isChecked()) {
			server_ET.setText(Live);	
		}
		
		
		live.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				server_ET.setText(Live);
			}
		});
		test.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				server_ET.setText(Test);
			}
		});
		
		update_apk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				showDialog(ALERT_USER);
			}
		});
		

		if (test.isChecked()) {
			DataBase DBH = new DataBase(getApplicationContext());
			String IpSettingQuery = "select IP_Address from ipsetting";
			Cursor res = DBH.Eq(IpSettingQuery);
			res.moveToFirst();
	        while(res.isAfterLast() == false){
				 IP = res.getString(res.getColumnIndex("IP_Address"));		
		         res.moveToNext();
			 }
			if(IP != null){
				server_ET.setText(IP);
				Log.e("IpSetting IP", IP);
			}else{
				//server_ET.setText("");
			//	IpSettings.IP="http://192.168.11.8";
			}
				
		}
		
		
		cancel= (Button)findViewById(R.id.back);
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		/************************SAVING IP INTO DATABASE******************************/
		save = (Button)findViewById(R.id.save);
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try{
					String IpAddress = server_ET.getText().toString();
					DataBase DBH= new DataBase(getApplicationContext());
					
					String DBHIPAddress = DBH.IpSetting(IpAddress.toString().trim());
					IP_SettingsActivity.IP=DBHIPAddress.toString().trim();				
					Log.e("DBHIPAddress", DBHIPAddress);
					finish();
					ContentValues values = new ContentValues();
        			values.put("IP", IpAddress);
					SQLiteDatabase db = openOrCreateDatabase(DataBase.DATABASE_NAME,MODE_PRIVATE, null);
        			db.execSQL(DataBase.CREATE_IP_TABLE);
        			db.execSQL("delete from " + DataBase.IP_TABLE);            			
          			db.insert(DataBase.IP_TABLE, null, values); // Inserting Row
        			
        			 System.out.println("********************* TABLE Insertion Successfully **********************************");
        			 String selectQuery = "SELECT  * FROM " + DataBase.IP_TABLE;
        		       // SQLiteDatabase db = this.getWritableDatabase();
        		        Cursor cursor = db.rawQuery(selectQuery, null);
        		 
        		        // looping through all rows and adding to list
        		        if (cursor.moveToFirst()) {
        		            do {
        		            	 Log.i("IP VALUE :",""+ cursor.getString(0));
        		            } while (cursor.moveToNext());
        		        }
        			 db.close();
        			 
					Toast toast = Toast.makeText(getApplicationContext(), "IP Settings Saved Successfully", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					View toastView = toast.getView();
			    	toastView.setBackgroundResource(R.drawable.toast_background);
				    toast.show();
					finish();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
		});
		
		/************************SAVING IP INTO DATABASE******************************/
	}
	
class Async_UpdateApk extends AsyncTask<Void, Void, String>{
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showDialog(PROGRESS_DIALOG);
			
		}

		@Override
		protected String doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
				 
		    FTPClient ftpClient = new FTPClient();

		    try {
		        ftpClient.connect(server, port);
		        ftpClient.login(username, password);
		        ftpClient.enterLocalPassiveMode();
		        ftpClient.setBufferSize(1024*1024);
		        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

		        //ftp://192.168.11.9:99/23/TabAPK/Version-1.5.1.txt
		        File downloadFile1 = new File("/sdcard/Download/ePettyCase_HYD.apk");
		        String remoteFile1 = "/23/TabAPK"+"/"+version;
		        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile1));
		        boolean success = ftpClient.retrieveFile(remoteFile1, outputStream);
		        		        
		        FileOutputStream fileOutput = new FileOutputStream(downloadFile1);
		        InputStream inputStream = ftpClient.retrieveFileStream(remoteFile1);
		        if (inputStream == null || ftpClient.getReplyCode() == 550) {
		        	// it means that file doesn't exist.
		        	fileOutput.close();
		            outputStream.close();
		            
		        	runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							removeDialog(PROGRESS_DIALOG);
				            
							//showToast("your is Upto Date");	
							TextView title = new TextView(IP_SettingsActivity.this);
							title.setText("Hyderabad ePettycase");
							title.setBackgroundColor(Color.RED);
							title.setGravity(Gravity.CENTER);
							title.setTextColor(Color.WHITE);
							title.setTextSize(26);
							title.setTypeface(title.getTypeface(), Typeface.BOLD);
							title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hyd_city_small, 0, R.drawable.hyd_city_small, 0);
							title.setPadding(20, 0, 20, 0);
							title.setHeight(70);
							
							String otp_message = "Your Application is Upto Date \n No Need to Update" ;
							
							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(IP_SettingsActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
							alertDialogBuilder.setCustomTitle(title);
							alertDialogBuilder.setIcon(R.drawable.dialog_logo);
							alertDialogBuilder.setMessage(otp_message);
							alertDialogBuilder.setCancelable(false);
							alertDialogBuilder.setPositiveButton("Ok",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub
											 
										}
									});

						        AlertDialog alertDialog = alertDialogBuilder.create();
						        alertDialog.show();
						      
						        alertDialog.getWindow().getAttributes();

						        TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
						        textView.setTextSize(28);
						        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
						        textView.setGravity(Gravity.CENTER);
						        
						        Button btn1 = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
						        btn1.setTextSize(22);
						        btn1.setTextColor(Color.WHITE);
						        btn1.setTypeface(btn1.getTypeface(), Typeface.BOLD);
						        btn1.setBackgroundColor(Color.RED);  
							
						}
					});
	        	}
		        
		        else{
		            totalSize = remoteFile1.length();
		 
		            runOnUiThread(new Runnable() {
		                public void run() {
		                	removeDialog(PROGRESS_DIALOG);
		                	showProgress(server);
		                    progress.setMax(totalSize);
		                }               
		            });
		             
		            //create a buffer...
		            byte[] buffer = new byte[1024];
		            int bufferLength = 0;
		 
		            while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
		                fileOutput.write(buffer, 0, bufferLength);
		                downloadedSize += bufferLength;
		                // update the progressbar //
		                runOnUiThread(new Runnable() {
		                    public void run() {
		                        progress.setProgress(downloadedSize);
		                        float per = ((float)downloadedSize/totalSize) * 100;
		                       
		                       // cur_val.setText("Downloaded " + downloadedSize/1024*1024 + "MB / " + totalSize + "MB (" + ((int)per /1024*1024)/100 + "%)" );
		                        cur_val.setText((int)per/100000 + "%");
		                    }
		                });
		            }
		          //close the output stream when complete //
		            fileOutput.close();

		            outputStream.close();

			        if (success) {
			        	
			            System.out.println("File #1 has been downloaded successfully.");
			            Intent intent = new Intent(Intent.ACTION_VIEW);
			            intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/" + "ePettyCase_HYD.apk")), "application/vnd.android.package-archive");
			            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			            startActivity(intent);
			          
			        }
	        	}
	            
		    } catch (SocketException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    } catch (FileNotFoundException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }
		        return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			//removeDialog(PROGRESS_DIALOG);
		}
		
		 @Override
         protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
         }

	}

private void showProgress(String server) {
	// TODO Auto-generated method stub
	dialog = new Dialog(IP_SettingsActivity.this);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setContentView(R.layout.myprogressdialog);
    dialog.setTitle("Download Progress");
    dialog.setCancelable(false);
    

    TextView text = (TextView) dialog.findViewById(R.id.tv1);
    text.setText("Downloading file ... ");
    cur_val = (TextView) dialog.findViewById(R.id.cur_pg_tv);
    cur_val.setText("It may Take Few Minutes.....");
    dialog.show();
     
    progress = (ProgressBar)dialog.findViewById(R.id.progress_bar);
    progress.setProgress(0);//initially progress is 0  
    progress.setMax(100);
    progress.setIndeterminate(true);
    progress.setProgressDrawable(getResources().getDrawable(R.drawable.green_progress)); 
}

@SuppressLint("NewApi")
@Override
protected Dialog onCreateDialog(int id) {
	// TODO Auto-generated method stub
	switch (id) {
	case PROGRESS_DIALOG:
		ProgressDialog pd = ProgressDialog.show(this, "", "",	true);
		pd.setContentView(R.layout.custom_progress_dialog);
		pd.setCancelable(false); 
		
		
		return pd;
		
	case ALERT_USER:
		TextView title2 = new TextView(this);
		title2.setText("ALERT");
		title2.setBackgroundColor(Color.RED);
		title2.setGravity(Gravity.CENTER);
		title2.setTextColor(Color.WHITE);
		title2.setTextSize(24);
		title2.setTypeface(title2.getTypeface(), Typeface.BOLD);
		title2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hyd_city_small, 0, R.drawable.hyd_city_small, 0);
		title2.setPadding(20, 0, 20, 0);
		title2.setHeight(70);
		
		String network_message = "Please Check Your Network Connection Properly.This Process Takes Some Time To Download Application" ;
		
		AlertDialog.Builder builder = new AlertDialog.Builder(IP_SettingsActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
		builder.setCustomTitle(title2);
		//builder.setIcon(R.drawable.alert);
		builder.setMessage(network_message);
		builder.setCancelable(false);
		builder.setPositiveButton( "Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						removeDialog(ALERT_USER);
					}
				});

		builder.setNegativeButton("Ok",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						if (isOnline()) {
							new Async_UpdateApk().execute();

						} else {
							Toast toast = Toast.makeText(getApplicationContext(), "No Proper Internet Connection", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.CENTER, 0, 0);
							View toastView = toast.getView();
					    	toastView.setBackgroundResource(R.drawable.toast_background);
						    toast.show();
						}
					}
				});
		
	      AlertDialog alert_Dialog = builder.create();
	      alert_Dialog.show();
	      
	      alert_Dialog.getWindow().getAttributes();

	        TextView text_View = (TextView) alert_Dialog.findViewById(android.R.id.message);
	        text_View.setTextSize(28);
	        text_View.setTypeface(title2.getTypeface(), Typeface.BOLD);
	        text_View.setGravity(Gravity.CENTER);
	        
	        Button btn1 = alert_Dialog.getButton(DialogInterface.BUTTON_POSITIVE);
	        btn1.setTextSize(22);
	        btn1.setTextColor(Color.WHITE);
	        btn1.setTypeface(btn1.getTypeface(), Typeface.BOLD);
	        btn1.setBackgroundColor(Color.RED);
	        
	        Button btn3 = alert_Dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
	        btn3.setTextSize(22);
	        btn3.setTextColor(Color.WHITE);
	        btn3.setTypeface(btn3.getTypeface(), Typeface.BOLD);
	        btn3.setBackgroundColor(Color.RED); 
		
		
		return alert_Dialog;	
		
	default:
		break;
	}
	return super.onCreateDialog(id);
}

public Boolean isOnline() {
	ConnectivityManager conManager = (ConnectivityManager) this
			.getSystemService(Context.CONNECTIVITY_SERVICE);
	NetworkInfo nwInfo = conManager.getActiveNetworkInfo();
	return nwInfo != null;
}

	@Override
	public void onBackPressed()
	{
	}
	
}