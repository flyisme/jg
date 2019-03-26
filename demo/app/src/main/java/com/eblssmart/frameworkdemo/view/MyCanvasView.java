package com.eblssmart.frameworkdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.eblssmart.frameworkdemo.R;

public class MyCanvasView extends View {

    private Paint mPaint;
    private Paint mPaintText;
    private Path mPath;

    private Bitmap mExtraBitmap;
    private Canvas mExtraCanvas;

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    private Rect mFrame;

    public MyCanvasView(Context context) {
        super(context);
        init();
    }


    public MyCanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyCanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.opaque_yellow));
        mPaint.setAntiAlias(true);
        mPath = new Path();
        mPaint.setDither(true);

        mPaintText = new Paint();
        mPaintText.setColor(getResources().getColor(R.color.cyan1));
        mPaintText.setStyle(Paint.Style.FILL_AND_STROKE);
        //设置问本相对于画笔的对齐方式
        mPaintText.setTextAlign(Paint.Align.CENTER);
        mPaintText.setTextSize(40f);
        mPaintText.setStrokeWidth(1);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(12);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mExtraBitmap, 0, 0, null);

        // canvas.drawRect(mFrame, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mExtraBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mExtraCanvas = new Canvas(mExtraBitmap);
        mExtraCanvas.drawColor(ResourcesCompat.getColor(getResources(), R.color.buttonLabel, null));
        int inset = 40;
        mFrame = new Rect(inset, inset, w - inset, h - inset);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                touchUp();
                break;
        }
        return true;
    }

    private void touchUp() {
        // Reset the path so it doesn't get drawn again.
        mPath.reset();
    }

    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, x, y);
            mX = x;
            mY = y;
            //mExtraCanvas.drawPath(mPath, mPaint);
            mExtraCanvas.drawColor(ResourcesCompat.getColor(getResources(), R.color.buttonLabel, null));

            int x1 = (int) Math.max(100, x);
            int y1 = (int) Math.max(100, y);
            mExtraCanvas.drawRect(new Rect(12, 12, x1, y1), mPaint);
            mExtraCanvas.drawText("x:" + x + ",y:" + y, x1 - 20, y1 + 50, mPaintText);
        }
    }

    private void touchStart(float x, float y) {
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }
}
