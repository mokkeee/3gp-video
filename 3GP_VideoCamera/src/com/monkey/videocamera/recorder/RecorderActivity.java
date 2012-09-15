package com.monkey.videocamera.recorder;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class RecorderActivity extends Activity {
	private VideoCameraView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.view = new VideoCameraView(this);
		setContentView(view);
	}

	@Override
	protected void onStop() {
		super.onStop();
		view.stopRecording();
	}
}
