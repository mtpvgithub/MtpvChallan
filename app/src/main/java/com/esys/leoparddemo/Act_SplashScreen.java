package com.esys.leoparddemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.button.R;

public class Act_SplashScreen extends Activity {

	// Splash screen timer
	private static int SPLASH_TIME_OUT = 3000;
	ScaleAnimation scale;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		 ImageView img_view =(ImageView)findViewById(R.id.add_idProof);
		 scale=new ScaleAnimation( -2 ,2 ,-2 ,2);
         scale.setDuration(500);
         img_view.startAnimation(scale);
		new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

			@Override
			public void run() {
				// This method will be executed once the timer is over
				// Start your app main activity
				Intent i = new Intent(Act_SplashScreen.this, Act_Main.class);
				startActivity(i);

				// close this activity
				finish();
			}
		}, SPLASH_TIME_OUT);
		
		
	}

}
