package com.monkey.videocamera.recorder;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class Recorder2Activity extends Activity {

	private VideoCameraService service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		VideoCameraService video = new  VideoCameraService(this);
		setContentView(video);
		this.service = video;
	}

	@Override
	protected void onStop() {
		super.onStop();
		service.stopService();
	}
}
