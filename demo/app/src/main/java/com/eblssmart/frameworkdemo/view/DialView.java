package com.eblssmart.frameworkdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.eblssmart.frameworkdemo.R;

public class DialView extends View {

    private static int SELECTION_COUNT = 4; // Total number of selections.
    private float mWidth;                   // Custom view width.
    private float mHeight;                  // Custom view height.
    private Paint mTextPaint;               // For text in the view.
    private Paint mDialPaint;               // For dial circle in the view.
    private float mRadius;                  // Radius of the circle.
    private int mActiveSelection;           // The active selection.
    // String buffer for dial labels and float for ComputeXY result.
    private final StringBuffer mTempLabel = new StringBuffer(8);
    private final float[] mTempResult = new float[2];

    private int mFanOnColor;
    private int mFanOffColor;

    public DialView(Context context) {
        super(context);
        init(null);
    }

    public DialView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public DialView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        // Set default fan on and fan off colors
        mFanOnColor = Color.CYAN;
        mFanOffColor = Color.GRAY;
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.DialView);
            mFanOffColor = array.getColor(R.styleable.DialView_fanOffColor, Color.GRAY);
            mFanOnColor = array.getColor(R.styleable.DialView_fanOnColor, Color.CYAN);
        }

        //抗锯齿
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        //设置问本相对于画笔的对齐方式
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(40f);
        mDialPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDialPaint.setColor(mFanOffColor);

        // Initialize current selection.
        mActiveSelection = 0;
        // TODO: Set up onClick listener for this view.
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mActiveSelection = (mActiveSelection + 1) % SELECTION_COUNT;
                // Set dial background color to green if selection is >= 1.
                if (mActiveSelection >= 1) {
                    mDialPaint.setColor(mFanOnColor);
                } else {
                    mDialPaint.setColor(mFanOffColor);
                }
                invalidate();
            }
        });

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        mRadius = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画圆圈
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mDialPaint);
        //画文本
        float labelRadius = mRadius + 20;
        StringBuffer sb = mTempLabel;
        for (int i = 0; i < SELECTION_COUNT; i++) {
            float[] xyData = computeXYForPosition(i, labelRadius);
            float x = xyData[0];
            float y = xyData[1];
            sb.setLength(0);
            sb.append(i);
            canvas.drawText(sb, 0, sb.length(), x, y, mTextPaint);
        }
        //画指示器标记
        // Draw the indicator mark.
        float markerRadius = mRadius - 35;
        float[] xyData = computeXYForPosition(mActiveSelection,
                markerRadius);
        float x = xyData[0];
        float y = xyData[1];
        canvas.drawCircle(x, y, 20, mTextPaint);
    }


    private float[] computeXYForPosition
            (final int pos, final float radius) {
        float[] result = mTempResult;
        Double startAngle = Math.PI * (9 / 8d);   // Angles are in radians.
        Double angle = startAngle + (pos * (Math.PI / 4));
        result[0] = (float) (radius * Math.cos(angle)) + (mWidth / 2);
        result[1] = (float) (radius * Math.sin(angle)) + (mHeight / 2);
        return result;
    }
}
