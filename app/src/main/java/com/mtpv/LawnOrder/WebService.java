package com.mtpv.LawnOrder;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

public class WebService extends Activity  {
	
   //public static String dyamic_Ip= IP_SettingsActivity.IP;  
	public static String dyamic_Ip= "";  
	//public final String SOAP_ADDRESS = "http://www.echallan.org/39BService/services/BServiceImpl?WSDL";
	public static  String SOAP_ADDRESS = dyamic_Ip+"/HydPettyCaseService/services/PettyCaseServiceImpl?wsdl";
	//public final String SOAP_ADDRESS = "http://192.168.11.8/39BService/services/BServiceImpl?WSDL";
	//public final String SOAP_ADDRESS = "http://192.168.11.10:8080/39BService/services/BServiceImpl?WSDL";
	public final String WSDL_TARGET_NAMESPACE = "http://service.mother.com";
	public final String SOAP_ACTION = "http://service.mother.com/authenticateUser";
	public final String AUTHENTICATE_USER = "authenticateUser";
	public final String PSNAMES = "getPSMaster";
	public final String PSPOINTNAMES = "getPointDetailsByPscode";
	public final String IDProofMaster = "getIDProofMaster";
	public final String DetainItemsMaster = "getDetainItemsMaster";
	public final String Genratechallan = "generateChallan";
	public final String getDuplicatePrint = "getDuplicatePrint";
	public final String getDuplicatePrintByPettyCase = "getDuplicatePrintByPettyCase";
	public final String getReport ="getReport";
	private static String NAMESPACE = "http://service.mother.com";
	//public static String SOAP_ACTION = NAMESPACE;
	public static String SOAP_ACTION_ID = NAMESPACE + "getDuplicatePrint", challansListResponse;
	public static String SOAP_ACTION_DUPLICATE = NAMESPACE + "getDuplicatePrintByPettyCase", ecaseResponse;
	
	public WebService() 
	{
		
		 Log.e("web service url after", ""+SOAP_ADDRESS);
	}


	public String AuthenticateUser(String eT_userID, String eT_password) {
		// TODO Auto-generated method stub
		
		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,AUTHENTICATE_USER);
		PropertyInfo pi=new PropertyInfo();
		pi.setName("ET_userID");
			pi.setValue(eT_userID);
			pi.setType(Integer.class);
			request.addProperty(pi);
			pi=new PropertyInfo();
			
			pi.setName("ET_password");
	        pi.setValue(eT_password);
	        pi.setType(Integer.class);
	        request.addProperty(pi);
		
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		    envelope.dotNet = true;
		    
		    envelope.setOutputSoapObject(request);

		    HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
		    Object response=null;
		    
		    try
		    {
		    httpTransport.call(SOAP_ACTION, envelope);
		    response = envelope.getResponse();
		    }
		    catch (Exception exception)
		    {
		    response=exception.toString();
		    }
			
