package com.esys.leoparddemo;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.evolute.bluetooth.BluetoothComm;
import com.evolute.textimage.TextGenerator;
import com.evolute.textimage.TextGenerator.Justify;
import com.example.button.R;
import com.leopard.api.Printer;

public class Act_Printer extends Activity implements OnClickListener {
	
	private ImageView img_Galery;
	private String sPicturePath="";
	Context context = this;
	private static byte bFontstyle;
	private static byte bBarcodestyle;
	private static int bacodepostion = 1;
	private Button btn_TestPrint, btn_LogoPrint, btn_CustomText, btn_Bitmap,
	btn_BarCode, btn_PaperFeed, btn_Diagnostics, btn_Lineprint,btn_unicode;
	public static Printer ptr;
	InputStream inputStream = null;
	OutputStream outputStream = null;
	private int iRetVal;
	private EditText edt_Text, edt_BarCode, edt_AddLine;
	private static ProgressDialog dlgPg;
	private String sBarCode, sAddData;
	private byte bAddLineSyle;
	private Dialog dlgBarCode;
	TextView tv_selectLang;
	Spinner sp_SelectLanguage,sp_SelectPosition;
	public static String str;
	public static Justify  justfypostion;
	private String[] sEnterTextFont = { 
			"FONT LARGE NORMAL", "FONT LARGE BOLD",
			"FONT SMALL NORMAL", "FONT SMALL BOLD", "FONT ULLARGE NORMAL",
			"FONT ULLARGE BOLD", "FONT ULSMALL NORMAL", "FONT ULSMALL BOLD",
			"FONT 180LARGE NORMAL", "FONT 180LARGE BOLD",
			"FONT 180SMALL NORMAL", "FONT 180 SMALLBOLD",
			"FONT 180ULLARGE NORMAL", "FONT 180ULLARGE BOLD",
			"FONT 180ULSMALL NORMAL", "FONT 180ULSMALL BOLD" 
	};

