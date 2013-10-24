package com.tlorrain.android.rezenerator;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class RezeneratorActivity extends Activity {

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// you can reference icons as before
		((ImageView) findViewById(R.id.im_prog)).setImageResource(R.drawable.my_icon);
	}

}
