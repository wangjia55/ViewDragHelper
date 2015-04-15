package com.jacob.viewdraghelper.lesson4;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jacob.viewdraghelper.R;

/**
 * Created by jacob-wj on 2015/4/15.
 */
public class DragLayoutFour extends RelativeLayout {
    private final double AUTO_OPEN_SPEED_LIMIT = 800;
    private ViewDragHelper mViewDragHelper;
    private Button mButtonQueen;

    private int mVerticalRange;

    private boolean isOpen;

    private int mDragState;

    private int mDragBorder;


    public DragLayoutFour(Context context) {
        this(context, null);
    }

    public DragLayoutFour(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragLayoutFour(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mViewDragHelper = ViewDragHelper.create(this, 1f, new ViewDragCallBack());
        mButtonQueen = (Button) findViewById(R.id.button_queen);
        isOpen = false;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mVerticalRange = (int) (h * 0.5f);
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        super.computeScroll();
    }

    private class ViewDragCallBack extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return mButtonQueen == child;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            if (state == mDragState) {
                return;
            }

            if ( (state == ViewDragHelper.STATE_IDLE)) {
               if (mDragBorder == mVerticalRange) {
                    isOpen = true;
                }
            }

            mDragState = state;
            super.onViewDragStateChanged(state);
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            mDragBorder = top;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return mVerticalRange;
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isButtonTarget(event) && mViewDragHelper.shouldInterceptTouchEvent(event)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isButtonTarget(MotionEvent event) {
        int[] buttonLocation = new int[2];
        mButtonQueen.getLocationOnScreen(buttonLocation);
        int bottom = buttonLocation[1] + mButtonQueen.getMeasuredHeight();
        int top = buttonLocation[1];

        int y = (int) event.getRawY();
        return y > top && y < bottom;
    }

    public boolean isMoving() {
        return (mDragState == ViewDragHelper.STATE_DRAGGING || mDragState == ViewDragHelper.STATE_SETTLING);
    }

}
