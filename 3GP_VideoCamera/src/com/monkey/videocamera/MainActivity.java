package com.monkey.videocamera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.monkey.videocamera.recorder.VideoActivity;
import com.monkey.videocamera.recorder.VideoWithSoundActivity;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        

		findViewById(R.id.rec_btn).setOnClickListener(this);
		findViewById(R.id.rec_sound_btn).setOnClickListener(this);
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    */

	@Override
	public void onClick(View arg0) {
		int id = arg0.getId();
		// 録画ボタン
		if ( id == R.id.rec_btn ) {
			Intent intent =
				new Intent(this, VideoActivity.class);
//				new Intent(this, Recorder2Activity.class);
			startActivity(intent);
		} else if ( id == R.id.rec_sound_btn ) {
			Intent intent =
					new Intent(this, VideoWithSoundActivity.class);
			startActivity(intent);
		}
	}
}
