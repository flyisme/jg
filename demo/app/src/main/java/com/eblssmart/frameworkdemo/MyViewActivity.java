package com.eblssmart.frameworkdemo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.eblssmart.frameworkdemo.surface.GameView;
import com.eblssmart.frameworkdemo.view.MyCanvasView;

public class MyViewActivity extends AppCompatActivity {

    private Canvas mCanvas;
    private Paint mPaint;
    private Paint mPaintText;
    private Bitmap mBitmap;

    private ImageView mImageView;
    private Rect mRect = new Rect();
    private Rect mBounds = new Rect();

    private static final int OFFSET = 120;
    private int mOffset = OFFSET;

    private static final int MULTIPLIER = 100;

    private int mColorBackground;
    private int mColorRectangle;
    private int mColorAccent;

    private GameView mGameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_my_view);


        mPaint = new Paint();
        mPaintText = new Paint(Paint.UNDERLINE_TEXT_FLAG);

        mColorBackground = ResourcesCompat.getColor(getResources(),
                R.color.colorBackground, null);
        mColorRectangle = ResourcesCompat.getColor(getResources(),
                R.color.colorRectangle, null);
        mColorAccent = ResourcesCompat.getColor(getResources(),
                R.color.colorAccent, null);

        mPaint.setColor(mColorBackground);
        mPaintText.setColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
        mPaintText.setTextSize(70);

        mImageView = findViewById(R.id.iv);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawSomething(v);
            }
        });*/

        /*MyCanvasView view = new MyCanvasView(this);
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(view);*/

        setContentView(R.layout.activity_my_view2);
        /*
        mGameView = new GameView(this);
        mGameView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(mGameView);*/

    }


    private void drawSomething(View view) {
        int vWidth = view.getWidth();
        int vHeight = view.getHeight();
        int halfWidth = vWidth / 2;
        int halfHeight = vHeight / 2;
        if (mBitmap == null) {
            mBitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888);
            mImageView.setImageBitmap(mBitmap);
        }
        if (mOffset == OFFSET) {
            mCanvas = new Canvas(mBitmap);
            mCanvas.drawColor(mColorBackground);
            mCanvas.drawText(getString(R.string.keep_tapping), 100, 100, mPaintText);
        } else {
            if (mOffset < halfWidth && mOffset < halfHeight) {
                mPaint.setColor(mColorRectangle - MULTIPLIER * mOffset);
                mRect.set(mOffset, mOffset, vWidth - mOffset, vHeight - mOffset);
                mCanvas.drawRect(mRect, mPaint);
            } else {
                mPaint.setColor(mColorAccent);
                mCanvas.drawCircle(halfWidth, halfHeight, halfWidth / 3, mPaint);
                String text = getString(R.string.done);
                // Get bounding box for text to calculate where to draw it.
                mPaintText.getTextBounds(text, 0, text.length(), mBounds);
                // Calculate x and y for text so it's centered.
                int x = halfWidth - mBounds.centerX();
                int y = halfHeight - mBounds.centerY();
                mCanvas.drawText(text, x, y, mPaintText);
            }
        }
        mOffset += OFFSET;

        view.invalidate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGameView!=null)
        {
            mGameView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGameView!=null)
        {
            mGameView.resume();
        }
    }
}
