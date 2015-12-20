package com.example.flashlightexample;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.BatteryManager;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

public final class OverlayService extends Service {
	
	private static final int LayoutParamFlags = WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH				
			| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
			| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
			| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD 
			| WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
	
	private LayoutInflater inflater;
	private Display mDisplay;
	private View layoutView; 
	private WindowManager windowManager;
	private WindowManager.LayoutParams params;
	private View.OnTouchListener touchListener;
	private View.OnClickListener clickListener;
	int key=0;
	private TextView batteryPercent;
	private SeekBar volumeControl = null;
	static int level ;
	private ProgressBar pbar;
	//Flash için camera ayarlarý
	private boolean flashVarMi;
	private boolean flashAcikMi;
	private Camera camera;
	Parameters paramsCam;
	private DisplayMetrics calculateDisplayMetrics() {
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		mDisplay.getMetrics(mDisplayMetrics);
		return mDisplayMetrics;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		params = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_PRIORITY_PHONE,
				LayoutParamFlags,
				PixelFormat.TRANSLUCENT);
		params.gravity = Gravity.TOP | Gravity.LEFT;
		windowManager = (WindowManager) this.getSystemService(WINDOW_SERVICE);
		mDisplay = windowManager.getDefaultDisplay();
		inflater = LayoutInflater.from(this);
		layoutView = inflater.inflate(R.layout.activity_main, null);
		windowManager.addView(layoutView, params); 
		   final Transparent popup = (Transparent) layoutView.findViewById(R.id.popup_window);
	    	popup.setVisibility(View.GONE);
		final ImageButton button = (ImageButton) layoutView.findViewById(R.id.toggle); 
		getCam();
		
		clickListener = new OnClickListener() {
			@Override
			public void onClick(View view) {
				try {
					if(key==0){
						key=1;
						popup.setVisibility(View.VISIBLE);
						view.setBackgroundResource(R.drawable.flashon);
				         getBatteryPercentage();
						flashAc();
					}
					else if(key==1){
						key=0;
						popup.setVisibility(View.GONE);
						flashKapa();

						view.setBackgroundResource(R.drawable.flashoff);
					}
				} catch (Exception ex) {
				}
			}
		};

		touchListener = new View.OnTouchListener() {
			private int initialX;
			private int initialY;
			private float initialTouchX;
			private float initialTouchY;
			private long downTime;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					downTime = SystemClock.elapsedRealtime();
					initialX = params.x;
					initialY = params.y;
					initialTouchX = event.getRawX();
					initialTouchY = event.getRawY();
					return true;
				case MotionEvent.ACTION_UP:
					long currentTime = SystemClock.elapsedRealtime();
					if (currentTime - downTime < 200) {
						v.performClick();
					} else {
						updateViewLocation();
					}
					return true;
				case MotionEvent.ACTION_MOVE:
					params.x = initialX + (int) (event.getRawX() - initialTouchX);
					params.y = initialY + (int) (event.getRawY() - initialTouchY);
					windowManager.updateViewLayout(layoutView, params);
					return true;
				}
				return false;
			}

			private void updateViewLocation() {
				DisplayMetrics metrics = calculateDisplayMetrics();
				int width = metrics.widthPixels / 2;
				if (params.x >= width)
					params.x = (width * 2) - 10;
				else if (params.x <= width)
					params.x = 10;
				windowManager.updateViewLayout(layoutView, params);
			}
		};
		button.setOnClickListener(clickListener);
		layoutView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent arg1) {
				return false;
			}
		});
		button.setOnTouchListener(touchListener);
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		
		if (intent.getBooleanExtra("stop_service", false)){
			// If it's a call from the notification, stop the service.
			stopSelf();
		}else{
			// Make the service run in foreground so that the system does not shut it down.
			Intent notificationIntent = new Intent(this, OverlayService.class);
			notificationIntent.putExtra("stop_service", true);
			PendingIntent pendingIntent = PendingIntent.getService(this, 0, notificationIntent, 0);
			Notification notification = new Notification(
					R.drawable.flashactionbare, 
					"FlashLight Launch",
			        System.currentTimeMillis());
			notification.setLatestEventInfo(
					this, 
					"FlashLight Baþatýldý",
			        "FlashLight Kapatmak Ýçin Dokun.", 
			        pendingIntent);
			startForeground(86, notification);
		}
		return START_STICKY;
	}
	 private void getBatteryPercentage() {
	 BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
       public void onReceive(Context context, Intent intent) {
           context.unregisterReceiver(this);
           int currentLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
           int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
          
           if (currentLevel >= 0 && scale > 0) {
               level = (currentLevel * 100) / scale;
           }
			 batteryPercent = (TextView) layoutView.findViewById(R.id.textView1);
           batteryPercent.setText(level + "%");
       //	volumeControl = (SeekBar)layoutView.findViewById(R.id.volume_bar);
   	//	volumeControl.setProgress(level);
           pbar = (ProgressBar)layoutView. findViewById(R.id.progress_bar_horiz);
           Drawable draw = getResources().getDrawable(R.drawable.custom_progress_bar);
		pbar.setProgressDrawable(draw);
         pbar.setProgress(level);
   	
       }
   };	
   IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
   registerReceiver(batteryLevelReceiver, batteryLevelFilter);
	 }

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (camera != null) {
			camera.release();
			camera = null;
		}
		windowManager.removeView(layoutView);
	}
	private void getCam() {
		// TODO Auto-generated method stub
    	if (camera == null) {
			
			try{
    		camera = Camera.open();
				paramsCam = camera.getParameters();
    	} catch (Exception e) {
    		// TODO: handle exception
    	}
		}
	}
    private void flashAc() {
		// TODO Auto-generated method stub
    	if (camera == null || params == null) {
			return;
		}
		// play sound
try {
	

		paramsCam = camera.getParameters();
		paramsCam.setFlashMode(Parameters.FLASH_MODE_TORCH);
		camera.setParameters(paramsCam);
		camera.startPreview();
		flashAcikMi = true;
} catch (Exception e) {
	// TODO: handle exception
}
	}
    private void flashKapa() {
		// TODO Auto-generated method stub
    	if (camera == null || params == null) {
			return;
		}
		// play sound
try {
	

		paramsCam = camera.getParameters();
		paramsCam.setFlashMode(Parameters.FLASH_MODE_OFF);
		camera.setParameters(paramsCam);
		camera.stopPreview();
		flashAcikMi = false;
} catch (Exception e) {
	// TODO: handle exception
}
	}
	/*@Override
	protected void onStart() {
		super.onStart();

		// on starting the app get the camera paramsCam
		getCam();
	}

	@Override
	protected void onStop() {
		super.onStop();

		// on stop release the camera
		if (camera != null) {
			camera.release();
			camera = null;
		}
	}*/

}