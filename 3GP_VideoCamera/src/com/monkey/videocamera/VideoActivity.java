package com.monkey.videocamera;

import java.io.File;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;

public class VideoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(new CameraView(this));
	}

	class CameraView extends SurfaceView implements SurfaceHolder.Callback {
		private SurfaceHolder holder; // ホルダー
		private MediaRecorder recorder; // メディアレコーダー

		// private static final int VIDEO_SOURCE =
		// MediaRecorder.VideoSource.DEFAULT;
		private static final int VIDEO_SOURCE = MediaRecorder.VideoSource.CAMERA;

		private static final int OUTPUT_FORMAT = MediaRecorder.OutputFormat.THREE_GPP;

		private static final int VIDEO_ENCODER = MediaRecorder.VideoEncoder.MPEG_4_SP;

		// private static final int VIDEO_ENCODER =
		// MediaRecorder.VideoEncoder.DEFAULT;

		/**
		 * コンストラクタ
		 * 
		 * @param context
		 */
		public CameraView(Context context) {
			super(context);

			// サーフェイスホルダーの生成
			holder = getHolder();
			holder.addCallback(this);

			// レコーダ生成
			recorder = new MediaRecorder();
		}

		/**
		 * サーフェイス生成イベントの処理
		 * 
		 * @holder
		 */
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			recorder.setVideoSource(VIDEO_SOURCE);
			recorder.setOutputFormat(OUTPUT_FORMAT);
			recorder.setVideoEncoder(VIDEO_ENCODER);

			File file = createFile();
			recorder.setOutputFile(file.getAbsolutePath());

			recorder.setPreviewDisplay(holder.getSurface());

			// フレームレート
			recorder.setVideoFrameRate(40);
			// recorder.setVideoFrameRate(30);

			// ビデオサイズ
			recorder.setVideoSize(640, 480); // OK
			// recorder.setVideoSize(840, 680); //NG
			// recorder.setVideoSize(480, 640); //NG
			// recorder.setVideoSize(1280, 960); //NG

			// 録画開始
			try {
				recorder.prepare();
				recorder.start();
			} catch (Exception e) {
				android.util.Log.e("start", e.toString());
			}
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {

		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			try {
				recorder.stop();
			} catch (Exception e) {
				android.util.Log.e("destroy", e.toString());
			}

			recorder.reset();
			// recorder.release();
		}

		/**
		 * ファイル名生成
		 * 
		 * @return
		 */
		private File createFile() {
			File dir = Environment.getExternalStorageDirectory();
			CharSequence dateStr = DateFormat.format("yyyyMMddkkmmss",
					new Date());
			// File file = File.createTempFile("hal-",".3gp", dir);
			return new File(dir, "hal-" + dateStr + ".3gp");
		}
	}
}
