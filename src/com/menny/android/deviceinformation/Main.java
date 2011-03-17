package com.menny.android.deviceinformation;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main extends Activity implements OnClickListener {
	private String mReport = "Empty";
	private LinearLayout mLayout;
	private String mAppName = "Device info";
	
	private static final String[] PACKAGES = new String[]{
		"com.menny.android.anysoftkeyboard"
		};
	
	private static final String[] SEND_TO_EMAIL = new String[]{
			"ask@evendanan.net"
			};
		
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mLayout = (LinearLayout)findViewById(R.id.layout);
        mReport = "Device information report:\n";
        try {
        	PackageInfo info = super.getApplication().getPackageManager().getPackageInfo(getApplication().getPackageName(), 0);
			mAppName  = "Device Info v"+info.versionName+"("+info.versionCode+")";
			setTextOfLabel(true, mAppName);
			for(String pkg : PACKAGES)
			{
				setTextOfLabel(false, getPkgVersion(pkg));
			}
			
			setTextOfLabel(false, "Locale: "+getResources().getConfiguration().locale.toString());
			setTextOfLabel(true, "** Device:");
			setTextOfLabel(false, "Board: "+android.os.Build.BOARD);
			setTextOfLabel(false, "Brand: "+android.os.Build.BRAND);
			setTextOfLabel(false, "Device: "+android.os.Build.DEVICE);
			setTextOfLabel(false, "Model: "+android.os.Build.MODEL);
			setTextOfLabel(false, "Product: "+android.os.Build.PRODUCT);
			setTextOfLabel(false, "TAGS: "+android.os.Build.TAGS);
			
			setTextOfLabel(true, "** OS:");
			setTextOfLabel(false, "Build release "+android.os.Build.VERSION.RELEASE + ", Inc: '"+android.os.Build.VERSION.INCREMENTAL+"'");
			setTextOfLabel(false, "Display build: "+android.os.Build.DISPLAY);
			setTextOfLabel(false, "Finger print: "+android.os.Build.FINGERPRINT);
			setTextOfLabel(false, "Build ID: "+android.os.Build.ID);
			setTextOfLabel(false, "Time: "+android.os.Build.TIME);
			setTextOfLabel(false, "Type: "+android.os.Build.TYPE);
			setTextOfLabel(false, "User: "+android.os.Build.USER);
			
			setTextOfLabel(true, "** Density:");
			DisplayMetrics metrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metrics);
			setTextOfLabel(false, "density: "+metrics.density);
			setTextOfLabel(false, "densityDpi: "+metrics.densityDpi);
			setTextOfLabel(false, "scaledDensity: "+metrics.scaledDensity);
			setTextOfLabel(false, "xdpi: "+metrics.xdpi);
			setTextOfLabel(false, "ydpi: "+metrics.ydpi);
			setTextOfLabel(true, "** Density reference:");
			setTextOfLabel(false, "DENSITY_DEFAULT: "+DisplayMetrics.DENSITY_DEFAULT);
			setTextOfLabel(false, "DENSITY_LOW: "+DisplayMetrics.DENSITY_LOW);
			setTextOfLabel(false, "DENSITY_MEDIUM: "+DisplayMetrics.DENSITY_MEDIUM);
			setTextOfLabel(false, "DENSITY_HIGH: "+DisplayMetrics.DENSITY_HIGH);
			
			setTextOfLabel(true, "** Screen:");
			setTextOfLabel(false, "heightPixels: "+metrics.heightPixels);
			setTextOfLabel(false, "widthPixels: "+metrics.widthPixels);
			
			
			setTextOfLabel(true, "** Resources:");
			setTextOfResource("values-nokeys", R.string.nokeys);
			setTextOfResource("values-12key", R.string.keys12);
			setTextOfResource("values-qwerty", R.string.qwerty);
			setTextOfResource("values-dpad", R.string.dpad);
			setTextOfResource("values-nonav", R.string.nonav);
			setTextOfResource("values-trackball", R.string.trackball);
			setTextOfResource("values-wheel", R.string.wheel);
			setTextOfResource("values-stylus", R.string.stylus);
			setTextOfResource("values-finger", R.string.finger);
			setTextOfResource("values-notouch", R.string.notouch);
			
			setTextOfResource("values-nodpi", R.string.nodpi);
			setTextOfResource("values-ldpi", R.string.ldpi);
			setTextOfResource("values-mdpi", R.string.mdpi);
			setTextOfResource("values-hdpi", R.string.hdpi);
			setTextOfResource("values-xhdpi", R.string.xhdpi);
			setTextOfResource("prefered density", R.string.pref_pdi);
			
			setTextOfResource("values-small", R.string.small);
			setTextOfResource("values-normal", R.string.normal);
			setTextOfResource("values-large", R.string.large);
			setTextOfResource("values-xlarge", R.string.xlarge);
			setTextOfResource("prefered screen", R.string.pref_screen);
			
			setTextOfResource("values-long", R.string.long_resource);
			setTextOfResource("values-notlong", R.string.notlong);
			
			setTextOfResource("values-keysexposed", R.string.keysexposed);
			setTextOfResource("values-keyssoft", R.string.keysoft);
			
			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					setTextOfLabel(true, "** Drawable:");
					setTextOfLabel(false, "icon original width: 48");
					setTextOfLabel(false, "icon original height: 48");
					ImageView i = (ImageView)findViewById(R.id.app_icon_view);
					setTextOfLabel(false, "icon actual width: "+i.getWidth());
					setTextOfLabel(false, "icon actual height: "+i.getHeight());
					
					Button sendEmail = new Button(Main.this);
					sendEmail.setText("Send report...");
					sendEmail.setOnClickListener(Main.this);
					mLayout.addView(sendEmail);
				}
			},250);
		} catch (Exception e) {
			e.printStackTrace();
			setTextOfLabel(true, "Exception: "+e.toString());
		}
    }
    
    private void setTextOfResource(String resName, int resId) {
    	setTextOfLabel(false, String.format("%s : %s", resName, getText(resId)));
	}

	private String getPkgVersion(String packageName) {
		try {
			PackageInfo info = getApplication().getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
			return packageName+" "+info.versionName+" ("+info.versionCode+")";
		} catch (NameNotFoundException e) {
			return "Failed to get '"+packageName+"' info: "+e.getMessage();
		}
	}

	private void setTextOfLabel(boolean bold, String text)
    {
    	TextView label = new TextView(this);
		label.setText(text);
		label.setTypeface(Typeface.DEFAULT, bold?Typeface.BOLD : Typeface.NORMAL);
		mLayout.addView(label);
		mReport = mReport + "\n" + text;
    }
    
	@Override
	public void onClick(View arg0) {
		/* Create the Intent */  
		final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);  
		  
		/* Fill it with Data */  
		emailIntent.setType("plain/text");  
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, SEND_TO_EMAIL);  
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mAppName);  
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, mReport);  
		//emailIntent.putExtra(Intent.EXTRA_STREAM, u);
		/* Send it off to the Activity-Chooser */  
		this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));		
	}
}