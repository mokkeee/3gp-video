package com.monkey.videocamera.recorder;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class RecorderActivity extends Activity {
	VideoCameraView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.view = new VideoCameraView(this);
		setContentView(view);
	}

}
