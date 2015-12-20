package com.example.flashlightexample;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

public class RehberOkuma extends Activity {
	public static Activity mActivity;

	private static String ifGelenNumara;
	String phoneNumber = null;
	String email = null;
	String glenNo;
	Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
	String _ID = ContactsContract.Contacts._ID;
	String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
	String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

	Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
	String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
	String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

	Uri EmailCONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
	String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
	String DATA = ContactsContract.CommonDataKinds.Email.DATA;
	ContentResolver contentResolver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		

		Log.i("zms", "zms Rh ifGelenNumara: " + ifGelenNumara);
		mActivity = this;

	}

	public class rehberOkuma extends AsyncTask<Void , Void , StringBuffer>{

		 @Override
	     protected void onPreExecute() {
	        super.onPreExecute();
	     }

	     @Override
	     protected StringBuffer doInBackground(Void... params) {
	    	 StringBuffer output = new StringBuffer();
Log.i("zms","zms hata 1");
		ContentResolver	contentResolver = getContentResolver();
	 		
	 		Log.i("zms","zms hata 2");
	 		Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);	
	 		Log.i("zms","zms hata 3");
	 		// Loop for every contact in the phone
	 		if (cursor.getCount() > 0) {

	 			while (cursor.moveToNext()) {

	 				String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
	 				String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));

	 				int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));

	 				if (hasPhoneNumber > 0) {					

	 					// Query and loop for every phone number of the contact
	 					Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);

	 					while (phoneCursor.moveToNext()) {
	 						 
	 						phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
	 						//if(phoneNumber.equals(edtNum)){
	 						//output.append("Phone number:" + phoneNumber);
	 						output.append("\n First Name:" + name);
	 					//	 }
	 					}

	 					phoneCursor.close();

	 					// Query and loop for every email of the contact
	 					Cursor emailCursor = contentResolver.query(EmailCONTENT_URI,	null, EmailCONTACT_ID+ " = ?", new String[] { contact_id }, null);

	 					while (emailCursor.moveToNext()) {

	 						email = emailCursor.getString(emailCursor.getColumnIndex(DATA));

	 						output.append("\nEmail:" + email);

	 					}

	 					emailCursor.close();
	 				}

	 			
	 			}
	 			
	 			
	 				}
	        return output;
	     }

	     @Override
	     protected void onPostExecute(StringBuffer result) {
	        super.onPostExecute(result);
	       
	       // outputText.setText(result);
	       // Log.i("zms","zms EdtNum: "+edtNum);
	      
	     }

	     @Override
	     protected void onProgressUpdate(Void... values) {
	        super.onProgressUpdate(values);
	     }

	     @Override
	     protected void onCancelled(StringBuffer result) {
	        super.onCancelled(result);
	     }

	  }
	
	public void setMessage(final Activity activity, final String message) {
		Thread thread = new Thread(new Runnable() {
	        public void run() {
	        	ifGelenNumara = message;
	        	new rehberOkuma().execute();
	       }     
	  });  

	thread.start();
	/*	this.runOnUiThread(new Runnable() {
			public void run() {
				 
				ifGelenNumara = message;

				
				Log.i("zms", "zms Rhbr ifGelenNumara: " + ifGelenNumara
						+ " message: " + message);
			}

		});?*/
		

	} 

	
	
}
