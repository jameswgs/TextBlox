package uk.co.bbc.textbloxexample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class ResizableRelativeLayout extends RelativeLayout {

    private float mLastX;
    private float mLastY;

    public ResizableRelativeLayout(final Context context) {
        super(context);
    }

    public ResizableRelativeLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ResizableRelativeLayout(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN :
                setLastXY(event);
                break;
            case MotionEvent.ACTION_MOVE:
                updateSize(event);
                setLastXY(event);
                break;
        }

        return true;
    }

    private void updateSize(final MotionEvent event) {
        int deltaX = (int) (event.getX() - mLastX);
        int deltaY = (int) (event.getY() - mLastY);
        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.width = getWidth() + deltaX;
        lp.height = getHeight() + deltaY;
        ((ViewGroup)getParent()).updateViewLayout(this, lp);
    }

    private void setLastXY(final MotionEvent event) {
        mLastX = event.getX();
        mLastY = event.getY();
    }
}