	private String[] sBarCodeStyle = {
			"BARCODE 2OF5 COMPRESSED","BARCODE 2OF5 UNCOMPRESSED", 
			"BARCODE 3OF9 COMPRESSED","BARCODE 3OF9 UNCOMPRESSED", 
			"EAN13/UPC-A" 
	};
	/*   List of Return codes for the respective response */
	public static final int DEVICE_NOTCONNECTED = -100;
	public static int RESULT_LOAD_IMAGE = 10;
	private int iWidth ;
	private final static int MESSAGE_BOX = 1;
	
	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_printer);

		Display display = getWindowManager().getDefaultDisplay(); 
		iWidth = display.getWidth();

		// initialize the buttons
		btn_TestPrint = (Button) findViewById(R.id.btn_TestPrint);
		btn_TestPrint.setOnClickListener(this);

		btn_LogoPrint = (Button) findViewById(R.id.btn_LogoPrint);
		btn_LogoPrint.setOnClickListener(this);

		btn_CustomText = (Button) findViewById(R.id.btn_CustomText);
		btn_CustomText.setOnClickListener(this);

		btn_Bitmap = (Button) findViewById(R.id.btn_Bitmap);
		btn_Bitmap.setOnClickListener(this);

		btn_BarCode = (Button) findViewById(R.id.btn_BarCode);
		btn_BarCode.setOnClickListener(this);

		btn_PaperFeed = (Button) findViewById(R.id.btn_PaperFeed);
		btn_PaperFeed.setOnClickListener(this);

		btn_Diagnostics = (Button) findViewById(R.id.btn_Diagnostics);
		btn_Diagnostics.setOnClickListener(this);
		
		btn_unicode = (Button)findViewById(R.id.btn_unicode);
		btn_unicode.setOnClickListener(this);

		btn_Lineprint = (Button) findViewById(R.id.btn_Lineprint);
		btn_Lineprint.setOnClickListener(this);

		try {

			OutputStream outSt = BluetoothComm.mosOut;
			InputStream inSt = BluetoothComm.misIn;
			ptr = new Printer(Act_Main.setupInstance, outSt, inSt);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* Handler to display UI response messages   */
	@SuppressLint("HandlerLeak")
	Handler hander = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MESSAGE_BOX:
				String str = (String) msg.obj;
				showDialog(str);
				break;
			default:
				break;
			}
		};
	};

	/*  To show response messages  */
	public void showDialog(String str) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle("Leopard Demo Application");
		alertDialogBuilder.setMessage(str).setCancelable(false)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
			}
		});
		/* create alert dialog*/
		AlertDialog alertDialog = alertDialogBuilder.create();
		/* show alert dialog*/
		alertDialog.show();
	}
	
	//Button Events
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_TestPrint:
			/* ITestPrint  undergoes AsynTask operation*/
			TestPrintTask testprint = new TestPrintTask();
			testprint.execute(0);
			break;
		case R.id.btn_LogoPrint:
			/* ILogPrint  undergoes AsynTask operation*/
			LogPrintTask logprint = new LogPrintTask();
			logprint.execute(0);
			break;
		case R.id.btn_CustomText:
			EnterText();
			break;
		case R.id.btn_Bitmap:
			ShowImagDialog();
			break;
		case R.id.btn_BarCode:
			BarCodeDialog();
			break;
		case R.id.btn_PaperFeed:
			/* PaperFeed  undergoes AsynTask operation*/
			PaperFeedTask paperFeed = new PaperFeedTask();
			paperFeed.execute(0);
			break;
		case R.id.btn_Diagnostics:
			/* DiagnousPrint  undergoes AsynTask operation*/
			DiagnousTask diagonous = new DiagnousTask();
			diagonous.execute(0);
			break;
		case R.id.btn_Lineprint:
			AddLinebox();
			break;
		case R.id.btn_unicode:
			Unicode();
			break;
		default:
			break;
		}
	}

	public void AddLinebox() {
		final Dialog addline_dailog = new Dialog(context);
		addline_dailog.setTitle("Line Printing");
		addline_dailog.setContentView(R.layout.dlg_addline);
		edt_AddLine = (EditText) addline_dailog.findViewById(R.id.addline_edt);
		Spinner sp_AddLine = (Spinner) addline_dailog.findViewById(R.id.addline_sp);
		ArrayAdapter<String> arryFontStyle = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, sEnterTextFont);
		arryFontStyle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_AddLine.setAdapter(arryFontStyle);
		sp_AddLine.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					bAddLineSyle = (byte) 0x01;
					break;
				case 1:
					bAddLineSyle = (byte) 0x02;
					break;
				case 2:
					bAddLineSyle = (byte) 0x03;
					break;
				case 3:
					bAddLineSyle = (byte) 0x04;
					break;
				case 4:
					bAddLineSyle = (byte) 0x05;
					break;
				case 5:
					bAddLineSyle = (byte) 0x06;
					break;
				case 6:
					bAddLineSyle = (byte) 0x07;
					break;
				case 7:
					bAddLineSyle = (byte) 0x08;
					break;
				case 8:
					bAddLineSyle = (byte) 0x09;
					break;
				case 9:
					bAddLineSyle = (byte) 0x0A;
					break;
				case 10:
					bAddLineSyle = (byte) 0x0B;
					break;
				case 11:
					bAddLineSyle = (byte) 0x0C;
					break;
				case 12:
					bAddLineSyle = (byte) 0x0D;
					break;
				case 13:
					bAddLineSyle = (byte) 0x0E;
					break;
				case 14:
					bAddLineSyle = (byte) 0x0F;
					break;
				case 15:
					bAddLineSyle = (byte) 0x10;
					break;
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		Button btn_AddLine = (Button) addline_dailog.findViewById(R.id.btn_Addline);
		btn_AddLine.setWidth(iWidth);
		btn_AddLine.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String addstr = edt_AddLine.getText().toString();
				if (addstr.length() == 0) {
					hander.obtainMessage(MESSAGE_BOX,"Enter Single character").sendToTarget();
				} else if (addstr.length() > 0) {
					addline_dailog.dismiss();
					AddLineAsyc addline = new AddLineAsyc();
					addline.execute(0);
				}
			}
		});
		addline_dailog.show();
	}

	/*   This method shows the ITestPrint  AsynTask operation */
	public class TestPrintTask extends AsyncTask<Integer, Integer, Integer> {
		/* displays the progress dialog until background task is completed*/
		@Override
		protected void onPreExecute() {
			progressDialog(context, "Please Wait ...");
			super.onPreExecute();
		}
		/* Task of ITestPrint performing in the background*/
		@Override
		protected Integer doInBackground(Integer... params) {
			try {
				ptr.iFlushBuf();
				iRetVal =   ptr.iTestPrint();
			} catch (NullPointerException e) {
				iRetVal = DEVICE_NOTCONNECTED;
				return iRetVal;
			}
			return iRetVal;
		}

		/* This displays the status messages of ITestPrint in the dialog box */
		@Override
		protected void onPostExecute(Integer result) {
			dlgPg.dismiss();
			if (iRetVal == DEVICE_NOTCONNECTED) {
				hander.obtainMessage(DEVICE_NOTCONNECTED,"Device not connected").sendToTarget();
			} else if (iRetVal == Printer.PR_SUCCESS) {
				hander.obtainMessage(MESSAGE_BOX, "Test print success").sendToTarget();
			} else if (iRetVal == Printer.PR_PLATEN_OPEN) {
				hander.obtainMessage(MESSAGE_BOX,"Printer platen is open").sendToTarget();
			} else if (iRetVal == Printer.PR_PAPER_OUT) {
				hander.obtainMessage(MESSAGE_BOX,"Printer paper is out").sendToTarget();
			} else if (iRetVal == Printer.PR_IMPROPER_VOLTAGE) {
				hander.obtainMessage(MESSAGE_BOX,"Printer improper voltage").sendToTarget();
			} else if (iRetVal == Printer.PR_FAIL) {
				hander.obtainMessage(MESSAGE_BOX,"Printer failed").sendToTarget();
			} else if (iRetVal == Printer.PR_PARAM_ERROR) {
				hander.obtainMessage(MESSAGE_BOX,"Printer param error").sendToTarget();
			} else if (iRetVal == Printer.PR_NO_RESPONSE) {
				hander.obtainMessage(MESSAGE_BOX,"No response from Leopard device").sendToTarget();
			}else if (iRetVal== Printer.PR_DEMO_VERSION) {
				hander.obtainMessage(MESSAGE_BOX,"Library is in demo version").sendToTarget();
			}else if (iRetVal==Printer.PR_INVALID_DEVICE_ID) {
				hander.obtainMessage(MESSAGE_BOX,"Connected  device is not license authenticated.").sendToTarget();
			}else if (iRetVal==Printer.PR_ILLEGAL_LIBRARY) {
				hander.obtainMessage(MESSAGE_BOX,"Library not valid").sendToTarget();
			}
			super.onPostExecute(result);
		}
	}

	/*   This method shows the ILogPrint  AsynTask operation */
	public class LogPrintTask extends AsyncTask<Integer, Integer, Integer> {
		/* displays the progress dialog until background task is completed*/
		@Override
		protected void onPreExecute() {
			progressDialog(context, "Please Wait ...");
			super.onPreExecute();
		}
		/* Task of ILogPrint performing in the background*/
		@Override
		protected Integer doInBackground(Integer... params) {
			try {
				ptr.iFlushBuf();
				iRetVal =   ptr.iLogoPrint(Logos.EVOLUTER);
				if (iRetVal == Printer.PR_SUCCESS) {
					String empty = "\n";
					ptr.iPrinterAddData((byte) 0x01, empty);
					ptr.iStartPrinting(1);
				}
			} catch (NullPointerException e) {
				iRetVal = DEVICE_NOTCONNECTED;
				return iRetVal;
			}
			return iRetVal;
		}
		/* This displays the status messages of ILogPrint in the dialog box */
		@Override
		protected void onPostExecute(Integer result) {
			dlgPg.dismiss();
			if (iRetVal == DEVICE_NOTCONNECTED) {
				hander.obtainMessage(DEVICE_NOTCONNECTED,"Device not connected").sendToTarget();
			} else if (iRetVal == Printer.PR_SUCCESS) {
				hander.obtainMessage(MESSAGE_BOX, "Print Success").sendToTarget();
			} else if (iRetVal == Printer.PR_PLATEN_OPEN) {
				hander.obtainMessage(MESSAGE_BOX,"Printer platen is open").sendToTarget();
			} else if (iRetVal == Printer.PR_PAPER_OUT) {
				hander.obtainMessage(MESSAGE_BOX,"Printer paper is out").sendToTarget();
			} else if (iRetVal == Printer.PR_IMPROPER_VOLTAGE) {
				hander.obtainMessage(MESSAGE_BOX,"Printer improper voltage").sendToTarget();
			} else if (iRetVal == Printer.PR_FAIL) {
				hander.obtainMessage(MESSAGE_BOX, "Printer failed").sendToTarget();
			} else if (iRetVal == Printer.PR_PARAM_ERROR) {
				hander.obtainMessage(MESSAGE_BOX,"Printer param error").sendToTarget();
			}else if (iRetVal == Printer.PR_NO_RESPONSE) {
				hander.obtainMessage(MESSAGE_BOX,"No response from Leopard device").sendToTarget();
			}else if (iRetVal== Printer.PR_DEMO_VERSION) {
				hander.obtainMessage(MESSAGE_BOX,"Library is in demo version").sendToTarget();
			}else if (iRetVal==Printer.PR_INVALID_DEVICE_ID) {
				hander.obtainMessage(MESSAGE_BOX,"Connected  device is not license authenticated.").sendToTarget();
			}else if (iRetVal==Printer.PR_ILLEGAL_LIBRARY) {
				hander.obtainMessage(MESSAGE_BOX,"Library not valid").sendToTarget();
			}
			super.onPostExecute(result);
		}
	}

	public void BarCodeDialog() {
	    dlgBarCode = new Dialog(context);
		dlgBarCode.setTitle("Enter Data to Print Barcode");
		dlgBarCode.setContentView(R.layout.dlg_barcode);
		edt_BarCode = (EditText) dlgBarCode.findViewById(R.id.barcode_edt);
		Spinner sp_BarCode = (Spinner) dlgBarCode.findViewById(R.id.barcode_sp);
		ArrayAdapter<String> arryBarCode = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, sBarCodeStyle);
		arryBarCode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_BarCode.setAdapter(arryBarCode);
		sp_BarCode.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View v,
					int position, long arg3) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					bacodepostion = 1;
					bBarcodestyle = (byte) (0X01);
					break;
				case 1:
					bacodepostion = 2;
					bBarcodestyle = (byte) (0X02);
					break;
				case 2:
					bacodepostion = 3;
					bBarcodestyle = (byte) (0X03);
					break;
				case 3:
					bacodepostion = 4;
					bBarcodestyle = (byte) (0X04);
					break;
				case 4:
					bacodepostion = 5;
					bBarcodestyle = (byte) (0X05);
					break;
				}
				InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				mgr.hideSoftInputFromWindow(v.getWindowToken(), 0);
				edt_BarCode.setText("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

		edt_BarCode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {// TODO 
				switch (bacodepostion) {
				case 1:
					edt_BarCode.setInputType(InputType.TYPE_CLASS_NUMBER);
					edt_BarCode.setFilters(new InputFilter[] { new InputFilter.LengthFilter(25)});
					break;
				case 2:
					edt_BarCode.setInputType(InputType.TYPE_CLASS_NUMBER);
					edt_BarCode.setFilters(new InputFilter[] { new InputFilter.LengthFilter(12)});
					break;
				case 3:
					int itype = InputType.TYPE_CLASS_TEXT ;
					edt_BarCode.setInputType(itype);
					edt_BarCode.setFilters(new InputFilter[] { new InputFilter.LengthFilter(25), 
							new InputFilter.AllCaps() });
					break;
				case 4:
					edt_BarCode.setInputType(InputType.TYPE_CLASS_TEXT);
					edt_BarCode.setFilters(new InputFilter[] { new InputFilter.LengthFilter(12), 
							new InputFilter.AllCaps()});
					break;
				case 5:
					edt_BarCode.setInputType(InputType.TYPE_CLASS_NUMBER);
					edt_BarCode.setFilters(new InputFilter[] { new InputFilter.LengthFilter(12)});
					break;
				default:
					break;
				}

			}
		});

		Button btn_BarCode = (Button) dlgBarCode.findViewById(R.id.btn_BarCode);
		btn_BarCode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				sBarCode = edt_BarCode.getText().toString();
				char[] ccc = sBarCode.toCharArray();
				if (sBarCode.length() == 0) {
					hander.obtainMessage(MESSAGE_BOX, "Enter text")
					.sendToTarget();
				} else if (sBarCode.length() > 0) {
					switch (bacodepostion) {
					case 3:
						BarCodeTask barcode = new BarCodeTask();
						barcode.execute(0);
						break;
					case 1:
						int i;
						for (i = 0; i < sBarCode.length(); i++) {
							if (!(ccc[i] >= '0' && ccc[i] <= '9')) {
								break;
							}
						}
						if (i != sBarCode.length()) {
							hander.obtainMessage(MESSAGE_BOX,
									"Please enter numeric characters")
									.sendToTarget();
						} else {
							BarCodeTask barcode1 = new BarCodeTask();
							barcode1.execute(0);
						}
						break;
					case 2:
						sBarCode = edt_BarCode.getText().toString();
						if (sBarCode.length() > 12) {
							hander.obtainMessage(MESSAGE_BOX,
									"Enter data less than 13 characters")
									.sendToTarget();
						} else {
							for (i = 0; i < sBarCode.length(); i++) {
								if (!(ccc[i] >= '0' && ccc[i] <= '9')) {
									break;
								}
							}
							if (i != sBarCode.length()) {
								hander.obtainMessage(MESSAGE_BOX,
										"Please enter numeric characters")
										.sendToTarget();
							} else {
								BarCodeTask barcode1 = new BarCodeTask();
								barcode1.execute(0);
							}
						}
						break;
					case 4:
						sBarCode = edt_BarCode.getText().toString();
						if (sBarCode.length() > 12) {

							hander.obtainMessage(MESSAGE_BOX,
									"Enter data less than 13 characters")
									.sendToTarget();
						} else {
							BarCodeTask barcode1 = new BarCodeTask();
							barcode1.execute(0);
						}
						break;
					case 5:
						sBarCode = edt_BarCode.getText().toString();
						if (sBarCode.length() > 13) {
							hander.obtainMessage(MESSAGE_BOX,
									"Enter data less than 13").sendToTarget();
						} else {
							// int i;
							for (i = 0; i < sBarCode.length(); i++) {
								if (!(ccc[i] >= '0' && ccc[i] <= '9')) {
									break;
								}
							}
							if (i != sBarCode.length()) {
								hander.obtainMessage(MESSAGE_BOX,
										"Please enter numerics only")
										.sendToTarget();
							} else {
								BarCodeTask barcode1 = new BarCodeTask();
								barcode1.execute(0);
							}
						}
						break;
					}
				}
			}
		});
		dlgBarCode.show();
	}
	
	/*   This method shows the BarCodePrint  AsynTask operation */
	public class BarCodeTask extends AsyncTask<Integer, Integer, Integer> {
		/* displays the progress dialog until background task is completed*/
		@Override
		protected void onPreExecute() {
			progressDialog(context, "Please Wait...");
			super.onPreExecute();
		}
		/* Task of BarCodePrint performing in the background*/	
		@Override
		protected Integer doInBackground(Integer... params) {
			try {
				ptr.iFlushBuf();
				sBarCode = edt_BarCode.getText().toString();
				iRetVal = ptr.iPrintBarcode(bBarcodestyle, sBarCode);
				if (iRetVal==Printer.PR_SUCCESS) {
					SystemClock.sleep(100);
					String empty = " \n" + " \n" + " \n" + " \n";
					ptr.iPrinterAddData((byte) 0x01, empty);
					ptr.iStartPrinting(1);
				}
			} catch (NullPointerException e) {
				iRetVal = DEVICE_NOTCONNECTED;
				return iRetVal;
			}
			return iRetVal;
		}

		/* This displays the status messages of BarCodePrint in the dialog box */
		@Override
		protected void onPostExecute(Integer result) {
			dlgPg.dismiss();
			if (iRetVal == DEVICE_NOTCONNECTED) {
				hander.obtainMessage(DEVICE_NOTCONNECTED,"Device not connected").sendToTarget();
			} else if (iRetVal == Printer.PR_SUCCESS) {
				dlgBarCode.dismiss();
				hander.obtainMessage(MESSAGE_BOX, "Print Success").sendToTarget();
			} else if (iRetVal == Printer.PR_PLATEN_OPEN) {
				hander.obtainMessage(MESSAGE_BOX,"Printer platen is open").sendToTarget();
			} else if (iRetVal == Printer.PR_PAPER_OUT) {
				hander.obtainMessage(MESSAGE_BOX,"Printer paper is out").sendToTarget();
			} else if (iRetVal == Printer.PR_IMPROPER_VOLTAGE) {
				hander.obtainMessage(MESSAGE_BOX,"Printer improper voltage").sendToTarget();
			} else if (iRetVal == Printer.PR_FAIL) {
				hander.obtainMessage(MESSAGE_BOX, "Printer failed").sendToTarget();
			} else if (iRetVal == Printer.PR_PARAM_ERROR) {
				hander.obtainMessage(MESSAGE_BOX,"Printer param error").sendToTarget();
			} else if (iRetVal == Printer.PR_NO_RESPONSE) {
				hander.obtainMessage(MESSAGE_BOX,"No response from Leopard device").sendToTarget();
			}else if (iRetVal== Printer.PR_DEMO_VERSION) {
				hander.obtainMessage(MESSAGE_BOX,"Library is in demo version").sendToTarget();
			}else if (iRetVal==Printer.PR_INVALID_DEVICE_ID) {
				hander.obtainMessage(MESSAGE_BOX,"Connected  device is not license authenticated.").sendToTarget();
			}else if (iRetVal==Printer.PR_ILLEGAL_LIBRARY) {
				hander.obtainMessage(MESSAGE_BOX,"Library not valid").sendToTarget();
			}
			super.onPostExecute(result);
		}
	}

	public void EnterText() {	//TODO
		final Dialog dlgEnterText = new Dialog(context);
		dlgEnterText.setContentView(R.layout.dlg_entertext);
		dlgEnterText.setTitle("Enter Text to Print");
		dlgEnterText.setCancelable(true);
		edt_Text = (EditText) dlgEnterText.findViewById(R.id.editText1);
		edt_Text.setText("Evolute Systems");
		Spinner sp_FontStyle = (Spinner) dlgEnterText.findViewById(R.id.font_sty);
		ArrayAdapter<String> arrFontStyle = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, sEnterTextFont);
		arrFontStyle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_FontStyle.setAdapter(arrFontStyle);
		sp_FontStyle.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				switch (position) {
				case  0: bFontstyle = Printer.PR_FONTLARGENORMAL;		break; //0x01;
				case  1: bFontstyle = Printer.PR_FONTLARGEBOLD;			break; //0x02;
				case  2: bFontstyle = Printer.PR_FONTSMALLNORMAL;		break; //0x03;
				case  3: bFontstyle = Printer.PR_FONTSMALLBOLD;			break; //0x04;
				case  4: bFontstyle = Printer.PR_FONTULLARGENORMAL;		break; //0x05;
				case  5: bFontstyle = Printer.PR_FONTULLARGEBOLD;		break; //0x06;
				case  6: bFontstyle = Printer.PR_FONTULSMALLNORMAL;		break; //0x07;
				case  7: bFontstyle = Printer.PR_FONTULSMALLBOLD;		break; //0x08;
				case  8: bFontstyle = Printer.PR_FONT180LARGENORMAL;	break; //0x09;
				case  9: bFontstyle = Printer.PR_FONT180LARGEBOLD;		break; //0x0A;
				case 10: bFontstyle = Printer.PR_FONT180SMALLNORMAL;	break; //0x0B;
				case 11: bFontstyle = Printer.PR_FONT180SMALLBOLD;		break; //0x0C;
				case 12: bFontstyle = Printer.PR_FONT180ULLARGENORMAL;	break; //0x0D;
				case 13: bFontstyle = Printer.PR_FONT180ULLARGEBOLD;	break; //0x0E;
				case 14: bFontstyle = Printer.PR_FONT180ULSMALLNORMAL;	break; //0x0F;
				case 15: bFontstyle = Printer.PR_FONT180ULSMALLBOLD;	break; //0x10;
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		Button dlgOk = (Button) dlgEnterText.findViewById(R.id.ok);
		dlgOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sAddData = edt_Text.getText().toString();
				if (sAddData.length() == 0) {
					hander.obtainMessage(MESSAGE_BOX, "Enter Text").sendToTarget();
				} else if (sAddData.length() > 0) {
					dlgEnterText.dismiss();
					EnterTextTask asynctask = new EnterTextTask();
					asynctask.execute(0);
				}
			}
		});
		dlgEnterText.show();
	}

	/* This performs Progress dialog box to show the progress of operation */
	public static void progressDialog(Context context, String msg) {
		dlgPg = new ProgressDialog(context);
		dlgPg.setMessage(msg);
		dlgPg.setIndeterminate(true);
		dlgPg.setCancelable(false);
		dlgPg.show();
	}

	/*   This method shows the EnterTextAsyc  AsynTask operation */
	public class EnterTextTask extends AsyncTask<Integer, Integer, Integer> {
		/* displays the progress dialog until background task is completed*/
		@Override
		protected void onPreExecute() {
			progressDialog(context, "Please Wait...");
			super.onPreExecute();
		}
		/* Task of EnterTextAsyc performing in the background*/	
		@Override
		protected Integer doInBackground(Integer... params) {
			try {
				sAddData = edt_Text.getText().toString();
				ptr.iFlushBuf();
				String empty = sAddData ;//+ "\n" + "\n" + "\n" + "\n" + "\n"+ "\n";
				ptr.iPrinterAddData(bFontstyle, empty);
				ptr.iPrinterAddData(Printer.PR_FONTLARGENORMAL, " \n \n \n \n \n \n");
				ptr.iPrinterAddData(Printer.PR_FONT180LARGENORMAL, " \n \n \n \n \n \n");
				iRetVal = ptr.iStartPrinting(1);
			} catch (NullPointerException e) {
				iRetVal = DEVICE_NOTCONNECTED;
				return iRetVal;
			}
			return iRetVal;
		}
		/* This displays the status messages of EnterTextAsyc in the dialog box */
		@Override
		protected void onPostExecute(Integer result) {
			dlgPg.dismiss();
			if (iRetVal == DEVICE_NOTCONNECTED) {
				hander.obtainMessage(DEVICE_NOTCONNECTED,"Device not connected").sendToTarget();
			} else if (iRetVal == Printer.PR_SUCCESS) {
				hander.obtainMessage(MESSAGE_BOX, "Print Success").sendToTarget();
			} else if (iRetVal == Printer.PR_PLATEN_OPEN) {
				hander.obtainMessage(MESSAGE_BOX,"Printer platen is open").sendToTarget();
			} else if (iRetVal == Printer.PR_PAPER_OUT) {
				hander.obtainMessage(MESSAGE_BOX,"Printer paper is out").sendToTarget();
			} else if (iRetVal == Printer.PR_IMPROPER_VOLTAGE) {
				hander.obtainMessage(MESSAGE_BOX,"Printer improper voltage").sendToTarget();
			} else if (iRetVal == Printer.PR_FAIL) {
				hander.obtainMessage(MESSAGE_BOX, "Printer failed").sendToTarget();
			} else if (iRetVal == Printer.PR_PARAM_ERROR) {
				hander.obtainMessage(MESSAGE_BOX,"Printer param error").sendToTarget();
			} else if (iRetVal == Printer.PR_NO_RESPONSE) {
				hander.obtainMessage(MESSAGE_BOX,"No response from Leopard device").sendToTarget();
			} else if (iRetVal== Printer.PR_DEMO_VERSION) {
				hander.obtainMessage(MESSAGE_BOX,"Library is in demo version").sendToTarget();
			} else if (iRetVal==Printer.PR_INVALID_DEVICE_ID) {
				hander.obtainMessage(MESSAGE_BOX,"Connected  device is not license authenticated.").sendToTarget();
			} else if (iRetVal==Printer.PR_ILLEGAL_LIBRARY) {
				hander.obtainMessage(MESSAGE_BOX,"Library not valid").sendToTarget();
			}
			super.onPostExecute(result);
		}
	}

	/*   This method shows the PrintBitmap  AsynTask operation */
	public class BitmapTask extends AsyncTask<Integer, Integer, Integer> {
		/* displays the progress dialog until background task is completed*/
		@Override
		protected void onPreExecute() {
			progressDialog(context, "Please Wait ...");
			super.onPreExecute();
		}
		/* Task of PrintBitmap performing in the background*/		
		@Override
		protected Integer doInBackground(Integer... params) {
			try {
				if (sPicturePath==""){
					iRetVal = -555;
					return iRetVal;
				}
				ptr.iFlushBuf();
				iRetVal =   ptr.iBmpPrint(sPicturePath); 
				if (iRetVal == Printer.PR_SUCCESS) {
					String empty = "\n";
					ptr.iPrinterAddData((byte) 0x01, empty);
					ptr.iStartPrinting(1);
				}
			} catch (NullPointerException e) {
				iRetVal = DEVICE_NOTCONNECTED;
				return iRetVal;
			}
			return iRetVal;
		}
		/* This displays the status messages of PrintBitmap in the dialog box */
		@Override
		protected void onPostExecute(Integer result) {
			dlgPg.dismiss();
			if (iRetVal == DEVICE_NOTCONNECTED) {
				hander.obtainMessage(DEVICE_NOTCONNECTED,"Device not connected").sendToTarget();
			} else if (iRetVal == -555) {
				hander.obtainMessage(MESSAGE_BOX, "No Image is Selected").sendToTarget();
			} else if (iRetVal == Printer.PR_SUCCESS) {
				hander.obtainMessage(MESSAGE_BOX, "Print Success").sendToTarget();
			} else if (iRetVal == Printer.PR_PLATEN_OPEN) {
				hander.obtainMessage(MESSAGE_BOX,"Printer platen is open").sendToTarget();
			} else if (iRetVal == Printer.PR_PAPER_OUT) {
				hander.obtainMessage(MESSAGE_BOX,"Printer paper is out").sendToTarget();
			} else if (iRetVal == Printer.PR_IMPROPER_VOLTAGE) {
				hander.obtainMessage(MESSAGE_BOX,"Printer improper voltage").sendToTarget();
			} else if (iRetVal == Printer.PR_FAIL) {
				hander.obtainMessage(MESSAGE_BOX, "Printer failed").sendToTarget();
			} else if (iRetVal == Printer.PR_PARAM_ERROR) {
				hander.obtainMessage(MESSAGE_BOX,"Printer param error").sendToTarget();
			} else if (iRetVal == Printer.PR_NO_RESPONSE) {
				hander.obtainMessage(MESSAGE_BOX,"No response from Leopard device").sendToTarget();
			} else if (iRetVal == Printer.PR_DEMO_VERSION) {
				hander.obtainMessage(MESSAGE_BOX,"Library is in demo version").sendToTarget();
			} else if (iRetVal == Printer.PR_INVALID_DEVICE_ID) {
				hander.obtainMessage(MESSAGE_BOX,"Connected  device is not license authenticated.").sendToTarget();
			} else if (iRetVal == Printer.PR_ILLEGAL_LIBRARY) {
				hander.obtainMessage(MESSAGE_BOX,"Library not valid").sendToTarget();
			} else if (iRetVal == Printer.PR_BMP_FILE_NOT_FOUND) {
				hander.obtainMessage(MESSAGE_BOX,"File not found or File Size is large").sendToTarget();
			}
			super.onPostExecute(result);
		}
	}

	/*   This method shows the AddLine  AsynTask operation */
	public class AddLineAsyc extends AsyncTask<Integer, Integer, Integer> {
		/* displays the progress dialog until background task is completed*/
		@Override
		protected void onPreExecute() {
			progressDialog(context, "Please Wait ...");
			super.onPreExecute();
		}
		/* Task of AddLine  performing in the background*/	
		@Override
		protected Integer doInBackground(Integer... params) {
			try {
				ptr.iFlushBuf();
				String addlinestr = edt_AddLine.getText().toString();
				Character ch = addlinestr.charAt(0);
				ptr.iPrinterAddLine(bAddLineSyle, ch);
				String empty = "\n" + "\n" + "\n" + "\n";
				ptr.iPrinterAddData((byte) 0x01, empty);
				iRetVal =   ptr.iStartPrinting(1);
			} catch (NullPointerException e) {
				iRetVal = DEVICE_NOTCONNECTED;
				return iRetVal;
			}
			return iRetVal;
		}

		/* This displays the status messages of AddLine in the dialog box */
		@Override
		protected void onPostExecute(Integer result) {
			dlgPg.dismiss();
			if (iRetVal == DEVICE_NOTCONNECTED) {
				hander.obtainMessage(DEVICE_NOTCONNECTED,"Device not connected").sendToTarget();
			} else if (iRetVal == Printer.PR_SUCCESS) {
				hander.obtainMessage(MESSAGE_BOX, "Print Success").sendToTarget();
			} else if (iRetVal == Printer.PR_PLATEN_OPEN) {
				hander.obtainMessage(MESSAGE_BOX,"Printer platen is open").sendToTarget();
			} else if (iRetVal == Printer.PR_PAPER_OUT) {
				hander.obtainMessage(MESSAGE_BOX,"Printer paper is out").sendToTarget();
			} else if (iRetVal == Printer.PR_IMPROPER_VOLTAGE) {
				hander.obtainMessage(MESSAGE_BOX,"Printer improper voltage").sendToTarget();
			} else if (iRetVal == Printer.PR_FAIL) {
				hander.obtainMessage(MESSAGE_BOX, "Printer failed").sendToTarget();
			} else if (iRetVal == Printer.PR_PARAM_ERROR) {
				hander.obtainMessage(MESSAGE_BOX,"Printer param error").sendToTarget();
			}else if (iRetVal == Printer.PR_NO_RESPONSE) {
				hander.obtainMessage(MESSAGE_BOX,"No response from Leopard device").sendToTarget();
			}else if (iRetVal== Printer.PR_DEMO_VERSION) {
				hander.obtainMessage(MESSAGE_BOX,"Library is in demo version").sendToTarget();
			}else if (iRetVal==Printer.PR_INVALID_DEVICE_ID) {
				hander.obtainMessage(MESSAGE_BOX,"Connected  device is not license authenticated.").sendToTarget();
			}else if (iRetVal==Printer.PR_ILLEGAL_LIBRARY) {
				hander.obtainMessage(MESSAGE_BOX,"Library not valid").sendToTarget();
			}
			super.onPostExecute(result);
		}
	}

	/*   This method shows the PaperFeed  AsynTask operation */
	public class PaperFeedTask extends AsyncTask<Integer, Integer, Integer> {
		/* displays the progress dialog until background task is completed*/
		@Override
		protected void onPreExecute() {
			progressDialog(context, "Please Wait ...");
			super.onPreExecute();
		}
		/* Task of PaperFeed  performing in the background*/	
		@Override
		protected Integer doInBackground(Integer... params) {
			try {
				iRetVal =   ptr.iPaperFeed();
			} catch (NullPointerException e) {
				iRetVal = DEVICE_NOTCONNECTED;
				return iRetVal;
			}
			return iRetVal;
		}
		/* This displays the status messages of PaperFeed in the dialog box */
		@Override
		protected void onPostExecute(Integer result) {
			dlgPg.dismiss();
			if (iRetVal == DEVICE_NOTCONNECTED) {
				hander.obtainMessage(DEVICE_NOTCONNECTED,"Device not connected").sendToTarget();
			} else if (iRetVal == Printer.PR_SUCCESS) {
				hander.obtainMessage(MESSAGE_BOX, "Paperfeed is success").sendToTarget();
			} else if (iRetVal == Printer.PR_PLATEN_OPEN) {
				hander.obtainMessage(MESSAGE_BOX,"Printer platen is open").sendToTarget();
			} else if (iRetVal == Printer.PR_PAPER_OUT) {
				hander.obtainMessage(MESSAGE_BOX,"Printer paper is out").sendToTarget();
			} else if (iRetVal == Printer.PR_IMPROPER_VOLTAGE) {
				hander.obtainMessage(MESSAGE_BOX,"Printer improper voltage").sendToTarget();
			} else if (iRetVal == Printer.PR_FAIL) {
				hander.obtainMessage(MESSAGE_BOX, "Printer failed").sendToTarget();
			} else if (iRetVal == Printer.PR_PARAM_ERROR) {
				hander.obtainMessage(MESSAGE_BOX,"Printer param error").sendToTarget();
			}else if (iRetVal == Printer.PR_NO_RESPONSE) {
				hander.obtainMessage(MESSAGE_BOX,"No response from Leopard device").sendToTarget();
			}else if (iRetVal== Printer.PR_DEMO_VERSION) {
				hander.obtainMessage(MESSAGE_BOX,"Library is in demo version").sendToTarget();
			}else if (iRetVal==Printer.PR_INVALID_DEVICE_ID) {
				hander.obtainMessage(MESSAGE_BOX,"Connected  device is not license authenticated.").sendToTarget();
			}else if (iRetVal==Printer.PR_ILLEGAL_LIBRARY) {
				hander.obtainMessage(MESSAGE_BOX,"Library not valid").sendToTarget();
			}
			super.onPostExecute(result);
		}
	}

	/*   This method shows the Diagnose AsynTask operation */
	public class DiagnousTask extends AsyncTask<Integer, Integer, Integer> {

		/* displays the progress dialog until background task is completed*/
		@Override
		protected void onPreExecute() {
			progressDialog(context, "Please Wait ...");
			super.onPreExecute();
		}

		/* Task of Diagnose performing in the background*/
		@Override
		protected Integer doInBackground(Integer... params) {
			try {
				iRetVal =   ptr.iPrinterDiagnostics();
			} catch (NullPointerException e) {
				iRetVal = DEVICE_NOTCONNECTED;
				return iRetVal;
			}
			return iRetVal;
		}
		/* This sends message to handler to display the status messages 
		 * of Diagnose in the dialog box */
		@Override
		protected void onPostExecute(Integer result) {
			dlgPg.dismiss();
			if (iRetVal == DEVICE_NOTCONNECTED) {
				hander.obtainMessage(DEVICE_NOTCONNECTED,"Device not connected").sendToTarget();
			} else if (iRetVal == Printer.PR_SUCCESS) {
				hander.obtainMessage(MESSAGE_BOX,"Printer is in good condition").sendToTarget();
			} else if (iRetVal == Printer.PR_PLATEN_OPEN) {
				hander.obtainMessage(MESSAGE_BOX,"Printer platen is open").sendToTarget();
			} else if (iRetVal == Printer.PR_PAPER_OUT) {
				hander.obtainMessage(MESSAGE_BOX,"Printer paper is out").sendToTarget();
			} else if (iRetVal == Printer.PR_IMPROPER_VOLTAGE) {
				hander.obtainMessage(MESSAGE_BOX,"Printer improper voltage").sendToTarget();
			} else if (iRetVal == Printer.PR_FAIL) {
				hander.obtainMessage(MESSAGE_BOX, "Printer failed").sendToTarget();
			} else if (iRetVal == Printer.PR_PARAM_ERROR) {
				hander.obtainMessage(MESSAGE_BOX,"Printer param error").sendToTarget();
			}else if (iRetVal == Printer.PR_NO_RESPONSE) {
				hander.obtainMessage(MESSAGE_BOX,"No response from Leopard device").sendToTarget();
			}else if (iRetVal== Printer.PR_DEMO_VERSION) {
				hander.obtainMessage(MESSAGE_BOX,"Library is in demo version").sendToTarget();
			}else if (iRetVal==Printer.PR_INVALID_DEVICE_ID) {
				hander.obtainMessage(MESSAGE_BOX,"Connected  device is not license authenticated.").sendToTarget();
			}else if (iRetVal==Printer.PR_ILLEGAL_LIBRARY) {
				hander.obtainMessage(MESSAGE_BOX,"Library not valid").sendToTarget();
			}
			super.onPostExecute(result);
		}
	}
	//Button btn_Print;
	Dialog dlgImg;
	//LinearLayout llImage;
	@SuppressWarnings("unused")
	public void ShowImagDialog()
	{
		sPicturePath="";
		dlgImg = new Dialog(context);
		dlgImg.setTitle("Select BMP File from storage");
		//dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dlgImg.setContentView(R.layout.dlg_bitmap);
		final Button btn_Print = (Button)dlgImg.findViewById(R.id.Print_but);
		img_Galery = (ImageView)dlgImg.findViewById(R.id.galery_img);
		//img_Galery.setImageResource(R.drawable.icon);
		LinearLayout llImage =(LinearLayout)dlgImg.findViewById(R.id.imaglay);
		final Button selectimage_but = (Button)dlgImg.findViewById(R.id.selectimage_but);
		selectimage_but.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectimage_but.setEnabled(true);
				Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, RESULT_LOAD_IMAGE);
				btn_Print.setEnabled(true);
				TextView selectpath_tv = (TextView)dlgImg.findViewById(R.id.selectpath_tv);
				selectpath_tv.setText(sPicturePath);
			}
		});
		btn_Print.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dlgImg.dismiss();
				BitmapTask bmp = new BitmapTask();
				bmp.execute(0);
			}
		});
		dlgImg.show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RESULT_LOAD_IMAGE){// && resultCode == RESULT_OK && null != data) {
			try {
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };
				Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				sPicturePath = cursor.getString(columnIndex);
				cursor.close();
				img_Galery.setImageBitmap(BitmapFactory.decodeFile(sPicturePath));
			} catch (Exception e) {
				e.printStackTrace();
				sPicturePath = "";
				Toast.makeText(Act_Printer.this, "No Image Selected", Toast.LENGTH_SHORT).show();
			}         
		}
	}
	
	public void Unicode()
	{
		final Dialog dlgUnicodeText = new Dialog(context);
		dlgUnicodeText.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dlgUnicodeText.setContentView(R.layout.act_unicode);
		dlgUnicodeText.setTitle("Enter Text to Print");
		dlgUnicodeText.setCancelable(true);
		TextView tvTitel = (TextView)dlgUnicodeText.findViewById(R.id.tv_unicodecode);
		tvTitel.setWidth(500);
		tv_selectLang = (TextView)dlgUnicodeText.findViewById(R.id.tv_selectLang);
		sp_SelectLanguage = (Spinner)dlgUnicodeText.findViewById(R.id.sp_SelectLanguage);
		List<String> lang = new ArrayList<String>();
		lang.add("Hindi");
		lang.add("Kannada");
		lang.add("Telugu");
		lang.add("Tamil");
		//lang.add("Urdu");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,lang);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_SelectLanguage.setAdapter(dataAdapter);
		sp_SelectLanguage.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				if(position==0){
					Log.e("Str value","<<<unicode1>>>>"+str);
					str = "à¤�à¤µà¥‹à¤²à¥�à¤¤à¥‡ à¤¸à¤¿à¤¸à¥�à¤Ÿà¤® à¤ªà¥�à¤°à¤¾à¤‡à¤µà¥‡à¤Ÿ à¤²à¤¿à¤®à¤¿à¤Ÿà¥‡à¤¡ "+"\n"+"\n"+"\n"+"\n";
					Log.e("Str value","<<<unicode2>>>>"+str);
					tv_selectLang.setText("à¤�à¤µà¥‹à¤²à¥�à¤¤à¥‡ à¤¸à¤¿à¤¸à¥�à¤Ÿà¤® à¤ªà¥�à¤°à¤¾à¤‡à¤µà¥‡à¤Ÿ à¤²à¤¿à¤®à¤¿à¤Ÿà¥‡à¤¡");	
				}else if (position==1) {
					str = "à²Žà²µà³Šà²²à³�à²¤à³† à²¸à²¿à²¸à³�à²Ÿà²®à³� à²ªà³�à²°à³ˆà²µà³‡à²Ÿà³� à²²à²¿à²®à²¿à²Ÿà³†à²¡à³� "+"\n"+"\n"+"\n"+"\n";	
					tv_selectLang.setText("à²Žà²µà³Šà²²à³�à²¤à³† à²¸à²¿à²¸à³�à²Ÿà²®à³� à²ªà³�à²°à³ˆà²µà³‡à²Ÿà³� à²²à²¿à²®à²¿à²Ÿà³†à²¡à³� ");	
				}else if (position==2) {
					str = "à°Žà°µà±‹à°²à±�à°Ÿà±‡ à°¸à°¿à°¸à±�à°Ÿà°‚ à°ªà±�à°°à±ˆà°µà±‡à°Ÿà±� à°²à°¿à°®à°¿à°Ÿà±†à°¡à±�"+"\n"+"\n"+"\n"+"\n";	
					tv_selectLang.setText("à°Žà°µà±‹à°²à±�à°Ÿà±‡ à°¸à°¿à°¸à±�à°Ÿà°‚ à°ªà±�à°°à±ˆà°µà±‡à°Ÿà±� à°²à°¿à°®à°¿à°Ÿà±†à°¡à±�");
				}else if (position==3) {
					str = "à®Žà®µà¯‹à®³à¯�à®Ÿà¯‡ à®šà®¿à®¸à¯�à®Ÿà®®à¯� à®ªà®¿à®°à¯ˆà®µà¯‡à®Ÿà¯� à®²à®¿à®©à¯�à®®à®¿à®Ÿà¯‡à®¤à¯�"+"\n"+"\n"+"\n"+"\n";	
					tv_selectLang.setText("à®Žà®µà¯‹à®³à¯�à®Ÿà¯‡ à®šà®¿à®¸à¯�à®Ÿà®®à¯� à®ªà®¿à®°à¯ˆà®µà¯‡à®Ÿà¯� à®²à®¿à®©à¯�à®®à®¿à®Ÿà¯‡à®¤à¯�");
				}else if (position==4) {
					str = "Ø§ÙˆÙ„ØªÛ’ Ø³Ø³Ù¹Ù… Ù¾Ø±Ø§Ø¦ÛŒÙˆÛŒÙ¹ Ù„Ù…ÛŒÙ¹Úˆ"+"\n"+"\n"+"\n"+"\n";	
					tv_selectLang.setText("Ø§ÙˆÙ„ØªÛ’ Ø³Ø³Ù¹Ù… Ù¾Ø±Ø§Ø¦ÛŒÙˆÛŒÙ¹ Ù„Ù…ÛŒÙ¹Úˆ");
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}});
		
		sp_SelectPosition = (Spinner)dlgUnicodeText.findViewById(R.id.sp_SelectPosition);
		List<String> list = new ArrayList<String>();
		list.add("Left");
		list.add("Center");
		list.add("Right");
		ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,list);
		dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_SelectPosition.setAdapter(dataAdapter1);
		sp_SelectPosition.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
				// TODO Auto-generated method stub
				if(position==0){
					justfypostion = Justify.ALIGN_LEFT;
				}else if (position==1) {
					justfypostion = Justify.ALIGN_CENTER;	
				}else if (position==2) {
					justfypostion = Justify.ALIGN_RIGHT;	
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Button btn_unicode = (Button) dlgUnicodeText.findViewById(R.id.btn_unicode);
		btn_unicode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
					dlgUnicodeText.dismiss();
					UnicodeASync uniasynctask = new UnicodeASync();
					uniasynctask.execute(0);
				}
		});
		dlgUnicodeText.show();
	
	}
	   //This method shows the EnterTextAsyc  AsynTask operation 
	public class UnicodeASync extends AsyncTask<Integer, Integer, Integer> {
		// displays the progress dialog until background task is completed
		@Override
		protected void onPreExecute() {
			progressDialog(context, "Please Wait...");
			super.onPreExecute();
		}
		 //Task of EnterTextAsyc performing in the background	
		@Override
		protected Integer doInBackground(Integer... params) {
			try {
				
			Bitmap bmpImg3 = TextGenerator.bmpDrawText(str, 32, justfypostion);
			Log.e("Str value","<<<unicode 3>>>>"+str);
			Log.e("Str value","<<<unicode 3>>>>"+justfypostion);
			byte[] bBmpFileData = TextGenerator.bGetBmpFileData(bmpImg3);
			ByteArrayInputStream bb= new ByteArrayInputStream(bBmpFileData);
		    iRetVal = Act_Printer.ptr.iBmpPrint(bb);
		
			} catch (NullPointerException e) {
				iRetVal = DEVICE_NOTCONNECTED;
			//	if(Frag_Printer.help)Log.e(Frag_Printer.TAG,"+++++LEOPARD Printer EXP StartPrinting+++++"+iRetVal);
				return iRetVal;
			}
			return iRetVal;
		}
		// This displays the status messages of EnterTextAsyc in the dialog box 
		@Override
		protected void onPostExecute(Integer result) {
			Log.e("DATA ","+++++TIMER STOPED+++++++");
			/*llprog.setVisibility(View.GONE);
			btnOk.setVisibility(View.VISIBLE);*/
			dlgPg.dismiss();
			//if(Frag_Printer.help)Log.e(Frag_Printer.TAG,"+++++LEOPARD Printer onPostExecute StartPrinting+++++"+iRetVal);
			if (iRetVal == DEVICE_NOTCONNECTED) {
				hander.obtainMessage(1,"Device not connected").sendToTarget();
			} else if (iRetVal == Printer.PR_SUCCESS) {
				hander.obtainMessage(1, "Print Success").sendToTarget();
			} else if (iRetVal == Printer.PR_PLATEN_OPEN) {
				hander.obtainMessage(1,"Printer platen is open").sendToTarget();
			} else if (iRetVal == Printer.PR_PAPER_OUT) {
				hander.obtainMessage(1,"Printer paper is out").sendToTarget();
			} else if (iRetVal == Printer.PR_IMPROPER_VOLTAGE) {
				hander.obtainMessage(1,"Printer improper voltage").sendToTarget();
			} else if (iRetVal == Printer.PR_FAIL) {
				hander.obtainMessage(1, "Printer failed").sendToTarget();
			} else if (iRetVal == Printer.PR_PARAM_ERROR) {
				hander.obtainMessage(1,"Printer param error").sendToTarget();
			}else if (iRetVal == Printer.PR_NO_RESPONSE) {
				hander.obtainMessage(1,"No response from impress device").sendToTarget();
			}else if (iRetVal== Printer.PR_DEMO_VERSION) {
				hander.obtainMessage(1,"Library is in demo version").sendToTarget();
			}else if (iRetVal==Printer.PR_INVALID_DEVICE_ID) {
				hander.obtainMessage(1,"Connected  device is not license authenticated.").sendToTarget();
			}else if (iRetVal==Printer.PR_ILLEGAL_LIBRARY) {
				hander.obtainMessage(1,"Library not valid").sendToTarget();
			}
			super.onPostExecute(result);
		}
	}
}
