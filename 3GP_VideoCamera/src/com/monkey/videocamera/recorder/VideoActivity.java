package com.monkey.videocamera.recorder;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

public class VideoActivity extends Activity {
	protected VideoCameraView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.view = getVideoCamera(this);
		setContentView(view);
	}

	@Override
	protected void onStop() {
		super.onStop();
		view.stopRecording();
	}
	
	protected VideoCameraView getVideoCamera(Context con) {
		return new VideoCameraView(con);
	}
}
