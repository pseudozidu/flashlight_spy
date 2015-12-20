package com.example.flashlightexample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
//Arkadaþlar burada yeni db oluþtrmamýzýn amacý giden smsleri ayrýbir db de tutmak neden böyle yaptýðýmýzý ilerde anlayacaksýnýz.
	//mantýk ayný olduðu için diðer db den kopyalayalým
	
public class SQLiteDataBaseHelperGidenSms extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "zaferdbgidensms";
	public static final int DATABASE_VERSION = 1;
	public static final String TABLE_NEWS = "zaferiki";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_DATETIME = "dateTime";
	public static final String COLUMN_ACTION = "action";
	public static final String COLUMN_CONTENT = "content";
	public static final String COLUMN_SERIALNUMBER = "serialNumber";
	public static final String CREATE_QUERY = "create table " + TABLE_NEWS
			+ " (" + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_DATETIME + " text not null, " + COLUMN_ACTION
			+ " text not null, " + COLUMN_CONTENT + " text not null, "
			+ COLUMN_SERIALNUMBER + " text not null)";
	private static SQLiteDataBaseHelperGidenSms dbHelperGidenSms;

	public SQLiteDataBaseHelperGidenSms(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_QUERY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("drop table if exits "+ TABLE_NEWS);
		onCreate(db);
	}
	public static SQLiteDataBaseHelperGidenSms getInstance(Context context){
		if(dbHelperGidenSms==null){
			dbHelperGidenSms=new SQLiteDataBaseHelperGidenSms(context);
		}
		return dbHelperGidenSms;
	
	
}
	
}
