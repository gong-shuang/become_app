package com.gs.learn.device.widget;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import com.gs.learn.device.util.BitmapUtil;
import com.gs.learn.device.util.MetricsUtil;
import com.gs.learn.device.util.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.ShutterCallback;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraView extends SurfaceView {
	private static final String TAG = "CameraView";
	private Context mContext;
	private Camera mCamera;
	private SurfaceHolder mHolder = null;
	private boolean isPreviewing = false;
	private Point mCameraSize;
	private int mCameraType = CAMERA_BEHIND;
	public static int CAMERA_BEHIND = 0;
	public static int CAMERA_FRONT = 1;

	public CameraView(Context context) {
		this(context, null);
	}

	public CameraView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		mHolder = getHolder();
		//去除黑色背景。TRANSLUCENT半透明；TRANSPARENT透明
		mHolder.setFormat(PixelFormat.TRANSPARENT);
		//mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mHolder.addCallback(mSurfaceCallback);
	}

	public int getCameraType() {
		return mCameraType;
	}
	
	public void setCameraType(int CameraType) {
		mCameraType = CameraType;
	}

	// 下面是单拍的代码
	private String mPhotoPath;

	public String getPhotoPath() {
		return mPhotoPath;
	}

	public void doTakePicture() {
		if(isPreviewing && mCamera!=null) {
			mCamera.takePicture(mShutterCallback, null, mPictureCallback);
		}
	}

	//快门按下的回调，在这里可以设置类似播放“咔嚓”声之类的操作。默认的就是咔嚓。
	private ShutterCallback mShutterCallback = new ShutterCallback() {
		public void onShutter() {
			Log.d(TAG, "onShutter...");
		}
	};
	
	//获得拍照图片的回调。在此保存图片
	private PictureCallback mPictureCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.d(TAG, "onPictureTaken...");
			Bitmap raw = null;
			if(null != data) {
				raw = BitmapFactory.decodeByteArray(data, 0, data.length);//data是字节数据，将其解析成位图
				mCamera.stopPreview();
				isPreviewing = false;
			}
			Bitmap bitmap = BitmapUtil.getRotateBitmap(raw, (mCameraType==CAMERA_BEHIND)?90:-90);
			mPhotoPath = String.format("%s%s.jpg", BitmapUtil.getCachePath(mContext), Utils.getNowDateTime());
			BitmapUtil.saveBitmap(mPhotoPath, bitmap, "jpg", 80);
			Log.d(TAG, "bitmap.size="+(bitmap.getByteCount()/1024)+"K"+", path="+mPhotoPath);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//再次进入预览
			mCamera.startPreview();
			isPreviewing = true;
		}
	};

	//预览画面状态变更时的回调
	private SurfaceHolder.Callback mSurfaceCallback = new SurfaceHolder.Callback() {
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			// 当预览视图创建的时候开启相机
			mCamera = Camera.open(mCameraType);
			try {
				// 设置预览
				mCamera.setPreviewDisplay(holder);
				mCameraSize = MetricsUtil.getCameraSize(mCamera.getParameters(), MetricsUtil.getSize(mContext));
				Log.d(TAG, "width="+mCameraSize.x+", height="+mCameraSize.y);
			    Camera.Parameters parameters = mCamera.getParameters();
				// 设置预览大小
			    parameters.setPreviewSize(mCameraSize.x, mCameraSize.y);
				// 设置图片保存时的分辨率大小
				parameters.setPictureSize(mCameraSize.x, mCameraSize.y);
				// 设置格式
				parameters.setPictureFormat(ImageFormat.JPEG);
				// 设置自动对焦。前置摄像头似乎无法自动对焦
				if (mCameraType == CameraView.CAMERA_BEHIND) {
					parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
				}
			    mCamera.setParameters(parameters);
			} catch (Exception e) {
				e.printStackTrace();
				mCamera.release();
				mCamera = null;
			}
			return;
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			mCamera.setDisplayOrientation(90);
			mCamera.startPreview();
			isPreviewing = true;
			mCamera.autoFocus(null);
			//setPreviewCallback给连拍使用
			mCamera.setPreviewCallback(mPreviewCallback);
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			mCamera.setPreviewCallback(null);
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
	};

	// 下面是连拍的代码
	private boolean bShooting = false;
	private int shooting_num = 0;
	private ArrayList<String> mShootingArray;
	
	public ArrayList<String> getShootingList() {
		Log.d(TAG, "mShootingArray.size()="+mShootingArray.size());
		return mShootingArray;
	}
	
	public void doTakeShooting() {
		mShootingArray = new ArrayList<String>();
		bShooting = true;
		shooting_num = 0;
	}

	private PreviewCallback mPreviewCallback = new PreviewCallback() {
		@Override
		public void onPreviewFrame(byte[] data, Camera camera) {
			Log.d(TAG, "onPreviewFrame bShooting="+bShooting+", shooting_num="+shooting_num);
			if (!bShooting) {
				return;
			}
			Camera.Parameters parameters = camera.getParameters();
			int imageFormat = parameters.getPreviewFormat();
			int w = parameters.getPreviewSize().width;
			int h = parameters.getPreviewSize().height;
			Rect rect = new Rect(0, 0, w, h);
			YuvImage yuvImg = new YuvImage(data, imageFormat, w, h, null);
			try {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				yuvImg.compressToJpeg(rect, 80, bos);
				Bitmap raw = BitmapFactory.decodeByteArray(
						bos.toByteArray(), 0, bos.size());
				Bitmap bitmap = BitmapUtil.getRotateBitmap(raw, (mCameraType==CAMERA_BEHIND)?90:-90);
				String path = String.format("%s%s.jpg", BitmapUtil.getCachePath(mContext), Utils.getNowDateTimeFull());
				BitmapUtil.saveBitmap(path, bitmap, "jpg", 80);
				Log.d(TAG, "bitmap.size="+(bitmap.getByteCount()/1024)+"K"+", path="+path);
				//再次进入预览
				camera.startPreview();
				shooting_num++;
				mShootingArray.add(path);
				if (shooting_num > 8) {  //每次连拍9张
					bShooting = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	};

}
