package com.jungyoonsung.startingpoint.Settings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

@SuppressLint("AppCompatCustomView")
public class SquareCardView2 extends CardView {

    public SquareCardView2(@NonNull Context context) {
        super(context);
    }

    public SquareCardView2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareCardView2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredHeight();
        setMeasuredDimension(width, width);
    }
}