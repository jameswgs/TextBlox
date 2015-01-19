package uk.co.bbc.textbloxexample;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TextBlox extends LinearLayout {

    public static final int MAXLINES = 10;

    public TextBlox(final Context context) {
        super(context);
    }

    public TextBlox(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public TextBlox(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        if(hasTextViewChildren()) {
            restAllTextViewChildrenToMaxLines();
            int height = MeasureSpec.getSize(heightMeasureSpec);
            int infHeightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE, MeasureSpec.AT_MOST);

            super.onMeasure(widthMeasureSpec, infHeightMeasureSpec);
            //Log.d("TextBlox", "on measure with textView children " + getMeasuredHeight() + " <> " + height );

            while(getMeasuredHeight() > height) {
                if(allChildrenHaveOneLineMax()) {
                    break;
                }
                else {
                    reduceMaxLinesByOne();
                    super.onMeasure(widthMeasureSpec, infHeightMeasureSpec);
                }
                Log.d("TextBlox", "height is greater than measureSpec height");
            }
        }
        else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private void reduceMaxLinesByOne() {
        for(int i=0; i<getChildCount(); i++) {
            View child = getChildAt(i);
            if(child instanceof TextView) {
                TextView textView = (TextView) child;
                int maxLines = textView.getMaxLines();
                if(maxLines>1) {
                    textView.setMaxLines(maxLines-1);
                    break;
                }
            }
        }
    }

    private boolean allChildrenHaveOneLineMax() {
        for(int i=0; i<getChildCount(); i++) {
            View child = getChildAt(i);
            if(child instanceof TextView) {
                if(((TextView)child).getMaxLines()>1) {
                    return false;
                }
            }
        }
        return true;
    }

    private void restAllTextViewChildrenToMaxLines() {
        for(int i=0; i<getChildCount(); i++) {
            View child = getChildAt(i);
            if(child instanceof TextView) {
                ((TextView)child).setMaxLines(MAXLINES);
            }
        }
    }

    private boolean hasTextViewChildren() {
        for(int i=0; i<getChildCount(); i++) {
            View child = getChildAt(i);
            if(child instanceof TextView) {
                return true;
            }
        }
        return false;
    }
}
