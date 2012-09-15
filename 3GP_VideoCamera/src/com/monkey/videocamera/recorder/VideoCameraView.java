package com.monkey.videocamera.recorder;

import java.io.File;
import java.util.Date;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class VideoCameraView extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder holder; // ホルダー
	private MediaRecorder recorder; // メディアレコーダー

	/** ビデオ起動中か否か */
	private boolean serviceEnable;

	/** Photo-U動画最大ファイルサイズ */
	private static final long MAX_FILESIZE_PHOTO_U = 2 * 1024 * 1024;

	/**
	 * コンストラクタ
	 * 
	 * @param context
	 */
	public VideoCameraView(Context context) {
		super(context);

		// サーフェイスホルダーの生成
		holder = getHolder();
		holder.addCallback(this);

		serviceEnable = false;
	}

	/**
	 * サーフェイス生成イベントの処理
	 * 
	 * @holder
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		synchronized (this) {
			if (!serviceEnable) {
				// レコーダ生成
				recorder = new MediaRecorder();

				try {
					// recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
					// //NG
					recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
					// recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);

					recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

					// recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
					recorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);

					File file = createFile();
					recorder.setOutputFile(file.getAbsolutePath());

					recorder.setMaxFileSize(MAX_FILESIZE_PHOTO_U);

					recorder.setPreviewDisplay(holder.getSurface());

					// フレームレート
					recorder.setVideoFrameRate(40);
					// recorder.setVideoFrameRate(30);

					// ビデオサイズ
					// TODO スマホの情報取得して解像度とか取れないの？
					recorder.setVideoSize(640, 480); // OK
					// recorder.setVideoSize(840, 680); //NG
					// recorder.setVideoSize(480, 640); //NG
					// recorder.setVideoSize(1280, 960); //NG

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
	 * サーフェイスサイズ変更時は何も行わない
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	/**
	 * サーフェイスを停止するときのコールバック関数
	 * たまに呼ばれないことがある
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		stopRecording();
	}

	/**
	 * 起動中の録画を停止する
	 */
	public void stopRecording() {
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
	 * @return
	 */
	private File createFile() {
		// TODO もう少し綺麗なやり方あるんじゃないかと。。
		File dir = new File(Environment.getExternalStorageDirectory()
				.toString() + "/3gpVideoCamera");
		if (dir.exists() == false) {
			dir.mkdir();
		}
		CharSequence dateStr = DateFormat.format("yyyyMMddkkmmss", new Date());
		return new File(dir, "hal-" + dateStr + ".3gp");
	}
}
