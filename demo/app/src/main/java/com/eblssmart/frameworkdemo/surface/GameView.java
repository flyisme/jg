package com.eblssmart.frameworkdemo.surface;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.eblssmart.frameworkdemo.R;

public class GameView extends SurfaceView implements Runnable {

    private Context mContext;
    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;
    private Path mPath;
    private float mBitmapX, mBitmapY, mViewWidth, mViewHeight;
    private Bitmap mBitmap;
    private RectF mWinnerRect;
    private FlashLightCone mFlashLightCone;

    private boolean mRunning = false;

    private Thread mGameThread;

    public GameView(Context context) {
        super(context);
        init(context);
    }


    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mSurfaceHolder = getHolder();
        mPaint = new Paint();
        mPaint.setColor(Color.DKGRAY);
        mPath = new Path();
    }

    private void setUpBitmap() {
        mBitmapX = (int) Math.floor(
                Math.random() * (mViewWidth - mBitmap.getWidth()));
        mBitmapY = (int) Math.floor(
                Math.random() * (mViewHeight - mBitmap.getHeight()));
        mWinnerRect = new RectF(mBitmapX, mBitmapY,
                mBitmapX + mBitmap.getWidth(),
                mBitmapY + mBitmap.getHeight());
    }


    @Override
    public void run() {
        Canvas canvas;
        while (mRunning) {
            if (mSurfaceHolder.getSurface().isValid()) {
                float x = mFlashLightCone.getX();
                float y = mFlashLightCone.getY();
                float radius = mFlashLightCone.getRadius();

                try {
                    canvas = mSurfaceHolder.lockCanvas();
                    //保存当前画布
                    canvas.save();
                    //白色填充画布
                    canvas.drawColor(Color.WHITE);
                    //绘制Android位图
                    canvas.drawBitmap(mBitmap, mBitmapX, mBitmapY, mPaint);
                    mPath.addCircle(x, y, radius, Path.Direction.CCW);

                    // The method clipPath(path, Region.Op.DIFFERENCE) was
                    // deprecated in API level 26. The recommended alternative
                    // method is clipOutPath(Path), which is currently available
                    // in API level 26 and higher.
                    //剪切
                    if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                        canvas.clipPath(mPath, Region.Op.DIFFERENCE);
                    } else {
                        canvas.clipOutPath(mPath);
                    }
                    canvas.drawColor(Color.BLACK);
                    if (x > mWinnerRect.left && x < mWinnerRect.right
                            && y > mWinnerRect.top && y < mWinnerRect.bottom) {
                        canvas.drawColor(Color.WHITE);
                        canvas.drawBitmap(mBitmap, mBitmapX, mBitmapY, mPaint);
                        canvas.drawText(
                                "WIN!", mViewWidth / 4, mViewHeight / 2, mPaint);
                    }
                    //回退路径，取消锁定
                    mPath.rewind();
                    canvas.restore();
                    mSurfaceHolder.unlockCanvasAndPost(canvas);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        // Invalidate() is inside the case statements because there are
        // many other motion events, and we don't want to invalidate
        // the view for those.
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setUpBitmap();
                updateFrame((int) x, (int) y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                updateFrame((int) x, (int) y);
                invalidate();
                break;
            default:
                // Do nothing.
        }
        return true;
    }

    private void updateFrame(int x, int y) {
        mFlashLightCone.update(x, y);
    }


    public void pause() {
        mRunning = false;
        try {
            mGameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        mRunning = true;
        mGameThread = new Thread(this);
        mGameThread.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewHeight = h;
        mViewWidth = w;
        mFlashLightCone = new FlashLightCone(mViewWidth, mViewHeight);
        mPaint.setTextSize(mViewHeight / 5);
        mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.android);
        setUpBitmap();
    }
}
