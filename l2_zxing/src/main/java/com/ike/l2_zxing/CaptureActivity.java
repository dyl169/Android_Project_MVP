
package com.ike.l2_zxing;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.ike.l2_zxing.camera.CameraManager;
import com.ike.l2_zxing.decoding.CaptureActivityHandler;
import com.ike.l2_zxing.decoding.InactivityTimer;
import com.ike.l2_zxing.utils.CapInterface;
import com.ike.l2_zxing.view.ViewfinderView;
import com.yunzao.umaLite.l2_zxing.R;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/**
 * Initial the camera
 *
 * @author yeungeek
 * @ClassName: CaptureActivity
 * @Description: 扫描页面，如果想要自定义页面，复写zxing_code_scan.xml文件
 * @date 2013-4-28 下午12:59:44
 */
public class CaptureActivity extends AppCompatActivity implements Callback {
    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    protected InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private Bitmap surfaceBitmap;
    private Timer timer;
    private CapInterface capInterface;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zxing_code_scan);
//        Arad.bus.register(this);
        CameraManager.init(getApplication());
        initControl();
    }

    private void initControl() {
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initScan();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    public void onDestroy() {
        inactivityTimer.shutdown();
        caleanTimer();
//        Arad.bus.unregister(this);
        super.onDestroy();
    }

    /**
     * 初始化扫描
     */
    public void initScan() {
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    /**
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();

        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(ZxingUtil.RESULT_PARAM, result.getText());
        resultIntent.putExtras(bundle);
        setResult(RESULT_OK, resultIntent);
        caleanTimer();
        finish();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            cameraManager = CameraManager.get();
            cameraManager.openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
        }
//        startGetBitmapTimer(cameraManager);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    /**
     * 扫描正确后的震动声音,如果感觉apk大了,可以删除
     */
    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    protected void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };


    //自动打开闪光灯功能

    private void startGetBitmapTimer(final CameraManager cameraManager) {

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (cameraManager != null)
                    cameraManager.getCamera().takePicture(null, null, new PicCallBacKImpl());
            }
        }, 1000, 10000);
    }

    private void caleanTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * 拍照后的最主要的返回
     */
    private class PicCallBacKImpl implements Camera.PictureCallback {
        @Override
        public void onPictureTaken(final byte[] data, Camera camera) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (surfaceBitmap != null) {
                            if (!surfaceBitmap.isRecycled()) {
                                surfaceBitmap.recycle();
                            }
                            surfaceBitmap = null;
                        }
                        surfaceBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        cameraManager.getCamera().startPreview();
                        getBrig(surfaceBitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }


    private void getBrig(Bitmap surfaceBitmap) {
        Drawable localDrawable = new BitmapDrawable(surfaceBitmap);
        Bitmap bitmap = Bitmap
                .createBitmap(
                        localDrawable.getIntrinsicWidth(),
                        localDrawable.getIntrinsicHeight(),
                        localDrawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        localDrawable.setBounds(0, 0, localDrawable.getIntrinsicWidth(),
                localDrawable.getIntrinsicHeight());
        localDrawable.draw(canvas);

        int localWidth = this.getWindowManager().getDefaultDisplay().getWidth();
        int y[] = {0, 4, 9, 13, 18, 23, 28, 33, 38, 43, 48};
        int x[] = {0, localWidth / 8, localWidth * 2 / 8, localWidth * 3 / 8,
                localWidth * 4 / 8, localWidth * 5 / 8, localWidth * 6 / 8,
                localWidth * 7 / 8, localWidth};

        int r;
        int g;
        int b;
        int number = 0;
        double bright = 0;
        Integer localTemp;
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < y.length; j++) {
                number++;
                localTemp = (Integer) bitmap.getPixel(x[i], y[j]);
                r = (localTemp | 0xff00ffff) >> 16 & 0x00ff;
                g = (localTemp | 0xffff00ff) >> 8 & 0x0000ff;
                b = (localTemp | 0xffffff00) & 0x0000ff;

                bright = bright + 0.299 * r + 0.587 * g + 0.114 * b;
                Log.i("xiao", "bright = " + bright);
            }
        }
        localDrawable = null;
        bitmap = null;
        bright = (int) (bright / number);
        Log.e("获取摄像机得到的亮度", "bright = " + bright);
    }

}
