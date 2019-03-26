package com.eblssmart.frameworkdemo.surface;

public class FlashLightCone {

    private float mX, mY, mRadius;

    public FlashLightCone(float viewWidth, float viewHeight) {
        mX = viewWidth;
        mY = viewHeight;
        mRadius = (viewWidth <= viewHeight ? mX / 5 : mY / 5);
    }

    public float getX() {
        return mX;
    }

    public float getY() {
        return mY;
    }

    public float getRadius() {
        return mRadius;
    }

    public void update(float newX, float newY) {
        mY = newY;
        mX = newX;
    }
}