			return  response.toString();
	}


	public String GetPsNames(String unitCode) {
		// TODO Auto-generated method stub
		
		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,PSNAMES);
		PropertyInfo pi=new PropertyInfo();
			
			pi.setName("unitCode");
			pi.setValue(unitCode);
			pi.setType(Integer.class);
			request.addProperty(pi);

		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		 envelope.dotNet = true;
		 envelope.setOutputSoapObject(request);
		 HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
		 Object response=null;
		
		 try
		    {
		    httpTransport.call(SOAP_ACTION, envelope);
		    response = envelope.getResponse();
		    }
		    catch (Exception exception)
		    {
		    response=exception.toString();
		    }
			
			return  response.toString();
				
		
	}


	public String GetPsPointNames(String sPSN) {
		// TODO Auto-generated method stub
		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,PSPOINTNAMES);
		PropertyInfo pi=new PropertyInfo();
		pi.setName("ET_userID");
			pi.setValue(sPSN);
			pi.setType(Integer.class);
			request.addProperty(pi);
		//	pi=new PropertyInfo();
			
			
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		    envelope.dotNet = true;
		    
		    envelope.setOutputSoapObject(request);

		    HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
		    Object response=null;
		    
		    try
		    {
		    httpTransport.call(SOAP_ACTION, envelope);
		    response = envelope.getResponse();
		    }
		    catch (Exception exception)
		    {
		    response=exception.toString();
		    }
			
			return  response.toString();

	}


	public String GetIDProofMaster(String sPSN) {
		// TODO Auto-generated method stub
		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,IDProofMaster);
		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		 envelope.dotNet = true;
		 envelope.setOutputSoapObject(request);
		 HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
		 Object response=null;
		
		 try
		    {
		    httpTransport.call(SOAP_ACTION, envelope);
		    response = envelope.getResponse();
		    }
		    catch (Exception exception)
		    {
		    response=exception.toString();
		    }
			
			return  response.toString();
	}


	public String GETDetainItemsMaster() {
		// TODO Auto-generated method stub
		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,DetainItemsMaster);
		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		 envelope.dotNet = true;
		 envelope.setOutputSoapObject(request);
		 HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
		 Object response=null;
		
		 try
		    {
		    httpTransport.call(SOAP_ACTION, envelope);
		    response = envelope.getResponse();
		    }
		    catch (Exception exception)
		    {
		    response=exception.toString();
		    }
			
			return  response.toString();
			
	}


	public String generateChallan(String unitCode, String unitName,
			String bookedPsCode, String bookedPsName, String pointCode,
			String pointName, String operaterCd, String operaterName,
			String pidCd, String pidName, String password, String cadreCD,
			String cadre, String onlineMode, String imageEvidence,
			String imgEncodedData, String offenceDt, String offenceTime,
			String firmRegnNo, String shopName, String ShopOwnerName,String ShopAddress, String location, String psCode,
			String psName, String respondantName, String respondantFatherName,
			String respondantAddress, String respondantContactNo,
			String respondantAge, String iDCode, String iDDetails,
			String witness1Name, String witness1FatherName,
			String witness1Address, String witness2Name,
			String witness2FatherName, String witness2Address,
			String detainedItems, String simId, String imeiNo, String macId,
			String gpsLatitude, String gpsLongitude) {
		// TODO Auto-generated method stub
		
		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,Genratechallan);
		PropertyInfo pi=new PropertyInfo();



		pi.setName("unitCode");
		pi.setValue(unitCode);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("unitName");
		pi.setValue(unitName);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("bookedPsCode");
		pi.setValue(bookedPsCode);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("bookedPsName");
		pi.setValue(bookedPsName);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("pointCode");
		pi.setValue(pointCode);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("pointName");
		pi.setValue(pointName);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("operaterCd");
		pi.setValue(operaterCd);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("operaterName");
		pi.setValue(operaterName);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("pidCd");
		pi.setValue(pidCd);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("pidName");
		pi.setValue(pidName);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("password");
		pi.setValue(password);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("cadreCD");
		pi.setValue(cadreCD);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("cadre");
		pi.setValue(cadre);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("onlineMode");
		pi.setValue(onlineMode);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("imageEvidence");
		pi.setValue(imageEvidence);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("imgEncodedData");
		pi.setValue(imgEncodedData);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("offenceDt");
		pi.setValue(offenceDt);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("offenceTime");
		pi.setValue(offenceTime);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("firmRegnNo");
		pi.setValue(firmRegnNo);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("shopName");
		pi.setValue(shopName);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("ShopOwnerName");
		pi.setValue(ShopOwnerName);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("ShopAddress");
		pi.setValue(ShopAddress);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("location");
		pi.setValue(location);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("psCode");
		pi.setValue(psCode);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("psName");
		pi.setValue(psName);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("respondantName");
		pi.setValue(respondantName);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("respondantFatherName");
		pi.setValue(respondantFatherName);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("respondantAddress");
		pi.setValue(respondantAddress);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		
		pi.setName("respondantContactNo");
		pi.setValue(respondantContactNo);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("respondantAge");
		pi.setValue(respondantAge);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("iDCode");
		pi.setValue(iDCode);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("iDDetails");
		pi.setValue(iDDetails);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("witness1Name");
		pi.setValue(witness1Name);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("witness1FatherName");
		pi.setValue(witness1FatherName);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("witness1Address");
		pi.setValue(witness1Address);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("witness2Name");
		pi.setValue(witness2Name);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("witness2FatherName");
		pi.setValue(witness2FatherName);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("witness2Address");
		pi.setValue(witness2Address);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("detainedItems");
		pi.setValue(detainedItems);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("simId");
		pi.setValue(simId);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("imeiNo");
		pi.setValue(imeiNo);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("macId");
		pi.setValue(macId);
		pi.setType(Integer.class);
		request.addProperty(pi);
		pi=new PropertyInfo();
		
		pi.setName("gpsLatitude");
			pi.setValue(gpsLatitude);
			pi.setType(Integer.class);
			request.addProperty(pi);
			pi=new PropertyInfo();
			
			pi.setName("gpsLongitude");
	        pi.setValue(gpsLongitude);
	        pi.setType(Integer.class);
	        request.addProperty(pi);
		
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		    envelope.dotNet = true;
		    
		    envelope.setOutputSoapObject(request);

		    HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
		    Object response=null;
		    
		    try
		    {
		    httpTransport.call(SOAP_ACTION, envelope);
		    response = envelope.getResponse();
		    }
		    catch (Exception exception)
		    {
		    response=exception.toString();
		    }
			
			return  response.toString();
		
	}


	public String getDuplicatePrintByPettyCase(String pettyCaseNo) {
		// TODO Auto-generated method stub
		new AsyncGetPrintePettyCase().execute();
		return  ecaseResponse.toString();
		
	}
	
	class AsyncGetPrintePettyCase extends AsyncTask<Void,Void, String>{
		ProgressDialog dialog = new ProgressDialog(WebService.this);
			
		@Override
		protected void onPreExecute() {
			  dialog.setMessage("Please wait.....!");
	            dialog.setIndeterminate(true);
	            dialog.setCancelable(true);
	            dialog.show();
		
			super.onPreExecute();
		}
	@Override
	protected String doInBackground(Void... params) {try {
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
		SoapObject request = new SoapObject(NAMESPACE, "getDuplicatePrintByPettyCase");
	    request.addProperty("pettyCaseNO",DuplicatePrintActivity.pettyCaseNo);
		
	    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		
		Log.i("request", "" + request);
		
		HttpTransportSE androidHttpTransport = new HttpTransportSE(ip+"/HydPettyCaseService/services/PettyCaseServiceImpl?wsdl");
		Log.i("androidHttpTransport", "" + androidHttpTransport);
		androidHttpTransport.call(SOAP_ACTION_DUPLICATE, envelope);
		Object result = envelope.getResponse();
		ecaseResponse=result.toString();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return ecaseResponse;
	}//end of doinBack

		@Override
		protected void onPostExecute(String result) {

					dialog.dismiss();
					super.onPostExecute(result);
				
				}
			}


	public String getReport(String offenceDate, String pidCode) {
		// TODO Auto-generated method stub
		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,getReport);
		PropertyInfo pi=new PropertyInfo();
		    pi.setName("offenceDate");
			pi.setValue(offenceDate);
			pi.setType(Integer.class);
			request.addProperty(pi);
			pi=new PropertyInfo();
			
			pi.setName("pidCode");
	        pi.setValue(pidCode);
	        pi.setType(Integer.class);
	        request.addProperty(pi);
		
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		    envelope.dotNet = true;
		    
		    envelope.setOutputSoapObject(request);

		    HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
		    Object response=null;
		    
		    try
		    {
		    httpTransport.call(SOAP_ACTION, envelope);
		    response = envelope.getResponse();
		    
		    }
		    catch (Exception exception)
		    {
		    response=exception.toString();
		    }
			
			return  response.toString();
	}


	public String getDuplicatePrint(String offenceDate, String pidCode,
			String shopName, String firmRegnNo, String responsdentName) {
		// TODO Auto-generated method stub
		return null;
	}


}
