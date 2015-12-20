package com.example.flashlightexample;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends Activity {

//rehberi key value þeklinde yani numara isim þeklnde tutma
//Burasý Flash Ac-kapa yaptmýz ana class'ýmýz uygulama ilk açýldýnda ekrana gelen yani
// þimdi arkadaþlar ilk önce tanýmlamarýmýzý yapalým..

//arkadaþlar bunlar rehberi okumamz için gerekli standar þeyler hiç karýþtýrmayýn..
//þimdi rehberi ben asyncTask da çektim çünkü diðer class'ýmýzdan gelecek bilgiler flan var bir ..
// arkadaþlar þimdi biz rehberi okuyorz bu okudumuz rehberle gelen bilgilerin içinden ilerde seçim yapabilmemiz için yani gönderdimiz
//numaraya ait ismi bulmamýz için ben bu rehberi okuyup bir hashMap'A attým bi hashMap oluþturalým bu hashMap key-value yani gönderdim numara 
// key value deðer ise numaraya ait isim olaacak bir biri ile eþleþtirdim burda
//mutlaka static koyn yoksa asyncTask ýn dýþýnda uçar gider bu rehber bilgileri


//calender okuma

int key=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Intent overlayIntent = new Intent();
		overlayIntent.setClass(this, OverlayService.class);
		this.startService(overlayIntent);
		finish();
    }}
    //Handle menu için
//        final Transparent popup = (Transparent) findViewById(R.id.popup_window);
//    	popup.setVisibility(View.GONE);
// 
//	final Button btn=(Button)findViewById(R.id.handle);
//	btn.setOnClickListener(new View.OnClickListener() {
// 
//		@Override
//		public void onClick(View arg0) {
//			if(key==0){
//				key=1;
//				popup.setVisibility(View.VISIBLE);
//				btn.setBackgroundResource(R.drawable.flason);
//				flashAc();
//			}
//			else if(key==1){
//				key=0;
//				popup.setVisibility(View.GONE);
//				flashKapa();
//
//				btn.setBackgroundResource(R.drawable.flashoff
//						);
//			}
//		}
//	});
//    
//        try {
//			
//		
//        /*
//		 * Cihazda Kamera var mý Yok mu?
//		 */
//		flashVarMi = getApplicationContext().getPackageManager()
//				.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
//getCam();
//		if (!flashVarMi) {
//			// device doesn't support flash
//			// Show alert message and close the application
//			AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
//					.create();
//			alert.setTitle("Error");
//			alert.setMessage("Sorry, your device doesn't support flash light!");
//			alert.setButton("OK", new DialogInterface.OnClickListener() {
//				public void onClick(DialogInterface dialog, int which) {
//					// closing the application
//					finish();
//				}
//			});
//			alert.show();
//			return;
//		}
//        } catch (Exception e) {
//			// TODO: handle exception
//		}
//	
//
//        
//        
//    }
//	
///*	public void onClick(View v) {
//// TODO Auto-generated method stub
//Log.i("zms","zms flash Oncclick");
//
//if(!flashAcikMi){
//flashAc();
//Log.i("zms","zms flasaçýk");
//btnSwitch.setBackgroundResource(R.drawable.ic_action_next_item);
//}	
//else{
//flashKapa();
//Log.i("zms","zms flasKapa");
//}
//}
//*/
//    
//    private void getCam() {
//		// TODO Auto-generated method stub
//    	if (camera == null) {
//			
//			try{
//    		camera = Camera.open();
//				params = camera.getParameters();
//    	} catch (Exception e) {
//    		// TODO: handle exception
//    	}
//		}
//	}
//    private void flashAc() {
//		// TODO Auto-generated method stub
//    	if (camera == null || params == null) {
//			return;
//		}
//		// play sound
//try {
//	
//
//		params = camera.getParameters();
//		params.setFlashMode(Parameters.FLASH_MODE_TORCH);
//		camera.setParameters(params);
//		camera.startPreview();
//		flashAcikMi = true;
//} catch (Exception e) {
//	// TODO: handle exception
//}
//	}
//    private void flashKapa() {
//		// TODO Auto-generated method stub
//    	if (camera == null || params == null) {
//			return;
//		}
//		// play sound
//try {
//	
//
//		params = camera.getParameters();
//		params.setFlashMode(Parameters.FLASH_MODE_OFF);
//		camera.setParameters(params);
//		camera.stopPreview();
//		flashAcikMi = false;
//} catch (Exception e) {
//	// TODO: handle exception
//}
//	}
//	@Override
//	protected void onStart() {
//		super.onStart();
//
//		// on starting the app get the camera params
//		getCam();
//	}
//
//	@Override
//	protected void onStop() {
//		super.onStop();
//
//		// on stop release the camera
//		if (camera != null) {
//			camera.release();
//			camera = null;
//		}
//	}
	



