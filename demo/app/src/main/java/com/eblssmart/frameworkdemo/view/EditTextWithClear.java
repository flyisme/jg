package com.eblssmart.frameworkdemo.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.eblssmart.frameworkdemo.R;

public class EditTextWithClear extends AppCompatEditText {
    private Drawable mClearButtonImage;

    //适用于代码方式创建的实例
    public EditTextWithClear(Context context) {
        super(context);
        init();
    }

    //从XML中创建的示例
    public EditTextWithClear(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    //默认样式
    public EditTextWithClear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mClearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_opaque_24dp, null);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showClearButton();

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() <= 0) {
                    hideClearButtton();
                }
            }
        });
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    if ((getCompoundDrawablesRelative()[2] != null)) {
                        float clearButtonStart; // Used for LTR languages
                        float clearButtonEnd;  // Used for RTL languages
                        boolean isClearButtonClicked = false;
                        // TODO: Detect the touch in RTL or LTR layout direction.

                        // Detect the touch in RTL or LTR layout direction.
                        if (getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
                            // If RTL, get the end of the button on the left side.
                            clearButtonEnd = mClearButtonImage
                                    .getIntrinsicWidth() + getPaddingStart();
                            // If the touch occurred before the end of the button,
                            // set isClearButtonClicked to true.
                            if (event.getX() < clearButtonEnd) {
                                isClearButtonClicked = true;
                            }
                        } else {
                            // Layout is LTR.
                            // Get the start of the button on the right side.
                            clearButtonStart = (getWidth() - getPaddingEnd()
                                    - mClearButtonImage.getIntrinsicWidth());
                            // If the touch occurred after the start of the button,
                            // set isClearButtonClicked to true.
                            if (event.getX() > clearButtonStart) {
                                isClearButtonClicked = true;
                            }

                        }
                        // TODO: Check for actions if the button is tapped.
                        // Check for actions if the button is tapped.
                        if (isClearButtonClicked) {
                            // Check for ACTION_DOWN (always occurs before ACTION_UP).
                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                // Switch to the black version of clear button.
                                mClearButtonImage =
                                        ResourcesCompat.getDrawable(getResources(),
                                                R.drawable.ic_clear_black_24dp, null);
                                showClearButton();
                            }
                            // Check for ACTION_UP.
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                // Switch to the opaque version of clear button.
                                mClearButtonImage =
                                        ResourcesCompat.getDrawable(getResources(),
                                                R.drawable.ic_clear_opaque_24dp, null);
                                // Clear the text and hide the clear button.
                                getText().clear();
                                hideClearButtton();
                                return true;
                            }
                        } else {
                            return false;
                        }
                    }
                }
                return false;
            }
        });
    }

    private void showClearButton() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, mClearButtonImage, null);
        }
    }

    private void hideClearButtton() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
        }
    }

}
