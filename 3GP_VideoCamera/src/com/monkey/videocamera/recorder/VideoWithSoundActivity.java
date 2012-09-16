package com.monkey.videocamera.recorder;

import android.content.Context;

public class VideoWithSoundActivity extends VideoActivity {

	@Override
	protected VideoCameraView getVideoCamera(Context con) {
		return new VideoCameraView(con, true);
	}
}
