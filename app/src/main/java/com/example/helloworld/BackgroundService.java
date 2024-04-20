package com.example.helloworld;

import android.app.Service;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class BackgroundService extends Service {
    private static final int CHANGE_INTERVAL = 30 * 1000;

    private WallpaperManager wallpaperManager;
    private Handler handler;
    private Runnable changeWallpaperRunnable;
    private List<Integer> imageResources;
    private int currentImageIndex = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        wallpaperManager = WallpaperManager.getInstance(this);
        handler = new Handler();
        imageResources = Arrays.asList(R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        changeWallpaperRunnable = new Runnable() {
            @Override
            public void run() {
                changeWallpaper();
                handler.postDelayed(this, CHANGE_INTERVAL);
            }
        };
        handler.postDelayed(changeWallpaperRunnable, CHANGE_INTERVAL);
        return START_STICKY;
    }

    private void changeWallpaper() {
        try {
            int imageResourceId = imageResources.get(currentImageIndex);
            currentImageIndex = currentImageIndex + 1 == 4 ? 0 : currentImageIndex + 1;

            InputStream imageStream = getResources().openRawResource(imageResourceId);
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);

            wallpaperManager.setBitmap(bitmap);
        } catch (IOException e) {
            Log.e("WallpaperService", "Error changing wallpaper", e);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(changeWallpaperRunnable);
    }
}
