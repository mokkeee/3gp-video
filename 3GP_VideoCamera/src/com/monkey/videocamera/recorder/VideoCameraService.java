package com.monkey.videocamera.recorder;

import java.io.File;
import java.util.Date;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.monkey.videocamera.service.Service;

public class VideoCameraService extends SurfaceView implements Service,
		SurfaceHolder.Callback {
	private SurfaceHolder holder; // ホルダー
	private MediaRecorder recorder; // メディアレコーダー

	/** Photo-U動画最大ファイルサイズ */
	private static final long MAX_FILESIZE_PHOTO_U = 2 * 1024 * 1024;

	private boolean soundEnable;

	private boolean serviceEnable;

	/**
	 * コンストラクタ
	 * 
	 * @param context
	 */
	public VideoCameraService(Context context) {
		super(context);
		holder = getHolder();
		holder.addCallback(this);
		
		this.soundEnable = false;
		this.serviceEnable = false;
	}

	/**
	 * ビデオ録画を開始する
	 */
	@Override
	public void startService() {
		synchronized (this) {
			if (serviceEnable == false) {

				// レコーダ生成
				recorder = new MediaRecorder();

				try {
					recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
					if (soundEnable) {
						recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
					}

					recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

					recorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
					if (soundEnable) {
						recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
					}

					File f = createFile();
					recorder.setOutputFile(f.getAbsolutePath());

					recorder.setMaxFileSize(MAX_FILESIZE_PHOTO_U);

					recorder.setPreviewDisplay(holder.getSurface());

					// フレームレート
					recorder.setVideoFrameRate(40);

					// ビデオサイズ
					// TODO スマホの情報取得して解像度とか取れないの？
					recorder.setVideoSize(640, 480); // OK

					// 録画開始
					recorder.prepare();
					recorder.start();

					serviceEnable = true;
				} catch (Exception e) {
					android.util.Log.e("start", e.toString());
				}
			}
		}
	}

	/**
	 * ビデオを停止する
	 */
	@Override
	public void stopService() {
		synchronized (this) {
			if (serviceEnable) {
				try {
					recorder.stop();
					recorder.reset();
					recorder.release();
				} catch (Exception e) {
					android.util.Log.e("destroy", e.toString());
				}
				serviceEnable = false;
			}
		}
	}

	/**
	 * ファイル名生成
	 * 
	 * @return ファイル名
	 */
	private File createFile() {
		File dir = new File(Environment.getExternalStorageDirectory()
				.toString() + "/3gpVideoCamera");
		if (dir.exists() == false) {
			dir.mkdir();
		}
		CharSequence dateStr = DateFormat.format("yyyyMMddkkmmss", new Date());
		return new File(dir, "hal-" + dateStr + ".3gp");
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		startService();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		stopService();
	}
}
