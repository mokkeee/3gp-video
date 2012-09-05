package com.monkey.videocamera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.monkey.videocamera.recorder.RecorderActivity;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        

		findViewById(R.id.rec_btn).setOnClickListener(this);
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
				new Intent(this, RecorderActivity.class);
			startActivity(intent);
		}
	}
}
